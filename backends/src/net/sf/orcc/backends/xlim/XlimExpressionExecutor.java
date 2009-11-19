package net.sf.orcc.backends.xlim;

import java.util.Map;

import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.expr.VarExpr;

/**
 * XlimExpressionExecutor executes initialization to get initial values
 * 
 * @author Samuel Keller
 */
public class XlimExpressionExecutor extends ExpressionEvaluator {

	/**
	 * Variables data
	 */
	private Map<String, Object> datas;

	/**
	 * Constructs a XlimExpressionExecutor with variables
	 * 
	 * @param datas
	 *            Variables data
	 */
	public XlimExpressionExecutor(Map<String, Object> datas) {
		this.datas = datas;
	}

	/**
	 * Interprets variable expression by getting in the map
	 * 
	 * @param expr
	 *            Variable expression
	 * @return Value of the variable
	 */
	public Object interpret(VarExpr expr, Object... args) {
		return datas.get(expr.getVar().getVariable().toString());
	}

}
