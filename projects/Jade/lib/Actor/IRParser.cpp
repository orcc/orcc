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
@brief Implementation of class IRParser
@author Jerome Gorin
@file IRParser.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <map>

#include "llvm/Constants.h"
#include "llvm/ValueSymbolTable.h"
#include "llvm/Metadata.h"
#include "llvm/DerivedTypes.h"
#include "llvm/ADT/StringRef.h"
#include "llvm/Support/CommandLine.h"

#include "llvm/Module.h"

#include "Jade/JIT.h"
#include "Jade/Actor/ActionScheduler.h"
#include "Jade/Actor/Action.h"
#include "Jade/Actor/ActionTag.h"
#include "Jade/Actor/Actor.h"
#include "Jade/Actor/FSM.h"
#include "Jade/Actor/IRParser.h"
#include "Jade/Actor/Port.h"
#include "Jade/Actor/StateVariable.h"
#include "Jade/Decoder/Procedure.h"


#include "IRConstant.h"
//------------------------------

using namespace std;
using namespace llvm;

extern cl::opt<std::string> VTLDir;


IRParser::IRParser(llvm::LLVMContext& C, JIT* jit) : Context(C){
	this->jit = jit;
}


Actor* IRParser::parseActor(string classz){
    size_t found=classz.find_last_of("/");;
	string file;

	if (found!=string::npos){
		file = classz.substr(found + 1);
	}else {
		file = classz;
	}
	
	Module* module = jit->LoadBitcode(file, VTLDir);

	if (module == 0){
		fprintf(stderr,"Error when parsing bytecode");
		exit(0);
	}

	symbolTable = &module->getMDSymbolTable();

	// Parse name
	NamedMDNode* nameNMD =  symbolTable->lookup(IRConstant::KEY_NAME);
	MDNode* nameMD =cast<MDNode>(nameNMD->getOperand(0));
	MDString* name = cast<MDString>(nameMD->getOperand(0));

	// Parse actor elements
	map<string, Type*>* fifos = parseFifos(module);
	map<string, Port*>* inputs = parsePorts(IRConstant::KEY_INPUTS, module);
	map<string, Port*>* outputs = parsePorts(IRConstant::KEY_OUTPUTS, module);
	map<string, Variable*>* parameters =  parseParameters(module);
	map<string, Variable*>* stateVars = parseStateVars(module);
	map<string, Procedure*>* procs = parseProcs(module);
	list<Action*>* initializes = parseActions(IRConstant::KEY_INITIALIZES, module);
	list<Action*>* actions = parseActions(IRConstant::KEY_ACTIONS, module);
	ActionScheduler* actionScheduler = parseActionScheduler(module);

	return new Actor(name->getString(), classz, fifos, inputs, outputs, stateVars, 
						parameters, procs, initializes, actions, actionScheduler);
}

map<string, Port*>* IRParser::parsePorts(string key, Module* module){
	map<string, Port*>* ports = new map<string, Port*>();
	
	NamedMDNode* inputsMD =  symbolTable->lookup(key);

	if (inputsMD == NULL) {
		return ports;
	}
	
	 for (unsigned i = 0, e = inputsMD->getNumOperands(); i != e; ++i) {
		 Port* port = parsePort(inputsMD->getOperand(i));
		 ports->insert(pair<string,Port*>(port->getName(), port));
	 }

	return ports;
}


map<string, Type*>* IRParser::parseFifos(Module* module){
	map<string,Type*>* fifos = new map<string,Type*>();
	std::string structName;

	structName = "struct.fifo_i8_s";
	fifos->insert(pair<string, Type*>(structName, parseFifo(structName, module)));

	structName = "struct.fifo_i32_s";
	fifos->insert(pair<string, Type*>(structName, parseFifo(structName, module)));

	structName = "struct.fifo_i16_s";
	fifos->insert(pair<string, Type*>(structName, parseFifo(structName, module)));

	return fifos;
}

Type* IRParser::parseFifo(std::string name, Module* module){
	Type* type = (Type*)module->getTypeByName(name);

	if (type == NULL){
		fprintf(stderr,"Structure %s hasn't been found in a parsed actor.", name.c_str());
		exit(0);
	}

	return type;
}


map<string, Procedure*>* IRParser::parseProcs(Module* module){
	map<string, Procedure*>* procedures = new map<string, Procedure*>();

	NamedMDNode* inputsMD =  symbolTable->lookup(IRConstant::KEY_PROCEDURES);

	if (inputsMD != NULL){
		for (unsigned i = 0, e = inputsMD->getNumOperands(); i != e; ++i) {
			
			//Parse a procedure
			Procedure* proc= parseProc(inputsMD->getOperand(i));
			procedures->insert(pair<string, Procedure*>(proc->getName(), proc));
		}
		
	}

	return procedures;
}

