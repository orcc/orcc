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
package net.sf.orcc.tools.merger.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
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
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.MoC;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

/**
 * This class defines a merger for CSDF actors. In other words, a new action is
 * created from the detected pattern of a CSDF model of computation.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @author Herve Yviquel
 * 
 */
public class CSDFActionMerger {

	/**
	 * This class contains code to transform a pattern to IR code.
	 */
	private class BodyBuilder implements PatternVisitor {

		private int depth;
		private List<Var> indexes;
		private List<Block> blocks;
		private Procedure procedure;

		public BodyBuilder(Procedure procedure) {
			this.procedure = procedure;
			this.blocks = procedure.getBlocks();
			this.indexes = new ArrayList<Var>();
		}

		private EList<Instruction> updateCounter(Pattern pattern) {
			EList<Instruction> instrs = new BasicEList<Instruction>();
			for (Port port : pattern.getPorts()) {
				int numTokens = pattern.getNumTokens(port);
				Var varCount = portToVarCountMap.get(port);

				InstAssign assign = irFactory.createInstAssign(varCount,
						irFactory.createExprBinary(
								irFactory.createExprVar(varCount),
								OpBinary.PLUS,
								irFactory.createExprInt(numTokens),
								irFactory.createTypeInt(32)));
				instrs.add(assign);
			}
			return instrs;
		}

		@Override
		public void visit(LoopPattern pattern) {
			depth++;
			if (indexes.size() < depth) {
				Var varDef = irFactory.createVar(0, irFactory.createTypeBool(),
						"loop", true, depth - 1);
				procedure.getLocals().add(varDef);
				indexes.add(varDef);
			}

			Var loopVar = indexes.get(depth - 1);

			// Initialize loop counter
			BlockBasic block = IrUtil.getLast(blocks);
			InstAssign assign = irFactory.createInstAssign(loopVar,
					irFactory.createExprInt(0));
			block.add(assign);

			// Create while
			List<Block> oldNodes = blocks;
			BlockWhile nodeWhile = irFactory.createBlockWhile();
			nodeWhile.setJoinBlock(irFactory.createBlockBasic());

			blocks = nodeWhile.getBlocks();
			oldNodes.add(nodeWhile);

			// Create the loop condition
			Expression condition = irFactory.createExprBinary(
					irFactory.createExprVar(loopVar), OpBinary.LT,
					irFactory.createExprInt(pattern.getNumIterations()),
					irFactory.createTypeBool());
			nodeWhile.setCondition(condition);

			// Accept sub pattern
			pattern.getPattern().accept(this);

			// Increment the loop counter
			block = IrUtil.getLast(blocks);
			assign = irFactory.createInstAssign(loopVar, irFactory
					.createExprBinary(irFactory.createExprVar(loopVar),
							OpBinary.PLUS, irFactory.createExprInt(1),
							irFactory.createTypeInt(32)));
			block.add(assign);

			// Restore stuff
			this.blocks = oldNodes;
			depth--;
		}

		@Override
		public void visit(SequentialPattern pattern) {
			for (ExecutionPattern subPattern : pattern) {
				subPattern.accept(this);
			}
		}

		@Override
		public void visit(SimplePattern pattern) {
			// Copy the body of the action
			Action action = pattern.getAction();
			Procedure bodyCopy = IrUtil.copy(action.getBody());

			// Rename variables
			for (Var var : bodyCopy.getLocals()) {
				String varName = var.getName();
				Var existingVar = procedure.getLocal(varName);
				for (int i = 0; existingVar != null; i++) {
					varName = var.getName() + i;
					existingVar = procedure.getLocal(varName);
				}
				var.setName(varName);
			}

			// Move variables and blocks in the body of the new action
			procedure.getLocals().addAll(bodyCopy.getLocals());
			blocks.addAll(bodyCopy.getBlocks());

			// Update variable counter index
			BlockBasic block = IrUtil.getLast(blocks);
			block.getInstructions().addAll(
					updateCounter(action.getInputPattern()));
			block.getInstructions().addAll(
					updateCounter(action.getOutputPattern()));
		}
	}

	private class UpdatePortAccesses extends AbstractIrVisitor<Void> {

		@Override
		public Void caseInstLoad(InstLoad load) {
			Use use = load.getSource();
			Var var = use.getVariable();
			if (var.eContainer() instanceof Pattern) {
				Pattern oldPattern = (Pattern) var.eContainer();
				Port port = oldPattern.getPort(var);
				use.setVariable(inputPattern.getVariable(port));
				updateIndex(portToVarCountMap.get(port), load.getIndexes());
			}
			return null;
		}

