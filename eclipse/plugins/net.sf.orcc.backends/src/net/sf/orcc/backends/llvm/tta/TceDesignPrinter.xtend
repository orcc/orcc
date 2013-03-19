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

import java.io.File
import net.sf.orcc.backends.llvm.tta.architecture.Design
import net.sf.orcc.backends.llvm.tta.architecture.Processor
import net.sf.orcc.df.Port
import net.sf.orcc.util.OrccUtil
import net.sf.orcc.df.Actor

/*
 * The template to print the Multiprocessor Architecture Description File.
 *  
 * @author Herve Yviquel
 * 
 */
class TceDesignPrinter extends TTAPrinter {
	
	String path;
	
	new(String path){
		this.path = path;
	}
	
	def print(Design design, String targetFolder) {
		val file = new File(targetFolder + File::separator + "top.pndf")
		OrccUtil::printFile(design.pndf, file)
	}
	
	def private getPndf(Design design)
		'''
		<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
		<processor-network version="0.1">
			«FOR processor:design.processors»
				«processor.pndf»
			«ENDFOR»
		</processor-network>
		'''
	
	def private getPndf(Processor processor)
		'''
		<processor name="«processor.name»" >
			<adf>«path»/«processor.name»/«processor.name».adf</adf>
			<tpef>«path»/«processor.name»/«processor.name».tpef</tpef>
			«FOR vertex: processor.mappedActors»
				«val actor = vertex.getAdapter(typeof(Actor))»
				«FOR input: actor.inputs.filter(port | !port.native)»
					«val incoming = actor.incomingPortMap.get(input)»
					«IF incoming != null && !processor.mappedActors.contains(incoming.source)»
						<input name="«input.name»">
							<address-space>«processor.getMemory(incoming).name»</address-space>
							<var-name>fifo_«incoming.getValueAsObject("id").toString»</var-name>
							<signed>«input.type.int»</signed>
							<width>«input.width»</width>
							<size>«incoming.size»</size>
							<trace>«path»/trace/«vertex.label»_«input.name».txt</trace>
						</input>
					«ENDIF»
				«ENDFOR»
				«FOR output : actor.outputs.filter[!native]»
					«FOR outgoing : actor.outgoingPortMap.get(output)»
						«IF !processor.mappedActors.contains(outgoing.target)»
							«val id = outgoing.getValueAsObject("id").toString»
							<output name="«output.name»_«id»">
								<address-space>«processor.getMemory(outgoing).name»</address-space>
								<var-name>fifo_«id»</var-name>
								<signed>«output.type.int»</signed>
								<width>«output.width»</width>
								<size>«outgoing.size»</size>
								<trace>«path»/trace/«vertex.label»_«output.name».txt</trace>
							</output>
						«ENDIF»
					«ENDFOR»
				«ENDFOR»
			«ENDFOR»
		</processor>
		'''
		
	def private getWidth(Port port) {
		Math::ceil(port.type.sizeInBits/8.0).intValue
	}
	
}
