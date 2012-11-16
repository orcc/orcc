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
import java.io.File

class ModelSim_Wave extends TTAPrinter {
	
	def print(Design design, String targetFolder) {
		val file = new File(targetFolder + File::separator + "wave.do")
		printFile(doSwitch(design), file)
	}
	
	override caseDesign(Design design) 
		'''
		onerror {resume}
		quietly WaveActivateNextPane {} 0
		add wave -noupdate -divider <NULL>
		add wave -noupdate -divider Top
		add wave -noupdate -divider <NULL>
		add wave -noupdate -format Logic /tb_top/clk
		add wave -noupdate -format Logic /tb_top/rst_n
		
		«FOR port: design.inputs + design.outputs»
			«port.printNetworkPort»
		«ENDFOR»
		
		«FOR port: design.processors»
			«port.printProcessorWave(true)»
		«ENDFOR»
		
		«printOptions»
		'''
		
	override caseProcessor(Processor processor)
		'''
		onerror {resume}
		quietly WaveActivateNextPane {} 0
		
		«processor.printProcessorWave(false)»
		
		«printOptions»
		'''
		
			
	def	printNetworkPort(Port port) 
		'''
		add wave -noupdate -format Literal /tb_top/top_orcc/«port.label»
		'''
		
	def	printProcessorWave(Processor processor, boolean choosePrefix)
		'''
		add wave -noupdate -divider \<NULL\>
		add wave -noupdate -divider «processor.name»
		add wave -noupdate -divider inputs
		«FOR edge: processor.incoming»
			«val Link link = edge as Link»
			«link.printProcessorPortWave(processor, link.targetPort, choosePrefix)»
		«ENDFOR»
		add wave -noupdate -divider outputs
		«FOR edge: processor.outgoing»
			«val Link link = edge as Link»
			«link.printProcessorPortWave(processor, link.sourcePort, choosePrefix)»
		«ENDFOR»
		'''
		
	def	printProcessorPortWave(Link link, Processor processor, Port port, boolean choosePrefix) 
		'''
		«IF(!link.signal)»
		add wave -noupdate -format Literal -radix decimal «IF(choosePrefix)»tb_top/top_orcc«ELSE»/tb_«processor.name»«ENDIF»/«processor.name»_inst/fu_<port.name>_dmem_data_in
		add wave -noupdate -format Literal -radix decimal «IF(choosePrefix)»tb_top/top_orcc«ELSE»/tb_«processor.name»«ENDIF»/«processor.name»_inst/fu_<port.name>_dmem_data_out
		add wave -noupdate -format Literal -radix decimal «IF(choosePrefix)»tb_top/top_orcc«ELSE»/tb_«processor.name»«ENDIF»/«processor.name»_inst/fu_<port.name>_dmem_addr
		add wave -noupdate -format Logic «IF(choosePrefix)»tb_top/top_orcc«ELSE»/tb_«processor.name»«ENDIF»/«processor.name»_inst/fu_<port.name>_dmem_wr_en
		add wave -noupdate -format Literal «IF(choosePrefix)»tb_top/top_orcc«ELSE»/tb_«processor.name»«ENDIF»/«processor.name»_inst/fu_<port.name>_dmem_bytemask<
		«ELSE»
		add wave -noupdate -format Literal «IF(choosePrefix)»tb_top/top_orcc«ELSE»/tb_«processor.name»«ENDIF»/«processor.name»_inst/fu_<port.name>
		«ENDIF»
		'''
		
	def printOptions()
		'''
		TreeUpdate [SetDefaultTree]
		WaveRestoreCursors {{Cursor 1} {112 ps} 0}
		configure wave -namecolwidth 222
		configure wave -valuecolwidth 100
		configure wave -justifyvalue left
		configure wave -signalnamewidth 1
		configure wave -snapdistance 10
		configure wave -datasetprefix 0
		configure wave -rowmargin 4
		configure wave -childrowmargin 2
		configure wave -gridoffset 0
		configure wave -gridperiod 1
		configure wave -griddelta 40
		configure wave -timeline 0
		configure wave -timelineunits ns
		update
		WaveRestoreZoom {0 ps} {2911 ps}
		'''
}