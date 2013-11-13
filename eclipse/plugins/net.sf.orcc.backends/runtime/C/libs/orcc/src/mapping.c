/*
 * Copyright (c) 2013, INSA of Rennes
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
 *   * Neither the name of the INSA of Rennes nor the names of its
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

#include "mapping.h"
#include "util.h"


/**
 * Find actor by its name in the given table.
 */
struct actor_s * find_actor(char *name, struct actor_s **actors,
        int actors_size) {
    int i;
    for (i = 0; i < actors_size; i++) {
        if (strcmp(name, actors[i]->name) == 0) {
            return actors[i];
        }
    }
    return NULL;
}

/**
 * Give the id of the mapped core of the given actor in the given mapping structure.
 */
int find_mapped_core(struct mapping_s *mapping, struct actor_s *actor) {
    int i;
    for (i = 0; i < mapping->number_of_threads; i++) {
        if (find_actor(actor->name, mapping->partitions_of_actors[i],
                mapping->partitions_size[i]) != NULL) {
            return i;
        }
    }
    return -1;
}

/**
 * Creates a mapping structure.
 */
struct mapping_s* allocate_mapping(int number_of_threads) {
    struct mapping_s *mapping = (struct mapping_s *) malloc(
            sizeof(struct mapping_s));
    mapping->number_of_threads = number_of_threads;
    mapping->threads_affinities = (int*) malloc(number_of_threads * sizeof(int));
    mapping->partitions_of_actors = (struct actor_s ***) malloc(
            number_of_threads * sizeof(struct actor_s **));
    mapping->partitions_size = (int*) malloc(number_of_threads * sizeof(int));
    return mapping;
}

/**
 * Releases memory of the given mapping structure.
 */
void delete_mapping(struct mapping_s* mapping, int clean_all) {
    if (clean_all) {
        int i;
        for (i = 0; i < mapping->number_of_threads; i++) {
            free(mapping->partitions_of_actors[i]);
        }
    }
    free(mapping->partitions_of_actors);
    free(mapping->partitions_size);
    free(mapping->threads_affinities);
    free(mapping);
}

/**
 * Computes a partitionment of actors on threads from an XML file given in parameter.
 */
struct mapping_s* map_actors(struct actor_s **actors, int actors_size) {
    if (mapping_file == NULL) {
        struct mapping_s *mapping = allocate_mapping(1);
        mapping->threads_affinities[0] = 0;
        mapping->partitions_size[0] = actors_size;
        mapping->partitions_of_actors[0] = actors;
        return mapping;
    } else {
        struct mappings_set_s *mappings_set = compute_mappings_from_file(
                mapping_file, actors, actors_size);
        return mappings_set->mappings[0];
    }
}
