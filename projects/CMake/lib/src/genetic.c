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

#include "orcc.h"
#include "orcc_genetic.h"

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

void initializePopulation(population *pop, struct actor_s *actors[],
		int actorsNb, int availCoresNb) {
	int i, j;
	for (i = 0; i < POPULATION_SIZE; i++) {
		individual ind;
		ind.fps = -1;
		for (j = 0; j < actorsNb; j++) {
			gene g =
					{ .actor = actors[j], .mappedCore = rand() % availCoresNb };
			ind.genes[j] = &g;
		}
		pop->individuals[i] = &ind;
	}
	pop->generationNb = 0;
}

population* computeNextPopulation(population *pop, int actorsNb,
		int availCoresNb) {
	// sort population by descending fps value
	quickSort(pop, 0, POPULATION_SIZE);

	// selection
	// crossover
	// mutation

	return pop;
}

void crossover(individual *children[2], individual *parents[2], int actorsNb) {
	int cut = rand() % (actorsNb - 1) + 1;

	int i;
	for (i = 0; i < actorsNb; i++) {
		gene childGene0, childGene1;
		if (i < cut) {
			childGene0 = *parents[0]->genes[i];
			childGene1 = *parents[1]->genes[i];
		} else {
			childGene0 = *parents[1]->genes[i];
			childGene1 = *parents[0]->genes[i];
		}
		children[0]->genes[i] = &childGene0;
		children[1]->genes[i] = &childGene1;
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


