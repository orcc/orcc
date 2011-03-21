/*********************************************************
Copyright or © or Copr. IETR/INSA: Maxime Pelcat

Contact mpelcat for more information:
mpelcat@insa-rennes.fr

This software is a computer program whose purpose is to execute
parallel applications.

 *********************************************************/
 
#ifndef HDAG_GRAPH
#define HDAG_GRAPH

#include "HDAGVertex.h"
#include "HDAGEdge.h"

#include "SchedulerDimensions.h"
#include "SchedulingError.h"

/**
 * A HDAG graph. It contains HDAG vertices and edges. It has a bigger table for vertices and edges than DAG.
 * Each edge production and consumption must be equal. There is no repetition vector for the vertices.
 * 
 * @author mpelcat
 */

class HDAGGraph {

	private :
		/**
		 number of HDAG vertices
		*/
		int nbVertices;

		/**
		 table of HDAG vertices
		*/
		HDAGVertex* vertices[MAX_HDAG_VERTICES];

		/**
		 number of HDAG edges
		*/
		int nbEdges;

		/**
		 table of HDAG edges
		*/
		HDAGEdge* edges[MAX_HDAG_EDGES];

		/**
		 Precomputes the successor vertices of a given vertex and stores their pointers in it

		 @param vertex: the current vertex
		*/
		void precomputeSuccessors(HDAGVertex* vertex);

		/**
		 The graph contains one starting point: the first vertex
		*/
		HDAGVertex* firstVertex;
	public : 
		/**
		 Constructor
		*/
		HDAGGraph();

		/**
		 Destructor
		*/
		~HDAGGraph();

		/**
		 Adding a vertex to the graph. 
		 
		 @param the vertex name
		 @return the new vertex
		*/
		HDAGVertex* addVertex(char* name);
		void addVertex(HDAGVertex* vertex);

		/**
		 Adding a vertex to the graph that will correspond to the first actor
		 
		 @param the vertex name
		 @return the new vertex
		*/
		HDAGVertex* addFirstVertex(char* name);

		/**
		 Adding an edge to the graph. Vertices and edges must be added in topological order.
		 There is no initial token on edges
		 
		 @param source: The source vertex of the edge
		 @param tokenRate: number of tokens (chars) produced by the source and consumed by the sink
		 @param sink: The sink vertex of the edge
		 @return the created edge
		*/
		HDAGEdge* addEdge(HDAGVertex* source, int tokenRate, HDAGVertex* sink);
		void addEdge(HDAGVertex* source, HDAGVertex* sink, HDAGEdge* edge);

		/**
		 Removes the given vertex
		*/
		bool removeVertex(HDAGVertex* vertex);

		/**
		 Removes the last added edge
		*/
		void removeLastEdge();

		/**
		 Removes all the edges in this graph that are also contained in the specified edge array.
		*/
		bool removeAllEdges(HDAGEdge** edges, int nbRemEdges);

		/**
		 Removes an edge in the graph. refreshEdges() must be called if the edge is found.
		*/
		bool removeEdge(HDAGEdge* edge);

		/**
		Refresh edges of the graph. This method MUST be called when edge are changed in the graph.
		*/
		void refreshEdges();

		/**
		 Returns the target vertex of an edge.
		*/
		HDAGVertex* getEdgeTarget(HDAGEdge* edge);

		/**
		 Returns the source vertex of an edge.
		*/
		HDAGVertex* getEdgeSource(HDAGEdge* edge);

		/**
		 Removes all edges and vertices
		*/
		void flush();

		/**
		 Gets the actor at the given index
		 
		 @param index: index of the actor in the actor list
		 @return actor
		*/
		HDAGVertex* getVertex(int index);

		/**
		 Gets the hdag vertices that share the given DAG reference
		 
		 @param ref: the DAG reference
		 @param output: the output HDAG vertices
		 @return the number of found references
		*/
		int getVerticesFromCSDAGReference(CSDAGVertex* ref, HDAGVertex** output);

		/**
		 Gets the index of the given actor
		 
		 @param vertex: actor vertex
		 @return index of the actor in the actor list
		*/
		int getVertexIndex(HDAGVertex* vertex);

		/**
		 Gets the index of the given edge
		 
		 @param edge: edge
		 @return index of the edge in the edge list
		*/
		int getEdgeIndex(HDAGEdge* edge);

		/**
		 Gets the actor number
		 
		 @return number of vertices
		*/
		int getNbVertices();

		/**
		 Gets the edge at the given index
		 
		 @param index: index of the edge in the edge list
		 @return edge
		*/
		HDAGEdge* getEdge(int index);

		/**
		 Gets the edge number
		 
		 @return number of edges
		*/
		int getNbEdges();

		/**
		 Gets the input edges of a given vertex. Careful!! Slow!
		 
		 @param vertex: input vertex
		 @param output: table to store the edges
		 @return the number of input edges
		*/
		int getInputEdges(HDAGVertex* vertex, HDAGEdge** output);

		/**
		 Gets the output edges of a given vertex. Careful!! Slow!
		 
		 @param vertex: input vertex
		 @param output: table to store the edges
		 @return the number of output edges
		*/
		int getOutputEdges(HDAGVertex* vertex, HDAGEdge** output);

