/*
 * Copyright (c) 201Z, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.tta.transformations;

import net.sf.dftools.util.util.EcoreHelper;
import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.util.WriteListener;

/**
 * 
 * This class defines a slow operation detector.
 * 
 * @author Herve Yviquel
 * 
 */
public class SlowOperationDetector extends AbstractActorVisitor<Object> {

	private WriteListener writeListener;

	public SlowOperationDetector(WriteListener writeListener) {
		super(true);
		this.writeListener = writeListener;
	}

	public Object caseExprBinary(ExprBinary expr) {
		OpBinary op = expr.getOp();
		if (op == OpBinary.DIV || op == OpBinary.DIV) {
			writeListener.writeText("Warning: Division operation detected in "
					+ EcoreHelper.getContainerOfType(expr, Actor.class)
							.getName()
					+ " at line "
					+ EcoreHelper.getContainerOfType(expr, Instruction.class)
							.getLineNumber() + ".\n");
		} else if (op == OpBinary.MOD) {
			writeListener.writeText("Warning: Modulo operation detected in "
					+ EcoreHelper.getContainerOfType(expr, Actor.class)
							.getName()
					+ " at line "
					+ EcoreHelper.getContainerOfType(expr, Instruction.class)
							.getLineNumber() + ".\n");
		}
		return null;
	}

}
