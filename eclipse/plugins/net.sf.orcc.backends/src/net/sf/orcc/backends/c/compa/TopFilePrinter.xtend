package net.sf.orcc.backends.c.compa

import java.io.File
import net.sf.orcc.backends.CommonPrinter
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil

class TopFilePrinter extends CommonPrinter {	
	boolean[][] currentMap
	Network network
	int nbProcessors 
	
	new(Network network, boolean[][] currentMap, int nbProcessors){
		this.currentMap = currentMap
		this.network = network
		this.nbProcessors = nbProcessors
	}
	
	def protected print(String targetFolder) {
//		checkConnectivy
		var File file
		var String procFolder
		var CharSequence content
		for (i : 0 ..< nbProcessors) {
			if (i<10){
//				procFolder = targetFolder + File::separator + "Top_0" + i + ".c"
				procFolder = targetFolder + File::separator + "Top_0" + i + File::separator + "Top_0" + i + ".c"
			}
			else{
//				procFolder = targetFolder + File::separator + "Top_" + i + ".c"
				procFolder = targetFolder + File::separator + "Top_" + i + File::separator + "Top_" + i + ".c"
			}
			
			if(i < currentMap.length){
				content = i.fileContent
			}
			else{
				content = i.fileContentBootloop
			}
			
			file = new File(procFolder)
			
			if(needToWriteFile(content, file) || needToWriteFile(content, file)) 
			{
				OrccUtil::printFile(content, file)
			}
		}
	}
	
