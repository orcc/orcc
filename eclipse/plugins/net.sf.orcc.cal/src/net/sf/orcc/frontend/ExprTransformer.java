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
import net.sf.orcc.ir.ExprVar;
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
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Use;
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

	public ExprTransformer(Procedure procedure) {
		this.procedure = procedure;
		indexes = new ArrayList<Expression>();
	}

	public ExprTransformer(Procedure procedure, Var target) {
		this(procedure);
		this.target = target;
	}

	public ExprTransformer(Procedure procedure, Var target,
			List<Expression> indexes) {
		this.procedure = procedure;
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
				exprCall.getParameters());

		// add call
		InstCall call = IrFactory.eINSTANCE.createInstCall(lineNumber, target,
				calledProcedure, parameters);
		procedure.getLast().add(call);

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

		List<Expression> indexes = AstIrUtil.transformExpressions(procedure,
				expression.getIndexes());

		Var target = procedure.newTempLocalVariable(Typer.getType(expression),
				"local_" + var.getName());

		InstLoad load = IrFactory.eINSTANCE.createInstLoad(lineNumber, target,
				var, indexes);
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
						var = procedure.newTempLocalVariable(global.getType(),
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
	 * Returns a list of CFG nodes from the given expression. This method
	 * creates a new block node to hold the statements created when translating
	 * the expression, transforms the expression, and transfers the nodes
	 * created to a new list that is the result.
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
	 * Returns a list of CFG nodes from the given list of statements. This
	 * method creates a new block node to hold the statements, transforms the
	 * statements, and transfers the nodes created to a new list that is the
	 * result.
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
		List<Node> nodes = getNodes(expressions);

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
			NodeBlock block = IrUtil.getLast(nodes);
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
					lineNumber, loopVar, IrFactory.eINSTANCE.createExprBinary(
							IrFactory.eINSTANCE.createExprVar(loopVar),
							OpBinary.PLUS,
							IrFactory.eINSTANCE.createExprInt(1),
							loopVar.getType()));
			block.add(assign);

			// create while
			NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
			nodeWhile.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
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
