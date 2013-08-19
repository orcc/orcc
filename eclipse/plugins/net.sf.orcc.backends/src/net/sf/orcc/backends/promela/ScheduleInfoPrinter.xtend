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
import net.sf.orcc.backends.promela.transform.Scheduler
import net.sf.orcc.backends.promela.transform.Schedule
import net.sf.orcc.backends.promela.transform.ScheduleBalanceEq
import net.sf.orcc.df.Instance

/**
 * Generated an initial schedule with only actor level scheduling completed 
 *  
 * @author Johan Ersfolk
 * 
 */
class ScheduleInfoPrinter extends PromelaTemplate {
	
	val Network network;
	val ScheduleBalanceEq balanceEq;
	
	new(Network network, ScheduleBalanceEq balanceEq) {
		this.network = network
		this.balanceEq = balanceEq
	}
	
	def print(String targetFolder) {
		
		val content = schedulerFileContent
		val file = new File(targetFolder + File::separator + "schedule_info_" + network.simpleName + ".xml")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def getSchedulerFileContent() 
	'''
		<!-- Generated from "«network.name»" -->
		
		<network>
			«FOR instance : balanceEq.instances»
				«instance.printInstance»
			«ENDFOR»
		</network>
		
	'''
	
	def printInstance(Instance instance) { 
	'''
		<actor name="«instance.name»">
			«balanceEq.getScheduler(instance).schedulesxml»
			<connections>
			«instance.connections»
			</connections>
		</actor>
	'''
	}
	
	def schedulesxml(Scheduler scheduler){
	'''
		«FOR sched : scheduler.schedules»
			<schedule initstate="«sched.initStateName»" action="«sched.enablingActionName»">
				«sched.rates»
			</schedule>
		«ENDFOR»
	'''
	}
	
	def connections(Instance instance) {
	'''
		«FOR port : instance.incomingPortMap.keySet»
			«IF balanceEq.getSource(instance.incomingPortMap.get(port)) != null»
				<input port="«port.name»" instance="«balanceEq.getSource(instance.incomingPortMap.get(port)).simpleName»"/>
			«ELSE»
				<input port="«port.name»" instance="NULL"/>
			«ENDIF»
		«ENDFOR»
		«FOR port : instance.outgoingPortMap.keySet»
			«FOR con : instance.outgoingPortMap.get(port)»
				«IF balanceEq.getDestination(con) != null»
					<output port="«port.name»" instance="«balanceEq.getDestination(con).simpleName»"/>
				«ELSE»
					<output port="«port.name»" instance="NULL"/>
				«ENDIF»
			«ENDFOR»
		«ENDFOR»
	'''
	}
	
	def rates(Schedule schedule) {
	'''
		<rates>
		«FOR port : schedule.portPeeks.keySet»
			<peek port="«port»" value="«schedule.portPeeks.get(port).get(0)»"/>
		«ENDFOR»
		«FOR port : schedule.portReads.keySet»
			<read port="«port»" value="«schedule.portReads.get(port).size»"/>
		«ENDFOR»
		«FOR port : schedule.portWrites.keySet»
			<write port="«port»" value="«schedule.portWrites.get(port).size»"/>
		«ENDFOR»
		</rates>
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
	
}