/*
 * Copyright (c) 2010-2012, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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

import net.sf.orcc.backends.ir.InstTernary;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an extension of DeadVariableRemoval to remove variable
 * from specific instructions as well. TODO: Remove InstCast
 * 
 * @author Herve Yviquel
 * 
 */
public class DeadVariableRemoval extends
		net.sf.orcc.ir.transform.DeadVariableRemoval {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.orcc.ir.util.IrSwitch#defaultCase(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Void defaultCase(EObject object) {
		if (object instanceof InstTernary) {
			InstTernary ternaryOperation = (InstTernary) object;

			Var target = ternaryOperation.getTarget().getVariable();
			if (target != null && !target.isUsed()) {
				handleInstruction(target, ternaryOperation);
			}
			return null;
		}
		return super.defaultCase(object);
	}
}
