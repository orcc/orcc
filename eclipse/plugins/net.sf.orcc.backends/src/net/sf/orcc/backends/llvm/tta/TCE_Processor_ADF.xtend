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

import net.sf.orcc.backends.llvm.tta.architecture.Bus
import net.sf.orcc.backends.llvm.tta.architecture.ExprBinary
import net.sf.orcc.backends.llvm.tta.architecture.ExprFalse
import net.sf.orcc.backends.llvm.tta.architecture.ExprTrue
import net.sf.orcc.backends.llvm.tta.architecture.ExprUnary
import net.sf.orcc.backends.llvm.tta.architecture.FuPort
import net.sf.orcc.backends.llvm.tta.architecture.FunctionUnit
import net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit
import net.sf.orcc.backends.llvm.tta.architecture.Guard
import net.sf.orcc.backends.llvm.tta.architecture.Memory
import net.sf.orcc.backends.llvm.tta.architecture.Operation
import net.sf.orcc.backends.llvm.tta.architecture.Processor
import net.sf.orcc.backends.llvm.tta.architecture.Reads
import net.sf.orcc.backends.llvm.tta.architecture.RegisterFile
import net.sf.orcc.backends.llvm.tta.architecture.Resource
import net.sf.orcc.backends.llvm.tta.architecture.Segment
import net.sf.orcc.backends.llvm.tta.architecture.ShortImmediate
import net.sf.orcc.backends.llvm.tta.architecture.Socket
import net.sf.orcc.backends.llvm.tta.architecture.TermBool
import net.sf.orcc.backends.llvm.tta.architecture.TermUnit
import net.sf.orcc.backends.llvm.tta.architecture.Writes
import net.sf.orcc.backends.llvm.tta.architecture.util.ArchitectureSwitch

class TCE_Processor_ADF extends ArchitectureSwitch<CharSequence> {
		
	def print(Processor processor)
		'''
		<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
		<adf version="1.7">
			«FOR bus: processor.buses»
				«bus.printBus»
			«ENDFOR»
			«FOR socket: processor.sockets»
				«socket.print»
			«ENDFOR»
			«FOR fu: processor.functionUnits»
				«fu.print»
			«ENDFOR»
			«FOR rf: processor.registerFiles»
				«rf.print»
			«ENDFOR»
			«processor.ROM.print(processor, false)»
			«FOR ram: processor.localRAMs»
				«ram.print(processor, true)»
			«ENDFOR»
			«FOR ram: processor.sharedRAMs»
				«ram.print(processor, true)»
			«ENDFOR»
			«processor.gcu?.print»
		</adf>
		'''
	
	def printBus(Bus bus) 
		'''
		<bus name="«bus.name»">
			<width>«bus.width»</width>
			«FOR guard: bus.guards»
				<guard>
					«guard.doSwitch»
				</guard>
			«ENDFOR»
			«FOR segment: bus.segments»
				«segment.print»
			«ENDFOR»
			«bus.shortImmediate.print»
		</bus>
		'''
	
	override caseExprBinary(ExprBinary expr) 
		'''
		«IF(expr.and)»<and-expr>«ELSE»<or-expr>«ENDIF»
			«expr.e1.doSwitch»
			«expr.e2.doSwitch»
		«IF(expr.and)»</and-expr>«ELSE»</or-expr>«ENDIF»
		'''
	
	override caseExprUnary(ExprUnary expr) 
		'''
		«IF(expr.simple)»<simple-expr>«ELSE»<inverted-expr>«ENDIF»
			«expr.term.doSwitch»
		«IF(expr.simple)»</simple-expr>«ELSE»</inverted-expr>«ENDIF»
		'''
	
	override caseExprFalse(ExprFalse object) 
		'''
		<always-false/>
		'''
	
	override caseExprTrue(ExprTrue object) 
		'''
		<always-true/>
		'''
		
	override caseTermBool(TermBool term)
		'''
		<bool>
			<name>«term.register.name»</name>
			<index>«term.index»</index>
		</bool>
		'''
	
	override caseTermUnit(TermUnit term)
		'''
		<unit>
			<name>«term.functionUnit.name»</name>
			<port>«term.port.name»</port>
		</unit>
		'''
		
	override caseReads(Reads element)
		'''
		«val Operation op = element.eContainer as Operation»
		<reads name="«op.portToIndexMap.get(element.port)»">
			<start-cycle>«element.startCycle»</start-cycle>
			<cycles>«element.cycles»</cycles>
		</reads>
		'''

	override caseWrites(Writes element) 
		'''
		«val Operation op = element.eContainer as Operation»
		<writes name="«op.portToIndexMap.get(element.port)»">
			<start-cycle>«element.startCycle»</start-cycle>
			<cycles>«element.cycles»</cycles>
		</writes>
		'''

