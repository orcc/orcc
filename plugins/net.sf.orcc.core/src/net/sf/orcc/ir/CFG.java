/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.ir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccException;

import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.jgrapht.ext.StringNameProvider;
import org.jgrapht.ext.VertexNameProvider;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * This class defines a Control Flow Graph.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CFG extends DefaultDirectedGraph<CFGNode, CFGEdge> {

	private class CFGNodeNameProvider implements VertexNameProvider<CFGNode> {

		private int nodeCount;

		private Map<CFGNode, Integer> nodeMap;

		public CFGNodeNameProvider() {
			nodeMap = new HashMap<CFGNode, Integer>();
		}

		@Override
		public String getVertexName(CFGNode node) {
			Integer id = nodeMap.get(node);
			if (id == null) {
				nodeCount++;
				id = nodeCount;
				nodeMap.put(node, id);
			}

			return id.toString();
		}

	}

	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	public CFG() {
		super(CFGEdge.class);
	}

	/**
	 * Prints a graph representation of this CFG.
	 * 
	 * @param file
	 *            output file
	 * @throws OrccException
	 *             if something goes wrong (most probably I/O error)
	 */
	public void printGraph(File file) throws OrccException {
		try {
			OutputStream out = new FileOutputStream(file);
			DOTExporter<CFGNode, CFGEdge> exporter = new DOTExporter<CFGNode, CFGEdge>(
					new CFGNodeNameProvider(),
					new StringNameProvider<CFGNode>(),
					new StringEdgeNameProvider<CFGEdge>());
			exporter.export(new OutputStreamWriter(out), this);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

}