ActionScheduler* IRParser::parseActionScheduler(Module* module){
	NamedMDNode* inputsMD =  symbolTable->lookup(IRConstant::KEY_ACTION_SCHED);
	MDNode* actionSchedulerMD = cast<MDNode>(inputsMD->getOperand(0));
	Function* schedulerFunction = NULL;
	Function* initializeFunction = NULL;
	FSM* fsm = NULL;
	
	//Get fsm if present
	Value* fsmNode = actionSchedulerMD->getOperand(0);

	if (fsmNode != NULL){
		fsm = parseFSM(cast<MDNode>(fsmNode));
	}

	//Get function corresponding to the action scheduler
	schedulerFunction = cast<Function>(actionSchedulerMD->getOperand(1));

	//Get fsm if present
	if (actionSchedulerMD->getNumOperands() > 2){
		initializeFunction = cast<Function>(actionSchedulerMD->getOperand(2));
	}

	return new ActionScheduler(schedulerFunction, initializeFunction, fsm);
}

map<string, Variable*>* IRParser::parseParameters(Module* module){
	map<string, Variable*>* parameters = new map<string, Variable*>();
	
	NamedMDNode* inputsMD =  symbolTable->lookup(IRConstant::KEY_PARAMETERS);
	if (inputsMD != NULL){
		for (unsigned i = 0, e = inputsMD->getNumOperands(); i != e; ++i) {
			
			//Parse a parameter
			MDNode* parameterNode = cast<MDNode>(inputsMD->getOperand(i));
			MDNode* details = cast<MDNode>(parameterNode->getOperand(0));
			MDString* nameMD = cast<MDString>(details->getOperand(0));

			Type* type = parseType(cast<MDNode>(parameterNode->getOperand(1)));

			GlobalVariable* variable = cast<GlobalVariable>(parameterNode->getOperand(2));

			//Parse create parameter
			StateVar* parameter = new StateVar(NULL, type, nameMD->getString(), false, variable);

			parameters->insert(pair<string, Variable*>(nameMD->getString(), parameter));
		}
		
	}
	return parameters;
}


map<string, Variable*>*  IRParser::parseStateVars(Module* module){
	map<string, Variable*>* stateVars = new map<string, Variable*>();

	NamedMDNode* stateVarsMD =  symbolTable->lookup(IRConstant::KEY_STATE_VARS);
	
	if (stateVarsMD == NULL) {
		return stateVars;
	}
	
	for (unsigned i = 0, e = stateVarsMD->getNumOperands(); i != e; ++i) {
		 Variable* var = parseStateVar(stateVarsMD->getOperand(i));
		 stateVars->insert(pair<string,Variable*>(var->getName(), var));
	}

	return stateVars;
}


Variable* IRParser::parseStateVar(MDNode* node){
	// Parsing VarDef
	MDNode* varDefMD = cast<MDNode>(node->getOperand(0));
	
	MDString* name = cast<MDString>(varDefMD->getOperand(0));
	ConstantInt* assignable = cast<ConstantInt>(varDefMD->getOperand(1));

	IntegerType* type = (IntegerType*)parseType(cast<MDNode>(node->getOperand(1)));
	GlobalVariable* variable = cast<GlobalVariable>(node->getOperand(2));


	return new StateVar(NULL, type, name->getString(), assignable->getValue().getBoolValue(), variable);
}

list<Action*>* IRParser::parseActions(string key, Module* module){
	list<Action*>* actions = new list<Action*>();
	
	NamedMDNode* inputsMD =  symbolTable->lookup(key);

	if (inputsMD == NULL) {
		return actions;
	}

	for (unsigned i = 0, e = inputsMD->getNumOperands(); i != e; ++i) {
		Action* action = parseAction(inputsMD->getOperand(i));
		actions->push_back(action);
	}

	return actions;
}


Action* IRParser::parseAction(MDNode* node){
	Value* tagValue = node->getOperand(0);
	ActionTag* tag = new ActionTag();

	if (tagValue != NULL){
		MDNode* tagArray = cast<MDNode>(tagValue);
		for (unsigned i = 0, e = tagArray->getNumOperands(); i != e; ++i) {
			MDString* tagMD = cast<MDString>(tagArray->getOperand(i));
			tag->add(tagMD->getString());
		}
	}

	Procedure* scheduler = parseProc(cast<MDNode>(node->getOperand(1)));
	Procedure* body = parseProc(cast<MDNode>(node->getOperand(2)));
	Action* action = new Action(tag, scheduler, body);

	putAction(tag, action);

	return action;
}


Procedure* IRParser::parseProc(MDNode* node){
	MDString* name = cast<MDString>(node->getOperand(0));
	ConstantInt* isExtern = cast<ConstantInt>(node->getOperand(1));
	Function* function = cast<Function>(node->getOperand(2));

	return new Procedure(name->getString(), isExtern, function);
}



