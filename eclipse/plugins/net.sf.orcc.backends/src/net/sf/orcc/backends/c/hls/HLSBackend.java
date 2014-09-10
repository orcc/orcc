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
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
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

	private NetworkPrinter networkPrinter;

	public HLSBackend() {
		networkPrinter = new NetworkPrinter();
	}

	@Override
	protected Result doLibrariesExtraction() {
		// Never extract libraries (Note: we can also force attribute
		// NO_LIBRARY_EXPORT to true)
		return Result.newInstance();
	}

	@Override
	protected void doInitializeOptions() {

		// Inherits from C backend options and configuration
		super.doInitializeOptions();

		// Configure paths were files will be generated
		srcPath = path + File.separator + "HLSBackend";
		commandPath = srcPath + File.separator + "batchCommand";
		vhdlPath = srcPath + File.separator + "TopVHDL";

		// Load options map into various code generator instances
		networkPrinter.setOptions(getOptions());

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
		OrccLogger.trace("Printing network testbench... ");
		if (new NetworkTestBenchPrinter(network, getOptions()).print(srcPath) > 0) {// VHDLTestBenchPath
			OrccLogger.traceRaw("Cached\n");
		} else {
			OrccLogger.traceRaw("Done\n");
		}

		OrccLogger.trace("Printing network VHDL Top... ");
		if (new TopVhdlPrinter(network, getOptions()).print(srcPath) > 0) {
			OrccLogger.traceRaw("Cached\n");
		} else {
			OrccLogger.traceRaw("Done\n");
		}

		OrccLogger.trace("Printing Windows batch command... ");
		if (new BatchCommandPrinter(network, getOptions()).print(commandPath) > 0) {
			OrccLogger.traceRaw("Cached\n");
		} else {
			OrccLogger.traceRaw("Done\n");
		}
		
		OrccLogger.trace("Printing Linux batch command... ");
		if (new BatchCommandPrinterLinux(network, getOptions()).print(commandPath) > 0) {
			OrccLogger.traceRaw("Cached\n");
		} else {
			OrccLogger.traceRaw("Done\n");
		}
		return result;
	}

	@Override
	protected Result doGenerateInstance(Instance instance) {
		final Result result = Result.newInstance();
		new InstancePrinter(getOptions()).print(srcPath, instance);
		return result;
	}
	
	@Override
	protected Result doAdditionalGeneration(Instance instance) {
		final Result result = Result.newInstance();
		new InstanceCosimPrinter(getOptions()).print(srcPath, instance);
		new InstancePrinterCast(getOptions()).print(srcPath, instance);
		new ActorTopVhdlPrinter(getOptions()).print(srcPath, instance);
		new ActorNetworkTestBenchPrinter(getOptions()).print(srcPath, instance);
		new UnitaryBatchCommandPrinter(getOptions()).print(commandPath, instance);
		new UnitaryBatchCommandPrinterLinux(getOptions()).print(commandPath, instance);
		return result;
	}

	//FIXME: delete these useless methods
	@Override
	protected boolean printInstance(Instance instance) {
		return false;
	}
	@Override
	protected void doXdfCodeGeneration(Network network) {
	}
}
