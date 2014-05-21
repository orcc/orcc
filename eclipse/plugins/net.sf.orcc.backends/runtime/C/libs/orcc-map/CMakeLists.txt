
# Build orcc-map as a standalone exectuable

add_definitions(${extra_definitions})

add_executable(orcc-map src/main.c)
target_link_libraries(orcc-map orcc-runtime)

install(TARGETS orcc-map
    RUNTIME DESTINATION bin
)
