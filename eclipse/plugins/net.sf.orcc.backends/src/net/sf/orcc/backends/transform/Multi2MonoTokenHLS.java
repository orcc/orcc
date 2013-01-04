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

package net.sf.orcc.backends.transform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.backends.util.BackendUtil;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Tag;
import net.sf.orcc.df.Transition;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines a visitor that transforms multi-token to mono-token data
 * transfer
 * 
 * @author Khaled Jerbi
 * 
 */
public class Multi2MonoTokenHLS extends DfVisitor<Void> {

	/**
	 * This class defines a visitor that substitutes the peek from the port to
	 * the new buffer and changes the index from (index) to
	 * (index+writeIndex&maskValue)
	 * 
	 * @author Khaled Jerbi
	 * 
	 */
	private class ModifyActionScheduler extends AbstractIrVisitor<Object> {
		private Var buffer;
		private int bufferSize;
		private Port currentPort;
		private Var writeIndex;

		public ModifyActionScheduler(Var buffer, Var writeIndex,
				Port currentPort, int bufferSize) {
			this.buffer = buffer;
			this.writeIndex = writeIndex;
			this.currentPort = currentPort;
			this.bufferSize = bufferSize;
		}

		@Override
		public Object caseInstLoad(InstLoad load) {
			Var varSource = load.getSource().getVariable();
			Pattern pattern = EcoreHelper.getContainerOfType(varSource,
					Pattern.class);
			if (pattern != null) {
				Port testPort = pattern.getPort(varSource);
				if (currentPort.equals(testPort)) {
					// change tab Name
					load.getSource().setVariable(buffer);
					// change index --> writeIndex+index
					Expression maskValue = irFactory
							.createExprInt(bufferSize - 1);
					Expression index = irFactory.createExprVar(writeIndex);
					if (!load.getIndexes().isEmpty()) {
						Expression expression1 = load.getIndexes().get(0);
						Expression sum = irFactory.createExprBinary(
								expression1, OpBinary.PLUS, index,
								irFactory.createTypeInt(32));
						Expression mask = irFactory.createExprBinary(sum,
								OpBinary.BITAND, maskValue,
								irFactory.createTypeInt(32));

						load.getIndexes().add(mask);
					} else {
						Expression mask2 = irFactory.createExprBinary(index,
								OpBinary.BITAND, maskValue,
								irFactory.createTypeInt(32));
						load.getIndexes().add(mask2);
					}
				}
			}
			return null;
		}
	}

	/**
	 * This class defines a visitor that substitutes process variable names with
	 * those of the newly defined actions for InstStore
	 * 
	 * @author Khaled Jerbi
	 * 
	 */
	private class ModifyProcessActionStore extends AbstractIrVisitor<Object> {
		private int bufferSize;
		private Var tab;
		private Var writeIndex;

		public ModifyProcessActionStore(Var tab, Var writeIndex, int bufferSize) {
			this.tab = tab;
			this.writeIndex = writeIndex;
			this.bufferSize = bufferSize;
		}

		@Override
		public Object caseInstLoad(InstLoad load) {
			Var varSource = load.getSource().getVariable();
			Pattern pattern = EcoreHelper.getContainerOfType(varSource,
					Pattern.class);
			if (pattern != null) {
				Port testPort = pattern.getPort(varSource);
				if (port.equals(testPort)) {
					// change tab Name
					load.getSource().setVariable(tab);
					Expression indexInit = load.getIndexes().get(0);
					Expression indexFinal = irFactory.createExprBinary(
							indexInit, OpBinary.PLUS,
							irFactory.createExprVar(writeIndex),
							irFactory.createTypeInt(32));
					Expression exprMask = irFactory
							.createExprInt(bufferSize - 1);
					Expression maskValue = irFactory.createExprBinary(
							indexFinal, OpBinary.BITAND, exprMask,
							irFactory.createTypeInt(32));

					load.getIndexes().add(maskValue);
				}
			}
			return null;
		}
	}

	/**
	 * This class defines a visitor that substitutes process variable names with
	 * those of the newly defined actions for write
	 * 
	 * @author Khaled JERBI
	 * 
	 */
	private class ModifyProcessActionWrite extends AbstractIrVisitor<Object> {

		private Var tab;

		public ModifyProcessActionWrite(Var tab) {
			this.tab = tab;
		}

		@Override
		public Object caseInstLoad(InstLoad load) {
			Var varSource = load.getSource().getVariable();
			Pattern pattern = EcoreHelper.getContainerOfType(varSource,
					Pattern.class);
			if (pattern != null) {
				Port testPort = pattern.getPort(varSource);
				if (port.equals(testPort)) {
					// change tab Name
					load.getSource().setVariable(tab);
				}
			}
			return null;
		}

		@Override
		public Object caseInstStore(InstStore store) {
			Var varTarget = store.getTarget().getVariable();
			Pattern pattern = EcoreHelper.getContainerOfType(varTarget,
					Pattern.class);
			if (pattern != null) {
				Port testPort = pattern.getPort(varTarget);
				if (port.equals(testPort)) {
					// change tab Name
					store.getTarget().setVariable(tab);
				}
			}
			return null;
		}

	}

	private static DfFactory dfFactory = DfFactory.eINSTANCE;
	private static IrFactory irFactory = IrFactory.eINSTANCE;

