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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.NewWizardAction;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * This class defines a default refinement policy.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DefaultRefinementPolicy implements IRefinementPolicy {

	/**
	 * This class provides a listener for the
	 * {@link IResourceChangeEvent#POST_BUILD} event.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private final class NewFileListener implements IResourceChangeListener {

		private String refinement;

		private Vertex vertex;

		public NewFileListener(Vertex vertex) {
			this.vertex = vertex;
		}

		/**
		 * Returns the first {@link IResource} added to the workspace.
		 * 
		 * @param delta
		 *            The {@link IResourceDelta} obtained from an
		 *            {@link IResourceChangeEvent}.
		 * @return The first {@link IResource} added to the workspace.
		 */
		private IResource findAddedFile(IResourceDelta delta) {
			IResourceDelta[] deltas = delta
					.getAffectedChildren(IResourceDelta.CHANGED);
			if (deltas.length == 0) {
				deltas = delta.getAffectedChildren(IResourceDelta.ADDED);
				return deltas[0].getResource();
			} else {
				return findAddedFile(deltas[0]);
			}
		}

		public String getRefinement() {
			return refinement;
		}

		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			IResource resource = findAddedFile(event.getDelta());
			if (resource instanceof IFile) {
				refinement = getRefinementValue(vertex, (IFile) resource);
			}
		}
	}

	private static final String PLUGIN_ID = "net.sf.orcc.graphiti.model";

	/**
	 * Execute the {@link NewWizardAction}, and listens for resource change in
	 * the workspace to find out the file added before calling
	 * {@link #setRefinement(IWorkbenchPage, IFile)} on it.
	 * 
	 * @param shell
	 *            The active window's {@link Shell}.
	 * @param page
	 *            The current {@link IWorkbenchPage}.
	 */
	protected String createNewFile(Vertex vertex, Shell shell) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		NewWizardAction action = new NewWizardAction(
				workbench.getActiveWorkbenchWindow());

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		NewFileListener listener = new NewFileListener(vertex);
		workspace.addResourceChangeListener(listener,
				IResourceChangeEvent.POST_BUILD);
		action.run();
		workspace.removeResourceChangeListener(listener);

		return listener.getRefinement();
	}

	/**
	 * Returns the absolute path of the given refinement using the given parent
	 * filename.
	 * 
	 * @param parent
	 *            parent file name
	 * @param refinement
	 *            a refinement
	 * @return the absolute path of the refinement of the given vertex
	 */
	protected IPath getAbsolutePath(IPath parent, String refinement) {
		// get the path from the refinement
		IPath path = new Path(refinement);
		if (path.isAbsolute() == false) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IFile file = root.getFile(parent);
			path = file.getParent().getFullPath().append(path);
		}

		return path;
	}

	@Override
	public String getNewRefinement(Vertex vertex) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		Shell shell = window.getShell();

		// prompts the user to choose a file
		final String message = "The selected vertex can be refined by an existing "
				+ "file, or by a new file you can create.";
		MessageDialog dialog = new MessageDialog(shell,
				"Set/Update Refinement", null, message, MessageDialog.QUESTION,
				new String[] { "Use an existing file", "Create a new file" }, 0);
		int index = dialog.open();
		if (index == 0) {
			return useExistingFile(vertex, shell);
		} else if (index == 1) {
			return createNewFile(vertex, shell);
		} else {
			return null;
		}
	}

	@Override
	public String getRefinement(Vertex vertex) {
		if (vertex != null) {
			Object refinement = vertex
					.getValue(ObjectType.PARAMETER_REFINEMENT);
			if (refinement instanceof String) {
				return (String) refinement;
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
		IResource resource = root.findMember(path);
		if (resource instanceof IFile) {
			return (IFile) resource;
		} else {
			return null;
		}
	}

	/**
	 * Returns the refinement value corresponding to the given file. This method
	 * automatically uses relative or absolute form depending on the location of
	 * file compared to {@link #editedFile}.
	 * 
	 * @param file
	 *            The file refinining the selected vertex.
	 * @return A {@link String} with the refinement value.
	 */
	protected String getRefinementValue(Vertex vertex, IFile file) {
		IPath editedFilePath = vertex.getParent().getFile().getParent()
				.getFullPath();
		IPath createdFilePath = file.getParent().getFullPath();

		int n = editedFilePath.matchingFirstSegments(createdFilePath);
		IPath refinement = null;
		if (n == 0) {
			// no common path segments: absolute path
			refinement = createdFilePath;
		} else {
			// common path segments: using a relative path
			if (editedFilePath.isPrefixOf(createdFilePath)) {
				// just remove the common segments
				refinement = createdFilePath.removeFirstSegments(n);
			} else {
				// go up
				int max = editedFilePath.segmentCount();
				String path = "";
				for (int i = 0; i < max - n; i++) {
					path += "../";
				}
				// and then down
				path += createdFilePath.removeFirstSegments(n);
				refinement = new Path(path);
			}
		}

		String fileName = file.getName();
		refinement = refinement.append(fileName);
		return refinement.toString();
	}

	@Override
	public boolean isRefinable(Vertex vertex) {
		if (vertex != null) {
			List<Parameter> parameters = vertex.getParameters();
			for (Parameter parameter : parameters) {
				if (parameter.getName().equals(ObjectType.PARAMETER_REFINEMENT)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public String setRefinement(Vertex vertex, String refinement) {
		return (String) vertex.setValue(ObjectType.PARAMETER_REFINEMENT,
				refinement);
	}

	/**
	 * Ask the user to choose an existing file to refine the selected vertex.
	 * 
	 * @param shell
	 *            The active window's {@link Shell}.
	 */
	protected String useExistingFile(final Vertex vertex, Shell shell) {
		ElementTreeSelectionDialog tree = new ElementTreeSelectionDialog(shell,
				WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		tree.setAllowMultiple(false);
		tree.setInput(ResourcesPlugin.getWorkspace().getRoot());
		tree.setMessage("Please select an existing file:");
		tree.setTitle("Choose an existing file");
		tree.setValidator(new ISelectionStatusValidator() {

			@Override
			public IStatus validate(Object[] selection) {
				if (selection.length == 1) {
					if (selection[0] instanceof IFile) {
						IFile file = (IFile) selection[0];
						String message = "Vertex refinement: "
								+ getRefinementValue(vertex, file);
						return new Status(Status.OK, PLUGIN_ID, message);
					}
				}

				return new Status(Status.ERROR, PLUGIN_ID,
						"Only files can be selected, not folders nor projects");
			}

		});

		// initial selection
		IResource resource = getRefinementFile(vertex);
		if (resource == null) {
			resource = vertex.getParent().getFile().getParent();
		}
		tree.setInitialSelection(resource);

		// opens the dialog
		if (tree.open() == Window.OK) {
			IFile file = (IFile) tree.getFirstResult();
			if (file != null) {
				return getRefinementValue(vertex, file);
			}
		}

		return null;
	}

}
