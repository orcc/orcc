/*
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of IRISA nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
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
package net.sf.orcc.backends.llvm.tta

import net.sf.orcc.backends.llvm.tta.architecture.FunctionUnit
import net.sf.orcc.backends.llvm.tta.architecture.Implementation
import net.sf.orcc.backends.llvm.tta.architecture.Processor
import net.sf.orcc.backends.llvm.tta.architecture.RegisterFile
import net.sf.orcc.backends.llvm.tta.architecture.util.ArchitectureSwitch
import net.sf.orcc.util.OrccLogger
import org.eclipse.emf.common.util.EMap

class TCE_Processor_IDF extends ArchitectureSwitch<CharSequence> {
	
	EMap<String, Implementation> hwDb;
	
	new(EMap<String, Implementation> hwDb) {
		this.hwDb = hwDb;
	}
		
		
	override caseProcessor(Processor processor)
		'''
		<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
		<adf-implementation>
			
			<ic-decoder-plugin>
				<name>DefaultICDecoder</name>
				<file>DefaultICDecoderPlugin.so</file>
				<hdb-file>asic_130nm_1.5V.hdb</hdb-file>
			</ic-decoder-plugin>
			
			«FOR fu: processor.functionUnits»
				«fu.doSwitch»
			«ENDFOR»
			«FOR rf: processor.registerFiles»
				«rf.doSwitch»
			«ENDFOR»
			
		</adf-implementation>
		'''

	override caseFunctionUnit(FunctionUnit fu) {
		val impl = hwDb.get(fu.implementation)
		if(impl == null){
			OrccLogger::warnln("Unknown implementation of " + fu.name)
		} else {
			'''
			<fu name="«fu.name»">
				<hdb-file>«impl.hdbFile»</hdb-file>
				<fu-id>«impl.id»</fu-id>
			</fu>
			'''
		}
	}

	override caseRegisterFile(RegisterFile rf) {
		val impl = hwDb.get(rf.implementation)
		if(impl == null){
			OrccLogger::warnln("Unknown implementation of " + rf.name)
		} else {
			'''
			<rf name="«rf.name»">
				<hdb-file>«impl.hdbFile»</hdb-file>
			    <rf-id>«impl.id»</rf-id>
			</rf>
			'''
		}
	}
		
}