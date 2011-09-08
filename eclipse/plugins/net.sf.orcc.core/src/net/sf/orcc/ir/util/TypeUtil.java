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

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

import java.math.BigInteger;
import java.util.Iterator;

import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
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

		if (src.isBool() && dst.isBool() || src.isFloat() && dst.isFloat()
				|| src.isString() && dst.isString()
				|| (src.isInt() || src.isUint())
				&& (dst.isInt() || dst.isUint())) {
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

	/**
	 * Returns the Greatest Lower Bound of the given types. The GLB is the
	 * smallest type of (t1, t2).
	 * 
	 * @param t1
	 *            a type
	 * @param t2
	 *            another type
	 * @return the Greatest Lower Bound of the given types
	 */
	public static Type getGlb(Type t1, Type t2) {
		if (t1.isInt() && t2.isInt()) {
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
	 * Returns the Least Upper Bound of the given types.
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
			return IrFactory.eINSTANCE.createTypeFloat();
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
				return IrFactory.eINSTANCE.createTypeList(size, type);
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
	 * given number, <i>including</i> a sign bit.
	 * 
	 * @param number
	 *            a number
	 * @return the number of bits in the two's-complement representation of the
	 *         given number, <i>including</i> a sign bit
	 */
	public static int getSize(BigInteger number) {
		return number.bitLength() + 1;
	}

	/**
	 * Returns the number of bits in the two's-complement representation of the
	 * given number, <i>including</i> a sign bit.
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

			return eINSTANCE.createTypeList(expr.getSize(), eltLubType);
		}

		return null;
	}

}
