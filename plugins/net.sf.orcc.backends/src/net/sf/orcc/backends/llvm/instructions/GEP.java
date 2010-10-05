/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
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
package net.sf.orcc.backends.llvm.instructions;

import java.util.List;

import net.sf.orcc.ir.Cast;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.TargetContainer;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.ValueContainer;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.util.CommonNodeOperations;

/**
 * This class defines an 'getelementptr' instruction. This node is used to get
 * the address of a subelement of an expression.
 * 
 * @author Jérôme Gorin
 * 
 */
public class GEP extends SpecificInstruction implements TargetContainer,
		ValueContainer {

	private List<Expression> indexes;

	private Variable target;

	private Expression value;

	/**
	 * Creates a new GEP instruction from the given value, its indexes and a
	 * target.
	 * 
	 * @param target
	 *            the target
	 * @param source
	 *            the source
	 * @param indexes
	 *            a list of indexes
	 */
	public GEP(Location location, Variable target, Expression value,
			List<Expression> indexes) {
		super(location);
		setIndexes(indexes);
		setValue(value);
		setTarget(target);
	}

	/**
	 * Creates a new GEP instruction from the given value, its indexes, a target
	 * and a location.
	 * 
	 * @param target
	 *            the target
	 * @param source
	 *            the source
	 * @param indexes
	 *            a list of indexes
	 */
	public GEP(Variable target, Expression value, List<Expression> indexes) {
		this(new Location(), target, value, indexes);
	}

	@Override
	public Cast getCast() {
		Type expr = value.getType();
		Type val = target.getType();

		if (expr == null) {
			return null;
		}

		if (value.isIntExpr() || value.isBooleanExpr()) {
			return null;
		}

		Cast cast = new Cast(expr, val);

		if (cast.isExtended() || cast.isTrunced()) {
			return cast;
		}

		return null;
	}

	/**
	 * Returns the (possibly empty) list of indexes of this store.
	 * 
	 * @return the (possibly empty) list of indexes of this store
	 */
	public List<Expression> getIndexes() {
		return indexes;
	}

	/**
	 * Returns the target of this Store. The target is a {@link Use}.
	 * 
	 * @return the target of this Store
	 */
	@Override
	public Variable getTarget() {
		return target;
	}

	@Override
	public Expression getValue() {
		return value;
	}

	@Override
	public void internalSetTarget(Variable target) {
		this.target = target;
	}

	@Override
	public void internalSetValue(Expression value) {
		this.value = value;
	}

	/**
	 * Sets the indexes of this store instruction. Uses are updated to point to
	 * this instruction.
	 * 
	 * @param indexes
	 *            a list of expressions
	 */
	private void setIndexes(List<Expression> indexes) {
		if (this.indexes != null) {
			Use.removeUses(this, this.indexes);
		}
		this.indexes = indexes;
		Use.addUses(this, indexes);
	}

	@Override
	public void setTarget(Variable target) {
		CommonNodeOperations.setTarget(this, target);
	}

	@Override
	public void setValue(Expression value) {
		CommonNodeOperations.setValue(this, value);
	}

	@Override
	public String toString() {
		return target.toString() + " = getelementptr " + getValue() + ", "
				+ indexes;
	}

}
