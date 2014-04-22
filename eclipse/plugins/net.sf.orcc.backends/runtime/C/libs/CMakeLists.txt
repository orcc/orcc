cmake_minimum_required (VERSION 2.6)

project (orcc-lib)

set(CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR}/cmake/Modules/)

if(NOT MSVC AND NOT CMAKE_CROSSCOMPILING)
    # Optimize the compilation for the local processor architecture
    list(APPEND extra_definitions -march=native)
endif()

if(NOT NO_EXTERNAL_DEPENDENCIES)
    include_directories(roxml/include)
    add_subdirectory(roxml)
endif()

include_directories(orcc-runtime/include)
include_directories(orcc-native/include)

add_subdirectory(orcc-runtime)
add_subdirectory(orcc-native)

option(BUILD_ORCCMAP "Build the mapping tool as a standalone executable" 0)
if(BUILD_ORCCMAP)
    add_subdirectory(orcc-map)
endif()

set(extra_definitions ${extra_definitions} PARENT_SCOPE)
set(extra_includes ${extra_includes} PARENT_SCOPE)
