cmake_minimum_required (VERSION 2.6)

project (orcc-lib)

set(CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR}/cmake/Modules/)

include_directories(orcc/include)

if(NO_EXTERNAL_DEPENDENCIES)

else() # Build library without any external library required (SDL, pthread, etc)
        include_directories(roxml/include)
        add_subdirectory(roxml)

endif()

add_subdirectory(orcc)
