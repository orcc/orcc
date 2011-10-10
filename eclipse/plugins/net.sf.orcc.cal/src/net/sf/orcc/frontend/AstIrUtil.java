package net.sf.orcc.frontend;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;

public class AstIrUtil {

	public static void createAssignOrStore(Procedure procedure, int lineNumber,
			Var target, List<Expression> indexes, Expression value) {
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

		procedure.getLast().add(instruction);
	}

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
			List<AstExpression> expressions) {
		int length = expressions.size();
		List<Expression> irExpressions = new ArrayList<Expression>(length);
		for (AstExpression expression : expressions) {
			ExprTransformer transformer = new ExprTransformer(procedure);
			irExpressions.add(transformer.doSwitch(expression));
		}
		return irExpressions;
	}

}
