#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <string>

#include "OSAL.hh"
#include "TCEString.hh"
#include "OperationGlobals.hh"
#include "Application.hh"
#include "Conversion.hh"
#include "NetworkSimulatorContext.hh"


//////////////////////////////////////////////////////////////////////////////
// INPUT_STREAM - State definition for the CAL_STREAM_IN.
//
// Opens a file simulating the input stream. Default filename is tta_stream.in,
// and can be changed with the environment variable TTASIM_STREAM_IN_FILE.
//////////////////////////////////////////////////////////////////////////////

DEFINE_STATE(ORCC_FU)

    FILE *file;

    INIT_STATE(ORCC_FU)
    END_INIT_STATE;

    FINALIZE_STATE(ORCC_FU)
    END_FINALIZE_STATE;

END_DEFINE_STATE


//////////////////////////////////////////////////////////////////////////////
// SOURCE_INIT
//////////////////////////////////////////////////////////////////////////////
OPERATION_WITH_STATE(SOURCE_INIT, ORCC_FU)

TRIGGER
    OUTPUT_STREAM << "source_init()\n";
    string input_file = NetworkSimulatorContext::inputFile();

    if (input_file.empty()) {
        OUTPUT_STREAM << "No input file given!\n";
        exit(1);
    }

    STATE.file = fopen(input_file.c_str(), "rb");
    if (STATE.file == NULL) {
        OUTPUT_STREAM << "could not open input file.\n";
        exit(1);
    }
END_TRIGGER;

END_OPERATION_WITH_STATE(SOURCE_INIT)

//////////////////////////////////////////////////////////////////////////////
// SOURCE_READBYTE
//////////////////////////////////////////////////////////////////////////////
OPERATION_WITH_STATE(SOURCE_READBYTE, ORCC_FU)

TRIGGER
    unsigned char buf[1];
    int n = fread(&buf, 1, 1, STATE.file);

    if (n < 1) {
        if (feof(STATE.file)) {
            OUTPUT_STREAM << "warning\n";
            rewind(STATE.file);
            n = fread(&buf, 1, 1, STATE.file);
        }
        else {
            OUTPUT_STREAM << "Problem when reading input file.\n";
            exit(1);
        }
    }

    IO(2) = static_cast<UIntWord>(buf[0]);
END_TRIGGER;

END_OPERATION_WITH_STATE(SOURCE_READBYTE)

//////////////////////////////////////////////////////////////////////////////
// SOURCE_SIZEOFFILE
//////////////////////////////////////////////////////////////////////////////
OPERATION_WITH_STATE(SOURCE_SIZEOFFILE, ORCC_FU)

TRIGGER
    OUTPUT_STREAM << "source_sizeoffile()\n";
    struct stat st; 
    int size;
    fstat(fileno(STATE.file), &st); 
    size = st.st_size;
    IO(2) = static_cast<UIntWord>(size); 
END_TRIGGER;

END_OPERATION_WITH_STATE(SOURCE_SIZEOFFILE)

//////////////////////////////////////////////////////////////////////////////
// SOURCE_REWIND
//////////////////////////////////////////////////////////////////////////////
OPERATION_WITH_STATE(SOURCE_REWIND, ORCC_FU)

TRIGGER
    if(STATE.file != NULL) {
        rewind(STATE.file);
    }
END_TRIGGER;

END_OPERATION_WITH_STATE(SOURCE_REWIND)

//////////////////////////////////////////////////////////////////////////////
// SOURCE_DECREMENTNBLOOPS
//////////////////////////////////////////////////////////////////////////////
OPERATION_WITH_STATE(SOURCE_DECREMENTNBLOOPS, ORCC_FU)

TRIGGER

END_TRIGGER;

END_OPERATION_WITH_STATE(SOURCE_DECREMENTNBLOOPS)

//////////////////////////////////////////////////////////////////////////////
// SOURCE_ISMAXLOOPSREACHED
//////////////////////////////////////////////////////////////////////////////
OPERATION_WITH_STATE(SOURCE_ISMAXLOOPSREACHED, ORCC_FU)

TRIGGER
    IO(2) = false;
END_TRIGGER;

END_OPERATION_WITH_STATE(SOURCE_ISMAXLOOPSREACHED)

//////////////////////////////////////////////////////////////////////////////
// DISPLAY_CHECKSUM
//////////////////////////////////////////////////////////////////////////////
OPERATION(SOURCE_EXIT)

TRIGGER
    exit(INT(2));
END_TRIGGER;

END_OPERATION(SOURCE_EXIT)

//////////////////////////////////////////////////////////////////////////////
// DISPLAY_CHECKSUM
//////////////////////////////////////////////////////////////////////////////
OPERATION_WITH_STATE(DISPLAY_CHECKSUM, ORCC_FU)

TRIGGER
    OUTPUT_STREAM << "Checksum = " << INT(2) << "\n";
END_TRIGGER;

END_OPERATION_WITH_STATE(DISPLAY_CHECKSUM)

//////////////////////////////////////////////////////////////////////////////
// PRINT_CYCLE_COUNT
//////////////////////////////////////////////////////////////////////////////
OPERATION(PRINT_CYCLECOUNT)

TRIGGER
    OUTPUT_STREAM << "CYCLE = " << CYCLE_COUNT << "\n";
END_TRIGGER;

END_OPERATION(PRINT_CYCLECOUNT)
