/*********************************************************
Copyright or � or Copr. IETR/INSA: Maxime Pelcat

Contact mpelcat for more information:
mpelcat@insa-rennes.fr

This software is a computer program whose purpose is to execute
parallel applications.

 *********************************************************/
 
/**
 * A HDAG graph. It contains HDAG vertices and edges. It has a bigger table for vertices and edges than DAG.
 * Each edge production and consumption must be equal. There is no repetition vector for the vertices.
 * 
 * @author mpelcat
 */

#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Graph/HDAGVertex.h"
#include "Jade/Graph/HDAGEdge.h"
#include <cstdio>
#include <cstring>
#include <map>

using namespace std;

/**
 Constructor
*/
HDAGGraph::HDAGGraph()
{
	// There is no dynamic allocation of graph members
	nbVertices = 0;
	nbEdges = 0;
}

/**
 Destructor
*/
HDAGGraph::~HDAGGraph()
{
}

/**
 Adding an edge to the graph
 
 @param source: The source vertex of the edge
 @param production: number of tokens (chars) produced by the source
 @param sink: The sink vertex of the edge
 @param consumption: number of tokens (chars) consumed by the sink
 @return the created edge
*/
HDAGEdge* HDAGGraph::addEdge(HDAGVertex* source, int tokenRate, HDAGVertex* sink){
	HDAGEdge* edge;
#ifdef _DEBUG
	if(nbEdges >= MAX_HDAG_EDGES){
		// Adding an edge while the graph is already full
		exitWithCode(1001);
	}
#endif
	edge = edges[nbEdges];
	edge->setSource(source);
	edge->setTokenRate(tokenRate);
	edge->setSink(sink);
	nbEdges++;
	return edge;
}

/**
 Adding the given edge into the graph
 
 @param source: The source vertex of the edge
 @param sink: The sink vertex of the edge
 @param production: number of tokens (chars) produced by the source
 @param consumption: number of tokens (chars) consumed by the sink
 @return the created edge
*/
void HDAGGraph::addEdge(HDAGVertex* source, HDAGVertex* sink, HDAGEdge* edge){
#ifdef _DEBUG
	if(nbEdges >= MAX_HDAG_EDGES){
		// Adding an edge while the graph is already full
		exitWithCode(1001);
	}
#endif
	edge->setSource(source);
	edge->setTokenRate(0);
	edge->setSink(sink);
	edges[nbEdges] = edge;
	nbEdges++;
}


/**
 Removes the last added edge
*/
void HDAGGraph::removeLastEdge(){
	if(nbEdges > 0){
		nbEdges--;
	}
	else{
		// Removing an edge from an empty graph
		exitWithCode(1007);
	}
}

/**
 Removes all the edges in this graph that are also contained in the specified edge array.
*/
bool HDAGGraph::removeAllEdges(HDAGEdge** edges, int nbRemEdges){
	bool graphChanged = false;

	for(int i=0; i<nbEdges; i++){
		graphChanged |= removeEdge(edges[i]);
	}
	
	if (graphChanged){
		refreshEdges();
	}

	return graphChanged;
}

/**
 Refresh edges of the graph. This method MUST be called when edge are changed in the graph.
*/
void HDAGGraph::refreshEdges(){
	int newNbEdges = 0;

	for(int i=0; i<nbEdges; i++){
		if (edges[i] != NULL){
			if(newNbEdges != i){
				edges[newNbEdges]= edges[i];
				edges[i] = NULL;
			}
			newNbEdges++;
		}
	}
	
	nbEdges = newNbEdges;
}

/**
 Removes an edge in the graph.
*/
bool HDAGGraph::removeEdge(HDAGEdge* edge){
	for(int i=0; i<nbEdges; i++){
		if (edge == edges[i]){
			// delete edge
			edges[i] = NULL;
			delete edge;

			//edge is found
			return true;
		}
	}

	// edge not found
	return false;
}

HDAGVertex* HDAGGraph::getEdgeTarget(HDAGEdge* edge){
	if (edge->getSink() == NULL)
		return NULL;

	for(int i=0; i<nbVertices; i++){
		HDAGVertex* currentVertex = vertices[i];
		if(edge->getSink()->equals(currentVertex)){
			return currentVertex;
		}
	}
	
	return NULL;
}


/**
 Removes all edges and vertices
*/
void HDAGGraph::flush(){
	nbVertices = nbEdges = 0;
	HDAGEdge::firstInSinkOrder = NULL;
}

