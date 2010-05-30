/*
 * Copyright (c) 2009, Samuel Keller EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package net.sf.orcc.backends.xlim;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.xlim.templates.XlimAttributeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimElementTemplate;
import net.sf.orcc.backends.xlim.templates.XlimModuleTemplate;
import net.sf.orcc.backends.xlim.templates.XlimNodeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimOperationTemplate;
import net.sf.orcc.backends.xlim.templates.XlimTypeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimValueTemplate;
import net.sf.orcc.interpreter.InterpretedActor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.util.DomUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * XlimActorPrinter manages transformation of an Actor
 * 
 * @author Samuel Keller
 */
public class XlimActorPrinter implements XlimTypeTemplate, XlimModuleTemplate,
		XlimOperationTemplate, XlimAttributeTemplate, XlimValueTemplate,
		XlimElementTemplate {

	/**
	 * Fire count
	 */
	private static int fcount = 0;

	/**
	 * Index count
	 */
	private static int icount = 0;

	/**
	 * Outval count
	 */
	private static int ocount = 0;

	/**
	 * XLIM naming
	 */
	private XlimNames names;

	/**
	 * Root Element
	 */
	private Element root;

	/**
	 * Base Document
	 */
	private Document xlim;

	/**
	 * XlimActorPrinter default constructor
	 */
	public XlimActorPrinter() {
		names = new XlimNames();
	}

	/**
	 * Add one action
	 * 
	 * @param action
	 *            Action to add
	 */
	private void addAction(Action action) {
		String actionName = action.getName();

		Element actionE = XlimNodeTemplate.newModule(root, ACTION, FALSE,
				actionName);

		// action.getOutputPattern();

		XlimNodeVisitor visitor = new XlimNodeVisitor(names, actionE,
				actionName);
		for (CFGNode nodes : action.getBody().getNodes()) {
			nodes.accept(visitor);
		}
	}

	/**
	 * Add all action of an Actor
	 * 
	 * @param actor
	 *            Actor where to take the action
	 */
	private void addActions(Actor actor) {
		for (Action action : actor.getActions()) {
			for (Port oport : action.getOutputPattern().keySet()) {
				names.getVarName(oport, action.getName());
			}
			addAction(action);
		}
	}

	/**
	 * Add scheduler conditions for an action
	 * 
	 * @param action
	 *            Action to analyze
	 * @param body
	 *            Element where to add the conditions
	 */
	private void addConditions(Action action, Element body) {

		// Inputs

		Element guardE = XlimNodeTemplate.newDiffOperation(body, AND);
		String name = action.getName();

		Pattern input = action.getInputPattern();
		for (Entry<Port, Integer> entry : input.entrySet()) {
			String index = "index" + (icount++);
			Port port = entry.getKey();
			String portname = port.getName();

			Element statusE = XlimNodeTemplate.newPortOperation(body,
					PINSTATUS, portname);

			String ready = "ready_" + index;

			XlimNodeTemplate.newOutPort(statusE, ready, "1", BOOL);
			XlimNodeTemplate.newInPort(guardE, ready);
		}

		List<CFGNode> init = action.getScheduler().getNodes();

		if ((init != null) && (init.get(init.size() - 1) instanceof IfNode)) {
			init = ((IfNode) init.get(init.size() - 1)).getThenNodes();
			if ((init != null) && (init.size() > 0)) {
				XlimInstructionVisitor iv = new XlimInstructionVisitor(names,
						body, "_scheduler_" + action.getName(),
						new Vector<String>(), new TreeMap<String, Element>());
				Iterator<Instruction> it = ((BlockNode) init.get(0))
						.getInstructions().iterator();

				while (it.hasNext()) {
					Instruction instruction = it.next();
					if (it.hasNext()) {
						instruction.accept(iv);
					} else {
						if (instruction instanceof Assign) {
							String index = "index" + (icount++);
							String ready = "ready_" + index;
							Assign assign = ((Assign) instruction);

							assign.getValue().accept(
									new XlimExprVisitor(names, body,
											"_scheduler_" + action.getName()));
							Element operationE = XlimNodeTemplate.newOperation(
									body, NOOP);
							XlimNodeTemplate.newInPort(operationE,
									names.getTempName());
							XlimNodeTemplate.newOutPort(operationE, ready,
									assign.getTarget().getType());
							XlimNodeTemplate.newInPort(guardE, ready);
						}
					}
				}
			}
		}

		// Default true
		Element operationE1 = XlimNodeTemplate.newValueOperation(body, LITINT,
				"1");
		XlimNodeTemplate
				.newOutPort(operationE1, names.putTempName(), "1", BOOL);

		XlimNodeTemplate.newInPort(guardE, names.getTempName());

		XlimNodeTemplate.newOutPort(guardE, "guard_" + name, "1", BOOL);

		body.appendChild(guardE);

		// Outputs

		Element fireE = XlimNodeTemplate.newDiffOperation(body, AND);

		Pattern output = action.getOutputPattern();
		for (Entry<Port, Integer> entry : output.entrySet()) {
			String index = "index" + (icount++);

			Port port = entry.getKey();
			String portname = port.getName();

			Element statusE = XlimNodeTemplate.newPortOperation(body,
					PINSTATUS, portname);

			String status = "status_" + index;

			XlimNodeTemplate.newOutPort(statusE, status, "1", BOOL);
			XlimNodeTemplate.newInPort(fireE, status);
		}

		// Default true
		Element operationE2 = XlimNodeTemplate.newValueOperation(body, LITINT,
				"1");
		XlimNodeTemplate
				.newOutPort(operationE2, names.putTempName(), "1", BOOL);

		XlimNodeTemplate.newInPort(fireE, names.getTempName());

		XlimNodeTemplate.newOutPort(fireE, "fire_" + name, "1", BOOL);

		body.appendChild(fireE);
	}

	/**
	 * Add lower priority transitions
	 * 
	 * @param transition
	 *            Transition to add
	 * @param nextState
	 *            Next state
	 * @param base
	 *            Base where to add the transition
	 * @return Created else container
	 */
	private Element addElseTransition(Transition transition,
			NextStateInfo nextState, Element base) {
		Element testE = addTransition(transition, nextState, base);
		return XlimNodeTemplate.newModule(testE, ELSE);
	}

	/**
	 * Add FSM
	 * 
	 * @param fsm
	 *            FSM to analyze
	 * @param root
	 *            Root where to add the transition
	 */
	private void addFSM(FSM fsm, Element root) {
		Element mutexE = XlimNodeTemplate.newModule(root, BLOCK);
		mutexE.setAttribute(MUTEX, TRUE);

		for (Transition transition : fsm.getTransitions()) {
			Element checkstate = createCheckState(transition, mutexE);

			Iterator<NextStateInfo> it = transition.getNextStateInfo()
					.iterator();
			NextStateInfo state = it.next();
			if (state == null) {
				continue;
			}
			while (it.hasNext()) {
				checkstate = addElseTransition(transition, state, checkstate);
				state = it.next();
			}
			addTransition(transition, state, checkstate);
		}
	}

	/**
	 * Add FSM status copies
	 * 
	 * @param actor
	 *            Actor to analyze
	 * @param root
	 *            Root where to add the transition
	 */
	private void addFSMStatusCopy(Actor actor, Element root) {
		FSM fsm = actor.getActionScheduler().getFsm();

		for (String state : fsm.getStates()) {
			String fsmname = "fsm" + state;
			Element operationE = XlimNodeTemplate.newOperation(root, NOOP);
			XlimNodeTemplate.newInPort(operationE, fsmname);
			XlimNodeTemplate.newOutPort(operationE, fsmname + "_copy", "1",
					BOOL);
		}
	}

	/**
	 * Add one Port
	 * 
	 * @param port
	 *            Port to add
	 * @param dir
	 *            Direction of the port
	 */
	private void addPort(Port port, String dir) {
		Element newPort = XlimNodeTemplate.newActorPort(root, dir,
				port.getName());
		port.getType().accept(new XlimTypeSizeVisitor(newPort));
	}

	/**
	 * Add all Ports
	 * 
	 * @param actor
	 *            Actor to analyze
	 */
	private void addPorts(Actor actor) {
		for (Port input : actor.getInputs()) {
			addPort(input, IN);
		}
		for (Port output : actor.getOutputs()) {
			addPort(output, OUT);
		}
	}

	/**
	 * Add the scheduling of an action
	 * 
	 * @param action
	 *            Action to schedule
	 * @param initialguard
	 *            Action guard condition
	 * @param finalfire
	 *            Action fire condition
	 * @param base
	 *            Base where to add the scheduling
	 * @return the if and the then nodes
	 */
	private Element[] addScheduler(Action action, String initialguard,
			String finalfire, Element base) {
		String actionname = action.getName();
		String guard = "guard_" + actionname;
		String fire = "fire_" + actionname;

		Element[] result = new Element[2];

		Element if1E = XlimNodeTemplate.newModule(base, IF);

		Element dguardE = XlimNodeTemplate.newTestModule(if1E, initialguard);

		Element oguardE = XlimNodeTemplate.newOperation(dguardE, NOOP);

		XlimNodeTemplate.newInPort(oguardE, guard);

		XlimNodeTemplate.newOutPort(oguardE, initialguard, "1", BOOL);

		Element then1E = XlimNodeTemplate.newModule(if1E, THEN);

		Element if2E = XlimNodeTemplate.newModule(then1E, IF);

		Element dfireE = XlimNodeTemplate.newTestModule(if2E, finalfire);

		Element ofireE = XlimNodeTemplate.newOperation(dfireE, NOOP);

		XlimNodeTemplate.newInPort(ofireE, fire);

		XlimNodeTemplate.newOutPort(ofireE, finalfire, "1", BOOL);

		Element then2E = XlimNodeTemplate.newModule(if2E, THEN);

		XlimNodeTemplate.newTargetOperation(then2E, TASKCALL, actionname);

		result[0] = if1E;
		result[1] = then2E;

		return result;
	}

	/**
	 * Add the whole scheduling
	 * 
	 * @param scheduler
	 *            Scheduling to analyze
	 * @param root
	 *            Root where to add the scheduling
	 */
	private void addScheduler(ActionScheduler scheduler, Element root) {
		for (Action action : scheduler.getActions()) {
			Element[] sched = addScheduler(action, names.putDecision(),
					names.putDecision(), root);
			Element ifE = sched[0];
			root = XlimNodeTemplate.newModule(ifE, ELSE);
		}
		if (scheduler.hasFsm()) {
			addFSM(scheduler.getFsm(), root);
		}
	}

	/**
	 * Add Scheduling
	 * 
	 * @param actor
	 *            Actor to analyze
	 */
	private void addScheduler(Actor actor) {

		FSM fsm = actor.getActionScheduler().getFsm();

		if (fsm != null) {
			String start = fsm.getInitialState().getName();

			for (String state : fsm.getStates()) {
				String fsmname = "fsm" + state;

				Element fsmState = XlimNodeTemplate.newStateVar(root, fsmname);

				XlimNodeTemplate.newInitValue(fsmState, "1", BOOL,
						start.equals(state) ? "1" : "0");
			}
		}

		Element schedE = XlimNodeTemplate.newModule(root, "action-scheduler",
				TRUE, "action-scheduler", "action-scheduler");

		Element operationE = XlimNodeTemplate.newValueOperation(schedE, LITINT,
				"1");
		XlimNodeTemplate.newOutPort(operationE, "var_sched", "1", BOOL);

		Element moduleE = XlimNodeTemplate.newModule(schedE, LOOP);
		Element moduledE = XlimNodeTemplate.newTestModule(moduleE, "var_loop");

		Element operationdE = XlimNodeTemplate.newOperation(moduledE, NOOP);

		XlimNodeTemplate.newInPort(operationdE, "var_sched");
		XlimNodeTemplate.newOutPort(operationdE, "var_loop", "1", BOOL);

		Element modulebE = XlimNodeTemplate.newModule(moduleE, BODY);

		addScheduler(actor, modulebE);
	}

	/**
	 * Add the whole scheduling process
	 * 
	 * @param actor
	 *            Actor to analyze
	 * @param body
	 *            Body where to add the scheduling
	 */
	private void addScheduler(Actor actor, Element body) {
		for (Action action : actor.getActions()) {
			addConditions(action, body);
		}
		if (actor.getActionScheduler().hasFsm()) {
			addFSMStatusCopy(actor, body);
		}
		addScheduler(actor.getActionScheduler(), body);
	}

	/**
	 * Add State Variables Declarations
	 * 
	 * @param actor
	 *            Actor to analyze
	 */
	private void addStateVars(Actor actor) {
		// removes phi assignments on the initialize actions
		PhiRemoval phiRemoval = new PhiRemoval();
		for (Action action : actor.getInitializes()) {
			phiRemoval.visitProcedure(action.getBody());
		}

		// initializes the actor
		// TODO : must get parameters map from instance for actors parameters
		// initialization
		InterpretedActor interpreted = new InterpretedActor(actor.getName(),
				null, actor, null, null);
		try {
			interpreted.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Prints all stateVars
		for (Variable stateVar : actor.getStateVars()) {
			if (stateVar.isUsed()) {

				String sourceName = stateVar.getName();
				Element newState = XlimNodeTemplate.newStateVar(root,
						names.getVarName(stateVar, ""), sourceName);

				StateVariable state = (StateVariable) stateVar;
				/*
				 * Element init = XlimNodeTemplate.newInitValue(newState,
				 * "Let");
				 * 
				 * Element init2 = XlimNodeTemplate.newInitValue(init);
				 */
				Element init2 = XlimNodeTemplate.newInitValue(newState);

				// For lists use result of initialize execution (initializes
				// loops case)
				if (stateVar.getType().isList()) {
					state.getType().accept(new XlimTypeSizeVisitor(init2));

					Object[] value = (Object[]) stateVar.getValue();
					for (Object obj : value) {
						if (obj != null) {
							Element el = XlimNodeTemplate.newInitValue(init2);
							el.setAttribute(VALUE, obj.toString());
							((ListType) state.getType()).getElementType()
									.accept(new XlimTypeSizeVisitor(el));
						}
					}
					if (value[0] == null) {
						init2.setAttribute(VALUE, "0");
						((ListType) state.getType()).getElementType().accept(
								new XlimTypeSizeVisitor(init2));
					}
				} else {
					// For others just use the init value
					Object value = state.getConstantValue();
					if (value != null) {
						// FIXME
						// value.accept(new XlimValueVisitor(init2, state
						// .getType()));
					} else {
						init2.setAttribute(VALUE, "0");
						state.getType().accept(new XlimTypeSizeVisitor(init2));
					}
				}
			}
		}
	}

	/**
	 * Add FSM Transitions
	 * 
	 * @param transition
	 *            Transition to add
	 * @param nextState
	 *            Next state of Transition
	 * @param base
	 *            Base where to add the FSM Transition
	 * @return if node (to add else)
	 */
	private Element addTransition(Transition transition,
			NextStateInfo nextState, Element base) {
		String actionname = nextState.getAction().getName();
		String initialname = "fsm" + transition.getSourceState();
		String finalname = "fsm" + nextState.getTargetState();
		String initialguard = initialname + "_guard_" + actionname;
		String finalfire = finalname + "_fire_" + actionname + "_" + (fcount++);

		String falseO = "outval" + (ocount++);
		String trueO = "outval" + (ocount++);

		Element[] sched = addScheduler(nextState.getAction(), initialguard,
				finalfire, base);

		Element ifE = sched[0];
		Element thenE = sched[1];

		if (transition.getSourceState() != nextState.getTargetState()) {
			Element ofalse = XlimNodeTemplate.newValueOperation(thenE, LITINT,
					"0");

			XlimNodeTemplate.newOutPort(ofalse, falseO, "1", BOOL);

			Element otrue = XlimNodeTemplate.newValueOperation(thenE, LITINT,
					"1");

			XlimNodeTemplate.newOutPort(otrue, trueO, "1", BOOL);

			Element afalse = XlimNodeTemplate.newTargetOperation(thenE, ASSIGN,
					initialname);

			XlimNodeTemplate.newInPort(afalse, falseO);

			Element atrue = XlimNodeTemplate.newTargetOperation(thenE, ASSIGN,
					finalname);

			XlimNodeTemplate.newInPort(atrue, trueO);
		}
		return ifE;
	}

	/**
	 * Create state checking for one transition in FSM
	 * 
	 * @param transition
	 *            Transition to check
	 * @param mutexE
	 *            Element where to add the checking
	 * @return if node (to add else)
	 */
	private Element createCheckState(Transition transition, Element mutexE) {
		Element ifE = XlimNodeTemplate.newModule(mutexE, IF);

		String source = transition.getSourceState().getName();
		String name = "fsm" + source;
		String decision = "decision_" + name;

		Element testE = XlimNodeTemplate.newTestModule(ifE, decision);

		Element opE = XlimNodeTemplate.newOperation(testE, NOOP);

		XlimNodeTemplate.newInPort(opE, name + "_copy");

		XlimNodeTemplate.newOutPort(opE, decision, "1", BOOL);

		Element thenE = XlimNodeTemplate.newModule(ifE, THEN);

		return thenE;
	}

	/**
	 * Create the XLIM Document
	 * 
	 * @param actor
	 *            Actor to analyze (get the name)
	 */
	private void createXlimDocument(Actor actor) {
		try {
			xlim = DomUtil.createDocument(DESIGN);
		} catch (OrccException e) {
			e.printStackTrace();
		}
		root = xlim.getDocumentElement();
		XlimNodeTemplate.newDesign(root, actor.getName());
	}

	/**
	 * Print an Actor in the file name
	 * 
	 * @param fileName
	 *            File to be printed
	 * @param actor
	 *            Actor to print
	 * @throws IOException
	 */
	public void printActor(String fileName, Actor actor) throws IOException {
		createXlimDocument(actor);
		addPorts(actor);
		addStateVars(actor);
		addActions(actor);
		addScheduler(actor);
		printXlimFile(new File(fileName));
	}

	/**
	 * Print the XLIM document into the file
	 * 
	 * @param file
	 *            File to be printed
	 */
	private void printXlimFile(File file) {
		OutputStream os;
		try {
			os = new FileOutputStream(file);
			DomUtil.writeDocument(os, xlim);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
