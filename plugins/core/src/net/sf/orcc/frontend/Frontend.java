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

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.serialize.IRWriter;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.serialize.XDFParser;
import net.sf.orcc.network.serialize.XDFWriter;

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
		if (args.length == 4) {
			boolean printPrio = Boolean.parseBoolean(args[2]);
			boolean printFSM = Boolean.parseBoolean(args[3]);
			new Frontend(args[0], args[1], printPrio, printFSM);
		} else if (args.length == 2) {
			new Frontend(args[0], args[1]);
		} else {
			System.err.println("Usage: Frontend "
					+ "<absolute path of top-level network> "
					+ "<absolute path of output folder> "
					+ "[<priorities print flag> <FSM print flag>]");
		}
	}

	/**
	 * output folder
	 */
	private File outputFolder;

	/**
	 * print FSM flag
	 */
	private boolean printFSM;

	/**
	 * print priorities flag
	 */
	private boolean printPriorities;

	/**
	 * Creates a front-end that parses the network hierarchy starting from the
	 * top-level network, compiles RVC-CAL actors referenced by networks into IR
	 * form and serializes IR actors into the given output folder. The networks
	 * are serialized back to XDF to the output folder.
	 * 
	 * <p>
	 * The load/save cycle of XDF networks ensures that any reference to
	 * relative folders is solved, which means that the networks saved in the
	 * output folder are self-contained, i.e. only reference networks present in
	 * the same folder.
	 * </p>
	 * 
	 * @param topLevelNetwork
	 *            absolute file name of top-level network
	 * @param outputFolder
	 *            absolute file name of output folder
	 * @throws OrccException
	 */
	public Frontend(String topLevelNetwork, String outputFolder)
			throws OrccException {
		this(topLevelNetwork, outputFolder, false, false);
	}

	/**
	 * Creates a front-end that parses the network hierarchy starting from the
	 * top-level network, compiles RVC-CAL actors referenced by networks into IR
	 * form and serializes IR actors into the given output folder. The networks
	 * are serialized back to XDF to the output folder.
	 * 
	 * <p>
	 * The load/save cycle of XDF networks ensures that any reference to
	 * relative folders is solved, which means that the networks saved in the
	 * output folder are self-contained, i.e. only reference networks present in
	 * the same folder.
	 * </p>
	 * 
	 * @param topLevelNetwork
	 *            absolute file name of top-level network
	 * @param outputFolder
	 *            absolute file name of output folder
	 * @param printPriorities
	 *            if <code>true</code>, the front-end will print a DOT file with
	 *            the priority graph
	 * @param printFSM
	 *            if <code>true</code>, the front-end will print a DOT file with
	 *            the FSM graph
	 * @throws OrccException
	 */
	public Frontend(String topLevelNetwork, String outputFolder,
			boolean printPriorities, boolean printFSM) throws OrccException {
		this.printPriorities = printPriorities;
		this.printFSM = printFSM;

		String fileName;
		try {
			this.outputFolder = new File(outputFolder).getCanonicalFile();
			fileName = new File(topLevelNetwork).getCanonicalPath();
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}

		XDFParser parser = new XDFParser(fileName);
		Network network = parser.parseNetwork();
		getActors(network);

		new XDFWriter(this.outputFolder, network);
	}

	/**
	 * Gets the actors referenced by the given network, and add the path where
	 * they can be loaded from to the actors set.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 */
	private void getActors(Network network) throws OrccException {
		for (Instance instance : network.getInstances()) {
			if (instance.isNetwork()) {
				getActors(instance.getNetwork());
			} else {
				IAttribute attr = instance.getAttribute("skip");
				if (attr == null || attr.getType() != IAttribute.FLAG) {
					// generate code if the "skip" attribute is not present
					// or is not a flag
					String parent = instance.getFile().getParent();
					String clasz = instance.getClasz();

					String actorPath = parent + File.separator + clasz;
					Actor actor = processActor(actorPath);
					instance.setClasz(actor.getName());
				}
			}
		}
	}

	private Actor processActor(String actorPath) throws OrccException {
		net.sf.orcc.cal.Actor astActor;
		Actor actor = null;

		if (printPriorities) {
			// prints priority graph
			String fileName = "priority_" + actor.getName() + ".dot";
			File file = new File(outputFolder, fileName);
		}

		if (printFSM) {
			// prints FSM
			String fileName = "fsm_" + actor.getName() + ".dot";
			File file = new File(outputFolder, fileName);

			// prints FSM after priorities have been applied
			ActionScheduler scheduler = actor.getActionScheduler();
			if (scheduler.hasFsm()) {
				fileName = "fsm_" + actor.getName() + "_2.dot";
				file = new File(outputFolder, fileName);
				scheduler.getFsm().printGraph(file);
			}
		}

		new IRWriter(actor).write(outputFolder.toString());
		return actor;
	}

}
