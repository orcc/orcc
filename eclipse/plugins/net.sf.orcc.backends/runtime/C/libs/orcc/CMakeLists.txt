
# Orcc library files
set(orcc_sources
    src/access_file.c
    src/dataflow.c
    src/getopt.c
    src/graph.c
    src/options.c
    src/scheduler.c
    src/source.c
    src/trace.c
    src/util.c
    src/writer.c
    include/util.h
    include/types.h
)

# Build library without any external library required (SDL, pthread, etc)
if(NO_EXTERNAL_DEPENDENCIES)

    add_library(orcc STATIC ${orcc_sources})

# Build library with all features (require external libraries)
else()

    file(GLOB orcc_headers
        include/*.h
    )

    set(orcc_sources
        ${orcc_sources}
        src/audio.c
        src/compare.c
        src/compareyuv.c
        src/fps_print.c
        src/mapping.c
        src/serialize.c
        src/thread.c
    )
    set(orcc_additional_libraries)

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
            ${CMAKE_CURRENT_SOURCE_DIR}/../windows/SDL-*
        )
    endif()

    # Try to find SDL on the user system
    find_package(SDL2 QUIET)
    if(SDL2_FOUND)
        include_directories(${SDL2_INCLUDE_DIR})
        list(APPEND orcc_sources src/display_sdl2.c)
        list(APPEND orcc_additional_libraries ${SDL2_LIBRARY})
    else()
        find_package(SDL REQUIRED)
        if(SDL_FOUND)
            include_directories(${SDL_INCLUDE_DIR})
            list(APPEND orcc_sources src/display_sdl.c)
            list(APPEND orcc_additional_libraries ${SDL_LIBRARY})
        endif()
    endif()

    option(USE_SSE "Use SSE native functions if available" 0)
    if(USE_SSE)
        # Define SSE_ENABLE constant for the whole project to avoid
        # its redefinition in the CMakeLists.txt of generated applications
        add_definitions(-DSSE_ENABLE)
        set_property(DIRECTORY ${CMAKE_SOURCE_DIR} PROPERTY COMPILE_DEFINITIONS SSE_ENABLE)

        list(APPEND orcc_sources src/sse.c)

        find_package(OPENHEVC QUIET)
        if(OPENHEVC_FOUND)
            # Define OPEN_HEVC_ENABLE constant for the whole project
            add_definitions(-DOPENHEVC_ENABLE)
            set_property(DIRECTORY ${CMAKE_SOURCE_DIR} PROPERTY COMPILE_DEFINITIONS OPENHEVC_ENABLE APPEND)

            include_directories(${OPENHEVC_INCLUDE_DIR})
            list(APPEND orcc_sources src/openhevc.c)
            list(APPEND orcc_additional_libraries ${OPENHEVC_LIBRARY})
            message(STATUS "Find OpenHEVC: ${OPENHEVC_LIBRARY}")
        else()
            message(STATUS "Cannot find OpenHEVC")
        endif()
    endif()

    option(USE_METIS "Use the Metis library for the actor mapping" 0)
    if(USE_METIS)
        find_package(METIS REQUIRED)
        if(METIS_FOUND)
            # Defines METIS_ENABLE constant for the current directory
            add_definitions(-DMETIS_ENABLE)

            list(APPEND orcc_additional_libraries ${METIS_LIBRARY})
            message(STATUS "Find Metis: ${METIS_LIBRARY}")
        else()
            message(STATUS "Cannot find Metis")
        endif()
    endif()

    # Compile libraries
    add_library(orcc STATIC ${orcc_sources} ${orcc_headers})
    # Do the linking
    target_link_libraries(orcc roxml ${orcc_additional_libraries})

    # Build orcc-map as a standalone exectuable
    set(orccmap_sources
        include/serialize.h
        src/serialize.c
        src/main.c
    )
    add_executable(orcc-map ${orccmap_sources})
    target_link_libraries(orcc-map orcc roxml)

    INSTALL(TARGETS orcc-map orcc
        RUNTIME DESTINATION bin
        LIBRARY DESTINATION lib
        ARCHIVE DESTINATION lib
    )
endif()
