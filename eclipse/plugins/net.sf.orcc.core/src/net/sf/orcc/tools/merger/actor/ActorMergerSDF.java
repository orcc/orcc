/*
 * Copyright (c) 2010, EPFL
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
 *   * Neither the name of the EPFL nor the names of its contributors may be used 
 *     to endorse or promote products derived from this software without specific 
 *     prior written permission.
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
package net.sf.orcc.tools.merger.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines the transformation from a network of SDF actors into a
 * single cluster actor.
 * 
 * @author Ghislain Roquier
 * @author Herve Yviquel
 * 
 */
public class ActorMergerSDF extends DfSwitch<Actor> {

	/**
	 * This class defines a transformation to update the FIFO accesses.
	 * 
	 * @author Ghislain Roquier
	 * 
	 */
	public class ActionUpdater extends AbstractIrVisitor<Void> {

		private Procedure body;

		private Map<Var, Integer> loads;

		private Pattern oldInputPattern;

		private Pattern oldOutputPattern;

		private Map<Var, Integer> stores;

		/**
		 * Create a new visitor able to update the FIFO accesses.
		 * 
		 * @param action
		 *            the action containing the old patterns
		 * @param body
		 *            the procedure to update
		 */
		public ActionUpdater(Action action, Procedure body) {
			this.body = body;
			this.oldInputPattern = action.getInputPattern();
			this.oldOutputPattern = action.getOutputPattern();
		}

		@Override
		public Void caseInstLoad(InstLoad load) {
			Use use = load.getSource();
			Var var = use.getVariable();
			Port port = oldInputPattern.getPort(var);
			if (port != null) {
				if (buffersMap.containsKey(port)) {
					var = buffersMap.get(port);
				} else {
					var = inputPattern.getVariable(portsMap.get(port));
				}
				int cns = oldInputPattern.getNumTokens(port);
				loads.put(var, cns);
				use.setVariable(var);
				List<Expression> indexes = load.getIndexes();
				Expression e1 = irFactory.createExprVar(body.getLocal(var
						.getName() + "_r"));
				Expression e2 = IrUtil.copy(indexes.get(0));
				Expression bop = irFactory.createExprBinary(e1, OpBinary.PLUS,
						e2, e1.getType());
				indexes.set(0, bop);
			}
			return null;
		}

		@Override
		public Void caseInstReturn(InstReturn inst) {
			IrUtil.delete(inst);
			indexInst--;
			return null;
		}

		@Override
		public Void caseInstStore(InstStore store) {
			Def def = store.getTarget();
			Var var = def.getVariable();
			Port port = oldOutputPattern.getPort(var);
			if (port != null) {
				if (buffersMap.containsKey(port)) {
					var = buffersMap.get(port);
				} else {
					var = outputPattern.getVariable(portsMap.get(port));
				}
				int prd = oldOutputPattern.getNumTokens(port);
				stores.put(var, prd);
				def.setVariable(var);
				Expression e1 = irFactory.createExprVar(body.getLocal(var
						.getName() + "_w"));
				Expression e2 = IrUtil.copy(store.getIndexes().get(0));
				Expression bop = irFactory.createExprBinary(e1, OpBinary.PLUS,
						e2, e1.getType());
				store.getIndexes().set(0, bop);
			}

			port = oldInputPattern.getPort(var);
			if (port != null) {
				if (buffersMap.containsKey(port)) {
					var = buffersMap.get(port);
				} else {
					var = inputPattern.getPortToVarMap()
							.get(portsMap.get(port));
				}
				int cns = oldInputPattern.getNumTokens(port);
				stores.put(var, cns);
				def.setVariable(var);
				Expression e1 = irFactory.createExprVar(body.getLocal(var
						.getName() + "_r"));
				Expression e2 = IrUtil.copy(store.getIndexes().get(0));
				Expression bop = irFactory.createExprBinary(e1, OpBinary.PLUS,
						e2, e1.getType());
				store.getIndexes().set(0, bop);
			}
			return null;
		}

		@Override
		public Void caseProcedure(Procedure procedure) {
			loads = new HashMap<Var, Integer>();
			stores = new HashMap<Var, Integer>();

			super.caseProcedure(procedure);

			// Update indexes
			for (Map.Entry<Var, Integer> entry : loads.entrySet()) {
				Var var = entry.getKey();
				int cns = entry.getValue();

				Var readVar = body.getLocal(var.getName() + "_r");
				ExprBinary incr = irFactory.createExprBinary(
						irFactory.createExprVar(readVar), OpBinary.PLUS,
						irFactory.createExprInt(cns), readVar.getType());

				InstAssign assign = irFactory.createInstAssign(readVar, incr);
				procedure.getLast().add(assign);
			}

			for (Map.Entry<Var, Integer> entry : stores.entrySet()) {
				Var var = entry.getKey();
				int prd = entry.getValue();

				Var readVar = body.getLocal(var.getName() + "_w");
				ExprBinary incr = irFactory.createExprBinary(
						irFactory.createExprVar(readVar), OpBinary.PLUS,
						irFactory.createExprInt(prd), readVar.getType());

				InstAssign assign = irFactory.createInstAssign(readVar, incr);
				procedure.getLast().add(assign);
			}

			return null;
		}

	}

