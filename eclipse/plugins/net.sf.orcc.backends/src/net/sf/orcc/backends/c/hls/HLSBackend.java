/*
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.c.hls;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.backends.transform.CastArgFuncCall;
import net.sf.orcc.backends.transform.DisconnectedOutputPortRemoval;
import net.sf.orcc.backends.transform.Multi2MonoToken;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transform.ArgumentEvaluator;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.df.util.NetworkValidator;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.action.ActionMerger;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccAttributes;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Result;

/**
 * C backend targeting high-level synthesis tools
 * 
 * @author Mariem Abid
 * @author Khaled Jerbi
 * @author Herve Yviquel
 */
public class HLSBackend extends CBackend {

	private String commandPath;
	private String vhdlPath;

	private final NetworkPrinter networkPrinter;
	private final NetworkTestBenchPrinter netTestBenchPrinter;
	private final TopVhdlPrinter topVHDLPrinter;
	private final BatchCommandPrinter batchCommandPrinter;
	private final BatchCommandPrinterLinux batchCommandPrinterLinux;

	private final InstancePrinter instancePrinter;
	private final InstanceCosimPrinter instanceCosimPrinter;
	private final InstancePrinterCast instancePrinterCast;
	private final ActorTopVhdlPrinter actorTopVHDLPrinter;
	private final ActorNetworkTestBenchPrinter actorNetTestBenchPrinter;
	private final UnitaryBatchCommandPrinter unitaryBatchCommandPrinter;
	private final UnitaryBatchCommandPrinterLinux unitaryBatchCommandPrinterLinux;

	public HLSBackend() {
		networkPrinter = new NetworkPrinter();

		netTestBenchPrinter = new NetworkTestBenchPrinter();
		topVHDLPrinter = new TopVhdlPrinter();
		batchCommandPrinter = new BatchCommandPrinter();
		batchCommandPrinterLinux = new BatchCommandPrinterLinux();

		instancePrinter = new InstancePrinter();
		instanceCosimPrinter = new InstanceCosimPrinter();
		instancePrinterCast = new InstancePrinterCast();
		actorTopVHDLPrinter = new ActorTopVhdlPrinter();
		actorNetTestBenchPrinter = new ActorNetworkTestBenchPrinter();
		unitaryBatchCommandPrinter = new UnitaryBatchCommandPrinter();
		unitaryBatchCommandPrinterLinux = new UnitaryBatchCommandPrinterLinux();
	}

	@Override
	protected Result doLibrariesExtraction() {
		// Never extract libraries (Note: we can also force attribute
		// NO_LIBRARY_EXPORT to true)
		return Result.newInstance();
	}

	@Override
	protected void doInitializeOptions() {

		// Configure paths were files will be generated
		srcPath = outputPath + File.separator + "HLSBackend";
		commandPath = srcPath + File.separator + "batchCommand";
		vhdlPath = srcPath + File.separator + "TopVHDL";

		// Load options map into various code generator instances
		networkPrinter.setOptions(getOptions());
		netTestBenchPrinter.setOptions(getOptions());
		topVHDLPrinter.setOptions(getOptions());
		batchCommandPrinter.setOptions(getOptions());
		batchCommandPrinterLinux.setOptions(getOptions());

		instancePrinter.setOptions(getOptions());
		instanceCosimPrinter.setOptions(getOptions());
		instancePrinterCast.setOptions(getOptions());
		actorTopVHDLPrinter.setOptions(getOptions());
		actorNetTestBenchPrinter.setOptions(getOptions());
		unitaryBatchCommandPrinter.setOptions(getOptions());
		unitaryBatchCommandPrinterLinux.setOptions(getOptions());


		// Configure the map used in RenameTransformation
		final Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("abs", "abs_my_precious");
		renameMap.put("getw", "getw_my_precious");
		renameMap.put("index", "index_my_precious");
		renameMap.put("max", "max_my_precious");
		renameMap.put("min", "min_my_precious");
		renameMap.put("select", "select_my_precious");
		renameMap.put("OUT", "OUT_my_precious");
		renameMap.put("IN", "IN_my_precious");
		renameMap.put("bitand", "bitand_my_precious");

		// -----------------------------------------------------
		// Transformations that will be applied on the Network
		// -----------------------------------------------------
		networkTransfos.add(new Instantiator(false));
		networkTransfos.add(new NetworkFlattener());
		if (classify) {
			OrccLogger.traceln("Classification of actors...");
			networkTransfos.add(new Classifier());
		}
		if (mergeActors) {
			OrccLogger.traceln("Merging of actors...");
			networkTransfos.add(new ActionMerger());
		}
		networkTransfos.add(new BroadcastAdder());
		networkTransfos.add(new ArgumentEvaluator());
		networkTransfos.add(new DisconnectedOutputPortRemoval());

		// -----------------------------------------------------
		// Transformations that will be applied on Instances
		// -----------------------------------------------------
		if (mergeActions) {
			childrenTransfos.add(new ActionMerger());
		}
		if (convertMulti2Mono) {
			childrenTransfos.add(new Multi2MonoToken());
		}
		childrenTransfos.add(new UnitImporter());
		childrenTransfos.add(new TypeResizer(true, true, true, false));
		childrenTransfos.add(new RenameTransformation(renameMap));
		childrenTransfos.add(new DfVisitor<Void>(new CastArgFuncCall()));
	}

