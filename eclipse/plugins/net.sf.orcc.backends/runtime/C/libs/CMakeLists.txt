cmake_minimum_required (VERSION 2.6)

# Orcc library files
set(orcc_sources
	orcc/src/accessFile.c
	orcc/src/getopt.c
	orcc/src/source.c
	orcc/src/writer.c
	orcc/src/orcc_util.c
	orcc/include/orcc_util.h
	orcc/include/orcc_types.h
)

# Build library without any external library required (SDL, pthread, etc)
if(NO_EXTERNAL_DEPENDENCIES)

	add_library (orcc STATIC ${orcc_sources})

# Build library with all features (require external libraries)
else()
	# Initialize source files list for roxml
	file(GLOB roxml_sources
		roxml/src/*.c
		roxml/include/*.h
	)

	# Helps CMake to find SDL libraries (Windows only)
	if(WIN32)
		# Detect current architecture. Depending on CMAKE_LIBRARY_ARCHITECTURE value,
		# CMake will search in lib/x64 or lib/x86 sub-folders to find SDL.lib
		if(CMAKE_SIZEOF_VOID_P EQUAL 8)
			set(CMAKE_LIBRARY_ARCHITECTURE "x64")
		else()
			set(CMAKE_LIBRARY_ARCHITECTURE "x86")
		endif()
		message(STATUS "Architecture: ${CMAKE_LIBRARY_ARCHITECTURE}")

		# Configure additional paths to search for libraries
		set(CMAKE_PREFIX_PATH
			${CMAKE_PREFIX_PATH}
			${CMAKE_CURRENT_SOURCE_DIR}/windows/SDL-*
		)
	endif()

	# Try to find SDL on the user system
	find_package(SDL REQUIRED)

	if(SDL_FOUND)
		include_directories(
			${SDL_INCLUDE_DIR}
			roxml/include
		)
		# Add orcc files which depend on SDL and roxml
		list(APPEND orcc_sources
			# Source files
			orcc/src/compare.c
			orcc/src/compareyuv.c
			orcc/src/scheduler.c
			orcc/src/display.c
			orcc/src/audio.c
			orcc/src/fpsPrint.c
			orcc/src/genetic.c
			orcc/src/thread.c
			# Headers
			orcc/include/orcc_fifo.h
			orcc/include/orcc_fifo_socket.h
			orcc/include/orcc_generic_fifo.h
			orcc/include/orcc_generic_fifo.inl
			orcc/include/orcc_generic_fifo_socket.h
			orcc/include/orcc_genetic.h
			orcc/include/orcc_scheduler.h
			orcc/include/orcc_scheduler.inl
			orcc/include/orcc_thread.h
			orcc/include/socket.h
		)

		# Compile libraries
		add_library(roxml STATIC ${roxml_sources})
		add_library (orcc STATIC ${orcc_sources})

		# Do the linking
		target_link_libraries(orcc roxml ${SDL_LIBRARY})
	endif()
endif()
