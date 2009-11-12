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
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Switch;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Util;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.TypeMismatchException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.UnhandledCaseException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.AbstractSubgraph;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Action;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.EFSM;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Port;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.PriorityChain;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.State;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Transition;

/**
 * Represents a simulation environment
 * 
 * @author Victor Martin 5.12.2008
 * 
 */
public class Simulator {

    //Limits
    private static final int FIRED_ACTIONS_LIMIT = 150;
    // Initial edge
    private Transition initEdge;
    // Extended Finite State Machine
    private EFSM efsm;
    // Current state
    private State currentState;
    // private Action currentAction;
    // Current edge
    private Transition currentEdge;
    // HashTable of variables
    private HashMap<String, Variable> varTable;
    // private Set<String> varNames;
    // Iterator of firable actions
    private Iterator<Transition> firableActionIter;
    // Number of fired actions
    private int numActionsFired;
    private boolean differentChain;

    

    /**
     *
     * Constructor
     *
     * @param efsm
     *            Extended Finite State Machine
     * @param initEdge
     *            Initial edge
     */
    public Simulator(EFSM efsm, Transition initEdge) {
        this.initEdge = initEdge;
        this.efsm = efsm;
        currentEdge = null;
        currentState = null;
        numActionsFired = 0;
    }

    /**
     * copies <code>table</code> into <code>varTable</code>. Keys are shallow
     * copied and values are copied deep.
     *
     * @param table
     */
    public void setVarTable(HashMap<String, Variable> table) {
        varTable = Util.copyMap(table);
    }

    /**
     * @param expr
     *            Expression
     * @return Expression's value
     * @throws UnhandledCaseException
     * @throws TypeMismatchException
     */
    private int evaluate(Expr expr) throws UnhandledCaseException,
            TypeMismatchException {
        int value = -1;
        int value1 = -1, value2 = -1;
        Arity arity = expr.getArity();
        if (arity == Arity.APPLICATION) {
            if (expr.appName != null && expr.appName.equals("bitand")) {

            Vector<Expr> args = expr.args;
            if (args.size() != 2) {
                throw new UnhandledCaseException("Number of args in bitand =" + args.size());
            } else {
                value1 = evaluate(args.get(0));
                value2 = evaluate(args.get(1));
                value = Util.bitand(value1, value2);// bitand operation
                return value;
                }
            }
            throw new UnhandledCaseException(
                    "Trying to evaluate Application expr");
        }

        switch (expr.getOp()) {
            case constOp:
                value = expr.getValue();
                break;
            case varOp:
                String name = expr.toString();
                if(varTable.containsKey(name))
                	value = varTable.get(name).getValue();
                break;
            case uminus:
                value1 = evaluate(expr.getLeftTree());
                value = -value1;
                break;
            case notOp:
                value1 = evaluate(expr.getLeftTree());
                if (Util.isBoolean(value1)) {
                    value = 1 - value1;
                } else {
                    value = expr.getValue();
                	//throw new TypeMismatchException(value1 + " " + value2);
                }
                break;
            case plus:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                value = value1 + value2;
                break;
            case minus:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                value = value1 - value2;
                break;
            case multOp:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                value = value1 * value2;
                break;
            case divOp:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                value = value1 / value2;
                break;

            case and:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                if (Util.isBoolean(value1) && Util.isBoolean(value2)) {
                    value = value1 & value2;
                } else {
                    throw new TypeMismatchException(value1 + " " + value2);
                }
                break;
            case or:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                if (Util.isBoolean(value1) && Util.isBoolean(value2)) {
                    value = value1 | value2;
                } else {
                    throw new TypeMismatchException(value1 + " " + value2);
                }
                break;
            case lt:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                value = value1 < value2 ? 1 : 0;
                break;
            case gt:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                value = value1 > value2 ? 1 : 0;
                break;
            case leq:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                value = value1 <= value2 ? 1 : 0;
                break;
            case geq:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                value = value1 >= value2 ? 1 : 0;
                break;
            case eq:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                value = value1 == value2 ? 1 : 0;
                break;
            case neq:
                value1 = evaluate(expr.getLeftTree());
                value2 = evaluate(expr.getRightTree());
                value = value1 != value2 ? 1 : 0;
                break;

            default:
                throw new UnhandledCaseException("Unknown operator");
        }
        return value;
    }

