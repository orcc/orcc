/*
 * Copyright (c) 2012, Ecole Polytechnique Fédérale de Lausanne
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
package net.sf.orcc.backends.opencl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.core.resources.IFile;

/**
 * OpenCL back-end
 * 
 * @author Endri Bezati
 * 
 */
public class OpenCLBackend extends AbstractBackend {

	private String includePath;
	private String kernelSrcPath;
	private String srcPath;

	@Override
	protected void doInitializeOptions() {

		/** Create the build and source directories **/
		File srcDir = new File(path + File.separator + "src");
		File kernelSrcDir = new File(srcDir.getPath() + File.separator
				+ "kernels");
		File includeDir = new File(path + File.separator + "include");
		File buildDir = new File(path + File.separator + "build");

		/** Create the directories if they do not exists **/
		if (!srcDir.exists()) {
			srcDir.mkdirs();
		}

		if (!includeDir.exists()) {
			includeDir.mkdirs();
		}

		if (!kernelSrcDir.exists()) {
			kernelSrcDir.mkdirs();
		}

		if (!buildDir.exists()) {
			buildDir.mkdirs();
		}

		/** Initialize the source Path **/
		srcPath = srcDir.getPath();
		kernelSrcPath = kernelSrcDir.getPath();
		includePath = includeDir.getPath();
	}

	@Override
	protected void doTransformActor(Actor actor) {
		Map<String, String> replacementMap = new HashMap<String, String>();
		replacementMap.put("abs", "abs_");
		replacementMap.put("getw", "getw_");
		replacementMap.put("index", "index_");
		replacementMap.put("max", "max_");
		replacementMap.put("min", "min_");
		replacementMap.put("select", "select_");
		replacementMap.put("OUT", "OUT_");
		replacementMap.put("IN", "IN_");
		replacementMap.put("DEBUG", "DEBUG_");
		replacementMap.put("INT_MIN", "INT_MIN_");

		List<DfSwitch<?>> transformations = new ArrayList<DfSwitch<?>>();
		transformations.add(new UnitImporter());
		transformations.add(new RenameTransformation(replacementMap));
		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
		}

	}

	/**
	 * Transforms the network
	 * 
	 * @param network
	 *            the network
	 * @return a modified network
	 */
	private Network doTransformNetwork(Network network) {
		OrccLogger.trace("Instantiating... ");
		new Instantiator(false).doSwitch(network);
		OrccLogger.traceRaw("done\n");
		new NetworkFlattener().doSwitch(network);

		return network;
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		// do not generate a OpenCL/C++ VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		network = doTransformNetwork(network);
		transformActors(network.getAllActors());

		network.computeTemplateMaps();
		printChildren(network);

		// print network
		OrccLogger.traceln("Printing network...");
		printNetwork(network);
	}

	@Override
	public boolean exportRuntimeLibrary() {
		OrccLogger.trace("Exporting Run-Time sources into " + path + "... ");
		if (copyFolderToFileSystem("/runtime/OpenCL", path, debug)) {
			OrccLogger.traceRaw("OK" + "\n");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean printInstance(Instance instance) {
		OpenCLPrinter printer = new OpenCLPrinter(!debug);
		printer.print(srcPath, kernelSrcPath, includePath, instance);
		return false;
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            a network
	 */
	public void printNetwork(Network network) {
		OpenCLPrinter printer = new OpenCLPrinter(!debug);
		printer.print(path, srcPath, network);
	}

}
