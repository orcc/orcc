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

import java.util.Map
import net.sf.orcc.backends.llvm.aot.InstancePrinter
import net.sf.orcc.df.Action
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Port
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.Var
import net.sf.orcc.ir.InstCall

class LLVM_Actor extends InstancePrinter {
	
	Map<Port, Integer> portToIdMap;
	
	new(Instance instance, boolean optionProfile, Map<Port, Integer> portToIdMap) {
		super(instance, optionProfile)
		this.portToIdMap = portToIdMap
	}
	
	override printAddrSpace(Port port) ''' addrspace(«portToIdMap.get(port)»)'''
	
	override printProperties(Port port) ''' volatile'''
	
	def printNativeWrite(Port port, Var variable) {
		val innerType = (variable.type as TypeList).innermostType.doSwitch
		'''
		%tmp_«variable.name»_elt = getelementptr «variable.type.doSwitch»* «variable.print», i32 0, i1 0 
		%tmp_«variable.name» = load «innerType»* %tmp_«variable.name»_elt
		tail call void asm sideeffect "SIG_OUT_«port.name».LEDS", "ir"(«innerType» %tmp_«variable.name») nounwind
		'''
	}
	
	override printArchitecture() ''''''

	override print(Action action) '''
		define internal i1 @«action.scheduler.name»() nounwind {
		entry:
			«FOR local : action.scheduler.locals»
				«local.variableDeclaration»
			«ENDFOR»
			«FOR port : action.peekPattern.ports.filter[ ! native]»
				«port.fifoVar»
			«ENDFOR»
			br label %b«action.scheduler.blocks.head.label»
		
		«FOR block : action.scheduler.blocks»
			«block.doSwitch»
		«ENDFOR»
		}
		
		define internal void @«action.body.name»() nounwind {
		entry:
			«FOR local : action.body.locals»
				«local.variableDeclaration»
			«ENDFOR»
			«FOR port : action.outputPattern.ports.filter[ native ]»
				«action.outputPattern.getVariable(port).variableDeclaration»
			«ENDFOR»
			«FOR port : action.inputPattern.ports.filter[ ! native] + action.outputPattern.ports.filter[ ! native]»
				«port.fifoVar»
			«ENDFOR»
			br label %b«action.body.blocks.head.label»
		
		«FOR block : action.body.blocks»
			«block.doSwitch»
		«ENDFOR»
			«FOR port : action.inputPattern.ports.filter[ ! native]»
				«printFifoEnd(port, action.inputPattern.numTokensMap.get(port))»
				call void @read_end_«port.name»()
			«ENDFOR»
			«FOR port : action.outputPattern.ports.filter[ ! native]»
				«printFifoEnd(port, action.outputPattern.numTokensMap.get(port))»
				call void @write_end_«port.name»()
			«ENDFOR»
			«FOR port : action.outputPattern.ports.filter[ native ]»
				«printNativeWrite(port, action.outputPattern.portToVarMap.get(port))»
			«ENDFOR»
			ret void
		}
	'''
	
	override caseInstCall(InstCall call) '''
		«IF call.print»
			call i32 (i8*, ...)* @printf(«call.parameters.join(", ", [printParameter])»)
		«ELSEIF call.procedure.native»
			«IF call.target != null»%«call.target.variable.indexedName» = «ENDIF»tail call void asm sideeffect "ORCC_FU.«call.procedure.name.toUpperCase»", "ir,ir"(i32 0«IF call.parameters != null», «formatParameters(call.procedure.parameters, call.parameters).join(", ")»«ENDIF») nounwind
		«ELSE»
			«IF call.target != null»%«call.target.variable.indexedName» = «ENDIF»call «call.procedure.returnType.doSwitch» @«call.procedure.name» («formatParameters(call.procedure.parameters, call.parameters).join(", ")»)
		«ENDIF»
	'''
}