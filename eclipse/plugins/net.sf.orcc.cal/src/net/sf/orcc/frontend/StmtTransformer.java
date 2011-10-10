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

import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.ExpressionBinary;
import net.sf.orcc.cal.cal.Statement;
import net.sf.orcc.cal.cal.StatementAssign;
import net.sf.orcc.cal.cal.StatementCall;
import net.sf.orcc.cal.cal.StatementElsif;
import net.sf.orcc.cal.cal.StatementForeach;
import net.sf.orcc.cal.cal.StatementIf;
import net.sf.orcc.cal.cal.StatementWhile;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;

/**
 * This class transforms an AST statement into one or more IR instructions
 * and/or nodes. It returns null because it appends the instructions/nodes
 * directly to the {@link #nodes} field.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StmtTransformer extends CalSwitch<EObject> {

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
			ExprTransformer transformer = new ExprTransformer(procedure);
			Expression expression = transformer.doSwitch(astExpression);
			parameters.add(expression);

			return object;
		}

		@Override
		public Object caseExpressionBinary(ExpressionBinary astExpression) {
			OpBinary op = OpBinary.getOperator(astExpression.getOperator());
			if (op == OpBinary.PLUS) {
				doSwitch(astExpression.getLeft());
				ExprTransformer transformer = new ExprTransformer(procedure);
				Expression expression = transformer.doSwitch(astExpression
						.getRight());
				parameters.add(expression);

				return object;
			}

			// fall back to general case
			return null;
		}

	}

	@Override
	public EObject caseStatementAssign(StatementAssign assign) {
		int lineNumber = Util.getLocation(assign);

		// get target
		Variable variable = assign.getTarget().getVariable();
		Var target = Frontend.getMapping(variable);

		// transform indexes and value
		List<Expression> indexes = AstIrUtil.transformExpressions(procedure,
				assign.getIndexes());

		ExprTransformer transformer = new ExprTransformer(procedure, target,
				indexes);
		Expression value = transformer.doSwitch(assign.getValue());
		AstIrUtil.createAssignOrStore(procedure, lineNumber, target, indexes,
				value);

		return null;
	}

	@Override
	public EObject caseStatementCall(StatementCall stmtCall) {
		int lineNumber = Util.getLocation(stmtCall);

		// retrieve IR procedure
		AstProcedure astProcedure = stmtCall.getProcedure();
		// special case if the procedure is a built-in procedure
		if (astProcedure.eContainer() == null) {
			transformBuiltinProcedure(stmtCall);
			return null;
		}

		// retrieve IR procedure
		Procedure calledProc = Frontend.getMapping(astProcedure);

		// transform parameters
		List<Expression> parameters = AstIrUtil.transformExpressions(procedure,
				stmtCall.getParameters());

		// add call
		InstCall call = IrFactory.eINSTANCE.createInstCall(lineNumber, null,
				calledProc, parameters);
		procedure.getLast().add(call);

		return null;
	}

	@Override
	public EObject caseStatementForeach(StatementForeach foreach) {
		int lineNumber = Util.getLocation(foreach);

		// creates loop variable and assigns it
		Variable variable = foreach.getVariable();
		Var loopVar = AstIrUtil.getLocalByName(procedure, variable);

		AstExpression astLower = foreach.getLower();
		ExprTransformer transformer = new ExprTransformer(procedure);
		Expression lower = transformer.doSwitch(astLower);
		InstAssign assign = IrFactory.eINSTANCE.createInstAssign(lineNumber,
				loopVar, lower);
		procedure.getLast().add(assign);

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
						OpBinary.PLUS, IrFactory.eINSTANCE.createExprInt(1),
						loopVar.getType()));
		block.add(assign);

		// create while
		NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
		nodeWhile.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
		nodeWhile.setLineNumber(lineNumber);
		nodeWhile.setCondition(condition);
		nodeWhile.getNodes().addAll(nodes);

		procedure.getNodes().add(nodeWhile);

		return null;
	}

	@Override
	public EObject caseStatementIf(StatementIf stmtIf) {
		int lineNumber = Util.getLocation(stmtIf);

		ExprTransformer transformer = new ExprTransformer(procedure);
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

			transformer = new ExprTransformer(procedure);
			condition = transformer.doSwitch(elsif.getCondition());
			node.setCondition(condition);

			thenNodes = getNodes(elsif.getThen());
			node.getThenNodes().addAll(thenNodes);
		}

		// add else nodes to current if
		List<Node> elseNodes = getNodes(stmtIf.getElse());
		node.getElseNodes().addAll(elseNodes);

		return null;
	}

	@Override
	public EObject caseStatementWhile(StatementWhile stmtWhile) {
		int lineNumber = Util.getLocation(stmtWhile);

		// to help track the instructions added
		NodeBlock block = procedure.getLast();
		List<Instruction> instructions = block.getInstructions();
		int first = instructions.size();

		ExprTransformer transformer = new ExprTransformer(procedure);
		Expression condition = transformer.doSwitch(stmtWhile.getCondition());
		int last = instructions.size();

		// the body
		List<Node> nodes = getNodes(stmtWhile.getStatements());

		// copy load instructions
		List<Instruction> subList = instructions.subList(first, last);
		for (Instruction instruction : subList) {
			if (instruction instanceof InstLoad) {
				InstLoad load = (InstLoad) instruction;
				load = IrFactory.eINSTANCE.createInstLoad(load.getLineNumber(),
						load.getTarget().getVariable(), load.getSource()
								.getVariable(), (List<Expression>) IrUtil
								.copy(load.getIndexes()));
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

		return null;
	}

	/**
	 * Returns a list of CFG nodes from the given list of statements. This
	 * method creates a new block node to hold the statements, transforms the
	 * statements, and transfers the nodes created to a new list that is the
	 * result.
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
	 * Transforms a call to a built-in procedure (print or println). Both are
	 * transformed to a call to print, with an additional "\\n" parameter in the
	 * case of println.
	 * 
	 * @param call
	 *            an AST call statement
	 */
	private void transformBuiltinProcedure(StatementCall stmtCall) {
		int lineNumber = Util.getLocation(stmtCall);
		String name = stmtCall.getProcedure().getName();
		if ("print".equals(name) || "println".equals(name)) {
			if (print == null) {
				print = IrFactory.eINSTANCE.createProcedure("print",
						lineNumber, IrFactory.eINSTANCE.createTypeVoid());
				print.setNative(true);

				AstEntity entity = EcoreUtil2.getContainerOfType(stmtCall,
						AstEntity.class);
				Frontend.getProcedures(entity).add(print);
			}

			List<AstExpression> astParameters = stmtCall.getParameters();
			List<Expression> parameters = new ArrayList<Expression>(7);
			if (!astParameters.isEmpty()) {
				AstExpression astExpression = astParameters.get(0);
				new PrintlnTransformer(parameters).doSwitch(astExpression);
			}

			if ("println".equals(name)) {
				parameters.add(IrFactory.eINSTANCE.createExprString("\\n"));
			}

			InstCall call = IrFactory.eINSTANCE.createInstCall(lineNumber,
					null, print, parameters);
			procedure.getLast().add(call);
		}
	}

	private Procedure print;

	private Procedure procedure;

	/**
	 * Creates a new AST to IR transformer.
	 */
	public StmtTransformer() {
	}

	/**
	 * Creates a new AST to IR transformer, which will append instructions and
	 * nodes to the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	public StmtTransformer(Procedure procedure) {
		this.procedure = procedure;
	}

	/**
	 * Transforms the given AST statements to IR instructions and/or nodes that
	 * are added directly to the current {@link #procedure}.
	 * 
	 * @param statements
	 *            a list of AST statements
	 */
	private void transformStatements(List<Statement> statements) {
		StmtTransformer transformer = new StmtTransformer(procedure);
		for (Statement statement : statements) {
			transformer.doSwitch(statement);
		}
	}

}
