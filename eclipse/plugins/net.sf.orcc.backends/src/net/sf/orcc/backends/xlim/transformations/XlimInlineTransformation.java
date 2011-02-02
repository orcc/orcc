package net.sf.orcc.backends.xlim.transformations;

import net.sf.orcc.backends.transformations.InlineTransformation;
import net.sf.orcc.backends.xlim.instructions.TernaryOperation;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.instructions.SpecificInstruction;

public class XlimInlineTransformation extends InlineTransformation {

	public XlimInlineTransformation(boolean inlineProcedure,
			boolean inlineFunction) {
		super(inlineProcedure, inlineFunction);
		inlineCloner = new XlimInlineCloner();
	}

	private class XlimInlineCloner extends InlineCloner {
		@Override
		public Object interpret(SpecificInstruction specific, Object... args) {
			if (specific instanceof TernaryOperation) {
				TernaryOperation ternaryOperation = (TernaryOperation) specific;
				LocalVariable target = (LocalVariable) variableToLocalVariableMap
						.get(ternaryOperation.getTarget());

				Expression conditionValue = (Expression) ternaryOperation
						.getConditionValue().accept(this, args);
				Expression trueValue = (Expression) ternaryOperation
						.getTrueValue().accept(this, args);
				Expression falseValue = (Expression) ternaryOperation
						.getFalseValue().accept(this, args);

				TernaryOperation t = new TernaryOperation(target,
						conditionValue, trueValue, falseValue);
				Use.addUses(t, conditionValue);
				Use.addUses(t, trueValue);
				Use.addUses(t, falseValue);
				return t;
			}
			super.interpret(specific, args);
			return specific;
		}
	}

}
