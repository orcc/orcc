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
@brief Description of the JIT class interface
@author Jerome Gorin
@file JIT.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef JIT_H
#define JIT_H

// Video Tools Library directory
#include <list>
#include <map>

#include "llvm/Constants.h"
#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/DerivedTypes.h"
#include "llvm/PassManager.h"
#include "llvm/ExecutionEngine/JIT.h"
#include "llvm/System/Signals.h"
#include "llvm/Transforms/Utils/Cloning.h"
#include "llvm/Transforms/Utils/ValueMapper.h"

class AbstractFifo;
class Action;
class ActionTag;
class ActionScheduler;
class Actor;
class Decoder;
class FuncUnit;
class FSM;
class Instance;
class InstancedFU;
class Procedure;
class Network;
class Port;
class Location;
class Variable;
//------------------------------

/**
 * @brief  This class defines the JIT Engine part of the DecoderEngine.
 * 
 * @author Jerome Gorin
 * 
 */
class JIT {
public:

	/**
     *  @brief Constructor
     *
	 *	Initialize the JIT engine
	 *
     */
	JIT(llvm::LLVMContext& C);
	~JIT();

	int initEngine(Decoder* decoder);
	

	/**
     *  @brief Load and parse the bitcode file
     *
	 *  Read the specified bitcode file of the current actor and return the corresponding 
	 *		module. 
	 *
	 * @param actor : file to parse
	 *
	 * @return the corresponding llvm::Module
	 *
     */
	llvm::Module* LoadBitcode(std::string file, std::string directory);

	
	/**
     *  @brief Add a new llvm::Type in the given decoder
     *
	 *  Insert an entry in the decoder Type table mapping. If there is already an entry 
	 *   for this name, true is returned and the symbol table of the decoder is not modified. 
	 *
	 * @param name : name of the type
	 *
	 * @param type : llvm::Type to add
	 *
	 * @param decoder : Decoder to add type
	 *
	 * @return True ff there is already an entry for this name, otherwise false.
	 *
     */
	bool addType(std::string name, const llvm::Type* type, Decoder* decoder);

	/**
     *  @brief Add a new llvm::GlobalVariable in the given decoder
     *
	 *  Insert the given global variable into the decoder. This global variable
	 *   can either represent an actor port, state, parameter or internal variable.
	 *
	 * @param variable : llvm::GlobalVariable to add
	 *
	 * @param decoder : decoder to had the variable
	 *
	 * @return True if successfull, otherwise false
     */
	llvm::GlobalVariable* addVariable(std::string prefix, llvm::GlobalVariable* variable);

	llvm::sys::Path getFilename(std::string bitcode, std::string directory);


	/**
     *  @brief Add a new llvm::Function in the given decoder
     *
	 *  Insert the given function as external into the decoder. This function
	 *   can either represent an action, an action scheduler or a procedure.
	 *
	 * @param variable : llvm::Function to add
	 *
	 * @param decoder : decoder to had the function
	 *
	 * @return True if successfull, otherwise false
     */
	llvm::GlobalValue* addFunctionProtosExternal(std::string prefix, const llvm::Function* function);

	/**
     *  @brief Add a new llvm::Function in the given decoder
     *
	 *  Insert the given function as internal into the decoder. This function
	 *   can either represent an action, an action scheduler or a procedure.
	 *
	 * @param variable : llvm::Function to add
	 *
	 * @param decoder : decoder to had the function
	 *
	 * @return True if successfull, otherwise false
     */
	llvm::GlobalValue* addFunctionProtosInternal(std::string prefix, const llvm::Function* function);

	/**
     *  @brief Create a new Functional Unit with the given actor and module
     *
	 *  Create a new functional unit from the parsed actor with information linked
	 *   to the given llvm::Module
	 *
	 *	@param actor : Actor bound to the Function Unit to create
	 *
	 *	@param module : llvm::Module corresponding to the given Actor
	 *
	 * @return a new FuncUnit if successfull, otherwise NULL
     */
	FuncUnit* createFuncUnit(Actor* actor, llvm::Module* module);

	/**
     *  @brief set functional unit variable from the given module
     *
	 *  Link the functional unit variable to llvm::module. The variable can
	 *  either be port, parameter or state of the actor.
	 *
	 *	@param fu : FuncUnit to set variable.
	 *
	 *	@param module : llvm::Module corresponding to the functional unit
     */
	void setVariableFromModule(FuncUnit* fu, llvm::Module* module);


	/**
     *  @brief set functional unit variable from the given module
     *
	 *  Link the functional unit function to llvm::module. Function can
	 *  either be action or procedure.
	 *
	 *	@param fu : FuncUnit to set function.
	 *
	 *	@param module : llvm::Module corresponding to the functional unit
     */
	void setFunctionFromModule(FuncUnit* fu, llvm::Module* module);
	
