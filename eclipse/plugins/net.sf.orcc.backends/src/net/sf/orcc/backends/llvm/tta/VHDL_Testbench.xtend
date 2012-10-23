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
import net.sf.orcc.backends.llvm.tta.architecture.util.ArchitectureSwitch
import net.sf.orcc.backends.llvm.tta.architecture.Port

class VHDL_Testbench extends ArchitectureSwitch<CharSequence> {
	
	
	override caseDesign(Design design)
		'''
		------------------------------------------------------------------------------
		-- Generated from <vertex.simpleName>
		------------------------------------------------------------------------------
		
		library ieee;
		use ieee.std_logic_1164.all; 
		use std.textio.all;
		use ieee.numeric_std.all;
		
		library work;
		use work.sim_package.all;
		
		entity tb_top is
		
		end tb_top;
		
		
		architecture arch_tb_top of tb_top is 
		
		  ---------------------------------------------------------------------------
		  -- Signal & constant declaration
		  --------------------------------------------------------------------------- 
		  «design.declareVertexSigAndConst»
		  ---------------------------------------------------------------------------
		
		begin
		
		  top_orcc : entity work.top
		    port map (
		      clk => clk,
		      «design.mapSignals»
		      rst_n => rst_n);
		      
		  -- clock generation
		  clk <= not clk after PERIOD/2;
		
		  -- reset generation
		  reset_proc: process
		  begin
		    rst_n <= '0';
		    wait for 100 ns;
		    rst_n <= '1';
		    wait;
		  end process;
		
		end architecture arch_tb_top;
		'''
		
	def declareVertexSigAndConst(Design design)
		'''
		constant PERIOD : time := 10 ns;
		--
		type severity_level is (note, warning, error, failure);		
		--
		-- Input and Output signals
		«FOR port : design.inputs + design.outputs»
			«port.declareSignal»
		«ENDFOR»
		--
		-- Configuration
		signal clk   : std_logic := '0';
		signal rst_n : std_logic := '0';
		'''
		
	def declareSignal(Port port)
		'''
		signal «port.name» : std_logic_vector(«port.size»-1 downto 0);
		'''
		
	def mapSignals(Design design)
		'''
		«FOR port : design.inputs + design.outputs»
			«port.mapSignal»
		«ENDFOR»
		'''
	
	def mapSignal(Port port)
		'''
		«port.name» => <port.name>,
		'''
		
	
}