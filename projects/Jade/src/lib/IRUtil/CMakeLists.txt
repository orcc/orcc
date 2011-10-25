include_directories(${LLVM_INCLUDE_DIRS} ${JADE_MAIN_INCLUDE_DIR})

add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})

add_library (IRUtil
	FifoMng.cpp
	FunctionMng.cpp
	PackageMng.cpp
	OptionMng.cpp
	CompressionMng.cpp
 )