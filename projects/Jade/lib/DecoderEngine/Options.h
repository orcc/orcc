#ifndef OPTION_H
#define OPTION_H

#include "llvm/Support/CommandLine.h"

extern llvm::cl::opt<std::string> VTLDir;

extern llvm::cl::opt<std::string> ToolsDir;

extern llvm::cl::opt<std::string> Fifo;

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
