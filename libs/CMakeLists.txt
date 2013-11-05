cmake_minimum_required (VERSION 2.6)

file ( GLOB roxml_SRCS roxml/src/*.c )
add_library (roxml STATIC ${roxml_SRCS})

