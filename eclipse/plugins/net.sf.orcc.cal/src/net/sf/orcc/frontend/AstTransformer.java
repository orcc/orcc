/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.cal.cal.AnnotationArgument;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstAnnotation;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.ExpressionBinary;
import net.sf.orcc.cal.cal.ExpressionBoolean;
import net.sf.orcc.cal.cal.ExpressionCall;
import net.sf.orcc.cal.cal.ExpressionIf;
import net.sf.orcc.cal.cal.ExpressionIndex;
import net.sf.orcc.cal.cal.ExpressionInteger;
import net.sf.orcc.cal.cal.ExpressionList;
import net.sf.orcc.cal.cal.ExpressionString;
import net.sf.orcc.cal.cal.ExpressionUnary;
import net.sf.orcc.cal.cal.ExpressionVariable;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.Statement;
import net.sf.orcc.cal.cal.StatementAssign;
import net.sf.orcc.cal.cal.StatementCall;
import net.sf.orcc.cal.cal.StatementElsif;
import net.sf.orcc.cal.cal.StatementForeach;
import net.sf.orcc.cal.cal.StatementIf;
import net.sf.orcc.cal.cal.StatementWhile;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.services.Evaluator;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.Annotation;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;

/**
 * This class transforms an AST actor to its IR equivalent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AstTransformer extends CalSwitch<EObject> {

	/**
	 * This class transforms an AST expression into one or more IR instructions
	 * and/or nodes, and returns an IR expression.
	 * 
	 */
	private class ExprTransformer extends CalSwitch<Expression> {

		private List<Expression> indexes;

		private Var target;

		public ExprTransformer() {
			indexes = new ArrayList<Expression>();
		}

		public ExprTransformer(Var target) {
			this();
			this.target = target;
		}

		public ExprTransformer(Var target, List<Expression> indexes) {
			this.target = target;
			this.indexes = indexes;
		}

		@Override
		public Expression caseExpressionBinary(ExpressionBinary expression) {
			OpBinary op = OpBinary.getOperator(expression.getOperator());
			Expression e1 = doSwitch(expression.getLeft());
			Expression e2 = doSwitch(expression.getRight());

			return IrFactory.eINSTANCE.createExprBinary(e1, op, e2,
					Typer.getType(expression));
		}

		@Override
		public Expression caseExpressionBoolean(ExpressionBoolean expression) {
			boolean value = expression.isValue();
			return IrFactory.eINSTANCE.createExprBool(value);
		}

		@Override
		public Expression caseExpressionCall(ExpressionCall call) {
			int lineNumber = Util.getLocation(call);

			// retrieve IR procedure
			Function function = call.getFunction();
			Procedure calledProcedure = Frontend.getMapping(function);

			// generates a new target
			Var target = procedure.newTempLocalVariable(
					calledProcedure.getReturnType(),
					"call_" + calledProcedure.getName());

			// creates call with spilling code around it
			createCall(lineNumber, target, calledProcedure,
					call.getParameters());

			// return local variable
			Expression varExpr = IrFactory.eINSTANCE.createExprVar(target);
			return varExpr;
		}

		@Override
		public Expression caseExpressionIf(ExpressionIf expression) {
			int lineNumber = Util.getLocation(expression);

			Expression condition = doSwitch(expression.getCondition());

			Var currentTarget = target;
			List<Expression> currentIndexes = indexes;

			Type type = Typer.getType(expression);
			// create a temporary variable if the target is null
			// or it is not null and the expression does not return a list
			if (target == null || !type.isList()) {
				// type of the new target will be the same as the type of the
				// existing target (if it is a scalar) or its innermost type (if
				// it is a list)
				if (target != null) {
					type = target.getType();
					if (type.isList()) {
						type = ((TypeList) type).getInnermostType();
					}
				}

				target = procedure.newTempLocalVariable(type, "_tmp_if");
				indexes = new ArrayList<Expression>(0);
			}

			// transforms "then" statements and "else" statements
			List<Node> thenNodes = getNodes(expression.getThen());
			List<Node> elseNodes = getNodes(expression.getElse());

			NodeIf node = IrFactoryImpl.eINSTANCE.createNodeIf();
			node.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
			node.setLineNumber(lineNumber);
			node.setCondition(condition);
			node.getThenNodes().addAll(thenNodes);
			node.getElseNodes().addAll(elseNodes);
			procedure.getNodes().add(node);

			Expression varExpr = IrFactory.eINSTANCE.createExprVar(target);

			// restores target and indexes
			target = currentTarget;
			indexes = currentIndexes;

			// return the expression
			return varExpr;
		}

		@Override
		public Expression caseExpressionIndex(ExpressionIndex expression) {
			// we always load in this case
			int lineNumber = Util.getLocation(expression);
			Variable variable = expression.getSource().getVariable();
			Var var = Frontend.getMapping(variable);

			List<Expression> indexes = transformExpressions(expression
					.getIndexes());

			Var target = procedure.newTempLocalVariable(
					Typer.getType(expression), "local_" + var.getName());

			InstLoad load = IrFactory.eINSTANCE.createInstLoad(lineNumber,
					target, var, indexes);
			addInstruction(load);

			Expression varExpr = IrFactory.eINSTANCE.createExprVar(target);
			return varExpr;
		}

		@Override
		public Expression caseExpressionInteger(ExpressionInteger expression) {
			long value = expression.getValue();
			return IrFactory.eINSTANCE.createExprInt(value);
		}

		@Override
		public Expression caseExpressionList(ExpressionList astExpression) {
			Var currentTarget = target;
			List<Expression> currentIndexes = indexes;

			List<AstExpression> expressions = astExpression.getExpressions();
			List<Generator> generators = astExpression.getGenerators();

			checkTarget(expressions, generators);

			if (generators.isEmpty()) {
				transformListSimple(expressions);
			} else {
				transformListGenerators(expressions, generators);
			}

			Expression expression = IrFactory.eINSTANCE.createExprVar(target);

			// restores target and indexes
			target = currentTarget;
			indexes = currentIndexes;

			// return the expression
			return expression;
		}

		@Override
		public Expression caseExpressionString(ExpressionString expression) {
			return IrFactory.eINSTANCE.createExprString(OrccUtil
					.getEscapedString(expression.getValue()));
		}

		@Override
		public Expression caseExpressionUnary(ExpressionUnary expression) {
			OpUnary op = OpUnary.getOperator(expression.getUnaryOperator());
			Expression expr = doSwitch(expression.getExpression());

			if (OpUnary.NUM_ELTS == op) {
				TypeList typeList = (TypeList) expr.getType();
				return IrFactory.eINSTANCE.createExprInt(typeList.getSize());
			}

			return IrFactory.eINSTANCE.createExprUnary(op, expr,
					Typer.getType(expression));
		}

		@Override
		public Expression caseExpressionVariable(ExpressionVariable expression) {
			Variable variable = expression.getValue().getVariable();
			Var var = Frontend.getMapping(variable);

			if (var.getType().isList()) {
				Expression varExpr = IrFactory.eINSTANCE.createExprVar(var);
				return varExpr;
			} else {
				if (procedure != null) {
					if (var.isGlobal()) {
						Var global = var;
						var = procedure.getLocal("local_" + global.getName());
						if (var == null) {
							var = procedure.newTempLocalVariable(
									global.getType(),
									"local_" + global.getName());
						}
						addInstruction(IrFactory.eINSTANCE.createInstLoad(var,
								global));
					}
				}

				return IrFactory.eINSTANCE.createExprVar(var);
			}
		}

		/**
		 * Checks that the current target is not <code>null</code>. If it is,
		 * this method initializes it according to the expressions and
		 * generators.
		 * 
		 * @param expressions
		 *            a list of expressions
		 * @param generators
		 *            a list of generators
		 */
		private void checkTarget(List<AstExpression> expressions,
				List<Generator> generators) {
			if (target != null) {
				return;
			}

			int size = 1;

			// size of generators
			for (Generator generator : generators) {
				int lower = Evaluator.getIntValue(generator.getLower());
				int higher = Evaluator.getIntValue(generator.getHigher());
				size *= (higher - lower) + 1;
			}

			// size of expressions
			Type type = Typer.getType(expressions);
			size *= expressions.size();

			Type listType = IrFactory.eINSTANCE.createTypeList(size, type);
			target = procedure.newTempLocalVariable(listType, "_list");
		}

		/**
		 * Returns a list of CFG nodes from the given expression. This method
		 * creates a new block node to hold the statements created when
		 * translating the expression, transforms the expression, and transfers
		 * the nodes created to a new list that is the result.
		 * 
		 * @param astExpression
		 *            an AST expression
		 * @return a list of CFG nodes
		 */
		private List<Node> getNodes(AstExpression astExpression) {
			int lineNumber = Util.getLocation(astExpression);
			List<Node> nodes = procedure.getNodes();

			int first = nodes.size();
			nodes.add(IrFactoryImpl.eINSTANCE.createNodeBlock());

			Expression value = doSwitch(astExpression);
			createAssignOrStore(lineNumber, target, indexes, value);

			int last = nodes.size();

			// moves selected CFG nodes from "nodes" list to resultNodes
			List<Node> subList = nodes.subList(first, last);
			List<Node> resultNodes = new ArrayList<Node>(subList);
			subList.clear();

			return resultNodes;
		}

		/**
		 * Returns a list of CFG nodes from the given list of statements. This
		 * method creates a new block node to hold the statements, transforms
		 * the statements, and transfers the nodes created to a new list that is
		 * the result.
		 * 
		 * @param statements
		 *            a list of statements
		 * @return a list of CFG nodes
		 */
		private List<Node> getNodes(List<AstExpression> astExpressions) {
			List<Node> nodes = procedure.getNodes();

			int first = nodes.size();
			nodes.add(IrFactoryImpl.eINSTANCE.createNodeBlock());

			for (AstExpression astExpression : astExpressions) {
				int lineNumber = Util.getLocation(astExpression);
				Expression value = doSwitch(astExpression);
				createAssignOrStore(lineNumber, target, indexes, value);
			}

			int last = nodes.size();

			// moves selected CFG nodes from "nodes" list to resultNodes
			List<Node> subList = nodes.subList(first, last);
			List<Node> resultNodes = new ArrayList<Node>(subList);
			subList.clear();

			return resultNodes;
		}

		/**
		 * Transforms the given expressions and generators.
		 * 
		 * @param expressions
		 *            a list of expressions
		 * @param generators
		 *            a list of generators
		 */
		private void transformListGenerators(List<AstExpression> expressions,
				List<Generator> generators) {
			Var currentTarget = target;
			List<Expression> currentIndexes = indexes;

			indexes = new ArrayList<Expression>(currentIndexes);

			// first add local variables
			Expression index = null;
			for (Generator generator : generators) {
				Variable Variable = generator.getVariable();
				Var loopVar = caseVariableLocal(Variable);
				procedure.getLocals().add(loopVar);

				int lower = Evaluator.getIntValue(generator.getLower());
				Expression thisIndex = IrFactory.eINSTANCE
						.createExprVar(loopVar);
				if (lower != 0) {
					thisIndex = IrFactory.eINSTANCE.createExprBinary(thisIndex,
							OpBinary.MINUS,
							IrFactory.eINSTANCE.createExprInt(lower),
							thisIndex.getType());
				}

				int higher = Evaluator.getIntValue(generator.getHigher());

				if (index == null) {
					index = thisIndex;
				} else {
					index = IrFactory.eINSTANCE.createExprBinary(
							IrFactory.eINSTANCE.createExprBinary(
									index,
									OpBinary.TIMES,
									IrFactory.eINSTANCE.createExprInt(higher
											- lower + 1), index.getType()),
							OpBinary.PLUS, thisIndex, index.getType());
				}
			}

			indexes.add(index);

			// translates the expression (this will form the innermost nodes)
			List<Node> nodes = getNodes(expressions);

			// build the loops from the inside out
			ListIterator<Generator> it = generators.listIterator(generators
					.size());
			while (it.hasPrevious()) {
				Generator generator = it.previous();
				int lineNumber = Util.getLocation(generator);

				// assigns the loop variable its initial value
				Variable variable = generator.getVariable();
				Var loopVar = Frontend.getMapping(variable);

				// condition
				AstExpression astHigher = generator.getHigher();
				Expression higher = doSwitch(astHigher);
				Expression condition = IrFactory.eINSTANCE.createExprBinary(
						IrFactory.eINSTANCE.createExprVar(loopVar),
						OpBinary.LE, higher,
						IrFactory.eINSTANCE.createTypeBool());

				// add increment to body
				NodeBlock block = IrUtil.getLast(nodes);
				InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
						lineNumber, loopVar, IrFactory.eINSTANCE
								.createExprBinary(IrFactory.eINSTANCE
										.createExprVar(loopVar), OpBinary.PLUS,
										IrFactory.eINSTANCE.createExprInt(1),
										loopVar.getType()));
				block.add(assign);

				// create while
				NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
				nodeWhile
						.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
				nodeWhile.setCondition(condition);
				nodeWhile.getNodes().addAll(nodes);

				// create assign
				block = IrFactoryImpl.eINSTANCE.createNodeBlock();
				AstExpression astLower = generator.getLower();
				Expression lower = doSwitch(astLower);
				InstAssign assignInit = IrFactory.eINSTANCE.createInstAssign(
						lineNumber, loopVar, lower);
				block.add(assignInit);

				// nodes
				nodes = new ArrayList<Node>(2);
				nodes.add(block);
				nodes.add(nodeWhile);
			}

			// add the outer while node
			procedure.getNodes().addAll(nodes);

			// restores target and indexes
			target = currentTarget;
			indexes = currentIndexes;
		}

		/**
		 * Transforms the list of expressions of an AstExpressionList (without
		 * generators).
		 * 
		 * @param expressions
		 *            a list of AST expressions
		 */
		private void transformListSimple(List<AstExpression> expressions) {
			Var currentTarget = target;
			List<Expression> currentIndexes = indexes;

			int i = 0;
			for (AstExpression expression : expressions) {
				int lineNumber = Util.getLocation(expression);

				indexes = new ArrayList<Expression>(IrUtil.copy(currentIndexes));
				indexes.add(IrFactory.eINSTANCE.createExprInt(i));
				i++;

				Expression value = doSwitch(expression);

				createAssignOrStore(lineNumber, target, indexes, value);
			}

			// restores target and indexes
			target = currentTarget;
			indexes = currentIndexes;
		}
	}

	/**
	 * This class transforms the expression passed to a print/println procedure
	 * to a list of IR expressions.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class PrintlnTransformer extends CalSwitch<Object> {

		private Object object;

		private List<Expression> parameters;

		public PrintlnTransformer(List<Expression> parameters) {
			this.parameters = parameters;
			this.object = new Object();
		}

		@Override
		public Object caseAstExpression(AstExpression astExpression) {
			ExprTransformer transformer = new ExprTransformer();
			Expression expression = transformer.doSwitch(astExpression);
			parameters.add(expression);

			return object;
		}

		@Override
		public Object caseExpressionBinary(ExpressionBinary astExpression) {
			OpBinary op = OpBinary.getOperator(astExpression.getOperator());
			if (op == OpBinary.PLUS) {
				doSwitch(astExpression.getLeft());
				ExprTransformer transformer = new ExprTransformer();
				Expression expression = transformer.doSwitch(astExpression
						.getRight());
				parameters.add(expression);

				return object;
			}

			// fall back to general case
			return null;
		}

	}

	/**
	 * This class transforms an AST statement into one or more IR instructions
	 * and/or nodes. It returns null because it appends the instructions/nodes
	 * directly to the {@link #nodes} field.
	 * 
	 */
	private class StmtTransformer extends CalSwitch<Object> {

		private Object object;

		public StmtTransformer() {
			this.object = new Object();
		}

		@Override
		public Object caseStatementAssign(StatementAssign assign) {
			int lineNumber = Util.getLocation(assign);

			// get target
			Variable variable = assign.getTarget().getVariable();
			Var target = Frontend.getMapping(variable);

			// transform indexes and value
			List<Expression> indexes = transformExpressions(assign.getIndexes());

			ExprTransformer transformer = new ExprTransformer(target, indexes);
			Expression value = transformer.doSwitch(assign.getValue());
			createAssignOrStore(lineNumber, target, indexes, value);

			return object;
		}

		@Override
		public Object caseStatementCall(StatementCall call) {
			int lineNumber = Util.getLocation(call);

			// retrieve IR procedure
			AstProcedure astProcedure = call.getProcedure();
			// special case if the procedure is a built-in procedure
			if (astProcedure.eContainer() == null) {
				transformBuiltinProcedure(call);
				return null;
			}

			// retrieve IR procedure
			Procedure procedure = Frontend.getMapping(astProcedure);

			// creates call with spilling code around it
			createCall(lineNumber, null, procedure, call.getParameters());

			return object;
		}

		@Override
		public Object caseStatementForeach(StatementForeach foreach) {
			int lineNumber = Util.getLocation(foreach);

			// creates loop variable and assigns it
			Variable variable = foreach.getVariable();
			Var loopVar = caseVariableLocal(variable);
			procedure.getLocals().add(loopVar);

			AstExpression astLower = foreach.getLower();
			ExprTransformer transformer = new ExprTransformer();
			Expression lower = transformer.doSwitch(astLower);
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
					lineNumber, loopVar, lower);
			addInstruction(assign);

			// condition
			AstExpression astHigher = foreach.getHigher();
			Expression higher = transformer.doSwitch(astHigher);
			Expression condition = IrFactory.eINSTANCE.createExprBinary(
					IrFactory.eINSTANCE.createExprVar(loopVar), OpBinary.LE,
					higher, IrFactory.eINSTANCE.createTypeBool());

			// body
			List<Node> nodes = getNodes(foreach.getStatements());
			NodeBlock block = IrUtil.getLast(nodes);
			assign = IrFactory.eINSTANCE.createInstAssign(lineNumber, loopVar,
					IrFactory.eINSTANCE.createExprBinary(
							IrFactory.eINSTANCE.createExprVar(loopVar),
							OpBinary.PLUS,
							IrFactory.eINSTANCE.createExprInt(1),
							loopVar.getType()));
			block.add(assign);

			// create while
			NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
			nodeWhile.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
			nodeWhile.setLineNumber(lineNumber);
			nodeWhile.setCondition(condition);
			nodeWhile.getNodes().addAll(nodes);

			procedure.getNodes().add(nodeWhile);

			return object;
		}

		@Override
		public Object caseStatementIf(StatementIf stmtIf) {
			int lineNumber = Util.getLocation(stmtIf);

			ExprTransformer transformer = new ExprTransformer();
			Expression condition = transformer.doSwitch(stmtIf.getCondition());

			// transforms "then" statements and "else" statements
			List<Node> thenNodes = getNodes(stmtIf.getThen());

			// creates if and adds it to procedure
			NodeIf node = IrFactoryImpl.eINSTANCE.createNodeIf();
			procedure.getNodes().add(node);
			node.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
			node.setLineNumber(lineNumber);
			node.setCondition(condition);
			node.getThenNodes().addAll(thenNodes);

			// add elsif statements
			for (StatementElsif elsif : stmtIf.getElsifs()) {
				// saves outerIf, creates new if, and adds it to else nodes
				NodeIf outerIf = node;
				node = IrFactoryImpl.eINSTANCE.createNodeIf();
				outerIf.getElseNodes().add(node);

				node.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
				node.setLineNumber(lineNumber);

				transformer = new ExprTransformer();
				condition = transformer.doSwitch(elsif.getCondition());
				node.setCondition(condition);

				thenNodes = getNodes(elsif.getThen());
				node.getThenNodes().addAll(thenNodes);
			}

			// add else nodes to current if
			List<Node> elseNodes = getNodes(stmtIf.getElse());
			node.getElseNodes().addAll(elseNodes);

			return object;
		}

		@Override
		public Object caseStatementWhile(StatementWhile stmtWhile) {
			int lineNumber = Util.getLocation(stmtWhile);

			// to help track the instructions added
			NodeBlock block = procedure.getLast();
			List<Instruction> instructions = block.getInstructions();
			int first = instructions.size();

			ExprTransformer transformer = new ExprTransformer();
			Expression condition = transformer.doSwitch(stmtWhile
					.getCondition());
			int last = instructions.size();

			// the body
			List<Node> nodes = getNodes(stmtWhile.getStatements());

			// copy load instructions
			List<Instruction> subList = instructions.subList(first, last);
			for (Instruction instruction : subList) {
				if (instruction instanceof InstLoad) {
					InstLoad load = (InstLoad) instruction;
					load = IrFactory.eINSTANCE.createInstLoad(load
							.getLineNumber(), load.getTarget().getVariable(),
							load.getSource().getVariable(),
							(List<Expression>) IrUtil.copy(load.getIndexes()));
					IrUtil.getLast(nodes).add(load);
				}
			}

			// create the while
			NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
			nodeWhile.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
			nodeWhile.setLineNumber(lineNumber);
			nodeWhile.setCondition(condition);
			nodeWhile.getNodes().addAll(nodes);

			procedure.getNodes().add(nodeWhile);

			return object;
		}

		/**
		 * Returns a list of CFG nodes from the given list of statements. This
		 * method creates a new block node to hold the statements, transforms
		 * the statements, and transfers the nodes created to a new list that is
		 * the result.
		 * 
		 * @param statements
		 *            a list of statements
		 * @return a list of CFG nodes
		 */
		private List<Node> getNodes(List<Statement> statements) {
			List<Node> nodes = procedure.getNodes();

			int first = nodes.size();
			nodes.add(IrFactoryImpl.eINSTANCE.createNodeBlock());
			transformStatements(statements);
			int last = nodes.size();

			// moves selected CFG nodes from "nodes" list to resultNodes
			List<Node> subList = nodes.subList(first, last);
			List<Node> resultNodes = new ArrayList<Node>(subList);
			subList.clear();

			return resultNodes;
		}

		/**
		 * Transforms a call to a built-in procedure (print or println). Both
		 * are transformed to a call to print, with an additional "\\n"
		 * parameter in the case of println.
		 * 
		 * @param call
		 *            an AST call statement
		 */
		private void transformBuiltinProcedure(StatementCall call) {
			int lineNumber = Util.getLocation(call);
			String name = call.getProcedure().getName();
			if ("print".equals(name) || "println".equals(name)) {
				if (print == null) {
					print = IrFactory.eINSTANCE.createProcedure("print",
							lineNumber, IrFactory.eINSTANCE.createTypeVoid());
					print.setNative(true);

					AstEntity entity = EcoreUtil2.getContainerOfType(call,
							AstEntity.class);
					Frontend.getProcedures(entity).add(print);
				}

				List<AstExpression> astParameters = call.getParameters();
				List<Expression> parameters = new ArrayList<Expression>(7);
				if (!astParameters.isEmpty()) {
					AstExpression astExpression = astParameters.get(0);
					new PrintlnTransformer(parameters).doSwitch(astExpression);
				}

				if ("println".equals(name)) {
					parameters.add(IrFactory.eINSTANCE.createExprString("\\n"));
				}

				addInstruction(IrFactory.eINSTANCE.createInstCall(lineNumber,
						(Var) null, print, parameters));
			}
		}

	}

	/**
	 * allows creation of unique names
	 */
	private int blockCount;

	private Procedure print;

	private Procedure procedure;

	/**
	 * Creates a new AST to IR transformer.
	 */
	public AstTransformer() {
	}

	/**
	 * Creates a new AST to IR transformer, which will append instructions and
	 * nodes to the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	public AstTransformer(Procedure procedure) {
		this.procedure = procedure;
	}

	/**
	 * Adds the given instruction to the last block of the current procedure.
	 * 
	 * @param instruction
	 *            an instruction
	 */
	private void addInstruction(Instruction instruction) {
		NodeBlock block = procedure.getLast();
		block.add(instruction);
	}

	/**
	 * Adds a return with the given value at the end of the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param value
	 *            an expression, possibly <code>null</code> for procedures that
	 *            do not have a return value
	 */
	public void addReturn(Procedure procedure, Expression value) {
		NodeBlock block = procedure.getLast();
		InstReturn returnInstr = IrFactory.eINSTANCE.createInstReturn(value);
		block.add(returnInstr);
	}

	@Override
	public EObject caseAstPort(AstPort astPort) {
		Type type = EcoreUtil.copy(Typer.getType(astPort));
		Port port = IrFactory.eINSTANCE.createPort(type, astPort.getName(),
				astPort.isNative());
		Frontend.putMapping(astPort, port);
		return port;
	}

	/**
	 * Transforms and adds a mapping from the given AST procedure to an IR
	 * procedure.
	 * 
	 * @param astProcedure
	 *            an AST procedure
	 * @return the IR procedure
	 */
	@Override
	public Procedure caseAstProcedure(AstProcedure astProcedure) {
		String name = astProcedure.getName();
		int lineNumber = Util.getLocation(astProcedure);

		// create procedure
		procedure = IrFactory.eINSTANCE.createProcedure(name, lineNumber,
				IrFactory.eINSTANCE.createTypeVoid());

		// set native flag
		if (astProcedure.isNative()) {
			procedure.setNative(true);
		}

		// add mapping now (in case this procedure is recursive)
		Frontend.putMapping(astProcedure, procedure);

		transformParameters(astProcedure.getParameters());
		transformLocalVariables(astProcedure.getVariables());
		transformStatements(astProcedure.getStatements());

		addReturn(procedure, null);

		return procedure;
	}

	/**
	 * Transforms and adds a mapping from the given AST function to an IR
	 * procedure.
	 * 
	 * @param function
	 *            an AST function
	 * @return the IR procedure
	 */
	@Override
	public Procedure caseFunction(Function function) {
		String name = function.getName();
		int lineNumber = Util.getLocation(function);
		Type type = Typer.getType(function);

		// create procedure
		procedure = IrFactory.eINSTANCE.createProcedure(name, lineNumber, type);

		// set native flag
		if (function.isNative()) {
			procedure.setNative(true);
		}

		// add mapping now (in case this function is recursive)
		Frontend.putMapping(function, procedure);

		transformParameters(function.getParameters());
		transformLocalVariables(function.getVariables());

		Expression value;
		if (function.isNative()) {
			value = null;
		} else {
			ExprTransformer transformer = new ExprTransformer();
			value = transformer.doSwitch(function.getExpression());
		}

		addReturn(procedure, value);

		return procedure;
	}

	@Override
	public EObject caseVariable(Variable variable) {
		EObject cter = variable.eContainer();
		if (cter instanceof AstActor || cter instanceof AstUnit) {
			return caseVariableGlobal(variable);
		} else {
			return caseVariableLocal(variable);
		}
	}

	/**
	 * Transforms a global AST variable to its IR equivalent. The initial value
	 * of an AST state variable is evaluated to a constant by
	 * {@link #exprEvaluator}.
	 * 
	 * @param variable
	 *            an AST variable
	 * @return the IR equivalent of the AST variable
	 */
	private Var caseVariableGlobal(Variable variable) {
		int lineNumber = Util.getLocation(variable);
		Type type = EcoreUtil.copy(Typer.getType(variable));
		String name = variable.getName();
		boolean assignable = !variable.isConstant();

		// retrieve initial value (may be null)
		Expression initialValue = EcoreUtil.copy(Evaluator.getValue(variable));

		// create state variable and put it in the map
		Var var = IrFactory.eINSTANCE.createVar(lineNumber, type, name,
				assignable, initialValue);
		transformAnnotations(var, variable.getAnnotations());
		Frontend.putMapping(variable, var);

		return var;
	}

	/**
	 * Transforms the given AST variable to an IR variable that has the name and
	 * type of <code>variable</code>.
	 * 
	 * @param variable
	 *            an AST variable
	 * @return the IR local variable created
	 */
	private Var caseVariableLocal(Variable variable) {
		int lineNumber = Util.getLocation(variable);
		Type type = Typer.getType(variable);
		String name = getQualifiedName(variable);
		boolean assignable = !variable.isConstant();

		// create local variable with the given name
		Var local = IrFactory.eINSTANCE.createVar(lineNumber, type, name,
				assignable, 0);

		AstExpression value = variable.getValue();
		if (value != null) {
			ExprTransformer transformer = new ExprTransformer(local);
			Expression expression = transformer.doSwitch(value);
			createAssignOrStore(lineNumber, local, null, expression);
		}

		Frontend.putMapping(variable, local);
		return local;
	}

	private void createAssignOrStore(int lineNumber, Var target,
			List<Expression> indexes, Expression value) {
		// special case for list expressions
		if (value.isVarExpr()) {
			Use use = ((ExprVar) value).getUse();
			if (use.getVariable().getType().isList()) {
				return;
			}
		}

		Instruction instruction;
		if (target.isLocal() && (indexes == null || indexes.isEmpty())) {
			instruction = IrFactory.eINSTANCE.createInstAssign(lineNumber,
					target, value);
		} else {
			instruction = IrFactory.eINSTANCE.createInstStore(lineNumber,
					target, indexes, value);
		}
		addInstruction(instruction);
	}

	/**
	 * Creates a call with the given arguments, and adds spilling code.
	 * 
	 * @param lineNumber
	 *            line number of the call
	 * @param target
	 *            target (or <code>null</code>)
	 * @param procedure
	 *            procedure being called
	 * @param expressions
	 *            arguments
	 */
	private void createCall(int lineNumber, Var target, Procedure procedure,
			List<AstExpression> expressions) {
		// transform parameters
		List<Expression> parameters = transformExpressions(expressions);

		// add call
		InstCall call = IrFactory.eINSTANCE.createInstCall(lineNumber, target,
				procedure, parameters);
		addInstruction(call);
	}

	/**
	 * Returns the qualified name for the given object.
	 * 
	 * @param obj
	 *            an object
	 * @return the qualified name for the given object
	 */
	private String getQualifiedName(Variable variable) {
		EObject cter = variable.eContainer();
		String name = variable.getName();
		if (cter instanceof Generator) {
			name = "generator" + blockCount + "_" + name;
			blockCount++;
		} else if (cter instanceof StatementForeach) {
			name = "foreach" + blockCount + "_" + name;
			blockCount++;
		}

		return name;
	}

	private void transformAnnotations(Var variable,
			List<AstAnnotation> annotations) {
		for (AstAnnotation astAnnotation : annotations) {
			Annotation annotation = IrFactory.eINSTANCE
					.createAnnotation(astAnnotation.getName());
			for (AnnotationArgument arg : astAnnotation.getArguments()) {
				annotation.getAttributes().put(arg.getName(), arg.getValue());
			}
			variable.getAnnotations().add(annotation);
		}
	}

	/**
	 * Transforms the given AST expression to an IR expression.
	 * 
	 * @param expression
	 *            an AST expression
	 * @return an IR expression
	 */
	public Expression transformExpression(AstExpression expression) {
		return new ExprTransformer().doSwitch(expression);
	}

	/**
	 * Transforms the given AST expressions to a list of IR expressions. In the
	 * process nodes may be created and added to the current {@link #procedure},
	 * since many RVC-CAL expressions are expressed with IR statements.
	 * 
	 * @param expressions
	 *            a list of AST expressions
	 * @return a list of IR expressions
	 */
	public List<Expression> transformExpressions(List<AstExpression> expressions) {
		int length = expressions.size();
		List<Expression> irExpressions = new ArrayList<Expression>(length);
		for (AstExpression expression : expressions) {
			ExprTransformer transformer = new ExprTransformer();
			irExpressions.add(transformer.doSwitch(expression));
		}
		return irExpressions;
	}

	/**
	 * Transforms the given list of AST variables to IR variables, and adds them
	 * to the current {@link #procedure}'s local variables list.
	 * 
	 * @param variables
	 *            a list of AST variables
	 */
	public void transformLocalVariables(List<Variable> variables) {
		for (Variable Variable : variables) {
			Var local = caseVariableLocal(Variable);
			procedure.getLocals().add(local);
		}
	}

	/**
	 * Transforms the given list of AST parameters to IR variables, and adds
	 * them to the current {@link #procedure}'s parameters list.
	 * 
	 * @param parameters
	 *            a list of AST parameters
	 */
	private void transformParameters(List<Variable> parameters) {
		List<Param> params = procedure.getParameters();
		for (Variable astParameter : parameters) {
			Var local = caseVariableLocal(astParameter);
			params.add(IrFactory.eINSTANCE.createParam(local));
		}
	}

	/**
	 * Transforms the given AST statements to IR instructions and/or nodes that
	 * are added directly to the current {@link #procedure}.
	 * 
	 * @param statements
	 *            a list of AST statements
	 */
	public void transformStatements(List<Statement> statements) {
		StmtTransformer transformer = new StmtTransformer();
		for (Statement statement : statements) {
			transformer.doSwitch(statement);
		}
	}

}
