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

#ifndef _ORCC_OPTIONS_H_
#define _ORCC_OPTIONS_H_

#include "orcc.h"

/*
 * Options of the application runtime
 */
struct options_s
{
    /* Video specific options */
    char *input_file;
    char *input_directory;               // Directory for input files.

    /* Video specific options */
    char display_flags;                  // Display flags
    int nbLoops;                         // (Deprecated) Number of times the input file is read
    int nbFrames;                        // Number of frames to display before closing application
    char *yuv_file;                      // Reference YUV file

    /* Runtime options */
    schedstrategy_et sched_strategy;     // Strategy for the actor scheduling
    char *mapping_input_file;            // Predefined mapping configuration
    char *mapping_output_file;           //
    int nb_processors;
    boolean enable_dynamic_mapping;
    mappingstrategy_et mapping_strategy; // Strategy for the actor mapping
    int nbProfiledFrames;                // Number of frames to display before remapping application
    int mapping_repetition;              // Repetition of the actor remapping

    char *profiling_file; // profiling file
    char *write_file; // write file

    /* Debugging options */
    boolean print_firings;
};

/**
 * Creates and init options structure.
 */
options_t *set_default_options();

/**
 * Releases memory of the given options structure.
 */
void delete_options(options_t *opt);

void set_nb_processors(char *arg_value, options_t *opt);
void set_mapping_strategy(char *arg_value, options_t *opt);
void set_scheduling_strategy(char *arg_value, options_t *opt);
void set_verbose_level(char *arg_value, options_t *opt);
void set_default_output_filename(char *arg_value, options_t *opt);


#endif  /* _ORCC_OPTIONS_H_ */
