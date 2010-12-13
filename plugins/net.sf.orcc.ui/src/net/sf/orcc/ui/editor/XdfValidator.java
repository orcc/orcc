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
package net.sf.orcc.ui.editor;

import java.util.Set;

import net.sf.graphiti.model.Graph;
import net.sf.graphiti.model.IRefinementPolicy;
import net.sf.graphiti.model.ObjectType;
import net.sf.graphiti.model.Vertex;
import net.sf.graphiti.validators.DataflowValidator;

import org.eclipse.core.resources.IFile;

/**
 * This class implements a model validator.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XdfValidator extends DataflowValidator {

	private boolean checkName(Graph graph, IFile file) {
		String name = (String) graph.getValue(ObjectType.PARAMETER_ID);
		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');
		if (index != -1) {
			fileName = fileName.substring(0, index);
		}

		if (!fileName.equals(name)) {
			String message = "The current name of the network is \"" + name
					+ "\", it should be \"" + fileName + "\"";
			createMarker(file, message);
			return false;
		}

		return true;
	}

	private boolean checkRefinements(Graph graph, IFile file) {
		IRefinementPolicy policy = graph.getConfiguration()
				.getRefinementPolicy();

		boolean refinementsValid = true;
		Set<Vertex> vertices = graph.vertexSet();
		for (Vertex vertex : vertices) {
			if ("Instance".equals(vertex.getType().getName())) {
				if (policy.getRefinementFile(vertex) == null) {
					refinementsValid = false;
					String message = "Refinement of vertex "
							+ vertex.getValue(ObjectType.PARAMETER_ID) + ": "
							+ policy.getRefinement(vertex) + " is invalid";
					createMarker(file, message);
				}
			}
		}

		return refinementsValid;
	}

	@Override
	public boolean validate(Graph graph, IFile file) {
		return checkName(graph, file) && checkRefinements(graph, file)
				&& super.validate(graph, file);
	}

}
