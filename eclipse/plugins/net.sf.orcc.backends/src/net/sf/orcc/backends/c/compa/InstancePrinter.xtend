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
package net.sf.orcc.backends.c.compa

import java.io.File
import java.util.Map
import net.sf.orcc.df.Action
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.Port
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.TypeList
import net.sf.orcc.util.OrccUtil

/**
 * Generate and print instance source file for COMPA backend.
 *  
 * @author Antoine Lorence
 * 
 */
class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {	
	// Whether the actor has a main function or the main is within the Top file.
	private boolean printMainFunc
	private String actorsFolder
	private boolean measureTime = false
	private boolean enableTest = false
	private boolean enableFifosCopy = false
	private int actorGlobalIx;
	
	new(Map<String, Object> options, String srcPath, boolean printTop) {
		super(options)
		printMainFunc = !printTop
		actorsFolder = srcPath + File.separator + "Actors"
	}
	
	def print(String targetFolder, Actor actor, int actorIx) {
		setActor(actor)
		actorGlobalIx = actorIx
		print(targetFolder)
	}
	
	override protected print(String targetFolder) {
//		checkConnectivy

		val content = fileContent
//		val topContent = topFileContent
//		val testContent = testFileContent
		var File file
		
//		if(actorGlobalIx < 10){
//			file = new File(actorsFolder + File::separator + "0" + actorGlobalIx + "_" + entityName + ".c")
//		}else{
//			file = new File(actorsFolder + File::separator + actorGlobalIx + "_" + entityName + ".c")
//		}
		file = new File(actorsFolder + File::separator + entityName + ".c")
		
//		val topFile = new File(targetFolder + File::separator + "Top_" + entityName + ".c")
//		val testFile = new File(targetFolder + File::separator + entityName + "_test.h")

		if(actor.native) {
//			OrccLogger::noticeln(entityName + " is native and not generated.")
		} else if(needToWriteFile(content, file)) {//} || needToWriteFile(topContent, topFile)) {
			OrccUtil::printFile(content, file)
//			OrccUtil::printFile(topContent, topFile)
			return 0
		} else {
			return 1
		}
	}
	
//	def private printCompareWithTraces() '''
//		static void compareWithTraces(u32 fifoIx, int *tokens, u32 FIFO_SIZE, const TCHAR* file_name, DWORD *file_ptr, DWORD *file_ln){
//			FIL file;
//			char trace_str[10];
//			int trace_value;
//			FRESULT rc = FR_OK;
//		
//			if(fifoIx > *file_ln){
//				Xil_DCacheDisable();
//				// Opening trace file.
//				rc = f_open(&file, file_name, FA_READ);
//				if(rc != FR_OK){
//					xil_printf("Error %d while opening file.\n\r", rc);
//					Xil_DCacheEnable();
//					return;
//				}
//		
//				// Position trace file pointer after the last read byte.
//				f_lseek(&file, *file_ptr);
//		
//				// Compare until the number of lines read from the file is equal to the number of tokens in the fifo.
//				while(fifoIx > *file_ln){
//					memset(trace_str, 0, sizeof(trace_str));			// Clear string.
//					f_gets(trace_str, sizeof(trace_str), &file);		// Read a line from trace file.
//					trace_value = atoi(trace_str);						// Convert string to number.
//					if (trace_value != tokens[*file_ln%FIFO_SIZE]){	// If different values, print a message to the console.
//						xil_printf("Token %d: %d != %d\n\r", *file_ln, trace_value, tokens[*file_ln%FIFO_SIZE]);
//						exit(-1);	// This actor must be debugged step by step!!
//					}
//					(*file_ln)++;	// Inc number of lines read from the trace file.
//				}
//		
//				*file_ptr = file.fptr;	// Store the file position for the next time.
//				f_close(&file);
//				Xil_DCacheEnable();
//			}
//		}
//	'''
	
