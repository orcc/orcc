
# Orcc library files
set(orcc_native_sources
    src/access_file.c
    src/source.c
    src/writer.c
    src/compare.c
    src/compareyuv.c
    src/native.c
    src/native_util.c
)

set(orcc_native_headers
    include/native.h
    include/native_util.h
)

if(THREADS_FOUND)
    list(APPEND orcc_native_sources src/thread.c)
    list(APPEND orcc_native_headers include/thread.h)
endif()

# try to find openCV
find_package(OpenCV)

if(OPENCV_FOUND)
    list(APPEND orcc_native_sources src/source_camera.c)
    list(APPEND extra_libraries ${OpenCV_LIBS}) # opencv libs
    list(APPEND extra_libraries m) # maths lib required
else()
    message(STATUS "Cannot find OpenCV")
endif()

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
find_package(SDL QUIET)

if(SDL2_FOUND)
    list(APPEND extra_includes ${SDL2_INCLUDE_DIR})
    list(APPEND extra_libraries ${SDL2_LIBRARY})
    list(APPEND orcc_native_sources
        src/display_sdl2.c
        src/audio_sdl.c
        src/framerate_sdl.c
    )
elseif(SDL_FOUND)
    list(APPEND extra_includes ${SDL_INCLUDE_DIR})
    list(APPEND extra_libraries ${SDL_LIBRARY})
    list(APPEND orcc_native_sources
        src/display_sdl.c
        src/audio_sdl.c
        src/framerate_sdl.c
    )
else()
    list(APPEND orcc_native_sources
        src/nodisplay.c
        src/framerate.c
    )
endif()

find_package(OPENHEVC QUIET)
if(OPENHEVC_FOUND)
    list(APPEND extra_libraries ${OPENHEVC_LIBRARY})
    list(APPEND extra_includes ${OPENHEVC_INCLUDE_DIR})
    message(STATUS "Find OpenHEVC: ${OPENHEVC_LIBRARY}")
else()
    message(STATUS "Cannot find OpenHEVC")
endif()

find_package(FFMPEG QUIET)
if(FFMPEG_FOUND)
    list(APPEND extra_libraries ${FFMPEG_LIBRARIES})
    list(APPEND extra_includes ${FFMPEG_INCLUDE_DIR})
    list(APPEND orcc_native_sources src/ffmpeg_util.c)
    list(APPEND orcc_native_headers include/ffmpeg_util.h)
    message(STATUS "Find FFmpeg: ${FFMPEG_LIBRARIES}")
else()
    message(STATUS "Cannot find FFmpeg")
endif()

find_library(papi_LIBRARY papi)
if(papi_LIBRARY)
    list(APPEND orcc_native_sources src/eventLib.c)
    list(APPEND orcc_native_headers include/eventLib.h)
    list(APPEND extra_libraries ${papi_LIBRARY})
    list(APPEND extra_includes /usr/local/include)
endif()

option(USE_SSE "Use SSE native functions if available" 0)
if(USE_SSE)
    message(STATUS "Enable SSE optimizations")
    list(APPEND extra_definitions -DSSE_ENABLE)
    list(APPEND orcc_native_sources
        src/hevc_sse.c
        src/hevc_idct_sse4.c
        src/hevc_intra_pred_sse.c
        src/hevc_mc_sse.c
    )
    list(APPEND orcc_native_headers include/hevc_sse.h)

    if(OPENHEVC_FOUND)
        list(APPEND extra_definitions -DOPENHEVC_ENABLE)
        list(APPEND orcc_native_sources src/openhevc_sse.c)
    	list(APPEND orcc_native_headers include/openhevc_sse.h)
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
set(extra_libraries ${extra_libraries} PARENT_SCOPE)
