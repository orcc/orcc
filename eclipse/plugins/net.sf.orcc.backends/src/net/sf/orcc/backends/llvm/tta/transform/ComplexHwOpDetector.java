/*
 * Copyright (c) 2012, IRISA
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
package net.sf.orcc.backends.llvm.tta.transform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.orcc.df.Instance;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.ValueUtil;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.util.EcoreHelper;

/**
 * 
 * This class defines a code analyzer able to detect the operations needing
 * complex hardware implementations.
 * 
 * @author Herve Yviquel
 * 
 */
public class ComplexHwOpDetector extends DfVisitor<Void> {

	private class Detector extends AbstractIrVisitor<Void> {
		public Detector() {
			super(true);
		}

		@Override
		public Void caseExprBinary(ExprBinary expr) {
			OpBinary op = expr.getOp();
			// The operation will be transform in a simple shift.
			if (op == OpBinary.TIMES && !ValueUtil.isPowerOfTwo(expr.getE1())
					&& !ValueUtil.isPowerOfTwo(expr.getE2())) {
				detectedOps.add(op);
				operationsLines.add(EcoreHelper.getContainerOfType(expr,
						Instruction.class).getLineNumber());
			} else if ((op == OpBinary.MOD || op == OpBinary.DIV)
					&& !ValueUtil.isPowerOfTwo(expr.getE2())) {
				detectedOps.add(op);
				operationsLines.add(EcoreHelper.getContainerOfType(expr,
						Instruction.class).getLineNumber());
			}
			return null;
		}
	}

	private Set<OpBinary> detectedOps;
	private List<Integer> operationsLines;

	public ComplexHwOpDetector() {
		this.irVisitor = new Detector();
	};

	@Override
	public Void caseInstance(Instance instance) {
		detectedOps = new HashSet<OpBinary>();
		operationsLines = new ArrayList<Integer>();

		super.caseInstance(instance);

		if (!detectedOps.isEmpty()) {
			OrccLogger.traceln(detectedOps.toString()
					+ " operation(s) detected in " + instance.getName()
					+ " at line(s) " + operationsLines.toString() + ".");
		}
		return null;
	}
}
