package net.sf.orcc.backends.c.dal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

/**
 * Get a collection of the uses of vars in an expression tree
 *
 * @author James Guthrie
 *
 */
public class GetVars extends AbstractIrVisitor<Collection<Var>> {

	private Set<Var> vars;

	public GetVars() {
		super(true);
		vars = new HashSet<Var>();
	}

	@Override
	public Collection<Var> caseExpression(Expression expr) {
		throw new OrccRuntimeException("Unhandled expression type: " + expr.getClass());
	}

	@Override
	public Collection<Var> caseExprInt(ExprInt exprInt) {
		return new HashSet<Var>();
	}

	@Override
	public Collection<Var> caseExprString(ExprString exprString) {
		return new HashSet<Var>();
	}

	@Override
	public Collection<Var> caseExprBinary(ExprBinary exprBinary) {
		vars.addAll(doSwitch(exprBinary.getE1()));
		vars.addAll(doSwitch(exprBinary.getE2()));
		return new HashSet<Var>(vars);
	}

	@Override
	public Collection<Var> caseExprBool(ExprBool exprBool) {
		return new HashSet<Var>();
	}

	@Override
	public Collection<Var> caseExprUnary(ExprUnary exprUnary) {
		vars.addAll(doSwitch(exprUnary.getExpr()));
		return new HashSet<Var>(vars);
	}

	@Override
	public Collection<Var> caseArgByVal(ArgByVal arg) {
		return doSwitch(arg.getValue());
	}

	@Override
	public Collection<Var> caseArgByRef(ArgByRef arg) {
		vars.add(arg.getUse().getVariable());
		return new HashSet<Var>(vars);
	}

	@Override
	public Collection<Var> caseInstAssign(InstAssign instAssign) {
		vars.addAll(doSwitch(instAssign.getValue()));
		return new HashSet<Var>(vars);
	}

	@Override
	public Collection<Var> caseInstLoad(InstLoad instLoad) {
		for (Expression e : instLoad.getIndexes()) {
			vars.addAll(doSwitch(e));
		}
		return new HashSet<Var>(vars);
	}

	@Override
	public Collection<Var> caseInstCall(InstCall instCall) {
		for (Arg a : instCall.getArguments()) {
			vars.addAll(doSwitch(a));
		}
		return new HashSet<Var>(vars);
	}

	@Override
	public Collection<Var> caseExprVar(ExprVar exprVar) {
		vars.add(exprVar.getUse().getVariable());
		return new HashSet<Var>(vars);
	}

}