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

import java.util.Set
import net.sf.orcc.backends.promela.transform.Schedule
import net.sf.orcc.backends.promela.transform.Scheduler
import net.sf.orcc.df.Network

/**
 * Generated an initial schedule with only actor level scheduling completed 
 *  
 * @author Johan Ersfolk
 * 
 */
class SchedulePrinter extends PromelaTemplate {
	
	var Network network;
	val Set<Scheduler> actorSchedulers;
	
	new(Set<Scheduler> actorSchedulers) {
		this.actorSchedulers = actorSchedulers
	}
	
	def setNetwork(Network network) {
		this.network = network
	}

	def getSchedulerFileContent() 
	'''
		<!-- Generated from "«network.name»" -->
		
		<superactorlist>
		«FOR actorScheduler : actorSchedulers»
			«actorScheduler.superActor»
		«ENDFOR»
		</superactorlist>
		
	'''
	
	def superActor(Scheduler actorSched) { 
	'''
		<superactor name="cluster_«actorSched.actor.name»">
			<actor name="«actorSched.actor.name»"/>
			«actorSched.schedulerxml»
			«actorSched.schedulesxml»
		</superactor>
	'''
	}
	
	def schedulerxml(Scheduler scheduler) {
	'''
		<fsm initial="«scheduler.initStateName»">
		«FOR sched : scheduler.schedules»
			<transition action="«sched.initStateName»_«sched.enablingActionName»" dst="«sched.endStateName»" src="«sched.initStateName»"/>
		«ENDFOR»
		</fsm>
	'''
	}
	
	def schedulesxml(Scheduler scheduler){
	'''
		«FOR sched : scheduler.schedules»
			<superaction name="«sched.initStateName»_«sched.enablingActionName»">
				<guard></guard>
			«FOR action : sched.sequence»
				<iterand action="«action»" actor="«scheduler.actor.name»" repetitions="1"/>
			«ENDFOR»
			</superaction>
		«ENDFOR»
	'''
	}
	
	def scheduledatarates(Schedule schedule) {
	'''
		«schedule.enablingActionName»
		«FOR port : schedule.portPeeks.keySet»
				«port»=«schedule.portPeeks.get(port).get(0)»
		«ENDFOR»
		«FOR port : schedule.portReads.keySet»
			«port»=«schedule.portReads.get(port).size»
		«ENDFOR»
		«FOR port : schedule.portWrites.keySet»
			«port»=«schedule.portWrites.get(port).size»
		«ENDFOR»
	'''
	}
	
	def enablingActionName(Schedule schedule) {
	'''«IF schedule.enablingAction!=null»«schedule.enablingAction.name»«ELSE»«schedule.sequence.get(0).name»«ENDIF»'''
	}
	
	def initStateName(Schedule schedule) {
	'''«IF schedule.initState!=null»«schedule.initState.name»«ELSE»one_state«ENDIF»'''
	}

	def endStateName(Schedule schedule) {
	'''«IF schedule.endState!=null»«schedule.endState.name»«ELSE»one_state«ENDIF»'''
	}

	def initStateName(Scheduler scheduler) {
	'''«IF scheduler.initialState!=null»«scheduler.initialState.name»«ELSE»one_state«ENDIF»'''
	}
	
}