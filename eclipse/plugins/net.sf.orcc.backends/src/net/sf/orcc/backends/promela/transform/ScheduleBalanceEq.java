package net.sf.orcc.backends.promela.transform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;

public class ScheduleBalanceEq {

	private Set<Scheduler> schedulers;
	
	private Network network;
	
	private Set<NodeInfo> nodeInfoSet = new HashSet<NodeInfo>();
	
	private Map<Connection, ChannelInfo> conToChanMap = new HashMap<Connection, ScheduleBalanceEq.ChannelInfo>();
	
	private Map<Instance, NodeInfo> instToNodeMap = new HashMap<Instance, ScheduleBalanceEq.NodeInfo>();
	
	private class NodeInfo {
		Instance instance = null;
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
	
	public Scheduler getScheduler(Instance inst) {
		return instToNodeMap.get(inst).scheduler;
	}
	
	public Set<Instance> getInstances() {
		return instToNodeMap.keySet();
	}
	
	/**
	 * Return the instance that feed this channel or null if the instance var not generated
	 * @param Connection con
	 * @return Instance instance
	 */
	public Instance getSource(Connection con) {
		try {
			return conToChanMap.get(con).srcNode.instance;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Instance getDestination(Connection con) {
		try {
			return conToChanMap.get(con).dstNode.instance;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	private void createTopology() {
		for (Vertex v : network.getChildren()) {
			if (v instanceof Instance) {
				Instance instance=(Instance)v;
				NodeInfo newNode = new NodeInfo();
				newNode.instance=instance;
				instToNodeMap.put(instance, newNode);
				nodeInfoSet.add(newNode);
				for (Port p : instance.getOutgoingPortMap().keySet()) {
					for (Connection con : instance.getOutgoingPortMap().get(p)) {
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
				for (Port p : instance.getIncomingPortMap().keySet()) {
					ChannelInfo cInfo;
					if (conToChanMap.containsKey(instance.getIncomingPortMap().get(p))) {
						cInfo = conToChanMap.get(instance.getIncomingPortMap().get(p));
					} else {
						cInfo = new ChannelInfo();
						conToChanMap.put(instance.getIncomingPortMap().get(p), cInfo);
					}
					cInfo.dstPort=p;
					cInfo.dstNode=newNode;
					cInfo.connection=instance.getIncomingPortMap().get(p);
					newNode.inChannels.add(cInfo);
				}
			}
		}
		//also connect appropriate schedulers to the nodes
		for (Scheduler scheduler : schedulers) {
			instToNodeMap.get(scheduler.getInstance()).scheduler=scheduler;
		}

	}
	

}
