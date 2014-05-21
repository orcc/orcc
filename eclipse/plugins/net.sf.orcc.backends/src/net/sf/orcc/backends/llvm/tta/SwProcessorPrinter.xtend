/*
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.llvm.tta

import java.io.File
import java.util.Map
import net.sf.orcc.backends.llvm.aot.LLVMTemplate
import net.sf.orcc.backends.llvm.tta.architecture.Processor
import net.sf.orcc.df.Actor
import net.sf.orcc.util.OrccUtil

class SwProcessorPrinter extends LLVMTemplate {
	
	new(Map<String, Object> options) {
		super(options)
	}

	def print(Processor processor, String targetFolder) {
		val content = processor.print
		val file = new File(targetFolder + File::separator + processor.getName() + ".ll")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def private print(Processor processor)
		'''
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Declare and initialize FIFO variables 
	
		
		«FOR ram : processor.localRAMs + processor.sharedRAMs»
			«val addrSpace = processor.memToAddrSpaceIdMap.get(ram)»
			«FOR conn : ram.mappedConnections»
				@fifo_«conn.getAttribute("id").objectValue»_content = addrspace(«addrSpace») global [«conn.size» x «conn.sourcePort.type.doSwitch»] zeroinitializer
				@fifo_«conn.getAttribute("id").objectValue»_rdIndex = addrspace(«addrSpace») global i32 zeroinitializer
				@fifo_«conn.getAttribute("id").objectValue»_wrIndex = addrspace(«addrSpace») global i32 zeroinitializer
				
			«ENDFOR»
		«ENDFOR»
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Declare the scheduling function of each actor
		
		«FOR vertex : processor.mappedActors»
			declare void @«vertex.label»_scheduler()
		«ENDFOR»
		«FOR vertex : processor.mappedActors»
			«val actor = vertex.getAdapter(typeof(Actor))»
			«IF !actor.initializes.empty»
				declare void @«vertex.label»_initialize()
			«ENDIF»
		«ENDFOR»
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; The main function - A simple round-robin scheduler
		
		define void @main() noreturn nounwind {
		entry:
			«FOR vertex : processor.mappedActors»
				«val actor = vertex.getAdapter(typeof(Actor))»
				«IF !actor.initializes.empty»
					call void @«vertex.label»_initialize()
				«ENDIF»
			«ENDFOR»
			br label %loop
		
		loop:
			«FOR vertex : processor.mappedActors»
				call void @«vertex.label»_scheduler()
			«ENDFOR»
			br label %loop
		}
		'''

}