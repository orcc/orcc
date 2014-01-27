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
import net.sf.orcc.df.Actor
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
	
	protected var boolean profileNetwork = false
	protected var boolean profileActions = false
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

		if (options.containsKey(PROFILE_NETWORK)) {
			profileNetwork = options.get(PROFILE_NETWORK) as Boolean
			if(options.containsKey(PROFILE_ACTIONS)){
				profileActions = options.get(PROFILE_ACTIONS) as Boolean
			}
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
		«IF profileNetwork || dynamicMapping»
			#include "cycle.h"
			#include "serialize.h"
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
		
		«IF profileActions && profileNetwork»
			/////////////////////////////////////////////////
			// Declaration of the actions
			
			«FOR child : network.children»
				«FOR act : child.getAdapter(typeof(Actor)).actions»
					action_t action_«child.label»_«act.body.name» = {"«act.body.name»", 0, 0};			
				«ENDFOR»
			«ENDFOR»
			
			«FOR child : network.children»
				action_t *«child.label»_actions[] = {
					«FOR act : child.getAdapter(typeof(Actor)).actions SEPARATOR ","»
						&action_«child.label»_«act.body.name»
					«ENDFOR»
				};
				
			«ENDFOR»
		«ENDIF»
		
		/////////////////////////////////////////////////
		// Actor functions
		«FOR child : network.children»
			extern void «child.label»_initialize(schedinfo_t *si);
			extern void «child.label»_scheduler(schedinfo_t *si);
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Declaration of the actors array
		
		«FOR child : network.children»
			«IF profileActions && profileNetwork»
				actor_t «child.label» = {"«child.label»", «vertexToIdMap.get(child)», «child.label»_initialize, NULL, «child.label»_scheduler, 0, 0, 0, 0, NULL, -1, «network.children.indexOf(child)», 0, 1, 0, 0, 0, «child.label»_actions, «child.getAdapter(typeof(Actor)).actions.size», 0};
			«ELSE»
				actor_t «child.label» = {"«child.label»", «vertexToIdMap.get(child)», «child.label»_initialize, NULL, «child.label»_scheduler, 0, 0, 0, 0, NULL, -1, «network.children.indexOf(child)», 0, 1, 0, 0, 0, NULL, 0, 0};
			«ENDIF»						
		«ENDFOR»
		
		actor_t *actors[] = {
			«FOR child : network.children SEPARATOR ","»
				&«child.label»
			«ENDFOR»
		};

		/////////////////////////////////////////////////
		// Declaration of the connections array
		
		«FOR connection : network.connections»
			connection_t connection_«connection.target.label»_«connection.targetPort.name» = {&«connection.source.label», &«connection.target.label», 0, 0};
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
			«IF profileNetwork || dynamicMapping»
				if (profiling_file != NULL) {
					compute_workloads(&network);
					save_profiling(profiling_file, &network);
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
			mapping_t *mapping = map_actors(&network);
			int nb_threads = «IF dynamicMapping»nbThreads«ELSE»mapping->number_of_threads«ENDIF»;
			
			cpu_set_t cpuset;
			thread_struct threads[MAX_THREAD_NB];
			thread_id_struct threads_id[MAX_THREAD_NB];
			«IF dynamicMapping»
				thread_struct thread_agent;
				thread_id_struct thread_agent_id;
				sync_t sync;
				options_t *options = set_options(mapping_strategy, nb_threads);
			«ENDIF»
			
			global_scheduler_t *scheduler = allocate_global_scheduler(nb_threads, «IF dynamicMapping»&sync«ELSE»NULL«ENDIF»);
			«IF dynamicMapping»
				agent_t *agent = agent_init(&sync, options, scheduler, &network, nb_threads);
				sync_init(&sync);
			«ENDIF»
			
			global_scheduler_init(scheduler, mapping);
			
			clear_cpu_set(cpuset);
			
			for(i=0 ; i < nb_threads; i++){
				thread_create(threads[i], scheduler_routine, *scheduler->schedulers[i], threads_id[i]);
				set_thread_affinity(cpuset, i, threads[i]);
			}
			«IF dynamicMapping»
				thread_create(thread_agent, agent_routine, *agent, thread_agent_id);
			«ENDIF»
			
			for(i=0 ; i < nb_threads; i++){
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
		void *scheduler_routine(void *data) {
			local_scheduler_t *sched = (local_scheduler_t *) data;
			actor_t *my_actor;
			schedinfo_t si;
			int j;
			«IF profileNetwork || dynamicMapping»
				ticks tick_in, tick_out;
				double diff_tick;
			«ENDIF»
		
			set_realtime_priority();
			sched_init_actors(sched, &si);
			
			while (1) {
				my_actor = sched_get_next«IF newSchedul»_schedulable(sched, RING_TOPOLOGY)«ELSE»(sched)«ENDIF»;
				if(my_actor != NULL){
					«IF profileNetwork || dynamicMapping»
						tick_in = getticks();
					«ENDIF»
					si.num_firings = 0;
					my_actor->sched_func(&si);
					«IF profileNetwork || dynamicMapping»
						tick_out = getticks();
						diff_tick = elapsed(tick_out, tick_in);
						my_actor->ticks += diff_tick;
					«ENDIF»
		#ifdef PRINT_FIRINGS
					printf("%2i  %5i\t%s\t%s\n", sched->id, si.num_firings, si.reason == starved ? "starved" : "full", my_actor->name);
		#endif
				}
				«IF dynamicMapping»
					if(my_actor == NULL || needMapping()) {
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