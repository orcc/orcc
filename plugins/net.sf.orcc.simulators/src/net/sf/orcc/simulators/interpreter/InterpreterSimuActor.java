/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.simulators.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.interpreter.ActorInterpreter;
import net.sf.orcc.interpreter.ListAllocator;
import net.sf.orcc.interpreter.NodeInterpreter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.plugins.simulators.Simulator.DebugStackFrame;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.simulators.SimuActor;

public class InterpreterSimuActor extends AbstractInterpreterSimuActor
		implements SimuActor {

	private OrccProcess process;

	private String instanceId;
	private Actor actorIR;

	private Map<String, Fifo> fifos;

	/**
	 * Debugger utils
	 */
	private class Breakpoint {
		public Action action;

		public int lineNb;

		public Breakpoint(Action action, Integer lineNb) {
			this.action = action;
			this.lineNb = lineNb;
		}
	}

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

	private Action breakAction = null;
	private List<Breakpoint> breakpoints;
	private Action currentAction = null;
	private List<Instruction> instrStack;
	private List<NodeInfo> nodeStack;
	private int nodeStackLevel;
	private boolean isStepping = false;

	/**
	 * Interpretation and evaluation tools
	 */
	private ActorInterpreter actorInterpreter;
	protected ExpressionEvaluator exprEvaluator;
	private ListAllocator listAllocator;

	/**
	 * Constructor
	 * 
	 * @param instanceId
	 *            : name of the instance of the Actor model
	 * @param actorParameters
	 *            : map of actor's parameters values to be set at init
	 * @param actorIR
	 *            : duplicated intermediate representation of the Actor model to
	 *            be directly used/modified for the interpretation
	 * @param tracesFolder
	 *            : the defined folder for tracing simulation information into
	 *            output files
	 * @param process
	 *            : the master OrccProcess (can be used for write to output
	 *            console
	 */
	public InterpreterSimuActor(String instanceId,
			Map<String, Expression> actorParameters, Actor actorIR,
			String outputFolder, OrccProcess process) {
		this.process = process;

		this.instanceId = instanceId;
		this.actorIR = actorIR;

		this.fifos = new HashMap<String, Fifo>();

		// Create lists for nodes and instructions in case of debug mode
		this.nodeStack = new ArrayList<NodeInfo>();
		this.instrStack = new ArrayList<Instruction>();
		this.breakpoints = new ArrayList<Breakpoint>();
		this.nbOfFirings = 0;

		this.listAllocator = new ListAllocator();

		// Create an Actor interpreter that is able to interpret simulated
		// actor's instance according to a visitor design pattern
		this.actorInterpreter = new ActorInterpreter(actorParameters, actorIR,
				process);
		// Create the expression evaluator
		this.exprEvaluator = new ExpressionEvaluator();
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
	}

	@Override
	public String getActorName() {
		return actorIR.getName();
	}

	@Override
	public String getFileName() {
		return actorIR.getFile();
	}
	
	@Override
	public String getInstanceId() {
		return instanceId;
	}

	@Override
	public DebugStackFrame getStackFrame() {
		DebugStackFrame stackFrame = new DebugStackFrame();
		stackFrame.actorFilename = actorIR.getFile();
		stackFrame.codeLine = lastVisitedLocation.getStartLine();
		stackFrame.nbOfFirings = nbOfFirings;
		stackFrame.stateVars.clear();
		for (Variable stateVar : actorIR.getStateVars()) {
			stackFrame.stateVars.put(stateVar.getName(), stateVar.getValue());
		}
		stackFrame.currentAction = lastVisitedAction;
		stackFrame.fsmState = actorInterpreter.getFsmState();
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

	@Override
	public void initialize() {
		actorInterpreter.initialize();
		actorInterpreter.setFifos(fifos);
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
						&& ((Boolean) node.condition.accept(exprEvaluator))) {
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
							exprEvaluator);
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
					if ((Boolean) condition.accept(exprEvaluator)) {
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
				instr.accept(new NodeInterpreter(), fifos, process);
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
	public int runAllSchedulableAction() {
		try {
			// Skip execution if currently stepping
			if (isStepping) {
				return 0;
			}
			// "Round-robbin-like" scheduling policy : schedule only all
			// schedulable
			// action of an
			// actor before returning
			int nbOfFiredActions = 0;
			Action action;
			while ((action = actorInterpreter.getNextAction()) != null) {
				for (Breakpoint bkpt : breakpoints) {
					if (action == bkpt.action) {
						breakAction = action;
						return -2;
					}
				}
				nbOfFiredActions += actorInterpreter.execute(action);
				nbOfFirings += nbOfFiredActions;
				return nbOfFiredActions;
			}
			return 0;
		} catch (OrccRuntimeException ex) {
			throw new OrccRuntimeException("Runtime exception thrown by actor "
					+ actorIR.getName() + " :\n" + ex.getMessage());
		}
	}

	@Override
	public int runNextSchedulableAction() {
		try {
			// Skip execution if currently stepping
			if (isStepping) {
				return 0;
			}
			// "Synchronous-like" scheduling policy : schedule only 1 action per
			// actor at each "schedule" (network logical cycle) call
			Action action = actorInterpreter.getNextAction();
			if (action != null) {
				for (Breakpoint bkpt : breakpoints) {
					if (action == bkpt.action) {
						breakAction = action;
						return -2;
					}
				}
				return actorInterpreter.execute(action);
			} else {
				return 0;
			}
		} catch (OrccRuntimeException ex) {
			throw new OrccRuntimeException("Runtime exception thrown by actor "
					+ actorIR.getName() + " :\n" + ex.getMessage());
		}
	}

	@Override
	public void setBreakpoint(int breakpoint) {
		Breakpoint bkpt = new Breakpoint(actorIR.getActions().get(0),
				breakpoint);
		for (Action action : actorIR.getActions()) {
			if (action.getLocation().getStartLine() <= breakpoint) {
				if (action.getLocation().getStartLine() > bkpt.action
						.getLocation().getStartLine()) {
					bkpt.action = action;
				}
			}
		}
		// Add breakpoint to the list
		breakpoints.add(bkpt);
	}

	@Override
	public void setFifo(Port port, Fifo fifo) {
		fifos.put(port.getName(), fifo);
	}

	@Override
	public int step(boolean stepInto) {
		try {
			if (currentAction == null) {
				if (isStepping == false) {
					currentAction = breakAction;
				} else {
					currentAction = actorInterpreter.getNextAction();
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
					+ actorIR.getName() + " :\n" + ex.getMessage());
		}
	}

}
