/**
 * \file
 * \author  Ghislain Roquier <ghislain.roquier@epfl.ch>
 * \version 1.0
 *
 * \section LICENSE
 *
 * Copyright (c) 2011, EPFL
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the EPFL nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * 
 * \section DESCRIPTION
 *
 */

#include <string.h>
#include <algorithm>

#include "timer.h"


Timer::Timer() 
{
	reset();
}

Timer::~Timer() {
}

void Timer::reset() 
{
#if _WIN32
	QueryPerformanceFrequency(&mFrequency);
	QueryPerformanceCounter(&mStartTime);
	mStartTick = GetTickCount();
	mLastTime = 0;
	mZeroClock = clock();
#else
	mZeroClock = clock();
	gettimeofday(&start, NULL);
#endif
}

unsigned long Timer::getMilliseconds() 
{
#ifdef _WIN32
	LARGE_INTEGER curTime;
	QueryPerformanceCounter(&curTime);
	LONGLONG newTime = curTime.QuadPart - mStartTime.QuadPart;

	unsigned long newTicks = (unsigned long) (1000 * newTime / mFrequency.QuadPart);

	unsigned long check = GetTickCount() - mStartTick;
	signed long msecOff = (signed long)(newTicks - check);
	if (msecOff < -100 || msecOff > 100) {
		LONGLONG adjust = (std::min)(msecOff * mFrequency.QuadPart / 1000, newTime - mLastTime);
		mStartTime.QuadPart += adjust;
		newTime -= adjust;

		newTicks = (unsigned long) (1000 * newTime / mFrequency.QuadPart);
	}
	mLastTime = newTime;

	return newTicks;

#else
	struct timeval now;
	gettimeofday(&now, NULL);
	return (now.tv_sec-start.tv_sec)*1000+(now.tv_usec-start.tv_usec)/1000;
#endif
}

unsigned long Timer::getMicroseconds() 
{
#ifdef _WIN32
	LARGE_INTEGER curTime;

	QueryPerformanceCounter(&curTime);


	LONGLONG newTime = curTime.QuadPart - mStartTime.QuadPart;

	unsigned long newTicks = (unsigned long) (1000 * newTime / mFrequency.QuadPart);

	unsigned long check = GetTickCount() - mStartTick;
	signed long msecOff = (signed long)(newTicks - check);
	if (msecOff < -100 || msecOff > 100) {
		LONGLONG adjust = (std::min)(msecOff * mFrequency.QuadPart / 1000, newTime - mLastTime);
		mStartTime.QuadPart += adjust;
		newTime -= adjust;
	}

	mLastTime = newTime;

	return (unsigned long) (1000000 * newTime / mFrequency.QuadPart);
#else
	struct timeval now;
	gettimeofday(&now, NULL);
	return (now.tv_sec-start.tv_sec)*1000000+(now.tv_usec-start.tv_usec);
#endif
}

unsigned long Timer::getMillisecondsCPU() 
{
	clock_t newClock = clock();
	return (unsigned long)( (double)( newClock - mZeroClock ) / ( (double)CLOCKS_PER_SEC / 1000.0 ) );
}

unsigned long Timer::getMicrosecondsCPU() 
{
	clock_t newClock = clock();
	return (unsigned long)( (double)( newClock - mZeroClock ) / ( (double)CLOCKS_PER_SEC / 1000000.0 ) );
}

