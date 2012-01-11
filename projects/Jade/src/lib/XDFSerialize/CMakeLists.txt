add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

include_directories(${LLVM_INCLUDE_DIRS} ${JADE_MAIN_INCLUDE_DIR})

add_library (XDFSerialize
  BinOpSeqParser.cpp
  BinOpSeqParser.h
  ExprParser.cpp
  ExprParser.h
  #InstanceIdRemoval.cpp
  NetworkParser.cpp
  NetworkParser.h
  ParseContinuation.h
  TypeParser.cpp
  TypeParser.h
  XDFConstant.cpp
  XDFConstant.h
  XDFParser.cpp
  XDFWriter.cpp
  )
  
if(UNIX)
  set_target_properties(XDFSerialize PROPERTIES COMPILE_FLAGS "-fPIC")
endif(UNIX)
