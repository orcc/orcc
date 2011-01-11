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

static population* initialize_population(struct actor_s **actors, int actorsNb, int availCoresNb);
static population* compute_next_population(population *pop, int actorsNb, int availCoresNb);
static void compute_new_mapping(individual *individual, struct scheduler_s **schedulers, int threadsNb, int actorsNb);
static void crossover(individual **children, individual **parents, int actorsNb);
static individual* mutation(individual *original, int actorsNb, int availCoresNb);
static int compare(void const *a, void const *b);

void *monitor(void *data) {
	struct monitor_s *monitoring = (struct monitor_s *) data;
	int evalIndNb = 0;
	population *population;

	// Initialize
	printf("\nGenerate initial population...\n\n");
	population = initialize_population(monitoring->actors,
			monitoring->actorsNb, monitoring->threadsNb);

	compute_new_mapping(population->individuals[evalIndNb],
			monitoring->schedulers, monitoring->threadsNb, monitoring->actorsNb);

	while (1) {
		int i;
		float fps;		
		
		// wakeup all threads
		for (i = 0; i < monitoring->threadsNb; i++) {
			sem_post(&monitoring->sync->sem_threads);
		}

		// wait threads synchro
		
		for (i = 0; i < monitoring->sync->threadsNb; i++) {
			sem_wait(&monitoring->sync->sem_monitor);
		}

		// work process
		fps = compute_fps_sync();;
		population->individuals[evalIndNb]->fps = fps;
		printf("Evaluation of mapping %i = %f fps\n",evalIndNb,fps);

		evalIndNb++;

		if (evalIndNb == POPULATION_SIZE) {
			printf("\nCompute next generation...\n\n");
			population = compute_next_population(population,
					monitoring->actorsNb, monitoring->threadsNb);
			evalIndNb = 0;
		}
		compute_new_mapping(population->individuals[evalIndNb],
				monitoring->schedulers, monitoring->threadsNb,
				monitoring->actorsNb);
	}

	return NULL;
}

static void compute_new_mapping(individual *individual, struct scheduler_s **schedulers, int threadsNb, int actorsNb) {
	int i, j, k;

	for (i = 0; i < threadsNb; i++) {
		struct actor_s **actors = (struct actor_s **) malloc(actorsNb * sizeof(struct actor_s *));
		k = 0;

		for (j = 0; j < actorsNb; j++) {
			if (individual->genes[j]->mappedCore == i) {
				actors[k] = individual->genes[j]->actor;
				k++;
			}
		}
		sched_reinit(schedulers[i], k, actors);
	}
}

static population* initialize_population(struct actor_s **actors, int actorsNb,
		int availCoresNb) {
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
		pop->individuals[i]->genes = malloc(actorsNb * sizeof(gene*));
		pop->individuals[i]->fps = -1;

		// Initialize genes randomly
		for (j = 0; j < actorsNb; j++) {
			pop->individuals[i]->genes[j] = malloc(sizeof(gene));
			pop->individuals[i]->genes[j]->actor = actors[j];
			pop->individuals[i]->genes[j]->mappedCore = rand() % availCoresNb;
		}
	}

	return pop;
}

static population* compute_next_population(population *pop, int actorsNb,
		int availCoresNb) {
	int i;

	// Allocate memory to store the new population
	population *nextPop = malloc(sizeof(population));
	individual **individuals = (individual**) malloc(POPULATION_SIZE
			* sizeof(individual*));

	// Initialize population fields
	nextPop->individuals = individuals;
	nextPop->generationNb = pop->generationNb + 1;

	// Sort population by descending fps value
	qsort(pop->individuals, sizeof(pop->individuals)/sizeof(*pop->individuals), sizeof(*pop->individuals), compare);

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

		crossover(children, parents, actorsNb);

		nextPop->individuals[i] = children[0];
		nextPop->individuals[i + 1] = children[1];
	}

	// Mutation
	for (; i < POPULATION_SIZE; i++) {
		nextPop->individuals[i] = mutation(pop->individuals[rand() % POPULATION_SIZE], actorsNb, availCoresNb);
	}

	// TODO: Remove old population and unused individuals from memory

	return nextPop;
}

static void crossover(individual **children, individual **parents, int actorsNb) {
	int i, cut = rand() % (actorsNb - 1) + 1;

	children[0] = (individual*) malloc(sizeof(individual));
	children[1] = (individual*) malloc(sizeof(individual));
	children[0]->genes = (gene**) malloc(actorsNb * sizeof(gene*));
	children[1]->genes = (gene**) malloc(actorsNb * sizeof(gene*));

	for (i = 0; i < actorsNb; i++) {
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

static individual* mutation(individual *original, int actorsNb, int availCoresNb) {
	individual *mutated = (individual*) malloc(sizeof(individual));
	int i, mutatedIndex = rand() % actorsNb;

	mutated->genes = (gene**) malloc(actorsNb * sizeof(gene*));
	mutated->fps = -1;

	for (i = 0; i < actorsNb; i++) {
		mutated->genes[i] = (gene*) malloc(sizeof(gene));

		mutated->genes[i] = original->genes[i];
		if (i == mutatedIndex) {
			mutated->genes[i]->mappedCore = rand() % availCoresNb;
		}
	}

	return mutated;
}

static int partition(population *pop, int p, int r) {
	int pivot = pop->individuals[p]->fps, i = p - 1, j = r + 1;
	individual *temp;
	printf("partition\n");
	while (1) {
		do
			j--;
		while (pop->individuals[j]->fps < pivot);
		do
			i++;
		while (pop->individuals[i]->fps > pivot);
		if (i < j) {
			temp = pop->individuals[i];
			pop->individuals[i] = pop->individuals[j];
			pop->individuals[j] = temp;
		} else
			return j;
	}
}

static void quicksort(population *pop, int p, int r) {
	int q;
	printf("quicksort\n");
	if (p < r) {
		q = partition(pop, p, r);
		quicksort(pop, p, q);
		quicksort(pop, q + 1, r);
	}
}

static int compare(void const *a, void const *b)
{
	individual const *i1 = (individual const *) a;
	individual const *i2 = (individual const *) b;

	return i1->fps - i2->fps;
}