	def protected getFileContent(int procNb)
	'''
		#include "string.h"
		#include "xil_cache.h"
		#include "xtmrctr.h"
		#include "xaxipmon.h"
		#include "config_mb.h"
		#include "ctrl_fifo_functions.h"
		#include "actors.h"
		#include "timerMngr.h"
		
		#define TIMER_CNTR_0	0
		#define CLOCK_FREQ_KHZ	XPAR_TIMERS_AXI_TIMER_0_CLOCK_FREQ_HZ/1000
		#define CLOCK_FREQ_MHZ	XPAR_TIMERS_AXI_TIMER_0_CLOCK_FREQ_HZ/1000000
		
		/////////////////////////////////////////////////
		// Actors' schedulers
		«FOR child : network.children.filter[label != "source" && label != "display"]»
			extern void call_«child.label»_scheduler();
		«ENDFOR»
		
		// Control FIFOs.
		ctrl_fifo_hdlr ctrl_fifo_input;
		ctrl_fifo_hdlr ctrl_fifo_output;
		
		// Timer
		XTmrCtr xtimer;
		XTmrCtr* TmrCtrInstancePtr = &xtimer;

		// APM
		#define getNbWrittenBytes(AxiPmonInstPtr) \
			XAxiPmon_GetMetricCounter(AxiPmonInstPtr, XPAR_CPU_ID%8)
		XAxiPmon AxiPmon;
		
		u8 init_done = 0;
		u32 nbElements = 0;
		u32 nbStaticActors = 2;
		u32 nbDynActors = 0;
		actorsNames sched_callers_array[NB_ACTORS] = {0};
		u64 globalStartTime, globalStopTime;
		u64 actorStartTime, actorStopTime;
		u64 diffTime;
		u64 execTimes[NB_ACTORS + 1] = {0}; // The last element stores the global execution time!
		u32 actorWrittenByteStart, actorWrittenByteStop;
		u32 writtenBytes[NB_ACTORS + 1] = {0}; // The last element stores the total number of written bytes!
		
		void init_timer(){
			int Status;
			// Initialize device and link it to a handler. It is initialized by the ARM.
			Status = XTmrCtr_Initialize(TmrCtrInstancePtr, XPAR_TIMERS_AXI_TIMER_0_DEVICE_ID);
			if ((Status != XST_SUCCESS) || (Status != XST_DEVICE_IS_STARTED)) {
		//		TODO: ..handle error
			}
		}


		int init_apm(XAxiPmon *AxiPmonInstPtr, u16 DeviceID, u8 Metric)
		{
			int Status;
			XAxiPmon_Config *ConfigPtr;
			u8 SlotId = 0x0;
		
			ConfigPtr = XAxiPmon_LookupConfig(DeviceID);
			if (ConfigPtr == NULL) {
				return XST_FAILURE;
			}
		
			AxiPmonInstPtr->IsReady = XIL_COMPONENT_IS_READY;
			AxiPmonInstPtr->Mode = XAPM_MODE_ADVANCED;
			AxiPmonInstPtr->Config.DeviceId = ConfigPtr->DeviceId;
			AxiPmonInstPtr->Config.BaseAddress = ConfigPtr->BaseAddress;
		
			return XST_SUCCESS;
		
		}


		void main() {
			u32 i;
			actorsNames schedCaller;
		
			init_timer();
			init_apm(&AxiPmon, XPAR_AXIPMON_0_DEVICE_ID, XAPM_METRIC_SET_2);
			
			// Initializing control FIFOs.
			init_ctrl_fifo_hdlr(&ctrl_fifo_input, CTRL_FIFO_IN_0_START_ADDR, CTRL_FIFO_IN_0_RD_IX_ADDR, CTRL_FIFO_IN_0_WR_IX_ADDR, CTRL_FIFO_IN_0_SIZE);
			init_ctrl_fifo_hdlr(&ctrl_fifo_output, CTRL_FIFO_OUT_0_START_ADDR, CTRL_FIFO_OUT_0_RD_IX_ADDR, CTRL_FIFO_OUT_0_WR_IX_ADDR, CTRL_FIFO_OUT_0_SIZE);
			
		#if ENABLE_START_SIGNAL == 1
			
			// Wait for start signal from the master processor.
			do {
				rd_ctrl_fifo(&ctrl_fifo_input);
			} while ((ctrl_fifo_input.ctrl_fifo_num_tkn - ctrl_fifo_input.ctrl_fifo_rd_ix)<=0);
			ctrl_fifo_input.ctrl_fifo_rd_ix = ctrl_fifo_input.ctrl_fifo_wr_ix; // Reads all written data, i.e. read index equal to write index (in the FIFO handler).
			updt_rd_ix(&ctrl_fifo_input); // Update read index (in the shared memory).
			//----//
		#endif
		
			//microblaze_enable_dcache();
			microblaze_enable_icache();
		
			while(1) {
				// Reading input control FIFO to get the actor(s) to be executed.
				u8 msg = rcvCtrlMsgType(&ctrl_fifo_input);
				switch (msg) {
					case MSG_INIT_DONE:
						init_done = 1;
						break;
					case MSG_ACTORS_MAP:
						nbElements = pop_contents_input_ctrl_fifo_blocking(&ctrl_fifo_input, (u8*)sched_callers_array);
						nbDynActors = nbElements/sizeof(int); // FIFO elements are bytes while scheduler addresses are words!
						break;
					case MSG_FLUSH_DCACHE:
						microblaze_flush_dcache();
						mbar(0);
						sendCtrlMsgType(&ctrl_fifo_output, MSG_FLUSH_DCACHE_DONE);
						nbElements = 0;
						nbDynActors = 0;
						memset(execTimes, 0, sizeof(execTimes));
						memset(writtenBytes, 0, sizeof(writtenBytes));
						//XAxiPmon_ResetMetricCounter(&AxiPmon);
						break;
					case MSG_GET_METRICS:
						sendCtrlMsgType_blocking(&ctrl_fifo_output, MSG_GET_METRICS_OK);
						push_contents_output_ctrl_fifo_blocking(&ctrl_fifo_output, (u8*)execTimes, sizeof(execTimes));
						push_contents_output_ctrl_fifo_blocking(&ctrl_fifo_output, (u8*)writtenBytes, sizeof(writtenBytes));
						break;
					default:
						break;
				}
		
				if((init_done) && ((nbStaticActors > 0) || (nbDynActors > 0))){
					globalStartTime = getTimerValue64();
		
					// Calling scheduler of static actors
		
					// Calling the scheduler of each actor (if any).
					for (i = 0; i < nbDynActors; i++) {
						actorStartTime = getTimerValue64();
						actorWrittenByteStart = getNbWrittenBytes(&AxiPmon);
						
						schedCaller = sched_callers_array[i];
						switch (schedCaller) {
							«FOR child : network.children.filter[label != "source" && label != "display"]»
								case «child.label»:
									call_«child.label»_scheduler();
									break;
							«ENDFOR»
							default:
								break;
						}
						actorStopTime = getTimerValue64();
						diffTime = actorStopTime - actorStartTime;
						execTimes[schedCaller] += (diffTime);
						
						actorWrittenByteStop = getNbWrittenBytes(&AxiPmon);
						writtenBytes[schedCaller] += (actorWrittenByteStop - actorWrittenByteStart);
					}
					globalStopTime = getTimerValue64();
					diffTime = globalStopTime - globalStartTime;
					execTimes[NB_ACTORS] += (diffTime);
					
					writtenBytes[NB_ACTORS] += getNbWrittenBytes(&AxiPmon);
				}
			}
		}
	'''
	
	def protected getFileContentBootloop(int procNb){
		'''
			void main() {
				while(1);
			}
		'''
	}
}