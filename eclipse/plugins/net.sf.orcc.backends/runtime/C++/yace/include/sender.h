/**
 * @file
 * @author  Ghislain Roquier <ghislain.roquier@epfl.ch>
 * @version 1.0
 *
 * @section LICENSE
 *
 * Copyright (c) 2011, EPFL
 * All rights reserved.
 * 
 * @section DESCRIPTION
 *
 */

#ifndef __SENDER_H__
#define __SENDER_H__

#include "port.h"
#include "interface.h"

#define BURST_SIZE 1024

template<typename T>
class Sender : public Thread
{
public:
	Sender(Interface* intf) : intf(intf) {}

	virtual void run(void* args);

	PortIn<T> port_In;

private:
	Interface* intf;
};

template<typename T>
void Sender<T>::run(void* args)
{
	T* ptr;
	while(1)
	{
		int count = port_In.getCount();
		if(count >= BURST_SIZE)
		{
			ptr = port_In.getRdPtr(BURST_SIZE);
			intf->send(ptr, BURST_SIZE*sizeof(T));
			port_In.release(BURST_SIZE);
		}
		else
		{
			sleep(0);
		}
	}
}

#endif