	private List<Action> AddedUntaggedActions;
	private List<Integer> bufferSizes;
	// private int bufferSize;
	private Type entryType;
	private FSM fsm;
	private List<Var> inputBuffers;
	private List<Var> outputBuffers;
	private int inputIndex;
	private int outIndex;
	private List<Port> inputPorts;
	private List<Port> outputPorts;
	private Set<Action> noRepeatActions;
	private int numTokens;
	private int outputIndex;
	private Port port;
	private List<Var> readIndexes;
	private List<Var> outputReadIndexes;
	private List<Var> outputWriteIndexes;
	private boolean repeatInput;
	private List<Transition> transitionsList;
	private List<Action> visitedActions;
	private List<String> visitedActionsNames;
	private int visitedRenameIndex;
	private Action write;
	private List<Var> writeIndexes;

	/**
	 * returns the position of an action name in an actions names list
	 * 
	 * @param list
	 *            list of actions names
	 * @param seek
	 *            action researched action
	 * @return position of the seek action in the list
	 */
	private int actionNamePosition(List<String> list, String seekAction) {
		int position = 0;
		for (String action : list) {
			if (action.equals(seekAction)) {
				break;
			} else {
				position++;
			}
		}
		return position;
	}

	/**
	 * returns the position of an action name in an actions names list
	 * 
	 * @param list
	 *            list of actions names
	 * @param seek
	 *            action researched action
	 * @return position of the seek action in the list
	 */
	private int actionPosition(List<Action> list, String seekAction) {
		int position = 0;
		for (Action action : list) {
			if (action.getName().equals(seekAction)) {
				break;
			} else {
				position++;
			}
		}
		return position;
	}

	/**
	 * transforms the transformed action to a transition action
	 * 
	 * @param action
	 *            modified action
	 * @param buffer
	 *            current store buffer
	 * @param writeIndex
	 *            write index of the buffer
	 * @param readIndex
	 *            read index of the buffer
	 */
	private void actionToTransition(Port port, Action action, Var buffer,
			Var writeIndex, Var readIndex, int bufferSize) {
		ModifyActionScheduler modifyActionScheduler = new ModifyActionScheduler(
				buffer, writeIndex, port, bufferSize);
		modifyActionScheduler.doSwitch(action.getScheduler());
		modifyActionSchedulability(action, writeIndex, readIndex, OpBinary.GE,
				irFactory.createExprInt(numTokens), port);
	}

	@Override
	public Void caseActor(Actor actor) {
		this.actor = actor;
		inputIndex = 0;
		outIndex = 0;
		outputIndex = 0;
		visitedRenameIndex = 0;
		repeatInput = false;
		// bufferSize = 0;
		AddedUntaggedActions = new ArrayList<Action>();
		inputBuffers = new ArrayList<Var>();
		outputBuffers = new ArrayList<Var>();
		inputPorts = new ArrayList<Port>();
		outputPorts = new ArrayList<Port>();
		noRepeatActions = new HashSet<Action>();
		readIndexes = new ArrayList<Var>();
		outputReadIndexes = new ArrayList<Var>();
		outputWriteIndexes = new ArrayList<Var>();
		writeIndexes = new ArrayList<Var>();
		bufferSizes = new ArrayList<Integer>();
		visitedActions = new ArrayList<Action>();
		visitedActionsNames = new ArrayList<String>();
		transitionsList = new ArrayList<Transition>();
		modifyRepeatActionsInFSM();
		modifyUntaggedActions(actor);
		return null;
	}

	/**
	 * This method creates an action with the given name.
	 * 
	 * @param name
	 *            name of the action
	 * @return a new action created with the given name
	 */
	private Action createAction(Expression condition, String name) {
		Tag tag = dfFactory.createTag(name);

		// Scheduler building
		Procedure scheduler = irFactory.createProcedure(
				"isSchedulable_" + name, 0, irFactory.createTypeBool());
		BlockBasic blockScheduler = irFactory.createBlockBasic();
		Var result = scheduler.newTempLocalVariable(irFactory.createTypeBool(),
				"actionResult");
		result.setIndex(1);
		blockScheduler.add(irFactory.createInstAssign(result, condition));
		blockScheduler.add(irFactory.createInstReturn(irFactory
				.createExprVar(result)));
		scheduler.getBlocks().add(blockScheduler);

		// Body building ;-)
		Procedure body = irFactory.createProcedure(name, 0,
				irFactory.createTypeVoid());
		BlockBasic blockBody = irFactory.createBlockBasic();
		blockBody.add(irFactory.createInstReturn());
		body.getBlocks().add(blockBody);

		Action action = dfFactory.createAction(tag, dfFactory.createPattern(),
				dfFactory.createPattern(), dfFactory.createPattern(),
				scheduler, body);
		actor.getActions().add(action);
		return action;
	}

	/**
	 * This method creates the required InstStore, done, untagged and process
	 * actions
	 * 
	 * @param action
	 *            the action getting transformed
	 */
	private void createActionsSet(Action action, State sourceState,
			State targetState) {
		scanInputs(action, sourceState, targetState);
		scanOutputs(action, sourceState, targetState);
	}

	/**
	 * This method creates a global variable counter for store with the given
	 * name.
	 * 
	 * @param name
	 *            name of the counter
	 * @return new counter with the given name
	 */
	private Var createCounter(String name) {
		Var newCounter = irFactory.createVar(0, irFactory.createTypeInt(32),
				name, true, irFactory.createExprInt(0));

		Expression expression = irFactory.createExprInt(0);
		newCounter.setInitialValue(expression);
		if (!actor.getStateVars().contains(newCounter.getName())) {
			actor.getStateVars().add(newCounter);
		}
		return newCounter;
	}

