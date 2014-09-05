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

import static net.sf.orcc.OrccLaunchConstants.NO_LIBRARY_EXPORT;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.backends.c.compa.InstancePrinter;
import net.sf.orcc.backends.c.transform.CBroadcastAdder;
import net.sf.orcc.backends.transform.DisconnectedOutputPortRemoval;
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
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * C backend targeting hardware platforms
 * 
 * @author Antoine Lorence
 */
public class COMPABackend extends CBackend {
	private static final int currentMappingNb = 14;
	private static final int nbProcessors = 15;
	private final boolean printTop = false;
	private final boolean[][] currentMap = new mappings().mapping[currentMappingNb];
	
	@Override
	protected void doInitializeOptions() {
//		// Create empty folders
//		new File(path + File.separator + "build").mkdirs();
//		new File(path + File.separator + "bin").mkdirs();
//
		srcPath = path + File.separator + "src";
	}
	
	
	@Override
	protected boolean exportRuntimeLibrary() {
		boolean exportLibrary = !getAttribute(NO_LIBRARY_EXPORT, false);

		if (exportLibrary) {
			String libsPath = path + File.separator + "libs";

//			// Copy specific windows batch file
//			if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
//				copyFileToFilesystem("/runtime/C/run_cmake_with_VS_env.bat",
//						path + File.separator + "run_cmake_with_VS_env.bat",
//						debug);
//			}
//
//			copyFileToFilesystem("/runtime/C/README.txt", path + File.separator
//					+ "README.txt", debug);
			
			

			OrccLogger.trace("Export libraries sources into " + libsPath
					+ "... ");
			final boolean orccOk = copyFolderToFileSystem("/runtime/COMPA/libs",
					libsPath, debug);
			if (orccOk) {
				OrccLogger.traceRaw("OK" + "\n");
				new File(libsPath + File.separator + "orcc" + File.separator
						+ "benchAutoMapping.py").setExecutable(true);
			} else {
				OrccLogger.warnRaw("Error" + "\n");
			}

			final String commonLibPath = libsPath + File.separator + "orcc"
					+ File.separator + "common";
			OrccLogger.trace("Export common library files into "
					+ commonLibPath + "... ");
			final boolean commonOk = copyFolderToFileSystem("/runtime/common",
					commonLibPath, debug);
			if (commonOk) {
				OrccLogger.traceRaw("OK" + "\n");
			} else {
				OrccLogger.warnRaw("Error" + "\n");
			}

			final String cmakeModPath = path + File.separator + "cmake";
			OrccLogger.trace("Export cmake modules into " + cmakeModPath + "... ");
			final boolean cmakeModOk = copyFolderToFileSystem("/runtime/COMPA/cmake", cmakeModPath, debug);
			if (cmakeModOk) {
				OrccLogger.traceRaw("OK" + "\n");
			} else {
				OrccLogger.warnRaw("Error" + "\n");
			}
			
			return orccOk & commonOk & cmakeModOk;
		}
		return false;
	}
	
