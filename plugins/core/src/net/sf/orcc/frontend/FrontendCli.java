/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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

import net.sf.orcc.OrccException;

/**
 * This class defines an RVC-CAL front-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class FrontendCli {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws OrccException {
		if (args.length == 4) {
			boolean printPrio = Boolean.parseBoolean(args[2]);
			boolean printFSM = Boolean.parseBoolean(args[3]);
			new FrontendCli(args[0], args[1], printPrio, printFSM);
		} else if (args.length == 2) {
			new FrontendCli(args[0], args[1]);
		} else {
			System.err.println("Usage: Frontend "
					+ "<absolute path of VTL folder> "
					+ "<absolute path of output folder> "
					+ "[<priorities print flag> <FSM print flag>]");
		}
	}

	/**
	 * output folder
	 */
	//private File outputFolder;

	//private IParser parser;

	/**
	 * print FSM flag
	 */
	//private boolean printFSM;

	/**
	 * print priorities flag
	 */
	//private boolean printPriorities;

	public FrontendCli(String vtlFolder, String outputFolder)
			throws OrccException {
		this(vtlFolder, outputFolder, false, false);
	}

	/**
	 * 
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
	public FrontendCli(String vtlFolder, String outputFolder,
			boolean printPriorities, boolean printFSM) throws OrccException {
//		this.printPriorities = printPriorities;
//		this.printFSM = printFSM;
//
//		try {
//			this.outputFolder = new File(outputFolder).getCanonicalFile();
//		} catch (IOException e) {
//			throw new OrccException("I/O error", e);
//		}
//
//		// guice stuff
//		Injector guiceInjector = new CalStandaloneSetup()
//				.createInjectorAndDoEMFRegistration();
//		parser = guiceInjector.getInstance(IAntlrParser.class);

		
	}

//	private Actor processActor(String actorPath) throws OrccException {
//		net.sf.orcc.cal.cal.Actor aActor = null;
//
//		try {
//			actorPath = new File(actorPath + ".cal").getCanonicalPath();
//			Reader in = new FileReader(actorPath);
//			IParseResult result = parser.parse(in);
//			EObject root = result.getRootASTElement();
//			aActor = (net.sf.orcc.cal.cal.Actor) root;
//		} catch (IOException e) {
//			throw new OrccException("I/O error", e);
//		}
//
//		Actor actor = new AstToIR().transform(actorPath, aActor);
//
//		if (printPriorities) {
//			// prints priority graph
//			String fileName = "priority_" + actor.getName() + ".dot";
//			File file = new File(outputFolder, fileName);
//			file.toString();
//		}
//
//		if (printFSM) {
//			// prints FSM
//			String fileName = "fsm_" + actor.getName() + ".dot";
//			File file = new File(outputFolder, fileName);
//
//			// prints FSM after priorities have been applied
//			ActionScheduler scheduler = actor.getActionScheduler();
//			if (scheduler.hasFsm()) {
//				fileName = "fsm_" + actor.getName() + "_2.dot";
//				file = new File(outputFolder, fileName);
//				scheduler.getFsm().printGraph(file);
//			}
//		}
//
//		new IRWriter(actor).write(outputFolder.toString());
//		return actor;
//	}

}
