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

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "orcc.h"
#include "mapping.h"
#include "dataflow.h"
#include "serialize.h"
#include "options.h"
#include "trace.h"

extern char *optarg;
extern int getopt(int nargc, char * const *nargv, const char *ostr);

options_t *opt;

static const char *usage =
    "Orcc mapping tool -- See orcc.sf.net for more information\n"

    "\nUsage: orccmap [options] -n nbproc -i filename\n"

    "\nDescription:\n"
    "Compute mappings of dataflow programs on multi-core platforms.\n"
    "Various strategies based on profiling information can be used.\n"

    "\nRequired parameters:\n"
    "-n <nbproc>        Number of processor core.\n"
    "-i <filename>      XML description of the dataflow network.\n"

    "\nOptional parameters:\n"
    "-o <filename>      Name of the output file.\n"
    "-m <strategy>      Specify the mapping strategy.\n"
    "   The possible values are: {Default : ROUND_ROBIN}\n"
#ifdef METIS_ENABLE
    "       MR   : METIS Recursive graph partition mapping\n"
    "       MKCV : METIS KWay graph partition mapping (Optimize Communication volume)\n"
    "       MKEC : METIS KWay graph partition mapping (Optimize Edge-cut)\n"
#endif /* METIS_ENABLE */
    "       RR   : A simple Round-Robin mapping\n"
    "       QM   : Quick Mapping (Not available)\n"
    "       WLB  : Weighted Load Balancing\n"
    "       COW  : Communication Optimized Weighted Load Balancing (Not available)\n"
    "       KLR  : Kernighan Lin Refinement Weighted Load Balancing\n"
    "-v <level>         Verbosity level\n"
    "   The possible values are: {Default : 1}\n"
    "       1 : summary and results\n"
    "       2 : debug informations\n"
    "-h                 Print this message.\n";

void print_usage_orccmap() {
    printf("%s", usage);
    fflush(stdout);
}

void start_orcc_mapping(options_t *opt) {
    int ret = ORCC_OK;
    mapping_t *mapping;
    network_t *network;
    assert(opt != NULL);

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Starting Orcc-Map");
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Nb of processors\t: %d", opt->nb_processors);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Input file\t\t: %s", opt->input_file);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Output file\t: %s", opt->mapping_output_file);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Mapping strategy\t: %s", ORCC_STRATEGY_TXT[opt->mapping_strategy]);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Verbose level\t: %d", verbose_level);

    network = load_network(opt->input_file);
    mapping = allocate_mapping(opt->nb_processors, network->nb_actors);

    ret = do_mapping(network, opt, mapping);
    ret = save_mapping(opt->mapping_output_file, mapping);

    delete_mapping(mapping);
    free(network);
}

int main (int argc, char **argv) {
    int nFlag, iFlag = 0;
    int c;
    const char *ostr = "n:i:o:m:v::h";

    opt = set_default_options();

    while ((c = getopt(argc, argv, ostr)) != -1) {
        switch (c) {
        case '?': // BADCH
            print_orcc_error(ORCC_ERR_BAD_ARGS);
            print_usage_orccmap();
            exit(ORCC_ERR_BAD_ARGS);
        case ':': // BADARG
            print_orcc_error(ORCC_ERR_BAD_ARGS);
            print_usage_orccmap();
            exit(ORCC_ERR_BAD_ARGS);
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
            opt->mapping_output_file = optarg;
            break;
        case 'm':
            set_mapping_strategy(optarg, opt);
            break;
        case 'v':
            set_verbose_level(optarg, opt);
            break;
        case 'h':
            print_usage_orccmap();
            exit(ORCC_OK);
        default:
            print_orcc_error(ORCC_ERR_BAD_ARGS);
            print_usage_orccmap();
            exit(ORCC_ERR_BAD_ARGS);
        }
    }

    start_orcc_mapping(opt);

    exit (ORCC_OK);
}
