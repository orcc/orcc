find_package(LibXml2)

add_definitions(${LLVM_CPP_FLAGS})

IF (NOT LIBXML2_FOUND)
   FIND_PATH(LIBXML2_INCLUDE_DIR libxml/parser.h
       /usr/include
       /usr/include/libxml2
       /sw/include
       /sw/include/libxml2
       ${VISUS_INCLUDE}
       ${VISUS_INCLUDE}/libxml2
   )

   FIND_LIBRARY(LIBXML2_LIBRARIES xml2 
       /usr/lib
       /sw/lib
       ${VISUS_LIBRARIES}
   )
   IF (LIBXML2_LIBRARIES AND LIBXML2_INCLUDE_DIR)
      SET(LIBXML2_FOUND "YES")
   ENDIF (LIBXML2_LIBRARIES AND LIBXML2_INCLUDE_DIR)
ELSEIF (CMAKE_VERBOSE_MAKEFILE)
      MESSAGE("Using LIBXML2_INCLUDE_DIR = " ${LIBXML2_INCLUDE_DIR})     
      MESSAGE("Using LIBXML2_LIBRARIES = " ${LIBXML2_LIBRARIES})     
ENDIF (NOT LIBXML2_FOUND)

include_directories(${LLVM_INCLUDE_DIRS} ${LIBXML2_INCLUDE_DIR} ${JADE_MAIN_INCLUDE_DIR})

add_library (XDFParser
  XDFParser.cpp
  ExprParser.cpp
  ExprParser.h
  NetworkParser.cpp
  NetworkParser.h
  TypeParser.cpp
  TypeParser.h
  )
  
  target_link_libraries(XDFParser Network ${LIBXML2_LIBRARIES})