	/**
	 * This method creates a global variable counter for data storing (writing)
	 * 
	 * @param name
	 *            name of the list
	 * @param numTokens
	 *            size of the list
	 * @param entryType
	 *            type of the list
	 * @return a global variable list
	 */
	private Var createTab(String name, Type entryType, int size) {
		Type type = irFactory.createTypeList(size, entryType);
		Var newList = irFactory.createVar(0, type, name, true);
		if (!actor.getStateVars().contains(newList.getName())) {
			actor.getStateVars().add(newList);
		}

		return newList;
	}

	/**
	 * creates an untagged action to store tokens
	 * 
	 * @param storeCounter
	 *            global variable counter
	 * @param storeList
	 *            global variable list to store
	 * @param priority
	 *            whether to put the untagged action as high priority or not
	 * @return new untagged action
	 */

	private Action createUntaggedAction(Var readIndex, Var writeIndex,
			Var storeList, Port port, boolean priority, int bufferSize) {
		Expression expression = irFactory.createExprBool(true);
		Action newUntaggedAction = createAction(expression,
				"untagged_" + port.getName());
		Var localINPUT = irFactory.createVar(0,
				irFactory.createTypeList(1, port.getType()), port.getName(),
				true, 0);

		defineUntaggedBody(readIndex, storeList, newUntaggedAction.getBody(),
				localINPUT, port, bufferSize);
		modifyActionSchedulability(newUntaggedAction, writeIndex, readIndex,
				OpBinary.LT, irFactory.createExprInt(bufferSize), port);
		Pattern pattern = newUntaggedAction.getInputPattern();
		pattern.setNumTokens(port, 1);
		pattern.setVariable(port, localINPUT);
		if (priority) {
			actor.getActionsOutsideFsm().add(0, newUntaggedAction);
		} else {
			actor.getActionsOutsideFsm().add(newUntaggedAction);
		}
		AddedUntaggedActions.add(newUntaggedAction);
		return newUntaggedAction;
	}

	/**
	 * This method creates the new write action
	 * 
	 * @param actionName
	 *            action name
	 * @param writeCounter
	 *            global variable write counter
	 * @param writeList
	 *            global variable write list
	 * @return new write action
	 */
	private Action createUntaggedWriteAction(String actionName,
			Var writeCounter, Var writeList, Var readIndex, Var writeIndex,
			int bufferSize) {
		String writeName = actionName + port.getName() + "_untaggedWrite";
		Expression expression = irFactory.createExprBool(true);
		Action newWriteAction = createAction(expression, writeName);

		Var OUTPUT = irFactory.createVar(0,
				irFactory.createTypeList(1, port.getType()), port.getName()
						+ "_OUTPUT", true, 0);
		defineWriteBody(writeCounter, writeList, newWriteAction.getBody(),
				OUTPUT, bufferSize);

		modifyActionSchedulability(newWriteAction, writeIndex, readIndex,
				OpBinary.GT, irFactory.createExprInt(1), port);
		// add output pattern
		Pattern pattern = newWriteAction.getOutputPattern();
		pattern.setVariable(port, OUTPUT);
		pattern.setNumTokens(port, 1);

		return newWriteAction;
	}

	/**
	 * This method creates the instructions for the body of the new untagged
	 * action
	 * 
	 * @param port
	 *            repeat port
	 * @param readCounter
	 *            global variable counter
	 * @param storeList
	 *            global store list
	 * @param body
	 *            new untagged action body
	 */
	private void defineUntaggedBody(Var readCounter, Var storeList,
			Procedure body, Var localINPUT, Port port, int bufferSize) {
		BlockBasic bodyBlock = body.getFirst();

		EList<Var> locals = body.getLocals();
		Var input = irFactory.createVar(0, port.getType(), port.getName()
				+ "_Input", true, 1);
		locals.add(input);
		List<Expression> load2Index = new ArrayList<Expression>(1);
		load2Index.add(irFactory.createExprInt(0));

		bodyBlock.add(irFactory.createInstLoad(input, localINPUT, load2Index));

		Var counter = irFactory.createVar(0, readCounter.getType(),
				port.getName() + "_Local_counter", true, 1);
		locals.add(counter);
		bodyBlock.add(irFactory.createInstLoad(counter, readCounter));

		Var mask = irFactory.createVar(0, irFactory.createTypeInt(32), "mask",
				true, 1);
		locals.add(mask);
		Expression maskValue = irFactory.createExprBinary(
				irFactory.createExprVar(counter), OpBinary.BITAND,
				irFactory.createExprInt(bufferSize - 1),
				irFactory.createTypeInt(32));
		bodyBlock.add(irFactory.createInstAssign(mask, maskValue));

		bodyBlock.add(irFactory.createInstStore(storeList, mask, input));

		Expression indexInc = irFactory.createExprBinary(
				irFactory.createExprVar(counter), OpBinary.PLUS,
				irFactory.createExprInt(1), readCounter.getType());
		bodyBlock.add(irFactory.createInstStore(readCounter, indexInc));
	}

