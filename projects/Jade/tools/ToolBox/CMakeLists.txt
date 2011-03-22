add_executable( JadeToolbox
	Toolbox.cpp
	ArMng.cpp
	FilesMng.cpp
	OptMng.cpp
)


# Library requiered
target_link_libraries(JadeToolbox IRUtil ZLib ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS})
