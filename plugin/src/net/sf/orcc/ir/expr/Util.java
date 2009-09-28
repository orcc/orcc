package net.sf.orcc.ir.expr;

import net.sf.orcc.OrccException;

public class Util {

	public static int evaluateAsInteger(IExpr expr) throws OrccException {
		expr = expr.evaluate();
		if (expr.getExprType() != IExpr.INT) {
			// evaluated ok, but not as an integer
			throw new OrccException("expected integer expression");
		}

		return ((IntExpr) expr).getValue();
	}

}