	/**
	 * This method defines the instructions of the new write action body
	 * 
	 * @param writeCounter
	 *            global variable counter
	 * @param writeList
	 *            global variable list for write
	 * @param body
	 *            body of the new write action
	 */
	private void defineWriteBody(Var writeCounter, Var writeList,
			Procedure body, Var OUTPUT, int bufferSize) {
		BlockBasic bodyNode = body.getFirst();
		EList<Var> locals = body.getLocals();
		Var counter1 = irFactory.createVar(0, writeCounter.getType(),
				port.getName() + "_Local_writeCounter", true, outputIndex);
		locals.add(counter1);
		bodyNode.add(irFactory.createInstLoad(counter1, writeCounter));

		Var output = irFactory.createVar(0, port.getType(), port.getName()
				+ "_LocalOutput", true, outputIndex);
		locals.add(output);

		Var mask = irFactory.createVar(0, irFactory.createTypeInt(32), "mask",
				true, 1);
		locals.add(mask);
		Expression maskValue = irFactory.createExprBinary(
				irFactory.createExprVar(writeCounter), OpBinary.BITAND,
				irFactory.createExprInt(bufferSize - 1),
				irFactory.createTypeInt(32));
		bodyNode.add(irFactory.createInstAssign(mask, maskValue));
		List<Expression> load2Index = new ArrayList<Expression>(1);
		load2Index.add(irFactory.createExprVar(mask));
		bodyNode.add(irFactory.createInstLoad(output, writeList, load2Index));

		Expression assign2Value = irFactory.createExprBinary(
				irFactory.createExprVar(counter1), OpBinary.PLUS,
				irFactory.createExprInt(1), irFactory.createTypeInt(32));

		// locals.put(OUTPUT.getName(), OUTPUT);
		bodyNode.add(irFactory.createInstStore(OUTPUT, 0, output));
		bodyNode.add(irFactory.createInstStore(writeCounter, assign2Value));
	}

	/**
	 * this method changes the schedulability of the action accordingly to
	 * tokens disponibility in the buffer
	 * 
	 * @param writeIndex
	 *            write index of the buffer
	 * @param readIndex
	 *            read index of the buffer
	 */
	private void modifyActionSchedulability(Action action, Var writeIndex,
			Var readIndex, OpBinary op, Expression reference, Port port) {
		int index = 0;
		Procedure scheduler = action.getScheduler();
		BlockBasic bodyBlock = scheduler.getLast();
		EList<Var> locals = scheduler.getLocals();

		Var localRead = irFactory.createVar(0, irFactory.createTypeInt(32),
				"readIndex_" + port.getName() + "_" + inputIndex, true,
				inputIndex);
		locals.add(localRead);
		bodyBlock.add(index, irFactory.createInstLoad(localRead, readIndex));
		index++;

		Var localWrite = irFactory.createVar(0, irFactory.createTypeInt(32),
				"writeIndex_" + port.getName() + "_" + inputIndex, true,
				inputIndex);
		locals.add(localWrite);
		bodyBlock.add(index, irFactory.createInstLoad(localWrite, writeIndex));
		index++;

		Var diff = irFactory.createVar(0, irFactory.createTypeInt(32), "diff"
				+ port.getName() + "_" + inputIndex, true, inputIndex);
		locals.add(diff);
		Expression value = irFactory.createExprBinary(
				irFactory.createExprVar(localRead), OpBinary.MINUS,
				irFactory.createExprVar(localWrite),
				irFactory.createTypeInt(32));
		bodyBlock.add(index, irFactory.createInstAssign(diff, value));
		index++;

		Var conditionVar = irFactory.createVar(0, irFactory.createTypeBool(),
				"condition_" + port.getName(), true, inputIndex);
		locals.add(conditionVar);
		Expression value2 = irFactory.createExprBinary(
				irFactory.createExprVar(diff), op, reference,
				irFactory.createTypeBool());
		bodyBlock.add(index, irFactory.createInstAssign(conditionVar, value2));
		index++;

		Var myResult = irFactory.createVar(0, irFactory.createTypeBool(),
				"myResult_" + port.getName(), true, inputIndex);
		locals.add(myResult);
		int returnIndex = bodyBlock.getInstructions().size() - 1;
		InstReturn actionReturn = (InstReturn) bodyBlock.getInstructions().get(
				returnIndex);
		// VarLocal currentResult
		Expression e = irFactory.createExprBinary(actionReturn.getValue(),
				OpBinary.LOGIC_AND, irFactory.createExprVar(conditionVar),
				irFactory.createTypeBool());

		bodyBlock.add(returnIndex, irFactory.createInstAssign(myResult, e));
		actionReturn.setValue(irFactory.createExprVar(myResult));
	}

	/**
	 * this method transforms tagged actions in the FSM
	 * 
	 * @param actor
	 *            the actor containing the FSM to modify
	 */
	private void modifyRepeatActionsInFSM() {
		fsm = actor.getFsm();

		if (fsm == null) {
			List<Action> actions = new ArrayList<Action>(
					actor.getActionsOutsideFsm());

			// check repeats on all actions

			// ////////
			State initState = dfFactory.createState("init");
			// no FSM: simply visit all the actions
			setFsm(initState);
			for (Action action : actions) {
				if (!action.getTag().isEmpty()) {
					visitTransition(initState, initState, action);
				}
			}

		} else {
			// with an FSM: visits all transitions
			for (Transition transition : fsm.getTransitions()) {
				State source = transition.getSource();
				State target = transition.getTarget();
				Action action = transition.getAction();
				visitTransition(source, target, action);
			}
			if (!transitionsList.isEmpty()) {
				for (Transition t : transitionsList) {
					fsm.getTransitions().add(t);
				}
			}
		}
	}