	override protected printStateLabel(State state) '''
		l_«state.name»:
			«IF ! actor.actionsOutsideFsm.empty»
				i += «entityName»_outside_FSM_scheduler();
			«ENDIF»
			«IF state.outgoing.empty»
				xil_printf("Stuck in state "«state.name»" in the instance «entityName»\n");
				exit(1);
			«ELSE»
				«state.printStateTransitions»
			«ENDIF»
	'''
	
	override protected printStateTransitions(State state) '''
		«FOR trans : state.outgoing.map[it as Transition] SEPARATOR " else "»
			if («trans.action.inputPattern.checkInputPattern»isSchedulable_«trans.action.name»()) {
				«IF trans.action.outputPattern != null»
					«trans.action.outputPattern.printOutputPattern»
						_FSM_state = my_state_«state.name»;
						goto finished;
					}
				«ENDIF»
				«trans.action.body.name»();
				i++;
				goto l_«trans.target.name»;
			}«ENDFOR» else {
			«transitionPattern.get(state).printTransitionPattern»
			_FSM_state = my_state_«state.name»;
			goto finished;
		}
	'''
	
	override protected printTransitionPattern(Pattern pattern) {
		// Does nothing, but do not remove. Some C code has to not be printed*
		// in COMPA backend
	}
	
	override protected printActorScheduler() '''
		«IF actor.hasFsm»
			«printFsm»
		«ELSE»
			int «entityName»_scheduler() {
				int i = 0;
				if(needInit){
					«entityName»_initialize();
					needInit = 0;
				}
				«printCallTokensFunctions»
				
				«IF enableTrace»
					«FOR port : actor.inputs + actor.outputs»
						int «port.name»_startIx = index_«port.name»;
					«ENDFOR»
				«ENDIF»

				«IF enableFifosCopy»
					«printCopyIndices»
				«ENDIF»

				«actor.actionsOutsideFsm.printActionSchedulingLoop»
				
			finished:
«««				«IF enableTrace»
«««					«FOR port : actor.outputs.notNative»
«««						if(!compareWithTraces_«port.type.doSwitch»(actorIx, «port.fullName»_id, index_«port.name», («port.type.doSwitch»*)tokens_«port.name», SIZE_«port.name»)) exit(-1);
««««««						if(compareWithTraces_«port.type.doSwitch»(«actorGlobalIx», «actor.outputs.indexOf(port)», index_«port.name», («port.type.doSwitch»*)tokens_«port.name», SIZE_«port.name») == 0) exit(-1);
«««					«ENDFOR»
«««				«ENDIF»
«««				«IF enableFifosCopy»
«««					if(i>0){	// At least one action was executed.
«««						// Comparing input tokens with data copied into an extra buffer for debugging communications.
«««						«FOR port : actor.inputs»
«««							if(readCmpInputTokens(«port.fullName»_ix, (u8*)tokens_«port.name», start_index_«port.name», index_«port.name», sizeof(«port.type.doSwitch»))){
«««								index_«port.name» = 0;
«««								read_end_«port.name»();
«««								exit(-1);
«««							}
«««						«ENDFOR»
«««						
«««						// Copying output tokens into an extra buffer for debugging communications.
«««						«FOR port : actor.outputs.filter[!native]»
«««							copyOutputTokens(«port.fullName»_ix, (u8*)tokens_«port.name», start_index_«port.name», index_«port.name», sizeof(«port.type.doSwitch»));
«««						«ENDFOR»
«««					}
«««				«ENDIF»

				«FOR port : actor.inputs»
					read_end_«port.name»();
				«ENDFOR»
				«FOR port : actor.outputs.notNative»
					write_end_«port.name»();
				«ENDFOR»
				«IF actor.inputs.nullOrEmpty && actor.outputs.nullOrEmpty »
					// no read_end/write_end here!
					return;
				«ENDIF»
				
				«IF enableTrace»
					«printDataVerification»
				«ENDIF»
				
				return i;
			}
		«ENDIF»
	'''


