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
package net.sf.orcc.ui.editor;

import java.io.OutputStream;

import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.graphiti.GraphitiModelPlugin;
import net.sf.orcc.graphiti.io.ITransformation;
import net.sf.orcc.graphiti.model.Configuration;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

/**
 * This class defines a transformation from CAL to Graphiti.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalTransformation implements ITransformation {

	@Override
	public Graph transform(IFile file) {
		XtextResourceSet resourceSet = new XtextResourceSet();
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL,
				Boolean.TRUE);
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(),
				true);
		Resource resource = resourceSet.getResource(uri, true);
		AstEntity entity = (AstEntity) resource.getContents().get(0);
		AstActor actor = entity.getActor();

		Configuration configuration = GraphitiModelPlugin.getDefault()
				.getConfiguration("CAL");
		Graph graph = new Graph(configuration,
				configuration.getGraphType("CAL actor"), true);

		graph.setValue(ObjectType.PARAMETER_ID, entity.getName());
		ObjectType type = configuration.getVertexType("Input port");
		for (AstPort port : actor.getInputs()) {
			Vertex vertex = new Vertex(type);
			vertex.setValue(ObjectType.PARAMETER_ID, port.getName());
			graph.addVertex(vertex);
		}

		type = configuration.getVertexType("Output port");
		for (AstPort port : actor.getOutputs()) {
			Vertex vertex = new Vertex(type);
			vertex.setValue(ObjectType.PARAMETER_ID, port.getName());
			graph.addVertex(vertex);
		}

		return graph;
	}

	@Override
	public void transform(Graph graph, OutputStream out) {
		// never write back
	}

}