	/**
	 * this method transforms untagged actions containing repeats
	 * 
	 * @param actor
	 *            the actor containing the untagged actions to modify
	 */
	private void modifyUntaggedActions(Actor actor) {
		List<Action> actions = new ArrayList<Action>(
				actor.getActionsOutsideFsm());
		for (Action action : actions) {
			// modify only untagged actions existing before transformation
			if (!AddedUntaggedActions.contains(action)) {
				scanUntaggedInputs(action);
				// scanUntaggedOutputs(action);
			}
		}
	}

	/**
	 * This method return the closest power of 2 of the maximum repeat value of
	 * a port
	 * 
	 * @param action
	 *            action containing the port
	 * @param port
	 *            repeat port
	 * @return optimal buffer size
	 */
	private int OptimalBufferSize(Port port) {
		int size = 0;
		int optimalSize = 0;
		List<Action> actions = new ArrayList<Action>(actor.getActions());
		for (Action action : actions) {
			for (Entry<Port, Integer> entry : action.getInputPattern()
					.getNumTokensMap().entrySet()) {
				if (entry.getKey() == port) {
					if (entry.getValue() > size) {
						size = entry.getValue();
					}
				}
			}
		}
		optimalSize = BackendUtil.closestPow_2(size);
		return optimalSize;
	}

	/**
	 * This method return the closest power of 2 of the maximum repeat value of
	 * a port
	 * 
	 * @param action
	 *            action containing the port
	 * @param port
	 *            repeat port
	 * @return optimal buffer size
	 */
	private int OptimalOutputBufferSize(Port port) {
		int size = 0;
		int optimalSize = 0;
		List<Action> actions = new ArrayList<Action>(actor.getActions());
		for (Action action : actions) {
			for (Entry<Port, Integer> entry : action.getOutputPattern()
					.getNumTokensMap().entrySet()) {
				if (entry.getKey() == port) {
					if (entry.getValue() > size) {
						size = entry.getValue();
					}
				}
			}
		}
		optimalSize = BackendUtil.closestPow_2(size);
		return optimalSize;
	}

	/**
	 * returns the position of a port in a port list
	 * 
	 * @param list
	 *            list of ports
	 * @param seekPort
	 *            researched port
	 * @return position of a port in a list
	 */
	private int portPosition(List<Port> list, Port seekPort) {
		int position = 0;
		for (Port inputPort : list) {
			if (inputPort == seekPort) {
				break;
			} else {
				position++;
			}
		}
		return position;
	}

	/**
	 * Creates the new required actions for every input of the action.
	 * 
	 * @param action
	 *            action to transform
	 * @param sourceName
	 *            name of the source state of the action in the FSM
	 * @param targetName
	 *            name of the target state of the action in the FSM
	 */
	private void scanInputs(Action action, State sourceState, State targetState) {

		for (Entry<Port, Integer> entry : action.getInputPattern()
				.getNumTokensMap().entrySet()) {

			Var untagBuffer = irFactory.createVar(0, entryType, "buffer", true);
			Var untagReadIndex = irFactory.createVar(0,
					irFactory.createTypeInt(32), "UntagReadIndex", true,
					irFactory.createExprInt(0));
			Var untagWriteIndex = irFactory.createVar(0,
					irFactory.createTypeInt(32), "UntagWriteIndex", true,
					irFactory.createExprInt(0));

			numTokens = entry.getValue();
			inputIndex = inputIndex + 100;
			port = entry.getKey();
			int bufferSize = OptimalBufferSize(port);
			entryType = port.getType();

			if (inputPorts.contains(port)) {
				int position = portPosition(inputPorts, port);
				untagBuffer = inputBuffers.get(position);
				untagReadIndex = readIndexes.get(position);
				untagWriteIndex = writeIndexes.get(position);
				bufferSize = bufferSizes.get(position);
			} else {
				inputPorts.add(port);
				bufferSizes.add(bufferSize);
				untagBuffer = createTab(port.getName() + "_buffer", entryType,
						bufferSize);
				inputBuffers.add(untagBuffer);
				untagReadIndex = createCounter("readIndex_" + port.getName());
				readIndexes.add(untagReadIndex);
				untagWriteIndex = createCounter("writeIndex_" + port.getName());
				writeIndexes.add(untagWriteIndex);
				createUntaggedAction(untagReadIndex, untagWriteIndex,
						untagBuffer, port, true, bufferSize);
			}

			Procedure body = action.getBody();
			Var index = body.newTempLocalVariable(irFactory.createTypeInt(32),
					"writeIndex");
			index.setIndex(1);
			body.getFirst().add(
					irFactory.createInstLoad(index, untagWriteIndex));
			ModifyProcessActionStore modifyProcessAction = new ModifyProcessActionStore(
					untagBuffer, untagWriteIndex, bufferSize);
			modifyProcessAction.doSwitch(action.getBody());
			actionToTransition(port, action, untagBuffer, untagWriteIndex,
					untagReadIndex, bufferSize);
			Expression value = irFactory.createExprBinary(
					irFactory.createExprVar(index), OpBinary.PLUS,
					irFactory.createExprInt(numTokens),
					irFactory.createTypeInt(32));
			body.getLast().add(
					irFactory.createInstStore(untagWriteIndex, value));

		}

		action.getInputPattern().clear();
	}