	override printInitialize() '''
		«FOR init : actor.initializes»
			«init.print»
		«ENDFOR»
		
		void «entityName»_initialize() {
			int i = 0;
			«IF actor.hasFsm»
				/* Set initial state to current FSM state */
				_FSM_state = my_state_«actor.fsm.initialState.name»;
			«ENDIF»
			«IF !actor.initializes.nullOrEmpty»
				«actor.initializes.printActionsScheduling»
			«ENDIF»
			
		finished:
			// no read_end/write_end here!
			return;
		}
	'''

	override protected printFsm() '''
		«IF ! actor.actionsOutsideFsm.empty»
			int «entityName»_outside_FSM_scheduler() {
				int i = 0;
				«actor.actionsOutsideFsm.printActionSchedulingLoop»
			finished:
				// no read_end/write_end here!
				return i;
			}
		«ENDIF»

		int «entityName»_scheduler() {
			int i = 0;
			if(needInit){
				«entityName»_initialize();
				needInit = 0;
			}

			«printCallTokensFunctions»
				
			«IF enableTrace»
				«FOR port : actor.inputs + actor.outputs»
					int «port.name»_startIx = index_«port.name»;
				«ENDFOR»
			«ENDIF»

			«IF enableFifosCopy»
				«printCopyIndices»
			«ENDIF»
			
			// jump to FSM state 
			switch (_FSM_state) {
			«FOR state : actor.fsm.states»
				case my_state_«state.name»:
					goto l_«state.name»;
			«ENDFOR»
			default:
«««				xil_printf("unknown state in «entityName».c : %s\n", stateNames[_FSM_state]				
				exit(1);
			}

			// FSM transitions
			«FOR state : actor.fsm.states»
				«state.printStateLabel»
			«ENDFOR»
		finished:
«««			«IF enableTrace»
«««				«FOR port : actor.outputs.notNative»
«««					if(i > 0){					
«««						if(!compareWithTraces_«port.type.doSwitch»(actorIx, «port.fullName»_id, index_«port.name», («port.type.doSwitch»*)tokens_«port.name», SIZE_«port.name»)) exit(-1);
««««««					if(compareWithTraces_«port.type.doSwitch»(«actorGlobalIx», «actor.outputs.indexOf(port)», index_«port.name», («port.type.doSwitch»*)tokens_«port.name», SIZE_«port.name») == 0) 
««««««							exit(-1);
«««					}
«««				«ENDFOR»
«««			«ENDIF»
«««			«IF enableFifosCopy»
«««				if(i>0){	// At least one action was executed.
«««					// Comparing input tokens with data copied into an extra buffer for debugging communications.
«««					«FOR port : actor.inputs»
«««						if(readCmpInputTokens(«port.fullName»_ix, (u8*)tokens_«port.name», start_index_«port.name», index_«port.name», sizeof(«port.type.doSwitch»))){
«««							index_«port.name» = 0;
«««							read_end_«port.name»();
«««							exit(-1);
«««						}
«««					«ENDFOR»
«««					
«««					// Copying output tokens into an extra buffer for debugging communications.
«««					«FOR port : actor.outputs.filter[!native]»
«««						copyOutputTokens(«port.fullName»_ix, (u8*)tokens_«port.name», start_index_«port.name», index_«port.name», sizeof(«port.type.doSwitch»));
«««					«ENDFOR»
«««				}
«««			«ENDIF»
			
			«FOR port : actor.inputs»
				read_end_«port.name»();
			«ENDFOR»
			
			«FOR port : actor.outputs.filter[!native]»
				write_end_«port.name»();
			«ENDFOR»
			
			«IF enableTrace»
				«printDataVerification»
			«ENDIF»
			
			return i;
		}
	'''
	
