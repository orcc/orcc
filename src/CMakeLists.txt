# Generated from Top_decoder_serial

set(filenames
        mapping.c
        orccmap.c
)

add_executable(orccmap ${filenames})
target_link_libraries(orccmap roxml metis m)

