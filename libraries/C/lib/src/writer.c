// Source file is "/home/endrix/Projects/jpeg/src/jpeg/encoder/hw/Test/writer.cal"

#include <stdio.h>
#include <stdlib.h>

#include "orcc_types.h"
#include "orcc_fifo.h"
#include "orcc_util.h"

#define SIZE 512

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs
extern struct fifo_i16_s *writer_Byte;
extern struct fifo_i32_s *writer_pEOF;

static unsigned int index_Byte;
static unsigned int numTokens_Byte;
#define NUM_READERS_Byte 1
#define SIZE_Byte SIZE
#define tokens_Byte writer_Byte->contents

static unsigned int index_pEOF;
static unsigned int numTokens_pEOF;
#define NUM_READERS_pEOF 1
#define SIZE_pEOF SIZE
#define tokens_pEOF writer_pEOF->contents

static FILE *F = NULL;
static int cnt = 0;


////////////////////////////////////////////////////////////////////////////////
// Input FIFOs
////////////////////////////////////////////////////////////////////////////////
// Input FIFOs Id
static unsigned int fifo_writer_Byte_id; 
static unsigned int fifo_writer_pEOF_id;  

////////////////////////////////////////////////////////////////////////////////
// Parameter values of the instance
////////////////////////////////////////////////////////////////////////////////
// State variables of the actor


////////////////////////////////////////////////////////////////////////////////
// Initial FSM state of the actor
enum states {
	my_state_s0 = 0,
	my_state_s1
};

static char *stateNames[] = {
	"s0",
	"s1"
};

static enum states _FSM_state;


////////////////////////////////////////////////////////////////////////////////
// Token functions

static void read_Byte() {
	index_Byte = writer_Byte->read_inds[fifo_writer_Byte_id];
	numTokens_Byte = index_Byte + fifo_i16_get_num_tokens(writer_Byte, fifo_writer_Byte_id);
}

static void read_end_Byte() {
	writer_Byte->read_inds[fifo_writer_Byte_id] = index_Byte;
}

static void read_pEOF() {
	index_pEOF = writer_pEOF->read_inds[fifo_writer_pEOF_id];
	numTokens_pEOF = index_pEOF + fifo_i32_get_num_tokens(writer_pEOF, fifo_writer_pEOF_id);
}

static void read_end_pEOF() {
	writer_pEOF->read_inds[fifo_writer_pEOF_id] = index_pEOF;
}


////////////////////////////////////////////////////////////////////////////////
// Initializes
void writer_initialize(unsigned int fifo_Byte_id, unsigned int fifo_pEOF_id) {
	
	/* Set initial state to current FSM state */
	_FSM_state = my_state_s0;

	/* Set initial value to global variable */

	/* Initialize input FIFOs id */
	fifo_writer_Byte_id = fifo_Byte_id; 
	fifo_writer_pEOF_id = fifo_pEOF_id;  


	if (write_file == NULL) {
		print_usage();
		fprintf(stderr, "No write file given!\n");
		wait_for_key();
		exit(1);
	}

	F = fopen(write_file, "wb");
	if (F == NULL) {
		if (write_file == NULL) {
			write_file = "<null>";
		}

		fprintf(stderr, "could not open file \"%s\"\n", write_file);
		wait_for_key();
		exit(1);
	}else{
		fseek(F,0,SEEK_SET);	
	}
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler
void writer_scheduler(struct schedinfo_s *si) {
	int i = 0;
	si->ports = 0;
	i32 peof;
	u8 wr;
	read_Byte();
	read_pEOF();


	// jump to FSM state 
	switch (_FSM_state) {
	case my_state_s0:
		goto l_s0;
	case my_state_s1:
		goto l_s1;
	default:
		printf("unknown state in writer.c : %s\n", stateNames[_FSM_state]);
		wait_for_key();
		exit(1);
	}

	// FSM transitions

l_s0:
	if (index_pEOF + 1 <= numTokens_pEOF) {
		int ports = 0;
		if (ports != 0) {
			_FSM_state = my_state_s0;
			si->num_firings = i;
			si->reason = full;
			si->ports = ports;
			goto finished;
		}
		peof = tokens_pEOF[(index_pEOF + 0) % SIZE_pEOF];
		if (peof == 0) {
			i++;		
			index_pEOF += 1;
			goto l_s1;
		} else {
			fclose(F);
			index_pEOF += 1;
			exit(666);		
		}
	} else {
		int ports = 0;
		if (index_pEOF + 1 > numTokens_pEOF) {
			ports |= 0x02;
		}
		si->num_firings = i;
		si->reason = starved;
		si->ports |= ports;
		_FSM_state = my_state_s0;
		goto finished;
	}

l_s1:
	if (index_Byte + 1 <= numTokens_Byte ) {
		int ports = 0;
		if (ports != 0) {
			_FSM_state = my_state_s1;
			si->num_firings = i;
			si->reason = full;
			si->ports = ports;
			goto finished;
		}
		wr = tokens_Byte[(index_Byte + 0) % SIZE_Byte];
		fseek(F,sizeof(u8)*cnt,SEEK_SET);
		fwrite(&wr,sizeof(u8),1,F);
		cnt++;	

		index_Byte += 1;
		
		i++;
		goto l_s0;
	} else {
		int ports = 0;
		if (index_Byte + 1 > numTokens_Byte) {
			ports |= 0x01;
		}
		si->num_firings = i;
		si->reason = starved;
		si->ports |= ports;
		_FSM_state = my_state_s1;
		goto finished;
	}

finished:
	read_end_Byte();
	read_end_pEOF();
}

