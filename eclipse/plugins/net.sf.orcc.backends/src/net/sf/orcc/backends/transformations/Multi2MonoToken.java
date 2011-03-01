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

import net.sf.orcc.backends.vhdl.transformations.ActionSplitter;
import net.sf.orcc.backends.vhdl.transformations.CodeMover;
import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a visitor that transforms multi-token to mono-token data
 * transfer
 * 
 * @author Khaled Jerbi
 * 
 */
public class Multi2MonoToken extends ActionSplitter {
	/**
	 * This class defines a visitor that substitutes process variable names with
	 * those of the newly defined actions
	 * 
	 * @author Khaled Jerbi
	 * 
	 */
	private class ModifyProcessAction extends AbstractActorVisitor {

		private GlobalVariable tab;

		private Variable tempTab;

		public ModifyProcessAction(GlobalVariable tab) {
			this.tab = tab;
		}

		@Override
		public void visit(Load load) {
			Use useArray = new Use(tab);
			if (load.getSource().getVariable().getName().equals(port.getName())) {
				load.setSource(useArray);
				for (Use use : load.getTarget().getUses()) {
					if (use.getNode().isInstruction()) {
						Instruction instruction = (Instruction) use.getNode();
						if (instruction.isStore()) {
							Store storeInstruction = (Store) instruction;
							tempTab = storeInstruction.getTarget();
						}
					}
				}
			}
			if (load.getSource().getVariable().equals(tempTab)) {
				load.getSource().setVariable(tab);
			}
		}

		@Override
		public void visit(Read read) {
			itInstruction.remove();
		}

		@Override
		public void visit(Store store) {
			if (store.getTarget().equals(tempTab)) {
				store.setTarget(tab);
			}
		}
	}

	private Action done;

	private Type entryType;

	private int inputIndex = 0;

	private int numTokens;

	private Port port;

	private Action process;

	private boolean repeatInput = false;

	private LocalVariable result;

	private Action store;

