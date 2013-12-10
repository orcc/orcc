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
import net.sf.orcc.backends.llvm.tta.architecture.Processor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Port
import net.sf.orcc.df.Action
import net.sf.orcc.ir.Var
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.Arg
import org.eclipse.emf.common.util.EList

import static net.sf.orcc.backends.BackendsConstants.*


class SwActorPrinter extends InstancePrinter {
	
	Processor processor;
	
	new(Map<String, Object> options, Processor processor) {
		super(options)
		this.processor = processor
	}
	
	override protected getAddrSpace(Connection connection) {
		val id = processor.getAddrSpaceId(connection)
		if(id != null) {
			''' addrspace(«id»)'''
		}
	}
	
	override protected getProperties(Port port) {
		if(!outgoingPortMap.get(port).nullOrEmpty || incomingPortMap.get(port) != null) {
			''' volatile'''
		}
	}
	
	def private printNativeWrite(Port port, Var variable) {
		val innerType = (variable.type as TypeList).innermostType.doSwitch
		'''
		%tmp_«variable.name»_elt = getelementptr «variable.type.doSwitch»* «variable.print», i32 0, i1 0 
		%tmp_«variable.name» = load «innerType»* %tmp_«variable.name»_elt
		tail call void asm sideeffect "SIG_OUT_«port.name».LEDS", "ir"(«innerType» %tmp_«variable.name») nounwind
		'''
	}
	
	override protected printArchitecture() ''''''

	override protected printVectorizable(Action action) {
		isActionVectorizable = action.hasAttribute(VECTORIZABLE)
		val output = '''
		«IF isActionVectorizable»

		define internal «action.body.returnType.doSwitch» @«action.body.name»_vectorizable() «IF optionProfile»noinline «ENDIF»nounwind {
		entry:
			«FOR local : action.body.locals»
				«local.declare»
			«ENDFOR»
			«FOR port : action.outputPattern.ports.filter[native]»
				«action.outputPattern.getVariable(port).declare»
			«ENDFOR»
			«FOR port : action.inputPattern.ports.notNative»
				«port.loadVar(incomingPortMap.get(port), action.name)»
			«ENDFOR»
			«FOR port : action.outputPattern.ports.notNative»
				«FOR connection : outgoingPortMap.get(port)»
					«port.loadVar(connection, action.name)»
				«ENDFOR»
			«ENDFOR»
			br label %b«action.body.blocks.head.label»
		
		«FOR block : action.body.blocks»
			«block.doSwitch»
		«ENDFOR»
			«FOR port : action.inputPattern.ports.notNative»
				«val connection = incomingPortMap.get(port)»
				«port.updateVar(connection, action.inputPattern.numTokensMap.get(port), action.name)»
			«ENDFOR»
			«FOR port : action.outputPattern.ports.notNative»
				«FOR connection : outgoingPortMap.get(port)»
					«port.updateVar(connection, action.outputPattern.getNumTokens(port), action.name)»
				«ENDFOR»
			«ENDFOR»
			«FOR port : action.outputPattern.ports.filter[native]»
				«printNativeWrite(port, action.outputPattern.portToVarMap.get(port))»
			«ENDFOR»
			ret void
		}
		«ENDIF»	
		'''
		isActionVectorizable = false
		return output
	}

	override protected print(Action action) '''
		define internal «action.scheduler.returnType.doSwitch» @«action.scheduler.name»() nounwind {
		entry:
			«FOR local : action.scheduler.locals»
				«local.declare»
			«ENDFOR»
			«FOR port : action.peekPattern.ports.notNative»
				«port.loadVar(incomingPortMap.get(port), action.name)»
			«ENDFOR»
			br label %b«action.scheduler.blocks.head.label»
		
		«FOR block : action.scheduler.blocks»
			«block.doSwitch»
		«ENDFOR»
		}
		«IF !action.hasAttribute(VECTORIZABLE_ALWAYS)»

		define internal «action.body.returnType.doSwitch» @«action.body.name»() «IF optionProfile»noinline «ENDIF»nounwind {
		entry:
			«FOR local : action.body.locals»
				«local.declare»
			«ENDFOR»
			«FOR port : action.outputPattern.ports.filter[native]»
				«action.outputPattern.getVariable(port).declare»
			«ENDFOR»
			«FOR port : action.inputPattern.ports.notNative»
				«port.loadVar(incomingPortMap.get(port), action.name)»
			«ENDFOR»
			«FOR port : action.outputPattern.ports.notNative»
				«FOR connection : outgoingPortMap.get(port)»
					«port.loadVar(connection, action.name)»
				«ENDFOR»
			«ENDFOR»
			br label %b«action.body.blocks.head.label»
		
		«FOR block : action.body.blocks»
			«block.doSwitch»
		«ENDFOR»
			«FOR port : action.inputPattern.ports.notNative»
				«val connection = incomingPortMap.get(port)»
				«port.updateVar(connection, action.inputPattern.numTokensMap.get(port), action.name)»
			«ENDFOR»
			«FOR port : action.outputPattern.ports.notNative»
				«FOR connection : outgoingPortMap.get(port)»
					«port.updateVar(connection, action.outputPattern.getNumTokens(port), action.name)»
				«ENDFOR»
			«ENDFOR»
			«FOR port : action.outputPattern.ports.filter[native]»
				«printNativeWrite(port, action.outputPattern.portToVarMap.get(port))»
			«ENDFOR»
			
			«FOR port : action.inputPattern.ports.notNative»
				«val connection = incomingPortMap.get(port)»
				call void @read_end_«port.name»_«connection.getSafeId(port)»()
			«ENDFOR»
			«FOR port : action.outputPattern.ports.notNative»
				«FOR connection : outgoingPortMap.get(port)»
					call void @write_end_«port.name»_«connection.getSafeId(port)»()
				«ENDFOR»
			«ENDFOR»
			ret void
		}
		«ENDIF»
		«action.printVectorizable»
	'''
	
	override caseInstCall(InstCall call) '''
		«val target = call.target»
		«val args = call.arguments»
		«val parameters = call.procedure.parameters»
		«IF call.procedure.native»
			«IF target != null»%«target.variable.name» = «ENDIF»tail call «call.procedure.returnType.doSwitch» asm sideeffect "ORCC_FU.«call.procedure.name.toUpperCase»", "«IF target != null»=ir, «ENDIF»ir«args.ir»"(i32 0«IF !args.nullOrEmpty», «args.format(parameters).join(", ")»«ENDIF») nounwind
		«ELSE»
			«super.caseInstCall(call)»
		«ENDIF»
	'''
	
	override protected print(Procedure procedure) '''
		«IF !procedure.native»
			«super.print(procedure)»
		«ENDIF»
	'''
	
	def private getIr(EList<Arg> args) {
		var irs = new String;
		for (arg : args) {
			irs = irs + ", ir"
		}
		return irs
	}

}