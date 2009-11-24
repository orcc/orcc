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

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Expr;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Stmt;

/**
 * 
 * The Action class represents the the actor element in the CALML file.<br/>
 * Action does not have the source state and destination state.<br/> The source
 * and destination can be found in the <code>Transition</code> of the Action.
 * 
 */
public class Action {

	String QID;
	Set<String> inputPorts;
	Set<String> outputPorts;

	private boolean artificial;
	private boolean needsTokensForUnrolling;
	Set<Expr> guards;
	Vector<Stmt> body;
	public Action() {
		this.QID = null;
		inputPorts = new HashSet<String>();
		outputPorts = new HashSet<String>();
		guards = new HashSet<Expr>();
		body = new Vector<Stmt>();
	}

	public Action(String QID) {
		this.QID = QID;
		inputPorts = new HashSet<String>();
		outputPorts = new HashSet<String>();
		guards = new HashSet<Expr>();
		body = new Vector<Stmt>();
	}

	public boolean hasInputPorts() {
		return (inputPorts != null) && inputPorts.size() > 0;
	}

	public boolean hasOutputPorts() {
		return (outputPorts != null) && outputPorts.size() > 0;
	}

	/**
	 * Adds a guard to guards
	 * @param expr guard
	 * @return a boolean
	 */
	public boolean addGuard(Expr expr){
		if(expr != null){
			return guards.add(expr);
		}
		else throw new NullPointerException("Guard Expr = null");
	}
	/**
	 * add a statement to the body of the action
	 * @param stmt
	 * @return true if the stmt is added normally to the body.
	 * @throws NullPointerException if stmt is null.
	 */
	public boolean addStmtToBody(Stmt stmt){
		if(stmt != null){
			return body.add(stmt);
		}
		else throw new NullPointerException("Body Stmt = null");
	}
	/**
	 * 
	 * @return action's identifier
	 */
	public String getQID() {
		return QID;
	}

	/**
	 * sets actor's identifier
	 * @param qid
	 */
	public void setQID(String qid) {
		QID = qid;
	}

	/**
	 * 
	 * @return input ports of the action
	 */
	public Set<String> getInputPorts() {
		return inputPorts;
	}

	/**
	 * sets input ports
	 * 
	 * @param inputPorts
	 */
	public void setInputPorts(Set<String> inputPorts) {
		this.inputPorts = inputPorts;
	}

	/**
	 * 
	 * @return output ports of the action
	 */
	public Set<String> getOutputPorts() {
		return outputPorts;
	}

	/**
	 * sets the output ports of the action
	 * @param outputPorts
	 */
	public void setOutputPorts(Set<String> outputPorts) {
		this.outputPorts = outputPorts;
	}

	
	public String toString() {
		return getQID();
	}

	public Set<Expr> getGuards() {
		return guards;
	}

	public Vector<Stmt> getBody() {
		return body;
	}
	public boolean isArtificial(){
		return artificial;
	}
	public void setArtificial(){
		artificial = true;
	}

	public boolean needsTokensForUnrolling() {
		return needsTokensForUnrolling;
	}

	public void setNeedsTokensForUnrolling() {
		this.needsTokensForUnrolling = true;
	}
	public void addInputPort(String port){
		inputPorts.add(port);
	}
	public void addOutputPort(String port){
		outputPorts.add(port);
	}

    /**
     * Checks all input ports of the action and determines if it can be fired
     *
     * @param efsm Extended finite state machine
     * @return  if action can be fired.
     */
    public boolean canBeFired(EFSM efsm){
        for(Port port: efsm.getInputPorts()){
            String portName = port.getRef();
            int remaningPortTokens = port.getRemTokens();
            int consumptionPortTokens = port.getConsumptionTokens();
            if(inputPorts.contains(portName) && (remaningPortTokens == 0 || remaningPortTokens < consumptionPortTokens)){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks all input ports and determines if the action was fired
     * @param efsm
     * @return true if the action was alredy fired
     *         false in other case
     */
    public boolean notFiredYet(EFSM efsm){
        for(Port port: efsm.getInputPorts()){
            String portName = port.getRef();
            int remaningPortTokens = port.getRemTokens();
            int numPortTokens = port.getRemTokens();
            if(inputPorts.contains(portName) && remaningPortTokens != numPortTokens){
                return false;
            }
        }
        return true;
    }

}
