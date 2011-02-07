/*
 * Copyright (c) 2009, IRISA
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
package net.sf.orcc.stats;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.network.Network;

/**
 * This class define a network analyzer that compute memory statistics about
 * actors.
 * 
 * @author Herve Yviquel
 * 
 */
public class MemoryStats {

	public class MemoryStatsElement {
		private int globalMemorySize;

		private int globalVariableNb;

		public MemoryStatsElement() {
			globalMemorySize = 0;
			globalVariableNb = 0;
		}

		public int getGlobalMemorySize() {
			return globalMemorySize;
		}

		public int getGlobalVariableNb() {
			return globalVariableNb;
		}
	}

	private Map<Actor, MemoryStatsElement> memoryStatsMap;

	private void computeMemorySize(Actor actor, MemoryStatsElement statsElement) {
		for (GlobalVariable var : actor.getStateVars()) {
			statsElement.globalMemorySize += getSize(var.getType());
		}
	}

	public void computeMemoryStats(Network network) {
		memoryStatsMap = new HashMap<Actor, MemoryStats.MemoryStatsElement>();
		for (Actor actor : network.getActors()) {
			MemoryStatsElement statsElement = new MemoryStatsElement();
			computeMemorySize(actor, statsElement);
			memoryStatsMap.put(actor, statsElement);
		}

	}

	public Map<Actor, MemoryStatsElement> getMemoryStatsMap() {
		return memoryStatsMap;
	}

	private int getSize(Type type) {
		int size;
		if (type.isBool()) {
			size = 1;
		} else if (type.isFloat()) {
			size = 32;
		} else if (type.isInt()) {
			size = ((TypeInt) type).getSize();
		} else if (type.isList()) {
			size = getSize(((TypeList) type).getElementType());
			for (int dim : type.getDimensions()) {
				size *= dim;
			}
		} else if (type.isString()) {
			size = ((TypeString) type).getSize();
		} else if (type.isUint()) {
			size = ((TypeUint) type).getSize();
		} else {
			size = 0;
		}
		return size;
	}

}
