cmake_minimum_required (VERSION 2.6)

# Orcc library files
set(orcc_sources
	orcc/src/access_file.c
	orcc/src/getopt.c
	orcc/src/source.c
	orcc/src/writer.c
	orcc/src/util.c
	orcc/include/util.h
	orcc/include/types.h
)

# Build library without any external library required (SDL, pthread, etc)
if(NO_EXTERNAL_DEPENDENCIES)

	add_library(orcc STATIC ${orcc_sources})

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
	
	# To enable usage of SSE instructions, simply add a folder named "openHevc" or "libOpenHevc"
	# directly in the libs folder. This folder must contain the *.lib file corresponding to libLibOpenHevcWrapper.dll
	# You can put files under lib/x86 or lib/x64 according to the architecture.
	# The OPEN_HEVC_ENABLE constant will be set automatically, to activate usage of these SSE instructions.
    find_library(OpenHevc libLibOpenHevcWrapper PATHS ${CMAKE_CURRENT_SOURCE_DIR}/openHevc ${CMAKE_CURRENT_SOURCE_DIR}/libOpenHevc)
    if(OpenHevc)
        add_definitions(-DOPEN_HEVC_ENABLE)
    endif()

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
			orcc/src/mapping.c
			orcc/src/display.c
			orcc/src/audio.c
			orcc/src/fps_print.c
			orcc/src/genetic.c
			orcc/src/thread.c
			orcc/src/open_hevc.c
			orcc/src/serialize.c
			# Headers
			orcc/include/fifo.h
			orcc/include/generic_fifo.h
			orcc/include/genetic.h
			orcc/include/scheduler.h
			orcc/include/mapping.h
			orcc/include/thread.h
			orcc/include/open_hevc.h
			orcc/include/serialize.h
		)

		# Compile libraries
		add_library(roxml STATIC ${roxml_sources})
		add_library(orcc STATIC ${orcc_sources})

		# Do the linking
        target_link_libraries(orcc roxml ${SDL_LIBRARY})
        if(OpenHevc)
            target_link_libraries(orcc ${OpenHevc})
        endif()
	endif()
endif()
