/*
 * Copyright (c) 2009, ARTEMIS SudParis - IETR/INSA of Rennes
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
@brief Interface of XCFParser
@author Jerome Gorin
@file XCFParser.h
@version 1.0
@date 20/09/2011
*/

//------------------------------
#ifndef XCFPARSER_H
#define XCFPARSER_H
#include <string>
#include <map>

class TiXmlDocument;
class TiXmlElement;
//------------------------------

/**
*
* @class XCFParser
* @brief This class defines a parser of XCF files.
*
* XCFParser is a class that parses an xcf file given, as a string filename in constructor, and
* produce a mapping class representing the mapping of a network.
*
* @author Jerome Gorin
*
*/
class XCFParser {
public:

	/**
     *  @brief Constructor of the class XDFParser
     */
	XCFParser (bool verbose = false);

	 /**
     *  @brief Destructor of the class XDFParser
     */
	~XCFParser (){};

   /**
     *  @brief Start Parsing XCF filep
	 *
	 *  @param	filename : name of the XCF file to parse
     *
     *  @return a mapping class that describe the mapping of a network, NULL if parsing failed
     */
	std::map<std::string, std::string>* parseFile (std::string filename);

private:
	/*!
     *  @brief Parses an XCF document.
	 *
     *  @return a mapping class that describe the mapping of a network, NULL if parsing failed
     *  
     */
	std::map<std::string, std::string>* parseXCFDoc();

	/*!
     *  @brief Parses partitioning of the XCF document.
     *
     *  Parses the body of the XCF document. The body can contain any element
	 *  among the supPorted elements. SupPorted elements are: Partitionning, Partitip,
	 * and Instance.
	 *
     *  @param root : TiXmlElement representation of root element 
	 *
	 *  @return a map of instance id and its corresponding partition
     */
	void parsePartitioning(TiXmlElement* root);

	void parsePartition(TiXmlElement* elt);

private:

	/* TinyXml document container */
	TiXmlDocument* xcfDoc;

	/* Resulting mapping */
	std::map<std::string, std::string>* mapStr;

	/** Verbose actions taken */
	bool verbose;
};

#endif
