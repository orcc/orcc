package net.sf.orcc.frontend.parser;

import static net.sf.orcc.frontend.parser.Util.parseLocation;

import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.frontend.parser.internal.RVCCalLexer;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.util.Scope;

import org.antlr.runtime.tree.Tree;

public class StatementParser {

	private final ExpressionParser exprParser;

	/**
	 * the file being parsed
	 */
	private final String file;

	private Scope<Variable> scope;

	private final TypeParser typeParser;

	/**
	 * Creates a new action parser with the given file
	 * 
	 * @param file
	 *            the file being parsed
	 * 
	 */
	public StatementParser(String file, TypeParser typeParser,
			ExpressionParser exprParser) {
		this.file = file;
		this.typeParser = typeParser;
		this.exprParser = exprParser;
	}

	public Block parseBlock(Tree tree, int start) throws OrccException {
		Scope<Variable> variables = new Scope<Variable>(scope, false);
		scope = variables;
		parseLocalVariables(variables, tree.getChild(start));

		// parse nodes
		List<CFGNode> nodes = null;
		scope = scope.getParent();

		return new Block(variables, nodes);
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

	public void parseLocalVariables(Scope<Variable> variables, Tree tree)
			throws OrccException {
		int numChildren = tree.getChildCount();
		for (int i = 0; i < numChildren; i++) {
			LocalVariable variable = parseLocalVariable(tree.getChild(i));
			variables.add(file, variable.getLocation(), variable.getName(),
					variable);
		}
	}

	/**
	 * Sets the current scope of variable.
	 * 
	 * @param scope
	 *            the current scope of variable
	 */
	public void setVariableScope(Scope<Variable> scope) {
		this.scope = scope;
		exprParser.setVariableScope(scope);
	}

}
