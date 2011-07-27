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

/**
 * This class defines native functions for the Chain unit.
 * 
 * @author Thavot Richard
 * 
 */
public class Chain {

	public static void split(String string, String regex,
			String dividedString[]) {
		String[] ds = string.split(regex);
		for (int i = 0; i < ds.length; i++) {
			dividedString[i] = ds[i];
		}
	}

	public static String concat(String dividedString[], String separator) {
		StringBuffer result = new StringBuffer();
		if (dividedString.length > 0) {
			result.append(dividedString[0]);
			for (int i = 1; i < dividedString.length; i++) {
				if (dividedString[i] != null) {
					result.append(separator);
					result.append(dividedString[i]);
				}
			}
		}
		return result.toString();
	}

	public static String replace(String string, String regex, String replacement) {
		StringBuffer result = new StringBuffer(string);
		int k;
		while ((k = result.indexOf(regex)) > 0) {
			result.replace(k, k + regex.length(), replacement);
		}
		return result.toString();
	}

}
