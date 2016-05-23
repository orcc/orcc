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
package net.sf.orcc.frontend;

import static net.sf.orcc.ir.IrFactory.eINSTANCE;
import static net.sf.orcc.ir.OpBinary.MINUS;
import static net.sf.orcc.ir.OpBinary.PLUS;
import static net.sf.orcc.ir.OpBinary.TIMES;
import static net.sf.orcc.util.OrccAttributes.COPY_OF_TOKENS;
import static net.sf.orcc.util.OrccAttributes.REMOVABLE_COPY;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.ExpressionBinary;
import net.sf.orcc.cal.cal.ExpressionBoolean;
import net.sf.orcc.cal.cal.ExpressionCall;
import net.sf.orcc.cal.cal.ExpressionElsif;
import net.sf.orcc.cal.cal.ExpressionFloat;
import net.sf.orcc.cal.cal.ExpressionIf;
import net.sf.orcc.cal.cal.ExpressionIndex;
import net.sf.orcc.cal.cal.ExpressionInteger;
import net.sf.orcc.cal.cal.ExpressionList;
import net.sf.orcc.cal.cal.ExpressionString;
import net.sf.orcc.cal.cal.ExpressionUnary;
import net.sf.orcc.cal.cal.ExpressionVariable;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.StatementCall;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.services.Evaluator;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.ecore.EObject;

/**
 * This class transforms an AST expression into one or more IR instructions
 * and/or blocks, and returns an IR expression. The main idea is that an
 * expression will assign to a target with indexes unless target is
 * <code>null</code>. So simple arithmetic (binary and unary) sub-expressions
 * are translated with target set to <code>null</code> to avoid further
 * assignments.
 * 
 * <p>
 * The assignments are appended to the given List of block called
 * <code>blocks</code>. The Procedure passed as a parameter is used for lookup
 * and creation of local variables.
 * </p>
 * 
 * @author Matthieu Wipliez
 * @author Hervé Yviquel
 */
public class ExprTransformer extends CalSwitch<Expression> {

	private List<Expression> indexes;

	private final List<Block> blocks;

	private final Procedure procedure;

	private Var target;

	/**
	 * Creates a new transformer with the given procedure and blocks, and with a
	 * <code>null</code> target. To be used as a last resort (or when the target
	 * is unknown, e.g. condition of if and while statements, expression of a
	 * function).
	 * 
	 * @param procedure
	 *            procedure in which the expression is transformed
	 * @param blocks
	 *            a list of blocks to which instructions and other blocks may be
	 *            appended. In general, this is a subset of the procedure's
	 *            block list.
	 */
	public ExprTransformer(Procedure procedure, List<Block> blocks) {
		this(procedure, blocks, null);
	}

	/**
	 * Creates a new transformer with the given procedure, blocks, and target.
	 * To be used when the expression to be translated is to be assigned to the
	 * target without indexes.
	 * 
	 * @param procedure
	 *            procedure in which the expression is transformed
	 * @param blocks
	 *            a list of blocks to which instructions and other blocks may be
	 *            appended. In general, this is a subset of the procedure's
	 *            block list.
	 * @param target
	 *            the variable to which the expression should be assigned
	 */
	public ExprTransformer(Procedure procedure, List<Block> blocks, Var target) {
		this(procedure, blocks, target, null);
	}

	/**
	 * Creates a new transformer with the given procedure, blocks, target, and
	 * indexes. To be used when the expression to be translated is to be
	 * assigned to the target with indexes.
	 * 
	 * @param procedure
	 *            procedure in which the expression is transformed
	 * @param blocks
	 *            a list of blocks to which instructions and other blocks may be
	 *            appended. In general, this is a subset of the procedure's
	 *            block list.
	 * @param target
	 *            the variable to which the expression should be assigned
	 * @param indexes
	 *            a list of expression to use when creating assignments (Store
	 *            instructions) to the target
	 */
	public ExprTransformer(Procedure procedure, List<Block> blocks, Var target,
			List<Expression> indexes) {
		this.procedure = procedure;
		this.blocks = blocks;
		this.target = target;
		this.indexes = indexes;
	}

