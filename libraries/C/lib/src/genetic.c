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
#define _CRT_SECURE_NO_WARNINGS

#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <string.h>

#include "orcc_types.h"
#include "orcc_genetic.h"
#include "orcc_thread.h"
#include "orcc_scheduler.h"

int clean_cache(int size) {
	int i, res = 0;
	int *table = (int*) malloc(size * sizeof(int));
	memset(table, 0, size);
	for (i = 0; i < size; i++) {
		res += table[i];
	}
	free(table);
	return res;
}

static struct mapping_s* compute_mapping(individual *individual,
		struct genetic_s *genetic_info) {
	int i, j, k;
	struct mapping_s *mapping = (struct mapping_s*) malloc(
			sizeof(struct mapping_s));
	mapping->actors_per_threads = (int*) malloc(
			genetic_info->threads_nb * sizeof(int));
	mapping->actors_mapping = (struct actor_s***) malloc(
			genetic_info->threads_nb * sizeof(struct actor_s **));

	for (i = 0; i < genetic_info->threads_nb; i++) {
		struct actor_s **actors_tmp = (struct actor_s**) malloc(
				genetic_info->actors_nb * sizeof(struct actor_s *));
		k = 0;

		for (j = 0; j < genetic_info->actors_nb; j++) {
			if (individual->genes[j]->mapped_core == i) {
				actors_tmp[k] = individual->genes[j]->actor;
				k++;
			}
		}

		mapping->actors_mapping[i] = (struct actor_s**) malloc(
				k * sizeof(struct actor_s*));
		memcpy(mapping->actors_mapping[i], actors_tmp,
				k * sizeof(struct actor_s*));
		mapping->actors_per_threads[i] = k;
		free(actors_tmp);
	}

	return mapping;
}

static int write_better_mapping(population *pop, struct genetic_s *genetic_info) {
	FILE *mappingFile = fopen("better_mapping.txt", "w");
	if (mappingFile != NULL) {
		int i, j, k;
		fprintf(mappingFile, "// Better mapping\n\n");

		for (i = 0; i < genetic_info->population_size
				* genetic_info->keep_ratio; i++) {
			struct mapping_s* mapping = compute_mapping(pop->individuals[i],
					genetic_info);
			fprintf(mappingFile, "// Mapping n°%i (%f fps)\n\n", i,
					pop->individuals[i]->fps);

			for (j = 0; j < genetic_info->threads_nb; j++) {
				fprintf(mappingFile, "<Partition id=\"%i\">\n", j);

				for (k = 0; k < mapping->actors_per_threads[j]; k++) {
					fprintf(mappingFile, "\t<Instance actor-id=\"%s/0\"/>\n",
							mapping->actors_mapping[j][k]->name);
				}

				fprintf(mappingFile, "</Partition>\n\n");
			}
		}

		fclose(mappingFile);
	} else {
		perror("I/O error during writing of final mapping file");
	}
	return 0;
}

static void print_mapping(individual *ind, struct genetic_s *genetic_info) {
	int i;
	printf("{");
	for (i = 0; i < genetic_info->actors_nb; i++) {
		printf("%i", ind->genes[i]->mapped_core);
		if (i != genetic_info->actors_nb - 1) {
			printf(", ");
		}
	}
	printf("}\n");
}

static void print_actor_list(individual *ind, struct genetic_s *genetic_info) {
	int i;
	printf("Actor list =\n");
	for (i = 0; i < genetic_info->actors_nb; i++) {
		printf("%i -> %s\n", i, ind->genes[i]->actor->name);
	}
	printf("\n\n");

}

static int individual_equal(individual* ind1, individual* ind2,
		struct genetic_s *genetic_info) {
	int i, equal = 1;
	for (i = 0; i < genetic_info->actors_nb && equal; i++) {
		equal = (ind1->genes[i] == ind2->genes[i]);
	}
	return equal;
}

