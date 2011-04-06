/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.tools.classifier;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.util.ActorVisitor;
import net.sf.orcc.ir.Port;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.DPNMoC;
import net.sf.orcc.moc.KPNMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.moc.SDFMoC;
import net.sf.orcc.util.UniqueEdge;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.traverse.DepthFirstIterator;

/**
 * This class defines an actor classifier that uses symbolic execution to
 * classify an actor as static, cyclo-static, quasi-static, or dynamic.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActorClassifier implements ActorVisitor {

	private Actor actor;

	/**
	 * Creates a new classifier
	 */
	public ActorClassifier() {
	}

	/**
	 * Classifies the actor as dynamic, quasi-static, or static.
	 * 
	 * @return the class of the actor
	 */
	public void classify() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFileForLocation(
				new Path(actor.getFile()));

		try {
			IMarker[] markers = file.findMarkers(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE);
			for (IMarker marker : markers) {
				if (marker.getAttribute(IMarker.SEVERITY,
						IMarker.SEVERITY_ERROR) == IMarker.SEVERITY_INFO) {
					marker.delete();
				}
			}
		} catch (CoreException e) {
		}

		// checks for empty actors
		List<Action> actions = actor.getActions();
		if (actions.isEmpty()) {
			System.out.println("actor " + actor
					+ " does not contain any actions, defaults to dynamic");
			actor.setMoC(new KPNMoC());
			return;
		}

		// checks for actors with time-dependent behavior
		MoC moc;
		TimeDependencyAnalyzer tdAnalyzer = new TimeDependencyAnalyzer();
		if (tdAnalyzer.isTimeDependent(actor)) {
			moc = new DPNMoC();
			showMarker();
		} else {
			// merges actions with the same input/output pattern together
			//new SDFActionsMerger().visit(actor);

			// first tries SDF with *all* the actions of the actor
			moc = classifySDF(actions);
			if (!moc.isSDF()) {
				try {
					// not SDF, tries CSDF
					moc = classifyCSDF();
				} catch (OrccRuntimeException e) {
					// data-dependent behavior
				}

				if (!moc.isCSDF()) {
					// not CSDF, tries QSDF
					if (actor.getActionScheduler().hasFsm()) {
						try {
							moc = classifyQSDF();
						} catch (OrccRuntimeException e) {
							// data-dependent behavior
						}
					}
				}
			}
		}

		// set and print MoC
		actor.setMoC(moc);
		System.out.println("MoC of " + actor + ":");
		System.out.println(moc);
		System.out.println();
	}

	/**
	 * Tries to classify an actor as CSDF. Classification works only on actor
	 * with a non-empty state. The state consists of all scalar state variables
	 * that have an initial value.
	 * 
	 * @return an actor class
	 */
	private MoC classifyCSDF() {
		// new interpreter must be called before creation of ActorState
		AbstractInterpreter interpreter = newInterpreter();

		ActorState state = new ActorState(actor);
		if (state.isEmpty()) {
			FSM fsm = actor.getActionScheduler().getFsm();
			if (fsm == null || !isCycloStaticFsm(fsm)) {
				// no state, no cyclo-static FSM => dynamic
				return new KPNMoC();
			}
		}

		// creates the MoC
		CSDFMoC csdfMoc = new CSDFMoC();
		int nbPhases = 0;

		// loops until the actor goes back to the initial state, or there is a
		// data-dependent condition
		final int MAX_PHASES = 16384;
		String initialState = interpreter.getFsmState();
		do {
			interpreter.schedule();
			csdfMoc.addAction(interpreter.getScheduledAction());
			nbPhases++;
		} while ((!state.isInitialState() || !interpreter.getFsmState().equals(
				initialState))
				&& nbPhases < MAX_PHASES);

		if (nbPhases == MAX_PHASES) {
			return new KPNMoC();
		}

		// set token rates
		csdfMoc.setTokenConsumptions(actor);
		csdfMoc.setTokenProductions(actor);
		csdfMoc.setNumberOfPhases(nbPhases);

		return csdfMoc;
	}

	/**
	 * Classify the FSM of the actor this unroller was created with, starting
	 * from the given initial state, and using the given configuration.
	 * 
	 * @param initialState
	 *            the initial state of the FSM
	 * @param configuration
	 *            a configuration
	 * @return a static class
	 */
	private SDFMoC classifyFsmConfiguration(Map<Port, Expression> configuration) {
		SDFMoC sdfMoc = new SDFMoC();

		AbstractInterpreter interpretedActor = newInterpreter();
		interpretedActor.setConfiguration(configuration);

		// schedule the actor
		String initialState = actor.getActionScheduler().getFsm()
				.getInitialState().getName();
		int nbPhases = 0;
		final int MAX_PHASES = 16384;
		do {
			interpretedActor.schedule();
			Action latest = interpretedActor.getScheduledAction();
			sdfMoc.addAction(latest);
			nbPhases++;
		} while (!interpretedActor.getFsmState().equals(initialState)
				&& nbPhases < MAX_PHASES);

		if (nbPhases == MAX_PHASES) {
			throw new OrccRuntimeException("too many phases");
		}

		// set token rates
		sdfMoc.setTokenConsumptions(actor);
		sdfMoc.setTokenProductions(actor);

		return sdfMoc;
	}

	/**
	 * Tries to classify this actor with an FSM as QSDF.
	 * 
	 * @return an MoC
	 */
	private MoC classifyQSDF() {
		ActionScheduler sched = actor.getActionScheduler();
		FSM fsm = sched.getFsm();
		if (isQuasiStaticFsm(fsm)) {
			String initialState = fsm.getInitialState().getName();

			// analyze the configuration of this actor
			ConfigurationAnalyzer analyzer = new ConfigurationAnalyzer();
			analyzer.analyze(actor);

			// will unroll for each branch departing from the initial state
			QSDFMoC quasiStatic = new QSDFMoC();

			for (NextStateInfo info : fsm.getTransitions(initialState)) {
				Action action = info.getAction();
				Map<Port, Expression> configuration = analyzer
						.getConfiguration(action);
				if (configuration == null) {
					return new KPNMoC();
				}
				SDFMoC staticClass = classifyFsmConfiguration(configuration);

				quasiStatic.addConfiguration(action, staticClass);
			}

			return quasiStatic;
		} else {
			System.out.println(actor.getName()
					+ " has an FSM that is NOT compatible with quasi-static");
			return new KPNMoC();
		}
	}

	/**
	 * Tries to classify an actor as SDF. An actor is SDF if it has one action.
	 * If an actor has many actions (in a given state if it has an FSM) with the
	 * same input/output patterns, then these actions are merged by the
	 * SDFActionsMerger transformation.
	 * 
	 * @param actions
	 *            a list of actions sorted by descending priority
	 * @return a Model of Computation
	 */
	private MoC classifySDF(List<Action> actions) {
		int numActions = actions.size();
		if (numActions != 1) {
			// two cases: 1) an empty actor is considered dynamic because the
			// only actors with no actions are system actors such as source and
			// display

			// 2) an actor with many actions after the SDFActionMerger
			// transformation has been applied is dynamic
			return new KPNMoC();
		}

		// schedule
		SDFMoC sdfMoc = new SDFMoC();
		AbstractInterpreter interpretedActor = newInterpreter();
		interpretedActor.schedule();
		sdfMoc.addAction(interpretedActor.getScheduledAction());

		// set token rates
		sdfMoc.setTokenConsumptions(actor);
		sdfMoc.setTokenProductions(actor);

		// an SDF actor is a specific CSDF with 1 phase
		sdfMoc.setNumberOfPhases(1);

		return sdfMoc;
	}

	/**
	 * Returns <code>true</code> if the given FSM looks like the FSM of a
	 * cyclo-static actor, <code>false</code> otherwise. A potentially
	 * cyclo-static actor is an actor with an FSM that cycles back to its
	 * initial state.
	 * 
	 * @param fsm
	 *            a Finite State Machine
	 * @return <code>true</code> if the given FSM has cyclo-static form
	 */
	private boolean isCycloStaticFsm(FSM fsm) {
		DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
		ConnectivityInspector<State, UniqueEdge> inspector;
		inspector = new ConnectivityInspector<State, UniqueEdge>(graph);
		State initial = fsm.getInitialState();
		return inspector.pathExists(initial, initial);
	}

	/**
	 * Returns <code>true</code> if the given FSM looks like the FSM of a
	 * quasi-static actor, <code>false</code> otherwise. We simply check that
	 * for each outgoing edge of the initial state, there is a path back to the
	 * initial state.
	 * 
	 * @param fsm
	 *            a Finite State Machine
	 * @return <code>true</code> if the given FSM has quasi-static form
	 */
	private boolean isQuasiStaticFsm(FSM fsm) {
		DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
		State initialState = fsm.getInitialState();
		Set<UniqueEdge> edges = graph.outgoingEdgesOf(initialState);
		for (UniqueEdge edge : edges) {
			State target = graph.getEdgeTarget(edge);

			DepthFirstIterator<State, UniqueEdge> it;
			it = new DepthFirstIterator<FSM.State, UniqueEdge>(graph, target);

			boolean cyclesBackToInitialState = false;
			while (it.hasNext()) {
				State state = it.next();
				if (state.equals(initialState)) {
					cyclesBackToInitialState = true;
					break;
				}
			}

			if (!cyclesBackToInitialState) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Creates a new interpreted actor, initializes it, and resets the actor's
	 * token production/consumption.
	 * 
	 * @return the interpreter created
	 */
	private AbstractInterpreter newInterpreter() {
		AbstractInterpreter interpretedActor = new AbstractInterpreter(actor);

		actor.resetTokenConsumption();
		actor.resetTokenProduction();

		interpretedActor.initialize();

		return interpretedActor;
	}

	private void showMarker() {
		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IFile file = workspace.getRoot().getFileForLocation(
					new Path(actor.getFile()));

			IMarker marker = file.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
			marker.setAttribute(IMarker.MESSAGE,
					"Actor " + actor.getSimpleName() + " is time-dependent");
		} catch (CoreException e) {
		}
	}

	@Override
	public String toString() {
		return actor.toString();
	}

	@Override
	public void visit(Actor actor) {
		try {
			this.actor = actor;
			classify();
			actor = null;
		} catch (Exception e) {
			System.out.println("Error of classification for actor " + actor
					+ ":" + e);
			System.out.println("Set actor moc of " + actor + " to default");
			actor.setMoC(new DPNMoC());
		}
	}

}