	@Override
	public Expression caseExpressionBinary(ExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Expression e1 = new ExprTransformer(procedure, blocks)
				.doSwitch(expression.getLeft());
		Expression e2 = new ExprTransformer(procedure, blocks)
				.doSwitch(expression.getRight());

		Expression value = eINSTANCE.createExprBinary(e1, op, e2,
				Typer.getType(expression));
		return storeExpr(expression, value);
	}

	@Override
	public Expression caseExpressionBoolean(ExpressionBoolean expression) {
		Expression value = eINSTANCE.createExprBool(expression.isValue());
		return storeExpr(expression, value);
	}

	@Override
	public Expression caseExpressionCall(ExpressionCall exprCall) {
		int lineNumber = Util.getLocation(exprCall);

		// retrieve IR procedure
		Procedure calledProc = Frontend.instance.getMapping(exprCall
				.getFunction());

		// transform parameters
		List<Expression> parameters = AstIrUtil.transformExpressions(procedure,
				blocks, exprCall.getParameters());

		// set call target and add call
		Var callTarget = getScalar(calledProc.getReturnType(),
				calledProc.getName());
		InstCall call = eINSTANCE.createInstCall(lineNumber, callTarget,
				calledProc, parameters);
		Util.transformAnnotations(call, exprCall.getAnnotations());
		IrUtil.getLast(blocks).add(call);

		// return expr
		if (callTarget != target) {
			// need to store back into target
			return storeExpr(exprCall, eINSTANCE.createExprVar(callTarget));
		} else {
			return null;
		}
	}

	@Override
	public Expression caseExpressionIf(ExpressionIf expression) {
		int lineNumber = Util.getLocation(expression);
		Expression condition = new ExprTransformer(procedure, blocks)
				.doSwitch(expression.getCondition());

		// transforms "then" statements and "else" statements
		BlockIf blockIf = eINSTANCE.createBlockIf();
		blockIf.setJoinBlock(eINSTANCE.createBlockBasic());
		blockIf.setLineNumber(lineNumber);
		blockIf.setCondition(condition);

		blocks.add(blockIf);

		Var ifTarget;
		if (target == null) {
			ifTarget = procedure.newTempLocalVariable(
					Typer.getType(expression), "tmp_if");
		} else {
			ifTarget = target;
		}

		// transforms "then" expression
		new ExprTransformer(procedure, blockIf.getThenBlocks(), ifTarget,
				indexes).doSwitch(expression.getThen());

		// add elsif expressions
		for (ExpressionElsif elsif : expression.getElsifs()) {
			condition = new ExprTransformer(procedure, blockIf.getElseBlocks())
					.doSwitch(elsif.getCondition());

			// creates inner if
			lineNumber = Util.getLocation(elsif);
			BlockIf innerIf = eINSTANCE.createBlockIf();
			innerIf.setJoinBlock(eINSTANCE.createBlockBasic());
			innerIf.setLineNumber(lineNumber);
			innerIf.setCondition(condition);
			new ExprTransformer(procedure, innerIf.getThenBlocks(), ifTarget,
					indexes).doSwitch(elsif.getThen());

			// adds elsif to block's else blocks, and assign elsif to block
			blockIf.getElseBlocks().add(innerIf);
			blockIf = innerIf;
		}

		new ExprTransformer(procedure, blockIf.getElseBlocks(), ifTarget,
				indexes).doSwitch(expression.getElse());

		// return expr
		if (target == null) {
			return eINSTANCE.createExprVar(ifTarget);
		} else {
			return null;
		}
	}

	@Override
	public Expression caseExpressionIndex(ExpressionIndex expression) {
		// we always load in this case
		int lineNumber = Util.getLocation(expression);
		Variable variable = expression.getSource().getVariable();
		Var var = Frontend.instance.getMapping(variable);

		List<Expression> indexes = AstIrUtil.transformExpressions(procedure,
				blocks, expression.getIndexes());

		// set load target and add load
		Var loadTarget = getScalar(Typer.getType(expression), var.getName());
		InstLoad load = eINSTANCE.createInstLoad(lineNumber, loadTarget, var,
				indexes);
		IrUtil.getLast(blocks).add(load);

		// return expr
		if (loadTarget != target) {
			// need to store back into target
			return storeExpr(expression, eINSTANCE.createExprVar(loadTarget));
		} else {
			return null;
		}
	}

