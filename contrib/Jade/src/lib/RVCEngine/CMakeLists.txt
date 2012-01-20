include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS})

add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

add_library (RVCEngine
  Constant.h
  Decoder.cpp
  RVCEngine.cpp
 )

if(UNIX)
  set_target_properties(RVCEngine PROPERTIES COMPILE_FLAGS "-fPIC")
endif(UNIX)
