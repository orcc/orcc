package net.sf.orcc.backends.c.dal;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.util.OrccLogger;

public class ConstraintValidator {

	private List<Var> varList;

	private class LoadDetector extends AbstractIrVisitor<Void> {

		public LoadDetector() {
			super(true);
		}

		@Override
		public Void caseExprVar(ExprVar expr) {
			varList.add(expr.getUse().getVariable());
			return null;
		}
	}

	private boolean findVar(String var, SortedSet<Token> tokens) {
		boolean found = false;
		for (Token t : tokens) {
			if (t.getTargetVar().getName().equals(var)) {
				found = true;
			}
		}
		return found;
	}

	public boolean validate(InstAssign compute, SortedSet<Token> tokens) {
		boolean constraintsOk = true;
		varList = new ArrayList<Var>();
		LoadDetector detector = new LoadDetector();
		detector.doSwitch(compute.getValue());
		for (Var v : varList) {
			if(!findVar(v.getName(), tokens)) {
				OrccLogger.warnln("Attempting to solve constraint " +
						compute.toString() + " but token " + v.getName() +
						" is not available");
				constraintsOk = false;
			}
		}
		return constraintsOk;
	}
}
