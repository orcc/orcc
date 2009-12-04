package net.sf.orcc.backends.c.quasistatic.scheduler.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.backends.c.quasistatic.scheduler.main.Scheduler;
import net.sf.orcc.backends.c.quasistatic.scheduler.parsers.InputXDFParser;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.Constants;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.Connection;

public class NetworkGraph{
	
	private HashMap<String,Graph<Action, DefaultEdge>> graphsMap;
	private Network network;
	private List<ActorGraph> scheduledActorsList;
	
	public NetworkGraph(Network network){
		this.network = network;
	}
	
	public void init() throws OrccException{
		graphsMap = new HashMap<String,Graph<Action, DefaultEdge>>();
		createScheduledActorsList();
	}
	
	private void createScheduledActorsList() throws OrccException{
		
		List<ActorGraph> actors = new ArrayList<ActorGraph>();
		InputXDFParser inputXDFParser = new InputXDFParser(Scheduler.workingDirectoryPath + File.separator + Constants.INPUT_FILE_NAME);
		List<String> schedulableActorsNamesList = inputXDFParser.parseSchedulableActorsList();
		for (Vertex vertex : network.getGraph().vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isActor()) {
					Actor actor = instance.getActor();
					ActorGraph actorGraph = new ActorGraph(actor);
					if(schedulableActorsNamesList.contains(actor.getName())){
						actors.add(actorGraph);
					}
				}
			}
		}
		
		scheduledActorsList = actors;
	}
	
	public void unrollActors() throws OrccException, QuasiStaticSchedulerException{
		for(ActorGraph actor : scheduledActorsList){
			actor.unrollFSM();
		}
	}
	
	public void updateTokensPattern() throws OrccException {
		InputXDFParser inputXDFParser = new InputXDFParser(
				Scheduler.workingDirectoryPath + File.separator
						+ Constants.INPUT_FILE_NAME);
		String btype = Switch.getBTYPE();
		HashMap<String, List<TokensPattern>> tokensPatternsMap = inputXDFParser
				.parseTokensPattern();
		List<TokensPattern> tokensPatternsList = tokensPatternsMap.get(btype);
		for (ActorGraph actor : scheduledActorsList) {
			for (TokensPattern tokensPattern : tokensPatternsList) {
				if (tokensPattern.getActorName().equals(actor.getName())) {
					//actor.updateTokensPattern(tokensPattern);
					tokensPatternsList.remove(tokensPattern);
					break;
				}
			}
		}
	}
	
	public List<ActorGraph> getScheduledActors() throws OrccException{
		if(scheduledActorsList == null){ 
			createScheduledActorsList();
		}
		return scheduledActorsList;
	}
	
	/**
	 * Creates a system level graph for the current btype value.
	 * 
	 * @return this system level graph.
	 * @throws OrccException
	 */
	public Graph<Action, DefaultEdge> createSystemLevelGraph() throws OrccException{
		//TODO: create a system-level graph once all the actors have been unrolled
		return new DirectedMultigraph<Action, DefaultEdge>(DefaultEdge.class);
		/*	Graph graph = new DirectedMultigraph<Action, DefaultEdge>(DefaultEdge.class);
		DSEInputFilesCreator dseInputFilesCreator = new DSEInputFilesCreator();
		
		//Inserts the actions/connections of each actor
		for(ActorGraph actor: scheduledActorsList){
			//Take the actor subgraph
			Graph<Action, DefaultEdge> subgraph = actor.getGraph(Switch.getBTYPE());
			subgraph.
			graph.addEdges(subgraph.edgeSet());
			graph.addVertices(subgraph.vertexSet());
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
					if(srcActor != null && tgtActor != null){
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
		graphsMap.put(Switch.getBTYPE(), graph);
		//Prints DSE scheduler input files
		dseInputFilesCreator.print();
		
		return graph;*/
	}
	
	
	public Set<Connection> getNetworkConnections(){
		return network.getGraph().edgeSet();
	}

	public ActorGraph getActorGraph(Actor actor){
		for(ActorGraph actorGraph: scheduledActorsList){
			if(actorGraph.contains(actor)){
				return actorGraph;
			}
		}
		return null;
	}

}
