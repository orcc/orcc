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

import java.util.Map
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.graph.Vertex
import net.sf.orcc.util.OrccLogger
import java.util.HashMap

/**
 * Compile top Network c source code 
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends CTemplate {
	
	val Network network;
	val Map<String, Object> options
	val int fifoSize;
	
	var boolean geneticAlgo = false
	var boolean ringTopology = false
	
	var boolean newSchedul = false
	
	var int threadsNb = 1;
	
	val int numberOfGroups
	val Map<Instance, Integer> instanceToIdMap
	
	new(Network network, Map<String, Object> options) {
		this.network = network
		this.options = options
		
		if (options.containsKey("fifoSize")) {
			fifoSize = options.get("fifoSize") as Integer
		} else {
			fifoSize = 512
			OrccLogger::warnln("fifoSize option is not set")
		}

		if (options.containsKey("useGeneticAlgorithm")) {
			geneticAlgo = options.get("useGeneticAlgorithm") as Boolean
		}
		if (options.containsKey("newScheduler")) {
			newSchedul = options.get("newScheduler") as Boolean
		}
		if (options.containsKey("ringTopology")) {
			ringTopology = options.get("ringTopology") as Boolean
		}
		if (options.containsKey("threadsNb")) {
			threadsNb = options.get("threadsNb") as Integer
		}
				
		//Template data :
		// TODO : set the right values when genetic algorithm will be fixed
		numberOfGroups = 0
		instanceToIdMap = computeInstanceToIdMap
	}

	def getNetworkFileContent() '''
		// Generated from "«network.name»"

		#include <locale.h>
		#include <stdio.h>
		#include <stdlib.h>
		«IF geneticAlgo»
			#include <time.h>
		«ENDIF»
		
		#ifndef _WIN32
		#define __USE_GNU
		#endif
		
		#include "orcc_types.h"
		#include "orcc_fifo.h"
		#include "orcc_scheduler.h"
		#include "orcc_util.h"
		
		«IF geneticAlgo || threadsNb > 1»
			#include "orcc_thread.h"
		«ENDIF»
		«IF geneticAlgo»
			#include "orcc_genetic.h"
			#define THREAD_NB «threadsNb»
			#define POPULATION_SIZE 100
			#define GENERATION_NB 20
			
			#define GROUPS_RATIO 0.8
			#define KEEP_RATIO 0.2
			#define CROSSOVER_RATIO 0.8
			
			#define TIMEOUT 900
			#define STEP_BW_CHK 1000000
			
			#define CACHE_SIZE 4096
		«ELSE»
			#define MAX_THREAD_NB 10
		«ENDIF»
		«IF newSchedul»
			#define RING_TOPOLOGY «IF ringTopology»1«ELSE»0«ENDIF»
		«ENDIF»
		
		#define SIZE «fifoSize»
		// #define PRINT_FIRINGS

		/////////////////////////////////////////////////
		// FIFO allocation
		«FOR vertice : network.children.filter(typeof(Instance)).filter[isActor]»
			«vertice.allocateFifos»
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// FIFO pointer assignments
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.assignFifo»
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Action schedulers
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			extern void «instance.name»_initialize(«FOR port : instance.actor.inputs SEPARATOR ", "»unsigned int fifo_«port.name»_id«ENDFOR»);
		«ENDFOR»
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			extern void «instance.name»_scheduler(struct schedinfo_s *si);
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Declaration of a struct actor for each actor
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			struct actor_s «instance.name»;
		«ENDFOR»

		/////////////////////////////////////////////////
		// Declaration of the actors array
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			struct actor_s «instance.name» = {"«instance.name»", «instanceToIdMap.get(instance)», «instance.name»_scheduler, «instance.actor.inputs.size»0, «instance.actor.outputs.size», 0, 0, NULL, 0};			
		«ENDFOR»
		
		struct actor_s *actors[] = {
			«FOR instance : network.children.filter(typeof(Instance)).filter[isActor] SEPARATOR ","»
				&«instance.name»
			«ENDFOR»
		};
		
		«IF geneticAlgo»
			extern int source_is_stopped();
			extern int clean_cache(int size);
			
			void clear_fifos() {
				«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
					«instance.clearFifosWithMap»
				«ENDFOR»
			}
			static int timeout = 0;
			int is_timeout() {
				return timeout;
			}
		«ENDIF»

		/////////////////////////////////////////////////
		// Actor scheduler
		«printScheduler»
		
		/////////////////////////////////////////////////
		// Initializer and launcher
		void initialize_instances() {
			«FOR instance : network.children.filter(typeof(Instance))»
				«IF instance.isActor»
					«instance.name»_initialize(«FOR port : instance.actor.inputs SEPARATOR ","»«if (instance.incomingPortMap.get(port) != null) instance.incomingPortMap.get(port).getAttribute("fifoId").value else "-1"»«ENDFOR»);
				«ENDIF»
			«ENDFOR»
		}
		
		static void launcher() {
			int i, display_scheduler = -1;
			
			cpu_set_t cpuset;
			«IF ! geneticAlgo»
				thread_struct threads[MAX_THREAD_NB];
				thread_id_struct threads_id[MAX_THREAD_NB];
				
				struct mapping_s *mapping = map_actors(actors, sizeof(actors) / sizeof(actors[0]));
				struct scheduler_s *schedulers = (struct scheduler_s *) malloc(mapping->number_of_threads * sizeof(struct scheduler_s));
				struct waiting_s *waiting_schedulables = (struct waiting_s *) malloc(mapping->number_of_threads * sizeof(struct waiting_s));
			«ELSE»
				thread_struct threads[THREAD_NB], thread_monitor;
				thread_id_struct threads_id[THREAD_NB], thread_monitor_id;
				
				struct scheduler_s schedulers[THREAD_NB];
				struct waiting_s waiting_schedulables[THREAD_NB];
				
				struct sync_s sched_sync;
				struct genetic_s genetic_info;
				struct monitor_s monitoring;
				
				sync_init(&sched_sync);
				genetic_init(&genetic_info, POPULATION_SIZE, GENERATION_NB, KEEP_RATIO, CROSSOVER_RATIO, actors, schedulers, sizeof(actors) / sizeof(actors[0]), THREAD_NB, «IF newSchedul»RING_TOPOLOGY«ELSE»0«ENDIF», «numberOfGroups», GROUPS_RATIO);
				monitor_init(&monitoring, &sched_sync, &genetic_info);
			«ENDIF»
			
			initialize_instances();
			
			«IF !geneticAlgo»
				for(i=0; i < mapping->number_of_threads; ++i){
					sched_init(&schedulers[i], mapping->threads_ids[i], mapping->partitions_size[i], mapping->partitions_of_actors[i], &waiting_schedulables[i], &waiting_schedulables[(i+1) % mapping->number_of_threads], mapping->number_of_threads, NULL);
				}
			«ELSE»
				for(i=0; i < THREAD_NB; ++i){
					sched_init(&schedulers[i], i, 0, NULL, &waiting_schedulables[i], &waiting_schedulables[(i+1) % THREAD_NB], THREAD_NB, &sched_sync);
				}
			«ENDIF»
			
			clear_cpu_set(cpuset);
			
			for(i=0 ; i < «if (geneticAlgo) "THREAD_NB" else "mapping->number_of_threads"» ; i++){
				if(find_actor("display", schedulers[i].actors, schedulers[i].num_actors) == NULL){
					thread_create(threads[i], scheduler, schedulers[i], threads_id[i]);
					set_thread_affinity(cpuset, i, threads[i]);
				} else {
					display_scheduler = i;
				}
			}
			«IF geneticAlgo»
				thread_create(thread_monitor, monitor, monitoring, thread_monitor_id);
			«ENDIF»
			
			if(display_scheduler != -1){
				(*scheduler)((void*) &schedulers[display_scheduler]);
			}
			
			for(i=0 ; i < «if (geneticAlgo) "THREAD_NB" else "mapping->number_of_threads"» ; i++){
				if(i != display_scheduler){
					thread_join(threads[i]);
				}
			}
			«IF geneticAlgo»
				thread_join(thread_monitor);
			«ENDIF»
		}
		
		////////////////////////////////////////////////////////////////////////////////
		// Main
		int main(int argc, char *argv[]) {
			init_orcc(argc, argv);
			
			launcher();
			
			printf("End of simulation ! «IF ! geneticAlgo» Press a key to continue«ENDIF»\n");
			«IF ! geneticAlgo»wait_for_key();«ENDIF»
			return 0;
		}
	'''
	
	// TODO : simplify this :
	def assignFifo(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && !(connList.head.target instanceof Port)»
				«printFifoAssign(connList.head.source, connList.head.sourcePort, connList.head.getAttribute("idNoBcast").value as Integer)»
			«ENDIF»
			«FOR conn : connList»
				«IF conn.source instanceof Instance && conn.target instanceof Instance»
					«printFifoAssign(conn.target as Instance, conn.targetPort, conn.getAttribute("idNoBcast").value as Integer)»
				«ENDIF»
			«ENDFOR»
			
		«ENDFOR»
	'''
	
	def printFifoAssign(Vertex vertex, Port port, int fifoIndex) '''
		«IF vertex instanceof Instance»struct fifo_«port.type.doSwitch»_s *«(vertex as Instance).name»_«port.name» = &fifo_«fifoIndex»;«ENDIF»
	'''

	def printScheduler() '''
		void *scheduler(void *data) {
			struct scheduler_s *sched = (struct scheduler_s *) data;
			struct actor_s *my_actor;
			struct schedinfo_s si;
			«IF geneticAlgo»
				
				int i = 0;
				clock_t start, end;	
				semaphore_wait(sched->sem_thread);
				start = clock ();
			«ENDIF»
			
			while (1) {
				my_actor = sched_get_next«IF newSchedul»_schedulable(sched, RING_TOPOLOGY)«ELSE»(sched)«ENDIF»;
				if(my_actor != NULL){
					si.num_firings = 0;
					my_actor->sched_func(&si);
		#ifdef PRINT_FIRINGS
					printf("%2i  %5i\t%s\t%s\n", sched->id, si.num_firings, si.reason == starved ? "starved" : "full", my_actor->name);
		#endif
				}
				«IF geneticAlgo»
					++i;
					if(i > STEP_BW_CHK) {
						end = clock();
						timeout = ( (end - start) / (double) CLOCKS_PER_SEC) >= TIMEOUT;
						i = 0;
					}
					if(source_is_stopped() || timeout) {
						semaphore_set(sched->sync->sem_monitor);
						clean_cache(CACHE_SIZE);
						semaphore_wait(sched->sem_thread);
						timeout = 0;
						start = clock ();
					}
				«ENDIF»
			}
		}
	'''

	def clearFifosWithMap(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF connList.get(0).source instanceof Instance && connList.get(0).target instanceof Instance»
				«clearFifo(connList.get(0))»
			«ENDIF»
		«ENDFOR»
	'''
	
	def clearFifo(Connection connection) '''
		«IF connection.source instanceof Actor»
			fifo_«connection.sourcePort.type.doSwitch»_clear(&fifo_«connection.getAttribute("idNoBcast")»);
		«ELSE»
			fifo_«connection.targetPort.type.doSwitch»_clear(&fifo_«connection.getAttribute("id")»);
		«ENDIF»
	'''

	def allocateFifos(Instance instance) '''
		«FOR connectionList : instance.outgoingPortMap.values»
			«allocateFifo(connectionList.get(0), connectionList.size)»
		«ENDFOR»
	'''
	
	def allocateFifo(Connection conn, int nbReaders) '''
		«IF conn.source instanceof Instance»
			DECLARE_FIFO(«conn.sourcePort.type.doSwitch», «if (conn.size != null) conn.size else "SIZE"», «conn.getAttribute("idNoBcast").value», «nbReaders»)
		«ELSEIF conn.target instanceof Instance»
			DECLARE_FIFO(«conn.targetPort.type.doSwitch», «if (conn.size != null) conn.size else "SIZE"», «conn.getAttribute("idNoBcast").value», «nbReaders»)
		«ELSE»
			«/* TODO: debug to find types of source & target when compiling a non-top network */»
			«OrccLogger::warnln("An edge has both source and target linked to a non-Actor vertex.")»
		«ENDIF»
	'''

	def computeInstanceToIdMap() {
		val instanceToIdMap = new HashMap<Instance, Integer>
		
		for(instance : network.children.filter(typeof(Instance))) {
			// TODO : compute the right value when genetic algorithm will be fixed
			instanceToIdMap.put(instance, 0)
		}
		
		return instanceToIdMap
	}
}