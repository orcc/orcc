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

import net.sf.orcc.df.Instance
import java.util.Map
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Port
import java.io.File
import net.sf.orcc.ir.TypeBool

/**
 * generates testbench for for vivado co-simulation
 *  
 * @author Khaled Jerbi
 * 
 */
 
 
 class InstanceTestBenchPrinter extends net.sf.orcc.backends.c.InstancePrinter {

	new(Instance benchInstance, Map<String, Object> options) {
		super(benchInstance, options)
	}

	override getInstanceFileContent() '''
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
		
		«FOR port : instance.actor.inputs»
			«IF instance.incomingPortMap.get(port) != null»
				stream<«instance.incomingPortMap.get(port).fifoType.doSwitch»>	«instance.incomingPortMap.get(port).fifoName»;
				int counter_«instance.incomingPortMap.get(port).fifoName»
				«instance.incomingPortMap.get(port).fifoType.doSwitch» tab_«instance.incomingPortMap.get(port).fifoName»
				«instance.incomingPortMap.get(port).fifoType.doSwitch» tmp_«instance.incomingPortMap.get(port).fifoName»
			«ENDIF»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Output FIFOs
		
		«FOR port : instance.actor.outputs.filter[! native]»
			«FOR connection : instance.outgoingPortMap.get(port)»
				stream<«connection.fifoType.doSwitch»> «connection.fifoName»;
				int counter_«connection.fifoName»
				«connection.fifoType.doSwitch» tab_«connection.fifoName»
				«connection.fifoType.doSwitch» tmp_«connection.fifoName»
			«ENDFOR»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// functions definition
		
		«instance.name»_scheduler();
		
		////////////////////////////////////////////////////////////////////////////////
		
		int main{
			
			FILE *fp
			int i;
			
			// read data
			«FOR port : instance.actor.inputs»
				«IF instance.incomingPortMap.get(port) != null»
					fp=fopen("«instance.name».txt","r");
					for (i=0 ; i<1000 ; i++){
						fscanf(fp, "%d", &tmp_«instance.incomingPortMap.get(port).fifoName»);
						tab_«instance.incomingPortMap.get(port).fifoName»[i]=tmp_«instance.incomingPortMap.get(port).fifoName»;
						
					}
					fclose(fp);
				«ENDIF»
			«ENDFOR»
			// scheduler execution
			for (i=0 ; i<1000 ; i++){
				«FOR port : instance.actor.inputs»
					«IF instance.incomingPortMap.get(port) != null»
						if(!«instance.incomingPortMap.get(port).fifoName».full()){
						«instance.incomingPortMap.get(port).fifoName».write(tab_«instance.incomingPortMap.get(port).fifoName»[counter_«instance.incomingPortMap.get(port).fifoName»]);
						counter_«instance.incomingPortMap.get(port).fifoName» ++;
						}
					«ENDIF»
				«ENDFOR»
				
				«instance.name»_scheduler();
				
				«FOR port : instance.actor.outputs.filter[! native]»
					«FOR connection : instance.outgoingPortMap.get(port)»
						if(!«connection.fifoName».empty()){
							«connection.fifoName».read(tab_«connection.fifoName»[counter_«connection.fifoName»]);
							counter_«connection.fifoName» ++;
						}
					«ENDFOR»
				«ENDFOR»
			}
			// write results	
			«FOR port : instance.actor.outputs.filter[! native]»
				«FOR connection : instance.outgoingPortMap.get(port)»
					fp=fopen("«instance.name».txt","r");
					for (i=0 ; i<1000 ; i++){
						tmp_«connection.fifoName»=tab_«connection.fifoName»[i];
						fprintf(fp, "%d \n", tmp_«connection.fifoName»);
						
					}
					fclose(fp);
				«ENDFOR»
			«ENDFOR»
			
			return 0;
		}
		
	'''	
	override printInstance(String targetFolder) {
		val content = instanceFileContent
		val file = new File(targetFolder + File::separator + instance.name+ "_tb" + ".vhd")
		
		if(needToWriteFile(content, file)) {
			printFile(content, file)
			return 0
		} else {
			return 1
		}
	}
	
	def fifoType(Connection connection) {
		if(connection.sourcePort == null){
		connection.targetPort.type}
		else{
			connection.sourcePort.type
		}
	}
	
	def fifoName(Connection connection) '''
		«IF connection != null»
			myStream_«connection.getAttribute("id").objectValue»
		«ENDIF»
	'''
	
	override caseTypeBool(TypeBool type) 
	'''bool'''
}