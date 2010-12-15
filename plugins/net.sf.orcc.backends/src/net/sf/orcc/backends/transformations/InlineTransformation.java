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

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.xlim.instructions.TernaryOperation;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.FloatExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.InstructionInterpreter;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeInterpreter;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;

/**
 * This class defines an actor transformation that inline the functions and/or
 * the procedures
 * 
 * @author Herve Yviquel
 * 
 */
public class InlineTransformation extends AbstractActorTransformation {

	protected class InlineCloner implements NodeInterpreter,
			InstructionInterpreter, ExpressionInterpreter {

		@Override
		public Object interpret(Assign assign, Object... args) {
			LocalVariable target = (LocalVariable) variableToLocalVariableMap
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
		public Object interpret(BlockNode node, Object... args) {
			BlockNode blockNode = new BlockNode(node.getLocation(), procedure);
			for (Instruction instruction : node.getInstructions()) {
				Instruction i = (Instruction) instruction.accept(this, args);
				if (i != null) {
					blockNode.add(i);
				}
			}
			return blockNode;
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			BoolExpr e = new BoolExpr(expr.getValue());
			return e;
		}

		@Override
		public Object interpret(Call call, Object... args) {
			LocalVariable target = (LocalVariable) variableToLocalVariableMap
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
		public Object interpret(IfNode node, Object... args) {
			Expression condition = (Expression) node.getValue().accept(this,
					args);
			List<CFGNode> thenNodes = new ArrayList<CFGNode>();
			for (CFGNode n : node.getThenNodes()) {
				thenNodes.add((CFGNode) n.accept(this, args));
			}
			List<CFGNode> elseNodes = new ArrayList<CFGNode>();
			for (CFGNode n : node.getElseNodes()) {
				elseNodes.add((CFGNode) n.accept(this, args));
			}
			BlockNode joinNode = (BlockNode) node.getJoinNode().accept(this,
					args);
			IfNode ifNode = new IfNode(node.getLocation(), procedure,
					condition, thenNodes, elseNodes, joinNode);
			Use.addUses(ifNode, condition);
			return ifNode;
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
			LocalVariable target = (LocalVariable) variableToLocalVariableMap
					.get(load.getTarget());
			List<Expression> indexes = new ArrayList<Expression>();
			for (Expression index : load.getIndexes()) {
				indexes.add((Expression) index.accept(this, args));
			}
			Load l;
			Variable sourceVariable = load.getSource().getVariable();
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
		public Object interpret(Peek peek, Object... args) {
			throw new OrccRuntimeException(peek.getLocation(),
					"Error: Peek call in function or procedure body.");
		}

		@Override
		public Object interpret(PhiAssignment phi, Object... args) {
			LocalVariable target = (LocalVariable) variableToLocalVariableMap
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
		public Object interpret(Read read, Object... args) {
			throw new OrccRuntimeException(read.getLocation(),
					"Error: Read call in function or procedure body.");
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
			Variable target;
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
			LocalVariable target = (LocalVariable) variableToLocalVariableMap
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
			Variable newVar = variableToLocalVariableMap.get(expr.getVar()
					.getVariable());
			VarExpr varExpr = new VarExpr(new Use(newVar));
			return varExpr;
		}

		@Override
		public Object interpret(WhileNode node, Object... args) {
			Expression condition = (Expression) node.getValue().accept(this,
					args);
			List<CFGNode> nodes = new ArrayList<CFGNode>();
			for (CFGNode n : node.getNodes()) {
				nodes.add((CFGNode) n.accept(this, args));
			}
			BlockNode joinNode = (BlockNode) node.getJoinNode().accept(this,
					args);
			WhileNode whileNode = new WhileNode(node.getLocation(), procedure,
					condition, nodes, joinNode);
			Use.addUses(whileNode, condition);
			return whileNode;
		}

		@Override
		public Object interpret(Write write, Object... args) {
			throw new OrccRuntimeException(write.getLocation(),
					"Error: Write call in function or procedure body.");
		}

	}

	protected InlineCloner inlineCloner;

	private boolean inlineFunction;

	private boolean inlineProcedure;

	private boolean needToSkipThisNode;

	private LocalVariable returnVariableOfCurrentFunction;

	protected Map<Variable, Variable> variableToLocalVariableMap;

	public InlineTransformation(boolean inlineProcedure, boolean inlineFunction) {
		this.inlineProcedure = inlineProcedure;
		this.inlineFunction = inlineFunction;
		inlineCloner = new InlineCloner();
	}

	private void inline(Call call) {
		// The function or the procedure
		Procedure function = call.getProcedure();

		// Set the function/procedure to external thus it will not be printed
		function.setExternal(true);

		// Create a new local variable to all function/procedure's variable
		// except for list (reference is using)
		variableToLocalVariableMap = new HashMap<Variable, Variable>();
		for (Variable var : function.getLocals().getList()) {
			LocalVariable oldVar = (LocalVariable) var;
			LocalVariable newVar = procedure.newTempLocalVariable("",
					oldVar.getType(), oldVar.getName());
			newVar.setIndex(oldVar.getIndex());
			newVar.setLocation(oldVar.getLocation());
			newVar.setAssignable(oldVar.isAssignable());
			variableToLocalVariableMap.put(oldVar, newVar);
		}
		for (Variable var : function.getParameters().getList()) {
			LocalVariable oldVar = (LocalVariable) var;
			if (var.getType().isList()) {
				// In case of list, the parameter could be a global variable
				Variable newVar = ((VarExpr) call.getParameters().get(
						function.getParameters().getList().indexOf(var)))
						.getVar().getVariable();
				variableToLocalVariableMap.put(oldVar, newVar);
			} else {
				LocalVariable newVar = procedure.newTempLocalVariable("",
						oldVar.getType(), oldVar.getName());
				newVar.setIndex(oldVar.getIndex());
				newVar.setLocation(oldVar.getLocation());
				newVar.setAssignable(oldVar.isAssignable());
				variableToLocalVariableMap.put(oldVar, newVar);
			}
		}

		List<CFGNode> nodes = new ArrayList<CFGNode>();

		// Assign all parameters except for list
		BlockNode newBlockNode = new BlockNode(procedure);
		for (int i = 0; i < function.getParameters().getLength(); i++) {
			Variable parameter = function.getParameters().getList().get(i);
			if (!parameter.getType().isList()) {
				Expression expr = call.getParameters().get(i);
				Assign assign = new Assign(
						(LocalVariable) variableToLocalVariableMap
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
		for (CFGNode node : function.getNodes()) {
			nodes.add((CFGNode) node.accept(inlineCloner, (Object) null));
		}

		// Remove old block and add the new ones
		BlockNode secondBlockNodePart = new BlockNode(procedure);

		instructionIterator.remove();
		while (instructionIterator.hasNext()) {
			secondBlockNodePart.add(instructionIterator.next());
			instructionIterator.remove();
		}

		nodes.add(secondBlockNodePart);

		for (CFGNode node : nodes) {
			nodeIterator.add(node);
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
	public void visit(BlockNode blockNode) {
		ListIterator<Instruction> it = blockNode.listIterator();
		needToSkipThisNode = false;
		while (it.hasNext() && !needToSkipThisNode) {
			Instruction instruction = it.next();
			instructionIterator = it;
			instruction.accept(this);
		}
		if (needToSkipThisNode) {
			nodeIterator.previous();
		}
	}

	@Override
	public void visit(Call call) {
		// Function case
		if (!call.getProcedure().getReturnType().isVoid() && inlineFunction) {
			returnVariableOfCurrentFunction = call.getTarget();
			inline(call);
		}

		// Procedure case
		if (call.getProcedure().getReturnType().isVoid() && inlineProcedure) {
			inline(call);
		}
	}
	
	@Override
	public void visit(WhileNode whileNode) {
		ListIterator<CFGNode> oldNodeIterator = nodeIterator;
		visit(whileNode.getNodes());
		nodeIterator = oldNodeIterator;
		visit(whileNode.getJoinNode());
	}
	
	@Override
	public void visit(IfNode ifNode) {
		ListIterator<CFGNode> oldNodeIterator = nodeIterator;
		visit(ifNode.getThenNodes());
		visit(ifNode.getElseNodes());
		nodeIterator = oldNodeIterator;
		visit(ifNode.getJoinNode());
	}

}
