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
		llist->_head = llist->_currNode = y;
	else
		llist->_currNode = llist->_currNode->_next = y;

	llist->_numFirings++;
}

inline static uint64_t sqr(uint64_t x) {
	return x*x;
}

inline void calVariance(rdtsc_data_t *llist) {
	rdtsc_node_t *y = llist->_head;
	uint64_t counter = 0;
	long double tmp = 0.0, variance = 0.0;

	while(y != NULL) {
		tmp = y->_weight - llist->_avgWeight;
		variance += tmp * tmp;

		y = y->_next;
		counter++;
	}

	// Variance
	llist->_variance = variance / counter;
}

inline void calcWeightLSQR(rdtsc_data_t *llist)
{
	rdtsc_node_t *y = llist->_head;
	uint64_t i = 0;

	long double slope = 0.0, intercept = 0.0, rcc = 0.0, d1 = 0.0, d2 = 0.0;

	long double sumx = 0.0; /* sum of x                      */
	long double sumx2 = 0.0; /* sum of x**2                   */
	long double sumxy = 0.0; /* sum of x * y                  */
	long double sumy = 0.0; /* sum of y                      */
	long double sumy2 = 0.0; /* sum of y**2                   */
	long double denom;

	while(y != NULL) {
		sumx += i;
		sumx2 += sqr(i);
		sumxy += i * y->_weight;
		sumy += y->_weight;
		sumy2 += sqr(y->_weight);

		y = y->_next;
		i++;
	}

	denom = (i * sumx2 - sqr(sumx));
	if (denom == 0.0) {
		// singular matrix. can't solve the problem.
		slope = 0;
		intercept = 0;
		rcc = 0;
		return;
	}

	slope = (i * sumxy - sumx * sumy) / denom;
	intercept = (sumy * sumx2 - sumx * sumxy) / denom;

	rcc = (sumxy - sumx * sumy / i) / /* compute correlation coeff */
	sqrt((sumx2 - sqr(sumx)/i) * (sumy2 - sqr(sumy)/i));

	d1 = intercept;
	d2 = intercept + slope*(i - 1);

	llist->_maxWeight = d1 > d2 ? d1 : d2;
	llist->_minWeight = d1 < d2 ? d1 : d2;
	llist->_avgWeight = intercept + slope*(i-1)/2.0;
	llist->_variance = rcc;
}

inline void calcWeightSimple(rdtsc_data_t *llist) {
	rdtsc_node_t *y = llist->_head;
	uint64_t counter = 0;
	uint64_t sumWeights = 0;
	long double variance = 0.0;

	while(y != NULL) {
		// Minimum
		if(y->_weight < llist->_minWeight)
			llist->_minWeight = (long double) y->_weight;

		// Maximum
		if(y->_weight > llist->_maxWeight)
			llist->_maxWeight = (long double) y->_weight;

		// Sum up for average.
		sumWeights += y->_weight;

		y = y->_next;
		counter++;
	}

	// Average
	llist->_avgWeight = ((counter > 0) ? sumWeights / (long double) counter : 0.0);

	if(counter > 1)
		calVariance(llist);
	else
		llist->_variance = 0.0;
}


inline void calcWeightStats(rdtsc_data_t *llist, int useLSQR) {
	if(useLSQR)
		calcWeightLSQR(llist);
	else
		calcWeightSimple(llist);
}

inline void printFiringcWeights(rdtsc_data_t *llist, FILE *fp) {
	rdtsc_node_t *y = llist->_head;
	uint64_t counter = 0;

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
