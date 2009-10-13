#include <conio.h>
#include <stdio.h>
#include <windows.h>

int source_X;

extern void scheduler_v0();
extern void scheduler_v1();
extern void scheduler_v2();
extern void scheduler_v3();
extern void scheduler_vSDF();

LARGE_INTEGER freq;

typedef void (*func_t)();

#ifdef _DEBUG
#define COUNTER 10
#else
#define COUNTER 1000
#endif

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
	
	elapsedTime = measure_time(scheduler_v0);
	printf("elapsed time initial: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_v1);
	printf("elapsed time v1: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_v2);
	printf("elapsed time v2: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_v3);
	printf("elapsed time v3: %f ms\n", elapsedTime);
	elapsedTime = measure_time(scheduler_vSDF);
	printf("elapsed time SDF: %f ms\n", elapsedTime);

	_getch();

	return 0;
}
