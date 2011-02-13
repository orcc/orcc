package net.sf.orcc.test;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.cal.CalStandaloneSetup;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.frontend.Frontend;
import net.sf.orcc.interpreter.ActorInterpreter;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.serialize.IRParser;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.Severity;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Injector;

public class TestFrontend {

	private static Frontend frontend;

	private static String path;

	private static XtextResourceSet resourceSet;

	@BeforeClass
	public static void configureInjector() {
		System.out.println("setup of CAL Xtext parser");
		Injector injector = new CalStandaloneSetup()
				.createInjectorAndDoEMFRegistration();
		frontend = injector.getInstance(Frontend.class);
		System.out.println("done");

		resourceSet = new XtextResourceSet();
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL,
				Boolean.TRUE);

		// resolve test path
		try {
			path = new File(System.getProperty("user.dir")
					+ "/../../../test/Pass").getCanonicalPath();
		} catch (IOException e) {
			fail("cannot find the Pass test folder");
		}

		// configure output folder
		String outputFolder = new File(path, "bin").getAbsolutePath();
		frontend.setOutputFolder(outputFolder);
	}

	/**
	 * Compiles the actor located at the given absolute path.
	 * 
	 * @param path
	 *            absolute path of actor to compile
	 */
	private void compile(String path) {
		URI uri = URI.createFileURI(path);
		Resource resource = resourceSet.getResource(uri, true);
		AstEntity entity = (AstEntity) resource.getContents().get(0);

		boolean hasErrors = false;

		// contains linking errors
		List<Diagnostic> errors = resource.getErrors();
		if (!errors.isEmpty()) {
			for (Diagnostic error : errors) {
				System.err.println(error);
			}

			hasErrors = true;
		}

		// validates (unique names and CAL validator)
		IResourceValidator v = ((XtextResource) resource)
				.getResourceServiceProvider().getResourceValidator();
		List<Issue> issues = v.validate(resource, CheckMode.ALL,
				new CancelIndicator.NullImpl());

		for (Issue issue : issues) {
			if (issue.getSeverity() == Severity.ERROR) {
				System.err.println(issue.toString());
				hasErrors = true;
			} else {
				System.out.println(issue.toString());
			}
		}

		if (hasErrors) {
			fail("file " + path + " has compile errors");
		}

		// only compile if there are no errors
		try {
			AstActor actor = entity.getActor();
			if (actor != null) {
				frontend.compile(path, actor);
			}
		} catch (OrccException e) {
			fail("unexpected exception when compiling file " + path);
		}
	}

	private void interpret(String path) {
		IRParser parser = new IRParser();
		Actor actor = null;
		try {
			actor = parser.parseActor(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			fail("IR file not found: " + path);
		} catch (OrccException e) {
			fail("unexpected exception when compiling file " + path);
		}

		ActorInterpreter interpreter = new ActorInterpreter(null, actor, null);
		interpreter.schedule();
	}

	@Test
	public void test1() {
		File file = new File(path, "VTL/net/sf/orcc/test/actors/Actor1.cal");
		compile(file.getAbsolutePath());
		file = new File(path, "bin/net/sf/orcc/test/actors/Actor1.json");
		interpret(file.getAbsolutePath());
	}

}
