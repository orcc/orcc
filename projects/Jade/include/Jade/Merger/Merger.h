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
@brief Description of the Merger class interface
@author Jerome Gorin
@file Merger.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef MERGER_H
#define MERGER_H

namespace llvm{
	class ConstantInt;
}

class Configuration;
class Network;
class Vertex;
class Rational;
class SuperInstance;
//------------------------------

/**
 * @brief  This transformation merge static actors of a configuration together
 * 
 * @author Jerome Gorin
 * 
 */
class Merger {
public:
	Merger(llvm::LLVMContext& C, Configuration* configuration);
	~Merger(){};

	void transform();

private:
	/**
	 * @brief Merge two instance in a network
	 */
	void mergeInstance(Instance* src, Instance* dst);

	/**
	 * @brief Get a SuperInstance of the given instance
	 *
	 * @param src : the source Instance
	 *
	 * @param dst : the destination Instance
	 */
	SuperInstance* getSuperInstance(Instance* src, Instance* dst, std::list<Connection*>* connections );

	/**
	 * @brief Get the Rational of a production/comsuption
	 * 
	 * @return Rational of the connection.
	 */
	Rational getRational(llvm::ConstantInt* srcProd, llvm::ConstantInt* dstCons);

	/**
	 * @brief Update connections of the super instance
	 * 
	 * @param connections : internal connections of the SuperInstance.
	 *
	 * @param src : the instance source.
	 *
	 * @param dst : the destination instance.
	 *
	 * @param vertex : the corresponding vertex.
	 */
	void updateConnections(std::list<Connection*>* connections, Instance* src, Instance* dst, Vertex* vertex);

	/** Index of merger */
	int index;

	/** Configuration to update */
	Configuration* configuration;

	/** Network to merge */
	Network* network;

	/** LLVM Context */
	llvm::LLVMContext &Context;
};

#endif