	@Override
	protected void doTransformActor(Actor actor) {
		Map<String, String> replacementMap = new HashMap<String, String>();
		replacementMap.put("abs", "abs_replaced");
		replacementMap.put("getw", "getw_replaced");
		replacementMap.put("exit", "exit_replaced");
		replacementMap.put("index", "index_replaced");
		replacementMap.put("log2", "log2_replaced");
		replacementMap.put("max", "max_replaced");
		replacementMap.put("min", "min_replaced");
		replacementMap.put("select", "select_replaced");
		replacementMap.put("OUT", "OUT_REPLACED");
		replacementMap.put("IN", "IN_REPLACED");
		replacementMap.put("SIZE", "SIZE_REPLACED");

		List<DfSwitch<?>> transformations = new ArrayList<DfSwitch<?>>();
		transformations.add(new TypeResizer(true, false, true, false));
		transformations.add(new RenameTransformation(replacementMap));
		transformations.add(new DisconnectedOutputPortRemoval());

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
			if (debug) {
				OrccUtil.validateObject(transformation.toString() + " on "
						+ actor.getName(), actor);
			}
		}
	}

	@Override
	protected void doTransformNetwork(Network network) {
		// instantiate and flattens network
		OrccLogger.traceln("Instantiating...");
		new Instantiator(true, fifoSize).doSwitch(network);
		OrccLogger.traceln("Flattening...");
		new NetworkFlattener().doSwitch(network);
		new UnitImporter().doSwitch(network);

		new CBroadcastAdder().doSwitch(network);
		new ArgumentEvaluator().doSwitch(network);

		//new XdfExtender().doSwitch(network);
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		// do not generate a C VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		Validator.checkTopLevel(network);
		Validator.checkMinimalFifoSize(network, fifoSize);

		doTransformNetwork(network);

		if (debug) {
			// Serialization of the actors will break proxy link
			EcoreUtil.resolveAll(network);
		}

		transformActors(network.getAllActors());
		network.computeTemplateMaps();

		// print instances
//		printChildren(network);
		printChildrenCompa(network);
		
		
		// Print fifo allocation file into the orcc lib include folder.
		OrccLogger.trace("Printing the fifo allocation file... ");
		if (new NetworkPrinter(network, options).printFifoFile(path + "/libs/orcc/include") > 0) {
			OrccLogger.traceRaw("Cached\n");
		} else {
			OrccLogger.traceRaw("Done\n");
		}
		
		// Print Top files
		new TopFilePrinter(network, currentMap, nbProcessors).print(srcPath);
		
		if (printTop){
			// print network
			OrccLogger.trace("Printing network... ");
			if (new NetworkPrinter(network, options).print(srcPath) > 0) {
				OrccLogger.traceRaw("Cached\n");
			} else {
				OrccLogger.traceRaw("Done\n");
			}			
		}
		
		new CMakePrinter(network).printCMakeFiles(path);

		OrccLogger.traceln("Print flattened and attributed network...");
		URI uri = URI.createFileURI(srcPath + File.separator
				+ network.getSimpleName() + ".xdf");
		Resource resource = new ResourceSetImpl().createResource(uri);
		resource.getContents().add(network);
		try {
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		OrccLogger.traceln("Print network meta-informations...");
		new Mapping(network, mapping).print(srcPath);
	}


	@Override
	protected boolean printInstance(Instance instance) {
//		// Copy Xilinx platform specific files into the instance source folder.
//		OrccLogger.trace("Copying Xilinx platform files... ");
//		final boolean xilFilesOk = copyFolderToFileSystem("/runtime/COMPA/xilinx",	path + File.separator + instance.getSimpleName(), debug);
//		if (xilFilesOk) {
//			OrccLogger.traceRaw("OK" + "\n");
//		} else {
//			OrccLogger.warnRaw("Error" + "\n");
//		}
//		if (printTop)
			return new InstancePrinter(options, printTop).print(srcPath, instance) > 0;
//		else
//			return new InstancePrinter(options, printTop).print(srcPath + File.separator + instance.getSimpleName(), instance) > 0;
	}
	
	@Override
	protected boolean printActor(Actor actor) {
//		if (printTop)
			return new InstancePrinter(options, printTop).print(srcPath, actor) > 0;
//		else
//			return new InstancePrinter(options, printTop).print(srcPath + File.separator + actor.getSimpleName(), actor) > 0;
	}
	
	protected boolean printVertex(Vertex vertex, String targetFolder){
		final Actor actor = vertex.getAdapter(Actor.class);
		return new InstancePrinter(options, printTop).print(targetFolder, actor) > 0;
		
	}
	
	/**
	 * Print entities of the given network.
	 * 
	 * @param entities
	 *            a list of entities
	 */
	protected void printChildrenCompa(Network network) {
//		checkConnectivy
		String procFolder;
		for (int i=0; i< nbProcessors; i++) {
			if (i<10){
				procFolder = srcPath + File.separator + "Top_0" + i;
//				procFolder = targetFolder + File::separator + "Top_0" + i + File::separator + "Top_0" + i + ".c"
			}
			else{
				procFolder = srcPath + File.separator + "Top_" + i;
//				procFolder = targetFolder + File::separator + "Top_" + i + File::separator + "Top_" + i + ".c"
			}
			
			if(i < currentMap.length){
				// Print assigned actors into the corresponding folder
				OrccLogger.traceln("Processor " + i + " : ");
				for (ActorsIndex actorIx : ActorsIndex.values()){
					if(currentMap[i][actorIx.ordinal()]){
						OrccLogger.traceln(network.getChild(actorIx.toString()).getLabel());
						printVertex(network.getChild(actorIx.toString()), procFolder);
					}
				}		
			}
		}
	}
}
