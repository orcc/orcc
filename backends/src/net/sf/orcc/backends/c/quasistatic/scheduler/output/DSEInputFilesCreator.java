/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
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
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.sf.orcc.backends.c.quasistatic.scheduler.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;


import net.sf.orcc.backends.c.quasistatic.scheduler.dse.DSEScheduler;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.Switch;
import net.sf.orcc.ir.Action;

/**
 * 
 * @author Victor Martin
 */
public class DSEInputFilesCreator {
	
	private Graph<Action, DefaultEdge> systemLevelGraph;
	
	private HashMap<String, Integer> actionsIndexes;
	private ArrayList<String> actionsConnections;
	private int lastActionIndex;

	public DSEInputFilesCreator() {
		actionsConnections = new ArrayList<String>();
		actionsIndexes = new HashMap<String, Integer>();
		lastActionIndex = 0;
	}
	
	public void addNode(String actorId, String actionId) {
		String key = actorId + " " + actionId;
		if (actionsIndexes.containsKey(key))
			return;
		actionsIndexes.put(key, lastActionIndex);
		lastActionIndex++;
	}

	public void addNodes(String actorId, Collection<?>/*<GraphVertex>*/ vertices){
		/*for(GraphVertex vertex: vertices){
			String actionId = vertex.getVertexName();
			addNode(actorId, actionId);
		}*/
	}
	
	public void addConnection(String fromActorId, String fromActionId, String toActorId, String toActionId) {
		Integer fromIndex = actionsIndexes.get(fromActorId + " " + fromActionId);
		Integer toIndex = actionsIndexes.get(fromActorId + " " + fromActionId);
		
		if(fromIndex == null || toIndex == null) {
			return;
		}
		
		actionsConnections.add(fromIndex + " " + toIndex);
	}

	public void addConnections(String actorId, Collection<?>/*<GraphEdge>*/ edges){
		//addConnections(actorId, actorId, edges);
	}
	
	public void addConnections(String fromActorId, String toActorId, Collection<?>/*<GraphEdge>*/ edges){
		/*for(GraphEdge edge: edges){
			String fromActionId = edge.getFromVertex().getVertexName();
			String toActionId = edge.getToVertex().getVertexName();
			addConnection(fromActorId, fromActionId, toActorId, toActionId);
		}*/
	}
	
	public void print() {
		File actionFile = new File(DSEScheduler.INPUT_FOLDER + "actors_"
				+ Switch.getBTYPE() + ".txt");
		File connectionsFile = new File(DSEScheduler.INPUT_FOLDER + "edges_"
				+ Switch.getBTYPE() + ".txt");
		try {
			actionFile.createNewFile();
			connectionsFile.createNewFile();
			BufferedWriter actionsBW = new BufferedWriter(new FileWriter(
					actionFile.getAbsolutePath()));
			BufferedWriter connectionsBW = new BufferedWriter(new FileWriter(
					connectionsFile.getAbsolutePath()));
			
			// prints indexes of each action
			for (String actionId : actionsIndexes.keySet()) {
				actionsBW.write(actionsIndexes.get(actionId) + " " + actionId + "\n");
			}
			actionsBW.close();

			int actorsCount = lastActionIndex;
			int edgesCount = actionsConnections.size();
			connectionsBW.write(actorsCount + " " + edgesCount + "\n");
			
			// prints connections
			for (int i = 0; i < edgesCount; i++) {
				connectionsBW.write(actionsConnections.get(i) + "\n");
			}
			connectionsBW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates the delays.txt file which is an input of DSE Scheduler
	 */
	public static void createDelaysFile() {
		try {
			File delaysFile = new File(DSEScheduler.INPUT_FOLDER + "delays.txt");
			delaysFile.createNewFile();
			BufferedWriter delaysWriter = new BufferedWriter(new FileWriter(
					delaysFile));
			delaysWriter.write(0 + "\n");
			delaysWriter.close();
		} catch (IOException ex) {
			Logger.getLogger(DSEInputFilesCreator.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * Creates mapping file
	 */
	public static void createMappingFile(int noProcessors, int noClusters,
			HashMap<String, Integer> clusterMap) {
		BufferedWriter mappingWriter = null;
		try {
			File mappingFile = new File(DSEScheduler.INPUT_FOLDER
					+ "mapping.txt");
			mappingFile.createNewFile();
			mappingWriter = new BufferedWriter(new FileWriter(mappingFile));
			mappingWriter.write(clusterMap.size() + " " + noClusters + " "
					+ noProcessors + "\n");
			for (String actorName : clusterMap.keySet()) {
				int cluster = clusterMap.get(actorName);
				mappingWriter.write(actorName + " " + cluster + "\n");
			}
			mappingWriter.close();
		} catch (IOException ex) {
			Logger.getLogger(DSEInputFilesCreator.class.getName()).log(
					Level.SEVERE, null, ex);
		} finally {
			try {
				mappingWriter.close();
			} catch (IOException ex) {
				Logger.getLogger(DSEInputFilesCreator.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}
}
