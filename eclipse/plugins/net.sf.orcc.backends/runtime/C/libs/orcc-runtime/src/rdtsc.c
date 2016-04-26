#include <stdlib.h>
#include <inttypes.h>
#include <stdint.h>
#include <math.h>
#include "rdtsc.h"

inline void saveNewFiringWeight(rdtsc_data_t *llist, uint64_t weight) {
	rdtsc_node_t *y = (rdtsc_node_t *) malloc(sizeof(rdtsc_node_t *));
	y->_weight = weight;
	y->_next = NULL;

	if(llist->_head == NULL)
		llist->_head = llist->_lastNode = y;
	else
		llist->_lastNode = llist->_lastNode->_next = y;

	llist->_numFirings++;
}

inline static uint64_t sqr(uint64_t x) {
	return x*x;
}

inline void calcVariance(rdtsc_data_t *llist) {
	rdtsc_node_t *y = llist->_head;
	long double tmp = 0.0, sumDiffMean = 0.0;

	if(llist->_numFirings > 2)
	{
		while(y != NULL) {
			tmp = y->_weight - llist->_avgWeight;
			sumDiffMean += tmp * tmp;

			y = y->_next;
		}

		// Variance
		llist->_variance = sumDiffMean / (long double) (llist->_numFirings-1);
	}
	else
		llist->_variance = 0.0;
}

inline void calcMean(rdtsc_data_t *llist) {
	rdtsc_node_t *y = llist->_head;
	long double sumWeights = 0.0;

	if(llist->_numFirings > 0)
	{
		while(y != NULL) {
			sumWeights += y->_weight;

			y = y->_next;
		}

		// Mean
		llist->_avgWeight = sumWeights / (long double) llist->_numFirings;
	}
	else
		llist->_avgWeight = 0.0;
}

inline void calcWeightSimple(rdtsc_data_t *llist, long double threshold) {
	rdtsc_node_t *y = llist->_head;
	uint64_t sumWeights = 0;
	long double variance = 0.0;

	while(y != NULL) {
		if(y->_weight > threshold) {
			llist->_numFirings--;
		}
		else {
			// Minimum
			if(y->_weight < llist->_minWeight)
				llist->_minWeight = (long double) y->_weight;

			// Maximum
			if(y->_weight > llist->_maxWeight)
				llist->_maxWeight = (long double) y->_weight;

			// Sum up for average.
			sumWeights += y->_weight;
		}

		y = y->_next;
	}

	// Mean
	llist->_avgWeight = ((llist->_numFirings > 0) ? sumWeights / (long double) llist->_numFirings : 0.0);

	// Vairance
	calcVariance(llist);
}

inline void calcWeightStats(rdtsc_data_t *llist, int useFilter) {
	long double threshold = LDBL_MAX;

	if(useFilter && llist->_numFirings > 2)
	{
		calcMean(llist);
		calcVariance(llist);
		threshold = llist->_avgWeight + 2 * sqrt(llist->_variance);
	}

	calcWeightSimple(llist, threshold);
}

inline void printFiringcWeights(char *actionName, rdtsc_data_t *llist, FILE *fp) {
	rdtsc_node_t *y = llist->_head;
	uint64_t counter = 0;

	fprintf(fp, "\n%s:\n", actionName);
	while(y != NULL) {
		fprintf(fp, "%"PRIu64"\t%"PRIu64"\n", counter, y->_weight);
		y = y->_next;
		counter++;
	}
}

/*--------------------------------------------------------------------------------------------------*/
inline void rdtsc_func(unsigned int *cycles_high, unsigned int *cycles_low) {
#if defined(__GNUC__) || defined(__GNUG__) // gcc/g++ compilers.
	asm volatile (
	 "CPUID\n\t"
	 "RDTSC\n\t"
	 "mov %%edx, %0\n\t"
	 "mov %%eax, %1\n\t"
	:
	 "=r" (*cycles_high), "=r" (*cycles_low)
	::
	 "%rax", "%rbx", "%rcx", "%rdx");
#else // Non-gcc/g++ compilers.
	*cycles_high = *cycles_low = 0;
#endif
}

inline void rdtscp_func(unsigned int *cycles_high, unsigned int *cycles_low) {
#if defined(__GNUC__) || defined(__GNUG__) // gcc/g++ compilers.
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
#else // Non-gcc/g++ compilers.
	*cycles_high = *cycles_low = 0;
#endif
}

inline void rdtsc_warmup(unsigned int *cycles_high1, unsigned int *cycles_low1, unsigned int *cycles_high2, unsigned int *cycles_low2) {
	rdtsc_func(cycles_high1, cycles_low1);
	rdtscp_func(cycles_high2, cycles_low2);
	rdtsc_func(cycles_high1, cycles_low1);
	rdtscp_func(cycles_high2, cycles_low2);
}

inline void rdtsc_tick(unsigned int *cycles_high1, unsigned int *cycles_low1) {
	rdtsc_func(cycles_high1, cycles_low1);
}

inline void rdtsc_tock(unsigned int *cycles_high2, unsigned int *cycles_low2) {
	rdtscp_func(cycles_high2, cycles_low2);
}

inline uint64_t rdtsc_getTicksCount(unsigned int cycles_high1, unsigned int cycles_low1, unsigned int cycles_high2, unsigned int cycles_low2) {

	uint64_t ticksCount = (((uint64_t) cycles_high2 << 32) | cycles_low2) - (((uint64_t) cycles_high1 << 32) | cycles_low1);

	if(ticksCount < 0)
	{
		printf("negative ticksCount\n");
		return 0;
	}
	else
		return ticksCount;
}
