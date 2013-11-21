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

#ifndef _ORCCMAP_UTIL_H_
#define _ORCCMAP_UTIL_H_

#include "metis.h"
#include "mapping.h"

typedef enum { FALSE, TRUE } boolean;

/* Error codes */
/* !TODO : Add more errors */
typedef enum {
    ORCC_OK = 0,
    ORCC_ERR_BAD_ARGS,
    ORCC_ERR_BAD_ARGS_NBPROC,
    ORCC_ERR_BAD_ARGS_MS,
    ORCC_ERR_BAD_ARGS_VERBOSE,
    ORCC_ERR_MANDATORY_ARGS,
    ORCC_ERR_DEF_OUTPUT,
    ORCC_ERR_METIS,
    ORCC_ERR_METIS_FIX_WEIGHTS,
    ORCC_ERR_METIS_FIX_NEEDED,
    ORCC_ERR_SWAP_ACTORS,
    ORCC_ERR_ROXML_OPEN,
    ORCC_ERR_ROXML_NODE_ROOT,
    ORCC_ERR_ROXML_NODE_CONF,
    ORCC_ERR_ROXML_NODE_PART,
    ORCC_ERR_SIZE /* only used for string tab declaration */
} orccmap_error_et;

/* Verbose level */
typedef enum {
    ORCC_VL_QUIET,
    ORCC_VL_VERBOSE_1,
    ORCC_VL_VERBOSE_2
} verbose_level_et;


#define arrayCopy(DST,SRC,LEN) \
            { size_t TMPSZ = sizeof(*(SRC)) * (LEN); \
              if ( ((DST) = malloc(TMPSZ)) != NULL ) \
                memcpy((DST), (SRC), TMPSZ); }

static const char *ORCC_ERRORS_TXT[ORCC_ERR_SIZE] = {
    "",
    "Bad arguments. Please check usage print.",
    "Arg value for -n is not valide.",
    "Arg value for -m is not valide.",
    "Arg value for -v is not valide.",
    "Mandatory argument missing. Please check usage print.",
    "Cannot generate default output file name.",
    "METIS error",
    "The network is not compatible with Metis. Weights must be >= 0",
    "The network is not compatible with Metis.",
    "Actors swap fails.",
    "Cannot open input file.",
    "Cannot create root node.",
    "Cannot create Configuration node.",
    "Cannot create Partition node."
};

static const char *ORCC_STRATEGY_TXT[ORCC_MS_SIZE] = {
    "METIS Recursive",
    "METIS Kway",
    "Round Robin"
};

/**
 * !TODO
 */
void print_orcc_error(orccmap_error_et error);

/**
 * !TODO
 */
void check_metis_error(rstatus_et error);

/**
 * !TODO
 */
void check_orcc_error(orccmap_error_et error);

/**
 * !TODO
 */
boolean print_trace_block(verbose_level_et level);

/**
 * !TODO
 */
void print_orcc_trace(verbose_level_et level, const char *trace, ...);

#endif  /* _ORCCMAP_UTIL_H_ */
