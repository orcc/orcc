package net.sf.orcc.frontend;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

public class AstIrUtil {

	/**
	 * Returns the IR equivalent of the given AST variable using its name. This
	 * method is intended for generators/foreach loops.
	 * 
	 * @param variable
	 *            a local variable
	 * @return the IR equivalent of the given AST variable
	 */
	public static Var getLocalByName(Procedure procedure, Variable variable) {
		Var var = procedure.getLocal(variable.getName());
		if (var == null) {
			var = Frontend.getMapping(variable);
			procedure.getLocals().add(var);
		} else {
			Frontend.putMapping(variable, var);
		}

		return var;
	}

	/**
	 * Transforms the given AST expressions to a list of IR expressions. In the
	 * process nodes may be created and added to the current {@link #procedure},
	 * since many RVC-CAL expressions are expressed with IR statements.
	 * 
	 * @param expressions
	 *            a list of AST expressions
	 * @return a list of IR expressions
	 */
	public static List<Expression> transformExpressions(Procedure procedure,
			List<Node> nodes, List<AstExpression> expressions) {
		int length = expressions.size();
		List<Expression> irExpressions = new ArrayList<Expression>(length);
		for (AstExpression expression : expressions) {
			ExprTransformer transformer = new ExprTransformer(procedure, nodes);
			irExpressions.add(transformer.doSwitch(expression));
		}
		return irExpressions;
	}

}
