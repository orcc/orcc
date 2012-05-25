if(NO_DISPLAY)
add_definitions(-DNO_DISPLAY)
else()
find_package(SDL)
endif()

include_directories(
${YACE_INCLUDE_DIR} 
${TINYXML_INCLUDE_DIR}
if(NOT NO_DISPLAY)
${SDL_INCLUDE_DIR}
endif()
)

add_library(Yace STATIC
#include files
yace/include/actor.h
yace/include/condition.h
yace/include/config_parser.h
yace/include/fifo.h
yace/include/get_opt.h
yace/include/mutex.h
yace/include/port.h
yace/include/scheduler.h
yace/include/thread.h
yace/include/thread_pool.h
yace/include/timer.h
#source files
yace/src/condition.cpp
yace/src/config_parser.cpp
yace/src/display.cpp
yace/src/get_opt.cpp
yace/src/mutex.cpp
yace/src/source.cpp
yace/src/timer.cpp
yace/src/thread.cpp
)


add_library(TinyXml STATIC
#include files
tinyxml/include/tinystr.h
tinyxml/include/tinyxml.h
#source files
tinyxml/src/tinystr.cpp
tinyxml/src/tinyxml.cpp
tinyxml/src/tinyxmlerror.cpp
tinyxml/src/tinyxmlparser.cpp
)

