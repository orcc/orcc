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
package net.sf.orcc.backends.util

import java.io.File
import java.util.List
import java.util.Map
import net.sf.orcc.df.Instance

/**
 * Printer used to create the xcf file, containing information on
 * mapping between actors and processor cores
 * 
 * @author Antoine Lorence
 * 
 */
class XcfPrinter extends CommonPrinter {
	
	Map<String, List<Instance>> coreToInstanceMap
	
	var i = 0
	
	new(Map<String, List<Instance>> coreToInstanceMap) {
		this.coreToInstanceMap = coreToInstanceMap
	}
	
	def printXcfFile(String fileName) {
		printFile(compileXcfFile, new File(fileName))
	}
	
	def compileXcfFile() '''
		<?xml version="1.0" encoding="UTF-8"?>
		<Configuration>
			<Partitioning>
				«FOR instances : coreToInstanceMap.values»
					«instances.printPartition»
				«ENDFOR»
			</Partitioning>
		</Configuration>
	'''
	
	def printPartition(List<Instance> instances) '''
		<Partition id="« i = i + 1 »">
			«FOR instance : instances»
				<Instance id="«instance.name»"/>
			«ENDFOR»
		</Partition>
	'''
}