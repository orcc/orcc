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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Constants;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.CALMLParsingException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.UnhandledCaseException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.CalmlParser;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Simulator;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Variable;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.unrollers.EFSMUnroller;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * Represents the Extended Finite State Machine of an actor.
 */
public class EFSM extends DirectedMultigraph<State, Transition> {

    static final long serialVersionUID = 10000001L;
    String EFSMname;
    String actorName;


    String calmlFileName;
    Set<Port> inputPorts;
    Set<Port> outputPorts;
    Vector<? extends AbstractSubgraph> actorSubgraphs;
    State initialState;
    CalmlParser translator;
    public boolean hasNoTransitions;
    private boolean hasProcAction;
    // key = initActionQID, value = actorSubgraph corresponding to that initActionQID
    HashMap<String, AbstractSubgraph> subgraphMap;
    public Set<String> declaredVariables;
    public HashMap<String, Variable> decTable;
    HashMap<String, Action> actionTable;
    private Set<Action> actions;
    Set<PriorityChain> priorities;

    String networkPath;

    int actorIndex;

    /**
     * Only constructor for EFSM
     *
     * @param calmlFileName
     *            the name of the CALML file which contains the description of
     *            the actor. <br/>Example:
     *            <code>EFSM efsm = new EFSM("MPEG4_mgnt_Framebuf.calml");</code>.
     *
     */
    public EFSM(String actorName, String networkName, String calmlFileName) throws IOException {
        super(Transition.class);
        this.actorName = actorName;
        this.networkPath = networkName;
        initialState = null;
        setCalmlFileName(calmlFileName);
        actorSubgraphs = new Vector<AbstractSubgraph>();
        subgraphMap = new HashMap<String, AbstractSubgraph>();
        try {
            CalmlParser.parse(calmlFileName, this);

        } catch (CALMLParsingException e) {
            e.printStackTrace();
        } catch (UnhandledCaseException e) {
            e.printStackTrace();
        }
    }

    public boolean isHasNoTransitions() {
        return hasNoTransitions;
    }

    public String getNetworkPath() {
        return networkPath;
    }

    public void setNetworkPath(String networkName) {
        this.networkPath = networkName;
    }



    @SuppressWarnings("unused")
	private void printPorts() {
        System.out.println("EFSM Name = " + EFSMname);
        System.out.print("\tIP: ");
        for (Port port : inputPorts) {
            System.out.print(port.getRef() + " , ");
        }
        System.out.println();
        System.out.print("\tOP: ");
        for (Port port : outputPorts) {
            System.out.print(port.getRef() + " , ");
        }
        System.out.println();

    }

    public boolean hasProcAction() {
        return hasProcAction;
    }

    /**
     *
     * @param calmlFileName
     *            the name of the file which contains the description of the
     *            actor in CALML language.
     */
    public void setCalmlFileName(String calmlFileName) {
        this.calmlFileName = calmlFileName;
    }

    /**
     *
     * @return the inputPorts
     */
    public Set<Port> getInputPorts() {
        return inputPorts;
    }

    /**
     *
     * @param inputPorts
     *            the inputPorts to set
     */
    public void setInputPorts(Set<Port> inputPorts) {
        this.inputPorts = inputPorts;
    }

    /**
     *
     * @return  output Ports
     */
    public Set<Port> getOutputPorts() {
        return outputPorts;
    }

    /**
     * @param outputPorts
     *            the outputPorts to set
     */
    public void setOutputPorts(Set<Port> outputPorts) {
        this.outputPorts = outputPorts;
    }

    /**
     * forms an association between initActionQIDs and actor subgraphs.<br/>
     * Example for initActionQID - cmd.newVop
     *
     */
    private void constructSubgraphMap() {
        for (AbstractSubgraph g : actorSubgraphs) {
            subgraphMap.put(g.getInitActionQID(), g);

        }
    }

    /**
     * Gets the unrolled actor SDF subgraph for an action QID.
     *
     * @param actionQID
     *            an actionQID
     * @return the subgraph corresponding to the actionQID.
     */
    public AbstractSubgraph getSubgraphFromQID(String actionQID) {
        return subgraphMap.get(actionQID);
    }

    /**
     * automatic subgraph selector
     */
    public AbstractSubgraph getSubgraph() {
        //check the guard of initedge of each subgraph
        //there should be only one subgraph with a valid guard
        Set<AbstractSubgraph> eligibleGraphs = new HashSet<AbstractSubgraph>();
        Set<AbstractSubgraph> firableGraphs = new HashSet<AbstractSubgraph>();

        for (AbstractSubgraph sg : actorSubgraphs) {
            boolean b = Simulator.validateInitEdge(sg.initEdge);
            if (b) {
                eligibleGraphs.add(sg);
            }
        }
        //Only one elegible graph
        if (eligibleGraphs.size() == 1) {
            return eligibleGraphs.iterator().next();
        }
        //More than one elegible graph
        if (eligibleGraphs.size() > 1) {
            //System.out.print("\tMultiple elegible subgraphs. Initial states:[ ");
        	/*Iterator<AbstractSubgraph> iterator = eligibleGraphs.iterator();
            while (iterator.hasNext()) {
                AbstractSubgraph sg = iterator.next();
                String strConnection = iterator.hasNext() ? " ," : " ]\n";
                //System.out.print(sg.getInitActionQID() + strConnection);
            }*/
        }
        //Takes the firable graphs
        firableGraphs = Simulator.getFirableSubgraphs(eligibleGraphs, getPriorities());
        if (firableGraphs.size() == 1) {
            return firableGraphs.iterator().next();
        }
        if (firableGraphs.size() > 1) {
            System.out.println("Error: Too many subgraphs are firable: ");
            for (AbstractSubgraph sg : firableGraphs) {
                System.out.print(sg.getInitActionQID() + ", ");
            }
            System.out.println();
        }
        return null;
    }

