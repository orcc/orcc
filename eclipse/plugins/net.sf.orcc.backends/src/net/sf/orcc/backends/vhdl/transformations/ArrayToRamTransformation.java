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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

	private Map<RAM, List<RamWrite>> pendingWrites;

	private Map<Variable, RAM> ramMap;

	private void addInstruction(Instruction instruction) {
		List<Expression> indexes;
		boolean isLoad;
		Variable variable;
		Load load = null;
		Store store = null;
		BlockNode block = instruction.getBlock();

		if (instruction instanceof Load) {
			load = (Load) instruction;
			isLoad = true;
			indexes = load.getIndexes();
			variable = load.getSource().getVariable();
		} else {
			store = (Store) instruction;
			isLoad = false;
			indexes = store.getIndexes();
			variable = store.getTarget();
		}

		RAM ram = ramMap.get(variable);
		int port;
		if (isLoad) {
			if (ram.isLastAccessRead()) {
				port = ram.getLastPortUsed();
				if (port == 1) {
					port = 2;
				} else {
					addSplitInstruction(block);
					addSetAddress(block, indexes, port, variable);
					port = 1;
				}

				RamRead read = new RamRead();
				read.setBlock(block);
				read.setPort(port);
				read.setTarget(load.getTarget());
				read.setVariable(variable);
				itInstruction.add(read);
			} else {
				port = ram.getLastPortUsed();
			}
		} else {
			if (ram.isLastAccessRead()) {
				// last access was a read => split
				addSplitInstruction(block);
				port = 0;
			} else {
				port = ram.getLastPortUsed();
				if (port == 1) {
					port = 2;
				} else {
					addSplitInstruction(block);
					port = 1;
				}
			}

			addSetAddress(block, indexes, port, variable);

			RamWrite write = new RamWrite();
			write.setBlock(block);
			write.setPort(port);
			write.setVariable(variable);
			write.setValue(store.getValue());
			itInstruction.add(write);
		}

		ram.setLastAccessRead(isLoad);
		ram.setLastPortUsed(port);
	}

	private void addSplitInstruction(BlockNode block) {
		SplitInstruction instruction = new SplitInstruction();
		instruction.setBlock(block);
		itInstruction.add(instruction);
	}

	private void addSetAddress(BlockNode block, List<Expression> indexes,
			int port, Variable variable) {
		RamSetAddress rsa = new RamSetAddress(indexes);
		rsa.setBlock(block);
		rsa.setPort(port);
		rsa.setVariable(variable);
		itInstruction.add(rsa);
	}

	@Override
	public void visit(Actor actor) {
		pendingReads = new HashMap<RAM, List<RamRead>>();
		pendingWrites = new HashMap<RAM, List<RamWrite>>();
		ramMap = new HashMap<Variable, RAM>();
		for (GlobalVariable variable : actor.getStateVars()) {
			if (variable.isAssignable() && variable.getType().isList()) {
				RAM ram = new RAM();
				ramMap.put(variable, ram);
				pendingReads.put(ram, new ArrayList<RamRead>(2));
				pendingWrites.put(ram, new ArrayList<RamWrite>(2));
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

			addInstruction(load);
		}
	}

	@Override
	public void visit(Procedure procedure) {
		super.visit(procedure);

		BlockNode block = BlockNode.getLast(procedure);
		int index = block.getInstructions().size();
		for (Entry<RAM, List<RamRead>> entry : pendingReads.entrySet()) {
			List<RamRead> reads = entry.getValue();
			for (RamRead read : reads) {
				block.add(index, read);
				index++;
			}
		}
	}

	@Override
	public void visit(Store store) {
		Variable variable = store.getTarget();
		if (!store.getIndexes().isEmpty() && variable.isAssignable()
				&& variable.isGlobal()) {
			itInstruction.remove();

			addInstruction(store);
		}
	}

}
