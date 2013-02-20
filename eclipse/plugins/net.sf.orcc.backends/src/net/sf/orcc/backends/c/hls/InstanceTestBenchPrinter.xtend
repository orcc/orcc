/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * about
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
package net.sf.orcc.backends.c.hls

import java.io.File
import java.util.Map
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Port
import net.sf.orcc.util.OrccUtil

/**
 * generates top Network testbench
 *  
 * @author Khaled Jerbi
 * 
 */
 
 
 class InstanceTestBenchPrinter extends net.sf.orcc.backends.c.InstancePrinter {

	new(Map<String, Object> options) {
		super(options)
	}

	override getFileContent() '''
	LIBRARY ieee;
	USE ieee.std_logic_1164.ALL;
	USE ieee.std_logic_unsigned.all;
	USE ieee.numeric_std.ALL;
	USE std.textio.all;
	
	
	ENTITY testbench IS
	END testbench;
	
	ARCHITECTURE behavior OF testbench IS
	
	-- Component Declaration
	COMPONENT «instance.name»_scheduler
	PORT(
	ap_clk : IN STD_LOGIC;
	ap_rst : IN STD_LOGIC;
	ap_start : IN STD_LOGIC;
	ap_done : OUT STD_LOGIC;
	ap_idle : OUT STD_LOGIC;
	«FOR connection : instance.outgoingPortMap.values»
		«assignOutputFifo(connection.head)»
	«ENDFOR»
	«FOR connection : instance.incomingPortMap.values»
		«assignInputFifo(connection)»
	«ENDFOR»
	ap_ready : OUT STD_LOGIC
	 );
	END COMPONENT;	
		
	signal ap_clk :  STD_LOGIC:= '0';
	signal ap_rst : STD_LOGIC:= '0';
	signal ap_start : STD_LOGIC:= '0';
	signal ap_done :  STD_LOGIC;
	signal ap_idle :  STD_LOGIC;
	signal ap_ready :  STD_LOGIC;
	«FOR connection : instance.outgoingPortMap.values»
		«printOutputSignalFifoAssignHLS(connection.head)»
	«ENDFOR»
	«FOR connection : instance.incomingPortMap.values»
		«printInputSignalFifoAssignHLS(connection)»
	«ENDFOR»
	
	-- Configuration
	signal count       : integer range 255 downto 0 := 0;
	signal countSendEdge : std_logic_vector(15 downto 0) := (others => '0');
	
	constant PERIOD : time := 50 ns;
	constant DUTY_CYCLE : real := 0.5;
	constant OFFSET : time := 100 ns;

	type severity_level is (note, warning, error, failure);
	type tb_type is (after_reset, read_file, CheckRead);
	
	 -- Input and Output files
	signal tb_FSM_bits  : tb_type;
	«FOR connection : instance.outgoingPortMap.values»
		«IF !(connection.head.source instanceof Port) && (connection.head.target instanceof Port)»
				file sim_file_«instance.name»_«connection.head.sourcePort.name»  : text is "«instance.name»_«connection.head.sourcePort.name».txt";
		«ENDIF»
	«ENDFOR»
	«FOR connection : instance.incomingPortMap.values»
		«IF (connection.source instanceof Port) && !(connection.target instanceof Port)»
				file sim_file_«instance.name»_«connection.targetPort.name»  : text is "«instance.name»_«connection.targetPort.name».txt";
		«ENDIF»
	«ENDFOR»
	begin

	uut : main port map (
	ap_clk => ap_clk,
	ap_rst => ap_rst,
	ap_start => ap_start,
	ap_done => ap_done,
	ap_idle => ap_idle,
	«FOR connection : instance.outgoingPortMap.values»
		«printOutputFifoMappingHLS(connection.head)»
	«ENDFOR»
	«FOR connection : instance.incomingPortMap.values»
		«printInputFifoMappingHLS(connection)»
	«ENDFOR»
	ap_ready =>ap_ready);

	clockProcess : process
	   begin
		  wait for OFFSET;
		  clock_LOOP : loop
			  ap_clk <= '0';
	          wait for (PERIOD - (PERIOD * DUTY_CYCLE));
	          ap_clk <= '1';
	          wait for (PERIOD * DUTY_CYCLE);
	      end loop clock_LOOP;
	   end process;


	resetProcess : process
	   begin                
	      wait for OFFSET;
	      -- reset state for 100 ns.
	      ap_rst <= '1';
	      wait for 100 ns;
	      ap_rst <= '0';        
	      wait;
	   end process;
	
	«IF ! instance.incomingPortMap.empty»
	WaveGen_Proc_In : process (ap_clk)
	  variable Input_bit   : integer range 2147483647 downto - 2147483648;
	  variable line_number : line;
	begin
	  if rising_edge(ap_clk) then
	«FOR connection : instance.incomingPortMap.values»
		«printInputWaveGen(instance, connection)»
	«ENDFOR»
	end if;
	end process WaveGen_Proc_In;
	«ENDIF»
	
	«IF ! instance.outgoingPortMap.empty»
	WaveGen_Proc_Out : process (clock)
	variable Input_bit   : integer range 2147483647 downto - 2147483648;
	variable line_number : line;
	begin
	if (rising_edge(clock)) then
	«FOR connection : instance.outgoingPortMap.values.head»
		«printOutputWaveGen(instance, connection)»
	«ENDFOR»
	end if;
	end process WaveGen_Proc_Out;
	«ENDIF»
	
	«FOR connection : instance.outgoingPortMap.values»
		«IF !(connection.head.source instanceof Port) && (connection.head.target instanceof Port)»
				«connection.head.fifoName»_full_n <= '1';
		«ENDIF»
	«ENDFOR»
	
	«IF (instance.outgoingPortMap.empty) && (instance.incomingPortMap.empty)»
	ap_start <= '1';
	«ENDIF»
	END;
	'''
	
	def assignOutputFifo(Connection connection) '''
		
		«connection.fifoName»_din    : OUT STD_LOGIC_VECTOR («connection.fifoType.sizeInBits» - 1 downto 0);
		«connection.fifoName»_full_n : IN STD_LOGIC;
		«connection.fifoName»_write  : OUT STD_LOGIC;
	'''
	
	
	
	def assignInputFifo(Connection connection) '''
		
		«connection.fifoName»_dout   : IN STD_LOGIC_VECTOR («connection.fifoType.sizeInBits» - 1 downto 0);
		«connection.fifoName»_empty_n : IN STD_LOGIC;
		«connection.fifoName»_read    : OUT STD_LOGIC;
	'''
	
	def printOutputSignalFifoAssignHLS(Connection connection) '''
		
		signal «connection.fifoName»_din    :  STD_LOGIC_VECTOR («connection.fifoType.sizeInBits» - 1 downto 0) := (others => '0');
		signal «connection.fifoName»_full_n :  STD_LOGIC := '0';
		signal «connection.fifoName»_write  :  STD_LOGIC := '0';
	'''
	
	def printInputSignalFifoAssignHLS(Connection connection) '''
		
		signal «connection.fifoName»_dout   :  STD_LOGIC_VECTOR («connection.fifoType.sizeInBits» - 1 downto 0) := (others => '0');
		signal «connection.fifoName»_empty_n :  STD_LOGIC := '0';
		signal «connection.fifoName»_read    :  STD_LOGIC := '0';
	'''
	
	def printOutputFifoMappingHLS(Connection connection) '''
		«connection.fifoName»_din    => «connection.fifoName»_din,
		«connection.fifoName»_full_n => «connection.fifoName»_full_n,
		«connection.fifoName»_write  => «connection.fifoName»_write,
	'''
		
	def printInputFifoMappingHLS(Connection connection) '''
		«connection.fifoName»_dout    => «connection.fifoName»_dout,
		«connection.fifoName»_empty_n => «connection.fifoName»_empty_n,
		«connection.fifoName»_read    => «connection.fifoName»_read,
	'''
	
	def printInputWaveGen(Instance instance, Connection connection) '''
		case tb_FSM_bits is
			when after_reset =>
			count <= count + 1;
			if (count = 15) then
			tb_FSM_bits <= read_file;
			count           <= 0;
		end if;

		when read_file =>
		if (not endfile (sim_file_«instance.name»_«connection.targetPort.name»)) then
			readline(sim_file_«instance.name»_«connection.targetPort.name», line_number);
			if (line_number'length > 0 and line_number(1) /= '/') then
				read(line_number, input_bit);
				«IF connection.fifoType.int»
					«connection.fifoName»_dout  <= std_logic_vector(to_signed(input_bit, «connection.fifoType.sizeInBits»));
				«ENDIF»
				«IF connection.fifoType.uint»
					«connection.fifoName»_dout  <= std_logic_vector(to_unsigned(input_bit, «connection.fifoType.sizeInBits»));
				«ENDIF»
				«IF connection.fifoType.bool»
					if (input_bit = 1) then 
					«connection.fifoName»_dout  <= '1';
					else
					«connection.fifoName»_dout  <= '0';
					end if;
				«ENDIF»
				«connection.fifoName»_empty_n <= '1';
				ap_start <= '1';    
				tb_FSM_bits <= CheckRead;
			end if;
		end if;

		when CheckRead =>
		if (not endfile (sim_file_«instance.name»_«connection.targetPort.name»)) and «connection.fifoName»_read = '1' then
			«connection.fifoName»_empty_n <= '0';
			readline(sim_file_«instance.name»_«connection.targetPort.name», line_number);
			if (line_number'length > 0 and line_number(1) /= '/') then
				read(line_number, input_bit);
				«IF connection.fifoType.int»
					«connection.fifoName»_dout  <= std_logic_vector(to_signed(input_bit, «connection.fifoType.sizeInBits»));
				«ENDIF»
				«IF connection.fifoType.uint»
					«connection.fifoName»_dout  <= std_logic_vector(to_unsigned(input_bit, «connection.fifoType.sizeInBits»));
				«ENDIF»
				«connection.fifoName»_empty_n <= '1';
				«IF connection.fifoType.bool»
					if (input_bit = 1) then 
					«connection.fifoName»_dout  <= '1';
					else
					«connection.fifoName»_dout  <= '0';
					end if;
				«ENDIF»
				ap_start <= '1';      
		end if;
			elsif (endfile (sim_file_«instance.name»_«connection.targetPort.name»)) then
				ap_start <= '0';
				«connection.fifoName»_empty_n <= '0';
			end if;
		when others => null;
		end case;
	'''
	
	def printOutputWaveGen(Instance vertex, Connection connection) '''
		if (not endfile (sim_file_«vertex.name»_«connection.sourcePort.name») and «connection.fifoName»_write = '1') then
			readline(sim_file_«vertex.name»_«connection.sourcePort.name», line_number);
			if (line_number'length > 0 and line_number(1) /= '/') then
				read(line_number, input_bit);
				«IF connection.fifoType.int»
				assert («connection.fifoName»_din  = std_logic_vector(to_signed(input_bit, «connection.fifoType.sizeInBits»)))
				-- report "on Y incorrectly value computed : " & to_string(to_integer(to_signed(«connection.fifoName»_din))) & " instead of :" & to_string(input_bit)
				report "on port Y incorrectly value computed : " & str(to_integer(signed(«connection.fifoName»_din))) & " instead of :" & str(input_bit)
				severity error;
				«ENDIF»
				«IF connection.fifoType.uint»
				assert («connection.fifoName»_din  = std_logic_vector(to_unsigned(input_bit, «connection.fifoType.sizeInBits»)))
				-- report "on Y incorrectly value computed : " & to_string(to_integer(to_unsigned(«connection.fifoName»_din))) & " instead of :" & to_string(input_bit)
				report "on port Y incorrectly value computed : " & str(to_integer(unsigned(«connection.fifoName»_din))) & " instead of :" & str(input_bit)
				severity error;
				«ENDIF»
				«IF connection.fifoType.bool»
				if (input_bit = 1)
					assert («connection.fifoName»_din  = '1')
					report "0" instead of "1"
					severity error;
				else
					assert («connection.fifoName»_din  = '0')
					report "1" instead of "0"
					severity error;
				end if;
				«ENDIF»
				
			
				-- assert («connection.fifoName»_din /= std_logic_vector(to_signed(input_bit, «connection.fifoType.sizeInBits»)))
				-- report "on port Y correct value computed : " & str(to_integer(signed(Y_data))) & " equals :" & str(input_bit)
				-- severity note;

			end if;
		end if;
	'''
	
	override print(String targetFolder) {		
		val content = fileContent
		val file = new File(targetFolder + File::separator + instance.name+ "_tb" + ".vhd")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}
	
	def fifoName(Connection connection)
		'''myStream_«connection.getAttribute("id").objectValue»_V'''
	
	def fifoType(Connection connection) {
		if (connection.sourcePort != null){	
			connection.sourcePort.type
		}else{
			connection.targetPort.type
		}
	}
}