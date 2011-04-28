/*********************************************************
Copyright or © or Copr. IETR/INSA: Maxime Pelcat

Contact mpelcat for more information:
mpelcat@insa-rennes.fr

This software is a computer program whose purpose is to execute
parallel applications.

 *********************************************************/
 
/**
 * An edge in a delay-less HDAG graph (no initial token).
 * The base unit of production and consumption is a char (8 bits).
 * The space reserved for tokens can be filled with a structure.
 * 
 * @author mpelcat
 */
#include "Jade/Graph/HDAGEdge.h"

HDAGEdge* HDAGEdge::firstInSinkOrder = NULL;
HDAGEdge* HDAGEdge::lastInSinkOrder = NULL;


/**
 Constructor
*/
HDAGEdge::HDAGEdge()
{
	prevInSinkOrder = NULL;
	nextInSinkOrder = NULL;
}

/**
 Destructor
*/
HDAGEdge::~HDAGEdge()
{
}