    /**
     * Execute a statement
     *
     * @param stmt Statement to execute
     * @throws TypeMismatchException
     * @throws UnhandledCaseException
     */
    private void execute(Stmt stmt) throws TypeMismatchException,
            UnhandledCaseException {
        switch (stmt.getType()) {
            case ASIGN:
                Expr rhs = stmt.getRhs();
                int value = evaluate(rhs);
                Variable var = varTable.get(stmt.getLhs());
                var.setValue(value);
                break;
            case IF:
                Expr ifCond = stmt.ifCond;
                int cond = evaluate(ifCond);
                if (Util.isTrue(cond)) {
                    execute(stmt.ifBlock);
                }
                break;
            case BLOCK:
                for (Stmt blockStmt : stmt.block) {
                    execute(blockStmt);
                }
                break;
            default:
                throw new UnhandledCaseException("Unhandled Stmt type " + stmt.getType() + " in execution");
        }
    }

    /**
     * Check if there are more actions to fired. It happens if:
     *      1. Number of actions fired are lower or equal to limit of fired actions(FIRED_ACTIONS_LIMIT)
     *      2. Current state is null: first time this method is called, current state is null.
     *      3. Current state is not Initial state.
     */
    public boolean hasNextAction() {
        boolean firedActionsLimitExceeded = numActionsFired > FIRED_ACTIONS_LIMIT;
        boolean isCurrentStateNull = currentState == null;
        boolean isInitialState = isCurrentStateNull ? false : currentState.equals(efsm.getInitialState());
        /*if (firedActionsLimitExceeded) {
            System.out.println("Unroll finished due to fired actions have exceeded limit: " + FIRED_ACTIONS_LIMIT + " actions");
        } else if (isInitialState) {
            System.out.println("Unroll finished due to it has been reached the initial state");
        }*/
        
        return !firedActionsLimitExceeded && (isCurrentStateNull || !isInitialState);
    }

    

    /**
     * Gets the next action after considering guards and priorities of the
     * actions from the currentState of the simulator. Also sets currentEdge.
     * If there are multiple firable actions even after considering guards and
     * priorities, then those actions are kept in a buffer and supplied in an
     * unspecified order.
     */
    public Action nextAction() throws UnhandledCaseException {

        setDifferentChain(false);

        // are there any firable actions left in the buffer?
        if (firableActionIter != null && firableActionIter.hasNext()) {
            currentEdge = firableActionIter.next();
            return currentEdge.getAction();
        }

        // This cond is true when nextAction() is called for the first time
        if (currentState == null) {
            currentEdge = initEdge;
            return currentEdge.getAction();
        }

        //Get the possible edges
        Set<Transition> eligible = getEligibleEdges();
        //Get the activated edges presents in eligible
        Set<Transition> activated = getActivatedEdges(eligible);
        //Get the firable edges presents in activated
        Set<Transition> firable = getFirableEdges(activated);

        //Take the iterator
        firableActionIter = firable.iterator();
        if (firableActionIter.hasNext()) {
            //Take the new current edge
            currentEdge = firableActionIter.next();
            //return the next action( of current edge )
            return currentEdge.getAction();
        } else {
            return initEdge.getAction();
        }
    }

    /**
     * Consumes input tokens and produces output tokens
     */
    private boolean tokenConsumption(Action action) {

        Set<String> IP = action.getInputPorts();
        Set<String> OP = action.getOutputPorts();
        boolean success = true;
        //Consumes input tokens
        for (Port port : efsm.getInputPorts()) {
            String portName = port.getRef();
            if (IP.contains(portName)) {
                success = port.consumes();

            }
        }

        //Produces output tokens
        for (Port port : efsm.getOutputPorts()) {
            String portName = port.getRef();
            if (OP.contains(portName)) {
                port.produces();
            }
        }
        return success;

    }

