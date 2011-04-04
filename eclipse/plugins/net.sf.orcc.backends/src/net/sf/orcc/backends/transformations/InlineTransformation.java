/*
 * Copyright (c) 2010, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.sf.orcc.backends.xlim.instructions.TernaryOperation;
import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.VarLocal;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.FloatExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.impl.NodeInterpreter;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.InstructionInterpreter;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;

/**
 * This class defines an actor transformation that inline the functions and/or
 * the procedures
 * 
 * @author Herve Yviquel
 * 
 */
public class InlineTransformation extends AbstractActorVisitor {

	protected class InlineCloner implements NodeInterpreter,
			InstructionInterpreter, ExpressionInterpreter {

		@Override
		public Object interpret(Assign assign, Object... args) {
			VarLocal target = (VarLocal) variableToLocalVariableMap
					.get(assign.getTarget());
			Expression value = (Expression) assign.getValue()
					.accept(this, args);
			Assign a = new Assign(assign.getLocation(), target, value);
			Use.addUses(a, value);
			return a;
		}

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			Expression e1 = (Expression) expr.getE1().accept(this, args);
			Expression e2 = (Expression) expr.getE2().accept(this, args);
			BinaryExpr e = new BinaryExpr(e1, expr.getOp(), e2, expr.getType());
			return e;
		}

		@Override
		public Object interpret(NodeBlock node, Object... args) {
			NodeBlock nodeBlock = IrFactoryImpl.eINSTANCE.createNodeBlock();
			nodeBlock.setLocation(node.getLocation());
			for (Instruction instruction : node.getInstructions()) {
				Instruction i = (Instruction) instruction.accept(this, args);
				if (i != null) {
					nodeBlock.add(i);
				}
			}
			return nodeBlock;
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			BoolExpr e = new BoolExpr(expr.getValue());
			return e;
		}

		@Override
		public Object interpret(Call call, Object... args) {
			VarLocal target = (VarLocal) variableToLocalVariableMap
					.get(call.getTarget());
			List<Expression> parameters = new ArrayList<Expression>();
			for (Expression parameter : call.getParameters()) {
				parameters.add((Expression) parameter.accept(this, args));
			}
			Call c = new Call(call.getLocation(), target, call.getProcedure(),
					parameters);
			Use.addUses(c, parameters);
			return c;
		}

		@Override
		public Object interpret(FloatExpr expr, Object... args) {
			FloatExpr e = new FloatExpr(expr.getValue());
			return e;
		}

		@Override
		public Object interpret(NodeIf node, Object... args) {
			Expression condition = (Expression) node.getValue().accept(this,
					args);
			List<Node> thenNodes = new ArrayList<Node>();
			for (Node n : node.getThenNodes()) {
				thenNodes.add((Node) n.accept(this, args));
			}
			List<Node> elseNodes = new ArrayList<Node>();
			for (Node n : node.getElseNodes()) {
				elseNodes.add((Node) n.accept(this, args));
			}
			NodeBlock joinNode = (NodeBlock) node.getJoinNode().accept(this,
					args);

			NodeIf nodeIf = IrFactoryImpl.eINSTANCE.createNodeIf();
			nodeIf.setLocation(node.getLocation());
			node.setValue(condition);
			node.getThenNodes().addAll(thenNodes);
			node.getElseNodes().addAll(elseNodes);
			node.setJoinNode(joinNode);

			Use.addUses(nodeIf, condition);
			return nodeIf;
		}

		@Override
		public Object interpret(IntExpr expr, Object... args) {
			IntExpr e = new IntExpr(expr.getValue());
			return e;
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			List<Expression> expressions = new ArrayList<Expression>();
			for (Expression e : expr.getValue()) {
				expressions.add((Expression) e.accept(this, args));
			}
			ListExpr listExpr = new ListExpr(expressions);
			return listExpr;
		}

		@Override
		public Object interpret(Load load, Object... args) {
			VarLocal target = (VarLocal) variableToLocalVariableMap
					.get(load.getTarget());
			List<Expression> indexes = new ArrayList<Expression>();
			for (Expression index : load.getIndexes()) {
				indexes.add((Expression) index.accept(this, args));
			}
			Load l;
			Var sourceVariable = load.getSource().getVariable();
			if (sourceVariable.isGlobal()) {
				l = new Load(load.getLocation(), target, load.getSource(),
						indexes);
			} else {
				l = new Load(load.getLocation(), target, new Use(
						variableToLocalVariableMap.get(sourceVariable)),
						indexes);
			}
			Use.addUses(l, indexes);
			return l;
		}

