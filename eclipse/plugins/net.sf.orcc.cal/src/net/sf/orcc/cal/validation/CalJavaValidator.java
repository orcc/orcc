/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.validation;

import static net.sf.orcc.cal.cal.CalPackage.eINSTANCE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.orcc.cal.CalConstants;
import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionIndex;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstInequality;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstOutputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstPriority;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstSchedule;
import net.sf.orcc.cal.cal.AstScheduleRegExp;
import net.sf.orcc.cal.cal.AstStatementAssign;
import net.sf.orcc.cal.cal.AstStatementCall;
import net.sf.orcc.cal.cal.AstStatementElsif;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstStatementIf;
import net.sf.orcc.cal.cal.AstStatementWhile;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.AstVariableReference;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.type.TypeChecker;
import net.sf.orcc.cal.type.TypeCycleDetector;
import net.sf.orcc.cal.util.BooleanSwitch;
import net.sf.orcc.cal.util.CalActionList;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.util.TypeUtil;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * This class describes the validation of an RVC-CAL actor. The checks tagged as
 * "expensive" are only performed when the file is saved and before code
 * generation.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalJavaValidator extends AbstractCalJavaValidator {

	private static final String DEFAULT = "(default)";

	/**
	 * Creates a new CAL validator written in Java.
	 */
	public CalJavaValidator() {
	}

	private void checkActionGuards(AstAction action) {
		List<AstExpression> guards = action.getGuards();
		int index = 0;
		for (AstExpression guard : guards) {
			Type type = new TypeChecker(this).getType(guard);
			if (type == null || !type.isBool()) {
				error("Type mismatch: cannot convert from " + type + " to bool",
						action, eINSTANCE.getAstAction_Guards(), index);
			}
			index++;
		}
	}

	/**
	 * Checks the inputs patterns.
	 * 
	 * @param inputs
	 *            the input patterns of an action
	 */
	private void checkActionInputs(List<AstInputPattern> inputs) {
		List<AstPort> ports = new ArrayList<AstPort>();

		for (AstInputPattern pattern : inputs) {
			AstPort port = pattern.getPort();
			if (ports.contains(port)) {
				error("duplicate reference to port " + port.getName(), pattern,
						eINSTANCE.getAstInputPattern_Port(), -1);
			} else {
				ports.add(port);
			}
		}
	}

	/**
	 * Checks the token expressions are correctly typed.
	 * 
	 * @param outputs
	 *            the output patterns of an action
	 */
	private void checkActionOutputs(List<AstOutputPattern> outputs) {
		TypeChecker checker = new TypeChecker(this);
		List<AstPort> ports = new ArrayList<AstPort>();

		for (AstOutputPattern pattern : outputs) {
			AstPort port = pattern.getPort();
			if (ports.contains(port)) {
				error("duplicate reference to port " + port.getName(), pattern,
						eINSTANCE.getAstOutputPattern_Port(), -1);
			} else {
				ports.add(port);
			}

			Type portType = Util.getType(port);
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat == null) {
				List<AstExpression> values = pattern.getValues();
				int index = 0;
				for (AstExpression value : values) {
					Type type = checker.getType(value);
					if (!TypeUtil.isConvertibleTo(type, portType)) {
						error("this expression must be of type " + portType,
								pattern,
								eINSTANCE.getAstOutputPattern_Values(), index);
					}
					index++;
				}
			} else {
				int repeat = Util.getIntValue(astRepeat);
				if (repeat != 1) {
					// each value is supposed to be a list
					List<AstExpression> values = pattern.getValues();
					int index = 0;
					for (AstExpression value : values) {
						Type type = checker.getType(value);
						if (type.isList()) {
							TypeList typeList = (TypeList) type;
							Type lub = TypeUtil.getLub(portType,
									typeList.getType());
							if (lub != null && typeList.getSize() >= repeat) {
								continue;
							}
						}

						error("Type mismatch: expected " + portType + "["
								+ repeat + "]", pattern,
								eINSTANCE.getAstOutputPattern_Values(), index);
						index++;
					}
				}
			}
		}
	}

	/**
	 * Check that the action tag is different from port and state variable
	 * names.
	 * 
	 * @param action
	 *            the action
	 */
	private void checkActionTag(AstAction action) {
		AstActor actor = EcoreUtil2.getContainerOfType(action, AstActor.class);

		String name = getName(action);

		// Check if tag name is not already used in a state variable
		List<AstVariable> variables = actor.getStateVariables();
		for (AstVariable variable : variables) {
			if (name.equals(variable.getName())) {
				error("Action " + name
						+ " has the same name as a state variable",
						eINSTANCE.getAstAction_Tag());
			}
		}

		// Check if tag name is not already used in an input port
		List<AstPort> inputs = actor.getInputs();
		for (AstPort input : inputs) {
			if (name.equals(input.getName())) {
				error("Action " + name + " has the same name as an input port",
						eINSTANCE.getAstAction_Tag());
			}
		}

		// Check if tag name is not already used in an output port
		List<AstPort> outputs = actor.getOutputs();
		for (AstPort output : outputs) {
			if (name.equals(output.getName())) {
				error("Action " + name + " has the same name as an output port",
						eINSTANCE.getAstAction_Tag());
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstAction(AstAction action) {
		checkActionGuards(action);
		checkActionTag(action);
		checkActionInputs(action.getInputs());
		checkActionOutputs(action.getOutputs());
	}

	@Check(CheckType.NORMAL)
	public void checkAstActor(AstActor actor) {
		// build action list
		CalActionList actionList = new CalActionList();
		actionList.addActions(actor.getActions());

		// check FSM and priorities
		AstSchedule schedule = actor.getSchedule();
		if (schedule != null) {
			checkFsm(actionList, schedule);
		}
		AstScheduleRegExp scheduleRegExp = actor.getScheduleRegExp();
		if (scheduleRegExp != null && !actor.getPriorities().isEmpty()) {
			error("Regexp scheduler with priorities.", actor,
					eINSTANCE.getAstActor_ScheduleRegExp(), -1);
		}

		checkPriorities(actor, actionList);
	}

	@Check(CheckType.NORMAL)
	public void checkAstEntity(AstEntity entity) {
		checkEntityPackage(entity);
		checkEntityName(entity);

		// check there are no cycles in type definitions
		if (!new TypeCycleDetector(this).detectCycles(entity)) {
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstExpressionCall(AstExpressionCall astCall) {
		AstFunction function = astCall.getFunction();
		String name = function.getName();

		EObject rootCter = EcoreUtil.getRootContainer(astCall);
		EObject rootCterFunction = EcoreUtil.getRootContainer(function);
		if (function.eContainer() instanceof AstActor
				&& rootCter != rootCterFunction) {
			// calling an actor's function from another actor/unit
			error("function " + name
					+ " cannot be called from another actor/unit", astCall,
					eINSTANCE.getAstExpressionCall_Function(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstFunction(final AstFunction function) {
		if (!function.isNative()) {
			checkReturnType(function);
		}

		// do not check functions of a unit
		if (function.eContainer() instanceof AstUnit) {
			return;
		}

		boolean used = new BooleanSwitch() {

			@Override
			public Boolean caseAstExpressionCall(AstExpressionCall expression) {
				if (expression.getFunction().equals(function)) {
					return true;
				}

				return super.caseAstExpressionCall(expression);
			}

		}.doSwitch(Util.getTopLevelContainer(function));

		if (!used && !function.isNative()) {
			warning("The function " + function.getName() + " is never called",
					eINSTANCE.getAstFunction_Name());
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstGenerator(AstGenerator generator) {
		int lower = Util.getIntValue(generator.getLower());
		int higher = Util.getIntValue(generator.getHigher());

		if (higher < lower) {
			error("higher bound must be greater than lower bound", generator,
					eINSTANCE.getAstGenerator_Higher(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstProcedure(final AstProcedure procedure) {
		boolean used = new BooleanSwitch() {

			@Override
			public Boolean caseAstStatementCall(AstStatementCall call) {
				if (call.getProcedure().equals(procedure)) {
					return true;
				}

				return false;
			}

		}.doSwitch(Util.getTopLevelContainer(procedure));

		if (!used && procedure.eContainer() instanceof AstActor
				&& !procedure.isNative()) {
			warning("The procedure " + procedure.getName() + " is never called",
					eINSTANCE.getAstProcedure_Name());
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstStatementAssign(AstStatementAssign assign) {
		AstVariable variable = assign.getTarget().getVariable();
		if (variable.isConstant()
				|| variable.eContainingFeature() == CalPackage.Literals.AST_ACTOR__PARAMETERS) {
			error("The variable " + variable.getName() + " is not assignable",
					eINSTANCE.getAstStatementAssign_Target());
		}

		// create expression
		AstExpressionIndex expression = CalFactory.eINSTANCE
				.createAstExpressionIndex();

		// set reference
		AstVariableReference reference = CalFactory.eINSTANCE
				.createAstVariableReference();
		reference.setVariable(variable);
		expression.setSource(reference);

		// copy indexes
		expression.getIndexes().addAll(EcoreUtil.copyAll(assign.getIndexes()));

		// check types
		TypeChecker checker = new TypeChecker(this);
		Type targetType = checker.getType(expression);
		Type type = checker.getType(assign.getValue());
		if (!TypeUtil.isConvertibleTo(type, targetType)) {
			error("Type mismatch: cannot convert from " + type + " to "
					+ targetType, assign,
					eINSTANCE.getAstStatementAssign_Value(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstStatementCall(AstStatementCall astCall) {
		AstProcedure procedure = astCall.getProcedure();
		String name = procedure.getName();
		List<AstExpression> parameters = astCall.getParameters();

		if (procedure.eContainer() == null) {
			if ("print".equals(name) || "println".equals(name)) {
				if (parameters.size() > 1) {
					error("built-in procedure " + name
							+ " takes at most one expression", astCall,
							eINSTANCE.getAstStatementCall_Procedure(), -1);
				}
			}

			return;
		}

		EObject rootCter = EcoreUtil.getRootContainer(astCall);
		EObject rootCterProcedure = EcoreUtil.getRootContainer(procedure);
		if (procedure.eContainer() instanceof AstActor
				&& rootCter != rootCterProcedure) {
			// calling an actor's procedure from another actor/unit
			error("procedure " + name
					+ " cannot be called from another actor/unit", astCall,
					eINSTANCE.getAstStatementCall_Procedure(), -1);
		}

		if (procedure.getParameters().size() != parameters.size()) {
			error("procedure " + name + " takes "
					+ procedure.getParameters().size() + " arguments.",
					astCall, eINSTANCE.getAstStatementCall_Procedure(), -1);
			return;
		}

		TypeChecker checker = new TypeChecker(this);
		Iterator<AstVariable> itFormal = procedure.getParameters().iterator();
		Iterator<AstExpression> itActual = parameters.iterator();
		int index = 0;
		while (itFormal.hasNext() && itActual.hasNext()) {
			Type formalType = Util.getType(itFormal.next());
			AstExpression expression = itActual.next();
			Type actualType = checker.getType(expression);

			// check types
			if (!TypeUtil.isConvertibleTo(actualType, formalType)) {
				error("Type mismatch: cannot convert from " + actualType
						+ " to " + formalType, astCall,
						eINSTANCE.getAstStatementCall_Parameters(), index);
			}
			index++;
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstStatementElsif(AstStatementElsif elsIf) {
		Type type = new TypeChecker(this).getType(elsIf.getCondition());
		if (type == null || !type.isBool()) {
			error("Type mismatch: cannot convert from " + type + " to bool",
					elsIf, eINSTANCE.getAstStatementElsif_Condition(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstStatementIf(AstStatementIf astIf) {
		Type type = new TypeChecker(this).getType(astIf.getCondition());
		if (type == null || !type.isBool()) {
			error("Type mismatch: cannot convert from " + type + " to bool",
					astIf, eINSTANCE.getAstStatementIf_Condition(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstStatementWhile(AstStatementWhile astWhile) {
		Type type = new TypeChecker(this).getType(astWhile.getCondition());
		if (type == null || !type.isBool()) {
			error("Type mismatch: cannot convert from " + type + " to bool",
					astWhile, eINSTANCE.getAstStatementWhile_Condition(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstUnit(AstUnit unit) {
		// check unique names
	}

	@Check(CheckType.NORMAL)
	public void checkAstVariable(AstVariable variable) {
		checkIsVariableUsed(variable);
		AstExpression value = variable.getValue();
		TypeChecker checker = new TypeChecker(this);
		if (value != null) {
			// check types
			Type targetType = Util.getType(variable);
			Type type = checker.getType(value);
			if (!TypeUtil.isConvertibleTo(type, targetType)) {
				error("Type mismatch: cannot convert from " + type + " to "
						+ targetType, variable,
						eINSTANCE.getAstVariable_Value(), -1);
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstVariableReference(AstVariableReference ref) {
		AstVariable variable = ref.getVariable();
		String name = variable.getName();

		EObject rootCter = EcoreUtil.getRootContainer(ref);
		EObject rootCter2 = EcoreUtil.getRootContainer(variable);
		if (variable.eContainer() instanceof AstActor && rootCter != rootCter2) {
			// referencing an actor's variable from another actor/unit
			error("variable " + name + " can only be referenced "
					+ "within the actor in which it is declared", ref,
					eINSTANCE.getAstVariableReference_Variable(), -1);
		}
	}

	/**
	 * Checks the name of the given entity.
	 * 
	 * @param entity
	 *            the entity
	 */
	private void checkEntityName(AstEntity entity) {
		// check entity name matches file name
		String path = entity.eResource().getURI().path();
		String expectedName = new Path(path).removeFileExtension()
				.lastSegment();

		String entityName = entity.getName();
		if (!expectedName.equals(entityName)) {
			error("The qualified name " + entityName
					+ " does not match the expected name " + expectedName,
					entity, eINSTANCE.getAstEntity_Name(),
					CalConstants.ERROR_NAME, entityName, expectedName);
		}
	}

	/**
	 * Checks the package of the given entity.
	 * 
	 * @param entity
	 *            the entity
	 */
	private void checkEntityPackage(AstEntity entity) {
		// get platform path /project/folder/.../file
		String platformPath = entity.eResource().getURI()
				.toPlatformString(true);
		if (platformPath == null) {
			return;
		}

		// get the file (we know it's a file)
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = (IFile) workspace.getRoot().findMember(platformPath);
		if (file == null) {
			return;
		}

		// get the associated Java project (if any)
		IProject project = file.getProject();
		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return;
		}

		// get segments
		String[] segments;
		String packageName = entity.getPackage();
		if (packageName == null) {
			packageName = DEFAULT;
			segments = new String[0];
		} else {
			segments = packageName.split("\\.");
		}

		try {
			IPackageFragmentRoot[] roots = javaProject
					.getAllPackageFragmentRoots();
			for (IPackageFragmentRoot root : roots) {
				IPath rootPath = root.getPath();
				if (!rootPath.isPrefixOf(file.getFullPath())) {
					continue;
				}

				IPath path = rootPath;
				for (String segment : segments) {
					path = path.append(segment);
				}

				IResource res = workspace.getRoot().findMember(path);
				if (res == null || !file.getParent().equals(res)) {
					String expectedName = getExpectedName(rootPath,
							file.getFullPath());
					String code;
					if (packageName == DEFAULT) {
						code = CalConstants.ERROR_MISSING_PACKAGE;
					} else if (expectedName == DEFAULT) {
						code = CalConstants.ERROR_EXTRANEOUS_PACKAGE;
					} else {
						code = CalConstants.ERROR_PACKAGE;
					}

					error("The package " + packageName
							+ " does not match the expected package "
							+ expectedName, entity,
							eINSTANCE.getAstEntity_Package(), code,
							packageName, expectedName);
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks the given FSM using the given action list. This check is not
	 * annotated because we need to build the action list, which is also useful
	 * for checking the priorities, and we do not want to build that twice.
	 * 
	 * @param actionList
	 *            the action list of the actor
	 * @param schedule
	 *            the FSM of the actor
	 */
	private void checkFsm(CalActionList actionList, AstSchedule schedule) {
		for (AstTransition transition : schedule.getTransitions()) {
			AstTag tag = transition.getTag();
			if (tag != null) {
				List<AstAction> actions = actionList.getTaggedActions(tag
						.getIdentifiers());
				if (actions == null || actions.isEmpty()) {
					error("tag " + getName(tag)
							+ " does not refer to any action", transition,
							eINSTANCE.getAstTransition_Tag(), -1);
				}
			}
		}
	}

	/**
	 * Checks that the given variable is used. If it is not, issue a warning.
	 * 
	 * @param variable
	 *            a variable
	 */
	private void checkIsVariableUsed(final AstVariable variable) {
		// do not take variables declared by input patterns and
		// generator/foreach
		EObject container = variable.eContainer();
		if (container instanceof AstInputPattern
				|| container instanceof AstGenerator
				|| container instanceof AstStatementForeach
				|| container instanceof AstUnit) {
			return;
		}

		boolean used = new BooleanSwitch() {

			@Override
			public Boolean caseAstExpressionIndex(AstExpressionIndex expression) {
				if (expression.getSource().getVariable().equals(variable)) {
					return true;
				}

				return super.caseAstExpressionIndex(expression);
			}

			@Override
			public Boolean caseAstStatementAssign(AstStatementAssign assign) {
				if (assign.getTarget().getVariable().equals(variable)) {
					return true;
				}

				return super.caseAstStatementAssign(assign);
			}

			@Override
			public Boolean caseAstVariableReference(AstVariableReference ref) {
				return ref.getVariable().equals(variable);
			}

		}.doSwitch(Util.getTopLevelContainer(variable));

		// do not warn about unused actor parameters
		// used for system actors
		EReference reference = variable.eContainmentFeature();
		if (!used) {
			if (reference == CalPackage.eINSTANCE.getAstActor_Parameters()) {
				return;
			}

			if (variable.eContainer() instanceof AstFunction) {
				AstFunction function = (AstFunction) variable.eContainer();
				if (function.isNative()) {
					return;
				}
			}

			if (variable.eContainer() instanceof AstProcedure) {
				AstProcedure procedure = (AstProcedure) variable.eContainer();
				if (procedure.isNative()) {
					return;
				}
			}

			warning("The variable " + variable.getName() + " is never read",
					eINSTANCE.getAstVariable_Name());
		}
	}

	/**
	 * Checks the priorities of the given actor using the given action list.
	 * This check is not annotated because we need to build the action list,
	 * which is also useful for checking the FSM, and we do not want to build
	 * that twice.
	 * 
	 * @param actor
	 *            the actor
	 * @param actionList
	 *            the action list of the actor
	 */
	private void checkPriorities(AstActor actor, CalActionList actionList) {
		List<AstPriority> priorities = actor.getPriorities();
		DirectedGraph<AstAction, DefaultEdge> graph = new DefaultDirectedGraph<AstAction, DefaultEdge>(
				DefaultEdge.class);

		// add one vertex per tagged action
		for (AstAction action : actionList) {
			AstTag tag = action.getTag();
			if (tag != null) {
				graph.addVertex(action);
			}
		}

		for (AstPriority priority : priorities) {
			for (AstInequality inequality : priority.getInequalities()) {
				// the grammar requires there be at least two tags
				Iterator<AstTag> it = inequality.getTags().iterator();
				AstTag previousTag = it.next();

				List<AstAction> sources = actionList
						.getTaggedActions(previousTag.getIdentifiers());
				int index = 0;
				if (sources == null || sources.isEmpty()) {
					error("tag " + getName(previousTag)
							+ " does not refer to any action", inequality,
							eINSTANCE.getAstInequality_Tags(), index);
				}

				while (it.hasNext()) {
					AstTag tag = it.next();
					index++;
					sources = actionList.getTaggedActions(previousTag
							.getIdentifiers());
					List<AstAction> targets = actionList.getTaggedActions(tag
							.getIdentifiers());

					if (targets == null || targets.isEmpty()) {
						error("tag " + getName(tag)
								+ " does not refer to any action", inequality,
								eINSTANCE.getAstInequality_Tags(), index);
					}

					if (sources != null && targets != null) {
						for (AstAction source : sources) {
							for (AstAction target : targets) {
								graph.addEdge(source, target);
							}
						}
					}

					previousTag = tag;
				}
			}
		}

		CycleDetector<AstAction, DefaultEdge> cycleDetector = new CycleDetector<AstAction, DefaultEdge>(
				graph);
		Set<AstAction> cycle = cycleDetector.findCycles();
		if (!cycle.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for (AstAction action : cycle) {
				builder.append(getName(action.getTag()));
				builder.append(", ");
			}

			Iterator<AstAction> it = cycle.iterator();
			builder.append(getName(it.next().getTag()));

			error("priorities of actor "
					+ ((AstEntity) actor.eContainer()).getName()
					+ " contain a cycle: " + builder.toString(), actor,
					eINSTANCE.getAstActor_Priorities(), -1);
		}
	}

	private void checkReturnType(AstFunction function) {
		Type returnType = Util.getType(function);
		Type expressionType = Util.getType(function.getExpression());
		if (!TypeUtil.isConvertibleTo(expressionType, returnType)) {
			error("Type mismatch: cannot convert from " + expressionType
					+ " to " + returnType, function,
					eINSTANCE.getAstFunction_Expression(), -1);
		}
	}

	@Override
	public void error(String string, EObject source,
			EStructuralFeature feature, int index) {
		super.error(string, source, feature, index);
	}

	private String getExpectedName(IPath rootPath, IPath filePath) {
		int count = rootPath.matchingFirstSegments(filePath);
		String[] segments = filePath.removeFirstSegments(count)
				.removeLastSegments(1).segments();
		if (segments.length == 0) {
			return DEFAULT;
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append(segments[0]);
			for (int i = 1; i < segments.length; i++) {
				builder.append('.');
				builder.append(segments[i]);
			}

			return builder.toString();
		}
	}

	private String getName(AstAction action) {
		AstTag tag = action.getTag();
		if (tag == null) {
			return "(untagged)";
		} else {
			return getName(tag);
		}
	}

	private String getName(AstTag tag) {
		return OrccUtil.toString(tag.getIdentifiers(), ".");
	}

}
