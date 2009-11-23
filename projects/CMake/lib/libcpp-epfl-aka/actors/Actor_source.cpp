/**
 * Generated from "source"
 */

#define _CRT_SECURE_NO_WARNINGS

#include "Actor_source.h"

extern "C" {
#include "orcc_util.h"
};

#include <stdlib.h>

Actor_source::Actor_source():ActorGen(source_IPORT_SIZE, source_OPORT_SIZE)
{
	F = NULL;
	cnt = 0;
}
		
void Actor_source::initialize() {
	if (input_file == NULL) {
		print_usage();
		fprintf(stderr, "No input file given!\n");
		pause();
		exit(1);
	}

	F = fopen(input_file, "rb");
	if (F == NULL) {
		if (input_file == NULL) {
			input_file = (char *) "<null>";
		}

		fprintf(stderr, "could not open file \"%s\"\n", fname);
		pause();
		exit(1);
	}
}

	// Action scheduler

void Actor_source::process() {
	char O[1];
	int n;

	if (feof(F)) {
		return;
	}
	
	while (m_poTabOut[source_O]->hasRooms(1)) {
		n = fread(O, 1, 1, F);
		m_poTabOut[source_O]->put(O);
		if (n < 1) {
			fseek(F, 0, 0);
			cnt = 0;
			n = fread(O, 1, 1, F);
		}
		cnt++;
	}
}