	private static final DfFactory dfFactory = DfFactory.eINSTANCE;
	private static final IrFactory irFactory = IrFactory.eINSTANCE;

	private Map<Port, Var> buffersMap = new HashMap<Port, Var>();
	private Copier copier;
	private int depth;

	private Pattern inputPattern;
	private Pattern outputPattern;

	private Map<Port, Port> portsMap = new HashMap<Port, Port>();

	private SASLoopScheduler scheduler;
	private Actor superActor;

	/**
	 * Creates a new merger for connected SDF actor.
	 * 
	 * @param scheduler
	 *            the pre-initialized single appearance schedule
	 * @param copier
	 *            the associated copier
	 */
	public ActorMergerSDF(SASLoopScheduler scheduler, Copier copier) {
		this.scheduler = scheduler;
		this.copier = copier;
	}

	@Override
	public Actor caseNetwork(Network network) {
		superActor = dfFactory.createActor();
		buffersMap = new HashMap<Port, Var>();
		portsMap = new HashMap<Port, Port>();

		superActor.setName(network.getName());

		SDFMoC sdfMoC = MocFactory.eINSTANCE.createSDFMoC();
		superActor.setMoC(sdfMoC);

		// Create input/output ports
		for (Port port : network.getInputs()) {
			Port portCopy = (Port) copier.copy(port);
			Connection connection = (Connection) port.getOutgoing().get(0);
			Actor tgt = connection.getTarget().getAdapter(Actor.class);
			CSDFMoC moc = (CSDFMoC) tgt.getMoC();
			int cns = scheduler.getRepetitions(tgt)
					* moc.getNumTokensConsumed(connection.getTargetPort());
			sdfMoC.getInputPattern().setNumTokens(portCopy, cns);

			superActor.getInputs().add(portCopy);
			portsMap.put(connection.getTargetPort(), portCopy);
		}

		for (Port port : network.getOutputs()) {
			Port portCopy = (Port) copier.copy(port);
			Connection connection = (Connection) port.getIncoming().get(0);
			Actor src = connection.getSource().getAdapter(Actor.class);
			CSDFMoC moc = (CSDFMoC) src.getMoC();
			int prd = scheduler.getRepetitions(src)
					* moc.getNumTokensProduced(connection.getSourcePort());
			sdfMoC.getOutputPattern().setNumTokens(portCopy, prd);

			superActor.getOutputs().add(portCopy);
			portsMap.put(connection.getSourcePort(), portCopy);
		}

		// Move variables and procedures
		for (Vertex vertex : network.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);
			for (Procedure proc : new ArrayList<Procedure>(actor.getProcs())) {
				if (!proc.isNative()) {
					proc.setName(actor.getName() + "_" + proc.getName());
				}
				superActor.getProcs().add(proc);
			}
			for (Var var : new ArrayList<Var>(actor.getStateVars())) {
				superActor.addStateVar(var);
			}
			for (Var param : new ArrayList<Var>(actor.getParameters())) {
				superActor.addStateVar(param);
			}
		}

		// Create the merged action
		inputPattern = dfFactory.createPattern();
		for (Port port : superActor.getInputs()) {
			int numTokens = sdfMoC.getNumTokensConsumed(port);
			Type type = irFactory.createTypeList(numTokens,
					EcoreUtil.copy(port.getType()));
			Var var = irFactory.createVar(0, type, port.getName(), true);
			inputPattern.setNumTokens(port, numTokens);
			inputPattern.setVariable(port, var);
		}

		outputPattern = dfFactory.createPattern();
		for (Port port : superActor.getOutputs()) {
			int numTokens = sdfMoC.getNumTokensProduced(port);
			Type type = irFactory.createTypeList(numTokens,
					EcoreUtil.copy(port.getType()));
			Var var = irFactory.createVar(0, type, port.getName(), true);
			outputPattern.setNumTokens(port, numTokens);
			outputPattern.setVariable(port, var);
		}

		Pattern peekPattern = dfFactory.createPattern();

		Procedure scheduler = irFactory.createProcedure(
				"isSchedulable_mergedAction", 0, irFactory.createTypeBool());
		scheduler.getLast().add(
				irFactory.createInstReturn(irFactory.createExprBool(true)));

