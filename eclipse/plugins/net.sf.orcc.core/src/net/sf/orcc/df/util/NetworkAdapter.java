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
package net.sf.orcc.df.util;

import static net.sf.orcc.df.DfPackage.eINSTANCE;

import java.util.List;

import net.sf.dftools.graph.Graph;
import net.sf.dftools.graph.util.GraphAdapter;
import net.sf.orcc.df.DfVertex;

import org.eclipse.emf.common.notify.Notification;

/**
 * This class defines an adapter for a network that automatically adds an
 * entity/instance/port to the vertices list when it is added to a containment
 * list, and automatically removes an entity/instance/port from the vertices
 * list when it is removed from its containment list.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class NetworkAdapter extends GraphAdapter {

	@Override
	@SuppressWarnings("unchecked")
	public void notifyChanged(Notification msg) {
		Object feature = msg.getFeature();
		if (feature == eINSTANCE.getEntity_Inputs()
				|| feature == eINSTANCE.getEntity_Outputs()
				|| feature == eINSTANCE.getNetwork_Entities()
				|| feature == eINSTANCE.getNetwork_Instances()) {
			switch (msg.getEventType()) {
			case Notification.ADD: {
				DfVertex vertex = (DfVertex) msg.getNewValue();
				((Graph) target).getVertices().add(vertex);
				return;
			}

			case Notification.ADD_MANY: {
				List<DfVertex> vertices = (List<DfVertex>) msg.getNewValue();
				((Graph) target).getVertices().addAll(vertices);
				return;
			}

			case Notification.REMOVE_MANY: {
				List<DfVertex> vertices = (List<DfVertex>) msg.getOldValue();
				((Graph) target).getVertices().removeAll(vertices);
				return;
			}

			case Notification.REMOVE: {
				DfVertex vertex = (DfVertex) msg.getOldValue();
				((Graph) target).getVertices().remove(vertex);
				return;
			}
			}
		}

		// delegates to super if not applicable here
		super.notifyChanged(msg);
	}

}
