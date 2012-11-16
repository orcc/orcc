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
package net.sf.orcc.backends.c.hls

import java.io.File
import java.util.Map
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.graph.Vertex

/**
 * Compile top Network c source code 
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends net.sf.orcc.backends.c.NetworkPrinter {

	new(Network network, Map<String,Object> options) {
		super(network, options)
	}

	override getNetworkFileContent() '''
		// Generated from "«network.name»"

		#include <hls_stream.h>
		using namespace hls;

		
		/////////////////////////////////////////////////
		// FIFO pointer assignments
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.assignFifoHLS»
		«ENDFOR»
		
		
		
		/////////////////////////////////////////////////
		// Action schedulers
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			void «instance.name»_scheduler();
		«ENDFOR»
		
		
		////////////////////////////////////////////////////////////////////////////////
		// Main
		int main() {
			
			while(1) {
				«FOR instance : network.children.filter(typeof(Instance))»
					«IF instance.isActor»
						«instance.name»_scheduler();
					«ENDIF»
				«ENDFOR»
			}
			return 0;
		}
	'''
	
	def getProjectFileContent() '''
		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="com.autoesl.autopilot.project" top="main" projectType="C/C++">
		  <files>
		    <file name="src/«network.simpleName».c" sc="0" tb="false" cflags=""/>
		    «FOR instance : network.children.filter(typeof(Instance))»
		    	<file name="src/«instance.name».c" sc="0" tb="false" cflags=""/>
		    «ENDFOR»
		  </files>
		  <solutions>
		    <solution name="solution1" status="active"/>
		  </solutions>
		  <includePaths/>
		  <libraryPaths/>
		</project>
	'''
	def assignFifoHLS(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && !(connList.head.target instanceof Port)»
				«printFifoAssignHLS(connList.head.source, connList.head.sourcePort, connList.head.<Integer>getValueAsObject("idNoBcast"))»
			«ENDIF»
			
		«ENDFOR»
	'''
	
	def printFifoAssignHLS(Vertex vertex, Port port, int fifoIndex) '''
		«IF vertex instanceof Instance»stream<«port.type.doSwitch»> «(vertex as Instance).name»_«port.name»;«ENDIF»
	'''
	override print(String targetFolder) {
		val i = super.print(targetFolder)
		val content = projectFileContent
		val file = new File(targetFolder + File::separator + "vivado_hls.app")
		
		if(needToWriteFile(content, file)) {
			printFile(content, file)
			return i
		} else {
			return i + 1
		}
	}

}