	/**
	 * Creates the new required actions for every output of the action
	 * 
	 * @param action
	 *            action to transform
	 * @param sourceName
	 *            name of the source state of the action in the actor fsm
	 * @param targetName
	 *            name of the target state of the action in the actor fsm
	 */
	private void scanOutputs(Action action, State sourceState, State targetState) {
		for (Entry<Port, Integer> entry : action.getOutputPattern()
				.getNumTokensMap().entrySet()) {

			Var untagBuffer = irFactory.createVar(0, entryType, "outputBuffer",
					true);
			Var untagReadIndex = irFactory.createVar(0,
					irFactory.createTypeInt(32), "outputUntagReadIndex", true,
					irFactory.createExprInt(0));
			Var untagWriteIndex = irFactory.createVar(0,
					irFactory.createTypeInt(32), "outputUntagWriteIndex", true,
					irFactory.createExprInt(0));

			numTokens = entry.getValue();
			outIndex = outIndex + 100;
			port = entry.getKey();
			int bufferSize = OptimalOutputBufferSize(port);
			entryType = port.getType();

			if (outputPorts.contains(port)) {
				int position = portPosition(outputPorts, port);
				untagBuffer = outputBuffers.get(position);
				untagReadIndex = outputReadIndexes.get(position);
				untagWriteIndex = outputWriteIndexes.get(position);
				bufferSize = bufferSizes.get(position);
			} else {
				outputPorts.add(port);
				bufferSizes.add(bufferSize);
				untagBuffer = createTab(port.getName() + "_outputBuffer",
						entryType, bufferSize);
				outputBuffers.add(untagBuffer);
				untagReadIndex = createCounter("outputReadIndex_"
						+ port.getName());
				outputReadIndexes.add(untagReadIndex);
				untagWriteIndex = createCounter("outputWriteIndex_"
						+ port.getName());
				outputWriteIndexes.add(untagWriteIndex);

				write = createUntaggedWriteAction(action.getName(),
						untagWriteIndex, untagBuffer, untagReadIndex,
						untagWriteIndex, bufferSize);
				write.getOutputPattern().setNumTokens(port, 1);
			}

			Procedure body = action.getBody();
			Var index = body.newTempLocalVariable(irFactory.createTypeInt(32),
					"outputWriteIndex");
			index.setIndex(1);
			body.getFirst().add(
					irFactory.createInstLoad(index, untagWriteIndex));
			outputIndex = outputIndex + 100;
			String listName = action.getName() + "NewWriteList" + outputIndex;
			Var tab = createTab(listName, entryType, numTokens);
			ModifyProcessActionWrite modifyProcessActionWrite = new ModifyProcessActionWrite(
					tab);
			modifyProcessActionWrite.doSwitch(action.getBody());

			dataTransfer(numTokens, untagReadIndex, tab, bufferSize,
					untagBuffer, action.getBody());

		}
		// remove outputPattern from transition action
		action.getOutputPattern().clear();
	}

	/**
	 * This function creates a for loop to move date from newlist to the
	 * untagged buffer
	 * 
	 * @param numTokens
	 *            number of loop cycles
	 * @param untagReadIndex
	 *            global read index
	 * @param tab
	 *            source tab
	 * @param bufferSize
	 *            used for the mask
	 * @param untagbuffer
	 *            target tab
	 * @param body
	 *            procedure where to add the loop
	 */
	private void dataTransfer(int numTokens, Var untagReadIndex, Var tab,
			int bufferSize, Var untagBuffer, Procedure body) {
		
		// create and initialize loop counter
		BlockBasic initCounterBlock = irFactory.createBlockBasic();
		Var localWriteIndex = body.newTempLocalVariable(
				irFactory.createTypeInt(32), "loopCounter");
		initCounterBlock.add(irFactory.createInstAssign(localWriteIndex, irFactory.createExprInt(0)));
		body.getBlocks().add(initCounterBlock);
		// create while block
		BlockWhile whileBlock = irFactory.createBlockWhile();
		// create and set while condition
		BlockBasic insideBlock = irFactory.createBlockBasic();
		Expression condition = irFactory.createExprBinary(
				irFactory.createExprVar(localWriteIndex), OpBinary.LT,
				irFactory.createExprInt(numTokens), irFactory.createTypeBool());
		whileBlock.setCondition(condition);

		// create then node
		Var tmpVar = body.newTempLocalVariable(irFactory.createTypeInt(32),
				"tmpVar");
		List<Expression> loadIndex = new ArrayList<Expression>(1);
		loadIndex.add(irFactory.createExprVar(localWriteIndex));
		insideBlock.add(irFactory.createInstLoad(tmpVar, tab, loadIndex));

		Expression maskValue = irFactory.createExprBinary(
				irFactory.createExprVar(untagReadIndex), OpBinary.BITAND,
				irFactory.createExprInt(bufferSize - 1),
				irFactory.createTypeInt(32));
		Expression index = irFactory.createExprBinary(maskValue, OpBinary.PLUS,
				irFactory.createExprVar(localWriteIndex),
				irFactory.createTypeInt(32));
		List<Expression> load2Index = new ArrayList<Expression>(1);
		load2Index.add(index);
		insideBlock.add(irFactory.createInstStore(untagBuffer, load2Index,
				irFactory.createExprVar(tmpVar)));
		whileBlock.getBlocks().add(insideBlock);
		body.getBlocks().add(whileBlock);

		BlockBasic incrementBlock = irFactory.createBlockBasic();
		Expression increment = irFactory
				.createExprBinary(irFactory.createExprVar(untagReadIndex),
						OpBinary.PLUS, irFactory.createExprInt(numTokens),
						irFactory.createTypeInt(32));
		incrementBlock.add(irFactory
				.createInstAssign(untagReadIndex, increment));
		body.getBlocks().add(incrementBlock);
	}

