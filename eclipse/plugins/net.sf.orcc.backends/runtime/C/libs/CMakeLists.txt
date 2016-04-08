cmake_minimum_required (VERSION 2.6)

project (orcc-lib)

configure_file(orcc_config.h.in orcc_config.h)

find_package(Threads QUIET)
if(THREADS_FOUND)
    list(APPEND extra_libraries ${CMAKE_THREAD_LIBS_INIT})
    list(APPEND extra_definitions -DTHREADS_ENABLE)
else()
    message(STATUS "Cannot find Threads")
endif()

include_directories(
	${PROJECT_BINARY_DIR} # to find orcc_config.h
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
