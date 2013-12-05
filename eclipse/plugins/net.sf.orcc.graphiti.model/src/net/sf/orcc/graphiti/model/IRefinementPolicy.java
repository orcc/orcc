/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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

import org.eclipse.core.resources.IFile;

/**
 * This interface defines the policy for refining vertices.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface IRefinementPolicy {

	/**
	 * Returns the value of the refinement parameter associated with the given
	 * vertex.
	 * 
	 * @param vertex
	 *            a vertex
	 * @return the value of the refinement parameter associated with the given
	 *         vertex, or <code>null</code>
	 */
	String getRefinement(Vertex vertex);

	/**
	 * Returns the file that corresponds to the refinement associated with the
	 * given vertex.
	 * 
	 * @param vertex
	 *            a vertex
	 * @return the file that corresponds to the refinement associated with the
	 *         given vertex, or <code>null</code>
	 */
	IFile getRefinementFile(Vertex vertex);

	/**
	 * Returns <code>true</code> if the given vertex accepts a refinement
	 * parameter.
	 * 
	 * @param vertex
	 *            a vertex
	 * @return <code>true</code> if the given vertex accepts a refinement
	 *         parameter.
	 */
	boolean isRefinable(Vertex vertex);

	/**
	 * Gets a new refinement by asking the user.
	 * 
	 * @param vertex
	 *            a vertex
	 * @return <code>true</code> if the refinement changed, <code>false</code>
	 *         otherwise
	 */
	String getNewRefinement(Vertex vertex);

	/**
	 * Sets the refinement of this vertex to the given value.
	 * 
	 * @param vertex
	 *            a vertex
	 * @param refinement
	 *            a refinement
	 * @return the previous value of the refinement
	 */
	String setRefinement(Vertex vertex, String refinement);

}
