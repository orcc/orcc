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

import java.util.Map
import net.sf.orcc.df.Action
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.Port
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.ir.TypeList

/**
 * Generate and print instance source file for COMPA backend.
 *  
 * @author Antoine Lorence
 * 
 */
class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {	
	// Whether the actor has a main function or the main is within the Top file.
	private boolean printMainFunc
	private boolean enableTest = false

	def setOptions(Map<String, Object> options, boolean printTop) {
		super.setOptions(options)
		printMainFunc = !printTop
	}
	
	override protected printStateLabel(State state) '''
		l_«state.name»:
			«IF ! actor.actionsOutsideFsm.empty»
				i += «entityName»_outside_FSM_scheduler();
			«ENDIF»
			«IF state.outgoing.empty»
				xil_printf("Stuck in state \"«state.name»\" in the instance «entityName»\n");
				exit(1);
			«ELSE»
				«state.printStateTransitions»
			«ENDIF»
	'''
	
	override protected printStateTransitions(State state) '''
		«FOR trans : state.outgoing.map[it as Transition] SEPARATOR " else "»
			if («trans.action.inputPattern.checkInputPattern»«trans.action.scheduler.name»()) {
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
				«printCallTokensFunctions»
				«actor.actionsOutsideFsm.printActionSchedulingLoop»
				
			finished:
				«FOR port : actor.inputs»
					read_end_«port.name»();
				«ENDFOR»
				«FOR port : actor.outputs.notNative»
					write_end_«port.name»();
				«ENDFOR»
				«IF actor.inputs.nullOrEmpty && actor.outputs.nullOrEmpty»
					// no read_end/write_end here!
					return;
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

			«printCallTokensFunctions»

			// jump to FSM state
			switch (_FSM_state) {
			«FOR state : actor.fsm.states»
				case my_state_«state.name»:
					goto l_«state.name»;
			«ENDFOR»
			default:
				xil_printf("unknown state in «entityName».c : %s\n", stateNames[_FSM_state]);
				exit(1);
			}

			// FSM transitions
			«FOR state : actor.fsm.states»
		«state.printStateLabel»
			«ENDFOR»
		finished:
			«FOR port : actor.inputs»
				read_end_«port.name»();
			«ENDFOR»
			«FOR port : actor.outputs.filter[!native]»
				write_end_«port.name»();
			«ENDFOR»
			return i;
		}
	'''
	
