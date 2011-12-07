
# Common include directories
include_directories(${SDL_INCLUDE_DIR} ${SDLIMAGE_INCLUDE_DIR})

# Orcc library
include_directories(orcc/include)

add_library (orcc
orcc/src/compare.c
orcc/src/compareyuv.c
orcc/src/display.c
orcc/src/getopt.c
# orcc/src/img_display.c
# orcc/src/img_read.c
orcc/src/source.c
orcc/src/writer.c
orcc/src/source_no_loop.c
orcc/src/orcc_util.c
orcc/src/thread.c
orcc/src/genetic.c
orcc/src/accessFile.c
orcc/src/fpsPrint.c
)

