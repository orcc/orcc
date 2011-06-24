/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.ir.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;

/**
 * This class defines many static utility methods to deal with values.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ValueUtil {

	public static Object add(Object val1, Object val2) {
		return getIntValue(getBigInteger(val1).add(getBigInteger(val2)));
	}

	public static Object and(Object val1, Object val2) {
		return getIntValue(getBigInteger(val1).and(getBigInteger(val2)));
	}

	public static boolean areInts(Object val1, Object val2) {
		return isInt(val1) && isInt(val2);
	}

	public static Object divide(Object val1, Object val2) {
		return getIntValue(getBigInteger(val1).xor(getBigInteger(val2)));
	}

	public static Object ge(Object val1, Object val2) {
		return getBigInteger(val1).compareTo(getBigInteger(val2)) >= 0;
	}

	public static Object get(Type type, Object array, List<Expression> indexes) {
		// TODO Auto-generated method stub
		return null;
	}

	public static BigInteger getBigInteger(Object value) {
		if (value instanceof Integer) {
			return BigInteger.valueOf(((Integer) value));
		} else if (value instanceof Long) {
			return BigInteger.valueOf(((Long) value));
		} else if (value instanceof BigInteger) {
			return (BigInteger) value;
		} else {
			throw new ClassCastException();
		}
	}

	public static Object getFloatValue(BigDecimal decimal) {
		float f = decimal.floatValue();
		if (!Float.isInfinite(f)) {
			return f;
		}

		double d = decimal.doubleValue();
		if (!Double.isInfinite(d)) {
			return d;
		}

		return decimal;
	}

	public static Object getIntValue(BigInteger integer) {
		long l = integer.longValue();
		if (BigInteger.valueOf(l).equals(integer)) {
			int i = (int) l;
			if (i == l) {
				return i;
			}
			return l;
		}
		return integer;
	}

	public static int getIntValue(Object value) {
		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		} else if (value instanceof Long) {
			return ((Long) value).intValue();
		} else if (value instanceof BigInteger) {
			return ((BigInteger) value).intValue();
		} else {
			throw new ClassCastException();
		}
	}

	public static Object gt(Object val1, Object val2) {
		return getBigInteger(val1).compareTo(getBigInteger(val2)) > 0;
	}

	public static boolean isBool(Object value) {
		return (value instanceof Boolean);
	}

	public static boolean isFloat(Object value) {
		return (value instanceof Float || value instanceof Double || value instanceof BigDecimal);
	}

	public static boolean isInt(Object value) {
		return (value instanceof Integer || value instanceof Long || value instanceof BigInteger);
	}

	public static boolean isList(Object value) {
		return value.getClass().isArray();
	}

	public static boolean isTrue(Object value) {
		return isBool(value) && (Boolean) value;
	}

	public static Object le(Object val1, Object val2) {
		return getBigInteger(val1).compareTo(getBigInteger(val2)) <= 0;
	}

	public static Object lt(Object val1, Object val2) {
		return getBigInteger(val1).compareTo(getBigInteger(val2)) < 0;
	}

	public static Object mod(Object val1, Object val2) {
		return getIntValue(getBigInteger(val1).mod(getBigInteger(val2)));
	}

	public static Object multiply(Object val1, Object val2) {
		return getIntValue(getBigInteger(val1).multiply(getBigInteger(val2)));
	}

	public static Object or(Object val1, Object val2) {
		return getIntValue(getBigInteger(val1).or(getBigInteger(val2)));
	}

	public static void set(Type type, Object array, List<Expression> indexes,
			Object value) {
		// TODO Auto-generated method stub

	}

	public static Object shiftLeft(Object val1, Object val2) {
		return getIntValue(getBigInteger(val1).shiftLeft(getIntValue(val2)));
	}

	public static Object shiftRight(Object val1, Object val2) {
		return getIntValue(getBigInteger(val1).shiftRight(getIntValue(val2)));
	}

	public static Object subtract(Object val1, Object val2) {
		return getIntValue(getBigInteger(val1).subtract(getBigInteger(val2)));
	}

	public static Object xor(Object val1, Object val2) {
		return getIntValue(getBigInteger(val1).xor(getBigInteger(val2)));
	}

}
