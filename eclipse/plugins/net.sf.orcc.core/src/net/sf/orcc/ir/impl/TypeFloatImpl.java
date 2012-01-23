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
package net.sf.orcc.ir.impl;

import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeFloat;

import org.eclipse.emf.ecore.EClass;

/**
 * This class defines a float type.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeFloatImpl extends TypeImpl implements TypeFloat {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeFloatImpl() {
		super();
	}

	/**
	 * Return -2 if obj is not an instance of TypeFloat. If it is, return -1 if
	 * its size (in bits) is greater than this size, 0 if it is equal, and 1 if
	 * its size is lesser.
	 * 
	 * @param obj
	 *            Type to compare to
	 * @return int
	 */
	@Override
	public int compareTo(Type obj) {
		if (obj instanceof TypeFloat) {
			if (this.getSizeInBits() < obj.getSizeInBits()) {
				return -1;
			} else if (this.getSizeInBits() == obj.getSizeInBits()) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return -2;
		}
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TypeFloat);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.TYPE_FLOAT;
	}
	
	@Override
	public int getSizeInBits() {
		return 32;
	}

	@Override
	public boolean isFloat() {
		return true;
	}

	@Override
	public String toString() {
		return "float";
	}

}
