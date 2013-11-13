
set(library_filenames
    ../include/orccmap.h
    mapping.c
    util.c
    options.c
    dataflow.c
    graph.c
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

INSTALL(TARGETS orcc-map orccmap
  RUNTIME DESTINATION bin
  LIBRARY DESTINATION lib
  ARCHIVE DESTINATION lib
)

