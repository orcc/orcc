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

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.AbstractPrinter;
import net.sf.orcc.graphiti.io.ITransformation;
import net.sf.orcc.graphiti.model.AbstractObject;
import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;

import org.eclipse.core.resources.IFile;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

/**
 * This class defines a writer of .nl files.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class NlTransformation implements ITransformation {

	private STGroup group;

	@Override
	public Graph transform(IFile file) {
		return null;
	}

	@Override
	public void transform(Graph graph, OutputStream out) {
		// reload group
		group = AbstractPrinter.loadGroup("net/sf/orcc/ui/editor/NL.stg",
				NlTransformation.class.getClassLoader());

		// register model adaptor for AbstractObject
		group.registerModelAdaptor(AbstractObject.class,
				new ObjectModelAdaptor() {

					@Override
					public Object getProperty(Interpreter interp, ST self,
							Object o, Object property, String propertyName)
							throws STNoSuchPropertyException {
						try {
							// first try standard object adaptor
							return super.getProperty(interp, self, o, property,
									propertyName);
						} catch (STNoSuchPropertyException e) {
							// if not found, try in the properties
							return ((AbstractObject) o).getValue(String
									.valueOf(property));
						}
					}
				});

		// instantiate template
		ST template = group.getInstanceOf("printNetwork");
		template.add("id", graph.getValue(ObjectType.PARAMETER_ID));

		List<Vertex> inputs = new ArrayList<Vertex>();
		template.add("inputs", inputs);
		List<Vertex> outputs = new ArrayList<Vertex>();
		template.add("outputs", outputs);
		List<Vertex> instances = new ArrayList<Vertex>();
		template.add("instances", instances);

		for (Vertex vertex : graph.vertexSet()) {
			if ("Input port".equals(vertex.getType().getName())) {
				inputs.add(vertex);
			} else if ("Output port".equals(vertex.getType().getName())) {
				outputs.add(vertex);
			} else {
				instances.add(vertex);
			}
		}

		List<Edge> connections = new ArrayList<Edge>(graph.edgeSet());
		template.add("connections", connections);

		byte[] b = template.render(80).getBytes();
		try {
			out.write(b);
			out.close();
		} catch (IOException e) {
			throw new OrccRuntimeException("I/O error", e);
		}
	}

}
