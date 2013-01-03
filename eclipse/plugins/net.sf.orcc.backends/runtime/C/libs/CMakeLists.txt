cmake_minimum_required (VERSION 2.6)

# Helps cmake to find where SDL libraries are saved (win32 only)
if(WIN32)
	set(ENV{CMAKE_PREFIX_PATH} ${CMAKE_CURRENT_SOURCE_DIR}/windows/SDL-*\;${CMAKE_CURRENT_SOURCE_DIR}/windows/SDL_image-*)
endif(WIN32)

# Common packages required
find_package(SDL REQUIRED)
find_package(SDL_image REQUIRED)
find_package(Threads REQUIRED)

# Common include directories
include_directories(
	${SDL_INCLUDE_DIR}
	${SDLIMAGE_INCLUDE_DIR}
	${THREADS_INCLUDE_DIR}
	orcc/include
	roxml/include
)

file ( GLOB orcc_SRCS orcc/src/*.c )
add_library (orcc STATIC ${orcc_SRCS})

file ( GLOB roxml_SRCS roxml/src/*.c )
add_library (roxml STATIC ${roxml_SRCS})

