/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the EPFL and University of Oulu nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler;

import java.util.ArrayList;

/**
 * A bunch of mathematical methods
 * 
 * @author Victor Martin
 */
public class MathExtended {

	public static boolean bitAndNotEqualsZero(int a, int b) {

		String binA = Integer.toBinaryString(a);
		String binB = Integer.toBinaryString(b);
		int n = Math.abs(binA.length() - binB.length());
		if (binA.length() < binB.length()){
			binA = increaseLenght(binA, n);
		}
		else if (binB.length() < binA.length()){
			binB = increaseLenght(binB, n);
		}	
		char[] binAArray = binA.toCharArray();
		char[] binBArray = binB.toCharArray();
		for (int i = 0; i < binAArray.length; i++) {
			if (binAArray[i] == '1' && binBArray[i] == '1')

				return true;

		}

		return false;
	}

	/**
	 * Increses n bits of a binary number. For exameple, if n = 3 and binary
	 * number if 1010, the method returns 0001010
	 * 
	 * @param binaryNum
	 *            binary number
	 * @param n
	 *            number of bits to increase
	 * @return the same binary number with a lenght of n + oldLength
	 */
	private static String increaseLenght(String binaryNum, int n) {
		String str = "";
		for (int i = 0; i < n; i++)
			str += "0";
		str += binaryNum;
		return str;
	}

	/**
	 * Determines which is the maximum contained value
	 * 
	 * @param in
	 *            list of values
	 * @return max value of the list
	 */
	public static double max(ArrayList<Double> in) {
		double max = Double.MIN_VALUE;

		for (int i = 0; i < in.size(); i++) {
			max = Math.max(max, in.get(i));
		}

		return max;
	}

	/**
	 * Determines which is the minimum contained value
	 * 
	 * @param in
	 *            list of values
	 * @return min values of the list
	 */
	public static double min(ArrayList<Double> in) {
		double min = Double.MAX_VALUE;

		for (int i = 0; i < in.size(); i++) {
			min = Math.min(min, in.get(i));
		}

		return min;
	}

	/**
	 * Determines which is the average value
	 * 
	 * @param in
	 * @return a double
	 */
	public static double avg(ArrayList<Double> in) {
		double sum = 0;

		for (int i = 0; i < in.size(); i++) {
			sum += in.get(i);
		}

		return (in.size() > 0) ? sum / in.size() : 0;
	}

}
