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

#ifndef _ORCC_TRACE_H_
#define _ORCC_TRACE_H_

#include "orcc.h"

#ifdef METIS_ENABLE
#include "metis.h"
#endif

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
    "Cannot generate deTHREAD_Hfault output file name.",
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
#ifdef METIS_ENABLE
    "METIS Recursive",
    "METIS Kway (Comm Volume)",
    "METIS Kway (Edge-cut)",
#endif /* METIS_ENABLE */
    "Round Robin",
    "Quick Mapping",
    "Weighted Load Balancing",
    "Communication Optimized Load Balancing",
    "Kernighan Lin Refinement Weighted Load Balancing"
};

extern verbose_level_et verbose_level;

void set_trace_level(verbose_level_et level);

/**
 * !TODO
 */
void print_orcc_error(orccmap_error_et error);

#ifdef METIS_ENABLE
/**
 * !TODO
 */
void check_metis_error(rstatus_et error);
#endif

/**
 * !TODO
 */
void check_orcc_error(orccmap_error_et error);

/**
 * !TODO
 */
boolean check_verbosity(verbose_level_et level);

/**
 * !TODO
 */
void print_orcc_trace(verbose_level_et level, const char *trace, ...);

#endif  /* _ORCC_TRACE_H_ */
