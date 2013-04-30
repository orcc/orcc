/*
 * Copyright (c) 2008-2010, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.validators;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.IValidator;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

/**
 * This class implements a model validator.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DataflowValidator implements IValidator {

	private boolean checkInputPorts(Graph graph, IFile file) {
		boolean res = true;
		for (Vertex vertex : graph.vertexSet()) {
			Set<Edge> edges = graph.incomingEdgesOf(vertex);
			Map<String, Integer> countMap = new HashMap<String, Integer>();
			for (Edge edge : edges) {
				Object value = edge.getValue(ObjectType.PARAMETER_TARGET_PORT);
				if (value != null) {
					String tgt = (String) value;
					Integer inCount = countMap.get(tgt);
					if (inCount == null) {
						inCount = 0;
					}

					countMap.put(tgt, inCount + 1);
				}
			}

			for (Entry<String, Integer> count : countMap.entrySet()) {
				if (count.getValue() > 1) {
					res = false;
					String message = "The input port " + count.getKey()
							+ " of vertex "
							+ vertex.getValue(ObjectType.PARAMETER_ID)
							+ " has " + count.getValue() + " connections "
							+ "but should not have more than one connection";
					createMarker(file, message);
				}
			}
		}

		return res;
	}

	protected void createMarker(IFile file, String message) {
		try {
			IMarker marker = file.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			marker.setAttribute(IMarker.MESSAGE, message);
		} catch (CoreException e) {
		}
	}

	@Override
	public boolean validate(Graph graph, IFile file) {
		return checkInputPorts(graph, file);
	}

}
