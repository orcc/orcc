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
package net.sf.orcc.ui;

import net.sf.graphiti.model.DefaultRefinementPolicy;
import net.sf.graphiti.model.Vertex;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * This class extends the default refinement policy with XDF-specific policy.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class NetworkRefinementPolicy extends DefaultRefinementPolicy {

	private final String[] fileExtensions = { "cal", "nl", "xdf" };

	@Override
	public String getNewRefinement(Vertex vertex) {
		String newRefinement = super.getNewRefinement(vertex);
		if (newRefinement == null) {
			return null;
		}

		for (String extension : fileExtensions) {
			IPath path = getAbsolutePath(vertex.getParent().getFileName(),
					newRefinement);
			if (extension.equals(path.getFileExtension())) {
				return new Path(newRefinement).removeFileExtension().toString();
			}
		}

		return null;

	}

	@Override
	public IFile getRefinementFile(Vertex vertex) {
		String refinement = getRefinement(vertex);
		if (refinement == null) {
			return null;
		}

		IPath path = getAbsolutePath(vertex.getParent().getFileName(),
				refinement);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (String extension : fileExtensions) {
			IPath extPath = path.addFileExtension(extension);
			IResource resource = root.findMember(extPath);
			if (resource != null && resource.getType() == IResource.FILE) {
				return (IFile) resource;
			}
		}

		return null;
	}

}
