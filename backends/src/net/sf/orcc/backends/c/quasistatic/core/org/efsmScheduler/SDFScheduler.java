/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the EPFL and University of Oulu nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.main.Scheduler_Simulator;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.FlowVertex;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.SDFEdge;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.SDFGraph;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.SDFVertex;

/**
 * @author Veeranjaneyulu Sadhanala Jul 7, 2008
 * 
 * Synchronous data flow scheduler
 * 
 */
public class SDFScheduler {

	private static int MAX_NUMBER_OF_PROCESSORS = 1 ;
    //Synchronous Data Flow graph
	private static SDFGraph graph;
    //Vertices
	private static Set<FlowVertex> sourceVertices;
	private static Set<FlowVertex> sinkVertices;
	//Number of processors. This should be read form the graph
    private static int numProcessors;
    //procTime[i] = Current time in processor i.
	private static int[] procTime;
    //procMap = [Name of action, processor number]
	private static HashMap<String, Integer> procMap;
	//actionExecTime = [Name of action, Execution time of the action]
	private static HashMap<String, Integer> actionExecTime;
	
	private static ArrayList<String> sortedActors;
	private static HashMap<String, ArrayList<String>> sortedActionsMap;
	
	private static ArrayList<SDFVertex> firedVertices;
	
	static final int EXEC_TIME_UNIT = 1;
	private static ScheduleWriter writer;

	public static void scheduleSDF(SDFGraph graph) {
		SDFScheduler.graph = graph;
		//Find sources and sinks of the system
		findSourcesAndSinks();
        System.out.println("\nSystem Level graph sinks and sources:");
		System.out.println("\tSources: " + sourceVertices);
		System.out.println("\tSinks: " + sinkVertices);
		addEdgesFromSinksToSources();
		createMaps();
        System.out.println(procMapToString());
        writer = new ScheduleWriter(MAX_NUMBER_OF_PROCESSORS);
		makeSchedule();
	}

    public static Set<FlowVertex> getSourceVertices() {
		return sourceVertices;
	}

	public static void setSourceVertices(Set<FlowVertex> sourceVertices) {
		SDFScheduler.sourceVertices = sourceVertices;
	}

	public static ArrayList<String> getSortedActors() {
		return sortedActors;
	}

	public static void setSortedActors(ArrayList<String> sortedActors) {
		SDFScheduler.sortedActors = sortedActors;
	}

    public static void setNumberOfProcessors(int numProcessors){
        MAX_NUMBER_OF_PROCESSORS = numProcessors;
    }

    public static int getNumberOfProcessors(){
        return MAX_NUMBER_OF_PROCESSORS;
    }

