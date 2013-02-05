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
import net.sf.orcc.ir.TypeBool

/**
 * Compile top Network c source code 
 *  
 * @author Antoine Lorence and Khaled Jerbi
 * 
 */
class NetworkPrinter extends net.sf.orcc.backends.c.NetworkPrinter {

	new(Network network, Map<String,Object> options) {
		super(network, options)
	}

	override getNetworkFileContent() '''
		// Generated from "«network.name»"

		#include <hls_stream.h>
		using namespace hls;

		typedef signed char i8;
		typedef short i16;
		typedef int i32;
		typedef long long int i64;
		
		typedef unsigned char u8;
		typedef unsigned short u16;
		typedef unsigned int u32;
		typedef unsigned long long int u64;
		
		/////////////////////////////////////////////////
		// FIFO pointer assignments
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.assignFifo»
		«ENDFOR»
		
		
		
		/////////////////////////////////////////////////
		// Action initializes schedulers
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«IF (!instance.actor.stateVars.empty) || (instance.actor.hasFsm )»
				void «instance.name»_initialize();
			«ENDIF»
		«ENDFOR»
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			void «instance.name»_scheduler();
		«ENDFOR»
		
		
		////////////////////////////////////////////////////////////////////////////////
		// Main
		int main() {
			«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
				«IF (!instance.actor.stateVars.empty) || (instance.actor.hasFsm )»
					 «instance.name»_initialize();
				«ENDIF»
			«ENDFOR»
			
			while(1) {
				«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
					«instance.name»_scheduler();
				«ENDFOR»
			}
			return 0;
		}
	'''
	
	def getProjectFileContent() '''
		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="com.autoesl.autopilot.project" top="main" projectType="C/C++">
		  <files>
		    <file name="src/«network.simpleName».c" sc="0" tb="false" cflags=""/>
		    «FOR instance : network.children.filter(typeof(Instance))»
		    	<file name="src/«instance.name».c" sc="0" tb="false" cflags=""/>
		    «ENDFOR»
		  </files>
		  <solutions>
		    <solution name="solution1" status="active"/>
		  </solutions>
		  <includePaths/>
		  <libraryPaths/>
		</project>
	'''
	override assignFifo(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF (!(connList.head.source instanceof Port) && !(connList.head.target instanceof Port))||(!(connList.head.source instanceof Port) && (connList.head.target instanceof Port))»
				«printFifoAssignHLS(connList.head )»
			«ENDIF»			
		«ENDFOR»
		«FOR connList : instance.incomingPortMap.values»
			«IF ((connList.source instanceof Port) && !(connList.target instanceof Port))»
				«printFifoAssignHLS(connList)»
			«ENDIF»
		«ENDFOR»
	'''
	
	def printFifoAssignHLS(Connection connection) '''
		stream<«connection.fifoType.doSwitch»> «connection.fifoName»;
	'''
	
	override print(String targetFolder) {
		val i = super.print(targetFolder)
		val contentProject = projectFileContent
		val contentNetwork = networkFileContent
		val contentVhdlTop = fifoFileContent
		val contentTestBench = "int test() { return 0;}"
		val projectFile = new File(targetFolder + File::separator + "vivado_hls.app")
		val NetworkFile = new File(targetFolder + File::separator + network.simpleName + ".cpp")
		val testBenchFile = new File(targetFolder + File::separator + "testBench" + ".cpp")
		val FifoVhdlFile = new File(targetFolder + File::separator + "genericFifo" + ".vhd")
		if(needToWriteFile(contentNetwork, NetworkFile)) {
			printFile(contentProject, projectFile)
			printFile(contentNetwork, NetworkFile)
			printFile(contentTestBench, testBenchFile)
			printFile(contentVhdlTop, FifoVhdlFile)
			return i
		} else {
			return i + 1
		}
	}
	
	def fifoName(Connection connection)
		'''myStream_«connection.getAttribute("id").objectValue»'''
	
	def fifoType(Connection connection) {
		if (connection.sourcePort != null){	
			connection.sourcePort.type
		}else{
			connection.targetPort.type
		}
	}
		
	override caseTypeBool(TypeBool type) 
		'''bool'''
		
	def fifoFileContent()'''
	library ieee;
	use ieee.std_logic_1164.all;
	use ieee.std_logic_arith.all;
	entity FIFO_main_myStream is 
	    generic (
	        width : integer := 15);
	    port (
	        clk : IN STD_LOGIC;
	        reset : IN STD_LOGIC;
	        if_empty_n : OUT STD_LOGIC;
	        if_read : IN STD_LOGIC;
	        if_dout : OUT STD_LOGIC_VECTOR(width downto 0);
	        if_full_n : OUT STD_LOGIC;
	        if_write : IN STD_LOGIC;
	        if_din : IN STD_LOGIC_VECTOR(width downto 0));
	end entity;
	
	architecture rtl of FIFO_main_myStream is
	    type memtype is array (0 to «fifoSize») of STD_LOGIC_VECTOR(width downto 0);
	    signal mStorage : memtype;
	    signal mInPtr  : UNSIGNED(«closestLog_2(fifoSize)» - 1 downto 0) := (others => '0');
	    signal mOutPtr : UNSIGNED(«closestLog_2(fifoSize)» - 1 downto 0) := (others => '0');
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
	                    if (mOutPtr = «fifoSize») then
	                        mOutPtr <= (others => '0');
	                        mFlag_nEF_hint <= not mFlag_nEF_hint;
	                    else
	                        mOutPtr <= mOutPtr + 1;
	                    end if;
	                end if;
	                if if_write = '1' and internal_full_n = '1' then
	                    mStorage(CONV_INTEGER(mInPtr)) <= if_din;
	                    if (mInPtr = «fifoSize») then
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
	
	def closestLog_2(int x) {
		var p = 1;
		var r = 0;
		while (p < x) {
			p = p * 2
			r = r + 1
		}
		return r;
	}
}