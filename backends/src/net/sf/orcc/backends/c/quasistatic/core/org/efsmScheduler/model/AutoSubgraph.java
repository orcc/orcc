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

/**
 * This class represents the actor subgraphs used by the automatic unroller.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.PropertiesParser;


/**
 * 
 * @author vimartin
 *
 */
public class AutoSubgraph extends AbstractSubgraph {

    private static final long serialVersionUID = 10000018L;
    FlowVertex curVertex;
    private HashMap<String, Vector<FlowVertex>> iportVec;
    private HashMap<String, Vector<FlowVertex>> oportVec;

    public AutoSubgraph() {
        super();
        curVertex = null;
        iportVec = new HashMap<String, Vector<FlowVertex>>();
        oportVec = new HashMap<String, Vector<FlowVertex>>();
    }

    public AutoSubgraph(EFSM efsm, Transition initEdge) {
        super(efsm, initEdge);
        this.efsm = efsm;
        this.initEdge = initEdge;
        iportVec = new HashMap<String, Vector<FlowVertex>>();
        oportVec = new HashMap<String, Vector<FlowVertex>>();
    }

    private String getVertexName(String actorName, String efsmName, String qid, int actorIndex) {
        ArrayList<SDFVertex> vertexSet = this.vertexList();
        int count = 0;
        for (SDFVertex vertex : vertexSet) {
            if (vertex.getName().contains(qid)) {
                count++;
            }
        }
        actorName = PropertiesParser.getActorShortName(actorName, efsmName)+ "@" + actorIndex;
        String vertexName = actorName + ": " + qid + "#" + count ;
        return vertexName;
    }

    /**
     * creates an SDF vertex and adds to the subgraph.
     * @param action the action which should be added to the graph.
     * @param dontConnToPrev if this false, then the newly created SDF vertex is connected to the
     * last vertex of the graph. Last vertex is the one added to the graph just before the new vertex.
     * If the newly created vertex is the first one to be added to the graph, then dontConnToPrev is ignored.
     */
    public void addAction(String actorName, String efsmName, Action action, int actorIndex) {
        if (action == null) {
            return;
        }
        FlowVertex v = new FlowVertex();
        String vertexName = getVertexName(actorName, efsmName, action.getQID(),actorIndex);
        v.setVertexName(vertexName);
        v.setName(vertexName);
        this.addVertex(v);
        FlowVertex prev = curVertex;
        curVertex = v;
        if (prev != null) {
            String prevName = prev.getVertexName();
            String prevqid = prevName.contains("#") ? prevName.split(": ")[1].split("#")[0] : prevName.split(": ")[1];
            boolean actionChanged = prev.getVertexName().contains("act") &&
                    curVertex.getVertexName().contains("act") &&
                    !prevqid.equals(action.getQID());
            boolean connectToPrev = !efsm.hasNoTransitions() || !actionChanged;
            if (connectToPrev) {
                addEdge(prev, curVertex);
            }
        }
        insertIntoPortTables(action, v);
    }

    private void insertIntoPortTables(Action action, FlowVertex vertex) {
        if (action == null) {
            return;
        }

        Set<String> ips = action.getInputPorts();
        Set<String> ops = action.getOutputPorts();



        Set<PortInterface> ipf = new HashSet<PortInterface>();
        Set<PortInterface> opf = new HashSet<PortInterface>();

        for (String port : ips) {
            Vector<FlowVertex> V = iportVec.get(port);
            if (V == null) {
                V = new Vector<FlowVertex>();
                iportVec.put(port, V);
            }
            ipf.add(new PortInterface(port, V.size()));

            V.add(vertex);

        }
        for (String port : ops) {
            Vector<FlowVertex> V = oportVec.get(port);
            if (V == null) {
                V = new Vector<FlowVertex>();
                oportVec.put(port, V);
            }
            opf.add(new PortInterface(port, V.size()));

            V.add(vertex);

        }
        vertex.setInputPorts(ipf);
        vertex.setOutputPorts(opf);

    }

    @Override
    public boolean hasOutputPort(String portRef) {
        return oportVec.containsKey(portRef);
    }

    @Override
    public boolean hasInputPort(String portRef) {
        return iportVec.containsKey(portRef);
    }

    public FlowVertex getVertexWithOutPortInterface(PortInterface pi, int numTokens) {
        return getVertexWithPI(oportVec, pi, numTokens);
    }

    public FlowVertex getVertexWithInPortInterface(PortInterface pi, int numTokens) {
        return getVertexWithPI(iportVec, pi, numTokens);
    }

    private FlowVertex getVertexWithPI(HashMap<String, Vector<FlowVertex>> table, PortInterface pi, int numTokens) {
        Vector<FlowVertex> V = table.get(pi.getPortRef());
        if (V == null) {
            System.out.println("No port with name " + pi.getPortRef() + " in " + this.getInitActionQID());
            return null;
        }
        if (V.size() == 0) {
            System.out.println("No vertex with port name " + pi.getPortRef() + " in " + this.getInitActionQID());
            return null;
        }

        int remTokens = pi.getTokenNum();
        int initialTokenNum = pi.getInitialTokenNum();
        int tokenConsumption = pi.getTokenConsumption();
        if (remTokens <= 0) {
            return null;
        }

        //Looks for which is the next vertex to connect
        int index = (initialTokenNum - remTokens) / tokenConsumption;

        //Updates the number of tokens of port
        pi.setTokenNum(remTokens - numTokens);
        if (index < V.size()) {
            return V.get(index);
        } else {
            return null;
        }

    }

    /**
     * Visits the vertices of the SDF.
     */
    public void visit() {
        /*System.out.println("Visiting SDF------------------------");
        TopologyVisitor topo = new TopologyVisitor();
        topo.visit(this);*/
    	System.err.println("Warning!! Autosubgraph : Visit method not implemented yet!");
    }
}
