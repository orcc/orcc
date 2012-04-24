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
package net.sf.orcc.tools.classifier;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Unit;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrSwitch;
import net.sf.orcc.ir.util.TypePrinter;
import net.sf.orcc.tools.classifier.smt.SmtScript;
import net.sf.orcc.tools.classifier.smt.SmtSolver;
import net.sf.orcc.util.sexp.SExp;
import net.sf.orcc.util.sexp.SExpList;
import net.sf.orcc.util.sexp.SExpSymbol;

import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a satisfiability checker for guards of actions. The
 * checker checks that the guards of two actions or more are mutually exclusive
 * with an SMT solver.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class GuardSatChecker {

	/**
	 * This class defines a visitor that examines expressions that depend on a
	 * value peeked from the configuration port.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class SmtIrTranslator extends AbstractIrVisitor<Object> {

		private StringBuilder builder;

		private int numLets;

		/**
		 * list of procedures declared in this script
		 */
		private List<Procedure> procs;

		private SmtScript script;

		/**
		 * Creates a new constraint expression visitor.
		 * 
		 */
		public SmtIrTranslator() {
			script = new SmtScript();
			procs = new ArrayList<Procedure>();
		}

		/**
		 * Adds an assertion that the given variable equals the given value.
		 * 
		 * @param variable
		 *            a variable
		 * @param value
		 *            an expression
		 */
		private void addLet(Var variable, Expression value) {
			addLet(variable, (String) doSwitch(value));
		}

		/**
		 * Adds an assertion that the given variable equals the given term.
		 * 
		 * @param variable
		 *            a variable
		 * @param term
		 *            a term
		 */
		private void addLet(Var variable, String term) {
			builder.append("(let ((");
			builder.append(variable.getName());
			builder.append(" ");
			builder.append(term);
			builder.append(")) ");
			numLets++;
		}

		@Override
		public Object caseExprBinary(ExprBinary expr) {
			String e1 = (String) doSwitch(expr.getE1());
			String e2 = (String) doSwitch(expr.getE2());

			switch (expr.getOp()) {
			case BITAND:
				return "(bvand " + e1 + " " + e2 + ")";
			case BITOR:
				return "(bvor " + e1 + " " + e2 + ")";
			case BITXOR:
				return "(bvand (bvnot (bvand " + e1 + " " + e2 + ")) (bvor "
						+ e1 + " " + e2 + "))";
			case DIV:
			case DIV_INT:
				return "(bvudiv " + e1 + " " + e2 + ")";
			case EQ:
				return "(= " + e1 + " " + e2 + ")";
			case GE:
				return "(not (bvult " + e1 + " " + e2 + "))";
			case GT:
				return "(and (not (= " + e1 + " " + e2 + ")) (not (bvult " + e1
						+ " " + e2 + ")))";
			case LE:
				return "(or (= " + e1 + " " + e2 + ") (bvult " + e1 + " " + e2
						+ "))";
			case LOGIC_AND:
				return "(and " + e1 + " " + e2 + ")";
			case LOGIC_OR:
				return "(or " + e1 + " " + e2 + ")";
			case LT:
				return "(bvult " + e1 + " " + e2 + ")";
			case MINUS:
				return "(bvadd " + e1 + " (bvneg " + e2 + "))";
			case MOD:
				return "(bvurem " + e1 + " " + e2 + ")";
			case NE:
				return "(not (= " + e1 + " " + e2 + "))";
			case PLUS:
				return "(bvadd " + e1 + " " + e2 + ")";
			case SHIFT_LEFT:
				return "(bvshl " + e1 + " " + e2 + ")";
			case SHIFT_RIGHT:
				return "(bvlshr " + e1 + " " + e2 + ")";
			case TIMES:
				return "(bvmul " + e1 + " " + e2 + ")";
			default:
				return "TODO";
			}
		}

		@Override
		public Object caseExprBool(ExprBool expr) {
			return String.valueOf(expr.isValue());
		}

		@Override
		public Object caseExprInt(ExprInt expr) {
			return getStringOfInt(expr.getValue());
		}

		@Override
		public Object caseExprUnary(ExprUnary expr) {
			String e1 = (String) doSwitch(expr.getExpr());
			switch (expr.getOp()) {
			case BITNOT:
				return "(bvnot " + e1 + ")";
			case LOGIC_NOT:
				return "(not " + e1 + ")";
			case MINUS:
				return "(bvneg " + e1 + ")";
			case NUM_ELTS: {
				Type type = expr.getExpr().getType();
				int size;
				if (type.isList()) {
					size = ((TypeList) type).getSize();
				} else {
					size = 0;
				}
				return getStringOfInt(BigInteger.valueOf(size));
			}
			default:
				return null;
			}
		}

		@Override
		public Object caseExprVar(ExprVar expr) {
			Var variable = expr.getUse().getVariable();
			EObject cter = variable.eContainer();
			if ((cter instanceof Actor || cter instanceof Unit)
					&& !script.getVariables().contains(variable)) {
				// an expr var may contain a reference to a global var (array)
				declareVar(variable);
			}

			return getUniqueName(variable);
		}

		@Override
		public Object caseInstAssign(InstAssign assign) {
			addLet(assign.getTarget().getVariable(), assign.getValue());
			return null;
		}

		@Override
		public Object caseInstCall(InstCall call) {
			if (call.getTarget() != null) {
				Procedure proc = call.getProcedure();
				if (!procs.contains(procs)) {
					doSwitch(proc);
				}

				Var variable = call.getTarget().getVariable();

				String args = new String();
				for (Arg arg : call.getParameters()) {
					if (arg.isByVal()) {
						Expression expr = ((ArgByVal) arg).getValue();
						args += " " + doSwitch(expr);
					}
				}

				String term = new String();
				if (args.isEmpty()) {
					term = call.getProcedure().getName();
				} else {
					term = "(" + call.getProcedure().getName() + args + ")";
				}
				addLet(variable, term);
			}
			return null;
		}

		@Override
		public Object caseInstLoad(InstLoad load) {
			Use source = load.getSource();
			Var variable = source.getVariable();
			EObject cter = variable.eContainer();
			if ((cter instanceof Actor || cter instanceof Unit)
					&& !script.getVariables().contains(variable)) {
				// might be necessary to declare the variable loaded
				declareVar(variable);
			}

			String term = getUniqueName(variable);
			for (Expression index : load.getIndexes()) {
				term = "(select " + term + " " + doSwitch(index) + ")";
			}

			addLet(load.getTarget().getVariable(), term);

			return null;
		}

		@Override
		public Object caseInstReturn(InstReturn instReturn) {
			builder.append(doSwitch(instReturn.getValue()));
			for (; numLets > 0; numLets--) {
				builder.append(")");
			}
			return null;
		}

		@Override
		public Object caseInstStore(InstStore store) {
			// if (store.getIndexes().isEmpty()) {
			// addLet(store.getTarget().getVariable(), store.getValue());
			// }
			System.err.println("store");
			return null;
		}

		@Override
		public Object caseProcedure(Procedure procedure) {
			// add procedure to list of procedures
			procs.add(procedure);

			// save state
			StringBuilder oldBuilder = builder;
			int oldNumLets = numLets;

			// start definition
			numLets = 0;
			builder = new StringBuilder();
			if (procedure.isNative()) {
				// cannot define a native function/procedure
				builder.append("(declare-fun ");
			} else {
				builder.append("(define-fun ");
			}
			builder.append(procedure.getName());

			// parameters
			builder.append(" (");
			for (Param param : procedure.getParameters()) {
				Var var = param.getVariable();
				builder.append("(");
				builder.append(var.getName());
				String type = new TypeSwitchBitVec().doSwitch(var.getType());
				builder.append(" ");
				builder.append(type);
				builder.append(") ");
			}
			builder.append(") ");

			// return type
			String type = new TypeSwitchBitVec().doSwitch(procedure
					.getReturnType());
			builder.append(type);
			builder.append(" ");

			// body
			if (!procedure.isNative()) {
				super.caseProcedure(procedure);
			}
			builder.append(")");

			// add declaration to script
			script.addCommand("");
			script.addCommand(builder.toString());

			// restore state
			builder = oldBuilder;
			numLets = oldNumLets;

			return null;
		}

		/**
		 * Declares the variable with the given name.
		 * 
		 * @param variable
		 *            a variable
		 */
		private void declareVar(Var variable) {
			String name = getUniqueName(variable);
			String type = new TypeSwitchBitVec().doSwitch(variable.getType());
			script.addCommand("(declare-fun " + name + " () " + type + ")");
			script.getVariables().add(variable);

			// constant
			if (!variable.isAssignable() && variable.isInitialized()) {
				String term = (String) doSwitch(variable.getInitialValue());
				if (term != null) {
					script.addCommand("(assert (= " + name + " " + term + "))");
				}
			}
		}

		/**
		 * Returns the 32-bit BitVec representation of the given BigInteger.
		 * 
		 * @param integer
		 *            a BigInteger
		 * @return the 32-bit BitVec representation
		 */
		private String getStringOfInt(BigInteger integer) {
			if (integer.signum() == -1) {
				BigInteger m = BigInteger.valueOf((long) 1 << 32);
				integer = m.add(integer);
			}
			return "(_ bv" + integer + " 32)";
		}

		/**
		 * Returns the 32-bit BitVec representation of the given BigInteger.
		 * 
		 * @param integer
		 *            a BigInteger
		 * @return the 32-bit BitVec representation
		 */
		private String getStringOfInt(int integer) {
			return "(_ bv" + integer + " 32)";
		}

		/**
		 * Returns the name of the given variable so that it does not conflict
		 * with variables with a similar name declared in other procedures.
		 * 
		 * @param variable
		 *            a variable
		 * @return a unique name for the given variable
		 */
		private String getUniqueName(Var variable) {
			String name = variable.getName();
			EObject cter = variable.eContainer();
			if (cter instanceof Pattern) {
				return ((Action) cter.eContainer()).getName() + "_" + name;
			} else if (cter instanceof Procedure && variable.getType().isList()) {
				return ((Procedure) cter).getName() + "_" + name;
			}
			return name;
		}

	}

	/**
	 * This class defines a visitor that examines expressions that depend on a
	 * value peeked from the configuration port.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class SmtTranslator extends DfVisitor<Object> {

		private SmtIrTranslator irTranslator;

		public SmtTranslator() {
			irTranslator = new SmtIrTranslator();
			irVisitor = irTranslator;
		}

		@Override
		public Object caseAction(Action action) {
			Pattern pattern = action.getPeekPattern();
			for (Port port : pattern.getPorts()) {
				Var variable = pattern.getVariable(port);
				irTranslator.declareVar(variable);
				int numTokens = pattern.getNumTokens(port);
				String command = irTranslator.getUniqueName(variable);
				for (int i = 0; i < numTokens; i++) {
					String select = "(select " + port.getName() + " "
							+ irTranslator.getStringOfInt(i) + ")";
					command = "(store " + command + " "
							+ irTranslator.getStringOfInt(i) + " " + select
							+ ")";
				}
				command = "(assert (= " + irTranslator.getUniqueName(variable)
						+ " " + command + "))";
				irTranslator.script.addCommand(command);
			}

			irTranslator.doSwitch(action.getScheduler());

			return null;
		}

		@Override
		public Object casePort(Port port) {
			String name = port.getName();
			Type portType = IrFactory.eINSTANCE.createTypeList(0,
					port.getType());
			String type = new TypeSwitchBitVec().doSwitch(portType);
			irTranslator.script.addCommand("(declare-fun " + name + " () "
					+ type + ")");
			return null;
		}

		/**
		 * Returns the script created by this translator.
		 * 
		 * @return the script created by this translator
		 */
		public SmtScript getScript() {
			return irTranslator.script;
		}

	}

	/**
	 * This class defines a switch that returns the SMT-LIB type using BitVec
	 * for integers.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class TypeSwitchBitVec extends IrSwitch<String> {

		@Override
		public String caseType(Type type) {
			throw new OrccRuntimeException("unexpected type "
					+ new TypePrinter().doSwitch(type));
		}

		@Override
		public String caseTypeBool(TypeBool type) {
			return "Bool";
		}

		@Override
		public String caseTypeInt(TypeInt type) {
			int size = 32; // type.getSizeInBits()
			return "(_ BitVec " + size + ")";
		}

		@Override
		public String caseTypeList(TypeList type) {
			// (Array indexType valueType)
			return "(Array (_ BitVec 32) " + doSwitch(type.getType()) + ")";
		}

		@Override
		public String caseTypeUint(TypeUint type) {
			int size = 32; // type.getSizeInBits()
			return "(_ BitVec " + size + ")";
		}

	}

	private Actor actor;

	public GuardSatChecker(Actor actor) {
		this.actor = actor;
	}

	/**
	 * Returns <code>true</code> if the guards of action1 and action2 are
	 * compatible, and <code>false</code> if they are mutually exclusive.
	 * 
	 * @param action1
	 *            action 1
	 * @param action2
	 *            action 2
	 * @return <code>true</code> if the guards of action1 and action2 are
	 *         compatible
	 */
	public boolean checkSat(Action action1, Action action2) {
		SmtTranslator translator = new SmtTranslator();
		for (Port port : actor.getInputs()) {
			translator.doSwitch(port);
		}
		translator.doSwitch(action1);
		translator.doSwitch(action2);

		SmtScript script = translator.getScript();

		// check whether the guards are compatible or not
		script.addCommand("(assert (and " + action1.getScheduler().getName()
				+ " " + action2.getScheduler().getName() + "))");
		script.addCommand("(check-sat)");

		return new SmtSolver(actor).checkSat(script);
	}

	/**
	 * Computes the values of tokens that when present on the given ports
	 * satisfy the following two conditions:
	 * <ol>
	 * <li>no action in <code>others</code> can be fired</li>
	 * <li>the given action can be fired</li>
	 * </ol>
	 * 
	 * @param ports
	 *            a list of ports
	 * @param others
	 *            a list of actions
	 * @param action
	 *            an action
	 * @return a map that associate ports with values so that when token are
	 *         peeked on ports in the map, values associated with them are given
	 *         to the interpreter, which allows <code>action</code> to fire
	 */
	public Map<String, Object> computeTokenValues(List<Port> ports,
			List<Action> others, Action action) {
		SmtTranslator translator = new SmtTranslator();
		for (Port port : actor.getInputs()) {
			translator.doSwitch(port);
		}
		translator.doSwitch(action);

		SExp assertion = new SExpSymbol(action.getScheduler().getName());
		ListIterator<Action> it = others.listIterator(others.size());
		while (it.hasPrevious()) {
			Action previous = it.previous();
			translator.doSwitch(previous);
			assertion = new SExpList(new SExpSymbol("ite"), new SExpSymbol(
					previous.getScheduler().getName()),
					new SExpSymbol("false"), assertion);
		}

		SmtScript script = translator.getScript();

		// check whether the guards are compatible or not
		SExpList sexpAssert = new SExpList(new SExpSymbol("assert"), assertion);
		script.addCommand(sexpAssert.toString());
		script.addCommand("(check-sat)");

		SmtSolver solver = new SmtSolver(actor);
		solver.checkSat(script, action, ports);

		// fills the map
		return solver.getAssertions();
	}

}
