/*********************************************************
Copyright or © or Copr. IETR/INSA: Maxime Pelcat

Contact mpelcat for more information:
mpelcat@insa-rennes.fr

This software is a computer program whose purpose is to execute
parallel applications.

 *********************************************************/
 
#ifndef HDAG_VERTEX
#define HDAG_VERTEX

class DAGVertex;
class CSDAGVertex;
class HDAGGraph;
class HDAGEdge;
#include <cstring>

#include "SchedulerDimensions.h"
#include "SchedulingError.h"

/**
 * A vertex in a HDAG graph
 * 
 * @author mpelcat
 */

class HDAGVertex {

	public :
		/**
		 The base, i.e. the graph in which current vertex is included
		*/
		HDAGGraph* base;
		
		/**
		 Integer solved parameters. Retrieved while solving the edges
		*/
		int paramValues[MAX_PARAM];

		/**
		 The reference DAG graph (if generated from a CSDAG)
		*/
		CSDAGVertex* csDagReference;

		/**
		 The vertex top level
		*/
		int tLevel;

		/**
		 The vertex implementation slave index
		*/
		char slaveIndex;

		/**
		 The duplication index of the vertex to distinguish it from other vertices created from dagReference.
		*/
		int referenceIndex;

		/**
		 The vertex name
		*/
		char name[MAX_VERTEX_NAME_SIZE];

		/**
		 A table of the vertices following the current vertices in the graph. The table is initialized from
		 edges information by the precomputeSuccessors method in HDAGGraph
		*/
		HDAGVertex* successors[MAX_HDAG_INPUT_EDGES];
		int nbSuccessors;
	public : 

		/**
		 Constructor
		*/
		HDAGVertex();

		/**
		 Constructor
		*/
		HDAGVertex(char* name){this->setName(name);};

		/**
		 Destructor
		*/
		~HDAGVertex();

		/**
		 Setting the base, i.e. the graph in which current vertex is included

		 @param base: the base
		*/
		void setBase(HDAGGraph* graph);

		/**
		 Getting the value of a parameter

		 @param paramIndex: the parameter index
		 @return the parameter value
		*/
		int getParamValue(int paramIndex);

		/**
		 Setting the value of a parameter

		 @param paramIndex: the parameter index
		 @param value: the parameter value
		*/
		void setParamValue(int paramIndex, int value);

		/**
		 Getting the CSDAG vertex that generated the current HDAG vertex

		 @return the CSDAG reference vertex
		*/
		CSDAGVertex* getCsDagReference();

		/**
		 Setting the value of a parameter

		 @param:vertex the CSDAG reference vertex
		*/
		void setCsDagReference(CSDAGVertex* vertex);

		/**
		 Getting the duplication index of the vertex that distinguishes
		 it from other vertices created from dagReference.

		 @return the CSDAG reference index
		*/
		int getReferenceIndex();

		/**
		 Setting the duplication index of the vertex to distinguish 
		 it from other vertices created from dagReference.

		 @param index: the vertex reference index
		*/
		void setReferenceIndex(int index);

		/**
		 Getter of the implementation information giving the slave that will execute the vertex.

		 @return the slave index
		*/
		char getSlaveIndex();

		/**
		 Setter of the implementation information giving the slave that will execute the vertex.

		 @param slaveIndex: the slave index
		*/
		void setSlaveIndex(char slaveIndex);

		/**
		 Getter of the vertex top level: the time between the loop execution beginning and the vertex execution beginning.

		 @return the t level of the vertex
		*/
		int getTLevel();

		/**
		 Setter of the vertex top level: the time between the loop execution beginning and the vertex execution beginning.

		 @param value: the t level of the vertex
		*/
		void setTLevel(int value);

		/**
		 Setting the vertex name

		 @param name: the name
		*/
		void setName(char* name);

		/**
		 Getting the vertex name

		 @param name: the name
		*/
		char* getName();

		/**
		 Removes all the vertices from the successors table
		*/
		void flushSuccessors();

		/**
		 Adds a vertex in the the successors table

		 @param vertex: the vertex to add
		*/
		void addSuccessor(HDAGVertex* vertex);

