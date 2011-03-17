add_executable(JadeToolbox Toolbox.cpp)


# Library requiered
target_link_libraries(JadeToolbox IRUtil Compression ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS})