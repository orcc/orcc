/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class provides the configuration for a {@link Graph}. A configuration is
 * defined by an extension point, and contains attributes and parameters that
 * are defined for the graph, vertices and edges.
 * <p>
 * Attributes are specified for classes of objects: for example, all vertices of
 * type T will have an attribute A which has the value V. Examples of such
 * attributes for vertices are "shape" or "color".
 * </p>
 * <p>
 * Parameters are specified for each instance of graph/vertex/edge. Examples of
 * parameters are position or id.
 * </p>
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Configuration {

	private String contributorId;

	/**
	 * A edge type name -> edge type object map.
	 */
	private Map<String, ObjectType> edgeTypes;

	/**
	 * The file format associated with this configuration.
	 */
	private FileFormat fileFormat;

	/**
	 * A graph type name -> graph type object map.
	 */
	private Map<String, ObjectType> graphTypes;

	/**
	 * The configuration absolute file name.
	 */
	private String name;

	/**
	 * The refinement policy.
	 */
	private IRefinementPolicy refinementPolicy;

	/**
	 * The validator called when the graph is about to be saved.
	 */
	private IValidator validator;

	/**
	 * A vertex type name -> vertex type object map.
	 */
	private Map<String, ObjectType> vertexTypes;

	/**
	 * Creates a new document configuration.
	 * 
	 * @param name
	 *            the name of this configuration
	 * @param contributorId
	 *            the identifier of the contributor of this configuration
	 * @param fileFormat
	 *            the associated file format
	 * @param refinementFileExtensions
	 *            an array of file extensions
	 * @param graphTypes
	 *            types of graphs
	 * @param vertexTypes
	 *            types of vertices
	 * @param edgeTypes
	 *            types of edges
	 * @param validator
	 *            the validator
	 */
	public Configuration(String name, String contributorId,
			FileFormat fileFormat, Map<String, ObjectType> graphTypes,
			Map<String, ObjectType> vertexTypes,
			Map<String, ObjectType> edgeTypes, IValidator validator,
			IRefinementPolicy policy) {
		this.contributorId = contributorId;
		this.edgeTypes = edgeTypes;
		this.fileFormat = fileFormat;
		this.name = name;
		this.graphTypes = graphTypes;
		if (policy == null) {
			refinementPolicy = new DefaultRefinementPolicy();
		} else {
			this.refinementPolicy = policy;
		}
		this.validator = validator;
		this.vertexTypes = vertexTypes;
	}

	/**
	 * Returns the identifier of the contributor of this configuration.
	 * 
	 * @return the identifier of the contributor of this configuration
	 */
	public String getContributorId() {
		return contributorId;
	}

	/**
	 * Returns the edge type whose name matches the given name.
	 * 
	 * @param name
	 *            The name of the edge type we're looking for.
	 * @return The relevant edge type.
	 */
	public ObjectType getEdgeType(String name) {
		return edgeTypes.get(name);
	}

	/**
	 * Returns the edge types.
	 * 
	 * @return A set of edge types.
	 */
	public Set<ObjectType> getEdgeTypes() {
		return new TreeSet<ObjectType>(edgeTypes.values());
	}

	/**
	 * Returns the file format associated with this configuration.
	 * 
	 * @return A {@link FileFormat} associated with this configuration.
	 */
	public FileFormat getFileFormat() {
		return fileFormat;
	}

	/**
	 * Returns the graph type whose name matches the given name.
	 * 
	 * @param name
	 *            The name of the graph type we're looking for.
	 * @return The relevant graph type.
	 */
	public ObjectType getGraphType(String name) {
		return graphTypes.get(name);
	}

	/**
	 * Returns the graph types.
	 * 
	 * @return A set of graph types.
	 */
	public Set<ObjectType> getGraphTypes() {
		return new TreeSet<ObjectType>(graphTypes.values());
	}

	/**
	 * Returns the name of this configuration.
	 * 
	 * @return the name of this configuration
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the refinement policy for this configuration.
	 * 
	 * @return the refinement policy for this configuration.
	 */
	public IRefinementPolicy getRefinementPolicy() {
		return refinementPolicy;
	}

	/**
	 * Returns the validator for this configuration.
	 * 
	 * @return the validator for this configuration.
	 */
	public IValidator getValidator() {
		return validator;
	}

	/**
	 * Returns the vertex type whose name matches the given name.
	 * 
	 * @param name
	 *            The name of the vertex type we're looking for.
	 * @return The relevant vertex type.
	 */
	public ObjectType getVertexType(String name) {
		return vertexTypes.get(name);
	}

	/**
	 * Returns the vertex types.
	 * 
	 * @return A set of vertex types.
	 */
	public Set<ObjectType> getVertexTypes() {
		return new TreeSet<ObjectType>(vertexTypes.values());
	}

	@Override
	public String toString() {
		return "[" + name + "] " + fileFormat;
	}

}
