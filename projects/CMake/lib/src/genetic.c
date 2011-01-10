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

void *monitor(void *data) {
	struct monitor_s *monitoring = (struct monitor_s *) data;
	int i, evalIndNb = 0;

	// Initialize
	population *population = initializePopulation(monitoring->actors,
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
		printf("Time to process mapping (all threads are stopped)...\n");
		fps = compute_fps_sync();;
		population->individuals[evalIndNb]->fps = fps;
		printf("Computed FPS = %f\n",fps);

		evalIndNb++;

		if (evalIndNb == POPULATION_SIZE) {
			population = computeNextPopulation(population,
					monitoring->actorsNb, monitoring->threadsNb);
			evalIndNb = 0;
		}
		compute_new_mapping(population->individuals[evalIndNb],
				monitoring->schedulers, monitoring->threadsNb,
				monitoring->actorsNb);
	}

	return NULL;
}

void compute_new_mapping(individual *individual, struct scheduler_s **schedulers, int threadsNb, int actorsNb) {
	printf("compute_new_mapping\n");
	int i, j, k;
	for (i = 0; i < threadsNb; i++) {
		struct actor_s **actors = (struct actor_s **) malloc(actorsNb * sizeof(struct actor_s **));
		k = 0;
		for (j = 0; j < actorsNb; j++) {
			gene *gene = individual->genes[j];
			if (gene->mappedCore == i) {
				actors[k] = gene->actor;
				k++;
			}
		}
		sched_reinit(schedulers[i], k, actors);
	}
}

population* initializePopulation(struct actor_s **actors, int actorsNb,
		int availCoresNb) {
	printf("Monitor: initialize population\n");

	// Allocate memory to store the new population
	population *pop = (population *) malloc(sizeof(*pop));
	individual **individuals = (individual**) malloc(POPULATION_SIZE
			* sizeof(**individuals));

	// Initialize population fields
	pop->individuals = individuals;
	pop->generationNb = 0;

	// Initialize random function
	srand(time(NULL));

	// Create first generation of individuals
	int i, j;
	for (i = 0; i < POPULATION_SIZE; i++) {
		// Allocate memory to store new individuals
		individual *ind = malloc(sizeof(individual));
		gene **genes = malloc(actorsNb * sizeof(**genes));

		// Initialize individuals fields
		ind->genes = genes;
		ind->fps = -1;
		for (j = 0; j < actorsNb; j++) {
			gene *gene = malloc(sizeof(*gene));
			gene->actor = actors[j];
			gene->mappedCore = rand() % availCoresNb;
			ind->genes[j] = gene;
		}
		pop->individuals[i] = ind;
	}

	return pop;
}

population* computeNextPopulation(population *pop, int actorsNb,
		int availCoresNb) {
	printf("compute next population\n");
	// Allocate memory to store the new population
	population *nextPop = malloc(sizeof(population));
	individual **individuals = (individual**) malloc(POPULATION_SIZE
			* sizeof(**individuals));

	// Initialize population fields
	nextPop->individuals = individuals;
	nextPop->generationNb = pop->generationNb + 1;

	// Sort population by descending fps value
	quickSort(pop, 0, POPULATION_SIZE-1);

	// Backup better individuals
	int i;
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
		children[0] = malloc(sizeof(children[0]));
		children[1] = malloc(sizeof(children[1]));

		// TOFIX: choose parents with tournament
		parents[0] = pop->individuals[rand() % POPULATION_SIZE];
		parents[1] = pop->individuals[rand() % POPULATION_SIZE];

		crossover(children, parents, actorsNb);

		nextPop->individuals[i] = children[0];
		nextPop->individuals[i + 1] = children[1];
	}

	// Mutation
	for (; i < POPULATION_SIZE; i++) {
		individual *mutated = malloc(sizeof(individual));
		mutation(mutated, pop->individuals[rand() % POPULATION_SIZE], actorsNb,
				availCoresNb);
	}

	// TODO: Remove old population and unused individuals from memory

	return nextPop;
}

void crossover(individual *children[2], individual *parents[2], int actorsNb) {
	int cut = rand() % (actorsNb - 1) + 1;

	int i;
	for (i = 0; i < actorsNb; i++) {
		gene *childGene0 = (gene*) malloc(sizeof(gene));
		gene *childGene1 = (gene*) malloc(sizeof(gene));
		if (i < cut) {
			childGene0 = parents[0]->genes[i];
			childGene1 = parents[1]->genes[i];
		} else {
			childGene0 = parents[1]->genes[i];
			childGene1 = parents[0]->genes[i];
		}
		children[0]->genes[i] = childGene0;
		children[1]->genes[i] = childGene1;
	}
	children[0]->fps = -1;
	children[1]->fps = -1;
}

void mutation(individual *mutated, individual *original, int actorsNb,
		int availCoresNb) {
	int mutatedIndex = rand() % actorsNb;

	int i;
	for (i = 0; i < actorsNb; i++) {
		gene mutatedGene = *original->genes[i];
		if (i == mutatedIndex) {
			mutatedGene.mappedCore = rand() % availCoresNb;
		}
		mutated->genes[i] = &mutatedGene;
	}
	mutated->fps = -1;
}

int partitionner(population *pop, int p, int r) {
	int pivot = pop->individuals[p]->fps, i = p - 1, j = r + 1;
	individual *temp;
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

void quickSort(population *pop, int p, int r) {
	int q;
	if (p < r) {
		q = partitionner(pop, p, r);
		quickSort(pop, p, q);
		quickSort(pop, q + 1, r);
	}
}
