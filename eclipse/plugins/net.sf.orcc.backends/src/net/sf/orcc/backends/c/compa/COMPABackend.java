/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c.compa;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.backends.c.transform.CBroadcastAdder;
import net.sf.orcc.backends.transform.DisconnectedOutputPortRemoval;
import net.sf.orcc.backends.util.Alignable;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.ArgumentEvaluator;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.XdfWriter;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.Result;

/**
 * C backend targeting Xilinx Zynq platforms
 * 
 * @author Antoine Lorence
 * @author Yaset Oliva
 */
public class COMPABackend extends CBackend {

	final boolean printTop = false;

	private NetworkPrinter netPrinter;
	private InstancePrinter childrenPrinter;
	private CMakePrinter cmakePrinter;

	private Mapping computedMapping;

	public COMPABackend() {
		netPrinter = new NetworkPrinter();
		childrenPrinter = new InstancePrinter();
		cmakePrinter = new CMakePrinter();
	}

	@Override
	protected void doInitializeOptions() {
		// Configure the options used in code generation
		netPrinter.setOptions(getOptions());
		childrenPrinter.setOptions(getOptions(), printTop);

		// Create the directory tree
		new File(path, "src").mkdir();
		srcPath = new File(path, "src").toString();

		// Configure the map used in RenameTransformation
		final Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("abs", "abs_replaced");
		renameMap.put("getw", "getw_replaced");
		renameMap.put("exit", "exit_replaced");
		renameMap.put("index", "index_replaced");
		renameMap.put("log2", "log2_replaced");
		renameMap.put("max", "max_replaced");
		renameMap.put("min", "min_replaced");
		renameMap.put("select", "select_replaced");
		renameMap.put("OUT", "OUT_REPLACED");
		renameMap.put("IN", "IN_REPLACED");
		renameMap.put("SIZE", "SIZE_REPLACED");

		// -----------------------------------------------------
		// Transformations that will be applied on the Network
		// -----------------------------------------------------
		networkTransfos.add(new Instantiator(true));
		networkTransfos.add(new NetworkFlattener());
		networkTransfos.add(new UnitImporter());
		networkTransfos.add(new UnitImporter());

		networkTransfos.add(new CBroadcastAdder());
		networkTransfos.add(new ArgumentEvaluator());
		// networkTransfos.add(new XdfExtender());

		// -----------------------------------------------------
		// Transformations that will be applied on Actors
		// -----------------------------------------------------
		childrenTransfos.add(new TypeResizer(true, false, true, false));
		childrenTransfos.add(new RenameTransformation(renameMap));
		childrenTransfos.add(new DisconnectedOutputPortRemoval());
	}

	@Override
	protected Result doLibrariesExtraction() {
		OrccLogger.trace("Export libraries sources");
		Result result = FilesManager.extract("/runtime/COMPA/libs", path);
		result.merge(FilesManager.extract("/runtime/COMPA/cmake", path));

		return result;
	}

	@Override
	protected void doValidate(Network network) {
		Validator.checkTopLevel(network);
		Validator.checkMinimalFifoSize(network, fifoSize);

		network.computeTemplateMaps();

		// Compute the actor mapping
		computedMapping = new Mapping(network, mapping);

		// update "vectorizable" information
		Alignable.setAlignability(network);
	}

	@Override
	protected Result doGenerateNetwork(Network network) {
		final Result result = Result.newInstance();

		// Configure the network
		netPrinter.setNetwork(network);
		netPrinter.setOptions(getOptions());

		result.merge(FilesManager.writeFile(netPrinter.getFifoContent(), path
				+ "/libs/orcc/include", "fifoAllocations.h"));
		if (printTop) {
			result.merge(FilesManager.writeFile(netPrinter.getContent(),
					srcPath, network.getSimpleName() + ".c"));
		}

		return result;
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		final Result result = Result.newInstance();

		OrccLogger.traceln("Print flattened and attributed network...");
		new XdfWriter().write(new File(srcPath), network);

		OrccLogger.traceln("Print network meta-informations...");
		result.merge(FilesManager.writeFile(computedMapping.getContentFile(),
				srcPath, network.getSimpleName() + ".xcf"));

		return result;
	}

	@Override
	protected Result doGenerateInstance(Instance instance) {
		final Result result = Result.newInstance();
		childrenPrinter.setInstance(instance);

		if (printTop) {
			result.merge(FilesManager.writeFile(childrenPrinter.getContent(),
					srcPath, instance.getName() + ".c"));
			result.merge(FilesManager.writeFile(
					childrenPrinter.getTestContent(), srcPath,
					instance.getName() + "_test.h"));
		} else {
			String childPath = OrccUtil.createFolder(path,
					instance.getSimpleName());
			result.merge(FilesManager.writeFile(childrenPrinter.getContent(),
					childPath, instance.getName() + ".c"));
			result.merge(FilesManager.writeFile(
					childrenPrinter.getTestContent(), childPath,
					instance.getName() + "_test.h"));
			result.merge(FilesManager.writeFile(
					cmakePrinter.rootCMakeContent(instance.getSimpleName()),
					childPath, "CMakeLists.txt"));
		}

		return result;
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		// FIXME: Override until the C back-end is migrated
	}

	@Override
	protected Result doGenerateActor(Actor actor) {
		final Result result = Result.newInstance();
		childrenPrinter.setActor(actor);

		if (printTop) {
			result.merge(FilesManager.writeFile(childrenPrinter.getContent(),
					srcPath, actor.getName() + ".c"));
			result.merge(FilesManager.writeFile(
					childrenPrinter.getTestContent(), srcPath, actor.getName()
							+ "_test.h"));
		} else {
			String childPath = OrccUtil.createFolder(path,
					actor.getSimpleName());
			result.merge(FilesManager.writeFile(childrenPrinter.getContent(),
					childPath, actor.getName() + ".c"));
			result.merge(FilesManager.writeFile(
					childrenPrinter.getTestContent(), childPath,
					actor.getName() + "_test.h"));
			result.merge(FilesManager.writeFile(
					cmakePrinter.rootCMakeContent(actor.getSimpleName()),
					childPath, "CMakeLists.txt"));
		}

		return result;
	}
}
