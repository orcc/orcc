/*
 * Copyright (c) 2010, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.xlim.instructions;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalTargetContainer;
import net.sf.orcc.ir.VarLocal;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.impl.InstructionInterpreter;
import net.sf.orcc.ir.impl.InstructionVisitor;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.util.CommonNodeOperations;

/**
 * 
 * This class defines a ternary operation.
 * 
 * @author Herve Yviquel
 * 
 */
public class TernaryOperation extends SpecificInstruction implements
		LocalTargetContainer {

	private Expression conditionValue;

	private Expression falseValue;

	private VarLocal target;

	private Expression trueValue;

	public TernaryOperation(Location location, VarLocal target,
			Expression conditionValue, Expression trueValue,
			Expression falseValue) {
		super(location);
		setTarget(target);
		setConditionValue(conditionValue);
		setTrueValue(trueValue);
		setFalseValue(falseValue);
	}

	public TernaryOperation(VarLocal target, Expression conditionValue,
			Expression trueValue, Expression falseValue) {
		this(new Location(), target, conditionValue, trueValue, falseValue);
	}

	@Override
	public Object accept(InstructionInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(InstructionVisitor visitor) {
		visitor.visit(this);
	}

	public Expression getConditionValue() {
		return conditionValue;
	}

	public Expression getFalseValue() {
		return falseValue;
	}

	@Override
	public VarLocal getTarget() {
		return target;
	}

	public Expression getTrueValue() {
		return trueValue;
	}

	@Override
	public void internalSetTarget(VarLocal target) {
		this.target = target;
	}

	public boolean isTernaryOperation() {
		return true;
	}

	public void setConditionValue(Expression conditionValue) {
		this.conditionValue = conditionValue;
	}

	public void setFalseValue(Expression falseValue) {
		this.falseValue = falseValue;
	}

	@Override
	public void setTarget(VarLocal target) {
		CommonNodeOperations.setTarget(this, target);
	}

	public void setTrueValue(Expression trueValue) {
		this.trueValue = trueValue;
	}

	@Override
	public String toString() {
		return getTarget() + " = " + getConditionValue() + " ? "
				+ getTrueValue() + " : " + getFalseValue();
	}

}
