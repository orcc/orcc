/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.df.transformations;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfSwitch;

/**
 * This class defines a transformation that updates the name of all entities of
 * a network to make them unique.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class UniqueNameTransformation extends DfSwitch<Void> {

	private Map<String, Integer> identifiers;

	public UniqueNameTransformation() {
		identifiers = new HashMap<String, Integer>();
	}

	@Override
	public Void caseEntity(Entity entity) {
		String id = entity.getName();
		Integer num = identifiers.get(id);
		if (num == null) {
			identifiers.put(id, 0);
		} else {
			num++;
			entity.setName(id + "_" + num);
			identifiers.put(id, num);
		}

		return null;
	}

	@Override
	public Void caseNetwork(Network network) {
		for (Entity entity : network.getEntities()) {
			doSwitch(entity);
		}

		return null;
	}

}
