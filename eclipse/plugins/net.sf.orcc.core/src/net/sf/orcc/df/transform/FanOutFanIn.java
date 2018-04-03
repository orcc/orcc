/*
 * Copyright (c) 2016, Heriot-Watt University Edinburgh
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
package net.sf.orcc.df.transform;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfUtil;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Void;
import net.sf.orcc.df.util.TransformPreconditionPredicates;

/*
* @author Rob Stewart
* @author Idris Ibrahim
*/
public class FanOutFanIn extends DfVisitor<Void> {
	private static DfFactory dfFactory = DfFactory.eINSTANCE;
	private static IrFactory irFactory = IrFactory.eINSTANCE;

	int dataParallelismCopies;
	List<Instance> instancesToTransform;
	List<Actor> actorsToWriteToFile = new ArrayList<Actor>();

	public FanOutFanIn(int numDataParallelCopies, List<Instance> instancesToTransform) {
		dataParallelismCopies = numDataParallelCopies;
		this.instancesToTransform = instancesToTransform;
	}

	/**
	 * 
	 * @return the list of new or modified actors to be serialised to CAL source
	 *         files.
	 */
	public List<Actor> actorsToFile() {
		return actorsToWriteToFile;
	}

	public static boolean canFire(Network network, List<Instance> selectedInstances) {
		boolean canFire = true;

		List<Instance> allInstances = new ArrayList<Instance>();
		for (Vertex vert : network.getVertices()) {
			if (vert instanceof Instance) {
				allInstances.add((Instance) vert);
			}
		}

		Actor actor;
		for (Instance inst : selectedInstances) {
			actor = inst.getActor();
			/*
			 * all selected instances must have exactly one input and one output
			 * port
			 */
			boolean oneInputOneOutput = TransformPreconditionPredicates.hasOneInputPort(actor)
					&& TransformPreconditionPredicates.hasOneOutputPort(actor);

			/* exactly one successor instance of the selected instances */
			boolean oneSuccessor = rightMostInstance(allInstances, selectedInstances).getOutgoing().size() == 1;

			/* exactly one predecessor instance to the selected instances */
			boolean onePredecessor = leftMostInstance(allInstances, selectedInstances).getIncoming().size() == 1;

			/* only one action */
			boolean oneAction = TransformPreconditionPredicates.hasOneAction(actor);

			/*
			 * if any precondition checks fail for any selected instance, then
			 * the fan out/in transformation is disabled.
			 */
			if (!(oneInputOneOutput && oneSuccessor && onePredecessor && oneAction)) {
				canFire = false;
			}
		}
		return canFire;
	}

	protected Network network;

