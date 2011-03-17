add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

include_directories(${LLVM_INCLUDE_DIRS} ${JADE_MAIN_INCLUDE_DIR})

add_library (XDFParser
  BinOpSeqParser.cpp
  BinOpSeqParser.h
  ExprParser.cpp
  ExprParser.h
  NetworkParser.cpp
  NetworkParser.h
  ParseContinuation.h
  TypeParser.cpp
  TypeParser.h
  XDFConstant.h
  XDFParser.cpp
  )
  
  target_link_libraries(XDFParser Compression)
