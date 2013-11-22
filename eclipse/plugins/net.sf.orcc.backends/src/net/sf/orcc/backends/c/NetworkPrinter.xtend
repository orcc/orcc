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
package net.sf.orcc.backends.c

import java.io.File
import java.util.HashMap
import java.util.Map
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Entity
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.graph.Vertex
import net.sf.orcc.util.OrccUtil

import static net.sf.orcc.OrccLaunchConstants.*
import static net.sf.orcc.backends.BackendsConstants.*

/**
 * Generate and print network source file for C backend.
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends CTemplate {
	
	protected val Network network;
	protected val int fifoSize;
	
	protected var boolean instrumentNetwork = false
	protected var boolean dynamicMapping = false
	var boolean ringTopology = false
	
	var boolean newSchedul = false
	
	var int threadsNb = 1;
	
	val int numberOfGroups
	protected val Map<Vertex, Integer> vertexToIdMap
	
	new(Network network, Map<String, Object> options) {
		this.network = network
		
		if (options.containsKey(FIFO_SIZE)) {
			fifoSize = options.get(FIFO_SIZE) as Integer
		} else {
			fifoSize = DEFAULT_FIFO_SIZE
		}

		if (options.containsKey(INSTRUMENT_NETWORK)) {
			instrumentNetwork = options.get(INSTRUMENT_NETWORK) as Boolean
		}
		if (options.containsKey(DYNAMIC_MAPPING)) {
			dynamicMapping = options.get(DYNAMIC_MAPPING) as Boolean
		}
		if (options.containsKey(NEW_SCHEDULER)) {
			newSchedul = options.get(NEW_SCHEDULER) as Boolean
		}
		if (options.containsKey(NEW_SCHEDULER_TOPOLOGY) ) {
			ringTopology = options.get(NEW_SCHEDULER_TOPOLOGY).equals("Ring")
		}
		if (options.containsKey(THREADS_NB)) {
			if(options.get(THREADS_NB) instanceof String) {
				threadsNb = Integer::valueOf(options.get(THREADS_NB) as String)
			} else {
				threadsNb = options.get(THREADS_NB) as Integer
			}
		}
		
		//Template data :
		// TODO : set the right values when genetic algorithm will be fixed
		numberOfGroups = 0
		vertexToIdMap = new HashMap<Vertex, Integer>
		for(instance : network.children) {
			vertexToIdMap.put(instance, 0)
		}
	}
	
	/**
	 * Print file content for the network
	 * @param targetFolder folder to print the network file
	 * @return 1 if file was cached, 0 if file was printed
	 */
	def print(String targetFolder) {
		
		val content = networkFileContent
		val file = new File(targetFolder + File::separator + network.simpleName + ".c")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def protected getNetworkFileContent() '''
		// Generated from "«network.name»"

		#include <locale.h>
		#include <stdio.h>
		#include <stdlib.h>
		
		#ifndef _WIN32
		#define __USE_GNU
		#endif
		
		#include "types.h"
		#include "fifo.h"
		#include "scheduler.h"
		#include "mapping.h"
		#include "util.h"
		#include "dataflow.h"
		«IF instrumentNetwork || dynamicMapping»
			#include "cycle.h"
		«ENDIF»
		«IF dynamicMapping»
			#include "options.h"
		«ENDIF»
		
		#include "thread.h"
		#define MAX_THREAD_NB 10
		«IF newSchedul»
			#define RING_TOPOLOGY «IF ringTopology»1«ELSE»0«ENDIF»
		«ENDIF»
		
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
		
		/////////////////////////////////////////////////
		// Actor functions
		«FOR child : network.children»
			extern void «child.label»_initialize(schedinfo_t *si);
			extern void «child.label»_scheduler(schedinfo_t *si);
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Declaration of the actors array
		
		«FOR child : network.children»
			actor_t «child.label» = {"«child.label»", «vertexToIdMap.get(child)», «child.label»_initialize, NULL, «child.label»_scheduler, 0, 0, 0, 0, NULL, 0, 0.0};			
		«ENDFOR»
		
		actor_t *actors[] = {
			«FOR child : network.children SEPARATOR ","»
				&«child.label»
			«ENDFOR»
		};

		/////////////////////////////////////////////////
		// Declaration of the connections array
		
		«FOR connection : network.connections»
			connection_t connection_«connection.target.label»_«connection.targetPort.name» = {&«connection.source.label», &«connection.target.label», 1};
		«ENDFOR»
		
		connection_t *connections[] = {
			«FOR connection : network.connections SEPARATOR ","»
			    &connection_«connection.target.label»_«connection.targetPort.name»
			«ENDFOR»
		};
		
		/////////////////////////////////////////////////
		// Declaration of the network
		network_t network = {"«network.simpleName»", actors, connections, «network.allActors.size», «network.connections.size»};

		/////////////////////////////////////////////////
		// Actor scheduler
		«printScheduler»
		
		/////////////////////////////////////////////////
		// Initializer and launcher
		
		«printLauncher»
		
		/////////////////////////////////////////////////
		// Actions to do when exting properly
		static void atexit_actions() {
			«IF instrumentNetwork || dynamicMapping»
				if (instrumentation_file != NULL) {
					save_instrumentation(instrumentation_file, network);
				}
			«ENDIF»
		}
		
		////////////////////////////////////////////////////////////////////////////////
		// Main
		int main(int argc, char *argv[]) {
		    atexit(atexit_actions);
			init_orcc(argc, argv);
			
			launcher();
			
			printf("End of simulation !\n");
			return compareErrors;
		}
	'''

	def protected printLauncher() '''
		static void launcher() {
			int i;
			
			cpu_set_t cpuset;
			
			thread_struct threads[MAX_THREAD_NB];
			thread_id_struct threads_id[MAX_THREAD_NB];
			«IF dynamicMapping»
				thread_struct thread_agent;
				thread_id_struct thread_agent_id;
				sync_t sync;
				agent_t agent;
				options_t options;
				
				sync_init(&sync);
				agent_init(&agent, &sync, &options);
			«ENDIF»
			
			mapping_t *mapping = map_actors(&network);
			local_scheduler_t *schedulers = (local_scheduler_t *) malloc(mapping->number_of_threads * sizeof(local_scheduler_t));
			waiting_t *waiting_schedulables = (waiting_t *) malloc(mapping->number_of_threads * sizeof(waiting_t));
		
			«IF !dynamicMapping»
				for(i=0; i < mapping->number_of_threads; ++i){
					sched_init(&schedulers[i], i, mapping->partitions_size[i], mapping->partitions_of_actors[i], &waiting_schedulables[i], &waiting_schedulables[(i+1) % mapping->number_of_threads], mapping->number_of_threads, NULL);
				}
			«ELSEIF dynamicMapping»
				for(i=0; i < nbThreads; ++i){
					sched_init(&schedulers[i], i, mapping->partitions_size[i], mapping->partitions_of_actors[i], &waiting_schedulables[i], &waiting_schedulables[(i+1) % nbThreads], nbThreads, &sync);
				}
			«ENDIF»
			
			clear_cpu_set(cpuset);
			
			«IF !dynamicMapping»
				for(i=0 ; i < mapping->number_of_threads; i++){
					thread_create(threads[i], scheduler, schedulers[i], threads_id[i]);
					set_thread_affinity(cpuset, mapping->threads_affinities[i], threads[i]);
				}
			«ELSEIF dynamicMapping»
				for(i=0 ; i < nbThreads; i++){
					thread_create(threads[i], scheduler, schedulers[i], threads_id[i]);
					set_thread_affinity(cpuset, mapping->threads_affinities[i], threads[i]);
				}
				thread_create(thread_agent, map, agent, thread_agent_id);
			«ENDIF»
			
			for(i=0 ; i < mapping->number_of_threads; i++){
				thread_join(threads[i]);
			}
			«IF dynamicMapping»
				thread_join(thread_agent);
			«ENDIF»
		}
	'''
	
	def protected assignFifo(Vertex vertex) '''
		«FOR connList : vertex.getAdapter(typeof(Entity)).outgoingPortMap.values»
			«printFifoAssign(connList.head.source.label, connList.head.sourcePort, connList.head.<Integer>getValueAsObject("idNoBcast"))»
			«FOR conn : connList»
				«printFifoAssign(conn.target.label, conn.targetPort, conn.<Integer>getValueAsObject("idNoBcast"))»
			«ENDFOR»
			
		«ENDFOR»
	'''
	
	def protected printFifoAssign(String name, Port port, int fifoIndex) '''
		fifo_«port.type.doSwitch»_t *«name»_«port.name» = &fifo_«fifoIndex»;
	'''

	def protected printScheduler() '''
		void *scheduler(void *data) {
			local_scheduler_t *sched = (local_scheduler_t *) data;
			actor_t *my_actor;
			schedinfo_t si;
			int j;
			«IF instrumentNetwork || dynamicMapping»
				ticks tick_in, tick_out;
				double diff_tick;
			«ENDIF»
		
			sched_init_actors(sched, &si);
			
			while (1) {
				my_actor = sched_get_next«IF newSchedul»_schedulable(sched, RING_TOPOLOGY)«ELSE»(sched)«ENDIF»;
				if(my_actor != NULL){
					«IF instrumentNetwork || dynamicMapping»
						tick_in = getticks();
					«ENDIF»
					si.num_firings = 0;
					my_actor->sched_func(&si);
					«IF instrumentNetwork || dynamicMapping»
						tick_out = getticks();
						diff_tick = elapsed(tick_out, tick_in);
						my_actor->workload += diff_tick;
					«ENDIF»
		#ifdef PRINT_FIRINGS
					printf("%2i  %5i\t%s\t%s\n", sched->id, si.num_firings, si.reason == starved ? "starved" : "full", my_actor->name);
		#endif
				}
				«IF dynamicMapping»
					if(needMapping()) {
						semaphore_set(sched->sync->sem_monitor);
						semaphore_wait(sched->sem_thread);
					}
				«ENDIF»
			}
		}
	'''

	def protected allocateFifos(Vertex vertex) '''
		«FOR connectionList : vertex.getAdapter(typeof(Entity)).outgoingPortMap.values»
			«allocateFifo(connectionList.get(0), connectionList.size)»
		«ENDFOR»
	'''
	
	def protected allocateFifo(Connection conn, int nbReaders) '''
		DECLARE_FIFO(«conn.sourcePort.type.doSwitch», «if (conn.size != null) conn.size else "SIZE"», «conn.<Object>getValueAsObject("idNoBcast")», «nbReaders»)
	'''
	
}