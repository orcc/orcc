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
import net.sf.orcc.backends.llvm.tta.architecture.Processor
import net.sf.orcc.backends.llvm.tta.architecture.util.ArchitectureSwitch
import net.sf.orcc.backends.util.FPGA

class ModelSim_Script extends ArchitectureSwitch<CharSequence> {
	
	private FPGA fpga;
	
	new(FPGA fpga) {
		this.fpga = fpga;
	}
	
	override caseDesign(Design design) 
		'''
		«initializeLibraries(true)»

		«compileOtherComponents(true)»

		«compileInterfaceComponents(true)»
		
		«compileSharedComponent(true)»
		
		«FOR processor:design.processors»
			«processor.compileProcessor(true)»
		«ENDFOR»
		
		# Network
		vcom -93 -quiet -work work top.vhd
		vcom -93 -quiet -work work top_tb.vhd
		
		# Simulate
		vsim -novopt «IF(fpga.altera)»-L altera_mf «ENDIF»work.tb_top -t ps -do "do wave.do;"
		'''
		
	override caseProcessor(Processor processor)
		'''
		«initializeLibraries(false)»
		
		«compileOtherComponents(false)»

		«compileInterfaceComponents(false)»
		
		«compileSharedComponent(false)»
		
		«compileProcessor(processor, false)»
		
		vcom -93 -quiet -work work simulation/<processor.name>_tb.vhd
		
		# Simulate
		vsim -novopt  «IF(fpga.altera)»-L altera_mf «ENDIF»work.tb_«processor.name» -t ps -do "do simulation/wave.do;"
		'''

def initializeLibraries(boolean callByNetwork) 
	'''
	# Remove old libraries
	vdel -all -lib work
	«IF(fpga.altera)»vdel -all -lib altera_mf«ENDIF»
	
	# Create Working library
	vlib work
	
	«IF(fpga.altera)»
	# Create and compile Altera library
	vlib altera_mf
	vmap altera_mf altera_mf
	vcom -quiet -opt=-clkOpt -work altera_mf -93 -explicit «IF(!callByNetwork)»../«ENDIF»simulation/altera_mf_components.vhd
	vcom -quiet -opt=-clkOpt -work altera_mf -93 -explicit «IF(!callByNetwork)»../«ENDIF»simulation/altera_mf.vhd
	«ELSE»
	exec compxlib -s mti_se -l vhdl -arch virtex6 -lib unisim -lib xilinxcorelib &
	«ENDIF»
	'''

def compileProcessor(Processor processor, boolean callByNetwork)
	'''
	# Compile processor <processor.name>
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/vhdl/imem_mau_pkg.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/vhdl/<processor.name>_tl_globals_pkg.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/vhdl/<processor.name>_tl_params_pkg.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/vhdl/<processor.name>_mem_constants_pkg.vhd
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/vhdl/<processor.name>_tl.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/vhdl/<processor.name>.vhd
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/gcu_ic/gcu_opcodes_pkg.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/gcu_ic/output_socket_<length(processor.buses)>_1.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/gcu_ic/output_socket_1_1.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/gcu_ic/input_socket_<length(processor.buses)>.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/gcu_ic/ifetch.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/gcu_ic/idecompressor.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/gcu_ic/ic.vhdl
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/gcu_ic/decoder.vhdl
	
	«IF(fpga.xilinx)»
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/vhdl/dram_<processor.name>.vhd
	vcom -93 -quiet -work work «IF(callByNetwork)»<processor.name>/«ENDIF»tta/vhdl/irom_<processor.name>.vhd
	
	exec cp -f «IF(callByNetwork)»<processor.name>/«ENDIF»tta/vhdl/dram_<processor.name>.mif . &
	exec cp -f «IF(callByNetwork)»<processor.name>/«ENDIF»tta/vhdl/irom_<processor.name>.mif . &
	«ELSE»
	exec cp -f «IF(!callByNetwork)»../«ENDIF»wrapper/dram_<processor.name>.mif . &
	exec cp -f «IF(!callByNetwork)»../«ENDIF»wrapper/irom_<processor.name>.mif . &
	«ENDIF»
	'''

def compileOtherComponents(boolean callByNetwork)
	'''
	# Compile other components
	«IF(fpga.altera)»
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»wrapper/altera_ram_1p.vhd
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»wrapper/altera_ram_2p.vhd
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»wrapper/altera_rom.vhd
	«ENDIF»
	
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»simulation/sim_package.vhd
	'''

def compileInterfaceComponents(boolean callByNetwork) 
	'''
	# Compile interface components
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»interface/counter.vhd
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»interface/segment_display_conv.vhd
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»interface/segment_display_sel.vhd
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»interface/fps_eval.vhd
	'''

def compileSharedComponent(boolean callByNetwork)
	'''
	# Compile Shared components
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»share/vhdl/util_pkg.vhdl
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»share/vhdl/tce_util_pkg.vhdl
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»share/vhdl/rf_1wr_1rd_always_1_guarded_0.vhd
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»share/vhdl/mul.vhdl
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»share/vhdl/ldh_ldhu_ldq_ldqu_ldw_sth_stq_stw.vhdl
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»share/vhdl/and_ior_xor.vhdl
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»share/vhdl/add_and_eq_gt_gtu_ior_shl_shr_shru_sub_sxhw_sxqw_xor.vhdl
	vcom -93 -quiet -work work «IF(!callByNetwork)»../«ENDIF»share/vhdl/stratix3_led_io_always_1.vhd
	'''
}