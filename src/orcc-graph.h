/*
 * Copyright (c) 2013, INSA Rennes
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
 * about
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

#ifndef _ORCC_GRAPH_H_
#define _ORCC_GRAPH_H_

#include <stdio.h>
#include <stdlib.h>

/* Load Balancing strategy codes */
typedef enum {
  ORCC_LB_METIS_REC,
  ORCC_LB_METIS_KWAY,
  ORCC_LB_OTHER
} lbstrategy_et;

typedef int int32_t;

typedef enum { FALSE, TRUE } boolean;

/*
* Graph data structures
*
*/

/* The adjacency structure of the graph is stored using the compressed storage format (CSR) */
typedef struct adjacency_list
{
	/* is_directed : True if the graph is directed, esle False */
    boolean is_directed;

	/* xadj : For each vertex i, xadj[i] gives the entry index in the array adjncy */
	int32_t *xadj;

	/* adjncy : For each vertex, its adjacency list is stored in consecutive locations in the array adjncy */
	int32_t *adjncy;

	/* vwgt : The weights of the vertices */
	int32_t *vwgt;

	/* adjwgt : The weights of the edges */
	int32_t *adjwgt;
} adjacency_list;

/*
* Function prototypes
*
*/

#ifdef _WINDLL
#define ORCC_API(type) __declspec(dllexport) type __cdecl
#elif defined(__cdecl)
#define ORCC_API(type) type __cdecl
#else
#define ORCC_API(type) type
#endif

/*
 *
*/
int runPartitionRecWithMETIS(adjacency_list al, int iProcessorNumber);

/*
 *
*/
int runPartitionKwayWithMETIS(adjacency_list al, int iProcessorNumber);

/*
 *
*/
ORCC_API(int) solveLoadBalancing(adjacency_list al, int iProcessorNumber, lbstrategy_et lbs);


#endif  /* _ORCC_GRAPH_H_ */
