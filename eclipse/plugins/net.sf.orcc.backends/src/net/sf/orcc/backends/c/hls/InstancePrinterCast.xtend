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

import net.sf.orcc.util.OrccUtil
import java.io.File

import net.sf.orcc.df.Connection

import java.util.Map

/*
 * Compile Instance c source code
 *  
 * @author Antoine Lorence and Khaled Jerbi 
 * 
 */
class InstancePrinterCast extends net.sf.orcc.backends.c.InstancePrinter {

	new(Map<String, Object> options) {
		super(options)
	}

	def getFileContentWrite() '''
		#include <hls_stream.h>
		using namespace hls;
		#include <stdio.h>
		#include <stdlib.h>
		
		typedef signed char i8;
		typedef short i16;
		typedef int i32;
		typedef long long int i64;
		
		typedef unsigned char u8;
		typedef unsigned short u16;
		typedef unsigned int u32;
		typedef unsigned long long int u64;			
		////////////////////////////////////////////////////////////////////////////////
		«FOR port : instance.getActor.inputs»			
			// Input FIFOs
			extern stream<«instance.incomingPortMap.get(port).fifoTypeOut.doSwitch»> «instance.incomingPortMap.get(port).
			castfifoNameWrite»;
			// Output FIFOS
			extern «instance.incomingPortMap.get(port).fifoTypeIn.doSwitch»	«instance.incomingPortMap.get(port).ramName»[512];
			extern unsigned int	«instance.incomingPortMap.get(port).wName»[1];
			extern unsigned int	«instance.incomingPortMap.get(port).rName»[1];
			unsigned int «instance.incomingPortMap.get(port).localwName»=0;					
		«ENDFOR»				
		////////////////////////////////////////////////////////////////////////////////
		«IF instance.getActor.outputs.empty»
			extern stream<int> outFIFO_«instance.name»;
		«ENDIF»
		////////////////////////////////////////////////////////////////////////////////
		// Actions
		static void cast_«instance.name»_write_untagged_0() {
			«FOR port : instance.getActor.inputs»			
				«IF instance.incomingPortMap.get(port).sourcePort != null»
					i32 «instance.incomingPortMap.get(port).maskName» = «instance.incomingPortMap.get(port).localwName» & 511;
					«instance.incomingPortMap.get(port).fifoTypeOut.doSwitch» tmp_«instance.incomingPortMap.get(port).sourcePort.name»;
					«instance.incomingPortMap.get(port).castfifoNameWrite».read_nb(tmp_«instance.incomingPortMap.get(port).sourcePort.
			name»);
					«instance.incomingPortMap.get(port).ramName»[«instance.incomingPortMap.get(port).maskName»]=tmp_«instance.
			incomingPortMap.get(port).sourcePort.name» ;
					«instance.incomingPortMap.get(port).localwName» = «instance.incomingPortMap.get(port).localwName» +1;
					«instance.incomingPortMap.get(port).wName»[0] = «instance.incomingPortMap.get(port).localwName»;
				«ENDIF»
			«ENDFOR»	
		}
		
		static bool isSchedulable_untagged_0() {
		bool result;
		
		result = 1;
		return result;
		}		
		
		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		void cast_«instance.name»_write_scheduler() {		
			«FOR port : instance.getActor.inputs»			
				«IF instance.incomingPortMap.get(port).sourcePort != null»
					if (! «instance.incomingPortMap.get(port).castfifoNameWrite».empty()) &&   
					isSchedulable_untagged_0()) {
					if(1
					&& (512 - «instance.incomingPortMap.get(port).localwName» + «instance.incomingPortMap.get(port).rName»[0] >= 1)
					){
					cast_«instance.name»_write_untagged_0();
					}
					} else {
					goto finished;
					}		
					finished:
					return;
					}
				«ENDIF»
			«ENDFOR»	
	'''

