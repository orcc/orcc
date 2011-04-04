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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.VarGlobal;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.VarLocal;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.serialize.IRCloner;
import net.sf.orcc.util.OrderedMap;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a visitor that transforms multi-token to mono-token data
 * transfer
 * 
 * @author Khaled Jerbi
 * 
 */
public class Multi2MonoToken extends AbstractActorVisitor {
	/**
	 * This class defines a visitor that substitutes the peek from the port to
	 * the new buffer and changes the index from (index) to
	 * (index+writeIndex&maskValue)
	 * 
	 * @author Khaled Jerbi
	 * 
	 */
	private class ModifyActionScheduler extends AbstractActorVisitor {

		private VarGlobal buffer;
		private Port currentPort;
		private VarGlobal writeIndex;
		private int bufferSize;

		// private Var tempTab;

		public ModifyActionScheduler(VarGlobal buffer,
				VarGlobal writeIndex, Port currentPort, int bufferSize) {
			this.buffer = buffer;
			this.writeIndex = writeIndex;
			this.currentPort = currentPort;
			this.bufferSize = bufferSize;
		}

		@Override
		public void visit(Load load) {

			if (load.getSource().getVariable().getName()
					.equals(currentPort.getName())) {
				// change tab Name
				Use useArray = new Use(buffer);
				load.setSource(useArray);
				// change index --> writeIndex+index
				Expression expression1 = load.getIndexes().get(0);
				Expression expression2 = new VarExpr(new Use(writeIndex));
				Expression newExpression = new BinaryExpr(expression1,
						BinaryOp.PLUS, expression2, port.getType());
				Expression maskValue = new IntExpr(bufferSize - 1);
				Expression mask = new BinaryExpr(newExpression,
						BinaryOp.BITAND, maskValue,
						IrFactory.eINSTANCE.createTypeInt(32));
				load.getIndexes().set(0, mask);
			}
		}
	}

	/**
	 * This class replaces the load sources names from ports to specific buffers
	 * and adds required instructions in the clone action body
	 * 
	 * @author kjerbi-adm
	 * 
	 */
	private class ModifyCloneBody extends AbstractActorVisitor {

		private VarGlobal buffer;
		private Port currentPort;
		private VarGlobal writeIndex;
		private VarGlobal readIndex;
		private Procedure body;
		private int bufferSize;
		private Action cloneAction;

		// private Var tempTab;

		public ModifyCloneBody(VarGlobal buffer,
				VarGlobal writeIndex, VarGlobal readIndex,
				Port currentPort, Procedure body, int bufferSize, Action cloneAction) {
			this.buffer = buffer;
			this.writeIndex = writeIndex;
			this.readIndex = readIndex;
			this.currentPort = currentPort;
			this.body = body;
			this.bufferSize = bufferSize;
			this.cloneAction = cloneAction;
		}

		@Override
		public void visit(Load load) {
			
			if (load.getSource().getVariable().getName()
					.equals(currentPort.getName())) {
				VarLocal inputTmp = new VarLocal(true, 0, new Location(),
						currentPort.getName(), entryType);
				VarLocal tmp = new VarLocal(true, 0, new Location(),
						"tmp", entryType);
				tmp = load.getTarget();
				// change tab Name
				Use useArray = new Use(buffer);
				load.setSource(useArray);
				// change index --> writeIndex+index
				Expression expression1 = load.getIndexes().get(0);
				Expression expression2 = new VarExpr(new Use(writeIndex));
				Expression newExpression = new BinaryExpr(expression1,
						BinaryOp.PLUS, expression2, currentPort.getType());
				Expression maskValue = new IntExpr(bufferSize - 1);
				Expression mask = new BinaryExpr(newExpression,
						BinaryOp.BITAND, maskValue,
						IrFactory.eINSTANCE.createTypeInt(32));
				load.getIndexes().set(0, mask);

				// addStoreFromBuffer(body, position, tmp);
				//removePortFromPattern(cloneAction.getInputPattern(), currentPort,
					//	inputTmp);
			}
		}
	}

	/**
	 * This class defines a visitor that substitutes process variable names with
	 * those of the newly defined actions for Store
	 * 
	 * @author Khaled Jerbi
	 * 
	 */
	private class ModifyProcessActionStore extends AbstractActorVisitor {

		private VarGlobal tab;

		// private Var tempTab;

		public ModifyProcessActionStore(VarGlobal tab) {
			this.tab = tab;
		}

		@Override
		public void visit(Load load) {

			if (load.getSource().getVariable().getName().equals(port.getName())) {
				Use useArray = new Use(tab);
				load.setSource(useArray);
			}
		}
	}

	/**
	 * This class defines a visitor that substitutes process variable names with
	 * those of the newly defined actions for write
	 * 
	 * @author Khaled JERBI
	 * 
	 */
	private class ModifyProcessActionWrite extends AbstractActorVisitor {

		private VarGlobal tab;

		public ModifyProcessActionWrite(VarGlobal tab) {
			this.tab = tab;
		}

		@Override
		public void visit(Store store) {
			if (store.getTarget().getName().equals(port.getName())) {
				store.setTarget(tab);
			}
		}
	}