	def printDataVerification()'''
		if(i > 0){
			int portIx = 0;
			«FOR port : actor.inputs + actor.outputs»
				strcpy(dataFifos[portIx].traceFileName, «port.name»_fileName);
				dataFifos[portIx].tokens = (int)tokens_«port.name»;
				dataFifos[portIx].startIx = «port.name»_startIx;
				dataFifos[portIx].endIx = index_«port.name»;
				dataFifos[portIx].tokenSz = sizeof(«port.type.doSwitch»);
				dataFifos[portIx].fifoSz = SIZE_«port.name»;
				portIx++;
			«ENDFOR»
«««					«FOR port : actor.outputs.notNative»
«««						if(!compareWithTraces_«port.type.doSwitch»(actorIx, «port.fullName»_id, index_«port.name», («port.type.doSwitch»*)tokens_«port.name», SIZE_«port.name»)) exit(-1);
««««««						if(compareWithTraces_«port.type.doSwitch»(«actorGlobalIx», «actor.outputs.indexOf(port)», index_«port.name», («port.type.doSwitch»*)tokens_«port.name», SIZE_«port.name») == 0) exit(-1);
«««					«ENDFOR»
			sendCtrlMsg_blocking(&ctrl_fifo_output, MSG_VERIFY_DATA_FIFOS, (u8*)dataFifos, sizeof(dataFifos));
			u8 msg = rcvCtrlMsgType_blocking(&ctrl_fifo_input);
			if(msg == MSG_VERIFY_DATA_FIFOS_ERROR) exit(-1);
		}
	'''
	
	def printCopyIndices() '''
		«FOR port : actor.inputs»
			u32 start_index_«port.name» = index_«port.name»;
		«ENDFOR»
		«FOR port : actor.outputs.notNative»
			u32 start_index_«port.name» = index_«port.name»;
		«ENDFOR»
	'''
	
	override protected printActionsScheduling(Iterable<Action> actions) '''
		// Action loop
		«FOR action : actions SEPARATOR " else "»
			if («inputPattern.checkInputPattern»isSchedulable_«action.name»()) {
				«IF action.outputPattern != null»
					«action.outputPattern.printOutputPattern»
						goto finished;
					}
				«ENDIF»
				«action.body.name»();
				i++;
			}«ENDFOR» else {
			«inputPattern.printTransitionPattern»
			goto finished;
		}
	'''
	
	def protected printInitSD()'''
		void initSD(){
			FRESULT rc = FR_OK;
		
		//	Xil_DCacheDisable();
		//	Xil_ICacheDisable();
		
			/* Register volume work area, initialize device */
			rc = f_mount(0, &fatfs);
			if (rc != FR_OK) {
				xil_printf("SD_INIT_FAIL: %d\n", rc);
				return;
			}
		
		//	Xil_DCacheEnable();
		//	Xil_ICacheEnable();
		}
	'''
	
	def protected printInitSharedMemory()'''
		void initSharedMemory()
		{
		//    unsigned char * base = 	(unsigned char *) 0x30000000;
		//    unsigned char * end = 	(unsigned char *) 0x300C0000;
		    unsigned char * base = 	(unsigned char *) XPAR_SHARED_MEMORY_96KB_AXI_BRAM_CTRL_1_S_AXI_BASEADDR;
		    unsigned char * end = 	(unsigned char *) XPAR_SHARED_MEMORY_96KB_AXI_BRAM_CTRL_1_S_AXI_HIGHADDR;
		
		    unsigned char * ptr;
		    int i = 0;
		
		    xil_printf("Initializing memory range : %x - %x ...", base, end);
		    for(ptr= base; ptr < end; ptr++) {
		    	unsigned char expected = i & 0xFF;
		    	*(ptr) = expected;
		    	if (expected != *ptr) {
		    		xil_printf("Error at %x : write %x but read %x \r\n", ptr, expected, *ptr);
		    		break;
		    	}
		    }
		    xil_printf("done!\r\n");
		}
	'''
	