	def getFileContentRead() '''
				
				#include <hls_stream.h>
				using namespace hls;
				#include <stdio.h>
				#include <stdlib.h>
				
				typedef signed char i8;
				typedef short i16;
				typedef int i32;
				typedef long long int i64;
				
				typedef unsigned char u8;
				typedef unsigned short u16;
				typedef unsigned int u32;
				typedef unsigned long long int u64;			
				////////////////////////////////////////////////////////////////////////////////
							
				«FOR portout : instance.getActor.outputs.filter[! native]»
				«FOR connection : instance.outgoingPortMap.get(portout)»
					// Input FIFOS
					extern «connection.fifoTypeOut.doSwitch» «connection.ramName»[512];
					extern unsigned int «connection.wName»[1];
					extern unsigned int «connection.rName»[1];
					unsigned int «connection.localrName»=0;
					// Output FIFOs
					extern stream<«connection.fifoTypeOut.doSwitch»> «connection.castfifoNameRead»;					
				«ENDFOR»
				«ENDFOR»
			////////////////////////////////////////////////////////////////////////////////
			«IF instance.getActor.outputs.empty»
			extern stream<int> outFIFO_«instance.name»;
			«ENDIF»
			////////////////////////////////////////////////////////////////////////////////
			// Actions
			static void cast_«instance.name»_read_untagged_0() {
				«FOR port : instance.getActor.outputs.filter[! native]»
					«IF instance.outgoingPortMap.get(port).head.targetPort != null»
						i32 «instance.outgoingPortMap.get(port).head.maskName» = «instance.outgoingPortMap.get(port).head.localrName» & 511;
						«instance.outgoingPortMap.get(port).head.fifoTypeOut.doSwitch» tmp_«instance.outgoingPortMap.get(port).head.
						targetPort.name»;
						tmp_«instance.outgoingPortMap.get(port).head.targetPort.name» = «instance.outgoingPortMap.get(port).head.ramName»[«instance.
						outgoingPortMap.get(port).head.maskName»];
						«instance.outgoingPortMap.get(port).head.castfifoNameRead».write_nb(tmp_«instance.outgoingPortMap.get(port).head.targetPort.name»);
						«instance.outgoingPortMap.get(port).head.localrName» = «instance.outgoingPortMap.get(port).head.localrName» +1;
						«instance.outgoingPortMap.get(port).head.rName»[0] = «instance.outgoingPortMap.get(port).head.localrName»;
					«ENDIF»
				«ENDFOR»
			}
			
			static bool isSchedulable_untagged_0() {
			bool result;
			
			result = 1;
			return result;
			}
			////////////////////////////////////////////////////////////////////////////////
			// Action scheduler
			void cast_«instance.name»_read_scheduler() {		
			«FOR portout : instance.getActor.outputs.filter[! native]»
			«FOR connection : instance.outgoingPortMap.get(portout)»
				«IF connection.targetPort != null»
					if («connection.wName»[0] - «connection.localrName» >= 1  &&
					isSchedulable_untagged_0()) {
					if(1
					&& (! «connection.castfifoNameRead».full())
					){
					cast_«instance.name»_read_untagged_0();
					}
					} else {
					goto finished;
					}		
					finished:
					return;
					}
				«ENDIF»
			«ENDFOR»
			«ENDFOR»
			
	'''

	override print(String targetFolder) {
		val contentRead = getFileContentRead
		val fileRead = new File(targetFolder + File::separator + "cast_" + instance.name + "_read" + ".cpp")
		val contentWrite = getFileContentWrite
		val fileWrite = new File(targetFolder + File::separator + "cast_" + instance.name + "_write" + ".cpp")
		
		for (port : instance.getActor.inputs) {
			if (instance.incomingPortMap.get(port).sourcePort == null) {
				for (portout : instance.getActor.outputs.filter[! native]) {
					for (connection : instance.outgoingPortMap.get(portout)) {
						if (connection.targetPort == null) {
						} else {
							//stream+ram = ram+stream= read
							OrccUtil::printFile(contentRead, fileRead)
						}
					}
				}
			} else {
				//ram+stream=stream+ram= write
				for (portout : instance.getActor.outputs.filter[! native]) {
					for (connection : instance.outgoingPortMap.get(portout)) {
						if (connection.targetPort == null) {
							OrccUtil::printFile(contentWrite, fileWrite)
						} else {
							//ram+ram= read+write
							OrccUtil::printFile(contentRead, fileRead)
							OrccUtil::printFile(contentWrite, fileWrite)
						}
					}
				}
			}
			return 0
		}


	}

	def castfifoNameWrite(Connection connection) '''«IF connection != null»myStream_cast_«connection.getAttribute("id").
		objectValue»_write«ENDIF»'''

	def castfifoNameRead(Connection connection) '''«IF connection != null»myStream_cast_«connection.getAttribute("id").
		objectValue»_read«ENDIF»'''

	def ramName(Connection connection) '''«IF connection != null»tab_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def wName(Connection connection) '''«IF connection != null»writeIdx_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def localwName(Connection connection) '''«IF connection != null»wIdx_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def localrName(Connection connection) '''«IF connection != null»rIdx_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def rName(Connection connection) '''«IF connection != null»readIdx_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def maskName(Connection connection) '''«IF connection != null»mask_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def fifoTypeOut(Connection connection) {
		if (connection.sourcePort == null) {
			connection.targetPort.type
		} else {
			connection.sourcePort.type
		}
	}

	def fifoTypeIn(Connection connection) {
		if (connection.targetPort == null) {
			connection.sourcePort.type
		} else {
			connection.targetPort.type
		}
	}

}
