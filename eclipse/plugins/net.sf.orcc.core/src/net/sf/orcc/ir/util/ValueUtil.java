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

	/**
	 * Returns a new integer equal to the sum of the two operands, or
	 * <code>null</code> if the two operands are not both integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object add(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getIntValue(bi1.add(bi2));
	}

	/**
	 * Returns a new integer equal to the bitwise and of the two operands, or
	 * <code>null</code> if the two operands are not both integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object and(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getIntValue(bi1.and(bi2));
	}

	public static boolean areInts(Object val1, Object val2) {
		return isInt(val1) && isInt(val2);
	}

	/**
	 * Returns a new integer equal to the division of the two operands, or
	 * <code>null</code> if the two operands are not both integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object divide(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getIntValue(bi1.divide(bi2));
	}

	/**
	 * Returns a new boolean that is <code>true</code> if val1 is greater than
	 * or equal to val2, or <code>null</code> if the two operands are not both
	 * of the same type.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return a boolean value or <code>null</code>
	 */
	public static Object ge(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
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
			return null;
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
			return 0;
		}
	}

	/**
	 * Returns a new boolean that is <code>true</code> if val1 is greater than
	 * val2, or <code>null</code> if the two operands are not both of the same
	 * type.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return a boolean value or <code>null</code>
	 */
	public static Object gt(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getBigInteger(val1).compareTo(getBigInteger(val2)) > 0;
	}

	/**
	 * Returns <code>true</code> if value is a boolean.
	 * 
	 * @param value
	 *            a value
	 * @return <code>true</code> if value is a boolean
	 */
	public static boolean isBool(Object value) {
		return (value instanceof Boolean);
	}

	/**
	 * Returns <code>true</code> if value is a float.
	 * 
	 * @param value
	 *            a value
	 * @return <code>true</code> if value is a float
	 */
	public static boolean isFloat(Object value) {
		return (value instanceof Float || value instanceof Double || value instanceof BigDecimal);
	}

	/**
	 * Returns <code>true</code> if value is an integer.
	 * 
	 * @param value
	 *            a value
	 * @return <code>true</code> if value is an integer
	 */
	public static boolean isInt(Object value) {
		return (value instanceof Integer || value instanceof Long || value instanceof BigInteger);
	}

	/**
	 * Returns <code>true</code> if value is a list.
	 * 
	 * @param value
	 *            a value
	 * @return <code>true</code> if value is a list
	 */
	public static boolean isList(Object value) {
		return value.getClass().isArray();
	}

	/**
	 * Returns <code>true</code> if value is a boolean and is <code>true</code>
	 * 
	 * @param value
	 *            a value
	 * @return <code>true</code> if value is a boolean and is <code>true</code>
	 */
	public static boolean isTrue(Object value) {
		return isBool(value) && (Boolean) value;
	}

	/**
	 * Returns a new boolean that is <code>true</code> if val1 is less than or
	 * equal to val2, or <code>null</code> if the two operands are not both of
	 * the same type.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return a boolean value or <code>null</code>
	 */
	public static Object le(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getBigInteger(val1).compareTo(getBigInteger(val2)) <= 0;
	}

	/**
	 * Returns a new boolean that is <code>true</code> if val1 is less than
	 * val2, or <code>null</code> if the two operands are not both of the same
	 * type.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return a boolean value or <code>null</code>
	 */
	public static Object lt(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getBigInteger(val1).compareTo(getBigInteger(val2)) < 0;
	}

	/**
	 * Returns a new integer equal to the modulo of the two operands, or
	 * <code>null</code> if the two operands are not both integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object mod(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getIntValue(bi1.mod(bi2));
	}

	/**
	 * Returns a new integer equal to the product of the two operands, or
	 * <code>null</code> if the two operands are not both integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object multiply(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getIntValue(bi1.multiply(bi2));
	}

	/**
	 * Returns a new integer equal to the bitwise or of the two operands, or
	 * <code>null</code> if the two operands are not both integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object or(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getIntValue(bi1.or(bi2));
	}

	public static void set(Type type, Object array, List<Expression> indexes,
			Object value) {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns a new integer equal to the first operand left shifted by the
	 * right operand, or <code>null</code> if the two operands are not both
	 * integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object shiftLeft(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		int i2 = getIntValue(val2);
		if (bi1 == null) {
			return null;
		}
		return getIntValue(getBigInteger(val1).shiftLeft(i2));
	}

	/**
	 * Returns a new integer equal to the first operand right shifted by the
	 * right operand, or <code>null</code> if the two operands are not both
	 * integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object shiftRight(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		int i2 = getIntValue(val2);
		if (bi1 == null) {
			return null;
		}
		return getIntValue(getBigInteger(val1).shiftRight(i2));
	}

	/**
	 * Returns a new integer equal to the difference of the two operands, or
	 * <code>null</code> if the two operands are not both integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object subtract(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getIntValue(bi1.subtract(bi2));
	}

	/**
	 * Returns a new integer equal to the bitwise xor of the two operands, or
	 * <code>null</code> if the two operands are not both integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object xor(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		BigInteger bi2 = getBigInteger(val2);
		if (bi1 == null || bi2 == null) {
			return null;
		}
		return getIntValue(bi1.xor(bi2));
	}

}
