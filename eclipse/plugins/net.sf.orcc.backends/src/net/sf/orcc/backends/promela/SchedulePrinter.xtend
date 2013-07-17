/*
 * Copyright (c) 2012, Åbo Akademi
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
package net.sf.orcc.backends.promela

import java.io.File
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil
import net.sf.orcc.backends.promela.transform.PromelaSchedulabilityTest
import java.util.Set

/**
 * Generated an initial schedule with only actor level scheduling completed 
 *  
 * @author Johan Ersfolk
 * 
 */
class SchedulePrinter extends PromelaTemplate {
	
	val Network network;
	val Set<PromelaSchedulabilityTest> actorSchedulers;
	
	new(Network network, Set<PromelaSchedulabilityTest> actorSchedulers) {
		this.network = network
		this.actorSchedulers = actorSchedulers
	}
	
	def print(String targetFolder) {
		
		val content = networkFileContent
		val file = new File(targetFolder + File::separator + "schedule_" + network.simpleName + ".xml")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def getNetworkFileContent() 
	'''
		<!-- Generated from "«network.name»" -->
		
		<superactorlist>
		«FOR actorScheduler : actorSchedulers»
			«actorScheduler.superActor»
		«ENDFOR»
		</superactorlist>
		
	'''
	
	def superActor(PromelaSchedulabilityTest actorSched) { 
	'''
		<superactor name="cluster_z">
			<actor name="chipMapper_inst"/>
			«actorSched.printFSM»
			«actorSched.printSchedule»
		</superactor>
	'''
	}
	
}