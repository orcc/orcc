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
target_link_libraries(JadeToolbox DecoderEngine XDFSerialize HDAGGraph IRJit Fifo IRCore TinyXml IRUtil ZLib ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS})