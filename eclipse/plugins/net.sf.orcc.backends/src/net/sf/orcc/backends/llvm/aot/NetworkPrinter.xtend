/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
 * about
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
package net.sf.orcc.backends.llvm.aot

import java.util.Map
import net.sf.orcc.backends.BackendsConstants
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Network

/*
 * Compile Network LLVM source code 
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends LLVMTemplate {

	private var Network network;
	protected var optionDatalayout = BackendsConstants::LLVM_DEFAULT_TARGET_DATALAYOUT
	protected var optionArch = BackendsConstants::LLVM_DEFAULT_TARGET_TRIPLE

	def setNetwork(Network network) {
		this.network = network
	}

	override setOptions(Map<String, Object> options) {
		super.setOptions(options)

		if (options.containsKey(BackendsConstants::LLVM_TARGET_TRIPLE)) {
			optionArch = options.get(BackendsConstants::LLVM_TARGET_TRIPLE) as String
		}
		if (options.containsKey(BackendsConstants::LLVM_TARGET_DATALAYOUT)) {
			optionDatalayout = options.get(BackendsConstants::LLVM_TARGET_DATALAYOUT) as String
		}
	}

	def getNetworkFileContent() '''
		target datalayout = "«optionDatalayout»"
		target triple = "«optionArch»"
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Declare and initialize FIFO variables 
		
		declare void @init_orcc(i32 %argc, i8** %argv)
		
		@network = global i32 zeroinitializer
		
		«FOR conn : network.connections»
			@fifo_«conn.getAttribute("id").objectValue»_content = global [«conn.safeSize» x «conn.sourcePort.type.doSwitch»] zeroinitializer
			@fifo_«conn.getAttribute("id").objectValue»_rdIndex = global i32 zeroinitializer
			@fifo_«conn.getAttribute("id").objectValue»_wrIndex = global i32 zeroinitializer
			
		«ENDFOR»
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Declare the scheduling function of each actor
		
		«FOR instance : network.children.actorInstances»
			declare void @«instance.name»_scheduler()
			«IF ! instance.getActor.initializes.empty»
				declare void @«instance.name»_initialize()
			«ENDIF»
		«ENDFOR»
		«FOR actor : network.children.filter(typeof(Actor))»
			declare void @«actor.name»_scheduler()
			«IF ! actor.initializes.empty»
				declare void @«actor.name»_initialize()
			«ENDIF»
		«ENDFOR»
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; The main function - A simple round-robin scheduler
		
		define void @main(i32 %argc, i8** %argv) noinline noreturn nounwind {
		entry:
			call void @init_orcc(i32 %argc, i8** %argv);
			«FOR instance : network.children.actorInstances»
				«IF ! instance.getActor.initializes.empty»
					call void @«instance.name»_initialize()
				«ENDIF»
			«ENDFOR»
			«FOR actor : network.children.filter(typeof(Actor))»
				«IF ! actor.initializes.empty»
					call void @«actor.name»_initialize()
				«ENDIF»
			«ENDFOR»
			br label %loop
		
		loop:
			«FOR child : network.children»
				call void @«child.label»_scheduler()
			«ENDFOR»
			br label %loop
		}
	'''

}
