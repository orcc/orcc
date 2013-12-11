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

import static net.sf.orcc.OrccLaunchConstants.NO_LIBRARY_EXPORT;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.transform.DeadCodeElimination;
import net.sf.orcc.ir.transform.DeadGlobalElimination;
import net.sf.orcc.ir.transform.DeadVariableRemoval;
import net.sf.orcc.ir.transform.SSAVariableRenamer;
import net.sf.orcc.ir.transform.PhiRemoval;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.ir.transform.SSATransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.actor.ActorMerger;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Void;

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

	@Override
	public void doInitializeOptions() {
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
		replacementMap.put("bitand", "bitand_");
		replacementMap.put("bitor", "bitor_");
		replacementMap.put("not", "not_");
		replacementMap.put("and", "and_");
		replacementMap.put("OUT", "OUT_");
		replacementMap.put("IN", "IN_");
		replacementMap.put("DEBUG", "DEBUG_");
		replacementMap.put("INT_MIN", "INT_MIN_");

		List<DfSwitch<?>> transformations = new ArrayList<DfSwitch<?>>();
		transformations.add(new UnitImporter());
		transformations.add(new TypeResizer(false, false, false, false));
		transformations.add(new RenameTransformation(replacementMap));
		if (!debug) {
			transformations.add(new DeadGlobalElimination());
			transformations.add(new DfVisitor<Void>(new SSATransformation()));
			transformations.add(new DfVisitor<Void>(new PhiRemoval()));
			transformations.add(new DfVisitor<Void>(new SSAVariableRenamer()));
			transformations.add(new DeadGlobalElimination());
			transformations.add(new DfVisitor<Void>(new DeadVariableRemoval()));
			transformations.add(new DfVisitor<Void>(new DeadCodeElimination()));
			transformations.add(new DfVisitor<Void>(new DeadVariableRemoval()));
		}

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
		}

	}

	private void doTransformNetwork(Network network) {
		OrccLogger.trace("Instantiating... ");
		new Instantiator(false).doSwitch(network);
		OrccLogger.traceRaw("done\n");
		new NetworkFlattener().doSwitch(network);

		if (classify) {
			OrccLogger.trace("Starting classification of actors... ");
			new Classifier().doSwitch(network);
			OrccLogger.traceRaw("done\n");
		}
		if (mergeActors) {
			new ActorMerger().doSwitch(network);
		}
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		// do not generate a C++ VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {

		doTransformNetwork(network);
		transformActors(network.getAllActors());

		network.computeTemplateMaps();

		// hack, should be done in the method just above
		for (Vertex vertex : network.getChildren()) {
			Entity entity = vertex.getAdapter(Entity.class);
			Map<Port, List<Connection>> map = entity.getOutgoingPortMap();
			for (List<Connection> connections : map.values()) {
				for (Connection connection : connections) {
					connection.setAttribute("nbReaders", connections.size());
				}
			}
		}

		printChildren(network);
		// print network
		OrccLogger.traceln("Printing network...");
		printNetwork(network);
	}

	@Override
	public boolean printInstance(Instance instance) {
		return new InstancePrinter(instance, options).print(path) > 1;
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            a network
	 */
	public void printNetwork(Network network) {

		new Mapping(network, mapping).print(path);
		NetworkPrinter printer = new NetworkPrinter(network, options);

		printer.printNetwork(path);
		printer.printCMakeLists(path);

	}

	@Override
	protected boolean exportRuntimeLibrary() {
		if (!getAttribute(NO_LIBRARY_EXPORT, false)) {
			String target = path + File.separator + "libs";
			OrccLogger
					.trace("Export libraries sources into " + target + "... ");
			if (copyFolderToFileSystem("/runtime/C++", target, debug)) {
				OrccLogger.traceRaw("OK" + "\n");
				return true;
			} else {
				OrccLogger.warnRaw("Error" + "\n");
				return false;
			}
		}
		return false;
	}

}
