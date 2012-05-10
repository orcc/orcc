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
package net.sf.orcc.backends.java;

import net.sf.orcc.df.Port;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.util.TypePrinter;
import net.sf.orcc.util.util.EcoreHelper;

/**
 * This class defines a Java type printer.
 * 
 * @author Matthieu Wipliez
 * @author Antoine Lorence
 * @author Herve Yyviquel
 * 
 */
public class JavaTypePrinter extends TypePrinter {

	@Override
	public String caseTypeBool(TypeBool type) {
		Port p = EcoreHelper.getContainerOfType(type, Port.class);

		if (p == null) {
			return "boolean";
		} else {
			return "Boolean";
		}
	}

	@Override
	public String caseTypeFloat(TypeFloat type) {
		if (EcoreHelper.getContainerOfType(type, Port.class) == null) {
			if (type.getSize() == 64) {
				return "double";
			}
			return "float";
		} else {
			if (type.getSize() == 64) {
				return "Double";
			}
			return "Float";
		}
	}

	@Override
	public String caseTypeInt(TypeInt type) {
		Port p = EcoreHelper.getContainerOfType(type, Port.class);
		int s = type.getSizeInBits();

		if (p == null) {
			if (s <= 32) {
				return "int";
			} else {
				return "long";
			}
		} else {
			if (s <= 32) {
				return "Integer";
			} else {
				return "Long";
			}
		}
	}

	@Override
	public String caseTypeList(TypeList type) {
		return doSwitch(type.getType()) + "[]";
	}

	@Override
	public String caseTypeString(TypeString type) {
		return "String";
	}

	@Override
	public String caseTypeUint(TypeUint type) {
		// no unsigned in Java, and size is not taken in consideration anyway
		Port p = EcoreHelper.getContainerOfType(type, Port.class);
		int s = type.getSizeInBits();

		if (p == null) {
			if (s <= 32) {
				return "int";
			} else {
				return "long";
			}
		} else {
			if (s <= 32) {
				return "Integer";
			} else {
				return "Long";
			}
		}
	}

}
