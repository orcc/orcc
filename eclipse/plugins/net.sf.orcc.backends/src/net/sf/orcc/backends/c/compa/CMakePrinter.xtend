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
package net.sf.orcc.backends.c.compa

class CMakePrinter {

//	override public addLibrariesSubdirs() '''
//		# Prevent usage of SDL or pthread libraries in library built
//		set(NO_EXTERNAL_DEPENDENCIES True)
//		
//		# Compile required libs
//		add_subdirectory(libs)
//		# Compile application
//		add_subdirectory(src)
//	'''
//
//	override protected srcCMakeContent() '''
//		# Generated from «network.simpleName»
//
//		set(filenames
//«««			«network.simpleName».c
//			«FOR child : network.children.actorInstances.filter[!getActor.native]»
//				«child.label».c
//			«ENDFOR»
//			«FOR child : network.children.filter(typeof(Actor)).filter[!native]»
//				«child.label».c
//			«ENDFOR»
//		)
//
//		add_executable(«network.simpleName» ${filenames})
//
//		# Build library without any external library required
//		target_link_libraries(«network.simpleName» orcc)
//	'''

//	def protected srcCMakeContent(Instance instance) '''
//		# Generated from «instance.simpleName»
//
//		set(filenames
//			«instance.simpleName».c
//		)
//
//		add_executable(«instance.simpleName» ${filenames})
//
//		# Build library without any external library required
//		target_link_libraries(«instance.simpleName» orcc)
//	'''

	def rootCMakeContent(String entityName) '''
		# Generated from «entityName»

		cmake_minimum_required (VERSION 2.6)

		project («entityName»)

		# Include the subdirectory where cmake modules can be found.
		set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/../cmake/Modules/")
		
		# Tries to find the ORCC library.
		find_package(LibORCC REQUIRED)

		# Tries to find the Xilinx libraries.
		find_package(LibXil REQUIRED)
		
		# Runtime libraries inclusion
		include_directories(${LibORCC_INCLUDE_DIRS} ${LibXil_INCLUDE_DIRS})
		set(LIBS ${LIBS} ${LibORCC_LIBRARIES} ${LibXil_LIBRARIES})
		
		set(filenames
			«entityName».c
			platform.c
		)
		
		add_executable(«entityName».elf ${filenames})
		
		# Link to orcc library.
		TARGET_LINK_LIBRARIES(«entityName».elf ${LIBS})
	'''
}