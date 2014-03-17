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

import net.sf.orcc.util.OrccUtil
import java.util.Map

import java.io.File
import net.sf.orcc.df.Port

/**
 * Compile top Network c source code 
 *  
 * @author Khaled Jerbi
 * 
 */
class UnitaryBatchCommandPrinter extends net.sf.orcc.backends.c.InstancePrinter {

	new(Map<String, Object> options) {
		super(options)
	}

	def getFileContentBatch() '''
		PATH=D:\Users\mabid\2013.3\Xilinx\Vivado_HLS\2013.3\bin;%PATH%;D:\Users\mabid\2013.3\Xilinx\Vivado_HLS\2013.3\msys\bin
		set AUTOESL_HOME=D:\Users\mabid\2013.3\Xilinx\Vivado_HLS\2013.3\bin
		set VIVADO_HLS_HOME=D:\Users\mabid\2013.3\Xilinx\Vivado_HLS\2013.3\bin
		
		if not "x%PROCESSOR_ARCHITECTURE%" == "xAMD64" goto _NotX64
		set COMSPEC=%WINDIR%\SysWOW64\cmd.exe
		goto START
		:_NotX64
		set COMSPEC=%WINDIR%\System32\cmd.exe
		:START
		
			cd ..
				
			%COMSPEC% /C vivado_hls -f script_«instance.name».tcl
				«FOR portIN : instance.getActor.inputs»
					«IF instance.incomingPortMap.get(portIN).sourcePort != null»
						%COMSPEC% /C vivado_hls -f script_cast_«instance.name»_«instance.incomingPortMap.get(portIN).targetPort.name»_write.tcl
					«ENDIF»
				«ENDFOR»		
				«FOR portout : instance.getActor.outputs.filter[! native]»			
					«IF instance.outgoingPortMap.get(portout).head.targetPort != null»
						%COMSPEC% /C vivado_hls -f script_cast_«instance.name»_«instance.outgoingPortMap.get(portout).head.sourcePort.name»_read.tcl					
					«ENDIF»
				«ENDFOR»
		
		copy %cd%\TopVHDL\sim_package.vhd %cd%\«instance.name»TopVHDL
		copy %cd%\TopVHDL\ram_tab.vhd %cd%\«instance.name»TopVHDL
		
		
			copy %cd%\subProject_«instance.name»\solution1\syn\vhdl %cd%\«instance.name»TopVHDL
			
			«FOR portIN : instance.getActor.inputs»
				«IF instance.incomingPortMap.get(portIN).sourcePort != null»
					copy %cd%\subProject_cast_«instance.name»_«instance.incomingPortMap.get(portIN).targetPort.name»_write\solution1\syn\vhdl %cd%\«instance.
			name»TopVHDL
				«ENDIF»
			«ENDFOR»
			«FOR portout : instance.getActor.outputs.filter[! native]»
				
					«IF instance.outgoingPortMap.get(portout).head.targetPort != null»
						copy %cd%\subProject_cast_«instance.name»_«instance.outgoingPortMap.get(portout).head.sourcePort.name»_read\solution1\syn\vhdl %cd%\«instance.
			name»TopVHDL
						
				«ENDIF»
			«ENDFOR»
			
		
	'''

	override print(String targetFolder) {
		val contentNetwork = getFileContentBatch
		val NetworkFile = new File(targetFolder + File::separator + "Command" + "_" + instance.name + ".bat")

		if (needToWriteFile(contentNetwork, NetworkFile)) {
			OrccUtil::printFile(contentNetwork, NetworkFile)
			return 0
		} else {
			return 1
		}
	}

}
