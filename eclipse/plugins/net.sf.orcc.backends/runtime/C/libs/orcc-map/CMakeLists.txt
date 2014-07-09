
# Build orcc-map as a standalone exectuable

add_definitions(${extra_definitions})
include_directories(${extra_includes})

add_executable(orcc-map src/main.c)
target_link_libraries(orcc-map orcc-runtime ${extra_libraries})

install(TARGETS orcc-map
    RUNTIME DESTINATION bin
)
