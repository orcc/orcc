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
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.ir.TypeBool

/**
 * Compile top Network c source code 
 *  
 * @author Antoine Lorence and Khaled Jerbi
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

		typedef signed char i8;
		typedef short i16;
		typedef int i32;
		typedef long long int i64;
		
		typedef unsigned char u8;
		typedef unsigned short u16;
		typedef unsigned int u32;
		typedef unsigned long long int u64;
		
		/////////////////////////////////////////////////
		// FIFO pointer assignments
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.assignFifo»
		«ENDFOR»
		
		
		
		/////////////////////////////////////////////////
		// Actions initializes
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«IF (!instance.actor.stateVars.empty) || (instance.actor.hasFsm )»
				void «instance.name»_initialize();
			«ENDIF»
		«ENDFOR»
		
		// Actions schedulers
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			void «instance.name»_scheduler(«instance.assignAllInstanceFifoDeclaration»);
		«ENDFOR»
		
		// main loop definition
		void loopModule(«network.children.filter(typeof(Instance)).filter[isActor].filter[! (it.incomingPortMap.empty)].join(",", [assignAllFifoDeclaration])»){
			while(1) {
				«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
					«instance.name»_scheduler(«instance.assignInstanceFifoUse»);
				«ENDFOR»
			}
		}
		////////////////////////////////////////////////////////////////////////////////
		// Main
		int main() {
			«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
				«IF (!instance.actor.stateVars.empty) || (instance.actor.hasFsm )»
					 «instance.name»_initialize();
				«ENDIF»
			«ENDFOR»
			loopModule(«network.children.filter(typeof(Instance)).filter[isActor].filter[! (it.incomingPortMap.empty)].join(",",[assignFifoUse])»);
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
	override assignFifo(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF (!(connList.head.source instanceof Port) && !(connList.head.target instanceof Port))||(!(connList.head.source instanceof Port) && (connList.head.target instanceof Port))»
				«printFifoAssignHLS(connList.head )»
			«ENDIF»			
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF ((connList.source instanceof Port) && !(connList.target instanceof Port))»
				«printFifoAssignHLS(connList)»
			«ENDIF»
		«ENDFOR»
	'''
	
	def printFifoAssignHLS(Connection connection) '''
		static stream<«connection.fifoType.doSwitch»> «connection.fifoName»;
	'''
	
	override print(String targetFolder) {
		val i = super.print(targetFolder)
		val contentProject = projectFileContent
		val contentNetwork = networkFileContent
		val contentTestBench = "int test() { return 0;}"
		val projectFile = new File(targetFolder + File::separator + "vivado_hls.app")
		val NetworkFile = new File(targetFolder + File::separator + network.simpleName + ".cpp")
		val testBenchFile = new File(targetFolder + File::separator + "testBench" + ".cpp")
		if(needToWriteFile(contentNetwork, NetworkFile)) {
			printFile(contentProject, projectFile)
			printFile(contentNetwork, NetworkFile)
			printFile(contentTestBench, testBenchFile)
			return i
		} else {
			return i + 1
		}
	}
	
	def fifoName(Connection connection)
		'''myStream_«connection.getAttribute("id").objectValue»'''
	
	def fifoType(Connection connection) {
		if (connection.sourcePort != null){	
			connection.sourcePort.type
		}else{
			connection.targetPort.type
		}
	}
		
	override caseTypeBool(TypeBool type) 
		'''bool'''
		
	def assignAllFifoDeclaration(Instance instance) {
		val inList = instance.incomingPortMap.values
		val outList = instance.outgoingPortMap.values.map[head].filter[target instanceof Port]
		
		'''«(inList + outList).join(",", [printFifoAssignDeclarationHLS])»'''
	}
	
	def assignAllInstanceFifoDeclaration(Instance instance) {
		val inList = instance.incomingPortMap.values
		val outList = instance.outgoingPortMap.values.map[head]
		
		'''«(inList + outList).join(",", [printFifoAssignDeclarationHLS])»'''
	}
		
	def printFifoAssignDeclarationHLS(Connection connection) 
	'''stream<«connection.fifoType.doSwitch»>& «connection.fifoName»'''
	
	def assignFifoUse(Instance instance) {
		val inList = instance.incomingPortMap.values
		val outList = instance.outgoingPortMap.values.map[head].filter[ (target instanceof Port)]
		
		return '''«(inList + outList).join(",", [printFifoAssignUSEHLS])»'''
	}
	
	def assignInstanceFifoUse(Instance instance) {
		val inList = instance.incomingPortMap.values
		val outList = instance.outgoingPortMap.values.map[head]
		
		return '''«(inList + outList).join(",", [printFifoAssignUSEHLS])»'''
	}
	
	def printFifoAssignUSEHLS(Connection connection) 
	'''«connection.fifoName»'''
}