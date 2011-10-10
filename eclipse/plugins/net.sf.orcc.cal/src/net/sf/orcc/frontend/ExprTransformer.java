package net.sf.orcc.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.cal.cal.AstExpression;
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
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.services.Evaluator;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccUtil;

/**
 * This class transforms an AST expression into one or more IR instructions
 * and/or nodes, and returns an IR expression.
 * 
 */
public class ExprTransformer extends CalSwitch<Expression> {

	private List<Expression> indexes;

	private Var target;

	private Procedure procedure;

	private List<Node> nodes;

	public ExprTransformer(Procedure procedure, List<Node> nodes) {
		this(procedure, nodes, null);
	}

	public ExprTransformer(Procedure procedure, List<Node> nodes, Var target) {
		this(procedure, nodes, target, new ArrayList<Expression>());
	}

	public ExprTransformer(Procedure procedure, List<Node> nodes, Var target,
			List<Expression> indexes) {
		this.procedure = procedure;
		this.nodes = nodes;
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
	public Expression caseExpressionCall(ExpressionCall exprCall) {
		int lineNumber = Util.getLocation(exprCall);

		// retrieve IR procedure
		Function function = exprCall.getFunction();
		Procedure calledProcedure = Frontend.getMapping(function);

		// generates a new target
		Var target = procedure.newTempLocalVariable(
				calledProcedure.getReturnType(),
				"call_" + calledProcedure.getName());

		// transform parameters
		List<Expression> parameters = AstIrUtil.transformExpressions(procedure,
				nodes, exprCall.getParameters());

		// add call
		InstCall call = IrFactory.eINSTANCE.createInstCall(lineNumber, target,
				calledProcedure, parameters);
		IrUtil.getLast(nodes).add(call);

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
		NodeIf node = IrFactoryImpl.eINSTANCE.createNodeIf();
		node.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
		node.setLineNumber(lineNumber);
		node.setCondition(condition);

		Expression value = new ExprTransformer(procedure, node.getThenNodes(),
				target, indexes).doSwitch(expression.getThen());
		AstIrUtil.createAssignOrStore(node.getThenNodes(), lineNumber, target,
				indexes, value);
		value = new ExprTransformer(procedure, node.getElseNodes(), target,
				indexes).doSwitch(expression.getElse());
		AstIrUtil.createAssignOrStore(node.getElseNodes(), lineNumber, target,
				indexes, value);

		nodes.add(node);

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

		List<Expression> indexes = AstIrUtil.transformExpressions(procedure,
				nodes, expression.getIndexes());

		Var target = procedure.newTempLocalVariable(Typer.getType(expression),
				"local_" + var.getName());

		InstLoad load = IrFactory.eINSTANCE.createInstLoad(lineNumber, target,
				var, indexes);
		IrUtil.getLast(nodes).add(load);

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
						var = procedure.newTempLocalVariable(global.getType(),
								"local_" + global.getName());
					}
					InstLoad load = IrFactory.eINSTANCE.createInstLoad(var,
							global);
					IrUtil.getLast(nodes).add(load);
				}
			}

			return IrFactory.eINSTANCE.createExprVar(var);
		}
	}

	/**
	 * Checks that the current target is not <code>null</code>. If it is, this
	 * method initializes it according to the expressions and generators.
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
			Variable variable = generator.getVariable();
			Var loopVar = AstIrUtil.getLocalByName(procedure, variable);

			int lower = Evaluator.getIntValue(generator.getLower());
			Expression thisIndex = IrFactory.eINSTANCE.createExprVar(loopVar);
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
		List<Node> innerNodes = new ArrayList<Node>();
		ExprTransformer tfer = new ExprTransformer(procedure, innerNodes,
				target, indexes);
		for (AstExpression astExpression : expressions) {
			int lineNumber = Util.getLocation(astExpression);
			Expression value = tfer.doSwitch(astExpression);
			AstIrUtil.createAssignOrStore(innerNodes, lineNumber, target,
					indexes, value);
		}

		// build the loops from the inside out
		ListIterator<Generator> it = generators.listIterator(generators.size());
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
					IrFactory.eINSTANCE.createExprVar(loopVar), OpBinary.LE,
					higher, IrFactory.eINSTANCE.createTypeBool());

			// add increment to body
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
					lineNumber, loopVar, IrFactory.eINSTANCE.createExprBinary(
							IrFactory.eINSTANCE.createExprVar(loopVar),
							OpBinary.PLUS,
							IrFactory.eINSTANCE.createExprInt(1),
							loopVar.getType()));
			IrUtil.getLast(innerNodes).add(assign);

			// create while
			NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
			nodeWhile.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
			nodeWhile.setCondition(condition);
			nodeWhile.getNodes().addAll(innerNodes);

			// create assign
			NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();
			AstExpression astLower = generator.getLower();
			Expression lower = doSwitch(astLower);
			InstAssign assignInit = IrFactory.eINSTANCE.createInstAssign(
					lineNumber, loopVar, lower);
			block.add(assignInit);

			// nodes
			innerNodes = new ArrayList<Node>(2);
			innerNodes.add(block);
			innerNodes.add(nodeWhile);
		}

		// add the outer while node
		nodes.addAll(innerNodes);

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

			AstIrUtil.createAssignOrStore(nodes, lineNumber, target, indexes,
					value);
		}

		// restores target and indexes
		target = currentTarget;
		indexes = currentIndexes;
	}

}
