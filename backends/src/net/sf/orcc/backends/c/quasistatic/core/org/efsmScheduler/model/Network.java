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
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.dse.DSEScheduler;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Constants;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.SDFScheduler;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Switch;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Util;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.XDFParsingException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.XNLParsingException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.main.Scheduler_Simulator;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.PropertiesParser;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.XDFParser;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.XNLParser;

/**
 * 
 * The Network class represents networks of actors, that is, decoder
 * configurations.
 * 
 */
public class Network {

	private String name;
	private String path;
	private Set<NetworkActor> actors;
	private Set<Connection> connections;
	private HashMap<String, NetworkActor> actorMap;
	public Set<Connection> localOutConn, localInConn;
	public Set<Network> subnets;
	// is removed from parent network
	// loose but it's ok
	private boolean isRemoved;
	private SDFGraph systemLevelGraph;
	private static HashMap<String, Integer> orderMap;
	private HashMap<String, String> initialVertexMap;

	public Network(String name) {
		this.name = name;// Scheduler_Simulator.getInstance().getNetworkName();
		actors = new HashSet<NetworkActor>();
		connections = new HashSet<Connection>();
		actorMap = new HashMap<String, NetworkActor>();
		localOutConn = new HashSet<Connection>();
		localInConn = new HashSet<Connection>();
		subnets = new HashSet<Network>();
		isRemoved = false;
		path = name;
	}

	public int getActorIndex(String actorName) {
		int index = 0;
		for (NetworkActor actor : actors) {
			if (actor.getName().equals(actorName)) {
				index++;
			}
		}
		return index;
	}

	public String getVisualActorName(String actorName) {
		for (NetworkActor actor : actors) {
			String shortActorName = PropertiesParser.getActorShortName(actor
					.getName(), actor.getLongName());
			if (shortActorName.equals(actorName))
				return actor.getName();
		}
		return "Actor not found";
	}

	public boolean addActor(NetworkActor actor) {
		int actorsIndex = numberOfOccurences(actor.getLongName());
		actorMap.put(actor.getNetworkPath() + "." + actor.getName(), actor);
		actor.setActorIndex(actorsIndex);
		return actors.add(actor);
	}

	public String getNetworkName() {
		return name;
	}

	public HashMap<String, String> getInitialVertexMap() {
		return initialVertexMap;
	}

	public void setInitialVertexMap(HashMap<String, String> initialVertexMap) {
		this.initialVertexMap = initialVertexMap;
	}

	public boolean isIsRemoved() {
		return isRemoved;
	}

	public void setIsRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public Set<Connection> getLocalInConn() {
		return localInConn;
	}

	public void setLocalInConn(Set<Connection> localInConn) {
		this.localInConn = localInConn;
	}

	public Set<Connection> getLocalOutConn() {
		return localOutConn;
	}

	public void setLocalOutConn(Set<Connection> localOutConn) {
		this.localOutConn = localOutConn;
	}

	public Set<Network> getSubnets() {
		return subnets;
	}

	public void setSubnets(Set<Network> subnets) {
		this.subnets = subnets;
	}

	public int numberOfOccurences(String actorLongName) {
		int n = 0;

		for (NetworkActor actor : actors) {
			if (actor.getLongName().equals(actorLongName)) {
				n++;
			}
		}

		return n;
	}

