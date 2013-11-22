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

#include <string.h>
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "options.h"

verbose_level_et verbose_level = ORCC_VL_QUIET;

/**
 * Creates and init options structure.
 */
options_t *set_default_options() {
    options_t *opt = (options_t*) malloc(sizeof(options_t));
    opt->strategy = ORCC_MS_ROUND_ROBIN;
    opt->nb_processors = 1;
    opt->input_file = "";
    opt->output_file = "";

    return opt;
}

/**
 * Creates and init options structure.
 */
options_t *set_options(mappingstrategy_et strategy, int nb_processors) {
    options_t *opt = (options_t*) malloc(sizeof(options_t));
    opt->strategy = strategy;
    opt->nb_processors = nb_processors;
    opt->input_file = "";
    opt->output_file = "";

    return opt;
}

/**
 * Releases memory of the given options structure.
 */
void delete_options(options_t *opt) {
    free(opt);
}

void set_nb_processors(char *arg_value, options_t *opt) {
    assert(arg_value != NULL);

    opt->nb_processors = atoi(arg_value);
    if (opt->nb_processors < 1) {
        print_orcc_error(ORCC_ERR_BAD_ARGS_NBPROC);
        printf("\n");
        exit(ORCC_ERR_BAD_ARGS_NBPROC);
    }
}

void set_mapping_strategy(char *arg_value, options_t *opt) {
    assert(arg_value != NULL);
    assert(opt != NULL);

    if (strcmp(arg_value, "METIS_REC") == 0) {
        opt->strategy = ORCC_MS_METIS_REC;
    } else if (strcmp(arg_value, "METIS_KWAY") == 0) {
        opt->strategy = ORCC_MS_METIS_KWAY;
    } else if (strcmp(arg_value, "ROUND_ROBIN") == 0) {
        opt->strategy = ORCC_MS_ROUND_ROBIN;
    } else {
        print_orcc_error(ORCC_ERR_BAD_ARGS_MS);
        printf("\n");
        exit(ORCC_ERR_BAD_ARGS_MS);
    }
}

void set_verbose_level(char *arg_value, options_t *opt) {
    assert(opt != NULL);
    int trace_level = 0;

    if (arg_value == NULL) {
        set_trace_level(ORCC_VL_VERBOSE_1);
    } else {
        trace_level = atoi(arg_value);
        if (trace_level < 1 || trace_level > ORCC_VL_VERBOSE_2-ORCC_VL_QUIET) {
            print_orcc_error(ORCC_ERR_BAD_ARGS_VERBOSE);
            printf("\n");
            exit(ORCC_ERR_BAD_ARGS_VERBOSE);
        }
        set_trace_level(ORCC_VL_QUIET+trace_level);
    }
}

void set_default_output_filename(char *arg_value, options_t *opt) {
    assert(arg_value != NULL);
    assert(opt != NULL);
    char *pDot, *output;

    if (arg_value != NULL) {
        output = (char *)malloc(strlen(arg_value)*sizeof(char));
        strcpy(output, arg_value);
        pDot = strrchr(output, '.');
        if(pDot != NULL)
            *pDot = '\0';
        strcat(output, ".xcf");
    } else {
        print_orcc_error(ORCC_ERR_DEF_OUTPUT);
        printf("\n");
        exit(ORCC_ERR_DEF_OUTPUT);
    }

    opt->output_file = output;
}

void set_trace_level(verbose_level_et level) {
    verbose_level = level;
}
