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

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.dse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Switch;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.main.Scheduler_Simulator;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.output.CSchedulerUtils;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.PropertiesParser;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.utilities.ArrayUtilities;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.utilities.FileUtilities;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.utilities.OutputResultUtilities;
import net.sf.orcc.backends.c.quasistatic.utils.CQuasiStaticConstants;

/**
 *
 * @author vimartin
 */
public class DSEScheduler {

	private HashMap<String, List<String>> scheduleMap;
	
    //CONSTANTS
    public final static int NORMAL = 1;
    public final static int ERROR = -1;
    private final static int STEP1 = 2;
    private final static int STEP2 = 1;
    private final static int STEP3 = 0;
    private final static int IPC_COST = 1;
    
    public static String INPUT_FOLDER = Scheduler_Simulator.getInstance().getWorkingDirectory() + 
    									File.separator + 
    									CQuasiStaticConstants.DSE_INPUT_PATH + 
    									File.separator;
    
    //------------
    private int initial_token_edge_count;
    private int[] initial_token_edge;
    private BufferedWriter[] log;
    private BufferedWriter permutationWriter, mapWriter;
    private BufferedReader permutationReader, mapReader;

    private int permutation_size;
    private SchedulingGraph graph;
    private int operationMode;
    private int[] exec_time, exec_order;
    private int[] temp;
    private String switchName;
    /**
     * Default constructor
     */
    public DSEScheduler() {
    	initScheduleMap();
    }

    /**
     * Init method
     */
    private void init() {
        operationMode = STEP1;
        graph = new SchedulingGraph();
    }


