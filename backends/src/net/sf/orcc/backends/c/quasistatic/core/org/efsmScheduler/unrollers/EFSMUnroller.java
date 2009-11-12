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
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.unrollers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Util;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.UnhandledCaseException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Action;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.AutoSubgraph;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.EFSM;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.FlowVertex;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Port;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.SDFVertex;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.State;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Transition;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.PropertiesParser;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Expr;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Simulator;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Stmt;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Variable;

/**
 * Automatic unroller
 * 
 * @author Victor Martin
 *
 */
public class EFSMUnroller extends AbstractEFSMUnroller {

    private EFSM efsm;
    private HashMap<String, Variable> varTable;
    private Set<String> varNames;

    public EFSMUnroller() {
        super();
    }

    /**
     * Only for Static actor subgraphs. Unrolls this efsm and returns the set of
     * SDF graphs originating from the state machine efsm. This method modifies
     * machine also.
     *
     * @param machine the machine which should be unrolled
     */
    @Override
    public Vector<AutoSubgraph> unrollStaticActor(EFSM machine) {
        this.efsm = machine;

        formVarTable();
        //Shows EFSM
        //System.out.println(this.efsm.toString());
        //Execute only relevant statements. Others will be removed
        //System.out.println("\nRemoving non-critical statements:");
        removeNonCriticalStmts();
        //System.out.println("\nActions after removing critical statements");
        //Prints actions of each actor
        /*for (Action action : efsm.getActions()) {
            //System.out.println("Action: " + action.getQID());
            //System.out.println("\tGuards :" + action.getGuards());
            //System.out.println("\tAction Body: ");
            //for (Stmt stmt : action.getBody()) {
            //System.out.m.out.println("\t\t" + stmt);
            //}
            System.out.println();
        }*/
        //Starts unrolling
        //System.out.println();
        //Graph vector: one graph per switch value
        Vector<AutoSubgraph> actorSubgraphs = new Vector<AutoSubgraph>();
        //Takes the init edge
        Set<Transition> initEdges = getInitEdges();
        //Fills vector
        for (Transition edge : initEdges) {
            AutoSubgraph actorSg = unrollSubgraph(edge);
            actorSg.setGraphName(machine.getEFSMName());
            actorSubgraphs.add(actorSg);
        }
        
        return actorSubgraphs;
    }

    /**
     * Only for Static actor subgraphs. Unrolls this efsm and returns the set of 
     * SDF graphs originating from the state machine efsm. This method modifies 
     * machine also.
     *
     * @param machine the machine which should be unrolled
     */
    @Override
    public Vector<AutoSubgraph> unrollBorderLineActor(EFSM machine) {
        this.efsm = machine;

        formVarTable();
        //Shows EFSM
        //System.out.println(this.efsm.toString());
        //Execute only relevant statements. Others will be removed
        //System.out.println("\nRemoving non-critical statements:");
        removeNonCriticalStmts();
        //System.out.println("\nActions after removing critical statements");
        //Prints actions of each actor
        /*for (Action action : efsm.getActions()) {
            //System.out.println("Action: " + action.getQID());
            //System.out.println("\tGuards :" + action.getGuards());
            System.out.println("\tAction Body: ");
            for (Stmt stmt : action.getBody()) {
                System.out.println("\t\t" + stmt);
            }
            System.out.println();
        }*/
        //Starts unrolling
        //System.out.println();
        //Graph vector: one graph per switch value
        Vector<AutoSubgraph> actorSubgraphs = new Vector<AutoSubgraph>();
        //Takes the init edge
        Set<Transition> initEdges = getInitEdges();
        //Fills vector
        for (Transition edge : initEdges) {
            AutoSubgraph actorSg = unrollSubgraph(edge);
            actorSg.setGraphName(machine.getEFSMName());
            actorSubgraphs.add(actorSg);
        }
        
        return actorSubgraphs;
    }

    private void formVarTable() {

        varTable = new HashMap<String, Variable>();

        Set<String> decVars = efsm.declaredVariables;
        Set<Action> actions = efsm.getActions();
        HashMap<String, Variable> decTable = efsm.decTable;

        Set<String> guardVars = new HashSet<String>();

        for (Action action : actions) {
            for (Expr guardExpr : action.getGuards()) {
                guardVars.addAll(guardExpr.getVarNames());
            }
        }
        // these are critical variables
        varNames = Util.intersection(decVars, guardVars);

        // System.out.println("Critical variables"+varNames);
        for (String varName : varNames) {
            Variable var = decTable.get(varName);
            varTable.put(varName, var);
        }
    }

