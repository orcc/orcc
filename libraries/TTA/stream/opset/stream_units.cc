/**
 * OSAL behavior definition file.
 */
#include <iostream>
#include <fstream>
#include <math.h> // isnan()
using namespace std;

#include "OSAL.hh"
#include "OperationGlobals.hh"
#include "Application.hh"
#include "Conversion.hh"


static std::ifstream inputFile[8];
static std::ofstream outputFile[8];


//////////////////////////////////////////////////////////////////////////////
// INPUT_STREAM - State definition for the CAL_STREAM_IN.
//
// Opens a file simulating the input stream. Default filename is tta_stream.in,
// and can be changed with the environment variable TTASIM_STREAM_IN_FILE.
//////////////////////////////////////////////////////////////////////////////

DEFINE_STATE(INPUT_STREAM)

    

INIT_STATE(INPUT_STREAM)
    if(!inputFile[0].is_open()) inputFile[0].open("tta_stream_0.in");
    if(!inputFile[1].is_open()) inputFile[1].open("tta_stream_1.in");
    if(!inputFile[2].is_open()) inputFile[2].open("tta_stream_2.in");
    if(!inputFile[3].is_open()) inputFile[3].open("tta_stream_3.in");
    if(!inputFile[4].is_open()) inputFile[4].open("tta_stream_4.in");
    if(!inputFile[5].is_open()) inputFile[5].open("tta_stream_5.in");
    if(!inputFile[6].is_open()) inputFile[6].open("tta_stream_6.in");
    if(!inputFile[7].is_open()) inputFile[7].open("tta_stream_7.in");
    
END_INIT_STATE;

FINALIZE_STATE(INPUT_STREAM)
	if(inputFile[0].is_open()) inputFile[0].close();
	if(inputFile[1].is_open()) inputFile[1].close();
	if(inputFile[2].is_open()) inputFile[2].close();
	if(inputFile[3].is_open()) inputFile[3].close();
	if(inputFile[4].is_open()) inputFile[4].close();
	if(inputFile[5].is_open()) inputFile[5].close();
	if(inputFile[6].is_open()) inputFile[6].close();
	if(inputFile[7].is_open()) inputFile[7].close();
END_FINALIZE_STATE;

END_DEFINE_STATE

//////////////////////////////////////////////////////////////////////////////
// CAL_STREAM_IN_READ - Reads a sample from the default input stream.
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(CAL_STREAM_IN_READ, INPUT_STREAM)

TRIGGER
	int index = INT(1);
	    
	int inum;
    char input[12];
    inputFile[index].getline(input, 12);
	std::istringstream iss(input);
    iss >> std::dec >> inum;
    
    IO(2) = inum;
END_TRIGGER;

END_OPERATION_WITH_STATE(CAL_STREAM_IN_READ)


//////////////////////////////////////////////////////////////////////////////
// STREAM_IN_STATUS - Reads the status of the input buffer.
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(CAL_STREAM_IN_STATUS, INPUT_STREAM)

TRIGGER
	int index = INT(1);

	unsigned int fifo_size, tokens_remaining, present_pos;
	int limit;
	present_pos = inputFile[index].tellg();
	inputFile[index].seekg(0, std::ios_base::end);
	fifo_size = inputFile[index].tellg();
	inputFile[index].seekg(present_pos, std::ios_base::beg);
	tokens_remaining = fifo_size - inputFile[index].tellg();
	tokens_remaining /= 10;
	limit = (int)sqrt((double)fifo_size);
	if(tokens_remaining <= limit)
		RUNTIME_ERROR("Stream out of data.")
		
    IO(2) = tokens_remaining;
END_TRIGGER;

END_OPERATION_WITH_STATE(CAL_STREAM_IN_STATUS)


//////////////////////////////////////////////////////////////////////////////
// STREAM_IN_PEEK - Checks the value of the next token
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(CAL_STREAM_IN_PEEK, INPUT_STREAM)

TRIGGER
	int index = INT(1);

	int inum, i, present_pos;
    char input[12];
    present_pos = inputFile[index].tellg();
    inputFile[index].getline(input, 12);
	std::istringstream iss(input);
    iss >> std::dec >> inum;
    inputFile[index].seekg(present_pos, std::ios_base::beg);
    
    IO(2) = inum;
END_TRIGGER;

END_OPERATION_WITH_STATE(CAL_STREAM_IN_PEEK)


//////////////////////////////////////////////////////////////////////////////
// OUTPUT_STREAM - State definition for the STREAM_OUT.
//
// Opens a file simulating the output stream. Default filename is 
// tta_stream.out, and can be changed with the environment variable 
// TTASIM_STREAM_IN_FILE.
//////////////////////////////////////////////////////////////////////////////

DEFINE_STATE(OUTPUT_STREAM)
    

INIT_STATE(OUTPUT_STREAM)
    if(!outputFile[0].is_open()) outputFile[0].open("tta_stream_0.out");
    if(!outputFile[1].is_open()) outputFile[1].open("tta_stream_1.out");
    if(!outputFile[2].is_open()) outputFile[2].open("tta_stream_2.out");
    if(!outputFile[3].is_open()) outputFile[3].open("tta_stream_3.out");
    if(!outputFile[4].is_open()) outputFile[4].open("tta_stream_4.out");
    if(!outputFile[5].is_open()) outputFile[5].open("tta_stream_5.out");
    if(!outputFile[6].is_open()) outputFile[6].open("tta_stream_6.out");
    if(!outputFile[7].is_open()) outputFile[7].open("tta_stream_7.out");
END_INIT_STATE;

FINALIZE_STATE(OUTPUT_STREAM)
	if(outputFile[0].is_open()) outputFile[0].close();
	if(outputFile[1].is_open()) outputFile[1].close();
	if(outputFile[2].is_open()) outputFile[2].close();
	if(outputFile[3].is_open()) outputFile[3].close();
	if(outputFile[4].is_open()) outputFile[4].close();
	if(outputFile[5].is_open()) outputFile[5].close();
	if(outputFile[6].is_open()) outputFile[6].close();
	if(outputFile[7].is_open()) outputFile[7].close();
END_FINALIZE_STATE;

END_DEFINE_STATE


//////////////////////////////////////////////////////////////////////////////
// CAL_STREAM_OUT_WRITE - Writes a sample to the default output stream.
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(CAL_STREAM_OUT_WRITE, OUTPUT_STREAM)

TRIGGER
	int index = INT(1);
    int data = INT(2);
	
	outputFile[index] << data << std::endl;
	outputFile[index] << std::flush;
	
	if (outputFile[index].fail()) {
		OUTPUT_STREAM << "error while writing the output file" << std::endl;
	}
			
END_TRIGGER;

END_OPERATION_WITH_STATE(CAL_STREAM_OUT_WRITE)

//////////////////////////////////////////////////////////////////////////////
// STREAM_OUT_STATUS - Reads the status of the output buffer.
//
// This simulation behavior always returns 1, which means output buffer
// is empty and can be written to.
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(CAL_STREAM_OUT_STATUS, OUTPUT_STREAM)

TRIGGER
    IO(2) = 0;
END_TRIGGER;

END_OPERATION_WITH_STATE(CAL_STREAM_OUT_STATUS)