	public Void caseNetwork(Network network) {
		this.network = network;

		/* all instances in the network */
		List<Instance> allInstances = new ArrayList<Instance>();
		for (Vertex vert : network.getVertices()) {
			if (vert instanceof Instance) {
				allInstances.add((Instance) vert);
			}
		}

		Instance leftMost = leftMostInstance(allInstances, instancesToTransform);
		Instance rightMost = rightMostInstance(allInstances, instancesToTransform);
		List<Instance> leftToRight = leftToRight(leftMost, instancesToTransform, allInstances);

		/*
		 * look at the network to find the actor that is feeding data into the
		 * left most actor, the precessor actor to this pipeline.
		 */
		Instance predecessor = whatFeeds(network, leftMost);

		/*
		 * we need to look at the `network`, to find the actor that is fed data
		 * from the right most actor, i.e. the successor actor to this pipeline.
		 */
		Instance successor = whatIsFed(network, rightMost);

		// do the transformation
		String projectRoot = "/" + network.getFileName().split("/")[1];

		/*
		 * token type should the type of the tokens be what the left most actor
		 * was receiving prior to transformation
		 */
		Type fanOutTokenType = leftMost.getActor().getInputs().get(0).getType();

		String fanOutName = "FanOut" + leftMost.getSimpleName() + rightMost.getSimpleName();

		/*
		 * remove previous versions of fan out between left most and right most
		 */
		for (Actor actor : network.getAllActors()) {
			if (actor.getSimpleName().equals(fanOutName)) {
				network.remove(actor);
			}
		}
		for (Vertex vert : network.getAllActors()) {
			if (vert instanceof Instance) {
				Instance inst = (Instance) vert;
				if (inst.getSimpleName().equals(fanOutName)) {
					network.remove(inst);
				}
			}
		}

		Actor FanOut = createFanOutActor(fanOutName, dataParallelismCopies, fanOutTokenType, projectRoot);
		Instance FanOutInstance = dfFactory.createInstance(DfUtil.getSimpleName(FanOut.getName()), FanOut);
		network.add(FanOutInstance);

		/* the ports between predecessor and the fanout actor */
		Port outputPortFromPredecessor = predecessor.getOutgoingPortMap().entrySet().iterator().next().getKey();
		Port inputPortOfFanOutActor = FanOut.getInputs().get(0);

		/* remove existing connection between predecessor and leftMost actor */
		int i = 0;
		boolean removed = false;
		while (!removed && i < network.getConnections().size()) {
			Connection c = network.getConnections().get(i);
			Vertex v = c.getSource();
			if (v instanceof Actor) {
				Actor a = (Actor) v;
				if (a.getName().equals(predecessor.getName())) {
					network.getConnections().remove(i);
					removed = true;
				}
			}
			i++;
		}

		Connection connBetweenPredecessorAndFanOut = dfFactory.createConnection(predecessor, outputPortFromPredecessor,
				FanOutInstance, inputPortOfFanOutActor);
		network.getConnections().add(connBetweenPredecessorAndFanOut);

		Type fanInTokenType = rightMost.getActor().getOutputs().get(0).getType();

		String fanInName = "FanIn" + leftMost.getSimpleName() + rightMost.getSimpleName();

		/*
		 * remove previous versions of fan in between left most and right most
		 */
		for (Actor actor : network.getAllActors()) {
			if (actor.getSimpleName().equals(fanInName)) {
				network.remove(actor);
			}
		}
		for (Vertex vert : network.getAllActors()) {
			if (vert instanceof Instance) {
				Instance inst = (Instance) vert;
				if (inst.getSimpleName().equals(fanInName)) {
					network.remove(inst);
				}
			}
		}

		Actor FanIn = createFanInActor(fanInName, dataParallelismCopies, fanInTokenType, projectRoot);
		Instance FanInInstance = dfFactory.createInstance(DfUtil.getSimpleName(FanIn.getName()), FanIn);
		network.add(FanInInstance);

		/* the ports between fanin actor and the sucessor */
		Port inputPortofSucessor = successor.getIncomingPortMap().entrySet().iterator().next().getKey();
		Port outputPortFromFanInActor = FanIn.getOutputs().get(0);

		Connection connBetweensuccessorAndFanIn = dfFactory.createConnection(FanInInstance, outputPortFromFanInActor,
				successor, inputPortofSucessor);
		network.getConnections().add(connBetweensuccessorAndFanIn);

		/* remove the originally selected actors from the network */
		for (Instance inst : leftToRight) {
			network.remove(inst);
		}

		List<Instance> predecessors = new ArrayList<Instance>();
		List<Port> predecessorPorts = new ArrayList<Port>();

		/* initialise with fanout details */
		for (int k = 0; k < dataParallelismCopies; k++) {
			predecessors.add(k, FanOutInstance);
			predecessorPorts.add(k, FanOut.getOutputs().get(k));
		}

		/*
		 * walk along sorted list of selected actors, attaching copies to
		 * predecessor k and preceddor ports for k.
		 */
		for (Instance nextInstance : leftToRight) {
			for (int k = 0; k < dataParallelismCopies; k++) {
				Instance instanceClone = IrUtil.copy(nextInstance);
				instanceClone.setLabel(DfUtil.getSimpleName(nextInstance.getName()) + k);
				network.add(instanceClone);
				Port inputP = nextInstance.getActor().getInputs().get(0);
				Port outputP = nextInstance.getActor().getOutputs().get(0);
				Connection conn = dfFactory.createConnection(predecessors.get(k), predecessorPorts.get(k),
						instanceClone, inputP);
				network.add(conn);

				/* update precessors and precedessorPorts */
				predecessors.set(k, instanceClone);
				predecessorPorts.set(k, outputP);
			}
		}

		/* complete the process by connecting predecessors with fanin actor */
		for (int k = 0; k < dataParallelismCopies; k++) {
			Connection conn = dfFactory.createConnection(predecessors.get(k), predecessorPorts.get(k), FanInInstance,
					FanIn.getInputs().get(k));
			network.add(conn);
		}

		return null;
	}