	public boolean addConnection(Connection conn) {
		if (conn == null) {
			return false;
		}
		// add ports to network actors
		// and change the actorNames of ports to longNames of actors
		// from actorRefs. like "address" to "MPEG4_mgnt_Address"
		Port fromPort = conn.getFrom();
		Port toPort = conn.getTo();
		String fromActorRef = fromPort.networkPath + "."
				+ fromPort.getActorName();
		if (fromPort.getActorName() != null
				&& !fromPort.getActorName().equals("")) {
			NetworkActor actor = getActorByRef(fromActorRef);
			// fromPort.setActorName(actor.getLongName());
			actor.addOutputPort(fromPort);
			actor.addOutConn(conn);
		}
		String toActorRef = toPort.getNetworkPath() + "."
				+ toPort.getActorName();
		if (toPort.getActorName() != null && !toPort.getActorName().equals("")) {
			NetworkActor actor = getActorByRef(toActorRef);
			// toPort.setActorName(actor.getLongName());
			actor.addInputPort(toPort);
			actor.addInConn(conn);
		}

		if (conn.getTo().isLocal()) {
			localOutConn.add(conn);
		} else if (conn.getFrom().isLocal()) {
			localInConn.add(conn);
		}

		return connections.add(conn);
	}

	public NetworkActor getActorByRef(String actorRef) {
		return actorMap.get(actorRef);
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setIsRemoved() {
		isRemoved = true;
	}

	public String toString() {
		String str;
		str = "\nNetwork Actors:\n";
		Iterator<NetworkActor> actorsIt = actors.iterator();
		while (actorsIt.hasNext()) {
			str += actorsIt.next().toString() + "\n";
		}
		str += "\n";
		str += "Network Connections:\n";
		Iterator<Connection> connectionsIt = connections.iterator();
		while (connectionsIt.hasNext()) {
			str += "\t" + connectionsIt.next().toString() + "\n";
		}
		str += "\n";
		return str;
	}

	public SDFGraph getSystemLevelGraph() {
		updateSystemLevelGraph();
		return systemLevelGraph;
	}

	public void createGraphFiles() {
		BufferedWriter actorsFileBufferedWriter = null, edgesFileBufferedWriter = null;
		try {
			String BTYPE = Switch.getInstance().getSwitchType();
			File actorsFile = new File(DSEScheduler.INPUT_FOLDER + "actors_"
					+ BTYPE + "2.txt");
			File edgessFile = new File(DSEScheduler.INPUT_FOLDER + "edges_"
					+ BTYPE + "2.txt");
			actorsFile.createNewFile();
			edgessFile.createNewFile();
			actorsFileBufferedWriter = new BufferedWriter(new FileWriter(
					actorsFile));
			edgesFileBufferedWriter = new BufferedWriter(new FileWriter(
					edgessFile));
			for (SDFVertex v : systemLevelGraph.vertexList()) {
				FlowVertex vertex = (FlowVertex) v;
				String actionId = vertex.getId();
				String actorId = vertex.getActorId();
				String actionName = vertex.getActionName();
				actorsFileBufferedWriter.write(actionId + " " + actorId + " "
						+ actionName + "\n");
			}
			for (SDFEdge edge : systemLevelGraph.edgeList()) {
				FlowVertex source = (FlowVertex) edge.getSource();
				FlowVertex target = (FlowVertex) edge.getTarget();
				String idSouce = source.getId();
				String idTarget = target.getId();
				edgesFileBufferedWriter.write(idSouce + " " + idTarget + "\n");
			}
		} catch (IOException ex) {
			Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null,
					ex);
		} finally {
			try {
				actorsFileBufferedWriter.close();
				edgesFileBufferedWriter.close();
			} catch (IOException ex) {
				Logger.getLogger(Network.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}

	/**
	 * Selects subgraphs from actors depending on <code>btype</code> and
	 * combines them into one system level graph.
	 * 
	 * @return system level graph obtained after combining subgraphs of actors.
	 */
	public void updateSystemLevelGraph() {
		Set<AbstractSubgraph> subgraphs;
		// actorRef to subgraph map
		// System.out.println("Sub-graphs of each actor:\n");
		HashMap<String, AbstractSubgraph> actorToSubgraphMap;
		subgraphs = new HashSet<AbstractSubgraph>();
		actorToSubgraphMap = new HashMap<String, AbstractSubgraph>();
		for (NetworkActor actor : actors) {
			AbstractSubgraph g;
			// System.out.println("Actor " + actor.getName() + " ( " +
			// actor.getKind() + " ):");
			g = actor.getSubgraph();

			if (g == null) {
				//String switchName = Switch.getInstance().getSwitchName();
				// System.out.println("\tThe Actor " + actor.getName() +
				// " has no sub-graph for " + (switchName == null? "BTYPE":
				// switchName) + "= " +
				// Integer.toBinaryString(Switch.getInstance().getToken("")));
			} else {
				// System.out.println("\tAdding sub-graph " + actor.getName() +
				// ". Initial state:" + g.getInitActionQID());
				subgraphs.add(g);
				actorToSubgraphMap.put(actor.getNetworkPath() + "."
						+ actor.getName(), g);
			}
		}

		systemLevelGraph = getSystemLevelGraphFromSubGraphs(subgraphs);

		// interconnect the subgraphs
		// System.out.println("\nInterconnect sub-graphs:\n");
		// System.out.println("Connections:");
		for (Connection conn : connections) {
			// System.out.println("\t" + conn.toString());

			// Gets the two subgraphs which are connected by this connection
			Port from = conn.getFrom();
			Port to = conn.getTo();
			AbstractSubgraph fromSubgraph = actorToSubgraphMap.get(from
					.getNetworkPath()
					+ "." + from.getActorName());
			AbstractSubgraph toSubgraph = actorToSubgraphMap.get(to
					.getNetworkPath()
					+ "." + to.getActorName());
			// Excludes wrong connections
			if (fromSubgraph == null || toSubgraph == null) {
				continue;
			}

			// Gets the two port references which are connected by this
			// connection
			String fromPortRef = from.getRef();
			String toPortRef = to.getRef();
			// containing local ports also
			if (!(fromSubgraph.hasOutputPort(fromPortRef) && toSubgraph
					.hasInputPort(toPortRef))) {
				// System.out.println("No data flow");
				continue;
			}
			// Gets the two actors which are connected by this connection
			NetworkActor fromActor = getActor(from.getNetworkPath(), from
					.getActorName());
			NetworkActor toActor = getActor(to.getNetworkPath(), to
					.getActorName());
			// Gets the two ports which are connected by this connection
			Port fromPort = fromActor.getOutputPort(fromPortRef);
			Port toPort = toActor.getInputPort(toPortRef);
			// Gets the number of tokens in this ports
			int numTokensFromPi = fromPort.getRemTokens();
			int numTokensToPi = toPort.getRemTokens();
			// Creates interfaces
			PortInterface frompi = new PortInterface(conn.getFrom().getRef(),
					numTokensFromPi, fromPort.getConsumptionTokens());
			PortInterface topi = new PortInterface(conn.getTo().getRef(),
					numTokensToPi, toPort.getConsumptionTokens());
			int numTokens = Math.min(fromPort.getConsumptionTokens(), toPort
					.getConsumptionTokens());
			loop: while (true) {
				FlowVertex fromVertex = fromSubgraph
						.getVertexWithOutPortInterface(frompi, numTokens);
				FlowVertex toVertex = toSubgraph.getVertexWithInPortInterface(
						topi, numTokens);

				if (fromVertex == null || toVertex == null) {
					break loop;
				}
				String initialVertexFrom = initialVertexMap.get(fromVertex
						.getId());
				String initialVertexTo = initialVertexMap.get(toVertex.getId());
				int orderFrom = orderMap.get(initialVertexFrom);
				int orderTo = orderMap.get(initialVertexTo);
				if (orderFrom > orderTo) {
					orderMap.put(initialVertexFrom, orderTo);
					orderMap.put(initialVertexTo, orderFrom);
				}

				if (systemLevelGraph.containsVertex(fromVertex)
						&& systemLevelGraph.containsVertex(toVertex)
						&& !containsEdge(fromVertex, toVertex)) {
					systemLevelGraph.addEdge(fromVertex, toVertex);
				}
			}
		}
		// System.out.println();
	}

	private boolean containsEdge(FlowVertex fromVertex, FlowVertex toVertex) {
		for (SDFEdge edge : systemLevelGraph.edgeList()) {
			if (edge.getSource().equals(fromVertex)
					&& edge.getTarget().equals(toVertex)) {
				return true;
			}
		}
		return false;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 
	 * @param name
	 *            actor´s name
	 * @return Network actor
	 */
	private NetworkActor getActor(String subnetworkName, String actorName) {
		for (NetworkActor actor : actors) {
			if (actor.getName().equals(actorName)
					&& actor.getNetworkPath().equals(subnetworkName)) {
				return actor;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return Map which contents the order in which actors must me displayed
	 */
	public static HashMap<String, Integer> getOrderMap() {
		return orderMap;
	}

	/**
	 * Sets order map
	 * 
	 * @param orderMap
	 */
	public void setOrderMap(HashMap<String, Integer> orderMap) {
		Network.orderMap = orderMap;
	}

	/**
	 * This method joins sub-graphs in only one graph
	 * 
	 * @param subgraphs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private SDFGraph getSystemLevelGraphFromSubGraphs(
			Set<AbstractSubgraph> subgraphs) {
		orderMap = new HashMap<String, Integer>();
		// The system Level graph
		systemLevelGraph = new SDFGraph();
		ArrayList<SDFVertex> sourceVertices = new ArrayList<SDFVertex>();

		// Take all the subgraphs.
		for (AbstractSubgraph g : subgraphs) {
			ArrayList<SDFVertex> sV = getSourceVertices(g);
			sourceVertices = Util.concatArrayList(sV, sourceVertices);
			addSubgraphToSystemLevelGraph(g);
		}
		// Init order map
		for (int i = 0; i < sourceVertices.size(); i++) {
			FlowVertex vertex = (FlowVertex) sourceVertices.get(i);
			orderMap.put(vertex.getId(), i);
		}
		// Sets initialVerticesMap
		initialVertexMap = new HashMap<String, String>();
		String actualInitialVertex = null;
		for (SDFVertex vertex : systemLevelGraph.vertexList()) {
			String vertexId = ((FlowVertex) vertex).getId();
			if (sourceVertices.contains(vertex)) {
				actualInitialVertex = vertexId;
			}
			initialVertexMap.put(vertexId, actualInitialVertex);
		}

		return systemLevelGraph;
	}

	private void addSubgraphToSystemLevelGraph(SDFGraph subgraph) {
		for (SDFVertex v : subgraph.vertexList()) {
			systemLevelGraph.addVertex(v);
		}
		for (SDFEdge edge : subgraph.edgeList()) {
			systemLevelGraph.addEdge(edge.getSource(), edge.getTarget());
		}
	}

	/**
	 * Update arrayList updateVertex
	 * 
	 * @param graph
	 */
	private static ArrayList<SDFVertex> getSourceVertices(SDFGraph graph) {
		ArrayList<SDFVertex> sourceVertices = new ArrayList<SDFVertex>();

		for (SDFVertex vertex : graph.vertexList()) {
			if (vertex != null && graph.inDegreeOf(vertex) == 0) {
				sourceVertices.add(vertex);
			}
		}

		return sourceVertices;
	}

	/**
	 * Update arrayList sinkVertices
	 * 
	 * @param graph
	 */
	public static ArrayList<SDFVertex> getSinkVertices(SDFGraph graph) {
		ArrayList<SDFVertex> sinkVertices = new ArrayList<SDFVertex>();

		for (SDFVertex vertex : graph.vertexList()) {
			if (vertex != null && graph.outDegreeOf(vertex) == 0) {
				sinkVertices.add(vertex);
			}
		}

		return sinkVertices;
	}

	public Set<NetworkActor> getActors() {
		return actors;
	}

	public Set<Connection> getConnections() {
		return connections;
	}

	public void parseCalml() {
		Set<NetworkActor> xnlActors = new HashSet<NetworkActor>(getActors());
		for (NetworkActor actor : xnlActors) {
			String actorName = actor.getName();
			String actorPath = actor.getNetworkPath();
			System.out.println("\tParsing actor " + actorName);
			if (actorName.equals("parser")
					|| actorName.equals("GEN_mgnt_Merger420")
					|| actorName.equals("serialize")) {
				actor.setIsConfigActor();
				continue;
			}
			try {
				actor.parseCalml();
			} catch (FileNotFoundException fnfe) {
				Scheduler_Simulator sim = Scheduler_Simulator.getInstance();
				boolean isNetworkFileNL = sim.getKindOfTopModelFile().equals(
						Constants.NL_FILE);
				// might be a network ; like idct2d
				String networkDirectoryPath = isNetworkFileNL ? sim
						.getXNLDirectory().getAbsolutePath() : sim
						.getXDFDirectory().getAbsolutePath();
				String networkFileName = networkDirectoryPath + File.separator
						+ actor.getLongName()
						+ (isNetworkFileNL ? ".xnl" : ".xdf");
				Network network;
				try {
					String networkPath = actorPath + "." + actorName;
					network = isNetworkFileNL ? XNLParser.parse(networkPath,
							networkFileName) : XDFParser.parse(networkPath,
							networkFileName);
					network.setName(actor.getName());
					network.setPath(networkPath);
					network.parseCalml();
					actor.setNetwork(network);
					this.addSubnet(network);
				} catch (XNLParsingException e) {
					e.printStackTrace();
				} catch (XDFParsingException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.err.println("IOException due to unknown reason");
				e.printStackTrace();
			}
		}
		flattenSubnets();
	}

	/**
	 * Separates actions of the actors in this network
	 */
	public void separateActors() {
		// First, sets the actors who aren't networks
		for (NetworkActor actor : getActors()) {
			actor.adjustKind();
		}

		// Last, sets the actors who are networks
		/*
		 * for (NetworkActor actor : getNetworkActors()) { actor.setKind(); }
		 */
	}

	/**
	 * Separates actions of the actors in this network
	 */
	public void setTokensPatterns() {

		for (NetworkActor actor : getActors()) {
			actor.setTokenPatterns();
		}
	}

	public Set<NetworkActor> getNetworkActors() {
		HashSet<NetworkActor> networkActors = new HashSet<NetworkActor>();
		for (NetworkActor actor : getActors()) {
			if (actor.getNetwork() != null) {
				networkActors.add(actor);
			}
		}
		return networkActors;
	}

	public void printActors() {
		for (NetworkActor actor : getActors()) {
			System.err.println(actor.toString());
		}
	}

	/**
	 * Separates actions of the actors in this network
	 */
	public void printKindOfActors() {
		System.out.println("Actors' kind");

		for (NetworkActor actor : getActors()) {
			String actorName = actor.getLongName();
			String kind = actor.getKind();
			System.out.println("\t" + actorName + ": " + kind);
		}
	}

	public void addSubnet(Network N) {
		subnets.add(N);
	}

	public HashMap<String, Integer> getActorPriorityMap() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		ArrayList<String> sortedRActors = SDFScheduler.getSortedActors();
		for (NetworkActor actor : getActors()) {
			String actorName = actor.getLongName();
			String kind = actor.getKind();
			int priority;
			if (kind.equals(Constants.ND_ACTOR)) {
				priority = -2;
			} else if (kind.equals(Constants.BORDERLINE_ACTOR)) {
				priority = -1;
			} else {
				priority = sortedRActors.indexOf(actorName);
			}
			map.put(actorName, priority);
		}
		return map;
	}

	private void flattenSubnets() {

		// Add actors and connections of the subnets
		// System.out.println("\t\t----- Adding subnets to network " + name +
		// "-----");
		for (Network N : subnets) {
			for (NetworkActor actor : N.getActors()) {
				this.addActor(actor);
			}
			for (Connection conn : N.getConnections()) {
				this.addConnection(conn);
			}
		}
		// Flattening subnets
		// System.out.println("\t\t----- Flattening subnets -----");
		for (Network N : subnets) {
			// System.out.println("Subnet name: " + N.name);
			NetworkActor subnetActor = getActorByRef(path + "." + N.name);
			// System.out.println("Processing output connections:");
			for (Connection localConn : N.localOutConn) {
				Port localPort = localConn.getTo();
				if (subnetActor == null)
					System.err.println();
				Set<Connection> secondConns = subnetActor
						.getConnFromPort(localPort);

				for (Connection secondConn : secondConns) {
					Port finalTarget = secondConn.getTo();
					NetworkActor a = getActorByRef(finalTarget.getNetworkPath()
							+ "." + finalTarget.getActorName());
					// System.out.println("\tNetwork actor: " + a);
					if (a != null && a.isCompound()
							&& a.getNetwork().isRemoved()) {
					} else {
						Connection newConn = new Connection(
								localConn.getFrom(), finalTarget);
						addConnection(newConn);
						// System.out.println("\tConnection added: " +
						// newConn.toString());
					}

				}
			}
			// same for In connections
			// System.out.println("Processing input connections:");
			for (Connection localConn : N.localInConn) {
				Port localPort = localConn.getFrom();
				Set<Connection> secondConns = subnetActor
						.getConnToPort(localPort);
				for (Connection secondConn : secondConns) {
					Port finalSource = secondConn.getFrom();
					NetworkActor a = getActorByRef(finalSource.getNetworkPath()
							+ "." + finalSource.getActorName());
					// System.out.println("\tNetwork actor: " + a);
					if (a != null && a.isCompound()
							&& a.getNetwork().isRemoved()) {
						// nothing
					} else {
						Connection newConn = new Connection(finalSource,
								localConn.getTo());
						addConnection(newConn);
						// System.out.println("\tConnection added: " +
						// newConn.toString());
					}
				}
			}
			// System.out.println("---- Removing local connections -----");
			// remove local connections
			// System.out.println(N.localInConn);
			// System.out.println(N.localOutConn);

			connections.removeAll(N.localInConn);
			connections.removeAll(N.localOutConn);
			// System.out.println("---- Removing local connections: Done -----");

			// and connections between subnet actor and the network
			// System.out.println("---- Removing connections between subnet actor and the network -----");
			connections.removeAll(subnetActor.getInConn());
			connections.removeAll(subnetActor.getOutConn());
			// System.out.println("---- Removing connections between subnet actor and the network : Done-----");
			N.setIsRemoved();

		}
	}

	/**
	 * unrolls efsms of the actors in this network
	 */
	public void unrollActors() {
		for (NetworkActor actor : getActors()) {
			System.out.println("Unrolling " + actor.getName() );
			actor.unroll();
		}
	}

	public static void displaySDFGraph(SDFGraph sysGraph) {
		/*
		 * SDFApplet applet = new SDFApplet(sysGraph); applet.init();
		 * applet.displayGraph();
		 */
	}

	/**
	 * Prints the topology matrix (one edge per row) to System.out
	 */
	public static void printTopoMatrix(SDFGraph graph) {
		/*
		 * double[][] mat = graph.getTopologyMatrix(); for (int i = 0; i <
		 * mat.length; i++) { for (int j = 0; j < mat[i].length; j++) { if
		 * (mat[i][j] != 0) { System.out.print(mat[i][j] + "  "); } else {
		 * System.out.print("   " + "  "); } } System.out.println(); }
		 * System.out.println();
		 */
	}

	public static void visitSDFGraph(SDFGraph sysGraph) {
		/*
		 * System.out.println("\nVisiting sysGraph \n"); TopologyVisitor topo =
		 * new TopologyVisitor(); topo.visit(sysGraph);
		 */
		System.err
				.println("Warning!! Network: Visit method not implemented yet!");
	}
}
