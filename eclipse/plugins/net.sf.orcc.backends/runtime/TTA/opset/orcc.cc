#include <iostream>
#include <fstream>
#include <math.h> // isnan()

#include "OSAL.hh"
#include "TCEString.hh"
#include "OperationGlobals.hh"
#include "Application.hh"
#include "Conversion.hh"

#define RUNTIME_ERROR_WITH_INT(MESSAGE, DATA) {\
       int len = strlen(MESSAGE) + 15;                \
       char *tmpBuf = static_cast<char*>(alloca(len));\
       snprintf(tmpBuf, len, "%s %d", MESSAGE, DATA); \
       OperationGlobals::runtimeError(                \
           tmpBuf, __FILE__, __LINE__, parent_);      \
}

// A macro to obtain maximum value that can be represented with 'x' bits.
// NOTE: If this is needed a lot it should be in the OSAL
// language. Currently I believe it will not be needed too much, and
// hence it could be removed.
#define MAX_VALUE(x) (((x) < sizeof(SIntWord) * 8) ? \
                     (static_cast<SIntWord>(1 << (x)) - 1) : \
                     static_cast<SIntWord>(~0))

// Macro for obtaining minimum bit width of two operands.
#define MIN(x, y) ((x < y) ? x : y)

//////////////////////////////////////////////////////////////////////////////
// ORCC_PRINT - Output a byte of data to "standard output", whatever it might
//          be on the platform at hand. Prints the char to simulator console.
//////////////////////////////////////////////////////////////////////////////
OPERATION(STDOUT)

TRIGGER
    OUTPUT_STREAM << static_cast<char>(INT(1));
END_TRIGGER;

END_OPERATION(STDOUT)

//////////////////////////////////////////////////////////////////////////////
// ORCC_READ - Output a byte of data to "standard output", whatever it might
//          be on the platform at hand. Prints the char to simulator console.
//////////////////////////////////////////////////////////////////////////////
OPERATION(STDOUT)

TRIGGER
    OUTPUT_STREAM << static_cast<char>(INT(1));
END_TRIGGER;

END_OPERATION(STDOUT)

//////////////////////////////////////////////////////////////////////////////
// ORCC_CYCLE - Output a byte of data to "standard output", whatever it might
//          be on the platform at hand. Prints the char to simulator console.
//////////////////////////////////////////////////////////////////////////////
OPERATION(STDOUT)

TRIGGER
    OUTPUT_STREAM << static_cast<char>(INT(1));
END_TRIGGER;

END_OPERATION(STDOUT)
