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

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Switch;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.UnrollingException;



/**
 * The Port class implements ports of actors.
 * 
 */
public class Port {
	String networkPath;
    /**
	 * name of the actor to which this port is bound to
	 */
	String actorName;
	/**
	 * name of the port
	 */
	String ref;
	/**
	 * type of the port, Input or Output
	 */
	PortType type;
	/**
	 * number of tokens that can be consumed or produced
	 */
	int numTokens;
	/**
	 * tokens left or to be produced
	 */
	int remTokens;
    /**
     * number of tokens consumed when an action is fired
     */
    int consumptionTokens;

    int actorHashCode;

	public Port(String actorName, String ref, PortType type, int numTokens) {
		super();
        this.actorName = actorName;
		this.ref = ref;
		this.type = type;
		this.numTokens = numTokens;
		this.remTokens = numTokens;
        networkPath = "Main Network";
	}

	/**
	 * copy function, except for remTokens = port.numTokens
	 * 
	 * @param port
	 *            the source of copying
	 */
	public Port(Port port) {
		super();
		this.actorName = port.actorName;
		this.ref = port.ref;
		this.type = port.type;
		this.numTokens = port.numTokens;
		this.remTokens = port.numTokens;
        networkPath = "Main Network";
	}

	public String toString() {
		return "(Port: " + networkPath + "." +actorName + "." + ref + " " + type + " " + numTokens
				+ " "+remTokens +")";
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

    public int getActorHashCode() {
        return actorHashCode;
    }

    public void setActorHashCode(int actorHashCode) {
        this.actorHashCode = actorHashCode;
    }

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

    public int getConsumptionTokens() {
        return consumptionTokens;
    }

    public void setConsumptionTokens(int consumptionTokens) {
        this.consumptionTokens = consumptionTokens;
    }

	public PortType getType() {
		return type;
	}

	public void setType(PortType type) {
		this.type = type;
	}

	public int getNumTokens() {
		return numTokens;
	}

	public int getRemTokens() {
		return remTokens;
	}

	/**
	 * 
	 * @param numTokensRemoved
	 *            number of tokens consumed from the port
	 * @return number of remaining tokens on the port
	 * @throws UnrollingException
	 *             if the numTokensRemoved is less than remTokens
	 */
	public int removeTokens(int numTokensRemoved) throws UnrollingException {

		if (remTokens < numTokensRemoved)
			throw new UnrollingException("Port "+getRef()+" empty " + remTokens + " "
					+ numTokensRemoved);
		if (!getRef().equals("BTYPE")) {// TODO hard coded, see 27/05 in
										// todo.txt
			System.out.println("Removing " + numTokensRemoved + " tokens from port "+ getRef());
			remTokens -= numTokensRemoved;
		}

		return remTokens;
	}

    public boolean consumes(){
        if(remTokens == 0 )
        	return false;
    	if (remTokens < consumptionTokens)
            return false;
        if(!getRef().equals(Switch.getInstance().getSwitchName())){
            remTokens -= consumptionTokens;
        }
        return true;
		
    }

    public void produces(){
        if(!getRef().equals(Switch.getInstance().getSwitchName())){
            remTokens += consumptionTokens;
        }
    }

    public String getNetworkPath() {
        return networkPath;
    }

    public void setNetworkPath(String networkName) {
        this.networkPath = networkName;
    }

    public void setNumTokens(int numTokens) {
		this.numTokens = numTokens;
		this.remTokens = numTokens;
	}
	/**
	 * 
	 * @param p the port with which equality is checked
	 * @return true if actorName and ref of p are equal to those of this port.
	 */
	public boolean nameEquals(Port p){
		return this.actorName.equals(p.getActorName()) 
			&& this.ref.equals(p.getRef());
	}
	public boolean isLocal(){
		return this.actorName == null;
	}
}
