
if(THREADS_FOUND)
    list(APPEND extra_libraries roxml)
    list(APPEND extra_definitions -DROXML_ENABLE)
    set(ROXML_ENABLE 1 PARENT_SCOPE)

    # Initialize source files list for roxml
    file(GLOB roxml_sources
        src/*.c
        include/*.h
    )

    # Compile libraries
    add_library(roxml STATIC ${roxml_sources})

    message(STATUS "Build roxml")
else()
    message(STATUS "Cannot build roxml")
endif()

set(extra_definitions ${extra_definitions} PARENT_SCOPE)
set(extra_includes ${extra_includes} PARENT_SCOPE)
set(extra_libraries ${extra_libraries} PARENT_SCOPE)
