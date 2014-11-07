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

import java.io.File
import java.util.Map
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil
import net.sf.orcc.df.Entity

/**
 * Generate and print network source file for COMPA backend.
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends net.sf.orcc.backends.c.NetworkPrinter {
//	int memoryBaseAddr = 0x30000000
	int memoryBaseAddr = 0xc0000000
	int maxNbOutputPorts = 10
	int maxTraceBuffSize = 1024 
	int tracesBuffsBaseAddr = 0x20000000
	
	new(Network network, Map<String, Object> options, int fAddr) {
		super(network, options)
		memoryBaseAddr = fAddr;
	}
	
	override protected getNetworkFileContent() '''
		// Generated from "«network.name»"

		#include <locale.h>
		#include <stdio.h>
		#include <stdlib.h>
		
		#ifndef _WIN32
		#define __USE_GNU
		#endif
		
		#include "fifoAllocations.h"
		#include "util.h"
		
«««		#define SIZE «fifoSize»
«««		// #define PRINT_FIRINGS
«««
«««		/////////////////////////////////////////////////
«««		// FIFO allocation
«««		«FOR vertice : network.children.actorInstances»
«««			«vertice.allocateFifos»
«««		«ENDFOR»
«««		
«««		/////////////////////////////////////////////////
«««		// FIFO pointer assignments
«««		«FOR instance : network.children.actorInstances»
«««			«instance.assignFifo»
«««		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Action initializes
		«FOR child : network.children»
			extern void «child.label»_initialize();
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Action schedulers
		«FOR child : network.children»
			extern int «child.label»_scheduler();
		«ENDFOR»

		/////////////////////////////////////////////////
		// Actor scheduler
		static void scheduler() {
			int stop = 0;

			«FOR child : network.children»
				«child.label»_initialize();
			«ENDFOR»

			int i;
			while(!stop) {
				i = 0;
				«FOR child : network.children»
					i += «child.label»_scheduler();
				«ENDFOR»

				stop = stop || (i == 0);
			}
		}

		////////////////////////////////////////////////////////////////////////////////
		// Main
		int main(int argc, char *argv[]) {
«««			init_orcc(argc, argv);

			scheduler();

			printf("End of simulation !\n");
			return compareErrors;
		}
	'''

	def printFifoFile(String targetFolder){
		val content = fifoFileContent
		val file = new File(targetFolder + File::separator + "fifoAllocations.h")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def printTracesDefsFile(String targetFolder){
		val content = tracesDefsFileContent
		val file = new File(targetFolder + File::separator + "tracesDefs.h")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def private getFifoFileContent()'''
		// Generated from "«network.name»"

		#include "types.h"
		#include "fifo.h"
		
		#define SIZE «fifoSize»
		// #define PRINT_FIRINGS

		/////////////////////////////////////////////////
		// FIFO allocation
		«FOR child : network.children»
			«child.allocateFifos»
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// FIFO pointer assignments
		«FOR child : network.children»
			«child.assignFifo»
		«ENDFOR»		
		
	'''

	def private getTracesDefsFileContent()'''
		// Generated from "«network.name»"
		#define MAX_NB_OUT_PORTS		«maxNbOutputPorts»
		#define MAX_TRACE_BUFF_SIZE		«maxTraceBuffSize» // int elements
		#define NB_ACTORS				«network.children.length»
		#define TRACES_BUFFS_START_ADDR	«String.format("0x%x", tracesBuffsBaseAddr)»
		
		typedef struct{
			int values[MAX_NB_OUT_PORTS][MAX_TRACE_BUFF_SIZE];
			int indices[MAX_NB_OUT_PORTS];						// Set to 0s by the ARM.
		}TRACES_BUFF;
		static TRACES_BUFF* tracesBuffs = (TRACES_BUFF*)TRACES_BUFFS_START_ADDR;
				
		static const char* tracesNames[NB_ACTORS][MAX_NB_OUT_PORTS] = {
			«FOR child : network.children»
				{
				«FOR port : child.getAdapter(typeof(Entity)).outputs»
						"/traces/«child.getAdapter(typeof(Entity)).name»_«port.name».txt",
				«ENDFOR»
				},
			«ENDFOR»
		};
		
		static int compareWithTraces_u8(int actorIx, int portIx, unsigned int fifoWrIx, u8 *fifoTokens, unsigned int fifoSize){
			int trace_value;
			int* trace_ix;
			int fifo_value;
		
			trace_ix = &tracesBuffs[actorIx].indices[portIx];
			while((*trace_ix < fifoWrIx)&&(*trace_ix < MAX_TRACE_BUFF_SIZE)){
				trace_value = tracesBuffs[actorIx].values[portIx][*trace_ix];
				fifo_value = fifoTokens[*trace_ix % fifoSize];
				if (trace_value != fifo_value){
					return 0;
				}
				(*trace_ix)++;
			}
			return 1;
		}
		
		static int compareWithTraces_u16(int actorIx, int portIx, unsigned int fifoWrIx, u16 *fifoTokens, unsigned int fifoSize){
			int trace_value;
			int* trace_ix;
			int fifo_value;
		
			trace_ix = &tracesBuffs[actorIx].indices[portIx];
			while((*trace_ix < fifoWrIx)&&(*trace_ix < MAX_TRACE_BUFF_SIZE)){
				trace_value = tracesBuffs[actorIx].values[portIx][*trace_ix];
				fifo_value = fifoTokens[*trace_ix % fifoSize];
				if (trace_value != fifo_value){
					return 0;
				}
				(*trace_ix)++;
			}
			return 1;
		}
		
		
		static int compareWithTraces_i8(int actorIx, int portIx, unsigned int fifoWrIx, i8 *fifoTokens, unsigned int fifoSize){
			int trace_value;
			int* trace_ix;
			int fifo_value;
		
			trace_ix = &tracesBuffs[actorIx].indices[portIx];
			while((*trace_ix < fifoWrIx)&&(*trace_ix < MAX_TRACE_BUFF_SIZE)){
				trace_value = tracesBuffs[actorIx].values[portIx][*trace_ix];
				fifo_value = fifoTokens[*trace_ix % fifoSize];
				if (trace_value != fifo_value){
					return 0;
				}
				(*trace_ix)++;
			}
			return 1;
		}
		
		static int compareWithTraces_i16(int actorIx, int portIx, unsigned int fifoWrIx, i16 *fifoTokens, unsigned int fifoSize){
			int trace_value;
			int* trace_ix;
			int fifo_value;
		
			trace_ix = &tracesBuffs[actorIx].indices[portIx];
			while((*trace_ix < fifoWrIx)&&(*trace_ix < MAX_TRACE_BUFF_SIZE)){
				trace_value = tracesBuffs[actorIx].values[portIx][*trace_ix];
				fifo_value = fifoTokens[*trace_ix % fifoSize];
				if (trace_value != fifo_value){
					return 0;
				}
				(*trace_ix)++;
			}
			return 1;
		}
		
		static int compareWithTraces_i32(int actorIx, int portIx, unsigned int fifoWrIx, i32 *fifoTokens, unsigned int fifoSize){
			int trace_value;
			int* trace_ix;
			int fifo_value;
		
			trace_ix = &tracesBuffs[actorIx].indices[portIx];
			while((*trace_ix < fifoWrIx)&&(*trace_ix < MAX_TRACE_BUFF_SIZE)){
				trace_value = tracesBuffs[actorIx].values[portIx][*trace_ix];
				fifo_value = fifoTokens[*trace_ix % fifoSize];
				if (trace_value != fifo_value){
					return 0;
				}
				(*trace_ix)++;
			}
			return 1;
		}

	'''

	 override protected allocateFifo(Connection conn, int nbReaders) {
	  	val size = if (conn.size != null) conn.size else fifoSize
		val id = conn.<Object>getValueAsObject("idNoBcast")
	  	val portSizeInBytes = if (conn.sourcePort.type.sizeInBits == 1) 4 else (conn.sourcePort.type.sizeInBits/8)
	  	val bufferAddr = memoryBaseAddr
	  	val rdIndicesAddr = memoryBaseAddr + size * portSizeInBytes
	  	val wrIndexAddr = rdIndicesAddr + nbReaders * 4
	  	memoryBaseAddr = wrIndexAddr + 4
 		'''
««« 		DECLARE_FIFO(«conn.sourcePort.type.doSwitch», «size», «id», «nbReaders», «String.format("0x%x", bufferAddr)», «String.format("0x%x", rdIndicesAddr)», «String.format("0x%x", wrIndexAddr)»)
			fifo_«conn.sourcePort.type.doSwitch»_t fifo_«id» = {«size», («conn.sourcePort.type.doSwitch» *) «String.format("0x%x", bufferAddr)», «nbReaders», (unsigned int *) «String.format("0x%x", rdIndicesAddr)», (unsigned int *) «String.format("0x%x", wrIndexAddr)»};
		'''
	 }
}