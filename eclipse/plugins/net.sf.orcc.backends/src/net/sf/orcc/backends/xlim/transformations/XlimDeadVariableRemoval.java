package net.sf.orcc.backends.xlim.transformations;

import net.sf.orcc.backends.xlim.instructions.TernaryOperation;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.transformations.DeadVariableRemoval;

public class XlimDeadVariableRemoval extends DeadVariableRemoval {

	public XlimDeadVariableRemoval(boolean keepTokenSwallowerVariable) {
		super(keepTokenSwallowerVariable);
	}

	public void visit(SpecificInstruction specific) {
		if (specific instanceof TernaryOperation) {
			TernaryOperation ternaryOperation = (TernaryOperation) specific;

			Variable variable = ternaryOperation.getTarget();
			if (!variable.isUsed()) {
				// do not remove ternaryOperation to variables that are used by
				// writes
				if (variable.isPort()) {
					return;
				}

				// clean up uses
				ternaryOperation.setTarget(null);

				((VarExpr) ternaryOperation.getConditionValue()).getVar()
						.remove();
				((VarExpr) ternaryOperation.getTrueValue()).getVar().remove();
				((VarExpr) ternaryOperation.getFalseValue()).getVar().remove();

				// remove instruction
				itInstruction.remove();

				procedure.getLocals().remove(variable.getName());
				changed = true;
			}
		}
	}

}
