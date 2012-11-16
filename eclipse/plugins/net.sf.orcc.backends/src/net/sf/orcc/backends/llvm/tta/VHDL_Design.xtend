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
import net.sf.orcc.backends.CommonPrinter
import net.sf.orcc.backends.llvm.tta.architecture.Component
import net.sf.orcc.backends.llvm.tta.architecture.Design
import net.sf.orcc.backends.llvm.tta.architecture.Link
import net.sf.orcc.backends.llvm.tta.architecture.Memory
import net.sf.orcc.backends.llvm.tta.architecture.Port
import net.sf.orcc.backends.llvm.tta.architecture.Processor
import net.sf.orcc.backends.llvm.tta.architecture.Signal
import net.sf.orcc.backends.util.FPGA
import net.sf.orcc.ir.util.ExpressionPrinter
import net.sf.orcc.util.Attribute
import org.eclipse.emf.common.util.EList

class VHDL_Design extends CommonPrinter {
	
	ExpressionPrinter exprPrinter;
	FPGA fpga;
	
	new(FPGA fpga) {
		this.fpga = fpga;
		this.exprPrinter = new ExpressionPrinter();
	}
	
	def print(Design design, String targetFolder) {
		val file = new File(targetFolder + File::separator + "top.vhdl")
		printFile(design.vhdl, file)
	}
	
	def private getVhdl(Design design)
		'''
		-------------------------------------------------------------------------------
		-- Title      : Network: «design.name»
		-- Project    : 
		-------------------------------------------------------------------------------
		-- File       : «design.name».vhd
		-- Author     : Orcc - TTA
		-- Company    : 
		-- Created    : 
		-- Standard   : VHDL'93
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
		
		------------------------------------------------------------------------------
		entity top is
		  port
		    (
		      clk : in  std_logic;
		      «design.declarePorts»
		      rst_n : in  std_logic
		      );
		end top;
		
		------------------------------------------------------------------------------
		architecture bdf_type of top is
		
		  «design.declareSignals»
		
		begin
		
		  «design.instantiate»
		
		end bdf_type;
		'''
		
	def private getVhdl(Processor processor)
		'''
		«processor.name»_inst : entity work.«processor.name»
		  generic map(device_family => "«fpga.family»")
		  port map(clk                    => clk,
		           «processor.assignPorts»
		           rst_n                  => rst_n);
		'''
	
	def private getVhdl(Component component)
		'''
		«component.name»_inst : entity work.«component.name»
		  «IF(!component.attributes.empty)»generic map(«component.attributes.assignGenerics»)«ENDIF»
		  port map(clk => clk,
		           «component.assignPorts»
		           rst_n => rst_n);
		'''
		
	def private getVhdl(Memory memory)
		'''
		«memory.name» : entity work.dram_2p
		  generic map(depth      => «memory.depth»/4,
		              byte_width => «memory.wordWidth»,
		              addr_width => «memory.addrWidth»-2,
		              bytes      => 4,
		              device_family => "«fpga.family»")
		  port map(clk        => clk,
		           wren_p1    => «memory.name»_wren_p1,
		           address_p1 => «memory.name»_address_p1,
		           byteen_p1  => «memory.name»_byteen_p1,
		           data_p1    => «memory.name»_data_p1,
		           queue_p1   => «memory.name»_queue_p1,
		           wren_p2    => «memory.name»_wren_p2,
		           address_p2 => «memory.name»_address_p2,
		           byteen_p2  => «memory.name»_byteen_p2,
		           data_p2    => «memory.name»_data_p2,
		           queue_p2   => «memory.name»_queue_p2,
		           rst_n      => rst_n);
		'''
		
	def private assign(Port port, Signal signal)
		'''
		«port.name» <= s_«signal.name»;
		'''
		
	def private assignPorts(Component component)
		'''
		«FOR edge : component.incoming»
			«val link = edge as Link»
			«val port = link.targetPort»
			«port.assignInput(link)»
		«ENDFOR»
		«FOR edge : component.outgoing»
			«val link = edge as Link»
			«val port = link.sourcePort»
			«port.assignOutput(link)»
		«ENDFOR»
		'''
		
	def private assignGenerics(EList<Attribute> attributes)
		'''
		«FOR attribute : attributes SEPARATOR ",\n"»
			«attribute.name» => «exprPrinter.doSwitch(attribute.referencedValue)»
		«ENDFOR»
		'''
		
