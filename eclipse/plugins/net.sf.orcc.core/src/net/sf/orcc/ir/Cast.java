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

/**
 * This class provide information about cast associated to an instruction
 * 
 * @author Jerome Gorin
 * @author Herve Yviquel
 * 
 */
public class Cast {

	/**
	 * Determine the size of a type.
	 * 
	 * @return integer corresponding to the size of the selected type
	 */
	public static int getSizeOfType(Type type) {
		// Select a particular size according to type
		if (type.isBool()) {
			return 1;
		} else if (type.isInt()) {
			TypeInt intType = (TypeInt) type;
			return intType.getSize();
		} else if (type.isUint()) {
			TypeUint uintType = (TypeUint) type;
			return uintType.getSize();
		} else if (type.isList()) {
			TypeList listType = (TypeList) type;
			return getSizeOfType(listType.getType());
		}

		return 0;
	}

	private Type source;

	private Type target;

	public Cast(Type source, Type target) {
		this.source = source;
		this.target = target;
	}

	/**
	 * Getter of the Type from the cast's source
	 * 
	 * @return Type of the source
	 */
	public Type getSource() {
		return getType(source);
	}

	/**
	 * Getter of Type from the cast's target
	 * 
	 * @return Type of the target
	 */
	public Type getTarget() {
		return getType(target);
	}

	/**
	 * Determine the size of a type.
	 * 
	 * @return integer corresponding to the size of the selected type
	 */
	private Type getType(Type type) {
		if (type.isList()) {
			TypeList listType = (TypeList) type;
			return getType(listType.getType());
		}

		return type;
	}

	/**
	 * Return true if the target type is different from the source type.
	 * 
	 * @return a boolean indicating if target type is different from the source
	 *         type
	 */
	public boolean isDifferent() {
		if (target.isList()) {
			TypeList list = (TypeList) target;
			return source.getClass() != list.getElementType().getClass();
		} else {
			return source.getClass() != target.getClass();
		}
	}

	/**
	 * Return true if the target type is extended from the source type.
	 * 
	 * @return a boolean indicating if target type is extended from the source
	 *         type
	 */
	public boolean isExtended() {
		if (target.isList()) {
			TypeList list = (TypeList) target;
			if (source.equals(list.getElementType())) {
				return false;
			}
		}

		return getSizeOfType(source) < getSizeOfType(target);
	}

	/**
	 * Return true if the source type is signed
	 * 
	 * @return a boolean indicating if source is signed type
	 */
	/**
	 * Determine the size of a type.
	 * 
	 * @return integer corresponding to the size of the selected type
	 */
	public boolean isSigned() {
		if (source.isUint() || target.isUint()) {
			return false;
		}

		if (source.isBool() || target.isBool()) {
			return false;
		}

		if (source.isList()) {
			TypeList type = (TypeList) source;
			Type elementType = type.getElementType();
			if (elementType.isUint() || elementType.isBool()) {
				return false;
			}
		}

		if (target.isList()) {
			TypeList type = (TypeList) target;
			Type elementType = type.getElementType();
			if (elementType.isUint() || elementType.isBool()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Return true if the target type is trunced from the source type.
	 * 
	 * @return a boolean indicating if target type is trunced from the source
	 *         type
	 */
	public boolean isTrunced() {
		if (target.isList()) {
			TypeList list = (TypeList) target;
			if (source.equals(list.getElementType())) {
				return false;
			}
		}

		return getSizeOfType(source) > getSizeOfType(target);
	}

}
