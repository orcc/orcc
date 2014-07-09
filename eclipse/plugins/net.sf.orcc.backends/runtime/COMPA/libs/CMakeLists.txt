cmake_minimum_required (VERSION 2.6)

project (orcc-lib)

#set(CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR}/cmake/Modules/)

include_directories(orcc/include)

add_subdirectory(orcc)
