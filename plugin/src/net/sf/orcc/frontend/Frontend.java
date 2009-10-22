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
package net.sf.orcc.frontend;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import net.sf.orcc.OrccException;
import net.sf.orcc.frontend.parser.RVCCalASTParser;
import net.sf.orcc.frontend.writer.ActorWriter;
import net.sf.orcc.ir.actor.ActionScheduler;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.parser.NetworkParser;

/**
 * This class defines an RVC-CAL front-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Frontend {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws OrccException {
		if (args.length == 2) {
			new Frontend(args[0], args[1]);
		} else {
			System.err.println("Usage: Frontend "
					+ "<absolute path of top-level network> "
					+ "<absolute path of output folder>");
		}
	}

	/**
	 * a set of file names that contain actors
	 */
	private Set<String> actors;

	/**
	 * output folder
	 */
	private File outputFolder;

	/**
	 * Creates a front-end that parses the given top-level network, compiles
	 * RVC-CAL actors referenced by networks into IR form and serializes IR
	 * actors into the given output folder. The networks are just copied to the
	 * output folder.
	 * 
	 * @param inputFolder
	 * @param outputFolder
	 * @throws OrccException
	 */
	public Frontend(String topLevelNetwork, String outputFolder)
			throws OrccException {
		String fileName;
		try {
			this.outputFolder = new File(outputFolder).getCanonicalFile();
			fileName = new File(topLevelNetwork).getCanonicalPath();
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}

		actors = new TreeSet<String>();

		NetworkParser parser = new NetworkParser(fileName);
		Network network = parser.parseNetwork();
		getActors(network);
		processActors();
	}

	/**
	 * Gets the actors referenced by the given network, and add the path where
	 * they can be loaded from to the actors set.
	 * 
	 * @param network
	 *            a network
	 */
	private void getActors(Network network) {
		for (Vertex vertex : network.getGraph().vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isNetwork()) {
					getActors(instance.getNetwork());
				} else {
					IAttribute attr = instance.getAttribute("skip");
					if (attr == null || attr.getType() != IAttribute.FLAG) {
						// generate code if the "skip" attribute is not present
						// or is not a flag
						String parent = instance.getFile().getParent();
						String clasz = instance.getClasz();
						actors.add(parent + File.separator + clasz + ".cal");
					}
				}
			}
		}
	}

	/**
	 * Parses all the actors in the actors set, translates them to IR, and
	 * writes them to the output folder.
	 */
	private void processActors() {
		for (String path : actors) {
			try {
				RVCCalASTParser parser = new RVCCalASTParser(path);
				Actor actor = parser.parse();

				// prints priority graph
				String fileName = outputFolder + File.separator + "priority_"
						+ actor.getName() + ".dot";
				parser.printPriorityGraph(fileName);

				// prints FSM
				fileName = outputFolder + File.separator + "fsm_"
						+ actor.getName() + ".dot";
				parser.printFSMGraph(fileName);

				// prints FSM after priorities have been applied
				ActionScheduler scheduler = actor.getActionScheduler();
				if (scheduler.hasFsm()) {
					fileName = outputFolder + File.separator + "fsm2_"
							+ actor.getName() + ".dot";
					scheduler.getFsm().printGraph(fileName);
				}

				new ActorWriter(actor).write(outputFolder.toString());
			} catch (OrccException e) {
				e.printStackTrace();
			}
		}
	}
}