	def private dispatch assignInput(Port port, Memory memory)
		'''
		fu_«port.name»_dmem_data_in  => «memory.name»_queue_p2,
		fu_«port.name»_dmem_data_out => «memory.name»_data_p2,
		fu_«port.name»_dmem_addr     => «memory.name»_address_p2,
		fu_«port.name»_dmem_wr_en    => «memory.name»_wren_p2,
		fu_«port.name»_dmem_bytemask => «memory.name»_byteen_p2,
		'''
	
	def private dispatch assignInput(Port port, Signal signal)
		'''
		«port.name» => s_«signal.name»(«signal.size»-1 downto 0),
		'''
		
	def private dispatch assignOutput(Port port, Memory memory)
		'''
		fu_«port.name»_dmem_data_in  => «memory.name»_queue_p1,
		fu_«port.name»_dmem_data_out => «memory.name»_data_p1,
		fu_«port.name»_dmem_addr     => «memory.name»_address_p1,
		fu_«port.name»_dmem_wr_en    => «memory.name»_wren_p1,
		fu_«port.name»_dmem_bytemask => «memory.name»_byteen_p1,
		'''
	
	def private dispatch assignOutput(Port port, Signal signal)
		'''
		«port.name»(«signal.size»-1 downto 0) => s_«signal.name»,
		'''
		
	def private instantiate(Design design)
		'''
		---------------------------------------------------------------------------
		-- Ports instantiation 
		---------------------------------------------------------------------------
		«FOR port : design.inputs»
			«port.assign(port.outgoing.get(0) as Signal)»
		«ENDFOR»
		«FOR port : design.outputs»
			«port.assign(port.incoming.get(0) as Signal)»
		«ENDFOR»
		---------------------------------------------------------------------------
		-- Buffers instantiation 
		--------------------------------------------------------------------------- 
		«FOR memory : design.sharedMemories SEPARATOR "\n"»
			«memory.vhdl»
		«ENDFOR»
		---------------------------------------------------------------------------
		-- Processors instantiation 
		---------------------------------------------------------------------------
		«FOR processor : design.processors SEPARATOR "\n"»
			«processor.vhdl»
		«ENDFOR»
		---------------------------------------------------------------------------
		-- Components instantiation 
		---------------------------------------------------------------------------
		«FOR component : design.components SEPARATOR "\n"»
			«component.vhdl»
		«ENDFOR»
		---------------------------------------------------------------------------
		'''
		
	def private declareSignals(Design design)
		'''
		---------------------------------------------------------------------------
		-- Signals declaration
		---------------------------------------------------------------------------
		«FOR signal : design.signals»
			«signal.declare»
		«ENDFOR»
		«FOR memory : design.sharedMemories»
			«memory.declareSignals»
		«ENDFOR»
		---------------------------------------------------------------------------
		'''
		
	def private declare(Signal signal)
		'''
		signal s_«signal.name» : std_logic_vector(«signal.size»-1 downto 0);
		'''
		
	def private declareSignals(Memory memory)
		'''
		signal «memory.name»_wren_p1    : std_logic;
		signal «memory.name»_address_p1 : std_logic_vector(«memory.addrWidth»-2-1 downto 0);
		signal «memory.name»_byteen_p1  : std_logic_vector(3 downto 0);
		signal «memory.name»_data_p1    : std_logic_vector(31 downto 0);
		signal «memory.name»_queue_p1   : std_logic_vector(31 downto 0);
		signal «memory.name»_wren_p2    : std_logic;
		signal «memory.name»_address_p2 : std_logic_vector(«memory.addrWidth»-2-1 downto 0);
		signal «memory.name»_byteen_p2  : std_logic_vector(3 downto 0);
		signal «memory.name»_data_p2    : std_logic_vector(31 downto 0);
		signal «memory.name»_queue_p2   : std_logic_vector(31 downto 0);
		'''
		
	def private declarePorts(Design design)
		'''
		«FOR port : design.inputs»
			«port.name» : in std_logic_vector(«port.size»-1 downto 0);
		«ENDFOR»
		«FOR port : design.outputs»
			«port.name» : out std_logic_vector(«port.size»-1 downto 0);
		«ENDFOR»
		'''
}