	/**
	 * 
	 * @param network
	 * @param leftMost
	 *            the left most (earliest in the stream) selected instance
	 * @return the instance that is the predecessor to the left most instance
	 */
	private Instance whatFeeds(Network network, Instance leftMost) {
		Vertex v = leftMost.getIncoming().get(0).getSource();

		if (v instanceof Instance) {
			return ((Instance) v);
		} else
			return null;
	}

	/**
	 * 
	 * @param network
	 * @param rightMost
	 *            the right most (last in the stream) selected instance
	 * @return the instance that is the successor to the right most instance
	 */
	private Instance whatIsFed(Network network, Instance rightMost) {
		Vertex v = rightMost.getOutgoing().get(0).getTarget();
		if (v instanceof Instance) {
			return ((Instance) v);
		} else
			return null;
	}

	/**
	 * 
	 * @param leftMost
	 *            the instance earliest in the stream
	 * @param selectedInstances
	 *            the graphically selected instances
	 * @param allInstances
	 *            all instances in the network
	 * @return list of selected instances, ordered early to late in the stream
	 */
	private List<Instance> leftToRight(Instance leftMost, List<Instance> selectedInstances,
			List<Instance> allInstances) {
		List<Instance> ordered = new ArrayList<Instance>();
		ordered.add(leftMost);
		selectedInstances.remove(leftMost);
		if (!selectedInstances.isEmpty()) {
			Instance nextLeftMost = leftMostInstance(allInstances, selectedInstances);
			ordered.addAll(leftToRight(nextLeftMost, selectedInstances, allInstances));
		}
		return ordered;
	}

	/**
	 * 
	 * @param allInstances
	 *            all instance in the network
	 * @param selectedInstances
	 *            graphically selected instances
	 * @return the instance earliest in the stream
	 */
	private static Instance leftMostInstance(List<Instance> allInstances, List<Instance> selectedInstances) {
		Instance leftMost = null;
		for (Instance next : selectedInstances) {
			List<Instance> predecessors = allPredecesorsOfOneInstance(next);
			boolean anyInstancesInPredecessors = false;
			for (Instance act : selectedInstances) {
				if (predecessors.contains(act)) {
					anyInstancesInPredecessors = true;
				}
			}
			if (!anyInstancesInPredecessors) {
				leftMost = next;
			}
		}

		if (leftMost == null) {
			OrccLogger.severeln("Unable to find left most in leftMostInstance()");
		}

		return leftMost;
	}

	/**
	 * 
	 * @param allInstances
	 *            all instance in the network
	 * @param selectedInstances
	 *            graphically selected instances
	 * @return the instance last in the stream
	 */
	private static Instance rightMostInstance(List<Instance> allInstances, List<Instance> selectedInstances) {
		Instance rightMost = null;
		for (Instance next : selectedInstances) {
			List<Instance> sucessors = allSuccessorsOfOneInstance(next);
			boolean anyInstancesInSuccessors = false;
			for (Instance act : selectedInstances) {
				if (sucessors.contains(act)) {
					anyInstancesInSuccessors = true;
				}
			}
			if (!anyInstancesInSuccessors) {
				rightMost = next;
			}
		}

		if (rightMost == null) {
			OrccLogger.severeln("Unable to find right most in rightMostInstance()");
		}

		return rightMost;
	}

