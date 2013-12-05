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
		
		val content = scriptFileContent
		val file = new File(targetFolder + File::separator + "run_checker_" + network.simpleName + ".py")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def getScriptFileContent() {
	'''
		# Generated from "«network.name»"
		
		from pylibs.modelchecking import ModelChecker
		from pylibs.scheduler import SchedulerXML, FSM, Transition
		from pylibs.interaction import UserArgs
		from pylibs.schedconfig import Configuration, RunConfiguration, StateDescription, ChannelConfigXML
		
		uargs = UserArgs()
		uargs.parseargs()
		
		actors=«network.allActors.join("[",", ", "]",['''"«simpleName»"'''])»
		
		conf=Configuration('config_«network.simpleName»', 'config.txt')
		conf.loadconfiguration(actors)
		
		if uargs.removeactor is not None:
			conf.removeactor(uargs.removeactor)
			conf.saveconfiguration()
			exit()
		
		if uargs.setleader is not None:
			conf.setleader(uargs.setleader)

		ccx=ChannelConfigXML('schedule_info_«network.simpleName».xml')

		rc=RunConfiguration(conf)
		
		mc = ModelChecker()
		
		if uargs.configure:
			scheduler=SchedulerXML('schedule_«network.simpleName».xml')
			fsm=scheduler.getactorfsm(conf.leader)
			fsm.printfsm()
			fsm.savefsm('config_«network.simpleName»', 'scheduler.txt')
			rc.confinitsearch()
			mc.simulate('main_«network.simpleName».pml')
			endstate = mc.endstate
			sd=StateDescription()
			sd.fromstring(endstate)
			sd.savestate('config_«network.simpleName»', 's0.txt')

		if uargs.runcheckerid is not None:
			fsm=FSM()
			fsm.loadfsm('config_«network.simpleName»', 'scheduler.txt')
			fsm.printfsm()
			sched=fsm.gettransition(uargs.runcheckerid)
			if sched.nsrc=='?':
				print ('Error: The starting state for the requested schedule is still unknown')
				exit()
			print ("\nRunning schedule search for:\n"+sched.tostring())
			statefile=sched.nsrc+'.txt'
			sd=StateDescription()
			sd.loadstate('config_«network.simpleName»', statefile)
			inputs=ccx.findinputs(conf)
			if uargs.findspecificstate is not None:
				next_state_file=uargs.findspecificstate+'.txt'
				nsd=StateDescription()
				nsd.loadstate('config_«network.simpleName»', next_state_file)
				rc.configure(sd, inputs, sched.src, sched.action, sched.dst, nsd.getfsmstates())
			else:
				rc.configure(sd, inputs, sched.src, sched.action, sched.dst, {})
			conf.setschedule(uargs.runcheckerid)
		
		if uargs.runchecker:
			mc.generatemc('main_«network.simpleName».pml')
			mc.compilemc()
			mc.runmc()
			if mc.tracefound:
				print ("\n\nSchedule found.")
				fsm=FSM()
				fsm.loadfsm('config_«network.simpleName»', 'scheduler.txt')
				sched=fsm.gettransition(conf.schedule)
				mc.simulatetrail('main_«network.simpleName».pml')
				nsd=StateDescription()
				nsd.fromstring(mc.endstate)
				nsd.setfilter('schedule_info_«network.simpleName».xml')
				ns=nsd.isequalto('config_«network.simpleName»', fsm.getnstates(sched.dst))
				if ns is None:
					ns=fsm.getnewstatename()
					fsm.addnewnstate(sched.dst, ns)
				nsd.savestate('config_«network.simpleName»', ns+'.txt')
				sched.ndst=ns
				sched.sequence=mc.schedxml
				fsm.savefsm('config_«network.simpleName»', 'scheduler.txt')
				fsm.printfsm()
			else:
				print ("\n\nSchedule was not found!!")
		
		if uargs.printXML:
			fsm=FSM()
			fsm.loadfsm('config_«network.simpleName»', 'scheduler.txt')
			fsm.savefsm('config_«network.simpleName»', 'scheduler.txt')
			scheduler=SchedulerXML('schedule_«network.simpleName».xml')
			scheduler.savenewfsm(fsm, conf, 'schedule_«network.simpleName»_new.xml', 'config_«network.simpleName»')
				
		
		conf.printconfiguration()
		conf.saveconfiguration()
	'''
	}
}
