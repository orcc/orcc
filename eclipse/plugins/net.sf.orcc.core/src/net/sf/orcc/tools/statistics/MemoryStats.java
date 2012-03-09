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
package net.sf.orcc.tools.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

/**
 * This class define a network analyzer that compute memory statistics about
 * actors.
 * 
 * @author Herve Yviquel
 * 
 */
public class MemoryStats {

	/**
	 * Returns the memory size needed by the given actor in bits. This size
	 * corresponds to the sum of state variable size plus the maximum of the sum
	 * of local arrays per each action.
	 * 
	 * @param actor
	 *            the given actor
	 * @return the memory size needed by the given actor
	 */
	public static int computeNeededMemorySize(Actor actor) {
		int neededMemorySize = 0;
		// Compute memory size needed by state variable
		for (Var var : actor.getStateVars()) {
			neededMemorySize += var.getType().getSizeInBits();
		}
		// Compute memory size needed by the actions
		Collection<Integer> actionsMemorySize = new ArrayList<Integer>();
		for (Action action : actor.getActions()) {
			actionsMemorySize.add(computeNeededMemorySize(action.getBody()));
			actionsMemorySize
					.add(computeNeededMemorySize(action.getScheduler()));
		}
		neededMemorySize += Collections.max(actionsMemorySize);
		return neededMemorySize;
	}

	/**
	 * Return the memory size needed by the given procedure. That corresponds to
	 * the total size of its local arrays.
	 * 
	 * @param procedure
	 *            the given procedure
	 * @return the memory size needed by the given procedure
	 */
	public static int computeNeededMemorySize(Procedure procedure) {
		int neededMemorySize = 0;
		// Compute memory size needed by local arrays
		for (Var var : procedure.getLocals()) {
			if (var.getType().isList()) {
				neededMemorySize += var.getType().getSizeInBits();
			}
		}
		return neededMemorySize;
	}

}
