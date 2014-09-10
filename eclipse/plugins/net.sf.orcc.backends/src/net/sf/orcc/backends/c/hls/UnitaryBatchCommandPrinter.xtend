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

import static net.sf.orcc.util.OrccAttributes.*

/**
 * Bach command for each actor
 *  
 * @author Khaled Jerbi and Mariem Abid
 * 
 */
class UnitaryBatchCommandPrinter extends net.sf.orcc.backends.c.InstancePrinter {

	def getFileContentBatch() '''
		:: The path variable must be set system wide to include vivado_hls and msys binaries, e.g.
		:: PATH=D:\Users\JoeBloggs\2013.4\Xilinx\Vivado_HLS\2013.4\bin;%PATH%;D:\Users\JoeBloggs\2013.4\Xilinx\Vivado_HLS\2013.4\msys\bin
		::
		:: Two environment variables must be set system wide to include vivado_hls , e.g.
		:: set AUTOESL_HOME=D:\Users\JoeBloggs\2013.4\Xilinx\Vivado_HLS\2013.4\bin
		:: set VIVADO_HLS_HOME=D:\Users\JoeBloggs\2013.4\Xilinx\Vivado_HLS\2013.4\bin

		if not "x%PROCESSOR_ARCHITECTURE%" == "xAMD64" goto _NotX64
		set COMSPEC=%WINDIR%\SysWOW64\cmd.exe
		goto START
		:_NotX64
		set COMSPEC=%WINDIR%\System32\cmd.exe
		:START
		
		cd ..
			
		%COMSPEC% /C vivado_hls -f script_«entityName».tcl
		«FOR port : actor.inputs»
			«val connection = incomingPortMap.get(port)»
			«IF connection != null»
				%COMSPEC% /C vivado_hls -f script_cast_«entityName»_«connection.targetPort.name»_write.tcl
			«ENDIF»
		«ENDFOR»		
		«FOR port : actor.outputs.filter[! native]»			
			«FOR connection : outgoingPortMap.get(port)»
				%COMSPEC% /C vivado_hls -f script_cast_«entityName»_«connection.sourcePort.name»_read.tcl					
			«ENDFOR»
		«ENDFOR»
		«IF actor.hasAttribute(DIRECTIVE_DEBUG)»
			«FOR action : actor.actions»
				%COMSPEC% /C vivado_hls -f script_cast_«entityName»_tab_«action.name»_read.tcl
			«ENDFOR»
		«ENDIF»
		
		copy %cd%\TopVHDL\sim_package.vhd %cd%\«entityName»TopVHDL
		copy %cd%\TopVHDL\ram_tab.vhd %cd%\«entityName»TopVHDL
		
		
		copy %cd%\subProject_«entityName»\solution1\syn\vhdl %cd%\«entityName»TopVHDL
		
		«FOR port : actor.inputs»
			«val connection = incomingPortMap.get(port)»
			«IF connection != null»
				copy %cd%\subProject_cast_«entityName»_«connection.targetPort.name»_write\solution1\syn\vhdl %cd%\«entityName»TopVHDL
			«ENDIF»
		«ENDFOR»
		«FOR port : actor.outputs.filter[! native]»
			«FOR connection : outgoingPortMap.get(port)»
				copy %cd%\subProject_cast_«entityName»_«connection.sourcePort.name»_read\solution1\syn\vhdl %cd%\«entityName»TopVHDL
			«ENDFOR»
		«ENDFOR»
		«IF actor.hasAttribute(DIRECTIVE_DEBUG)»
			«FOR action : actor.actions»
				copy %cd%\subProject_cast_«entityName»_tab_«action.name»_read\solution1\syn\vhdl %cd%\«entityName»TopVHDL
			«ENDFOR»
		«ENDIF»
			
		
	'''
}
