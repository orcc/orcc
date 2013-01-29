package net.sf.orcc.simulators;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.ir.util.ExpressionPrinter;

/**
 * This class extends ExpressionEvaluator to support short circuit evaluation.
 * 
 * @author groquier
 * 
 */
public class RuntimeExpressionEvaluator extends ExpressionEvaluator {

	@Override
	public Object caseExprBinary(ExprBinary expr) {
		Object result = doSwitch(expr.getE1());
		if (expr.getOp() == OpBinary.LOGIC_AND) {
			if (result != null && ((Boolean) result) == false) {
				return result;
			}
		} else if (expr.getOp() == OpBinary.LOGIC_OR) {
			if (result != null && ((Boolean) result) == true) {
				return result;
			}
		}

		result = super.caseExprBinary(expr);

		if (result == null) {
			throw new OrccRuntimeException(
					"Could not evaluate binary expression:\n"
							+ new ExpressionPrinter().doSwitch(expr) + "\n");
		}
		return result;
	}

}
