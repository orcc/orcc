add_executable(Jade Jade.cpp Console.cpp Console.h)

# Library requiered
target_link_libraries(Jade DecoderEngine ConfigurationEngine XDFSerialize IRMerger Scenario IROptimize IRSerialize RoundRobinScheduler IRJit IRActor Fifo IRCore IRUtil HDAGGraph TinyXml ZLib ${SDL_LIBRARY} ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS} ${CMAKE_THREAD_LIBS_INIT})