		Procedure body = createBody();

		Action action = dfFactory.createAction("mergedAction", inputPattern,
				outputPattern, peekPattern, scheduler, body);

		superActor.getActions().add(action);
		superActor.getActionsOutsideFsm().add(action);

		return superActor;
	}

	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 */
	private Procedure createBody() {
		Procedure body = irFactory.createProcedure("mergedAction", 0,
				irFactory.createTypeVoid());

		BlockBasic block = body.getLast();

		// Create counters for inputs
		for (Port port : superActor.getInputs()) {
			Var readIdx = body.newTempLocalVariable(
					irFactory.createTypeInt(32), port.getName() + "_r");
			block.add(irFactory.createInstAssign(readIdx, irFactory.createExprInt(0)));
		}

		// Create counters for outputs
		for (Port port : superActor.getOutputs()) {
			Var writeIdx = body.newTempLocalVariable(
					irFactory.createTypeInt(32), port.getName() + "_w");
			block.add(irFactory.createInstAssign(writeIdx, irFactory.createExprInt(0)));
		}

		int index = 0;
		// Create buffers and counters for inner connections
		for (Connection conn : scheduler.getMaxTokens().keySet()) {
			String name = "buffer_" + index++;

			// create inner buffer
			int size = scheduler.getMaxTokens().get(conn);
			Type eltType = conn.getSourcePort().getType();
			Type type = irFactory.createTypeList(size, eltType);
			Var buffer = body.newTempLocalVariable(type, name);

			// create write counter
			Var writeIdx = body.newTempLocalVariable(
					irFactory.createTypeInt(32), name + "_w");
			block.add(irFactory.createInstAssign(writeIdx, irFactory.createExprInt(0)));

			// create read counter
			Var readIdx = body.newTempLocalVariable(
					irFactory.createTypeInt(32), name + "_r");
			block.add(irFactory.createInstAssign(readIdx, irFactory.createExprInt(0)));

			buffersMap.put(conn.getSourcePort(), buffer);
			buffersMap.put(conn.getTargetPort(), buffer);
		}

		// Add loop counter(s)
		int i = 0;
		do { // one loop var is required even if the schedule as a depth of 0
			body.newTempLocalVariable(irFactory.createTypeInt(32), "idx_" + i);
			i++;
		} while (i < scheduler.getDepth());

		createStaticSchedule(body, scheduler.getSchedule(), body.getBlocks());
		body.getLast().add(irFactory.createInstReturn());

		return body;
	}

	/**
	 * Create the procedural code of a static schedule.
	 * 
	 * @param procedure
	 *            the associated procedure
	 * @param schedule
	 *            the current schedule
	 * @param blocks
	 *            the current list of blocks
	 */
	private void createStaticSchedule(Procedure procedure, Schedule schedule,
			List<Block> blocks) {
		for (Iterand iterand : schedule.getIterands()) {
			if (iterand.isActor()) {
				Actor actor = iterand.getActor();
				CSDFMoC moc = (CSDFMoC) actor.getMoC();
				for (Invocation invocation : moc.getInvocations()) {
					Action action = IrUtil.copy(invocation.getAction());

					// Copy local variable
					for (Var var : new ArrayList<Var>(action.getBody()
							.getLocals())) {
						procedure.addLocal(var);
					}

					new ActionUpdater(action, procedure).doSwitch(action
							.getBody());
					blocks.addAll(action.getBody().getBlocks());
				}

			} else {
				Schedule sched = iterand.getSchedule();
				Var loopVar = procedure.getLocal("idx_" + depth);

				// init counter
				BlockBasic block = IrUtil.getLast(blocks);
				block.add(irFactory.createInstAssign(loopVar,
						irFactory.createExprInt(0)));

				// while loop
				Expression condition = irFactory.createExprBinary(
						irFactory.createExprVar(loopVar), OpBinary.LT,
						irFactory.createExprInt(sched.getIterationCount()),
						irFactory.createTypeBool());

				BlockWhile blockWhile = irFactory.createBlockWhile();
				blockWhile.setJoinBlock(irFactory.createBlockBasic());
				blockWhile.setCondition(condition);
				blocks.add(blockWhile);

				depth++;
				// recursion
				createStaticSchedule(procedure, sched, blockWhile.getBlocks());
				depth--;

				// Increment current while loop variable
				Expression expr = irFactory
						.createExprBinary(irFactory.createExprVar(loopVar),
								OpBinary.PLUS, irFactory.createExprInt(1),
								irFactory.createTypeInt(32));
				InstAssign assign = irFactory.createInstAssign(loopVar, expr);
				IrUtil.getLast(blockWhile.getBlocks()).add(assign);
			}
		}
	}
}
