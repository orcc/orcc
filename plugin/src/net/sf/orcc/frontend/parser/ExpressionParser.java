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
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.type.VoidType;
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
	protected final String file;

	/**
	 * a map of global variables that were loaded
	 */
	private Map<Variable, LocalVariable> globalsLoaded;

	/**
	 * a list of nodes at the end of which this parser may add nodes
	 */
	private List<CFGNode> nodes;

	private Procedure procedure;

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
				List<Expression> indexes = new ArrayList<Expression>(0);
				Use use = new Use(variable);
				Load load = new Load(location, local, use, indexes);
				use.setNode(load);
				BlockNode block = BlockNode.getLast(procedure, nodes);
				block.add(load);
			}

			variable = local;
		}

		return variable;
	}

	/**
	 * Returns the current scope of variable.
	 * 
	 * @return the current scope of variable
	 */
	public Scope<Variable> getVariableScope() {
		return scope;
	}

	/**
	 * Sets the current variable scope and node list where nodes may be added
	 * when an expression is translated as statements. Such expressions are
	 * calls, ifs, and lists.
	 * 
	 * @param scope
	 *            the current scope of variable
	 * @param nodes
	 *            a list of CFG nodes
	 */
	public void init(Scope<Variable> scope, List<CFGNode> nodes) {
		this.scope = scope;
		this.nodes = nodes;
		globalsLoaded.clear();
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
			operators.add(parseOpBinary(treeOps.getChild(i)));
		}

		return BinOpSeqParser.parse(expressions, operators);
	}

	private Expression parseExprBool(Tree tree) {
		Tree child = tree.getChild(0);
		boolean value = Boolean.parseBoolean(child.getText());
		return new BoolExpr(parseLocation(child), value);
	}

	private Expression parseExprCall(Tree tree) {
		System.out.println("EXPR_CAL not implemented");
		return new IntExpr(42);
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
		case ALBaseLexer.EXPR_BOOL:
			return parseExprBool(tree);
		case ALBaseLexer.EXPR_CALL:
			return parseExprCall(tree);
		case ALBaseLexer.EXPR_FLOAT:
			return parseExprFloat(tree);
		case ALBaseLexer.EXPR_IDX:
			return parseExprIndex(tree);
		case ALBaseLexer.EXPR_IF:
			return parseExprIf(tree);
		case ALBaseLexer.EXPR_INT:
			return parseExprInt(tree);
		case ALBaseLexer.EXPR_LIST:
			return parseExprList(tree);
		case ALBaseLexer.EXPR_STRING:
			return parseExprString(tree);
		case ALBaseLexer.EXPR_UNARY:
			return parseExprUnary(tree);
		case ALBaseLexer.EXPR_VAR:
			return parseExprVar(tree.getChild(0));
		default:
			throw new OrccException("unknown expression type: "
					+ tree.getText());
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

	private Expression parseExprFloat(Tree tree) throws OrccException {
		throw new OrccException("not yet implemented!");
	}

	private Expression parseExprIf(Tree tree) {
		System.out.println("EXPR_IF not implemented");
		return new IntExpr(42);
	}

	private Expression parseExprIndex(Tree tree) {
		System.out.println("EXPR_IDX not implemented");
		return new IntExpr(42);
	}

	private Expression parseExprInt(Tree tree) {
		Tree child = tree.getChild(0);
		int value = Integer.decode(child.getText());
		return new IntExpr(parseLocation(child), value);
	}

	protected Expression parseExprList(Tree tree) throws OrccException {
		System.out.println("EXPR_LIST not implemented");
		return new IntExpr(42);
	}

	private Expression parseExprString(Tree tree) {
		Tree child = tree.getChild(0);
		return new StringExpr(parseLocation(child), child.getText());
	}

	private Expression parseExprUnary(Tree tree) throws OrccException {
		Tree opTree = tree.getChild(0);
		Location location = parseLocation(opTree);
		UnaryOp op = parseOpUnary(opTree);

		Expression expression = parseExpression(tree.getChild(1));
		return new UnaryExpr(location, op, expression, new VoidType());
	}

	protected Expression parseExprVar(Tree tree) throws OrccException {
		Location location = parseLocation(tree);

		Variable variable = getVariable(location, tree.getText());
		Use localUse = new Use(variable);
		return new VarExpr(location, localUse);
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
	private BinaryOp parseOpBinary(Tree op) throws OrccException {
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
	 * Returns the unary operator that match the type of the given tree.
	 * 
	 * @param op
	 *            a Tree that represents an operator
	 * @return a unary operator
	 * @throws OrccException
	 *             if the operator is not valid
	 */
	private UnaryOp parseOpUnary(Tree op) throws OrccException {
		switch (op.getType()) {
		case ALBaseLexer.LOGIC_NOT:
			return UnaryOp.LOGIC_NOT;
		case ALBaseLexer.MINUS:
			return UnaryOp.MINUS;
		case ALBaseLexer.NUM_ELTS:
			return UnaryOp.NUM_ELTS;
		default:
			throw new OrccException("Unknown operator: " + op.getText());
		}
	}

	/**
	 * Sets the current scope of variable.
	 * 
	 * @param scope
	 *            the current scope of variable
	 */
	public void setVariableScope(Procedure procedure, Scope<Variable> scope) {
		this.procedure = procedure;
		this.scope = scope;
	}

}
