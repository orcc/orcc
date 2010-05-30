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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.cal.CalStandaloneSetup;
import net.sf.orcc.cal.cal.AstActor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.parser.antlr.IAntlrParser;

import com.google.inject.Injector;

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
		if (args.length == 2) {
			new FrontendCli(args[0], args[1]);
		} else {
			System.err.println("Usage: Frontend "
					+ "<absolute path of VTL folder> "
					+ "<absolute path of output folder>");
		}
	}

	private List<File> actors;

	private Frontend frontend;

	private IParser parser;

	public FrontendCli(String vtlFolder, String outputFolder)
			throws OrccException {
		File file = new File(outputFolder);
		if (!file.exists()) {
			file.mkdir();
		}
		
		frontend = new Frontend(outputFolder);

		// guice stuff
		Injector guiceInjector = new CalStandaloneSetup()
				.createInjectorAndDoEMFRegistration();
		parser = guiceInjector.getInstance(IAntlrParser.class);

		File vtl = new File(vtlFolder);
		actors = new ArrayList<File>();
		getActors(vtl);
		for (File actor : actors) {
			processActor(actor);
		}
	}

	private void getActors(File vtl) {
		for (File file : vtl.listFiles()) {
			if (file.isDirectory()) {
				getActors(file);
			} else if (file.getName().endsWith(".cal")) {
				actors.add(file);
			}
		}
	}

	private void processActor(File actorPath) throws OrccException {
		AstActor astActor = null;

		try {
			Reader in = new FileReader(actorPath);
			IParseResult result = parser.parse(in);
			EObject root = result.getRootASTElement();
			astActor = (AstActor) root;
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}

		frontend.compile(actorPath.getAbsolutePath(), astActor);
	}

}
