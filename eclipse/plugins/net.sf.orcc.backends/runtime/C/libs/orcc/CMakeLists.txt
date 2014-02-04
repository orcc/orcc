
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
        src/open_hevc.c
        src/serialize.c
        src/thread.c
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
            ${CMAKE_CURRENT_SOURCE_DIR}/../windows/SDL-*
        )
    endif()

    # Try to find SDL on the user system
    find_package(SDL2 QUIET)
    if(SDL2_FOUND)
        include_directories(
            ${SDL2_INCLUDE_DIR}
        )
        set(orcc_sources
            ${orcc_sources}
            src/display_sdl2.c
        )
    else()
        find_package(SDL REQUIRED)
        if(SDL_FOUND)
            include_directories(
                ${SDL_INCLUDE_DIR}
            )
            set(orcc_sources
                ${orcc_sources}
                src/display_sdl.c
            )
        endif()
    endif()

    # To enable usage of SSE instructions, simply add a folder named "openHevc" or "libOpenHevc"
    # directly in the libs folder. This folder must contain the *.lib file corresponding to libLibOpenHevcWrapper.dll
    # You can put files under lib/x86 or lib/x64 according to the architecture.
    # The OPEN_HEVC_ENABLE constant will be set automatically, to activate usage of these SSE instructions.
    find_library(OpenHevc libLibOpenHevcWrapper PATHS ${CMAKE_CURRENT_SOURCE_DIR}/openHevc ${CMAKE_CURRENT_SOURCE_DIR}/libOpenHevc)
    if(OpenHevc)
        add_definitions(-DOPEN_HEVC_ENABLE)
    endif()

    find_package(METIS QUIET)
    if(METIS_FOUND)
        add_definitions(-DMETIS_ENABLE)
    endif()

    # Compile libraries
    add_library(orcc STATIC ${orcc_sources} ${orcc_headers})

    # Do the linking
    if(SDL2_FOUND)
        target_link_libraries(orcc roxml ${SDL2_LIBRARY})
    elseif(SDL_FOUND)
        target_link_libraries(orcc roxml ${SDL_LIBRARY})
    endif()
    if(OpenHevc)
        target_link_libraries(orcc ${OpenHevc})
    endif()
    if(METIS_FOUND)
        target_link_libraries(orcc metis m)
    endif()

    set(orccmap_sources
        include/serialize.h
        src/serialize.c
        src/main.c
    )

    # Compile executable
    add_executable(orcc-map ${orccmap_sources})
    target_link_libraries(orcc-map orcc roxml)

    INSTALL(TARGETS orcc-map orcc
        RUNTIME DESTINATION bin
        LIBRARY DESTINATION lib
        ARCHIVE DESTINATION lib
    )

endif()
