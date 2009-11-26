package net.sf.orcc.backends.c.quasistatic.scheduler.model.util;

public class GraphEdge {
	
	private GraphVertex fromVertex;
	private GraphVertex toVertex;
	
	public GraphEdge(GraphVertex fromVertex, GraphVertex toVertex) {
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
	}
	
	public GraphVertex getFromVertex() {
		return fromVertex;
	}
	
	public void setFromVertex(GraphVertex fromVertex) {
		this.fromVertex = fromVertex;
	}
	
	public GraphVertex getToVertex() {
		return toVertex;
	}
	
	public void setToVertex(GraphVertex toVertex) {
		this.toVertex = toVertex;
	}
	
	public String toString(){
		return "(" + fromVertex.toString() + ") ---> (" + toVertex.toString() + ")";
	}
	
}
