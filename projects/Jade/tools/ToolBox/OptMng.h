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
@brief Optimization manager header of JadeToolbox
@author Jerome Gorin
@file OptMng.h
@version 1.0
@date 2011/03/22
*/

//------------------------------
#ifndef OPTMNG_H
#define OPTMNG_H


#include "llvm/Module.h"
#include "llvm/LinkAllPasses.h"
#include "llvm/Bitcode/ReaderWriter.h"
#include "llvm/Support/PassNameParser.h"
#include "llvm/Support/StandardPasses.h"
#include "llvm/Target/TargetData.h"
//------------------------------

inline void addPass(llvm::PassManagerBase &PM, llvm::Pass *P);

void AddOptimizationPasses(llvm::PassManagerBase &MPM, llvm::PassManagerBase &FPM,
                           unsigned OptLevel);

void AddStandardCompilePasses(llvm::PassManagerBase &PM);

void AddStandardLinkPasses(llvm::PassManagerBase &PM);

void opt(std::string file, llvm::Module* M);

#endif