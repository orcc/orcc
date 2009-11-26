package net.sf.orcc.backends.c.quasistatic.scheduler.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.util.Graph;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.util.GraphEdge;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.util.GraphVertex;
import net.sf.orcc.backends.c.quasistatic.scheduler.output.DSEInputFilesCreator;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Port;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.Connection;

public class NetworkGraph {
	
	private HashMap<String,Graph> graphsMap;
	private Network network;
	private List<ActorGraph> scheduledActors;
	
	public NetworkGraph(Network network){
		this.network = network;
	}
	
	public void reset() throws OrccException{
		graphsMap = new HashMap<String,Graph>();
		searchScheduledActors();
	}
	
	private void searchScheduledActors() throws OrccException{
		
		List<ActorGraph> actors = new ArrayList<ActorGraph>();
		
		for (Vertex vertex : network.getGraph().vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isActor()) {
					Actor actor = instance.getActor();
					ActorGraph actorGraph = new ActorGraph(actor);
					if(actorGraph.isStaticActor()){
						actors.add(actorGraph);
					}
				}
			}
		}
		
		scheduledActors = actors;
	}
	
	public void unrollStaticActors() throws OrccException, QuasiStaticSchedulerException{
		for(ActorGraph actor : scheduledActors){
			actor.unrollFSM();
		}
	}
	
	public List<ActorGraph> getScheduledActors() throws OrccException{
		if(scheduledActors == null){ 
			searchScheduledActors();
		}
		return scheduledActors;
	}
	
	/**
	 * Creates a system level graph for the current btype value.
	 * 
	 * @return this system level graph.
	 * @throws OrccException
	 */
	public Graph createSystemLevelGraph() throws OrccException{
		Graph graph = new Graph();
		DSEInputFilesCreator dseInputFilesCreator = new DSEInputFilesCreator();
		
		//Inserts the actions/connections of each actor
		for(ActorGraph actor: scheduledActors){
			Graph subgraph = actor.getGraph(Switch.btype);
			graph.addEdges(subgraph.getEdges());
			graph.addVertices(subgraph.getVertices());
			dseInputFilesCreator.addNodes(actor.getName(), subgraph.getVertices());
			dseInputFilesCreator.addConnections(actor.getName(), subgraph.getEdges());
		}
		
		//Inserts the connections between network's actors
		for(Connection connection: getNetworkConnections()){
			//Looks for the source and target actors
			Vertex srcVertex = network.getGraph().getEdgeSource(connection);
			Vertex tgtVertex = network.getGraph().getEdgeTarget(connection);
			Port srcPort = connection.getSource();
			Port tgtPort = connection.getTarget();
			if(srcVertex.isInstance() && tgtVertex.isInstance()){
				Instance srcInstance = srcVertex.getInstance();
				Instance tgtInstance = tgtVertex.getInstance();
				if(srcInstance.isActor() && tgtInstance.isActor()){
					ActorGraph srcActor = getActorGraph(srcInstance.getActor());
					ActorGraph tgtActor = getActorGraph(tgtInstance.getActor());
					if(srcActor != null && tgtActor != null && srcActor.isStaticActor() && tgtActor.isStaticActor()){
						//Looks for the implicated actions
						List<Action> srcActions = srcActor.getActionsWithPort(srcPort);
						List<Action> tgtActions = tgtActor.getActionsWithPort(tgtPort);
						List<GraphEdge> edges = new ArrayList<GraphEdge>();
						if(srcActions.size() == tgtActions.size()){
							for(int i = 0 ; i < srcActions.size() ; i ++){
								GraphVertex srcGraphVertex = graph.getVertex(srcActions.get(i));
								GraphVertex tgtGraphVertex = graph.getVertex(tgtActions.get(i));
								if(srcGraphVertex != null && tgtGraphVertex != null){
									edges.add(new GraphEdge(srcGraphVertex, tgtGraphVertex));
								}
							}
						}
						else{
							loop1:for(Action srcAction:srcActions){
								GraphVertex srcGraphVertex = graph.getVertex(srcAction);
								if(srcGraphVertex == null){ 
									continue loop1;
								}
								loop2:for(Action tgtAction: tgtActions){
									GraphVertex tgtGraphVertex = graph.getVertex(tgtAction);
									if(tgtGraphVertex == null){ 
										continue loop2;
									}
									edges.add(new GraphEdge(srcGraphVertex, tgtGraphVertex));
								}
							}
						}
						dseInputFilesCreator.addConnections(srcActor.getName(), tgtActor.getName(), edges);
						
					}
				}
			}
		}
		
		//Registers the new graph
		graphsMap.put(Switch.btype, graph);
		//Prints DSE scheduler input files
		dseInputFilesCreator.print();
		
		return graph;
	}
	
	
	public Set<Connection> getNetworkConnections(){
		return network.getGraph().edgeSet();
	}

	public ActorGraph getActorGraph(Actor actor){
		for(ActorGraph actorGraph: scheduledActors){
			if(actorGraph.contains(actor)){
				return actorGraph;
			}
		}
		return null;
	}
}
