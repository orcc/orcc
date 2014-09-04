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
package net.sf.orcc.backends.c

import java.io.File
import java.util.Map
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil

/**
 * Generate and print couples of traces file names.
 *  
 * @author Khaled JERBI
 * 
 */
class TracesPrinter extends CTemplate {
	
	protected val Network network;
	
	new(Network network, Map<String, Object> options) {
		super(options)
		this.network = network
	}
	
	/**
	 * Print file content for the network
	 * @param targetFolder folder to print the network file
	 * @return 1 if file was cached, 0 if file was printed
	 */
	def print(String targetFolder) {

		val content = tracesFileContent
		val file = new File(targetFolder + File::separator + "traces.txt")

		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def protected getTracesFileContent() '''
		«FOR connection : network.connections»
			«connection.target.label»_«connection.targetPort.name».txt «connection.source.label»_«connection.sourcePort.name».txt «connection.target.label» «connection.targetPort.name» «connection.source.label» «connection.sourcePort.name» «connection.safeSize»
		«ENDFOR»
	'''
}