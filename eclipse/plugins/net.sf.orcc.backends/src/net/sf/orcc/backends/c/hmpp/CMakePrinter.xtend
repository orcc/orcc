/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
 * about
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
package net.sf.orcc.backends.c.hmpp

import net.sf.orcc.df.Network

class CMakePrinter extends net.sf.orcc.backends.c.CMakePrinter {
	
	new(Network network) {
		super(network)
	}
	
	override protected rootCMakeContent() '''
		# Generated from «network.simpleName»
		
		cmake_minimum_required (VERSION 2.6)
		
		project («network.simpleName»)
		
		# Output folder
		set(EXECUTABLE_OUTPUT_PATH ${CMAKE_SOURCE_DIR}/bin)
		
		# Libraries folder
		set(LIBS_DIR ${CMAKE_SOURCE_DIR}/libs)
		set(SRC_DIR ${CMAKE_SOURCE_DIR}/src)
		
		# Runtime libraries inclusion
		set(ORCC_INCLUDE_DIR ${LIBS_DIR}/orcc/include)
		
		# Hmpp compiler
		set(HMPP_COMPILER CACHE STRING "hmpp compiler binary")
		
		set(CMAKE_C_COMPILER ${HMPP_COMPILER})
		set(CMAKE_CXX_COMPILER ${HMPP_COMPILER})
		set(CMAKE_C_FLAGS gcc)
		set(CMAKE_CXX_FLAGS g++)
		
		«addLibrariesSubdirs»
	'''
}