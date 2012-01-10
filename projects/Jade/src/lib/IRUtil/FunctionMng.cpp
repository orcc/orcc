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

void FunctionMng::createPrintf(Module* module, string message, Instruction* instr, Value* value){
	Function* func_printf = module->getFunction("printf");
	if (!func_printf) {
		 // Printf does'nt exist, create it
		 PointerType* PointerTy_0 = PointerType::get(IntegerType::get(module->getContext(), 8), 0);
		 
		 std::vector<Type*>FuncTy_8_args;
		 FuncTy_8_args.push_back(PointerTy_0);
		 FunctionType* FuncTy_8 = FunctionType::get(IntegerType::get(module->getContext(), 32), FuncTy_8_args, true);
		
		func_printf = Function::Create(FuncTy_8, GlobalValue::ExternalLinkage, "printf", module); // (external, no body)
		func_printf->setCallingConv(CallingConv::C);
		
		AttrListPtr func_printf_PAL;
		func_printf->setAttributes(func_printf_PAL);
   }

	// Create the message
	Value* messageExpr = createStdMessage(module, message);

	
	// Create arguments
	 if (value != NULL){
		 Type* type = value->getType();
		 if (type->isIntegerTy()){
			
			 IntegerType* intTy = cast<IntegerType>(type);
			 if (intTy->getBitWidth() < 32){
				 value = new ZExtInst(value, Type::getInt32Ty(module->getContext()), "", instr);
			 }else if (intTy->getBitWidth() > 32){
				 value = new TruncInst (value, Type::getInt32Ty(module->getContext()), "", instr);
			 }
		 }
		 
	 }
	  
	 
	 std::vector<Value*> params;
	  params.push_back(messageExpr);
	  params.push_back(value);
	  CallInst* int32_25 = CallInst::Create(func_printf, params, "", instr);
	  int32_25->setCallingConv(CallingConv::C);
	  int32_25->setTailCall(false);
	  AttrListPtr int32_25_PAL;
	  int32_25->setAttributes(int32_25_PAL);


}

void FunctionMng::createPuts(Module* module, string message, Instruction* instr){
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
	Value* messageExpr = createStdMessage(module, message);


	// Call puts
	CallInst* int32_puts = CallInst::Create(func_puts, messageExpr, "puts", instr);
	int32_puts->setCallingConv(CallingConv::C);
	int32_puts->setTailCall(true);
	AttrListPtr int32_puts_PAL;
	int32_puts->setAttributes(int32_puts_PAL);
}


Constant* FunctionMng::createStdMessage(Module* module, string message){
	ArrayType* ArrayTy_0 = ArrayType::get(IntegerType::get(module->getContext(), 8), message.size()+1);
	GlobalVariable* gvar_array_str = new GlobalVariable(*module, ArrayTy_0, true, GlobalValue::InternalLinkage, 0, "str");
	 Constant* const_array_7 = ConstantArray::get(module->getContext(), message, true);
	 std::vector<Constant*> const_ptr_8_indices;
	 ConstantInt* const_int64_9 = ConstantInt::get(module->getContext(), APInt(64, StringRef("0"), 10));
	 const_ptr_8_indices.push_back(const_int64_9);
	 const_ptr_8_indices.push_back(const_int64_9);
	
	 Constant* expr = ConstantExpr::getGetElementPtr(gvar_array_str, const_ptr_8_indices);
	 ConstantInt* const_int32_10 = ConstantInt::get(module->getContext(), APInt(32, StringRef("0"), 10));
	 gvar_array_str->setInitializer(const_array_7);
	 
	 return expr;
}