
set(libfilenames
    ../include/orccmap.h
    mapping.h
    mapping.c
)

set(exefilenames
    orcc-map.c
)

# Compile library
add_library (orccmap ${libfilenames})
target_link_libraries(orccmap roxml metis m)

# Compile executable
add_executable(orcc-map ${exefilenames})
target_link_libraries(orcc-map orccmap roxml)

