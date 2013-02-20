/*
 * Copyright (c) 2013, University of Rennes 1 / IRISA
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
 *   * Neither the name of the University of Rennes 1 / IRISA nor the names of its
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
package net.sf.orcc.tools.stats

import java.io.File
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil

/**
 * Generate statistics about an application.
 * 
 * @author Hervé Yviquel
 */
class StatisticsPrinter {
	
	def print(String targetFolder, Network network) {
		val file = new File(targetFolder + File::separator + "stats.csv")
		OrccUtil::printFile(network.content, file)
	}
	
	def private getContent(Network network) '''
		«networkHeader»
		«network.stats»
		
		«childrenHeader»
		«FOR instance : network.children.filter(typeof(Instance))»
			«instance.stats»
		«ENDFOR»
		«FOR actor : network.children.filter(typeof(Actor))»
			«actor.stats»
		«ENDFOR»
		
		«connectionsHeader»
		«FOR conn : network.connections »
			«conn.stats»
		«ENDFOR»
	'''
	
	def protected getNetworkHeader() 
		'''Name, Actors, Connections'''
		
	def protected getStats(Network network)
		'''«network.name», «network.children.size», «network.connections.size»'''
	
	def protected getChildrenHeader() 
		'''Name, Incoming, Outgoing, Inputs, Outputs, Actions, MoC'''

	def private getStats(Actor actor) 
		'''«actor.name», «actor.incoming.size», «actor.outgoing.size», «actor.inputs.size», «actor.outputs.size», «actor.actions.size», «actor.moC»'''
	
	def private getStats(Instance instance) {
		val actor = instance.actor
		'''«instance.name», «instance.incoming.size», «instance.outgoing.size», «actor.inputs.size», «actor.outputs.size», «actor.actions.size», «actor.moC»'''
	}
	
	def protected getConnectionsHeader() 
		'''Source, SrcPort, Target, TgtPort, Size'''
	
	def protected getStats(Connection conn) 
		'''«conn.source.label», «conn.sourcePort.name», «conn.target.label», «conn.targetPort.name», «conn.size»'''

}