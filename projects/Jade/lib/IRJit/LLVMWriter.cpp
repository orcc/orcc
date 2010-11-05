/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

/**
@brief Implementation of LLVMWriter
@author Jerome Gorin
@file CreateInstance.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include "Jade/JIT/LLVMWriter.h"
//------------------------------

using namespace std;
using namespace llvm;

LLVMWriter::LLVMWriter(string prefix, Module* module){
	this->module = module;
	this->prefix = prefix;
}

GlobalVariable* LLVMWriter::createVariable(GlobalVariable* variable){
	GlobalVariable* var = addVariable(variable);
	LinkGlobalInits(variable);

	return var;
}

GlobalVariable* LLVMWriter::addVariable(llvm::GlobalVariable* variable){
	string Err;
	const GlobalVariable *SGV = variable;
    GlobalValue *DGV = 0;
	Module *Dest = module;
	
      // No linking to be performed, simply create an identical version of the
      // symbol over in the dest module... the initializer will be filled in
      // later by LinkGlobalInits.
      GlobalVariable *NewDGV =
        new GlobalVariable(*Dest, SGV->getType()->getElementType(),
                           SGV->isConstant(), SGV->getLinkage(), /*init*/0,
                           prefix + SGV->getName(), 0, false,
                           SGV->getType()->getAddressSpace());
      // Propagate alignment, visibility and section info.
      CopyGVAttributes(NewDGV, SGV);

      // Make sure to remember this mapping.
      ValueMap[SGV] = NewDGV;

      return NewDGV;
}

bool LLVMWriter::LinkGlobalInits(llvm::GlobalVariable* variable){
	string Error;
	const GlobalVariable *SGV = variable;
	Module* Dest = module;
    
	GlobalVariable *GV = cast<GlobalVariable>(ValueMap[variable]);
    
	if (variable->hasInitializer())
      GV->setInitializer(cast<Constant>(MapValue(variable->getInitializer(),
                                                 ValueMap,
												 true)));
    GV->setLinkage(variable->getLinkage());
    GV->setThreadLocal(variable->isThreadLocal());
    GV->setConstant(variable->isConstant());

	return true;
}

/// CopyGVAttributes - copy additional attributes (those not needed to construct
/// a GlobalValue) from the SrcGV to the DestGV.
void LLVMWriter::CopyGVAttributes(GlobalValue *DestGV, const GlobalValue *SrcGV) {
  
	// Use the maximum alignment, rather than just copying the alignment of SrcGV.
  unsigned Alignment = std::max(DestGV->getAlignment(), SrcGV->getAlignment());
  DestGV->copyAttributesFrom(SrcGV);
  DestGV->setAlignment(Alignment);
}
