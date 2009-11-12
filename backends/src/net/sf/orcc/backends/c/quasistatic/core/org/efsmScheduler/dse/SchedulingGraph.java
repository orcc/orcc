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

/**
 *
 * @author Victor Martin
 */
public class SchedulingGraph {
    private Actor_t []actors;
	private Edge_t []edges;

	private int[] processor_time;				// a clock for each processor
	private int[] source_actors, sink_actors;
	private int edge_count, actor_count;
	private int num_processors;				// how many processors the system has
	private int[][] group_id;
	private int[] cluster_pe;
	private int[] group_cluster;
	private int group_count;
	private int source_count;
	private int sink_count;
	private int cluster_count;
	private int delay_count;				// number of explicitly mentioned action delays
	private char[][] delay_id;
	private int[] delay_val;
	private int[] actors_for_pe;

    public SchedulingGraph() {
    }

    public SchedulingGraph(Actor_t[] actor, Edge_t[] edge, int[] processor_time, int[] source_actors, int[] sink_actors, int edge_count, int actor_count, int num_processors, int group_count, int source_count, int sink_count, int cluster_count, int delay_count, int[] actors_for_pe) {
        this.actors = actor;
        this.edges = edge;
        this.processor_time = processor_time;
        this.source_actors = source_actors;
        this.sink_actors = sink_actors;
        this.edge_count = edge_count;
        this.actor_count = actor_count;
        this.num_processors = num_processors;
        this.group_count = group_count;
        this.source_count = source_count;
        this.sink_count = sink_count;
        this.cluster_count = cluster_count;
        this.delay_count = delay_count;
        this.actors_for_pe = actors_for_pe;
    }

    public void setActors(Actor_t[] actor) {
        this.actors = actor;
    }

    public int getActor_count() {
        return actor_count;
    }

    public void setActor_count(int actor_count) {
        this.actor_count = actor_count;
        
    }

    public int getActors_for_pe_At(int index){
        return actors_for_pe[index];
    }

    public void setActors_for_pe_At(int index, int value) {
        this.actors_for_pe[index] = value;
    }

    public int getCluster_count() {
        return cluster_count;
    }

    public void setCluster_count(int cluster_count) {
        cluster_pe = new int[cluster_count];
        this.cluster_count = cluster_count;
    }

    public int getCluster_pe_At(int index) {
        return cluster_pe[index];
    }

    public void setCluster_pe_At(int index, int value) {
        this.cluster_pe[index] = value;
    }

    public int getDelay_count() {
        return delay_count;
    }

    public void setDelay_count(int delay_count) {
        delay_id = new char[delay_count][5];
        delay_val = new int[delay_count];
        this.delay_count = delay_count;
    }

    public char getDelay_id_At(int row, int column) {
        return delay_id[row][column];
    }

    public void setDelay_id_At(int row, int column, char delay_id) {
        this.delay_id[row][column] = delay_id;
    }

    public int getDelay_val_At(int index) {
        return delay_val[index];
    }

    public void setDelay_val_At(int index , int delay_val) {
        this.delay_val[index] = delay_val;
    }

    public int getEdge_count() {
        return edge_count;
    }

    public void setEdge_count(int edge_count) {
        this.edge_count = edge_count;
    }

    public int getGroup_cluster_At(int index) {
        return group_cluster[index];
    }

    public void setGroup_cluster_At(int index, int group_cluster) {
        this.group_cluster[index] = group_cluster;
    }

    public int getGroup_count() {
        
        return group_count;
    }

    public void setGroup_count(int group_count) {
        group_id = new int[group_count][16];
        group_cluster = new int[group_count];
        this.group_count = group_count;
    }

    public int getGroup_id_At(int i, int j) {
        return group_id[i][j];
    }

    public void setGroup_id_At(int i, int j, int group_id) {
        this.group_id[i][j] = group_id;
    }

    public int getNum_processors() {
        return num_processors;
    }

    public void setNum_processors(int num_processors) {
        this.num_processors = num_processors;
    }

    public int getProcessor_time_At(int index ) {
        return processor_time[index];
    }

    public void setProcessor_time_At(int index, int processor_time) {
        this.processor_time[index] = processor_time;
    }

    public int getSink_actor_At(int index) {
        return sink_actors[index];
    }

    public void setSink_actor_At(int index, int sink_actors) {
        this.sink_actors[index] = sink_actors;
    }

    public int getSink_count() {
        return sink_count;
    }

    public void setSink_count(int sink_count) {
        this.sink_count = sink_count;
    }

    public int getSource_actor_At(int index) {
        return source_actors[index];
    }

    public void setSource_actors_At(int index, int source_actors) {
        this.source_actors[index] = source_actors;
    }

    public int getSource_count() {
        return source_count;
    }

    public void setSource_count(int source_count) {
        this.source_count = source_count;
    }

    public Actor_t getActorAt(int index){
        return (index < actors.length)? actors[index]: null;
    }

    public Edge_t getEdgeAt(int index){
        return (index < edges.length)? edges[index]: null;
    }

    public void setActorAt(int index, Actor_t actor){
        if(index >= actors.length) return;
        actors[index] = actor;
    }

    public void setEdgeAt(int index, Edge_t edge){
        if(index >= edges.length) return;
        edges[index] = edge;
    }

    void setActors_for_pe(int[] actors_for_pe) {
        this.actors_for_pe = actors_for_pe;
    }

    void setEdges(Edge_t[] edges) {
        this.edges = edges;
    }

    void setProcessor_time(int[] processors_time) {
        this.processor_time = processors_time;
    }

    void setSink_actors(int[] sink_actors) {
        this.sink_actors = sink_actors;
    }

    void setSource_actors(int[] source_actors) {
        this.source_actors = source_actors;
    }

}