	override protected printActionsScheduling(Iterable<Action> actions) '''
		// Action loop
		«FOR action : actions SEPARATOR " else "»
			if («inputPattern.checkInputPattern»«action.scheduler.name»()) {
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
	
	def protected printTestScheduler()'''
		void testScheduler(){
			uint i, nbWrittenTokens, nbReadTokens;
			unsigned int nbFreeSlots;
			unsigned int nbTokens;
			char value[16];
			FRESULT rc = FR_OK;
			int traceToken, fifoToken;

			«FOR port : actor.inputs»
				write_«port.name»();
				nbFreeSlots = fifo_«port.type.doSwitch»_get_room(«entityName»_«port.name», 1);
				if(nbFreeSlots>0){
«««					pushTokens_«port.type.doSwitch»(&fil_«port.name», &tokens_«port.name»[(index_«port.name» + (0)) % SIZE_«port.name»], nbFreeSlots);
					for (nbWrittenTokens = 0; nbWrittenTokens < nbFreeSlots; nbWrittenTokens++) {
						i = 0;
						memset(value, 0, sizeof(value));
						do{
							rc = f_read(&fil_«port.name», &value[i], 1, 0);
							if(rc != FR_OK){
								xil_printf("f_read failed: %d\n", rc);
								exit(-1);
							}
						}while(value[i++] != '\n');
						
						fifo_«port.type.doSwitch»_write_1(«entityName»_«port.name», atoi(value));
					}
					index_«port.name» += nbWrittenTokens;
					write_end_«port.name»();
				}
			«ENDFOR»
			«FOR port : actor.outputs»
			
				read_«port.name»();
				nbTokens = fifo_«port.type.doSwitch»_get_num_tokens(«entityName»_«port.name», 0);
				if(nbTokens>0){
«««					popCompareTokens_«port.type.doSwitch»(&fil_«port.name», &tokens_«port.name»[(index_«port.name» + (0)) % SIZE_«port.name»], nbTokens);
					for (nbReadTokens = 0; nbReadTokens < nbTokens; nbReadTokens++) {
						i = 0;
						memset(value, 0, sizeof(value));
						do{
							rc = f_read(&fil_«port.name», &value[i], 1, 0);
							if(rc != FR_OK){
								xil_printf("f_read failed: %d\n", rc);
								exit(-1);
							}
						}while(value[i++] != '\n');
			
						traceToken = atoi(value);
						fifoToken = fifo_«port.type.doSwitch»_read_1(«entityName»_«port.name», 0);
						if(fifoToken != traceToken){
							xil_printf("Error at token %d: %d != %d\n", nbReadTokens, fifoToken, traceToken);
							exit(-1);
						}
			
					}
					index_«port.name» += nbReadTokens;
					// index_IN += (64 * nbTokens);
					read_end_«port.name»();
				}
			«ENDFOR»
		}
	'''
	
	
	def protected printMain() '''
		int main(int argc, char *argv[]) {
			int i;
			int stop = 0;
			
«««		    init_platform();
«««			init_orcc(argc, argv);
			«IF enableTest»
				testInit();
			«ENDIF»

			«entityName»_initialize();

			while(1) {
				i = 0;
				
				«IF enableTest»
					testScheduler();
				«ENDIF»
		
				i += «entityName»_scheduler();
				stop = stop || (i == 0);
			}
			xil_printf("End of simulation !\n");
			
			return compareErrors;
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
	
	def getContent() '''
		// Source file is "«actor.file»"

		#include <stdio.h>
		#include <stdlib.h>

		«IF printMainFunc»
			#include "fifoAllocations.h"
		«ELSE»
			#include "fifo.h"
		«ENDIF»
		#include "util.h"
		#include "dataflow.h"
		
«««		«IF enableTest»
«««			#include "decoder_parser_parseheaders_test.h"
«««		«ENDIF»
«««		#include "platform.h"
«««		#include "xparameters.h"
		extern void xil_printf( const char *ctrl1, ...);
«««		#define xil_printf	printf

		«IF instance != null»
			«instance.printAttributes»
		«ELSE»
			«actor.printAttributes»
		«ENDIF»

«««		#define SIZE «fifoSize»

		«IF printMainFunc != true»
			////////////////////////////////////////////////////////////////////////////////
			// Instance
			extern actor_t «entityName»;
		«ENDIF»
		
		«IF !actor.inputs.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Input FIFOs
			«IF printMainFunc != true»
				«FOR port : actor.inputs»
					«if (incomingPortMap.get(port) != null) "extern "» fifo_«port.type.doSwitch»_t *«port.fullName»;
				«ENDFOR»
			«ENDIF»
			////////////////////////////////////////////////////////////////////////////////
			// Input Fifo control variables
			«FOR port : actor.inputs»
				static unsigned int index_«port.name»;
				static unsigned int numTokens_«port.name»;
				#define SIZE_«port.name» «incomingPortMap.get(port).sizeOrDefaultSize»
				#define tokens_«port.name» 	«port.fullName»->contents
				
			«ENDFOR»
			////////////////////////////////////////////////////////////////////////////////
			// Predecessors
			«FOR port : actor.inputs»
				«IF incomingPortMap.get(port) != null»
					extern actor_t «incomingPortMap.get(port).source.label»;
				«ENDIF»
			«ENDFOR»
		«ENDIF»
		
		«IF !actor.outputs.filter[! native].nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Output FIFOs
			«IF printMainFunc != true»
				«FOR port : actor.outputs.filter[! native]»
					extern fifo_«port.type.doSwitch»_t *«port.fullName»;
				«ENDFOR»
			«ENDIF»
			////////////////////////////////////////////////////////////////////////////////
			// Output Fifo control variables
			«FOR port : actor.outputs.filter[! native]»
				static unsigned int index_«port.name»;
				static unsigned int numFree_«port.name»;
				#define NUM_READERS_«port.name» «outgoingPortMap.get(port).size»
				#define SIZE_«port.name» «outgoingPortMap.get(port).get(0).sizeOrDefaultSize»
				#define tokens_«port.name» «port.fullName»->contents

			«ENDFOR»
			////////////////////////////////////////////////////////////////////////////////
			// Successors
			«FOR port : actor.outputs»
				«FOR successor : outgoingPortMap.get(port)»
					extern actor_t «successor.target.label»;
				«ENDFOR»
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
		
		«IF !actor.stateVars.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// State variables of the actor
			«FOR variable : actor.stateVars»
				«variable.declare»
			«ENDFOR»

		«ENDIF»
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

		////////////////////////////////////////////////////////////////////////////////
		// Initializes
		«printInitialize»

		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		«printActorScheduler»
		
		«IF printMainFunc»
			////////////////////////////////////////////////////////////////////////////////
			// main
			«printMain»
		«ENDIF»
	'''
	
	
	def getTestContent() '''
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
		
		«printTestScheduler»
		
	'''
}

