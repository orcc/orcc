/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.backends.java;

import static net.sf.orcc.OrccLaunchConstants.NO_LIBRARY_EXPORT;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;

/**
 * Java back-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class JavaBackendImpl extends AbstractBackend {

	private JavaPrinter printer;

	private String srcPath;
	private String libsPath;

	private final Map<String, String> replacementMap;

	public JavaBackendImpl() {
		replacementMap = new HashMap<String, String>();
		replacementMap.put("initialize", "my_initialize");
		replacementMap.put("isSchedulable_initialize",
				"my_isSchedulable_initialize");

		replacementMap.put("byte", "my_byte");
		replacementMap.put("int", "my_int");
		replacementMap.put("boolean", "my_boolean");
		replacementMap.put("long", "my_long");
		replacementMap.put("short", "my_short");

	}

	@Override
	protected void doInitializeOptions() {
		printer = new JavaPrinter(!debug);

		srcPath = path + File.separator + "src";
		libsPath = path + File.separator + "libs";
	}

	@Override
	protected void doTransformActor(Actor actor) {
		actorTransfos.add(new UnitImporter());
		actorTransfos.add(new RenameTransformation(replacementMap));

		for (DfSwitch<?> transformation : actorTransfos) {
			transformation.doSwitch(actor);
		}
	}

	private Network doTransformNetwork(Network network) {
		// instantiate and flattens network
		OrccLogger.traceln("Instantiating...");
		new Instantiator(false, fifoSize).doSwitch(network);

		OrccLogger.traceln("Flattening...");
		new NetworkFlattener().doSwitch(network);

		new BroadcastAdder().doSwitch(network);

		return network;
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		List<Actor> actors = parseActors(files);
		transformActors(actors);
		printActors(actors);
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		network = doTransformNetwork(network);

		// print network
		OrccLogger.traceln("Printing network...");
		printNetwork(network);
		OrccLogger.traceln("Done");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.orcc.backends.AbstractBackend#exportRuntimeLibrary()
	 */
	@Override
	public boolean exportRuntimeLibrary() {
		if (!getAttribute(NO_LIBRARY_EXPORT, false)) {
			OrccLogger.trace("Export libraries sources into " + libsPath
					+ "... ");
			if (copyFolderToFileSystem("/runtime/Java/src", libsPath)) {
				OrccLogger.traceRaw("OK" + "\n");
				return true;
			} else {
				OrccLogger.warnRaw("Error" + "\n");
				return false;
			}
		}
		return false;
	}

	@Override
	protected boolean printActor(Actor actor) {

		String folder = srcPath + File.separator + OrccUtil.getFolder(actor);

		return printer.print(folder, actor);
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            a network
	 */
	protected void printNetwork(Network network) {
		printer.printEclipseProjectFiles(path, network);
		printer.print(srcPath, network);
	}

}
