/**
 * Generated from "source"
 */
#ifndef __ACTOR_source_H__
#define __ACTOR_source_H__

#include "actoraka.h"

#include <stdio.h>

enum source_Iports{
source_IPORT_SIZE
};

enum source_Oports{
source_O,
source_OPORT_SIZE
};

class Actor_source : public ActorGen 
{

	// State variables of the actor
private:
	char * fname;
	
	FILE * F;
	int cnt;

public:
	// Default constructor
	Actor_source();
	
private:
	// Functions/procedures
	// Actions
public:
	void initialize();
	
	// Action scheduler

	void process();

};

#endif
