add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

include_directories(${LLVM_INCLUDE_DIRS} ${JADE_MAIN_INCLUDE_DIR})

add_library (XCFSerialize
  XCFParser.cpp
  XCFConstant.cpp
  XCFConstant.h
  )
  
