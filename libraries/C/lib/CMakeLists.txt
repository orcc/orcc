# The name of our project is "Orcc". CMakeLists files in this project can
# refer to the root source directory of the project as ${orcc_SOURCE_DIR} and
# to the root binary directory of the project as ${orcc_BINARY_DIR}.
cmake_minimum_required (VERSION 2.8)
project (orcc)

# Common packages required
find_package(SDL REQUIRED)
find_package(SDL_image REQUIRED)

# Common include directories
include_directories(${SDL_INCLUDE_DIR} ${SDLIMAGE_INCLUDE_DIR})

# Orcc library
include_directories(${orcc_SOURCE_DIR}/orcc/include)

add_library (orcc
${orcc_SOURCE_DIR}/orcc/src/compare.c
${orcc_SOURCE_DIR}/orcc/src/compareyuv.c
${orcc_SOURCE_DIR}/orcc/src/display.c
${orcc_SOURCE_DIR}/orcc/src/getopt.c
${orcc_SOURCE_DIR}/orcc/src/img_display.c
${orcc_SOURCE_DIR}/orcc/src/img_read.c
${orcc_SOURCE_DIR}/orcc/src/scheduler.c
${orcc_SOURCE_DIR}/orcc/src/source.c
${orcc_SOURCE_DIR}/orcc/src/writer.c
${orcc_SOURCE_DIR}/orcc/src/source_no_loop.c
${orcc_SOURCE_DIR}/orcc/src/orcc_util.c
${orcc_SOURCE_DIR}/orcc/src/thread.c
${orcc_SOURCE_DIR}/orcc/src/genetic.c
${orcc_SOURCE_DIR}/orcc/src/accessFile.c
${orcc_SOURCE_DIR}/orcc/src/fpsPrint.c
)

# Roxml library
include_directories(${orcc_SOURCE_DIR}/roxml/include)

add_library(roxml
${orcc_SOURCE_DIR}/roxml/src/roxml.c
${orcc_SOURCE_DIR}/roxml/src/roxml-internal.c
${orcc_SOURCE_DIR}/roxml/src/roxml-parse-engine.c
${orcc_SOURCE_DIR}/roxml/src/roxml-parser.c
)

# Installation
install (TARGETS orcc DESTINATION lib)
install (TARGETS roxml DESTINATION lib)
install(DIRECTORY orcc/include/ DESTINATION include/orcc PATTERN ".svn" EXCLUDE)
install (FILES roxml/include/roxml.h DESTINATION include/roxml)