	private int bufferSize = 0;
	private Action done;
	private Type entryType;
	private FSM fsm;
	private List<VarGlobal> inputBuffers = new ArrayList<VarGlobal>();
	private int inputIndex = 0;
	private List<Port> inputPorts = new ArrayList<Port>();
	private List<Action> NoRepeatActions = new ArrayList<Action>();
	private List<Action> NoRepeatActionsDone = new ArrayList<Action>();
	private List<Action> cloneActions = new ArrayList<Action>();
	private int numTokens;
	private int outputIndex = 0;
	private Port port;
	private Action process;
	private List<VarGlobal> readIndexes = new ArrayList<VarGlobal>();
	private boolean repeatInput = false;
	private boolean repeatOutput = false;
	private VarLocal result;
	private Action store;
	private Action untagged;
	private Action write;
	private List<VarGlobal> writeIndexes = new ArrayList<VarGlobal>();

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
	private void actionToTransition(Action action, VarGlobal buffer,
			VarGlobal writeIndex, VarGlobal readIndex) {
		ModifyActionScheduler modifyActionScheduler = new ModifyActionScheduler(
				buffer, writeIndex, port, bufferSize);
		modifyActionScheduler.visit(action.getScheduler());
		modifyActionSchedulability(action, writeIndex, readIndex, BinaryOp.GE,
				new IntExpr(numTokens));
	}

	/**
	 * Adds an FSM to an actor if it has not already
	 * 
	 */
	private void addFsm() {
		ActionScheduler scheduler = actor.getActionScheduler();

		fsm = new FSM();
		fsm.setInitialState("init");
		fsm.addState("init");
		for (Action action : scheduler.getActions()) {
			fsm.addTransition("init", action, "init");
		}

		scheduler.getActions().clear();
		scheduler.setFsm(fsm);
	}

