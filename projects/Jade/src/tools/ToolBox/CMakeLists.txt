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
target_link_libraries(JadeToolbox XDFSerialize RoundRobinScheduler IRUtil IRCore HDAGGraph TinyXml ZLib ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS})

INSTALL(TARGETS JadeToolbox
RUNTIME DESTINATION bin
PERMISSIONS
OWNER_READ OWNER_WRITE OWNER_EXECUTE
GROUP_READ GROUP_EXECUTE
WORLD_READ WORLD_EXECUTE
)