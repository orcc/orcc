
# Initialize source files list for roxml
file(GLOB roxml_sources
    src/*.c
    include/*.h
)

# Compile libraries
add_library (roxml STATIC ${roxml_sources})
