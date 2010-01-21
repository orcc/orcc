package net.sf.orcc.frontend.parser;

import static net.sf.orcc.frontend.parser.Util.parseLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccException;
import net.sf.orcc.frontend.parser.internal.ALBaseLexer;
import net.sf.orcc.frontend.parser.internal.RVCCalLexer;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.util.Scope;

import org.antlr.runtime.tree.Tree;

public class StatementParser {

	private final ExpressionParser exprParser;

	/**
	 * the file being parsed
	 */
	private final String file;

	/**
	 * a map of global variables that should be stored
	 */
	private Map<LocalVariable, Variable> globalsToStore;

	private List<CFGNode> nodes;

	private Procedure procedure;

	private Scope<Variable> scope;

	private final TypeParser typeParser;

	/**
	 * Creates a new action parser with the given file
	 * 
	 * @param file
	 *            the file being parsed
	 * 
	 */
	public StatementParser(String file, TypeParser typeParser) {
		this.file = file;
		this.typeParser = typeParser;
		this.exprParser = new ExpressionParser(file);

		globalsToStore = new LinkedHashMap<LocalVariable, Variable>();
	}

	/**
	 * Returns the variable whose name is given. If the variable is a global
	 * calls the {@link ExpressionParser#getVariable(Location, String)} method
	 * to get the local variable where it is stored. The {@link #globalsToStore}
	 * map is updated if no global is associated with the local variable.
	 * 
	 * @param location
	 *            a location
	 * @param variableName
	 *            variable name
	 * @return a variable
	 * @throws OrccException
	 */
	private Variable getVariable(Location location, String variableName)
			throws OrccException {
		Variable variable = scope.get(variableName);
		if (variable == null) {
			throw new OrccException(file, location, "unknown variable: \""
					+ variableName + "\"");
		}

		if (variable.isGlobal()) {
			LocalVariable local = (LocalVariable) exprParser.getVariable(
					location, variableName);
			Variable global = globalsToStore.get(local);
			if (global == null) {
				globalsToStore.put(local, variable);
			}

			return local;
		} else {
			return variable;
		}
	}

	/**
	 * Sets the current scope and node list where nodes may be added when
	 * statements are parsed.
	 * 
	 * @param scope
	 *            the current scope of variable
	 * @param nodes
	 *            a list of CFG nodes
	 */
	public void init(Scope<Variable> scope, List<CFGNode> nodes) {
		this.nodes = nodes;
		this.scope = scope;
		exprParser.init(scope, nodes);
	}

	/**
	 * Parses the given tree as a local parameter/local variable.
	 * 
	 * @param tree
	 *            a tree whose root is VARIABLE.
	 * @return a local variable
	 * @throws OrccException
	 */
	private LocalVariable parseLocalVariable(Tree tree) throws OrccException {
		Type type = typeParser.parseType(tree.getChild(0));
		Tree nameTree = tree.getChild(1);
		Location location = parseLocation(nameTree);
		String name = nameTree.getText();
		boolean assignable = (tree.getChild(2).getType() == RVCCalLexer.ASSIGNABLE);

		if (tree.getChildCount() == 4) {
			exprParser.parseExpression(tree.getChild(3));
		}

		Instruction instruction = null;
		return new LocalVariable(assignable, 0, location, name, instruction,
				null, type);
	}

	/**
	 * Parses the given tree as a list of local variables, which are added to
	 * the current scope.
	 * 
	 * @param tree
	 *            a tree
	 * @throws OrccException
	 */
	public void parseLocalVariables(Tree tree) throws OrccException {
		int numChildren = tree.getChildCount();
		for (int i = 0; i < numChildren; i++) {
			LocalVariable variable = parseLocalVariable(tree.getChild(i));
			scope.add(file, variable.getLocation(), variable.getName(),
					variable);
		}
	}

	/**
	 * Parses one statement.
	 * 
	 * @param tree
	 * @throws OrccException
	 */
	private void parseStatement(Tree tree) throws OrccException {
		if (tree.getType() == ALBaseLexer.ASSIGN) {
			Location location = parseLocation(tree.getChild(0));
			String targetName = tree.getChild(0).getText();
			List<Expression> indexes = exprParser.parseExpressions(tree
					.getChild(1));
			Expression value = exprParser.parseExpression(tree.getChild(2));

			if (indexes.isEmpty()) {
				Variable target = getVariable(location, targetName);
				LocalVariable local = (LocalVariable) target;

				BlockNode block = BlockNode.last(procedure, nodes);
				Assign assign = new Assign(block, location, local, value);
				block.add(assign);
			} else {
				// TODO: store
			}
		}
	}

	/**
	 * Parses statements.
	 * 
	 * @param tree
	 *            a STATEMENTS tree
	 * @throws OrccException
	 */
	public void parseStatements(Tree statements) throws OrccException {
		// parse nodes
		int n = statements.getChildCount();
		for (int i = 0; i < n; i++) {
			parseStatement(statements.getChild(i));
		}

		// adds global to store
		storeGlobals();
	}

	/**
	 * Sets the current scope of variable.
	 * 
	 * @param scope
	 *            the current scope of variable
	 */
	public void setVariableScope(Procedure procedure, Scope<Variable> scope) {
		this.scope = scope;
		this.procedure = procedure;
		exprParser.setVariableScope(procedure, scope);
	}

	/**
	 * Store back the globals that were modified. They are in the
	 * {@link #globalsToStore} map.
	 */
	private void storeGlobals() {
		for (Entry<LocalVariable, Variable> entry : globalsToStore.entrySet()) {
			LocalVariable source = entry.getKey();
			Variable target = entry.getValue();

			BlockNode block = BlockNode.last(procedure, nodes);
			Location location = block.getLocation();

			Use use = new Use(target, block);
			List<Expression> indexes = new ArrayList<Expression>(0);
			Expression value = new VarExpr(location, new Use(source, block));
			Store store = new Store(block, location, use, indexes, value);
			block.add(store);
		}

		globalsToStore.clear();
	}

}
