/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl.transforms;

import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.AbstractExpressionVisitor;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * This class defines a transformation that sets the type of expressions to the
 * type of their target.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class SizeRedimension extends AbstractActorTransformation {

	private static class ExpressionRedimension extends
			AbstractExpressionVisitor {

		/**
		 * type of the target variable
		 */
		private Type type;

		/**
		 * Creates a Expression redimension with the given target type.
		 * 
		 * @param type
		 *            the type of the target
		 */
		public ExpressionRedimension(Type type) {
			this.type = type;
		}

		@Override
		public void visit(BinaryExpr expr, Object... args) {
			// visits sub-expressions
			expr.getE1().accept(this);
			expr.getE2().accept(this);

			// updates the type of this expression
			expr.setType(type);
		}

	}

	@Override
	public void visit(Assign assign, Object... args) {
		Type targetType = assign.getTarget().getType();
		assign.getValue().accept(new ExpressionRedimension(targetType));
	}

}