	/**
	 * Visits the inputs of an untagged action to check repeats
	 * 
	 * @param action
	 *            action containing the inputs to check
	 */
	private void scanUntaggedInputs(Action action) {
		for (Entry<Port, Integer> verifEntry : action.getInputPattern()
				.getNumTokensMap().entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			Port verifPort = verifEntry.getKey();
			Type entryType = verifPort.getType();
			int bufferSize = OptimalBufferSize(verifPort);
			if (inputPorts.contains(verifPort)) {
				int position = portPosition(inputPorts, verifPort);
				Var buffer = inputBuffers.get(position);
				Var writeIndex = writeIndexes.get(position);
				Var readIndex = readIndexes.get(position);
				bufferSize = bufferSizes.get(position);
				ModifyActionScheduler modifyActionScheduler = new ModifyActionScheduler(
						buffer, writeIndex, verifPort, bufferSize);
				modifyActionScheduler.doSwitch(action.getBody());
				modifyActionScheduler.doSwitch(action.getScheduler());
				modifyActionSchedulability(action, writeIndex, readIndex,
						OpBinary.GE, irFactory.createExprInt(verifNumTokens),
						verifPort);
				updateUntagIndex(action, writeIndex, verifNumTokens);
				action.getInputPattern().remove(verifPort);
			} else {
				if (verifNumTokens > 1) {
					Var untagBuffer = irFactory.createVar(0, entryType,
							"buffer", true);
					Var untagReadIndex = irFactory.createVar(0,
							irFactory.createTypeInt(32), "UntagReadIndex",
							true, irFactory.createExprInt(0));
					Var untagWriteIndex = irFactory.createVar(0,
							irFactory.createTypeInt(32), "UntagWriteIndex",
							true, irFactory.createExprInt(0));
					inputPorts.add(verifPort);
					untagBuffer = createTab(verifPort.getName() + "_buffer",
							entryType, bufferSize);
					inputBuffers.add(untagBuffer);
					untagReadIndex = createCounter("readIndex_"
							+ verifPort.getName());
					readIndexes.add(untagReadIndex);
					untagWriteIndex = createCounter("writeIndex_"
							+ verifPort.getName());
					writeIndexes.add(untagWriteIndex);
					bufferSizes.add(bufferSize);
					createUntaggedAction(untagReadIndex, untagWriteIndex,
							untagBuffer, verifPort, false, bufferSize);

					ModifyActionScheduler modifyActionScheduler = new ModifyActionScheduler(
							untagBuffer, untagWriteIndex, verifPort, bufferSize);
					modifyActionScheduler.doSwitch(action.getBody());
					modifyActionScheduler.doSwitch(action.getScheduler());
					modifyActionSchedulability(action, untagWriteIndex,
							untagReadIndex, OpBinary.GE,
							irFactory.createExprInt(verifNumTokens), verifPort);
					updateUntagIndex(action, untagWriteIndex, verifNumTokens);
					action.getInputPattern().remove(verifPort);
				}
			}
		}
		AddedUntaggedActions.add(action);
	}

	/**
	 * Visits the outputs of an untagged action to check repeats (FIXME: not
	 * used because it deals with a very rare case of untagged actions having
	 * multi-token on their inputs and outputs)
	 * 
	 * @param action
	 *            action containing the outputs to check
	 */
	@SuppressWarnings("unused")
	private void scanUntaggedOutputs(Action action) {
		for (Entry<Port, Integer> verifEntry : action.getOutputPattern()
				.getNumTokensMap().entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			if (verifNumTokens > 1) {
				for (Entry<Port, Integer> entry : action.getOutputPattern()
						.getNumTokensMap().entrySet()) {
					numTokens = entry.getValue();
					Port verifPort = entry.getKey();
					String name = "TokensToSend" + verifPort.getName();
					Var tokensToSend = createCounter(name);
					Expression condition = irFactory.createExprBinary(
							irFactory.createExprVar(tokensToSend), OpBinary.GT,
							irFactory.createExprInt(0),
							irFactory.createTypeBool());
					String actionName = "untaggedWrite_" + verifPort.getName();
					Action untaggedWrite = createAction(condition, actionName);
					Pattern pattern = untaggedWrite.getOutputPattern();
					pattern.setNumTokens(verifPort, 1);
					Var OUTPUT = irFactory.createVar(0, verifPort.getType(),
							verifPort.getName() + "OUTPUT", true, 0);
					pattern.setVariable(verifPort, OUTPUT);
					// add instruction: tokensToSend = tokensToSend - 1 ;
					Var numTokenToSend = irFactory.createVar(0,
							irFactory.createTypeInt(32), "numTokensToSend",
							true, 0);
					BlockBasic untaggedBlkNode = untaggedWrite.getBody()
							.getLast();
					untaggedBlkNode.add(irFactory.createInstLoad(
							numTokenToSend, tokensToSend));
					Expression value = irFactory.createExprBinary(
							irFactory.createExprVar(numTokenToSend),
							OpBinary.MINUS, irFactory.createExprInt(1),
							irFactory.createTypeInt(32));
					untaggedBlkNode.add(irFactory.createInstStore(tokensToSend,
							value));
					// add untagged action in high priority
					actor.getActionsOutsideFsm().add(0, untaggedWrite);
					// add write condition to untagged action
					BlockBasic blkNode = action.getBody().getLast();
					blkNode.add(irFactory.createInstStore(tokensToSend,
							numTokens));
				}
			}
		}
		AddedUntaggedActions.add(action);
	}

