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
 * This class defines an integer number constant. The class is similar to Long
 * with additional methods to know if the integer is a 32-bit or a 64-bit
 * constant.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IntegerNumber {

	private long number;

	public IntegerNumber(long number) {
		this.number = number;
	}

	/**
	 * Returns the value of this integer number truncated as an <code>int</code>
	 * .
	 * 
	 * @return the value of this integer number truncated as an <code>int</code>
	 */
	public int getIntValue() {
		return (int) number;
	}

	/**
	 * Returns the value of this integer number as a <code>long</code>.
	 * 
	 * @return the value of this integer number as a <code>long</code>
	 */
	public long getLongValue() {
		return number;
	}

	/**
	 * Returns true if this integer number needs the "long" storage type.
	 * 
	 * @return true if this integer number needs the "long" storage type
	 */
	public boolean isLong() {
		return getIntValue() != getLongValue();
	}

	@Override
	public String toString() {
		return Long.toString(number);
	}

}
