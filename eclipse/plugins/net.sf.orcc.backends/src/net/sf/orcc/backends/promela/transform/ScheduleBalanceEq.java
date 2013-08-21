package net.sf.orcc.backends.promela.transform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;

public class ScheduleBalanceEq {

	private Set<Scheduler> schedulers;
	
	private Network network;
	
	private Set<NodeInfo> nodeInfoSet = new HashSet<NodeInfo>();
	
	private Map<Connection, ChannelInfo> conToChanMap = new HashMap<Connection, ScheduleBalanceEq.ChannelInfo>();
	
	private Map<Actor, NodeInfo> instToNodeMap = new HashMap<Actor, ScheduleBalanceEq.NodeInfo>();
	
	private class NodeInfo {
		Actor actor = null;
		Set<ChannelInfo> inChannels = new HashSet<ChannelInfo>();
		Set<ChannelInfo> outChannels = new HashSet<ChannelInfo>();
		Scheduler scheduler = null;
	}
	
	@SuppressWarnings("unused")
	private class ChannelInfo {
		NodeInfo srcNode = null;
		NodeInfo dstNode = null;
		Port sctPort = null;
		Port dstPort = null;
		Connection connection = null;
	}
	
	public ScheduleBalanceEq(Set<Scheduler> schedulers, Network network) {
		this.schedulers=schedulers;
		this.network=network;
		createTopology();
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
