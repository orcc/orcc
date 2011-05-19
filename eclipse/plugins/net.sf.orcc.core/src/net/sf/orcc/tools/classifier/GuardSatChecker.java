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

import static net.sf.orcc.ir.util.EcoreHelper.getContainerOfType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;
import net.sf.orcc.ir.util.IrSwitch;
import net.sf.orcc.ir.util.TypePrinter;
import net.sf.orcc.tools.classifier.smt.SmtLogic;
import net.sf.orcc.tools.classifier.smt.SmtScript;
import net.sf.orcc.tools.classifier.smt.SmtSolver;
import net.sf.orcc.util.sexp.SExpList;
import net.sf.orcc.util.sexp.SExpSymbol;

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
	private static class SmtTranslator extends AbstractActorVisitor<Object> {

		private SmtScript script;

		private List<SmtScript> scripts;

		/**
		 * Creates a new constraint expression visitor.
		 * 
		 */
		public SmtTranslator() {
			script = new SmtScript();
			script.setLogic(SmtLogic.QF_LIA);

			scripts = new ArrayList<SmtScript>();
			scripts.add(script);
		}

		/**
		 * Adds an assertion that the given variable equals the given value.
		 * 
		 * @param variable
		 *            a variable
		 * @param value
		 *            an expression
		 */
		private void addAssertion(Var variable, Expression value) {
			if (!script.getVariables().contains(variable)) {
				declareVar(variable);
			}

			String term = (String) doSwitch(value);
			String name = getUniqueName(variable);
			script.addCommand("(assert (= " + name + " " + term + "))");
		}

		@Override
		public Object caseExprBinary(ExprBinary expr) {
			StringBuilder builder = new StringBuilder();
			builder.append('(');
			switch (expr.getOp()) {
			case EQ:
				builder.append('=');
				break;
			case GE:
				builder.append(">=");
				break;
			case GT:
				builder.append('>');
				break;
			case LE:
				builder.append("<=");
				break;
			case LOGIC_AND:
				builder.append("and");
				break;
			case LOGIC_OR:
				builder.append("or");
				break;
			case LT:
				builder.append('<');
				break;
			case MINUS:
				builder.append('-');
				break;
			case NE:
				builder.append("not (=");
				break;
			case PLUS:
				builder.append('+');
				break;
			default:
				builder.append("TODO");
			}

			builder.append(' ');
			builder.append(doSwitch(expr.getE1()));
			builder.append(' ');
			builder.append(doSwitch(expr.getE2()));
			builder.append(')');

			if (expr.getOp() == OpBinary.NE) {
				builder.append(')');
			}

			return builder.toString();
		}

		@Override
		public Object caseExprBool(ExprBool expr) {
			return String.valueOf(expr.isValue());
		}

		@Override
		public Object caseExprInt(ExprInt expr) {
			return String.valueOf(expr.getValue());
		}

		@Override
		public Object caseExprUnary(ExprUnary expr) {
			StringBuilder builder = new StringBuilder();
			builder.append('(');
			switch (expr.getOp()) {
			case BITNOT:
				builder.append("TODO");
				break;
			case LOGIC_NOT:
				builder.append("not");
				break;
			case MINUS:
				builder.append('-');
				break;
			case NUM_ELTS: {
				Type type = expr.getExpr().getType();
				if (type.isList()) {
					builder.append(((TypeList) type).getSize());
				} else {
					builder.append(0);
				}
				break;
			}
			}

			builder.append(' ');
			builder.append(doSwitch(expr.getExpr()));
			builder.append(')');

			return builder.toString();
		}

		@Override
		public Object caseExprVar(ExprVar expr) {
			Var variable = expr.getUse().getVariable();
			if (!script.getVariables().contains(variable)) {
				declareVar(variable);
			}

			return getUniqueName(variable);
		}

		@Override
		public Object caseInstAssign(InstAssign assign) {
			addAssertion(assign.getTarget().getVariable(), assign.getValue());
			return null;
		}

		@Override
		public Object caseInstLoad(InstLoad load) {
			if (load.getIndexes().isEmpty()) {
				Use source = load.getSource();
				ExprVar expr = IrFactory.eINSTANCE.createExprVar(source);
				addAssertion(load.getTarget().getVariable(), expr);

				// set container back
				load.setSource(source);
			}
			return null;
		}

		@Override
		public Object caseInstReturn(InstReturn instReturn) {
			// a return becomes an assertion that a variable with the same name
			// as the procedure equals the returned value

			Expression value = instReturn.getValue();
			Procedure procedure = getContainerOfType(value, Procedure.class);
			Var variable = IrFactory.eINSTANCE.createVar(
					IrFactory.eINSTANCE.createTypeBool(), procedure.getName(),
					true, 0);
			addAssertion(variable, value);
			return null;
		}

		@Override
		public Object caseInstStore(InstStore store) {
			if (store.getIndexes().isEmpty()) {
				addAssertion(store.getTarget().getVariable(), store.getValue());
			}
			return null;
		}

		@Override
		public Object caseProcedure(Procedure procedure) {
			script.addCommand("; procedure " + procedure.getName());
			return super.caseProcedure(procedure);
		}

		/**
		 * Declares the variable with the given name.
		 * 
		 * @param variable
		 *            a variable
		 */
		private void declareVar(Var variable) {
			String type;
			if (script.getLogic() == SmtLogic.QF_BV) {
				type = new TypeSwitchBitVec().doSwitch(variable.getType());
			} else {
				type = new TypeSwitchInt().doSwitch(variable.getType());
			}

			String name = getUniqueName(variable);
			script.addCommand("(declare-fun " + name + " () " + type + ")");
			script.getVariables().add(variable);

			// constant
			if (!variable.isAssignable() && variable.isInitialized()) {
				String term = (String) doSwitch(variable.getInitialValue());
				script.addCommand("(assert (= " + name + " " + term + "))");
			}
		}

		/**
		 * Returns the list of scripts created by this translator.
		 * 
		 * @return the list of scripts created by this translator
		 */
		public List<SmtScript> getScripts() {
			return scripts;
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
			if (variable.eContainer() instanceof Procedure) {
				Procedure procedure = (Procedure) variable.eContainer();
				return procedure.getName() + "_" + variable.getIndexedName();
			} else {
				return variable.getName();
			}
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
			return "_ BitVec " + type.getSizeInBits();
		}

		@Override
		public String caseTypeList(TypeList type) {
			return "(Array Int " + doSwitch(type.getType()) + ")";
		}

		@Override
		public String caseTypeUint(TypeUint type) {
			return "_ BitVec " + type.getSizeInBits();
		}

	}

	/**
	 * This class defines a switch that returns the SMT-LIB type using Int for
	 * integers.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class TypeSwitchInt extends IrSwitch<String> {

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
			return "Int";
		}

		@Override
		public String caseTypeList(TypeList type) {
			return "(Array Int " + doSwitch(type.getType()) + ")";
		}

		@Override
		public String caseTypeUint(TypeUint type) {
			return "Int";
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
		translator.doSwitch(action1.getScheduler());
		translator.doSwitch(action2.getScheduler());

		List<SmtScript> scripts = translator.getScripts();
		SmtScript script = scripts.get(scripts.size() - 1);

		// check whether the guards are compatible or not
		script.addCommand("(assert (and " + action1.getScheduler().getName()
				+ " " + action2.getScheduler().getName() + "))");
		script.addCommand("(check-sat)");

		return new SmtSolver(actor).checkSat(translator.getScripts());
	}

	/**
	 * Computes a map from ports to token values so that the given action will
	 * be fireable, based on the given assertions.
	 * 
	 * @param action
	 *            an action
	 * @param assertions
	 *            a list of assertions as a map between variable names and
	 *            values
	 * @return a map from ports to token values
	 */
	private Map<Port, Expression> computePortTokenMap(Action action,
			Map<String, Expression> assertions) {
		Pattern pattern = action.getPeekPattern();
		Map<Port, Expression> portMap = new HashMap<Port, Expression>();
		for (Entry<String, Expression> entry : assertions.entrySet()) {
			String name = entry.getKey();
			Var var = action.getScheduler().getLocal(name);
			if (var != null) {
				for (Def def : var.getDefs()) {
					for (Use use : EcoreHelper.getUses(def.eContainer())) {
						Port port = pattern.getPort(use.getVariable());
						if (port != null) {
							Expression value = entry.getValue();
							portMap.put(port, value);
						}
					}
				}
			}
		}

		return portMap;
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
	public Map<Port, Expression> computeTokenValues(List<Port> ports,
			List<Action> others, Action action) {
		SmtTranslator translator = new SmtTranslator();
		SExpList assertion = new SExpList(new SExpSymbol("and"));

		for (Action previous : others) {
			translator.doSwitch(previous.getScheduler());
			assertion.add(new SExpList(new SExpSymbol("not"), new SExpSymbol(
					previous.getScheduler().getName())));
		}

		translator.doSwitch(action.getScheduler());
		assertion.add(new SExpSymbol(action.getScheduler().getName()));

		List<SmtScript> scripts = translator.getScripts();
		SmtScript script = scripts.get(scripts.size() - 1);

		// check whether the guards are compatible or not
		SExpList sexpAssert = new SExpList(new SExpSymbol("assert"), assertion);
		script.addCommand(sexpAssert.toString());
		script.addCommand("(check-sat)");

		SmtSolver solver = new SmtSolver(actor);
		solver.checkSat(translator.getScripts());

		// fills the map
		return computePortTokenMap(action, solver.getAssertions());
	}

}