	/**
     * Parse procMap to String
     * @return a string
     */
    public static String procMapToString(){
        String str = "Processors Map: [Action name=Process] \n";
        Iterator<String> iterator = procMap.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next().toString();
            Integer value = procMap.get(key);
            str = str + "\t" + key + " = " + value + "\n";
        }
        return str;
    }



    /**
     * Using graph, finds sources and sinks and stores it in sourceVertices and sinkVertices.
     */
	private static void findSourcesAndSinks() {
		//initializes sourceVertices and sinkVertices
        sourceVertices = new HashSet<FlowVertex>();
		sinkVertices = new HashSet<FlowVertex>();

		for (SDFVertex vertex : graph.vertexList()) {
			FlowVertex v = (FlowVertex) vertex;
			if (graph.inDegreeOf(vertex) == 0)
				sourceVertices.add(v);
			if (graph.outDegreeOf(vertex) == 0)
				sinkVertices.add(v);
		}
	
	}

    /**
     * Creates an edge between sinks and sources
     */
	private static void addEdgesFromSinksToSources() {
		for (FlowVertex sink : sinkVertices)
			for (FlowVertex src : sourceVertices) {
                //Create an edge
				SDFEdge edge = graph.addEdge(sink, src);
				//SDFEdge edge = graph.getEdge(sink, src);
                //This acts as a token
				edge.setDelay(1);
            }
	}

	/**
	 * Creates procMap and actionExecTime maps.
	 */
	private static void createMaps() {
        //Creates new hashmaps
		procMap = new HashMap<String, Integer>();
		actionExecTime = new HashMap<String, Integer>();
		//Initial number of processors
        numProcessors = 0;
        //loop to fill the Maps
		for (SDFVertex vertex : graph.vertexList()) {
			//In the SDF, the actions are in the vertices
            String actionName = vertex.getVertexName();
			if(actionName.contains("#"))
                actionName = actionName.split("#")[0];
            if (!procMap.containsKey(actionName)) {
                procMap.put(actionName, numProcessors);
				if(numProcessors < MAX_NUMBER_OF_PROCESSORS-1)
					numProcessors++;
				actionExecTime.put(actionName, EXEC_TIME_UNIT);
			}

		}
		procTime = new int[MAX_NUMBER_OF_PROCESSORS];
	}



	public static HashMap<String, ArrayList<String>> getSortedActionsMap() {
		return sortedActionsMap;
	}

	public static void setSortedActionsMap(
			HashMap<String, ArrayList<String>> sortedActionsMap) {
		SDFScheduler.sortedActionsMap = sortedActionsMap;
	}
	
	/**
	 * Performs the schedule
	 */
	private static void makeSchedule() {
		int numSinksFired = 0;
		sortedActionsMap = new HashMap<String, ArrayList<String>>();
		sortedActors = new ArrayList<String>();
		firedVertices = new ArrayList<SDFVertex>();
		while (firedVertices.size() < graph.vertexList().size()){//numSinksFired < numSinks) {
			loop : for (SDFVertex vertex : graph.vertexList()) {
				if(firedVertices.contains(vertex)) continue loop;
				
				int tokenSum = 0;
				int fireTime = 0, maxFireTime = 0;
				for (SDFEdge edge : graph.incomingEdgesOf(vertex)) {
					// slightly modified
					// if the token field has value > 0, there is a token. 0 is for no token
					if (edge.getDelay() > 0) {
						tokenSum++;
						fireTime = edge.getDelay();
						maxFireTime = Math.max(fireTime, maxFireTime);
					}
				}
				if (tokenSum == graph.inDegreeOf(vertex)) {
					boolean isSink = sinkVertices.contains(vertex);
					fireActor(vertex, maxFireTime, isSink);
					if (isSink) {
						numSinksFired++;
					}
					firedVertices.add(vertex);
				}
			}

		}
		writer.flush();
	}

	/**
	 * Fires an actor
	 * @param vertex
	 * @param fireTime
	 * @param isSink
	 * @return
	 */
	private static int fireActor(SDFVertex vertex, int fireTime,boolean isSink) {

		int proc = getProcNum(vertex);
		int firingTime = Math.max(fireTime, getProcTime(proc));
		int execTime = ((FlowVertex) vertex).getExecTime();

        //Update procTime
		procTime[proc] = firingTime + execTime;
        int procNum = getProcNum(vertex);
        System.out.print("Fired action " + vertex + " on processor " + procNum + ": ");
		System.out.println("Firing time: [ " + firingTime + " , " + procTime[proc] + "]");
		
		writer.printActor(proc, (FlowVertex)vertex, firingTime, procTime[proc]);
		if (!isSink) {
			// put tokens on outgoing edges
			ArrayList<SDFEdge> outEdges = graph.outgoingEdgesOf(vertex);
			for (SDFEdge edge : outEdges) {
				edge.setDelay(getProcTime(proc));
			}
			// remove tokens from incoming edges
			ArrayList<SDFEdge> inEdges = graph.incomingEdgesOf(vertex);
			for (SDFEdge edge : inEdges) {
				edge.setDelay(0);
			}
		}
		return firingTime;

	}

	/**
	 * 
	 * @param vertex
	 * @return processor number of vertex
	 */
	private static int getProcNum(SDFVertex vertex) {
		if(vertex == null) return -1;
        String actionName = vertex.getVertexName();
        if(actionName.contains("#"))
                actionName = actionName.split("#")[0];
        return procMap.get(actionName);
	}

	private static int getProcTime(int procNum) {
		return procTime[procNum];
	}

}
/**
 * This class is contains methods for printing schedules in xml format.
 * @author Veeranjaneyulu Sadhanala
 * Jul 19, 2008
 *
 */
