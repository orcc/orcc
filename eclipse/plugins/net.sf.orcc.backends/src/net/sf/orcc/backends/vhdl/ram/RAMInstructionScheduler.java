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
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;

/**
 * This class defines a visitor that transforms loads and stores to RAM
 * operations.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class RAMInstructionScheduler extends AbstractActorVisitor {

	private Map<RAM, List<InstRamRead>> pendingReads;

	private Map<Var, RAM> ramMap;

	/**
	 * Adds a RamSetAddress instruction before the previous SplitInstruction.
	 * 
	 * @param ram
	 *            RAM
	 * @param indexes
	 *            list of indexes
	 * @param var
	 *            variable
	 */
	private void addEarlySetAddress(List<Expression> indexes, int port, Var var) {
		InstRamSetAddress rsa = InstructionsFactory.eINSTANCE
				.createInstRamSetAddress(port, var, indexes);

		// save index
		int index = itInstruction.nextIndex() + 1;

		// insert the RSA before the previous split instruction
		while (itInstruction.hasPrevious()) {
			Instruction instruction = itInstruction.previous();
			if (instruction instanceof InstSplit) {
				itInstruction.add(rsa);
				break;
			}
		}

		// back to index
		while (itInstruction.nextIndex() != index) {
			itInstruction.next();
		}
	}

	/**
	 * Adds a RamRead to the list of pending reads on the given RAM.
	 * 
	 * @param ram
	 *            a RAM
	 * @param load
	 *            a Load
	 * @param port
	 *            a port
	 */
	private void addPendingRead(RAM ram, InstLoad load, int port) {
		InstRamRead read = InstructionsFactory.eINSTANCE.createInstRamRead(
				port, load.getSource().getVariable(), load.getTarget()
						.getVariable());

		List<InstRamRead> reads = pendingReads.get(ram);
		reads.add(read);
	}

	/**
	 * Adds a RamSetAddress instruction.
	 * 
	 * @param indexes
	 *            list of indexes
	 * @param port
	 *            port used
	 * @param var
	 *            variable
	 */
	private void addSetAddress(List<Expression> indexes, int port, Var var) {
		InstRamSetAddress rsa = InstructionsFactory.eINSTANCE
				.createInstRamSetAddress(port, var, indexes);
		itInstruction.add(rsa);
	}

	/**
	 * Adds a SplitInstruction.
	 * 
	 */
	private void addSplitInstruction() {
		itInstruction.add(InstructionsFactory.eINSTANCE.createInstSplit());
	}

	/**
	 * Adds a RamWrite instruction.
	 * 
	 * @param ram
	 *            a RAM
	 * @param store
	 *            a Store
	 * @param port
	 *            a port
	 */
	private void addWrite(RAM ram, InstStore store, int port) {
		InstRamWrite write = InstructionsFactory.eINSTANCE.createInstRamWrite(
				port, store.getTarget().getVariable(), store.getValue());
		itInstruction.add(write);
	}

	/**
	 * Converts the given Load to RAM instructions.
	 * 
	 * @param load
	 *            a Load
	 */
	private void convertLoad(InstLoad load) {
		List<Expression> indexes = load.getIndexes();
		Var var = load.getSource().getVariable();

		RAM ram = ramMap.get(var);
		if (ram.isLastAccessRead()) {
			int port = ram.getLastPortUsed() + 1;
			ram.setLastPortUsed(port);

			int ajustedPort = port % 2 + 1;
			if (ram.isWaitCycleNeeded()) {
				addSetAddress(indexes, ajustedPort, var);
			} else {
				addEarlySetAddress(indexes, ajustedPort, var);
			}
			addPendingRead(ram, load, ajustedPort);

			if (port % 2 == 1) {
				// two ports have been used
				if (ram.isWaitCycleNeeded()) {
					addSplitInstruction();
					ram.setWaitCycleNeeded(false);
				}

				addSplitInstruction();
				executeTwoPendingReads(ram);
			}
		} else {
			addSetAddress(indexes, 1, var);
			addPendingRead(ram, load, 1);

			ram.setLastAccessRead(true);
			ram.setLastPortUsed(0);
			ram.setWaitCycleNeeded(true);
		}
	}

	/**
	 * Converts the given Store to RAM instructions.
	 * 
	 * @param store
	 *            a Store
	 */
	private void convertStore(InstStore store) {
		List<Expression> indexes = store.getIndexes();
		Var var = store.getTarget().getVariable();

		RAM ram = ramMap.get(var);
		int port;
		if (ram.isLastAccessWrite()) {
			port = ram.getLastPortUsed() + 1;
			if (port > 0 && port % 2 == 0) {
				// port == 2, 4, 6, 8...
				addSplitInstruction();
			}
		} else {
			port = 0;
		}

		int ajustedPort = port % 2 + 1;
		addSetAddress(indexes, ajustedPort, var);
		addWrite(ram, store, ajustedPort);

		ram.setLastPortUsed(port);
		ram.setLastAccessRead(false);
	}

	private boolean executeTwoPendingReads(RAM ram) {
		List<InstRamRead> reads = pendingReads.get(ram);
		Iterator<InstRamRead> it = reads.iterator();
		for (int i = 0; it.hasNext() && i < 2; i++) {
			itInstruction.add(it.next());
			it.remove();
		}

		return it.hasNext();
	}

	@Override
	public void visit(Actor actor) {
		pendingReads = new HashMap<RAM, List<InstRamRead>>();
		ramMap = new HashMap<Var, RAM>();
		for (Var variable : actor.getStateVars()) {
			if (variable.isAssignable() && variable.getType().isList()) {
				RAM ram = new RAM();
				ramMap.put(variable, ram);
				pendingReads.put(ram, new ArrayList<InstRamRead>(2));
			}
		}

		for (Action action : actor.getActions()) {
			this.action = action;
			visit(action.getBody());
		}
	}

	@Override
	public void visit(InstLoad load) {
		Var var = load.getSource().getVariable();
		if (!load.getIndexes().isEmpty() && var.isAssignable()
				&& var.isGlobal()) {
			itInstruction.remove();

			convertLoad(load);
		}
	}

	@Override
	public void visit(Procedure procedure) {
		super.visit(procedure);

		NodeBlock block = procedure.getLast();
		itInstruction = block.lastListIterator();
		for (RAM ram : ramMap.values()) {
			// set the RAM as "never accessed"
			ram.reset();

			boolean hasMorePendingReads;
			do {
				hasMorePendingReads = executeTwoPendingReads(ram);
			} while (hasMorePendingReads);
		}
	}

	@Override
	public void visit(InstStore store) {
		Var var = store.getTarget().getVariable();
		if (!store.getIndexes().isEmpty() && var.isGlobal()) {
			itInstruction.remove();
			convertStore(store);
		}
	}

}
