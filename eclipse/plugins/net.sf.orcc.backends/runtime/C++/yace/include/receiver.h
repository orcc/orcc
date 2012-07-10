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

#ifndef __RECEIVER_H__
#define __RECEIVER_H__

#include "port.h"
#include "interface.h"
#include "thread.h"

#define BURST_SIZE 1024

template<typename T>
class Receiver : public Thread
{
public:
	Receiver(Interface* intf) : intf(intf) {}
	virtual void run(void* args);
	PortOut<T, 1> port_Out;
private:
	Interface* intf;
};
#endif

template<typename T>
void Receiver<T>::run(void* args)
{
	T* ptr;
	while(1)
	{
		int rooms = port_Out.getRooms();
		if(rooms >= BURST_SIZE)
		{
			ptr = port_Out.getWrPtr();
			intf->recv(ptr, BURST_SIZE*sizeof(T));
			port_Out.release(ptr, BURST_SIZE);
		}
		else
		{
			sleep(0);
		}
	}
}

