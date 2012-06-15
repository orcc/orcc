/*
 * Copyright (c) 2012, IRISA
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
package net.sf.orcc.backends.llvm.tta.architecture.util;

import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.util.BackendUtil;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

/**
 * The class defines an estimator of the quantity of memory needed by an design
 * after the projection of an application on this design.
 * 
 * @author Herve Yviquel
 */
public class ArchitectureMemoryEstimator extends ArchitectureVisitor<Void> {

	final double ERROR_MARGIN = 0.3;

	/**
	 * The class defines a Network visitor used to evaluate the memory needs of
	 * the given dataflow entities.
	 */
	private class InnerDfVisitor extends DfVisitor<Integer> {
		private class InnerIrVisitor extends AbstractIrVisitor<Integer> {

			@Override
			public Integer caseProcedure(Procedure procedure) {
				int bits = 0;
				for (Var local : procedure.getLocals()) {
					bits += doSwitch(local);
				}
				return bits;
			}

			@Override
			public Integer caseVar(Var var) {
				return getSize(var.getType());
			}

		}

		public InnerDfVisitor() {
			this.irVisitor = new InnerIrVisitor();
		}

		@Override
		public Integer caseAction(Action action) {
			int bits = doSwitch(action.getScheduler())
					+ doSwitch(action.getBody());
			return (int) Math.ceil(bits + bits * ERROR_MARGIN);
		}

		@Override
		public Integer caseActor(Actor actor) {
			int bits = 0;
			for (Var var : actor.getStateVars()) {
				if (var.isAssignable() || var.getType().isList()) {
					bits += getSize(var.getType());
				}
			}
			for (Action action : actor.getActions()) {
				bits += doSwitch(action);
			}
			return bits;
		}

		@Override
		public Integer caseConnection(Connection connection) {
			int bits = connection.getSize()
					* getSize(connection.getSourcePort().getType()) + 2 * 32;
			return (int) Math.ceil(bits + bits * ERROR_MARGIN);
		}

		@Override
		public Integer caseInstance(Instance instance) {
			return doSwitch(instance.getActor());
		}
	}

	private DfVisitor<Integer> dfVisitor;

	public ArchitectureMemoryEstimator() {
		dfVisitor = new InnerDfVisitor();
	}

	@Override
	public Void caseMemory(Memory buffer) {
		int bits = 0;
		for (Connection connection : buffer.getMappedConnections()) {
			bits += dfVisitor.doSwitch(connection);
		}
		buffer.setDepth(BackendUtil.quantizeUp(bits / 8 + 64));
		buffer.setWordWidth(8);
		buffer.setMinAddress(0);
		return null;
	}

	@Override
	public Void caseProcessor(Processor processor) {
		Memory rom = processor.getROM();
		rom.setDepth(480000);
		rom.setWordWidth(8);
		rom.setMinAddress(0);

		// Compute size of the local circular buffer
		for (Memory ram : processor.getLocalRAMs()) {
			doSwitch(ram);
		}

		// Increase the size of the first RAM according to the memory needs for
		// the stack and the state of the actors.
		int bits = 0;
		for (Vertex entity : processor.getMappedActors()) {
			bits += dfVisitor.doSwitch(entity);
		}
		Memory ram = processor.getLocalRAMs().get(0);
		ram.setDepth(BackendUtil.quantizeUp(ram.getDepth() + bits / 8));
		ram.setWordWidth(8);
		ram.setMinAddress(0);
		return null;
	}

	/**
	 * Compute the size in bits of the given type. The method getSizeInBits() of
	 * the class Type is not relevant here because the TCE consider boolean as
	 * an 8-bits type.
	 * 
	 * @param type
	 *            the type to evaluate
	 * @return the size of the type in bits.
	 */
	private int getSize(Type type) {
		int size;
		if (type.isList()) {
			size = getSize(((TypeList) type).getInnermostType());
			for (int dim : type.getDimensions()) {
				size *= dim;
			}
		} else if (type.isBool()) {
			size = 8;
		} else {
			size = type.getSizeInBits();
		}
		return size;
	}

}
