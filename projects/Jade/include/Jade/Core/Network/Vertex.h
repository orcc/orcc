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
@brief Description of the Vertex class interface
@author Jerome Gorin
@file Entry.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef VERTEX_H
#define VERTEX_H

#include "Jade/Graph/HDAGVertex.h"
#include "Jade/Core/Network/Instance.h"

class Port;
//------------------------------

/**
 * @class Vertex
 *
 * This class defines a vertex in an XDF network. A vertex is either an input
 * port, an output port, or an instance.
 *
 * @author Jerome Gorin
 * 
 */
class Vertex : public HDAGVertex {
private:
	enum Type { INPUT_PORT, INSTANCE, OUTPUT_PORT };

public:

	/*!
	 *  @brief Create a new vertex of instance.
	 *
	 * Creates a new vertex whose contents will be the given instance.
	 * 
	 * @param instance : Instance of the vertex
	 */
	Vertex(Instance* instance): HDAGVertex((char*)instance->getId().c_str()) {
		type = INSTANCE;
		this->contents = (void*)instance;
	}

	/**
	 *  @brief Create a new vertex of instance.
	 *
	 * Creates a new vertex whose contents will be the given port.
	 * 
	 * @param port : Port of the vertex
	 */
	Vertex(std::string kind, Port* port) {
		if (kind.compare("Input") == 0) {
			type = INPUT_PORT;
		} else {
			type = OUTPUT_PORT;
		}
		this->contents = (void*)port;
	}

	~Vertex(){
		delete contents;	
	};


	/**
	 * @brief Getter of Instance
	 *
	 * Returns the instance contained in this vertex.
	 * 
	 * @return the instance contained in this vertex.
	 */
	Instance* getInstance() {
		if (isInstance()) {
			return (Instance*)contents;
		} else {
			return NULL;
		}
	}

	/**
	 * @brief Sets the condition when to vertex are considered as equal.
	 *
 	 * @param vertex: HDAGVertex to compare with
	 *
	 * @return true if the vertex are equivalent
	 */
	bool equals(HDAGVertex* vertex) {
		return contents == ((Vertex*)vertex)->contents;
	}

	/**
	 * @brief Getter of Port
	 *
	 * Returns the port contained in this vertex.
	 * 
	 * @return the port contained in this vertex.
	 */
	Port* getPort() {
		if (isPort()) {
			return (Port*)contents;
		} else {
			return NULL;
		}
	}

	/**
	 *  @brief Return true if this vertex is an Instace.
	 *
	 * Returns true if this vertex contains an instance, and
	 * false otherwise. This method must be called to ensure a
	 * vertex is an instance before calling getInstance().
	 * 
	 * @return true if this vertex contains an instance, and
	 *         false otherwise
	 */
	bool isInstance() {
		return type == INSTANCE;
	}

	/**
	 *  @brief Return true if this vertex is a Port.
	 *
	 * Returns true if this vertex contains a port, and
	 * false otherwise. This method must be called to ensure a
	 * vertex is a port before calling getInstance().
	 * 
	 * @return true if this vertex contains a port, and
	 *         false otherwise
	 */
	bool isPort() {
		return type != INSTANCE;
	}

private:
	/**
	 * the contents of this vertex. Can only be an Instance or a Port.
	 */
	void* contents;

	/**
	 * the type of this vertex. One of INSTANCE, INPUT or OUTPUT.
	 */
	enum Type type;
};

#endif