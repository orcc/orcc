#include "BSDLParser.h"



BSDLParser::BSDLParser (std::string filename){
	LIBXML_TEST_VERSION
	
	xdfDoc = xmlReadFile(filename.c_str(), NULL, 0);
    
	if (xdfDoc == NULL) {
        fprintf(stderr, "Failed to parse %s\n", filename);
		exit(0);
    }

};


void BSDLParser::ParseBSDL (){
	xmlNode *root_element = NULL;
	xmlAttr *root_attribute = NULL;


	/*Get the root element node */
    root_element = xmlDocGetRootElement(xdfDoc);

	if (xmlStrcmp(root_element->name, (const xmlChar *)"BSDL")!=0){
		fprintf(stderr, "Expected \"BSDL\" start element");
		exit(1);
	}
	
	root_attribute = root_element->properties;
	if (xmlStrcmp(root_attribute->name, (const xmlChar *)"name")!=0){
		fprintf(stderr, "Expected a \"name\" attribute");
		exit(1);
	}

	//root_element
	parseBody(root_element->children);

};

void BSDLParser::parseAction(xmlNode* element){
	
}


void  BSDLParser::parseBody(xmlNode* root){
	xmlNode* node = NULL;
	for (node = root; node; node = node->next) {
        if (node->type == XML_ELEMENT_NODE) {
			const xmlChar* name =  node->name;

			if (xmlStrcmp(name, (const xmlChar *)"Action")==0) {
			}else if (xmlStrcmp(name, (const xmlChar *)"Decl")==0) {
			}else if (xmlStrcmp(name, (const xmlChar *)"Instance")==0) {
			}else if (xmlStrcmp(name, (const xmlChar *)"Package")==0) {
			}else if (xmlStrcmp(name, (const xmlChar *)"Port")==0) {
			}else {
			}
        }
    }
}


BSDLParser::~BSDLParser (){
	xmlFreeDoc(xdfDoc);

	/*
     * Cleanup function for the XML library.
     */
    xmlCleanupParser();
    /*
     * this is to debug memory for regression tests
     */
    xmlMemoryDump();

};