	/**
	 * Adds an FSM to an actor if it has not already
	 * 
	 * @param initialState
	 *            initial state of the new FSM
	 */
	private void setFsm(State initialState) {
		fsm = dfFactory.createFSM();
		fsm.getStates().add(initialState);
		fsm.setInitialState(initialState);
		for (Action action : actor.getActionsOutsideFsm()) {
			fsm.addTransition(initialState, action, initialState);
		}

		actor.getActionsOutsideFsm().clear();
		actor.setFsm(fsm);
	}

	/**
	 * if an already transformed action is reused in an another FSM transition,
	 * this method uses the transformed action to connect it to the current FSM
	 * transition
	 * 
	 * @param action
	 * @param source
	 * @param target
	 */
	private void updateFSM(Action action, Action oldAction, State source,
			State target) {
		List<Action> actions = actor.getActions();
		for (Entry<Port, Integer> verifEntry : action.getInputPattern()
				.getNumTokensMap().entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			if (verifNumTokens > 1) {
				repeatInput = true;
				Transition transition = dfFactory.createTransition(source,
						oldAction, target);
				transitionsList.add(transition);
				visitedRenameIndex++;
				break;
			}
			inputIndex = 0;
		}

		for (Entry<Port, Integer> verifEntry : action.getOutputPattern()
				.getNumTokensMap().entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			if (verifNumTokens > 1) {

				String updateWriteName = "newStateWrite" + action.getName()
						+ visitedRenameIndex;
				State writeState = dfFactory.createState(updateWriteName);
				fsm.getStates().add(writeState);
				// create new process action if not created while treating
				// inputs
				fsm.replaceTarget(source, oldAction, writeState);
				oldAction.getOutputPattern().clear();

				visitedRenameIndex++;
				for (Entry<Port, Integer> entry : action.getOutputPattern()
						.getNumTokensMap().entrySet()) {
					outputIndex = outputIndex + 100;
					port = entry.getKey();

					String writeName = action.getName() + port.getName()
							+ "_NewWrite";
					int writeIndex = actionPosition(actions, writeName);
					Action write = actions.get(writeIndex);
					Transition writeTransition = dfFactory.createTransition(
							writeState, write, writeState);
					transitionsList.add(writeTransition);

					// create a new write done action once
					if (outputIndex == 100) {
						String doneName = action.getName() + "newWriteDone";
						int doneIndex = actionPosition(actions, doneName);
						Action done = actions.get(doneIndex);
						Transition doneTransition = dfFactory.createTransition(
								writeState, done, target);
						transitionsList.add(doneTransition);
					}

				}
				break;
			}
			outputIndex = 0;
		}
		// repeatInput = false;
	}

	/**
	 * Updates the write index of the buffer after reading tokens
	 * 
	 * @param action
	 *            untagged action to change
	 * @param writeIndex
	 *            index to update
	 * @param numTokens
	 *            number of tokens read from the buffer
	 */
	private void updateUntagIndex(Action action, Var writeIndex, int numTokens) {
		BlockBasic basicBlock = action.getBody().getLast();
		Var localWriteIndex = action.getBody().newTempLocalVariable(
				irFactory.createTypeInt(32), "localWriteIndex");
		basicBlock.add(irFactory.createInstLoad(localWriteIndex, writeIndex));
		Expression value = irFactory
				.createExprBinary(irFactory.createExprVar(localWriteIndex),
						OpBinary.PLUS, irFactory.createExprInt(numTokens),
						irFactory.createTypeInt(32));
		basicBlock.add(irFactory.createInstStore(writeIndex, value));
	}

	/**
	 * Checks if the action has already been transformed. in that case the
	 * non-transformed action is reconsidered
	 * 
	 * @param action
	 *            action to be checked
	 */
	private void verifVisitedActions(Action action, State source, State target) {
		String actionName = action.getName();
		if (visitedActionsNames.isEmpty()) {
			// fill lists the first time
			visitedActionsNames.add(actionName);
			visitedActions.add(IrUtil.copy(action));
			createActionsSet(action, source, target);
		} else {
			if (visitedActionsNames.contains(actionName)) {
				// if action is visited then it is replaced by not transformed
				// action
				visitedRenameIndex++;
				int visitedIndex = actionNamePosition(visitedActionsNames,
						actionName);
				Action updateAction = visitedActions.get(visitedIndex);
				updateFSM(updateAction, action, source, target);
			} else {
				visitedActionsNames.add(actionName);
				visitedActions.add(IrUtil.copy(action));
				createActionsSet(action, source, target);
			}
		}
	}

	/**
	 * Visits a transition characterized by its source name, target name and
	 * action
	 * 
	 * @param sourceName
	 *            source state
	 * @param targetName
	 *            target state
	 * @param action
	 *            action of the transition
	 */
	private void visitTransition(State sourceState, State targetState,
			Action action) {
		// verify if the action is already transformed ==> update FSM
		verifVisitedActions(action, sourceState, targetState);

		if (!repeatInput) {
			noRepeatActions.add(action);
		}
		repeatInput = false;
	}
}