	/**
	 * 
	 * @param thisInstance
	 * @return the list of instances connected as predecessors to the instance
	 */
	private static List<Instance> allPredecesorsOfOneInstance(Instance thisInstance) {
		List<Instance> allPredecessors = new ArrayList<Instance>();
		for (Edge e : thisInstance.getIncoming()) {
			Vertex v = e.getSource();
			if (v instanceof Instance) {
				Instance inst = (Instance) v;
				allPredecessors.add(inst);
				allPredecessors.addAll(allPredecesorsOfOneInstance(inst));
			}
		}
		return allPredecessors;
	}

	/**
	 * 
	 * @param thisInstance
	 * @return the list of instances connected as successors to the instance
	 */
	private static List<Instance> allSuccessorsOfOneInstance(Instance thisInstance) {
		List<Instance> allSuccessors = new ArrayList<Instance>();
		for (Edge e : thisInstance.getOutgoing()) {
			Vertex v = e.getTarget();
			if (v instanceof Instance) {
				Instance inst = (Instance) v;
				allSuccessors.add(inst);
				allSuccessors.addAll(allSuccessorsOfOneInstance(inst));
			}
		}
		return allSuccessors;
	}

	/**
	 * 
	 * @param parallelismDegree
	 *            how many data parallel actors
	 * @param tokenType
	 *            the type of the tokens to be fanned out
	 * @param projectRoot
	 *            needed to create filename for the new actor
	 * @return the fan out actor
	 */
	private Actor createFanOutActor(String name, int parallelismDegree, Type tokenType, String projectRoot) {
		/* Create a new actor */
		Actor fanOutActor = dfFactory.createActor();
		fanOutActor.setName("gen." + name);

		/* create one fan out input port */
		Port inputPort = dfFactory.createPort(tokenType, "In1");
		fanOutActor.getInputs().add(inputPort);

		/* create fan out output ports */
		Port outputPort = null;
		for (int n = 1; n <= parallelismDegree; n++) {
			outputPort = dfFactory.createPort(tokenType, "Out" + n);
			fanOutActor.getOutputs().add(outputPort);
		}

		Procedure proc = irFactory.createProcedure("fan_out", 0, irFactory.createTypeVoid());
		BlockBasic blockFanOut = IrUtil.getLast(proc.getBlocks());

		/* create input Pattern */
		Pattern inputPatternNew_Actor = dfFactory.createPattern();
		Var inputokenVar = irFactory.createVar(tokenType, "In1", false, 0);
		for (int i = 0; i < parallelismDegree; i++) {
			Port p = fanOutActor.getInputs().get(0);
			inputPatternNew_Actor.getPorts().add(p);
			inputPatternNew_Actor.setNumTokens(p, parallelismDegree);
			Var v = irFactory.createVar(tokenType, "v" + i, false, 0);
			InstLoad instLoad = irFactory.createInstLoad(v, inputokenVar, i);
			blockFanOut.add(instLoad);
		}

		/* create output Pattern */
		Pattern outputPatternNew_Actor = dfFactory.createPattern();
		int i = 0;
		for (Port p : fanOutActor.getOutputs()) {
			outputPatternNew_Actor.getPorts().add(p);
			outputPatternNew_Actor.setNumTokens(p, 1);
			Var outputTokenVar = irFactory.createVar(tokenType, "Out" + (i + 1), false, 0);
			Var v = irFactory.createVar(tokenType, "v" + i, false, 0);
			InstStore instStore = irFactory.createInstStore(outputTokenVar, 0, v);
			blockFanOut.add(instStore);
			i++;
		}

		/* add a scheduler */
		Procedure schedulerFanInOutActors = irFactory.createProcedure("isSchedulable_fan_out", 0,
				irFactory.createTypeBool());

		/* add an action */
		Action actionFanOutActor = dfFactory.createAction("fan_out", inputPatternNew_Actor, outputPatternNew_Actor,
				dfFactory.createPattern(), schedulerFanInOutActors, proc);

		fanOutActor.getActions().add(actionFanOutActor);

		/* writes fan in actor to src/gen/FanIn.cal */
		String actorFilename = projectRoot + "/src/gen/" + DfUtil.getSimpleName(fanOutActor.getName()) + ".cal";
		fanOutActor.setFileName(actorFilename);
		actorsToWriteToFile.add(fanOutActor);
		network.add(fanOutActor);
		return fanOutActor;
	}