    /**
     * Executes the body of the current action and consumes tokens
     */
    public boolean fireAction() {

        Action action = null;
        boolean success = false;
        try {

            // Takes the current action
            action = currentEdge.getAction();
            if (action != null) {
                //Consumes tokens
                success = tokenConsumption(action);
                if (success) {
                    //Executes statements
                    for (Stmt stmt : action.getBody()) {
                        execute(stmt);
                    }
                }

            }

            //change the state
            currentState = efsm.getEdgeTarget(currentEdge);
            //change the number of actions fired
            numActionsFired++;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * @return Outgoing(possible or eligible) edges from the
     * <code>currentState</code>
     */
    private Set<Transition> getEligibleEdges() {
        Set<Transition> eligible = new HashSet<Transition>();
        Set<Transition> outEdges = efsm.outgoingEdgesOf(currentState);
        for (Transition edge : outEdges) {
            if (edge != null && edge.getAction() != null &&!edge.getAction().needsTokensForUnrolling()) {
                eligible.add(edge);
            }
        }
        return new HashSet<Transition>(efsm.outgoingEdgesOf(currentState));
    }

    /**
     * Gets transitions whose action-guards are valid from the set of eligible
     * transitions.
     * Assumption : each transition has only one action.
     *
     * @param eligible
     *            the set of eligible transitions
     *
     */
    private Set<Transition> getActivatedEdges(Set<Transition> eligible)
            throws UnhandledCaseException {
        Set<Transition> activated = new HashSet<Transition>();
        for (Transition edge : eligible) {
            Action action = edge.getAction();
            assert (action != null);
            boolean valid = action == null? false:validateGuards(action) && action.canBeFired(efsm);
            if (valid) {
                activated.add(edge);
            }
        }
        return activated;
    }

    /**
     *
     *
     * @param activated
     * @return a set of Transitions which could be fired
     */
    private Set<Transition> getFirableEdges(Set<Transition> activated) {

        Set<PriorityChain> priorities = efsm.getPriorities();
        if (priorities.isEmpty()) {
            return activated;
        }
        if (activated.isEmpty() || activated.size() == 1) {
            return activated;
        }

        Set<Transition> firable = new HashSet<Transition>();

        // try to find a chain containing all actions in the set.
        // if such chain does not exist, throw Unhandled case exception

        Set<String> qids = new HashSet<String>();
        for (Transition edge : activated) {
            Action action = edge.getAction();
            qids.add(action.getQID());
        }

        String someQID = qids.iterator().next();

        PriorityChain chain = null;
        for (PriorityChain pc : priorities) {
            if (pc.contains(someQID)) {
                // check for all qids in the same chain
                if (pc.containsAll(qids)) {
                    chain = pc;
                    break;
                }
            }
        }
        firable.add(chain.getTransWithMaxPr(activated));

        return firable;
    }

    /**
     * validates guards of <code>action</code>.
     *
     * @param action
     * @return <p>
     *         true if
     *         <ul>
     *         <li>action does not have guards or</li>
     *         <li>action has guards and all of them evaluate to true</li>
     *         </ul>
     *         <br/>
     *         false otherwise
     */
    private boolean validateGuards(Action action) throws UnhandledCaseException {
        Set<Expr> guards = action.getGuards();
        boolean valid = true;
        for (Expr guard : guards) {
            try {
                if (!Util.isTrue(evaluate(guard))) {
                    valid = false;
                    break;
                }
            } catch (TypeMismatchException e) {
                e.printStackTrace();
            }
        }
        return valid;
    }

    /**
     * @return different chain
     */
    public boolean isDifferentChain() {
        return differentChain;
    }

    /**
     * Set differentChain
     *
     * @param differentChain
     */
    public void setDifferentChain(boolean differentChain) {
        this.differentChain = differentChain;
    }

    /**
     * Validates <code>edge</code> as a possible initial edge
     *
     * @param edge Possible initial edge
     * @return true -> <code>edge<\code> is a valid initial edge
     *         false -> in other case
     */
    public static boolean validateInitEdge(Transition edge) {
        Action a = edge.getAction();
        Set<Expr> guards = a.getGuards();
        boolean valid = true;
        for (Expr guard : guards) {
            int val = 0;
            try {
                val = evaluateRuntime(guard);
                valid = Util.isTrue(val);
                if (!valid) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
                break;
            }

        }
        return valid;
    }

    /**
     * This method evaluates an expresion.
     * @param expr
     * @return integer value of input expresion
     * @throws org.efsmScheduler.exceptions.UnhandledCaseException
     * @throws org.efsmScheduler.exceptions.TypeMismatchException
     */
    public static int evaluateRuntime(Expr expr)
            throws UnhandledCaseException, TypeMismatchException {

        int value = -1;
        int value1 = -1, value2 = -1;
        Arity arity = expr.getArity();
        boolean isBitANDOperation = arity == Arity.APPLICATION && expr.appName != null && expr.appName.equals("bitand");
        if (isBitANDOperation) {

            Vector<Expr> args = expr.args;
            if (args.size() != 2) {
                throw new UnhandledCaseException("Number of args in bitand =" + args.size());
            } else {
                value1 = evaluateRuntime(args.get(0));
                value2 = evaluateRuntime(args.get(1));
                value = Util.bitand(value1, value2);// bitand operation
                return value;
            }
        }

        switch (expr.getOp()) {
            case constOp:
                value = expr.getValue();
                break;
            case varOp:
                Integer val = Switch.getInstance().getValue(expr.toString());
                if (val == null) {
                    value = Switch.getInstance().getToken(expr.toString());
                } else {
                    value = val.intValue();
                }
                break;

            case eq:
                value1 = evaluateRuntime(expr.getLeftTree());
                value2 = evaluateRuntime(expr.getRightTree());
                value = value1 == value2 ? 1 : 0;
                break;
            case neq:
                value1 = evaluateRuntime(expr.getLeftTree());
                value2 = evaluateRuntime(expr.getRightTree());
                value = value1 != value2 ? 1 : 0;
                break;
            case lt:
                value1 = evaluateRuntime(expr.getLeftTree());
                value2 = evaluateRuntime(expr.getRightTree());
                value = value1 < value2 ? 1 : 0;
                break;
            case gt:
                value1 = evaluateRuntime(expr.getLeftTree());
                value2 = evaluateRuntime(expr.getRightTree());
                value = value1 > value2 ? 1 : 0;
                break;
            case geq:
                value1 = evaluateRuntime(expr.getLeftTree());
                value2 = evaluateRuntime(expr.getRightTree());
                value = value1 >= value2 ? 1 : 0;
                break;
            case leq:
                value1 = evaluateRuntime(expr.getLeftTree());
                value2 = evaluateRuntime(expr.getRightTree());
                value = value1 <= value2 ? 1 : 0;
                break;
            case uminus:
                value = evaluateRuntime(expr.getLeftTree()) * -1;
                break;
            default:
                throw new UnhandledCaseException("Unknown operator :" + expr.getOp() + " in " + expr);
        }
        return value;

    }

    /**
     *
     * @param activated
     * @param priorities
     * @return a set of firable subgraphs
     */
    public static Set<AbstractSubgraph> getFirableSubgraphs(Set<AbstractSubgraph> activated, Set<PriorityChain> priorities) {

        //No priorities
        if (priorities.isEmpty()) {
            return activated;
        }
        //No elegible graphs
        if (activated.isEmpty() || activated.size() == 1) {
            return activated;
        }

        Set<AbstractSubgraph> firable = new HashSet<AbstractSubgraph>();

        // try to find a chain containing all actions in the set.
        // if such chain does not exist, throw Unhandled case exception

        Set<String> qids = new HashSet<String>();
        for (AbstractSubgraph sg : activated) {
            Transition edge = sg.getInitEdge();
            Action action = edge.getAction();
            qids.add(action.getQID());
        }

        String someQID = qids.iterator().next();

        PriorityChain chain = null;
        for (PriorityChain pc : priorities) {
            if (pc.contains(someQID)) {
                // check for all qids in the same chain
                if (pc.containsAll(qids)) {
                    chain = pc;
                    break;
                }
            }
        }
        firable.add(chain.getSubWithMaxPr(activated));

        return firable;

    }
}
