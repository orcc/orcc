
# Build orcc-map as a standalone exectuable

add_definitions(${extra_definitions})
include_directories(${extra_includes})

if(USE_OPENMP)
	find_package(OpenMP REQUIRED)
	if(OPENMP_FOUND)
	    set (CMAKE_C_FLAGS "${CMAKE_C_FLAGS} ${OpenMP_C_FLAGS}")
	    set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} ${OpenMP_CXX_FLAGS}")
	    message(STATUS "Found OpenMP: " ${OpenMP_C_FLAGS})
	else()
	    message(STATUS "Cannot find OpenMP")
	endif()
endif()

add_executable(orcc-map src/main.c)
target_link_libraries(orcc-map orcc-runtime ${extra_libraries})

install(TARGETS orcc-map
    RUNTIME DESTINATION bin
)
