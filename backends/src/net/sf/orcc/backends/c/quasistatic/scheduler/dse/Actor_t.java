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

package net.sf.orcc.backends.c.quasistatic.scheduler.dse;

import java.util.ArrayList;

/**
 * Represents an actor
 *
 * @author vimartin
 */
public class Actor_t {
    private Integer cluster;
	private Integer owner, delay, data;			// owner: the index of the processor that executes this actor
	private ArrayList<Integer> output, input;
    private String actionName;
    private String actorName;
    private String shortActorName;
    
    /**
     * Default constructor
     */
    public Actor_t() {
        this.output = new ArrayList<Integer>();
        this.input = new ArrayList<Integer>();
    }

    /**
     * Constructor
     * 
     * @param cluster 
     * @param owner 
     * @param delay
     * @param data
     * @param num_outputs
     * @param num_inputs
     * @param actionName
     * @param actorName
     * @param shortActorName
     */
    public Actor_t(int cluster, int owner, int delay, int data, int num_outputs, int num_inputs, String actionName, String actorName, String shortActorName) {
        this.cluster = cluster;
        this.owner = owner;
        this.delay = delay;
        this.data = data;
        this.output = new ArrayList<Integer>();
        this.input = new ArrayList<Integer>();
        this.shortActorName = shortActorName;
        this.actionName = actionName;
        this.actorName = actorName;
    }

    /**
     * 
     * @return
     */
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Integer getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public Integer getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Integer getNum_inputs() {
        return input.size();
    }

    public Integer getNum_outputs() {
        return output.size();
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public ArrayList<Integer> getInput() {
        return input;
    }

    public void setInput(ArrayList<Integer> input) {
        this.input = input;
    }

    public ArrayList<Integer> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<Integer> output) {
        this.output = output;
    }

    public boolean addOutput(int out){
        return output.add(out);
    }

    public boolean addInput(int in){
        return input.add(in);
    }

    public Integer getInputAt(int index){
        return input.get(index);
    }

    public Integer getOutputAt(int index){
        return output.get(index);
    }

    public String getShortActorName() {
        return shortActorName;
    }

    public void setShortActorName(String shortActorName) {
        this.shortActorName = shortActorName;
    }

    public void resetInputAndOutput(){
        this.output = new ArrayList<Integer>();
        this.input = new ArrayList<Integer>();
    }
}
