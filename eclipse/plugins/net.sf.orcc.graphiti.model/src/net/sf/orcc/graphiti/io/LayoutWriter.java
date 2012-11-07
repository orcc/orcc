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
package net.sf.orcc.graphiti.io;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.eclipse.draw2d.geometry.Rectangle;

import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;

/**
 * This class writes the .layout file associated with graphs.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class LayoutWriter {

	public void write(Graph graph, OutputStream out) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<layout>");
			writer.println("\t<vertices>");

			for (Vertex vertex : graph.vertexSet()) {
				String id = (String) vertex.getValue(ObjectType.PARAMETER_ID);
				Rectangle bounds = (Rectangle) vertex
						.getValue(Vertex.PROPERTY_SIZE);
				String x = String.valueOf(bounds.x);
				String y = String.valueOf(bounds.y);

				writer.println("\t\t<vertex id=\"" + id + "\" x=\"" + x
						+ "\" y=\"" + y + "\"/>");
			}

			writer.println("\t</vertices>");
			writer.println("</layout>");
			writer.close();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding unsupported", e);
		}
	}

}