#if 0
/**
 Pivot function for Quick Sort
*/
int partition(HDAGEdge* edgePointers, int p, int r, char sourceOrSink) {
  HDAGEdge* x = &edgePointers[r];
  static HDAGEdge temp;
  int j = p - 1;
  for (int i = p; i < r; i++) {
	  if (((sourceOrSink != 0) && (x->source >= edgePointers[i].source)) ||
		  ((sourceOrSink == 0) && (x->sink >= edgePointers[i].sink))) {
      j = j + 1;
      memcpy(&temp,&edgePointers[j],sizeof(HDAGEdge));
      memcpy(&edgePointers[j],&edgePointers[i],sizeof(HDAGEdge));
      memcpy(&edgePointers[i],&temp,sizeof(HDAGEdge));
    }
  }

  memcpy(&edgePointers[r],&edgePointers[j + 1],sizeof(HDAGEdge));
  memcpy(&edgePointers[j + 1],x,sizeof(HDAGEdge));

  return (j + 1);
}

/**
 quick Sort recursive algorithm 
*/
void quickSort(HDAGEdge* edgePointers, int p, int r, char sourceOrSink) {
  if (p < r) {
    int q = partition(edgePointers, p, r, sourceOrSink);
    quickSort(edgePointers, p, q - 1, sourceOrSink);
    quickSort(edgePointers, q + 1, r, sourceOrSink);
  }
}

/**
 quick Sort algorithm used for sorting edges pointers from their source or sink address
*/
void quickSort(HDAGEdge* edgePointers, int length, char sourceOrSink) {
  quickSort(edgePointers, 0, length-1, sourceOrSink);
}
#endif

/**
 Gets pointers on the edges of the graph in the order of their source or sink.
 Accelerates getting the input or output edges. The edges are linked together
 to constitute a linked list.
 
 @param startIndex: only the edges after this index are reordered
*/
void HDAGGraph::sortEdges(int startIndex){
	/*int length = this->nbEdges - startIndex;
	if(length>1){
		quickSort((&edges[startIndex]), length, 0);
	}*/
	HDAGEdge* currentNewEdge, *currentOldEdge;
	HDAGVertex* currentNewSink;

	for(int i=startIndex; i<nbEdges; i++){
		currentNewEdge = edges[i];
		
		// Adding the first edge
		if(HDAGEdge::firstInSinkOrder == NULL){
			HDAGEdge::firstInSinkOrder = currentNewEdge;
			HDAGEdge::lastInSinkOrder = currentNewEdge;
			currentNewEdge->prevInSinkOrder = NULL;
			currentNewEdge->nextInSinkOrder = NULL;
		}
		else{
			currentNewSink = currentNewEdge->getSink();
			
			/*currentOldEdge = HDAGEdge::firstInSinkOrder;
			// Going through the already ordered edges
			// while the edge has a next one and must be before the one we add, we go to the next one
			while((currentOldEdge->nextInSinkOrder != NULL) && (currentOldEdge->getSink() < currentNewSink)){
				currentOldEdge = currentOldEdge->nextInSinkOrder;
			}*/

			currentOldEdge = HDAGEdge::lastInSinkOrder;
			// Going through the already ordered edges in reverse order
			// while the edge has a next one and must be before the one we add, we go to the next one
			while((currentOldEdge->prevInSinkOrder != NULL) && (currentOldEdge->getSink() > currentNewSink)){
				currentOldEdge = currentOldEdge->prevInSinkOrder;
			}

			// The next is null and we need to add the new edge after the old one
			if(currentOldEdge->getSink() <= currentNewSink){
				currentNewEdge->prevInSinkOrder = currentOldEdge;
				currentNewEdge->nextInSinkOrder = NULL;
				HDAGEdge::lastInSinkOrder = currentNewEdge;
				currentOldEdge->nextInSinkOrder = currentNewEdge;
			}
			else{
				// We need to add the new edge before the old one
				currentNewEdge->prevInSinkOrder = currentOldEdge->prevInSinkOrder;
				if(currentOldEdge->prevInSinkOrder != NULL){
					currentOldEdge->prevInSinkOrder->nextInSinkOrder = currentNewEdge;
				}
				else{
					//New start vertex
					HDAGEdge::firstInSinkOrder = currentNewEdge;
				}
				currentNewEdge->nextInSinkOrder = currentOldEdge;
				currentOldEdge->prevInSinkOrder = currentNewEdge;
			}
		}
	}
}

/**
 Precomputes the successor vertices of all vertices to speed up the access
*/
void HDAGGraph::precomputeSuccessors(){
	for(int i=0; i<nbVertices; i++){
		HDAGVertex* currentVertex = vertices[i];
		currentVertex->flushSuccessors();
		precomputeSuccessors(currentVertex);
	}
}

/**
 Precomputes the successor vertices of a given vertex and stores their pointers in it

 @param vertex: the current vertex
*/
void HDAGGraph::precomputeSuccessors(HDAGVertex* vertex){
	// Retrieving all edges having vertex for source
	for(int i=0; i<nbEdges; i++){
		HDAGEdge* currentEdge = edges[i];
		if(currentEdge->getSource()->equals(vertex)){
			vertex->addSuccessor(currentEdge->getSink());
		}
	}
}