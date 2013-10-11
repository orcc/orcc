cmake_minimum_required (VERSION 2.8)

project (orcc-map)

# Output folder
set(EXECUTABLE_OUTPUT_PATH ${CMAKE_SOURCE_DIR}/bin)


# Libraries folder
set(LIBS_DIR ${CMAKE_SOURCE_DIR}/libs)
set(SRC_DIR ${CMAKE_SOURCE_DIR}/src)

include_directories(
        ${LIBS_DIR}/roxml/include
        ${LIBS_DIR}/metis/include
)

add_subdirectory(${LIBS_DIR})
add_subdirectory(${SRC_DIR})
