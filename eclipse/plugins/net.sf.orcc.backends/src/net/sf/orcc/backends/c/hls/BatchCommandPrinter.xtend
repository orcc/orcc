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
import java.io.File

/**
 * Compile top Network c source code 
 *  
 * @author Khaled Jerbi
 * 
 */
 
 class BatchCommandPrinter extends net.sf.orcc.backends.c.NetworkPrinter {

	new(Network bat, Map<String,Object> options) {
		super(bat, options)
	}

	override getNetworkFileContent()'''
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			cd ..\subProject_«instance.name»
			start batchCommand_«instance.name».bat
		«ENDFOR»
	''' 
	
	override print(String targetFolder) {
		
		val contentNetwork = networkFileContent
		val copier = getcopierContent(targetFolder)
		val NetworkFile = new File(targetFolder + File::separator + "Command" + ".bat")
		val copierFile = new File(targetFolder + File::separator + "copier" + ".bat")
		
		if(needToWriteFile(contentNetwork, NetworkFile)) {
			printFile(contentNetwork, NetworkFile)
			printFile(copier, copierFile)
			return 0
		} else {
			return 1
		}
	}
	
	def getcopierContent (String target){
		
	'''
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
				xcopy /s D:\WORK\Tests\MPEG4SPP2_ResearchDesign\VIVADOIntraTBench\src\subProject_«instance.name»\testAdd\solution\syn\vhdl D:\WORK\Tests\MPEG4SPP2_ResearchDesign\VIVADOIntraTBench\src\generatedVHDL
		«ENDFOR»
		
	'''
	}
}
 