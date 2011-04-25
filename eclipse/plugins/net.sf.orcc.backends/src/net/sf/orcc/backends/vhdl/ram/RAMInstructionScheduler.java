/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl.ram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.instructions.InstRamRead;
import net.sf.orcc.backends.instructions.InstRamSetAddress;
import net.sf.orcc.backends.instructions.InstRamWrite;
import net.sf.orcc.backends.instructions.InstSplit;
import net.sf.orcc.backends.instructions.InstructionsFactory;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Predicate;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

/**
 * This class defines a visitor that transforms loads and stores to RAM
 * operations.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class RAMInstructionScheduler extends AbstractActorVisitor<Object> {

	private Map<RAM, List<InstRamRead>> pendingReads;

	private Map<Var, RAM> ramMap;

	/**
	 * current list of instructions
	 */
	private List<Instruction> instructions;

	/**
	 * Adds a RamSetAddress instruction before the previous SplitInstruction.
	 * 
	 * @param ram
	 *            RAM
	 * @param var
	 *            variable
	 * @param indexes
	 *            list of expressions
	 */
	private void addEarlySetAddress(RAM ram, Var var, List<Expression> indexes) {
		int port = ram.getLastPortUsed() % 2 + 1;
		InstRamSetAddress rsa = InstructionsFactory.eINSTANCE
				.createInstRamSetAddress(port, var, indexes);
		rsa.setPredicate(EcoreHelper.copy(ram.getPredicate()));

		// insert the RSA before the previous split instruction
		for (int i = indexInst - 1; i > 0; i--) {
			Instruction instruction = instructions.get(i);
			if (instruction instanceof InstSplit) {
				instructions.add(i, rsa);
				break;
			}
		}
		indexInst++;
	}

	/**
	 * Adds a RamRead to the list of pending reads on the given RAM from an
	 * InstLoad.
	 * 
	 * @param ram
	 *            a RAM
	 * @param load
	 *            the InstLoad used to create the InstRamRead
	 */
	private void addPendingRead(RAM ram, InstLoad load) {
		int port = ram.getLastPortUsed() % 2 + 1;
		InstRamRead read = InstructionsFactory.eINSTANCE.createInstRamRead(
				port, load.getSource().getVariable(), load.getTarget()
						.getVariable());
		read.setPredicate(EcoreHelper.copy(ram.getPredicate()));

		// add pending read to the list
		pendingReads.get(ram).add(read);
	}

	/**
	 * Adds a RamSetAddress instruction at the current index.
	 * 
	 * @param ram
	 *            RAM
	 * @param var
	 *            variable
	 * @param indexes
	 *            list of expressions
	 */
	private void addSetAddress(RAM ram, Var var, List<Expression> indexes) {
		int port = ram.getLastPortUsed() % 2 + 1;
		InstRamSetAddress rsa = InstructionsFactory.eINSTANCE
				.createInstRamSetAddress(port, var, indexes);
		rsa.setPredicate(EcoreHelper.copy(ram.getPredicate()));
		instructions.add(indexInst++, rsa);
	}

	/**
	 * Adds a split instruction at the current index in instructions.
	 * 
	 * @param ram
	 *            a RAM
	 */
	private void addSplitInstruction(RAM ram) {
		InstSplit instSplit = InstructionsFactory.eINSTANCE.createInstSplit();
		instSplit.setPredicate(EcoreHelper.copy(ram.getPredicate()));
		instructions.add(indexInst++, instSplit);
	}

	/**
	 * Adds a RamWrite instruction at the current index.
	 * 
	 * @param predicate
	 *            a predicate
	 * @param ram
	 *            a RAM
	 * @param store
	 *            the InstStore used to create the InstRamWrite
	 */
	private void addWrite(RAM ram, InstStore store) {
		int port = ram.getLastPortUsed() % 2 + 1;
		InstRamWrite write = InstructionsFactory.eINSTANCE.createInstRamWrite(
				port, store.getTarget().getVariable(), store.getValue());
		write.setPredicate(EcoreHelper.copy(ram.getPredicate()));
		instructions.add(indexInst++, write);
	}

	@Override
	public Object caseActor(Actor actor) {
		pendingReads = new HashMap<RAM, List<InstRamRead>>();
		ramMap = new HashMap<Var, RAM>();
		for (Var variable : actor.getStateVars()) {
			if (variable.isAssignable() && variable.getType().isList()) {
				RAM ram = new RAM();
				ramMap.put(variable, ram);

				// initialize the list of pending reads for this RAM
				pendingReads.put(ram, new ArrayList<InstRamRead>(2));
			}
		}

		for (Action action : actor.getActions()) {
			this.action = action;
			doSwitch(action.getBody());
		}

		return null;
	}

	@Override
	public Object caseInstLoad(InstLoad load) {
		Var var = load.getSource().getVariable();
		if (!load.getIndexes().isEmpty() && var.isAssignable()
				&& var.isGlobal()) {
			convertLoad(load);
			EcoreHelper.delete(load);
			indexInst--;
		}
		return null;
	}

	@Override
	public Object caseInstStore(InstStore store) {
		Var var = store.getTarget().getVariable();
		if (!store.getIndexes().isEmpty() && var.isGlobal()) {
			convertStore(store);
			EcoreHelper.delete(store);
			indexInst--;
		}
		return null;
	}

	@Override
	public Object caseProcedure(Procedure procedure) {
		super.caseProcedure(procedure);

		NodeBlock block = procedure.getLast();
		for (RAM ram : ramMap.values()) {
			// set the RAM as "never accessed"
			ram.reset();

			boolean hasMorePendingReads;
			instructions = block.getInstructions();
			indexInst = instructions.size() - 1; // before the return
			do {
				hasMorePendingReads = executeTwoPendingReads(ram);
			} while (hasMorePendingReads);
		}

		return null;
	}

	/**
	 * Converts the given Load to RAM instructions.
	 * 
	 * @param load
	 *            a Load
	 */
	private void convertLoad(InstLoad load) {
		instructions = EcoreHelper.getContainingList(load);
		List<Expression> indexes = load.getIndexes();
		Var var = load.getSource().getVariable();
		Predicate predicate = load.getPredicate();

		RAM ram = ramMap.get(var);
		if (ram.isLastAccessRead() && ram.getPredicate().isSameAs(predicate)) {
			int port = ram.getLastPortUsed() + 1;
			ram.setLastPortUsed(port);

			if (port < 2) {
				addSetAddress(ram, var, indexes);
			} else {
				addEarlySetAddress(ram, var, indexes);
			}
			addPendingRead(ram, load);

			if (port % 2 == 1) {
				// two ports have been used
				executeTwoPendingReads(ram);
			}
		} else {
			// udpate state of RAM
			ram.setLastAccessRead();
			ram.setLastPortUsed(0);
			ram.setPredicate(predicate);

			// set address and add pending read
			addSetAddress(ram, var, indexes);
			addPendingRead(ram, load);
		}
	}

	/**
	 * Converts the given Store to RAM instructions.
	 * 
	 * @param store
	 *            a Store
	 */
	private void convertStore(InstStore store) {
		instructions = EcoreHelper.getContainingList(store);
		List<Expression> indexes = store.getIndexes();
		Var var = store.getTarget().getVariable();
		Predicate predicate = store.getPredicate();

		RAM ram = ramMap.get(var);
		int port;
		if (ram.isLastAccessWrite() && ram.getPredicate().isSameAs(predicate)) {
			port = ram.getLastPortUsed() + 1;
			if (port > 0 && port % 2 == 0) {
				// port == 2, 4, 6, 8...
				addSplitInstruction(ram);
			}
		} else {
			port = 0;
		}

		// update state of RAM
		ram.setLastAccessWrite();
		ram.setLastPortUsed(port);
		ram.setPredicate(predicate);

		// set address and write
		addSetAddress(ram, var, indexes);
		addWrite(ram, store);
	}

	/**
	 * Executes at most two pending reads. This method inserts split
	 * instructions as necessary before the reads.
	 * 
	 * @param instructions
	 *            a list of instructions that this method should add
	 *            instructions to
	 * @param predicate
	 *            predicate of instructions
	 * @param ram
	 *            a RAM
	 * @return <code>true</code> if there are still pending reads
	 */
	private boolean executeTwoPendingReads(RAM ram) {
		List<InstRamRead> reads = pendingReads.get(ram);
		if (reads.isEmpty()) {
			return false;
		} else {
			if (ram.getLastPortUsed() < 2) {
				addSplitInstruction(ram);
			}

			addSplitInstruction(ram);

			Iterator<InstRamRead> it = reads.iterator();
			for (int i = 0; it.hasNext() && i < 2; i++) {
				instructions.add(indexInst++, it.next());
				it.remove();
			}

			return it.hasNext();
		}
	}

}
