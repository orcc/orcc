# CMake projet of Jade
cmake_minimum_required (VERSION 2.6)
project (Jade)

find_program(LLVM_CONFIG llvm-config ${LLVM_INSTDIR}/bin DOC "path to llvm-config tool")
find_package(SDL REQUIRED)

IF(LLVM_CONFIG STREQUAL "LLVM_CONFIG-NOTFOUND" )
	set(LLVM_DIR /where/llvm/lives CACHE STRING "llvm source directory")
	set(LLVM_INST_DIR /where/llvm/is/installed CACHE STRING "llvm installation directory")
	if( MSVC )
		set(LLVM_LIB "Debug" CACHE STRING "Specify the llvm library Debug/Release configuration.")
		set(LLVM_INCLUDE_DIRS ${LLVM_DIR}/include ${LLVM_INST_DIR}/include)
		set(LLVM_LIBRARY ${LLVM_INST_DIR}/lib/${LLVM_LIB})
		set(LLVM_CPP_FLAGS "/D __STDC_CONSTANT_MACROS";
							"/D _CRT_SECURE_NO_DEPRECATE";
							"/D _CRT_SECURE_NO_WARNINGS";
							"/D _SCL_SECURE_NO_WARNINGS";
							"/D CRT_NONSTDC_NO_WARNINGS";
							"/D _SCL_SECURE_NO_DEPRECATE";
							"-fPIC";
							"-wd4146 -wd4503 -wd4996 -wd4800 -wd4244 -wd4624 -wd4355 -wd4715";
							"-wd4180 -wd4345 -wd4224 -wd4351")
		set(LLVM_LIBRARIES ${LLVM_LIBRARY}/LLVMJIT.lib;
						   ${LLVM_LIBRARY}/LLVMInterpreter.lib
						   ${LLVM_LIBRARY}/LLVMX86CodeGen.lib;
						   ${LLVM_LIBRARY}/LLVMLinker.lib;
						   ${LLVM_LIBRARY}/LLVMBitWriter.lib;
						   ${LLVM_LIBRARY}/LLVMArchive.lib;
						   ${LLVM_LIBRARY}/LLVMAsmParser.lib;
						   ${LLVM_LIBRARY}/LLVMBitReader.lib;
						   ${LLVM_LIBRARY}/LLVMExecutionEngine.lib;
						   ${LLVM_LIBRARY}/LLVMSelectionDAG.lib;
						   ${LLVM_LIBRARY}/LLVMX86Info.lib;
						   ${LLVM_LIBRARY}/LLVMAsmPrinter.lib;
						   ${LLVM_LIBRARY}/LLVMCodeGen.lib;
						   ${LLVM_LIBRARY}/LLVMScalarOpts.lib;
						   ${LLVM_LIBRARY}/LLVMTransformUtils.lib;
						   ${LLVM_LIBRARY}/LLVMipa.lib;
						   ${LLVM_LIBRARY}/LLVMipo.lib;
						   ${LLVM_LIBRARY}/LLVMInstrumentation.lib;
						   ${LLVM_LIBRARY}/LLVMAnalysis.lib;
						   ${LLVM_LIBRARY}/LLVMTarget.lib;
						   ${LLVM_LIBRARY}/LLVMCore.lib;
						   ${LLVM_LIBRARY}/LLVMMC.lib;
						   ${LLVM_LIBRARY}/LLVMSupPort.lib;
						   ${LLVM_LIBRARY}/LLVMSystem.lib;
						   ${LLVM_LIBRARY}/LLVMInstCombine.lib)
	endif( MSVC )
else(LLVM_CONFIG STREQUAL "LLVM_CONFIG-NOTFOUND" )
	string(REPLACE "/bin/llvm-config" "" LLVM_DIR ${LLVM_CONFIG})
	
	set(LLVM_INSTDIR ${LLVM_DIR} CACHE PATH "LLVM installation directory" FORCE)
	
	EXECUTE_PROCESS(COMMAND ${LLVM_CONFIG} --libs jit asmparser instrumentation interpreter nativecodegen bitreader selectiondag  linker bitreader bitwriter ipo OUTPUT_VARIABLE LLVM_LIBRARIES OUTPUT_STRIP_TRAILING_WHITESPACE)
	EXECUTE_PROCESS(COMMAND ${LLVM_CONFIG} --includedir OUTPUT_VARIABLE LLVM_INCLUDE_DIRS OUTPUT_STRIP_TRAILING_WHITESPACE)
	EXECUTE_PROCESS(COMMAND ${LLVM_CONFIG} --cppflags OUTPUT_VARIABLE LLVM_CPP_FLAGS OUTPUT_STRIP_TRAILING_WHITESPACE)
	EXECUTE_PROCESS(COMMAND ${LLVM_CONFIG} --ldflags OUTPUT_VARIABLE LLVM_LD_FLAGS OUTPUT_STRIP_TRAILING_WHITESPACE)
	SEPARATE_ARGUMENTS(LLVM_LIBRARIES)
	SEPARATE_ARGUMENTS(LLVM_INCLUDE_DIRS)
	SEPARATE_ARGUMENTS(LLVM_CPP_FLAGS)
endif(LLVM_CONFIG STREQUAL "LLVM_CONFIG-NOTFOUND" )

# Set Jade flags
set(JADE_MAIN_SRC_DIR ${CMAKE_CURRENT_SOURCE_DIR})
set(JADE_MAIN_INCLUDE_DIR ${JADE_MAIN_SRC_DIR}/include)
set(JADE_TOOLS_DIR ${JADE_MAIN_SRC_DIR}/tools)
set(JADE_VTL_DIR ${JADE_MAIN_SRC_DIR}/vtl)


# Generate documentation option
option(GENERATE_DOCS "Generate documentation using doxygen.")

if( GENERATE_DOCS)
	add_subdirectory(doc)
endif()


# Set output path
set(EXECUTABLE_OUTPUT_PATH bin/${CMAKE_BUILD_Type})


# Add libraries
add_subdirectory(lib/DecoderEngine)
add_subdirectory(lib/Graph)
add_subdirectory(lib/Fifo)
add_subdirectory(lib/IRActor)
add_subdirectory(lib/IRCore)
add_subdirectory(lib/IRJit)
add_subdirectory(lib/IRSerialize)
add_subdirectory(lib/IRUtil)
add_subdirectory(lib/IRTransform)
add_subdirectory(lib/Scheduler)
add_subdirectory(lib/TinyXml)
add_subdirectory(lib/XDFParser)

# Include directories
include_directories(${JADE_MAIN_INCLUDE_DIR} ${LLVM_INCLUDE_DIRS} ${SDL_INCLUDE_DIR})
 
add_definitions(${LLVM_CPP_FLAGS} ${JADE_CPP_FLAGS})
 
# Executable
IF(APPLE)
add_executable(Jade Jade.cpp /Library/Frameworks/SDL.framework/Resources/SDLMain.m)
ELSE(APPLE)
add_executable(Jade Jade.cpp)
ENDIF(APPLE)

add_executable(JadeTools JadeTools.cpp)

# Library requiered
target_link_libraries(Jade DecoderEngine XDFParser IRSerialize Scheduler IRJit IRTransform  IRActor Fifo IRUtil IRCore HDAGGraph TinyXml ${SDL_LIBRARY} ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS})
target_link_libraries(JadeTools IRUtil ${LLVM_LIBRARIES} ${LLVM_LD_FLAGS})
