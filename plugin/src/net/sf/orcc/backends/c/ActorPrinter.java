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
package net.sf.orcc.backends.c;


/**
 * Actor printer.
 * 
 * @author Mathieu Wippliez
 * 
 */
public class ActorPrinter {

//	private class TransitionPrinter implements PrinterCallback {
//		private String state;
//
//		@Override
//		public void print(Object... args) {
//			pp.println("_FSM_state = s_" + state + ";");
//		}
//
//		public void setState(String state) {
//			this.state = state;
//		}
//	}
//
//	protected String actorName;
//
//	private CNodeVisitor nodePrinter;
//
//	protected PrettyPrinter pp;
//
//	private TransitionPrinter transitionPrinter;
//
//	private TypeVisitor typePrinter;
//
//	protected VarDefPrinter varDefPrinter;
//
//	public ActorPrinter(String fileName, Actor actor)
//			throws FileNotFoundException {
//		pp = new PrettyPrinter(fileName, 80, 2);
//		transitionPrinter = new TransitionPrinter();
//
//		// fill port names list
//		List<String> ports = new ArrayList<String>();
//		fillPorts(ports, actor.getInputs());
//		fillPorts(ports, actor.getOutputs());
//
//		ExprVisitor exprPrinter = new ExprPrinter(pp, varDefPrinter);
//		actorName = actor.getName();
//		nodePrinter = new NodePrinter(pp, actorName, exprPrinter, varDefPrinter);
//
//		printActor(actor);
//
//		pp.close();
//	}
//
//	private void fillPorts(List<String> portNames, List<VarDef> ports) {
//		for (VarDef port : ports) {
//			portNames.add(port.getName());
//		}
//	}
//
//	/**
//	 * Prints an action scheduler (and everything associated with it).
//	 * 
//	 * @param scheduler
//	 *            An action scheduler.
//	 */
//	private void printActionScheduler(ActionScheduler scheduler) {
//		List<Action> actions = scheduler.getActions();
//		if (scheduler.hasFsm()) {
//			// states
//			FSM fsm = scheduler.getFsm();
//			printFSMStates(fsm.getStates(), fsm.getInitialState());
//
//			// outside FSM scheduler
//			if (!actions.isEmpty()) {
//				printOutsideFSM(actions);
//			}
//
//			// print a scheduler for each state.
//			for (Transition transition : fsm.getTransitions()) {
//				printFSMStateScheduler(transition);
//			}
//
//			// print the action scheduler
//			printActionSchedulerFSM(actions, fsm.getTransitions());
//		} else {
//			printActionSchedulerNoFSM(actions);
//		}
//	}
//
//	/**
//	 * Prints an action scheduler function when the actor has an FSM.
//	 * 
//	 * @param actions
//	 *            A list of actions.
//	 */
//	private void printActionSchedulerFSM(List<Action> actions,
//			List<Transition> transitions) {
//		pp.println();
//		pp.indent();
//		pp.println("int " + actorName + "_scheduler() {");
//		pp.println("int res = 1;");
//		pp.println();
//
//		pp.indent();
//		pp.println("while (res) {");
//
//		if (!actions.isEmpty()) {
//			pp.indent();
//			pp.println("if (outside_FSM_scheduler()) {");
//			pp.unindent();
//			pp.println("res = 1;");
//			pp.indent();
//			pp.println("} else {");
//		}
//
//		pp.indent();
//		pp.println("switch (_FSM_state) {");
//		for (Transition transition : transitions) {
//			pp.indent();
//			pp.println("case s_" + transition.getSourceState() + ":");
//			pp.println("res = " + transition.getSourceState()
//					+ "_state_scheduler();");
//			pp.unindent();
//			pp.println("break;");
//		}
//
//		// default
//		pp.indent();
//		pp.println("default:");
//		pp.println("printf(\"unknown state\\n\");");
//		pp.unindent();
//		pp.unindent();
//		pp.println("break;");
//
//		// end of switch
//		pp.unindent();
//		pp.println("}");
//
//		if (!actions.isEmpty()) {
//			pp.unindent();
//			pp.println("}");
//		}
//
//		pp.println("}");
//		pp.println();
//		pp.unindent();
//		pp.println("return 0;");
//		pp.println("}");
//	}
//
//	/**
//	 * Prints an action scheduler function when the actor has no FSM.
//	 * 
//	 * @param actions
//	 *            A list of actions.
//	 */
//	private void printActionSchedulerNoFSM(List<Action> actions) {
//		pp.println();
//		pp.indent();
//		pp.println("int " + actorName + "_scheduler() {");
//		pp.println("int res = 1;");
//		pp.println();
//
//		pp.indent();
//		pp.println("while (res) {");
//
//		printActionTests(actions, null);
//
//		for (int i = 0; i < actions.size() - 1; i++) {
//			pp.unindent();
//			pp.println("}");
//		}
//
//		if (!actions.isEmpty()) {
//			pp.unindent();
//			pp.println("}");
//		}
//
//		pp.println("}");
//		pp.println();
//		pp.unindent();
//		pp.println("return 0;");
//		pp.println("}");
//	}
//
//	private void printActionTest(Action action, PrinterCallback callback) {
//		String tag = action.getTagAsString();
//		if (tag.isEmpty()) {
//			tag = action.getBody().getName();
//		}
//
//		pp.indent();
//		pp.println("if (isSchedulable_" + tag + "()) {");
//
//		Map<VarDef, Integer> op = action.getOutputPattern();
//		if (!op.isEmpty()) {
//			printActionTestOutput(new TreeMap<VarDef, Integer>(op));
//		}
//
//		varDefPrinter.printName(tag);
//		pp.println("();");
//		if (callback != null) {
//			callback.print();
//		}
//		pp.unindent();
//		pp.println("res = 1;");
//
//		if (!op.isEmpty()) {
//			pp.indent();
//			pp.println("} else {");
//			pp.unindent();
//			pp.println("res = 0;");
//			pp.unindent();
//			pp.println("}");
//		}
//
//		pp.indent();
//		pp.println("} else {");
//	}
//
//	private void printActionTestOutput(Map<VarDef, Integer> op) {
//		pp.indent();
//		pp.print("if (");
//
//		// check if there is room
//		Object[] entries = op.entrySet().toArray();
//		int n = entries.length - 1;
//		for (int i = 0; i < n; i++) {
//			VarDef varDef = (VarDef) ((Entry<?, ?>) entries[i]).getKey();
//			int numTokens = (Integer) ((Entry<?, ?>) entries[i]).getValue();
//			pp.print("hasRoom(" + actorName + "_");
//			varDefPrinter.printVarDefName(varDef);
//			pp.print(", " + numTokens + ") && ");
//		}
//
//		VarDef varDef = (VarDef) ((Entry<?, ?>) entries[n]).getKey();
//		int numTokens = (Integer) ((Entry<?, ?>) entries[n]).getValue();
//		pp.print("hasRoom(" + actorName + "_");
//		varDefPrinter.printVarDefName(varDef);
//		pp.print(", " + numTokens + ")");
//
//		pp.println(") {");
//	}
//
//	/**
//	 * Prints action tests.
//	 * 
//	 * @param actions
//	 */
//	private void printActionTests(List<Action> actions, PrinterCallback callback) {
//		for (Action action : actions) {
//			printActionTest(action, callback);
//		}
//
//		pp.unindent();
//		pp.println("res = 0;");
//	}
//
//	protected void printActor(Actor actor) {
//		printActorBody(actor);
//	}
//
//	/**
//	 * Prints the body of the given actor. This includes functions, procedures,
//	 * actions, initializes and the action scheduler.
//	 * 
//	 * @param actor
//	 *            An {@link Actor}.
//	 */
//	final protected void printActorBody(Actor actor) {
//		List<Action> initializes = actor.getInitializes();
//		if (!initializes.isEmpty()) {
//			printInitializes(initializes);
//		}
//
//		printSeparator();
//		pp.println("// Actions");
//		pp.println();
//		for (Action action : actor.getActions()) {
//			printProc(action.getBody());
//			printProc(action.getScheduler());
//		}
//
//		printSeparator();
//		pp.println("// Action scheduler");
//		printActionScheduler(actor.getActionScheduler());
//	}
//
//	/**
//	 * Prints an enum of states and the initial state.
//	 * 
//	 * @param states
//	 *            A list of state names.
//	 * @param init
//	 *            The initial state.
//	 */
//	private void printFSMStates(List<String> states, String init) {
//		pp.println();
//		pp.indent();
//		pp.println("enum states {");
//		pp.println("s_" + states.get(0) + " = 0,");
//		for (int i = 1; i < states.size() - 1; i++) {
//			pp.println("s_" + states.get(i) + ",");
//		}
//		pp.unindent();
//		pp.println("s_" + states.get(states.size() - 1));
//		pp.println("};");
//
//		pp.println();
//		pp.indent();
//		pp.println("static char *stateNames[] = {");
//		for (int i = 0; i < states.size() - 1; i++) {
//			pp.println("\"s_" + states.get(i) + "\",");
//		}
//		pp.unindent();
//		pp.println("\"s_" + states.get(states.size() - 1) + "\"");
//		pp.println("};");
//
//		pp.println();
//		pp.println("static enum states _FSM_state = s_" + init + ";");
//	}
//
//	/**
//	 * Prints a state scheduler function for the given transition.
//	 * 
//	 * @param transition
//	 */
//	private void printFSMStateScheduler(Transition transition) {
//		String state = transition.getSourceState();
//
//		pp.println();
//		pp.indent();
//		pp.println("static int " + state + "_state_scheduler() {");
//		pp.println("int res;");
//		pp.println();
//
//		for (final NextStateInfo info : transition.getNextStateInfo()) {
//			Action action = info.getAction();
//			transitionPrinter.setState(info.getTargetState());
//			printActionTest(action, transitionPrinter);
//		}
//
//		pp.unindent();
//		pp.println("res = 0;");
//
//		for (int i = 0; i < transition.getNextStateInfo().size() - 1; i++) {
//			pp.unindent();
//			pp.println("}");
//		}
//
//		pp.println("}");
//		pp.println();
//		pp.unindent();
//		pp.println("return res;");
//		pp.println("}");
//	}
//
//	/**
//	 * Prints initialize actions, if there are any.
//	 * 
//	 * @param initializes
//	 *            A list of initialize actions.
//	 */
//	private void printInitializes(List<Action> initializes) {
//		printSeparator();
//		pp.println("// Initializes");
//		pp.println();
//		for (Action action : initializes) {
//			printProc(action.getBody());
//			printProc(action.getScheduler());
//		}
//
//		pp.println();
//		pp.indent();
//		pp.println("void " + actorName + "_initialize() {");
//		pp.println("int res = 1;");
//
//		// we just print the standard action tests.
//		// in this case it just tests the guard (if any).
//		printActionTests(initializes, null);
//
//		for (int i = 0; i < initializes.size(); i++) {
//			pp.unindent();
//			pp.println("}");
//		}
//
//		pp.println("}");
//	}
//
//	private void printNodes(List<AbstractNode> nodes) {
//		for (AbstractNode node : nodes) {
//			node.accept(nodePrinter);
//		}
//	}
//
//	private void printOutsideFSM(List<Action> actions) {
//		pp.println();
//		pp.indent();
//		pp.println("static int outside_FSM_scheduler() {");
//		pp.println("int res;");
//		pp.println();
//
//		printActionTests(actions, null);
//
//		for (int i = 0; i < actions.size() - 1; i++) {
//			pp.unindent();
//			pp.println("}");
//		}
//
//		pp.println("}");
//		pp.println();
//
//		pp.unindent();
//		pp.println("return res;");
//		pp.println("}");
//	}
//
//	private void printProc(Procedure proc) {
//		pp.setBreakMode(false);
//		pp.print("static ");
//		proc.getReturnType().accept(typePrinter);
//		pp.print(" ");
//		varDefPrinter.printName(proc.getName());
//		pp.print("(");
//		pp.setBreakMode(true);
//		pp.printList(", ", proc.getParameters(), new PrinterCallback() {
//
//			@Override
//			public void print(Object... args) {
//				varDefPrinter.printVarDef((VarDef) args[0], "");
//			}
//
//		});
//
//		// header
//		pp.setBreakMode(false);
//		pp.print(") {");
//		pp.indent();
//
//		// local variables
//		List<VarDef> locals = proc.getLocals();
//		if (!locals.isEmpty()) {
//			pp.println();
//			for (VarDef varDef : locals) {
//				varDefPrinter.printVarDef(varDef, ";");
//				pp.println();
//			}
//		}
//
//		// body
//		printNodes(proc.getNodes());
//
//		// footer
//		pp.unindent();
//		pp.println();
//		pp.println("}");
//		pp.println();
//	}
//
//	protected void printSeparator() {
//		pp.println();
//		pp.println("////////////////////////////////////////"
//				+ "////////////////////////////////////////");
//	}

}
