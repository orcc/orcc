package net.sf.orcc.backends.c.quasistatic.scheduler.model.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.ir.Action;

public class Graph {
	
	List<GraphVertex> vertices;
	List<GraphEdge> edges;
	
	GraphVertex initialVertex;
	
	public Graph(GraphVertex initialVertex) {
		vertices = new ArrayList<GraphVertex>();
		edges = new ArrayList<GraphEdge>();
		this.initialVertex = initialVertex;
		
	}
	
	public Graph(){
		this(null);
	}
	
	public String getGraphName(){
		return initialVertex.toString();
	}
	
	public boolean addVertex(GraphVertex vertex){
		if(initialVertex == null){
			initialVertex = vertex;
		}
		return vertices.add(vertex);
	}
	
	public boolean addVertex(Action action){
		return addVertex(new GraphVertex(action));
	}
	
	public boolean addEdge(GraphEdge edge){
		return edges.add(edge);
	}
	
	public boolean addEdge(GraphVertex fromVertex, GraphVertex toVertex){
		return addEdge(new GraphEdge(fromVertex, toVertex));
	}
	
	public boolean addEdge(Action from, Action to) throws QuasiStaticSchedulerException{
		GraphVertex fromVertex = getVertex(from);
		GraphVertex toVertex = getVertex(to);
		return addEdge(fromVertex, toVertex);
	}
	
	public GraphVertex getVertex(Action action){
		for(int i = 0 ; i < vertices.size(); i++){
			GraphVertex vertex = vertices.get(i);
			if(vertex.containsAction(action)){
				return vertex;
			}
		}
		return null;
	}
	
	public String toString(){
		String str = "";
		for(GraphEdge edge: edges){
			str += edge.toString() + "\n";
		}
		return str;
	}
	
	public boolean addVertices(List<GraphVertex> vertices){
		if(vertices == null || vertices.size() == 0 ){
			return false;
		}
		
		if(initialVertex == null){
			initialVertex = vertices.get(0);
		}
		return this.vertices.addAll(vertices);
	}
	
	public boolean addEdges(List<GraphEdge> edges){
		if(edges == null || edges.size() == 0 ){
			return false;
		}
		
		return this.edges.addAll(edges);
	}

	public List<GraphVertex> getVertices() {
		return vertices;
	}

	public List<GraphEdge> getEdges() {
		return edges;
	}
	
}
