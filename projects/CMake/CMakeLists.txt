# The name of our project is "Orcc". CMakeLists files in this project can
# refer to the root source directory of the project as ${Orcc_SOURCE_DIR} and
# to the root binary directory of the project as ${Orcc_BINARY_DIR}.
cmake_minimum_required (VERSION 2.8)
project (Orcc)

# Add path for custom modules
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} ${Orcc_SOURCE_DIR}/modules)

# Common packages required
find_package(SDL REQUIRED)
find_package(SDL_image REQUIRED)

# Common include directories
include_directories(${SDL_INCLUDE_DIR} ${SDLIMAGE_INCLUDE_DIR})

# Orcc library
include_directories(${Orcc_SOURCE_DIR}/include/liborcc )
add_library (liborcc
${Orcc_SOURCE_DIR}/lib/liborcc/actors/display.c
${Orcc_SOURCE_DIR}/lib/liborcc/actors/img_display.c
${Orcc_SOURCE_DIR}/lib/liborcc/actors/img_read.c
${Orcc_SOURCE_DIR}/lib/liborcc/actors/source.c
${Orcc_SOURCE_DIR}/lib/liborcc/orcc_util.c
${Orcc_SOURCE_DIR}/lib/liborcc/getopt.c
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

${Orcc_SOURCE_DIR}/lib/libcpp-epfl-aka/actors/Actor_img_display.cpp
${Orcc_SOURCE_DIR}/lib/libcpp-epfl-aka/actors/Actor_img_read.cpp
${Orcc_SOURCE_DIR}/lib/libcpp-epfl-aka/actors/Actor_display.cpp
${Orcc_SOURCE_DIR}/lib/libcpp-epfl-aka/actors/Actor_source.cpp
)

# projects in sub folders
option(BUILD_AVC "whether AVC should be built" false)
option(BUILD_MPEG2 "whether MPEG-2 should be built" false)
option(BUILD_MPEG4 "whether MPEG-4 should be built" false)
option(BUILD_MPEG4_SERIAL "whether MPEG-4 serial version should be built" false)
option(BUILD_MPEG4_SERIAL_MOTION "whether MPEG-4 serial with new motion version should be built" false)
option(BUILD_RVC_MPEG4 "whether RVC MPEG-4 should be built" false)
option(BUILD_SEAM_CARVING "whether Seam carving should be built" false)
option(BUILD_Cpp_MPEG4_SERIAL "whether C++ MPEG-4 serial should be built" false)


if(BUILD_AVC)
  add_subdirectory (AVC_CBP_Decoder)
endif(BUILD_AVC)

if(BUILD_MPEG2)
  add_subdirectory (MPEG2)
endif(BUILD_MPEG2)

if(BUILD_MPEG4)
  add_subdirectory (MPEG4_SP_Decoder)
endif(BUILD_MPEG4)

if(BUILD_MPEG4_SERIAL)
  add_subdirectory (MPEG4_SP_Decoder_Serial)
endif(BUILD_MPEG4_SERIAL)

if(BUILD_MPEG4_SERIAL_MOTION)
  add_subdirectory (MPEG4_SP_Decoder_Serial_new_motion)
endif(BUILD_MPEG4_SERIAL_MOTION)

if(BUILD_RVC_MPEG4)
  add_subdirectory (RVC_MPEG4_SP_Decoder)
endif(BUILD_RVC_MPEG4)

if(BUILD_SEAM_CARVING)
  add_subdirectory (Seam_carving)
endif(BUILD_SEAM_CARVING)

if(BUILD_Cpp_MPEG4_SERIAL)
  add_subdirectory (C++_MPEG4_SP_Decoder_Serial)
endif(BUILD_Cpp_MPEG4_SERIAL)

