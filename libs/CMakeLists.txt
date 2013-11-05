cmake_minimum_required (VERSION 2.6)

# Initialize source files list for roxml
file(GLOB roxml_sources
    roxml/src/*.c
    roxml/include/*.h
)

# Compile libraries
add_library (roxml STATIC ${roxml_sources})

