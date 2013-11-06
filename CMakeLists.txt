cmake_minimum_required (VERSION 2.8)

project (orcc-map)

# Output folder
set(EXECUTABLE_OUTPUT_PATH ${CMAKE_SOURCE_DIR}/bin)

set(CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake/Modules/)

find_package(METIS REQUIRED)

include_directories(
    libs/roxml/include
)

# Compile required libs
add_subdirectory(libs)
# Compile application
add_subdirectory(src)
