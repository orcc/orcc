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
import net.sf.orcc.backends.llvm.tta.architecture.Link
import net.sf.orcc.backends.llvm.tta.architecture.Memory
import net.sf.orcc.backends.llvm.tta.architecture.Port
import net.sf.orcc.backends.llvm.tta.architecture.Processor
import net.sf.orcc.backends.llvm.tta.architecture.Signal
import net.sf.orcc.backends.util.FPGA
import net.sf.orcc.backends.util.CommonPrinter

class VHDL_Processor extends CommonPrinter {
	
	FPGA fpga;
	
	new(FPGA fpga) {
		this.fpga = fpga;
	}
	
	def print(Processor processor, String targetFolder) {
		val file = new File(targetFolder + File::separator + processor.getName() + ".vhd")
		printFile(processor.vhdl, file)
	}
		
	def private getVhdl(Processor processor)
		'''
		-------------------------------------------------------------------------------
		-- Title      : «processor.name»
		-- Project    : 
		-------------------------------------------------------------------------------
		-- File       : «processor.name».vhd
		-- Author     : Orcc - TTA
		-- Company    : 
		-- Created    : 
		-- Standard   : VHDL 93
		-------------------------------------------------------------------------------
		-- Copyright (c)  
		-------------------------------------------------------------------------------
		-- Revisions  :
		-- Date        Version  Author  Description
		-- 
		-------------------------------------------------------------------------------
		
		
		------------------------------------------------------------------------------
		library ieee;
		use ieee.std_logic_1164.all;
		
		library work;
		use work.«processor.name»_tl_globals.all;
		use work.«processor.name»_tl_imem_mau.all;
		use work.«processor.name»_tl_params.all;
		use work.«processor.name»_mem_constants.all;
		
		
		------------------------------------------------------------------------------
		entity «processor.name» is
		  generic
		    (
		      device_family : string
		      );
		  port
		    (
		      clk                    : in  std_logic;
		      «processor.declarePorts»
		      rst_n                  : in  std_logic
		      );
		end «processor.name»;
		
		
		------------------------------------------------------------------------------
		architecture bdf_type of «processor.name» is
		
		  «processor.declare»
		
		begin
		
		  «processor.assign»

		end bdf_type;
		'''
		
	def private declare(Processor processor)
		'''
		«IF(fpga.xilinx)»
		---------------------------------------------------------------------------
		-- Components declaration
		---------------------------------------------------------------------------
		component dram_«processor.name»
		  port (
		    clka  : in  std_logic;
		    wea   : in  std_logic_vector(3 downto 0);
		    addra : in  std_logic_vector(fu_LSU_0_addrw-1 downto 0);
		    dina  : in  std_logic_vector(fu_LSU_0_dataw-1 downto 0);
		    douta : out std_logic_vector(fu_LSU_0_dataw-1 downto 0));
		end component;
		
		component irom_«processor.name»
		  port (
		    clka  : in  std_logic;
		    addra : in  std_logic_vector(INSTRUCTIONADDRWIDTH-1 downto 0);
		    douta : out std_logic_vector(INSTRUCTIONWIDTH-1 downto 0));
		end component;
		
		«ENDIF»
		---------------------------------------------------------------------------
		-- Signals declaration
		---------------------------------------------------------------------------
		signal dram_addr          : std_logic_vector(fu_LSU_0_addrw-2-1 downto 0);
		signal wren_wire          : std_logic;
		signal wren_x_wire        : std_logic;
		signal dram_data_in_wire  : std_logic_vector(fu_LSU_0_dataw-1 downto 0);
		signal dram_data_out_wire : std_logic_vector(fu_LSU_0_dataw-1 downto 0);
		signal bytemask_wire      : std_logic_vector(fu_LSU_0_dataw/8-1 downto 0);
		«IF(fpga.xilinx)»
		signal bytemask_i         : std_logic_vector(fu_LSU_0_dataw/8-1 downto 0);
		signal bytemask_i2        : std_logic_vector(fu_LSU_0_dataw/8-1 downto 0);
		«ENDIF»
		--
		signal imem_addr          : std_logic_vector(IMEMADDRWIDTH-1 downto 0);
		signal idata_wire         : std_logic_vector(INSTRUCTIONWIDTH-1 downto 0);
		--
		«FOR edge : processor.incoming»
			«val link = edge as Link»
			«val port = link.targetPort»
			«port.declareSignal(link)»
		«ENDFOR»
		«FOR edge : processor.outgoing»
			«val link = edge as Link»
			«val port = link.sourcePort»
			«port.declareSignal(link)»
		«ENDFOR»
		---------------------------------------------------------------------------
		'''
		
