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

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model;


/**
 * This class represents abstract subgraph.
 * @author sadhanal
 * @since 27/06/08
 */
public abstract class AbstractSubgraph extends SDFGraph {
	static final long serialVersionUID = 10000019L;
	EFSM efsm;
	Transition initEdge;

	public AbstractSubgraph() {
		super();
	}

	public AbstractSubgraph(EFSM efsm, Transition initEdge) {
		super();
		this.efsm = efsm;
		this.initEdge = initEdge;

	}

	/**
	 * Visits the vertices of the SDF.
	 */
	public void visit() {
		/*System.out.println("Visiting SDF------------------------");
		TopologyVisitor topo = new TopologyVisitor();
		topo.visit(this);*/
		System.err.println("Warning!! AbstractSubgraph: Visit method not implemented yet!");
	}

	public void displayGraph() {
		/*SDFApplet applet = new SDFApplet(this);
		applet.init();
		applet.displayGraph();*/
	}

    public void displayGraphOnUserInterface() {
		/*SDFApplet applet = new SDFApplet(this);
		applet.init();
		applet.displayGraphOnUserInterface(getGraphName());*/
	}

	public String getInitActionQID() {
		if (initEdge != null) {
			return initEdge.getAction().getQID();
		} else
			return null;
	}
	
	public String toString() {
		return getInitActionQID();
	}
	public Transition getInitEdge(){
		return initEdge;
	}
	abstract public boolean hasOutputPort(String portRef);

	abstract public boolean hasInputPort(String portRef);

	abstract public FlowVertex getVertexWithOutPortInterface(PortInterface pi, int numTokens);

	abstract public FlowVertex getVertexWithInPortInterface(PortInterface pi, int numTokens);
}
