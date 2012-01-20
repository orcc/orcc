/*********************************************************
Copyright or © or Copr. IETR/INSA: Maxime Pelcat

Contact mpelcat for more information:
mpelcat@insa-rennes.fr

This software is a computer program whose purpose is to execute
parallel applications.

 *********************************************************/
 
#ifndef HDAG_EDGE
#define HDAG_EDGE

#include "HDAGVertex.h"

/**
 * An edge in a delay-less HDAG graph (no initial token). 
 * The base unit of production and consumption is a char (8 bits).
 * The space reserved for tokens can be filled with a structure.
 * 
 * @author mpelcat
 */
class HDAGEdge {

	private :
		/**
		 token rate (solved and not depending on an expression). 
		 tokenRate = -1 means that the edge only represents a precedence
		*/
		int tokenRate;

		/**
		 Edge source
		*/
		HDAGVertex* source;
		/**
		 Edge sink
		*/
		HDAGVertex* sink;

	public : 
		/**
		 Constructor
		*/
		HDAGEdge();

		/**
		 Destructor
		*/
		~HDAGEdge();

		/**
		 TokenRate getter

		 @return production after resolving the expression
		*/
		int getTokenRate();

		/**
		 TokenRate setter

		 @param integer defining the token rate
		*/
		void setTokenRate(int rate);

		/**
		 Source getter

		 @return the source
		*/
		HDAGVertex* getSource();

		/**
		 Sink getter

		 @return the Sink
		*/
		HDAGVertex* getSink();

		/**
		 Source setter

		 @param vertex: the source
		*/
		void setSource(HDAGVertex* vertex);

		/**
		 Sink setter

		 @param vertex: the sink
		*/
		void setSink(HDAGVertex* vertex);

		// Public for performance sake

		/**
		 In order to fast access the input edges, a linked list of edges is done. The edges
		 can then be scanned in the linked list order to get the input edges corresponding
		 to the vertices in direct order.
		*/
		static HDAGEdge* firstInSinkOrder;
		HDAGEdge* prevInSinkOrder;
		HDAGEdge* nextInSinkOrder;
		static HDAGEdge* lastInSinkOrder;
};


/**
 Source getter

 @return the source
*/
INLINE
HDAGVertex* HDAGEdge::getSource(){
	return source;
}

/**
 Sink getter

 @return the Sink
*/
INLINE
HDAGVertex* HDAGEdge::getSink(){
	return sink;
}

/**
 Source setter

 @param vertex: the source
*/
INLINE
void HDAGEdge::setSource(HDAGVertex* vertex){
	source = vertex;
}

/**
 Sink setter

 @param vertex: the sink
*/
INLINE
void HDAGEdge::setSink(HDAGVertex* vertex){
	sink = vertex;
}

/**
 TokenRate getter (token rate = production = consumption in HDAG)

 @return production after resolving the expression
*/
INLINE
int HDAGEdge::getTokenRate()
{
	return(this->tokenRate);
}

/**
 TokenRate setter

 @param production: expression defining the token production of the edge source (in char)
 @return production after resolving the expression
*/
INLINE
void HDAGEdge::setTokenRate(int rate)
{
	this->tokenRate = rate;
}

#endif
