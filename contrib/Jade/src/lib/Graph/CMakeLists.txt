include_directories(${JADE_MAIN_INCLUDE_DIR})

add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

add_library (HDAGGraph
  HDAGEdge.cpp
  HDAGGraph.cpp
  HDAGVertex.cpp
  DotWriter.cpp
 )
 
if(UNIX)
  set_target_properties(HDAGGraph PROPERTIES COMPILE_FLAGS "-fPIC")
endif(UNIX)
