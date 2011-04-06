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

import net.sf.orcc.backends.instructions.InstructionsFactory;
import net.sf.orcc.backends.instructions.InstTernary;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.impl.InstructionInterpreter;
import net.sf.orcc.ir.impl.NodeInterpreter;
import net.sf.orcc.ir.util.AbstractActorVisitor;

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
		public Object interpret(InstAssign assign, Object... args) {
			Var target = variableToLocalVariableMap.get(assign.getTarget());
			Expression value = (Expression) assign.getValue()
					.accept(this, args);
			InstAssign a = IrFactory.eINSTANCE.createInstAssign(
					assign.getLocation(), target, value);
			return a;
		}

		@Override
		public Object interpret(ExprBinary expr, Object... args) {
			Expression e1 = (Expression) expr.getE1().accept(this, args);
			Expression e2 = (Expression) expr.getE2().accept(this, args);
			ExprBinary e = IrFactory.eINSTANCE.createExprBinary(e1,
					expr.getOp(), e2, expr.getType());
			return e;
		}

		@Override
		public Object interpret(NodeBlock node, Object... args) {
			NodeBlock nodeBlock = IrFactory.eINSTANCE.createNodeBlock();
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
		public Object interpret(ExprBool expr, Object... args) {
			ExprBool e = IrFactory.eINSTANCE.createExprBool(expr.isValue());
			return e;
		}

		@Override
		public Object interpret(InstCall call, Object... args) {
			Var target = variableToLocalVariableMap.get(call.getTarget());
			List<Expression> parameters = new ArrayList<Expression>();
			for (Expression parameter : call.getParameters()) {
				parameters.add((Expression) parameter.accept(this, args));
			}
			InstCall c = IrFactory.eINSTANCE.createInstCall(call.getLocation(),
					target, call.getProcedure(), parameters);
			return c;
		}

		@Override
		public Object interpret(ExprFloat expr, Object... args) {
			ExprFloat e = IrFactory.eINSTANCE.createExprFloat();
			e.setValue(expr.getValue());
			return e;
		}

		@Override
		public Object interpret(NodeIf node, Object... args) {
			Expression condition = (Expression) node.getCondition().accept(
					this, args);
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

			NodeIf nodeIf = IrFactory.eINSTANCE.createNodeIf();
			nodeIf.setLocation(node.getLocation());
			node.setCondition(condition);
			node.getThenNodes().addAll(thenNodes);
			node.getElseNodes().addAll(elseNodes);
			node.setJoinNode(joinNode);

			return nodeIf;
		}

		@Override
		public Object interpret(ExprInt expr, Object... args) {
			ExprInt e = IrFactory.eINSTANCE.createExprInt(expr.getValue());
			return e;
		}

		@Override
		public Object interpret(ExprList expr, Object... args) {
			List<Expression> expressions = new ArrayList<Expression>();
			for (Expression e : expr.getValue()) {
				expressions.add((Expression) e.accept(this, args));
			}
			ExprList listExpr = IrFactory.eINSTANCE.createExprList(expressions);
			return listExpr;
		}

		@Override
		public Object interpret(InstLoad load, Object... args) {
			Var target = variableToLocalVariableMap.get(load.getTarget());
			List<Expression> indexes = new ArrayList<Expression>();
			for (Expression index : load.getIndexes()) {
				indexes.add((Expression) index.accept(this, args));
			}
			InstLoad l;
			Var sourceVariable = load.getSource().getVariable();
			if (sourceVariable.isGlobal()) {
				l = IrFactory.eINSTANCE.createInstLoad(load.getLocation(),
						target, load.getSource().getVariable(), indexes);
			} else {
				l = IrFactory.eINSTANCE.createInstLoad(load.getLocation(),
						target, sourceVariable, indexes);
			}
			return l;
		}

		@Override
		public Object interpret(InstPhi phi, Object... args) {
			Var target = variableToLocalVariableMap.get(phi.getTarget());
			List<Expression> values = new ArrayList<Expression>();
			for (Expression value : phi.getValues()) {
				values.add((Expression) value.accept(this, args));
			}
			InstPhi p = IrFactory.eINSTANCE.createInstPhi(phi.getLocation(),
					target, values);
			return p;
		}

		@Override
		public Object interpret(InstReturn returnInst, Object... args) {
			if (returnInst.getValue() != null) {
				Expression value = (Expression) returnInst.getValue().accept(
						this, args);
				InstAssign a = IrFactory.eINSTANCE.createInstAssign(
						returnVariableOfCurrentFunction, value);
				return a;
			} else {
				return null;
			}

		}

		@Override
		public Object interpret(InstSpecific specific, Object... args) {
			return specific;
		}

		@Override
		public Object interpret(InstStore store, Object... args) {
			Var target = store.getTarget().getVariable();
			if (!target.isGlobal()) {
				target = variableToLocalVariableMap.get(target);
			}
			List<Expression> indexes = new ArrayList<Expression>();
			for (Expression index : store.getIndexes()) {
				indexes.add((Expression) index.accept(this, args));
			}
			Expression value = (Expression) store.getValue().accept(this, args);
			InstStore s = IrFactory.eINSTANCE.createInstStore(
					store.getLocation(), target, indexes, value);
			return s;
		}

		@Override
		public Object interpret(ExprString expr, Object... args) {
			ExprString stringExpr = IrFactory.eINSTANCE.createExprString(expr
					.getValue());
			return stringExpr;
		}

		public Object interpret(InstTernary ternaryOperation,
				Object... args) {
			Var target = variableToLocalVariableMap.get(ternaryOperation
					.getTarget());

			Expression conditionValue = (Expression) ternaryOperation
					.getConditionValue().accept(this, args);
			Expression trueValue = (Expression) ternaryOperation.getTrueValue()
					.accept(this, args);
			Expression falseValue = (Expression) ternaryOperation
					.getFalseValue().accept(this, args);

			InstTernary t = InstructionsFactory.eINSTANCE
					.createInstTernary(target, conditionValue, trueValue,
							falseValue);
			return t;
		}

		@Override
		public Object interpret(ExprUnary expr, Object... args) {
			Expression expression = (Expression) expr.getExpr().accept(this,
					args);
			ExprUnary unaryExpr = IrFactory.eINSTANCE.createExprUnary(
					expr.getOp(), expression, expr.getType());
			return unaryExpr;
		}

		@Override
		public Object interpret(ExprVar expr, Object... args) {
			Var newVar = variableToLocalVariableMap.get(expr.getUse()
					.getVariable());
			ExprVar varExpr = IrFactory.eINSTANCE.createExprVar(newVar);
			return varExpr;
		}

		@Override
		public Object interpret(NodeWhile node, Object... args) {
			Expression condition = (Expression) node.getCondition().accept(
					this, args);
			List<Node> nodes = new ArrayList<Node>();
			for (Node n : node.getNodes()) {
				nodes.add((Node) n.accept(this, args));
			}
			NodeBlock joinNode = (NodeBlock) node.getJoinNode().accept(this,
					args);

			NodeWhile nodeWhile = IrFactory.eINSTANCE.createNodeWhile();
			nodeWhile.setLocation(node.getLocation());
			nodeWhile.setCondition(condition);
			nodeWhile.getNodes().addAll(nodes);
			nodeWhile.setJoinNode(joinNode);

			return nodeWhile;
		}

	}

	protected InlineCloner inlineCloner;

	private boolean inlineFunction;

	private boolean inlineProcedure;

	private boolean needToSkipThisNode;

	private Var returnVariableOfCurrentFunction;

	protected Map<Var, Var> variableToLocalVariableMap;

	public InlineTransformation(boolean inlineProcedure, boolean inlineFunction) {
		this.inlineProcedure = inlineProcedure;
		this.inlineFunction = inlineFunction;
		inlineCloner = new InlineCloner();
	}

	private void inline(InstCall call) {
		// The function or the procedure
		Procedure function = call.getProcedure();

		// Set the function/procedure to external thus it will not be printed
		function.setNative(true);

		// Create a new local variable to all function/procedure's variable
		// except for list (reference is using)
		variableToLocalVariableMap = new HashMap<Var, Var>();
		for (Var var : function.getLocals()) {
			Var newVar = procedure.newTempLocalVariable("", var.getType(),
					procedure.getName() + "_" + var.getName() + "_"
							+ call.getLocation().getStartLine() + "_"
							+ call.getLocation().getStartColumn());
			newVar.setIndex(var.getIndex());
			newVar.setLocation(var.getLocation());
			newVar.setAssignable(var.isAssignable());
			variableToLocalVariableMap.put(var, newVar);
		}
		for (Var var : function.getParameters()) {
			if (var.getType().isList()) {
				// In case of list, the parameter could be a global variable
				Var newVar = ((ExprVar) call.getParameters().get(
						function.getParameters().indexOf(var))).getUse()
						.getVariable();
				variableToLocalVariableMap.put(var, newVar);
			} else {
				Var newVar = procedure.newTempLocalVariable("", var.getType(),
						procedure.getName() + "_" + var.getName() + "_"
								+ call.getLocation().getStartLine() + "_"
								+ call.getLocation().getStartColumn());
				newVar.setIndex(var.getIndex());
				newVar.setLocation(var.getLocation());
				newVar.setAssignable(var.isAssignable());
				variableToLocalVariableMap.put(var, newVar);
			}
		}

		List<Node> nodes = new ArrayList<Node>();

		// Assign all parameters except for list
		NodeBlock newBlockNode = IrFactory.eINSTANCE.createNodeBlock();
		for (int i = 0; i < function.getParameters().size(); i++) {
			Var parameter = function.getParameters().get(i);
			if (!parameter.getType().isList()) {
				Expression expr = call.getParameters().get(i);
				InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
						variableToLocalVariableMap.get(parameter), expr);
				newBlockNode.add(assign);
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
		NodeBlock secondBlockNodePart = IrFactory.eINSTANCE.createNodeBlock();

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
	public void visit(InstCall call) {
		// Function case
		if (!call.getProcedure().getReturnType().isVoid() && inlineFunction) {
			returnVariableOfCurrentFunction = call.getTarget().getVariable();
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
