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

package net.sf.orcc.backends.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.State;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.EcoreHelper;
import net.sf.orcc.util.UniqueEdge;

import org.eclipse.emf.common.util.EList;
import org.jgrapht.DirectedGraph;

/**
 * This class defines a visitor that transforms multi-token to mono-token data
 * transfer
 * 
 * @author Khaled Jerbi
 * 
 */
public class Multi2MonoToken extends AbstractActorVisitor<Object> {

	IrFactory factory = IrFactory.eINSTANCE;

	/**
	 * This class defines a visitor that substitutes the peek from the port to
	 * the new buffer and changes the index from (index) to
	 * (index+writeIndex&maskValue)
	 * 
	 * @author Khaled Jerbi
	 * 
	 */
	private class ModifyActionScheduler extends AbstractActorVisitor<Object> {
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
					Expression maskValue = factory
							.createExprInt(bufferSize - 1);
					Expression index = factory.createExprVar(writeIndex);
					if (!load.getIndexes().isEmpty()) {
						Expression expression1 = load.getIndexes().get(0);
						Expression sum = factory
								.createExprBinary(expression1, OpBinary.PLUS,
										index, factory.createTypeInt(32));
						Expression mask = factory.createExprBinary(sum,
								OpBinary.BITAND, maskValue,
								factory.createTypeInt(32));

						load.getIndexes().add(mask);
					} else {
						Expression mask2 = factory.createExprBinary(index,
								OpBinary.BITAND, maskValue,
								factory.createTypeInt(32));
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
	private class ModifyProcessActionStore extends AbstractActorVisitor<Object> {
		private Var tab;

		public ModifyProcessActionStore(Var tab) {
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
	}

	/**
	 * This class defines a visitor that substitutes process variable names with
	 * those of the newly defined actions for write
	 * 
	 * @author Khaled JERBI
	 * 
	 */
	private class ModifyProcessActionWrite extends AbstractActorVisitor<Object> {

		private Var tab;

		public ModifyProcessActionWrite(Var tab) {
			this.tab = tab;
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

	private List<Action> AddedUntaggedActions;
	//private int bufferSize;
	private Action done;
	private Type entryType;
	private FSM fsm;
	private List<Var> inputBuffers;
	private int inputIndex;
	private List<Port> inputPorts;
	private List<Action> noRepeatActions;
	private List<Action> visitedActions;
	private List<String> visitedActionsNames;
	private int numTokens;
	private int outputIndex;
	private int visitedRenameIndex;
	private Port port;
	private Action process;
	private List<Var> readIndexes;
	private boolean repeatInput;
	private boolean repeatOutput;
	private Var result;
	private Action store;
	private Map<String, State> statesMap;
	private Action write;
	private List<Var> writeIndexes;
	private List<Integer>bufferSizes;

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
				factory.createExprInt(numTokens), port);
	}

	/**
	 * Adds an FSM to an actor if it has not already
	 * 
	 */
	private void addFsm() {
		fsm = factory.createFSM();
		State initState = statesMap.get("init");
		fsm.getStates().add(initState);
		fsm.setInitialState(initState);
		for (Action action : actor.getActionsOutsideFsm()) {
			fsm.addTransition(initState, action, initState);
		}

		actor.getActionsOutsideFsm().clear();
		actor.setFsm(fsm);
	}

	@Override
	public Object caseActor(Actor actor) {
		this.actor = actor;
		inputIndex = 0;
		outputIndex = 0;
		visitedRenameIndex = 0;
		repeatInput = false;
		repeatOutput = false;
		//bufferSize = 0;
		AddedUntaggedActions = new ArrayList<Action>();
		inputBuffers = new ArrayList<Var>();
		inputPorts = new ArrayList<Port>();
		noRepeatActions = new ArrayList<Action>();
		readIndexes = new ArrayList<Var>();
		statesMap = new HashMap<String, State>();
		writeIndexes = new ArrayList<Var>();
		bufferSizes = new ArrayList<Integer>();
		visitedActions = new ArrayList<Action>();
		visitedActionsNames = new ArrayList<String>();
		modifyRepeatActionsInFSM();
		modifyUntaggedActions(actor);
		return null;
	}

	/**
	 * This method adds instructions for an action to read from a specific
	 * buffer at a specific index
	 * 
	 * @param body
	 *            body of the action
	 * @param position
	 *            position of the buffer in inputBuffers list
	 */
	private void consumeToken(Procedure body, int position, Port port) {
		NodeBlock bodyNode = body.getFirst();
		Var index = body.newTempLocalVariable(factory.createTypeInt(32),
				"index" + port.getName());
		index.setIndex(1);
		Var writeIndex = writeIndexes.get(position);
		bodyNode.add(factory.createInstLoad(index, writeIndex));

		Expression value = factory.createExprBinary(
				factory.createExprVar(index), OpBinary.PLUS,
				factory.createExprInt(1), factory.createTypeInt(32));
		bodyNode.add(factory.createInstStore(writeIndex, value));
	}

	/**
	 * This method copies the output patterns from a source action to a target
	 * one
	 * 
	 * @param source
	 *            source action
	 * @param target
	 *            target action
	 */
	private void copyOutputPattern(Action source, Action target) {
		Pattern targetPattern = target.getOutputPattern();
		Pattern sourcePattern = source.getOutputPattern();
		targetPattern.getNumTokensMap().putAll(sourcePattern.getNumTokensMap());
		targetPattern.getPorts().addAll(sourcePattern.getPorts());
		targetPattern.getPortToVarMap().putAll(sourcePattern.getPortToVarMap());
		targetPattern.getVarToPortMap().putAll(sourcePattern.getVarToPortMap());
	}

	/**
	 * This method creates an action with the given name.
	 * 
	 * @param name
	 *            name of the action
	 * @return a new action created with the given name
	 */
	private Action createAction(Expression condition, String name) {
		Tag tag = factory.createTag(name);

		// Scheduler building
		Procedure scheduler = factory.createProcedure("isSchedulable_" + name,
				0, factory.createTypeBool());
		NodeBlock blockScheduler = factory.createNodeBlock();
		Var result = scheduler.newTempLocalVariable(factory.createTypeBool(),
				"actionResult");
		result.setIndex(1);
		blockScheduler.add(factory.createInstAssign(result, condition));
		blockScheduler.add(factory.createInstReturn(factory
				.createExprVar(result)));
		scheduler.getNodes().add(blockScheduler);

		// Body building ;-)
		Procedure body = factory.createProcedure(name, 0,
				factory.createTypeVoid());
		NodeBlock blockBody = factory.createNodeBlock();
		blockBody.add(factory.createInstReturn());
		body.getNodes().add(blockBody);

		Action action = factory.createAction(tag, factory.createPattern(),
				factory.createPattern(), factory.createPattern(), scheduler,
				body);
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
		Var newCounter = factory.createVar(0, factory.createTypeInt(32), name,
				true, factory.createExprInt(0));

		Expression expression = factory.createExprInt(0);
		newCounter.setInitialValue(expression);
		if (!actor.getStateVars().contains(newCounter.getName())) {
			actor.getStateVars().add(newCounter);
		}
		return newCounter;
	}

	/**
	 * This method creates the done action that is schedulable when required
	 * number of tokens is read (written)
	 * 
	 * @param actionName
	 *            name of the action
	 * @param counter
	 *            global variable counter used for reading (writing) tokens
	 * @param numTokens
	 *            repeat value
	 * @return new done action
	 */
	private Action createDoneAction(String name, Var counter, int numTokens) {
		Tag tag = factory.createTag(name);

		// Body building ;-)
		Procedure body = factory.createProcedure(name, 0,
				factory.createTypeVoid());
		NodeBlock blockBody = factory.createNodeBlock();
		blockBody.add(factory.createInstStore(counter, 0));
		blockBody.add(factory.createInstReturn());
		body.getNodes().add(blockBody);

		// Scheduler building
		Procedure scheduler = factory.createProcedure("isSchedulable_" + name,
				0, factory.createTypeBool());
		Var temp = scheduler.newTempLocalVariable(factory.createTypeBool(),
				"temp");
		temp.setIndex(1);

		scheduler.getLocals().add(temp);
		result = factory.createVar(0, factory.createTypeBool(), "res", true, 0);

		scheduler.getLocals().add(result);
		Var localCounter = factory.createVar(0, counter.getType(),
				"localCounter", true, 1);
		scheduler.getLocals().add(localCounter);
		NodeBlock blockScheduler = factory.createNodeBlock();
		blockScheduler.add(0, factory.createInstLoad(localCounter, counter));

		Expression guardValue = factory.createExprInt(numTokens);
		Expression counterExpression = factory.createExprVar(localCounter);
		Expression expression = factory.createExprBinary(counterExpression,
				OpBinary.EQ, guardValue, factory.createTypeBool());
		blockScheduler.add(factory.createInstAssign(temp, expression));
		blockScheduler.add(factory.createInstAssign(result,
				factory.createExprVar(temp)));
		blockScheduler.add(factory.createInstReturn(factory
				.createExprVar(result)));
		scheduler.getNodes().add(blockScheduler);

		Action action = factory.createAction(tag, factory.createPattern(),
				factory.createPattern(), factory.createPattern(), scheduler,
				body);
		this.actor.getActions().add(action);

		return action;
	}

	/**
	 * This method creates the process action using the nodes & locals of the
	 * action getting transformed
	 * 
	 * @param action
	 *            currently transforming action
	 * @return new process action
	 */
	private Action createProcessAction(Action action) {
		Expression expression = factory.createExprBool(true);
		Action newProcessAction = createAction(expression, "newProcess_"
				+ action.getName());
		Procedure body = newProcessAction.getBody();

		ListIterator<Node> listIt = action.getBody().getNodes().listIterator();
		moveNodes(listIt, body);
		Iterator<Var> it = action.getBody().getLocals().iterator();
		moveLocals(it, body);

		return newProcessAction;
	}

	/**
	 * This method defines a new store action that reads 1 token on the repeat
	 * port
	 * 
	 * @param actionName
	 *            name of the new store action
	 * @param numTokens
	 *            repeat number
	 * @param port
	 *            repeat port
	 * @param readCounter
	 *            global variable counter
	 * @param storeList
	 *            global variable list of store (write)
	 * @return new store action
	 */
	private Action createStoreAction(String actionName, Var readCounter,
			Var storeList, Var buffer, Var writeIndex, int bufferSize) {
		String storeName = actionName + port.getName() + "_NewStore";
		Expression guardValue = factory.createExprInt(numTokens);
		Expression counterExpression = factory.createExprVar(readCounter);
		Expression expression = factory.createExprBinary(counterExpression,
				OpBinary.LT, guardValue, factory.createTypeBool());

		Action newStoreAction = createAction(expression, storeName);
		defineStoreBody(readCounter, storeList, newStoreAction.getBody(),
				buffer, writeIndex, bufferSize);
		return newStoreAction;
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
		Type type = factory.createTypeList(size, entryType);
		Var newList = factory.createVar(0, type, name, true, true);
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
		Expression expression = factory.createExprBool(true);
		Action newUntaggedAction = createAction(expression,
				"untagged_" + port.getName());
		Var localINPUT = factory.createVar(0,
				factory.createTypeList(1, port.getType()), port.getName(),
				true, 0);

		defineUntaggedBody(readIndex, storeList, newUntaggedAction.getBody(),
				localINPUT, port, bufferSize);
		modifyActionSchedulability(newUntaggedAction, writeIndex, readIndex,
				OpBinary.LT, factory.createExprInt(bufferSize), port);
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
	private Action createWriteAction(String actionName, Var writeCounter,
			Var writeList) {
		String writeName = actionName + port.getName() + "_NewWrite";
		Expression guardValue = factory.createExprInt(numTokens);
		Expression counterExpression = factory.createExprVar(writeCounter);
		Expression expression = factory.createExprBinary(counterExpression,
				OpBinary.LT, guardValue, factory.createTypeBool());
		Action newWriteAction = createAction(expression, writeName);

		Var OUTPUT = factory.createVar(0, port.getType(), port.getName()
				+ "_OUTPUT", true, 0);
		defineWriteBody(writeCounter, writeList, newWriteAction.getBody(),
				OUTPUT);
		// add output pattern
		Pattern pattern = newWriteAction.getOutputPattern();
		pattern.setNumTokens(port, 1);
		pattern.setVariable(port, OUTPUT);
		return newWriteAction;
	}

	/**
	 * This method creates the instructions for the body of the new store action
	 * 
	 * @param port
	 *            repeat port
	 * @param readCounter
	 *            global variable counter
	 * @param storeList
	 *            global store (write) list
	 * @param body
	 *            new store action body
	 */
	private void defineStoreBody(Var readCounter, Var storeList,
			Procedure body, Var buffer, Var writeIndex, int bufferSize) {

		NodeBlock bodyNode = body.getFirst();

		Var counter = body.newTempLocalVariable(
				IrUtil.copy(readCounter.getType()), port.getName()
						+ "_Local_counter");
		counter.setIndex(1);
		bodyNode.add(factory.createInstLoad(counter, readCounter));

		Var index = body.newTempLocalVariable(factory.createTypeInt(32),
				"writeIndex");
		index.setIndex(1);
		bodyNode.add(factory.createInstLoad(index, writeIndex));

		Var mask = body.newTempLocalVariable(factory.createTypeInt(32), "mask");
		mask.setIndex(1);
		Expression exprMask = factory.createExprInt(bufferSize - 1);
		Expression maskValue = factory.createExprBinary(
				factory.createExprVar(index), OpBinary.BITAND, exprMask,
				factory.createTypeInt(32));
		bodyNode.add(factory.createInstAssign(mask, maskValue));

		Var input = body.newTempLocalVariable(IrUtil.copy(port.getType()),
				port.getName() + "_Input");
		input.setIndex(1);

		List<Expression> load2Index = new ArrayList<Expression>(1);
		load2Index.add(factory.createExprVar(mask));
		bodyNode.add(factory.createInstLoad(input, buffer, load2Index));
		bodyNode.add(factory.createInstStore(storeList, counter, input));
		// globalCounter= globalCounter + 1

		Expression assignValue = factory.createExprBinary(
				factory.createExprVar(counter), OpBinary.PLUS,
				factory.createExprInt(1), factory.createTypeInt(32));
		bodyNode.add(factory.createInstStore(readCounter, assignValue));

		Expression incValue = factory.createExprBinary(
				factory.createExprVar(index), OpBinary.PLUS,
				factory.createExprInt(1), factory.createTypeInt(32));
		bodyNode.add(factory.createInstStore(writeIndex, incValue));
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
		NodeBlock bodyNode = body.getFirst();

		EList<Var> locals = body.getLocals();
		Var input = factory.createVar(0, port.getType(), port.getName()
				+ "_Input", true, 1);
		locals.add(input);
		List<Expression> load2Index = new ArrayList<Expression>(1);
		load2Index.add(factory.createExprInt(0));

		bodyNode.add(factory.createInstLoad(input, localINPUT, load2Index));

		Var counter = factory.createVar(0, readCounter.getType(),
				port.getName() + "_Local_counter", true, 1);
		locals.add(counter);
		bodyNode.add(factory.createInstLoad(counter, readCounter));

		Var mask = factory.createVar(0, factory.createTypeInt(32), "mask",
				true, 1);
		locals.add(mask);
		Expression maskValue = factory.createExprBinary(
				factory.createExprVar(counter), OpBinary.BITAND,
				factory.createExprInt(bufferSize - 1),
				factory.createTypeInt(32));
		bodyNode.add(factory.createInstAssign(mask, maskValue));

		bodyNode.add(factory.createInstStore(storeList, mask, input));

		Expression indexInc = factory.createExprBinary(
				factory.createExprVar(counter), OpBinary.PLUS,
				factory.createExprInt(1), readCounter.getType());
		bodyNode.add(factory.createInstStore(readCounter, indexInc));
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
			Procedure body, Var OUTPUT) {
		NodeBlock bodyNode = body.getFirst();
		EList<Var> locals = body.getLocals();
		Var counter1 = factory.createVar(0, writeCounter.getType(),
				port.getName() + "_Local_writeCounter", true, outputIndex);
		locals.add(counter1);
		bodyNode.add(factory.createInstLoad(counter1, writeCounter));

		Var output = factory.createVar(0, port.getType(), port.getName()
				+ "_LocalOutput", true, outputIndex);
		locals.add(output);
		List<Expression> load2Index = new ArrayList<Expression>(1);
		load2Index.add(factory.createExprVar(writeCounter));
		bodyNode.add(factory.createInstLoad(output, writeList, load2Index));

		Expression assign2Value = factory.createExprBinary(
				factory.createExprVar(counter1), OpBinary.PLUS,
				factory.createExprInt(1), factory.createTypeInt(32));

		// locals.put(OUTPUT.getName(), OUTPUT);
		bodyNode.add(factory.createInstStore(OUTPUT, 0, output));
		bodyNode.add(factory.createInstStore(writeCounter, assign2Value));
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
		NodeBlock bodyNode = scheduler.getLast();
		EList<Var> locals = scheduler.getLocals();

		Var localRead = factory.createVar(0, factory.createTypeInt(32),
				"readIndex_" + port.getName() + "_" + inputIndex, true,
				inputIndex);
		locals.add(localRead);
		bodyNode.add(index, factory.createInstLoad(localRead, readIndex));
		index++;

		Var localWrite = factory.createVar(0, factory.createTypeInt(32),
				"writeIndex_" + port.getName() + "_" + inputIndex, true,
				inputIndex);
		locals.add(localWrite);
		bodyNode.add(index, factory.createInstLoad(localWrite, writeIndex));
		index++;

		Var diff = factory.createVar(0, factory.createTypeInt(32), "diff"
				+ port.getName() + "_" + inputIndex, true, inputIndex);
		locals.add(diff);
		Expression value = factory.createExprBinary(
				factory.createExprVar(localRead), OpBinary.MINUS,
				factory.createExprVar(localWrite), factory.createTypeInt(32));
		bodyNode.add(index, factory.createInstAssign(diff, value));
		index++;

		Var conditionVar = factory.createVar(0, factory.createTypeBool(),
				"condition_" + port.getName(), true, inputIndex);
		locals.add(conditionVar);
		Expression value2 = factory.createExprBinary(
				factory.createExprVar(diff), op, reference,
				factory.createTypeBool());
		bodyNode.add(index, factory.createInstAssign(conditionVar, value2));
		index++;

		Var myResult = factory.createVar(0, factory.createTypeBool(),
				"myResult_" + port.getName(), true, inputIndex);
		locals.add(myResult);
		int returnIndex = bodyNode.getInstructions().size() - 1;
		InstReturn actionReturn = (InstReturn) bodyNode.getInstructions().get(
				returnIndex);
		// VarLocal currentResult
		Expression e = factory.createExprBinary(actionReturn.getValue(),
				OpBinary.LOGIC_AND, factory.createExprVar(conditionVar),
				factory.createTypeBool());

		bodyNode.add(returnIndex, factory.createInstAssign(myResult, e));
		actionReturn.setValue(factory.createExprVar(myResult));
	}

	/**
	 * This method changes the schedulability of the done action
	 * 
	 * @param counter
	 *            Global Var counter
	 */
	private void modifyDoneAction(Var counter, int portIndex, String portName) {

		NodeBlock blkNode = done.getBody().getFirst();
		blkNode.add(factory.createInstStore(counter, 0));

		blkNode = done.getScheduler().getFirst();
		EList<Var> schedulerLocals = done.getScheduler().getLocals();
		Var localCounter = factory.createVar(0, counter.getType(),
				"localCounterModif" + portName, true, portIndex);
		schedulerLocals.add(localCounter);

		blkNode.add(1, factory.createInstLoad(localCounter, counter));

		Var temp = factory.createVar(0, factory.createTypeBool(), "temp"
				+ portName, true, portIndex);
		schedulerLocals.add(temp);
		Expression guardValue = factory.createExprInt(numTokens);
		Expression counterExpression = factory.createExprVar(localCounter);
		Expression schedulerValue = factory.createExprBinary(counterExpression,
				OpBinary.EQ, guardValue, factory.createTypeBool());
		int index = blkNode.getInstructions().size() - 1;
		blkNode.add(index, factory.createInstAssign(temp, schedulerValue));
		index++;

		Expression buffrerExpression = factory.createExprVar(result);
		Expression resultExpression = factory.createExprVar(temp);
		Expression expression = factory.createExprBinary(buffrerExpression,
				OpBinary.LOGIC_AND, resultExpression, factory.createTypeBool());
		blkNode.add(index, factory.createInstAssign(result, expression));
	}

	/**
	 * This method modifies the no-repeat-actions to read from buffers instead
	 * of ports
	 * 
	 */
	private void modifyNoRepeatActionsInFSM() {
		DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
		Set<UniqueEdge> edges = graph.edgeSet();
		for (UniqueEdge edge : edges) {
			Action action = (Action) edge.getObject();
			if (noRepeatActions.contains(action)) {
				ListIterator<Port> it = action.getInputPattern().getPorts()
						.listIterator();
				while (it.hasNext()) {
					Port port = it.next();
					if (inputPorts.contains(port)) {
						int position = portPosition(inputPorts, port);
						Procedure body = action.getBody();
						Var buffer = inputBuffers.get(position);
						Var writeIndex = writeIndexes.get(position);
						Var readIndex = readIndexes.get(position);
						int bufferSize = bufferSizes.get(position);

						ModifyActionScheduler modifyActionScheduler = new ModifyActionScheduler(
								buffer, writeIndex, port, bufferSize);
						modifyActionScheduler.doSwitch(action.getBody());
						modifyActionScheduler.doSwitch(action.getScheduler());
						modifyActionSchedulability(action, writeIndex,
								readIndex, OpBinary.NE,
								factory.createExprInt(0), port);
						consumeToken(body, position, port);
						noRepeatActions.remove(action);

						int index = it.previousIndex();
						action.getInputPattern().remove(port);
						it = action.getInputPattern().getPorts()
								.listIterator(index);
					}
				}
			}
		}
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
			boolean transformOutFSM = false;
			for (Action verifAction : actions) {
				for (Entry<Port, Integer> verifEntry : verifAction
						.getInputPattern().getNumTokensMap().entrySet()) {
					int verifNumTokens = verifEntry.getValue();
					if (verifNumTokens > 1) {
						transformOutFSM = true;
						break;
					}
				}
				for (Entry<Port, Integer> verifEntry : verifAction
						.getOutputPattern().getNumTokensMap().entrySet()) {
					int verifNumTokens = verifEntry.getValue();
					if (verifNumTokens > 1) {
						transformOutFSM = true;
						break;
					}
				}
			}
			if (transformOutFSM == true) {
				// ////////
				State initState = factory.createState("init");
				statesMap.put("init", initState);
				// statesMap.put("init", initState);
				// no FSM: simply visit all the actions
				addFsm();
				for (Action action : actions) {
					visitTransition(initState, initState, action);
				}
				modifyNoRepeatActionsInFSM();
				transformOutFSM = false;
			}
			// //////
		} else {
				List<Action> actions = new ArrayList<Action>(
						actor.getActions());
				boolean transformFSM = false;
				for (Action verifAction : actions) {
					for (Entry<Port, Integer> verifEntry : verifAction
							.getInputPattern().getNumTokensMap().entrySet()) {
						int verifNumTokens = verifEntry.getValue();
						if (verifNumTokens > 1) {
							transformFSM = true;
							break;
						}
					}
					for (Entry<Port, Integer> verifEntry : verifAction
							.getOutputPattern().getNumTokensMap().entrySet()) {
						int verifNumTokens = verifEntry.getValue();
						if (verifNumTokens > 1) {
							transformFSM = true;
							break;
						}
					}
				}
				if (transformFSM == true) {
				// with an FSM: visits all transitions
				DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
				Set<UniqueEdge> edges = graph.edgeSet();
				for (UniqueEdge edge : edges) {
					State source = graph.getEdgeSource(edge);
					State target = graph.getEdgeTarget(edge);
					Action action = (Action) edge.getObject();
					visitTransition(source, target, action);
				}
				modifyNoRepeatActionsInFSM();
				transformFSM = false;
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
				scanUntaggedOutputs(action);
			}
		}
	}

	/**
	 * This method moves the local variables of a procedure to another using a
	 * VarLocal iterator
	 * 
	 * @param itVar
	 *            source VarLocal iterator
	 * @param newProc
	 *            target procedure
	 */
	private void moveLocals(Iterator<Var> itVar, Procedure newProc) {
		while (itVar.hasNext()) {
			Var var = itVar.next();
			itVar.remove();
			newProc.getLocals().add(var);
		}
	}

	/**
	 * This method moves the nodes of a procedure to another using a Node
	 * iterator
	 * 
	 * @param itNode
	 *            source node iterator
	 * @param newProc
	 *            target procedure
	 */
	private void moveNodes(ListIterator<Node> itNode, Procedure newProc) {
		while (itNode.hasNext()) {
			Node node = itNode.next();
			itNode.remove();
			newProc.getNodes().add(node);
		}
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
	 * 
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
				String processName = "newProcess_" + action.getName();
				int processIndex = actionPosition(actions, processName);
				process = actions.get(processIndex);

				String updatestoreName = "newUpdateStateStore" + action.getName()
						+ visitedRenameIndex;
				State storeState = factory.createState(updatestoreName);
				fsm.getStates().add(storeState);
				String upDateProcessName = "newUpdateStateProcess" + action.getName()
						+ visitedRenameIndex;
				State processState = factory.createState(upDateProcessName);
				statesMap.put(upDateProcessName, processState);
				fsm.getStates().add(processState);
				fsm.addTransition(processState, process, target);
				fsm.addTransition(source, oldAction, storeState);

				visitedRenameIndex++;
				
				for (Entry<Port, Integer> entry : action.getInputPattern()
						.getNumTokensMap().entrySet()) {
					inputIndex = inputIndex + 100;
					port = entry.getKey();
					String storeName = action.getName() + port.getName()
							+ "_NewStore";
					int storeIndex = actionPosition(actions, storeName);
					Action store = actions.get(storeIndex);
					fsm.addTransition(storeState, store, storeState);
					if (inputIndex == 100) {
						String doneName = action.getName() + "newStoreDone";
						int doneIndex = actionPosition(actions, doneName);
						Action done = actions.get(doneIndex);
						fsm.addTransition(storeState, done, processState);
					}
					
				}
				break;
			}
			inputIndex = 0;
		}

		for (Entry<Port, Integer> verifEntry : action.getOutputPattern()
				.getNumTokensMap().entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			if (verifNumTokens > 1) {
				String upDateProcessName = "newStateProcess" + action.getName()
						+ visitedRenameIndex;

				String updateWriteName = "newStateWrite" + action.getName()
						+ visitedRenameIndex;
				State writeState = factory.createState(updateWriteName);
				fsm.getStates().add(writeState);
				// create new process action if not created while treating
				// inputs
				if (!repeatInput) {
					fsm.replaceTarget(source, oldAction, writeState);
				} else {
					State processStateWrite = statesMap.get(upDateProcessName);
					fsm.replaceTarget(processStateWrite, process, writeState);
					process.getOutputPattern().clear();
				}
				visitedRenameIndex++;
				for (Entry<Port, Integer> entry : action.getOutputPattern()
						.getNumTokensMap().entrySet()) {
					outputIndex = outputIndex + 100;
					port = entry.getKey();

					String writeName = action.getName() + port.getName()
							+ "_NewWrite";
					int writeIndex = actionPosition(actions, writeName);
					Action write = actions.get(writeIndex);
					fsm.addTransition(writeState, write, writeState);

					// create a new write done action once
					if (outputIndex == 100) {
						String doneName = action.getName() + "newWriteDone";
						int doneIndex = actionPosition(actions, doneName);
						Action done = actions.get(doneIndex);
						fsm.addTransition(writeState, done, target);
					}

				}
				break;
			}
			outputIndex = 0;
		}
		repeatInput = false;
	}

