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
@brief Description of Jade console
@author Jerome Gorin
@file Console.cpp
@version 1.0
@date 18/07/2011
*/

//------------------------------
#include <iostream>

#include "console.h"
//------------------------------

using namespace std;
using namespace llvm;
using namespace llvm::cl;

extern cl::opt<std::string> VTLDir;
extern cl::opt<std::string> InputDir;
extern DecoderEngine* engine;

//Console control
map<int, Network*> networks;
pthread_t t1;


void parseConsole(string cmd){
		if (cmd == "A"){
			map<int, Network*>::iterator it;
			int id;

			//Select network
			cout << "Select the id of the network to verify : ";
			cin >> id;

			//Look for the network
			it = networks.find(id);
			if(it == networks.end()){
				cout << "No network loads at the given id.\n";
				return;
			}

			engine->verify(it->second, "error.txt");

		}else if (cmd == "C"){
			map<int, Network*>::iterator it;
			string file;
			int id;

			//Select network
			cout << "Select the id of the network to run : ";
			cin >> id;

			//Look for the network
			it = networks.find(id);
			if(it == networks.end()){
				cout << "No network loads at the given id.\n";
				return;
			}

			//Select network
			cout << "Select a new network : ";
			cin >> file;

			//Load network
			LLVMContext &Context = getGlobalContext();
			XDFParser xdfParser(VTLDir + file);
			Network* network = xdfParser.ParseXDF(Context);

			if (network == NULL){
				cout << "No network load. \n";
				return;
			}

			engine->reconfigure(it->second, network);
/*
			networks.erase(id);
			networks.insert(pair<int, Network*>(id, network));*/
	}else if (cmd == "L"){
			string file;
			int id;

			//Select the network file
			cout << "Select a network to load : ";
			cin >> file;
			
			//Load network
			LLVMContext &Context = getGlobalContext();
			XDFParser xdfParser(VTLDir + file);
			Network* network = xdfParser.ParseXDF(Context);


			if (network == NULL){
				cout << "No network load. \n";
				return;
			}

			//Store the network in an id
			cout << "Select an id for this network : ";
			cin >> id;
			networks.insert(pair<int, Network*>(id, network));

			engine->load(network, 3);
		}else if (cmd == "P"){
			map<int, Network*>::iterator it;
			string output;
			int id;

			//Select network
			cout << "Select the id of the network to run : ";
			cin >> id;

			//Look for the network
			it = networks.find(id);
			if(it == networks.end()){
				cout << "No network loads at the given id.\n";
				return;
			}

			//Select network
			cout << "Select an ouput file : ";
			cin >> output;

			engine->print(it->second, output);

		}else if (cmd == "R"){
			map<int, Network*>::iterator it;
			string input;
			int id;

			//Select network
			cout << "Select the id of the network to run : ";
			cin >> id;

			//Look for the network
			it = networks.find(id);
			if(it == networks.end()){
				cout << "No network loads at the given id.\n";
				return;
			}

			//Select network
			cout << "Select an input stimulus : ";
			cin >> input;

			engine->run(it->second, InputDir + input, &t1);

		}else if (cmd == "S"){
			map<int, Network*>::iterator it;
			string input;
			int id;

			//Select network
			cout << "Select the id of the network to stop : ";
			cin >> id;

			//Look for the network
			it = networks.find(id);
			if(it == networks.end()){
				cout << "No network loads at the given id.\n";
				return;
			}

			//Stop the given network
			engine->stop(it->second);

		}else if (cmd == "U"){
			map<int, Network*>::iterator it;
			string input;
			int id;

			//Select network
			cout << "Select the id of the network to unload : ";
			cin >> id;

			//Look for the network
			it = networks.find(id);
			if(it == networks.end()){
				cout << "No network loads at the given id.\n";
				return;
			}

			Network* network = it->second;
			engine->unload(network);

			networks.erase(id);

			delete network;

		}else if (cmd == "V"){
			map<int, Network*>::iterator it;
			string input;

			for (it = networks.begin(); it != networks.end(); it++){
				Network* network = it->second;
				cout << it->first << " : " << network->getName() << "\n";
			}
		} else if (cmd == "help"){ 
			cout << "Command line options :\n";
			cout << "A : verify a network\n";
			cout << "C : change a network to another\n";
			cout << "L : load a network \n";
			cout << "P : print a network \n";
			cout << "R : run a network \n";
			cout << "S : stop a network \n";
			cout << "U : unload a network \n";
			cout << "V : list view of the networks loads \n";
			cout << "X : exit console \n";
		}
}

void startConsole(){
	string cmdLine;

	while (cmdLine != "X"){
		cout << "Enter a command (help for documentation) : ";
		cin >> cmdLine;
		parseConsole(cmdLine);
	}
}