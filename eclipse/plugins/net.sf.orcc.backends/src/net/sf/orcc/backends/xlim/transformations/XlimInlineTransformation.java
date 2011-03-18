/*
 * Copyright (c) 2010-2011, IRISA
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IRISA nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.xlim.transformations;

import net.sf.orcc.backends.transformations.InlineTransformation;
import net.sf.orcc.backends.xlim.instructions.TernaryOperation;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.instructions.SpecificInstruction;

/**
 * This class defines an extension of InlineTransformation to do specified
 * treatment with XLIM backend.
 * 
 * @author Herve Yviquel
 * 
 */
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
