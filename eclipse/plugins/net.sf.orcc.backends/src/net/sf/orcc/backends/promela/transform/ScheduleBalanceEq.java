/*
 * Copyright (c) 2013, Abo Akademi University
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


package net.sf.orcc.backends.promela.transform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;

/**
 * This class defines what different Actor schedules consume/produce and 
 * implements the basic functionality of a balance equation
 * 
 * @author Johan Ersfolk
 */
public class ScheduleBalanceEq {

	private Set<Scheduler> schedulers;
	
	private Network network;
	
	private Set<NodeInfo> nodeInfoSet = new HashSet<NodeInfo>();
	
	private Map<Connection, ChannelInfo> conToChanMap = new HashMap<Connection, ScheduleBalanceEq.ChannelInfo>();
	
	private Map<Actor, NodeInfo> instToNodeMap = new HashMap<Actor, ScheduleBalanceEq.NodeInfo>();
	
	public class NodeInfo {
		Actor actor = null;
		Set<ChannelInfo> inChannels = new HashSet<ChannelInfo>();
		Set<ChannelInfo> outChannels = new HashSet<ChannelInfo>();
		Scheduler scheduler = null;
		//Map<Schedule, Map<NodeInfo, Map<Schedule, Set<Integer>>>> balance = new HashMap<Schedule, Map<NodeInfo, Map<Schedule, Set<Integer>>>>();
	}

	public class ChannelInfo {
		NodeInfo srcNode = null;
		NodeInfo dstNode = null;
		Port sctPort = null;
		Port dstPort = null;
		Connection connection = null;
		Map<Schedule, Integer> nrReads=new HashMap<Schedule, Integer>();
		Map<Schedule, Integer> nrWrites=new HashMap<Schedule, Integer>();
		int smallestFifoSize=1;
	}

	public Map<Schedule, Integer> getReads(Connection con) {
		return conToChanMap.get(con).nrReads;
	}

	public Map<Schedule, Integer> getWrites(Connection con) {
		return conToChanMap.get(con).nrWrites;
	}
	
	public ScheduleBalanceEq(Set<Scheduler> schedulers, Network network) {
		this.schedulers=schedulers;
		this.network=network;
		createTopology();
		createChannelRates();
		calculateSmallestFifoSize();
		//calculateNodeBalances();
		calculatePortSizes();
	}
	
	public Scheduler getScheduler(Actor a) {
		return instToNodeMap.get(a).scheduler;
	}
	
	public Set<Actor> getActors() {
		return instToNodeMap.keySet();
	}
	
