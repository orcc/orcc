cmake_minimum_required (VERSION 2.6)

project (orcc-lib)

set(CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR}/cmake/Modules/)

include(OptimizeForArchitecture)

if(NOT CMAKE_CROSSCOMPILING)
    # Optimize the compilation for the local processor architecture
    OptimizeForArchitecture()

    if(NOT MSVC)
        list(APPEND extra_definitions -march=native)
    endif()
endif()

configure_file(config.h.in config.h)

find_package(Threads QUIET)
if(THREADS_FOUND)
    list(APPEND extra_libraries ${CMAKE_THREAD_LIBS_INIT})
    list(APPEND extra_definitions -DTHREADS_ENABLE)
else()
    message(STATUS "Cannot find Threads")
endif()

include_directories(
    ${PROJECT_BINARY_DIR} # to find config.h
    roxml/include
    orcc-runtime/include
    orcc-native/include
)

# Allow linking to additional user libraries
# FIXME: Use foreach to allow multiple libraries
# FIXME: Include header directory
set(USR_LIB_DIR "" CACHE PATH "Path to user library directory")
set(USR_LIB_NAME "" CACHE STRING "Name of user library located in USR_LIB_DIR")
if(USR_LIB_NAME)
    find_library(USR_LIBRARY ${USR_LIB_NAME} PATHS ${USR_LIB_DIR})
    set(USR_LIBRARY "${USR_LIBRARY}" CACHE INTERNAL "")
    set(extra_libraries ${extra_libraries} ${USR_LIBRARY})
endif()

add_subdirectory(roxml)
add_subdirectory(orcc-runtime)
add_subdirectory(orcc-native)

option(BUILD_ORCCMAP "Build the mapping tool as a standalone executable" 0)
if(BUILD_ORCCMAP)
    add_subdirectory(orcc-map)
endif()

set(extra_definitions ${extra_definitions} PARENT_SCOPE)
set(extra_includes ${extra_includes} PARENT_SCOPE)
set(extra_libraries ${extra_libraries} PARENT_SCOPE)
