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
#include "trace.h"
#include "util.h"


/**
 * Creates and init options structure.
 */
options_t *set_default_options() {
    options_t *opt = (options_t*) malloc(sizeof(options_t));
    opt->sched_strategy = ORCC_SS_ROUND_ROBIN;
    opt->mapping_strategy = ORCC_MS_ROUND_ROBIN;
    opt->mapping_repetition = REMAP_ONCE;
    opt->nb_processors = 1;
    opt->input_file = NULL;
    opt->mapping_output_file = NULL;
    opt->mapping_input_file = NULL;
    opt->enable_dynamic_mapping = FALSE;
    opt->nbLoops = DEFAULT_INFINITE; // -1: infinite loop.
    opt->nbFrames = DEFAULT_INFINITE;
    opt->nbProfiledFrames = 10;
    opt->input_directory = NULL;
    opt->display_flags = DISPLAY_ENABLE;
    opt->yuv_file = NULL;
    opt->profiling_file = NULL;
    opt->print_firings = FALSE;

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

    if (strcmp(arg_value, "RR") == 0) {
        opt->mapping_strategy = ORCC_MS_ROUND_ROBIN;
#ifdef METIS_ENABLE
    } else if (strcmp(arg_value, "MR") == 0) {
        opt->mapping_strategy = ORCC_MS_METIS_REC;
    } else if (strcmp(arg_value, "MKCV") == 0) {
        opt->mapping_strategy = ORCC_MS_METIS_KWAY_CV;
    } else if (strcmp(arg_value, "MKEC") == 0) {
        opt->mapping_strategy = ORCC_MS_METIS_KWAY_EC;
#endif /* METIS_ENABLE */
    } else if (strcmp(arg_value, "QM") == 0) {
        opt->mapping_strategy = ORCC_MS_QM;
    } else if (strcmp(arg_value, "WLB") == 0) {
        opt->mapping_strategy = ORCC_MS_WLB;
    } else if (strcmp(arg_value, "COW") == 0) {
        opt->mapping_strategy = ORCC_MS_COWLB;
    } else if (strcmp(arg_value, "KLR") == 0) {
        opt->mapping_strategy = ORCC_MS_KRWLB;
    } else {
        print_orcc_error(ORCC_ERR_BAD_ARGS_MS);
        printf("\n");
        exit(ORCC_ERR_BAD_ARGS_MS);
    }
}

void set_scheduling_strategy(char *arg_value, options_t *opt) {
    assert(arg_value != NULL);
    assert(opt != NULL);

    if (strcmp(arg_value, "RR") == 0) {
        opt->sched_strategy = ORCC_SS_ROUND_ROBIN;
    } else if (strcmp(arg_value, "DD") == 0) {
        opt->sched_strategy = ORCC_SS_DD_DRIVEN;
    } else {
        print_orcc_error(ORCC_ERR_BAD_ARGS_MS);
        printf("\n");
        exit(ORCC_ERR_BAD_ARGS_MS);
    }
}

void set_verbose_level(char *arg_value, options_t *opt) {
    int trace_level = 0;
    assert(opt != NULL);

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
    char *pDot, *output;
    assert(arg_value != NULL);
    assert(opt != NULL);

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

    opt->mapping_output_file = output;
}

