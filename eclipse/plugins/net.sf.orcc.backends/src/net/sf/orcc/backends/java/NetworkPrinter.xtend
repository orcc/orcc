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
package net.sf.orcc.backends.java

import java.io.File
import java.util.Map
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil

import static net.sf.orcc.OrccLaunchConstants.*

/*
 * Compile Top_network Java source code 
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends JavaTemplate {
	
	Network network;
	
	new(Network network, Map<String, Object> options){
		super()
		this.network = network
	}
	
			
	def printNetwork(String targetFolder) {
		
		var content = getNetworkFileContent
		var file = new File(targetFolder + File::separator + network.simpleName + ".java")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
		
	}		
		
	def printEclipseProjectsFiles(String targetFolder) {
		
		var filesCached = 0
		
		var content = classpathFileContent
		var file = new File(targetFolder + File::separator + ".classpath")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
		} else {
			filesCached = filesCached + 1
		}
		
		content = projectFileContent
		file = new File(targetFolder + File::separator + ".project")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
		} else {
			filesCached = filesCached + 1
		}
		
		return filesCached
	}
	
	def getNetworkFileContent() '''
		// File generated from «network.fileName»

		import net.sf.orcc.runtime.*;
		import net.sf.orcc.runtime.actors.*;
		import net.sf.orcc.runtime.source.GenericSource;
		
		public class «network.simpleName» implements IScheduler {
			
			// Declare actors objects
			«FOR instance : network.children.filter(typeof(Instance)).filter([!broadcast])»
				private IActor «instance.name»;
			«ENDFOR»
			
			// Declare broadcast
			«FOR instance : network.children.filter(typeof(Instance)).filter([broadcast])»
				private Broadcast<«instance.actor.getInput("input").type.doSwitch»> «instance.name»;
			«ENDFOR»
			
			
			@Override
			@SuppressWarnings("unchecked")
			public void initialize() {
				
				// Instantiate actors
				«FOR instance : network.children.filter(typeof(Instance)).filter([!broadcast])»
					«instance.name» = new «instance.actor.name»(«printArguments(instance.actor.parameters, instance.arguments)»);
				«ENDFOR»
				
				// Instantiate broadcast
				«FOR instance : network.children.filter(typeof(Instance)).filter([broadcast])»
					«instance.name» = new Broadcast<«instance.actor.getInput("input").type.doSwitch»>(«instance.outgoing.size»);
				«ENDFOR»
				
				@SuppressWarnings("rawtypes")
				Fifo f;
				«FOR connection : network.connections»
					f = new Fifo<«connection.targetPort.type.doSwitch»>(«connection.size»);
					«(connection.source as Instance).name».setFifo("«connection.sourcePort.name»", f);
					«(connection.target as Instance).name».setFifo("«connection.targetPort.name»", f);
				«ENDFOR»
				
			}
		
			@Override
			public void schedule() {
				«FOR instance : network.children.filter(typeof(Instance))»
					«IF ! instance.actor?.initializes.empty »
					«instance.name».initialize();
					«ENDIF»
				«ENDFOR»
				
				int i;
				do {
					i = 0;
					«FOR instance : network.children.filter(typeof(Instance))»
					i += «instance.name».schedule();
					«ENDFOR»
				} while (i > 0);
				
				System.out.println("No more action to launch, exit the application.");
				System.exit(0);
			}
			
			/**
			 * Entry point of the application
			 * 
			 * @param args
			 * 			program arguments array
			 */
			public static void main(String[] args) {
				CLIParameters.getInstance().setArguments(args);
				GenericSource.setFileName(CLIParameters.getInstance().getSourceFile());
				IScheduler scheduler = new «network.simpleName»();
				scheduler.initialize();
				scheduler.schedule();
			}
		}
	'''
	
		
	/**
	 * TODO : replace this awful wart by a great way to detect an instance is  broadcast
	 * If an actors has 1 in port called "input" and a list of
	 * out ports called "output_x", it is a Broadcast actor
	 */
	def isBroadcast(Instance instance) {
		if (instance.actor.getInput("input") == null) {
			return false
		}
		for(port : instance.actor.outputs) {
			if ( ! port.name.startsWith("output_")) {
				return false
			}
		}
		return true
	}
	
	def getProjectFileContent() '''
		<?xml version="1.0" encoding="UTF-8"?>
		<projectDescription>
			<name>«network.simpleName»</name>
			<comment></comment>
			<projects>
			</projects>
			<buildSpec>
				<buildCommand>
					<name>org.eclipse.jdt.core.javabuilder</name>
					<arguments>
					</arguments>
				</buildCommand>
			</buildSpec>
			<natures>
				<nature>org.eclipse.jdt.core.javanature</nature>
			</natures>
		</projectDescription>
	'''
	
	def getClasspathFileContent() '''
		<?xml version="1.0" encoding="UTF-8"?>
		<classpath>
			<classpathentry kind="src" path="libs"/>
			<classpathentry kind="src" path="src"/>
			<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
			<classpathentry kind="output" path="bin"/>
		</classpath>
	'''
}