		@Override
		public Object interpret(PhiAssignment phi, Object... args) {
			VarLocal target = (VarLocal) variableToLocalVariableMap
					.get(phi.getTarget());
			List<Expression> values = new ArrayList<Expression>();
			for (Expression value : phi.getValues()) {
				values.add((Expression) value.accept(this, args));
			}
			PhiAssignment p = new PhiAssignment(phi.getLocation(), target,
					values);
			Use.addUses(p, values);
			return p;
		}

		@Override
		public Object interpret(Return returnInst, Object... args) {
			if (returnInst.getValue() != null) {
				Expression value = (Expression) returnInst.getValue().accept(
						this, args);
				Assign a = new Assign(returnVariableOfCurrentFunction, value);
				Use.addUses(a, value);
				return a;
			} else {
				return null;
			}

		}

		@Override
		public Object interpret(SpecificInstruction specific, Object... args) {
			return specific;
		}

		@Override
		public Object interpret(Store store, Object... args) {
			Var target;
			if (store.getTarget().isGlobal()) {
				target = store.getTarget();
			} else {
				target = variableToLocalVariableMap.get(store.getTarget());
			}
			List<Expression> indexes = new ArrayList<Expression>();
			for (Expression index : store.getIndexes()) {
				indexes.add((Expression) index.accept(this, args));
			}
			Expression value = (Expression) store.getValue().accept(this, args);
			Store s = new Store(store.getLocation(), target, indexes, value);
			Use.addUses(s, indexes);
			Use.addUses(s, value);
			return s;
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			StringExpr stringExpr = new StringExpr(expr.getValue());
			return stringExpr;
		}

		public Object interpret(TernaryOperation ternaryOperation,
				Object... args) {
			VarLocal target = (VarLocal) variableToLocalVariableMap
					.get(ternaryOperation.getTarget());

			Expression conditionValue = (Expression) ternaryOperation
					.getConditionValue().accept(this, args);
			Expression trueValue = (Expression) ternaryOperation.getTrueValue()
					.accept(this, args);
			Expression falseValue = (Expression) ternaryOperation
					.getFalseValue().accept(this, args);

			TernaryOperation t = new TernaryOperation(target, conditionValue,
					trueValue, falseValue);
			Use.addUses(t, conditionValue);
			Use.addUses(t, trueValue);
			Use.addUses(t, falseValue);
			return t;
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			Expression expression = (Expression) expr.getExpr().accept(this,
					args);
			UnaryExpr unaryExpr = new UnaryExpr(expr.getOp(), expression,
					expr.getType());
			return unaryExpr;
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			Var newVar = variableToLocalVariableMap.get(expr.getVar()
					.getVariable());
			VarExpr varExpr = new VarExpr(new Use(newVar));
			return varExpr;
		}

