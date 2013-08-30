/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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

package net.sf.orcc.backends.promela;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.promela.transform.GuardsExtractor;
import net.sf.orcc.backends.promela.transform.NetworkStateDefExtractor;
import net.sf.orcc.backends.promela.transform.PromelaAddPrefixToStateVar;
import net.sf.orcc.backends.promela.transform.PromelaDeadGlobalElimination;
import net.sf.orcc.backends.promela.transform.PromelaSchedulabilityTest;
import net.sf.orcc.backends.promela.transform.PromelaSchedulingModel;
import net.sf.orcc.backends.promela.transform.PromelaTokenAnalyzer;
import net.sf.orcc.backends.promela.transform.ScheduleBalanceEq;
import net.sf.orcc.backends.promela.transform.Scheduler;
import net.sf.orcc.backends.transform.Inliner;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.transform.DeadCodeElimination;
import net.sf.orcc.ir.transform.DeadVariableRemoval;
import net.sf.orcc.ir.transform.PhiRemoval;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a template-based PROMELA back-end.
 * 
 * @author Ghislain Roquier
 * @author Johan Ersfolk
 */
public class PromelaBackend extends AbstractBackend {

	private Map<Action, List<Expression>> guards = new HashMap<Action, List<Expression>>();
	private Map<Action, List<InstLoad>> loadPeeks = new HashMap<Action, List<InstLoad>>();
	private NetworkStateDefExtractor netStateDef;
	private Map<EObject, List<Action>> priority = new HashMap<EObject, List<Action>>();
	private PromelaSchedulingModel schedulingModel;
	private ScheduleBalanceEq balanceEq;

	private final Map<String, String> renameMap;

	private Set<Scheduler> actorSchedulers;

	/**
	 * Creates a new instance of the Promela back-end. Initializes the
	 * transformation hash map.
	 */
	public PromelaBackend() {
		renameMap = new HashMap<String, String>();
		renameMap.put("abs", "abs_prml");
		renameMap.put("getw", "getw_prml");
		renameMap.put("index", "index_prml");
		renameMap.put("max", "max_prml");
		renameMap.put("min", "min_prml");
		renameMap.put("select", "select_prml");
		renameMap.put("len", "len_prml");
	}

	@Override
	protected void doInitializeOptions() {
	}

	@Override
	protected void doTransformActor(Actor actor) {

		List<DfSwitch<?>> transfos = new ArrayList<DfSwitch<?>>();

		transfos.add(new UnitImporter());
		transfos.add(new DfVisitor<Void>(new Inliner(true, true)));
		transfos.add(new RenameTransformation(renameMap));
		transfos.add(new DfVisitor<Object>(new PhiRemoval()));
		transfos.add(new PromelaAddPrefixToStateVar());
		transfos.add(new GuardsExtractor(guards, priority, loadPeeks));
		for (DfSwitch<?> transformation : transfos) {
			transformation.doSwitch(actor);
		}
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		// do not generate a PROMELA VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		new BroadcastAdder().doSwitch(network);
		// Instantiate and flattens network
		new Instantiator(true).doSwitch(network);
		new NetworkFlattener().doSwitch(network);

		// Classify network
		new Classifier().doSwitch(network);

		options.put("guards", guards);
		options.put("priority", priority);
		options.put("loadPeeks", loadPeeks);

		transformActors(network.getAllActors());

		schedulingModel = new PromelaSchedulingModel(network);

		netStateDef = new NetworkStateDefExtractor(schedulingModel);
		netStateDef.doSwitch(network);

		schedulingModel.printDependencyGraph();
		actorSchedulers = new HashSet<Scheduler>();

		transformActorsAgain(network.getAllActors());

		printChildren(network);

		balanceEq = new ScheduleBalanceEq(actorSchedulers, network);

		network.computeTemplateMaps();
		printNetwork(network);
	}

	@Override
	protected boolean printActor(Actor instance) {
		return new InstancePrinter(instance, options).printInstance(path) > 0;
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	private void printNetwork(Network network) {
		new NetworkPrinter(network, options).print(path);
		new SchedulePrinter(network, actorSchedulers).print(path);
		new ScheduleInfoPrinter(network, balanceEq).print(path);
		new ScriptPrinter(network).print(path);
	}

	@Override
	protected boolean exportRuntimeLibrary() {
		String libsPath = path + File.separator + "pylibs";
		OrccLogger.trace("Export libraries sources into " + libsPath + "... ");
		if (copyFolderToFileSystem("/runtime/Promela/pylibs", libsPath, debug)) {
			OrccLogger.traceRaw("OK" + "\n");
			return true;
		} else {
			OrccLogger.warnRaw("Error" + "\n");
			return false;
		}
	}

	private void transformActorAgain(Actor actor) {
		List<DfSwitch<?>> transfos = new ArrayList<DfSwitch<?>>();
		transfos.add(new PromelaDeadGlobalElimination(netStateDef
				.getVarsUsedInScheduling(), netStateDef
				.getPortsUsedInScheduling()));
		transfos.add(new DfVisitor<Void>(new DeadCodeElimination()));
		transfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));

		for (DfSwitch<?> transformation : transfos) {
			transformation.doSwitch(actor);
		}
		new PromelaTokenAnalyzer(netStateDef).doSwitch(actor);
		PromelaSchedulabilityTest actorScheduler = new PromelaSchedulabilityTest(
				netStateDef);
		actorScheduler.doSwitch(actor);
		actorSchedulers.add(actorScheduler.getScheduler());
	}

	private void transformActorsAgain(List<Actor> actors) {
		OrccLogger.traceln("Transforming instances...");
		for (Actor a : actors) {
			transformActorAgain(a);
		}
	}
}