		/**
		 Gets a pointer on the successors table and the size of the table

		 @param successorVertices: a pointer to retrieve the table pointer
		 @return the successors table size
		*/
		int getSuccessors(HDAGVertex*** successorVertices);

		/**
		 Sets the condition when to HDAGVertex are considered as equal.

		 @param vertex: HDAGVertex to compare with
		 @return true if the vertex are equivalent
		*/
		virtual bool equals(HDAGVertex* vertex){
			return this == vertex;
		}
};


/**
 Setting the base, i.e. the graph in which current vertex is included

 @param base: the base
*/
INLINE
void HDAGVertex::setBase(HDAGGraph* graph){
	this->base = graph;
}

/**
 Getting the value of a parameter

 @param paramIndex: the parameter index
 @return the parameter value
*/
INLINE
int HDAGVertex::getParamValue(int paramIndex){
	return paramValues[paramIndex];
}

/**
 Setting the value of a parameter

 @param paramIndex: the parameter index
 @param value: the parameter value
*/
INLINE
void HDAGVertex::setParamValue(int paramIndex, int value){
	paramValues[paramIndex] = value;
}

/**
 Getting the CSDAG vertex that generated the current HDAG vertex

 @return the CSDAG reference vertex
*/
INLINE
CSDAGVertex* HDAGVertex::getCsDagReference(){
	return csDagReference;
}

/**
 Setting the value of a parameter

 @param vertex: the CSDAG reference vertex
*/
INLINE
void HDAGVertex::setCsDagReference(CSDAGVertex* vertex){
	csDagReference = vertex;
}

/**
 Getting the duplication index of the vertex that distinguishes
 it from other vertices created from dagReference.

 @return the CSDAG reference index
*/
INLINE
int HDAGVertex::getReferenceIndex(){
	return referenceIndex;
}

/**
 Setting the duplication index of the vertex to distinguish 
 it from other vertices created from dagReference.

 @param index: the vertex reference index
*/
INLINE
void HDAGVertex::setReferenceIndex(int index){
	referenceIndex = index;
}

/**
 Getter of the implementation information giving the slave that will execute the vertex.

 @return the slave index
*/
INLINE
char HDAGVertex::getSlaveIndex(){
	return slaveIndex;
}

/**
 Setter of the implementation information giving the slave that will execute the vertex.

 @param slaveIndex: the slave index
*/
INLINE
void HDAGVertex::setSlaveIndex(char index){
	slaveIndex = index;
}

/**
 Getter of the vertex top level: the time between the loop execution beginning and the vertex execution beginning.

 @return the t level of the vertex
*/
INLINE
int HDAGVertex::getTLevel(){
	return tLevel;
}

/**
 Setter of the vertex top level: the time between the loop execution beginning and the vertex execution beginning.

 @param value: the t level of the vertex
*/
INLINE
void HDAGVertex::setTLevel(int value){
	tLevel = value;
}

/**
 Setting the vertex name

 @param name: the name
*/
INLINE
void HDAGVertex::setName(char* name){
	strcpy(this->name,name);
}

/**
 Getting the vertex name

 @param name: the name
*/
INLINE
char* HDAGVertex::getName(){
	return name;
}

/**
 Removes all the vertices from the successors table
*/
INLINE
void HDAGVertex::flushSuccessors(){
	nbSuccessors = 0;
}

/**
 Adds a vertex in the the successors table

 @param the vertex to add
*/
INLINE
void HDAGVertex::addSuccessor(HDAGVertex* vertex){
#ifdef _DEBUG
	if(nbSuccessors >= MAX_HDAG_INPUT_EDGES){
		// Adding a successor vertex in a full table
		exitWithCode(1044);
	}
#endif
	successors[nbSuccessors] = vertex;
	nbSuccessors++;
}

/**
 Gets a pointer on the successors table and the size of the table

 @param a pointer to retrieve the table pointer
 @return the successors table size
*/
INLINE
int HDAGVertex::getSuccessors(HDAGVertex*** successorVertices){
	*successorVertices = successors;
	return nbSuccessors;
}

#endif
