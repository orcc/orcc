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

#include "serialize.h"
#include "roxml.h"
#include "mapping.h"
#include "dataflow.h"

/**
 * Generate some mapping structure from an XCF file.
 */
mappings_set_t* compute_mappings_from_file(char *xcf_file, actor_t **actors, int actors_size) {
    int i, j, k, size;
    char *nb, *name;
    node_t *configuration, *partitioning, *partition, *instance, *attribute;
    mappings_set_t *mappings_set = (mappings_set_t *) malloc(sizeof(mappings_set_t));

    configuration = roxml_load_doc(xcf_file);
    if (configuration == NULL) {
        printf("I/O error when reading mapping file.\n");
        exit(1);
    }

    mappings_set->size = roxml_get_chld_nb(configuration);
    mappings_set->mappings = (mapping_t **) malloc(mappings_set->size * sizeof(mapping_t *));

    for (i = 0; i < mappings_set->size; i++) {
        partitioning = roxml_get_chld(configuration, NULL, i);
        name = roxml_get_name(partitioning, NULL, 0);

        mappings_set->mappings[i] = allocate_mapping(
                roxml_get_chld_nb(partitioning));

        for (j = 0; j < mappings_set->mappings[i]->number_of_threads; j++) {
            partition = roxml_get_chld(partitioning, NULL, j);
            name = roxml_get_name(partition, NULL, 0);
            mappings_set->mappings[i]->partitions_size[j] = roxml_get_chld_nb(
                    partition);

            attribute = roxml_get_attr(partition, "id", 0);
            nb = roxml_get_content(attribute, NULL, 0, &size);
            mappings_set->mappings[i]->threads_affinities[j] = atoi(nb);

            mappings_set->mappings[i]->partitions_of_actors[j]
                    = (actor_t **) malloc(
                            mappings_set->mappings[i]->partitions_size[j]
                                    * sizeof(actor_t *));

            for (k = 0; k < mappings_set->mappings[i]->partitions_size[j]; k++) {
                instance = roxml_get_chld(partition, NULL, k);
                name = roxml_get_name(instance, NULL, 0);
                attribute = roxml_get_attr(instance, "id", 0);
                name = roxml_get_content(attribute, NULL, 0, &size);
                mappings_set->mappings[i]->partitions_of_actors[j][k]
                        = find_actor(name, actors, actors_size);
            }
        }
    }
    roxml_close(configuration);

    return mappings_set;
}

/**
 * Save network's workloads from instrumentation to a file
 * that could be used for mapping.
 */
void save_instrumentation(char* fileName, network_t network) {
    int i = 0;
    double total_workload = 0;
    node_t* rootNode;
    node_t* xdfNode;

    rootNode = roxml_add_node(NULL, 0, ROXML_PI_NODE, "xml", "version=\"1.0\" encoding=\"UTF-8\"");
    if (rootNode == NULL) {
        printf("ORCC_ERR_ROXML_NODE_ROOT");
    }

    xdfNode = roxml_add_node(rootNode, 0, ROXML_ELM_NODE, "XDF", NULL);
    if (xdfNode == NULL) {
        printf("ORCC_ERR_ROXML_NODE_CONF");
    }
    /*!TODO : get Network's name properly */
    roxml_add_node(xdfNode, 0, ROXML_ATTR_NODE, "name", network.name);

    for (i=0; i < network.nb_actors; i++) {
        total_workload += network.actors[i]->workload;
    }
    for (i=0; i < network.nb_actors; i++) {
        node_t * instanceNode;
        char * workload;

        instanceNode = roxml_add_node(xdfNode, 0, ROXML_ELM_NODE, "Instance", NULL);
        roxml_add_node(instanceNode, 0, ROXML_ATTR_NODE, "id", network.actors[i]->name);
        workload = (char*) malloc(sizeof(workload));
        sprintf(workload, "%.2lf", 1+network.actors[i]->workload*100/total_workload);
        roxml_add_node(instanceNode, 0, ROXML_ATTR_NODE, "workload", workload);
    }

    total_workload = 0;
    for (i=0; i < network.nb_connections; i++) {
        total_workload += network.connections[i]->workload;
    }
    for (i=0; i < network.nb_connections; i++) {
        char* workload;

        node_t* connectionNode = roxml_add_node(xdfNode, 0, ROXML_ELM_NODE, "Connection", NULL);
        roxml_add_node(connectionNode, 0, ROXML_ATTR_NODE, "src", network.connections[i]->src->name);
        roxml_add_node(connectionNode, 0, ROXML_ATTR_NODE, "dst", network.connections[i]->dst->name);
        workload = (char*) malloc(sizeof(workload));
        sprintf(workload, "%d", 1+(int)(network.connections[i]->workload*100000/total_workload));
        roxml_add_node(connectionNode, 0, ROXML_ATTR_NODE, "workload", workload);
    }

    roxml_commit_changes(rootNode, fileName, NULL, 1);
    roxml_close(rootNode);
}
