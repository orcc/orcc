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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.promela.transform.ControlTokenActorModel;
import net.sf.orcc.backends.promela.transform.GuardsExtractor;
import net.sf.orcc.backends.promela.transform.IdentifyStatelessActors;
import net.sf.orcc.backends.promela.transform.PromelaAddPrefixToStateVar;
import net.sf.orcc.backends.promela.transform.PromelaDeadGlobalElimination;
import net.sf.orcc.backends.promela.transform.PromelaSchedulabilityTest;
import net.sf.orcc.backends.promela.transform.PromelaSchedulingModel;
import net.sf.orcc.backends.promela.transform.ScheduleBalanceEq;
import net.sf.orcc.backends.promela.transform.Scheduler;
import net.sf.orcc.backends.transform.Inliner;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.df.util.NetworkValidator;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.transform.DeadCodeElimination;
import net.sf.orcc.ir.transform.DeadVariableRemoval;
import net.sf.orcc.ir.transform.PhiRemoval;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.Result;
import net.sf.orcc.util.Void;

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
	private Map<EObject, List<Action>> priority = new HashMap<EObject, List<Action>>();
	private PromelaSchedulingModel schedulingModel;
	private ScheduleBalanceEq balanceEq;

	private Set<Scheduler> actorSchedulers;

	private final NetworkPrinter networkPrinter;
	private final SchedulePrinter schedulerPrinter;
	private final ScriptPrinter scriptPrinter;

	private final InstancePrinter instancePrinter;

	public PromelaBackend() {
		actorSchedulers = new HashSet<Scheduler>();

		networkPrinter = new NetworkPrinter();
		schedulerPrinter = new SchedulePrinter(actorSchedulers);
		scriptPrinter = new ScriptPrinter();

		instancePrinter = new InstancePrinter();
	}

	@Override
	protected void doInitializeOptions() {
		actorSchedulers.clear();
		guards.clear();
		priority.clear();
		loadPeeks.clear();

		getOptions().put("guards", guards);
		getOptions().put("priority", priority);
		getOptions().put("loadPeeks", loadPeeks);
		networkPrinter.setOptions(getOptions());
		schedulerPrinter.setOptions(getOptions());
		scriptPrinter.setOptions(getOptions());
		instancePrinter.setOptions(getOptions());

		final Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("abs", "abs_prml");
		renameMap.put("getw", "getw_prml");
		renameMap.put("index", "index_prml");
		renameMap.put("max", "max_prml");
		renameMap.put("min", "min_prml");
		renameMap.put("select", "select_prml");
		renameMap.put("len", "len_prml");

		networkTransfos.add(new BroadcastAdder());
		networkTransfos.add(new Instantiator(true));
		networkTransfos.add(new NetworkFlattener());
		networkTransfos.add(new Classifier(true));
		networkTransfos.add(new UnitImporter());
		networkTransfos.add(new DfVisitor<Void>(new Inliner(true, true)));
		networkTransfos.add(new RenameTransformation(renameMap));
		networkTransfos.add(new DfVisitor<Void>(new PhiRemoval()));
		networkTransfos.add(new PromelaAddPrefixToStateVar());
		networkTransfos.add(new GuardsExtractor(guards, priority, loadPeeks));
	}

	@Override
	protected Result doLibrariesExtraction() {
		return FilesManager.extract("/runtime/Promela/pylibs", outputPath);
	}

	@Override
	protected void doValidate(Network network) {
		Validator.checkTopLevel(network);
		// We don't have to print Warnings for 'potential deadlocks'
		// Validator.checkMinimalFifoSize(network, fifoSize);

		new NetworkValidator().doSwitch(network);	}

	@Override
	protected void beforeGeneration(Network network) {
		schedulingModel = new PromelaSchedulingModel(network);
		schedulingModel.printDependencyGraph();

		for (Actor actor : network.getAllActors()) {
			final ControlTokenActorModel actorModel = schedulingModel
					.getActorModel(actor);

			final List<DfVisitor<?>> additionalTransfos = new ArrayList<DfVisitor<?>>();
			additionalTransfos.add(new IdentifyStatelessActors(actorModel));
			additionalTransfos.add(new PromelaDeadGlobalElimination(actorModel
					.getAllReacableSchedulingVars(), actorModel
					.getPortsUsedInScheduling()));
			additionalTransfos.add(new DfVisitor<Void>(new DeadCodeElimination()));
			additionalTransfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));

			applyTransformations(actor, additionalTransfos, debug);
			
			final PromelaSchedulabilityTest actorScheduler = new PromelaSchedulabilityTest(
					actorModel);
			actorScheduler.doSwitch(actor);
			actorSchedulers.add(actorScheduler.getScheduler());
		}

		balanceEq = new ScheduleBalanceEq(actorSchedulers, network);
		network.computeTemplateMaps();
	}

	@Override
	protected Result doGenerateNetwork(Network network) {
		networkPrinter.setNetwork(network);
		return FilesManager.writeFile(networkPrinter.getNetworkFileContent(),
				outputPath, "main_" + network.getSimpleName() + ".pml");
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		// Could be better to have a printer instance for all runs
		final ScheduleInfoPrinter scheduleInfoPrinter = new ScheduleInfoPrinter(
				balanceEq);

		schedulerPrinter.setNetwork(network);
		scheduleInfoPrinter.setNetwork(network);
		scheduleInfoPrinter.setOptions(getOptions());
		scriptPrinter.setNetwork(network);

		final Result result = Result.newInstance();
		result.merge(FilesManager.writeFile(
				schedulerPrinter.getSchedulerFileContent(), outputPath, "schedule_"
						+ network.getSimpleName() + ".xml"));
		result.merge(FilesManager.writeFile(
				scheduleInfoPrinter.getSchedulerFileContent(), outputPath,
				"schedule_info_" + network.getSimpleName() + ".xml"));
		result.merge(FilesManager.writeFile(scriptPrinter.getScriptFileContent(), outputPath,
				"run_checker_" + network.getSimpleName() + ".py"));
		return result;
	}

	@Override
	protected Result doGenerateActor(Actor actor) {
		instancePrinter.setActor(actor);
		instancePrinter.setSchedulingModel(schedulingModel);

		return FilesManager.writeFile(instancePrinter.getInstanceFileContent(), outputPath, actor.getName() + ".pml");
	}
}
