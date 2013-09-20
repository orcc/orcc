
cmake_minimum_required (VERSION 2.6)

project (orcc-map)

# Output folder
set(EXECUTABLE_OUTPUT_PATH ${CMAKE_SOURCE_DIR}/bin)

# Libraries folder
set(LIBS_DIR ${CMAKE_SOURCE_DIR}/libs)
set(SRC_DIR ${CMAKE_SOURCE_DIR}/src)

add_subdirectory(${LIBS_DIR})
add_subdirectory(${SRC_DIR})
