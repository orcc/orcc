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
package net.sf.orcc.tools.merger2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Action;
import net.sf.orcc.moc.AbstractMoC;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Vertex;

/**
 * This class defines a transformation that merges a region of static instances into a unique instance.
 * 
 * 
 * @author Jérôme Gorin
 * 
 */
public class MoCMerger {
	private class SuperMoC extends AbstractMoC {
		int sourceFactor;
		private MoC sourceMoC;
		int targetFactor;
		private MoC targetMoc;
		
		public SuperMoC(MoC sourceMoC, int sourceFactor, MoC targetMoc, int targetFactor){
			this.sourceMoC = sourceMoC;
			this.targetMoc = targetMoc;
			this.sourceFactor = sourceFactor;
			this.targetFactor = targetFactor;
		}
		
		public List<Action> getActions(){
			List<Action> actions = new ArrayList<Action> ();
			List<Action> sourceActions = getUnderneathActions(sourceMoC);
			List<Action> targetActions = getUnderneathActions(targetMoc);
			
			for (int i = 0 ; i < sourceFactor; i++){
				actions.addAll(sourceActions);
			}
			
			for (int i = 0 ; i < targetFactor; i++){
				actions.addAll(targetActions);
			}
			
			return actions;
		}
		
		private List<Action> getUnderneathActions(MoC moc){
			if (moc.isCSDF()){
				return ((CSDFMoC)moc).getActions();
			}
			
			return ((SuperMoC)moc).getActions();
		}
	}
	Map<Vertex, MoC> mocList;
	List<Vertex> region;
	
	StaticGraphAnalyzer staticGraph;
	
	public MoCMerger(StaticGraphAnalyzer staticGraph, List<Vertex> region){
		this.staticGraph = staticGraph;
		mocList = new HashMap<Vertex, MoC>();
		this.region = region;
		
		for (Vertex vertex : region){
			for (Vertex neighbor : staticGraph.getStaticNeighbors(vertex)){
				updateMoC(vertex, neighbor);
			}
		}
		
		SuperMoC moc = (SuperMoC) mocList.get(region.get(0));
		List<Action> actions = moc.getActions();
		
		
	
	}
	
	private MoC getMoC(Vertex vertex){
		if (mocList.containsKey(vertex)){
			//MoC is contained in MoC list
			return mocList.get(vertex);
		}
		
		//Vertex has not been visited yet, return its real MoC
		Instance instance = vertex.getInstance();
		return instance.getActor().getMoC();
	}
	
	private void updateMoC(Vertex source, Vertex target){
		int sourceRate = staticGraph.getSourceRate(source, target);
		int targetRate = staticGraph.getTargetRate(source, target);
		MoC sourceMoC = getMoC(source);
		MoC targetMoC = getMoC(target);
		SuperMoC superMoC = new SuperMoC(sourceMoC, sourceRate, targetMoC, targetRate);
		mocList.put(source, superMoC);
		mocList.put(target, superMoC);
	}
}
