include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS})

add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

add_library (Decoder
  BroadcastActor.h
  BroadcastActor.cpp
  BroadcastAdder.cpp
  BroadcastAdder.h
  InstancedActor.cpp
  Decoder.cpp
  Instanciator.cpp
  Instanciator.h
  Procedure.cpp
 )