static int is_contained(individual* ind, population* pop, int size,
		struct genetic_s *genetic_info) {
	int i, contains = 0;
	for (i = 0; i < size && !contains; i++) {
		contains = individual_equal(ind, pop->individuals[i], genetic_info);
	}
	return contains;
}

static gene* copy_gene(gene *g) {
	gene *new_g = (gene*) malloc(sizeof(gene));
	new_g->actor = g->actor;
	new_g->mapped_core = g->mapped_core;
	return new_g;
}

static individual* copy_individual(individual *ind,
		struct genetic_s *genetic_info) {
	int i;
	individual *new_ind = (individual*) malloc(sizeof(individual));
	new_ind->genes = (gene**) malloc(genetic_info->actors_nb * sizeof(gene*));
	new_ind->fps = ind->fps;
	new_ind->old_fps = ind->old_fps;
	for (i = 0; i < genetic_info->actors_nb; i++) {
		new_ind->genes[i] = copy_gene(ind->genes[i]);
	}
	return new_ind;
}

static int compare_individual_fps(void const *a, void const *b) {
	individual const **pi1 = (individual const**) a;
	individual const **pi2 = (individual const**) b;
	individual const *i1 = *pi1;
	individual const *i2 = *pi2;

	if (i2->fps < i1->fps) {
		return -1;
	} else if (i2->fps > i1->fps) {
		return 1;
	} else {
		return 0;
	}
}

static individual* selection(population *pop, struct genetic_s *genetic_info) {
	individual* i1 = pop->individuals[rand() % genetic_info->population_size];
	individual* i2 = pop->individuals[rand() % genetic_info->population_size];
	if (i1->fps >= i2->fps) {
		return i1;
	} else {
		return i2;
	}
}

static void crossover(individual **children, individual **parents,
		struct genetic_s *genetic_info) {
	int i, cut = rand() % (genetic_info->actors_nb - 1) + 1;

	children[0] = (individual*) malloc(sizeof(individual));
	children[1] = (individual*) malloc(sizeof(individual));
	children[0]->genes = (gene**) malloc(
			genetic_info->actors_nb * sizeof(gene*));
	children[1]->genes = (gene**) malloc(
			genetic_info->actors_nb * sizeof(gene*));
	children[0]->fps = -1;
	children[0]->old_fps = -1;
	children[1]->fps = -1;
	children[1]->old_fps = -1;

	for (i = 0; i < genetic_info->actors_nb; i++) {
		if (i < cut) {
			children[0]->genes[i] = copy_gene(parents[0]->genes[i]);
			children[1]->genes[i] = copy_gene(parents[1]->genes[i]);
		} else {
			children[0]->genes[i] = copy_gene(parents[1]->genes[i]);
			children[1]->genes[i] = copy_gene(parents[0]->genes[i]);
		}
	}
}

static individual* mutation(individual *original,
		struct genetic_s *genetic_info) {
	individual *mutated = (individual*) malloc(sizeof(individual));
	int i, mutated_index = rand() % genetic_info->actors_nb;

	mutated->genes = (gene**) malloc(genetic_info->actors_nb * sizeof(gene*));
	mutated->fps = -1;
	mutated->old_fps = -1;

	for (i = 0; i < genetic_info->actors_nb; i++) {
		mutated->genes[i] = copy_gene(original->genes[i]);
		if (i == mutated_index) {
			mutated->genes[i]->mapped_core = rand() % genetic_info->threads_nb;
		}
	}

	return mutated;
}

static individual* generate_constant_individual(struct genetic_s *genetic_info,
		int thread) {
	int i;
	individual* ind = (individual*) malloc(sizeof(individual));
	ind->genes = (gene**) malloc(genetic_info->actors_nb * sizeof(gene*));
	ind->fps = -1;
	ind->old_fps = -1;

	// Initialize genes randomly
	for (i = 0; i < genetic_info->actors_nb; i++) {
		ind->genes[i] = (gene*) malloc(sizeof(gene));
		ind->genes[i]->actor = genetic_info->actors[i];
		ind->genes[i]->mapped_core = thread;
	}

	return ind;
}

