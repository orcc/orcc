cmake_minimum_required (VERSION 2.6)

project (orcc-lib)

set(CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR}/cmake/Modules/)

include_directories(orcc/include)

if(NOT NO_EXTERNAL_DEPENDENCIES)
    include_directories(roxml/include)
    add_subdirectory(roxml)
endif()

add_subdirectory(orcc)

set(extra_definitions ${extra_definitions} PARENT_SCOPE)
set(extra_includes ${extra_includes} PARENT_SCOPE)
