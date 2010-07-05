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

import java.math.BigInteger;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.type.TypePrinter;

/**
 * This class defines a VHDL type printer.
 * 
 * @author Nicolas Siret
 * 
 */
public class VHDLTypePrinter extends TypePrinter {

	/**
	 * Prints an integer with the given number of bits.
	 * 
	 * @param size
	 *            an integer that gives the number of bits
	 */
	private void printInt(int size) {
		// limits size to 32 bits
		if (size >= 31) {
			size = 31;
		}

		BigInteger bound = new BigInteger("1").shiftLeft(size - 1);
		builder.append("integer range ");
		builder.append(bound.subtract(BigInteger.ONE));
		builder.append(" downto -");
		builder.append(bound);
	}

	@Override
	public void visit(TypeBool type) {
		builder.append("std_logic");
	}

	@Override
	public void visit(TypeInt type) {
		printInt(type.getSize());
	}

	@Override
	public void visit(TypeList type) {
		// size will be printed later
		type.getType().accept(this);
	}

	@Override
	public void visit(TypeString type) {
		throw new OrccRuntimeException("unsupported String type");
	}

	@Override
	public void visit(TypeUint type) {
		printInt(type.getSize() + 1);
	}

	@Override
	public void visit(TypeVoid type) {
	}

}
