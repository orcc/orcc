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

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.MoC;

/**
 * This class defines an actor transformation that normalizes actors so they can
 * later be merged together.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @author Herve Yviquel
 * 
 */
public class ActorNormalizer extends DfSwitch<Void> {

	@Override
	public Void caseActor(Actor actor) {
		MoC clasz = actor.getMoC();
		if (clasz.isCSDF() && actor.getActions().size() > 1) {
			Action action = new StaticActorNormalizer().normalize("xxx",
					(CSDFMoC) clasz);

			// Removes FSM
			actor.setFsm(null);
			// Removes all actions from action scheduler
			actor.getActionsOutsideFsm().clear();
			actor.getActions().clear();
			// Add the static action
			actor.getActions().add(action);
			actor.getActionsOutsideFsm().add(action);
		}

		return null;
	}

}
