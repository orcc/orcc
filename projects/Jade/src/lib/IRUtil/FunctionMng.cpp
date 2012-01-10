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
@brief Implementation of class PackageMng
@author Jerome Gorin
@file FunctionMng.cpp
@version 1.0
@date 22/10/2011
*/

//------------------------------
#include "llvm/Constants.h"
#include "llvm/LLVMContext.h"
#include "llvm/Instructions.h"
#include "llvm/Module.h"

#include "Jade/Decoder.h"
#include "Jade/Util/FunctionMng.h"
//------------------------------

using namespace std;
using namespace llvm;


void FunctionMng::createPuts(Module* module, string message, Instruction* instr, Value* value){
	Function* func_puts = module->getFunction("puts");

	if (func_puts == NULL){
		// Puts does'nt exist, create it
		PointerType* PointerTy_4 = PointerType::get(IntegerType::get(module->getContext(), 8), 0);

		vector<Type*>FuncTy_6_args;
		FuncTy_6_args.push_back(PointerTy_4);
		FunctionType* FuncTy_6 = FunctionType::get(IntegerType::get(module->getContext(), 32), FuncTy_6_args, false);

		func_puts = Function::Create(FuncTy_6, GlobalValue::ExternalLinkage,"puts", module);
		func_puts->setCallingConv(CallingConv::C);
		 AttrListPtr func_puts_PAL;
		 {
			  SmallVector<AttributeWithIndex, 4> Attrs;
			  AttributeWithIndex PAWI;
			  PAWI.Index = 1U; PAWI.Attrs = 0  | Attribute::NoCapture;
			  Attrs.push_back(PAWI);
			  PAWI.Index = 4294967295U; PAWI.Attrs = 0  | Attribute::NoUnwind;
			  Attrs.push_back(PAWI);
			  func_puts_PAL = AttrListPtr::get(Attrs.begin(), Attrs.end());
		 }
		 func_puts->setAttributes(func_puts_PAL);
	}
	 
	// Create the message
	ArrayType* ArrayTy_0 = ArrayType::get(IntegerType::get(module->getContext(), 8), message.size()+1);
	GlobalVariable* gvar_array_str = new GlobalVariable(*module, ArrayTy_0, true, GlobalValue::InternalLinkage, 0, "str");
	 Constant* const_array_7 = ConstantArray::get(module->getContext(), message, true);
	 std::vector<Constant*> const_ptr_8_indices;
	 ConstantInt* const_int64_9 = ConstantInt::get(module->getContext(), APInt(64, StringRef("0"), 10));
	 const_ptr_8_indices.push_back(const_int64_9);
	 const_ptr_8_indices.push_back(const_int64_9);
	 Constant* const_ptr_8 = ConstantExpr::getGetElementPtr(gvar_array_str, const_ptr_8_indices);
	 ConstantInt* const_int32_10 = ConstantInt::get(module->getContext(), APInt(32, StringRef("0"), 10));
	 gvar_array_str->setInitializer(const_array_7);


	// Call puts
	CallInst* int32_puts = CallInst::Create(func_puts, const_ptr_8, "puts", instr);
	int32_puts->setCallingConv(CallingConv::C);
	int32_puts->setTailCall(true);
	AttrListPtr int32_puts_PAL;
	int32_puts->setAttributes(int32_puts_PAL);
}
