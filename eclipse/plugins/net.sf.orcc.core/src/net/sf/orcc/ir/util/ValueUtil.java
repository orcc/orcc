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

import static java.math.BigInteger.ONE;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
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
	 * <code>null</code> if the two operands are not both floats or integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object add(Object val1, Object val2) {
		if (isFloat(val1) && isFloat(val2)) {
			return ((BigDecimal) val1).add((BigDecimal) val2);
		} else if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).add((BigInteger) val2);
		}
		throw new OrccRuntimeException("type mismatch in add");
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
		if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).and((BigInteger) val2);
		}
		throw new OrccRuntimeException("type mismatch in and");
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
		} else if (type.isInt() || type.isUint()) {
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
	 * <code>null</code> if the two operands are not both floats or integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object divide(Object val1, Object val2) {
		if (isFloat(val1) && isFloat(val2)) {
			return ((BigDecimal) val1).divide((BigDecimal) val2);
		} else if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).divide((BigInteger) val2);
		}
		throw new OrccRuntimeException("type mismatch in divide");
	}

	/**
	 * If the two operands have the same type, returns a new boolean that is the
	 * result of their comparison; if they do not have the same type, returns
	 * <code>null</code>.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return a boolean or <code>null</code> in case of type mismatch
	 */
	public static Object equals(Object val1, Object val2) {
		if (isBool(val1) && isBool(val2) || isFloat(val1) && isFloat(val2)
				|| isInt(val1) && isInt(val2)) {
			return val1.equals(val2);
		}
		throw new OrccRuntimeException("type mismatch in equals");
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
		if (isFloat(val1) && isFloat(val2)) {
			return ((BigDecimal) val1).compareTo((BigDecimal) val2) >= 0;
		} else if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).compareTo((BigInteger) val2) >= 0;
		}
		throw new OrccRuntimeException("type mismatch in ge");
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
			return BigDecimal.valueOf(Array.getFloat(array, index));
		} else if (type.isInt()) {
			int size = type.getSizeInBits();
			Object value = Array.get(array, index);
			long longVal;
			if (size <= 8) {
				longVal = (Byte) value;
			} else if (size <= 16) {
				longVal = (Short) value;
			} else if (size <= 32) {
				longVal = (Integer) value;
			} else if (size <= 64) {
				longVal = (Long) value;
			} else {
				return value;
			}
			return BigInteger.valueOf(longVal);
		} else if (type.isUint()) {
			int size = type.getSizeInBits();
			Object value = Array.get(array, index);
			long longVal;
			if (size <= 8) {
				longVal = ((Byte) value) & 0xFF;
			} else if (size <= 16) {
				longVal = ((Short) value) & 0xFFFF;
			} else if (size <= 32) {
				longVal = ((Integer) value) & 0xFFFFFFFFL;
			} else if (size <= 64) {
				BigInteger bigInt = BigInteger.valueOf((Long) value);
				return bigInt.and(ONE.shiftLeft(64).subtract(ONE));
			} else {
				return value;
			}
			return BigInteger.valueOf(longVal);
		}
		throw new OrccRuntimeException("unexpected type in set");
	}

	public static Expression getExpression(Object value) {
		if (isBool(value)) {
			return IrFactory.eINSTANCE.createExprBool((Boolean) value);
		} else if (isFloat(value)) {
			return IrFactory.eINSTANCE.createExprFloat((BigDecimal) value);
		} else if (isInt(value)) {
			return IrFactory.eINSTANCE.createExprInt((BigInteger) value);
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

	private static int getIntValue(Object value) {
		if (value instanceof Integer) {
			return (Integer) value;
		} else if (isInt(value)) {
			return ((BigInteger) value).intValue();
		}
		throw new OrccRuntimeException("type mismatch in getIntValue");
	}

	/**
	 * Returns the value of the given expression.
	 * 
	 * @param expr
	 *            an expression
	 * @return the value of the given expression
	 */
	public static Object getValue(Expression expr) {
		if (expr != null) {
			if (expr.isExprBool()) {
				return ((ExprBool) expr).isValue();
			} else if (expr.isExprFloat()) {
				return ((ExprFloat) expr).getValue();
			} else if (expr.isExprInt()) {
				return ((ExprInt) expr).getValue();
			} else if (expr.isExprString()) {
				return ((ExprString) expr).getValue();
			} else if (expr.isExprList()) {
				throw new OrccRuntimeException(
						"list type not supported yet in getValue");
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
		if (isFloat(val1) && isFloat(val2)) {
			return ((BigDecimal) val1).compareTo((BigDecimal) val2) > 0;
		} else if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).compareTo((BigInteger) val2) > 0;
		}
		throw new OrccRuntimeException("type mismatch in gt");
	}

	/**
	 * Returns <code>true</code> if value is a boolean.
	 * 
	 * @param value
	 *            a value
	 * @return <code>true</code> if value is a boolean
	 */
	public static boolean isBool(Object value) {
		return value instanceof Boolean;
	}

	/**
	 * Returns <code>true</code> if value is a float.
	 * 
	 * @param value
	 *            a value
	 * @return <code>true</code> if value is a float
	 */
	public static boolean isFloat(Object value) {
		return value instanceof BigDecimal;
	}

	/**
	 * Returns <code>true</code> if value is an integer.
	 * 
	 * @param value
	 *            a value
	 * @return <code>true</code> if value is an integer
	 */
	public static boolean isInt(Object value) {
		return value instanceof BigInteger;
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
		if (isFloat(val1) && isFloat(val2)) {
			return ((BigDecimal) val1).compareTo((BigDecimal) val2) <= 0;
		} else if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).compareTo((BigInteger) val2) <= 0;
		}
		throw new OrccRuntimeException("type mismatch in le");
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

	/**
	 * Returns a new boolean that is <code>true</code> if both val1 and val2 are
	 * true, or <code>null</code> if the two operands are not both of the same
	 * type.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return a boolean value or <code>null</code>
	 */
	public static Object logicAnd(Object val1, Object val2) {
		if (isBool(val1) && isBool(val2)) {
			return ((Boolean) val1) && ((Boolean) val2);
		}
		throw new OrccRuntimeException("type mismatch in logicAnd");
	}

	/**
	 * Returns a new boolean that is <code>!value</code> if value is a boolean,
	 * or <code>null</code> otherwise
	 * 
	 * @param value
	 *            an object
	 * @return a boolean value or <code>null</code>
	 */
	public static Object logicNot(Object value) {
		if (isBool(value)) {
			return !((Boolean) value);
		}
		throw new OrccRuntimeException("type mismatch in logicNot");
	}

	/**
	 * Returns a new boolean that is <code>true</code> if either val1 or val2 is
	 * true, or <code>null</code> if the two operands are not both of the same
	 * type.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return a boolean value or <code>null</code>
	 */
	public static Object logicOr(Object val1, Object val2) {
		if (isBool(val1) && isBool(val2)) {
			return ((Boolean) val1) || ((Boolean) val2);
		}
		throw new OrccRuntimeException("type mismatch in logicOr");
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
		if (isFloat(val1) && isFloat(val2)) {
			return ((BigDecimal) val1).compareTo((BigDecimal) val2) < 0;
		} else if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).compareTo((BigInteger) val2) < 0;
		}
		throw new OrccRuntimeException("type mismatch in lt");
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
		if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).mod((BigInteger) val2);
		}
		throw new OrccRuntimeException("type mismatch in mod");
	}

	/**
	 * Returns a new integer equal to the product of the two operands, or
	 * <code>null</code> if the two operands are not both floats or integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object multiply(Object val1, Object val2) {
		if (isFloat(val1) && isFloat(val2)) {
			return ((BigDecimal) val1).multiply((BigDecimal) val2);
		} else if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).multiply((BigInteger) val2);
		}
		throw new OrccRuntimeException("type mismatch in multiply");
	}

	/**
	 * Returns a new integer equal to <code>-value</code>, or <code>null</code>
	 * if value is not a float or an integer.
	 * 
	 * @param value
	 *            an object
	 * @return a float or integer value or <code>null</code>
	 */
	public static Object negate(Object value) {
		if (isFloat(value)) {
			return ((BigDecimal) value).negate();
		} else if (isInt(value)) {
			return ((BigInteger) value).negate();
		}
		throw new OrccRuntimeException("type mismatch in negate");
	}

	/**
	 * Returns a new integer equal to <code>~value</code>, or <code>null</code>
	 * if value is not an integer.
	 * 
	 * @param value
	 *            an object
	 * @return an integer value or <code>null</code>
	 */
	public static Object not(Object value) {
		if (isInt(value)) {
			return ((BigInteger) value).not();
		}
		throw new OrccRuntimeException("type mismatch in not");
	}

	/**
	 * If the two operands have the same type, returns a new boolean that is the
	 * result of their comparison; if they do not have the same type, returns
	 * <code>null</code>.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return a boolean or <code>null</code> in case of type mismatch
	 */
	public static Object notEquals(Object val1, Object val2) {
		Object result = equals(val1, val2);
		if (isBool(result)) {
			return !((Boolean) result);
		}
		throw new OrccRuntimeException("type mismatch in notEquals");
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
		if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).or((BigInteger) val2);
		}
		throw new OrccRuntimeException("type mismatch in or");
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
	public static Object pow(Object val1, Object val2) {
		if (isInt(val1) && isInt(val2)) {
			int exponent = ((BigInteger) val2).intValue();
			return ((BigInteger) val1).pow(exponent);
		}
		throw new OrccRuntimeException("type mismatch in pow");
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
		Object valueToSet;
		if (type.isBool() && isBool(value)) {
			valueToSet = value;
		} else if (type.isFloat() && isFloat(value)) {
			BigDecimal floatVal = (BigDecimal) value;
			valueToSet = floatVal.floatValue();
		} else if ((type.isInt() || type.isUint()) && isInt(value)) {
			BigInteger intVal = (BigInteger) value;
			int size = type.getSizeInBits();
			if (size <= 8) {
				valueToSet = intVal.byteValue();
			} else if (size <= 16) {
				valueToSet = intVal.shortValue();
			} else if (size <= 32) {
				valueToSet = intVal.intValue();
			} else if (size <= 64) {
				valueToSet = intVal.longValue();
			} else {
				valueToSet = value;
			}
		} else {
			throw new OrccRuntimeException("unexpected type in set");
		}
		Array.set(array, index, valueToSet);
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
		if (isInt(val1) && isInt(val2)) {
			int n = ((BigInteger) val2).intValue();
			return ((BigInteger) val1).shiftLeft(n);
		}
		throw new OrccRuntimeException("type mismatch in shiftLeft");
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
		if (isInt(val1) && isInt(val2)) {
			int n = ((BigInteger) val2).intValue();
			return ((BigInteger) val1).shiftRight(n);
		}
		throw new OrccRuntimeException("type mismatch in shiftRight");
	}

	/**
	 * Returns a new integer equal to the difference of the two operands, or
	 * <code>null</code> if the two operands are not both floats or integers.
	 * 
	 * @param val1
	 *            an object
	 * @param val2
	 *            an object
	 * @return a float or integer value or <code>null</code>
	 */
	public static Object subtract(Object val1, Object val2) {
		if (isFloat(val1) && isFloat(val2)) {
			return ((BigDecimal) val1).subtract((BigDecimal) val2);
		} else if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).subtract((BigInteger) val2);
		}
		throw new OrccRuntimeException("type mismatch in subtract");
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
		if (isInt(val1) && isInt(val2)) {
			return ((BigInteger) val1).xor((BigInteger) val2);
		}
		throw new OrccRuntimeException("type mismatch in xor");
	}

}
