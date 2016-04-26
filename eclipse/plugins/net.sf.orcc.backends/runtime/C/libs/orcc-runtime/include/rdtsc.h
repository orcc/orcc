#ifndef _ORCC_RDTSC_H_
#define _ORCC_RDTSC_H_

#include <stdio.h>
#include <stdint.h>
#include <float.h>

#define MAX_VAL_UINT64 UINT64_MAX
#define MIN_VAL_UINT64 0x0

typedef struct rdtsc_node {
	uint64_t _weight;
	struct rdtsc_node *_next;
} rdtsc_node_t;

typedef struct rdtsc_data {
	uint64_t _numFirings;
	long double _minWeight;
	long double _maxWeight;
	long double _avgWeight;
	long double _variance;
	rdtsc_node_t *_head;
	rdtsc_node_t *_lastNode;
} rdtsc_data_t;

#define DECLARE_ACTION_PROFILING_DATA(count) static rdtsc_data_t profDataAction_##count = {0, LDBL_MAX, 0.0, 0.0, 0.0, NULL, NULL};
#define DECLARE_SCHEDULER_PROFILING_DATA(count) static rdtsc_data_t profDataScheduler_##count = {0, LDBL_MAX, 0.0, 0.0, 0.0, NULL, NULL};

inline void saveNewFiringWeight(rdtsc_data_t *llist, uint64_t weight);

inline void calcWeightStats(rdtsc_data_t *llist, int useFilter);

inline void printFiringcWeights(char *actionName, rdtsc_data_t *llist, FILE *fp);

/*--------------------------------------------------------------------------------------------------*/

#define IF_RDTSCP 0

inline void rdtsc_func(unsigned int *cycles_high, unsigned int *cycles_low);

inline void rdtscp_func(unsigned int *cycles_high, unsigned int *cycles_low);

inline void rdtsc_warmup(unsigned int *cycles_high, unsigned int *cycles_low, unsigned int *cycles_high1, unsigned int *cycles_low1);

inline void rdtsc_tick(unsigned int *cycles_high, unsigned int *cycles_low);

inline void rdtsc_tock(unsigned int *cycles_high, unsigned int *cycles_low);

inline uint64_t rdtsc_getTicksCount(unsigned int cycles_high, unsigned int cycles_low, unsigned int cycles_high1, unsigned int cycles_low1);

#endif  /* _ORCC_RDTSC_H_ */
