package net.sf.orcc.backends.c.quasistatic.scheduler.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.orcc.OrccException;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.Constants;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.ActorGraph;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.NetworkGraph;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.Switch;
import net.sf.orcc.backends.c.quasistatic.scheduler.output.DSEInputFilesCreator;
import net.sf.orcc.backends.c.quasistatic.scheduler.output.SchedulePreparer;
import net.sf.orcc.backends.c.quasistatic.scheduler.dse.DSEScheduler;
import net.sf.orcc.backends.c.quasistatic.scheduler.dse.DSESchedulerUtils;
import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.network.Network;

public class Scheduler {

	private int noProcessors;
	private NetworkGraph networkGraph;
	public static String workingDirectoryPath;
	
	public Scheduler(String workingDirectoryPath, Network network, int noProcessors){
		Scheduler.workingDirectoryPath = workingDirectoryPath;
		SchedulePreparer.prepare();
		this.noProcessors = noProcessors;
		this.networkGraph = new NetworkGraph(network);
	}
	
	public Scheduler(String workingDirectoryPath, Network network){
		this(workingDirectoryPath, network, 1);
	}
	
	/**
	 * 
	 * @return
	 * @throws OrccException
	 * @throws QuasiStaticSchedulerException 
	 */
	public HashMap<String, List<String>> performSchedule() throws OrccException, QuasiStaticSchedulerException{
		HashMap<String, List<String>> scheduleMap;
		//search for scheduled actors
		networkGraph.reset();
		
		for(String btype: Switch.getSwitchValues()){
			System.out.println("BTYPE: " + btype);
			Switch.btype = btype;
			//unrolls actors
			unrollStaticActors();
			//creates system level graphs
			networkGraph.createSystemLevelGraph();
		}
		//performs DSE schedule
		scheduleMap = performDSESchedule();
		//returns schedule hashmap
		return scheduleMap;
	}
	
	private void unrollStaticActors() throws OrccException, QuasiStaticSchedulerException{
		networkGraph.unrollStaticActors();
	}
	
	private HashMap<String, List<String>> performDSESchedule() throws OrccException {
		DSEScheduler dseScheduler = new DSEScheduler();
		dseScheduler.createInputFiles();

		int noClusters = 5;
		HashMap<String, Integer> clusterMap = new HashMap<String, Integer>();
		List<ActorGraph> staticActors = networkGraph.getScheduledActors();
		for(ActorGraph actor : staticActors){
			int cluster = DSESchedulerUtils.getNoCluster(actor.getName());
			clusterMap.put(actor.getName(), cluster);
		}
		DSEInputFilesCreator.createMappingFile(noProcessors, noClusters + 1,
				clusterMap);
		System.out.println("Creating DSE Schedule");
		HashMap<String, List<String>> scheduleMap = dseScheduler.schedule();
		System.out.println("Schedule created successfully");
		
		return transformScheduleMap(scheduleMap);
	
	}
	
	private HashMap<String, List<String>> transformScheduleMap(
			HashMap<String, List<String>> map) {

		List<String> schedule = map.get(Constants.NEWVOP.toLowerCase());
		int div = schedule.size() / 3;
		int mod = schedule.size() % 3;
		int pivot = div + mod;
		ArrayList<ArrayList<String>> newSchedule = new ArrayList<ArrayList<String>>(
				3);
		newSchedule.add(0, new ArrayList<String>());
		newSchedule.add(1, new ArrayList<String>());
		newSchedule.add(2, new ArrayList<String>());

		for (int i = 0; i < schedule.size(); i++) {
			int index = (i >= 0 && i < pivot) ? 0 : (i < pivot + div) ? 1 : 2;
			newSchedule.get(index).add(schedule.get(i));
		}
		map.put(new String("btype_phase0"), newSchedule.get(0));
		map.put(new String("btype_phase1"), newSchedule.get(1));
		map.put(new String("btype_phase2"), newSchedule.get(2));
		map.remove(Constants.NEWVOP.toLowerCase());

		return map;
	}
}
