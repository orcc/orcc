package net.sf.orcc.backends.llvm.tta.architecture.util;

import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
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

public class ArchitectureMemoryEstimator extends ArchitectureVisitor<Void> {

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
			return doSwitch(action.getBody()) + doSwitch(action.getBody());
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
		public Integer caseInstance(Instance instance) {
			return doSwitch(instance.getActor());
		}

		@Override
		public Integer caseConnection(Connection connection) {
			return connection.getSize()
					* getSize(connection.getSourcePort().getType()) + 2 * 32;
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
		buffer.setDepth(bits / 8 + 64);
		buffer.setWordWidth(8);
		buffer.setMinAddress(0);
		return null;
	}

	@Override
	public Void caseProcessor(Processor processor) {
		int bits = 0;
		for (Vertex entity : processor.getMappedActors()) {
			bits = dfVisitor.doSwitch(entity);
		}
		Memory ram = processor.getLocalRAMs().get(0);
		ram.setDepth(bits / 8);
		ram.setWordWidth(8);
		ram.setMinAddress(0);
		return null;
	}

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
