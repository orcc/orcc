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
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.PrintStream
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.graph.Vertex
import net.sf.orcc.util.OrccLogger
import org.eclipse.emf.common.util.EList

/**
 * Generate statistics about an application.
 * 
 * @author Hervé Yviquel
 */
class StatisticsPrinter {
	
		/**
	 * Create a file and print content inside it. If parent folder doesn't
	 * exists, create it.
	 * 
	 * @param content
	 *            text to write in file
	 * @param target
	 *            file to write content to
	 * @return true if the file has correctly been written
	 */
	def protected printFile(CharSequence content, File target) {
		try {
			if ( ! target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
			}
			val ps = new PrintStream(new FileOutputStream(target));
			ps.print(content);
			ps.close();
			return true;
		} catch (FileNotFoundException e) {
			OrccLogger::severe("Unable to write file " + target.path + " : " + e.cause)
			OrccLogger::severe(e.localizedMessage)
			e.printStackTrace();
			return false;
		}
	}
	
	def print(String targetFolder, Network network) {
		val fifosFile = new File(targetFolder + File::separator + "connections.csv")
		val actorsFile = new File(targetFolder + File::separator + "children.csv")
		
		printFile(getConnectionsStats(network.connections), fifosFile)
		printFile(getChildrenStats(network.children), actorsFile)
	}
	
	def private getChildrenStats(EList<Vertex> vertices) '''
		«childrenHeader»
		«FOR instance : vertices.filter(typeof(Instance))»
			«instance.stats»
		«ENDFOR»
		«FOR actor : vertices.filter(typeof(Actor))»
			«actor.stats»
		«ENDFOR»
	'''
	
	def private getChildrenHeader() 
		'''Name, Incoming, Outgoing, Inputs, Outputs, Actions, MoC'''
	
	def private getConnectionsStats(EList<Connection> connections) '''
		«connectionsHeader»
		«FOR conn : connections »
			«conn.stats»
		«ENDFOR»
	'''
	
	def protected getConnectionsHeader() 
		'''Source, SrcPort, Target, TgtPort, Size'''
	
	def protected getStats(Connection conn) 
		'''«conn.source.label», «conn.sourcePort.name», «conn.target.label», «conn.targetPort.name», «conn.size»'''
	
	def private getStats(Actor actor) 
		'''«actor.name», «actor.incoming.size», «actor.outgoing.size», «actor.inputs.size», «actor.outputs.size», «actor.actions.size», «actor.moC»'''
	
	def private getStats(Instance instance) {
		val actor = instance.actor
		'''«instance.name», «instance.incoming.size», «instance.outgoing.size», «actor.inputs.size», «actor.outputs.size», «actor.actions.size», «actor.moC»'''
	}
	
}