/*********************************************************
Copyright or © or Copr. IETR/INSA: Maxime Pelcat

Contact mpelcat for more information:
mpelcat@insa-rennes.fr

This software is a computer program whose purpose is to execute
parallel applications.

 *********************************************************/
 
#ifndef DOT_WRITER
#define DOT_WRITER

#include "HDAGGraph.h"
#include "HDAGVertex.h"
#include "HDAGEdge.h"


/**
 * Writes a dot file from a graph of a given type
 * 
 * @author mpelcat
 */
class DotWriter {

	private :
	public : 
		/**
		 Constructor
		*/
		DotWriter();


		/**
		 Destructor
		*/
		~DotWriter();


		/**
		 Writes a HDAGGraph in a file

		 @param graph: written graph
		 @param path: output file path
		*/
		void write(HDAGGraph* graph, char* path, char displayNames);
};

#endif