	@Override
	public Expression caseExpressionInteger(ExpressionInteger expression) {
		Expression value = eINSTANCE.createExprInt(expression.getValue());
		return storeExpr(expression, value);
	}

	@Override
	public Expression caseExpressionFloat(ExpressionFloat expression) {
		Expression value = eINSTANCE.createExprFloat(expression.getValue());
		return storeExpr(expression, value);
	}

	@Override
	public Expression caseExpressionList(ExpressionList expression) {
		if (indexes == null) {
			indexes = new ArrayList<Expression>();
		}

		// Return new ExprVar only in case we need one
		boolean needReturn = false;

		if (target == null) {
			target = procedure.newTempLocalVariable(Typer.getType(expression),
					"tmp_list");
			needReturn = true;
		}

		List<AstExpression> expressions = expression.getExpressions();
		List<Generator> generators = expression.getGenerators();

		if (generators.isEmpty()) {
			transformListSimple(expressions);
		} else {
			transformListGenerators(expressions, generators);
		}

		if (needReturn) {
			return eINSTANCE.createExprVar(target);
		} else {
			return null;
		}
	}

	@Override
	public Expression caseExpressionString(ExpressionString expression) {
		Expression value = eINSTANCE.createExprString(OrccUtil
				.getEscapedString(expression.getValue()));
		return storeExpr(expression, value);
	}

	@Override
	public Expression caseExpressionUnary(ExpressionUnary expression) {
		OpUnary op = OpUnary.getOperator(expression.getUnaryOperator());
		if (OpUnary.NUM_ELTS == op) {
			return Evaluator.getValue(expression);
		}

		Expression expr = new ExprTransformer(procedure, blocks)
				.doSwitch(expression.getExpression());
		Expression value = eINSTANCE.createExprUnary(op, expr,
				Typer.getType(expression));
		return storeExpr(expression, value);
	}

	@Override
	public Expression caseExpressionVariable(ExpressionVariable expression) {
		Variable variable = expression.getValue().getVariable();
		Var var = Frontend.instance.getMapping(variable);

		Expression value;
		if (var.getType().isList()) {
			if (target == null) {
				EObject exprContainer = expression.eContainer();
				EObject procContainer = procedure.eContainer();
				// Check if the list variable need to be copied locally (when
				// the variable represents input data and is used as procedure
				// argument). Then, annotate the IR to allow optimizations at
				// back-end level.
				if (exprContainer instanceof StatementCall
						&& procContainer instanceof Action) {
					Pattern inputPattern = ((Action) procContainer)
							.getInputPattern();
					if (inputPattern.getVarToPortMap().contains(var)) {
						target = procedure.newTempLocalVariable(var.getType(),
								"local_" + var.getName());
						copyList(var, true);

						// Mark the variable as a local copy of input data and
						// reference the associated port.
						Port port = inputPattern.getVarToPortMap().get(var);
						target.setAttribute(COPY_OF_TOKENS, port);

						return eINSTANCE.createExprVar(target);
					}
				}
				return eINSTANCE.createExprVar(var);
			} else {
				Expression expr;
				// Check if the variable has been marked before by the
				// ActorTransformer as an optimizable copy of output variable
				// (when it used as procedure argument). Then, annotate the IR
				// to allow optimizations at back-end level.
				if (var.hasAttribute(COPY_OF_TOKENS)) {
					expr = copyList(var, true);

					// Mark the variable as a local copy of output data and
					// reference the associated port.
					Pattern outputPattern = ((Action) procedure.eContainer())
							.getOutputPattern();
					var.setAttribute(COPY_OF_TOKENS,
							outputPattern.getPortToVarMap().get(target));
				} else {
					expr = copyList(var, false);
				}
				return expr;
			}
		} else {
			if (procedure != null) {
				if (!AstIrUtil.isLocal(var)) {
					Var global = var;
					var = procedure.getLocal("local_" + global.getName());
					if (var == null) {
						var = procedure.newTempLocalVariable(global.getType(),
								"local_" + global.getName());
					}
					InstLoad load = eINSTANCE.createInstLoad(var, global);
					IrUtil.getLast(blocks).add(load);
				}
			}

			value = eINSTANCE.createExprVar(var);
		}

		return storeExpr(expression, value);
	}

