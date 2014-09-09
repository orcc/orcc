/*
 * Copyright (c) 2014, IETR/INSA of Rennes
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
package net.sf.orcc.backends.transform;

import net.sf.orcc.backends.ir.InstCast;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * This class extends the original TypeResizer to remove cast instructions that
 * become useless.
 * 
 * @author Herve Yviquel
 * 
 */
public class TypeResizer extends net.sf.orcc.df.transform.TypeResizer {

	private class InnerTypeResizer extends
			net.sf.orcc.df.transform.TypeResizer.InnerTypeResizer {

		@Override
		public Void defaultCase(EObject object) {
			if (object instanceof InstCast) {
				caseInstCast((InstCast) object);
			}
			return null;
		}

		public Void caseInstCast(InstCast cast) {
			Var source = cast.getSource().getVariable();
			Var target = cast.getTarget().getVariable();
			Type tSource = source.getType();
			Type tTarget = target.getType();

			// Check if the cast instruction is useless and can be removed. This
			// situation can happen after applying multiple IR transformations.
			if (tSource.getSizeInBits() == tTarget.getSizeInBits()
					&& ((tSource.getClass() == tTarget.getClass())
							|| (tSource.isInt() && tTarget.isUint()) || (tSource
							.isUint() && tTarget.isInt()))) {
				EList<Use> uses = target.getUses();
				while (!uses.isEmpty()) {
					uses.get(0).setVariable(source);
				}
				IrUtil.delete(cast);
				indexInst--;
			}
			return null;
		}

	}

	public TypeResizer(boolean castToPow2bits, boolean castTo32bits,
			boolean castNativePort, boolean castBoolToInt) {
		super(castToPow2bits, castTo32bits, castNativePort, castBoolToInt);
		irVisitor = new InnerTypeResizer();
	}

}
