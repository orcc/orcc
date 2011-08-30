package net.sf.orcc.cal;

import java.io.InputStream;
import java.util.List;

import junit.framework.Assert;

import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(CalInjectorProvider.class)
public class AllTests {

	@Inject
	private ParseHelper<AstEntity> parser;

	private XtextResourceSet resourceSet;

	private static final String prefix = "net/sf/orcc/cal/test/";

	public AllTests() {
		resourceSet = new XtextResourceSet();
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL,
				Boolean.TRUE);
	}

	@Test
	public void checkParam() throws Exception {
		Assert.assertNull(
				"assignment to an actor parameter must not be allowed",
				parseAndValidate(prefix + "xfail/Param.cal"));
	}

	@Test
	public void checkPattern1() throws Exception {
		Assert.assertNull(
				"reference to an output port in an input pattern must not be allowed",
				parseAndValidate(prefix + "xfail/Pattern1.cal"));
	}

	@Test
	public void checkPattern2() throws Exception {
		Assert.assertNull("an input pattern cannot contain expressions",
				parseAndValidate(prefix + "xfail/Pattern2.cal"));
	}

	@Test
	public void checkPattern3() throws Exception {
		Assert.assertNull(
				"combining Pattern1 and Pattern2 must be invalid code",
				parseAndValidate(prefix + "xfail/Pattern3.cal"));
	}

	@Test
	public void checkTypeCheck() throws Exception {
		Assert.assertNull(
				"passing a list in lieu of a scalar must raise a type error",
				parseAndValidate(prefix + "xfail/TypeCheck.cal"));
	}

	@Test
	public void checkInitialize() throws Exception {
		Assert.assertNotNull("expected correct actor with initialize action",
				parseAndValidate(prefix + "pass/InitializePattern.cal"));
	}

	@Test
	public void checkTypeInt() throws Exception {
		AstEntity entity = parseAndValidate(prefix + "pass/TypeInt.cal");
		List<AstVariable> stateVars = entity.getActor().getStateVariables();
		AstVariable x = stateVars.get(0);
		AstVariable y = stateVars.get(1);
		Type type = Util.getType(x);
		Assert.assertTrue("type of x should be int(size=5)",
				EcoreUtil.equals(type, IrFactory.eINSTANCE.createTypeInt(5)));

		type = Util.getType(x.getValue());
		Assert.assertTrue("type of value of x should be int(size=4)",
				EcoreUtil.equals(type, IrFactory.eINSTANCE.createTypeInt(4)));

		type = Util.getType(y.getValue());
		Assert.assertTrue("type of value of y should be int(size=6)",
				EcoreUtil.equals(type, IrFactory.eINSTANCE.createTypeInt(6)));
	}

	private AstEntity parseAndValidate(String name) {
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(name);
		URI uri = URI.createPlatformResourceURI(name, true);
		AstEntity entity = parser.parse(in, uri, null, resourceSet);

		boolean isValid = true;

		// contains linking errors
		Resource resource = entity.eResource();
		List<Diagnostic> errors = resource.getErrors();
		if (!errors.isEmpty()) {
			for (Diagnostic error : errors) {
				System.err.println(error);
			}

			isValid = false;
		}

		// validates (unique names and CAL validator)
		IResourceValidator v = ((XtextResource) resource)
				.getResourceServiceProvider().getResourceValidator();
		List<Issue> issues = v.validate(resource, CheckMode.ALL,
				CancelIndicator.NullImpl);

		for (Issue issue : issues) {
			if (issue.getSeverity() == Severity.ERROR) {
				System.err.println(issue.toString());
				isValid = false;
			} else {
				System.out.println(issue.toString());
			}
		}

		return isValid ? entity : null;
	}

}
