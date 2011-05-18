add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

include_directories(${LLVM_INCLUDE_DIRS} ${JADE_MAIN_INCLUDE_DIR})

add_library(lib_RVCDecoder SHARED
	RVCDecoder.cpp
 )

# Library requiered
target_link_libraries(lib_RVCDecoder RVCEngine ConfigurationEngine XDFSerialize IRMerger Scenario IROptimize IRSerialize RoundRobinScheduler IRJit IRActor IRUtil Fifo IRCore HDAGGraph TinyXml ZLib ${SDL_LIBRARY} ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS} ${CMAKE_THREAD_LIBS_INIT})