	@Override
	protected void doValidate(Network network) {
		Validator.checkMinimalFifoSize(network, fifoSize);

		new NetworkValidator().doSwitch(network);
	}

	@Override
	protected void beforeGeneration(Network network) {
		network.computeTemplateMaps();
	}

	@Override
	protected Result doGenerateNetwork(Network network) {
		final Result result = Result.newInstance();
		result.merge(FilesManager.writeFile(networkPrinter.fifoSimPackContent(), vhdlPath, "sim_package.vhd"));
		result.merge(FilesManager.writeFile(networkPrinter.fifoFileContent(), vhdlPath, "ram_tab.vhd"));
		return result;
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		final Result result = Result.newInstance();

		netTestBenchPrinter.setNetwork(network);
		topVHDLPrinter.setNetwork(network);
		batchCommandPrinter.setNetwork(network);
		batchCommandPrinterLinux.setNetwork(network);

		result.merge(FilesManager.writeFile(netTestBenchPrinter.getNetworkFileContent(), vhdlPath, network.getName() + "_TopTestBench.vhd"));
		result.merge(FilesManager.writeFile(topVHDLPrinter.getNetworkFileContent(), vhdlPath, network.getName() + "Top.vhd"));
		result.merge(FilesManager.writeFile(batchCommandPrinter.getNetworkFileContent(), commandPath, "Command.bat"));
		result.merge(FilesManager.writeFile(batchCommandPrinterLinux.getNetworkFileContent(), commandPath, "command-linux.sh"));

		return result;
	}

	@Override
	protected Result doGenerateInstance(Instance instance) {

		instancePrinter.setInstance(instance);

		final Result result = Result.newInstance();
		result.merge(FilesManager.writeFile(instancePrinter.getFileContent(),
				srcPath, instance.getName() + ".cpp"));
		result.merge(FilesManager.writeFile(instancePrinter.script(srcPath),
				srcPath, "script_" + instance.getName() + ".tcl"));
		result.merge(FilesManager.writeFile(instancePrinter.directive(srcPath),
				srcPath, "directive_" + instance.getName() + ".tcl"));
		return result;
	}

	@Override
	protected Result doAdditionalGeneration(Instance instance) {

		instanceCosimPrinter.setInstance(instance);
		instancePrinterCast.setInstance(instance);
		actorTopVHDLPrinter.setInstance(instance);
		actorNetTestBenchPrinter.setInstance(instance);
		unitaryBatchCommandPrinter.setInstance(instance);
		unitaryBatchCommandPrinterLinux.setInstance(instance);

		final Result result = Result.newInstance();

		final String instanceName = instance.getName();

		result.merge(FilesManager.writeFile(
				instanceCosimPrinter.getFileContent(), srcPath, instanceName
						+ "TestBench.cpp"));

		{
			final Actor actor = instance.getActor();

			if (actor.hasAttribute(OrccAttributes.DIRECTIVE_DEBUG)) {
				for (Action action : actor.getActions()) {
					// The base used to form folder and files names
					final String base = "cast_" + instanceName + "_tab_"
							+ action.getName() + "_read";

					result.merge(FilesManager.writeFile(instancePrinterCast
							.getFileContentReadDebug(action.getName()),
							srcPath, base + ".cpp"));
					result.merge(FilesManager.writeFile(
							instancePrinterCast.script(srcPath, base), srcPath,
							"script_" + base + ".tcl"));
				}
			}
			for (final Port portIn : actor.getInputs()) {
				final Connection connIn = instance.getIncomingPortMap().get(portIn);
				if (connIn != null) {
					// The base used to form folder and files names
					final String base = "cast_" + instanceName + "_"
							+ connIn.getTargetPort().getName() + "_write";

					result.merge(FilesManager.writeFile(
							instancePrinterCast.getFileContentWrite(connIn),
							srcPath, base + ".cpp"));

					result.merge(FilesManager.writeFile(
							instancePrinterCast.script(srcPath, base), srcPath,
							"script_" + base + ".tcl"));
				}
			}
			for (final Port portOut : actor.getOutputs()) {
				final Connection connOut = instance.getOutgoingPortMap()
						.get(portOut).get(0);
				if (connOut != null) {
					// The base used to form folder and files names
					final String base = "cast_" + instanceName + "_"
							+ connOut.getSourcePort().getName() + "_read";

					result.merge(FilesManager.writeFile(
							instancePrinterCast.getFileContentRead(connOut),
							srcPath, base + ".cpp"));

					result.merge(FilesManager.writeFile(
							instancePrinterCast.script(srcPath, base), srcPath,
							"script_" + base + ".tcl"));
				}
			}
		}

		result.merge(FilesManager.writeFile(
				actorTopVHDLPrinter.ActorTopFileContent(), srcPath
						+ File.separator + instanceName + "TopVHDL",
				instanceName + "Top.vhd"));
		result.merge(FilesManager.writeFile(
				actorNetTestBenchPrinter.actorNetworkFileContent(), srcPath
						+ File.separator + instanceName + "TopVHDL",
				instanceName + "_TopTestBench.vhd"));

		result.merge(FilesManager.writeFile(
				unitaryBatchCommandPrinter.getFileContentBatch(), commandPath,
				"Command_" + instanceName + ".bat"));
		result.merge(FilesManager.writeFile(
				unitaryBatchCommandPrinterLinux.getFileContentBatch(),
				commandPath, "command-linux_" + instanceName + ".sh"));

		return result;
	}
}
