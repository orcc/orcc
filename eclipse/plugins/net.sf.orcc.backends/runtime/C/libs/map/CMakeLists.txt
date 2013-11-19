cmake_minimum_required (VERSION 2.8)

find_package(METIS REQUIRED)

set(library_filenames
    include/orccmap.h
    include/mapping.h
    include/util.h
    include/options.h
    include/dataflow.h
    include/graph.h
    src/mapping.c
    src/util.c
    src/options.c
    src/dataflow.c
    src/graph.c
)

set(executable_filenames
    include/serialize.h
    src/serialize.c
    src/main.c
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