	/**
	 * Return the instance that feed this channel or null if the instance var not generated
	 * @param Connection con
	 * @return Instance instance
	 */
	public Actor getSource(Connection con) {
		try {
			return conToChanMap.get(con).srcNode.actor;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Actor getDestination(Connection con) {
		try {
			return conToChanMap.get(con).dstNode.actor;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Calculates the token rates by the different schedules
	 */
	private void createChannelRates() {
		for(ChannelInfo cInfo : conToChanMap.values()) {
			if (cInfo.srcNode!=null) {
				for (Schedule sched : cInfo.srcNode.scheduler.getSchedules()) {
					try {
						int nr = sched.getPortWrites().get(cInfo.sctPort.getName()).size();
						cInfo.nrWrites.put(sched, new Integer(nr));
					}catch (NullPointerException e) {
						cInfo.nrWrites.put(sched, new Integer(0));
					}
				}
			}
			if (cInfo.dstNode!=null) {
				for (Schedule sched : cInfo.dstNode.scheduler.getSchedules()) {
					try {
						int nr = sched.getPortReads().get(cInfo.dstPort.getName()).size();
						cInfo.nrReads.put(sched, new Integer(nr));
					}catch (NullPointerException e) {
						cInfo.nrReads.put(sched, new Integer(0));
					}
				}
			}
		}
	}
	
	private void calculateSmallestFifoSize() {
		for(ChannelInfo cInfo : conToChanMap.values()) {
			Set<Integer> reads = new HashSet<Integer>();
			Set<Integer> writes = new HashSet<Integer>();
			if (cInfo.srcNode!=null) {
				Actor actor = cInfo.srcNode.actor;
				for (Action action : actor.getActions()) {
					writes.add(action.getOutputPattern().getNumTokens(cInfo.sctPort));
				}
			}
			if (cInfo.dstNode!=null) {
				Actor actor = cInfo.dstNode.actor;
				for (Action action : actor.getActions()) {
					reads.add(action.getInputPattern().getNumTokens(cInfo.dstPort));
				}
			}
			reads.remove(0);	//skip these
			writes.remove(0);
			reads.add(1);		//in case only connected to one actor
			writes.add(1);
			for (int r : reads) {
				for (int w : writes) {
					int lcm=lcm(r,w);
					if (cInfo.smallestFifoSize<lcm) {
						cInfo.smallestFifoSize=lcm;
					}
				}
			}
			cInfo.connection.setSize(cInfo.smallestFifoSize);
		}
	}
	
	private int lcm(int i, int j) {
		int mi = i;
		int mj = j;
		while (mi != mj) {
			while (mi < mj) { mi += i; }
			while (mi > mj) { mj += j; }
		}  
		return mi;
	}
	
	/*private void calculateNodeBalances() {
		//step one, neighbors according to queues
		for (ChannelInfo ci : conToChanMap.values()) {
			for (Schedule s1 : ci.nrReads.keySet()) {
				for (Schedule s2 : ci.nrWrites.keySet()) {
					
				}
			}
		}
	}*/
	
	private void calculatePortSizes() {
		for (Actor actor : network.getAllActors()) {
			for (Action action : actor.getActions()) {
				for (Port port : action.getInputPattern().getPorts()) {
					int nr = action.getInputPattern().getNumTokens(port);
					int curr = port.getNumTokensConsumed();
					nr = Math.max(nr, curr);
					port.setNumTokensConsumed(nr);
				}
				for (Port port : action.getOutputPattern().getPorts()) {
					int nr = action.getOutputPattern().getNumTokens(port);
					int curr = port.getNumTokensProduced();
					nr = Math.max(nr, curr);
					port.setNumTokensProduced(nr);
				}
			}
		}
	}
	
	private void createTopology() {
		for (Actor actor : network.getAllActors()) {
			NodeInfo newNode = new NodeInfo();
			newNode.actor=actor;
			instToNodeMap.put(actor, newNode);
			nodeInfoSet.add(newNode);
			for (Port p : actor.getOutgoingPortMap().keySet()) {
				for (Connection con : actor.getOutgoingPortMap().get(p)) {
					ChannelInfo cInfo;
					if (conToChanMap.containsKey(con)) {
						cInfo = conToChanMap.get(con);
					} else {
						cInfo = new ChannelInfo();
						conToChanMap.put(con, cInfo);
					}
					cInfo.sctPort=p;
					cInfo.srcNode=newNode;
					cInfo.connection=con;
					newNode.outChannels.add(cInfo);
				}
			}
			for (Port p : actor.getIncomingPortMap().keySet()) {
				ChannelInfo cInfo;
				if (conToChanMap.containsKey(actor.getIncomingPortMap().get(p))) {
					cInfo = conToChanMap.get(actor.getIncomingPortMap().get(p));
				} else {
					cInfo = new ChannelInfo();
					conToChanMap.put(actor.getIncomingPortMap().get(p), cInfo);
				}
				cInfo.dstPort=p;
				cInfo.dstNode=newNode;
				cInfo.connection=actor.getIncomingPortMap().get(p);
				newNode.inChannels.add(cInfo);
			}
			
		}
		//also connect appropriate schedulers to the nodes
		for (Scheduler scheduler : schedulers) {
			instToNodeMap.get(scheduler.getActor()).scheduler=scheduler;
		}
	}
}
