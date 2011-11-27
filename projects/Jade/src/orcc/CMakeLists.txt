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
${orcc_SOURCE_DIR}/orcc/src/source.c
${orcc_SOURCE_DIR}/orcc/src/writer.c
${orcc_SOURCE_DIR}/orcc/src/source_no_loop.c
${orcc_SOURCE_DIR}/orcc/src/orcc_util.c
${orcc_SOURCE_DIR}/orcc/src/thread.c
${orcc_SOURCE_DIR}/orcc/src/genetic.c
${orcc_SOURCE_DIR}/orcc/src/accessFile.c
${orcc_SOURCE_DIR}/orcc/src/fpsPrint.c
)

