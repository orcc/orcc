add_executable(Jade Jade.cpp Console.cpp Console.h)

# Library requiered
target_link_libraries(Jade DecoderEngine ConfigurationEngine XDFParser Scenario IROptimize IRSerialize Scheduler IRJit IRActor Fifo IRCore IRUtil HDAGGraph TinyXml ${SDL_LIBRARY} ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS} ${CMAKE_THREAD_LIBS_INIT})
