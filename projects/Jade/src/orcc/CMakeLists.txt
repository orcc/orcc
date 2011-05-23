# Common packages required
find_package(SDL REQUIRED)
find_package(SDL_image REQUIRED)

# Common include directories
include_directories(${SDL_INCLUDE_DIR} ${SDLIMAGE_INCLUDE_DIR})

# Orcc library
include_directories(${orcc_SOURCE_DIR}/orcc/include)

add_library (orcc
${orcc_SOURCE_DIR}/orcc/src/compareyuv.c
${orcc_SOURCE_DIR}/orcc/src/display.c
${orcc_SOURCE_DIR}/orcc/src/getopt.c
${orcc_SOURCE_DIR}/orcc/src/img_display.c
${orcc_SOURCE_DIR}/orcc/src/img_read.c
#${orcc_SOURCE_DIR}/orcc/src/scheduler.c
${orcc_SOURCE_DIR}/orcc/src/source.c
${orcc_SOURCE_DIR}/orcc/src/writer.c
${orcc_SOURCE_DIR}/orcc/src/source_no_loop.c
${orcc_SOURCE_DIR}/orcc/src/orcc_util.c
${orcc_SOURCE_DIR}/orcc/src/thread.c
${orcc_SOURCE_DIR}/orcc/src/genetic.c
)