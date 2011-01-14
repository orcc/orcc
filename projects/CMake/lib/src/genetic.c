/*
 * Copyright (c) 2010, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <string.h>

#include "orcc.h"
#include "orcc_genetic.h"
#include "orcc_thread.h"


static struct mapping_s* compute_mapping(individual *individual, struct genetic_s *genetic_info){
	int i, j, k;
	struct mapping_s *mapping = malloc(sizeof(struct mapping_s));
	mapping->actors_per_threads = malloc(genetic_info->threads_nb * sizeof(int));
	mapping->actors_mapping = malloc(genetic_info->threads_nb * sizeof(struct actor_s **));

	for (i = 0; i < genetic_info->threads_nb; i++) {
		struct actor_s *actors_tmp[genetic_info->actors_nb];
		k = 0;

		for (j = 0; j < genetic_info->actors_nb; j++) {
			if (individual->genes[j]->mapped_core == i) {
				actors_tmp[k] = individual->genes[j]->actor;
				k++;
			}
		}

		mapping->actors_mapping[i] = malloc(k * sizeof(struct actor_s*));
		memcpy(mapping->actors_mapping[i], actors_tmp, k * sizeof(struct actor_s*));
		mapping->actors_per_threads[i] = k;
	}

	return mapping;
}


static int write_better_mapping(population *pop, struct genetic_s *genetic_info){
	FILE *mappingFile = fopen ("better_mapping.txt", "w");
	if (mappingFile != NULL){
		int i,j,k;
		fprintf (mappingFile, "// Better mapping\n\n");

		for(i = 0; i < genetic_info->population_size * genetic_info->keep_ratio; i++) {
			struct mapping_s* mapping = compute_mapping(pop->individuals[i],genetic_info);
			fprintf (mappingFile, "// Mapping n°%i (%f fps)\n\n", i, pop->individuals[i]->fps);

			for(j = 0; j < genetic_info->threads_nb; j++){
				fprintf (mappingFile, "<Partition id=\"%i\">\n", j);

				for(k = 0; k < mapping->actors_per_threads[j]; k++){
					fprintf (mappingFile, "\t<Instance actor-id=\"%s/0\"/>\n", get_actor_name(mapping->actors_mapping[j][k]));
				}

				fprintf (mappingFile, "</Partition>\n\n");
			}
		}

		fclose (mappingFile);
	}
	else {
		perror("I/O error during writing of final mapping file");
	}
	return 0;
}


static int compare(void const *a, void const *b)
{
	individual const **pi1 = (individual const **) a;
	individual const **pi2 = (individual const **) b;
	individual const *i1 = *pi1;
	individual const *i2 = *pi2;

    if(i2->fps < i1->fps){
        return -1;
    }
    else if(i2->fps > i1->fps){
    	return 1;
    }
    else{
    	return 0;
    }
}


static void crossover(individual **children, individual **parents, struct genetic_s *genetic_info) {
	int i, cut = rand() % (genetic_info->actors_nb - 1) + 1;

	children[0] = (individual*) malloc(sizeof(individual));
	children[1] = (individual*) malloc(sizeof(individual));
	children[0]->genes = (gene**) malloc(genetic_info->actors_nb * sizeof(gene*));
	children[1]->genes = (gene**) malloc(genetic_info->actors_nb * sizeof(gene*));
	children[0]->fps = -1;
	children[0]->old_fps = -1;
	children[1]->fps = -1;
	children[1]->old_fps = -1;

	for (i = 0; i < genetic_info->actors_nb; i++) {
		children[0]->genes[i] = (gene*) malloc(sizeof(gene));
		children[1]->genes[i] = (gene*) malloc(sizeof(gene));
		if (i < cut) {
			children[0]->genes[i] = parents[0]->genes[i];
			children[1]->genes[i] = parents[1]->genes[i];
		} else {
			children[0]->genes[i] = parents[1]->genes[i];
			children[1]->genes[i] = parents[0]->genes[i];
		}
	}
}


static individual* mutation(individual *original, struct genetic_s *genetic_info) {
	individual *mutated = (individual*) malloc(sizeof(individual));
	int i, mutatedIndex = rand() % genetic_info->actors_nb;

	mutated->genes = (gene**) malloc(genetic_info->actors_nb * sizeof(gene*));
	mutated->fps = -1;
	mutated->old_fps = -1;

	for (i = 0; i < genetic_info->actors_nb; i++) {
		mutated->genes[i] = (gene*) malloc(sizeof(gene));

		mutated->genes[i] = original->genes[i];
		if (i == mutatedIndex) {
			mutated->genes[i]->mapped_core = rand() % genetic_info->threads_nb;
		}
	}

	return mutated;
}


static void map_actors_on_threads(individual *individual, struct genetic_s *genetic_info) {
	int i;
	struct mapping_s *mapping  = compute_mapping(individual, genetic_info);
	struct actor_s **actors = mapping->actors_mapping[0];

	for (i = 0; i < genetic_info->threads_nb; i++) {
		sched_reinit(genetic_info->schedulers[i], mapping->actors_per_threads[i], mapping->actors_mapping[i]);
	}
}


static population* compute_next_population(population *pop, struct genetic_s *genetic_info) {
	int i;

	// Allocate memory to store the new population
	population *nextPop = malloc(sizeof(population));
	individual **individuals = (individual**) malloc(genetic_info->population_size
			* sizeof(individual*));

	// Initialize population fields
	nextPop->individuals = individuals;
	nextPop->generation_nb = pop->generation_nb + 1;

	// Sort population by descending fps value
	qsort(pop->individuals, genetic_info->population_size, sizeof(individual*), compare);

	// Backup better individuals
	for (i = 0; i < genetic_info->population_size * genetic_info->keep_ratio; i++) {
		nextPop->individuals[i] = pop->individuals[i];
		nextPop->individuals[i]->old_fps = nextPop->individuals[i]->fps;
		nextPop->individuals[i]->fps = -1;
	}

	// Crossover
	int free = genetic_info->population_size - i;
	int last = free * genetic_info->crossover_ratio + i;
	if ((last - free) % 2 == 1) {
		last = last - 1;
	}
	for (; i < last; i = i + 2) {
		individual *children[2];
		individual *parents[2];

		// TOFIX: choose parents with tournament
		parents[0] = pop->individuals[rand() % genetic_info->population_size];
		parents[1] = pop->individuals[rand() % genetic_info->population_size];

		crossover(children, parents, genetic_info);

		nextPop->individuals[i] = children[0];
		nextPop->individuals[i + 1] = children[1];
	}

	// Mutation
	for (; i < genetic_info->population_size; i++) {
		nextPop->individuals[i] = mutation(pop->individuals[rand() % genetic_info->population_size], genetic_info);
	}

	// TODO: Remove old population and unused individuals from memory

	return nextPop;
}


static population* initialize_population(struct genetic_s *genetic_info) {
	int i, j;

	// Allocate memory to store the new population
	population *pop = (population *) malloc(sizeof(population));
	individual **individuals = (individual**) malloc(genetic_info->population_size
			* sizeof(individual*));

	// Initialize population fields
	pop->individuals = individuals;
	pop->generation_nb = 0;

	// Initialize random function
	srand(time(NULL));

	// Initialize first generation of individuals
	for (i = 0; i < genetic_info->population_size; i++) {
		pop->individuals[i] = malloc(sizeof(individual));
		pop->individuals[i]->genes = malloc(genetic_info->actors_nb * sizeof(gene*));
		pop->individuals[i]->fps = -1;
		pop->individuals[i]->old_fps = -1;

		// Initialize genes randomly
		for (j = 0; j < genetic_info->actors_nb; j++) {
			pop->individuals[i]->genes[j] = malloc(sizeof(gene));
			pop->individuals[i]->genes[j]->actor = genetic_info->actors[j];
			pop->individuals[i]->genes[j]->mapped_core = rand() % genetic_info->threads_nb;
		}
	}

	return pop;
}


void *monitor(void *data) {
	struct monitor_s *monitoring = (struct monitor_s *) data;
	int i, evalIndNb = 0;
	population *population;

	// Initialize
	printf("\nGenerate initial population...\n\n");
	population = initialize_population(monitoring->genetic_info);

	map_actors_on_threads(population->individuals[evalIndNb], monitoring->genetic_info);

	while (population->generation_nb < monitoring->genetic_info->generation_nb) {
		float fps;

		// Backup informations to compute partial fps except first time
		if(evalIndNb != 0 || population->generation_nb != 0){
			backup_partial_start_info();
		}
		// wakeup all threads
		for (i = 0; i < monitoring->genetic_info->threads_nb; i++) {
			sem_post(&monitoring->sync->sem_threads);
		}

		// wait threads synchro
		for (i = 0; i < monitoring->genetic_info->threads_nb; i++) {
			sem_wait(&monitoring->sync->sem_monitor);
		}
		backup_partial_end_info();

		// work process
		population->individuals[evalIndNb]->fps = compute_partial_fps();
		printf("Evaluation of mapping %i = %f fps",evalIndNb,population->individuals[evalIndNb]->fps);
		if(population->individuals[evalIndNb]->old_fps == -1){
			printf("\n");
		}
		else{
			printf(" (old = %f fps)\n",population->individuals[evalIndNb]->old_fps);
		}

		evalIndNb++;

		if (evalIndNb == monitoring->genetic_info->population_size) {
			printf("\nCompute next generation...\n\n");
			population = compute_next_population(population, monitoring->genetic_info);
			evalIndNb = 0;
		}
		map_actors_on_threads(population->individuals[evalIndNb], monitoring->genetic_info);
	}
	monitoring->sync->active_sync = 0;
	write_better_mapping(population,monitoring->genetic_info);
	active_fps_printing();

	for (i = 0; i < monitoring->genetic_info->threads_nb; i++) {
		sem_post(&monitoring->sync->sem_threads);
	}

	return NULL;
}

