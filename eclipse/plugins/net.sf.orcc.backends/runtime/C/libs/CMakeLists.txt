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

find_package(THREADS QUIET)
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
