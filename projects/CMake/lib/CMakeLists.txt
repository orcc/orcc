# The name of our project is "Orcc". CMakeLists files in this project can
# refer to the root source directory of the project as ${Orcc_SOURCE_DIR} and
# to the root binary directory of the project as ${Orcc_BINARY_DIR}.
cmake_minimum_required (VERSION 2.8)
project (orcc)

# Common packages required
find_package(SDL REQUIRED)
find_package(SDL_image REQUIRED)

# Common include directories
include_directories(${SDL_INCLUDE_DIR} ${SDLIMAGE_INCLUDE_DIR})

# Orcc library
include_directories(${Orcc_SOURCE_DIR}/include)
add_library (orcc
${Orcc_SOURCE_DIR}/src/cJSON.c

${Orcc_SOURCE_DIR}/src/compareyuv.c
${Orcc_SOURCE_DIR}/src/display.c
${Orcc_SOURCE_DIR}/src/getopt.c
${Orcc_SOURCE_DIR}/src/img_display.c
${Orcc_SOURCE_DIR}/src/img_read.c
${Orcc_SOURCE_DIR}/src/sink.c
${Orcc_SOURCE_DIR}/src/source.c
${Orcc_SOURCE_DIR}/src/orcc_util.c

${Orcc_SOURCE_DIR}/src/Actor_img_display.cpp
${Orcc_SOURCE_DIR}/src/Actor_img_read.cpp
${Orcc_SOURCE_DIR}/src/Actor_display.cpp
${Orcc_SOURCE_DIR}/src/Actor_source.cpp
${Orcc_SOURCE_DIR}/src/actoraka.cpp
${Orcc_SOURCE_DIR}/src/fifoFanOut.cpp
${Orcc_SOURCE_DIR}/src/traceaka.cpp

)

install (TARGETS orcc DESTINATION lib)
install (FILES include/fifo.h DESTINATION include)
