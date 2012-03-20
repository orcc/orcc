/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.df.util;

import java.util.List;

import net.sf.dftools.graph.Graph;
import net.sf.dftools.graph.Vertex;
import net.sf.dftools.graph.util.GraphAdapter;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EReference;

/**
 * This class defines an adapter for a network that automatically adds a vertex
 * to the vertices list when it is added to a containment list, and
 * automatically removes a vertex from the vertices list when it is removed from
 * its containment list. The list of containment features are specified when
 * creating the adapter.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class GraphContainmentAdapter extends GraphAdapter {

	private EReference[] references;

	/**
	 * Creates a new graph containment adapter.
	 * 
	 * @param references
	 *            containment references
	 */
	public GraphContainmentAdapter(EReference... references) {
		this.references = references;
	}

	@Override
	public void notifyChanged(Notification msg) {
		Object feature = msg.getFeature();
		for (EReference reference : references) {
			if (feature == reference) {
				updateGraph(msg);
				return;
			}
		}

		// delegates to super if not applicable here
		super.notifyChanged(msg);
	}

	@SuppressWarnings("unchecked")
	private void updateGraph(Notification msg) {
		switch (msg.getEventType()) {
		case Notification.ADD: {
			Vertex vertex = (Vertex) msg.getNewValue();
			((Graph) target).getVertices().add(vertex);
			break;
		}

		case Notification.ADD_MANY: {
			List<Vertex> vertices = (List<Vertex>) msg.getNewValue();
			((Graph) target).getVertices().addAll(vertices);
			break;
		}

		case Notification.REMOVE_MANY: {
			List<Vertex> vertices = (List<Vertex>) msg.getOldValue();
			((Graph) target).getVertices().removeAll(vertices);
			break;
		}

		case Notification.REMOVE: {
			Vertex vertex = (Vertex) msg.getOldValue();
			((Graph) target).getVertices().remove(vertex);
			break;
		}
		}
	}

}
