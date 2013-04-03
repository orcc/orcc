/*
 * Copyright (c) 2012, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
 
 package net.sf.orcc.backends.cplusplus

import java.util.List
import net.sf.orcc.backends.cplusplus.entities.util.YaceEntitiesSwitch
import net.sf.orcc.backends.cplusplus.entities.Interface
import net.sf.orcc.backends.cplusplus.entities.Receiver
import net.sf.orcc.backends.cplusplus.entities.Sender
import net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet


/*
 * An entities printer.
 *  
 * @author Ghislain Roquier
 * 
 */
class EntitiesPrinter extends YaceEntitiesSwitch<CharSequence> {
	
	List<Interface> interfaces
	
	ExprAndTypePrinter typePrinter
	
	new () {
		interfaces = newArrayList()
		typePrinter = new ExprAndTypePrinter
	}
	
	override caseReceiver(Receiver receiver) {
		val inter = interfaces.findFirst(intf | intf.equals(receiver.intf))
		'''
		«IF inter == null»«inter.doSwitch»«ENDIF»
		Receiver<«typePrinter.doSwitch(receiver.output.type)»> inst_«receiver»
		'''
	}
	
	override caseSender(Sender sender) {		
	}

	override caseInterfaceEthernet(InterfaceEthernet intf) {
	interfaces.add(intf) '''
	«IF intf.server»
	TcpServerSocket «intf.id»(«intf.portNumber»);
	«ELSE»
	TcpServerClient«intf.id»("«intf.ip»", «intf.portNumber»);
	«ENDIF»
	'''
	}
}