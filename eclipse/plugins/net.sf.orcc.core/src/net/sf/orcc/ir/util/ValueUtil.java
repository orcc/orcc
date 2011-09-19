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

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;

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
	 * Creates a new array whose elements have the given type, and with the
	 * given number of dimensions.
	 * 
	 * @param type
	 *            type of elements
	 * @param dimensions
	 *            number of elements for each dimension
	 * @return an array
	 */
	public static Object createArray(Type type, int... dimensions) {
		if (type.isBool()) {
			return Array.newInstance(Boolean.TYPE, dimensions);
		} else if (type.isFloat()) {
			return Array.newInstance(Float.TYPE, dimensions);
		} else if (type.isInt()) {
			int size = type.getSizeInBits();
			if (size <= 8) {
				return Array.newInstance(Byte.TYPE, dimensions);
			} else if (size <= 16) {
				return Array.newInstance(Short.TYPE, dimensions);
			} else if (size <= 32) {
				return Array.newInstance(Integer.TYPE, dimensions);
			} else if (size <= 64) {
				return Array.newInstance(Long.TYPE, dimensions);
			} else {
				return Array.newInstance(BigInteger.class, dimensions);
			}
		} else if (type.isUint()) {
			int size = type.getSizeInBits();
			if (size < 8) {
				return Array.newInstance(Byte.TYPE, dimensions);
			} else if (size < 16) {
				return Array.newInstance(Short.TYPE, dimensions);
			} else if (size < 32) {
				return Array.newInstance(Integer.TYPE, dimensions);
			} else if (size < 64) {
				return Array.newInstance(Long.TYPE, dimensions);
			} else {
				return Array.newInstance(BigInteger.class, dimensions);
			}
		} else if (type.isString()) {
			return Array.newInstance(String.class, dimensions);
		} else {
			throw new IllegalArgumentException("expected scalar type");
		}
	}

	/**
	 * Creates a new array that matches the given type.
	 * 
	 * @param type
	 *            a type of list
	 * @return an array
	 */
	public static Object createArray(TypeList type) {
		List<Integer> listDimensions = type.getDimensions();
		int[] dimensions = new int[listDimensions.size()];
		for (int i = 0; i < dimensions.length; i++) {
			dimensions[i] = listDimensions.get(i);
		}

		Type eltType = ((TypeList) type).getInnermostType();
		return createArray(eltType, dimensions);
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

	public static Object equals(Object val1, Object val2) {
		if (isBool(val1) && isBool(val2)) {
			return ((Boolean) val1).equals(val2);
		} else if (isInt(val1) && isInt(val2)) {
			return getBigInteger(val1).equals(getBigInteger(val2));
		} else {
			return null;
		}
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

	/**
	 * Returns the value in the given array, at the given indexes, knowing the
	 * type of the elements of the array.
	 * 
	 * @param type
	 * @param array
	 * @param indexes
	 * @return
	 */
	public static Object get(Type type, Object array, Object... indexes) {
		if (array == null) {
			return null;
		}

		int numIndexes = indexes.length;
		for (int i = 0; i < numIndexes - 1; i++) {
			int index = getIntValue(indexes[i]);
			array = Array.get(array, index);
		}

		int index = getIntValue(indexes[numIndexes - 1]);
		if (type.isBool()) {
			return Array.getBoolean(array, index);
		} else if (type.isFloat()) {
			return Array.getFloat(array, index);
		} else if (type.isInt()) {
			int size = type.getSizeInBits();
			if (size <= 8) {
				// extend to int
				return (int) Array.getByte(array, index);
			} else if (size <= 16) {
				// extend to int
				return (int) Array.getShort(array, index);
			} else if (size <= 32) {
				return Array.getInt(array, index);
			} else if (size <= 64) {
				return Array.getLong(array, index);
			}
		} else if (type.isUint()) {
			int size = type.getSizeInBits();
			if (size < 8) {
				// extend to int
				return (int) Array.getByte(array, index);
			} else if (size < 16) {
				// extend to int
				return (int) Array.getShort(array, index);
			} else if (size < 32) {
				return Array.getInt(array, index);
			} else if (size < 64) {
				return Array.getLong(array, index);
			}
		}

		return Array.get(array, index);
	}

	public static BigInteger getBigInteger(Object value) {
		if (value instanceof Integer) {
			return BigInteger.valueOf(((Integer) value));
		} else if (value instanceof Long) {
			return BigInteger.valueOf(((Long) value));
		} else if (value instanceof BigInteger) {
			return (BigInteger) value;
		} else if (value instanceof Byte) {
			return BigInteger.valueOf((Byte) value);
		} else if (value instanceof Short) {
			return BigInteger.valueOf((Short) value);
		} else {
			return null;
		}
	}

	private static byte getByteValue(Object value) {
		if (value instanceof Integer) {
			return ((Integer) value).byteValue();
		} else if (value instanceof Long) {
			return ((Long) value).byteValue();
		} else if (value instanceof BigInteger) {
			return ((BigInteger) value).byteValue();
		} else {
			return 0;
		}
	}

	public static Expression getExpression(Object value) {
		if (isBool(value)) {
			return IrFactory.eINSTANCE.createExprBool((Boolean) value);
		} else if (isInt(value)) {
			return IrFactory.eINSTANCE.createExprInt(getBigInteger(value));
		} else if (isString(value)) {
			return IrFactory.eINSTANCE.createExprString((String) value);
		} else if (isList(value)) {
			ExprList list = IrFactory.eINSTANCE.createExprList();
			int length = Array.getLength(value);
			for (int i = 0; i < length; i++) {
				list.getValue().add(getExpression(Array.get(value, i)));
			}
			return list;

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
		} else if (value instanceof Byte) {
			return ((Byte) value).intValue();
		} else if (value instanceof Short) {
			return ((Short) value).intValue();
		} else {
			return 0;
		}
	}

	private static long getLongValue(Object value) {
		if (value instanceof Integer) {
			return ((Integer) value).longValue();
		} else if (value instanceof Long) {
			return ((Long) value).longValue();
		} else if (value instanceof BigInteger) {
			return ((BigInteger) value).longValue();
		} else {
			return 0;
		}
	}

	private static short getShortValue(Object value) {
		if (value instanceof Integer) {
			return ((Integer) value).shortValue();
		} else if (value instanceof Long) {
			return ((Long) value).shortValue();
		} else if (value instanceof BigInteger) {
			return ((BigInteger) value).shortValue();
		} else {
			return 0;
		}
	}

	public static Object getValue(Expression expr) {
		if (expr != null) {
			switch (expr.eClass().getClassifierID()) {
			case IrPackage.EXPR_BOOL:
				return ((ExprBool) expr).isValue();
			case IrPackage.EXPR_INT:
				return getIntValue(((ExprInt) expr).getValue());
			}
		}
		return null;
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
		return (value instanceof Byte || value instanceof Short
				|| value instanceof Integer || value instanceof Long || value instanceof BigInteger);
	}

	/**
	 * Returns <code>true</code> if value is a list.
	 * 
	 * @param value
	 *            a value
	 * @return <code>true</code> if value is a list
	 */
	public static boolean isList(Object value) {
		return (value != null && value.getClass().isArray());
	}

	/**
	 * Returns <code>true</code> if value is a String.
	 * 
	 * @param value
	 *            a value
	 * @return <code>true</code> if value is a String
	 */

	public static boolean isString(Object value) {
		return value instanceof String;
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
	 * If value is an array, returns its number of elements.
	 * 
	 * @param value
	 * @return
	 */
	public static int length(Object value) {
		if (isList(value)) {
			return Array.getLength(value);
		}
		return 0;
	}

	public static boolean logicAnd(Object val1, Object val2) {
		if (isBool(val1) && isBool(val2)) {
			return ((Boolean) val1) && ((Boolean) val2);
		}
		return false;
	}

	public static boolean logicNot(Object value) {
		if (isBool(value)) {
			return !((Boolean) value);
		}
		return false;
	}

	public static boolean logicOr(Object val1, Object val2) {
		if (isBool(val1) && isBool(val2)) {
			return ((Boolean) val1) || ((Boolean) val2);
		}
		return false;
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
	 * Returns <code>-value</code>.
	 * 
	 * @param value
	 * @return <code>-value</code>.
	 */
	public static Object negate(Object value) {
		BigInteger bi = getBigInteger(value);
		if (bi == null) {
			return null;
		}
		return bi.negate();
	}

	/**
	 * Returns <code>~value</code>.
	 * 
	 * @param value
	 * @return <code>~value</code>.
	 */
	public static Object not(Object value) {
		BigInteger bi = getBigInteger(value);
		if (bi == null) {
			return null;
		}
		return bi.not();
	}

	public static Object notEquals(Object val1, Object val2) {
		Object result = equals(val1, val2);
		if (isBool(result)) {
			return !((Boolean) result);
		}
		return null;
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

	public static Object pow(Object val1, Object val2) {
		BigInteger bi1 = getBigInteger(val1);
		int i2 = getIntValue(val2);
		if (bi1 == null) {
			return null;
		}
		return bi1.pow(i2);
	}

	/**
	 * Writes the given value in the given array at the given indexes.
	 * 
	 * @param type
	 *            type of the innermost elements of the array.
	 * @param array
	 *            an array object
	 * @param value
	 *            a value
	 * @param indexes
	 *            indexes
	 */
	public static void set(Type type, Object array, Object value,
			Object... indexes) {
		if (array == null || value == null) {
			return;
		}

		int numIndexes = indexes.length;
		for (int i = 0; i < numIndexes - 1; i++) {
			int index = getIntValue(indexes[i]);
			array = Array.get(array, index);
		}

		int index = getIntValue(indexes[numIndexes - 1]);
		if (type.isBool()) {
			Array.setBoolean(array, index, (Boolean) value);
		} else if (type.isFloat()) {
			Array.setFloat(array, index, (Float) value);
		} else if (type.isInt()) {
			int size = type.getSizeInBits();
			if (size <= 8) {
				Array.setByte(array, index, getByteValue(value));
			} else if (size <= 16) {
				Array.setShort(array, index, getShortValue(value));
			} else if (size <= 32) {
				Array.setInt(array, index, getIntValue(value));
			} else if (size <= 64) {
				Array.setLong(array, index, getLongValue(value));
			} else {
				Array.set(array, index, value);
			}
		} else if (type.isUint()) {
			int size = type.getSizeInBits();
			if (size < 8) {
				Array.setByte(array, index, getByteValue(value));
			} else if (size < 16) {
				Array.setShort(array, index, getShortValue(value));
			} else if (size < 32) {
				Array.setInt(array, index, getIntValue(value));
			} else if (size < 64) {
				Array.setLong(array, index, getLongValue(value));
			} else {
				Array.set(array, index, value);
			}
		} else {
			Array.set(array, index, value);
		}
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
