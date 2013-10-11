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

#include "orcc-graph.h"
#include "metis.h"

boolean isDirected(adjacency_list al) {
    return al.is_directed;
}

int addVertex(adjacency_list al) {

}

int addEdge(adjacency_list al) {

}

int runPartitionRecWithMETIS(adjacency_list al, int iProcessorNumber) {
    int ret;
    idx_t *objval;
    idx_t *part;

    ret = METIS_PartGraphRecursive(NULL, /* idx_t *nvtxs */
                                   NULL, /*idx_t *ncon*/
                                   al.xadj, /*idx_t *xadj*/
                                   al.adjncy, /*idx_t *adjncy*/
                                   al.vwgt, /*idx_t *vwgt*/
                                   NULL, /*idx_t *vsize*/
                                   al.adjwgt, /*idx_t *adjwgt*/
                                   &iProcessorNumber, /*idx_t *nparts*/
                                   NULL, /*real t *tpwgts*/
                                   NULL, /*real t ubvec*/
                                   NULL, /*idx_t *options*/
                                   objval, /*idx_t *objval*/
                                   part); /*idx_t *part*/

    return ret;
}

int runPartitionKwayWithMETIS(adjacency_list al, int iProcessorNumber) {
    int ret;
    idx_t *objval;
    idx_t *part;

    ret = METIS_PartGraphKway(NULL, /* idx_t *nvtxs */
                              NULL, /*idx_t *ncon*/
                              al.xadj, /*idx_t *xadj*/
                              al.adjncy, /*idx_t *adjncy*/
                              al.vwgt, /*idx_t *vwgt*/
                              NULL, /*idx_t *vsize*/
                              al.adjwgt, /*idx_t *adjwgt*/
                              &iProcessorNumber, /*idx_t *nparts*/
                              NULL, /*real t *tpwgts*/
                              NULL, /*real t ubvec*/
                              NULL, /*idx_t *options*/
                              objval, /*idx_t *objval*/
                              part); /*idx_t *part*/

    return ret;
}

int solveLoadBalancing(adjacency_list al, int iProcessorNumber, lbstrategy_et lbs) {
    int ret = 0;

    switch (lbs) {
    case ORCC_LB_METIS_REC:
        ret = runPartitionRecWithMETIS(al, iProcessorNumber);
        break;
    case ORCC_LB_METIS_KWAY:
        ret = runPartitionKwayWithMETIS(al, iProcessorNumber);
        break;
    case ORCC_LB_OTHER:
        break;
    default:
        break;
    }

    return ret;
}
