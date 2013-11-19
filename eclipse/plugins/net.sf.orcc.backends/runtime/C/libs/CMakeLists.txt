cmake_minimum_required (VERSION 2.6)

project (orcc-lib)

set(CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake/Modules/)

include_directories(orcc/include)

# Build library without any external library required (SDL, pthread, etc)
if(NO_EXTERNAL_DEPENDENCIES)

# Build library with all features (require external libraries)
else()

        include_directories(roxml/include)
        add_subdirectory(roxml)
        add_subdirectory(map)

endif()

add_subdirectory(orcc)
