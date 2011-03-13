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
package net.sf.orcc.backends.vhdl.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.vhdl.instructions.RamRead;
import net.sf.orcc.backends.vhdl.instructions.RamSetAddress;
import net.sf.orcc.backends.vhdl.instructions.RamWrite;
import net.sf.orcc.backends.vhdl.instructions.SplitInstruction;
import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;

/**
 * This class defines a visitor that transforms loads and stores to RAM
 * operations.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ArrayToRamTransformation extends AbstractActorVisitor {

	private Map<RAM, List<RamRead>> pendingReads;

	private Map<Variable, RAM> ramMap;

	/**
	 * Adds a RamSetAddress instruction before the previous SplitInstruction.
	 * 
	 * @param ram
	 *            RAM
	 * @param block
	 *            parent block
	 * @param indexes
	 *            list of indexes
	 * @param variable
	 *            variable
	 */
	private void addEarlySetAddress(RAM ram, BlockNode block,
			List<Expression> indexes, Variable variable) {
		RamSetAddress rsa = new RamSetAddress(indexes);
		rsa.setBlock(block);
		rsa.setPort(ram.getLastPortUsed());
		rsa.setVariable(variable);

		// save index
		int index = itInstruction.nextIndex() + 1;
		
		// insert the RSA before the previous split instruction
		while (itInstruction.hasPrevious()) {
			Instruction instruction = itInstruction.previous();
			if (instruction instanceof SplitInstruction) {
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
	private void addPendingRead(RAM ram, Load load, int port) {
		RamRead read = new RamRead();
		read.setBlock(load.getBlock());
		read.setPort(port);
		read.setTarget(load.getTarget());
		read.setVariable(load.getSource().getVariable());

		List<RamRead> reads = pendingReads.get(ram);
		reads.add(read);
	}

	/**
	 * Adds a RamSetAddress instruction.
	 * 
	 * @param block
	 *            parent block
	 * @param indexes
	 *            list of indexes
	 * @param port
	 *            port used
	 * @param variable
	 *            variable
	 */
	private void addSetAddress(BlockNode block, List<Expression> indexes,
			int port, Variable variable) {
		RamSetAddress rsa = new RamSetAddress(indexes);
		rsa.setBlock(block);
		rsa.setPort(port);
		rsa.setVariable(variable);
		itInstruction.add(rsa);
	}

	/**
	 * Adds a SplitInstruction to the given block.
	 * 
	 * @param block
	 *            a block
	 */
	private void addSplitInstruction(BlockNode block) {
		SplitInstruction instruction = new SplitInstruction();
		instruction.setBlock(block);
		itInstruction.add(instruction);
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
	private void addWrite(RAM ram, Store store, int port) {
		RamWrite write = new RamWrite();
		write.setBlock(store.getBlock());
		write.setPort(port);
		write.setVariable(store.getTarget());
		write.setValue(store.getValue());
		itInstruction.add(write);
	}

	/**
	 * Converts the given Load to RAM instructions.
	 * 
	 * @param load
	 *            a Load
	 */
	private void convertLoad(Load load) {
		BlockNode block = load.getBlock();
		List<Expression> indexes = load.getIndexes();
		Variable variable = load.getSource().getVariable();

		RAM ram = ramMap.get(variable);
		int port;
		if (ram.isLastAccessRead()) {
			port = ram.getLastPortUsed();
			if (port == 1) {
				port = 2;
			} else {
				port = 1;
			}
			ram.setLastPortUsed(port);

			if (ram.isWaitCycleNeeded()) {
				addSetAddress(block, indexes, port, variable);
			} else {
				addEarlySetAddress(ram, block, indexes, variable);
			}
			addPendingRead(ram, load, port);

			if (port == 2) {
				// two ports have been used
				if (ram.isWaitCycleNeeded()) {
					addSplitInstruction(block);
					addSplitInstruction(block);
					executeTwoPendingReads(block, ram);
					ram.setWaitCycleNeeded(false);
				} else {
					addSplitInstruction(block);
					executeTwoPendingReads(block, ram);
				}
			}
		} else {
			port = 1;
			addSetAddress(block, indexes, port, variable);
			addPendingRead(ram, load, port);

			ram.setLastAccessRead(true);
			ram.setLastPortUsed(port);
			ram.setWaitCycleNeeded(true);
		}
	}

	/**
	 * Converts the given Store to RAM instructions.
	 * 
	 * @param store
	 *            a Store
	 */
	private void convertStore(Store store) {
		BlockNode block = store.getBlock();
		List<Expression> indexes = store.getIndexes();
		Variable variable = store.getTarget();

		RAM ram = ramMap.get(variable);
		int port;
		if (ram.isLastAccessWrite()) {
			port = ram.getLastPortUsed();
			if (port == 1) {
				port = 2;
			} else {
				port = 1;
			}
		} else {
			port = 1;
		}

		addSetAddress(block, indexes, port, variable);
		addWrite(ram, store, port);

		ram.setLastPortUsed(port);
		ram.setLastAccessRead(false);
	}

	private boolean executeTwoPendingReads(BlockNode block, RAM ram) {
		List<RamRead> reads = pendingReads.get(ram);
		Iterator<RamRead> it = reads.iterator();
		for (int i = 0; it.hasNext() && i < 2; i++) {
			itInstruction.add(it.next());
			it.remove();
		}

		return it.hasNext();
	}

	@Override
	public void visit(Actor actor) {
		pendingReads = new HashMap<RAM, List<RamRead>>();
		ramMap = new HashMap<Variable, RAM>();
		for (GlobalVariable variable : actor.getStateVars()) {
			if (variable.isAssignable() && variable.getType().isList()) {
				RAM ram = new RAM();
				ramMap.put(variable, ram);
				pendingReads.put(ram, new ArrayList<RamRead>(2));
			}
		}

		for (Action action : actor.getActions()) {
			visit(action.getBody());
		}
	}

	@Override
	public void visit(Load load) {
		Variable variable = load.getSource().getVariable();
		if (!load.getIndexes().isEmpty() && variable.isAssignable()
				&& variable.isGlobal()) {
			itInstruction.remove();

			convertLoad(load);
		}
	}

	@Override
	public void visit(Procedure procedure) {
		super.visit(procedure);

		BlockNode block = BlockNode.getLast(procedure);
		itInstruction = block.lastListIterator();
		for (RAM ram : ramMap.values()) {
			ram.reset();

			boolean hasMorePendingReads;
			do {
				hasMorePendingReads = executeTwoPendingReads(block, ram);
			} while (hasMorePendingReads);
		}
	}

	@Override
	public void visit(Store store) {
		Variable variable = store.getTarget();
		if (!store.getIndexes().isEmpty() && variable.isAssignable()
				&& variable.isGlobal()) {
			itInstruction.remove();

			convertStore(store);
		}
	}

}