	/**
	 * For every Input of the action this method creates the new required
	 * actions
	 * 
	 * @param action
	 *            action to transform
	 * @param sourceName
	 *            name of the source state of the action in the actor fsm
	 * @param targetName
	 *            name of the target state of the action in the actor fsm
	 */
	private void scanInputs(Action action, State sourceState, State targetState) {
		for (Entry<Port, Integer> verifEntry : action.getInputPattern()
				.getNumTokensMap().entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			if (verifNumTokens > 1) {
				repeatInput = true;
				// create new process action
				process = createProcessAction(action);
				copyOutputPattern(action, process);
				// process.setOutputPattern(EcoreHelper.copy(action.getOutputPattern()));

				String storeName = "newStateStore" + action.getName();
				State storeState = factory.createState(storeName);
				fsm.getStates().add(storeState);
				String processName = "newStateProcess" + action.getName();
				State processState = factory.createState(processName);
				statesMap.put(processName, processState);
				fsm.getStates().add(processState);
				fsm.addTransition(processState, process, targetState);

				Var untagBuffer = factory.createVar(0, entryType, "buffer",
						true, true);
				Var untagReadIndex = factory.createVar(0,
						factory.createTypeInt(32), "UntagReadIndex", true,
						factory.createExprInt(0));
				Var untagWriteIndex = factory.createVar(0,
						factory.createTypeInt(32), "UntagWriteIndex", true,
						factory.createExprInt(0));
				// if input repeat detected --> treat all input ports

				for (Entry<Port, Integer> entry : action.getInputPattern()
						.getNumTokensMap().entrySet()) {
					numTokens = entry.getValue();
					inputIndex = inputIndex + 100;
					port = entry.getKey();
					int bufferSize =  OptimalBufferSize(action, port);
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
						untagBuffer = createTab(port.getName() + "_buffer",
								entryType, bufferSize);
						inputBuffers.add(untagBuffer);
						untagReadIndex = createCounter("readIndex_"
								+ port.getName());
						readIndexes.add(untagReadIndex);
						untagWriteIndex = createCounter("writeIndex_"
								+ port.getName());
						writeIndexes.add(untagWriteIndex);
						createUntaggedAction(untagReadIndex, untagWriteIndex,
								untagBuffer, port, true, bufferSize);
					}

					String counterName = action.getName() + "NewStoreCounter"
							+ inputIndex;
					Var counter = createCounter(counterName);
					String listName = action.getName() + "NewStoreList"
							+ inputIndex;
					Var tab = createTab(listName, entryType, numTokens);

					store = createStoreAction(action.getName(), counter, tab,
							untagBuffer, untagWriteIndex, bufferSize);

					ModifyProcessActionStore modifyProcessAction = new ModifyProcessActionStore(
							tab);
					modifyProcessAction.doSwitch(process.getBody());
					fsm.addTransition(storeState, store, storeState);
					// create a new store done action once
					if (inputIndex == 100) {
						done = createDoneAction(action.getName()
								+ "newStoreDone", counter, numTokens);
						fsm.addTransition(storeState, done, processState);
					} else {
						// the new done action already exists --> modify
						// schedulability
						modifyDoneAction(counter, inputIndex, port.getName());
					}
					actionToTransition(port, action, untagBuffer,
							untagWriteIndex, untagReadIndex, bufferSize);
					// action.getInputPattern().remove(port);
				}

				action.getBody().getNodes().clear();
				NodeBlock block = factory.createNodeBlock();
				block = factory.createNodeBlock();
				block.add(factory.createInstReturn());
				action.getBody().getNodes().add(block);
				fsm.replaceTarget(sourceState, action, storeState);
				action.getInputPattern().clear();
				break;

			}
		}
		inputIndex = 0;
	}

