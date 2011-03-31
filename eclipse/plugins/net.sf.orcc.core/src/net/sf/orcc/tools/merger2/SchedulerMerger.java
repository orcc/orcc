/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.tools.merger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.moc.AbstractMoCInterpreter;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a transformation that take a MoC as input and returns the
 * corresponding ActionScheduler.
 * 
 * @author Jerome Gorin
 * 
 */
public class SchedulerMerger extends AbstractMoCInterpreter {
	private List<Action> actions;
	private Action initializeCounter;
	private List<GlobalVariable> varCounters;

	public SchedulerMerger(List<Action> actions,
			Map<GlobalVariable, GlobalVariable> readCounts,
			Map<GlobalVariable, GlobalVariable> writeCounts) {
		this.actions = actions;
		varCounters = new ArrayList<GlobalVariable>();
		varCounters.addAll(readCounts.values());
		varCounters.addAll(writeCounts.values());

		initializeCounter = createInitializer();
	}

	private Procedure createBody() {
		List<CFGNode> nodes = new ArrayList<CFGNode>();

		Procedure procedure = new Procedure("initCounter", false,
				new Location(), IrFactory.eINSTANCE.createTypeVoid(),
				new OrderedMap<String, LocalVariable>(),
				new OrderedMap<String, LocalVariable>(), nodes);

		NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();
		nodes.add(block);

		for (GlobalVariable counter : varCounters) {
			Store store = new Store(counter, new IntExpr(0));
			block.add(store);
		}

		return procedure;
	}

	private Action createInitializer() {
		Procedure body = createBody();
		Procedure scheduler = createScheduler();

		return new Action(new Location(), new Tag("initCounter"),
				new Pattern(), new Pattern(), scheduler, body);
	}

	private Procedure createScheduler() {
		List<CFGNode> nodes = new ArrayList<CFGNode>();

		Procedure procedure = new Procedure("isSchedulable_initCounter",
				false, new Location(), IrFactory.eINSTANCE.createTypeBool(),
				new OrderedMap<String, LocalVariable>(),
				new OrderedMap<String, LocalVariable>(), nodes);

		NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();
		nodes.add(block);
		Return returnInstr = new Return(new BoolExpr(true));
		block.add(returnInstr);

		return procedure;
	}

	@Override
	public Object interpret(CSDFMoC moc, Object... args) {
		FSM fsm = new FSM();
		int stateId = 0;

		actions.add(initializeCounter);

		fsm.setInitialState("s" + stateId);

		for (Action action : moc.getActions()) {
			fsm.addTransition("s" + stateId++, action, "s" + stateId);
		}

		fsm.addTransition("s" + stateId, initializeCounter, fsm.getInitialState().toString());
		
		return new ActionScheduler(new ArrayList<Action>(), fsm);
	}
}
