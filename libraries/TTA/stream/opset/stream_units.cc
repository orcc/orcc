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

//////////////////////////////////////////////////////////////////////////////
// INPUT_STREAM - State definition for the CAL_STREAM_IN.
//
// Opens a file simulating the input stream. Default filename is tta_stream.in,
// and can be changed with the environment variable TTASIM_STREAM_IN_FILE.
//////////////////////////////////////////////////////////////////////////////

DEFINE_STATE(INPUT_STREAM)
    std::ifstream inputFile0;
    std::ifstream inputFile1;
    std::ifstream inputFile2;
    std::ifstream inputFile3;
    std::ifstream inputFile4;
    std::ifstream inputFile5;
    std::ifstream inputFile6;
    std::ifstream inputFile7;
    

INIT_STATE(INPUT_STREAM)
    inputFile0.open("tta_stream_0.in");
    inputFile1.open("tta_stream_1.in");
    inputFile2.open("tta_stream_2.in");
    inputFile3.open("tta_stream_3.in");
    inputFile4.open("tta_stream_4.in");
    inputFile5.open("tta_stream_5.in");
    inputFile6.open("tta_stream_6.in");
    inputFile7.open("tta_stream_7.in");
    
END_INIT_STATE;

FINALIZE_STATE(INPUT_STREAM)
	if(inputFile0.is_open()) inputFile0.close();
	if(inputFile1.is_open()) inputFile1.close();
	if(inputFile2.is_open()) inputFile2.close();
	if(inputFile3.is_open()) inputFile3.close();
	if(inputFile4.is_open()) inputFile4.close();
	if(inputFile5.is_open()) inputFile5.close();
	if(inputFile6.is_open()) inputFile6.close();
	if(inputFile7.is_open()) inputFile7.close();
END_FINALIZE_STATE;

END_DEFINE_STATE

//////////////////////////////////////////////////////////////////////////////
// CAL_STREAM_IN_READ - Reads a sample from the default input stream.
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(CAL_STREAM_IN_READ, INPUT_STREAM)

TRIGGER
	int index = INT(1);
    std::ifstream *inputFile;
    
    switch (index) {
		case 0:
			inputFile = &(STATE.inputFile0);
			break;
		case 1:
			inputFile = &(STATE.inputFile1);
			break;
		case 2:
			inputFile = &(STATE.inputFile2);
			break;
		case 3:
			inputFile = &(STATE.inputFile3);
			break;
		case 4:
			inputFile = &(STATE.inputFile4);
			break;
		case 5:
			inputFile = &(STATE.inputFile5);
			break;
		case 6:
			inputFile = &(STATE.inputFile6);
			break;
		case 7:
			inputFile = &(STATE.inputFile7);
	}

    if (!inputFile->is_open()) {
        IO(2) = 0;
        return true;
    }
    
	int inum;
    char input[12];
    inputFile->getline(input, 12);
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
    std::ifstream *inputFile;
    
    switch (index) {
		case 0:
			inputFile = &(STATE.inputFile0);
			break;
		case 1:
			inputFile = &(STATE.inputFile1);
			break;
		case 2:
			inputFile = &(STATE.inputFile2);
			break;
		case 3:
			inputFile = &(STATE.inputFile3);
			break;
		case 4:
			inputFile = &(STATE.inputFile4);
			break;
		case 5:
			inputFile = &(STATE.inputFile5);
			break;
		case 6:
			inputFile = &(STATE.inputFile6);
			break;
		case 7:
			inputFile = &(STATE.inputFile7);
	}  

	unsigned int fifo_size, tokens_remaining, present_pos;
	int limit;
	present_pos = inputFile->tellg();
	inputFile->seekg(0, std::ios_base::end);
	fifo_size = inputFile->tellg();
	inputFile->seekg(present_pos, std::ios_base::beg);
	tokens_remaining = fifo_size - inputFile->tellg();
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
    std::ifstream *inputFile;
    
    switch (index) {
		case 0:
			inputFile = &(STATE.inputFile0);
			break;
		case 1:
			inputFile = &(STATE.inputFile1);
			break;
		case 2:
			inputFile = &(STATE.inputFile2);
			break;
		case 3:
			inputFile = &(STATE.inputFile3);
			break;
		case 4:
			inputFile = &(STATE.inputFile4);
			break;
		case 5:
			inputFile = &(STATE.inputFile5);
			break;
		case 6:
			inputFile = &(STATE.inputFile6);
			break;
		case 7:
			inputFile = &(STATE.inputFile7);
	}  

	int inum, i, present_pos;
    char input[12];
    present_pos = inputFile->tellg();
    inputFile->getline(input, 12);
	std::istringstream iss(input);
    iss >> std::dec >> inum;
    inputFile->seekg(present_pos, std::ios_base::beg);
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
    std::ofstream outputFile0;
    std::ofstream outputFile1;
    std::ofstream outputFile2;
    std::ofstream outputFile3;
    std::ofstream outputFile4;
    std::ofstream outputFile5;
    std::ofstream outputFile6;
    std::ofstream outputFile7;

INIT_STATE(OUTPUT_STREAM)
    outputFile0.open("tta_stream_0.out");
    outputFile1.open("tta_stream_1.out");
    outputFile2.open("tta_stream_2.out");
    outputFile3.open("tta_stream_3.out");
    outputFile4.open("tta_stream_4.out");
    outputFile5.open("tta_stream_5.out");
    outputFile6.open("tta_stream_6.out");
    outputFile7.open("tta_stream_7.out");
END_INIT_STATE;

FINALIZE_STATE(OUTPUT_STREAM)
	if(outputFile0.is_open()) outputFile0.close();
	if(outputFile1.is_open()) outputFile1.close();
	if(outputFile2.is_open()) outputFile2.close();
	if(outputFile3.is_open()) outputFile3.close();
	if(outputFile4.is_open()) outputFile4.close();
	if(outputFile5.is_open()) outputFile5.close();
	if(outputFile6.is_open()) outputFile6.close();
	if(outputFile7.is_open()) outputFile7.close();
END_FINALIZE_STATE;

END_DEFINE_STATE

//////////////////////////////////////////////////////////////////////////////
// CAL_STREAM_OUT_WRITE - Writes a sample to the default output stream.
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(CAL_STREAM_OUT_WRITE, OUTPUT_STREAM)

TRIGGER
	int index = INT(1);
    int data = INT(2);
    std::ofstream *outputFile;
    
    switch (index) {
		case 0:
			outputFile = &(STATE.outputFile0);
			break;
		case 1:
			outputFile = &(STATE.outputFile1);
			break;
		case 2:
			outputFile = &(STATE.outputFile2);
			break;
		case 3:
			outputFile = &(STATE.outputFile3);
			break;
		case 4:
			outputFile = &(STATE.outputFile4);
			break;
		case 5:
			outputFile = &(STATE.outputFile5);
			break;
		case 6:
			outputFile = &(STATE.outputFile6);
			break;
		case 7:
			outputFile = &(STATE.outputFile7);
	}
	
	*outputFile << data << std::endl;
	*outputFile << std::flush;

	if (outputFile->fail()) {
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

