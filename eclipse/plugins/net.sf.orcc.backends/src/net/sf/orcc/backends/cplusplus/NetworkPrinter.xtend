/*
 * Copyright (c) 2012, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
 
 package net.sf.orcc.backends.cplusplus



/*
 * A network printer.
 *  
 * @author Ghislain Roquier
 * 
 */
import java.util.List
import java.util.Map
import net.sf.orcc.backends.cplusplus.entities.Communicator
import net.sf.orcc.backends.cplusplus.entities.Interface
import net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet
import net.sf.orcc.backends.cplusplus.entities.Receiver
import net.sf.orcc.backends.cplusplus.entities.Sender
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network

class NetworkPrinter extends ExprAndTypePrinter {
	
	EntitiesPrinter entitiesPrinter
	
	List<Interface> interfaces
	
	new () {
		entitiesPrinter = new EntitiesPrinter
		interfaces = newArrayList;
	}
	
	def compileNetwork(Network network, Map options) '''
		#include <map>
		#include <string>

		#include "config_parser.h"
		#include "fifo.h"
		#include "get_opt.h"
		#include "thread_pool.h"

		#ifdef _WIN32
		#undef IN
		#undef OUT
		#endif
		
		«FOR instance : network.children.filter(typeof(Instance))»
			 #include "«instance.name».h"
		«ENDFOR»
		«IF network.children.filter(typeof(Instance)).findFirst(i | i.entity instanceof Sender) != null»
			#include "sender.h" #include "ethernet.h"
		«ENDIF»
		«IF network.children.filter(typeof(Instance)).findFirst(i | i.entity instanceof Receiver) != null»
			#include "receiver.h" #include "ethernet.h"
		«ENDIF»

		«FOR instance : network.children.filter(typeof(Instance)).filter(i | i.entity instanceof Communicator)»
			«(instance.entity as Communicator).compileCommunicator(instance.name)»
		«ENDFOR»
		
		«FOR instance : network.children.filter(typeof(Instance))»
			«instance.name» inst_«instance.name»;
		«ENDFOR»

		«FOR instance : network.children.filter(typeof(Instance))»
			«FOR edges : instance.outgoingPortMap.values»
				Fifo<«edges.get(0).sourcePort.type.doSwitch», «edges.get(0).getAttribute("nbReaders").pojoValue»> fifo_«edges.get(0).getAttribute("idNoBcast").pojoValue»«IF edges.get(0).size!= null»(«edges.get(0).size»)«ENDIF»;
			«ENDFOR»
		«ENDFOR»

		int main(int argc, char *argv[]) {
			GetOpt(argc, argv).getOptions();
			
			std::map<std::string, Actor*> actors;
			«FOR instance : network.children.filter(typeof(Instance))»
				actors["«FOR seg : instance.hierarchicalId»/«seg»«ENDFOR»"] = &inst_«instance.name»;
			«ENDFOR»

			«FOR e : network.connections»
				inst_«(e.source as Instance).name».port_«e.sourcePort.name» = &fifo_«e.getAttribute("idNoBcast").pojoValue»;
				inst_«(e.target as Instance).name».port_«e.targetPort.name» = &fifo_«e.getAttribute("idNoBcast").pojoValue»;
			«ENDFOR»
						
			ConfigParser parser(config_file, actors);
			ThreadPool& pool = parser.getThreadPool();

			pool.start(0);
			
			«FOR instance : network.children.filter(typeof(Instance)).filter(i | i.entity instanceof Communicator)»
				«instance.name».start(0);
			«ENDFOR»

			pool.wait();
			
			pool.cancel();
			
			return 0;
		}
	'''
	
	def dispatch compileCommunicator(Receiver receiver, String name) {
		val interface = interfaces.findFirst(intf | intf.equals(receiver.intf))
		'''
		«IF interface == null»
			«receiver.intf.compileInterface»
		«ENDIF»
		Receiver<«receiver.output.type.doSwitch»> inst_«name»(&«receiver.intf.id»);
		'''
	}
	
	def dispatch compileCommunicator(Sender sender, String name) {		
		val interface = interfaces.findFirst(intf | intf.equals(sender.intf))
		'''
		«IF interface == null»
			«sender.intf.compileInterface»
		«ENDIF»
		Sender<«sender.input.type.doSwitch»> inst_«name»(&«sender.intf.id»);
		'''
	}

	def dispatch compileInterface(Interface intf) {
	}
	
	def dispatch compileInterface(InterfaceEthernet intf) {
		interfaces.add(intf) '''
		«IF intf.server»
			TcpServerSocket «intf.id»(«intf.portNumber»);
		«ELSE»
			TcpClientSocket «intf.id»(«intf.ip», «intf.portNumber»);
		«ENDIF»
	'''
	}
	
	def compileCmakeLists(Network network, Map options) '''
		cmake_minimum_required (VERSION 2.8)
		project («network.simpleName»)
		find_package(Threads REQUIRED)
		if(NOT NO_DISPLAY)
		find_package(SDL REQUIRED)
		endif()
		
		if(MSVC)
		set(CMAKE_CXX_FLAGS_DEBUG "/D_DEBUG /MTd /ZI /Ob0 /Od /RTC1")
		set(CMAKE_CXX_FLAGS_RELEASE "/MT /O2 /Ob2 /D NDEBUG")
		endif()

		if(CMAKE_COMPILER_IS_GNUCXX)
		set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -O0 -g")
		set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -O3")
		endif()

		set(YACE_INCLUDE_DIR ./libs/yace/include)
		set(TINYXML_INCLUDE_DIR ./libs/tinyxml/include)
		subdirs(./libs)

		include_directories(${YACE_INCLUDE_DIR} ${TINYXML_INCLUDE_DIR})
		if(NOT NO_DISPLAY)
		include_directories(${SDL_INCLUDE_DIR})
		endif()

		add_executable («network.simpleName»
		«network.simpleName».cpp
		«FOR instance : network.children.filter(typeof(Instance))»
			«instance.name».h
		«ENDFOR»
		)

		set(libraries Yace TinyXml)
		if(NOT NO_DISPLAY)
		set(libraries ${libraries} ${SDL_LIBRARY})
		endif()
		set(libraries ${libraries} ${CMAKE_THREAD_LIBS_INIT})
		target_link_libraries(«network.simpleName» ${libraries})
	'''
}
