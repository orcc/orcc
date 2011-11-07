/*
 * Copyright (c) 2011, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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

package net.sf.orcc.backends.xlim;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.ir.Var;

/**
 * This class is giving the necessary information for the XLIM Actor generation
 * 
 * @author Herve Yviquel
 * 
 */
public class XlimActorTemplateData {

	private Map<Action, Map<Port, Map<Integer, Var>>> customPeekedMapPerAction;

	private Map<State, Integer> stateToIndexMap;

	public XlimActorTemplateData() {
		customPeekedMapPerAction = new HashMap<Action, Map<Port, Map<Integer, Var>>>();
	}

	private void computeStateToIndexMap(Actor actor) {
		if (actor.hasFsm()) {
			int i = 0;
			for (State state : actor.getFsm().getStates()) {
				stateToIndexMap.put(state, i);
				i++;
			}
		}
	}

	public void computeTemplateMaps(Actor actor) {
		stateToIndexMap = new HashMap<State, Integer>();

		computeStateToIndexMap(actor);
	}

	public Map<Action, Map<Port, Map<Integer, Var>>> getCustomPeekedMapPerAction() {
		return customPeekedMapPerAction;
	}

	public Map<State, Integer> getStateToIndexMap() {
		return stateToIndexMap;
	}

}