Port* IRParser::parsePort(MDNode* node){
	Type* type = (Type*)parseType(cast<MDNode>(node->getOperand(0)));
	MDString* name = cast<MDString>(node->getOperand(1));
	GlobalVariable* variable = cast<GlobalVariable>(node->getOperand(2));
	
	return new Port(NULL, name->getString(), (IntegerType*)type, variable);
}

Location* IRParser::parseLocation(MDNode* node){

	if (node->getNumOperands() == 3) {
		ConstantInt* startLine = cast<ConstantInt>(node->getOperand(0));
		ConstantInt* startCol = cast<ConstantInt>(node->getOperand(1));
		ConstantInt* endCol = cast<ConstantInt>(node->getOperand(2));

		return new Location(startLine, startCol, startCol);
	}
	
	return new Location();
}

Type* IRParser::parseType(MDNode* node){
	
	//Get size of the element
	ConstantInt* typeSize = cast<ConstantInt>(node->getOperand(0));
	Type* type = (Type*)IntegerType::get(Context, typeSize->getLimitedValue());
	
	//if operand 2 is not null, the element is a list
	Value* listSize = cast<Value>(node->getOperand(1));
	
	if (listSize != NULL){
		for (unsigned i = 1, e = node->getNumOperands(); i != e; ++i){
			ConstantInt* size = cast<ConstantInt>(node->getOperand(i));
			type = ArrayType::get(type, size->getLimitedValue());
		}
		
	}

	return type;
}


FSM* IRParser::parseFSM(llvm::MDNode* node){
	FSM* fsm = new FSM();
	list<Function*>* functions = new list<Function*>();

	//Parse initial state
	MDString* initialState = cast<MDString>(node->getOperand(0));
	
	//Parse all fsm state
	MDNode* stateArray = cast<MDNode>(node->getOperand(1));

	for (unsigned i = 0, e = stateArray->getNumOperands(); i != e; ++i){
		MDString* stateMD = cast<MDString>(stateArray->getOperand(i));
		fsm->addState(stateMD->getString());
	}

	// set the initial state after initializing the states
	fsm->setInitialState(initialState->getString());

	
	//Parse transition
	MDNode* transitionsArray = cast<MDNode>(node->getOperand(2));

	for (unsigned i = 0, e = transitionsArray->getNumOperands(); i != e; ++i){
		MDNode* transitionArray = cast<MDNode>(transitionsArray->getOperand(i));
		MDString* source = cast<MDString>(transitionArray->getOperand(0));


		Value* targetValue = transitionArray->getOperand(1);
		
		//In case of "undefined" state, no target are given
		if (targetValue != NULL){
			MDNode* targetsArray = cast<MDNode>(targetValue);		
			for (unsigned j = 0, f = targetsArray->getNumOperands() ; j != f; ++j){
				MDNode* targetArray = cast<MDNode>(targetsArray->getOperand(j));

				Action* action = getAction(cast<MDNode>(targetArray->getOperand(0)));
				MDString* target = cast<MDString>(targetArray->getOperand(1));
			
				
				fsm->addTransition(source->getString(), target->getString(), action);
			}
		}

		Function* stateScheduler = cast<Function>(transitionArray->getOperand(2));
		
		functions->push_back(stateScheduler);
	}
	
	fsm->setFunctions(functions);


	//Parse state variable
	GlobalVariable* fsmState = cast<GlobalVariable>(node->getOperand(3));
	fsm->setFsmState(fsmState);

	if (node->getNumOperands()>4){
		//Outside FSM function
		Function* outFsm = cast<Function>(node->getOperand(4));
		fsm->setOutFsm(outFsm);
	}

	return fsm;
}

Action* IRParser::getAction(llvm::MDNode* node) {
	//Get identifier of the action
	node = cast<MDNode>(node->getOperand(0));

	if (node->getNumOperands() == 0){
		// removes the first untagged action found
		list<Action*>::iterator it;
		
		it = untaggedActions.begin();
		untaggedActions.erase(it);
		return *it;
	}
	
	//Get identifiers of action tag
	map<std::string, Action*>::iterator it;
	ActionTag tag;
	
	for (unsigned i = 0, e = node->getNumOperands(); i != e; ++i){
		MDString* tagMD = cast<MDString>(node->getOperand(i));
		tag.add(tagMD->getString());
	}
	
	it = actions.find(tag.getIdentifier());
	
	return it->second;
}

void IRParser::putAction(ActionTag* tag, Action* action){
	if (tag->isEmpty()){
		untaggedActions.push_back(action);
	} else {
		actions.insert(pair<std::string, Action*>(tag->getIdentifier(), action));
	}

}