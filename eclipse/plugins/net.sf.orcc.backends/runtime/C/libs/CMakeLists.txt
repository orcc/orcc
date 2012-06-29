# This project is used by source code produced with Orcc
# c backend. It is designed to build both Roxml and Orcc
# libraries and statically link them.

cmake_minimum_required (VERSION 2.6)

# Common packages required
find_package(SDL REQUIRED)
find_package(SDL_image REQUIRED)
find_package(Threads REQUIRED)

# Common include directories
include_directories(
	${SDL_INCLUDE_DIR}
	${SDLIMAGE_INCLUDE_DIR}
	${THREADS_INCLUDE_DIR}
)

include_directories(${ORCC_INCLUDE_DIR} ${ROXML_INCLUDE_DIR})

file ( GLOB orcc_source_files orcc/src/*.c )
add_library (orcc STATIC ${orcc_source_files})

file ( GLOB roxml_source_files roxml/src/*.c )
add_library (roxml STATIC ${roxml_source_files})

