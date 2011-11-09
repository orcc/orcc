include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS})

add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

add_library (IRSerialize
  IRConstant.h
  IRLinker.cpp
  IRParser.cpp
  IRWriter.cpp
  IRUnwriter.cpp
 )
 
if(UNIX)
  set_target_properties(IRSerialize PROPERTIES COMPILE_FLAGS "-fPIC")
endif(UNIX)
