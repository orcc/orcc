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
 *   * Neither the name of INSA Rennes nor the names of its
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

#include <getopt.h>
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "orcc.h"
#include "mapping.h"
#include "dataflow.h"
#include "serialize.h"
#include "options.h"
#include "trace.h"

void print_usage_orccmap() {
    /* !TODO: Find a kind way to format this text */
    char *INDENT = "    ";

    printf("\nUsage: orccmap [options] -n nbproc -i filename");
    printf("\n");
    printf("\nDescription:");
    printf("\nGenerates a xml file containing a partition of a given Orcc Network in a given number of partitions.");
    printf("\nVarious mapping strategies can be used, all based on actors and connections weighs.");

    printf("\n");
    printf("\nRequired parameters:");
    printf("\n%s-n, --nb_processors <nbproc>", INDENT);
    printf("\n%s%sThe number of processors (partitions).", INDENT, INDENT);
    printf("\n");
    printf("\n%s-i, --input_file <filename>", INDENT);
    printf("\n%s%sThe name of the input file containing a network description.", INDENT, INDENT);

    printf("\n");
    printf("\nOptional parameters:");
    printf("\n%s-o, --output_file <filename>", INDENT);
    printf("\n%s%sThe name of the output file.", INDENT, INDENT);

    printf("\n");
    printf("\n%s-m, --mapping_strategy <strategy>", INDENT);
    printf("\n%s%sThe strategy to apply to do the mapping.", INDENT, INDENT);
    printf("\n%s%sThe possible values are: {Default : ROUND_ROBIN}", INDENT, INDENT);
    printf("\n%s%s%sMR\t: METIS Recursive graph partition mapping", INDENT, INDENT, INDENT);
    printf("\n%s%s%sMKCV\t: METIS KWay graph partition mapping (Optimize Communication volume)", INDENT, INDENT, INDENT);
    printf("\n%s%s%sMKEC\t: METIS KWay graph partition mapping (Optimize Edge-cut)", INDENT, INDENT, INDENT);
    printf("\n%s%s%sRR\t: A simple Round-Robin mapping", INDENT, INDENT, INDENT);
    printf("\n%s%s%sQM\t: Quick Mapping (Not yet available)", INDENT, INDENT, INDENT);
    printf("\n%s%s%sWLB\t: Weighted Load Balancing", INDENT, INDENT, INDENT);
    printf("\n%s%s%sCOW\t: Communication Optimized Weighted Load Balancing (Not yet available)", INDENT, INDENT, INDENT);
    printf("\n%s%s%sKLR\t: Kernighan Lin Refinement Weighted Load Balancing", INDENT, INDENT, INDENT);

    printf("\n");
    printf("\n%s-v, --verbose [level]", INDENT);
    printf("\n%s%sPrint informations.", INDENT, INDENT);
    printf("\n%s%sThe possible values are: {Default : 1}", INDENT, INDENT);
    printf("\n%s%s%s1 : summary and results", INDENT, INDENT, INDENT);
    printf("\n%s%s%s2 : debug informations", INDENT, INDENT, INDENT);

    printf("\n");
    printf("\n%s-h, --help", INDENT);
    printf("\n%s%sPrints this message.", INDENT, INDENT);
    printf("\n");
}

void start_orcc_mapping(options_t *opt) {
    int ret = ORCC_OK;
    mapping_t *mapping;
    network_t *network;
    assert(opt != NULL);

    mapping = allocate_mapping(opt->nb_processors);

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Starting Orcc-Map");
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Nb of processors\t: %d", opt->nb_processors);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Input file\t\t: %s", opt->input_file);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Output file\t: %s", opt->output_file);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Mapping strategy\t: %s", ORCC_STRATEGY_TXT[opt->strategy]);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Verbose level\t: %d", verbose_level);

    network = load_network(opt->input_file);

    ret = do_mapping(network, opt, mapping);

    ret = save_mapping(opt->output_file, mapping);

    delete_mapping(mapping);
    free(network);
}

int main (int argc, char **argv) {
    int nFlag, iFlag = 0;
    int c;

    options_t *opt = set_default_options();

    static struct option long_options[] =
    {
        {"nb_processors", required_argument, 0, 'n'},
        {"input_file", required_argument, 0, 'i'},
        {"output_file", required_argument, 0, 'o'},
        {"mapping_strategy", required_argument, 0, 'm'},
        {"verbose", optional_argument, 0, 'v'},
        {"help", no_argument, 0, 'h'},
        {0, 0, 0, 0}
    };

    /* getopt_long stores the option index here. */
    int option_index = 0;

    while ((c = getopt_long (argc, argv, "n:i:o:m:v::h", long_options, &option_index)) != -1)
        switch (c) {
        case 'n':
            nFlag = 1;
            set_nb_processors(optarg, opt);
            break;
        case 'i':
            iFlag = 1;
            opt->input_file = optarg;
            set_default_output_filename(optarg, opt);
            break;
        case 'o':
            opt->output_file = optarg;
            break;
        case 'm':
            set_mapping_strategy(optarg, opt);
            break;
        case 'v':
            set_verbose_level(optarg, opt);
            break;
        case 'h':
            print_usage_orccmap();
            exit (ORCC_OK);
            break;
        case '?':
            break;
        default:
            abort();
        }

    if (optind < argc) {
        print_orcc_error(ORCC_ERR_BAD_ARGS);
        while (optind < argc)
            fprintf(stderr," [%s]", argv[optind++]);
        printf("\n");
        print_usage_orccmap();
        exit(ORCC_ERR_BAD_ARGS);
    }
    if (!nFlag || !iFlag) {
        print_orcc_error(ORCC_ERR_MANDATORY_ARGS);
        printf("\n");
        print_usage_orccmap();
        exit(ORCC_ERR_MANDATORY_ARGS);
    }

    start_orcc_mapping(opt);

    exit (ORCC_OK);
}
