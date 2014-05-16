# Locate OPENHEVC library
# This module defines
# OPENHEVC_LIBRARY, the name of the library to link against
# OPENHEVC_FOUND, if false, do not try to link to OPENHEVC
# OPENHEVC_INCLUDE_DIR, where to find header files.
#
# Additional Note: If you see an empty OPENHEVC_LIBRARY_TEMP in your configuration
# and no OPENHEVC_LIBRARY, it means CMake did not find your OPENHEVC library
# (LibOpenHevcWrapper.dll, libLibOpenHevcWrapper.so, etc).
# Set OPENHEVC_LIBRARY_TEMP to point to your OPENHEVC library, and configure again.
# These values are used to generate the final OPENHEVC_LIBRARY
# variable, but when these values are unset, OPENHEVC_LIBRARY does not get created.
#
# $OPENHEVCDIR is an environment variable that would correspond to a custom
# installation directory used in building OPENHEVC.
#
# On OSX, this will prefer the Framework version (if found) over others.
# People will have to manually change the cache values of
# OPENHEVC_LIBRARY to override this selection or set the CMake environment
# CMAKE_INCLUDE_PATH to modify the search paths.

SET(OPENHEVC_SEARCH_PATHS
    ~/Library/Frameworks
    /Library/Frameworks
    /usr/local
    /usr
    /sw # Fink
    /opt/local # DarwinPorts
    /opt/csw # Blastwave
    /opt
)

FIND_PATH(OPENHEVC_INCLUDE_DIR openHevcWrapper.h
    HINTS
    $ENV{OPENHEVCDIR}
    PATH_SUFFIXES include/OPENHEVC include
    PATHS ${OPENHEVC_SEARCH_PATHS}
)

FIND_LIBRARY(OPENHEVC_LIBRARY_TEMP
    NAMES LibOpenHevcWrapper
    HINTS
    $ENV{OPENHEVCDIR}
    PATH_SUFFIXES lib64 lib
    PATHS ${OPENHEVC_SEARCH_PATHS}
)

# OPENHEVC may require threads on your system.
# The Apple build may not need an explicit flag because one of the
# frameworks may already provide it.
# But for non-OSX systems, I will use the CMake Threads package.
IF(NOT APPLE)
    FIND_PACKAGE(Threads)
ENDIF(NOT APPLE)

# MinGW needs an additional library mwindows
IF(MINGW)
    SET(MINGW32_LIBRARY mingw32 CACHE STRING "mwindows for MinGW")
ENDIF(MINGW)

IF(OPENHEVC_LIBRARY_TEMP)
    # For threads, as mentioned Apple doesn't need this.
    # In fact, there seems to be a problem if I used the Threads package
    # and try using this line, so I'm just skipping it entirely for OS X.
    IF(NOT APPLE)
        SET(OPENHEVC_LIBRARY_TEMP ${OPENHEVC_LIBRARY_TEMP} ${CMAKE_THREAD_LIBS_INIT})
    ENDIF(NOT APPLE)

    # For MinGW library
    IF(MINGW)
        SET(OPENHEVC_LIBRARY_TEMP ${MINGW32_LIBRARY} ${OPENHEVC_LIBRARY_TEMP})
    ENDIF(MINGW)

    # Set the final string here so the GUI reflects the final state.
    SET(OPENHEVC_LIBRARY ${OPENHEVC_LIBRARY_TEMP} CACHE STRING "Where the OPENHEVC Library can be found")
    # Set the temp variable to INTERNAL so it is not seen in the CMake GUI
    SET(OPENHEVC_LIBRARY_TEMP "${OPENHEVC_LIBRARY_TEMP}" CACHE INTERNAL "")
ENDIF(OPENHEVC_LIBRARY_TEMP)

INCLUDE(FindPackageHandleStandardArgs)

FIND_PACKAGE_HANDLE_STANDARD_ARGS(OPENHEVC REQUIRED_VARS OPENHEVC_LIBRARY OPENHEVC_INCLUDE_DIR)
