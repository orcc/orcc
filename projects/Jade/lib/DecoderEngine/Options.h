#ifndef OPTION_H
#define OPTION_H

#include "llvm/Support/CommandLine.h"

#ifndef JADE_VTL_DIR
#define JADE_VTL_DIR ""
#endif

// Tools directory
#ifndef JADE_TOOLS_DIR
#define JADE_TOOLS_DIR ""
#endif

// Commande line options
llvm::cl::opt<std::string>
VTLDir("L", llvm::cl::desc("Video Tools Library directory"), 
	   llvm::cl::ValueRequired,
	   llvm::cl::value_desc("VTL Folder"), 
	   llvm::cl::init(JADE_VTL_DIR));

llvm::cl::opt<std::string> ToolsDir("T", llvm::cl::desc("Jade tools directory"), 
									llvm::cl::ValueRequired,
									llvm::cl::value_desc("Tools Folder"), 
									llvm::cl::init(JADE_TOOLS_DIR));

llvm::cl::opt<bool> ForceInterpreter("force-interpreter",
                                 llvm::cl::desc("Force interpretation: disable JIT"),
                                 llvm::cl::init(false));

llvm::cl::opt<std::string> MArch("march",
        llvm::cl::desc("Architecture to generate assembly for (see --version)"));

llvm::cl::opt<bool> DisableCoreFiles("disable-core-files", llvm::cl::Hidden,
                   llvm::cl::desc("Disable emission of core files if possible"));

llvm::cl::opt<bool> NoLazyCompilation("disable-lazy-compilation",
                  llvm::cl::desc("Disable JIT lazy compilation"),
                  llvm::cl::init(false));

llvm::cl::list<std::string> MAttrs("mattr",
         llvm::cl::CommaSeparated,
         llvm::cl::desc("Target specific attributes (-mattr=help for details)"),
         llvm::cl::value_desc("a1,+a2,-a3,..."));

llvm::cl::opt<std::string> MCPU("mcpu",
       llvm::cl::desc("Target a specific cpu type (-mcpu=help for details)"),
       llvm::cl::value_desc("cpu-name"),
       llvm::cl::init(""));

llvm::cl::opt<std::string> Fifo("fifo",
         llvm::cl::CommaSeparated,
         llvm::cl::desc("Specify fifo to be used in the decoder"),
         llvm::cl::value_desc("trace, circular, fast"),
		 llvm::cl::init("circular"));

//Verify if directory is well formed
void setDirectory(std::string* dir){
	size_t found = dir->find_last_of("/\\");
	if(found != dir->length()-1){
		dir->insert(dir->length(),"/");
	}
}


//Check options of the decoder engine
void setOptions(){
	setDirectory(&VTLDir);
	setDirectory(&ToolsDir);
}	


#endif
