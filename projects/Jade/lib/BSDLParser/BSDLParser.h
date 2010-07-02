#ifndef BSDLPARSER_H
#define BSDLPARSER_H

//#include <xstring>
#include <stdio.h>
#include <libxml/parser.h>
#include <libxml/tree.h>
#include "ir/Network.h"
#include "ir/Entry.h"
#include "graph/HDAGGraph.h"

using namespace std;

class BSDLParser {
public:

public:
	
	BSDLParser (std::string filename);
	~BSDLParser ();
	void ParseBSDL ();

private:
	xmlDoc *xdfDoc;
	void parseBody(xmlNode* root);
	void parseAction(xmlNode* element);
};

#endif
