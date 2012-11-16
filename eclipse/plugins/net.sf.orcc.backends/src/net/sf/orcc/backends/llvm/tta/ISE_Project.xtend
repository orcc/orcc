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
import net.sf.orcc.backends.llvm.tta.architecture.Design
import net.sf.orcc.backends.llvm.tta.architecture.Processor

class ISE_Project extends TTAPrinter {
	
	override caseDesign(Design design) 
		'''
		<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
		<project xmlns="http://www.xilinx.com/XMLSchema" xmlns:xil_pn="http://www.xilinx.com/XMLSchema">
		
		  <header>
		    <!-- ISE source project file created by the Open RVC-CAL Compiler      -->
		    <!--                                                                   -->
		    <!-- This file contains project source information including a list of -->
		    <!-- project source files, project and process properties.  This file, -->
		    <!-- along with the project source files, is sufficient to open and    -->
		    <!-- implement in ISE Project Navigator.                               -->
		    <!--                                                                   -->
		  </header>
		
		  <files>
		    <file xil_pn:name="top.ucf" xil_pn:type="FILE_UCF">
		      <association xil_pn:name="Implementation"/>
		    </file>
		
		    <file xil_pn:name="top.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		
		    «FOR processor:design.processors»
				«processor.doSwitch»
			«ENDFOR»
		
		    <!--                   -->
		    <!-- Shared components.-->
		    <!--                   -->
		    <file xil_pn:name="share/vhdl/util_pkg.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/tce_util_pkg.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/rf_1wr_1rd_always_1_guarded_0.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/mul.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/ldh_ldhu_ldq_ldqu_ldw_sth_stq_stw.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/and_ior_xor.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="share/vhdl/add_and_eq_gt_gtu_ior_shl_shr_shru_sub_sxhw_sxqw_xor.vhdl" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		
		    <!--                  -->
		    <!-- Other components.-->
		    <!--                  -->
		    <file xil_pn:name="interface/counter.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="interface/fps_eval.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="interface/segment_display_conv.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		    <file xil_pn:name="interface/segment_display_sel.vhd" xil_pn:type="FILE_VHDL">
		      <association xil_pn:name="BehavioralSimulation"/>
		      <association xil_pn:name="Implementation"/>
		    </file>
		
		  </files>
		
		  <properties>
		    <property xil_pn:name="Auto Implementation Top" xil_pn:value="false" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Implementation Top" xil_pn:value="Architecture|top|bdf_type" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Implementation Top File" xil_pn:value="top.vhd" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Implementation Top Instance Path" xil_pn:value="/top" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Device Family" xil_pn:value="Virtex6" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Device" xil_pn:value="xc6vlx240t" xil_pn:valueState="non-default"/>
		    <property xil_pn:name="Package" xil_pn:value="ff1156" xil_pn:valueState="default"/>
		    <property xil_pn:name="Speed Grade" xil_pn:value="-1" xil_pn:valueState="non-default"/>
		  </properties>
		
		  <bindings/>
		
		  <libraries/>printUcf
		
		  <autoManagedFiles>
		    <!-- The following files are identified by `include statements in verilog -->
		    <!-- source files and are automatically managed by Project Navigator.     -->
		    <!--                                                                      -->
		    <!-- Do not hand-edit this section, as it will be overwritten when the    -->
		    <!-- project is analyzed based on files automatically identified as       -->
		    <!-- include files.                                                       -->
		  </autoManagedFiles>
		
		</project>
		'''
		
	override caseProcessor(Processor processor)
		'''
		<!-- Processor «processor.name» -->
		<file xil_pn:name="«processor.name»/tta/vhdl/«processor.name»_tl_params_pkg.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/«processor.name»_tl.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/«processor.name»_tl_globals_pkg.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/«processor.name».vhd" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/imem_mau_pkg.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/output_socket_«processor.buses.size»_1.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/output_socket_1_1.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/input_socket_«processor.buses.size».vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulationprintUcf"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/ifetch.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/idecompressor.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/ic.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/gcu_opcodes_pkg.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/gcu_ic/decoder.vhdl" xil_pn:type="FILE_VHDL">
		  <association xil_pn:name="BehavioralSimulation"/>
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/irom_«processor.name».ngc" xil_pn:type="FILE_NGC">
		  <association xil_pn:name="Implementation"/>
		</file>
		<file xil_pn:name="«processor.name»/tta/vhdl/dram_«processor.name».ngc" xil_pn:type="FILE_NGC">
		  <association xil_pn:name="Implementation"/>
		</file>
		'''
		
	def print(Design design, String targetFolder) {
		val ucfFile = new File(targetFolder + File::separator + "top.ucf")
		val xiseFile = new File(targetFolder + File::separator + "top.xise")
		printFile(getUcfContent, ucfFile)
		printFile(doSwitch(design), xiseFile)
	}
	
	def private getUcfContent()
		'''
		NET "leds[0]" IOSTANDARD = LVCMOS18;
		NET "leds[0]" LOC = AC22;
		NET "leds[1]" IOSTANDARD = LVCMOS18;
		NET "leds[1]" LOC = AC24;
		NET "leds[2]" IOSTANDARD = LVCMOS18;
		NET "leds[2]" LOC = AE22;
		NET "leds[3]" IOSTANDARD = LVCMOS18;		// LVCMOS25
		NET "leds[3]" LOC = AE23;
		NET "leds[4]" IOSTANDARD = LVCMOS18;
		NET "leds[4]" LOC = AB23;
		NET "leds[5]" IOSTANDARD = LVCMOS18;		// LVCMOS25
		NET "leds[5]" LOC = AG23;
		NET "leds[6]" IOSTANDARD = LVCMOS18;		// LVCMOS25
		NET "leds[6]" LOC = AE24;
		NET "leds[7]" IOSTANDARD = LVCMOS18;		// LVCMOS25
		NET "leds[7]" LOC = AD24;
		
		NET "rst_n" TIG;
		//LVCMOS33
		NET "rst_n" IOSTANDARD = LVCMOS25;
		NET "rst_n" PULLUP;
		NET "rst_n" LOC = D22;
		
		NET "clk" TNM_NET = "sys_clk_pin";
		NET "clk" CLOCK_DEDICATED_ROUTE = FALSE;
		TIMESPEC TS_sys_clk_pin = PERIOD "sys_clk_pin" 100000 KHz;
		//LVCMOS33
		NET "clk" IOSTANDARD = LVCMOS25;
		NET "clk" LOC = AE16;
		'''
}