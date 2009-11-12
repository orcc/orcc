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

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Switch;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.dse.DSEScheduler;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.FlowVertex;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.SDFEdge;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.SDFGraph;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.SDFVertex;

/**
 * Represents a set of utilities to perform a simulation's summary
 * 
 * @author Victor Martin
 */
public class OutputResultUtilities {

	private static HashMap<String, Integer> actionsIndexes;
	private static ArrayList<Connection> actionsConnections;
	private static Integer lastIndex;
	private static String BTYPE;
	private static ArrayList<String> actorsList;

	/**
	 * Create a summary using a SDFGraph
	 * 
	 * @param sysGraph
	 */
	public static void createSummary(SDFGraph sysGraph) {
		initSummary();

		// Adds all the actions
		for (SDFVertex vertex : sysGraph.vertexList()) {
			FlowVertex flowVertex = (FlowVertex) vertex;
			String actorId = flowVertex.getActorId();
			if (!actorsList.contains(actorId))
				actorsList.add(actorId);
			String vertexId = flowVertex.getVertexName();
			flowVertex.getActorId();
			addAction(vertexId);
		}

		// Adds all the actions
		for (SDFEdge edge : sysGraph.edgeList()) {
			String fromVertexId = ((FlowVertex) edge.getSource())
					.getVertexName();
			String toVertexId = ((FlowVertex) edge.getTarget()).getVertexName();
			addConnection(fromVertexId, toVertexId);
		}

		printSummary();
	}

	/**
	 * Init method
	 */
	private static void initSummary() {
		actionsIndexes = new HashMap<String, Integer>();
		actionsConnections = new ArrayList<Connection>();
		actorsList = new ArrayList<String>();
		lastIndex = 0;
		BTYPE = Switch.getInstance().getSwitchType();
	}

	/**
	 * Inserts a new connection between actions
	 * 
	 * @param fromAction
	 * @param toAction
	 */
	private static void addConnection(String fromAction, String toAction) {
		Integer from = actionsIndexes.get(fromAction);
		Integer to = actionsIndexes.get(toAction);
		actionsConnections.add(new Connection(from, to));
	}

	/**
	 * Includes a new action in the summary
	 * 
	 * @param actionName
	 */
	private static void addAction(String actionName) {
		if (actionsIndexes.containsKey(actionName))
			return;
		actionsIndexes.put(actionName, lastIndex);
		lastIndex++;
	}

	/**
	 * Print summary into the default output
	 */
	private static void printSummary() {
		try {

			File actionFile = new File(DSEScheduler.INPUT_FOLDER + "actors_"
					+ BTYPE + ".txt");
			actionFile.createNewFile();
			File connectionsFile = new File(DSEScheduler.INPUT_FOLDER
					+ "edges_" + BTYPE + ".txt");
			connectionsFile.createNewFile();

			BufferedWriter actionsBW = new BufferedWriter(new FileWriter(
					actionFile.getAbsolutePath()));
			BufferedWriter connectionsBW = new BufferedWriter(new FileWriter(
					connectionsFile.getAbsolutePath()));

			// prints indexes of each action
			for (String action : actionsIndexes.keySet()) {
				String actorId = action.split(": ")[0];
				String actionId = action.split(": ")[1];
				actionsBW.write(actionsIndexes.get(action) + " " + actorId
						+ " " + actionId + "\n");
			}
			actionsBW.close();

			int actorsCount = lastIndex;
			int edgesCount = actionsConnections.size();
			connectionsBW.write(actorsCount + " " + edgesCount + "\n");
			// prints connections
			for (int i = 0; i < edgesCount; i++) {
				connectionsBW
						.write(actionsConnections.get(i).toString() + "\n");
			}
			connectionsBW.close();

		} catch (IOException ex) {
			Logger.getLogger(OutputResultUtilities.class.getName()).log(
					Level.SEVERE, null, ex);
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
			Logger.getLogger(OutputResultUtilities.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/**
	 * Creates a file called <code>fileName</code> in the selected output
	 * directory, adding the content to that file.
	 */
	public static void createFile(String outputDirectory, String fileName,
			String content) {
		try {
			File file = new File(outputDirectory + fileName);
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
			writer.close();
		} catch (IOException ex) {
			Logger.getLogger(OutputResultUtilities.class.getName()).log(
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
			Logger.getLogger(OutputResultUtilities.class.getName()).log(
					Level.SEVERE, null, ex);
		} finally {
			try {
				mappingWriter.close();
			} catch (IOException ex) {
				Logger.getLogger(OutputResultUtilities.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * Represents a connection. Usually it represents a connection between
	 * actions
	 */
	static class Connection {
		Integer from;
		Integer to;

		/**
		 * Default Constructor
		 * 
		 * @param from
		 * @param to
		 */
		public Connection(int from, int to) {
			this.from = from;
			this.to = to;
		}

		/**
		 * Overwrite toString method
		 * 
		 * @return
		 */
		@Override
		public String toString() {
			return from + " " + to;
		}
	}

}
