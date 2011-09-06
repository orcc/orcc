/*
 * Copyright (c) 2011, EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package std.lang.impl;

import java.math.BigInteger;

public class Integer {

	/*
	 * Returns a String representing the specified integer.
	 */
	public static String toString(java.lang.Integer i) {
		return i.toString();
	}

	/*
	 * Returns a string representation of the integer argument as an unsigned
	 * integer in base 16.
	 */
	public static String toHexString(java.lang.Integer i) {
		return java.lang.Integer.toHexString(i);
	}

	public static String toHexString(BigInteger i) {
		return toHexString(i.intValue());
	}
	
	public static String toHexString(Long i) {
		return toHexString(i.intValue());
	}

	/*
	 * Returns a string representation of the integer argument as an unsigned
	 * integer in base 8.
	 */
	public static String toOctalString(java.lang.Integer i) {
		return java.lang.Integer.toOctalString(i);
	}

	/*
	 * Returns a string representation of the integer argument as an unsigned
	 * integer in base 2.
	 */
	public static String toBinaryString(java.lang.Integer i) {
		return java.lang.Integer.toBinaryString(i);
	}

	/*
	 * Parses the string argument as a signed integer in the radix specified by
	 * the second argument.
	 */

	public static int toBinaryString(String s, java.lang.Integer radix) {
		return java.lang.Integer.parseInt(s, radix);
	}

}
