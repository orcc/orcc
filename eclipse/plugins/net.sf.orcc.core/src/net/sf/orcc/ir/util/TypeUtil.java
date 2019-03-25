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

import java.math.BigInteger;
import java.util.Iterator;

import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines static utility methods to deal with types.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeUtil {

	private static class Glb implements Unification {

		public static Glb instance = new Glb();

		@Override
		public int getSize(Type t1, Type t2) {
			return 0;
		}

		@Override
		public Type getType(Type t1, Type t2) {
			return TypeUtil.getGlb(t1, t2);
		}

	}

	private static class Lub implements Unification {

		public static Lub instance = new Lub();

		protected Type type;

		@Override
		public int getSize(Type t1, Type t2) {
			return 0;
		}

		@Override
		public Type getType(Type t1, Type t2) {
			type = TypeUtil.getLub(t1, t2);
			return type;
		}

	}

	private static class LubPlus1 extends Lub {

		public static LubPlus1 instance = new LubPlus1();

		@Override
		public int getSize(Type t1, Type t2) {
			return type == null ? 0 : type.getSizeInBits() + 1;
		}

	}

	private static class LubSum extends Lub {

		public static LubSum instance = new LubSum();

		@Override
		public int getSize(Type t1, Type t2) {
			return t1.getSizeInBits() + t2.getSizeInBits();
		}

	}

	private static class LubSumPow extends Lub {

		public static LubSumPow instance = new LubSumPow();

		@Override
		public int getSize(Type t1, Type t2) {
			int shift = t2.getSizeInBits();
			if (shift >= 6) {
				// limits type size to 64
				shift = 6;
			}
			return t1.getSizeInBits() + (1 << shift) - 1;
		}

	}

	private static class SignedLub extends Lub {

		public static SignedLub instance = new SignedLub();

		@Override
		public Type getType(Type t1, Type t2) {
			super.getType(t1, t2);
			if (type.isUint()) {
				type = IrFactory.eINSTANCE
						.createTypeInt(type.getSizeInBits() + 1);
			}
			return type;
		}

	}

	private static interface Unification {

		int getSize(Type t1, Type t2);

		Type getType(Type t1, Type t2);

	}

	/**
	 * Creates a new type based on the unification of t1 and t2, and clips its
	 * size to {@link #maxSize}.
	 * 
	 * @param t1
	 *            a type
	 * @param t2
	 *            a type
	 * @param unification
	 *            how to unify t1 and t2
	 */
	public static Type createType(Type t1, Type t2, Unification unification) {
		Type type = unification.getType(t1, t2);
		int size = unification.getSize(t1, t2);

		// only set the size if it is different than 0
		if (size != 0) {
			if (type.isInt()) {
				((TypeInt) type).setSize(size);
			} else if (type.isUint()) {
				((TypeUint) type).setSize(size);
			}
		}

		return type;
	}

	/**
	 * Returns the Greatest Lower Bound of the given types. The GLB is the
	 * smallest type of (t1, t2). When t1 and t2 have different types (e.g. one
	 * is a int and the other is a uint), an int is returned.
	 * 
	 * @param t1
	 *            a type
	 * @param t2
	 *            another type
	 * @return the Greatest Lower Bound of the given types
	 */
	public static Type getGlb(Type t1, Type t2) {
		if (t1.isBool() && t2.isBool()) {
			return IrFactory.eINSTANCE.createTypeBool();
		} else if (t1.isInt() && t2.isInt()) {
			return IrFactory.eINSTANCE.createTypeInt(Math.min(
					((TypeInt) t1).getSize(), ((TypeInt) t2).getSize()));
		} else if (t1.isUint() && t2.isUint()) {
			return IrFactory.eINSTANCE.createTypeUint(Math.min(
					((TypeUint) t1).getSize(), ((TypeUint) t2).getSize()));
		} else if (t1.isInt() && t2.isUint()) {
			int si = ((TypeInt) t1).getSize();
			int su = ((TypeUint) t2).getSize();
			if (si > su) {
				return IrFactory.eINSTANCE.createTypeInt(su + 1);
			} else {
				return IrFactory.eINSTANCE.createTypeInt(si);
			}
		} else if (t1.isUint() && t2.isInt()) {
			int su = ((TypeUint) t1).getSize();
			int si = ((TypeInt) t2).getSize();
			if (si > su) {
				return IrFactory.eINSTANCE.createTypeInt(su + 1);
			} else {
				return IrFactory.eINSTANCE.createTypeInt(si);
			}
		}

		return null;
	}

	/**
	 * Returns the Least Upper Bound of the given types, which is the smallest
	 * type that can contain both types. If no such type exists (e.g. when types
	 * are not compatible with each other), <code>null</code> is returned.
	 * 
	 * @param t1
	 *            a type
	 * @param t2
	 *            another type
	 * @return the Least Upper Bound of the given types
	 */
	public static Type getLub(Type t1, Type t2) {
		if (t1 == null || t2 == null) {
			return null;
		}

		if (t1.isBool() && t2.isBool()) {
			return IrFactory.eINSTANCE.createTypeBool();
		} else if (t1.isFloat() && t2.isFloat()) {
			return IrFactory.eINSTANCE.createTypeFloat(Math.max(
					((TypeFloat) t1).getSize(), ((TypeFloat) t2).getSize()));
		} else if (t1.isString() && t2.isString()) {
			return IrFactory.eINSTANCE.createTypeString();
		} else if (t1.isInt() && t2.isInt()) {
			return IrFactory.eINSTANCE.createTypeInt(Math.max(
					((TypeInt) t1).getSize(), ((TypeInt) t2).getSize()));
		} else if (t1.isList() && t2.isList()) {
			TypeList listType1 = (TypeList) t1;
			TypeList listType2 = (TypeList) t2;
			Type type = getLub(listType1.getType(), listType2.getType());
			if (type != null) {
				// only return a list when the underlying type is valid
				int size = Math.max(listType1.getSize(), listType2.getSize());
				return IrFactory.eINSTANCE.createTypeList(size, type, listType1.getDyn() && listType2.getDyn());
			}
		} else if (t1.isUint() && t2.isUint()) {
			return IrFactory.eINSTANCE.createTypeUint(Math.max(
					((TypeUint) t1).getSize(), ((TypeUint) t2).getSize()));
		} else if (t1.isInt() && t2.isUint()) {
			int si = ((TypeInt) t1).getSize();
			int su = ((TypeUint) t2).getSize();
			if (si > su) {
				return IrFactory.eINSTANCE.createTypeInt(si);
			} else {
				return IrFactory.eINSTANCE.createTypeInt(su + 1);
			}
		} else if (t1.isUint() && t2.isInt()) {
			int su = ((TypeUint) t1).getSize();
			int si = ((TypeInt) t2).getSize();
			if (si > su) {
				return IrFactory.eINSTANCE.createTypeInt(si);
			} else {
				return IrFactory.eINSTANCE.createTypeInt(su + 1);
			}
		}

		return null;
	}

	/**
	 * Returns the number of bits in the two's-complement representation of the
	 * given number, including a sign bit <i>only</i> if <code>number</code> is
	 * less than zero.
	 * 
	 * @param number
	 *            a number
	 * @return the number of bits in the two's-complement representation of the
	 *         given number, <i>including</i> a sign bit
	 */
	public static int getSize(BigInteger number) {
		int cmp = number.compareTo(BigInteger.ZERO);
		if (cmp == 0) {
			// 0 is represented as a uint(size=1)
			return 1;
		} else {
			int bitLength = number.bitLength();
			return (cmp > 0) ? bitLength : bitLength + 1;
		}
	}

	/**
	 * Returns the number of bits in the two's-complement representation of the
	 * given number, including a sign bit <i>only</i> if <code>number</code> is
	 * less than zero.
	 * 
	 * @param number
	 *            a number
	 * @return the number of bits in the two's-complement representation of the
	 *         given number, <i>including</i> a sign bit
	 */
	public static int getSize(long number) {
		return getSize(BigInteger.valueOf(number));
	}

	/**
	 * Computes the type of the given list expression.
	 * 
	 * @param expr
	 *            a list expression
	 * @return the type of the given list expression
	 */
	public static Type getType(ExprList expr) {
		Iterator<Expression> it = expr.getValue().iterator();
		if (it.hasNext()) {
			Expression subExpr = it.next();
			Type eltLubType = EcoreUtil.copy(subExpr.getType());
			while (it.hasNext()) {
				subExpr = it.next();
				Type t2 = subExpr.getType();
				eltLubType = TypeUtil.getLub(eltLubType, t2);
			}

			return IrFactory.eINSTANCE.createTypeList(expr.getSize(),
					eltLubType);
		}

		return null;
	}

	/**
	 * Returns the type of a binary expression whose left operand has type t1
	 * and right operand has type t2, and whose operator is given.
	 * 
	 * TODO replace automatic int/uint to float casting with explicit casts
	 * 
	 * @param op
	 *            operator
	 * @param t1
	 *            type of the first operand
	 * @param t2
	 *            type of the second operand
	 * @return the type of the binary expression, or <code>null</code>
	 */
	public static Type getTypeBinary(OpBinary op, Type t1, Type t2) {
		if (t1 == null || t2 == null) {
			return null;
		}

		switch (op) {
		case BITAND:
			return createType(t1, t2, Glb.instance);

		case BITOR:
		case BITXOR:
			return createType(t1, t2, Lub.instance);

		case TIMES:
			if ((t1.isInt() || t1.isUint()) && t2.isFloat()) {
				return IrFactory.eINSTANCE.createTypeFloat(t2.getSizeInBits());
			} else if ((t2.isInt() || t2.isUint()) && t1.isFloat()) {
				return IrFactory.eINSTANCE.createTypeFloat(t1.getSizeInBits());
			} else if (t1.isFloat() && t2.isFloat()) {
				return createType(t1, t2, Lub.instance);
			}
			return createType(t1, t2, LubSum.instance);

		case MINUS:
			if ((t1.isInt() || t1.isUint()) && t2.isFloat()) {
				return IrFactory.eINSTANCE.createTypeFloat(t2.getSizeInBits());
			} else if ((t2.isInt() || t2.isUint()) && t1.isFloat()) {
				return IrFactory.eINSTANCE.createTypeFloat(t1.getSizeInBits());
			} else if (t1.isFloat() && t2.isFloat()) {
				return createType(t1, t2, Lub.instance);
			}
			return createType(t1, t2, SignedLub.instance);

		case PLUS:
			if (t1.isString() && !t2.isList() || t2.isString() && !t1.isList()) {
				return IrFactory.eINSTANCE.createTypeString();
			}

			if ((t1.isInt() || t1.isUint()) && t2.isFloat()) {
				return IrFactory.eINSTANCE.createTypeFloat(t2.getSizeInBits());
			} else if ((t2.isInt() || t2.isUint()) && t1.isFloat()) {
				return IrFactory.eINSTANCE.createTypeFloat(t1.getSizeInBits());
			} else if (t1.isFloat() && t2.isFloat()) {
				return createType(t1, t2, Lub.instance);
			}
			return createType(t1, t2, LubPlus1.instance);

		case DIV:
		case DIV_INT:
			if ((t1.isInt() || t1.isUint()) && t2.isFloat()) {
				return IrFactory.eINSTANCE.createTypeFloat(t2.getSizeInBits());
			} else if ((t2.isInt() || t2.isUint()) && t1.isFloat()) {
				return IrFactory.eINSTANCE.createTypeFloat(t1.getSizeInBits());
			} else if (t1.isFloat() && t2.isFloat()) {
				return createType(t1, t2, Lub.instance);
			}
		case SHIFT_RIGHT:
			return EcoreUtil.copy(t1);

		case MOD:
			return EcoreUtil.copy(t2);

		case SHIFT_LEFT:
			return createType(t1, t2, LubSumPow.instance);

		case EQ:
		case GE:
		case GT:
		case LE:
		case LT:
		case NE:
			return IrFactory.eINSTANCE.createTypeBool();

		case EXP:
			return null;

		case LOGIC_AND:
		case LOGIC_OR:
			return IrFactory.eINSTANCE.createTypeBool();

		default:
			return null;
		}
	}

	/**
	 * Returns <code>true</code> if type src can be converted to type dst.
	 * 
	 * @param src
	 *            a type
	 * @param dst
	 *            the type src should be converted to
	 * @return <code>true</code> if type src can be converted to type dst
	 */
	public static boolean isConvertibleTo(Type src, Type dst) {
		if (src == null || dst == null) {
			return false;
		}

		if (src.isFloat() && dst.isFloat()) {
			return dst.getSizeInBits() >= src.getSizeInBits();
		}

		if (src.isBool() && dst.isBool() || src.isString() && dst.isString()
				|| (src.isInt() || src.isUint())
				&& (dst.isInt() || dst.isUint())
				|| (src.isInt() || src.isUint()) && dst.isFloat()) {
			return true;
		}

		if (src.isList() && dst.isList()) {
			TypeList typeSrc = (TypeList) src;
			TypeList typeDst = (TypeList) dst;
			// Recursively check type convertibility
			if (isConvertibleTo(typeSrc.getType(), typeDst.getType())) {
				if (typeSrc.getSizeExpr() != null
						&& typeDst.getSizeExpr() != null) {
					return typeSrc.getSize() <= typeDst.getSize();
				}
				return true;
			}
		}

		return false;
	}

}
