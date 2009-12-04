package net.sf.orcc.frontend.parser;

import static net.sf.orcc.frontend.parser.Util.parseLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.frontend.parser.internal.ALBaseLexer;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.nodes.BlockNode;
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
	 * a map of global variables that were loaded
	 */
	private Map<Variable, LocalVariable> globalsLoaded;

	/**
	 * a list of nodes at the end of which this parser may add nodes
	 */
	private List<CFGNode> nodes;

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
		globalsLoaded = new LinkedHashMap<Variable, LocalVariable>();
	}

	/**
	 * Returns the variable whose name is given. If the variable is a global
	 * returns the local variable where it is stored. If no local variable
	 * matches, adds a Load instruction to nodes.
	 * 
	 * @param location
	 *            a location
	 * @param variableName
	 *            variable name
	 * @return a variable
	 * @throws OrccException
	 */
	public Variable getVariable(Location location, String variableName)
			throws OrccException {
		Variable variable = scope.get(variableName);
		if (variable == null) {
			throw new OrccException(file, location, "unknown variable: \""
					+ variableName + "\"");
		}

		if (variable.isGlobal()) {
			LocalVariable local = globalsLoaded.get(variable);
			if (local == null) {
				local = new LocalVariable(true, 0, location, "_local_"
						+ variableName, null, null, variable.getType());
				scope.add(file, location, local.getBaseName(), local);
				globalsLoaded.put(variable, local);

				// add a Load
				BlockNode block = BlockNode.last(nodes);
				List<Expression> indexes = new ArrayList<Expression>(0);
				Use use = new Use(variable, block);
				Load load = new Load(block, location, local, use, indexes);
				block.add(load);
			}

			variable = local;
		}

		return variable;
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
	 * @param tree
	 *            a tree that contains an expression
	 * @return an {@link Expression}.
	 * @throws OrccException
	 */
	public Expression parseExpression(Tree tree) throws OrccException {
		switch (tree.getType()) {
		case ALBaseLexer.EXPR_BINARY:
			return parseBinOpSeq(tree);
		case ALBaseLexer.EXPR_BOOL: {
			tree = tree.getChild(0);
			boolean value = Boolean.parseBoolean(tree.getText());
			return new BoolExpr(parseLocation(tree), value);
		}
		case ALBaseLexer.EXPR_FLOAT:
			throw new OrccException("not yet implemented!");
		case ALBaseLexer.EXPR_INT:
			tree = tree.getChild(0);
			int value = Integer.parseInt(tree.getText());
			return new IntExpr(parseLocation(tree), value);
		case ALBaseLexer.EXPR_STRING:
			tree = tree.getChild(0);
			return new StringExpr(parseLocation(tree), tree.getText());
		case ALBaseLexer.EXPR_VAR:
			return parseExprVar(tree.getChild(0));
		default:
			throw new OrccException("not yet implemented");
		}
	}

	/**
	 * Parses the children of the given tree as expressions.
	 * 
	 * @param tree
	 *            a tree whose children are expressions
	 * @return a list of {@link Expression}s
	 * @throws OrccException
	 */
	public List<Expression> parseExpressions(Tree tree) throws OrccException {
		int n = tree.getChildCount();
		List<Expression> list = new ArrayList<Expression>(n);
		for (int i = 0; i < n; i++) {
			list.add(parseExpression(tree.getChild(i)));
		}

		return list;
	}

	private Expression parseExprVar(Tree tree) throws OrccException {
		Location location = parseLocation(tree);

		Variable variable = getVariable(location, tree.getText());
		Use localUse = new Use(variable);
		return new VarExpr(location, localUse);
	}

	/**
	 * Sets the node list where nodes may be added when an expression is
	 * translated as statements. Such expressions are calls, ifs, and lists.
	 * 
	 * @param nodes
	 *            a list of CFG nodes
	 */
	public void setCFGNodeList(List<CFGNode> nodes) {
		this.nodes = nodes;
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