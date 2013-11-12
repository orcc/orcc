
set(library_filenames
    ../include/orccmap.h
    mapping.c
    util.c
    options.c
)

set(executable_filenames
    serialize.c
    main.c
)

# Compile library
add_library(orccmap ${library_filenames})
target_link_libraries(orccmap metis m)

# Compile executable
add_executable(orcc-map ${executable_filenames})
target_link_libraries(orcc-map orccmap roxml)

