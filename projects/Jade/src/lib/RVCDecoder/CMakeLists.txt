add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

include_directories(${LLVM_INCLUDE_DIRS} ${JADE_MAIN_INCLUDE_DIR})

add_library(RVCDecoder SHARED
	RVCDecoder.cpp
	source.c
	display.c
	compareyuv.c
	writer.c
	orcc_util.h
	orcc_types.h
 )

# Library required
target_link_libraries(RVCDecoder RVCEngine ConfigurationEngine XDFSerialize IRMerger Scenario IROptimize IRSerialize RoundRobinScheduler IRJit IRActor IRUtil Fifo IRCore HDAGGraph TinyXml ZLib ${SDL_LIBRARY} ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS} ${CMAKE_THREAD_LIBS_INIT})

INSTALL(TARGETS RVCDecoder DESTINATION lib PERMISSIONS
OWNER_READ OWNER_WRITE OWNER_EXECUTE
GROUP_READ GROUP_EXECUTE
WORLD_READ WORLD_EXECUTE)