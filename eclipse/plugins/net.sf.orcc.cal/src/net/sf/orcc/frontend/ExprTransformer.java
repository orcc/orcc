package net.sf.orcc.frontend;

import static net.sf.orcc.ir.IrFactory.eINSTANCE;
import static net.sf.orcc.ir.OpBinary.MINUS;
import static net.sf.orcc.ir.OpBinary.PLUS;
import static net.sf.orcc.ir.OpBinary.TIMES;

import java.util.ArrayList;
import java.util.List;

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
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.ecore.EObject;

/**
 * This class transforms an AST expression into one or more IR instructions
 * and/or nodes, and returns an IR expression.
 * 
 */
public class ExprTransformer extends CalSwitch<Expression> {

	private List<Expression> indexes;

	private List<Node> nodes;

	private Procedure procedure;

	private Var target;

	public ExprTransformer(Procedure procedure, List<Node> nodes) {
		this(procedure, nodes, null);
	}

	public ExprTransformer(Procedure procedure, List<Node> nodes, Var target) {
		this(procedure, nodes, target, null);
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
		Expression e1 = new ExprTransformer(procedure, nodes)
				.doSwitch(expression.getLeft());
		Expression e2 = new ExprTransformer(procedure, nodes)
				.doSwitch(expression.getRight());

		Expression value = IrFactory.eINSTANCE.createExprBinary(e1, op, e2,
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
		Procedure calledProc = Frontend.getMapping(exprCall.getFunction());

		// transform parameters
		List<Expression> parameters = AstIrUtil.transformExpressions(procedure,
				nodes, exprCall.getParameters());

		// generates a new target
		Var callTarget;
		if (target == null || indexes != null) {
			callTarget = procedure.newTempLocalVariable(
					calledProc.getReturnType(), "call_" + calledProc.getName());
		} else {
			callTarget = target;
		}

		// add call
		InstCall call = IrFactory.eINSTANCE.createInstCall(lineNumber,
				callTarget, calledProc, parameters);
		IrUtil.getLast(nodes).add(call);

		// return expr
		if (target != null && indexes == null) {
			return null;
		} else {
			return storeExpr(exprCall, eINSTANCE.createExprVar(callTarget));
		}
	}

	@Override
	public Expression caseExpressionIf(ExpressionIf expression) {
		int lineNumber = Util.getLocation(expression);
		Expression condition = new ExprTransformer(procedure, nodes)
				.doSwitch(expression.getCondition());

		// transforms "then" statements and "else" statements
		NodeIf node = eINSTANCE.createNodeIf();
		node.setJoinNode(eINSTANCE.createNodeBlock());
		node.setLineNumber(lineNumber);
		node.setCondition(condition);

		nodes.add(node);

		Var ifTarget;
		if (target == null) {
			ifTarget = procedure.newTempLocalVariable(
					Typer.getType(expression), "tmp_if");
		} else {
			ifTarget = target;
		}

		new ExprTransformer(procedure, node.getThenNodes(), ifTarget, indexes)
				.doSwitch(expression.getThen());
		new ExprTransformer(procedure, node.getElseNodes(), ifTarget, indexes)
				.doSwitch(expression.getElse());

		// return expr
		if (target == null) {
			return IrFactory.eINSTANCE.createExprVar(ifTarget);
		} else {
			return null;
		}
	}

	@Override
	public Expression caseExpressionIndex(ExpressionIndex expression) {
		// we always load in this case
		int lineNumber = Util.getLocation(expression);
		Variable variable = expression.getSource().getVariable();
		Var var = Frontend.getMapping(variable);

		List<Expression> indexes = AstIrUtil.transformExpressions(procedure,
				nodes, expression.getIndexes());

		Var loadTarget;
		if (target == null || this.indexes != null) {
			loadTarget = procedure.newTempLocalVariable(
					Typer.getType(expression), "local_" + var.getName());
		} else {
			loadTarget = target;
		}

		InstLoad load = eINSTANCE.createInstLoad(lineNumber, loadTarget, var,
				indexes);
		IrUtil.getLast(nodes).add(load);

		// return expr
		if (target != null && this.indexes == null) {
			return null;
		} else {
			return storeExpr(expression, eINSTANCE.createExprVar(loadTarget));
		}
	}

	@Override
	public Expression caseExpressionInteger(ExpressionInteger expression) {
		Expression value = eINSTANCE.createExprInt(expression.getValue());
		return storeExpr(expression, value);
	}

	@Override
	public Expression caseExpressionList(ExpressionList astExpression) {
		List<AstExpression> expressions = astExpression.getExpressions();
		List<Generator> generators = astExpression.getGenerators();

		if (generators.isEmpty()) {
			transformListSimple(expressions);
		} else {
			transformListGenerators(expressions, generators);
		}

		return null;
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
		Expression expr = new ExprTransformer(procedure, nodes)
				.doSwitch(expression.getExpression());

		if (OpUnary.NUM_ELTS == op) {
			TypeList typeList = (TypeList) expr.getType();
			return IrFactory.eINSTANCE.createExprInt(typeList.getSize());
		}

		Expression value = eINSTANCE.createExprUnary(op, expr,
				Typer.getType(expression));
		return storeExpr(expression, value);
	}

	@Override
	public Expression caseExpressionVariable(ExpressionVariable expression) {
		Variable variable = expression.getValue().getVariable();
		Var var = Frontend.getMapping(variable);

		Expression value;
		if (var.getType().isList()) {
			if (target == null) {
				return eINSTANCE.createExprVar(var);
			} else {
				return copyList(var);
			}
		} else {
			if (procedure != null) {
				if (var.isGlobal()) {
					Var global = var;
					var = procedure.getLocal("local_" + global.getName());
					if (var == null) {
						var = procedure.newTempLocalVariable(global.getType(),
								"local_" + global.getName());
					}
					InstLoad load = eINSTANCE.createInstLoad(var, global);
					IrUtil.getLast(nodes).add(load);
				}
			}

			value = eINSTANCE.createExprVar(var);
		}

		return storeExpr(expression, value);
	}

	private Expression copyList(Var var) {
		TypeList typeList = (TypeList) var.getType();
		List<Node> nodes = this.nodes;
		List<NodeWhile> whiles = new ArrayList<NodeWhile>();
		List<Var> loopVars = new ArrayList<Var>();
		List<Expression> indexes = new ArrayList<Expression>();
		for (int size : typeList.getDimensions()) {
			// add loop variable
			Var loopVar = procedure.newTempLocalVariable(
					eINSTANCE.createTypeInt(32), "idx_" + var.getName());
			loopVars.add(loopVar);
			InstAssign assign = eINSTANCE.createInstAssign(loopVar,
					eINSTANCE.createExprInt(0));
			IrUtil.getLast(nodes).add(assign);

			// add index
			indexes.add(eINSTANCE.createExprVar(loopVar));

			// create while node
			Expression condition = eINSTANCE.createExprBinary(
					eINSTANCE.createExprVar(loopVar), OpBinary.LT,
					eINSTANCE.createExprInt(size), eINSTANCE.createTypeBool());

			NodeWhile nodeWhile = eINSTANCE.createNodeWhile();
			nodeWhile.setJoinNode(eINSTANCE.createNodeBlock());
			nodeWhile.setCondition(condition);
			whiles.add(nodeWhile);

			nodes.add(nodeWhile);

			nodes = nodeWhile.getNodes();
		}

		// load
		Var loadTarget = procedure.newTempLocalVariable(
				typeList.getInnermostType(), "local_" + var.getName());
		InstLoad load = eINSTANCE.createInstLoad(0, loadTarget, var, indexes);
		IrUtil.getLast(nodes).add(load);

		// store
		if (this.indexes == null) {
			indexes = new ArrayList<Expression>(IrUtil.copy(indexes));
		} else {
			indexes = this.indexes;
		}
		InstStore store = eINSTANCE.createInstStore(0, target, indexes,
				eINSTANCE.createExprVar(loadTarget));
		IrUtil.getLast(nodes).add(store);

		// add increments
		int size = typeList.getDimensions().size();
		for (int i = 0; i < size; i++) {
			Var loopVar = loopVars.get(i);
			IrUtil.getLast(whiles.get(i).getNodes()).add(
					eINSTANCE.createInstAssign(loopVar, eINSTANCE
							.createExprBinary(eINSTANCE.createExprVar(loopVar),
									OpBinary.PLUS, eINSTANCE.createExprInt(1),
									eINSTANCE.createTypeInt(32))));
		}

		return null;
	}

	private Expression storeExpr(EObject astObject, Expression value) {
		if (target == null) {
			return value;
		}

		int lineNumber = Util.getLocation(astObject);

		Instruction instruction;
		if (target.isLocal() && indexes == null) {
			instruction = IrFactory.eINSTANCE.createInstAssign(lineNumber,
					target, value);
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

			instruction = IrFactory.eINSTANCE.createInstStore(lineNumber,
					target, indexes, value);
		}

		IrUtil.getLast(nodes).add(instruction);
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
		if (indexes == null) {
			indexes = new ArrayList<Expression>();
		}

		// first add local variables
		Expression index = null;
		for (Generator generator : generators) {
			Variable variable = generator.getVariable();
			Var loopVar = AstIrUtil.getLocalByName(procedure, variable);

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
		List<Node> nodes = this.nodes;
		List<NodeWhile> whiles = new ArrayList<NodeWhile>();
		for (Generator generator : generators) {
			// assigns the loop variable its initial value
			Var loopVar = Frontend.getMapping(generator.getVariable());
			new ExprTransformer(procedure, nodes, loopVar).doSwitch(generator
					.getLower());

			// condition
			Expression higher = new ExprTransformer(procedure, nodes)
					.doSwitch(generator.getHigher());
			Expression condition = eINSTANCE.createExprBinary(
					eINSTANCE.createExprVar(loopVar), OpBinary.LE, higher,
					eINSTANCE.createTypeBool());

			// create while
			NodeWhile nodeWhile = eINSTANCE.createNodeWhile();
			nodeWhile.setJoinNode(eINSTANCE.createNodeBlock());
			nodeWhile.setCondition(condition);
			whiles.add(nodeWhile);

			nodes.add(nodeWhile);

			nodes = nodeWhile.getNodes();
		}

		// translates the expression (add to the innermost nodes)
		ExprTransformer tfer = new ExprTransformer(procedure, nodes, target,
				indexes);
		for (AstExpression astExpression : expressions) {
			tfer.doSwitch(astExpression);
		}

		// add increments
		int i = 0;
		for (Generator generator : generators) {
			int lineNumber = Util.getLocation(generator);
			Var loopVar = Frontend.getMapping(generator.getVariable());
			IrUtil.getLast(whiles.get(i).getNodes()).add(
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
		if (indexes == null) {
			indexes = new ArrayList<Expression>();
		}

		int i = 0;
		for (AstExpression expression : expressions) {
			List<Expression> newIndexes = new ArrayList<Expression>(indexes);
			newIndexes.add(eINSTANCE.createExprInt(i));

			new ExprTransformer(procedure, nodes, target, newIndexes)
					.doSwitch(expression);

			i++;
		}
	}

}
