# CMake projet
cmake_minimum_required (VERSION 2.6)
project (sim_tta_network)

find_package(Threads REQUIRED)

find_library(tce_LIBRARY tce /home/hyviquel/Applications/tce/devel/)

set(TCE_INCLUDE_DIR /home/hyviquel/Applications/tce/devel/include)

include_directories(${TCE_INCLUDE_DIR})

add_executable(SimulatorMultiprocessor SimulatorMultiprocessor.cc)

target_link_libraries(SimulatorMultiprocessor ${tce_LIBRARY} ${CMAKE_THREAD_LIBS_INIT})
