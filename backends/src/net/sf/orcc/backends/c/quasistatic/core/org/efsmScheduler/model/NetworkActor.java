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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Constants;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Switch;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.main.Scheduler_Simulator;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.PropertiesParser;

/**
 * NetworkActor class is used to represent an actor while parsing the XML
 * representation(file with extension .xnl ) of the Network.
 * 
 * 
 */
public class NetworkActor {
	String name;
	String longName;
    int actorIndex;
    String kind = null;

    String networkPath;

	EFSM machine;

	Set<Port> inputPorts;
	Set<Port> outputPorts;

	Set<Connection> inConn;
	Set<Connection> outConn;

	/**
	 * if the actor is a network, then this field should be true after all
	 * parsing is done.
	 */
	private boolean isCompound;
	private boolean isConfigActor;
	/**
	 * if the actor is a network, this field contains the reference to that
	 * network.
	 */
	private Network network;
	private Network parentNetwork;

	public NetworkActor() {
		super();
		this.name = null;
		this.longName = null;
		inputPorts = new HashSet<Port>();
		outputPorts = new HashSet<Port>();
		inConn = new HashSet<Connection>();
		outConn = new HashSet<Connection>();
		isCompound = false;
		network = null;
		isConfigActor = false;
		parentNetwork = null;
        networkPath = "Main Network";
	}

	public NetworkActor(String name, String longName, int index) {
		super();
		this.name = name;
		this.longName = longName;
		inputPorts = new HashSet<Port>();
		outputPorts = new HashSet<Port>();
		inConn = new HashSet<Connection>();
		outConn = new HashSet<Connection>();
		isCompound = false;
		network = null;
		isConfigActor = false;
		parentNetwork = null;
        actorIndex = index;
        networkPath = "Main Network";
	}

	public void parseCalml() throws IOException {
        Scheduler_Simulator sche_sim = Scheduler_Simulator.getInstance();
        System.out.println(sche_sim.getCALMLDirectory().getAbsolutePath() + File.separator + longName + ".calml");
		machine = new EFSM(name, networkPath, sche_sim.getCALMLDirectory().getAbsolutePath() + File.separator + longName + ".calml");
        machine.setActorIndex(actorIndex);
	}

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNetworkPath() {
        return networkPath;
    }

    public void setNetworkPath(String networkName) {
        this.networkPath = networkName;
    }

    /**
     * If the actors is a network, sets kind checking which is the kind of the
     * contained actors.
     */
    public void setKind(){
        if (network == null) return;
        
        for(NetworkActor actor: network.getActors()){

            if(actor.getKind() == null){
                System.err.println(actor.longName);
                actor.adjustKind();
            }

            if(actor.getKind().equals(Constants.ND_ACTOR)){
                this.kind = Constants.ND_ACTOR;
                return;
            }
        }
        this.kind = Constants.STATIC_ACTOR;
    }
    /**
     * Check what kind of actor is using the following rules:
     *      1. Static actor: static input and output.
     *      2. Borderline Actor: Non-deterministic input, static output
     *      3. Non-deterministic Actors:  ND input and output
     */
    public void adjustKind(){
        if(kind != null) return;
        
        if(network == null)
            setKind(Constants.getActorKind(existsNDInputs(), existsNDOutputs(),longName));
        else{
            for(NetworkActor actor: network.getActors()){
                actor.adjustKind();
                if(actor.getKind().equals(Constants.STATIC_ACTOR))
                    this.kind = Constants.STATIC_ACTOR;
            }
            if(kind == null)
                this.kind = Constants.ND_ACTOR;

         }

    }

    private boolean existsNDInputs(){
        for(Port port: inputPorts){
            if(port.getNumTokens() == Constants.getVariableTokenRate()){
                return true;
            }
        }
        return false;
    }

    private boolean existsNDOutputs(){
        for(Port port: outputPorts){
            if(port.getNumTokens() == Constants.getVariableTokenRate()){
                return true;
            }
        }
        return false;
    }

	public void unroll() {

		if (!this.isCompound() && machine != null) {
			machine.unroll(kind);
		}
        else if (kind.equals(Constants.ND_ACTOR)) {
            return;
        }
    }

    /**
     * <p>
     * Sets the token patterns for an EFSM, on its ports.<br/> The ports and
     * the corresponding number of tokens are stored in
     */
    public void setTokenPatterns() {
        if(machine == null) return;
        
        HashMap<String, Integer> patterns = PropertiesParser.getPortsMap(machine.getEFSMName());
        HashMap<String, Integer> consumptionPatterns = PropertiesParser.getConsumptionTokenPortsMap(machine.getEFSMName());
        for (Port port : machine.getInputPorts()) {
        	int consumptionTokens = 0;
            int numTokens = 0;
        	if( patterns.containsKey(port.getRef()) && consumptionPatterns.containsKey(port.getRef())){
                consumptionTokens = consumptionPatterns.get(port.getRef());
                numTokens = patterns.get(port.getRef()) * consumptionTokens;
        	}
            port.setNumTokens(numTokens);
            port.setConsumptionTokens(consumptionTokens);
        }
        for (Port port : machine.getOutputPorts()) {
        	int consumptionTokens = 0;
            int numTokens = 0;
        	if(patterns.containsKey(port.getRef()) && consumptionPatterns.containsKey(port.getRef())){
                consumptionTokens = consumptionPatterns.get(port.getRef());
                numTokens = patterns.get(port.getRef()) * consumptionTokens;
        	}
            port.setNumTokens(numTokens);
            port.setConsumptionTokens(consumptionTokens);
        }
        
        setInputPorts(machine.getInputPorts());
        setOutputPorts(machine.getOutputPorts());
    }

