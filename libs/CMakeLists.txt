cmake_minimum_required (VERSION 2.6)

link_directories(${CMAKE_CURRENT_SOURCE_DIR}/metis/lib)

file ( GLOB roxml_SRCS roxml/src/*.c )
add_library (roxml STATIC ${roxml_SRCS})

add_custom_target(metis_h ALL SOURCES metis/include/metis.h)