    private void initLogs(){

        log = new BufferedWriter[graph.getNum_processors()];
        for (int j = 0; j < graph.getNum_processors(); j++) {
            try {
                File logFile = new File(Scheduler_Simulator.getInstance().getOutputDirectory().getAbsolutePath() + File.separator+ "DSE_Schedude "+ j +" - "+ switchName + ".xml");
                int switchValue = Switch.getSwitchValue(switchName);
                logFile.createNewFile();
                log[j] = new BufferedWriter(new FileWriter(logFile));
                log[j].write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                log[j].write("<Schedule processor = \""+ j +"\">\n");
                log[j].write("<switch><name>BTYPE</name><type>"+ switchName + "</type><value>"+switchValue + "</value></switch>\n");
                log[j].write("<Operation_Mode>"+ operationMode +"</Operation_Mode>\n");
            } catch (IOException ex) {
                Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void closeLogs(){
        for (int j = 0; j < graph.getNum_processors(); j++) {
            try {
                log[j].write("</Schedule>\n");
                log[j].close();
            } catch (IOException ex) {
                Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Updates operationMode
     * @return process is ended
     */
    private int nextStep() {
        return --operationMode;
    }

    private boolean hasNext() {
        return operationMode >= 0;
    }

    /**
     * Initializes all the instances
     *
     * @param graph
     * @param v_inputs
     * @param v_outputs
     */
    private void scheduler_allocmem() {
        graph.setProcessor_time(new int[graph.getNum_processors()]);
        graph.setActors_for_pe(new int[graph.getNum_processors()]);
        graph.setActors(new Actor_t[graph.getActor_count()]);

        for (int i = 0; i < graph.getActor_count(); i++) {
            graph.setActorAt(i, new Actor_t());
        }

        graph.setEdges(new Edge_t[graph.getEdge_count()]);
        initial_token_edge = new int[graph.getEdge_count()];

        for (int i = 0; i < graph.getEdge_count(); i++) {
            graph.setEdgeAt(i, new Edge_t());
        }
    }

    /**
     * Includes a new actor into the schedule
     * @param s
     * @param index
     * @param processor
     * @param exec_time
     * @param data
     * @param cluster
     */
    private void add_actor(int index, int processor, int exec_time, int data, int cluster, String actorName, String actionName, String shortActorName) {
        Actor_t a = new Actor_t(cluster, processor, exec_time, data, 0, 0, actionName, actorName, shortActorName);
        graph.setActorAt(index, a);
        int newValue = graph.getActors_for_pe_At(processor) + 1;
        graph.setActors_for_pe_At(processor, newValue);
     }

    /**
     * Adds an edge into the schedule
     * @param s
     * @param index
     * @param source
     * @param sink
     * @param token
     */
    private void add_edge(int index, int source, int sink, int token) {
        if (token == 0) {
            initial_token_edge[initial_token_edge_count] = index;
            initial_token_edge_count++;
        }
        Edge_t e = graph.getEdgeAt(index);
        e.setInput(source);
        e.setOutput(sink);
        e.setToken(token);
        

        Actor_t sourceActor = graph.getActorAt(source);
        Actor_t sinkActor = graph.getActorAt(sink);

        sourceActor.addOutput(index);
        sinkActor.addInput(index);

        
    }

    /**
     *
     * @param graph
     * @param topo
     */
    private void build_graph1(File topo) {
    	File map = new File(INPUT_FOLDER  + "mapping.txt");
        File del = new File(INPUT_FOLDER  + "delays.txt");
    	BufferedReader mapReader = null;
        BufferedReader delReader = null;
        BufferedReader topoReader = null;
    	try {
            int[] v_inputs;
            int[] v_outputs;
            
            //---------------------
            mapReader = new BufferedReader(new FileReader(map));
            delReader = new BufferedReader(new FileReader(del));
            topoReader = new BufferedReader(new FileReader(topo));
            
            String[] readersResults;
            readersResults = FileUtilities.fscanf(mapReader, " ", 3);
            graph.setGroup_count(Integer.parseInt(readersResults[0]));
            graph.setCluster_count(Integer.parseInt(readersResults[1]));
            graph.setNum_processors(Integer.parseInt(readersResults[2]));
            readersResults = FileUtilities.fscanf(topoReader, " ", 2);
            graph.setActor_count(Integer.parseInt(readersResults[0]));
            graph.setEdge_count(Integer.parseInt(readersResults[1]));
            readersResults = FileUtilities.fscanf(delReader, " ", 1);
            graph.setDelay_count(Integer.parseInt(readersResults[0]));
            for (int i = 0; i < graph.getDelay_count(); i++) {
                char[] s = new char[16];
                int t;
                readersResults = FileUtilities.fscanf(delReader, " ", 2);
                s = readersResults[0].toCharArray();
                t = Integer.parseInt(readersResults[1]);
                for (int j = 0; j < 5; j++) {
                    graph.setDelay_id_At(i, j, s[j]);
                }
                graph.setDelay_val_At(i, t);
            }
            v_inputs = new int[graph.getActor_count()];
            v_outputs = new int[graph.getActor_count()];
            for (int i = 0; i < graph.getEdge_count(); i++) {
                int s;
                int t;
                readersResults = FileUtilities.fscanf(topoReader, " ", 2);
                s = Integer.parseInt(readersResults[0]);
                t = Integer.parseInt(readersResults[1]);
                v_outputs[s]++;
                v_inputs[t]++;
            }
            graph.setSource_count(0);
            graph.setSink_count(0);
            for (int i = 0; i < graph.getActor_count(); i++) {
                if (v_inputs[i] == 0) {
                    graph.setSource_count(graph.getSource_count() + 1);
                }
                if (v_outputs[i] == 0) {
                    graph.setSink_count(graph.getSink_count() + 1);
                }
            }
            graph.setSource_actors(new int[graph.getSource_count()]);
            graph.setSink_actors(new int[graph.getSink_count()]);
            int sink_ind = 0;
            int src_ind = 0;
            for (int i = 0; i < graph.getActor_count(); i++) {
                if (v_inputs[i] == 0) {
                    graph.setSource_actors_At(src_ind++, i);
                }
                if (v_outputs[i] == 0) {
                    graph.setSink_actor_At(sink_ind++, i);
                }
            }
            graph.setEdge_count(graph.getEdge_count() + graph.getSource_count() * graph.getSink_count());
            scheduler_allocmem( );
            for (int i = 0; i < graph.getGroup_count(); i++) {
                int cluster;
                char[] s = new char[24];
                readersResults = FileUtilities.fscanf(mapReader, " ", 2);
                s = readersResults[0].toCharArray();
                cluster = Integer.parseInt(readersResults[1]);
                for (int j = 0; j < s.length; j++) {
                    graph.setGroup_id_At( i, j, s[j] );
                }
                graph.setGroup_cluster_At(i, cluster);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
        	try {
				mapReader.close();
				delReader.close();
	            topoReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            
        }

    }

    /**
     *
     * @param graph
     * @param mapping
     * @param vert
     * @param topo
     */
    private void build_graph2(int[] mapping, File vert, File topo) {

        BufferedReader vertReader = null;
        BufferedReader topoReader = null;
        try {
            vertReader = new BufferedReader(new FileReader(vert));
            topoReader = new BufferedReader(new FileReader(topo));
            String[] readersResults = FileUtilities.fscanf(topoReader, " ", 2);
            initial_token_edge_count = 0;
            for (int i = 0; i < graph.getActor_count(); i++) {
                graph.getActorAt(i).resetInputAndOutput();
            }
            for (int i = 0; i < graph.getNum_processors(); i++) {
                graph.setActors_for_pe_At(i, 0);
            }
            for (int i = 0; i < graph.getCluster_count(); i++) {
                graph.setCluster_pe_At(i, mapping[i]);
            }
            for (int i = 0; i < graph.getActor_count(); i++) {
                int index;
                int pe;
                int delay;
                int cluster = 0;
                char[] group = new char[20];
                char[] action = new char[20];
                readersResults = FileUtilities.fscanf(vertReader, " ", 3);
                index = Integer.parseInt(readersResults[0]);
                group = readersResults[1].toCharArray();
                action = readersResults[2].toCharArray();
                
                pe = -1;
                delay = 1;
                for (int j = 0; j < graph.getGroup_count(); j++) {
                    
                    int similarity = 0;
                    for(int k = 0; k < group.length; k++)
                        if(group[k] == graph.getGroup_id_At(j, k))
                            similarity++;
                    if(similarity == group.length)
                    {
                        cluster = graph.getGroup_cluster_At(j);
                        pe = graph.getCluster_pe_At(cluster);
                        break;
                    }
                }
                
                for (int j = 0; j < graph.getDelay_count(); j++) {

                    int id_match = 0;
                    for (int k = 0; k < 5; k++) {
                        if (action[k] == graph.getDelay_id_At(j, k)) {
                            id_match++;
                        }
                    }
                    if (id_match == 5) {
                        delay = graph.getDelay_val_At(j);
                        break;
                    }
                }
                String actorName = PropertiesParser.getActorLongName(new String(group).split("@")[0]);
                if (pe != -1)
                    add_actor(index, pe, delay, 0, cluster, actorName, new String(action), new String(group));
                else
                    System.err.println(actorName + ": hash not recognized.\n");

            }
            int k;
            for (k = 0; k < graph.getEdge_count() - graph.getSource_count() * graph.getSink_count(); k++) {
                int s, t;
                readersResults = FileUtilities.fscanf(topoReader, " ", 2);
                s = Integer.parseInt(readersResults[0]);
                t = Integer.parseInt(readersResults[1]);
                add_edge(k, s, t, -1); // the passed value should be -1 for no token and 0 for initial token
            } // however, in the file "1" stands for a token and "0" for no token

            // connect all sinks to all sources and place a token with time zero
            for (int i = 0; i < graph.getSink_count(); i++) {
                for (int j = 0; j < graph.getSource_count(); j++) {
                    add_edge(k++, graph.getSink_actor_At(i), graph.getSource_actor_At(j), 0);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                vertReader.close();
                topoReader.close();
            } catch (IOException ex) {
                Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

// 
    /**
     * FIRE_ACTOR: executed when an actor has a token in all inputs
     * parameters: s - the graph structure, i - the index of the vertex that fires,
     * firing_time - when the firing happens, is_sink - 1: vertex is sink, 0: is not
     * @param s
     * @param i
     * @param firing_time
     * @param is_sink
     * @return
     */
    private int fire_actor(int i, int firing_time, int is_sink) {
        int proc;		// a variable that contains the processor index on which this actor executes
        int execution_time;	// the delay of this actor (see header file)
        int en;			// edge number variable

        Actor_t actor = graph.getActorAt(i);

        proc = actor.getOwner();	// fetch processor number

        if (graph.getProcessor_time_At(proc) > firing_time) // get the actual firing time for this actor
        {
            firing_time = graph.getProcessor_time_At(proc);
        }

        execution_time = actor.getDelay();		// fetch actor delay

        int newProcessorTime = firing_time + execution_time;
        graph.setProcessor_time_At(proc, newProcessorTime);		// update processor time for this processor
        //String keyName = actor.getActorName()+": "+actor.getActionName().split("#")[0];
        try {

            // update processor time for this processor
            BufferedWriter out = log[proc];
            out.write("<Action><name>"+actor.getActionName()+"</name>" +
                      "<short_actor_name>"+actor.getShortActorName()+ "</short_actor_name>" +
                      "<actor_name>"+actor.getActorName()+ "</actor_name>" +
                      "<start>"+firing_time+ "</start>" +
                      "<end>"+ newProcessorTime + "</end>" +
                      "</Action>\n");

            //Adds action into the map
            if(operationMode == STEP3){
            	String actionName = actor.getActionName().replace('.','_').split("#")[0];
	            String visualActorName = Scheduler_Simulator.getInstance().getNetwork().getVisualActorName(actor.getShortActorName().split("@")[0]);
	            String visualActionName = (actionName.matches("act[0-9]")?"untagged0" + (Integer.parseInt(actionName.substring(3))+1):actionName);
	            Integer index = Integer.parseInt(actor.getShortActorName().split("@")[1]);
	            String context = visualActorName + (index == 0? "":"_0"+index) + "_" + visualActionName;
	            List<String> array = scheduleMap.get(switchName.toLowerCase());
	            array.add(context);
	        }
            
        } catch (IOException ex) {
            Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (is_sink == 0) // do for all non-sink actors
        {
            for (int j = 0; j < graph.getActorAt(i).getNum_outputs(); j++) // produce a token to each output
            {
                en = graph.getActorAt(i).getOutputAt(j);	// get output edge index
                graph.getEdgeAt(en).setToken(graph.getProcessor_time_At(proc));	// create token by witing the present time to the field
            }
        }

        for (int j = 0; j < graph.getActorAt(i).getNum_inputs(); j++) // consume tokens from inputs
        {
            en = graph.getActorAt(i).getInputAt(j);
            graph.getEdgeAt(en).setToken(-1);			// delete token by witing -1 to the time
        }
        
        return firing_time;
    }

    

    /**
     * MAKE_SCHEDULE: this is how the schedule is made
     * parameters: smem - the graph structure, exec_order_actor -- can be ignored, exec_order_time -- can be ignored
     * num_processors - number of processors in system, sink_vertices - an array containing sink vertex indexes
     * sink_count - number of sink vertexes
     * @param graph
     * @param exec_order_actor
     * @param exec_order_time
     * @param num_processors
     * @param permutation
     * @return
     */
    private int make_schedule(int[] permutation) {
        int tokensum;									// a variable for counting tokens on input edges
        int sinks_fired;								// a variable to count the sink actors that have been fired
        //int proc;										// a variable to store the index of the processor of the current vertex
        int en;											// to store and edge index
        int ft;											// to store a token time
        int ft_max;										// to store the largest token time of all input edges
        int out_index;									// (remove this variable)
        int[] enabled;

        for (int j = 0; j < graph.getEdge_count(); j++) {
            graph.getEdgeAt(j).setToken(-1);
        }
        for (int j = 0; j < initial_token_edge_count; j++) {
            graph.getEdgeAt(initial_token_edge[j]).setToken(0);
        }

        initLogs();

        enabled = new int[graph.getActor_count()];

        out_index = 0;		// remove

        sinks_fired = 0;

        for(int i = 0; i < graph.getNum_processors(); i++)		// initialize the clock of each processor to zero
            graph.setProcessor_time_At(i, 0);
        for(int i = 0; i < graph.getActor_count(); i++)
            enabled[i] = 0 ;

        while (sinks_fired < graph.getSink_count()) // loop until all sinks have fired
        {
            for (int i = 0; i < graph.getActor_count(); i++) // loop through all vertexes
            {
                if (enabled[i] == 0) {
                    //proc = graph.getActorAt(i).getOwner();

                    tokensum = 0;
                    ft_max = 0;

                    // count tokens and notice the one that has the greatest time
                    for (int j = 0; j < graph.getActorAt(i).getNum_inputs(); j++) {
                        en = graph.getActorAt(i).getInputAt(j);

                        if (graph.getEdgeAt(en).getToken() >= 0) // if the token field has value >= 0, there is a token. -1 is for no token
                        {
                            tokensum++;

                            ft = graph.getEdgeAt(en).getToken();

                            if (ft_max < ft) {
                                ft_max = ft;
                            }
                        }
                    }

                    if (tokensum == graph.getActorAt(i).getNum_inputs()) // if there is a token for each input edge -> fire actor
                    {
                        enabled[i] = ft_max + 1;
                    }
                }	// for i
            }
            for (int k = 0; k < graph.getCluster_count(); k++) {
                int p = permutation[k];

                for (int i = 0; i < graph.getActor_count(); i++) {
                    if (enabled[i] > 0 && graph.getActorAt(i).getCluster() == p) {
                        int is_sink = 0;		// temporary variable to store information if this vertex is a sink or not

                        for (int j = 0; j < graph.getSink_count(); j++) {
                            if (i == graph.getSink_actor_At(j)) {
                                is_sink = 1;
                                sinks_fired++;
                            }
                        }

                        exec_time[out_index] = fire_actor(i, enabled[i] - 1, is_sink);	// exec_order_time and
                        exec_order[out_index++] = i;											// exec_order_actor can be removed
                        
                        enabled[i] = -1;
                    }
                }
            }
        }
        closeLogs();
        return exec_time[out_index - 1] + graph.getActorAt(exec_order[out_index - 1]).getDelay();
    }

    private void print(int[] v, int size) {
        if (v != null) {
            try {
                for (int i = 0; i < size; i++) {
                    permutationWriter.write((v[i] - 1) + "\t");
                }
                permutationWriter.write("\n");
                permutation_size++;
            } catch (IOException ex) {
                Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    private int level = -1;
    /**
     * Alexander Bogomolyn's unordered permutation algorithm 
     * @param temp
     * @param N
     * @param k
     */
    private void visit(int N, int k) {
        level = level + 1;
        temp[k] = level;

        if (level == N) {
            print(temp, N);
        } else {
            for (int i = 0; i < N; i++) {
                if (temp[i] == 0) {
                    visit(N, i);
                }
            }
        }

        level = level - 1;
        temp[k] = 0;
    }

    /**
     * Creates permutations
     * @param cluster_count
     */
    void create_permutations(int cluster_count) {
        temp = new int[cluster_count];

        File permu = new File("permutations.txt");
        try {
            permu.createNewFile();
            permutationWriter = new BufferedWriter(new FileWriter(permu));
            visit(cluster_count, 0);
        } catch (IOException ex) {
            Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                permutationWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    private int[] to_base_n(int val, int n, int len) {
        int i;
        int[] coeffs;
        int[] result = new int[len];
        coeffs = new int[len + 1];

        coeffs[0] = 1;
        for (i = 1; i < len + 1; i++) {
            coeffs[i] = coeffs[i - 1] * n;
        }

        for (i = len; i >= 0; i--) {
            if (val / coeffs[i] > 0) {
                result[i] = val / coeffs[i];
                val -= (val / coeffs[i]) * coeffs[i];
            }
        }

        return result;
    }

    /**
     *
     * @param len
     * @param dep
     * @return
     */
    private int create_pe_patterns(int len, int dep) {
        try {
            int[] result;
            int max = dep;
            File mappings = new File("mappings.txt");
            mappings.createNewFile();

            result = new int[len];
            for (int i = 0; i < len - 1; i++) {
                max *= dep;
            }
            mapWriter = new BufferedWriter(new FileWriter(mappings));
            for (int i = 0; i < max; i++) {
                result = to_base_n(i, dep, len);
                for (int j = len - 1; j >= 0; j--) 
                    mapWriter.write(result[j] + "\t");
                    
                mapWriter.write("\n");
            }

            return max;
        } catch (IOException ex) {
            Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } finally {
            try {
                mapWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int count_ipc_edges() {
        int count = 0;

        for (int i = 0; i < graph.getEdge_count(); i++) {
            if (graph.getActorAt(graph.getEdgeAt(i).getInput()).getOwner() != graph.getActorAt(graph.getEdgeAt(i).getOutput()).getOwner()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString(){
        String str = "*********************\n" +
                     "* Operation Mode: "+operationMode + " *\n" +
                     "*********************\n";
        return str;
    }

    private void initScheduleMap(){
    	ArrayList<String> switchValues = Switch.getSwitchValues();
    	scheduleMap = new HashMap<String, List<String>>();
    	for(String btypeValue : switchValues){
    		scheduleMap.put(btypeValue.toLowerCase(), new ArrayList<String>());
    	}
    }
    
    /**
     * Performs the scheduler's simulation
     * @return
     */
    private int execute() {

        try {
            System.out.println(toString());
            ArrayList<String> switchValues = Switch.getSwitchValues();
            ArrayList<ArrayList<Integer>> results = new ArrayList<ArrayList<Integer>>();
            File permFile, mapFile;
            int[] permutations;
            int[] mapping;
            String[] permTmp, mapTmp;
            int span;
            int graph_count = switchValues.size();
            for (int grapIndex = 0; grapIndex < graph_count; grapIndex++) {

                switchName = switchValues.get(grapIndex);
                CSchedulerUtils.init();

                String actorsFileName = INPUT_FOLDER  + File.separator + "actors_" + switchName + ".txt";
                String edgesFileName = INPUT_FOLDER  + File.separator + "edges_" + switchName + ".txt";
                File actorsFile = new File(actorsFileName);
                File edgesFile = new File(edgesFileName);
                System.out.println("Actor file: " + actorsFileName + ", Edge file: " + edgesFileName);

                build_graph1(edgesFile);
                exec_order = new int[graph.getActor_count()];
                exec_time = new int[graph.getActor_count()];
                mapping = new int[graph.getCluster_count()];
                permutations = new int[graph.getCluster_count()];
                ArrayList<Integer> resultTmp = new ArrayList<Integer>();
                switch (operationMode) {
                    case STEP3:
                        permFile = new File(INPUT_FOLDER + "manual_clustering.txt");
                        permutationReader = new BufferedReader(new FileReader(permFile));
                        permTmp = FileUtilities.fscanf(permutationReader, " ", graph.getCluster_count());
                        for (int i = 0; i < graph.getCluster_count(); i++) {
                            permutations[i] = Integer.parseInt(permTmp[i]);
                        }
                        permutationReader.close();
                        //-----------
                        mapFile = new File(INPUT_FOLDER + "manual_mapping.txt");
                        mapReader = new BufferedReader(new FileReader(mapFile));
                        mapTmp = FileUtilities.fscanf(mapReader, " ", graph.getCluster_count());
                        for (int i = 0; i < graph.getCluster_count(); i++) {
                            mapping[i] = Integer.parseInt(mapTmp[i]);
                        }
                        mapReader.close();
                        //-----------
                        build_graph2(mapping, actorsFile, edgesFile);
                        span = make_schedule(permutations);
                        resultTmp.add(span);
                        //MainFrame.getInstance().addReport("DSE Scheduler's Gantt Chart - " + switchName,
                        //                    ChartFactory.createGanttChart(datasetForChart));

                        CSchedulerUtils.printOutput(switchName);
                        break;
                    case STEP2:
                        permutation_size = 0;
                        create_permutations(graph.getCluster_count());
                        permFile = new File("permutations.txt");
                        permutationReader = new BufferedReader(new FileReader(permFile));
                        mapFile = new File(INPUT_FOLDER + "manual_mapping.txt");
                        mapReader = new BufferedReader(new FileReader(mapFile));
                        //reads mapping
                        mapTmp = FileUtilities.fscanf(mapReader, " ", graph.getCluster_count());
                        for (int j = 0; j < graph.getCluster_count(); j++) {
                                mapping[j] = Integer.parseInt(mapTmp[j]);
                        }
                        for (int i = 0; i < permutation_size; i++) {
                            permTmp = FileUtilities.fscanf(permutationReader, "\t", graph.getCluster_count());
                            for (int j = 0; j < graph.getCluster_count(); j++) {
                                permutations[j] = Integer.parseInt(permTmp[j]);
                            }
                            //-------------------
                            build_graph2(mapping, actorsFile, edgesFile);
                            span = make_schedule(permutations);
                            resultTmp.add(span);
                        }
                        permutationReader.close();
                        mapReader.close();
                        break;
                    case STEP1:
                        int pattern_count = create_pe_patterns(graph.getCluster_count(), graph.getNum_processors());
                        permFile = new File(INPUT_FOLDER  + "manual_clustering.txt");
                        permutationReader = new BufferedReader(new FileReader(permFile));
                        permTmp = FileUtilities.fscanf(permutationReader, " ", graph.getCluster_count());
                        for (int i = 0; i < graph.getCluster_count(); i++) {
                            permutations[i] = Integer.parseInt(permTmp[i]);
                        }
                        permutationReader.close();
                        //-----------

                        mapFile = new File("mappings.txt");
                        mapReader = new BufferedReader(new FileReader(mapFile));
                        for (int i = 0; i < pattern_count; i++) {
                            span = 0 ;
                            mapTmp = FileUtilities.fscanf(mapReader, "\t", graph.getCluster_count());
                            for (int j = 0; j < graph.getCluster_count(); j++) 
                                mapping[j] = Integer.parseInt(mapTmp[j]);
                            
                            build_graph2(mapping, actorsFile, edgesFile);
                            span = make_schedule(permutations);
                            span += count_ipc_edges() * IPC_COST;
                            resultTmp.add(span);
                        }
                        mapReader.close();
                        break;
                    default:
                        System.out.println("ERROR: Wrong step!");
                }
                results.add(resultTmp);
            }
            System.out.println("Done");

            switch (operationMode) {
                case STEP3:
                    //int[] val = new int[graph_count];
                    int sum = 0;
                    System.out.print("Makespan: ");
                    for (int j = 0; j < graph_count; j++) {
                        System.out.print(results.get(j) + "\t");
                        sum += results.get(j).get(0);
                    }
                    System.out.println("  Sum: " + sum);
                    
                    break;
                default:
                    int len_results = results.size() > 0 ? results.get(0).size() : 0;
                    int[][] resultArray = new int[graph_count + 1][len_results];
                    int min_val = Integer.MAX_VALUE;
                    int min_i = -1;
                    for (int j = 0; j < graph_count; j++) {
                        ArrayList<Integer> resultsTmp = results.get(j);
                        for (int i = 0; i < len_results; i++) {
                            int value = resultsTmp.get(i);
                            resultArray[j][i] = value;
                            resultArray[graph_count][i] += value;
                        }
                    }
                    for (int i = 0; i < len_results; i++) {
                        for (int j = 0; j < graph_count+1; j++) {
                            System.out.print(resultArray[j][i] + (j == graph_count ? "\n" : "\t"));
                        }
                    }

                    for (int i = 0; i < len_results; i++) {
                        if (resultArray[graph_count][i] < min_val) {
                            min_val = resultArray[graph_count][i];
                            min_i = i;
                        }
                    }
                    File file = new File((operationMode == STEP1 ? "mappings.txt" : "permutations.txt"));
                    BufferedReader fileReader = new BufferedReader(new FileReader(file));
                    char[] min_ind = new char[graph.getCluster_count()];
                    for (int i = 0; i < min_i + 1; i++) {
                        String[] resultsString = FileUtilities.fscanf(fileReader, "\t", graph.getCluster_count());
                        for (int j = 0; j < graph.getCluster_count(); j++) {
                            min_ind[j] = (char)(Integer.parseInt(resultsString[j]) + 48);
                        }
                    }

                    fileReader.close();

                    System.out.println("Compared " + len_results + " alternatives.");
                    System.out.println("Minimum: " + min_val + " with parameters " + ArrayUtilities.toString(min_ind));

                    File outputFile = new File(INPUT_FOLDER  + (operationMode == STEP1? "manual_mapping.txt" : "manual_clustering.txt"));
                    FileUtilities.fprintf(outputFile, ArrayUtilities.toString(min_ind));
            }

            return NORMAL;
        } catch (IOException ex) {
            Logger.getLogger(DSEScheduler.class.getName()).log(Level.SEVERE, null, ex);
            return ERROR;
        }
        finally{
            
        }
    }

    /**
     *
     */
    public HashMap<String, List<String>> schedule() {
        init();
        while(hasNext()){
            execute();
            nextStep();
        }
        return scheduleMap;
    }

    public void createInputFiles(){
    	OutputResultUtilities.createFile(DSEScheduler.INPUT_FOLDER, "delays.txt", "0\n");
        OutputResultUtilities.createFile(DSEScheduler.INPUT_FOLDER, "manual_mapping.txt", "0 0 0 0 0 0 \n");
        OutputResultUtilities.createFile(DSEScheduler.INPUT_FOLDER, "manual_clustering.txt", "0 1 2 3 4 5 \n");
    }
    
    public static void main(String[] args) {
        //new DSEScheduler().schedule();
    	System.out.println("act1".matches("act[0-9]"));
    }

}
