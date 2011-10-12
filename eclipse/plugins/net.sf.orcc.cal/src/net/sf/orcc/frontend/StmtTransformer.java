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

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

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
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
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
			ExprTransformer transformer = new ExprTransformer(procedure, nodes);
			Expression expression = transformer.doSwitch(astExpression);
			parameters.add(expression);

			return object;
		}

		@Override
		public Object caseExpressionBinary(ExpressionBinary astExpression) {
			OpBinary op = OpBinary.getOperator(astExpression.getOperator());
			if (op == OpBinary.PLUS) {
				doSwitch(astExpression.getLeft());
				ExprTransformer transformer = new ExprTransformer(procedure,
						nodes);
				Expression expression = transformer.doSwitch(astExpression
						.getRight());
				parameters.add(expression);

				return object;
			}

			// fall back to general case
			return null;
		}

	}

	private List<Node> nodes;

	private Procedure print;

	private Procedure procedure;

	/**
	 * Creates a new AST to IR transformer, which will append instructions and
	 * nodes to the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	public StmtTransformer(Procedure procedure, List<Node> nodes) {
		this.procedure = procedure;
		this.nodes = nodes;
	}

	@Override
	public EObject caseStatementAssign(StatementAssign assign) {
		// get target
		Variable variable = assign.getTarget().getVariable();
		Var target = Frontend.getMapping(variable);

		// transform indexes and value
		List<Expression> indexes;
		if (assign.getIndexes().isEmpty()) {
			indexes = null;
		} else {
			indexes = AstIrUtil.transformExpressions(procedure, nodes,
					assign.getIndexes());
		}

		AstExpression value = Util.getAssignValue(assign);

		new ExprTransformer(procedure, nodes, target, indexes).doSwitch(value);

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
				nodes, stmtCall.getParameters());

		// add call
		InstCall call = eINSTANCE.createInstCall(lineNumber, null, calledProc,
				parameters);
		IrUtil.getLast(nodes).add(call);

		return null;
	}

	@Override
	public EObject caseStatementForeach(StatementForeach foreach) {
		int lineNumber = Util.getLocation(foreach);

		// creates loop variable and assigns it
		Variable variable = foreach.getVariable();
		Var loopVar = AstIrUtil.getLocalByName(procedure, variable);

		AstExpression astLower = foreach.getLower();
		new ExprTransformer(procedure, nodes, loopVar).doSwitch(astLower);

		// condition
		AstExpression astHigher = foreach.getHigher();
		Expression higher = new ExprTransformer(procedure, nodes)
				.doSwitch(astHigher);
		Expression condition = eINSTANCE.createExprBinary(
				eINSTANCE.createExprVar(loopVar), OpBinary.LE, higher,
				eINSTANCE.createTypeBool());

		// create while
		NodeWhile nodeWhile = eINSTANCE.createNodeWhile();
		nodeWhile.setJoinNode(eINSTANCE.createNodeBlock());
		nodeWhile.setLineNumber(lineNumber);
		nodeWhile.setCondition(condition);

		nodes.add(nodeWhile);

		// body
		new StmtTransformer(procedure, nodeWhile.getNodes()).doSwitch(foreach
				.getStatements());

		// add increment
		NodeBlock block = IrUtil.getLast(nodeWhile.getNodes());
		InstAssign assign = eINSTANCE.createInstAssign(lineNumber, loopVar,
				eINSTANCE.createExprBinary(eINSTANCE.createExprVar(loopVar),
						OpBinary.PLUS, eINSTANCE.createExprInt(1),
						loopVar.getType()));
		block.add(assign);

		return null;
	}

	@Override
	public EObject caseStatementIf(StatementIf stmtIf) {
		int lineNumber = Util.getLocation(stmtIf);

		Expression condition = new ExprTransformer(procedure, nodes)
				.doSwitch(stmtIf.getCondition());

		// creates if and adds it to procedure
		NodeIf node = eINSTANCE.createNodeIf();
		node.setJoinNode(eINSTANCE.createNodeBlock());
		node.setLineNumber(lineNumber);
		node.setCondition(condition);

		nodes.add(node);

		// transforms "then" statements
		new StmtTransformer(procedure, node.getThenNodes()).doSwitch(stmtIf
				.getThen());

		// add elsif statements
		for (StatementElsif elsif : stmtIf.getElsifs()) {
			condition = new ExprTransformer(procedure, node.getElseNodes())
					.doSwitch(elsif.getCondition());

			// creates inner if
			lineNumber = Util.getLocation(elsif);
			NodeIf innerIf = eINSTANCE.createNodeIf();
			innerIf.setJoinNode(eINSTANCE.createNodeBlock());
			innerIf.setLineNumber(lineNumber);
			innerIf.setCondition(condition);
			new StmtTransformer(procedure, innerIf.getThenNodes())
					.doSwitch(elsif.getThen());

			// adds elsif to node's else nodes, and assign elsif to node
			node.getElseNodes().add(innerIf);
			node = innerIf;
		}

		// add else nodes to current if
		new StmtTransformer(procedure, node.getElseNodes()).doSwitch(stmtIf
				.getElse());

		return null;
	}

	@Override
	public EObject caseStatementWhile(StatementWhile stmtWhile) {
		int lineNumber = Util.getLocation(stmtWhile);

		// to track the instructions created when condition was transformed
		List<Node> tempNodes = new ArrayList<Node>();
		ExprTransformer transformer = new ExprTransformer(procedure, tempNodes);
		Expression condition = transformer.doSwitch(stmtWhile.getCondition());

		// create the while
		NodeWhile nodeWhile = eINSTANCE.createNodeWhile();
		nodeWhile.setJoinNode(eINSTANCE.createNodeBlock());
		nodeWhile.setLineNumber(lineNumber);
		nodeWhile.setCondition(condition);

		// the body
		new StmtTransformer(procedure, nodeWhile.getNodes()).doSwitch(stmtWhile
				.getStatements());

		// copy instructions
		nodeWhile.getNodes().addAll(IrUtil.copy(tempNodes));

		nodes.addAll(tempNodes);
		nodes.add(nodeWhile);

		return null;
	}

	/**
	 * Transforms the given list of statements.
	 * 
	 * @param statements
	 *            a list of Statement
	 */
	public void doSwitch(List<Statement> statements) {
		for (Statement statement : statements) {
			doSwitch(statement);
		}
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
				print = eINSTANCE.createProcedure("print", lineNumber,
						eINSTANCE.createTypeVoid());
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
				parameters.add(eINSTANCE.createExprString("\\n"));
			}

			InstCall call = eINSTANCE.createInstCall(lineNumber, null, print,
					parameters);
			IrUtil.getLast(nodes).add(call);
		}
	}

}
