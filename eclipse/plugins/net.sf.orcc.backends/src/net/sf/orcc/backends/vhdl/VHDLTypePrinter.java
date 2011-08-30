/*
 * Copyright (c) 2009-2010, LEAD TECH DESIGN Rennes - France
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.util.TypePrinter;

/**
 * This class defines a VHDL type printer.
 * 
 * @author Nicolas Siret
 * 
 */
public class VHDLTypePrinter extends TypePrinter {

	@Override
	public String caseTypeBool(TypeBool type) {
		return "std_logic";
	}

	@Override
	public String caseTypeInt(TypeInt type) {
		return printInt(type.getSize());
	}

	@Override
	public String caseTypeList(TypeList type) {
		// size will be printed later
		return doSwitch(type.getType());
	}

	@Override
	public String caseTypeString(TypeString type) {
		throw new OrccRuntimeException("unsupported String type");
	}

	@Override
	public String caseTypeUint(TypeUint type) {
		return printUint(type.getSize());
	}

	/**
	 * Prints an integer with the given number of bits.
	 * 
	 * @param size
	 *            an integer that gives the number of bits
	 */
	private String printInt(int size) {
		// IEEE 1076-1993
		// Section 3.1.2.1 Predefined integer types
		// "The range of INTEGER is implementation dependent, but it is
		// guaranteed to include the range -2147483647 to +2147483647."

		// so we limit to 31-bit signed integers.
		if (size >= 31) {
			return "integer";
		} else {
			int bound = 1 << (size - 1);
			return "integer range " + (bound - 1) + " downto -" + bound;
		}
	}

	/**
	 * Prints an unsigned integer with the given number of bits.
	 * 
	 * @param size
	 *            an unsigned integer that gives the number of bits
	 */
	private String printUint(int size) {
		// we limit to 31-bit unsigned integers (see printInt)
		if (size >= 31) {
			return "integer";
		} else {
			int bound = 1 << size;
			return "integer range " + (bound - 1) + " downto 0";
		}
	}

}
