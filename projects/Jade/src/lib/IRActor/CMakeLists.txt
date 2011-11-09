include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS})

add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

add_library (IRActor
  BroadcastActor.cpp
  BroadcastAdder.cpp
 )
 
if(UNIX)
  set_target_properties(IRActor PROPERTIES COMPILE_FLAGS "-fPIC")
endif(UNIX)