	/**
	 * Copies the given variable to the current target. Will use the current
	 * indexes if they exist.
	 * 
	 * @param var
	 *            a variable of type list.
	 * @param removable
	 *            true if the copy can be removed when the FIFO is accessed
	 *            directly (according to the backend)
	 * @return <code>null</code>
	 */
	private Expression copyList(Var var, boolean removable) {
		TypeList typeList = (TypeList) target.getType();
		List<Block> blocks = this.blocks;
		List<BlockWhile> whiles = new ArrayList<BlockWhile>();
		List<Var> loopVars = new ArrayList<Var>();
		List<Expression> indexes = new ArrayList<Expression>();
		for (int size : typeList.getDimensions()) {
			// add loop variable
			Var loopVar = procedure.newTempLocalVariable(
					eINSTANCE.createTypeInt(32), "idx_" + var.getName());
			loopVars.add(loopVar);
			InstAssign assign = eINSTANCE.createInstAssign(loopVar,
					eINSTANCE.createExprInt(0));
			IrUtil.getLast(blocks).add(assign);

			// add index
			indexes.add(eINSTANCE.createExprVar(loopVar));

			// create while block
			Expression condition = eINSTANCE.createExprBinary(
					eINSTANCE.createExprVar(loopVar), OpBinary.LT,
					eINSTANCE.createExprInt(size), eINSTANCE.createTypeBool());

			BlockWhile blockWhile = eINSTANCE.createBlockWhile();
			blockWhile.setJoinBlock(eINSTANCE.createBlockBasic());
			blockWhile.setCondition(condition);
			whiles.add(blockWhile);

			blocks.add(blockWhile);

			blocks = blockWhile.getBlocks();

			if (removable) {
				blockWhile.addAttribute(REMOVABLE_COPY);
			}
		}

		// load
		Var loadTarget = procedure.newTempLocalVariable(
				typeList.getInnermostType(), "local_" + var.getName());
		InstLoad load = eINSTANCE.createInstLoad(0, loadTarget, var, indexes);
		IrUtil.getLast(blocks).add(load);

		// store
		if (this.indexes == null) {
			indexes = new ArrayList<Expression>(IrUtil.copy(indexes));
		} else {
			indexes = this.indexes;
		}
		InstStore store = eINSTANCE.createInstStore(0, target, indexes,
				eINSTANCE.createExprVar(loadTarget));
		IrUtil.getLast(blocks).add(store);

		// add increments
		int size = typeList.getDimensions().size();
		for (int i = 0; i < size; i++) {
			Var loopVar = loopVars.get(i);
			IrUtil.getLast(whiles.get(i).getBlocks()).add(
					eINSTANCE.createInstAssign(loopVar, eINSTANCE
							.createExprBinary(eINSTANCE.createExprVar(loopVar),
									OpBinary.PLUS, eINSTANCE.createExprInt(1),
									eINSTANCE.createTypeInt(32))));
		}

		return null;
	}

	/**
	 * If the current target is a local scalar variable, returns the current
	 * target. Otherwise, creates a new temporary local variable to hold the
	 * result of a Call or a Load.
	 * 
	 * @param type
	 *            type of the temp variable to create
	 * @param name
	 *            indication for the variable name (will be prefixed with
	 *            "tmp_")
	 * @return a scalar variable that can be used as a target
	 */
	private Var getScalar(Type type, String name) {
		if (target != null && AstIrUtil.isLocal(target) && indexes == null) {
			// local scalar => ok
			return target;
		} else {
			// otherwise, create temporary local scalar
			return procedure.newTempLocalVariable(type, "tmp_" + name);
		}
	}

