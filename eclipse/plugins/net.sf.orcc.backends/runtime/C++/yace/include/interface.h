/*
 * Copyright (c) 2011, Ecole Polytechnique Fédérale de Lausanne
 * All rights reserved.
 */
#ifndef __INTERFACE_H__
#define __INTERFACE_H__

class Interface
{
public:
	virtual int send(void*, const unsigned) = 0;
	
	virtual int recv(void*, const unsigned) = 0;
};

#endif
