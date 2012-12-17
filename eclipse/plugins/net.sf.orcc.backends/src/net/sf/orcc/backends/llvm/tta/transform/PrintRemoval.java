package net.sf.orcc.backends.llvm.tta.transform;

import java.util.ArrayList;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;

public class PrintRemoval extends DfVisitor<Void> {

	private class InnerPrintRemoval extends AbstractIrVisitor<Void> {

		public InnerPrintRemoval() {
			super(true);
		}

		public Void caseInstCall(InstCall call) {
			if (call.isPrint()) {
				IrUtil.delete(call);
				indexInst--;
			} else {
				for (Arg arg : new ArrayList<Arg>(call.getArguments())) {
					if (arg.isByVal()) {
						Expression expr = ((ArgByVal) arg).getValue();
						if (expr.isExprString()) {
							IrUtil.delete(arg);
						}
					}
				}
			}
			return null;
		}

		public Void caseProcedure(Procedure proc) {
			for (Param param : new ArrayList<Param>(proc.getParameters())) {
				if (param.getVariable().getType().isString()) {
					IrUtil.delete(param);
				}
			}
			for (Var var : new ArrayList<Var>(proc.getLocals())) {
				if (var.getType().isString()) {
					IrUtil.delete(var);
				}
			}
			return super.caseProcedure(proc);
		}
	}

	public PrintRemoval() {
		this.irVisitor = new InnerPrintRemoval();
	}

	public Void caseActor(Actor actor) {
		for (Var var : new ArrayList<Var>(actor.getStateVars())) {
			if (var.getType().isString()) {
				IrUtil.delete(var);
			}
		}
		return super.caseActor(actor);
	}

}
