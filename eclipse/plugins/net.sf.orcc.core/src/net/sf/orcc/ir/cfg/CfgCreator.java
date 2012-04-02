/*
 * Copyright (c) 2012, Synflow
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
package net.sf.orcc.ir.cfg;

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Cfg;
import net.sf.orcc.ir.CfgNode;

/**
 * This class defines a CFG creator from an FSM.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CfgCreator extends DfSwitch<Void> {

	private Cfg cfg;

	public CfgCreator() {
		cfg = eINSTANCE.createCfg();
		CfgNode start = eINSTANCE.createCfgNode();
		cfg.add(start);
		cfg.setEntry(start);
	}

	@Override
	public Void caseActor(Actor actor) {
		FSM fsm = actor.getFsm();
		if (fsm == null) {
			convertActionsToCfg(actor.getActionsOutsideFsm());
		} else {
			convertFsmToCfg(fsm);
		}

		normalizeConditionals();

		return null;
	}

	/**
	 * Transforms multiple conditioned branches to a series of binary branches.
	 */
	private void normalizeConditionals() {
		List<CfgNode> nodes = cfg.getNodes();
		nodes.toString();
	}

	/**
	 * Converts the given list of actions to a CFG.
	 * 
	 * @param actions
	 *            a list of actions
	 */
	private void convertActionsToCfg(List<Action> actions) {
		CfgNode idle = eINSTANCE.createCfgNode();
		cfg.add(cfg.getFirst(), idle);
		cfg.add(idle, idle);

		for (Action action : actions) {
			CfgNode bbNode = eINSTANCE.createCfgNode();
			bbNode.setAttribute("action", action);
			cfg.add(idle, bbNode);
			cfg.add(bbNode, idle);
		}
	}

	/**
	 * Converts the given FSM to a CFG. Each transition is transformed to two
	 * edges with a Basic Block node that has an "action" attribute.
	 * 
	 * @param fsm
	 *            an FSM
	 */
	private void convertFsmToCfg(FSM fsm) {
		Map<State, CfgNode> stateMap = new HashMap<State, CfgNode>();
		cfg.add(cfg.getFirst(), getCfgNode(stateMap, fsm.getInitialState()));

		for (Transition transition : fsm.getTransitions()) {
			CfgNode srcNode = getCfgNode(stateMap, transition.getSource());
			CfgNode tgtNode = getCfgNode(stateMap, transition.getTarget());
			CfgNode bbNode = eINSTANCE.createCfgNode();
			cfg.add(srcNode, bbNode);
			cfg.add(bbNode, tgtNode);
			bbNode.setAttribute("action", transition.getAction());
		}
	}

	/**
	 * Returns the CFG created by this class.
	 * 
	 * @return an instance of a Cfg
	 */
	public Cfg getCfg() {
		return cfg;
	}

	/**
	 * Returns the CFG node associated to the given state in the given map. If
	 * it does not exist, a new mapping is created.
	 * 
	 * @param stateMap
	 *            a map from State to CFG node
	 * @param state
	 *            a state
	 * @return a CFG node (already present in the map or newly-created)
	 */
	private CfgNode getCfgNode(Map<State, CfgNode> stateMap, State state) {
		CfgNode node = stateMap.get(state);
		if (node == null) {
			node = eINSTANCE.createCfgNode();
			stateMap.put(state, node);
		}
		return node;
	}

}
