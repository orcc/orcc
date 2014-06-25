/*
 * Copyright (c) 2014, IETR/INSA of Rennes
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
package net.sf.orcc.xdf.ui.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.impl.XdfResourceImpl;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This Command class can be executed on 1 or more Orcc projects. It will open
 * all diagram files, and update EMF references with latest version of URI.
 * 
 * This will help to quickly update all diagrams in a specific projects. The
 * goal is to update {@link XdfResourceImpl} methods to avoid errors when a
 * port/instance is renamed.
 * 
 * This class is associated with an org.eclipse.ui.commands in plugin.xml, and
 * is triggered with a org.eclipse.ui.menus extension (context menu entry)
 * 
 * @author Antoine Lorence
 *
 */
public class ProjectUpdater extends AbstractHandler {

	private final ResourceSet rs = new ResourceSetImpl();

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);

		if (selection.isEmpty()) {
			return null;
		}

		for (final Object o : selection.toArray()) {
			if (o instanceof IJavaProject) {
				final IJavaProject p = (IJavaProject) o;
				try {
					updateProject(p);
				} catch (CoreException e) {
					throw new ExecutionException(e.getMessage(), e);
				} catch (IOException e) {
					throw new ExecutionException(e.getMessage(), e);
				}
			}
		}

		return null;
	}

	private void updateProject(final IJavaProject project)
			throws CoreException, IOException {

		// Compute the list of all diagrams in the project
		List<IFile> diagramFiles = computeDiagramFilesList(project.getProject());

		for (IFile diagramFile : diagramFiles) {
			try {
				final URI uri = URI.createPlatformResourceURI(diagramFile
						.getFullPath().toString(), true);
				final Resource res = rs.getResource(uri, true);

				final EObject root = res.getContents().get(0);
				if (!(root instanceof Diagram)) {
					continue;
				}
				final Diagram diagram = (Diagram) root;
				// Simply access to each link between graphiti and business
				// objects. This will update the URI of linked objects on the
				// next serialization
				for (Shape shape : diagram.getChildren()) {
					Graphiti.getLinkService()
							.getBusinessObjectForLinkedPictogramElement(shape);
				}
				for (Connection connection : diagram.getConnections()) {
					Graphiti.getLinkService()
							.getBusinessObjectForLinkedPictogramElement(
									connection);
				}
				// Save the resource. The diagram file will now have updated
				// links URI
				res.save(Collections.EMPTY_MAP);
			} catch (OrccRuntimeException e) {
				OrccLogger.severeln(diagramFile.getName()
						+ "[OrccRuntimeException]");
				OrccLogger.traceln("message: " + e.getMessage());
				continue;
			} catch (Exception e) {
				OrccLogger.severeln(diagramFile.getName() + "["
						+ e.getCause().getClass().toString() + "]");
				OrccLogger.traceln("message: " + e.getMessage());
				continue;
			}
		}
	}

	private List<IFile> computeDiagramFilesList(final IContainer container)
			throws CoreException {
		final List<IFile> files = new ArrayList<IFile>();
		for (IResource member : container.members()) {
			if (member.getType() == IResource.FILE) {
				final IFile file = (IFile) member;
				if (OrccUtil.DIAGRAM_SUFFIX.equals(file.getFileExtension())) {
					files.add(file);
				}
			} else if (member.getType() == IResource.FOLDER) {
				files.addAll(computeDiagramFilesList((IContainer) member));
			} else {
				System.err.println("Bad member type: " + member);
			}
		}
		return files;
	}

}