		/**
		 Gets pointers on the edges of the graph in the order of their source or sink.
		 Accelerates getting the input or output edges
		 
		 @param sourceOrSink: 1 for sorting in source order, 0 for sink order
		*/
		void sortEdges(int startIndex);

		/**
		 Precomputes the successor vertices of all vertices to speed up the access
		*/
		void precomputeSuccessors();

		/**
		 Getting the vertex corresponding to the first actor
		 
		 @return the first vertex
		*/
		HDAGVertex* getFirstVertex();
};

/**
 Gets the actor at the given index
 
 @param index: index of the actor in the actor list
 @return actor
*/
INLINE
HDAGVertex* HDAGGraph::getVertex(int index){
	return vertices[index];
}

/**
 Gets the index of the given actor
 
 @param vertex: actor vertex
 @return index of the actor in the actor list
*/
INLINE
int HDAGGraph::getVertexIndex(HDAGVertex* vertex){
	int index;
	for(index=0;index<nbVertices;index++){
		if(vertex == vertices[index]){
			return index;
		}
	}
	return -1;
}

/**
 Gets the index of the given edge
 
 @param edge: edge
 @return index of the edge in the edge list
*/
INLINE
int HDAGGraph::getEdgeIndex(HDAGEdge* edge){
	int index;
	for(index=0;index<nbEdges;index++){
		if(edge == edges[index]){
			return index;
		}
	}
	return -1;
}

/**
 Gets the actor number
 
 @return number of vertices
*/
INLINE
int HDAGGraph::getNbVertices(){
	return nbVertices;
}

/**
 Gets the edge at the given index
 
 @param index: index of the edge in the edge list
 @return edge
*/
INLINE
HDAGEdge* HDAGGraph::getEdge(int index){
	return edges[index];
}

/**
 Gets the edge number
 
 @return number of edges
*/
INLINE
int HDAGGraph::getNbEdges(){
	return nbEdges;
}

/**
 Gets the hdag vertices that share the given DAG reference
 
 @param ref: the DAG reference
 @param output: the output HDAG vertices
 @return the number of found references
*/
INLINE
int HDAGGraph::getVerticesFromCSDAGReference(CSDAGVertex* RESTRICT ref, HDAGVertex** RESTRICT output){
	int size = 0;
	for(int i=0; i<nbVertices; i++){
		HDAGVertex* vertex = vertices[i];
		if(vertex->getCsDagReference() == ref){
			output[size] = vertex;
			size++;
		}
	}
	return size;
}

/**
 Gets the input edges of a given vertex. Careful!! Slow!
 
 @param vertex: input vertex
 @param output: table to store the edges
 @return the number of input edges
*/
INLINE
int HDAGGraph::getInputEdges(HDAGVertex* vertex, HDAGEdge** output){
	int nbEdges = 0;
	for(int i=0; i<this->nbEdges; i++){
		HDAGEdge* edge = edges[i];
		HDAGVertex* sink = edge->getSink();
		if(sink->equals(vertex)){
			output[nbEdges] = edge;
			nbEdges++;
		}
	}
	return nbEdges;
}

/**
 Gets the output edges of a given vertex. Careful!! Slow!
 
 @param vertex: input vertex
 @param output: table to store the edges
 @return the number of output edges
*/
INLINE
int HDAGGraph::getOutputEdges(HDAGVertex* RESTRICT vertex, HDAGEdge** RESTRICT output){
	int nbEdges = 0;
	for(int i=0; i<this->nbEdges; i++){
		HDAGEdge* edge = edges[i];
		HDAGVertex* source = edge->getSource();
		if(source->equals(vertex)){
			output[nbEdges] = edge;
			nbEdges++;
		}
	}
	return nbEdges;
}

/**
 Adding a vertex to the graph
 
 @param vertexName: the name of the new vertex

 @param name: the vertex name
 @return the new vertex
*/
INLINE
HDAGVertex* HDAGGraph::addVertex(char* name){
	HDAGVertex* vertex;
#ifdef _DEBUG
	if(nbVertices >= MAX_HDAG_VERTICES){
		// Adding a vertex while the graph is already full
		exitWithCode(1000);
	}
#endif
	vertex = vertices[nbVertices];
	vertex->setBase(this);
	vertex->setName(name);
	nbVertices++;
	return vertex;
}


/**
 Adding the current vertex into the graph
 
 @param vertex: vertex to add

*/
INLINE
void HDAGGraph::addVertex(HDAGVertex* vertex){
#ifdef _DEBUG
	if(nbVertices >= MAX_HDAG_VERTICES){
		// Adding a vertex while the graph is already full
		exitWithCode(1000);
	}
#endif
	vertices[nbVertices] = vertex ;
	vertex->setBase(this);
	nbVertices++;
}

/**
 Adding a vertex to the graph that will correspond to the first actor
 
 @param the vertex name
 @return the new vertex
*/
INLINE
HDAGVertex* HDAGGraph::addFirstVertex(char* name){
	firstVertex = addVertex(name);
	return firstVertex;
}

/**
 Getting the vertex corresponding to the first actor
 
 @return the first vertex
*/
INLINE
HDAGVertex* HDAGGraph::getFirstVertex(){
	return firstVertex;
}

#endif
