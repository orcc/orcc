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
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.cal.CalStandaloneSetup;
import net.sf.orcc.cal.cal.AstActor;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

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
	public static void main(String[] args) {
		if (args.length == 2) {
			System.out.println("doing setup of CAL Xtext parser");
			Injector injector = new CalStandaloneSetup()
					.createInjectorAndDoEMFRegistration();
			FrontendCli fe = injector.getInstance(FrontendCli.class);
			System.out.println("done");

			fe.setVtlFolder(args[0]);
			fe.setOutputFolder(args[1]);
			fe.processActors();
		} else {
			System.err.println("Usage: Frontend "
					+ "<absolute path of VTL folder> "
					+ "<absolute path of output folder>");
		}
	}

	private List<File> actors;

	private Frontend frontend;

	private XtextResourceSet resourceSet;

	public FrontendCli() {

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

	private void processActor(File actorPath) {
		URI uri = URI.createFileURI(actorPath.getAbsolutePath());
		Resource resource = resourceSet.getResource(uri, true);
		AstActor astActor = (AstActor) resource.getContents().get(0);

		// only compile if actor has no errors
		List<Diagnostic> errors = astActor.eResource().getErrors();
		if (!errors.isEmpty()) {
			for (Diagnostic error : errors) {
				System.err.println(error);
			}

			return;
		}

		try {
			frontend.compile(actorPath.getAbsolutePath(), astActor);
		} catch (OrccException e) {
			e.printStackTrace();
		}
	}

	public void processActors() {
		resourceSet = new XtextResourceSet();
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL,
				Boolean.TRUE);
		for (File actor : actors) {
			processActor(actor);
		}
	}

	private void setOutputFolder(String outputFolder) {
		File file = new File(outputFolder);
		if (!file.exists()) {
			file.mkdir();
		}

		frontend = new Frontend(outputFolder);
	}

	private void setVtlFolder(String vtlFolder) {
		File vtl = new File(vtlFolder);
		actors = new ArrayList<File>();
		getActors(vtl);
	}

}
