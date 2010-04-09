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
import java.util.Map.Entry;

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

	private ConstantEvaluator constEval;

	/**
	 * Debugger utils
	 */
	public class NodeInfo {
		public int subNodeIdx;
		public int nbSubNodes;
		public List<CFGNode> subNodes;
		public BlockNode joinNode;
		public Expression condition;

		public NodeInfo(int subNodeIdx, int nbSubNodes, List<CFGNode> subNodes,
				BlockNode joinNode, Expression condition) {
			this.subNodeIdx = subNodeIdx;
			this.nbSubNodes = nbSubNodes;
			this.subNodes = subNodes;
			this.joinNode = joinNode;
			this.condition = condition;
		}
	}

	private Action currentAction = null;
	private List<NodeInfo> nodeStack;
	private int nodeStackLevel;
	private List<Instruction> instrStack;

	/**
	 * Simulator properties
	 */
	protected String fsmState;

	protected NodeInterpreter interpret;
	protected boolean isSynchronousScheduler = false;

	protected ExpressionEvaluator exprInterpreter;
	private ListAllocator listAllocator;
	protected ActionScheduler sched;

	/**
	 * Creates a new interpreted actor with the given instance id and the given
	 * actor
	 * 
	 * @param id
	 *            id of the instance of the given actor
	 * @param actor
	 *            an actor
	 */
	public InterpretedActor(String id, Actor actor) {
		super(id, actor);
		sched = actor.getActionScheduler();
		if (sched.hasFsm()) {
			this.fsmState = sched.getFsm().getInitialState().getName();
		} else {
			fsmState = "IDLE";
		}

		// Create the List allocator for state and procedure local vars
		listAllocator = new ListAllocator();
		// Create the expression evaluator
		exprInterpreter = new ExpressionEvaluator();
		// Create lists for nodes and instructions in case of debug mode
		nodeStack = new ArrayList<NodeInfo>();
		instrStack = new ArrayList<Instruction>();

		// Build a node interpreter for visiting CFG and instructions
		interpret = new NodeInterpreter(name);

		// Creates an expression evaluator for state and local variables init
		this.constEval = new ConstantEvaluator();
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
			boolean freeOutput = true;
			for (Entry<Port, Integer> entry : outputPattern.entrySet()) {
				Port outputPort = entry.getKey();
				Integer nbOfTokens = entry.getValue();
				freeOutput &= outputPort.fifo().hasRoom(nbOfTokens);
			}

			return freeOutput;
		} else {
			return true;
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

	/**
	 * Launch initializing actions of this actor.
	 */
	@Override
	public void initialize() {
		// Check for List state variables which need to be allocated or
		// initialized
		for (Variable stateVar : actor.getStateVars()) {
			Type type = stateVar.getType();
			// Initialize variables with constant values
			Constant initConst = ((StateVariable) stateVar).getConstantValue();
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
			node.accept(interpret);
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
	public Integer schedule() {
		if (isSynchronousScheduler) {
			// "Synchronous-like" scheduling policy : schedule only 1 action per
			// actor at each "schedule" (network logical cycle) call
			Action action = getNextAction();
			if (action != null) {
				return execute(action);
			} else {
				return 0;
			}

		} else {
			// Other scheduling policy : schedule the actor as long as it can
			// execute an action
			int running = 0;
			boolean schedulable = true;
			if (sched.hasFsm()) {
				while (schedulable) {
					schedulable = false;
					// Check for untagged actions first
					for (Action action : sched.getActions()) {
						schedulable = isSchedulable(action);
						if (schedulable) {
							if (checkOutputPattern(action.getOutputPattern())) {
								running = execute(action);
							} else {
								schedulable = false;
							}
							break;
						}
					}
					if (!schedulable) {
						// If no untagged action has been executed,
						// Then check for next FSM transition
						for (NextStateInfo info : sched.getFsm()
								.getTransitions(fsmState)) {
							Action action = info.getAction();
							schedulable = isSchedulable(action);
							if (schedulable) {
								if (checkOutputPattern(action
										.getOutputPattern())) {
									// Update FSM state
									fsmState = info.getTargetState().getName();
									running = execute(action);
								} else {
									schedulable = false;
								}
								break;
							}
						}
					}
				}
			} else {
				while (schedulable) {
					schedulable = false;
					for (Action action : sched.getActions()) {
						schedulable = isSchedulable(action);
						if (schedulable) {
							if (checkOutputPattern(action.getOutputPattern())) {
								running = execute(action);
							} else {
								schedulable = false;
							}
							break;
						}
					}
				}
			}
			return running;
		}
	}

	private void popNodeStack() {
		NodeInfo node = nodeStack.get(nodeStackLevel - 1);
		if (node.nbSubNodes > 0) {
			if (node.subNodeIdx == node.nbSubNodes) {
				if ((node.condition != null)
						&& ((Boolean) node.condition.accept(exprInterpreter))) {
					node.subNodeIdx = 0;
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
				} else if (subNode instanceof WhileNode) {
					Expression condition = ((WhileNode) subNode).getValue();
					if ((Boolean) condition.accept(exprInterpreter)) {
						nodeStack.add(new NodeInfo(0, ((WhileNode) subNode)
								.getNodes().size(), ((WhileNode) subNode)
								.getNodes(), null, condition));
						nodeStackLevel++;
					}
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
				instr.accept(interpret);
				lastVisitedLocation = instr.getLocation();
			}
			if (instrStack.size() == 0) {
				nodeStack.remove(nodeStackLevel - 1);
				nodeStackLevel--;
			}
		}
	}

	@Override
	public int step(boolean doStepInto) {
		if (currentAction == null) {
			currentAction = getNextAction();
			if (currentAction != null) {
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
						.getNodes().size(), currentAction.getBody().getNodes(),
						null, null));
				nodeStackLevel = 1;
			}
		}
		if (currentAction != null) {
			if (nodeStackLevel > 0) {
				popNodeStack();
				return 0;
			} else {
				currentAction = null;
				return 1;
			}
		} else {
			return -1;
		}
	}
}
