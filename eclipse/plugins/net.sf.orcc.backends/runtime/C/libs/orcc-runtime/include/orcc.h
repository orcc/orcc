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

#ifndef _ORCC_H_
#define _ORCC_H_

#ifdef METIS_ENABLE
#include "metis.h"
#else

#ifdef _MSC_VER
#include <limits.h>
typedef __int32 int32_t;
#else
#include <inttypes.h>
#endif

typedef int32_t idx_t;

#endif

// from APR
/* Ignore Microsoft's interpretation of secure development
* and the POSIX string handling API
*/
#if defined(_MSC_VER) && _MSC_VER >= 1400
#ifndef _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_DEPRECATE
#endif
#pragma warning(disable: 4996)
#endif

typedef struct options_s options_t;
typedef struct global_scheduler_s global_scheduler_t;
typedef struct local_scheduler_s local_scheduler_t;
typedef struct network_s network_t;
typedef struct action_s action_t;
typedef struct actor_s actor_t;
typedef struct connection_s connection_t;
typedef struct mapping_s mapping_t;
typedef struct schedinfo_s schedinfo_t;
typedef struct waiting_s waiting_t;
typedef struct agent_s agent_t;
typedef struct adjacency_list_s adjacency_list;

typedef int boolean;
#define TRUE  1
#define FALSE 0

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

/* Scheduling strategy codes */
typedef enum {
    ORCC_SS_ROUND_ROBIN,
    ORCC_SS_DD_DRIVEN, /* data-driven & demand-driven */
    ORCC_SS_SIZE /* only used for string tab declaration */
} schedstrategy_et;

/* Mapping strategy codes */
typedef enum {
#ifdef METIS_ENABLE
    ORCC_MS_METIS_REC,
    ORCC_MS_METIS_KWAY_CV,
    ORCC_MS_METIS_KWAY_EC,
#endif /* METIS_ENABLE */
    ORCC_MS_ROUND_ROBIN,
    ORCC_MS_QM,
    ORCC_MS_WLB,
    ORCC_MS_COWLB,
    ORCC_MS_KRWLB,
    ORCC_MS_SIZE /* only used for string tab declaration */
} mappingstrategy_et;

#endif  /* _ORCC_H_ */
