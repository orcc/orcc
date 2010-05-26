/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Constant;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.consts.ConstantEvaluator;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;

/**
 * This class defines an actor that can be interpreted by calling
 * {@link #initialize()} and {@link #schedule()}. It consists in an action
 * scheduler, an FSM state, and a node interpreter.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class InterpretedActor extends AbstractInterpretedActor {

	private OrccProcess process;

	/**
	 * Debugger utils
	 */
	private class NodeInfo {
		public Expression condition;
		public BlockNode joinNode;
		public int nbSubNodes;
		public int subNodeIdx;
		public List<CFGNode> subNodes;

		public NodeInfo(int subNodeIdx, int nbSubNodes, List<CFGNode> subNodes,
				BlockNode joinNode, Expression condition) {
			this.subNodeIdx = subNodeIdx;
			this.nbSubNodes = nbSubNodes;
			this.subNodes = subNodes;
			this.joinNode = joinNode;
			this.condition = condition;
		}
	}

	private class Breakpoint {
		public Breakpoint(Action action, Integer lineNb) {
			this.action = action;
			this.lineNb = lineNb;
		}

		public Action action;
		public int lineNb;
	}

	private List<Breakpoint> breakpoints;
	private Action currentAction = null;
	private Action breakAction = null;
	private List<Instruction> instrStack;
	private boolean isStepping = false;
	private int nbOfFirings;
	private List<NodeInfo> nodeStack;
	private int nodeStackLevel;

	/**
	 * Interpretation and evaluation tools
	 */
	protected NodeInterpreter interpret;
	private ConstantEvaluator constEval;
	protected ExpressionEvaluator exprInterpreter;
	private ListAllocator listAllocator;

	/**
	 * Actor's FSM management parameters
	 */
	protected String fsmState;
	protected ActionScheduler sched;

	/**
	 * Actor's constant parameters to be set at initialization time
	 */
	private Map<String, Expression> parameters;

	/**
	 * Creates a new interpreted actor instance for simulation or debug
	 * 
	 * @param id
	 *            name of the associated instance
	 * @param parameters
	 *            actor's parameters to be set
	 * @param actor
	 *            actor class definition
	 * @param outputFolder
	 *            location of compiled actors objects
	 */
	public InterpretedActor(String id, Map<String, Expression> parameters,
			Actor actor, String outputFolder, OrccProcess process) {
		// Set instance name and actor class definition at parent level
		super(id, actor);

		// Register master process (used for console I/O access)
		this.process = process;
		
		// Build a node interpreter for visiting CFG and instructions
		interpret = new NodeInterpreter();
		// Create the List allocator for state and procedure local vars
		this.listAllocator = new ListAllocator();
		// Creates an expression evaluator for state and local variables init
		this.constEval = new ConstantEvaluator();
		// Create the expression evaluator
		this.exprInterpreter = new ExpressionEvaluator();

		// Get actor FSM properties
		this.sched = actor.getActionScheduler();
		if (sched.hasFsm()) {
			this.fsmState = sched.getFsm().getInitialState().getName();
		} else {
			this.fsmState = "IDLE";
		}

		// Get the parameters value from instance map
		this.parameters = parameters;

		// Create lists for nodes and instructions in case of debug mode
		this.nodeStack = new ArrayList<NodeInfo>();
		this.instrStack = new ArrayList<Instruction>();
		this.breakpoints = new ArrayList<Breakpoint>();
		this.nbOfFirings = 0;
	}

	/**
	 * Returns true if the action has no output pattern, or if it has an output
	 * pattern and there is enough room in the FIFOs to satisfy it.
	 * 
	 * @param outputPattern
	 *            output pattern of an action
	 * @return true if the pattern is empty or satisfiable
	 */
	protected boolean checkOutputPattern(Pattern outputPattern) {
		if (outputPattern != null) {
			for (Entry<Port, Integer> entry : outputPattern.entrySet()) {
				Port outputPort = entry.getKey();
				Integer nbOfTokens = entry.getValue();
				if (!ioFifos.get(outputPort.getName()).hasRoom(nbOfTokens)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void clearBreakpoint(int breakpoint) {
		// Remove breakpoint from the list
		for (Breakpoint bkpt : breakpoints) {
			if ((breakpoint == bkpt.lineNb)) {
				breakpoints.remove(bkpt);
			}
		}
	}

	@Override
	public void close() {
		// Nothing to do when terminating interpretation
	}

	/**
	 * Require the execution (interpretation) of the given actor's action
	 * 
	 * @param action
	 * @return <code>1</code>
	 */
	protected int execute(Action action) {
		// Update last visited location value :
		lastVisitedLocation = action.getBody().getLocation();
		lastVisitedAction = action.getName();
		// Interpret the whole action
		interpretProc(action.getBody());
		nbOfFirings++;
		return 1;
	}

	/**
	 * Returns the name of the current FSM state.
	 * 
	 * @return the name of the current FSM state
	 */
	public String getFsmState() {
		return fsmState;
	}

	/**
	 * Get the next schedulable action to be executed for this actor
	 * 
	 * @return the schedulable action or null
	 */
	private Action getNextAction() {
		if (sched.hasFsm()) {
			// Check for untagged actions first
			for (Action action : sched.getActions()) {
				if (isSchedulable(action)) {
					if (checkOutputPattern(action.getOutputPattern())) {
						return action;
					}
					break;
				}
			}

			// Then check for next FSM transition
			for (NextStateInfo info : sched.getFsm().getTransitions(fsmState)) {
				Action action = info.getAction();
				if (isSchedulable(action)) {
					// Update FSM state
					if (checkOutputPattern(action.getOutputPattern())) {
						fsmState = info.getTargetState().getName();
						return action;
					}
					break;
				}
			}
		} else {
			// Check next schedulable action in respect of the priority order
			for (Action action : sched.getActions()) {
				if (isSchedulable(action)) {
					if (checkOutputPattern(action.getOutputPattern())) {
						return action;
					}
					break;
				}
			}
		}
		return null;
	}

	@Override
	public InterpreterStackFrame getStackFrame() {
		InterpreterStackFrame stackFrame = new InterpreterStackFrame();
		stackFrame.actorFilename = actor.getFile();
		stackFrame.codeLine = lastVisitedLocation.getStartLine();
		stackFrame.nbOfFirings = nbOfFirings;
		stackFrame.stateVars.clear();
		for (Variable stateVar : actor.getStateVars()) {
			stackFrame.stateVars.put(stateVar.getName(), stateVar.getValue());
		}
		stackFrame.currentAction = lastVisitedAction;
		stackFrame.fsmState = fsmState;
		return stackFrame;
	}

	@Override
	public int goToBreakpoint() {
		int bkptLine = 0;
		// Then interpret the actor until breakpoint is reached
		while (bkptLine == 0) {
			int actorStatus = step(false);
			if (actorStatus <= 1) {
				throw new OrccRuntimeException(
						"Error : cannot synchronize to action breakpoint");
			} else {
				for (Breakpoint bkpt : breakpoints) {
					if ((bkpt.lineNb == lastVisitedLocation.getStartLine())) {
						bkptLine = bkpt.lineNb;
						isStepping = true;
					}
				}
			}
		}
		return bkptLine;
	}

	/**
	 * Initialize interpreted actor. That is to say constant parameters,
	 * initialized state variables, allocation and initialization of state
	 * arrays.
	 */
	@Override
	public void initialize() {
		try {
			// Initialize actors parameters with instance map
			for (Variable param : actor.getParameters()) {
				Expression value = parameters.get(param.getName());
				if (value != null) {
					param.setValue(value.accept(exprInterpreter));
				}
			}

			// Check for List state variables which need to be allocated or
			// initialized
			for (Variable stateVar : actor.getStateVars()) {
				Type type = stateVar.getType();
				// Initialize variables with constant values
				Constant initConst = ((StateVariable) stateVar)
						.getConstantValue();
				if (initConst == null) {
					if (type.isList()) {
						// Allocate empty array variable
						stateVar.setValue(listAllocator.allocate(type));
					}
				} else {
					// initialize
					Object initVal = initConst.accept(constEval);
					stateVar.setValue(initVal);
				}
			}

			// Get initializing procedure if any
			for (Action action : actor.getInitializes()) {
				Object isSchedulable = interpretProc(action.getScheduler());
				if (isSchedulable instanceof Boolean) {
					if ((Boolean) isSchedulable) {
						interpretProc(action.getBody());
					}
				}
			}
		} catch (OrccRuntimeException ex) {
			throw new OrccRuntimeException("Runtime exception thrown by actor "
					+ actor.getName() + " :\n" + ex.getMessage());
		}
	}

	/**
	 * Interprets the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 * 
	 * @return an object which contains procedure returned value
	 */
	protected Object interpretProc(Procedure procedure) {
		// Don't mind about procedure parameters (already set)

		// Allocate local List variables
		for (Variable local : procedure.getLocals()) {
			Type type = local.getType();
			if (type.isList()) {
				local.setValue(listAllocator.allocate(type));
			}
		}

		// Interpret procedure body
		for (CFGNode node : procedure.getNodes()) {
			node.accept(interpret, ioFifos, process);
		}

		// Procedure return value
		Object result = interpret.getReturnValue();

		// Return the result object
		return result;
	}

	/**
	 * Returns true if the given action is schedulable.
	 * 
	 * @param action
	 *            an action
	 * @return true if the given action is schedulable
	 */
	protected boolean isSchedulable(Action action) {
		Object isSchedulable = interpretProc(action.getScheduler());
		return ((isSchedulable instanceof Boolean) && ((Boolean) isSchedulable));
	}

	@Override
	public boolean isStepping() {
		return isStepping;
	}

	/**
	 * Manages the stack of nodes to be interpreted by the debugger
	 */
	private boolean popNodeStack() {
		boolean exeStmt = false;
		NodeInfo node = nodeStack.get(nodeStackLevel - 1);

		if (node.nbSubNodes > 0) {
			if (node.subNodeIdx == node.nbSubNodes) {
				if ((node.condition != null)
						&& ((Boolean) node.condition.accept(exprInterpreter))) {
					node.subNodeIdx = 0;
					exeStmt = true;
				} else {
					nodeStack.remove(nodeStackLevel - 1);
					nodeStackLevel--;
					if (node.joinNode != null) {
						Iterator<Instruction> it = node.joinNode.iterator();
						while (it.hasNext()) {
							instrStack.add(it.next());
						}
						nodeStack.add(new NodeInfo(0, 0, null, null, null));
						nodeStackLevel++;
					}
				}
			} else {
				CFGNode subNode = node.subNodes.get(node.subNodeIdx++);
				lastVisitedLocation = subNode.getLocation();
				if (subNode instanceof IfNode) {
					Object condition = ((IfNode) subNode).getValue().accept(
							exprInterpreter);
					if ((Boolean) condition) {
						nodeStack.add(new NodeInfo(0, ((IfNode) subNode)
								.getThenNodes().size(), ((IfNode) subNode)
								.getThenNodes(), ((IfNode) subNode)
								.getJoinNode(), null));
					} else {
						nodeStack.add(new NodeInfo(0, ((IfNode) subNode)
								.getElseNodes().size(), ((IfNode) subNode)
								.getElseNodes(), ((IfNode) subNode)
								.getJoinNode(), null));
					}
					nodeStackLevel++;
					exeStmt = true;
				} else if (subNode instanceof WhileNode) {
					Expression condition = ((WhileNode) subNode).getValue();
					if ((Boolean) condition.accept(exprInterpreter)) {
						nodeStack.add(new NodeInfo(0, ((WhileNode) subNode)
								.getNodes().size(), ((WhileNode) subNode)
								.getNodes(), null, condition));
						nodeStackLevel++;
					}
					exeStmt = true;
				} else /* BlockNode => add instructions to stack */{
					Iterator<Instruction> it = ((BlockNode) subNode).iterator();
					while (it.hasNext()) {
						instrStack.add(it.next());
					}
					nodeStack.add(new NodeInfo(0, 0, null, null, null));
					nodeStackLevel++;
				}
			}
		} else {
			// Instructions
			if (instrStack.size() > 0) {
				Instruction instr = instrStack.remove(0);
				instr.accept(interpret, ioFifos, process);
				lastVisitedLocation = instr.getLocation();
				exeStmt = true;
			}
			if (instrStack.size() == 0) {
				nodeStack.remove(nodeStackLevel - 1);
				nodeStackLevel--;
			}
		}
		return exeStmt;
	}

	@Override
	public Integer run() {
		try {
			// Synchronize actor to the end of current stepping action
			if (isStepping) {
				while (step(false) == 2)
					;
				isStepping = false;
			}
			// "Round-robbin-like" scheduling policy : schedule only all
			// schedulable
			// action of an
			// actor before returning
			int nbOfFiredActions = 0;
			Action action;
			while ((action = getNextAction()) != null) {
				for (Breakpoint bkpt : breakpoints) {
					if (action == bkpt.action) {
						breakAction = action;
						return -2;
					}
				}
				nbOfFiredActions += execute(action);
				nbOfFirings += nbOfFiredActions;
				return nbOfFiredActions;
			}
			return 0;
		} catch (OrccRuntimeException ex) {
			throw new OrccRuntimeException("Runtime exception thrown by actor "
					+ actor.getName() + " :\n" + ex.getMessage());
		}
	}

	@Override
	public Integer schedule() {
		try {
			// Synchronize actor to the end of current stepping action
			if (isStepping) {
				while (step(false) == 2)
					;
				isStepping = false;
			}
			// "Synchronous-like" scheduling policy : schedule only 1 action per
			// actor at each "schedule" (network logical cycle) call
			Action action = getNextAction();
			if (action != null) {
				for (Breakpoint bkpt : breakpoints) {
					if (action == bkpt.action) {
						breakAction = action;
						return -2;
					}
				}
				return execute(action);
			} else {
				return 0;
			}
		} catch (OrccRuntimeException ex) {
			throw new OrccRuntimeException("Runtime exception thrown by actor "
					+ actor.getName() + " :\n" + ex.getMessage());
		}
	}

	@Override
	public void setBreakpoint(int breakpoint) {
		Breakpoint bkpt = new Breakpoint(actor.getActions().get(0), breakpoint);
		for (Action action : actor.getActions()) {
			if ((action.getLocation().getStartLine() > bkpt.action.getLocation().getStartLine())
					&& (action.getLocation().getStartLine() < breakpoint)) {
				bkpt.action = action;
			}
		}
		// Add breakpoint to the list
		breakpoints.add(bkpt);
	}

	@Override
	public int step(boolean doStepInto) {
		try {
			if (currentAction == null) {
				if (isStepping == false) {
					currentAction = breakAction;
				} else {
					currentAction = getNextAction();
				}
				if (currentAction != null) {
					nbOfFirings++;
					lastVisitedAction = currentAction.getName();
					// Allocate local List variables
					for (Variable local : currentAction.getBody().getLocals()) {
						Type type = local.getType();
						if (type.isList()) {
							local.setValue(listAllocator.allocate(type));
						}
					}
					// Initialize stack frame
					nodeStack.add(new NodeInfo(0, currentAction.getBody()
							.getNodes().size(), currentAction.getBody()
							.getNodes(), null, null));
					nodeStackLevel = 1;
				}
			}
			if (currentAction != null) {
				while ((nodeStackLevel > 0) && (!popNodeStack()))
					;
				if (nodeStackLevel > 0) {
					return 2;
				} else {
					currentAction = null;
					return 1;
				}
			} else {
				return 0;
			}
		} catch (OrccRuntimeException ex) {
			throw new OrccRuntimeException("Runtime exception thrown by actor "
					+ actor.getName() + " :\n" + ex.getMessage());
		}
	}
}
