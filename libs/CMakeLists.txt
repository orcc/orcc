cmake_minimum_required (VERSION 2.6)

include_directories(
	roxml/include
)

file ( GLOB roxml_SRCS roxml/src/*.c )
add_library (roxml STATIC ${roxml_SRCS})

target_link_libraries(roxml)
