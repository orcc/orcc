# The name of our project is "Orcc". CMakeLists files in this project can
# refer to the root source directory of the project as ${Orcc_SOURCE_DIR} and
# to the root binary directory of the project as ${Orcc_BINARY_DIR}.
cmake_minimum_required (VERSION 2.8)
project (Orcc)

# Add path for custom modules
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} ${Orcc_SOURCE_DIR}/modules)

# Common packages required
find_package(APR REQUIRED)
find_package(SDL REQUIRED)

# Common include directories
include_directories(${APR_INCLUDE_DIR} ${SDL_INCLUDE_DIR})

# Orcc library
include_directories(${Orcc_SOURCE_DIR}/include/liborcc )
add_library (liborcc
${Orcc_SOURCE_DIR}/lib/liborcc/actors/display.c
${Orcc_SOURCE_DIR}/lib/liborcc/actors/source.c
${Orcc_SOURCE_DIR}/lib/liborcc/orcc_util.c
)

# C JSON library
include_directories(${Orcc_SOURCE_DIR}/include/libcjson)
add_library (libcjson
${Orcc_SOURCE_DIR}/lib/libcjson/cJSON.c
)

# EPFL AKA library
include_directories(${Orcc_SOURCE_DIR}/include/libcpp-epfl-aka ${Orcc_SOURCE_DIR}/include/libcpp-epfl-aka/actors)

add_library (libcpp-epfl-aka
${Orcc_SOURCE_DIR}/lib/libcpp-epfl-aka/actoraka.cpp
${Orcc_SOURCE_DIR}/lib/libcpp-epfl-aka/fifoFanOut.cpp
${Orcc_SOURCE_DIR}/lib/libcpp-epfl-aka/traceaka.cpp

${Orcc_SOURCE_DIR}/lib/libcpp-epfl-aka/actors/Actor_display.cpp
${Orcc_SOURCE_DIR}/lib/libcpp-epfl-aka/actors/Actor_source.cpp
)

# projects in sub folders
add_subdirectory (MPEG4_SP_Decoder)
