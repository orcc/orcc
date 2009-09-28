package net.sf.orcc.ir.expr;

public class Util {

	public static int evaluateAsInteger(IExpr expr)
			throws ExprEvaluateException {
		expr = expr.evaluate();
		if (expr.getExprType() != IExpr.INT) {
			// evaluated ok, but not as an integer
			throw new ExprEvaluateException("expected integer expression");
		}

		return ((IntExpr) expr).getValue();
	}

}
