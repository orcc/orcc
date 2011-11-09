include_directories(${JADE_MAIN_INCLUDE_DIR})

add_definitions(${JADE_CPP_FLAGS})

add_library(ZLib
	gzread.c
	gzwrite.c
	gzclose.c
	gzlib.c
	deflate.c
	inflate.c
	adler32.c
	crc32.c
	infback.c
	inffast.c
	inftrees.c
	trees.c
	zutil.c
 )
 
if(UNIX)
  set_target_properties(ZLib PROPERTIES COMPILE_FLAGS "-fPIC")
endif(UNIX)
