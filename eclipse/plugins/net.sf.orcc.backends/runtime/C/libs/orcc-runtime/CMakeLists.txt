
# Orcc library files
set(orcc_runtime_sources
    src/dataflow.c
    src/graph.c
    src/options.c
    src/scheduler.c
    src/trace.c
    src/util.c
    src/mapping.c
    src/profiling.c
    src/getopt.c
)

set(orcc_runtime_headers
    include/dataflow.h
    include/graph.h
    include/options.h
    include/scheduler.h
    include/trace.h
    include/fifo.h
    include/generic_fifo.h
    include/util.h
    include/mapping.h
    include/profiling.h
    include/types.h
    include/orcc.h
)

if(ROXML_ENABLE)
    list(APPEND orcc_runtime_sources src/serialize.c)
    list(APPEND orcc_runtime_headers include/serialize.h)
endif()

find_package(METIS QUIET)
if(METIS_FOUND)
    list(APPEND extra_definitions -DMETIS_ENABLE)
    list(APPEND extra_libraries ${METIS_LIBRARY})
    list(APPEND extra_includes ${METIS_INCLUDE_DIR})
    message(STATUS "Find Metis: ${METIS_LIBRARY}")
else()
    message(STATUS "Cannot find Metis")
endif()


include_directories(${extra_includes})
add_definitions(${extra_definitions})
# Compile libraries
add_library(orcc-runtime STATIC ${orcc_runtime_sources} ${orcc_runtime_headers})
# Do the linking
target_link_libraries(orcc-runtime orcc-native ${extra_libraries})

install(TARGETS orcc-runtime
    ARCHIVE DESTINATION lib
)

set(extra_definitions ${extra_definitions} PARENT_SCOPE)
set(extra_includes ${extra_includes} PARENT_SCOPE)
set(extra_libraries ${extra_libraries} PARENT_SCOPE)
