/*********************************************************
Copyright or © or Copr. IETR/INSA: Maxime Pelcat

Contact mpelcat for more information:
mpelcat@insa-rennes.fr

This software is a computer program whose purpose is to execute
parallel applications.

 *********************************************************/
 
#ifndef SCHEDULING_ERROR
#define SCHEDULING_ERROR

#include <cstdlib>
/**
 * Handling exit code when something is going wrong in the scheduler
 * 
 * @author mpelcat
 */

/**
 Exits returning a code representing the current error

 @param exitCode: code representing the current error
*/
static void exitWithCode(int exitCode){

	// ExitCodes:

	// 1000: Adding a vertex while the graph is already full
	// 1001: Adding an edge while the graph is already full
	// 1002: The edge source repetition number outsizes the maximal pattern size
	// 1003: Two input edges set different repetition numbers to their common sink
	// 1004: The edge sink repetition number outsizes the maximal pattern size
	// 1005: The CSDAG to HDAG transformation led to an unbalanced number of produced and consumed token for an edge
	// 1006: HDAG edge creation led to an unbalanced number of produced and consumed token for an edge
	// 1007: Removing an edge from an empty graph
	// 1008: Adding a slave when the slave table is full
	// 1009: Adding a slave type with a too big index
	// 1010: Adding a production pattern in a full table
	// 1011: Adding a consumption pattern in a full table
	// 1012: Adding a variable in an already full variable table
	// 1013: Too long expression
	// 1014: Early end of expression after delimiter
	// 1015: User entered a not existing operator like "//"
	// 1016: Garbage at the end of the expression 
	// 1018: Unwanted character in expression
	// 1019: Unexpected end of expression
	// 1020: Expression syntax error
	// 1021: Operator is not recognized
	// 1022: Operator is not recognized
	// 1023: Unknown variable
	// 1024: Unknown timing
	// 1025: Trying to allocate a buffer on a full memory
	// 1026: Adding an expression element in a full stack
	// 1027: Searching a non existing variable while creating a stack
	// 1028: Unrecognized token
	// 1029: Adding an expression element in a full queue
	// 1030: Removing an expression element from an empty queue
	// 1031: Popping an expression element from an empty stack
	// 1032: Parser parenthesis mismatch
	// 1033: Parser parenthesis mismatch. Left parenthesis missing
	// 1035: Peeking an expression element from an empty stack
	// 1036: Failure in RPN parsing
	// 1037: Expression syntax error: wrong variable assignment
	// 1038: Problem with variable assignment
	// 1039: Adding a parameter pattern in a full table
	// 1040: Adding a parameter in a full table
	// 1041: Accessing a slave with an incorrect index
	// 1042: Setting an improper data rate
	// 1043: Removing a slave from an empty architecture
	// 1044: Adding a successor vertex in a full table

	exit(exitCode);
}

#endif
