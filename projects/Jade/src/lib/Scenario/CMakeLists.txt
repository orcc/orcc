include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS})

add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

add_library (Scenario
	Manager.cpp
	Scenario.cpp
	Scenario.h
	ScenarioParser.cpp
	ScenarioParser.h
 )
 
if(UNIX)
  set_target_properties(Scenario PROPERTIES COMPILE_FLAGS "-fPIC")
endif(UNIX)
