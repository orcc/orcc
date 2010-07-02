/*********************************************************
Copyright or © or Copr. IETR/INSA: Maxime Pelcat

Contact mpelcat for more information:
mpelcat@insa-rennes.fr

This software is a computer program whose purpose is to execute
parallel applications.

 *********************************************************/
 
/**
 * Writes a dot file from a graph of a given type
 * 
 * @author mpelcat
 */
#include "Jade/Graph/DotWriter.h"
#include <cstdio>

//#include <direct.h> // for getcwd


/**
 Constructor
*/
DotWriter::DotWriter()
{
}

/**
 Destructor
*/
DotWriter::~DotWriter()
{
}

/**
 Writes a HDAGGraph in a file

 @param graph: written graph
 @param path: output file path
 @param displayName: 1 if the graph should display the vertices names
*/
void DotWriter::write(HDAGGraph* graph, char* path, char displayNames){
	FILE * pFile;

	//char directory[_MAX_PATH];
	//getcwd(directory, sizeof(directory));
	char name[MAX_VERTEX_NAME_SIZE];
	char name2[MAX_VERTEX_NAME_SIZE];

	pFile = fopen (path,"w");
	if(pFile != NULL){
		// Writing header
		fprintf (pFile, "digraph hdag {\n");
		fprintf (pFile, "node [color=Black];\n");
		fprintf (pFile, "edge [color=Red];\n");
		fprintf (pFile, "rankdir=LR;\n");
		for (int i=0 ; i<graph->getNbVertices() ; i++)
		{
			HDAGVertex* vertex = graph->getVertex(i);
			sprintf(name,"%s_%d",vertex->getName(),vertex->getReferenceIndex());
			if(displayNames){
				fprintf (pFile, "\t%s [label=\"%s\"];\n",name,name);
			}
			else{
				fprintf (pFile, "\t%s [label=\"\"];\n",name);
			}
		}
		for (int i=0 ; i<graph->getNbEdges() ; i++)
		{
			HDAGEdge* edge = graph->getEdge(i);
			sprintf(name,"%s_%d",edge->getSource()->getName(),edge->getSource()->getReferenceIndex());
			sprintf(name2,"%s_%d",edge->getSink()->getName(),edge->getSink()->getReferenceIndex());
			fprintf (pFile, "\t%s->%s [label=\"%d\"];\n",
				name,name2,
				edge->getTokenRate());
		}
		fprintf (pFile, "}\n");

		fclose (pFile);
	}
}


#ifdef DISPLAY
/**
 Writes a CSDAGGraph in a file

 @param graph: written graph
 @param path: output file path
*/
void DotWriter::write(CSDAGGraph* graph, char* path, char displayNames){
	FILE * pFile;

	//char directory[_MAX_PATH];
	//getcwd(directory, sizeof(directory));

	pFile = fopen (path,"w");
	if(pFile != NULL){
		// Writing header
		fprintf (pFile, "digraph csdag {\n");
		fprintf (pFile, "node [color=Black];\n");
		fprintf (pFile, "edge [color=Green];\n");
		fprintf (pFile, "rankdir=LR;\n");
		for (int i=0 ; i<graph->getNbVertices() ; i++)
		{
			CSDAGVertex* vertex = graph->getVertex(i);
			if(displayNames){
				fprintf (pFile, "\t%s [label=\"%s\"];\n",vertex->getName(),vertex->getName());
			}
			else{
				fprintf (pFile, "\t%s [label=\"\"];\n",vertex->getName());
			}
		}

		int labelDistance = 3;
		for (int i=0 ; i<graph->getNbEdges() ; i++)
		{
			char shortenedPExpr[EXPR_LEN_MAX];
			char shortenedCExpr[EXPR_LEN_MAX];
			CSDAGEdge* edge = graph->getEdge(i);

			globalParser.prettyPrint(edge->getProduction(),shortenedPExpr);
			globalParser.prettyPrint(edge->getConsumption(),shortenedCExpr);

			fprintf (pFile, "\t%s->%s [taillabel=\"%s\" headlabel=\"%s\" labeldistance=%d labelangle=50];\n",
				edge->getSource()->getName(),edge->getSink()->getName(),
				shortenedPExpr,shortenedCExpr,labelDistance);
			labelDistance = 3 + labelDistance%(3*4); // Oscillating the label distance to keep visibility
		}
		fprintf (pFile, "}\n");
		
		fclose (pFile);
	}
}
#endif