	/**
	 * This method creates an action with the given name.
	 * 
	 * @param name
	 *            name of the action
	 * @return a new action created with the given name
	 */
	private Action createAction(Expression condition, String name) {
		// scheduler
		Procedure scheduler = new Procedure("isSchedulable_" + name,
				new Location(), IrFactory.eINSTANCE.createTypeBool());
		LocalVariable result = scheduler.newTempLocalVariable(
				this.actor.getFile(), IrFactory.eINSTANCE.createTypeBool(),
				"result");
		result.setIndex(1);
		scheduler.getLocals().remove(result.getBaseName());
		scheduler.getLocals().put(result.getName(), result);

		BlockNode block = new BlockNode(scheduler);
		block.add(new Assign(result, condition));
		block.add(new Return(new VarExpr(new Use(result))));
		scheduler.getNodes().add(block);

		// body
		Procedure body = new Procedure(name, new Location(),
				IrFactory.eINSTANCE.createTypeVoid());
		block = new BlockNode(body);
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
	 * This method creates the required Store, done and process actions
	 * 
	 * @param action
	 *            the action getting transformed
	 */
	public void createActionsSet(Action action, String sourceName,
			String targetName) {
		for (Entry<Port, Integer> verifEntry : action.getInputPattern()
				.entrySet()) {
			int verifNumTokens = verifEntry.getValue();
			if (verifNumTokens > 1) {
				repeatInput = true;
				process = createProcessAction(action);
				String processName = "newStateProcess" + action.getName();
				addTransition(processName, targetName, process);

				// remove transformed action
				for (Entry<Port, Integer> entry : action.getInputPattern()
						.entrySet()) {
					numTokens = entry.getValue();
					inputIndex = inputIndex + 1;
					port = entry.getKey();
					entryType = entry.getKey().getType();

					repeatInput = true;
					String counterName = action.getName() + "NewReadCounter"
							+ inputIndex;
					GlobalVariable counter = createCounter(counterName);
					String listName = action.getName() + "NewStoreList"
							+ inputIndex;
					GlobalVariable tab = createTab(listName, numTokens,
							entryType);
					store = createStoreAction(action.getName(), numTokens,
							port, counter, tab);
					store.getInputPattern().put(port, 1);

					ModifyProcessAction modifyProcessAction = new ModifyProcessAction(
							tab);
					modifyProcessAction.visit(process.getBody());

					addTransition(sourceName, sourceName, store);

					if (inputIndex == 1) {
						done = createDoneAction(action.getName(), counter,
								numTokens);
						addTransition(sourceName, processName, done);
					} else {
						modifyDoneAction(counter);
					}
				}
				this.actor.getActions().remove(0);
				break;
			}
		}
	}

	/**
	 * This method creates a global variable counter for store with the given
	 * name.
	 * 
	 * @param name
	 *            name of the counter
	 * @return new counter with the given name
	 */
	private GlobalVariable createCounter(String name) {
		GlobalVariable newCounter = new GlobalVariable(new Location(),
				IrFactory.eINSTANCE.createTypeInt(16), name, true);
		Expression expression = new IntExpr(0);
		newCounter.setInitialValue(expression);
		actor.getStateVars().put(newCounter.getName(), newCounter);
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
	 * @return
	 */
	private Action createDoneAction(String actionName, GlobalVariable counter,
			int numTokens) {
		// body
		String name = actionName + "NewDone";
		Procedure body = new Procedure(name, new Location(),
				IrFactory.eINSTANCE.createTypeVoid());
		BlockNode block = new BlockNode(body);
		Store store = new Store(counter, new IntExpr(0));
		block.add(store);
		block.add(new Return(null));
		body.getNodes().add(block);

		// scheduler
		Procedure scheduler = new Procedure("isSchedulable_" + name,
				new Location(), IrFactory.eINSTANCE.createTypeBool());
		LocalVariable temp = scheduler.newTempLocalVariable(
				this.actor.getFile(), IrFactory.eINSTANCE.createTypeBool(),
				"temp");
		temp.setIndex(1);
		scheduler.getLocals().remove(temp.getBaseName());
		scheduler.getLocals().put(temp.getName(), temp);
		result = new LocalVariable(true, 0, new Location(), "result",
				IrFactory.eINSTANCE.createTypeBool());
		scheduler.getLocals().put(result.getName(), result);
		LocalVariable localCounter = new LocalVariable(true, 1, new Location(),
				"localCounter", counter.getType());
		scheduler.getLocals().put(localCounter.getName(), localCounter);
		block = new BlockNode(scheduler);
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
		CodeMover codeMover = new CodeMover();

		ListIterator<CFGNode> listIt = action.getBody().getNodes()
				.listIterator();
		codeMover.setTargetProcedure(body);
		codeMover.moveNodes(listIt);
		Iterator<LocalVariable> it = action.getBody().getLocals().iterator();
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
	private Action createStoreAction(String actionName, int numTokens,
			Port port, GlobalVariable readCounter, GlobalVariable storeList) {
		String storeName = actionName + port.getName() + "_NewStore";
		Expression guardValue = new IntExpr(numTokens);
		Expression counterExpression = new VarExpr(new Use(readCounter));
		Expression expression = new BinaryExpr(counterExpression, BinaryOp.LT,
				guardValue, IrFactory.eINSTANCE.createTypeBool());
		Action newStoreAction = createAction(expression, storeName);
		defineStoreBody(port, readCounter, storeList, newStoreAction.getBody());
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
	private GlobalVariable createTab(String name, int numTokens, Type entryType) {
		Type type = IrFactory.eINSTANCE.createTypeList(numTokens, entryType);
		GlobalVariable newList = new GlobalVariable(new Location(), type, name,
				true);
		actor.getStateVars().put(newList.getName(), newList);
		return newList;
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
	private void defineStoreBody(Port port, GlobalVariable readCounter,
			GlobalVariable storeList, Procedure body) {
		BlockNode bodyNode = BlockNode.getFirst(body);

		OrderedMap<String, LocalVariable> locals = body.getLocals();
		LocalVariable counter = new LocalVariable(true, 1, new Location(),
				port.getName() + "_Local_counter", readCounter.getType());
		locals.put(counter.getName(), counter);
		Use readCounterUse = new Use(readCounter);
		Instruction load1 = new Load(counter, readCounterUse);
		bodyNode.add(load1);

		LocalVariable localINPUT = new LocalVariable(true, 0, new Location(),
				port.getName(), IrFactory.eINSTANCE.createTypeList(1,
						port.getType()));
		locals.put(localINPUT.getName(), localINPUT);
		Instruction read = new Read(port, 1, localINPUT);
		localINPUT.setInstruction(read);
		bodyNode.add(read);

		LocalVariable input = new LocalVariable(true, 1, new Location(),
				port.getName() + "_Input", port.getType());
		locals.put(input.getName(), input);
		List<Expression> load2Index = new ArrayList<Expression>(1);
		load2Index.add(new IntExpr(0));
		Instruction load2 = new Load(input, new Use(localINPUT), load2Index);
		bodyNode.add(load2);

		List<Expression> store1Index = new ArrayList<Expression>(1);
		store1Index.add(new VarExpr(new Use(counter)));
		Instruction store1 = new Store(storeList, store1Index, new VarExpr(
				new Use(input)));
		bodyNode.add(store1);

		LocalVariable counter2 = new LocalVariable(true, 2, new Location(),
				port.getName() + "_Local_counter", readCounter.getType());
		locals.put(counter2.getName(), counter2);
		Expression storeIndexElement = new VarExpr(new Use(counter));
		Expression e2 = new IntExpr(1);
		Expression assignValue = new BinaryExpr(storeIndexElement,
				BinaryOp.PLUS, e2, entryType);
		Instruction assign = new Assign(counter2, assignValue);
		bodyNode.add(assign);

		Instruction store2 = new Store(readCounter, new VarExpr(new Use(
				counter2)));
		bodyNode.add(store2);
	}

	/**
	 * This method changes the schedulability of the done action
	 * 
	 * @param counter
	 *            Global Variable counter
	 */
	private void modifyDoneAction(GlobalVariable counter) {

		BlockNode blkNode = BlockNode.getFirst(done.getBody());
		Expression storeValue = new IntExpr(0);
		Instruction store = new Store(counter, storeValue);
		blkNode.add(store);

		blkNode = BlockNode.getFirst(done.getScheduler());
		OrderedMap<String, LocalVariable> schedulerLocals = done.getScheduler()
				.getLocals();
		LocalVariable localCounter = new LocalVariable(true, inputIndex,
				new Location(), "localCounter", counter.getType());
		schedulerLocals.put(localCounter.getName(), localCounter);

		Instruction load = new Load(localCounter, new Use(counter));
		blkNode.add(1, load);

		LocalVariable temp = new LocalVariable(true, inputIndex,
				new Location(), "temp", IrFactory.eINSTANCE.createTypeBool());
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
	 * This method moves the local variables of a procedure to another using a
	 * LocalVariable iterator
	 * 
	 * @param itVar
	 *            source LocalVariable iterator
	 * @param newProc
	 *            target procedure
	 */
	public void moveLocals(Iterator<LocalVariable> itVar, Procedure newProc) {
		while (itVar.hasNext()) {
			LocalVariable var = itVar.next();
			itVar.remove();
			newProc.getLocals().put(var.getName(), var);
		}
	}

	@Override
	public void visit(Actor actor) {
		super.visit(actor);
		visitAllActions();
	}

	@Override
	protected void visit(String sourceName, String targetName, Action action) {
		createActionsSet(action, sourceName, targetName);
		if (repeatInput) {
			removeTransition(sourceName, action);
		}
	}

}
