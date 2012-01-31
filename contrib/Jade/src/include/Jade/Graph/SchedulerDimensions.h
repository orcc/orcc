/*********************************************************
Copyright or � or Copr. IETR/INSA: Maxime Pelcat

Contact mpelcat for more information:
mpelcat@insa-rennes.fr

This software is a computer program whose purpose is to execute
parallel applications.

 *********************************************************/
 
#ifndef SCHEDULER_DIMENSIONS
#define SCHEDULER_DIMENSIONS

#ifdef C64XPLUS
#include "..\..\CCS\c64Specific.h"
#else
#include "X86Config.h"
#endif

/**
 * Defines used to statically allocate the scheduler objects
 * 
 * @author mpelcat
 */

// Architecture
#define MAX_SLAVE_TYPES 2 // The maximum number of slave types
#define MAX_SLAVES 8// The maximum number of slaves
#define MAX_SLAVE_NAME_SIZE 10// The maximum number of slaves

// Coomon to all types of graphs
#define MAX_VERTEX_NAME_SIZE 256

// CSDAG
#define MAX_TIMING_EXPRESSION 32 // The maximum size in char of an expression defining a timing
#define MAX_CSDAG_VERTICES 10
#define MAX_CSDAG_EDGES 10
#define MAX_CSDAG_INPUT_EDGES 2 // Maximum of input edges for a given DAG vertex

#define MAX_CSDAG_VERTEX_REPETITION 100 // The maximum number of repetitions for one vertex
#define MAX_CSDAG_PATTERN_TABLE_SIZE 2100 // Maximum size of the whole table containing the patterns of one CSDAG graph
#define MAX_CSDAG_PATTERN_SIZE 100 // Maximum size of one integer pattern (in number of integers)

// Homogeneous DAG
#define MAX_HDAG_VERTICES 1024
#define MAX_HDAG_EDGES 1024
#define MAX_HDAG_INPUT_EDGES 200
#define MAX_HDAG_OUTPUT_EDGES 200

// DAG
#define MAX_DAG_VERTEX_REPETITION 100 // The maximum number of repetitions for one vertex

// XParser
#define VARIABLE_NAME_LEN_MAX 30
#define MAX_VARIABLE_NUMBER 300
#define EXPR_LEN_MAX 128
#define REVERSE_POLISH_STACK_MAX_ELEMENTS 20 // Max number of elements in an expression reverse polish stack

#define MAX_PARAM 4 // Maximum number of parameters for an actor

#endif
