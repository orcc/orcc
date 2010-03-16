#include <conio.h>
#include <stdio.h>
#include <windows.h>


#include "defs.h"

extern void scheduler_v0();
extern void scheduler_v1();
extern void scheduler_v2();
extern void scheduler_v3();
extern void scheduler_v4();
extern void scheduler_v5();
extern void scheduler_v6();
extern void scheduler_vSDF();

extern void initialize2_v1();
extern void scheduler2_v1();

LARGE_INTEGER freq;

typedef void (*func_t)();

#ifdef _DEBUG
#define COUNTER 10
#else
#define COUNTER 1000
#endif

n_token = NTOKEN;

float measure_time(func_t scheduling_func) {
	int i;
	LARGE_INTEGER startTime, endTime;

	QueryPerformanceCounter(&startTime);
	for (i = 0; i < COUNTER; i++) {
		source_X = 0;
		scheduling_func();
	}
	QueryPerformanceCounter(&endTime);

	return (float)1000 * (float) (endTime.QuadPart - startTime.QuadPart) / (float) freq.QuadPart;
}


int main(int argc, char *argv[]) {
	float elapsedTime;

	QueryPerformanceFrequency(&freq);

	// initialize structure of scheduler
	initialize2_v1();

	elapsedTime = measure_time(scheduler2_v1);
	printf("elapsed time scheduler2: %f ms\n", elapsedTime);
	
	elapsedTime = measure_time(scheduler_v0);
	printf("elapsed time initial: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_v1);
	printf("elapsed time v1: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_v2);
	printf("elapsed time v2: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_v3);
	printf("elapsed time v3: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_v4);
	printf("elapsed time v4: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_v5);
	printf("elapsed time v5: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_v6);
	printf("elapsed time v5: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_vSDF);
	printf("elapsed time SDF: %f ms\n", elapsedTime);

	_getch();

	return 0;
}
