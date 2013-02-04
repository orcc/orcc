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
	end entity TopDesign;
		
		-- ----------------------------------------------------------------------------------
		-- Architecture Declaration
		-- ----------------------------------------------------------------------------------

	architecture rtl of TopDesign is

		-- ----------------------------------------------------------------------------------
		-- Signal Instantiation
		-- ----------------------------------------------------------------------------------
		
		signal tmp_ap_clk :  STD_LOGIC;
		signal tmp_ap_rst :  STD_LOGIC;
		signal tmp_ap_start :  STD_LOGIC;
		signal tmp_ap_done :  STD_LOGIC;
		signal tmp_ap_idle :  STD_LOGIC;
		signal tmp_ap_ready :  STD_LOGIC;
		
		-- FIFO Instantiation
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.assignFifoSignal»
		«ENDFOR»
		
	-- ----------------------------------------------------------------------------------
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
		tmp_ap_start <= ap_start;
		tmp_ap_clk <= ap_clk;
		tmp_ap_rst <= ap_rst;
		
	end architecture rtl;
	'''
	
	def assignNetworkPorts (Instance instance)'''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && (connList.head.target instanceof Port)»
				«connList.head.fifoName»_V_din <= tmp_«connList.head.fifoName»_din;
				--tmp_«connList.head.fifoName»_full_n <= «connList.head.fifoName»_V_full_n;
				«connList.head.fifoName»_V_write <= tmp_«connList.head.fifoName»_write;
			«ENDIF»
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF (connList.source instanceof Port) && !(connList.target instanceof Port) »
				tmp_«connList.fifoName»_dout <= «connList.fifoName»_V_dout;
				tmp_«connList.fifoName»_empty_n <= «connList.fifoName»_V_empty_n;
				«connList.fifoName»_V_read <= tmp_«connList.fifoName»_read;
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
				«connList.head.fifoName»_V_din => tmp_«connList.head.fifoName»_din,
				«connList.head.fifoName»_V_full_n => tmp_«connList.head.fifoName»_full_n,
				«connList.head.fifoName»_V_write => tmp_«connList.head.fifoName»_write,
			«ENDFOR»
			«FOR connList : instance.incomingPortMap.values»
				«connList.fifoName»_V_dout => tmp_«connList.fifoName»_dout,
				«connList.fifoName»_V_empty_n => tmp_«connList.fifoName»_empty_n,
				«connList.fifoName»_V_read => tmp_«connList.fifoName»_read,
			«ENDFOR»
			
			ap_start => tmp_ap_start,
			ap_clk => tmp_ap_clk,
			ap_rst => tmp_ap_rst,
			ap_done => tmp_ap_done,
			ap_idle => tmp_ap_idle,
			ap_ready => tmp_ap_ready
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
			clk => tmp_ap_clk,
			reset => tmp_ap_rst,
			if_din => tmp_«connection.fifoName»_din,
			if_full_n => tmp_«connection.fifoName»_full_n,
			if_write => tmp_«connection.fifoName»_write,
			if_dout => tmp_«connection.fifoName»_dout,
			if_empty_n => tmp_«connection.fifoName»_empty_n,
			if_read => tmp_«connection.fifoName»_read
		);
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
		«IF instance.actor.outputs.empty»
			outFIFO_«instance.name»_V_din    : OUT STD_LOGIC_VECTOR (31 downto 0);
			outFIFO_«instance.name»_V_full_n : IN STD_LOGIC;
			outFIFO_«instance.name»_V_write  : OUT STD_LOGIC;
		«ENDIF»
	'''
	
	def printOutputFifoAssignHLS( Connection connection) '''
		«connection.fifoName»_V_din    : OUT STD_LOGIC_VECTOR («connection.fifoType.sizeInBits - 1» downto 0);
		«connection.fifoName»_V_full_n : IN STD_LOGIC;
		«connection.fifoName»_V_write  : OUT STD_LOGIC;
	'''
	
	def printInputFifoAssignHLS(Connection connection) '''
		«connection.fifoName»_V_dout   : IN STD_LOGIC_VECTOR («connection.fifoType.sizeInBits - 1» downto 0);
		«connection.fifoName»_V_empty_n : IN STD_LOGIC;
		«connection.fifoName»_V_read    : OUT STD_LOGIC;
	'''
	
	def assignFifoSignal(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«printOutputFifoSignalAssignHLS(connList.head )»
			«IF connList.head.target instanceof Port»
				«printInputFifoSignalAssignHLS(connList.head)»
			«ENDIF»
		«ENDFOR»
		«FOR connection : instance.incomingPortMap.values»
			«printInputFifoSignalAssignHLS(connection)»
			«IF connection.source instanceof Port»
					«printOutputFifoSignalAssignHLS(connection)»
			«ENDIF»
		«ENDFOR»
		
	'''
	
	def printOutputFifoSignalAssignHLS( Connection connection) '''
		signal tmp_«connection.fifoName»_din    :  STD_LOGIC_VECTOR («connection.fifoType.sizeInBits - 1»  downto 0);
		signal tmp_«connection.fifoName»_full_n :  STD_LOGIC;
		signal tmp_«connection.fifoName»_write  :  STD_LOGIC;
	'''
	
	def printInputFifoSignalAssignHLS(Connection connection) '''
		signal tmp_«connection.fifoName»_dout   :  STD_LOGIC_VECTOR («connection.fifoType.sizeInBits - 1»  downto 0);
		signal tmp_«connection.fifoName»_empty_n :  STD_LOGIC;
		signal tmp_«connection.fifoName»_read    :  STD_LOGIC;
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
		'''myStream_«connection.getAttribute("id").objectValue»'''
	
	def fifoType(Connection connection) {
		if (connection.sourcePort != null){	
			connection.sourcePort.type
		}else{
			connection.targetPort.type
		}
	}
}
 