	def protected printTestInit()'''
		void testInit(){
			init_platform();
		    initSharedMemory();
		    initSD();
		    // Opening input trace files.
		    FRESULT rc = FR_OK;
		    «FOR port : actor.inputs»
		    	rc = f_open(&fil_«port.name», traces_«port.name», FA_READ);
		    	if (rc != FR_OK) {
		    		xil_printf("f_open failed: %d\n", rc);
		    		exit(-1);
		    	}
			«ENDFOR»
			
			// Opening output trace files.
		    «FOR port : actor.outputs»
		    	rc = f_open(&fil_«port.name», traces_«port.name», FA_READ);
		    	if (rc != FR_OK) {
		    		xil_printf("f_open failed: %d\n", rc);
		    		exit(-1);
		    	}
			«ENDFOR»
		}
	'''

	override protected writeTokensFunctions(Port port) '''
		static void write_«port.name»() {
			index_«port.name» = (*«port.fullName»->write_ind);
«««			index_«port.name» = «port.fullName»->write_ind;
			numFree_«port.name» = index_«port.name» + fifo_«port.type.doSwitch»_get_room(«port.fullName», NUM_READERS_«port.name»);
		}

		static void write_end_«port.name»() {
			(*«port.fullName»->write_ind) = index_«port.name»;
«««			«port.fullName»->write_ind = index_«port.name»;
		}
	'''

	def protected testReadTokensFunctions(Port port) '''
		static void read_«port.name»() {
			index_«port.name» = «port.fullName»->read_inds[0];
			numTokens_«port.name» = index_«port.name» + fifo_«port.type.doSwitch»_get_num_tokens(«port.fullName», 0);
		}

		static void read_end_«port.name»() {
			«port.fullName»->read_inds[0] = index_«port.name»;
		}
	'''
	
