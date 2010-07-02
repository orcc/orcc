include_directories(${JADE_MAIN_INCLUDE_DIR})

add_library (HDAGGraph
  HDAGEdge.cpp
  HDAGGraph.cpp
  HDAGVertex.cpp
  DotWriter.cpp
 )