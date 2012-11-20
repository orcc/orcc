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
package net.sf.orcc.backends.llvm.tta.architecture.util;

import net.sf.orcc.df.Instance;
import net.sf.orcc.ir.InstCall;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class ArchitectureUtil {

	/**
	 * Return true if at least one actor of the given list is using the 'print'
	 * function.
	 * 
	 * @param actors
	 *            the given list of actors
	 * @return true if at least one actor of the given list is using the 'print'
	 *         function
	 */
	public static boolean needToPrint(EList<Instance> actors) {
		for (Instance instance : actors) {
			TreeIterator<Object> it = EcoreUtil.getAllContents(
					instance.getActor(), true);
			while (it.hasNext()) {
				Object object = it.next();
				if (object instanceof InstCall) {
					InstCall call = (InstCall) object;
					if (call.isPrint()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Return true if at least one actor of the given list is using a native
	 * function which are only usable in simulation.
	 * 
	 * @param actors
	 *            the given list of actors
	 * @return true if at least one actor of the given list is using a native
	 *         function which are only usable in simulation
	 */
	public static boolean needOrccFu(EList<Instance> actors) {
		for (Instance instance : actors) {
			TreeIterator<Object> it = EcoreUtil.getAllContents(
					instance.getActor(), true);
			while (it.hasNext()) {
				Object object = it.next();
				if (object instanceof InstCall) {
					InstCall call = (InstCall) object;
					if (!call.isPrint() && call.getProcedure().isNative()) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
