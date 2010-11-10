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
@brief Description of the BroadcastAdder class interface
@author Jerome Gorin
@file BroadcastAdder.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef BROADADDER_H
#define BROADADDER_H

#include <list>

class Actor;
class Connection;
class HDAGGraph;
class Instance;
class Network;
class Port;
class Vertex;
struct ltstr;
class FifoCircular;
class BroadcastActor;
class Decoder;
class InstancedActor;
//------------------------------

/**
 * @brief  This class defines an broadcast management for Decoder.
 * 
 * @author Jerome Gorin
 * 
 */
class BroadcastAdder {
public:
	/**
     *  @brief Add broadcast into the given network
     *
	 *	This transformation detect multiple connection to port 
	 *    and insert broadcast actor to force port being connected 
	 *     to an only port
	 *
	 *	@param network : Network to transform
	 *
	 *	@param decoder : Decoder to add the broadcast
     */
	BroadcastAdder(llvm::LLVMContext& C, Decoder* decoder);

	void transform();

	~BroadcastAdder();

private:

	/**
	 * @brief Examine the outgoing connections of vertex.
	 * 
	 * @param vertex: a Vertex
	 *
	 * @param connections : an array of outgoing Connection for a vertex
	 *
	 * @param outMap : a map from each output port P(i) of vertex to a list of outgoing connections from P(i)
	 */
	void examineConnections(Vertex* vertex, Connection** connections, int nbEdges,
		std::map<Port*, std::list<Connection*>*, ltstr>* outMap);

	/**
	 * @brief Examine a vertex for eventual broadcast.
	 * 
	 * @param vertex: the Vertex to examine
	 */
	void examineVertex(Vertex* vertex);

	/**
	 * @brief creates incoming connections of a broadcast
	 *
	 * Creates a connection between the source vertex and the broadcast.
	 * 
	 * @param connection: Connection outgoing to vertex
	 *
	 * @param vertex : the Vertex
	 *
	 * @param vertexBCast : the Vertex that contains the broadcast
	 *
	 * @param actorBCast: the current BroadcastActor
	 */
	void createIncomingConnection(Connection* connection, Vertex* vertex, Vertex* vertexBCast, Instance* instance);

	/**
	 * @brief Creates outgoing connections of a broadcast
	 *
	 * Creates a connection between the broadcast and the target for each
	 * outgoing connection of vertex.
	 * 
	 * @param vertexBCast : the Vertex that contains the broadcast
	 *
	 * @param outList : a list of Connection that incomes from vertex
	 *
	 * @param actorBCast: the current BroadcastActor
	 */
	void createOutgoingConnections(Vertex* vertexBCast,std::list<Connection*>* outList, Instance* instance);

	/** graph of the network */
	HDAGGraph* graph;

	/** Decoder to apply the transformation */
	Decoder* decoder;

	/** list of Actor in the decoder */
	std::map<std::string, Actor*>* actors;

	/** fifo used in the decoder */
	AbstractFifo* fifo;

	/** list of connections to be removed */
	std::list<Connection*> toBeRemoved;

	/** LLVM Context */
	llvm::LLVMContext &Context;
};

#endif