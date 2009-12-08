package net.sf.orcc.frontend.parser;

import static net.sf.orcc.frontend.parser.Util.parseLocation;

import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.VarExpr;

import org.antlr.runtime.tree.Tree;

/**
 * This class defines a parser that can parse expressions used to initialize
 * state variables and translate them to IR expressions.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class GlobalExpressionParser extends ExpressionParser {

	/**
	 * Creates a new state expression parser with the given file
	 * 
	 * @param file
	 *            the file being parsed
	 * 
	 */
	public GlobalExpressionParser(String file) {
		super(file);
	}

	@Override
	protected Expression parseExprList(Tree tree) throws OrccException {
		if (tree.getChild(1).getChildCount() == 0) {
			return super.parseExprList(tree);
		} else {
			List<Expression> expressions = parseExpressions(tree.getChild(0));
			Location location;
			if (expressions.isEmpty()) {
				location = new Location();
			} else {
				location = expressions.get(0).getLocation();
			}

			return new ListExpr(location, expressions);
		}
	}

	protected Expression parseExprVar(Tree tree) throws OrccException {
		Location location = parseLocation(tree);

		String variableName = tree.getText();
		Variable variable = getVariableScope().get(variableName);
		if (variable == null) {
			throw new OrccException(file, location, "unknown variable: \""
					+ variableName + "\"");
		}

		Use use = new Use(variable);
		return new VarExpr(location, use);
	}

}
