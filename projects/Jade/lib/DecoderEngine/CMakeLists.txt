include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS})

add_definitions(${LLVM_CPP_FLAGS})

add_library (DecoderEngine
  DecoderEngine.cpp
  DisplayActor.cpp
  DisplayActor.h
  option.h
  SourceActor.cpp
  SourceActor.h
  UnprotectedFifo.cpp
  UnprotectedFifo.h
  FifoCircular.cpp
  FifoCircular.h
 )