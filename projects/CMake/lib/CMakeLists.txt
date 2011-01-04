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
include_directories(${orcc_SOURCE_DIR}/include)
add_library (orcc
${orcc_SOURCE_DIR}/src/cJSON.c

${orcc_SOURCE_DIR}/src/compareyuv.c
${orcc_SOURCE_DIR}/src/display.c
${orcc_SOURCE_DIR}/src/getopt.c
${orcc_SOURCE_DIR}/src/img_display.c
${orcc_SOURCE_DIR}/src/img_read.c
${orcc_SOURCE_DIR}/src/scheduler.c
${orcc_SOURCE_DIR}/src/source.c
${orcc_SOURCE_DIR}/src/writer.c
${orcc_SOURCE_DIR}/src/source_no_loop.c
${orcc_SOURCE_DIR}/src/orcc_util.c
${orcc_SOURCE_DIR}/src/thread.c
)

install (TARGETS orcc DESTINATION lib)
install(DIRECTORY include/ DESTINATION include/orcc PATTERN ".svn" EXCLUDE)
