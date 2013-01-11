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

import net.sf.orcc.df.Network
import java.util.Map
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Port
import net.sf.orcc.df.Connection
import java.io.File

/**
 * Compile top Network c source code 
 *  
 * @author Khaled Jerbi
 * 
 */
 
 class TopVhdlPrinter extends net.sf.orcc.backends.c.NetworkPrinter {

	new(Network VhdlNetwork, Map<String,Object> options) {
		super(VhdlNetwork, options)
	}

	override getNetworkFileContent()'''
	library ieee;
	use ieee.std_logic_1164.all;
	use ieee.std_logic_arith.all;
	
	entity TopDesign is
	port(
		ap_clk : IN STD_LOGIC;
		ap_rst : IN STD_LOGIC;
		ap_start : IN STD_LOGIC;
		ap_done : OUT STD_LOGIC;
		ap_idle : OUT STD_LOGIC;
		ap_ready : OUT STD_LOGIC;
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.assignFifo»
		«ENDFOR»
		ap_return : OUT STD_LOGIC_VECTOR (31 downto 0));
	end entity DecoderIntra;
		
		-- ----------------------------------------------------------------------------------
		-- Architecture Declaration
		-- ----------------------------------------------------------------------------------

	architecture rtl of TopDesign is

		-- ----------------------------------------------------------------------------------
		-- Signal Instantiation
		-- ----------------------------------------------------------------------------------
		
		ap_clk :  STD_LOGIC;
		ap_rst :  STD_LOGIC;
		ap_start :  STD_LOGIC;
		
		-- FIFO Instantiation
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.assignFifoSignal»
		«ENDFOR»
		
	*-- ----------------------------------------------------------------------------------
	-- Components of the Network
	-- ---------------------------------------------------------------------------------------
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.declareComponentSignal»
		«ENDFOR»
		
		component FIFO_main_myStream IS
					generic ( width : integer := INTEGER );
			port (
			clk : IN STD_LOGIC;
			reset : IN STD_LOGIC;
			if_din : IN STD_LOGIC_VECTOR (width downto 0);
			if_full_n : OUT STD_LOGIC;
			if_write : IN STD_LOGIC;
			if_dout : OUT STD_LOGIC_VECTOR (width downto 0);
			if_empty_n : OUT STD_LOGIC;
			if_read : IN STD_LOGIC );
		end component;
		
	begin
	
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.mappingComponentFifoSignal»
		«ENDFOR»
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.mappingComponentSignal»
		«ENDFOR»
		
	---------------------------------------------------------------------------
	-- Network Ports Instantiation 
	---------------------------------------------------------------------------
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.assignNetworkPorts»
		«ENDFOR»
		
	end architecture rtl;
	'''
	
	def assignNetworkPorts (Instance instance)'''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				«connList.head.fifoName»_V_in <= «connList.head.fifoName»_V_in;
				«connList.head.fifoName»_V_full_n <= «connList.head.fifoName»_V_full_n;
				«connList.head.fifoName»_V_write <= «connList.head.fifoName»_V_write;
			«ENDIF»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF (connList.source instanceof Port) && !(connList.target instanceof Port) »
				«connList.fifoName»_V_dout <= «connList.fifoName»_V_dout;
				«connList.fifoName»_V_empty_n <= «connList.fifoName»_V_empty_n;
				«connList.fifoName»_V_read <= «connList.fifoName»_V_read;
			«ENDIF»
		«ENDFOR»
	''' 
	
	override print(String targetFolder) {
		
		val contentNetwork = networkFileContent
		val NetworkFile = new File(targetFolder + File::separator + network.simpleName+ "Top" + ".vhd")
		
		if(needToWriteFile(contentNetwork, NetworkFile)) {
			printFile(contentNetwork, NetworkFile)
			return 0
		} else {
			return 1
		}
	}
	
	def mappingComponentSignal(Instance instance)'''
		call_«instance.name»_scheduler : component «instance.name»_scheduler
		port map(
			«FOR connList : instance.outgoingPortMap.values»
				«connList.head.fifoName»_V_in => «connList.head.fifoName»_V_in,
				«connList.head.fifoName»_V_full_n => «connList.head.fifoName»_V_full_n,
				«connList.head.fifoName»_V_write => «connList.head.fifoName»_V_write,
			«ENDFOR»
			«FOR connList : instance.incomingPortMap.values»
				«connList.fifoName»_V_dout => «connList.fifoName»_V_dout,
				«connList.fifoName»_V_empty_n => «connList.fifoName»_V_empty_n,
				«connList.fifoName»_V_read => «connList.fifoName»_V_read,
			«ENDFOR»
			
			ap_start => ap_start,
			ap_clk => ap_clk,
			ap_rst => ap_rst
			);
	'''
	
	
	
	def mappingComponentFifoSignal(Instance instance)'''
		«FOR conList : instance.outgoingPortMap.values»
			«printFifoMapping(conList.head)»
		«ENDFOR»
		
		«FOR connection : instance.incomingPortMap.values»
			«IF (connection.source instanceof Port) && !(connection.target instanceof Port) »
				«printFifoMapping(connection)»
			«ENDIF»
		«ENDFOR»
	'''
	
	def printFifoMapping (Connection connection)'''
		«connection.fifoName»_V : FIFO_main_myStream 
				generic map (width => «connection.fifoType.sizeInBits - 1»)
		port map (
			clk => ap_clk,
			reset => ap_rst,
			if_din => «connection.fifoName»_V_din,
			if_full_n => «connection.fifoName»_V_full_n,
			if_write => «connection.fifoName»_V_write,
			if_dout => «connection.fifoName»_V_dout,
			if_empty_n => «connection.fifoName»_V_empty_n,
			if_read => «connection.fifoName»_V_read
		);
	'''
	
	override assignFifo(Instance instance) '''
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
	
	def printOutputFifoAssignHLS( Connection connection) '''
		«IF connection.fifoType.bool»
			«connection.fifoName»_V_din    : OUT STD_LOGIC;
		«ELSE»
			«connection.fifoName»_V_din    : OUT STD_LOGIC_VECTOR («connection.fifoType.sizeInBits -1» downto 0);
		«ENDIF»
		«connection.fifoName»_V_full_n : IN STD_LOGIC;
		«connection.fifoName»_V_write  : OUT STD_LOGIC;
	'''
	
	def printInputFifoAssignHLS(Connection connection) '''
		«IF connection.fifoType.bool»
			«connection.fifoName»_V_dout   : IN STD_LOGIC;
		«ELSE»
			«connection.fifoName»_V_dout   : IN STD_LOGIC_VECTOR («connection.fifoType.sizeInBits - 1» downto 0);
		«ENDIF»
		«connection.fifoName»_V_empty_n : IN STD_LOGIC;
		«connection.fifoName»_V_read    : OUT STD_LOGIC;
	'''
	
	def assignFifoSignal(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
				«printOutputFifoAssignHLS(connList.head )»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
				«printInputFifoAssignHLS(connList)»
		«ENDFOR»
	'''
	
	def printOutputFifoSignalAssignHLS( Connection connection) '''
		«IF connection.fifoType.bool»
			signal «connection.fifoName»_V_din    :  STD_LOGIC;
		«ELSE»
			«connection.fifoName»_V_din    :  STD_LOGIC_VECTOR («connection.fifoType.sizeInBits» - 1 downto 0);
		«ENDIF»
		signal «connection.fifoName»_V_full_n :  STD_LOGIC;
		signal «connection.fifoName»_V_write  :  STD_LOGIC;
	'''
	
	def printInputFifoSignalAssignHLS(Connection connection) '''
		«IF connection.fifoType.bool»
			signal «connection.fifoName»_V_dout   :  STD_LOGIC;
		«ELSE»
			signal «connection.fifoName»_V_dout   :  STD_LOGIC_VECTOR («connection.fifoType.sizeInBits» - 1 downto 0);
		«ENDIF»
		signal «connection.fifoName»_V_empty_n :  STD_LOGIC;
		signal «connection.fifoName»_V_read    :  STD_LOGIC;
	'''
	
	def declareComponentSignal(Instance instance) '''
		component «instance.name»_scheduler IS
		port (
			«FOR connList : instance.outgoingPortMap.values»
				«printOutputFifoAssignHLS(connList.head )»
			«ENDFOR»
			«FOR connList : instance.incomingPortMap.values»
				«printInputFifoAssignHLS(connList)»
			«ENDFOR»
			
			ap_clk : IN STD_LOGIC;
			ap_rst : IN STD_LOGIC;
			ap_start : IN STD_LOGIC;
			ap_done : OUT STD_LOGIC;
			ap_idle : OUT STD_LOGIC;
			ap_ready : OUT STD_LOGIC);
		end component;
	'''	
	
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
 
 class FIFOvhdlPrinter extends net.sf.orcc.backends.c.NetworkPrinter {

	new(Network net, Map<String,Object> options) {
		super(net, options)
	}

	def getFIFOFileContent() '''
			library ieee;
	use ieee.std_logic_1164.all;
	use ieee.std_logic_arith.all;
	entity FIFO_main_myStream_0_V is 
	    generic (
	        DATA_WIDTH : integer := 8;
	        ADDR_WIDTH : integer := 10;
	        DEPTH : integer := 513);
	    port (
	        clk : IN STD_LOGIC;
	        reset : IN STD_LOGIC;
	        if_empty_n : OUT STD_LOGIC;
	        if_read : IN STD_LOGIC;
	        if_dout : OUT STD_LOGIC_VECTOR(DATA_WIDTH - 1 downto 0);
	        if_full_n : OUT STD_LOGIC;
	        if_write : IN STD_LOGIC;
	        if_din : IN STD_LOGIC_VECTOR(DATA_WIDTH - 1 downto 0));
	end entity;
	
	architecture rtl of FIFO_main_myStream_0_V is
	    type memtype is array (0 to DEPTH - 1) of STD_LOGIC_VECTOR(DATA_WIDTH - 1 downto 0);
	    signal mStorage : memtype;
	    signal mInPtr  : UNSIGNED(ADDR_WIDTH - 1 downto 0) := (others => '0');
	    signal mOutPtr : UNSIGNED(ADDR_WIDTH - 1 downto 0) := (others => '0');
	    signal internal_empty_n, internal_full_n : STD_LOGIC;
	    signal mFlag_nEF_hint : STD_LOGIC := '0';  -- 0: empty hint, 1: full hint
	begin
	    if_dout <= mStorage(CONV_INTEGER(mOutPtr));
	    if_empty_n <= internal_empty_n;
	    if_full_n <= internal_full_n;
	
	    internal_empty_n <= '0' when mInPtr = mOutPtr and mFlag_nEF_hint = '0' else '1';
	    internal_full_n <= '0' when mInptr = mOutPtr and mFlag_nEF_hint = '1' else '1';
	
	    process (clk)
	    begin
	        if clk'event and clk = '1' then
	            if reset = '1' then
	                mInPtr <= (others => '0');
	                mOutPtr <= (others => '0');
	                mFlag_nEF_hint <= '0'; -- empty hint
	            else
	                if if_read = '1' and internal_empty_n = '1' then
	                    if (mOutPtr = DEPTH -1) then
	                        mOutPtr <= (others => '0');
	                        mFlag_nEF_hint <= not mFlag_nEF_hint;
	                    else
	                        mOutPtr <= mOutPtr + 1;
	                    end if;
	                end if;
	                if if_write = '1' and internal_full_n = '1' then
	                    mStorage(CONV_INTEGER(mInPtr)) <= if_din;
	                    if (mInPtr = DEPTH -1) then
	                        mInPtr <= (others => '0');
	                        mFlag_nEF_hint <= not mFlag_nEF_hint;
	                    else
	                        mInPtr <= mInPtr + 1;
	                    end if;
	                end if;
	            end if;
	        end if;
	    end process;
	   
	end architecture;
	'''
	
	
}

