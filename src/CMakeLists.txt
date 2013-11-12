
set(libfilenames
    ../include/orccmap.h
    mapping.h
    mapping.c
    util.c
    util.h
    options.c
    options.h
)

set(exefilenames
    serialize.h
    serialize.c
    main.c
)

# Compile library
add_library(orccmap ${libfilenames})
target_link_libraries(orccmap metis m)

# Compile executable
add_executable(orcc-map ${exefilenames})
target_link_libraries(orcc-map orccmap roxml)

