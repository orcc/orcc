
# Build library without any external library required (SDL, pthread, etc)
if(NO_EXTERNAL_DEPENDENCIES)

        # Orcc library files
#include "orccmap.h"

        set(orcc_sources
                src/access_file.c
                src/getopt.c
                src/source.c
                src/writer.c
                src/util.c
                src/mapping.c
                src/trace.c
                src/options.c
                src/graph.c
                include/util.h
                include/types.h
        )

        add_library(orcc STATIC ${orcc_sources})

# Build library with all features (require external libraries)
else()

#include "orccmap.h"

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
                )


                file(GLOB orcc_sources
                    src/*.c
                    include/*.h
                )

                find_package(METIS REQUIRED)

                # Compile libraries
                add_library(orcc STATIC ${orcc_sources})

                # Do the linking
                target_link_libraries(orcc roxml ${SDL_LIBRARY} metis m)
                if(OpenHevc)
                    target_link_libraries(orcc ${OpenHevc})
                endif()

                set(executable_filenames
                    include/serialize.h
                    src/serialize.c
                    src/main.c
                )

                # Compile executable
                add_executable(orcc-map ${executable_filenames})
                target_link_libraries(orcc-map orcc roxml)

                INSTALL(TARGETS orcc-map orcc
                  RUNTIME DESTINATION bin
                  LIBRARY DESTINATION lib
                  ARCHIVE DESTINATION lib
                )
        endif()

endif()
