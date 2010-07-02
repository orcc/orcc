add_definitions(${LLVM_CPP_FLAGS})

include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS})

add_library (JIT
  CreateInstance.cpp
  DecoderExecution.cpp
  DecoderOptimization.cpp
  ParseActor.cpp
  Utility.cpp
 )