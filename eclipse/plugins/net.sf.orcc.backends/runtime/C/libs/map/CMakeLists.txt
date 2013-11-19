cmake_minimum_required (VERSION 2.8)

project (orcc-map)

# Output folder

find_package(METIS REQUIRED)

include_directories(
    include
)

# Compile application
add_subdirectory(src)
