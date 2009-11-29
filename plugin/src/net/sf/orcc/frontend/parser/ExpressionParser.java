package net.sf.orcc.frontend.parser;

import static net.sf.orcc.frontend.parser.Util.parseLocation;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.frontend.parser.internal.ALBaseLexer;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.util.BinOpSeqParser;
import net.sf.orcc.util.Scope;

import org.antlr.runtime.tree.Tree;

/**
 * This class defines a parser that can parse AL expressions and translate them
 * to IR expressions.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ExpressionParser {

	/**
	 * the file being parsed
	 */
	private final String file;

	/**
	 * the current scope of variable
	 */
	private Scope<Variable> scope;

	/**
	 * Creates a new expression parser with the given file
	 * 
	 * @param file
	 *            the file being parsed
	 * 
	 */
	public ExpressionParser(String file) {
		this.file = file;
	}

	/**
	 * Returns the binary operator that match the type of the given tree.
	 * 
	 * @param op
	 *            a Tree that represents an operator
	 * @return a binary operator
	 * @throws OrccException
	 *             if the operator is not valid
	 */
	private BinaryOp parseBinaryOp(Tree op) throws OrccException {
		switch (op.getType()) {
		case ALBaseLexer.LOGIC_AND:
			return BinaryOp.LOGIC_AND;
		case ALBaseLexer.BITAND:
			return BinaryOp.BITAND;
		case ALBaseLexer.BITOR:
			return BinaryOp.BITOR;
		case ALBaseLexer.DIV:
			return BinaryOp.DIV;
		case ALBaseLexer.DIV_INT:
			return BinaryOp.DIV_INT;
		case ALBaseLexer.EQ:
			return BinaryOp.EQ;
		case ALBaseLexer.EXP:
			return BinaryOp.EXP;
		case ALBaseLexer.GE:
			return BinaryOp.GE;
		case ALBaseLexer.GT:
			return BinaryOp.GT;
		case ALBaseLexer.LE:
			return BinaryOp.LE;
		case ALBaseLexer.LT:
			return BinaryOp.LT;
		case ALBaseLexer.MINUS:
			return BinaryOp.MINUS;
		case ALBaseLexer.MOD:
			return BinaryOp.MOD;
		case ALBaseLexer.NE:
			return BinaryOp.NE;
		case ALBaseLexer.LOGIC_OR:
			return BinaryOp.LOGIC_OR;
		case ALBaseLexer.PLUS:
			return BinaryOp.PLUS;
		case ALBaseLexer.SHIFT_LEFT:
			return BinaryOp.SHIFT_LEFT;
		case ALBaseLexer.SHIFT_RIGHT:
			return BinaryOp.SHIFT_RIGHT;
		case ALBaseLexer.TIMES:
			return BinaryOp.TIMES;
		default:
			throw new OrccException("Unknown operator: " + op.getText());
		}
	}

	/**
	 * Parses a sequence of binary operations represented by an ANTLR tree, and
	 * transforms it to a binary expression tree using the operators'
	 * precedences.
	 * 
	 * @param expr
	 *            a tree that contains a binary operation sequence
	 * @return an expression
	 * @throws OrccException
	 */
	private Expression parseBinOpSeq(Tree expr) throws OrccException {
		Tree treeExprs = expr.getChild(0);
		int numExprs = treeExprs.getChildCount();
		List<Expression> expressions = new ArrayList<Expression>(numExprs);
		for (int i = 0; i < numExprs; i++) {
			expressions.add(parseExpression(treeExprs.getChild(i)));
		}

		Tree treeOps = expr.getChild(1);
		int numOps = treeOps.getChildCount();
		List<BinaryOp> operators = new ArrayList<BinaryOp>(numOps);
		for (int i = 0; i < numOps; i++) {
			operators.add(parseBinaryOp(treeOps.getChild(i)));
		}

		return BinOpSeqParser.parse(expressions, operators);
	}

	/**
	 * Parses the given tree as an expression.
	 * 
	 * @param expr
	 *            a tree that contains an expression
	 * @return an {@link Expression}.
	 * @throws OrccException
	 */
	public Expression parseExpression(Tree expr) throws OrccException {
		switch (expr.getType()) {
		case ALBaseLexer.EXPR_BINARY:
			return parseBinOpSeq(expr);
		case ALBaseLexer.EXPR_BOOL: {
			expr = expr.getChild(0);
			boolean value = Boolean.parseBoolean(expr.getText());
			return new BoolExpr(parseLocation(expr), value);
		}
		case ALBaseLexer.EXPR_FLOAT:
			throw new OrccException("not yet implemented!");
		case ALBaseLexer.EXPR_INT:
			expr = expr.getChild(0);
			int value = Integer.parseInt(expr.getText());
			return new IntExpr(parseLocation(expr), value);
		case ALBaseLexer.EXPR_STRING:
			expr = expr.getChild(0);
			return new StringExpr(parseLocation(expr), expr.getText());
		case ALBaseLexer.EXPR_VAR:
			return parseExprVar(expr.getChild(0));
		default:
			throw new OrccException("not yet implemented");
		}
	}

	private Expression parseExprVar(Tree tree) throws OrccException {
		Location location = parseLocation(tree);

		Variable variable = scope.get(tree.getText());
		if (variable == null) {
			throw new OrccException(file, location, "unknown variable: "
					+ variable);
		}
		Use localUse = new Use(variable);
		return new VarExpr(location, localUse);
	}

	/**
	 * Sets the current scope of variable.
	 * 
	 * @param scope
	 *            the current scope of variable
	 */
	public void setVariableScope(Scope<Variable> scope) {
		this.scope = scope;
	}

}