	/**
	 * If target is <code>null</code>, returns the given value. Otherwise,
	 * creates an Assign or Store instruction depending on the target and
	 * indexes. Copies the indexes if necessary.
	 * 
	 * @param astObject
	 *            an AST block used to get a location (line number)
	 * @param value
	 *            an expression
	 * @return <code>value</code> or <code>null</code>
	 */
	private Expression storeExpr(EObject astObject, Expression value) {
		if (target == null) {
			return value;
		}

		int lineNumber = Util.getLocation(astObject);

		Instruction instruction;
		if (AstIrUtil.isLocal(target) && indexes == null) {
			instruction = eINSTANCE.createInstAssign(lineNumber, target, value);
		} else {
			if (indexes == null) {
				// a store with an empty list of indexes
				instruction = eINSTANCE.createInstStore(lineNumber, target,
						new ArrayList<Expression>(0), value);
			} else {
				boolean copyNeeded = false;
				for (Expression index : indexes) {
					if (index.eContainer() != null) {
						copyNeeded = true;
						break;
					}
				}

				if (copyNeeded) {
					indexes = new ArrayList<Expression>(IrUtil.copy(indexes));
				}

				instruction = eINSTANCE.createInstStore(lineNumber, target,
						indexes, value);
			}
		}

		IrUtil.getLast(blocks).add(instruction);
		return null;
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
		// first add local variables
		Expression index = null;
		for (Generator generator : generators) {
			Variable variable = generator.getVariable();

			Var loopVar = procedure.newTempLocalVariable(
					Typer.getType(variable), variable.getName());
			Frontend.instance.putMapping(variable, loopVar);

			int lower = Evaluator.getIntValue(generator.getLower());
			Expression thisIndex = eINSTANCE.createExprVar(loopVar);
			if (lower != 0) {
				thisIndex = eINSTANCE.createExprBinary(thisIndex, MINUS,
						eINSTANCE.createExprInt(lower), thisIndex.getType());
			}

			int higher = Evaluator.getIntValue(generator.getHigher());

			if (index == null) {
				index = thisIndex;
			} else {
				index = eINSTANCE.createExprBinary(eINSTANCE.createExprBinary(
						index, TIMES,
						eINSTANCE.createExprInt(higher - lower + 1),
						index.getType()), PLUS, thisIndex, index.getType());
			}
		}

		indexes.add(index);

		// build the loops
		List<Block> blocks = this.blocks;
		List<BlockWhile> whiles = new ArrayList<BlockWhile>();
		for (Generator generator : generators) {
			// assigns the loop variable its initial value
			Var loopVar = Frontend.instance.getMapping(generator.getVariable());
			new ExprTransformer(procedure, blocks, loopVar).doSwitch(generator
					.getLower());

			// condition
			Expression higher = new ExprTransformer(procedure, blocks)
					.doSwitch(generator.getHigher());
			Expression condition = eINSTANCE.createExprBinary(
					eINSTANCE.createExprVar(loopVar), OpBinary.LE, higher,
					eINSTANCE.createTypeBool());

			// create while
			BlockWhile blockWhile = eINSTANCE.createBlockWhile();
			blockWhile.setJoinBlock(eINSTANCE.createBlockBasic());
			blockWhile.setCondition(condition);
			whiles.add(blockWhile);

			blocks.add(blockWhile);

			blocks = blockWhile.getBlocks();
		}

		// translates the expression (add to the innermost blocks)
		ExprTransformer tfer = new ExprTransformer(procedure, blocks, target,
				indexes);
		for (AstExpression astExpression : expressions) {
			tfer.doSwitch(astExpression);
		}

		// add increments
		int i = 0;
		for (Generator generator : generators) {
			int lineNumber = Util.getLocation(generator);
			Var loopVar = Frontend.instance.getMapping(generator.getVariable());
			IrUtil.getLast(whiles.get(i).getBlocks()).add(
					eINSTANCE.createInstAssign(lineNumber, loopVar, eINSTANCE
							.createExprBinary(eINSTANCE.createExprVar(loopVar),
									OpBinary.PLUS, eINSTANCE.createExprInt(1),
									loopVar.getType())));
			i++;
		}
	}

	/**
	 * Transforms the list of expressions of an AstExpressionList (without
	 * generators).
	 * 
	 * @param expressions
	 *            a list of AST expressions
	 */
	private void transformListSimple(List<AstExpression> expressions) {
		int i = 0;
		for (AstExpression expression : expressions) {
			List<Expression> newIndexes = new ArrayList<Expression>(indexes);
			newIndexes.add(eINSTANCE.createExprInt(i));

			new ExprTransformer(procedure, blocks, target, newIndexes)
					.doSwitch(expression);

			i++;
		}
	}

}
