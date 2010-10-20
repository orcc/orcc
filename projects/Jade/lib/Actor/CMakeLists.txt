include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS})

add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

add_library (Actor
  Action.cpp
  IRConstant.h
  IRParser.cpp
  FSM.cpp
 )