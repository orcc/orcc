add_executable(Jade Jade.cpp Console.cpp Console.h)

# Library requiered
target_link_libraries(Jade RVCEngine ConfigurationEngine XDFSerialize IRMerger Scenario IROptimize IRSerialize RoundRobinScheduler IRJit IRActor IRUtil Fifo IRCore HDAGGraph TinyXml ZLib orcc ${SDL_LIBRARY} ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS} ${CMAKE_THREAD_LIBS_INIT})
