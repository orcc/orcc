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

import net.sf.orcc.backends.llvm.tta.architecture.Design
import net.sf.orcc.backends.llvm.tta.architecture.Link
import net.sf.orcc.backends.llvm.tta.architecture.Port
import net.sf.orcc.backends.llvm.tta.architecture.Processor
import net.sf.orcc.backends.util.FPGA

class Python_Design extends TTAPrinter {
	
	private FPGA fpga;
	
	new(FPGA fpga) {
		this.fpga = fpga;
	}
	
	override caseDesign(Design design) 
		'''
		# -*- coding: utf-8 -*-
		#
		# Generated from <design.name> using Open-RVC CAL Compiler
		#
		
		from orcc_ import *
		
		«FOR processor: design.processors»
			«processor.initProcessor»
		«ENDFOR»
		
		## Processors initialization
		processors = [
			«FOR processor: design.processors SEPARATOR ','»
				«processor.doSwitch»
			«ENDFOR»
		]
		
		## Network initialization
		design = Design("«design.name»", processors, «IF(fpga.altera)»True«ELSE»False«ENDIF»)
		'''
		
	override caseProcessor(Processor processor)
		'''Processor("«processor.name»", «processor.name»_instances, «processor.name»_inputs, «processor.name»_outputs)'''
		
	def printPort(Port port, int index) 
		'''Port("«port.label»", «index»«IF(port.native)», True, «port.size»«ENDIF»)'''

	def initProcessor(Processor processor)
		'''
		«processor.name»_inputs = [«FOR edge: processor.incoming SEPARATOR ', '»«(edge as Link).targetPort.printPort(0)»«ENDFOR»]
		«processor.name»_outputs = [«FOR edge: processor.outgoing SEPARATOR ', '»«(edge as Link).sourcePort.printPort(0)»«ENDFOR»]
		«processor.name»_instances = [«FOR instance: processor.mappedActors SEPARATOR ', '»"«instance.name»"«ENDFOR»]
		'''

	
}