static individual* generate_random_individual(struct genetic_s *genetic_info) {
	int i;
	individual* ind = (individual*) malloc(sizeof(individual));
	ind->genes = (gene**) malloc(genetic_info->actors_nb * sizeof(gene*));
	ind->fps = -1;
	ind->old_fps = -1;

	// Initialize genes randomly
	for (i = 0; i < genetic_info->actors_nb; i++) {
		ind->genes[i] = (gene*) malloc(sizeof(gene));
		ind->genes[i]->actor = genetic_info->actors[i];
		ind->genes[i]->mapped_core = rand() % genetic_info->threads_nb;
	}

	return ind;
}

static void map_actors_on_threads(individual *individual,
		struct genetic_s *genetic_info) {
	int i;
	struct mapping_s *mapping = compute_mapping(individual, genetic_info);

	for (i = 0; i < genetic_info->threads_nb; i++) {
		sched_reinit(&genetic_info->schedulers[i],
				mapping->actors_per_threads[i], mapping->actors_mapping[i],
				genetic_info->use_ring_topology, genetic_info->threads_nb);
	}
}

static void destroy_population(population *pop, struct genetic_s *genetic_info) {
	int i, j;
	for (i = 0; i < genetic_info->population_size; i++) {
		for (j = 0; j < genetic_info->actors_nb; j++) {
			free(pop->individuals[i]->genes[j]);
		}
		free(pop->individuals[i]);
	}
	free(pop);
}

static population* compute_next_population(population *pop,
		struct genetic_s *genetic_info) {
	int i, free, last;

	// Allocate memory to store the new population
	population *next_pop = (population*) malloc(sizeof(population));
	individual **individuals = (individual**) malloc(
			genetic_info->population_size * sizeof(individual*));

	// Initialize population fields
	next_pop->individuals = individuals;
	next_pop->generation_nb = pop->generation_nb + 1;

	// Sort population by descending fps value
	qsort(pop->individuals, genetic_info->population_size, sizeof(individual*),
			compare_individual_fps);

	// Backup better individuals
	for (i = 0; i < genetic_info->population_size * genetic_info->keep_ratio; i++) {
		next_pop->individuals[i] = copy_individual(pop->individuals[i],
				genetic_info);
		next_pop->individuals[i]->old_fps = next_pop->individuals[i]->fps;
		next_pop->individuals[i]->fps = -1;
	}

	// Crossover
	free = genetic_info->population_size - i;
	last = ((int) (free * genetic_info->crossover_ratio)) + i;
	if ((last - free) % 2 == 1) {
		last = last - 1;
	}
	for (; i < last; i = i + 2) {
		individual *children[2];
		individual *parents[2];

		do {
			parents[0] = selection(pop, genetic_info);
			parents[1] = selection(pop, genetic_info);

			crossover(children, parents, genetic_info);
		} while (is_contained(children[0], next_pop, i, genetic_info)
				|| is_contained(children[1], next_pop, i, genetic_info));

		next_pop->individuals[i] = children[0];
		next_pop->individuals[i + 1] = children[1];
	}

	// Mutation
	for (; i < genetic_info->population_size; i++) {
		do {
			next_pop->individuals[i] = mutation(selection(pop, genetic_info),
					genetic_info);
		} while (is_contained(next_pop->individuals[i], next_pop, i,
				genetic_info));
	}

	destroy_population(pop, genetic_info);

	return next_pop;
}