    /**
     * removes the statements which need not be executed during simulation
     */
    private void removeNonCriticalStmts() {
        Set<Action> actions = efsm.getActions();
        for (Action action : actions) {
            Vector<Stmt> body = action.getBody();
            if (body == null) {
                continue;
            }

            for (int i = 0; i < body.size(); i++) {
                if (!isCritical(body.get(i))) {
                    //System.out.println("\tStatement removed: " + body.get(i));
                    body.remove(i);
                    i--;
                }

            }
        }
    }

    private void removeNonCriticalStmts(Vector<Stmt> block) {
        for (int i = 0; i < block.size(); i++) {
            if (!isCritical(block.get(i))) {
                block.remove(i);
                i--;
            }
        }
    }

    /**
     * checks whether the statement should be simulated or not.
     *
     * @param stmt
     * @return true iff the stmt is an assignment and the variable on the lhs of
     *         the assignment is a critical variable. A variable is critical if
     *         it is declared within the cal file and is used in a guard of
     *         atleast one of the actions of the EFSM.
     */
    private boolean isCritical(Stmt stmt) {
        switch (stmt.getType()) {
            case ASIGN:
                return varNames.contains(stmt.getLhs());
            case IF:
                return varNames.containsAll(stmt.ifCond.getVarNames()) && isCritical(stmt.ifBlock);
            case BLOCK:
                removeNonCriticalStmts(stmt.block);
                return !stmt.block.isEmpty();
            default:
                return false;

        }

    }

    /**
     * unrolls the subgraph originating from initEdge
     *
     * @param initEdge
     * @return the unrolled subgraph
     */
    private AutoSubgraph unrollSubgraph(Transition initEdge) {
        AutoSubgraph actorSg = new AutoSubgraph(efsm, initEdge);
        Simulator sim = new Simulator(efsm, initEdge);
        sim.setVarTable(varTable);
        boolean success = false;
        //Takes all the actions
        while (sim.hasNextAction()) {
            Action action = null;
            try {
                //Takes action
                action = sim.nextAction();
                //Fires action
                success = sim.fireAction();
            } catch (UnhandledCaseException e) {
                e.printStackTrace();
            }
           //Once the action is fired, add action to graph
            if(success && !action.isArtificial()){
            	String efsmName = efsm.getEFSMName();
                String actorName = efsm.getActorName();
                int actorIndex = efsm.getActorIndex();
                actorSg.addAction(actorName,efsmName, action, actorIndex);
            }
        }
        //The name machine of each action is setted
        for (SDFVertex v : actorSg.vertexList()) {
            FlowVertex v1 = (FlowVertex) v;
            v1.setMachineName(efsm.getEFSMName());
        }
        actorSg.setName(efsm.getEFSMName());
        restoreTokenPatterns();
        //Return the actor's graph
        return actorSg;
    }

    private void restoreTokenPatterns() {
        if(efsm == null) return;
        
        HashMap<String, Integer> patterns = PropertiesParser.getPortsMap(efsm.getEFSMName());
        HashMap<String, Integer> consumptionPatterns = PropertiesParser.getConsumptionTokenPortsMap(efsm.getEFSMName());
        for (Port port : efsm.getInputPorts()) {
        	int consumptionTokens = 0;
            int numTokens = 0;
        	if( patterns.containsKey(port.getRef()) && consumptionPatterns.containsKey(port.getRef())){
                consumptionTokens = consumptionPatterns.get(port.getRef());
                numTokens = patterns.get(port.getRef()) * consumptionTokens;
        	}
            port.setNumTokens(numTokens);
            port.setConsumptionTokens(consumptionTokens);
        }
        for (Port port : efsm.getOutputPorts()) {
        	int consumptionTokens = 0;
            int numTokens = 0;
        	if( patterns.containsKey(port.getRef()) && consumptionPatterns.containsKey(port.getRef())){
                consumptionTokens = consumptionPatterns.get(port.getRef());
                numTokens = patterns.get(port.getRef()) * consumptionTokens;
        	}
            port.setNumTokens(numTokens);
            port.setConsumptionTokens(consumptionTokens);
        }
        //special case : port Inversequant.AC
        /*if (efsm.getName().equals("MPEG4_algo_Inversequant")) {
            //get the in port with name AC
            for (Port port : efsm.getInputPorts()) {
                if (port.getRef().equals("AC")) {
                    port.setNumTokens(Constants.getBlocksize() * Constants.getBlocksize());
                    break;
                }

            }
        }*/
    }

    private Set<Transition> getInitEdges() {
        State initialState = efsm.getInitialState();
        
        //Take all the init edges (edges from initial state)
        Set<Transition> initEdges = new HashSet<Transition>(efsm.outgoingEdgesOf(initialState));

        //Removes self-loops of initial state
        //Set<Transition> selfLoops = new HashSet<Transition>(efsm.getAllEdges(initialState, initialState));
        //initEdges.removeAll(selfLoops);

        return initEdges;
    }

    

}