	def private assign(Processor processor)
		'''
		wren_wire <= not(wren_x_wire);
		«FOR edge : processor.incoming»
			«val link = edge as Link»
			«val port = link.targetPort»
			«port.mapSignal(link)»
		«ENDFOR»
		«FOR edge : processor.outgoing»
			«val link = edge as Link»
			«val port = link.sourcePort»
			«port.mapSignal(link)»
		«ENDFOR»
		
		«IF(fpga.altera)»
		inst_dram_«processor.name» : entity work.dram_1p
		  generic map(depth         => DATADEPTH,
		              byte_width    => fu_LSU_0_dataw/4,
		              addr_width	  => fu_LSU_0_addrw-2,
		              bytes         => 4,
		              init_file     => "dram_«processor.name».mif",
		              device_family => device_family)
		  port map(clk     => clk,
		           wren    => wren_wire,
		           address => dram_addr,
		           byteen  => bytemask_wire,
		           data    => dram_data_in_wire,
		           queue   => dram_data_out_wire,
		           rst_n   => rst_n);

		inst_irom_«processor.name» : entity work.irom
		  generic map(depth         => INSTRUCTIONDEPTH,
		              byte_width    => IMEMMAUWIDTH,
		              addr_width    => INSTRUCTIONADDRWIDTH,
		              bytes         => IMEMWIDTHINMAUS,
		              init_file     => "irom_«processor.name».mif",
		              device_family => device_family)
		  port map(clk     => clk,
		           address => imem_addr(INSTRUCTIONADDRWIDTH-1 downto 0),
		           queue   => idata_wire,
		           rst_n   => rst_n);
		«ELSE»
		bytemask_i2 <= wren_wire & wren_wire & wren_wire & wren_wire;
		bytemask_i  <= bytemask_i2 and bytemask_wire;
		
		inst_irom_«processor.name» : irom_«processor.name»
		  port map (
		    clka  => clk,
		    addra => imem_addr(INSTRUCTIONADDRWIDTH-1 downto 0),
		    douta => idata_wire);
		
		inst_dram_«processor.name» : dram_«processor.name»
		  port map (
		    clka  => clk,
		    wea   => bytemask_i,
		    addra => dram_addr,
		    dina  => dram_data_in_wire,
		    douta => dram_data_out_wire);
		«ENDIF»
		
		inst_«processor.name»_tl : entity work.«processor.name»_tl
		  port map(clk                      => clk,
		           busy                     => '0',
		           imem_addr                => imem_addr,
		           imem_data                => idata_wire,
		           pc_init                  => (others => '0'),
		           fu_LSU_0_dmem_data_in    => dram_data_out_wire,
		           fu_LSU_0_dmem_data_out   => dram_data_in_wire,
		           fu_LSU_0_dmem_addr       => dram_addr,
		           fu_LSU_0_dmem_wr_en_x(0) => wren_x_wire,
		           fu_LSU_0_dmem_bytemask   => bytemask_wire,
		           «processor.mapPorts»
		           rstx                     => rst_n);
		'''
		
	def private declarePorts(Processor processor)
		'''
		«FOR edge : processor.incoming»
			«val link = edge as Link»
			«val port = link.targetPort»
			«port.declarePort(link)»
		«ENDFOR»
		«FOR edge : processor.outgoing»
			«val link = edge as Link»
			«val port = link.sourcePort»
			«port.declarePort(link)»
		«ENDFOR»
		'''
		
	def private dispatch declarePort(Port port, Signal signal)
		'''
		«port.name» : out std_logic_vector(«signal.size»-1 downto 0);
		'''
	
	def private dispatch declarePort(Port port, Memory memory)
		'''
		fu_«port.name»_dmem_data_in  : in std_logic_vector(fu_«port.name»_dataw-1 downto 0);
		fu_«port.name»_dmem_data_out : out std_logic_vector(fu_«port.name»_dataw-1 downto 0);
		fu_«port.name»_dmem_addr     : out std_logic_vector(fu_«port.name»_addrw-2-1 downto 0);
		fu_«port.name»_dmem_wr_en    : out std_logic;
		fu_«port.name»_dmem_bytemask : out std_logic_vector(fu_«port.name»_dataw/8-1 downto 0);
		'''

	def private dispatch declareSignal(Port port, Signal signal)
		'''
		signal «port.name»_i : std_logic_vector(7 downto 0);
		'''

	def private dispatch declareSignal(Port port, Memory memory)
		'''
		signal fu_«port.name»_dmem_wr_en_x : std_logic;
		'''
	
	def private mapPorts(Processor processor)
		'''
		«FOR edge : processor.incoming»
			«val link = edge as Link»
			«val port = link.targetPort»
			«port.mapPort(link)»
		«ENDFOR»
		«FOR edge : processor.outgoing»
			«val link = edge as Link»
			«val port = link.sourcePort»
			«port.mapPort(link)»
		«ENDFOR»
		'''
	
	def private dispatch mapPort(Port port, Signal signal)
		'''
		fu_«port.name»_STRATIXIII_LED => «port.name»_i,
		'''
	
	def private dispatch mapPort(Port port, Memory memory)
		'''
		fu_«port.name»_dmem_data_in    => fu_«port.name»_dmem_data_in,
		fu_«port.name»_dmem_data_out   => fu_«port.name»_dmem_data_out,
		fu_«port.name»_dmem_addr       => fu_«port.name»_dmem_addr,
		fu_«port.name»_dmem_wr_en_x(0) => fu_«port.name»_dmem_wr_en_x,
		fu_«port.name»_dmem_bytemask   => fu_«port.name»_dmem_bytemask,
		'''

	def private dispatch mapSignal(Port port, Signal signal)
		'''
		«port.name» <= «port.name»_i(«signal.size»-1 downto 0);
		'''

	def private dispatch mapSignal(Port port, Memory memory)
		'''
		fu_«port.name»_dmem_wr_en <= not(fu_«port.name»_dmem_wr_en_x);
		'''

}