	/**
	 * For every output of the action this method creates the new required
	 * actions
	 * 
	 * @param action
	 *            action to transform
	 * @param sourceName
	 *            name of the source state of the action in the actor fsm
	 * @param targetName
	 *            name of the target state of the action in the actor fsm
	 */
	private void scanOutputs(Action action, State sourceState, State targetState) {
		for (Entry<Port, Integer> verifEntry : action.getOutputPattern()
				.getNumTokensMap().entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			if (verifNumTokens > 1) {
				repeatOutput = true;
				String processName = "newStateProcess" + action.getName();
				String writeName = "newStateWrite" + action.getName();
				State writeState = factory.createState(writeName);
				fsm.getStates().add(writeState);
				// create new process action if not created while treating
				// inputs
				if (!repeatInput) {
					fsm.replaceTarget(sourceState, action, writeState);
				} else {
					State processState = statesMap.get(processName);
					fsm.replaceTarget(processState, process, writeState);
					process.getOutputPattern().clear();
				}
				for (Entry<Port, Integer> entry : action.getOutputPattern()
						.getNumTokensMap().entrySet()) {
					numTokens = entry.getValue();
					outputIndex = outputIndex + 100;
					port = entry.getKey();
					entryType = port.getType();
					String counterName = action.getName() + "NewWriteCounter"
							+ outputIndex;
					Var counter = createCounter(counterName);
					String listName = action.getName() + "NewWriteList"
							+ outputIndex;
					Var tab = createTab(listName, entryType, numTokens);
					write = createWriteAction(action.getName(), counter, tab);
					write.getOutputPattern().setNumTokens(port, 1);
					if (!repeatInput) {
						ModifyProcessActionWrite modifyProcessActionWrite = new ModifyProcessActionWrite(
								tab);
						modifyProcessActionWrite.doSwitch(action.getBody());
					} else {
						ModifyProcessActionWrite modifyProcessActionWrite = new ModifyProcessActionWrite(
								tab);
						modifyProcessActionWrite.doSwitch(process.getBody());
					}
					fsm.addTransition(writeState, write, writeState);

					// create a new write done action once
					if (outputIndex == 100) {
						done = createDoneAction(action.getName()
								+ "newWriteDone", counter, numTokens);
						fsm.addTransition(writeState, done, targetState);

					} else {
						modifyDoneAction(counter, outputIndex, port.getName());
					}
				}
				// remove outputPattern from transition action
				action.getOutputPattern().clear();
				break;
			}
		}
		outputIndex = 0;
	}

