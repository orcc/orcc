package net.sf.orcc.backends.c.quasistatic.scheduler.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.NetworkGraph;
import net.sf.orcc.backends.c.quasistatic.scheduler.output.SchedulePreparer;
import net.sf.orcc.network.Network;

public class Scheduler {

	public static String workingDirectoryPath;
	private NetworkGraph networkGraph;
	private int noProcessors;

	public Scheduler(Network network) {
		this(Scheduler.workingDirectoryPath, network, 1);
	}

	public Scheduler(String workingDirectoryPath, Network network) {
		this(workingDirectoryPath, network, 1);
	}

	public Scheduler(String workingDirectoryPath, Network network,
			int noProcessors) {
		Scheduler.workingDirectoryPath = workingDirectoryPath;
		this.noProcessors = noProcessors;
		this.networkGraph = new NetworkGraph(network);
	}

	/**
	 * 
	 * @return
	 * @throws OrccException
	 * @throws QuasiStaticSchedulerException
	 */
	public Map<String, List<String>> performSchedule() throws OrccException {
		try {
			Map<String, List<String>> scheduleMap;
			if (SchedulePreparer.needsToPrepare()) {
				SchedulePreparer.prepare();
			}
			// search for scheduled actors
			networkGraph.init();
			// unrolls actors
			networkGraph.unrollActors();
			// creates system level graphs
			networkGraph.createSystemLevelGraph();
			// performs DSE schedule
			// TODO: change DSE scheduler's API in order it uses the Orcc's API
			// (
			// and not external input files)
			scheduleMap = new HashMap<String, List<String>>();// performDSESchedule();
			SchedulePreparer.removeInputData();
			// returns schedule hashmap
			return scheduleMap;
		} catch (QuasiStaticSchedulerException e) {
			throw new OrccException("exception in quasi-static back-end", e);
		}
	}

	// public HashMap<String, List<String>> performDSESchedule() throws
	// OrccException {
	// DSEScheduler dseScheduler = new DSEScheduler();
	// dseScheduler.createInputFiles();
	//
	// int noClusters = 5;
	// HashMap<String, Integer> clusterMap = new HashMap<String, Integer>();
	// List<ActorGraph> staticActors = networkGraph.getScheduledActors();
	// for(ActorGraph actor : staticActors){
	// int cluster = DSESchedulerUtils.getNoCluster(actor.getName());
	// clusterMap.put(actor.getName(), cluster);
	// }
	// DSEInputFilesCreator.createMappingFile(noProcessors, noClusters + 1,
	// clusterMap);
	// System.out.println("Creating DSE Schedule");
	// HashMap<String, List<String>> scheduleMap = dseScheduler.schedule();
	// System.out.println("Schedule created successfully");
	//
	// return transformScheduleMap(scheduleMap);
	//
	// }
	//
	// private HashMap<String, List<String>> transformScheduleMap(
	// HashMap<String, List<String>> map) {
	//
	// List<String> schedule = map.get(Constants.NEWVOP.toLowerCase());
	// int div = schedule.size() / 3;
	// int mod = schedule.size() % 3;
	// int pivot = div + mod;
	// ArrayList<ArrayList<String>> newSchedule = new
	// ArrayList<ArrayList<String>>(
	// 3);
	// newSchedule.add(0, new ArrayList<String>());
	// newSchedule.add(1, new ArrayList<String>());
	// newSchedule.add(2, new ArrayList<String>());
	//
	// for (int i = 0; i < schedule.size(); i++) {
	// int index = (i >= 0 && i < pivot) ? 0 : (i < pivot + div) ? 1 : 2;
	// newSchedule.get(index).add(schedule.get(i));
	// }
	// map.put(new String("btype_phase0"), newSchedule.get(0));
	// map.put(new String("btype_phase1"), newSchedule.get(1));
	// map.put(new String("btype_phase2"), newSchedule.get(2));
	// map.remove(Constants.NEWVOP.toLowerCase());
	//
	// return map;
	// }

	@Override
	public String toString() {
		return Integer.toString(noProcessors);
	}

}
