/*
 * Copyright (c) 2012, IETR/INSA of Rennes
 * Copyright (c) 2014, Heriot-Watt University 
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

import net.sf.orcc.df.Instance

/**
 *Batch Command for the network
 *  
 * @author Rob Stewart and Khaled Jerbi and Mariem Abid
 * 
 */
class BatchCommandPrinterLinux extends net.sf.orcc.backends.c.NetworkPrinter {

	override getNetworkFileContent() '''
		# Two additions to your ~/.bash_profile or ~/.profile must be made,
		# to modify the $XILINXD_LICENSE_FILE and $PATH environment variables
		# 
		# Step 1. The $XILINXD_LICENSE_FILE environment variable must be set, e.g.
		# export XILINXD_LICENSE_FILE="<path>/Xilinx.lic"
		#
		# Step 2. The $PATH environment variable must include the Vivado HLS bin/ e.g.
		# export PATH=~/path/to/vivado-hls/bin:$PATH
		
		cd ..
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			
			vivado_hls -f script_«instance.name».tcl
			«FOR port : instance.getActor.inputs»
				«val connection = instance.incomingPortMap.get(port)»
				«IF connection !== null && connection.sourcePort === null»
					vivado_hls -f script_cast_«instance.name»_«connection.targetPort.name»_write.tcl
				«ENDIF»
			«ENDFOR»		
			«FOR port : instance.getActor.outputs.filter[! native]»			
				«FOR connection : instance.outgoingPortMap.get(port)»
					«IF connection.targetPort === null»
						vivado_hls -f script_cast_«instance.name»_«connection.sourcePort.name»_read.tcl
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
			
		«ENDFOR»
		
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			cp subProject_«instance.name»/solution1/syn/vhdl/* TopVHDL/
			«FOR port : instance.getActor.inputs»
				«val connection = instance.incomingPortMap.get(port)»
				«IF connection !== null && connection.sourcePort === null»
					cp subProject_cast_«instance.name»_«connection.targetPort.name»_write/solution1/syn/vhdl/* TopVHDL/
				«ENDIF»
			«ENDFOR»
			«FOR port : instance.getActor.outputs.filter[! native]»
				«FOR connection : instance.outgoingPortMap.get(port)»
					«IF connection.targetPort === null»				
						cp subProject_cast_«instance.name»_«connection.sourcePort.name»_read/solution1/syn/vhdl/* TopVHDL/
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
		«ENDFOR»
	'''

}