	override protected getFileContent()	'''
		// Source file is "«actor.file»"

		#include <stdio.h>
		#include <stdlib.h>
		«IF checkArrayInbounds»
			#include <assert.h>
		«ENDIF»
		«IF enableTrace»
			#include <string.h>
«««			#include "ff.h"
		«ENDIF»
		#include "dataflow.h"
«««		#include "fifo.h"
		#include "fifoAllocations.h"
«««		#include "ctrl_fifos.h"
		«IF enableTrace»
«««			#include "tracesDefs.h"
			#include "compa_trace.h"
		«ENDIF»
		
«««		«IF enableTest»
«««			#include "decoder_parser_parseheaders_test.h"
«««		«ENDIF»
«««		#include "platform.h"
«««		#include "xparameters.h"
«««		extern void xil_printf( const char *ctrl1, ...);
«««		#define xil_printf	printf
		
		«IF profileNetwork || dynamicMapping»
			#include "cycle.h"
		«ENDIF»

		«IF instance != null»
			«instance.printAttributes»
		«ELSE»
			«actor.printAttributes»
		«ENDIF»
		«IF newSchedul»

			#define RING_TOPOLOGY «IF ringTopology»1«ELSE»0«ENDIF»
		«ENDIF»

«««		#define SIZE «fifoSize»

		«IF printMainFunc != true»
			////////////////////////////////////////////////////////////////////////////////
			// Instance
			extern actor_t «entityName»;
		«ENDIF»
		
		«IF !actor.inputs.nullOrEmpty»
«««			////////////////////////////////////////////////////////////////////////////////
«««			// Input FIFOs
«««			«FOR port : actor.inputs»
«««				«if (incomingPortMap.get(port) != null) "extern "» fifo_«port.type.doSwitch»_t *«port.fullName»;
«««				«IF enableFifosCopy»
«««					#define «port.fullName»_ix  «incomingPortMap.get(port).<Integer>getValueAsObject("idNoBcast")»
«««					extern int readCmpInputTokens(u32 fifoIx, u8* data, u32 start, u32 end, u8 tokenSize);
«««				«ENDIF»
«««			«ENDFOR»

			////////////////////////////////////////////////////////////////////////////////
			// Input Fifo control variables
			«FOR port : actor.inputs»
				static unsigned int index_«port.name»;
				static unsigned int numTokens_«port.name»;
				#define SIZE_«port.name» «incomingPortMap.get(port).sizeOrDefaultSize»
				#define tokens_«port.name» 	«port.fullName»->contents
				
				«IF profileNetwork || dynamicMapping»
					extern connection_t connection_«entityName»_«port.name»;
					#define rate_«port.name» connection_«entityName»_«port.name».rate
				«ENDIF»
				
			«ENDFOR»

«««			////////////////////////////////////////////////////////////////////////////////
«««			// Predecessors
«««			«FOR port : actor.inputs»
«««				«IF incomingPortMap.get(port) != null»
«««					extern actor_t «incomingPortMap.get(port).source.label»;
«««				«ENDIF»
«««			«ENDFOR»
		«ENDIF»
		
		«IF !actor.outputs.filter[! native].nullOrEmpty»
«««			////////////////////////////////////////////////////////////////////////////////
«««			// Output FIFOs
«««			«FOR port : actor.outputs.filter[! native]»
«««				extern fifo_«port.type.doSwitch»_t *«port.fullName»;
«««				«IF enableFifosCopy»
«««					#define «port.fullName»_ix  «outgoingPortMap.get(port).head.<Integer>getValueAsObject("idNoBcast")»
«««					extern void copyOutputTokens(u32 fifoIx, u8* data, u32 start, u32 end, u8 tokenSize);
«««				«ENDIF»
«««			«ENDFOR»

			////////////////////////////////////////////////////////////////////////////////
			// Output Fifo control variables
			«FOR port : actor.outputs.filter[! native]»
				static unsigned int index_«port.name»;
				static unsigned int numFree_«port.name»;
				#define NUM_READERS_«port.name» «outgoingPortMap.get(port).size»
				#define SIZE_«port.name» «outgoingPortMap.get(port).get(0).sizeOrDefaultSize»
				#define tokens_«port.name» «port.fullName»->contents
			«ENDFOR»

«««			////////////////////////////////////////////////////////////////////////////////
«««			// Successors
«««			«FOR port : actor.outputs»
«««				«FOR successor : outgoingPortMap.get(port)»
«««					extern actor_t «successor.target.label»;
«««				«ENDFOR»
«««			«ENDFOR»

		«ENDIF»
	
		«IF enableTrace»	
			////////////////////////////////////////////////////////////////////////////////
			// Control FIFOs.
			extern ctrl_fifo_hdlr ctrl_fifo_input;
			extern ctrl_fifo_hdlr ctrl_fifo_output;
			
			////////////////////////////////////////////////////////////////////////////////
			// Declarations for verification against trace files.
«««				static const int actorIx = «actorGlobalIx»;
			static TVerifyDataFifo dataFifos[«actor.inputs.length + actor.outputs.length»];
			
			«FOR port : actor.inputs + actor.outputs»
«««					static const int «port.fullName»_id = «actor.outputs.indexOf(port)»;
				static const char* «port.name»_fileName = "«port.fullName»";
«««					//const TCHAR* fileName_«port.name» = "/traces/«port.fullName».txt";
«««					static DWORD file_ptr_«port.name» = 0;
«««					static DWORD file_ln_«port.name» = 0;				
			«ENDFOR»
		«ENDIF»
		
		«IF enableTest»
			// Including header for actor test
			#include "decoder_parser_parseheaders_test.h"
		«ENDIF»
		
		«IF (instance != null && !instance.arguments.nullOrEmpty) || !actor.parameters.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Parameter values of the instance
			«IF instance != null»
				«FOR arg : instance.arguments»
					«IF arg.value.exprList»
						static «IF (arg.value.type as TypeList).innermostType.uint»unsigned «ENDIF»int «arg.variable.name»«arg.value.type.dimensionsExpr.printArrayIndexes» = «arg.value.doSwitch»;
					«ELSE»
						#define «arg.variable.name» «arg.value.doSwitch»
					«ENDIF»
				«ENDFOR»
			«ELSE»
				«FOR variable : actor.parameters»
					«variable.declare»
				«ENDFOR»
			«ENDIF»

		«ENDIF»
		«IF profileActions && profileNetwork»
			////////////////////////////////////////////////////////////////////////////////
			// Action's workload for profiling
			«FOR action : actor.actions»
				extern action_t action_«actor.name»_«action.body.name»;
				#define ticks_«action.body.name» action_«actor.name»_«action.body.name».ticks
			«ENDFOR»		
			
		«ENDIF»
		
		«IF !actor.stateVars.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// State variables of the actor
			«FOR variable : actor.stateVars»
				«variable.declare»
			«ENDFOR»
		«ENDIF»
		
		static u8 needInit = 1;
		
		«IF actor.hasFsm»
			////////////////////////////////////////////////////////////////////////////////
			// Initial FSM state of the actor
			enum states {
				«FOR state : actor.fsm.states SEPARATOR ","»
					my_state_«state.name»
				«ENDFOR»
			};

			static char *stateNames[] = {
				«FOR state : actor.fsm.states SEPARATOR ","»
					"«state.name»"
				«ENDFOR»
			};

			static enum states _FSM_state;

		«ENDIF»
		////////////////////////////////////////////////////////////////////////////////
		// Token functions
		«FOR port : actor.inputs»
			«port.readTokensFunctions»
		«ENDFOR»

		«FOR port : actor.outputs.notNative»
			«port.writeTokensFunctions»
		«ENDFOR»

		////////////////////////////////////////////////////////////////////////////////
		// Functions/procedures
		«FOR proc : actor.procs»
			«proc.declare»
		«ENDFOR»

		«FOR proc : actor.procs.notNativeProcs»
			«proc.print»
		«ENDFOR»

		////////////////////////////////////////////////////////////////////////////////
		// Actions
		«FOR action : actor.actions»
			«action.print()»
		«ENDFOR»

«««		«IF enableTrace»
«««			«FOR port : actor.outputs.filter[! native]»
«««				compareWithTraces_«port.type.doSwitch()»(actorIx, «port.fullName»_id, index_«port.name», tokens_«port.name», SIZE_«port.name»);
«««			«ENDFOR»
««««««		////////////////////////////////////////////////////////////////////////////////
««««««		// Compare tokens in the output FIFO(s) with tokens in the corresponding trace file(s).
««««««		«printCompareWithTraces»
«««		«ENDIF»
		
		////////////////////////////////////////////////////////////////////////////////
		// Initializes
		«printInitialize»

		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		«printActorScheduler»
		
	'''
	
	
	def protected getTopFileContent() '''
		#include "xil_cache.h"
		«IF measureTime»
			#include "xtmrctr.h"
			
			#define TIMER_CNTR_0	0
		«ENDIF»
		
		extern void xil_printf( const char *ctrl1, ...);	
		extern void «entityName»_initialize();
		extern int «entityName»_scheduler();
		
		//int main(int argc, char *argv[]) __attribute__((section(".compak")));
		int main(int argc, char *argv[]) {
			int i;
			int stop = 0;
			
			«IF measureTime»
				int Status;
				u32 startTime, stopTime, acumTime = 0;
				
				XTmrCtr xtimer;
				XTmrCtr* TmrCtrInstancePtr = &xtimer;
				
				Status = XTmrCtr_Initialize(TmrCtrInstancePtr, XPAR_TMRCTR_0_DEVICE_ID);
				
				if (Status != XST_SUCCESS) {
					xil_printf("Init timer error\n");
				}
				
				XTmrCtr_SetResetValue(TmrCtrInstancePtr, TIMER_CNTR_0, 0);
				XTmrCtr_SetOptions(TmrCtrInstancePtr, TIMER_CNTR_0,
									XTC_AUTO_RELOAD_OPTION |
									XTC_CASCADE_MODE_OPTION);
				XTmrCtr_Reset(TmrCtrInstancePtr, TIMER_CNTR_0);
				XTmrCtr_Start(TmrCtrInstancePtr, TIMER_CNTR_0);
			«ENDIF»
			
«««		    init_platform();
«««			init_orcc(argc, argv);

«««			microblaze_enable_icache();
«««			microblaze_enable_dcache();
	
			«IF enableTest»
				testInit();
			«ENDIF»

			«entityName»_initialize();

			while(1) {
«««			while((*execEnd) == 0) {
				i = 0;
				
				«IF enableTest»
					testScheduler();
				«ENDIF»
				«IF measureTime»
					startTime = XTmrCtr_GetValue(TmrCtrInstancePtr, TIMER_CNTR_0);
				«ENDIF»
				i += «entityName»_scheduler();
				«IF measureTime»
					stopTime = XTmrCtr_GetValue(TmrCtrInstancePtr, TIMER_CNTR_0);
					if(i!=0){
						acumTime += (stopTime - startTime);
					}
				«ENDIF»
				stop = stop || (i == 0);
			}
«««			xil_printf("Execution time %d\n", acumTime);
			
			return 0;
		}
	'''
	