	/**
	 * This method adds instructions for an action to read from a specific
	 * buffer at a specific index
	 * 
	 * @param body
	 *            body of the action
	 * @param position
	 *            position of the buffer in inputBuffers list
	 * @param tmp
	 *            the Local variable used to read from the port
	 */
	private void addStoreFromBuffer(Procedure body, int position,
			VarLocal tmp) {
		NodeBlock bodyNode = body.getFirst();
		OrderedMap<String, VarLocal> locals = body.getLocals();
		locals.put(tmp.getBaseName(), tmp);
		VarLocal index = new VarLocal(true, 1, new Location(),
				"index", tmp.getType());
		locals.put(index.getName(), index);
		VarGlobal writeIndex = writeIndexes.get(position);
		Instruction loadInd = new Load(index, new Use(writeIndex));
		int addIndex = 0;
		bodyNode.add(addIndex, loadInd);
		addIndex++;

		List<Expression> loadIndex = new ArrayList<Expression>(1);
		Expression expression = new BinaryExpr(new VarExpr(new Use(index)),
				BinaryOp.BITAND, new IntExpr(bufferSize - 1),
				IrFactory.eINSTANCE.createTypeInt(32));
		loadIndex.add(expression);
		Instruction load = new Load(tmp, new Use(inputBuffers.get(position)),
				loadIndex);
		bodyNode.add(addIndex, load);
		addIndex++;

		VarLocal indexInc = new VarLocal(true, 2, new Location(),
				"index", tmp.getType());
		locals.put(indexInc.getName(), indexInc);
		Expression value = new BinaryExpr(new VarExpr(new Use(index)),
				BinaryOp.PLUS, new IntExpr(1),
				IrFactory.eINSTANCE.createTypeInt(32));
		Instruction assign = new Assign(indexInc, value);
		bodyNode.add(addIndex, assign);
		addIndex++;

		Instruction store = new Store(writeIndex,
				new VarExpr(new Use(indexInc)));
		bodyNode.add(addIndex, store);
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
	 * This method creates an action with the given name.
	 * 
	 * @param name
	 *            name of the action
	 * @return a new action created with the given name
	 */
	private Action createAction(Expression condition, String name) {
		// scheduler
		Procedure scheduler = IrFactory.eINSTANCE.createProcedure(
				"isSchedulable_" + name, new Location(),
				IrFactory.eINSTANCE.createTypeBool());
		VarLocal result = scheduler.newTempLocalVariable(
				this.actor.getFile(), IrFactory.eINSTANCE.createTypeBool(),
				"myResult");
		result.setIndex(1);
		scheduler.getLocals().remove(result.getBaseName());
		scheduler.getLocals().put(result.getName(), result);

		NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();
		block.add(new Assign(result, condition));
		block.add(new Return(new VarExpr(new Use(result))));
		scheduler.getNodes().add(block);

		// body
		Procedure body = IrFactory.eINSTANCE.createProcedure(name,
				new Location(), IrFactory.eINSTANCE.createTypeVoid());
		block = IrFactoryImpl.eINSTANCE.createNodeBlock();
		block.add(new Return(null));
		body.getNodes().add(block);

		// tag
		Tag tag = new Tag();
		tag.add(name);

		Action action = new Action(new Location(), tag, new Pattern(),
				new Pattern(), scheduler, body);

		// add action to actor's actions
		this.actor.getActions().add(action);

		return action;
	}

	/**
	 * This method creates the required Store, done, untagged and process
	 * actions
	 * 
	 * @param action
	 *            the action getting transformed
	 */
	private void createActionsSet(Action action, String sourceName,
			String targetName) {
		scanInputs(action, sourceName, targetName);
		scanOutputs(action, sourceName, targetName);
	}

	/**
	 * this method return a new action cloned from an initial action
	 * 
	 * @param action
	 *            action to clone
	 * @return new cloned action
	 */
	private Action createCloneAction(Action action) {
		String name = action.getName() + "_clone";
		IRCloner iRCloner = new IRCloner(actor);
		Action newCloneAction = iRCloner.cloneAction(action);
		newCloneAction.getTag().add("clone");
		newCloneAction.getTag().get(0)
				.replaceAll(newCloneAction.getName(), name);
		newCloneAction.getBody().setName(name);
		newCloneAction.getScheduler().setName("isSchedulable_" + name);
		actor.getActions().add(newCloneAction);

		return newCloneAction;
	}

	/**
	 * This method creates a global variable counter for store with the given
	 * name.
	 * 
	 * @param name
	 *            name of the counter
	 * @return new counter with the given name
	 */
	private VarGlobal createCounter(String name) {
		VarGlobal newCounter = new VarGlobal(new Location(),
				IrFactory.eINSTANCE.createTypeInt(32), name, true);
		Expression expression = new IntExpr(0);
		newCounter.setInitialValue(expression);
		if (!actor.getStateVars().contains(newCounter.getName())) {
			actor.getStateVars().put(newCounter.getName(), newCounter);
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
	private Action createDoneAction(String name, VarGlobal counter,
			int numTokens) {
		// body
		Procedure body = IrFactory.eINSTANCE.createProcedure(name,
				new Location(), IrFactory.eINSTANCE.createTypeVoid());
		NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();
		Store store = new Store(counter, new IntExpr(0));
		block.add(store);
		block.add(new Return(null));
		body.getNodes().add(block);

		// scheduler
		Procedure scheduler = IrFactory.eINSTANCE.createProcedure(
				"isSchedulable_" + name, new Location(),
				IrFactory.eINSTANCE.createTypeBool());
		VarLocal temp = scheduler.newTempLocalVariable(
				this.actor.getFile(), IrFactory.eINSTANCE.createTypeBool(),
				"temp");
		temp.setIndex(1);
		scheduler.getLocals().remove(temp.getBaseName());
		scheduler.getLocals().put(temp.getName(), temp);
		result = new VarLocal(true, 0, new Location(), "result",
				IrFactory.eINSTANCE.createTypeBool());
		scheduler.getLocals().put(result.getName(), result);
		VarLocal localCounter = new VarLocal(true, 1, new Location(),
				"localCounter", counter.getType());
		scheduler.getLocals().put(localCounter.getName(), localCounter);
		block = IrFactoryImpl.eINSTANCE.createNodeBlock();
		Load schedulerLoad = new Load(localCounter, new Use(counter));
		block.add(0, schedulerLoad);

		Expression guardValue = new IntExpr(numTokens);
		Expression counterExpression = new VarExpr(new Use(localCounter));
		Expression expression = new BinaryExpr(counterExpression, BinaryOp.EQ,
				guardValue, IrFactory.eINSTANCE.createTypeBool());
		block.add(new Assign(temp, expression));
		block.add(new Assign(result, new VarExpr(new Use(temp))));
		block.add(new Return(new VarExpr(new Use(result))));
		scheduler.getNodes().add(block);

		// tag
		Tag tag = new Tag();
		tag.add(name);

		Action action = new Action(new Location(), tag, new Pattern(),
				new Pattern(), scheduler, body);

		// add action to actor's actions
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
		Expression expression = new BoolExpr(true);
		Action newProcessAction = createAction(expression, "newProcess_"
				+ action.getName());
		Procedure body = newProcessAction.getBody();

		ListIterator<Node> listIt = action.getBody().getNodes().listIterator();
		moveNodes(listIt, body);
		Iterator<VarLocal> it = action.getBody().getLocals().iterator();
		moveLocals(it, body);
		if (repeatOutput && !repeatInput) {
			Procedure scheduler = newProcessAction.getScheduler();
			listIt = action.getScheduler().getNodes().listIterator();
			moveNodes(listIt, scheduler);
			it = action.getScheduler().getLocals().iterator();
			moveLocals(it, scheduler);
		}
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
	private Action createStoreAction(String actionName,
			VarGlobal readCounter, VarGlobal storeList,
			VarGlobal buffer, VarGlobal writeIndex) {
		String storeName = actionName + port.getName() + "_NewStore";
		Expression guardValue = new IntExpr(numTokens);
		Expression counterExpression = new VarExpr(new Use(readCounter));
		Expression expression = new BinaryExpr(counterExpression, BinaryOp.LT,
				guardValue, IrFactory.eINSTANCE.createTypeBool());

		Action newStoreAction = createAction(expression, storeName);
		defineStoreBody(readCounter, storeList, newStoreAction.getBody(),
				buffer, writeIndex);
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
	private VarGlobal createTab(String name, Type entryType, int size) {
		Type type = IrFactory.eINSTANCE.createTypeList(size, entryType);
		VarGlobal newList = new VarGlobal(new Location(), type, name,
				true);
		if (!actor.getStateVars().contains(newList.getName())) {
			actor.getStateVars().put(newList.getName(), newList);
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
	 * @return new untagged action
	 */
	private Action createUntaggedAction(VarGlobal readIndex,
			VarGlobal storeList) {
		Expression expression = new BoolExpr(true);
		Action newUntaggedAction = createAction(expression,
				"untagged_" + port.getName());
		VarLocal localINPUT = new VarLocal(true, 0, new Location(),
				port.getName(), IrFactory.eINSTANCE.createTypeList(1,
						port.getType()));
		defineUntaggedBody(readIndex, storeList, newUntaggedAction.getBody(),
				localINPUT);
		Pattern pattern = newUntaggedAction.getInputPattern();
		pattern.setNumTokens(port, 1);
		pattern.setVariable(port, localINPUT);
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
	private Action createWriteAction(String actionName,
			VarGlobal writeCounter, VarGlobal writeList) {
		String writeName = actionName + port.getName() + "_NewWrite";
		Expression guardValue = new IntExpr(numTokens);
		Expression counterExpression = new VarExpr(new Use(writeCounter));
		Expression expression = new BinaryExpr(counterExpression, BinaryOp.LT,
				guardValue, IrFactory.eINSTANCE.createTypeBool());
		Action newWriteAction = createAction(expression, writeName);

		VarLocal OUTPUT = new VarLocal(true, 0, new Location(),
				port.getName() + "OUTPUT", IrFactory.eINSTANCE.createTypeList(
						1, port.getType()));
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
	private void defineStoreBody(VarGlobal readCounter,
			VarGlobal storeList, Procedure body, VarGlobal buffer,
			VarGlobal writeIndex) {
		NodeBlock bodyNode = body.getFirst();

		OrderedMap<String, VarLocal> locals = body.getLocals();
		VarLocal counter = new VarLocal(true, 1, new Location(),
				port.getName() + "_Local_counter", readCounter.getType());
		locals.put(counter.getName(), counter);
		Instruction load1 = new Load(counter, new Use(readCounter));
		bodyNode.add(load1);

		VarLocal index = new VarLocal(true, 1, new Location(),
				"writeIndex", IrFactory.eINSTANCE.createTypeInt(32));
		locals.put(index.getName(), index);
		Instruction loadIndex = new Load(index, new Use(writeIndex));
		bodyNode.add(loadIndex);

		VarLocal mask = new VarLocal(true, 1, new Location(), "mask",
				IrFactory.eINSTANCE.createTypeInt(32));
		locals.put(mask.getName(), mask);
		Expression exprMask = new IntExpr(bufferSize - 1);
		Expression maskValue = new BinaryExpr(new VarExpr(new Use(index)),
				BinaryOp.BITAND, exprMask,
				IrFactory.eINSTANCE.createTypeInt(32));
		Assign assignMask = new Assign(mask, maskValue);
		bodyNode.add(assignMask);

		VarLocal input = new VarLocal(true, 1, new Location(),
				port.getName() + "_Input", port.getType());
		locals.put(input.getName(), input);
		List<Expression> load2Index = new ArrayList<Expression>(1);
		Expression expression1 = new VarExpr(new Use(mask));

		load2Index.add(expression1);
		Instruction load2 = new Load(input, new Use(buffer), load2Index);
		bodyNode.add(load2);

		List<Expression> store1Index = new ArrayList<Expression>(1);
		store1Index.add(new VarExpr(new Use(counter)));
		Instruction store1 = new Store(storeList, store1Index, new VarExpr(
				new Use(input)));
		bodyNode.add(store1);

		// globalCounter= globalCounter + 1
		VarLocal counter2 = new VarLocal(true, 2, new Location(),
				port.getName() + "_Local_counter", readCounter.getType());
		locals.put(counter2.getName(), counter2);
		Expression storeIndexElement = new VarExpr(new Use(counter));
		Expression inc1 = new IntExpr(1);
		Expression assignValue = new BinaryExpr(storeIndexElement,
				BinaryOp.PLUS, inc1, IrFactory.eINSTANCE.createTypeInt(32));
		Instruction assign = new Assign(counter2, assignValue);
		bodyNode.add(assign);
		Instruction store2 = new Store(readCounter, new VarExpr(new Use(
				counter2)));
		bodyNode.add(store2);

		VarLocal tmp = new VarLocal(true, 1, new Location(), "tmp",
				IrFactory.eINSTANCE.createTypeInt(32));
		locals.put(tmp.getName(), tmp);
		Expression incValue = new BinaryExpr(new VarExpr(new Use(index)),
				BinaryOp.PLUS, inc1, IrFactory.eINSTANCE.createTypeInt(32));
		Instruction assign2 = new Assign(tmp, incValue);
		bodyNode.add(assign2);

		Instruction store3 = new Store(writeIndex, new VarExpr(new Use(tmp)));
		bodyNode.add(store3);
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
	private void defineUntaggedBody(VarGlobal readCounter,
			VarGlobal storeList, Procedure body, VarLocal localINPUT) {
		NodeBlock bodyNode = body.getFirst();

		OrderedMap<String, VarLocal> locals = body.getLocals();
		VarLocal counter = new VarLocal(true, 1, new Location(),
				port.getName() + "_Local_counter", readCounter.getType());
		locals.put(counter.getName(), counter);
		Use readCounterUse = new Use(readCounter);
		Instruction load1 = new Load(counter, readCounterUse);
		bodyNode.add(load1);

		VarLocal mask = new VarLocal(true, 1, new Location(), "mask",
				IrFactory.eINSTANCE.createTypeInt(32));
		locals.put(mask.getName(), mask);
		Expression exprmask = new IntExpr(bufferSize - 1);
		Expression maskValue = new BinaryExpr(new VarExpr(new Use(counter)),
				BinaryOp.BITAND, exprmask,
				IrFactory.eINSTANCE.createTypeInt(32));
		Assign assignMask = new Assign(mask, maskValue);
		bodyNode.add(assignMask);

		VarLocal input = new VarLocal(true, 1, new Location(),
				port.getName() + "_Input", port.getType());
		locals.put(input.getName(), input);
		List<Expression> load2Index = new ArrayList<Expression>(1);
		load2Index.add(new IntExpr(0));
		Instruction load2 = new Load(input, new Use(localINPUT), load2Index);
		bodyNode.add(load2);

		List<Expression> store1Index = new ArrayList<Expression>(1);
		Expression e1 = new VarExpr(new Use(counter));

		Expression e2 = new IntExpr(1);
		Expression indexInc = new BinaryExpr(e1, BinaryOp.PLUS, e2,
				readCounter.getType());
		store1Index.add(new VarExpr(new Use(mask)));

		Instruction store1 = new Store(storeList, store1Index, new VarExpr(
				new Use(input)));
		bodyNode.add(store1);

		VarLocal counter2 = new VarLocal(true, 2, new Location(),
				port.getName() + "_Local_counter", readCounter.getType());
		locals.put(counter2.getName(), counter2);
		Instruction assign = new Assign(counter2, indexInc);
		bodyNode.add(assign);

		Instruction store2 = new Store(readCounter, new VarExpr(new Use(
				counter2)));
		bodyNode.add(store2);
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

	private void defineWriteBody(VarGlobal writeCounter,
			VarGlobal writeList, Procedure body, VarLocal OUTPUT) {
		NodeBlock bodyNode = body.getFirst();
		OrderedMap<String, VarLocal> locals = body.getLocals();
		VarLocal counter1 = new VarLocal(true, outputIndex,
				new Location(), port.getName() + "_Local_writeCounter",
				writeCounter.getType());
		locals.put(counter1.getName(), counter1);
		Use writeCounterUse = new Use(writeCounter);
		Instruction load1 = new Load(counter1, writeCounterUse);
		bodyNode.add(load1);

		VarLocal output = new VarLocal(true, outputIndex,
				new Location(), port.getName() + "_LocalOutput", port.getType());
		locals.put(output.getName(), output);
		List<Expression> load2Index = new ArrayList<Expression>(1);
		load2Index.add(new VarExpr(writeCounterUse));
		Instruction load2 = new Load(output, new Use(writeList), load2Index);
		bodyNode.add(load2);

		VarLocal out = new VarLocal(true, outputIndex,
				new Location(), "_LocalTemp", port.getType());
		locals.put(out.getName(), out);
		Use assign1Expr = new Use(output);
		Expression assign1Value = new VarExpr(assign1Expr);
		Instruction assign1 = new Assign(out, assign1Value);
		bodyNode.add(assign1);

		VarLocal counter2 = new VarLocal(true, outputIndex,
				new Location(), port.getName() + "_Local_writeCounter_2",
				writeCounter.getType());
		locals.put(counter2.getName(), counter2);
		Expression assign2IndexElement = new VarExpr(new Use(counter1));
		Expression e2Assign2 = new IntExpr(1);
		Expression assign2Value = new BinaryExpr(assign2IndexElement,
				BinaryOp.PLUS, e2Assign2, IrFactory.eINSTANCE.createTypeInt(32));
		Instruction assign2 = new Assign(counter2, assign2Value);
		bodyNode.add(assign2);

		// locals.put(OUTPUT.getName(), OUTPUT);
		VarExpr store1Expression = new VarExpr(new Use(out));
		List<Expression> store1Index = new ArrayList<Expression>(1);
		store1Index.add(new IntExpr(0));
		Instruction store1 = new Store(OUTPUT, store1Index, store1Expression);
		bodyNode.add(store1);

		Expression store2Expression = new VarExpr(new Use(counter2));
		Instruction store2 = new Store(writeCounter, store2Expression);
		bodyNode.add(store2);
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
	private void modifyActionSchedulability(Action action,
			VarGlobal writeIndex, VarGlobal readIndex, BinaryOp op,
			Expression reference) {
		Procedure scheduler = action.getScheduler();
		NodeBlock bodyNode = scheduler.getLast();
		OrderedMap<String, VarLocal> locals = scheduler.getLocals();

		VarLocal localRead = new VarLocal(true, inputIndex,
				new Location(), "readIndex",
				IrFactory.eINSTANCE.createTypeInt(32));
		locals.put(localRead.getName(), localRead);
		Instruction Load = new Load(localRead, new Use(readIndex));
		int index = 0;
		bodyNode.add(index, Load);
		index++;

		VarLocal localWrite = new VarLocal(true, inputIndex,
				new Location(), "writeIndex",
				IrFactory.eINSTANCE.createTypeInt(32));
		locals.put(localWrite.getName(), localWrite);
		Instruction Load2 = new Load(localWrite, new Use(writeIndex));
		bodyNode.add(index, Load2);
		index++;

		VarLocal diff = new VarLocal(true, inputIndex,
				new Location(), "diff", IrFactory.eINSTANCE.createTypeInt(32));
		locals.put(diff.getName(), diff);
		Expression value = new BinaryExpr(new VarExpr(new Use(readIndex)),
				BinaryOp.MINUS, new VarExpr(new Use(writeIndex)),
				IrFactory.eINSTANCE.createTypeInt(32));
		Instruction assign = new Assign(diff, value);
		bodyNode.add(index, assign);
		index++;

		VarLocal conditionVar = new VarLocal(true, inputIndex,
				new Location(), "condition",
				IrFactory.eINSTANCE.createTypeBool());
		locals.put(conditionVar.getName(), conditionVar);
		Expression value2 = new BinaryExpr(new VarExpr(new Use(diff)), op,
				reference, IrFactory.eINSTANCE.createTypeBool());
		Instruction assign2 = new Assign(conditionVar, value2);
		bodyNode.add(index, assign2);
		index++;

		VarLocal myResult = new VarLocal(true, inputIndex,
				new Location(), "myResult",
				IrFactory.eINSTANCE.createTypeBool());
		locals.put(myResult.getName(), myResult);
		int returnIndex = bodyNode.getInstructions().size() - 1;
		Return actionReturn = (Return) bodyNode.getInstructions().get(
				returnIndex);
		Expression returnExpr = actionReturn.getValue();
		// VarLocal currentResult
		Expression e = new BinaryExpr(returnExpr, BinaryOp.LOGIC_AND,
				new VarExpr(new Use(conditionVar)),
				IrFactory.eINSTANCE.createTypeBool());
		Instruction assign3 = new Assign(myResult, e);

		bodyNode.add(returnIndex, assign3);
		actionReturn.setValue(new VarExpr(new Use(myResult)));
	}

	/**
	 * This method changes the schedulability of the done action
	 * 
	 * @param counter
	 *            Global Var counter
	 */
	private void modifyDoneAction(VarGlobal counter, int portIndex) {

		NodeBlock blkNode = done.getBody().getFirst();
		Expression storeValue = new IntExpr(0);
		Instruction store = new Store(counter, storeValue);
		blkNode.add(store);

		blkNode = done.getScheduler().getFirst();
		OrderedMap<String, VarLocal> schedulerLocals = done.getScheduler()
				.getLocals();
		VarLocal localCounter = new VarLocal(true, portIndex,
				new Location(), "localCounterModif", counter.getType());
		schedulerLocals.put(localCounter.getName(), localCounter);

		Instruction load = new Load(localCounter, new Use(counter));
		blkNode.add(1, load);

		VarLocal temp = new VarLocal(true, portIndex, new Location(),
				"temp", IrFactory.eINSTANCE.createTypeBool());
		schedulerLocals.put(temp.getName(), temp);
		Expression guardValue = new IntExpr(numTokens);
		Expression counterExpression = new VarExpr(new Use(localCounter));
		Expression schedulerValue = new BinaryExpr(counterExpression,
				BinaryOp.EQ, guardValue, IrFactory.eINSTANCE.createTypeBool());
		Instruction assign = new Assign(temp, schedulerValue);
		int index = blkNode.getInstructions().size() - 1;
		blkNode.add(index, assign);
		index++;

		Expression buffrerExpression = new VarExpr(new Use(result));
		Expression resultExpression = new VarExpr(new Use(temp));
		Expression expression = new BinaryExpr(buffrerExpression,
				BinaryOp.LOGIC_AND, resultExpression,
				IrFactory.eINSTANCE.createTypeBool());
		Instruction bufferAssign = new Assign(result, expression);
		blkNode.add(index, bufferAssign);
	}

	/**
	 * This method creates a clone action that has the same behavior than a
	 * reference action but stores tokens from a specific buffer in spite of a
	 * port
	 * 
	 */
	private void modifyNoRepeatActions() {
		DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
		Set<UniqueEdge> edges = graph.edgeSet();
		for (UniqueEdge edge : edges) {
			State source = graph.getEdgeSource(edge);
			String sourceName = source.getName();
			State target = graph.getEdgeTarget(edge);
			String targetName = target.getName();
			Action action = (Action) edge.getObject();
			int cloneIndex = transitionPosition(fsm.getTransitions(sourceName),
					action);
			if (NoRepeatActions.contains(action)) {
				for (Entry<Port, Var> entry : action.getInputPattern()
						.getVariableMap().entrySet()) {
					Port port = entry.getKey();
					if (inputPorts.contains(port)) {
						if (!NoRepeatActionsDone.contains(action)) {
							Action cloneAction = createCloneAction(action);
							cloneActions.add(cloneAction);
							Procedure body = cloneAction.getBody();
							int position = portPosition(inputPorts, port);
							VarGlobal buffer = inputBuffers.get(position);
							VarGlobal writeIndex = writeIndexes
									.get(position);
							VarGlobal readIndex = readIndexes
									.get(position);

							//
							//ModifyCloneBody modifyCloneBody = new ModifyCloneBody(
									//buffer, writeIndex, readIndex, port, body , bufferSize, cloneAction);
							//modifyCloneBody.visit(cloneAction.getBody());
							//removePortFromPattern(
									//cloneAction.getInputPattern(), port);
							ModifyActionScheduler modifyActionScheduler = new ModifyActionScheduler(
									buffer, writeIndex, port, bufferSize);
							modifyActionScheduler.visit(cloneAction
									.getScheduler());
							modifyActionSchedulability(cloneAction, writeIndex,
									readIndex, BinaryOp.NE, new IntExpr(0));
							fsm.addTransition(sourceName, cloneAction,
									targetName);

						} else {
							int actionIndex = actionPosition(
									NoRepeatActionsDone, action);
							Action clone = cloneActions.get(actionIndex);
							fsm.addTransition(sourceName, clone, targetName);
						}
						int length = fsm.getTransitions(sourceName).size();
						NextStateInfo info = fsm.getTransitions(sourceName)
								.get(length - 1);
						fsm.getTransitions(sourceName).add(cloneIndex, info);
						fsm.getTransitions(sourceName).remove(length);
					}
				}
				NoRepeatActionsDone.add(action);
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
	private void moveLocals(Iterator<VarLocal> itVar, Procedure newProc) {
		while (itVar.hasNext()) {
			VarLocal var = itVar.next();
			itVar.remove();
			newProc.getLocals().put(var.getName(), var);
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
	 * This method clones the output patterns from a source action to a target
	 * one
	 * 
	 * @param source
	 *            source action
	 * @param target
	 *            target action
	 */
	private void moveOutputPattern(Action source, Action target) {
		Pattern targetPattern = target.getOutputPattern();
		Pattern sourcePattern = source.getOutputPattern();
		targetPattern.getNumTokensMap().putAll(sourcePattern.getNumTokensMap());
		targetPattern.getPorts().addAll(sourcePattern.getPorts());
		targetPattern.getVariableMap().putAll(sourcePattern.getVariableMap());
		targetPattern.getInverseVariableMap().putAll(
				sourcePattern.getInverseVariableMap());
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
		optimalSize = closestPow_2(size) * 8;
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
	 * returns the position of an action in an actions list
	 * 
	 * @param list
	 *            list of actions
	 * @param seekAction
	 *            researched action
	 * @return position of a the action in a list
	 */
	private int actionPosition(List<Action> list, Action seekAction) {
		int position = 0;
		for (Action action : list) {
			if (action == seekAction) {
				break;
			} else {
				position++;
			}
		}
		return position;
	}

	/**
	 * returns the position of an action transition in a transitions list
	 * 
	 * @param list
	 *            transitions list
	 * @param action
	 *            researched action
	 * @return position of the transition
	 */
	private int transitionPosition(List<NextStateInfo> list, Action action) {
		int position = 0;
		for (NextStateInfo info : list) {
			if (info.getAction() == action) {
				break;
			} else {
				position++;
			}
		}
		return position;
	}

	/**
	 * This method removes a port from a pattern
	 * 
	 * @param pattern
	 *            pattern containing the port
	 * @param port
	 *            port to remove
	 */
	private void removePortFromPattern(Pattern pattern, Port port, VarLocal tmp) {
		pattern.getVariableMap().remove(port);
		pattern.getPeekedMap().remove(port);
		pattern.getInverseVariableMap().remove(tmp);
		pattern.getNumTokensMap().remove(port);
		pattern.getPorts().remove(port);
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
	private void scanInputs(Action action, String sourceName, String targetName) {
		for (Entry<Port, Integer> verifEntry : action.getInputPattern()
				.getNumTokensMap().entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			if (verifNumTokens > 1) {
				repeatInput = true;
				// create new process action
				process = createProcessAction(action);
				String storeName = "newStateStore" + action.getName();
				String processName = "newStateProcess" + action.getName();
				fsm.addState(processName);
				fsm.addTransition(processName, process, targetName);
				// move action's Output pattern to new process action
				moveOutputPattern(action, process);
				// create a list to store the treated input ports

				VarGlobal untagBuffer = new VarGlobal(new Location(),
						entryType, "buffer", true);
				VarGlobal untagReadIndex = new VarGlobal(
						new Location(), entryType, "index", true);
				VarGlobal untagWriteIndex = new VarGlobal(
						new Location(), entryType, "index", true);
				// if input repeat detected --> treat all input ports
				for (Entry<Port, Integer> entry : action.getInputPattern()
						.getNumTokensMap().entrySet()) {
					numTokens = entry.getValue();
					inputIndex = inputIndex + 1;
					port = entry.getKey();
					bufferSize = 512;// OptimalBufferSize(action, port);
					entryType = entry.getKey().getType();

					if (inputPorts.contains(port)) {
						int position = portPosition(inputPorts, port);
						untagBuffer = inputBuffers.get(position);
						untagReadIndex = readIndexes.get(position);
						untagWriteIndex = writeIndexes.get(position);
					} else {
						inputPorts.add(port);
						untagBuffer = createTab(port.getName() + "_buffer",
								entryType, bufferSize);
						inputBuffers.add(untagBuffer);
						untagReadIndex = createCounter("readIndex_"
								+ port.getName());
						readIndexes.add(untagReadIndex);
						untagWriteIndex = createCounter("writeIndex_"
								+ port.getName());
						writeIndexes.add(untagWriteIndex);
						untagged = createUntaggedAction(untagReadIndex,
								untagBuffer);
						// actor.getActionScheduler().getActions().add(untagged);
						fsm.addTransition(sourceName, untagged, sourceName);
					}
					String counterName = action.getName() + "NewStoreCounter"
							+ inputIndex;
					VarGlobal counter = createCounter(counterName);
					String listName = action.getName() + "NewStoreList"
							+ inputIndex;
					VarGlobal tab = createTab(listName, entryType,
							numTokens);
					store = createStoreAction(action.getName(), counter, tab,
							untagBuffer, untagWriteIndex);
					ModifyProcessActionStore modifyProcessAction = new ModifyProcessActionStore(
							tab);
					modifyProcessAction.visit(process.getBody());
					fsm.addState(storeName);
					fsm.addTransition(storeName, store, storeName);

					// create a new store done action once
					if (inputIndex == 1) {
						done = createDoneAction(action.getName()
								+ "newStoreDone", counter, numTokens);
						fsm.addTransition(storeName, done, processName);
					} else {
						// the new done action already exists --> modify
						// schedulability
						modifyDoneAction(counter, inputIndex);
					}
					actionToTransition(action, untagBuffer, untagWriteIndex,
							untagReadIndex);
				}

				action.getInputPattern().clear();
				action.getBody().getNodes().clear();
				NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();
				block = IrFactoryImpl.eINSTANCE.createNodeBlock();
				block.add(new Return(null));
				action.getBody().getNodes().add(block);
				fsm.replaceTarget(sourceName, action, storeName);

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
	private void scanOutputs(Action action, String sourceName, String targetName) {
		for (Entry<Port, Integer> verifEntry : action.getOutputPattern()
				.getNumTokensMap().entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			if (verifNumTokens > 1) {
				repeatOutput = true;
				String processName = "newStateProcess" + action.getName();
				String writeName = "newStateWrite" + action.getName();

				// create new process action if not created while treating
				// inputs
				if (!repeatInput) {
					process = createProcessAction(action);
					fsm.addTransition(sourceName, process, writeName);
					this.actor.getActions().remove(action);
				} else {
					fsm.replaceTarget(processName, process, writeName);
					process.getOutputPattern().clear();
				}
				for (Entry<Port, Integer> entry : action.getOutputPattern()
						.getNumTokensMap().entrySet()) {
					numTokens = entry.getValue();
					outputIndex = outputIndex + 1;
					port = entry.getKey();
					entryType = entry.getKey().getType();
					String counterName = action.getName() + "NewWriteCounter"
							+ outputIndex;
					VarGlobal counter = createCounter(counterName);
					String listName = action.getName() + "NewWriteList"
							+ outputIndex;
					VarGlobal tab = createTab(listName, entryType,
							numTokens);
					write = createWriteAction(action.getName(), counter, tab);
					write.getOutputPattern().setNumTokens(port, 1);

					ModifyProcessActionWrite modifyProcessActionWrite = new ModifyProcessActionWrite(
							tab);
					modifyProcessActionWrite.visit(process.getBody());
					fsm.addState(writeName);
					fsm.addTransition(writeName, write, writeName);

					// create a new write done action once
					if (outputIndex == 1) {
						done = createDoneAction(action.getName()
								+ "newWriteDone", counter, numTokens);
						fsm.addTransition(writeName, done, targetName);

					} else {
						modifyDoneAction(counter, outputIndex);
					}
				}
				// remove outputPattern from transition action
				action.getOutputPattern().clear();
				break;
			}
		}
		outputIndex = 0;
	}

	@Override
	public void visit(Actor actor) {
		this.actor = actor;
		fsm = actor.getActionScheduler().getFsm();
		List<Action> actions = new ArrayList<Action>(actor.getActionScheduler()
				.getActions());

		if (fsm == null) {
			// no FSM: simply visit all the actions
			addFsm();
			for (Action action : actions) {
				String sourceName = "init";
				String targetName = "init";
				visitTransition(sourceName, targetName, action);
			}
		} else {
			// with an FSM: visits all transitions
			DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
			Set<UniqueEdge> edges = graph.edgeSet();
			for (UniqueEdge edge : edges) {
				State source = graph.getEdgeSource(edge);
				String sourceName = source.getName();

				State target = graph.getEdgeTarget(edge);
				String targetName = target.getName();

				Action action = (Action) edge.getObject();
				visitTransition(sourceName, targetName, action);
			}
		}
		modifyNoRepeatActions();
	}

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
	private void visitTransition(String sourceName, String targetName,
			Action action) {
		createActionsSet(action, sourceName, targetName);
		if (!repeatInput && !repeatOutput && !NoRepeatActions.contains(action)) {
			NoRepeatActions.add(action);
		}
		repeatInput = false;
		repeatOutput = false;
	}
}