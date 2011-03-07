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

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.moc.AbstractMoCInterpreter;
import net.sf.orcc.moc.CSDFMoC;

/**
 * This class defines a transformation that take a MoC as input and returns the
 * corresponding ActionScheduler.
 * 
 * @author Jerome Gorin
 * 
 */
public class SchedulerMerger extends AbstractMoCInterpreter {

	@Override
	public Object interpret(CSDFMoC moc, Object... args) {
		FSM fsm = new FSM();
		int stateId = 0;
		
		fsm.setInitialState("s"+stateId);
		
		for (Action action : moc.getActions()){
			fsm.addTransition("s"+stateId, action, "s"+ stateId++);
		}
		
		return new ActionScheduler(new ArrayList<Action>(), fsm);
	}
}
