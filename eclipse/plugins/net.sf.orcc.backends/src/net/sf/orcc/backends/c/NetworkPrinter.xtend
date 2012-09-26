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

import net.sf.orcc.df.Network
import java.util.Map
import net.sf.orcc.graph.Vertex
import net.sf.orcc.df.Instance

/*
 * Compile Top_network Java source code 
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends CTemplate {
	
	val Network network;
	val Map<String, Object> options
	var boolean geneticAlgo
	var boolean newSchedul
	
	new(Network network, Map<String, Object> options){
		super()
		this.network = network
		this.options = options
		
		geneticAlgo = options.containsKey("useGeneticAlgorithm")
		newSchedul = options.containsKey("newScheduler")
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
		«IF options.containsKey("threads") || geneticAlgo»
			#include "orcc_thread.h"
		«ENDIF»
		«IF geneticAlgo»
			#include "orcc_genetic.h"
			#define THREAD_NB <options.threadsNb>
		«ELSE»
			#define MAX_THREAD_NB 10
		«ENDIF»
		
		«IF newSchedul»
			#define RING_TOPOLOGY <if(options.ringTopology)>1<else>0<endif>
		«ENDIF»
		«IF geneticAlgo»
			#define POPULATION_SIZE 100
			#define GENERATION_NB 20
			
			#define GROUPS_RATIO 0.8
			#define KEEP_RATIO 0.2
			#define CROSSOVER_RATIO 0.8
			
			#define TIMEOUT 900
			#define STEP_BW_CHK 1000000
			
			#define CACHE_SIZE 4096
		«ENDIF»
		
		#define SIZE <options.fifoSize>
		// #define PRINT_FIRINGS

		/////////////////////////////////////////////////
		// FIFO allocation
		«FOR vertice : network.vertices»
			«vertice.allocateFifo»
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// FIFO pointer assignments
		«FOR vertice : network.vertices»
			«vertice.assignFifo»
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Action schedulers
		«FOR instance : network.children.filter(typeof(Instance))»
			«instance.actorInitialize»
		«ENDFOR»
		«FOR instance : network.children.filter(typeof(Instance))»
			«instance.actionScheduler»
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Declaration of a struct actor for each actor
		«FOR instance : network.children.filter(typeof(Instance))»
			struct actor_s «instance.name»;
		«ENDFOR»

		/////////////////////////////////////////////////
		// Declaration of the actors array
		«FOR instance : network.children.filter(typeof(Instance))»
			«instance.fillActorsStructs»
		«ENDFOR»
		
		«FOR instance : network.children.filter(typeof(Instance))»
			«instance.declareActorArray»
		«ENDFOR»
		
		«IF geneticAlgo»
			extern int source_is_stopped();
			extern int clean_cache(int size);
			
			void clear_fifos() {
				«FOR instance : network.children.filter(typeof(Instance))»
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
		«FOR instance : network.children.filter(typeof(Instance))»
			«instance.printInitialize»
		«ENDFOR»
		
		static void launcher() {
			int i, display_scheduler = -1;
			
			cpu_set_t cpuset;
			«IF geneticAlgo»
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
				genetic_init(&genetic_info, POPULATION_SIZE, GENERATION_NB, KEEP_RATIO, CROSSOVER_RATIO, actors, schedulers, sizeof(actors) / sizeof(actors[0]), THREAD_NB, <if(options.newScheduler)>RING_TOPOLOGY<else>0<endif>, <network.templateData.numberOfGroups>, GROUPS_RATIO);
				monitor_init(&monitoring, &sched_sync, &genetic_info);
			«ENDIF»
			
			initialize_instances();
			
			«IF geneticAlgo»
				for(i=0; i < mapping->number_of_threads; ++i){
					sched_init(&schedulers[i], mapping->threads_ids[i], mapping->partitions_size[i], mapping->partitions_of_actors[i], &waiting_schedulables[i], &waiting_schedulables[(i+1) % mapping->number_of_threads], mapping->number_of_threads, NULL);
				}
			«ELSE»
				for(i=0; i < THREAD_NB; ++i){
					sched_init(&schedulers[i], i, 0, NULL, &waiting_schedulables[i], &waiting_schedulables[(i+1) % THREAD_NB], THREAD_NB, &sched_sync);
				}
			«ENDIF»
			
			
			«IF newSchedul && !geneticAlgo»
				// <printAllAddSchedulable(network.templateData.sourceInstances)>
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
	
	def printInitialize(Instance instance) {
		// <printInitializes(network.instances)
		// Warning : list > element update
	}

	def printScheduler() { }

	def fillActorsStructs(Instance instance) {
		// see fillActorsStructs(network.instances)
		// Warning : list > element update
	}

	def clearFifosWithMap(Instance instance) { }

	def declareActorArray(Instance instance) {
		// see <declareActorsArray(network.instances)>
		// Warning : list > element update
	}

	def actionScheduler(Instance instance) { }

	
	
	def actorInitialize(Instance instance) { }

	def assignFifo(Vertex vertex) { }

	def allocateFifo(Vertex vertex) { }

}