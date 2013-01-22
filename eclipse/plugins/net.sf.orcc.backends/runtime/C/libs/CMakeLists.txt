cmake_minimum_required (VERSION 2.6)

# Helps cmake to find where SDL libraries are saved (win32 only)
if(WIN32)
	set(ENV{CMAKE_PREFIX_PATH} ${CMAKE_CURRENT_SOURCE_DIR}/windows/SDL-*\;${CMAKE_CURRENT_SOURCE_DIR}/windows/SDL_image-*)
endif(WIN32)


# Common include directories
include_directories(
	orcc/include
)

# Orcc library main files
set(orcc_SRCS
	orcc/src/accessFile.c
	orcc/src/getopt.c
	orcc/src/source.c
	orcc/src/writer.c
	orcc/src/orcc_util.c
)

# Build library without any external library required (SDL, pthread, etc)
if(NO_EXTERNAL_DEPENDENCIES)

	add_library (orcc STATIC ${orcc_SRCS})

# Build library with all features (require external libraries)
else(NO_EXTERNAL_DEPENDENCIES)

	# Common packages required
	find_package(SDL REQUIRED)
	find_package(SDL_image REQUIRED)
	find_package(Threads REQUIRED)

	include_directories(
		${SDL_INCLUDE_DIR}
		${SDLIMAGE_INCLUDE_DIR}
		${THREADS_INCLUDE_DIR}
		roxml/include
	)

	file ( GLOB roxml_SRCS roxml/src/*.c )
	add_library (roxml STATIC ${roxml_SRCS})

	list(APPEND orcc_SRCS
		orcc/src/compare.c
		orcc/src/compareyuv.c
		orcc/src/scheduler.c
		orcc/src/display.c
		orcc/src/fpsPrint.c
		orcc/src/genetic.c
		orcc/src/thread.c
	)

	add_library (orcc STATIC ${orcc_SRCS})
	target_link_libraries(orcc roxml ${CMAKE_THREAD_LIBS_INIT} ${SDL_LIBRARY})

endif(NO_EXTERNAL_DEPENDENCIES)
