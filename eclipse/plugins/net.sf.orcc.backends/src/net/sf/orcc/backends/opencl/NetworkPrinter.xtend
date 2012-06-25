/*
 * Copyright (c) 2012, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.opencl

import net.sf.orcc.backends.opencl.BasePrinter
import net.sf.orcc.df.Network
import java.util.Map
import java.util.Date
import java.text.SimpleDateFormat
import net.sf.orcc.df.Instance

/*
 * The Nework Printer
 * 
 * @author Endri Bezati
 */
class NetworkPrinter extends BasePrinter {
	def printNetwork(Network network, Map options){ 
		var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		var date = new Date();
		'''
		// ////////////////////////////////////////////////////////////////////////////
		// EPFL - OpenCL Backend
		// --
		// Date :  «dateFormat.format(date)»
		// Network Source File: "«network.file»" 
		// Network: «network.simpleName»
		// ////////////////////////////////////////////////////////////////////////////
		#include "CAL/DeviceManager.hpp"


		int main(int argc, char** argv){
			// Intialize the context of an OpenCL GPU device
			DeviceManager deviceManager = DeviceManager(CL_DEVICE_TYPE_GPU);
			
			return 0;
		}
		'''
	}
	
	def printCmakeLists(Network network, Map options){
		'''
		# EPFL OpenCL Backend, CMakeLists.txt file
		# Generated from "«network.file»"
		cmake_minimum_required (VERSION 2.8)
		project («network.simpleName»)
		
		# Find OpenCL and GLEW
		set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake")
		
		find_package( OpenCL REQUIRED )

		include_directories(  ${OPENCL_INCLUDE_DIRS} )
		include_directories( "${CMAKE_SOURCE_DIR}/include" )
		
		add_executable(«network.simpleName»
		src/CAL/DeviceManager.cpp
		src/«network.simpleName».cpp
		«FOR vertex : network.vertices»
		«IF vertex instanceof Instance»
		«instanceName(vertex as Instance)»
		«ENDIF»
		«ENDFOR»
		)
		target_link_libraries( «network.simpleName» ${OPENCL_LIBRARIES} )
		'''	
	}
	
	def instanceName(Instance instance){
		'''src/«instance.simpleName».cpp'''
	}
}