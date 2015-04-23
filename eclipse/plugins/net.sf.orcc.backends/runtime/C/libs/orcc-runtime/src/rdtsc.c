#include <stdlib.h>
#include <inttypes.h>
#include <stdint.h>
#include "rdtsc.h"

inline void saveNewFiringWeight(rdtsc_data_t *x, uint64_t weight) {
	rdtsc_node_t *y = (rdtsc_node_t *) malloc(sizeof(rdtsc_node_t *));
	y->_weight = weight;
	y->_next = NULL;

	if(x->_head == NULL)
		x->_head = x->_currNode = y;
	else
		x->_currNode = x->_currNode->_next = y;

	x->_numFirings++;
}

inline void calcWeightStats(rdtsc_data_t *x) {
	rdtsc_node_t *y = x->_head;
	uint64_t counter = 0;
	uint64_t sumWeights = 0;

	while(y != NULL) {
		// Minimum
		if(y->_weight < x->_minWeight)
			x->_minWeight = y->_weight;

		// Maximum
		if(y->_weight > x->_maxWeight)
			x->_maxWeight = y->_weight;

		// Sum up for average.
		sumWeights += y->_weight;

		y = y->_next;
		counter++;
	}

	// Average
	x->_avgWeight = ((counter > 0) ? sumWeights / (long double) counter : 0.0);
}

inline void printFiringcWeights(rdtsc_data_t *x, FILE *fp) {
	rdtsc_node_t *y = x->_head;
	uint64_t counter = 0;

	while(y != NULL) {
		fprintf(fp, "%"PRIu64"\t%"PRIu64"\n", counter, y->_weight);
		y = y->_next;
		counter++;
	}
}

/*--------------------------------------------------------------------------------------------------*/

inline void rdtsc_func(unsigned int *cycles_high, unsigned int *cycles_low) {
	asm volatile (
	 "CPUID\n\t"
	 "RDTSC\n\t"
	 "mov %%edx, %0\n\t"
	 "mov %%eax, %1\n\t"
	:
	 "=r" (*cycles_high), "=r" (*cycles_low)
	::
	 "%rax", "%rbx", "%rcx", "%rdx");
}

inline void rdtscp_func(unsigned int *cycles_high, unsigned int *cycles_low) {
	#if IF_RDTSCP
		asm volatile(
		 "RDTSCP\n\t"
		 "mov %%edx, %0\n\t"
		 "mov %%eax, %1\n\t"
		 "CPUID\n\t"
		:
		 "=r" (*cycles_high), "=r" (*cycles_low)
		::
		 "%rax", "%rbx", "%rcx", "%rdx");
	#else
		rdtsc_func(cycles_high, cycles_low);
		/*
		asm volatile(
		 "mov %%cr0, %%rax\n\t"
		 "mov %%rax, %%cr0\n\t"
		 "RDTSC\n\t"
		 "mov %%edx, %0\n\t"
		 "mov %%eax, %1\n\t"
		:
		 "=r" (*cycles_high), "=r" (*cycles_low)
		::
		 "%rax", "%rdx");*/
	#endif
}

inline void rdtsc_warmup(unsigned int *cycles_high, unsigned int *cycles_low, unsigned int *cycles_high1, unsigned int *cycles_low1) {
	rdtsc_func(cycles_high, cycles_low);
	rdtscp_func(cycles_high1, cycles_low1);
	rdtsc_func(cycles_high, cycles_low);
	rdtscp_func(cycles_high1, cycles_low1);
}

inline void rdtsc_tick(unsigned int *cycles_high, unsigned int *cycles_low) {
	rdtsc_func(cycles_high, cycles_low);
}

inline void rdtsc_tock(unsigned int *cycles_high, unsigned int *cycles_low) {
	rdtscp_func(cycles_high, cycles_low);
}

inline uint64_t rdtsc_getTicksCount(unsigned int cycles_high, unsigned int cycles_low, unsigned int cycles_high1, unsigned int cycles_low1) {

	uint64_t ticksCount = (((uint64_t) cycles_high1 << 32) | cycles_low1) - (((uint64_t) cycles_high << 32) | cycles_low);

	if(ticksCount < 0)
	{
		printf("negative ticksCount\n");
		return 0;
	}
	else
		return ticksCount;
}
