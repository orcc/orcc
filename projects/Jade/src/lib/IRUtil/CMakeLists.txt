include_directories(${LLVM_INCLUDE_DIRS} ${JADE_MAIN_INCLUDE_DIR})

add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

add_library (IRUtil
	FunctionMng.cpp
	PackageMng.cpp
	OptionMng.cpp
	CompressionMng.cpp
 )
 
if(UNIX)
  set_target_properties(IRUtil PROPERTIES COMPILE_FLAGS "-fPIC")
endif(UNIX)
