/**
 * OSAL behavior definition file.
 */
#include <iostream>
#include <fstream>
#include <math.h> // isnan()

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
    std::ifstream inputFile;

INIT_STATE(INPUT_STREAM)
    const char* fileNameFromEnv = getenv("TTASIM_STREAM_IN_FILE_V1");
    std::string fileName = "";
    if (fileNameFromEnv == NULL) {
        fileName = "tta_stream_v1.in";
    } else {
        fileName = fileNameFromEnv;
    }
    inputFile.open(fileName.c_str(), std::ios_base::binary);
    if (!inputFile.is_open()) {
        OUTPUT_STREAM 
            << "Cannot open input stream file " 
            << fileName << std::endl;
    }
END_INIT_STATE;

FINALIZE_STATE(INPUT_STREAM)
    inputFile.close();
END_FINALIZE_STATE;

END_DEFINE_STATE

//////////////////////////////////////////////////////////////////////////////
// CAL_STREAM_IN - Reads a sample from the default input stream.
//
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(CAL_STREAM_IN, INPUT_STREAM)

TRIGGER
    if (BWIDTH(2) != 32) {
        Application::logStream() 
            << "CAL_STREAM_IN works with 32 bit signed integers only at the moment." 
            << std::endl;
    }
    if (!STATE.inputFile.is_open()) {
        IO(2) = 0;
        return true;
    }
	int inum;
    char input[12];
    STATE.inputFile.getline(input, 12);
	std::istringstream iss(input);
    iss >> std::dec >> inum;
    IO(2) = inum;
END_TRIGGER;

END_OPERATION_WITH_STATE(CAL_STREAM_IN)

//////////////////////////////////////////////////////////////////////////////
// STREAM_IN_STATUS - Reads the status of the input buffer.
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(CAL_STREAM_IN_STATUS, INPUT_STREAM)

TRIGGER
	unsigned int fifo_size, tokens_remaining, present_pos;
	int limit;
	present_pos = STATE.inputFile.tellg();
	STATE.inputFile.seekg(0, std::ios_base::end);
	fifo_size = STATE.inputFile.tellg();
	STATE.inputFile.seekg(present_pos, std::ios_base::beg);
	tokens_remaining = fifo_size - STATE.inputFile.tellg();
	tokens_remaining /= 10;
	limit = (int)sqrt((double)fifo_size);
	if(tokens_remaining <= limit)
		RUNTIME_ERROR("Stream 1 out of data.")
    IO(2) = tokens_remaining;
END_TRIGGER;

END_OPERATION_WITH_STATE(CAL_STREAM_IN_STATUS)

//////////////////////////////////////////////////////////////////////////////
// STREAM_IN_PEEK - Checks the value of the next token
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(CAL_STREAM_IN_PEEK, INPUT_STREAM)

TRIGGER
	int inum, i, present_pos;
    char input[12];
    present_pos = STATE.inputFile.tellg();
    STATE.inputFile.getline(input, 12);
	std::istringstream iss(input);
    iss >> std::dec >> inum;
    STATE.inputFile.seekg(present_pos, std::ios_base::beg);
    IO(2) = inum;
END_TRIGGER;

END_OPERATION_WITH_STATE(CAL_STREAM_IN_PEEK)



//////////////////////////////////////////////////////////////////////////////
// OUTPUT_STREAM_V1 - State definition for the STREAM_OUT_V1.
//
// Opens a file simulating the output stream. Default filename is 
// tta_stream.out, and can be changed with the environment variable 
// TTASIM_STREAM_IN_FILE_V1.
//////////////////////////////////////////////////////////////////////////////

DEFINE_STATE(OUTPUT_STREAM_V1)
    std::ofstream outputFile;

INIT_STATE(OUTPUT_STREAM_V1)
 
    const char* fileNameFromEnv = getenv("TTASIM_STREAM_OUT_FILE_V1");
    std::string fileName = "";
    if (fileNameFromEnv == NULL) {
        fileName = "tta_stream_v1.out";
    } else {
        fileName = fileNameFromEnv;
    }
    outputFile.open(
        fileName.c_str(), 
        std::ios_base::out | std::ios_base::trunc | std::ios_base::binary);
    if (!outputFile.is_open()) {
        OUTPUT_STREAM 
            << "Cannot open output file!" 
            << fileName << std::endl;
    }
END_INIT_STATE;

FINALIZE_STATE(OUTPUT_STREAM_V1)
    outputFile.close();
END_FINALIZE_STATE;

END_DEFINE_STATE

//////////////////////////////////////////////////////////////////////////////
// STREAM_OUT_V1 - Writes a sample to the default output stream.
//
// @todo: Support for other sample sizes than 32.
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(STREAM_OUT_V1, OUTPUT_STREAM_V1)

TRIGGER

    if (BWIDTH(1) != 32) 
        Application::logStream() 
            << "STREAM_OUT_V1 works with signed integers only at the moment." 
            << std::endl;

    int data = UINT(1);
	STATE.outputFile << data << std::endl;
    STATE.outputFile << std::flush;

    if (STATE.outputFile.fail()) {
        OUTPUT_STREAM << "error while writing the output file" << std::endl;
    }
END_TRIGGER;

END_OPERATION_WITH_STATE(STREAM_OUT_V1)

//////////////////////////////////////////////////////////////////////////////
// STREAM_OUT_STATUS_V1 - Reads the status of the output buffer.
//
// This simulation behavior always returns 1, which means output buffer
// is empty and can be written to.
//////////////////////////////////////////////////////////////////////////////

OPERATION_WITH_STATE(STREAM_OUT_STATUS_V1, OUTPUT_STREAM_V1)

TRIGGER
    IO(2) = 0;
END_TRIGGER;

END_OPERATION_WITH_STATE(STREAM_OUT_STATUS_V1)

