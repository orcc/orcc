/*
* Copyright(c)2008, Jani Boutellier, Christophe Lucarz, Veeranjaneyulu Sadhanala 
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
* THIS SOFTWARE IS PROVIDED BY  Jani Boutellier, Christophe Lucarz, 
* Veeranjaneyulu Sadhanala ``AS IS'' AND ANY 
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL  Jani Boutellier, Christophe Lucarz, 
* Veeranjaneyulu Sadhanala BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model;

import java.util.Set;

/**
 * The FlowVertex class represents the vertices of a data flow graph.
 * It contains port interfaces.
 * @author sadhanal
 *
 */
public class FlowVertex extends SDFVertex {
	private static final long serialVersionUID = 10000013L;
	private Set<PortInterface> inputPorts;
	private Set<PortInterface> outputPorts;
	private int execTime=2;
	private int mark;//mark = 1 for source, 2 for sink,0 for nothing.
					//can a vertex be a source and a sink at the same time?
	private String machineName;
    private String id;
	public FlowVertex(Set<PortInterface> inputPorts,
			Set<PortInterface> outputPorts) {
		super();
		this.inputPorts = inputPorts;
		this.outputPorts = outputPorts;
        this.id = Integer.toString((int)Math.random() * 8000);
	}
	public FlowVertex() {
		super();
        this.id = Integer.toString((int)(Math.random() * 8000));
	}
	
	public Set<PortInterface> getInputPorts() {
		return inputPorts;
	}
	public void setInputPorts(Set<PortInterface> inputPorts) {
		this.inputPorts = inputPorts;
	}
	public Set<PortInterface> getOutputPorts() {
		return outputPorts;
	}
	public void setOutputPorts(Set<PortInterface> outputPorts) {
		this.outputPorts = outputPorts;
	}
	/*TODO:separate versions of this method are needed for input/output ports
	 */
	public void setTokenNum(String portRef,int tokenNum){
		PortInterface pi = getPortInterface(portRef);
		pi.setTokenNum(tokenNum);
	}
	/**
	 * gets the port interface of this vertex with the given portRef. 
	 * @param portRef the name of the port
	 * @return a port interface of this vertex with the given portRef if one
	 * or more such port interfaces exist, null otherwise. If there are more
	 * than one port interfaces with the given portRef, then any one of them is
	 * returned.
	 */
	private PortInterface getPortInterface(String portRef){
		for(PortInterface pi : inputPorts){
			if(pi.getPortRef().equals(portRef))
				return pi;
		}
		for(PortInterface pi : outputPorts){
			if(pi.getPortRef().equals(portRef))
				return pi;
		}
		return null;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	//TODO: where should the execTime be set? use a map
	public int getExecTime(){
		return execTime;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getId(){
        return id;
    }
	
    public String getActorId(){
        return getName().split(": ")[0];
    }
    
    public String getActionName(){
        return getName().split(": ")[1];
    }

}