	/**
	 * 
	 * @param parallelismDegree
	 *            how many data parallel actors
	 * @param tokenType
	 *            the type of the tokens to be fanned in
	 * @param projectRoot
	 *            needed to create filename for the new actor
	 * @return the fan in actor
	 */
	private Actor createFanInActor(String name, int parallelismDegree, Type tokenType, String projectRoot) {

		/* create a new actor */
		Actor fanInActor = dfFactory.createActor();
		fanInActor.setName("gen." + name);

		/* create fan in inport ports */
		Port inputPort = null;
		for (int n = 1; n <= parallelismDegree; n++) {
			inputPort = dfFactory.createPort(tokenType, "In" + n);
			fanInActor.getInputs().add(inputPort);
		}

		/* create one fan in output port */
		Port outputPort = dfFactory.createPort(tokenType, "Out1");
		fanInActor.getOutputs().add(outputPort);

		Procedure bodyNew_actor = irFactory.createProcedure("fan_in", 0, irFactory.createTypeVoid());
		BlockBasic blockFanIn = IrUtil.getLast(bodyNew_actor.getBlocks());

		/* create input pattern */
		Pattern inPatt = dfFactory.createPattern();
		int i = 0;
		for (Port p : fanInActor.getInputs()) {
			inPatt.getPorts().add(p);
			inPatt.setNumTokens(p, 1);
			Var outputTokenVar = irFactory.createVar(tokenType, "In" + (i + 1), false, 0);
			Var v = irFactory.createVar(tokenType, "v" + i, false, 0);
			InstLoad instStore = irFactory.createInstLoad(v, outputTokenVar, 0);
			blockFanIn.add(instStore);
			i++;
		}

		/* create output pattern */
		Pattern outPatt = dfFactory.createPattern();
		Var outputTokenVar = irFactory.createVar(tokenType, "Out1", false, 0);
		for (int j = 0; j < parallelismDegree; j++) {
			Port p = fanInActor.getInputs().get(0);
			outPatt.getPorts().add(p);
			outPatt.setNumTokens(p, parallelismDegree);
			Var v = irFactory.createVar(tokenType, "v" + j, false, 0);
			InstStore instStore = irFactory.createInstStore(outputTokenVar, j, v);
			blockFanIn.add(instStore);
		}

		/* add a scheduler */
		Procedure schedulerFanInOutActors = irFactory.createProcedure("isSchedulable_myActionInNewFanInOutActor", 0,
				irFactory.createTypeBool());

		/* add an action */
		Action actionFanIn = dfFactory.createAction("fan_in", inPatt, outPatt, dfFactory.createPattern(),
				schedulerFanInOutActors, bodyNew_actor);
		fanInActor.getActions().add(actionFanIn);

		/* writes fan in actor to src/gen/FanIn.cal */
		String actorFilename = projectRoot + "/src/gen/" + DfUtil.getSimpleName(fanInActor.getName()) + ".cal";
		fanInActor.setFileName(actorFilename);
		actorsToWriteToFile.add(fanInActor);
		network.add(fanInActor);
		return fanInActor;
	}
}
