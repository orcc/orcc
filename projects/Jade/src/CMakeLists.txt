# Add libraries
add_subdirectory(lib/ConfigurationEngine)
add_subdirectory(lib/RVCEngine)
add_subdirectory(lib/Graph)
add_subdirectory(lib/Fifo) 
add_subdirectory(lib/IRActor)
add_subdirectory(lib/IRCore)
add_subdirectory(lib/IRJit)
add_subdirectory(lib/IRMerger)
add_subdirectory(lib/IROptimize)
add_subdirectory(lib/IRSerialize)
add_subdirectory(lib/IRUtil)
add_subdirectory(lib/RoundRobinScheduler)
add_subdirectory(lib/Scenario)
add_subdirectory(lib/TinyXml)
add_subdirectory(lib/XDFSerialize)
add_subdirectory(lib/ZLib)
if(WIN32)
	add_subdirectory(lib/lib_RVCDecoder)
endif(WIN32)

# Include directories
include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS} ${SDL_INCLUDE_DIR})
 
add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})
 
# Tools
add_subdirectory(tools)
