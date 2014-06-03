package net.sf.orcc.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccProjectNature;
import net.sf.orcc.cal.CalInjectorProvider;
import net.sf.orcc.cal.CalStandaloneSetup;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.services.Evaluator;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.df.Actor;
import net.sf.orcc.frontend.ActorTransformer;
import net.sf.orcc.frontend.UnitTransformer;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.test.util.TestInterpreter;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@RunWith(XtextRunner.class)
@InjectWith(CalInjectorProvider.class)
public class AllTests extends AbstractXtextTests {

	private static final String prefix = "net/sf/orcc/cal/test/";

	private static final String projectName = "Tests";

	private static final String projectPrefix = "/Tests/src/";

	@Inject
	private ParseHelper<AstEntity> parser;

	@Inject
	private Provider<XtextResourceSet> provider;

	@Inject
	private IResourceServiceProvider serviceProvider;

	@Override
	@Before
	public void setUp() {
		try {
			super.setUp();
			with(CalStandaloneSetup.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		if (!project.exists()) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			try {
				IProjectDescription description = workspace
						.newProjectDescription(projectName);

				String[] natures = description.getNatureIds();
				String[] newNatures = new String[natures.length + 2];

				// add new natures
				System.arraycopy(natures, 0, newNatures, 2, natures.length);
				newNatures[0] = OrccProjectNature.NATURE_ID;
				newNatures[1] = JavaCore.NATURE_ID;
				description.setNatureIds(newNatures);

				project.create(description, null);

				// retrieves the up-to-date description
				project.open(null);
				description = project.getDescription();

				// filters out the Java builder
				ICommand[] commands = description.getBuildSpec();
				List<ICommand> buildSpec = new ArrayList<ICommand>(
						commands.length);
				for (ICommand command : commands) {
					if (!JavaCore.BUILDER_ID.equals(command.getBuilderName())) {
						buildSpec.add(command);
					}
				}

				// updates the description and replaces the existing description
				description.setBuildSpec(buildSpec.toArray(new ICommand[0]));
				project.setDescription(description, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Parses, validates, compiles, and interprets the actor defined in the file
	 * whose name is given. Then matches the output of the interpreter with the
	 * <code>expected</code> string.
	 * 
	 * @param expected
	 *            expected output
	 * @param name
	 *            name of a .cal file that contains an entity
	 */
	private void assertExecution(String expected, String name) {
		EObject entity = generateCode(name);
		Assert.assertNotNull("expected parsing, validation, and code "
				+ "generation to be correct for " + name, entity);

		TestInterpreter interpreter = new TestInterpreter((Actor) entity);
		interpreter.initialize();
		interpreter.schedule();

		String output = interpreter.getOutput();
		Assert.assertEquals("expected " + expected + ", got " + output,
				expected, output);
	}

	/**
	 * Parses, validates, and generates code for the entity defined in the file
	 * whose name is given.
	 * 
	 * @param name
	 *            name of a .cal file that contains an entity
	 * @return an IR entity if the file could be parsed, validated, and
	 *         translated to IR, otherwise <code>null</code>
	 */
	private EObject generateCode(String name) {
		AstEntity entity = parseAndValidate(name);
		if (entity == null) {
			return null;
		}

		if (entity.getActor() != null) {
			return new ActorTransformer().doSwitch(entity.getActor());
		} else if (entity.getUnit() != null) {
			return new UnitTransformer().doSwitch(entity.getActor());
		}
		return null;
	}

	/**
	 * Parses and validates the entity defined in the file whose name is given.
	 * 
	 * @param name
	 *            name of a .cal file that contains an entity
	 * @return an AST entity if the file could be parsed and validated,
	 *         otherwise <code>null</code>
	 */
	private AstEntity parseAndValidate(String name) {
		XtextResourceSet resourceSet = provider.get();
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL,
				Boolean.TRUE);

		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(name);
		URI uri = URI.createPlatformResourceURI(projectPrefix + name, true);
		AstEntity entity = parser.parse(in, uri, null, resourceSet);

		// validates resources
		IResourceValidator v = serviceProvider.getResourceValidator();
		Resource resource = entity.eResource();
		List<Issue> issues = v.validate(resource, CheckMode.ALL,
				CancelIndicator.NullImpl);

		boolean isValid = true;
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

	@Test
	public void passCheckGenerator() throws Exception {
		AstEntity entity = parseAndValidate(prefix + "pass/Generator.cal");
		List<Variable> stateVars = entity.getActor().getStateVariables();
		Variable var = stateVars.get(0);

		Expression expr = Evaluator.getValue(var);

		ExprList l11 = IrFactory.eINSTANCE.createExprList();
		l11.getValue().add(IrFactory.eINSTANCE.createExprInt(1));
		l11.getValue().add(IrFactory.eINSTANCE.createExprInt(2));
		l11.getValue().add(IrFactory.eINSTANCE.createExprInt(3));

		ExprList l12 = IrFactory.eINSTANCE.createExprList();
		l12.getValue().add(IrFactory.eINSTANCE.createExprInt(2));
		l12.getValue().add(IrFactory.eINSTANCE.createExprInt(4));
		l12.getValue().add(IrFactory.eINSTANCE.createExprInt(6));

		ExprList l1 = IrFactory.eINSTANCE.createExprList();
		l1.getValue().add(l11);
		l1.getValue().add(l12);

		ExprList expected = IrFactory.eINSTANCE.createExprList();
		expected.getValue().add(l1);

		String strExpected = new ExpressionPrinter().doSwitch(expected);

		Assert.assertTrue("value of stateVar should be " + strExpected,
				EcoreUtil.equals(expr, expected));
	}

	@Test
	public void passCheckInitialize() throws Exception {
		Assert.assertNotNull("expected correct actor with initialize action",
				parseAndValidate(prefix + "pass/InitializePattern.cal"));
	}

	@Test
	public void passCheckTypeInt() throws Exception {
		AstEntity entity = parseAndValidate(prefix + "pass/TypeInt.cal");
		List<Variable> stateVars = entity.getActor().getStateVariables();
		Variable x = stateVars.get(0);
		Variable y = stateVars.get(1);
		Type type = Typer.getType(x);
		Assert.assertTrue("type of x should be uint(size=3)",
				EcoreUtil.equals(type, IrFactory.eINSTANCE.createTypeUint(3)));

		type = Typer.getType(x.getValue());
		Assert.assertTrue("type of value of x should be uint(size=3)",
				EcoreUtil.equals(type, IrFactory.eINSTANCE.createTypeUint(3)));

		// type of "x + 1" is type of "8", i.e. uint(size=4)
		// but because the expression is assigned to y whose type is int(...)
		// it becomes int(size=5)
		type = Typer.getType(y.getValue());
		Assert.assertTrue("type of value of y should be uint(size=4)",
				EcoreUtil.equals(type, IrFactory.eINSTANCE.createTypeUint(4)));
	}

	@Test
	public void passExecElsif() throws Exception {
		assertExecution("okok", prefix + "pass/Elsif.cal");
	}

	@Test
	public void passExecElsifExpr() throws Exception {
		assertExecution("result = 0", prefix + "pass/ElsifExpr.cal");
	}

	@Test
	public void passExecElsifStateVar() throws Exception {
		assertExecution("ok", prefix + "pass/ElsifStateVar.cal");
	}

	@Test
	public void passExecInitStateVar() throws Exception {
		assertExecution("pp = 8", prefix + "pass/InitStateVarFunction.cal");
	}

	@Test
	public void passExecShadow() throws Exception {
		assertExecution("x = 0", prefix + "pass/Shadowing.cal");
	}

	@Test
	public void passExecWhile() throws Exception {
		assertExecution("idx is 60", prefix + "pass/CodegenWhile.cal");
	}

	@Test
	public void xfailCheckBadFsm() throws Exception {
		Assert.assertNull(
				"there cannot be two transitions from a source state with the same action",
				parseAndValidate(prefix + "xfail/BadFsm.cal"));
	}

	@Test
	public void xfailCheckParam() throws Exception {
		Assert.assertNull(
				"assignment to an actor parameter must not be allowed",
				parseAndValidate(prefix + "xfail/Param.cal"));
	}

	@Test
	public void xfailCheckPattern1() throws Exception {
		Assert.assertNull(
				"reference to an output port in an input pattern must not be allowed",
				parseAndValidate(prefix + "xfail/Pattern1.cal"));
	}

	@Test
	public void xfailCheckPattern2() throws Exception {
		Assert.assertNull("an input pattern cannot contain expressions",
				parseAndValidate(prefix + "xfail/Pattern2.cal"));
	}

	@Test
	public void xfailCheckPattern3() throws Exception {
		Assert.assertNull(
				"combining Pattern1 and Pattern2 must be invalid code",
				parseAndValidate(prefix + "xfail/Pattern3.cal"));
	}

	@Test
	public void xfailCheckPattern4() throws Exception {
		Assert.assertNull(
				"more than one reference per output port must not be allowed",
				parseAndValidate(prefix + "xfail/Pattern4.cal"));
	}

	@Test
	public void xfailCheckPattern5() throws Exception {
		Assert.assertNull(
				"more than one reference per input port must not be allowed",
				parseAndValidate(prefix + "xfail/Pattern5.cal"));
	}

	@Test
	public void xfailCheckTypeError1() throws Exception {
		Assert.assertNull(
				"passing a list in lieu of a scalar must raise a type error",
				parseAndValidate(prefix + "xfail/TypeError1.cal"));
	}

	@Test
	public void xfailCheckTypeError2() throws Exception {
		Assert.assertNull(
				"passing a scalar in lieu of a list must raise a type error",
				parseAndValidate(prefix + "xfail/TypeError2.cal"));
	}

	@Test
	public void xfailCheckTypeError3() throws Exception {
		Assert.assertNull(
				"the size of a type must be a compile-time constant > 0",
				parseAndValidate(prefix + "xfail/TypeError3.cal"));
	}

}
