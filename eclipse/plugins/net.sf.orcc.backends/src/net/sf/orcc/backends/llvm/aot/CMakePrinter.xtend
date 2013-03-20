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

import java.io.File
import java.util.Map
import net.sf.orcc.backends.CommonPrinter
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil

/**
 * Generate CMakeList.txt content
 * 
 * @author Antoine Lorence
 */
class CMakePrinter extends CommonPrinter {
	
	Network network;

	new(Network network, Map<String, Object> options) {
		this.network = network;
	}
	
	def printFiles(String targetFolder) {
		
		var int cachedFiles = 0
				
		var content = rootCMakeContent
		var file = new File(targetFolder + File::separator + "CMakeLists.txt")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
		} else {
			cachedFiles = cachedFiles + 1
		}
		
		content = srcCMakeContent
		file = new File(targetFolder + File::separator + "src" + File::separator + "CMakeLists.txt")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
		} else {
			cachedFiles = cachedFiles + 1
		}
		
		return cachedFiles
	}
	
	/**
	 * Return CMakeList's content to write in the root target folder
	 */
	def private rootCMakeContent() '''
		# Generated from «network.simpleName»
		cmake_minimum_required (VERSION 2.6)
		
		set(CMAKE_C_COMPILER "clang")
		
		project («network.simpleName» C)
		
		# Parse libraries
		add_subdirectory(libs)
		
		# Configure build types specific flags for clang
		# set (CMAKE_C_FLAGS_RELEASE "-O4 -DNDEBUG")
		if("${CMAKE_BUILD_TYPE}" STREQUAL "")
		    set(CLANG_FLAGS "")
		else(${CMAKE_BUILD_TYPE})
		    string(TOUPPER ${CMAKE_BUILD_TYPE} CMBT)
		    set(CLANG_FLAGS ${CMAKE_C_FLAGS_${CMBT}})
		endif()
		
		message(STATUS "Clang FLAGS : " ${CLANG_FLAGS})
		separate_arguments(CLANG_FLAGS)
		
		# Output folder
		set(EXECUTABLE_OUTPUT_PATH ${CMAKE_SOURCE_DIR}/bin)
		
		add_subdirectory(src)
	'''
	
	/**
	 * Return CMakeList's content to write in the src subdirectory
	 */
	def private srcCMakeContent() '''
		# Generated from «network.simpleName»
		
		cmake_minimum_required (VERSION 2.6)
		
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
		        DEPENDS ${_inpath}
		        COMMAND ${CMAKE_C_COMPILER} ${CLANG_FLAGS} -c ${_inpath} -o ${_outpath}
		        COMMENT "Building LLVM object ${_outfile}"
		    )
		    set(«network.simpleName»_OBJS ${«network.simpleName»_OBJS} ${_outpath})
		endforeach()
		
		add_executable(«network.simpleName» ${«network.simpleName»_OBJS})
		
		set_target_properties(«network.simpleName» PROPERTIES LINKER_LANGUAGE C)
		target_link_libraries(«network.simpleName» orcc ${SDL_LIBRARY})
	'''
}