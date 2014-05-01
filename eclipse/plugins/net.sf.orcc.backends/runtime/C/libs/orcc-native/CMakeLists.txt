
# Orcc library files
set(orcc_native_sources
    src/access_file.c
    src/source.c
    src/writer.c
    src/native.c
    include/native.h
)

# Build library with all features (require SDL)
if(NOT NO_EXTERNAL_DEPENDENCIES)

    file(GLOB orcc_native_headers
        include/*.h
    )

    list(APPEND orcc_native_sources
        src/audio.c
        src/compare.c
        src/compareyuv.c
        src/fps_print.c
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
        list(APPEND extra_includes ${SDL2_INCLUDE_DIR})
        list(APPEND extra_libraries ${SDL2_LIBRARY})
        list(APPEND orcc_native_sources src/display_sdl2.c)
    else()
        find_package(SDL REQUIRED)
        if(SDL_FOUND)
            list(APPEND extra_includes ${SDL_INCLUDE_DIR})
            list(APPEND extra_libraries ${SDL_LIBRARY})
            list(APPEND orcc_native_sources src/display_sdl.c)
        endif()
    endif()

    option(USE_SSE "Use SSE native functions if available" 0)
    if(USE_SSE)
        list(APPEND extra_definitions -DSSE_ENABLE)
        list(APPEND orcc_native_sources
            src/sse.c
            src/hevc_idct_sse4.c
            src/hevc_intra_pred_sse.c
            src/hevc_mc_sse.c
        )

        find_package(OPENHEVC QUIET)
        if(OPENHEVC_FOUND)
            list(APPEND extra_definitions -DOPENHEVC_ENABLE)
            list(APPEND orcc_native_sources src/openhevc.c)
            list(APPEND extra_libraries ${OPENHEVC_LIBRARY})
            list(APPEND extra_includes ${OPENHEVC_INCLUDE_DIR})
            message(STATUS "Find OpenHEVC: ${OPENHEVC_LIBRARY}")
        else()
            message(STATUS "Cannot find OpenHEVC")
        endif()
    endif()

endif()

include_directories(${extra_includes})
add_definitions(${extra_definitions})
# Compile libraries
add_library(orcc-native STATIC ${orcc_native_sources} ${orcc_native_headers})
# Do the linking
target_link_libraries(orcc-native orcc-runtime ${extra_libraries})

install(TARGETS orcc-native
    ARCHIVE DESTINATION lib
)

set(extra_definitions ${extra_definitions} PARENT_SCOPE)
set(extra_includes ${extra_includes} PARENT_SCOPE)