	public boolean isConfigActor() {
		return isConfigActor;
	}

	public void setIsConfigActor() {
		isConfigActor = true;
	}

	public String toString() {
        return "Parent Network: "+ networkPath + "\tActor Name: " + name + "-" + longName;
        /*if(machine != null)
            return "\tParent Network: "+ networkName + " Actor Name: " + name + "-" + longName + "\n\tEFSM: " + machine.toString() + "\n\tIP: "
			+ inputPorts.toString() + "\nOP: " + outputPorts.toString() ;
        else
            return "\tNET: "+ networkName + " Name: " + name + "-" + longName + "\n\tIP: "
			+ inputPorts.toString() + "\n\tOP: " + outputPorts.toString() ;*/
	}

	/**
	 * sets the network and isCompound fields of this actor.
	 * 
	 * @param network
	 */
	public void setNetwork(Network network) {
		if (network != null) {
			System.out.println("Setting network for " + getName());
			this.network = network;
			setIsCompound();
		}

	}

    public void setActorIndex(int index){
        actorIndex = index;
        if(machine != null)
            machine.setActorIndex(actorIndex);
    }

	public void setParentNetwork(Network parent) {
		parentNetwork = parent;
	}

	public Network getParentNetwork() {
		return parentNetwork;
	}

	public Network getNetwork() {
		return network;
	}

	public boolean addInConn(Connection newConn) {
		return inConn.add(newConn);
	}

	public boolean addOutConn(Connection newConn) {
		return outConn.add(newConn);
	}

	public Set<Connection> getInConn() {
		return inConn;
	}

	public Set<Connection> getOutConn() {
		return outConn;
	}

	public boolean addInputPort(Port p) {
		return inputPorts.add(p);
	}

	public boolean addOutputPort(Port p) {
		return outputPorts.add(p);
	}

    /**
     * Gets the Actor's subgraph.
     * Note: Use it only when switch no exists in the network
     *
     * @return Actor's subgraph
     */
	public AbstractSubgraph getSubgraph(){
		if (isCompound() || machine == null) {
			return null;
		}
		return machine.getSubgraph();
	}


    /**
     * Gets the Actor's subgraph.
     * Note: Use it only when switch exists in the network
     *  
     * @return Actor's subgraph
     */
	public AbstractSubgraph getSwitchSubgraph() {
		if (isCompound() ) {
			return null;
		}
        //Gets the initAction
		String initActionQID = getInitialAction();
        if(initActionQID == null)  {
            System.out.println("Not exists initial action.");
            return null;
        }
        
        //Gets the subgraph using initAction
        AbstractSubgraph a = machine.getSubgraphFromQID(initActionQID);

		return a;
    }


    /**
     * @return Initial action form Graph using switch value
     * TODO Made it general
     */
    private String getInitialAction(){

        String switchType = Switch.getInstance().getSwitchType();
        if(switchType == null){
            System.out.println("Switch type not found in switch.properties file");
            return null;
        }
        AbstractSubgraph graph = machine.getSubgraph();
        String initialAction = graph.getInitActionQID();

        return initialAction;
    }

	public String getSubgraphNames() {
		if (isCompound())
			return this.getName() + ": -none-";
		Vector<? extends AbstractSubgraph> A = machine.actorSubgraphs;
		String s = this.getName() + ":";
		for (int i = 0; i < A.size(); i++) {
			s += A.elementAt(i).getInitActionQID() + ", ";
		}
		return s;
	}

	public boolean isCompound() {
		return isCompound;
	}

	public void setIsCompound() {
		isCompound = true;
	}

	/**
	 * 
	 * @param p
	 * @return the set of network connections from the port p of this actor.
	 */
	public Set<Connection> getConnFromPort(Port p) {
		Set<Connection> conns = new HashSet<Connection>();

		for (Connection conn : getOutConn()) {
			if (conn.getFrom().getRef().equals(p.getRef())) {
				conns.add(conn);
			}
		}
		return conns;
	}

	/**
	 * 
	 * @param p
	 * @return the set of network connections to the port p of this actor.
	 */
	public Set<Connection> getConnToPort(Port p) {
		Set<Connection> conns = new HashSet<Connection>();

		for (Connection conn : getInConn()) {
			if (conn.getTo().getRef().equals(p.getRef())) {
				conns.add(conn);
			}
		}
		return conns;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @param longName
	 *            the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**
	 * @return the machine
	 */
	public EFSM getMachine() {
		return machine;
	}

	/**
	 * @param machine
	 *            the machine to set
	 */
	public void setMachine(EFSM machine) {
		this.machine = machine;
	}


    public Port getInputPort(String portRef){
        for (Port port :inputPorts) {
            if(port.getRef().equals(portRef))
                return port;
        }
        return null;
    }

    public Port getOutputPort(String portRef){
        for (Port port :outputPorts) {
            if(port.getRef().equals(portRef))
                return port;
        }
        return null;
    }

	/**
	 * @return the inputPorts
	 */
	public Set<Port> getInputPorts() {
		return inputPorts;
	}

	/**
	 * @param inputPorts
	 *            the inputPorts to set
	 */
	public void setInputPorts(Set<Port> inputPorts) {
		this.inputPorts = inputPorts;
	}

	/**
	 * @return the outputPorts
	 */
	public Set<Port> getOutputPorts() {
		return outputPorts;
	}

	/**
	 * @param outputPorts the outputPorts to set
	 */
	public void setOutputPorts(Set<Port> outputPorts) {
		this.outputPorts = outputPorts;
	}

    public int getActorIndex(){
        return (machine != null)? machine.getActorIndex():0;
    }
}
