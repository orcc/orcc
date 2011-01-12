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

#include "orcc.h"
#include "orcc_genetic.h"
#include "orcc_thread.h"


static int compare(void const *a, void const *b)
{
	individual const **pi1 = (individual const **) a;
	individual const **pi2 = (individual const **) b;
	individual const *i1 = *pi1;
	individual const *i2 = *pi2;

	return i2->fps - i1->fps;
}


static void crossover(individual **children, individual **parents, struct genetic_s *genetic_info) {
	int i, cut = rand() % (genetic_info->actorsNb - 1) + 1;

	children[0] = (individual*) malloc(sizeof(individual));
	children[1] = (individual*) malloc(sizeof(individual));
	children[0]->genes = (gene**) malloc(genetic_info->actorsNb * sizeof(gene*));
	children[1]->genes = (gene**) malloc(genetic_info->actorsNb * sizeof(gene*));

	for (i = 0; i < genetic_info->actorsNb; i++) {
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
	int i, mutatedIndex = rand() % genetic_info->actorsNb;

	mutated->genes = (gene**) malloc(genetic_info->actorsNb * sizeof(gene*));
	mutated->fps = -1;

	for (i = 0; i < genetic_info->actorsNb; i++) {
		mutated->genes[i] = (gene*) malloc(sizeof(gene));

		mutated->genes[i] = original->genes[i];
		if (i == mutatedIndex) {
			mutated->genes[i]->mappedCore = rand() % genetic_info->threadsNb;
		}
	}

	return mutated;
}


static void compute_new_mapping(individual *individual, struct genetic_s *genetic_info) {
	int i, j, k;

	for (i = 0; i < genetic_info->threadsNb; i++) {
		struct actor_s **actors = (struct actor_s **) malloc(genetic_info->actorsNb * sizeof(struct actor_s *));
		k = 0;

		for (j = 0; j < genetic_info->actorsNb; j++) {
			if (individual->genes[j]->mappedCore == i) {
				actors[k] = individual->genes[j]->actor;
				k++;
			}
		}
		sched_reinit(genetic_info->schedulers[i], k, actors);
	}
}


static population* compute_next_population(population *pop, struct genetic_s *genetic_info) {
	int i;

	// Allocate memory to store the new population
	population *nextPop = malloc(sizeof(population));
	individual **individuals = (individual**) malloc(POPULATION_SIZE
			* sizeof(individual*));

	// Initialize population fields
	nextPop->individuals = individuals;
	nextPop->generationNb = pop->generationNb + 1;

	printf("FPS unsort:");
		for (i = 0; i < POPULATION_SIZE; i++) {
			printf(" %f",pop->individuals[i]->fps);
		}
		printf("\n");

	// Sort population by descending fps value
	qsort(pop->individuals, POPULATION_SIZE, sizeof(individual*), compare);

	printf("FPS sort:");
	for (i = 0; i < POPULATION_SIZE; i++) {
		printf(" %f",pop->individuals[i]->fps);
	}
	printf("\n");

	// Backup better individuals
	for (i = 0; i < POPULATION_SIZE * KEEP_RATIO; i++) {
		nextPop->individuals[i] = pop->individuals[i];
	}

	// Crossover
	int free = POPULATION_SIZE - i;
	int last = free * CROSS_OVER_RATIO + i;
	if ((last - free) % 2 == 1) {
		last = last - 1;
	}
	for (; i < last; i = i + 2) {
		individual *children[2];
		individual *parents[2];

		// TOFIX: choose parents with tournament
		parents[0] = pop->individuals[rand() % POPULATION_SIZE];
		parents[1] = pop->individuals[rand() % POPULATION_SIZE];

		crossover(children, parents, genetic_info);

		nextPop->individuals[i] = children[0];
		nextPop->individuals[i + 1] = children[1];
	}

	// Mutation
	for (; i < POPULATION_SIZE; i++) {
		nextPop->individuals[i] = mutation(pop->individuals[rand() % POPULATION_SIZE], genetic_info);
	}

	// TODO: Remove old population and unused individuals from memory

	return nextPop;
}


static population* initialize_population(struct genetic_s *genetic_info) {
	int i, j;

	// Allocate memory to store the new population
	population *pop = (population *) malloc(sizeof(population));
	individual **individuals = (individual**) malloc(POPULATION_SIZE
			* sizeof(individual*));

	// Initialize population fields
	pop->individuals = individuals;
	pop->generationNb = 0;

	// Initialize random function
	srand(time(NULL));

	// Initialize first generation of individuals
	for (i = 0; i < POPULATION_SIZE; i++) {
		pop->individuals[i] = malloc(sizeof(individual));
		pop->individuals[i]->genes = malloc(genetic_info->actorsNb * sizeof(gene*));
		pop->individuals[i]->fps = -1;

		// Initialize genes randomly
		for (j = 0; j < genetic_info->actorsNb; j++) {
			pop->individuals[i]->genes[j] = malloc(sizeof(gene));
			pop->individuals[i]->genes[j]->actor = genetic_info->actors[j];
			pop->individuals[i]->genes[j]->mappedCore = rand() % genetic_info->threadsNb;
		}
	}

	return pop;
}


void *monitor(void *data) {
	struct genetic_s *genetic_info = (struct genetic_s *) data;
	int evalIndNb = 0;
	population *population;

	// Initialize
	printf("\nGenerate initial population...\n\n");
	population = initialize_population(genetic_info);

	compute_new_mapping(population->individuals[evalIndNb], genetic_info);

	while (1) {
		int i;
		float fps;

		// wakeup all threads
		for (i = 0; i < genetic_info->threadsNb; i++) {
			sem_post(&genetic_info->sync->sem_threads);
		}

		// wait threads synchro

		for (i = 0; i < genetic_info->sync->threadsNb; i++) {
			sem_wait(&genetic_info->sync->sem_monitor);
		}

		// work process
		fps = compute_fps_sync();;
		population->individuals[evalIndNb]->fps = fps;
		printf("Evaluation of mapping %i = %f fps\n",evalIndNb,fps);

		evalIndNb++;

		if (evalIndNb == POPULATION_SIZE) {
			printf("\nCompute next generation...\n\n");
			population = compute_next_population(population, genetic_info);
			evalIndNb = 0;
		}
		compute_new_mapping(population->individuals[evalIndNb], genetic_info);
	}

	return NULL;
}