	override caseResource(Resource element)
		'''
		<resource name="«element.name»">
			<start-cycle>«element.startCycle»</start-cycle>
			<cycles>«element.cycles»</cycles>
		</resource>
		'''
	
	
	def print(FuPort port, boolean needWidth, boolean isSpecial) 
		'''
		<«IF(isSpecial)»special-«ENDIF»port name="«port.name»">
			«port.inputSocket?.connect»
			«port.outputSocket?.connect»
			«IF(needWidth)»<width>«port.width»</width>«ENDIF»
			«IF(port.trigger)»<triggers/>«ENDIF»
			«IF(port.opcodeSelector)»<sets-opcode/>«ENDIF»
		</«IF(isSpecial)»special-«ENDIF»port>
		'''
	
	def print(FunctionUnit fu) 
		'''
		<function-unit name="«fu.name»">
			«FOR port: fu.ports»
				«port.print(true, false)»
			«ENDFOR»
			«FOR operation: fu.operations»
				«operation.print(false)»
			«ENDFOR»
			«fu.addressSpace.connect»
		</function-unit>
		'''
	
	def print(GlobalControlUnit gcu) 
		'''
		<global-control-unit name="«gcu.name»">
			«FOR port: gcu.ports»
				«port.print(true, false)»
			«ENDFOR»
			«gcu.returnAddress.print(true, true)»
			<return-address>«gcu.returnAddress.name»</return-address>
			«FOR operation: gcu.operations»
				«operation.print(true)»
			«ENDFOR»
			<address-space>«gcu.addressSpace.name»</address-space>
			<delay-slots>«gcu.delaySlots»</delay-slots>
			<guard-latency>«gcu.guardLatency»</guard-latency>
		</global-control-unit>
		'''
	
	def print(Guard guard) 
		'''
		<guard>
			<printExpression(guard)>
		</guard>
		'''
	
	def print(Operation operation, boolean isControl) 
		'''
		<«IF(isControl)»ctrl-«ENDIF»operation>
			<name>«operation.name»</name>
			«FOR port: operation.ports»
				<bind name="«operation.portToIndexMap.get(port)»">«port.name»</bind>
			«ENDFOR»
			<pipeline>
				«FOR element: operation.pipeline»
					«element.doSwitch»
				«ENDFOR»
			</pipeline>
		</«IF(isControl)»ctrl-«ENDIF»operation>
		'''
	
	def print(RegisterFile rf) 
		'''
		<register-file name="«rf.name»">
			<type>normal</type>
			<size>«rf.size»</size>
			<width>«rf.width»</width>
			<max-reads>«rf.maxReads»</max-reads>
			<max-writes>«rf.maxWrites»</max-writes>
			«FOR port: rf.ports»
				«port.print(false, false)»
			«ENDFOR»
		</register-file>
		'''
	
	def print(Socket socket) 
		'''
		<socket name="«socket.name»">
			«FOR segment: socket.connectedSegments»
				«segment.connect(socket)»
			«ENDFOR»
		</socket>
		'''
	
	def print(Memory addressSpace, Processor processor, boolean isRAM) 
		'''
		<address-space name="«addressSpace.name»">
			<width>«addressSpace.wordWidth»</width>
			<min-address>«addressSpace.minAddress»</min-address>
			<max-address>«addressSpace.maxAddress»</max-address>
			<shared-memory>«addressSpace.shared»</shared-memory>
			«IF(isRAM)»<numerical-id>«processor.memToAddrSpaceIdMap.get(addressSpace)»</numerical-id>«ENDIF»
		</address-space>
		'''
		
	def print(Segment segment) 
		'''
		<segment name="«segment.name»">
			<writes-to/>
		</segment>
		'''

	def print(ShortImmediate shortImmediate) 
		'''
		<short-immediate>
			<extension>zero</extension>
			<width>«shortImmediate.width»</width>
		</short-immediate>
		'''
		
	def connect(Segment segment, Socket socket)
		'''
		«IF(socket.input)»<reads-from>«ELSE»<writes-to>«ENDIF»
			<bus>«segment.bus.name»</bus>
			<segment>«segment.name»</segment>
		«IF(socket.input)»</reads-from>«ELSE»</writes-to>«ENDIF»
		'''
		
	def connect(Socket socket)
		'''
		<connects-to>«socket.name»</connects-to>
		'''
		
	def connect(Memory addressSpace)
		'''
		«IF(addressSpace == null)»<address-space/>«ELSE»<address-space>«addressSpace.name»</address-space>«ENDIF»
		'''
}