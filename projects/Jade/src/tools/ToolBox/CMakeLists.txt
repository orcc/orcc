add_executable( JadeToolbox
	Toolbox.cpp
	ArMng.cpp
	ArMng.h
	FilesMng.cpp
	FilesMng.h
	OptMng.cpp
	OptMng.h
)


# Library requiered
target_link_libraries(JadeToolbox RVCEngine XDFSerialize IRCore IRUtil IRCore HDAGGraph Fifo IRJit TinyXml ZLib ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS})
