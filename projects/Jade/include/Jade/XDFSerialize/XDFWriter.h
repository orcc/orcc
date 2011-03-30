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

/**
@brief Interface of XDFWriter
@author Olivier Labois
@file XDFWriter.h
@version 1.0
@date 23/03/2011
*/

//------------------------------
#ifndef XDFWRITER_H
#define XDFWRITER_H

#include <string>
namespace llvm{
	class LLVMContext;
}

#include "Jade/Core/Network.h"
#include "Jade/TinyXml/TinyXml.h"
//------------------------------

/**
*
* @class XDFWriter
* @brief This class defines a writer of XDF files.
*
* XDFWriter is a class that writes an xdf file with a network given.
*
* @author Olivier Labois
*
*/
class XDFWriter {

public:
	/**
     *  @brief Constructor of the class XDFWriter
     *
     *  @param	filename : a file that represents the absolute path of the output directory
	 *
	 *  @param network : a network
     */
	XDFWriter (std::string xdfPath, Network* network);

	 /**
     *  @brief Destructor of the class XDFWriter
     */
	~XDFWriter ();


	/**
     *  @brief Start writing of XDF
     */
	void WriteXDF();


private:

	/**
     *  @brief Returns an Instance parent that represents the given instance.
	 *   
	 *  @param instance : an instance
	 *
	 *  @return an instance xml element parent
     */
	TiXmlElement* writeInstance(Instance* instance);

	/**
     *  @brief Returns a Connection parent that represents the given connection.
	 *   
	 *  @param connection : a connection
	 *
	 *  @return a connection xml element parent
     */
	TiXmlElement* writeConnection(Connection* connection);

	/**
     *  @brief Appends Attribute elements to the given parent parent.
	 *
	 *	 Each attribute of the attributes map is transformed to an Attribute xml element parent.
	 *   
	 *  @param parent : the parent parent
	 *
	 *  @param attributes : a map of attributes
     */
	void writeAttributes(TiXmlElement* parent, std::map<std::string, IRAttribute*>* attributes);

	/**
     *  @brief Appends an Expr parent that represents the given expression to the given parent
	 *   
	 *  @param parent : the parent parent to which an Expr parent should be added
	 *
	 *  @param expr : an expression
     */
	void writeExpr(TiXmlElement* parent, Expr* expr);

	/**
     *  @brief Returns a Type parent that represents the given type.
	 *   
	 *  @param type : a type
	 *
	 *  @return a type xml element parent
     */
	TiXmlElement* writeType(IRType* type);

	/**
     *  @brief Returns an Entry parent that represents the given expression entry.
	 *   
	 *  @param name : the entry name
	 *
	 *  @param expr : the entry value as an expression
	 *
	 *	@return an entry xml element parent
     */
	TiXmlElement* writeEntry(std::string name, Expr* expr);


	/** Path of the xdf output file */
	std::string filename;

	/** network to write */
	Network* network;

	/* TinyXml document container */
	TiXmlDocument* xdfDoc;

	/** Verbose actions taken */
	bool verbose;
};

#endif
