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

import java.util.HashMap;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
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

public class ActorMergerSDF extends ActorMergerBase {

	SASLoopScheduler scheduler;

	protected int depth;
	
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

		copyVariables(network);
		copyProcedures(network);

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

		Procedure isSchedulable = irFactory.createProcedure(
				"isSchedulable_mergedAction", 0, irFactory.createTypeBool());
		isSchedulable.getLast().add(
				irFactory.createInstReturn(irFactory.createExprBool(true)));

		Procedure body = createBody("mergedAction", scheduler.getMaxTokens(), scheduler.getDepth());
		createStaticSchedule(body, (Schedule) scheduler.getSchedule(), body.getBlocks());
		body.getLast().add(irFactory.createInstReturn());

		Action action = dfFactory.createAction("mergedAction", inputPattern,
				outputPattern, peekPattern, isSchedulable, body);

		superActor.getActions().add(action);
		superActor.getActionsOutsideFsm().add(action);

		return superActor;
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
					addActionToBody(procedure, IrUtil.copy(invocation.getAction()), blocks);
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