	/**
	 * this method visits the inputs of an untagged action to check repeats
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
			int bufferSize = OptimalBufferSize(action, verifPort);
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
						OpBinary.GE, factory.createExprInt(verifNumTokens),
						verifPort);
				updateUntagIndex(action, writeIndex, verifNumTokens);
				action.getInputPattern().remove(verifPort);
			} else {
				if (verifNumTokens > 1) {
					Var untagBuffer = factory.createVar(0, entryType, "buffer",
							true, true);
					Var untagReadIndex = factory.createVar(0,
							factory.createTypeInt(32), "UntagReadIndex", true,
							factory.createExprInt(0));
					Var untagWriteIndex = factory.createVar(0,
							factory.createTypeInt(32), "UntagWriteIndex", true,
							factory.createExprInt(0));
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
							factory.createExprInt(verifNumTokens), verifPort);
					updateUntagIndex(action, untagWriteIndex, verifNumTokens);
					action.getInputPattern().remove(verifPort);
				}
			}
		}
		AddedUntaggedActions.add(action);
	}

	/**
	 * this method visits the outputs of an untagged action to check repeats
	 * 
	 * @param action
	 *            action containing the outputs to check
	 */
	private void scanUntaggedOutputs(Action action) {
		for (Entry<Port, Integer> verifEntry : action.getInputPattern()
				.getNumTokensMap().entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			if (verifNumTokens > 1) {
				for (Entry<Port, Integer> entry : action.getInputPattern()
						.getNumTokensMap().entrySet()) {
					numTokens = entry.getValue();
					Port verifPort = entry.getKey();
					String name = "TokensToSend" + verifPort.getName();
					Var tokensToSend = createCounter(name);
					Expression condition = factory.createExprBinary(
							factory.createExprVar(tokensToSend), OpBinary.GT,
							factory.createExprInt(0), factory.createTypeBool());
					String actionName = "untaggedWrite_" + verifPort.getName();
					Action untaggedWrite = createAction(condition, actionName);
					Pattern pattern = untaggedWrite.getOutputPattern();
					pattern.setNumTokens(verifPort, 1);
					Var OUTPUT = factory.createVar(0, verifPort.getType(),
							verifPort.getName() + "OUTPUT", true, 0);
					pattern.setVariable(verifPort, OUTPUT);
					// add instruction: tokensToSend = tokensToSend - 1 ;
					Var numTokenToSend = factory.createVar(0,
							factory.createTypeInt(32), "numTokensToSend", true,
							0);
					NodeBlock untaggedBlkNode = untaggedWrite.getBody()
							.getLast();
					untaggedBlkNode.add(factory.createInstLoad(numTokenToSend,
							tokensToSend));
					Expression value = factory.createExprBinary(
							factory.createExprVar(numTokenToSend),
							OpBinary.MINUS, factory.createExprInt(1),
							factory.createTypeInt(32));
					untaggedBlkNode.add(factory.createInstStore(tokensToSend,
							value));
					// add untagged action in high priority
					actor.getActionsOutsideFsm().add(0, untaggedWrite);
					// add write condition to untagged action
					NodeBlock blkNode = action.getBody().getLast();
					blkNode.add(factory
							.createInstStore(tokensToSend, numTokens));
				}
			}
		}
		AddedUntaggedActions.add(action);
	}

