# Locate METIS library
# This module defines
# METIS_LIBRARY, the name of the library to link against
# METIS_FOUND, if false, do not try to link to METIS
# METIS_INCLUDE_DIR, where to find header files.
#
# Additional Note: If you see an empty METIS_LIBRARY_TEMP in your configuration
# and no METIS_LIBRARY, it means CMake did not find your METIS library
# (metis.dll, libmetis.so, etc).
# Set METIS_LIBRARY_TEMP to point to your METIS library, and configure again.
# These values are used to generate the final METIS_LIBRARY
# variable, but when these values are unset, METIS_LIBRARY does not get created.
#
# $METISDIR is an environment variable that would correspond to a custom
# installation directory used in building METIS.
#
# On OSX, this will prefer the Framework version (if found) over others.
# People will have to manually change the cache values of
# METIS_LIBRARY to override this selection or set the CMake environment
# CMAKE_INCLUDE_PATH to modify the search paths.

SET(METIS_SEARCH_PATHS
    ~/Library/Frameworks
    /Library/Frameworks
    /usr/local
    /usr
    /sw # Fink
    /opt/local # DarwinPorts
    /opt/csw # Blastwave
    /opt
)

FIND_PATH(METIS_INCLUDE_DIR metis.h
    HINTS
    $ENV{METISDIR}
    PATH_SUFFIXES include/METIS include
    PATHS ${METIS_SEARCH_PATHS}
)

FIND_LIBRARY(METIS_LIBRARY_TEMP
    NAMES metis
    HINTS
    $ENV{METISDIR}
    PATH_SUFFIXES lib64 lib
    PATHS ${METIS_SEARCH_PATHS}
)

# MinGW needs an additional library mwindows
IF(MINGW)
        SET(MINGW32_LIBRARY mingw32 CACHE STRING "mwindows for MinGW")
ENDIF(MINGW)

IF(METIS_LIBRARY_TEMP)
    # Metis needs an additional library m
    SET(METIS_LIBRARY_TEMP ${METIS_LIBRARY_TEMP} m)

    # For MinGW library
    IF(MINGW)
            SET(METIS_LIBRARY_TEMP ${MINGW32_LIBRARY} ${METIS_LIBRARY_TEMP})
    ENDIF(MINGW)

    # Set the final string here so the GUI reflects the final state.
    SET(METIS_LIBRARY ${METIS_LIBRARY_TEMP} CACHE STRING "Where the METIS Library can be found")
    # Set the temp variable to INTERNAL so it is not seen in the CMake GUI
    SET(METIS_LIBRARY_TEMP "${METIS_LIBRARY_TEMP}" CACHE INTERNAL "")
ENDIF(METIS_LIBRARY_TEMP)

INCLUDE(FindPackageHandleStandardArgs)

FIND_PACKAGE_HANDLE_STANDARD_ARGS(METIS REQUIRED_VARS METIS_LIBRARY METIS_INCLUDE_DIR)