    /**
     * Return all the posible subgraphs. The final graph depends of switch's value
     */
    public ArrayList<AbstractSubgraph> getElegibleGraphs() {
        ArrayList<AbstractSubgraph> eligibleGraphs = new ArrayList<AbstractSubgraph>();

        for (AbstractSubgraph sg : actorSubgraphs) {
            boolean b = Simulator.validateInitEdge(sg.initEdge);
            if (b) {
                eligibleGraphs.add(sg);
            }
        }

        return eligibleGraphs;
    }

    /**
     *
     * @param s
     *            the state in question which has to be checked for having a
     *            self-loop
     * @return true if the state has at least one self-loop, false otherwise
     */
    @SuppressWarnings("unused")
	private boolean hasSelfLoop(State s) {
        return containsEdge(s, s);
    }

    /**
     * sets the initial State of the EFSM
     *
     * @param s
     *            initial State of the the EFSM.
     * @return true if the initial State is not set yet and s is not null.
     * @throws NullPointerException
     *             if s==null
     */
    public boolean setInitialState(State s) {
        if (s == null) {
            throw new NullPointerException();
        }
        if (initialState == null) {
            initialState = s;
            return true;
        } else {
            return false;
        }

    }

    @SuppressWarnings("unused")
	private boolean hasPortWithVarTokenRate() {
        boolean hasPortWithVarTokenRate = false;

        for (Port p : getInputPorts()) {
            if (p.getNumTokens() == Constants.getVariableTokenRate()) {
                hasPortWithVarTokenRate = true;
                break;
            }
        }
        for (Port p : getOutputPorts()) {
            if (p.getNumTokens() == Constants.getVariableTokenRate()) {
                hasPortWithVarTokenRate = true;
                break;
            }
        }
        return hasPortWithVarTokenRate;
    }

    @Override
    public String toString() {

        String init = (initialState == null) ? "Initial State = None" : "Initial State = " + initialState.toString();
        String str = "\nEFSM " + EFSMname + "\n\t" + init + "\n\tTransitions: " + super.toString() + "\n";
        return str;
    }

    /**
     * sets the name of the EFSM
     *
     * @param name
     *            the name to be set
     */
    public void setEFSMName(String name) {
        this.EFSMname = name;
    }

    /**
     * Gets the state of the EFSM which is equal to <code>s</code>.
     *
     * @param s
     *            state whose equal should be searched for in the grpah.
     * @return the state <code>r</code> in the graph which is equal to
     *         <code>s</code> if such an <code>r</code> exists, null
     *         otherwise.
     */
    public State getState(State s) {
        if (s == null) {
            throw new NullPointerException();
        }
        Set<State> VS = this.vertexSet();
        for (State state : VS) {
            if (state.equals(s)) {
                return state;
            }
        }
        return null;
    }

    /**
     * Unrolls the EFSM. <code>actorSubgraphs</code> are set.
     */
    public void unroll(String kind) {
        if (kind.equals(Constants.STATIC_ACTOR)) {
            actorSubgraphs = new EFSMUnroller().unrollStaticActor(this);
        } else if (kind.equals(Constants.BORDERLINE_ACTOR)) {
            actorSubgraphs = new EFSMUnroller().unrollBorderLineActor(this);
        }else if (kind.equals(Constants.ND_ACTOR)) {
            //System.out.println("Actor " + getEFSMName() + " is a ND actor and do not produces any graph.");
            return;
        }
        constructSubgraphMap();
    }

    /**
     * displays SDF subgraphs of the actor, one applet per subgraph.
     */
    public void displayFlowGraphs() {

        for (AbstractSubgraph g : actorSubgraphs) {
            System.out.println("displaying subgraph");
            // g.visit();
            g.displayGraph();
        }
    }

    /**
     * gets the name
     *
     * @return the name
     */
    public String getEFSMName() {
        return EFSMname;
    }

    public boolean hasNoTransitions() {
        return hasNoTransitions;
    }

    public State getInitialState() {
        return initialState;
    }

    public Set<PriorityChain> getPriorities() {
        return priorities;
    }

    public void setPriorities(Set<PriorityChain> priorities) {
        this.priorities = priorities;
    }

    public HashMap<String, Action> getActionTable() {
        return actionTable;
    }

    public void setActionTable(HashMap<String, Action> actionTable) {
        this.actionTable = actionTable;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public boolean addAction(Action action) {

        if (!actionTable.containsKey(action.getQID())) {
            actionTable.put(action.getQID(), action);
            return actions.add(action);
        } else {
            return false;
        }
    }

    public int getActorIndex() {
        return actorIndex;
    }

    public void setActorIndex(int actorIndex) {
        this.actorIndex = actorIndex;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
}
