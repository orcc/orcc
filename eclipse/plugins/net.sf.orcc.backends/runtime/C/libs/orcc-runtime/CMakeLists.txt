
# Orcc library files
set(orcc_runtime_sources
    src/dataflow.c
    src/getopt.c
    src/graph.c
    src/options.c
    src/scheduler.c
    src/trace.c
    src/util.c
    include/util.h
    include/types.h
)

# Build library with all features (require thread, etc)
if(NOT NO_EXTERNAL_DEPENDENCIES)

    file(GLOB orcc_runtime_headers
        include/*.h
    )

    list(APPEND orcc_runtime_sources
        src/mapping.c
        src/serialize.c
        src/thread.c
        src/profiling.c
    )

    find_package(Threads REQUIRED)
    list(APPEND extra_libraries ${CMAKE_THREAD_LIBS_INIT})
    list(APPEND extra_libraries roxml)

    find_package(METIS QUIET)
    if(METIS_FOUND)
        list(APPEND extra_definitions -DMETIS_ENABLE)
        list(APPEND extra_libraries ${METIS_LIBRARY})
        list(APPEND extra_includes ${METIS_INCLUDE_DIR})
        message(STATUS "Find Metis: ${METIS_LIBRARY}")
    else()
        message(STATUS "Cannot find Metis")
    endif()

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
