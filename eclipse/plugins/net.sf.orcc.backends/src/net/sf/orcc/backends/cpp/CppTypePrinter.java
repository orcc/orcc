/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique F�d�rale de Lausanne nor the names of its
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
package net.sf.orcc.backends.cpp;

import net.sf.orcc.backends.c.CTypePrinter;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;

/**
 * This class defines a C++ type printer. It extends the C type printer by
 * printing booleans as <code>bool</code>s, not <code>int</code>s.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CppTypePrinter extends CTypePrinter {

	private String printInt(int size) {
		if (size <= 8) {
			return "char";
		} else if (size <= 16) {
			return "short";
		} else if (size <= 32) {
			return "int";
		} else if (size <= 64) {
			return "long long";
		} else {
			return null;
		}
	}

	@Override
	public String caseTypeInt(TypeInt type) {
		return printInt(type.getSize());
	}

	@Override
	public String caseTypeBool(TypeBool type) {
		return "bool";
	}

	@Override
	public String caseTypeString(TypeString type) {
		return "std::string";
	}

	@Override
	public String caseTypeUint(TypeUint type) {
		return "unsigned " + printInt(type.getSize());
	}

}
