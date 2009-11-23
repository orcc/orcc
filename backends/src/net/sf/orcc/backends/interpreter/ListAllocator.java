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
package net.sf.orcc.backends.interpreter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.type.AbstractTypeInterpreter;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;

/**
 * Allocates a List of any dimension
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class ListAllocator extends AbstractTypeInterpreter {

	private ArrayList<Integer> sizeList;
	private ExpressionEvaluator expressionEvaluator;

	public ListAllocator() {
		sizeList = new ArrayList<Integer>();
		expressionEvaluator = new ExpressionEvaluator();
	}

	public List<Integer> getSize() {
		List<Integer> list = new ArrayList<Integer>(sizeList);
		sizeList.clear();
		return list;
	}

	public Object interpret(BoolType type) {
		return Boolean.class;
	}

	public Object interpret(IntType type) {
		return Integer.class;
	}

	public Object interpret(UintType type) {
		return Integer.class;
	}

	public Object interpret(VoidType type) {
		return null;
	}

	public Object interpret(StringType type) {
		return String.class;
	}

	public Object interpret(ListType type) {
		try {
			Object size = type.getSize().accept(expressionEvaluator);
			if (size instanceof Integer) {
				sizeList.add((Integer) size);
				return (Class<?>) type.getElementType().accept(this);
			} else {
				throw new OrccException("expected int");
			}
		} catch (OrccException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public Object allocate(Type type) {
		Class<?> classType = (Class<?>) type.accept(this);
		int[] dimensions = new int[sizeList.size()];
		for (int i = 0; i < sizeList.size(); i++) {
			dimensions[i] = (Integer) sizeList.get(i);
		}
		sizeList.clear();

		return Array.newInstance(classType, dimensions);
	}

//	public Object allocateAndInitialize(Type type, List<Object> init) {
//		Class<?> classType = (Class<?>) type.accept(this);
//		int[] dimensions = new int[sizeList.size()];
//		for (int i = 0; i < sizeList.size(); i++) {
//			dimensions[i] = (Integer) sizeList.get(i);
//		}
//		sizeList.clear();
//
//		Object array = Array.newInstance(classType, dimensions);
//		if (init != null) {
//			array=init;
////			Object prevArray=array;
////			Object newArray=array;
////			int lastDim=dimensions[0];
////			for (int dim : dimensions) {
////				prevArray = newArray;
////				lastDim=dimensions[i];
////				newArray = Array.get(prevArray, i);
////				
////				System.arraycopy(init, 0, prevArray, 0, );
//		}
//		
//		return array;
//	}
}
