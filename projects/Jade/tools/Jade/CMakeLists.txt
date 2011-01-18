IF(APPLE)
add_executable(Jade Jade.cpp Console.cpp /Library/Frameworks/SDL.framework/Resources/SDLMain.m)
ELSE(APPLE)
add_executable(Jade Jade.cpp Console.cpp)
ENDIF(APPLE)

# Library requiered
target_link_libraries(Jade DecoderEngine ConfigurationEngine XDFParser IROptimize IRSerialize Scheduler IRJit IRTransform  IRActor Fifo IRCore IRUtil HDAGGraph TinyXml ${SDL_LIBRARY} ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS} ${CMAKE_THREAD_LIBS_INIT})