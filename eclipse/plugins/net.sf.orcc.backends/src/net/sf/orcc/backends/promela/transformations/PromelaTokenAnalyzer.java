/*
 * Copyright (c) 2011, Åbo Akademi University
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
 *   * Neither the name of the Åbo Akademi University nor the names of its
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

package net.sf.orcc.backends.promela.transformations;

//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Edge;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.util.AbstractActorVisitor;

/**
 * This class generates information about the data/control tokens needed for the promela model.
 * The current version only sets the max number of tokens each port consumes/produces.
 *  
 * @author Johan Ersfolk
 * 
 */

public class PromelaTokenAnalyzer extends AbstractActorVisitor<Object>{
	
	//private Set<Port> schedulingPorts;
	
	//private NetworkStateDefExtractor netStateDef;
	
	//private Map<Var, Action> localVarToAction = new HashMap<Var, Action>();
	
	@Override
	public Object caseAction(Action action) {
		//for(Var var : action.getBody().getLocals()) {
		//	if (netStateDef.getVarsUsedInScheduling().contains(var)) {
		//		Set<Var> tc = new HashSet<Var>();
		//		netStateDef.getTransitiveClosure(var, tc);
		//		//System.out.println(var+ " -> " + tc);
		//		System.out.println("Action "+action.getName()+" contains var "+ var.getName());
		//		localVarToAction.put(var, action);
		//	}
		//}
		Pattern pattern = action.getOutputPattern();
		for (Port port : pattern.getPorts()) {
			//if (schedulingPorts.contains(port)) {
			//	System.out.println("Action "+action.getName()+" contributes to the output "+port.getName());
			//}
			int num_tokens = pattern.getVariable(port).getType().getDimensions().get(0);
			if (num_tokens > port.getNumTokensProduced()) {
				port.setNumTokensProduced(num_tokens);
			}
		}
		pattern = action.getInputPattern();
		for (Port port : pattern.getPorts()) {
			int num_tokens = pattern.getVariable(port).getType().getDimensions().get(0);
			if (num_tokens > port.getNumTokensConsumed()) {
				port.setNumTokensConsumed(num_tokens);
			}
		}
		return null;
	}

	@Override
	public Object caseActor(Actor actor) {
		//System.out.println("Actor: " + actor.getName());
		for (Action action : actor.getActions()) {
			doSwitch(action);
		}
		//for (Var var : actor.getStateVars()) {
			//Set<Var> tc = new HashSet<Var>();
			//netStateDef.getTransitiveClosure(var, tc);
			//System.out.println(var+ " " + tc);
		//}
		return null;
	}

	
	public PromelaTokenAnalyzer(NetworkStateDefExtractor netStateDef) {
		super();
		//this.netStateDef = netStateDef;
		//this.schedulingPorts = netStateDef.getPortsUsedInScheduling();
	}
	
}