static population* initialize_population(struct genetic_s *genetic_info) {
	int i;

	// Allocate memory to store the new population
	population *pop = (population*) malloc(sizeof(population));
	individual **individuals = (individual**) malloc(
			genetic_info->population_size * sizeof(individual*));

	// Initialize population fields
	pop->individuals = individuals;
	pop->generation_nb = 0;

	// Initialize random function
	srand((unsigned) time(NULL));

	// Initialize first generation of individuals (constant and random)
	for (i = 0; (i < genetic_info->population_size) && (i
			< genetic_info->threads_nb); i++) {
		pop->individuals[i] = generate_constant_individual(genetic_info, i);
	}
	for (i = genetic_info->threads_nb; i < genetic_info->population_size; i++) {
		do {
			pop->individuals[i] = generate_random_individual(genetic_info);
		} while (is_contained(pop->individuals[i], pop, i, genetic_info));
	}

	return pop;
}

void *monitor(void *data) {
	struct monitor_s *monitoring = (struct monitor_s *) data;
	int i, evalIndNb = 0;
	population *population;

	remove_fps_printing();
	source_active_genetic();
	//Compare_active_genetic();

	// Initialize
	printf("\nGenerate initial population...\n\n");
	population = initialize_population(monitoring->genetic_info);

	print_actor_list(population->individuals[evalIndNb],
			monitoring->genetic_info);
	map_actors_on_threads(population->individuals[evalIndNb],
			monitoring->genetic_info);

	while (1) {
		print_mapping(population->individuals[evalIndNb],
				monitoring->genetic_info);

		// Backup informations to compute partial fps except first time
		if (evalIndNb != 0 || population->generation_nb != 0) {
			backup_partial_start_info();
		}
		// wakeup all threads
		for (i = 0; i < monitoring->genetic_info->threads_nb; i++) {
			semaphore_set(monitoring->genetic_info->schedulers[i].sem_thread);
		}

		// wait threads synchro
		for (i = 0; i < monitoring->genetic_info->threads_nb; i++) {
			semaphore_wait(monitoring->sync->sem_monitor);
		}
		backup_partial_end_info();

		if (is_timeout()) {
			printf("Evaluation of mapping %i = TIMEOUT\n", evalIndNb);
		} else {
			population->individuals[evalIndNb]->fps = compute_partial_fps();
			printf("Evaluation of mapping %i = %f fps", evalIndNb,
					population->individuals[evalIndNb]->fps);
			if (population->individuals[evalIndNb]->old_fps == -1) {
				printf("\n");
			} else {
				printf(" (old = %f fps)\n",
						population->individuals[evalIndNb]->old_fps);
			}
		}

		evalIndNb++;

		if (evalIndNb == monitoring->genetic_info->population_size) {
			if (population->generation_nb
					== monitoring->genetic_info->generation_nb - 1) {
				break;
			} else {
				printf("\nCompute next generation...\n\n");
				population = compute_next_population(population,
						monitoring->genetic_info);
				evalIndNb = 0;
			}
		}
		map_actors_on_threads(population->individuals[evalIndNb],
				monitoring->genetic_info);

		//Compare_close();
		clear_fifos();
		initialize_instances();
	}
	//write_better_mapping(population, monitoring->genetic_info);
	//active_fps_printing();
	exit(0);

	return NULL;
}

void genetic_init(struct genetic_s *genetic_info, int population_size,
		int generation_nb, double keep_ratio, double crossover_ratio,
		struct actor_s **actors, struct scheduler_s *schedulers, int actors_nb,
		int threads_nb, int use_ring_topology) {
	genetic_info->population_size = population_size;
	genetic_info->generation_nb = generation_nb;
	genetic_info->keep_ratio = keep_ratio;
	genetic_info->crossover_ratio = crossover_ratio;
	genetic_info->actors = actors;
	genetic_info->schedulers = schedulers;
	genetic_info->actors_nb = actors_nb;
	genetic_info->threads_nb = threads_nb;
	genetic_info->use_ring_topology = use_ring_topology;
}

void monitor_init(struct monitor_s *monitoring, struct sync_s *sync,
		struct genetic_s *genetic_info) {
	monitoring->sync = sync;
	monitoring->genetic_info = genetic_info;
}
