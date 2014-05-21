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
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>

#include "trace.h"

verbose_level_et verbose_level = ORCC_VL_QUIET;

void set_trace_level(verbose_level_et level) {
    verbose_level = level;
}

/********************************************************************************************
 *
 * Orcc-Map utils functions
 *
 ********************************************************************************************/

void print_orcc_error(orccmap_error_et error) {
    if (error != ORCC_OK && error < ORCC_ERR_SIZE) {
        printf("\n");
        fprintf(stderr,"\nOrcc : ERROR : %s", ORCC_ERRORS_TXT[error]);
    }

}

#ifdef METIS_ENABLE
void check_metis_error(rstatus_et error) {
    if (error != METIS_OK) {
        print_orcc_error(ORCC_ERR_METIS);
        fprintf(stderr,"\t Code: %d", error);
        printf("\n");
        exit(error);
    }
}
#endif

void check_orcc_error(orccmap_error_et error) {
    if (error != ORCC_OK) {
        print_orcc_error(error);
        printf("\n");
        exit(error);
    }
}

boolean check_verbosity(verbose_level_et level) {
    return level<=verbose_level;
}

void print_orcc_trace(verbose_level_et level, const char *trace, ...) {
    va_list args;
    assert(trace != NULL);

    va_start (args, trace);

    if (level <= verbose_level) {
        vprintf(trace, args);
        printf("\n");
        fflush(stdout);
    }

    va_end (args);
}
