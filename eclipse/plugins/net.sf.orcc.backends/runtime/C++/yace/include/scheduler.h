/**
 * \file
 * \author  Ghislain Roquier <ghislain.roquier@epfl.ch>
 * \version 1.0
 *
 * \section LICENSE
 *
 * Copyright (c) 2012, EPFL
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
#ifndef __SCHEDULER_H__
#define __SCHEDULER_H__

#include <vector>

#include "actor.h"
#include "condition.h"
#include "thread.h"

class Scheduler : public Thread
{
public:
	Scheduler(Condition& done) : m_done(done)
	{
	}

	Scheduler(std::vector<Actor*>& actor, Condition& done)
		: actors(actors)
		, m_done(done)
	{
	}

	~Scheduler()
	{
	}

	virtual void run(void* args)
	{
		std::vector<Actor*>::iterator it;
		for(it = actors.begin(); it != actors.end(); it++)
		{
			(*it)->initialize();
		}

		for(;;)
		{
			EStatus status = None;
			for(it = actors.begin(); it != actors.end(); it++)
			{
				(*it)->schedule(status);
			}
			if(status == None)
				yield();
		}
	}

	void add(Actor* actor)
	{
		actors.push_back(actor);
	}

	std::vector<Actor*>& getActors() { return actors; }; 

	void done() 
	{ 
		m_done.signal();
		sleep(1000000);
	}

private:
	std::vector<Actor*> actors;

	Condition& m_done;
};

#endif

