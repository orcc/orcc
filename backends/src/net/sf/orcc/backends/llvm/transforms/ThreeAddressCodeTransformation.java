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
package net.sf.orcc.backends.llvm.transforms;

import java.util.ListIterator;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * Split expression and effective node.
 * 
 * @author Jérôme GORIN
 * 
 */
public class ThreeAddressCodeTransformation extends AbstractActorTransformation {

	private class ExpressionSplitter implements ExpressionInterpreter {

		public ExpressionSplitter(ListIterator<Instruction> it) {

		}

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object interpret(IntExpr expr, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(Assign assign, Object... args) {
		ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
		Expression value = assign.getValue();
		assign.setValue((Expression) value.accept(new ExpressionSplitter(it)));
	}

}
