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
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.util.OrccUtil

/**
 * generates top Network testbench
 *  
 * @author Khaled Jerbi
 * 
 */
 
 
 class NetworkTestBenchPrinter extends net.sf.orcc.backends.c.NetworkPrinter {

	new(Network benchNetwork, Map<String,Object> options) {
		super(benchNetwork, options)
	}
	
	override print(String targetFolder) {
		
		val contentNetwork = networkFileContent
		val NetworkFile = new File(targetFolder + File::separator +"TopVHDL"+ File::separator+ network.name +"_TopTestBench" + ".vhd")
		
		if(needToWriteFile(contentNetwork, NetworkFile)) {
			OrccUtil::printFile(contentNetwork, NetworkFile)
			return 0
		} else {
			return 1
		}
	}

	override getNetworkFileContent() '''
		LIBRARY ieee;
		USE ieee.std_logic_1164.ALL;
		USE ieee.std_logic_unsigned.all;
		USE ieee.numeric_std.ALL;
		USE std.textio.all;
		
		LIBRARY work;
		USE work.sim_package.all;
		
		ENTITY testbench IS
		END testbench;
		
		ARCHITECTURE behavior OF testbench IS
		
		-- Component Declaration
		COMPONENT TopDesign
		PORT(
		ap_clk : IN STD_LOGIC;
		ap_rst : IN STD_LOGIC;
		ap_start : IN STD_LOGIC;
		ap_done : OUT STD_LOGIC;
		ap_idle : OUT STD_LOGIC;
		ap_ready : OUT STD_LOGIC;
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«FOR port : instance.getActor.inputs»
				«val connection = instance.incomingPortMap.get(port)»
				«IF connection != null»
					«IF connection.sourcePort == null»
						«connection.castfifoNameWrite»_V_dout   : IN STD_LOGIC_VECTOR («connection.fifoType.sizeInBits - 1» downto 0);
						«connection.castfifoNameWrite»_V_empty_n : IN STD_LOGIC;
						«connection.castfifoNameWrite»_V_read    : OUT STD_LOGIC;
					«ENDIF»
				«ENDIF»
			«ENDFOR»
			«FOR portout : instance.getActor.outputs.filter[! native]»
				«FOR connection : instance.outgoingPortMap.get(portout)»
					«IF connection.targetPort == null»
						«connection.castfifoNameRead»_V_din    : OUT STD_LOGIC_VECTOR («connection.fifoType.sizeInBits - 1» downto 0);
						«connection.castfifoNameRead»_V_full_n : IN STD_LOGIC;
						«connection.castfifoNameRead»_V_write  : OUT STD_LOGIC;
					«ENDIF»				
				«ENDFOR»
			«ENDFOR»
		«ENDFOR»
		
		ap_return : OUT STD_LOGIC_VECTOR (31 downto 0)
		 );
		END COMPONENT;	
			
		signal ap_clk :  STD_LOGIC:= '0';
		signal ap_rst : STD_LOGIC:= '0';
		signal ap_start : STD_LOGIC:= '0';
		signal ap_done :  STD_LOGIC;
		signal ap_idle :  STD_LOGIC;
		signal ap_ready :  STD_LOGIC;
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«FOR port : instance.getActor.inputs»
				«val connection = instance.incomingPortMap.get(port)»
				«IF connection != null»
					«IF connection.sourcePort == null»
						signal «connection.castfifoNameWrite»_V_dout   :  STD_LOGIC_VECTOR («connection.fifoType.sizeInBits - 1» downto 0);
						signal «connection.castfifoNameWrite»_V_empty_n :  STD_LOGIC;
						signal «connection.castfifoNameWrite»_V_read    :  STD_LOGIC;
					«ENDIF»
				«ENDIF»
			«ENDFOR»
			«FOR portout : instance.getActor.outputs.filter[! native]»
				«FOR connection : instance.outgoingPortMap.get(portout)»
					«IF connection.targetPort == null»
						signal «connection.castfifoNameRead»_V_din    : STD_LOGIC_VECTOR («connection.fifoType.sizeInBits - 1» downto 0);
						signal «connection.castfifoNameRead»_V_full_n : STD_LOGIC;
						signal «connection.castfifoNameRead»_V_write  :  STD_LOGIC;
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
		«ENDFOR»
		signal ap_return :  STD_LOGIC_VECTOR (31 downto 0):= (others => '0');
		
		-- Configuration
		signal count       : integer range 255 downto 0 := 0;
			
		constant PERIOD : time := 20 ns;
		constant DUTY_CYCLE : real := 0.5;
		constant OFFSET : time := 100 ns;
	
		type severity_level is (note, warning, error, failure);
		type tb_type is (after_reset, read_file, CheckRead);
		
		 -- Input and Output files
		signal tb_FSM_bits  : tb_type;
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.assignFifoFile»
		«ENDFOR»
		begin
	
		uut : TopDesign port map (
		ap_clk => ap_clk,
		ap_rst => ap_rst,
		ap_start => ap_start,
		ap_done => ap_done,
		ap_idle => ap_idle,
		ap_ready =>ap_ready,
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.mappingFifoSignal»
		«ENDFOR»
		ap_return => ap_return);
	
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
		
		«IF ! network.inputs.empty»
			WaveGen_Proc_In : process (ap_clk)
			variable Input_bit   : integer range 2147483647 downto - 2147483648;
			variable line_number : line;
			«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
				«FOR port : instance.getActor.inputs»			
					«val connection = instance.incomingPortMap.get(port)»
					«IF connection != null»
						«IF connection.sourcePort == null»
							variable count«connection.castfifoNameWrite»: integer:= 0;
				   		«ENDIF»
				   	«ENDIF»
				«ENDFOR»
			«ENDFOR»
			
			begin
			  if rising_edge(ap_clk) then
			«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
				«instance.waveGenInputs»
			«ENDFOR»
			end if;
			end process WaveGen_Proc_In;
		«ENDIF»
		
		«IF ! network.outputs.empty»
			WaveGen_Proc_Out : process (ap_clk)
			variable Input_bit   : integer range 2147483647 downto - 2147483648;
			variable line_number : line;
			«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
				«FOR portout : instance.getActor.outputs.filter[! native]»
					«FOR connection : instance.outgoingPortMap.get(portout)»
						«IF connection.targetPort == null»
							variable count«connection.castfifoNameRead»: integer:= 0;
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
			«ENDFOR»
			begin
			if (rising_edge(ap_clk)) then
			«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
				«instance.waveGenOutputs»
			«ENDFOR»
			end if;
			end process WaveGen_Proc_Out;
		«ENDIF»
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.initOutputs»
		«ENDFOR»
		
		«IF network.inputs.empty»
			ap_start <= '1';
		«ENDIF»
		END;
	'''
	
	def assignFifo(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				«printOutputFifoAssignHLS(connList.head )»
			«ENDIF»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF (connList.source instanceof Port) && !(connList.target instanceof Port) »
				«printInputFifoAssignHLS(connList)»
			«ENDIF»
		«ENDFOR»
	'''
	
	def assignFifoSignal(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				«printOutputSignalFifoAssignHLS(connList.head )»
			«ENDIF»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF (connList.source instanceof Port) && !(connList.target instanceof Port)»
				«printInputSignalFifoAssignHLS(connList)»
			«ENDIF»
		«ENDFOR»
	'''
	
	def printOutputFifoAssignHLS( Connection connection) '''

		«connection.fifoName»_din    : OUT STD_LOGIC_VECTOR («connection.fifoTypeOut.sizeInBits - 1» downto 0);
		«connection.fifoName»_full_n : IN STD_LOGIC;
		«connection.fifoName»_write  : OUT STD_LOGIC;
	'''
	
	def printInputFifoAssignHLS(Connection connection) '''

		«connection.fifoName»_dout   : IN STD_LOGIC_VECTOR («connection.fifoTypeIn.sizeInBits - 1» downto 0);
		«connection.fifoName»_empty_n : IN STD_LOGIC;
		«connection.fifoName»_read    : OUT STD_LOGIC;
	'''
	
	def printOutputSignalFifoAssignHLS(Connection connection) '''
		
		signal «connection.fifoName»_din    :  STD_LOGIC_VECTOR («connection.fifoTypeOut.sizeInBits- 1» downto 0) := (others => '0');
		signal «connection.fifoName»_full_n :  STD_LOGIC := '0';
		signal «connection.fifoName»_write  :  STD_LOGIC := '0';
	'''
	
	def printInputSignalFifoAssignHLS(Connection connection) '''

		signal «connection.fifoName»_dout   :  STD_LOGIC_VECTOR («connection.fifoTypeIn.sizeInBits- 1» downto 0) := (others => '0');
		signal «connection.fifoName»_empty_n :  STD_LOGIC := '0';
		signal «connection.fifoName»_read    :  STD_LOGIC := '0';
	'''
	
	def assignFifoFile(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				file sim_file_«instance.name»_«connList.head.sourcePort.name»  : text is "«instance.name»_«connList.head.sourcePort.name».txt";
			«ENDIF»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF (connList.source instanceof Port) && !(connList.target instanceof Port)»
				file sim_file_«instance.name»_«connList.targetPort.name»  : text is "«instance.name»_«connList.targetPort.name».txt";
			«ENDIF»
		«ENDFOR»
	'''
	
	def mappingFifoSignal(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				«printOutputFifoMappingHLS(connList.head )»
			«ENDIF»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF (connList.source instanceof Port
			) && !(connList.target instanceof Port)»
				«printInputFifoMappingHLS(connList)»
			«ENDIF»
		«ENDFOR»
	'''
	def printOutputFifoMappingHLS(Connection connection) '''
		«connection.castfifoNameRead»_V_din    => «connection.castfifoNameRead»_V_din,
		«connection.castfifoNameRead»_V_full_n => «connection.castfifoNameRead»_V_full_n,
		«connection.castfifoNameRead»_V_write  => «connection.castfifoNameRead»_V_write,
	'''
		
	def printInputFifoMappingHLS(Connection connection) '''
		«connection.castfifoNameWrite»_V_dout    => «connection.castfifoNameWrite»_V_dout,
		«connection.castfifoNameWrite»_V_empty_n => «connection.castfifoNameWrite»_V_empty_n,
		«connection.castfifoNameWrite»_V_read    => «connection.castfifoNameWrite»_V_read,
	'''
	
	def waveGenInputs(Instance instance) '''
		«FOR port : instance.getActor.inputs»
			«val connection = instance.incomingPortMap.get(port)»
			«IF connection != null»
				«IF connection.sourcePort == null»
					«printInputWaveGen(connection.target as Instance, connection)»
				«ENDIF»
			«ENDIF»
		«ENDFOR»
	'''
	
	def waveGenOutputs(Instance instance) '''
		«FOR portout : instance.getActor.outputs.filter[! native]»
			«FOR connection : instance.outgoingPortMap.get(portout)»
				«IF connection.targetPort == null»
					«printOutputWaveGen(connection.source as Instance,connection )»
				«ENDIF»
			«ENDFOR»
		«ENDFOR»
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
				«IF connection.fifoTypeIn.int»
					«connection.castfifoNameWrite»_V_dout  <= std_logic_vector(to_signed(input_bit, «connection.fifoTypeIn.sizeInBits»));
				«ENDIF»
				«IF connection.fifoTypeIn.uint»
					«connection.castfifoNameWrite»_V_dout  <= std_logic_vector(to_unsigned(input_bit, «connection.fifoTypeIn.sizeInBits»));
				«ENDIF»
				«IF connection.fifoTypeIn.bool»
					if (input_bit = 1) then 
					«connection.castfifoNameWrite»_V_dout  <= "1";
					else
					«connection.castfifoNameWrite»_V_dout  <= "0";
					end if;
				«ENDIF»
				«connection.castfifoNameWrite»_V_empty_n <= '1';
				ap_start <= '1';    
				tb_FSM_bits <= CheckRead;
			end if;
		end if;

		when CheckRead =>
		if (not endfile (sim_file_«instance.name»_«connection.targetPort.name»)) and «connection.castfifoNameWrite»_V_read = '1' then
		 count«connection.castfifoNameWrite» := count«connection.castfifoNameWrite» + 1;
		 report "Number of inputs«connection.fifoName» = " & integer'image(count«connection.castfifoNameWrite»);
			«connection.castfifoNameWrite»_V_empty_n <= '0';
			readline(sim_file_«instance.name»_«connection.targetPort.name», line_number);
			if (line_number'length > 0 and line_number(1) /= '/') then
				read(line_number, input_bit);
				«IF connection.fifoTypeIn.int»
					«connection.castfifoNameWrite»_V_dout  <= std_logic_vector(to_signed(input_bit, «connection.fifoTypeIn.sizeInBits»));
				«ENDIF»
				«IF connection.fifoTypeIn.uint»
					«connection.castfifoNameWrite»_V_dout  <= std_logic_vector(to_unsigned(input_bit, «connection.fifoTypeIn.sizeInBits»));
				«ENDIF»
				«connection.castfifoNameWrite»_V_empty_n <= '1';
				«IF connection.fifoTypeIn.bool»
					if (input_bit = 1) then 
					«connection.castfifoNameWrite»_V_dout  <= "1";
					else
					«connection.castfifoNameWrite»_V_dout  <= "0";
					end if;
				«ENDIF»
				ap_start <= '1';      
		end if;
			elsif (endfile (sim_file_«instance.name»_«connection.targetPort.name»)) then
				ap_start <= '1';
				«connection.castfifoNameWrite»_V_empty_n <= '0';
			end if;
		when others => null;
		end case;
	'''
	
	def printOutputWaveGen(Instance vertex, Connection connection) '''
		if (not endfile (sim_file_«vertex.name»_«connection.sourcePort.name») and «connection.castfifoNameRead»_V_write = '1') then
		count«connection.castfifoNameRead» := count«connection.castfifoNameRead» + 1;
		 report "Number of outputs«connection.castfifoNameRead» = " & integer'image(count«connection.castfifoNameRead»);
			readline(sim_file_«vertex.name»_«connection.sourcePort.name», line_number);
			if (line_number'length > 0 and line_number(1) /= '/') then
				read(line_number, input_bit);
				«IF connection.fifoTypeOut.int»
					assert («connection.castfifoNameRead»_V_din  = std_logic_vector(to_signed(input_bit, «connection.fifoTypeOut.sizeInBits»)))
					-- report "on «connection.castfifoNameRead» incorrectly value computed : " & to_string(to_integer(to_signed(«connection.castfifoNameRead»_V_din))) & " instead of :" & to_string(input_bit)
					report "on port «connection.castfifoNameRead» incorrectly value computed : " & str(to_integer(signed(«connection.castfifoNameRead»_V_din))) & " instead of :" & str(input_bit)
					severity error;
				«ENDIF»
				«IF connection.fifoTypeOut.uint»
					assert («connection.castfifoNameRead»_V_din  = std_logic_vector(to_unsigned(input_bit, «connection.fifoTypeOut.sizeInBits»)))
					-- report "on «connection.castfifoNameRead» incorrectly value computed : " & to_string(to_integer(to_unsigned(«connection.castfifoNameRead»_V_din))) & " instead of :" & to_string(input_bit)
					report "on port «connection.castfifoNameRead» incorrectly value computed : " & str(to_integer(unsigned(«connection.castfifoNameRead»_V_din))) & " instead of :" & str(input_bit)
					severity error;
				«ENDIF»
				«IF connection.fifoTypeOut.bool»
					if (input_bit = 1) then
						assert («connection.castfifoNameRead»_V_din  = "1")
						report "on port «connection.castfifoNameRead» 0 instead of 1"
						severity error;
					else
						assert («connection.castfifoNameRead»_V_din  = "0")
						report "on port «connection.castfifoNameRead» 1 instead of 0"
						severity error;
					end if;
				«ENDIF»
				
			
				--assert («connection.castfifoNameRead»_V_din /= std_logic_vector(to_signed(input_bit, «connection.fifoTypeOut.sizeInBits»)))
				--report "on port «connection.castfifoNameRead» correct value computed : " & str(to_integer(signed(«connection.castfifoNameRead»_V_din))) & " equals :" & str(input_bit)
				--severity note;

			end if;
		end if;
	'''
	
	def initOutputs(Instance instance) '''
		«FOR portout : instance.getActor.outputs.filter[! native]»
			«FOR connection : instance.outgoingPortMap.get(portout)»
				«IF connection.targetPort == null»
					«connection.castfifoNameRead»_V_full_n <= '1';
				«ENDIF»
			«ENDFOR»
		«ENDFOR»
	'''
	def castfifoNameWrite(Connection connection) 
		'''«IF connection != null»myStream_cast_«connection.getAttribute("id").objectValue»_write«ENDIF»'''

	def castfifoNameRead(Connection connection) 
		'''«IF connection != null»myStream_cast_«connection.getAttribute("id").objectValue»_read«ENDIF»'''
	
	def fifoName(Connection connection)
		'''myStream_«connection.getAttribute("id").objectValue»_V'''
	
	def fifoTypeOut(Connection connection) {
		if(connection.sourcePort == null){
			connection.targetPort.type}
		else{
			connection.sourcePort.type
		}
	}
	
	def fifoTypeIn(Connection connection) {
		if(connection.targetPort == null) {
			connection.sourcePort.type}
		else {
			connection.targetPort.type
		}
	}
	
	def fifoType(Connection connection) {
		if (connection.sourcePort != null) {
			connection.sourcePort.type
		} else {
			connection.targetPort.type
		}
	}
}