	/**
	 * This method updates the write index of the buffer after reading tokens
	 * 
	 * @param action
	 *            untagged action to change
	 * @param writeIndex
	 *            index to update
	 * @param numTokens
	 *            number of tokens read from the buffer
	 */
	private void updateUntagIndex(Action action, Var writeIndex, int numTokens) {
		NodeBlock blkNode = action.getBody().getLast();
		Var localWriteIndex = action.getBody().newTempLocalVariable(
				factory.createTypeInt(32), "localWriteIndex");
		blkNode.add(factory.createInstLoad(localWriteIndex, writeIndex));
		Expression value = factory.createExprBinary(
				factory.createExprVar(localWriteIndex), OpBinary.PLUS,
				factory.createExprInt(numTokens), factory.createTypeInt(32));
		blkNode.add(factory.createInstStore(writeIndex, value));
	}

	/**
	 * this method checks if the action has already been transformed. in that
	 * case the non-transformed action is reconsidered
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
			}
		}
	}
	
	/**
	 * this method returns the closest power of 2 of x --> optimal buffer size
	 * 
	 * @param x
	 * @return closest power of 2 of x
	 */
	private int closestPow_2(int x) {
		int p = 1;
		while (p < x) {
			p = p * 2;
		}
		return p;
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
	private int OptimalBufferSize(Action action, Port port) {
		int size = 0;
		int optimalSize = 0;
		for (Entry<Port, Integer> entry : action.getInputPattern()
				.getNumTokensMap().entrySet()) {
			if (entry.getKey() == port) {
				if (entry.getValue() > size) {
					size = entry.getValue();
				}
			}
		}
		optimalSize = closestPow_2(size);
		return optimalSize;
	}

	/**

	/**
	 * visits a transition characterized by its source name, target name and
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
		verifVisitedActions(action, sourceState, targetState);
		createActionsSet(action, sourceState, targetState);
		
		if (repeatInput && !repeatOutput) {
			// output pattern already copied in process action
			action.getOutputPattern().clear();
		}
		if (!repeatInput && !noRepeatActions.contains(action)) {
			noRepeatActions.add(action);
		}
		repeatInput = false;
		repeatOutput = false;
	}
}