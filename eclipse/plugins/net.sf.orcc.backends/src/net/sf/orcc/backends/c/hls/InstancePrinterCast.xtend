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
import net.sf.orcc.df.Port

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

	def getFileContentWrite(Port port) '''
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
		
			// Input FIFOs
			extern stream<«instance.incomingPortMap.get(port).fifoTypeOut.doSwitch»> «instance.incomingPortMap.get(port).
			castfifoNameWrite»;
			// Output FIFOS
			extern «instance.incomingPortMap.get(port).fifoTypeIn.doSwitch»	«instance.incomingPortMap.get(port).ramName»[8192];
			extern unsigned int	«instance.incomingPortMap.get(port).wName»[1];
			extern unsigned int	«instance.incomingPortMap.get(port).rName»[1];
			unsigned int «instance.incomingPortMap.get(port).localwName»=0;					
				
		////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////
		// Actions
		static void cast_«instance.name»_«instance.incomingPortMap.get(port).targetPort.name»_write_untagged_0() {
			
					i32 «instance.incomingPortMap.get(port).maskName» = «instance.incomingPortMap.get(port).localwName» & 8191;
					«instance.incomingPortMap.get(port).fifoTypeOut.doSwitch» tmp_«instance.incomingPortMap.get(port).sourcePort.name»;
					«instance.incomingPortMap.get(port).castfifoNameWrite».read_nb(tmp_«instance.incomingPortMap.get(port).sourcePort.
			name»);
					«instance.incomingPortMap.get(port).ramName»[«instance.incomingPortMap.get(port).maskName»]=tmp_«instance.
			incomingPortMap.get(port).sourcePort.name» ;
					«instance.incomingPortMap.get(port).localwName» = «instance.incomingPortMap.get(port).localwName» +1;
					«instance.incomingPortMap.get(port).wName»[0] = «instance.incomingPortMap.get(port).localwName»;
			
		}
		
		static bool isSchedulable_untagged_0() {
		bool result;
		
		result = 1;
		return result;
		}		
		
		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		void cast_«instance.name»_«instance.incomingPortMap.get(port).targetPort.name»_write_scheduler() {		
			
					if (!«instance.incomingPortMap.get(port).castfifoNameWrite».empty() &&   
					isSchedulable_untagged_0()) {
					if(1
					&& (8192 - «instance.incomingPortMap.get(port).localwName» + «instance.incomingPortMap.get(port).rName»[0] >= 1)
					){
					cast_«instance.name»_«instance.incomingPortMap.get(port).targetPort.name»_write_untagged_0();
					}
					} else {
					goto finished;
					}		
					finished:
					return;
					}
			
	'''

	def getFileContentRead(Port portout) '''
			
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
			
		
				// Input FIFOS
				extern «instance.outgoingPortMap.get(portout).head.fifoTypeOut.doSwitch» «instance.outgoingPortMap.get(portout).
			head.ramName»[8192];
				extern unsigned int «instance.outgoingPortMap.get(portout).head.wName»[1];
				extern unsigned int «instance.outgoingPortMap.get(portout).head.rName»[1];
				unsigned int «instance.outgoingPortMap.get(portout).head.localrName»=0;
				// Output FIFOs
				extern stream<«instance.outgoingPortMap.get(portout).head.fifoTypeOut.doSwitch»> «instance.outgoingPortMap.get(
			portout).head.castfifoNameRead»;					
		
				////////////////////////////////////////////////////////////////////////////////
				// Actions
				static void cast_«instance.name»_«instance.outgoingPortMap.get(portout).head.sourcePort.name»_read_untagged_0() {
				
							i32 «instance.outgoingPortMap.get(portout).head.maskName» = «instance.outgoingPortMap.get(portout).head.
			localrName» & 8191;
							«instance.outgoingPortMap.get(portout).head.fifoTypeOut.doSwitch» tmp_«instance.outgoingPortMap.get(portout).
			head.targetPort.name»;
							tmp_«instance.outgoingPortMap.get(portout).head.targetPort.name» = «instance.outgoingPortMap.get(portout).head.
			ramName»[«instance.outgoingPortMap.get(portout).head.maskName»];
							«instance.outgoingPortMap.get(portout).head.castfifoNameRead».write_nb(tmp_«instance.outgoingPortMap.get(portout).
			head.targetPort.name»);
							«instance.outgoingPortMap.get(portout).head.localrName» = «instance.outgoingPortMap.get(portout).head.localrName» +1;
							«instance.outgoingPortMap.get(portout).head.rName»[0] = «instance.outgoingPortMap.get(portout).head.localrName»;
				
				}
				
				static bool isSchedulable_untagged_0() {
				bool result;
				
				result = 1;
				return result;
				}
				////////////////////////////////////////////////////////////////////////////////
				// Action scheduler
				void cast_«instance.name»_«instance.outgoingPortMap.get(portout).head.sourcePort.name»_read_scheduler() {		
						if («instance.outgoingPortMap.get(portout).head.wName»[0] - «instance.outgoingPortMap.get(portout).head.
			localrName» >= 1  &&
						isSchedulable_untagged_0()) {
						if(1
						&& (!«instance.outgoingPortMap.get(portout).head.castfifoNameRead».full())
						){
						cast_«instance.name»_«instance.outgoingPortMap.get(portout).head.sourcePort.name»_read_untagged_0();
						}
						} else {
						goto finished;
						}		
						finished:
						return;
						}
				
	'''

	override print(String targetFolder) {
		for (portIN : instance.getActor.inputs) {
			if (instance.incomingPortMap.get(portIN).sourcePort != null) {
				OrccUtil::printFile(getFileContentWrite(portIN),
					new File(
						targetFolder + File::separator + "cast_" + instance.name + "_" +
							instance.incomingPortMap.get(portIN).targetPort.name + "_write" + ".cpp"))
				OrccUtil::printFile(
					script(targetFolder,
						"cast_" + instance.name + "_" + instance.incomingPortMap.get(portIN).targetPort.name + "_write"),
					new File(
						targetFolder + File::separator + "script_" + "cast_" + instance.name + "_" +
							instance.incomingPortMap.get(portIN).targetPort.name + "_write" + ".tcl"
					))

			}
		}
		for (portout : instance.getActor.outputs.filter[! native]) {
			//for (connection : instance.outgoingPortMap.get(portout)) {
				if (instance.outgoingPortMap.get(portout).head.targetPort != null) {
					OrccUtil::printFile(getFileContentRead(portout),
						new File(
							targetFolder + File::separator + "cast_" + instance.name + "_" +
								instance.outgoingPortMap.get(portout).head.sourcePort.name + "_read" + ".cpp"))
					OrccUtil::printFile(
						script(targetFolder,
							"cast_" + instance.name + "_" +
								instance.outgoingPortMap.get(portout).head.sourcePort.name + "_read"),
						new File(
							targetFolder + File::separator + "script_" + "cast_" + instance.name + "_" +
								instance.outgoingPortMap.get(portout).head.sourcePort.name + "_read" + ".tcl"
						))
				}
			//}
		}
		return 0

	//			for (port : instance.getActor.inputs) {
	//				if (instance.incomingPortMap.get(port).sourcePort == null) {
	//					for (portout : instance.getActor.outputs.filter[! native]) {
	//						for (connection : instance.outgoingPortMap.get(portout)) {
	//							if (connection.targetPort == null) {
	//							} else {
	//	
	//								//stream+ram = ram+stream= read
	//								OrccUtil::printFile(getFileContentRead(portout),
	//									new File(
	//										targetFolder + File::separator + "cast_" + instance.name + instance.name + "_" +
	//											instance.outgoingPortMap.get(portout).head.sourcePort.name + "_read" + ".cpp"))
	//	
	//							}
	//						}
	//					}
	//				} else {
	//	
	//					//ram+stream=stream+ram= write
	//					for (portout : instance.getActor.outputs.filter[! native]) {
	//						for (connection : instance.outgoingPortMap.get(portout)) {
	//							if (connection.targetPort == null) {
	//								for (portIN : instance.getActor.inputs) {
	//									if (instance.incomingPortMap.get(portIN).sourcePort != null) {
	//										OrccUtil::printFile(getFileContentWrite(portIN),
	//											new File(
	//												targetFolder + File::separator + "cast_" + instance.name + "_" +
	//													instance.incomingPortMap.get(portIN).targetPort.name + "_write" + ".cpp"))
	//									}
	//	
	//								}
	//							} else {
	//	
	//								//ram+ram= read+write
	//								for (portin : instance.getActor.inputs) {
	//									if (instance.incomingPortMap.get(port).sourcePort == null) {
	//	
	//										OrccUtil::printFile(getFileContentWrite(portin),
	//											new File(
	//												targetFolder + File::separator + "cast_" + instance.name + "_" +
	//													instance.incomingPortMap.get(port).targetPort.name + "_write" + ".cpp"))
	//										OrccUtil::printFile(getFileContentRead(portout),
	//											new File(
	//												targetFolder + File::separator + "cast_" + instance.name + "_" +
	//													instance.outgoingPortMap.get(portout).head.sourcePort.name + "_read" +
	//													".cpp"))
	//									}
	//								}
	//	
	//							}
	//						}
	//					}
	//				}
	//			}
	//			return 0
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

	def script(String path, String Instname) '''
		
		open_project -reset subProject_«Instname»
		set_top «Instname»_scheduler
		add_files «Instname».cpp
		add_files -tb «Instname»TestBench.cpp
		open_solution -reset "solution1"
		set_part  {xc7vx330tffg1157-2}
		create_clock -period 20
		
		
		csynth_design
		exit
	'''

}