	/**
     *  @brief Print llvm decoder into a file
	 *
	 *	Print the decoder at its point of creation. This function can be
	 *	called at an any time of the decoder creation process instanciation.
	 *
	 * @param file		:  string name of the output file
	 *
	 * @param decoder	:  Decoder to print
     */
	void printModule(std::string file, Decoder* decoder);


	/**
     *  @brief get pointer of a port
	 *
	 *	Get the pointer of a port inside the given decoder.
	 *
	 * @param port		:  Port from wich the pointer.
	 *
	 * @param decoder	:  Decoder to take the pointer from.
     */
	void* getPortPointer(Port* port);

	/**
     *  @brief get pointer from a functiont
	 *
	 *  @return corresponding pointer from the function
     */
	void* getFunctionPointer(llvm::Function* function);

	InstancedFU* instanciate(Instance* instance, Decoder* decoder);
	void createParameters(Instance* instance, Decoder* decoder);
	std::list<Action*>* createActions(Instance* instance, std::list<Action*>* actions, std::map<std::string, Port*>* inputs, std::map<std::string, Port*>* outputs);
	std::list<Action*>* createInitializes(Instance* instance, std::list<Action*>* actions);
	Action* createAction(Instance* instance, Action* action, std::map<std::string, Port*>* inputs, std::map<std::string, Port*>* outputs);
	Procedure* CreateProcedure(Instance* instance, Procedure* procedure);
	llvm::Function* CreateFunction(Instance* instance, llvm::Function* function);
	llvm::GlobalVariable* CreateVariable(Instance* instance, llvm::GlobalVariable* variable);
	std::map<Variable*, llvm::GlobalVariable*>* createVariables(Instance* instance, std::map<std::string, Variable*>* vars);
	std::list<Procedure*>* createProcedures(Instance* instance, Decoder* decoder);
	ActionScheduler* createActionScheduler(Instance* instance, ActionScheduler* actionScheduler);
	FSM* createFSM(Instance* instance, FSM* fsm);
	std::map<Procedure*, llvm::Function*>* createProcedures(Instance* instance, std::map<std::string, Procedure*>* procs);

	void addPrintf(std::string text, Decoder* decoder, llvm::Function* function, llvm::BasicBlock* bb);

	/**
     *  @brief Verify decoder
	 *
	 *	Verify the coherence of the module and print error into the given file in
	 *	 case of error.
	 *
	 * @param file :  string name of the file to print error
	 *
	 * @param decoder	:  Decoder to verify
     */
	void verify(std::string file, Decoder* decoder);

	bool LinkGlobalInits(llvm::GlobalVariable* variable);

	bool LinkProcedureBody(llvm::Function* function);

	void LinkFunctionBody(llvm::Function *NewFunc, const llvm::Function *OldFunc,
		llvm::ValueMap<const llvm::Value*, llvm::Value*> &VMap,
                       bool ModuleLevelChanges,
					   llvm::SmallVectorImpl<llvm::ReturnInst*> &Returns,
					   AbstractFifo* fifo,
                       const char *NameSuffix = "", 
					   llvm::ClonedCodeInfo *CodeInfo = 0);

	
	std::map<std::string, Port*>* createPorts(Instance* instance, std::map<std::string, Port*>* ports);
	Port* createPort(Instance* instance, Port* port);

	void setDecoder(Decoder* decoder);
	llvm::Module* getModule(){return module;};
	void MapFunction(llvm::Function* function, void *Addr);
	void run(llvm::Function* func);
	void setNewInstance();
	void optimize(Decoder* decoder);

private:
	/** LLVM Context */
	llvm::LLVMContext &Context;

	int parseBitcode();
	void createInstance(Instance* instance, FuncUnit* fu);

	void getMetadataInformation(llvm::Module* module);

	void CopyGVAttributes(llvm::GlobalValue *DestGV, const llvm::GlobalValue *SrcGV);
	std::map<Port*, llvm::ConstantInt*>* createPattern(std::map<Port*, llvm::ConstantInt*>* pattern, std::map<std::string, Port*>* ports);
	void putAction(ActionTag* tag, Action* action);
	Action* getAction(Action* action);

	llvm::Module* module;
	Decoder* decoder;
	llvm::ExecutionEngine *EE;



	/** Help for linker */

	llvm::ValueToValueMapTy ValueMap;
	std::multimap<std::string, llvm::GlobalVariable *> AppendingVars;
	
	/** Help for opt */
	void AddOptimizationPasses(llvm::PassManagerBase &MPM, llvm::PassManagerBase &FPM,
                           unsigned OptLevel) ;
	void AddStandardLinkPasses(llvm::PassManagerBase &PM);
	void AddStandardCompilePasses(llvm::PassManagerBase &PM);
	void addPass(llvm::PassManagerBase &PM, llvm::Pass *P);
	void do_shutdown();
	
	/** list of actions of the current instance */
	std::map<std::string, Action*> actions;

	/** list of untagged actions of the current instance */
	std::list<Action*> untaggedActions;

	//Exit function
	llvm::Constant *Exit;

};

#endif