		@Override
		public Object interpret(NodeWhile node, Object... args) {
			Expression condition = (Expression) node.getValue().accept(this,
					args);
			List<Node> nodes = new ArrayList<Node>();
			for (Node n : node.getNodes()) {
				nodes.add((Node) n.accept(this, args));
			}
			NodeBlock joinNode = (NodeBlock) node.getJoinNode().accept(this,
					args);

			NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
			nodeWhile.setLocation(node.getLocation());
			nodeWhile.setValue(condition);
			nodeWhile.getNodes().addAll(nodes);
			nodeWhile.setJoinNode(joinNode);

			Use.addUses(nodeWhile, condition);
			return nodeWhile;
		}

	}

	protected InlineCloner inlineCloner;

	private boolean inlineFunction;

	private boolean inlineProcedure;

	private boolean needToSkipThisNode;

	private VarLocal returnVariableOfCurrentFunction;

	protected Map<Var, Var> variableToLocalVariableMap;

	public InlineTransformation(boolean inlineProcedure, boolean inlineFunction) {
		this.inlineProcedure = inlineProcedure;
		this.inlineFunction = inlineFunction;
		inlineCloner = new InlineCloner();
	}

	private void inline(Call call) {
		// The function or the procedure
		Procedure function = call.getProcedure();

		// Set the function/procedure to external thus it will not be printed
		function.setNative(true);

		// Create a new local variable to all function/procedure's variable
		// except for list (reference is using)
		variableToLocalVariableMap = new HashMap<Var, Var>();
		for (Var var : function.getLocals().getList()) {
			VarLocal oldVar = (VarLocal) var;
			VarLocal newVar = procedure.newTempLocalVariable("",
					oldVar.getType(),
					procedure.getName() + "_" + oldVar.getName() + "_"
							+ call.getLocation().getStartLine() + "_"
							+ call.getLocation().getStartColumn());
			newVar.setIndex(oldVar.getIndex());
			newVar.setLocation(oldVar.getLocation());
			newVar.setAssignable(oldVar.isAssignable());
			variableToLocalVariableMap.put(oldVar, newVar);
		}
		for (Var var : function.getParameters().getList()) {
			VarLocal oldVar = (VarLocal) var;
			if (var.getType().isList()) {
				// In case of list, the parameter could be a global variable
				Var newVar = ((VarExpr) call.getParameters().get(
						function.getParameters().getList().indexOf(var)))
						.getVar().getVariable();
				variableToLocalVariableMap.put(oldVar, newVar);
			} else {
				VarLocal newVar = procedure.newTempLocalVariable("",
						oldVar.getType(),
						procedure.getName() + "_" + oldVar.getName() + "_"
								+ call.getLocation().getStartLine() + "_"
								+ call.getLocation().getStartColumn());
				newVar.setIndex(oldVar.getIndex());
				newVar.setLocation(oldVar.getLocation());
				newVar.setAssignable(oldVar.isAssignable());
				variableToLocalVariableMap.put(oldVar, newVar);
			}
		}

		List<Node> nodes = new ArrayList<Node>();

		// Assign all parameters except for list
		NodeBlock newBlockNode = IrFactoryImpl.eINSTANCE.createNodeBlock();
		for (int i = 0; i < function.getParameters().getLength(); i++) {
			Var parameter = function.getParameters().getList().get(i);
			if (!parameter.getType().isList()) {
				Expression expr = call.getParameters().get(i);
				Assign assign = new Assign(
						(VarLocal) variableToLocalVariableMap
								.get(parameter),
						expr);
				newBlockNode.add(assign);
				Use.addUses(assign, expr);
			}
		}
		if (newBlockNode.getInstructions().size() > 0) {
			nodes.add(newBlockNode);
		}

		// Clone function/procedure body
		for (Node node : function.getNodes()) {
			nodes.add((Node) node.accept(inlineCloner, (Object) null));
		}

		// Remove old block and add the new ones
		NodeBlock secondBlockNodePart = IrFactoryImpl.eINSTANCE
				.createNodeBlock();

		itInstruction.remove();
		while (itInstruction.hasNext()) {
			secondBlockNodePart.add(itInstruction.next());
			itInstruction.remove();
		}

		nodes.add(secondBlockNodePart);

		for (Node node : nodes) {
			itNode.add(node);
		}

		needToSkipThisNode = true;
	}

	public boolean isInlineFunction() {
		return inlineFunction;
	}

	public boolean isInlineProcedure() {
		return inlineProcedure;
	}

	public void setInlineFunction(boolean inlineFunction) {
		this.inlineFunction = inlineFunction;
	}

	public void setInlineProcedure(boolean inlineProcedure) {
		this.inlineProcedure = inlineProcedure;
	}

	@Override
	public void visit(NodeBlock nodeBlock) {
		ListIterator<Instruction> it = nodeBlock.listIterator();
		needToSkipThisNode = false;
		while (it.hasNext() && !needToSkipThisNode) {
			Instruction instruction = it.next();
			itInstruction = it;
			instruction.accept(this);
		}
		if (needToSkipThisNode) {
			itNode.previous();
		}
	}

	@Override
	public void visit(Call call) {
		// Function case
		if (!call.getProcedure().getReturnType().isVoid() && inlineFunction) {
			returnVariableOfCurrentFunction = call.getTarget();
			inline(call);
			returnVariableOfCurrentFunction = null;
		}

		// Procedure case
		if (call.getProcedure().getReturnType().isVoid() && inlineProcedure) {
			inline(call);
		}
	}

	@Override
	public void visit(NodeWhile nodeWhile) {
		ListIterator<Node> oldNodeIterator = itNode;
		visit(nodeWhile.getNodes());
		itNode = oldNodeIterator;
		visit(nodeWhile.getJoinNode());
	}

	@Override
	public void visit(NodeIf nodeIf) {
		ListIterator<Node> oldNodeIterator = itNode;
		visit(nodeIf.getThenNodes());
		visit(nodeIf.getElseNodes());
		itNode = oldNodeIterator;
		visit(nodeIf.getJoinNode());
	}

}
