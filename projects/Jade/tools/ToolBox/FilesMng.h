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
@brief Files manager header of JadeToolbox
@author Jerome Gorin
@file FilesMng.h
@version 1.0
@date 2011/03/22
*/

//------------------------------
#ifndef FILESMNG_H
#define FILESMNG_H

#include <list>
#include <map>

#include "llvm/LLVMContext.h"
#include "llvm/Support/IRReader.h"
#include "llvm/Support/PassNameParser.h"
#include "llvm/System/Signals.h"
//------------------------------

/**
 *  @brief Build a map of files path
 *   
 *  @param filesPath : a map of files path
 */
void buildFilesPath(std::map<llvm::sys::Path,std::string>* filesPath);

/**
 *  @brief Parse files given to modules
 *   
 *  @param filesPath : a map of files path
 *
 *  @param modules : a map of module
 */
void parseFiles(std::map<llvm::sys::Path,std::string>* filesPath, std::map<std::string,llvm::Module*>* modules);

/**
 *  @brief Make recursively a map of directories
 *   
 *  @param path : path of file
 *
 *  @param name : file name
 *
 *  @param result : a map of directories
 */
void recurseMapDirectories(const llvm::sys::Path& path, std::string name, std::map<llvm::sys::Path,std::string>& result);

#endif