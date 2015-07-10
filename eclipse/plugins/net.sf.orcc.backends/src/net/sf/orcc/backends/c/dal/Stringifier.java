package net.sf.orcc.backends.c.dal;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.util.AbstractIrVisitor;

/**
 * Stringify lists of expressions
 *
 * @author James Guthrie
 *
 */
public class Stringifier extends AbstractIrVisitor<String> {

	public Stringifier() {
		super(true);
	}

	@Override
	public String caseExpression(Expression expr) {
		throw new OrccRuntimeException("unhandled expr: " + expr);
	}

	@Override
	public String caseArgByRef(ArgByRef argByRef) {
		return argByRef.getUse().getVariable().getName();
	}

	@Override
	public String caseArgByVal(ArgByVal argByVal) {
		return doSwitch(argByVal.getValue());
	}

	@Override
	public String caseExprVar(ExprVar exprVar) {
		return exprVar.getUse().getVariable().getName();
	}

	@Override
	public String caseExprInt(ExprInt exprInt) {
		return "" + exprInt.getIntValue();
	}

	@Override
	public String caseExprUnary(ExprUnary exprUnary) {
		return exprUnary.getOp().toString().toLowerCase() + "_" + doSwitch(exprUnary.getExpr());
	}

	@Override
	public String caseExprBinary(ExprBinary exprBinary) {
		String join = "";
		String left = doSwitch(exprBinary.getE1());
		String right = doSwitch(exprBinary.getE2());
		join = exprBinary.getOp().toString().toLowerCase();
		return left + "_" + join + "_" + right;
	}

	public <E extends EObject> String doSwitch(EList<E> arguments) {
		String retStr = "";
		Iterator<E> iter = arguments.iterator();
		while (iter.hasNext()) {
			E a = iter.next();
			retStr += this.doSwitch(a);
			if (iter.hasNext()) {
				retStr += "_";
			}
		}
		return retStr;
	}
}
