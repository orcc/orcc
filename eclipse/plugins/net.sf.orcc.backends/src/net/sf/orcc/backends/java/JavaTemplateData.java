/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.java;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

/**
 * This class computes a map for inserting cast information in some call
 * instructions arguments.
 * 
 * @author Antoine Lorence
 * 
 */
public class JavaTemplateData {

	/**
	 * List to save cast informations
	 */
	private Map<Arg, Type> castedListReferences;

	public JavaTemplateData(Actor actor) {
		castedListReferences = new HashMap<Arg, Type>();
		computeCastedListReferences(actor);
	}

	public Map<Arg, Type> getCastedListReferences() {
		return castedListReferences;
	}

	private void computeCastedListReferences(Actor actor) {
		TreeIterator<EObject> it = actor.eAllContents();
		while (it.hasNext()) {
			EObject object = it.next();

			// Working on CAL procedure calling except "print" instructions
			if (object instanceof InstCall
					&& !((InstCall) object).getProcedure().getName()
							.equals("print")) {
				InstCall call = (InstCall) object;

				EList<Arg> callArgs = call.getParameters();
				EList<Param> procParams = call.getProcedure().getParameters();

				if (callArgs.size() != procParams.size())
					System.out.println("Size error : " + callArgs.size()
							+ " given for " + procParams.size() + " needed.");
				else {
					int i;
					for (i = 0; i < callArgs.size(); ++i) {
						Type procParamType = procParams.get(i).getVariable()
								.getType();
						Type callParamType = callArgs.get(i).isByRef() ? ((ArgByRef) callArgs
								.get(i)).getUse().getVariable().getType()
								: ((ArgByVal) callArgs.get(i)).getValue()
										.getType();

						Expression e = ((ArgByVal) callArgs.get(i)).getValue();


						if (!callParamType.equals(procParamType)) {
							castedListReferences.put(callArgs.get(i),
									procParamType);
							break;
						}
					}
				}
			}
		}
	}
}