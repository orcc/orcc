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

import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.transformations.UnitImporter;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transformations.BroadcastAdder;
import net.sf.orcc.df.transformations.Instantiator;
import net.sf.orcc.df.transformations.NetworkFlattener;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.transformations.RenameTransformation;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;

/**
 * Java back-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class JavaBackendImpl extends AbstractBackend {

	private boolean debug;

	private final Map<String, String> transformations;

	public JavaBackendImpl() {
		transformations = new HashMap<String, String>();
		transformations.put("initialize", "my_initialize");
		transformations.put("isSchedulable_initialize",
				"my_isSchedulable_initialize");

		transformations.put("byte", "my_byte");
		transformations.put("int", "my_int");
		transformations.put("boolean", "my_boolean");
		transformations.put("long", "my_long");
		transformations.put("short", "my_short");
	}

	@Override
	protected void doInitializeOptions() {
		debug = getAttribute(DEBUG_MODE, true);
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		DfSwitch<?>[] transformations = { new UnitImporter(),
				new RenameTransformation(this.transformations) };

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
		}

		actor.setTemplateData(new JavaTemplateData(actor));
	}

	private Network doTransformNetwork(Network network) throws OrccException {
		// instantiate and flattens network
		write("Instantiating...\n");
		new Instantiator(fifoSize).doSwitch(network);

		write("Flattening...\n");
		new NetworkFlattener().doSwitch(network);

		// Add broadcasts before printing
		new BroadcastAdder().doSwitch(network);

		return network;
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		List<Actor> actors = parseActors(files);

		transformActors(actors);
		printActors(actors);
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network = doTransformNetwork(network);

		// print network
		write("Printing network...\n");
		printNetwork(network);
		write("Done\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.orcc.backends.AbstractBackend#exportRuntimeLibrary()
	 */
	@Override
	public boolean exportRuntimeLibrary() throws OrccException {
		String target = path + File.separator + "libs";
		write("Export libraries sources into " + target + "... ");
		if (copyFolderToFileSystem("/runtime/Java", target)) {
			write("OK" + "\n");
			return true;
		} else {
			write("Error" + "\n");
			return false;
		}
	}

	@Override
	protected boolean printActor(Actor actor) {
		StandardPrinter actorPrinter = new StandardPrinter(
				"net/sf/orcc/backends/java/Actor.stg", !debug);
		actorPrinter.setExpressionPrinter(new JavaExprPrinter());
		actorPrinter.setTypePrinter(new JavaTypePrinter());

		// create folder if necessary
		String folder = path + File.separator + OrccUtil.getFolder(actor);
		new File(folder).mkdirs();

		// transfer to template usage status of native procedures
		for (Procedure p : actor.getProcs()) {
			if (!p.getName().equals("print") && p.isNative()) {
				actorPrinter.getOptions().put("usingNativeProc", true);
				break;
			}
		}

		return actorPrinter.print(actor.getSimpleName() + ".java", folder,
				actor);
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	protected void printNetwork(Network network) throws OrccException {
		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/java/Network.stg");
		printer.setExpressionPrinter(new JavaExprPrinter());
		printer.setTypePrinter(new JavaTypePrinter());
		printer.getOptions().put("fifoSize", fifoSize);

		// create folder if necessary
		String folder = path + File.separator
				+ network.getPackage().replace('.', '/');
		new File(folder).mkdirs();

		String fileName = network.getSimpleName() + ".java";

		printer.print(fileName, folder, network);
	}

}
