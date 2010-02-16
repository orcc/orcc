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
package net.sf.orcc.ir;

import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.UintType;

/**
 * This class provide information about cast associated to an instruction
 * 
 * @author Jérôme Gorin
 * 
 */
public class Cast {

	Type source;

	Type target;

	public Cast(Type source, Type target) {
		this.source = source;
		this.target = target;
	}

	/**
	 * Determine the size of a type.
	 * 
	 * @return integer corresponding to the size of the selected type
	 */
	private int getSizeOf(Type type) {
		if (type.isBool()) {
			return 1;
		} else if (type.isInt()) {
			IntType intType = (IntType) type;
			return new ExpressionEvaluator().evaluateAsInteger(intType
					.getSize());
		} else if (type.isUint()) {
			UintType uintType = (UintType) type;
			return new ExpressionEvaluator().evaluateAsInteger(uintType
					.getSize());
		} else if (type.isList()) {
			ListType listType = (ListType) type;
			return getSizeOf(listType.getElementType());
		}

		return 0;
	}

	/**
	 * Getter of the Type from the cast's source
	 * 
	 * @return Type of the source
	 */
	public Type getSource() {
		return source;
	}

	/**
	 * Getter of Type from the cast's target
	 * 
	 * @return Type of the target
	 */
	public Type getTarget() {
		return target;
	}

	/**
	 * Return true if the target type is extended from the source type.
	 * 
	 * @return a boolean indicating if target type is extended from the source
	 *         type
	 */
	public boolean isExtended() {
		if (source.equals(target)) {
			return false;
		}

		if (source.toString().equals(target.toString())) {
			return false;
		}
		
		if (target.isList()){
			ListType list = (ListType)target;
			if (source.toString().equals(list.getElementType().toString())){
				return false;
			}
		}

		return getSizeOf(source) < getSizeOf(target);

	}

	/**
	 * Return true if the target type is trunced from the source type.
	 * 
	 * @return a boolean indicating if target type is trunced from the source
	 *         type
	 */
	public boolean isTrunced() {
		if (source.equals(target)) {
			return false;
		}

		if (source.toString().equals(target.toString())) {
			return false;
		}

		if (target.isList()) {
			ListType list = (ListType)target;
			if (source.toString().equals(list.getElementType().toString())){
				return false;
			}
		}
		
		return getSizeOf(source) > getSizeOf(target);
	}
}
