/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
package net.sf.orcc.backends.llvm.aot

import net.sf.orcc.df.Network

/**
 * Generate CMakeList.txt content
 * 
 * @author Antoine Lorence
 */
class CMakePrinter extends net.sf.orcc.backends.c.CMakePrinter {

	new(Network network) {
		super(network);
	}
	
	/**
	 * Return CMakeList's content to write in the root target folder
	 */
	 override rootCMakeContent() '''
		# Generated from «network.simpleName»
		cmake_minimum_required (VERSION 2.6)

		# Default compiler must be clang
		set(CMAKE_C_COMPILER "clang")
		project («network.simpleName» C)

		# Configure specific flags for clang (according to selected build type)
		if(NOT "${CMAKE_BUILD_TYPE}" STREQUAL "")
		    string(TOUPPER ${CMAKE_BUILD_TYPE} CMBT)
		    set(CLANG_FLAGS ${CMAKE_C_FLAGS_${CMBT}})
		endif()

		message(STATUS "Clang FLAGS : " ${CLANG_FLAGS})
		separate_arguments(CLANG_FLAGS)

		# Output folder
		set(EXECUTABLE_OUTPUT_PATH ${CMAKE_SOURCE_DIR}/bin)

		# Runtime libraries inclusion
		include_directories(libs/orcc-native/include)
		include_directories(libs/orcc-runtime/include)

		# Compile libraries
		add_subdirectory(libs)
		# Compile application
		add_subdirectory(src)
	'''
	
	/**
	 * Return CMakeList's content to write in the src subdirectory
	 */
	override srcCMakeContent() '''
		# Generated from «network.simpleName»

		set(«network.simpleName»_SRCS
			«network.simpleName».ll
			«FOR child : network.children»
				«child.label».ll
			«ENDFOR»
		)
		
		foreach(_infile ${«network.simpleName»_SRCS})
		    string(REPLACE ".ll" ${CMAKE_C_OUTPUT_EXTENSION} _outfile ${_infile})
		    set(_inpath ${CMAKE_CURRENT_SOURCE_DIR}/${_infile})
		    set(_outpath ${CMAKE_CURRENT_BINARY_DIR}/${_outfile})

		    add_custom_command(
		        OUTPUT ${_outpath}
		        COMMAND ${CMAKE_C_COMPILER} ${CLANG_FLAGS} -c ${_inpath} -o ${_outpath}
		        DEPENDS ${_inpath}
		        COMMENT "Building object ${_outfile} from LLVM bytecode"
		    )
		    list(APPEND «network.simpleName»_OBJS ${_outpath})
		endforeach()

		add_executable(«network.simpleName» ${«network.simpleName»_OBJS} ${«network.simpleName»_SRCS})

		set_target_properties(«network.simpleName» PROPERTIES LINKER_LANGUAGE C)
		target_link_libraries(«network.simpleName» orcc-runtime orcc-native ${SDL_LIBRARY})
	'''
}