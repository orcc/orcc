# The name of our project is "Orcc". CMakeLists files in this project can
# refer to the root source directory of the project as ${orcc_SOURCE_DIR} and
# to the root binary directory of the project as ${orcc_BINARY_DIR}.
cmake_minimum_required (VERSION 2.8)
project (orcc)

# Common packages required
# find_package(SDL REQUIRED)
# find_package(SDL_image REQUIRED)

# Common include directories
# include_directories(${SDL_INCLUDE_DIR} ${SDLIMAGE_INCLUDE_DIR})

# Orcc library
include_directories(${orcc_SOURCE_DIR}/include ${orcc_SOURCE_DIR}/../../../CMake/lib/include)
add_executable (scheduler
${orcc_SOURCE_DIR}/src/scheduler.c
${orcc_SOURCE_DIR}/src/testbed.c
${orcc_SOURCE_DIR}/src/scheduler2_v1.c
${orcc_SOURCE_DIR}/src/scheduler_v0.c
${orcc_SOURCE_DIR}/src/scheduler_v1.c
${orcc_SOURCE_DIR}/src/scheduler_v2.c
${orcc_SOURCE_DIR}/src/scheduler_v3.c
${orcc_SOURCE_DIR}/src/scheduler_v4.c
${orcc_SOURCE_DIR}/src/scheduler_v5.c
${orcc_SOURCE_DIR}/src/scheduler_v6.c
${orcc_SOURCE_DIR}/src/scheduler_vSDF.c
)
