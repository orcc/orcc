/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

/*!
 * @file NetworkParser.h
 * @brief Interface of NetworkParser
 * @author Jerome Gorin
 * @version 1.0
 */

//------------------------------
#ifndef NETPARSER_H
#define NETPARSER_H

#include <list>
#include <stdio.h>
#include "TypeParser.h"

#include "Jade/Core/Network.h"
#include "Jade/Core/Entry.h"
#include "Jade/TinyXml/TinyXml.h"

namespace llvm{
	class ConstantInt;
	class LLVMContext;
}

class Vertex;
class Port;
class Instance;
class HDAGGraph;
class Connection;
class IRAttribute;
//------------------------------

/*! 
*	@class NetworkParser
*	@brief This class defines the network parser of class XDFparser.
*
*/
class NetworkParser {

public:
	std::list<Connection*>* Connections;
	Network* network;

public:

	/*!
     *  @brief Constructor
     *
     *  Constructor of the class NetworkParser
     *
     *  @param filename : name of the network file to parse
     */
	NetworkParser (llvm::LLVMContext& C, std::string filename);

	/*!
     *  @brief Destructor
     *
     *  Destructor of the class NetworkParser
     */
	~NetworkParser ();

	/*!
     *  @brief Parses an XDF network.
     *
     *  Parses the document given in constructor as an XDF network.
	 *
	 *
     *  @return a network class that describe the network of the dataflow, NULL if parsing failed
     *  
     */
	Network* parseNetwork ();

private:

	/* TinyXml document container */
	TiXmlDocument* xdfDoc;
	
	/* Xml type parser */
	TypeParser* typeParser;

	/** XDF expression parser. */
	ExprParser* exprParser;

	/** map of input ports  */
	std::map<std::string, Port*>* inputs;

	/** map of outputs ports  */
	std::map<std::string, Port*>* outputs;

	/** map of instances ports  */
	std::map<std::string, Instance*>* instances;


	/** graph of the network  */
	HDAGGraph* graph;

	/*!
     *  @brief Parses the body of the XDF document.
     *
     *  Parses the body of the XDF document. The body can contain any element
	 *  among the supPorted elements. SupPorted elements are: Connection, Decl
	 * (kind=Param or kind=Var), Instance, Package, Port.
	 *
     *  @param root : TiXmlElement representation of root element 
     */
	void parseBody(TiXmlElement* root);


	/*!
     *  @brief Parses an "Instance" element and returns an {@link Instance}.
     *
	 *
     *  @param instance : TiXmlElement representation of root element .
	 *
	 *  @return an Instance
     */
	Instance* parseInstance(TiXmlElement* instance);
	
	/*!
     *  @brief Parses the given TiXmlElement as a Connection element.
     *
	 * Parses the given TiXmlElement as a Connection, and adds a matching
	 * Connection to the graph of the network being parsed.
	 *
     *  @param root : TiXmlElement representation of Connection element 
     */
	void parseConnection(TiXmlElement* connection);

	/*!
     *  @brief Parses IRAttributes elements.
     *
	 * Returns a map of IRAttribute names -> values by parsing the "IRAttribute"
	 * nodes.
	 *
     *  @param element : TiXmlNode representation of IRAttributes element 
	 *
	 *  @return  a map of IRAttributes with their names  
     */
	std::map<std::string, IRAttribute*> *parseAttributes(TiXmlNode* node);

	/*!
     *  @brief Parses the current node as a actor parameters.
     *
	 * Returns a map of IRAttribute parameter names -> values by parsing the parameter
	 * node of an actor.
	 *
     *  @param element : xmlNode representation of IRAttributes element 
	 *
	 *  @return  a map of IRAttributes with their value  
     */

	std::map<std::string, Expr*>* parseParameters(TiXmlNode* node);


	/*!
	 *  @brief return a new port.
	 *
	 *  If vertexName is not empty, returns a new Port whose name is set to
	 * portName.
	 * 
	 * @param vertexName : the name of a vertex
	 *
	 * @param portName : the name of a port
	 *
	 * @return a port
	 */
	Port* getPort(std::string vertexName, std::string portName);


	/**
	 *  @brief get a Vertex from graph..
	 *
	 * If vertexName is empty, returns a new Vertex that contains a port from
	 * the ports map that has the name portName. If vertexName is not empty,
	 * returns a new Vertex that contains an instance from the instances map.
	 * 
	 * @param vertexName : string of vertex
	 *
	 * @param portName : string of port
	 *
	 * @param ports : a map of input ports or output ports
	 *
	 * @return a vertex that contains a port or an instance
	 */
	Vertex* getVertex(std::string vertexName, std::string portName, std::string kind, std::map<std::string, Port*>* ports);
};

#endif
