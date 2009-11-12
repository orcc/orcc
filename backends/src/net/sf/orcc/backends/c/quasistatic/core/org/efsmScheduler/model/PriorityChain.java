/*
 * Copyright(c)2008, Jani Boutellier, Christophe Lucarz, Veeranjaneyulu Sadhanala 
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the EPFL and University of Oulu nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY  Jani Boutellier, Christophe Lucarz, 
 * Veeranjaneyulu Sadhanala ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Jani Boutellier, Christophe Lucarz, 
 * Veeranjaneyulu Sadhanala BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * This class represents a priority order among actions. This corresponds exactly to the
 * "Priority" element in CALML description of actors.
 * Eg: done > write_addr > read_addr
 * @author sadhanal
 *
 */

public class PriorityChain extends HashMap<String, Integer> {
	static final long serialVersionUID = 10000016L;
	public PriorityChain(){
		super();
	}
	/**
	 * checks whether an action is in a priority order or not.
	 * @param actionQID
	 * @return true if action is somewhere in the priority order, false otherwise.
	 */
	public boolean contains(String actionQID){
		return containsKey(actionQID);
	}
	/**
	 * checks whether a set of actions is in this priority order.
	 * @param keys
	 * @return true if all the elements in keys are present in the order, false otherwise.
	 */
	public boolean containsAll(Set<String> keys){
		boolean containsAllKeys = true;
		for(String key : keys){
			if(! contains(key)){
				containsAllKeys = false;
				break;
			}
		}
		return containsAllKeys;
	}
	/**
	 * from edges, this method gets the transition whose action has maximum priority
	 * @param edges
	 * @return Let A={a: a is an action corresponding to a transition in edges}.
	 * If for actions in A are present in this priority order, then let a = the action
	 * which has maximum priority in the order. a is returned.
	 * If there is an action in A, which is not present in this order, then a NullPointerException is thrown.
	 * 
	 */
	public Transition getTransWithMaxPr(Set<Transition> edges){
		int max = Integer.MAX_VALUE;
		Set<String> qids = new HashSet<String>();
		for(Transition edge : edges){
			Action action = edge.getAction();
			qids.add(action.getQID());
		}
		Transition trans = null;
		for(Transition edge : edges){
			String key =edge.getAction().getQID(); 
			int val = get(key);
			if(max > val){
				trans = edge;
				max = val;
			}
		}
		return trans;
	}
	
	public AbstractSubgraph getSubWithMaxPr(Set<AbstractSubgraph> subgraphs){
		int max = Integer.MAX_VALUE;
		Set<String> qids = new HashSet<String>();
		for(AbstractSubgraph sg : subgraphs){
			Transition edge = sg.getInitEdge();
			Action action = edge.getAction();
			qids.add(action.getQID());
		}
		AbstractSubgraph graph = null;
		for(AbstractSubgraph sg : subgraphs){
			Transition edge = sg.getInitEdge();
			String key =edge.getAction().getQID(); 
			int val = get(key);
			if(max > val){
				graph = sg;
				max = val;
			}
		}
		return graph;
	}
}
