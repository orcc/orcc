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
package net.sf.orcc.backends.c.quasistatic.scheduler.unrollers;

import java.util.ArrayList;
import java.util.List;
import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.RunTimeEvaluator;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.TokensPattern;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.util.Graph;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.FSM.Transition;

public class FSMUnroller implements AbstractFSMUnroller {

	private FSM fsm;
	private TokensPattern tokensPattern;
	
	public FSMUnroller(FSM fsm, TokensPattern tokensPattern){
		this.fsm = fsm;
		this.tokensPattern = tokensPattern;
	}
	
	/**
	 * Unrolls the FSM, generating a set of SDF graphs
	 * 
	 * @return the list of SDF graphs
	 */
	public List<Graph> unroll() throws QuasiStaticSchedulerException {
		List<Graph> actorSubgraphs = new ArrayList<Graph>();
        List<NextStateInfo> nextStateInfos = getTransitionsFrom(fsm.getInitialState());
		
        //Generates a subgraph for each initial transition
		for(NextStateInfo nextStateInfo: nextStateInfos ){
			tokensPattern.restoreTokenPattern();
			Graph actorSg = unrollSubgraph(nextStateInfo);
            actorSubgraphs.add(actorSg);
		}
		
		return actorSubgraphs;
	}
	
	private List<NextStateInfo> getTransitionsFrom(State state){
		List<Transition> transitions = fsm.getTransitions();
		List<NextStateInfo> transitionsFromState = new ArrayList<NextStateInfo>();
		for(Transition transition: transitions){
			if(transition.getSourceState().compareTo(state) == 0){
				transitionsFromState.addAll(transition.getNextStateInfo());
			}
		}
		
		return transitionsFromState;
	}
	
	/**
     * unrolls the subgraph originating from initEdge
     *
     * @param initEdge
     * @return the unrolled subgraph
	 * @throws QuasiStaticSchedulerException 
     */
    private Graph unrollSubgraph(NextStateInfo nSInfo) throws QuasiStaticSchedulerException {
    	
    	int iterations = 0;
    	Graph graph = new Graph();
    	State nextState; 
    	Action prevAct = null, actAct= null;
    	String initAction = nSInfo.getAction().toString();
    	System.out.println("Subgraph - init action: " + initAction);
		do{
			nextState = nSInfo.getTargetState();
			actAct = nSInfo.getAction();
			graph.addVertex(actAct);
			if(prevAct != null){
				graph.addEdge(prevAct, actAct);
			}
			nSInfo = RunTimeEvaluator.nextTransition(getTransitionsFrom(nextState), tokensPattern);
			if(nSInfo == null){
				System.out.println("Invalid graph");
				break;
			}
			prevAct = actAct;
			iterations++;	
		}while(!nextState.equals(fsm.getInitialState()) && iterations < 20);
    	
		System.out.println(graph.toString());
		
    	return graph;
    	
    }
    
    
}
