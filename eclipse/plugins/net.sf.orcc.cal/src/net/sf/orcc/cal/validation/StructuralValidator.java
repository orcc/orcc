/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.cal.CalConstants;
import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstState;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.ExpressionCall;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.Inequality;
import net.sf.orcc.cal.cal.InputPattern;
import net.sf.orcc.cal.cal.OutputPattern;
import net.sf.orcc.cal.cal.Priority;
import net.sf.orcc.cal.cal.RegExp;
import net.sf.orcc.cal.cal.ScheduleFsm;
import net.sf.orcc.cal.cal.StatementCall;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.VariableReference;
import net.sf.orcc.cal.services.Evaluator;
import net.sf.orcc.cal.util.CalActionList;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
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
 * This class describes a validator that performs structural validation of an
 * RVC-CAL actor/unit. The checks tagged as "normal" are only performed when the
 * file is saved and before code generation.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StructuralValidator extends AbstractCalJavaValidator {

	private static final String DEFAULT = "(default)";

	/**
	 * Checks the inputs patterns.
	 * 
	 * @param inputs
	 *            the input patterns of an action
	 */
	private void checkActionInputs(List<InputPattern> inputs) {
		List<AstPort> ports = new ArrayList<AstPort>();

		for (InputPattern pattern : inputs) {
			AstPort port = pattern.getPort();
			if (ports.contains(port)) {
				error("duplicate reference to port " + port.getName(), pattern,
						eINSTANCE.getInputPattern_Port(), -1);
			} else {
				ports.add(port);
			}

			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat != null) {
				int repeat = Evaluator.getIntValue(astRepeat);
				if (repeat <= 0) {
					error("This repeat clause must evaluate to a compile-time "
							+ "constant greater than zero", pattern,
							eINSTANCE.getInputPattern_Repeat(), -1);
				}
			}
		}
	}

	/**
	 * Checks the token expressions are correctly typed.
	 * 
	 * @param outputs
	 *            the output patterns of an action
	 */
	private void checkActionOutputs(List<OutputPattern> outputs) {
		List<AstPort> ports = new ArrayList<AstPort>();

		for (OutputPattern pattern : outputs) {
			AstPort port = pattern.getPort();
			if (ports.contains(port)) {
				error("duplicate reference to port " + port.getName(), pattern,
						eINSTANCE.getOutputPattern_Port(), -1);
			} else {
				ports.add(port);
			}

			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat != null) {
				int repeat = Evaluator.getIntValue(astRepeat);
				if (repeat <= 0) {
					error("This repeat clause must evaluate to a compile-time "
							+ "constant greater than zero", pattern,
							eINSTANCE.getOutputPattern_Repeat(), -1);
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
		List<Variable> variables = actor.getStateVariables();
		for (Variable variable : variables) {
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
		checkActionTag(action);
		checkActionInputs(action.getInputs());
		checkActionOutputs(action.getOutputs());

		checkInnerVarDecls(action, eINSTANCE.getAstAction_Guards());
		checkInnerVarDecls(action, eINSTANCE.getAstAction_Statements());
	}

	@Check(CheckType.NORMAL)
	public void checkAstActor(AstActor actor) {
		// build action list
		CalActionList actionList = new CalActionList();
		actionList.addActions(actor.getActions());

		// check FSM and priorities
		ScheduleFsm schedule = actor.getScheduleFsm();
		if (schedule != null) {
			Set<AstAction> actionSet = new HashSet<AstAction>(
					actor.getActions());
			checkFsm(actionList, schedule, actionSet);

			// shows warnings for tagged actions not referenced in the FSM
			// not in the warning validator because we need checkFsm
			for (AstAction action : actionSet) {
				AstTag tag = action.getTag();
				if (tag != null) {
					warning("Action " + getName(tag) + " is not referenced "
							+ "in the FSM", action,
							CalPackage.eINSTANCE.getAstAction_Tag(), -1);
				}
			}
		}

		RegExp scheduleRegExp = actor.getScheduleRegExp();
		if (scheduleRegExp != null && !actor.getPriorities().isEmpty()) {
			error("Regexp scheduler with priorities.", actor,
					eINSTANCE.getAstActor_ScheduleRegExp(), -1);
		}

		// check priorities
		checkPriorities(actor, actionList);
	}

	@Check(CheckType.FAST)
	public void checkAstEntity(AstEntity entity) {
		checkEntityPackage(entity);
		checkEntityName(entity);
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

	@Check(CheckType.NORMAL)
	public void checkExpressionCall(ExpressionCall call) {
		Function function = call.getFunction();
		String name = function.getName();

		EObject rootCter = EcoreUtil.getRootContainer(call);
		EObject rootCterFunction = EcoreUtil.getRootContainer(function);
		if (function.eContainer() instanceof AstActor
				&& rootCter != rootCterFunction) {
			// calling an actor's function from another actor/unit
			error("function " + name
					+ " cannot be called from another actor/unit", call,
					eINSTANCE.getExpressionCall_Function(), -1);
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
	 * @param actionsSet
	 *            on input the set of all actions; on output the set of actions
	 *            that are not referenced by the FSM
	 */
	private void checkFsm(CalActionList actionList, ScheduleFsm schedule,
			Set<AstAction> actionsSet) {
		// we use a map because the transitions departing from a given state can
		// be scattered throughout the schedule
		Map<AstState, List<AstAction>> stateActionMap = new HashMap<AstState, List<AstAction>>();
		for (AstTransition transition : schedule.getContents().getTransitions()) {
			AstTag tag = transition.getTag();
			if (tag != null) {
				List<AstAction> actions = actionList.getTaggedActions(tag
						.getIdentifiers());
				if (actions == null || actions.isEmpty()) {
					error("tag " + getName(tag)
							+ " does not refer to any action", transition,
							eINSTANCE.getAstTransition_Tag(), -1);
				} else {
					AstState source = transition.getSource();
					List<AstAction> stateActions = stateActionMap.get(source);
					if (stateActions == null) {
						stateActions = new ArrayList<AstAction>(1);
						stateActionMap.put(source, stateActions);
					}

					for (AstAction action : actions) {
						if (stateActions.contains(action)) {
							error(source.getName()
									+ " has more than one transition associated with "
									+ getName(action), transition,
									eINSTANCE.getAstTransition_Tag(), -1);
						} else {
							stateActions.add(action);
							actionsSet.remove(action);
						}
					}
				}
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkFunction(Function function) {
		checkInnerVarDecls(function, eINSTANCE.getFunction_Expression());
	}

	@Check(CheckType.NORMAL)
	public void checkGenerator(Generator generator) {
		int lower = Evaluator.getIntValue(generator.getLower());
		int higher = Evaluator.getIntValue(generator.getHigher());

		if (higher < lower) {
			error("higher bound must be greater than lower bound", generator,
					eINSTANCE.getGenerator_Higher(), -1);
			return;
		}

		Variable variable = EcoreUtil2.getContainerOfType(generator,
				Variable.class);
		if (variable != null) {
			EStructuralFeature feature = variable.eContainingFeature();
			if (feature == CalPackage.eINSTANCE.getAstActor_StateVariables()
					|| feature == CalPackage.eINSTANCE.getAstUnit_Variables()) {
				if (higher - lower > 65536) {
					error("List generated is too large, please use an initialize action",
							generator, eINSTANCE.getGenerator_Variable(), -1);
				}
			}
		}
	}

	private void checkInnerVarDecl(Set<String> names, EObject eObject) {
		for (Variable variable : EcoreHelper
				.getObjects(eObject, Variable.class)) {
			String name = variable.getName();
			if (names.contains(name)) {
				error("Variable " + name
						+ " shadows an existing variable with the same name",
						variable, CalPackage.eINSTANCE.getVariable_Name(), -1);
			}
		}
	}

	private void checkInnerVarDecls(EObject eObject, EStructuralFeature feature) {
		Set<String> names = new HashSet<String>();
		Set<Variable> variables = new HashSet<Variable>();
		for (EObject obj : eObject.eContents()) {
			if (obj instanceof Variable) {
				Variable variable = (Variable) obj;
				variables.add(variable);
				names.add(variable.getName());
			}
		}

		for (Variable variable : variables) {
			AstExpression value = variable.getValue();
			if (value != null) {
				checkInnerVarDecl(names, value);
			}
		}

		Object object = eObject.eGet(feature);
		if (feature.isMany()) {
			for (Object obj : (Iterable<?>) object) {
				if (obj instanceof EObject) {
					checkInnerVarDecl(names, (EObject) obj);
				}
			}
		} else {
			if (object instanceof EObject) {
				checkInnerVarDecl(names, (EObject) object);
			}
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
		List<Priority> priorities = actor.getPriorities();
		DirectedGraph<AstAction, DefaultEdge> graph = new DefaultDirectedGraph<AstAction, DefaultEdge>(
				DefaultEdge.class);

		// add one vertex per tagged action
		for (AstAction action : actionList) {
			AstTag tag = action.getTag();
			if (tag != null) {
				graph.addVertex(action);
			}
		}

		for (Priority priority : priorities) {
			for (Inequality inequality : priority.getInequalities()) {
				// the grammar requires there be at least two tags
				Iterator<AstTag> it = inequality.getTags().iterator();
				AstTag previousTag = it.next();

				List<AstAction> sources = actionList
						.getTaggedActions(previousTag.getIdentifiers());
				int index = 0;
				if (sources == null || sources.isEmpty()) {
					error("tag " + getName(previousTag)
							+ " does not refer to any action", inequality,
							eINSTANCE.getInequality_Tags(), index);
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
								eINSTANCE.getInequality_Tags(), index);
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

	@Check(CheckType.NORMAL)
	public void checkProcedure(AstProcedure procedure) {
		checkInnerVarDecls(procedure, eINSTANCE.getAstProcedure_Statements());
	}

	@Check(CheckType.NORMAL)
	public void checkStatementCall(StatementCall call) {
		AstProcedure procedure = call.getProcedure();
		String name = procedure.getName();

		EObject rootCter = EcoreUtil.getRootContainer(call);
		EObject rootCterProcedure = EcoreUtil.getRootContainer(procedure);
		if (procedure.eContainer() instanceof AstActor
				&& rootCter != rootCterProcedure) {
			// calling an actor's procedure from another actor/unit
			error("procedure " + name
					+ " cannot be called from another actor/unit", call,
					eINSTANCE.getStatementCall_Procedure(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkVariable(Variable variable) {
		if (variable.isConstant() && variable.getValue() == null) {
			String name = variable.getName();
			error("The constant " + name + " must have a value", variable,
					eINSTANCE.getVariable_Name(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkVariableReference(VariableReference ref) {
		Variable variable = ref.getVariable();
		String name = variable.getName();

		EObject rootCter = EcoreUtil.getRootContainer(ref);
		EObject rootCter2 = EcoreUtil.getRootContainer(variable);
		if (variable.eContainer() instanceof AstActor && rootCter != rootCter2) {
			// referencing an actor's variable from another actor/unit
			error("variable " + name + " can only be referenced "
					+ "within the actor in which it is declared", ref,
					eINSTANCE.getVariableReference_Variable(), -1);
		}

		Variable target = EcoreUtil2.getContainerOfType(ref, Variable.class);
		if (target != null) {
			EStructuralFeature feature = target.eContainingFeature();
			if (feature == CalPackage.eINSTANCE.getAstActor_StateVariables()
					|| feature == CalPackage.eINSTANCE.getAstUnit_Variables()) {
				if (variable.getValue() == null
						&& !(variable.eContainer() instanceof Generator)) {
					error("Cannot use the variable " + name + " in this "
							+ "context because it has no initial value", ref,
							eINSTANCE.getVariableReference_Variable(), -1);
				}
			}
		}
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