	def protected getTestFileContent() '''
		#include "ff.h"
		#include "xparameters.h"
		#include "platform.h"
		
		static FATFS fatfs;
		
		«IF !actor.inputs.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Input FIFOs
			////////////////////////////////////////////////////////////////////////////////
			// Input Fifo test control variables
			«FOR port : actor.inputs»
				static unsigned int numFree_«port.name»;
				#define NUM_READERS_«port.name»	1
				#define traces_«port.name» 	"traces/«port.name».txt"
				static 	FIL					fil_«port.name»;
			«ENDFOR»
		«ENDIF»
		
		«IF !actor.outputs.filter[! native].nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Output FIFOs
			////////////////////////////////////////////////////////////////////////////////
			// Output Fifo control variables
			«FOR port : actor.outputs.filter[! native]»
				static unsigned int numTokens_«port.name»;
				#define traces_«port.name» 	"traces/«port.name».txt"
				static 	FIL					fil_«port.name»;
			«ENDFOR»
		«ENDIF»

		////////////////////////////////////////////////////////////////////////////////
		// Token functions for test
		«FOR port : actor.inputs»
			«port.writeTokensFunctions»
		«ENDFOR»

		«FOR port : actor.outputs.notNative»
			«port.testReadTokensFunctions»
		«ENDFOR»

		«printInitSD»
		
		«printInitSharedMemory»
		
		«printTestInit»
		
	'''

//	override caseInstCall(InstCall call) '''
//		«IF call.print»
//«««			printf(«call.arguments.printfArgs.join(", ")»);
//		«ELSE»
//			«IF call.target != null»«call.target.variable.name» = «ENDIF»«call.procedure.name»(«call.arguments.join(", ")[print]»);
//		«ENDIF»
//	'''

