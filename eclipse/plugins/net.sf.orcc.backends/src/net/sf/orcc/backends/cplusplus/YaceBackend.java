/*
 * Copyright (c) 2009-2011, Ecole Polytechnique Fédérale de Lausanne
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
package net.sf.orcc.backends.cplusplus;

import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.OrccLaunchConstants.NO_LIBRARY_EXPORT;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.transform.TypeResizer;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.actor.ActorMerger;

import org.eclipse.core.resources.IFile;

/**
 * C++ back-end.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class YaceBackend extends AbstractBackend {

	public static final String DEFAULT_PARTITION = "default_partition";

	private boolean debugMode;

	private boolean classify;

	private boolean merge;

	@Override
	public void doInitializeOptions() {
		classify = getAttribute("net.sf.orcc.backends.classify", false);
		merge = getAttribute("net.sf.orcc.backends.merge", false);
		debugMode = getAttribute(DEBUG_MODE, true);
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
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
		transformations.add(new TypeResizer(false, false, false));
		transformations.add(new RenameTransformation(replacementMap));
		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
		}

	}

	private Network doTransformNetwork(Network network) throws OrccException {
		write("Instantiating... ");
		new Instantiator(false).doSwitch(network);
		write("done\n");
		new NetworkFlattener().doSwitch(network);

		if (classify) {
			write("Starting classification of actors... ");
			new Classifier(getWriteListener()).doSwitch(network);
			write("done\n");
			if (merge) {
				new ActorMerger().doSwitch(network);
			}
		}

		return network;
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate a C++ VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network = doTransformNetwork(network);

		transformActors(network.getAllActors());

		network.computeTemplateMaps();
		printInstances(network);

		// print network
		write("Printing network...\n");
		printNetwork(network);
	}

	@Override
	public boolean printInstance(Instance instance) {
		YacePrinter printer = new YacePrinter(!debugMode);
		printer.print(path, instance);

		return false;
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void printNetwork(Network network) throws OrccException {
		YacePrinter printer = new YacePrinter();
		printer.getOptions().put("codesign", getAttribute("codesign", false));
		printer.print(path, network);
	}

	@Override
	public boolean exportRuntimeLibrary() throws OrccException {
		if (!getAttribute(NO_LIBRARY_EXPORT, false)) {
			String target = path + File.separator + "libs";
			write("Export libraries sources into " + target + "... ");
			if (copyFolderToFileSystem("/runtime/C++", target)) {
				write("OK" + "\n");
				return true;
			} else {
				write("Error" + "\n");
				return false;
			}
		}
		return false;
	}

}