class ScheduleWriter {
	int numProcessors;

    private IndentedTextBuffer[] buffers;

	public ScheduleWriter(int numProcessors) {
		this.numProcessors = numProcessors;
		buffers = new IndentedTextBuffer[numProcessors];
		String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		String switchName = Switch.getInstance().getSwitchName();
        String switchType = Switch.getInstance().getSwitchType();
        String switchValue = Integer.toString(Switch.getInstance().getToken(""));
		String switchInfo = "<switch>"+ 
							"<name>" + switchName + "</name>" +
							"<type>" + switchType + "</type>" +
							"<value>" + switchValue + "</value>" +
							"</switch>";
		
		for (int i = 0; i < numProcessors; i++) {

			buffers[i] = new IndentedTextBuffer();
			IndentedTextBuffer buf = buffers[i];
			
			buf.addLine(head);
			buf.indent();
			buf.addLine("<Schedule processor=\"" + i + "\">");
			buf.indent();
			buf.addLine(switchInfo);
		}

	}

	public void printActor(int procNum, FlowVertex v, int start, int end) {
		IndentedTextBuffer buffer = buffers[procNum];
		String vertexName = v.getVertexName();
        String s = "<Action> <name>" +  vertexName.split(": ")[1] + "</name> "
                + "<short_actor_name>"+ vertexName.split(": ")[0] + "</short_actor_name>"
                + "<actor_name>"+v.getMachineName()+ "</actor_name>"
				+ "<start>"+ start +"</start>"
				+ "<end>"+ end +"</end>"
				+ "</Action>";
		buffer.addLine(s);
        if(vertexName.contains("#"))
             vertexName = vertexName.split("#")[0];
        //load dataset for chart
        String keyName = vertexName;
        System.out.print("Adding action "+ vertexName + " Key: " + keyName + " : ");
       
        //Updates sorted actions
        HashMap<String, ArrayList<String>> sortedActionsMap = SDFScheduler.getSortedActionsMap();
        ArrayList<String> actionsSorted;
        if(sortedActionsMap.containsKey(v.getMachineName()))
        		actionsSorted = sortedActionsMap.get(v.getMachineName());
        else{
        		actionsSorted = new ArrayList<String>();
        		sortedActionsMap.put(v.getMachineName(), actionsSorted);
        }
        if(!actionsSorted.contains(vertexName))
        	actionsSorted.add(vertexName);
        
        //Updates sorted actors
        if(!SDFScheduler.getSortedActors().contains(v.getMachineName()))
        	SDFScheduler.getSortedActors().add(v.getMachineName());
        
	}

	public void flush() {
		IndentedTextBuffer buf = null;
		PrintStream out = null;
		Scheduler_Simulator sche_sim = Scheduler_Simulator.getInstance();
        File ouputDirectory = sche_sim.getOutputDirectory();
		String switchValue = Switch.getInstance().getSwitchType();
        try {
			for (int i = 0; i < numProcessors; i++) {
				buf = buffers[i];
				buf.unindent();
				buf.addLine("</Schedule>");				
				out = new PrintStream(new File(ouputDirectory.getAbsolutePath() + File.separator + "Schedule " + i + " - " + switchValue +".xml"));
				out.print(buffers[i].getString());
			}
		} catch (FileNotFoundException e) {
			System.err.println("A schedule file could not be created.");
			e.printStackTrace();
		}
    }

    
}
