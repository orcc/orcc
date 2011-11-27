add_executable(Jade Jade.cpp Console.cpp Console.h)

# Library requiered
target_link_libraries(Jade RVCEngine ConfigurationEngine XDFSerialize XCFSerialize IRMerger Scenario IROptimize IRSerialize RoundRobinScheduler IRJit IRActor IRUtil Fifo IRCore HDAGGraph TinyXml ZLib orcc ${SDL_LIBRARY} ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS} ${CMAKE_THREAD_LIBS_INIT})


INSTALL(TARGETS Jade
RUNTIME DESTINATION bin
PERMISSIONS
OWNER_READ OWNER_WRITE OWNER_EXECUTE
GROUP_READ GROUP_EXECUTE
WORLD_READ WORLD_EXECUTE
)