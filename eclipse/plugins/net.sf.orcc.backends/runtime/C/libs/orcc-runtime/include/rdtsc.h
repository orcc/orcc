#ifndef _ORCC_RDTSC_H_
#define _ORCC_RDTSC_H_

#include <stdio.h>
#include <stdint.h>

#define MAX_VAL_UINT64 UINT64_MAX
#define MIN_VAL_UINT64 0x0

typedef struct rdtsc_node {
	uint64_t _weight;
	struct rdtsc_node *_next;
} rdtsc_node_t;

typedef struct rdtsc_data {
	uint64_t _numFirings;
	uint64_t _minWeight;
	uint64_t _maxWeight;
	long double _avgWeight;
	rdtsc_node_t *_head;
	rdtsc_node_t *_currNode;
} rdtsc_data_t;

#define DECLARE_RDTSC_DATA(count) static rdtsc_data_t rdtsc_data_##count = {0, MAX_VAL_UINT64, MIN_VAL_UINT64, 0.0, NULL, NULL};

inline void saveNewFiringWeight(rdtsc_data_t *x, uint64_t weight);

inline void calcWeightStats(rdtsc_data_t *x);

inline void printFiringcWeights(rdtsc_data_t *x, FILE *fp);


/*--------------------------------------------------------------------------------------------------*/

#define IF_RDTSCP 1

inline void rdtsc_func(unsigned int *cycles_high, unsigned int *cycles_low);

inline void rdtscp_func(unsigned int *cycles_high, unsigned int *cycles_low);

inline void rdtsc_warmup(unsigned int *cycles_high, unsigned int *cycles_low, unsigned int *cycles_high1, unsigned int *cycles_low1);

inline void rdtsc_tick(unsigned int *cycles_high, unsigned int *cycles_low);

inline void rdtsc_tock(unsigned int *cycles_high, unsigned int *cycles_low);

inline uint64_t rdtsc_getTicksCount(unsigned int cycles_high, unsigned int cycles_low, unsigned int cycles_high1, unsigned int cycles_low1);

#endif  /* _ORCC_RDTSC_H_ */
