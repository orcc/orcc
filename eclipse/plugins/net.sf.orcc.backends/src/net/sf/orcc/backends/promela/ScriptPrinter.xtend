/*
 * Copyright (c) 2011, Abo Akademi University
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
 *   * Neither the name of the Abo Akademi University nor the names of its
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
 
package net.sf.orcc.backends.promela

import java.io.File
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil
import net.sf.orcc.df.Instance

/**
 * Generated an initial schedule with only actor level scheduling completed 
 *  
 * @author Johan Ersfolk
 * 
 */
class ScriptPrinter extends PromelaTemplate {
	
	val Network network;
	
	new(Network network) {
		this.network = network
	}
	
	def print(String targetFolder) {
		
		val content = networkFileContent
		val file = new File(targetFolder + File::separator + "run_checker_" + network.simpleName + ".py")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def getNetworkFileContent() '''
		# Generated from "«network.name»"
		
		from pylibs.modelchecking import ModelChecker
		from pylibs.xmlformat import SchedulerXML, FSM, Transition
		from pylibs.interaction import UserArgs
		from pylibs.schedconfig import Configuration, RunConfiguration
		
		uargs = UserArgs()
		uargs.parseargs()
		
		actors=«network.children.filter(typeof(Instance)).join("[",", ", "]",['''"«simpleName»"'''])»
		
		conf=Configuration('config_«network.simpleName»', 'config.txt')
		conf.loadconfiguration(actors)
		
		if uargs.removeactor is not None:
			conf.removeactor(uargs.removeactor)
			conf.saveconfiguration()
			exit()
		
		if uargs.setleader is not None:
			conf.setleader(uargs.setleader)

		conf.printconfiguration()
		conf.saveconfiguration()

		rc=RunConfiguration(conf)
		rc.configure()
		
		scheduler=SchedulerXML('schedule_«network.simpleName».xml')
		fsm=scheduler.getactorfsm(conf.leader)
		fsm.printfsm()
		
		mc = ModelChecker()
		
		if uargs.configure:
			rc.confinitsearch()
			mc.simulate('main_«network.simpleName».pml')
			endstate = mc.endstate
			if uargs.outputfile == '':
				print (endstate, "\n")
			else:
				f = open(uargs.outputfile, 'w')
				f.write(endstate)
				f.close()

		if uargs.runchecker:
			mc.generatemc('main_«network.simpleName».pml')
			mc.compilemc()
			mc.runmc()
			#if mc.tracefound
				#mc.simulatetrail('main_«network.simpleName».pml')
	'''

	
}