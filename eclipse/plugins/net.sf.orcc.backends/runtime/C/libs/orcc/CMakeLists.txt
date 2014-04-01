
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
        src/profiling.c
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
        list(APPEND additional_definitions -DSSE_ENABLE)
        list(APPEND orcc_sources
            src/sse.c
            src/hevc_idct_sse4.c
            src/hevc_intra_pred_sse.c
            src/hevc_mc_sse.c
        )

        find_package(OPENHEVC QUIET)
        if(OPENHEVC_FOUND)
            list(APPEND additional_definitions -DOPENHEVC_ENABLE)
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
            list(APPEND additional_definitions -DMETIS_ENABLE)
            list(APPEND orcc_additional_libraries ${METIS_LIBRARY})
            message(STATUS "Find Metis: ${METIS_LIBRARY}")
        else()
            message(STATUS "Cannot find Metis")
        endif()
    endif()

    if(NOT MSVC AND NOT CMAKE_CROSSCOMPILING)
        # Optimize the compilation for the local processor architecture
        list(APPEND additional_definitions -march=native)
    endif()

    add_definitions(${additional_definitions})
    # Compile libraries
    add_library(orcc STATIC ${orcc_sources} ${orcc_headers})
    # Do the linking
    target_link_libraries(orcc roxml ${orcc_additional_libraries})

    option(BUILD_ORCCMAP "Build the mapping tool as a standalone executable" 0)
    if(BUILD_ORCCMAP)
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

endif()


set(additional_definitions ${additional_definitions} PARENT_SCOPE)
