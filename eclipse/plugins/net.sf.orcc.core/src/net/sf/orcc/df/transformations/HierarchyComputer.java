/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfSwitch;

/**
 * This class defines a transformation that computes the hierarchy of all
 * instances of a network. The hierarchy of an instance is defined as a list of
 * the instances that lead to it from the top-level network.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class HierarchyComputer extends DfSwitch<Void> {

	private List<Instance> instances;

	public HierarchyComputer() {
		instances = new ArrayList<Instance>();
	}

	private HierarchyComputer(List<Instance> instances, Instance instance) {
		this.instances = new ArrayList<Instance>(instances);
		this.instances.add(instance);
	}

	@Override
	public Void caseNetwork(Network network) {
		if (instances.isEmpty()) {
			Instance instance = DfFactory.eINSTANCE.createInstance(
					network.getSimpleName(), network);
			instances.add(instance);
		}

		for (Instance instance : network.getInstances()) {
			if (instance.isNetwork()) {
				Network subNetwork = instance.getNetwork();
				new HierarchyComputer(instances, instance).doSwitch(subNetwork);
			}

			instance.getHierarchy().addAll(instances);
		}

		return null;
	}

}
