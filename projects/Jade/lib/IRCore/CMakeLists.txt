add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS})

add_library (IRCore
	Action.cpp 
	Actor.cpp
	Connection.cpp
	FSM.cpp
	InstancedActor.cpp
	Network.cpp
 )