		@Override
		public Void caseInstStore(InstStore store) {
			Def def = store.getTarget();
			Var var = def.getVariable();
			if (var.eContainer() instanceof Pattern) {
				Pattern oldPattern = (Pattern) var.eContainer();
				Port port = oldPattern.getPort(var);
				def.setVariable(outputPattern.getVariable(port));
				updateIndex(portToVarCountMap.get(port), store.getIndexes());
			}
			return null;
		}

		private void updateIndex(Var varCount, List<Expression> indexes) {
			ExprBinary expr = IrFactory.eINSTANCE.createExprBinary(
					IrFactory.eINSTANCE.createExprVar(varCount), OpBinary.PLUS,
					indexes.get(0), IrFactory.eINSTANCE.createTypeInt(32));
			indexes.add(expr);
		}
	}

	private final DfFactory dfFactory = DfFactory.eINSTANCE;
	private final IrFactory irFactory = IrFactory.eINSTANCE;

	private CSDFMoC clasz;
	private Pattern inputPattern;
	private Pattern outputPattern;
	private String name;
	private Map<Port, Var> portToVarCountMap;

	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 */
	private Procedure createBody() {
		Procedure procedure = irFactory.createProcedure(name, 0,
				irFactory.createTypeVoid());

		// Initialize input and output port variables and their counters
		initializePorts(procedure, inputPattern);
		initializePorts(procedure, outputPattern);

		// Finds a pattern in the actions
		LoopPatternRecognizer r = new LoopPatternRecognizer();
		ExecutionPattern pattern = r.getPattern(clasz.getInvocations());
		System.out.println(pattern);

		// Build the new body
		pattern.accept(new BodyBuilder(procedure));

		// Update port accesses
		new UpdatePortAccesses().doSwitch(procedure);

		return procedure;
	}

	/**
	 * Creates the scheduler of the static action.
	 * 
	 * @return a scheduling procedure
	 */
	private Procedure createScheduler() {
		Procedure procedure = irFactory.createProcedure(
				"isSchedulable_" + name, 0, irFactory.createTypeBool());

		BlockBasic block = irFactory.createBlockBasic();
		InstReturn returnInstr = irFactory.createInstReturn(irFactory
				.createExprBool(true));

		block.add(returnInstr);
		procedure.getBlocks().add(block);

		return procedure;
	}

	/**
	 * Add port variables to the pattern. Create a counter for each port and
	 * initialize it to 0.
	 * 
	 * @param procedure
	 *            body of the target action
	 * @param pattern
	 *            input or output pattern
	 */
	private void initializePorts(Procedure procedure, Pattern pattern) {
		BlockBasic block = procedure.getLast();
		for (Port port : pattern.getPorts()) {
			// Port variable
			Type type = irFactory.createTypeList(pattern.getNumTokens(port),
					port.getType());
			Var var = irFactory.createVar(type, port.getName(), true, 0);
			pattern.setVariable(port, var);

			// Counter variable
			Var varCount = procedure.newTempLocalVariable(
					irFactory.createTypeInt(32), port.getName() + "_count");
			InstAssign assign = irFactory.createInstAssign(varCount,
					irFactory.createExprInt(0));
			block.add(assign);

			portToVarCountMap.put(port, varCount);
		}
	}

	public void merge(Actor actor) {
		MoC clasz = actor.getMoC();
		if (clasz.isCSDF()) {
			Action action = new CSDFActionMerger()
					.merge("xxx", (CSDFMoC) clasz);

			// Remove FSM
			actor.setFsm(null);
			// Remove all actions from action scheduler
			actor.getActionsOutsideFsm().clear();
			actor.getActions().clear();

			// Add the static action
			actor.getActions().add(action);
			actor.getActionsOutsideFsm().add(action);
		}
	}

	public Action merge(String name, CSDFMoC moc) {
		this.clasz = moc;
		this.name = name;
		this.inputPattern = IrUtil.copy(clasz.getInputPattern());
		this.outputPattern = IrUtil.copy(clasz.getOutputPattern());
		this.portToVarCountMap = new HashMap<Port, Var>();

		Procedure scheduler = createScheduler();
		Procedure body = createBody();
		Pattern peeked = dfFactory.createPattern();

		return dfFactory.createAction(name, inputPattern, outputPattern,
				peeked, scheduler, body);
	}
}
