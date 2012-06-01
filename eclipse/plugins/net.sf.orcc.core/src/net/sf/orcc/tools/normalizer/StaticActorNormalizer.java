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
package net.sf.orcc.tools.normalizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Tag;
import net.sf.orcc.df.util.DfVisitor;
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

/**
 * This class defines a normalizer for static actors.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class StaticActorNormalizer {

	/**
	 * This class contains code to transform a pattern to IR code (not entirely
	 * valid because not in SSA form at this point).
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class MyPatternVisitor implements PatternVisitor {

		private Procedure procedure;

		private int depth;

		private List<Var> indexes;

		private List<Block> nodes;

		public MyPatternVisitor(Procedure procedure) {
			this.procedure = procedure;
			this.nodes = procedure.getBlocks();
			this.indexes = new ArrayList<Var>();
		}

		private List<Instruction> updateIndex(Pattern pattern) {
			List<Instruction> instrs = new ArrayList<Instruction>();

			for (Port port : pattern.getPorts()) {
				int numTokens = pattern.getNumTokens(port);
				Var varCount = actor.getStateVar(port.getName() + "_count");

				InstStore store = irFactory.createInstStore(varCount, irFactory
						.createExprBinary(irFactory.createExprVar(varCount),
								OpBinary.PLUS,
								irFactory.createExprInt(numTokens),
								irFactory.createTypeInt(32)));
				instrs.add(store);
			}

			return instrs;
		}

		@Override
		public void visit(LoopPattern pattern) {
			depth++;
			if (indexes.size() < depth) {
				Var varDef = irFactory.createVar(0, irFactory.createTypeBool(),
						"loop", true, depth - 1);
				variables.add(varDef);
				indexes.add(varDef);
			}

			Var loopVar = indexes.get(depth - 1);

			// init var
			BlockBasic block = IrUtil.getLast(nodes);
			InstAssign assign = irFactory.createInstAssign(loopVar,
					irFactory.createExprInt(0));
			block.add(assign);

			// create while
			List<Block> oldNodes = nodes;
			nodes = new ArrayList<Block>();

			BlockWhile nodeWhile = irFactory.createBlockWhile();
			nodeWhile.setJoinBlock(irFactory.createBlockBasic());
			nodes = nodeWhile.getBlocks();

			oldNodes.add(nodeWhile);

			// assign condition
			Expression condition = irFactory.createExprBinary(
					irFactory.createExprVar(loopVar), OpBinary.LT,
					irFactory.createExprInt(pattern.getNumIterations()),
					irFactory.createTypeBool());
			nodeWhile.setCondition(condition);

			// accept sub pattern
			pattern.getPattern().accept(this);

			// add assign
			block = IrUtil.getLast(nodes);
			assign = irFactory.createInstAssign(loopVar, irFactory
					.createExprBinary(irFactory.createExprVar(loopVar),
							OpBinary.PLUS, irFactory.createExprInt(1),
							irFactory.createTypeInt(32)));
			block.add(assign);

			// restore stuff
			this.nodes = oldNodes;
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
			Action action = pattern.getAction();

			for(Var var : new ArrayList<Var>(action.getBody().getLocals())){
				var.setName(action.getName() + "_" + var.getName());
				procedure.getLocals().add(var);
			}
			nodes.addAll(action.getBody().getBlocks());

			// Update variable counter index
			List<Instruction> indexIn = updateIndex(action.getInputPattern());
			List<Instruction> indexOut = updateIndex(action.getOutputPattern());

			// Add all instructions
			BlockBasic block = IrUtil.getLast(nodes);
			block.getInstructions().addAll(indexIn);
			block.getInstructions().addAll(indexOut);
		}

	}

	private class UpdateFifoAccess extends AbstractIrVisitor<Void> {

		@Override
		public Void caseInstLoad(InstLoad load) {
			Use use = load.getSource();
			Var var = use.getVariable();
			if (var.eContainer() instanceof Pattern) {
				Pattern oldPattern = (Pattern) var.eContainer();
				Pattern newPattern = staticCls.getInputPattern();
				use.setVariable(newPattern.getVariable(oldPattern.getPort(var)));
				updateIndex(var, load.getIndexes());
			}
			return null;
		}

		@Override
		public Void caseInstStore(InstStore store) {
			Def def = store.getTarget();
			Var var = def.getVariable();
			if (var.eContainer() instanceof Pattern) {
				Pattern oldPattern = (Pattern) var.eContainer();
				Pattern newPattern = staticCls.getOutputPattern();
				def.setVariable(newPattern.getVariable(oldPattern.getPort(var)));
				updateIndex(var, store.getIndexes());
			}
			return null;
		}

		private void updateIndex(Var var, List<Expression> indexes) {
			if (indexes.size() == 1) {
				Var varCount = actor.getStateVar(var.getName() + "_count");
				ExprBinary expr = IrFactory.eINSTANCE.createExprBinary(
						IrFactory.eINSTANCE.createExprVar(varCount),
						OpBinary.PLUS, indexes.get(0),
						IrFactory.eINSTANCE.createTypeInt(32));

				indexes.add(expr);
			} else {
				System.err.println("TODO index");
			}
		}

	}

	private final DfFactory dfFactory = DfFactory.eINSTANCE;
	private final IrFactory irFactory = IrFactory.eINSTANCE;

	private static final String ACTION_NAME = "xxx";
	private static final String SCHEDULER_NAME = "isSchedulable_" + ACTION_NAME;

	private Actor actor;

	private CSDFMoC staticCls;

	private List<Var> variables;

	/**
	 * Creates a new normalizer
	 */
	public StaticActorNormalizer(Actor actor) {
		this.actor = actor;
		staticCls = (CSDFMoC) actor.getMoC();
	}

	/**
	 * Adds state variables to hold the values of data read/stored in the given
	 * pattern. Initializes count to 0.
	 * 
	 * @param procedure
	 *            body of the target action
	 * @param pattern
	 *            input or output pattern
	 */
	private void addStateVariables(Procedure procedure, Pattern pattern) {
		BlockBasic block = procedure.getLast();
		for (Port port : pattern.getPorts()) {
			Type type = irFactory.createTypeList(pattern.getNumTokens(port),
					port.getType());
			Var var = irFactory.createVar(type, port.getName(), true, 0);

			pattern.setVariable(port, var);

			Var varCount = irFactory
					.createVar(0, irFactory.createTypeInt(32), port.getName()
							+ "_count", true, irFactory.createExprInt(0));
			actor.getStateVars().add(varCount);

			InstStore store = irFactory.createInstStore(varCount,
					irFactory.createExprInt(0));
			block.add(store);
		}
	}

	/**
	 * Adds the given action to the actor and to its action scheduler.
	 * 
	 * @param action
	 *            an action
	 */
	private void addStaticAction(Action action) {
		actor.getActions().add(action);
		actor.getActionsOutsideFsm().add(action);
	}

	/**
	 * Cleans up the actor.
	 */
	private void cleanupActor() {
		// removes FSM
		actor.setFsm(null);

		// removes all actions from action scheduler
		actor.getActionsOutsideFsm().clear();
		actor.getActions().clear();
	}

	/**
	 * Creates the static action for this actor.
	 * 
	 * @return a static action
	 */
	private Action createAction() {
		Procedure scheduler = createScheduler();
		Procedure body = createBody();

		Pattern input = staticCls.getInputPattern();
		Pattern output = staticCls.getOutputPattern();
		Tag tag = dfFactory.createTag(ACTION_NAME);

		return dfFactory.createAction(tag, input, output,
				dfFactory.createPattern(), scheduler, body);
	}

	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 */
	private Procedure createBody() {
		Procedure procedure = irFactory.createProcedure(ACTION_NAME, 0,
				irFactory.createTypeVoid());

		variables = procedure.getLocals();

		// add state variables
		addStateVariables(procedure, staticCls.getInputPattern());
		addStateVariables(procedure, staticCls.getOutputPattern());

		// change accesses to FIFO
		new DfVisitor<Void>(new UpdateFifoAccess()).doSwitch(actor);

		// finds a pattern in the actions and visit it
		LoopPatternRecognizer r = new LoopPatternRecognizer();
		ExecutionPattern pattern = r.getPattern(staticCls.getInvocations());
		System.out.println(pattern);
		pattern.accept(new MyPatternVisitor(procedure));

		return procedure;
	}

	/**
	 * Creates a return instruction that uses the results of the hasTokens tests
	 * previously created.
	 * 
	 * @param block
	 *            block to which return is to be added
	 */
	private void createInputCondition(BlockBasic block) {
		Expression value;
		Iterator<Var> it = variables.iterator();
		if (it.hasNext()) {
			Var previous = it.next();
			value = irFactory.createExprVar(previous);

			while (it.hasNext()) {
				Var thisOne = it.next();
				value = irFactory.createExprBinary(value, OpBinary.LOGIC_AND,
						irFactory.createExprVar(thisOne),
						irFactory.createTypeBool());
				previous = thisOne;
			}
		} else {
			value = irFactory.createExprBool(true);
		}

		InstReturn returnInstr = irFactory.createInstReturn(value);
		block.add(returnInstr);
	}

	/**
	 * Creates an "isSchedulable" procedure for the static action of this actor.
	 * 
	 * @return an "isSchedulable" procedure
	 */
	private Procedure createScheduler() {
		Procedure procedure = irFactory.createProcedure(SCHEDULER_NAME, 0,
				irFactory.createTypeBool());

		variables = procedure.getLocals();

		BlockBasic block = irFactory.createBlockBasic();
		procedure.getBlocks().add(block);

		createInputCondition(block);

		return procedure;
	}

	/**
	 * Normalizes this actor so it fits the given static class.
	 * 
	 * @param staticCls
	 *            a static class
	 */
	public void normalize() {
		Action staticAction = createAction();
		cleanupActor();
		addStaticAction(staticAction);
	}

}
