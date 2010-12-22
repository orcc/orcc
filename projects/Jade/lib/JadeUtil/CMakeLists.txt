add_definitions(${JADE_CPP_FLAGS})

include_directories(${JADE_MAIN_INCLUDE_DIR})

add_library (JadeUtil
	PackageMgn.cpp
 )