#ifndef __BARRIER_H__
#define __BARRIER_H__

static void barrier() {
#ifdef _WIN32
#else
	asm volatile("":::"memory");
#endif
}
#endif
