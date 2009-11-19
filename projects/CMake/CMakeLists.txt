# The name of our project is "Orcc". CMakeLists files in this project can
# refer to the root source directory of the project as ${Orcc_SOURCE_DIR} and
# to the root binary directory of the project as ${Orcc_BINARY_DIR}.
cmake_minimum_required (VERSION 2.8)
project (Orcc)

# Add path for custom modules
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${Orcc_SOURCE_DIR}/modules")

# Common packages required
find_package(APR REQUIRED)
find_package(SDL REQUIRED)

# Common include directories
include_directories(${APR_INCLUDE_DIR} ${SDL_INCLUDE_DIR})

# Orcc library
include_directories(
${Orcc_SOURCE_DIR}/include/liborcc
${Orcc_SOURCE_DIR}/include/liborcc/mjson
)
add_library (liborcc
lib/liborcc/mjson/json.c
lib/liborcc/mjson/json_helper.c

lib/liborcc/src/actors/display.c
lib/liborcc/src/actors/source.c
lib/liborcc/src/orcc_util.c
)

# EPFL AKA library
include_directories(${Orcc_SOURCE_DIR}/include/libcpp-epfl-aka)
add_library (libcpp-epfl-aka
lib/libcpp-epfl-aka/actoraka.cpp
lib/libcpp-epfl-aka/fifoFanOut.cpp
lib/libcpp-epfl-aka/traceaka.cpp

lib/libcpp-epfl-aka/actors/Actor_display.cpp
lib/libcpp-epfl-aka/actors/Actor_source.cpp
)

# projects in sub folders
add_subdirectory (MPEG4_SP_Decoder)
