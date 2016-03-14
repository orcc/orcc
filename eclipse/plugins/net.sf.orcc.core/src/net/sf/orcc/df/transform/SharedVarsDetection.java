/*
 * Copyright (c) 2016, EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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

package net.sf.orcc.df.transform;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrccLogger;

public class SharedVarsDetection extends DfVisitor<Void> {

	Set<Var> sharedVars;

	@Override
	public Void caseNetwork(Network network) {
		sharedVars = new HashSet<Var>();
		for(Actor actor: network.getAllActors()){
			Set<Var> actorSharedVars = new HashSet<Var>();
			for(Var var : actor.getStateVars()){
				if (var.hasAttribute("shared")){
					String id = var.getAttribute("shared").getAttribute("id").getStringValue();
					var.setName(id);
					Type type = EcoreUtil.copy(var.getType());
					Var sharedVar = getNetworkSharedVar(id);
					if (sharedVar !=null){
						// -- Check if it has the same type
						Type sharedVarType = sharedVar.getType();
						if(!EcoreUtil.equals(type, sharedVarType)){
							OrccLogger.severeRaw("Shared variable with id: " + id +" has not the same type");
							throw new OrccRuntimeException("Shared variable with id: " + id +" has not the same type");
						}
					}else{
						sharedVar = IrFactory.eINSTANCE.createVar();
						sharedVar.setName(id);
						sharedVar.setType(type);
						sharedVars.add(sharedVar);
					}
					actorSharedVars.add(sharedVar);
				}
				actor.setAttribute("actor_shared_variables", actorSharedVars);
			}
		}
		
		if(!sharedVars.isEmpty()){
			network.setAttribute("network_shared_variables", sharedVars);
		}
		
		return null;
	}

	
	public Var getNetworkSharedVar(String id){
		
		for(Var var: sharedVars){
			if (var.getName().equals(id)){
				return var;
			}
		}
		
		return null;
	}
	
}