	override protected declare(Procedure proc){
		val modifier = if(proc.native) "extern" else "static"
		if(proc.name != "print")
		'''«modifier» «proc.returnType.doSwitch» «proc.name»(«proc.parameters.join(", ")[declare]»);'''
	}
	
	override protected printCore(Action action, boolean isAligned) '''
		static void «action.body.name»«IF isAligned»_aligned«ENDIF»() {

			// Compute aligned port indexes
			«FOR port : action.inputPattern.ports + action.outputPattern.ports»
				i32 index_aligned_«port.name» = index_«port.name» % SIZE_«port.name»;
			«ENDFOR»

			«FOR variable : action.body.locals»
				«variable.declare»;
			«ENDFOR»
			«beforeActionBody»

			«FOR block : action.body.blocks»
				«block.doSwitch»
			«ENDFOR»

			// Update ports indexes
			«FOR port : action.inputPattern.ports»
				index_«port.name» += «action.inputPattern.getNumTokens(port)»;
			«ENDFOR»
			«FOR port : action.outputPattern.ports»
				index_«port.name» += «action.outputPattern.getNumTokens(port)»;
			«ENDFOR»
			
			«FOR port : action.inputPattern.ports»
				read_end_«port.name»();
			«ENDFOR»
			«FOR port : action.outputPattern.ports»
				write_end_«port.name»();
			«ENDFOR»
		}
	'''
}

