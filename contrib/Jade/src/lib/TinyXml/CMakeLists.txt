include_directories(${JADE_MAIN_INCLUDE_DIR})

add_library (TinyXml
  tinystr.cpp
  tinyxml.cpp
  tinyxmlerror.cpp
  tinyxmlparser.cpp
  )
  
if(UNIX)
  set_target_properties(TinyXml PROPERTIES COMPILE_FLAGS "-fPIC")
endif(UNIX)
