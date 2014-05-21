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
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Unit;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.SwitchUtil;
import net.sf.orcc.util.Void;

import org.eclipse.xtext.EcoreUtil2;

/**
 * This class transforms an AST statement into one or more IR instructions
 * and/or blocks. It returns null because it appends the instructions/blocks
 * directly to the {@link #blocks} field.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StmtTransformer extends CalSwitch<Void> {

	/**
	 * This class transforms the expression passed to a print/println procedure
	 * to a list of IR expressions.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class PrintlnTransformer extends CalSwitch<Void> {

		private final List<Expression> parameters;

		public PrintlnTransformer(List<Expression> parameters) {
			this.parameters = parameters;
		}

		@Override
		public Void caseAstExpression(
				AstExpression astExpression) {
			ExprTransformer transformer = new ExprTransformer(procedure, blocks);
			Expression expression = transformer.doSwitch(astExpression);
			parameters.add(expression);
			return SwitchUtil.DONE;
		}

		@Override
		public Void caseExpressionBinary(
				ExpressionBinary astExpression) {
			OpBinary op = OpBinary.getOperator(astExpression.getOperator());
			if (op == OpBinary.PLUS) {
				doSwitch(astExpression.getLeft());
				ExprTransformer transformer = new ExprTransformer(procedure,
						blocks);
				Expression expression = transformer.doSwitch(astExpression
						.getRight());
				parameters.add(expression);

				return SwitchUtil.DONE;
			}

			return SwitchUtil.CASCADE;
		}

	}

	private final List<Block> blocks;

	private final Procedure procedure;

	/**
	 * Creates a new AST to IR transformer, which will append instructions and
	 * blocks to the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param blocks
	 * 
	 */
	public StmtTransformer(Procedure procedure, List<Block> blocks) {
		this.procedure = procedure;
		this.blocks = blocks;
	}

	@Override
	public Void caseStatementAssign(StatementAssign assign) {
		// get target
		Variable variable = assign.getTarget().getVariable();
		Var target = Frontend.instance.getMapping(variable);

		// transform indexes and value
		List<Expression> indexes;
		if (assign.getIndexes().isEmpty()) {
			indexes = null;
		} else {
			indexes = AstIrUtil.transformExpressions(procedure, blocks,
					assign.getIndexes());
		}

		new ExprTransformer(procedure, blocks, target, indexes).doSwitch(assign
				.getValue());

		return null;
	}

	@Override
	public Void caseStatementCall(StatementCall stmtCall) {
		int lineNumber = Util.getLocation(stmtCall);

		// retrieve IR procedure
		AstProcedure astProcedure = stmtCall.getProcedure();
		// special case if the procedure is a built-in procedure
		if (astProcedure.eContainer() == null) {
			transformBuiltinProcedure(stmtCall);
			return null;
		}

		// retrieve IR procedure
		Procedure calledProc = Frontend.instance.getMapping(astProcedure);

		// transform parameters
		List<Expression> parameters = AstIrUtil.transformExpressions(procedure,
				blocks, stmtCall.getArguments());

		// add call
		InstCall call = eINSTANCE.createInstCall(lineNumber, null, calledProc,
				parameters);
		IrUtil.getLast(blocks).add(call);

		// Add annotations
		Util.transformAnnotations(call, stmtCall.getAnnotations());
		return null;
	}

	@Override
	public Void caseStatementForeach(StatementForeach foreach) {
		int lineNumber = Util.getLocation(foreach);

		// creates loop variable and assigns it
		Variable variable = foreach.getVariable();

		Var loopVar = procedure.newTempLocalVariable(Typer.getType(variable),
				variable.getName());
		Frontend.instance.putMapping(variable, loopVar);

		AstExpression astLower = foreach.getLower();
		new ExprTransformer(procedure, blocks, loopVar).doSwitch(astLower);

		// condition
		AstExpression astHigher = foreach.getHigher();
		Expression higher = new ExprTransformer(procedure, blocks)
				.doSwitch(astHigher);
		Expression condition = eINSTANCE.createExprBinary(
				eINSTANCE.createExprVar(loopVar), OpBinary.LE, higher,
				eINSTANCE.createTypeBool());

		// create while
		BlockWhile blockWhile = eINSTANCE.createBlockWhile();
		blockWhile.setJoinBlock(eINSTANCE.createBlockBasic());
		blockWhile.setLineNumber(lineNumber);
		blockWhile.setCondition(condition);

		blocks.add(blockWhile);

		// body
		new StmtTransformer(procedure, blockWhile.getBlocks()).doSwitch(foreach
				.getStatements());

		// add increment
		BlockBasic block = IrUtil.getLast(blockWhile.getBlocks());
		InstAssign assign = eINSTANCE.createInstAssign(lineNumber, loopVar,
				eINSTANCE.createExprBinary(eINSTANCE.createExprVar(loopVar),
						OpBinary.PLUS, eINSTANCE.createExprInt(1),
						loopVar.getType()));
		block.add(assign);

		// Add annotations
		Util.transformAnnotations(blockWhile, foreach.getAnnotations());

		return null;
	}

	@Override
	public Void caseStatementIf(StatementIf stmtIf) {
		int lineNumber = Util.getLocation(stmtIf);

		Expression condition = new ExprTransformer(procedure, blocks)
				.doSwitch(stmtIf.getCondition());

		// creates if and adds it to procedure
		BlockIf blockIf = eINSTANCE.createBlockIf();
		blockIf.setJoinBlock(eINSTANCE.createBlockBasic());
		blockIf.setLineNumber(lineNumber);
		blockIf.setCondition(condition);

		blocks.add(blockIf);

		// Add annotations
		Util.transformAnnotations(blockIf, stmtIf.getAnnotations());

		// transforms "then" statements
		new StmtTransformer(procedure, blockIf.getThenBlocks()).doSwitch(stmtIf
				.getThen());

		// add elsif statements
		for (StatementElsif elsif : stmtIf.getElsifs()) {
			condition = new ExprTransformer(procedure, blockIf.getElseBlocks())
					.doSwitch(elsif.getCondition());

			// creates inner if
			lineNumber = Util.getLocation(elsif);
			BlockIf innerIf = eINSTANCE.createBlockIf();
			innerIf.setJoinBlock(eINSTANCE.createBlockBasic());
			innerIf.setLineNumber(lineNumber);
			innerIf.setCondition(condition);
			new StmtTransformer(procedure, innerIf.getThenBlocks())
					.doSwitch(elsif.getThen());

			// adds elsif to blocks's else blocks, and assign elsif to block
			blockIf.getElseBlocks().add(innerIf);
			blockIf = innerIf;
		}

		// add else blocks to current if
		new StmtTransformer(procedure, blockIf.getElseBlocks()).doSwitch(stmtIf
				.getElse());

		return null;
	}

	@Override
	public Void caseStatementWhile(StatementWhile stmtWhile) {
		int lineNumber = Util.getLocation(stmtWhile);

		// to track the instructions created when condition was transformed
		List<Block> tempBlocks = new ArrayList<Block>();
		ExprTransformer transformer = new ExprTransformer(procedure, tempBlocks);
		Expression condition = transformer.doSwitch(stmtWhile.getCondition());

		// create the while
		BlockWhile blockWhile = eINSTANCE.createBlockWhile();
		blockWhile.setJoinBlock(eINSTANCE.createBlockBasic());
		blockWhile.setLineNumber(lineNumber);
		blockWhile.setCondition(condition);

		// the body
		new StmtTransformer(procedure, blockWhile.getBlocks())
				.doSwitch(stmtWhile.getStatements());

		// copy instructions
		blockWhile.getBlocks().addAll(IrUtil.copy(tempBlocks));

		blocks.addAll(tempBlocks);
		blocks.add(blockWhile);

		// Add annotations
		Util.transformAnnotations(blockWhile, stmtWhile.getAnnotations());

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
			Procedure print = getOrCreatePrint(EcoreUtil2.getContainerOfType(
					stmtCall, AstEntity.class));

			List<AstExpression> astArguments = stmtCall.getArguments();
			List<Expression> arguments = new ArrayList<Expression>(7);
			if (!astArguments.isEmpty()) {
				AstExpression astExpression = astArguments.get(0);
				new PrintlnTransformer(arguments).doSwitch(astExpression);
			}

			if ("println".equals(name)) {
				arguments.add(eINSTANCE.createExprString("\\n"));
			}

			final InstCall call = eINSTANCE.createInstCall(lineNumber, null,
					print, arguments);
			IrUtil.getLast(blocks).add(call);
		}
	}

	/**
	 * Try to find a print procedure stored in the entity corresponding to the
	 * given AstEntity. If it does not exists, creates it and adds it to the
	 * entity procedures list. In any case, returns the print procedure.
	 * 
	 * @param astEntity
	 * @return
	 */
	private Procedure getOrCreatePrint(AstEntity astEntity) {

		final List<Procedure> procedureList;
		Procedure print;
		if (astEntity.getActor() != null) {
			final Actor actor = Frontend.instance.getMapping(astEntity
					.getActor());
			procedureList = actor.getProcs();
			print = actor.getProcedure("print");
		} else if (astEntity.getUnit() != null) {
			final Unit unit = Frontend.instance.getMapping(astEntity.getUnit());
			procedureList = unit.getProcedures();
			print = unit.getProcedure("print");
		} else {
			OrccLogger.severeln("The AstEntity type is undefined");
			return eINSTANCE.createProcedure("print", 0,
					eINSTANCE.createTypeVoid());
		}

		// We found it, return
		if (print != null) {
			return print;
		}

		// It is the first time print is called in this entity, create a new
		// print procedure
		print = eINSTANCE.createProcedure("print", 0,
				eINSTANCE.createTypeVoid());
		print.setNative(true);

		// Add to the actor / unit procedures list
		procedureList.add(print);

		return print;
	}
}
