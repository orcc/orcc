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
package net.sf.orcc.ui.launching;

import static net.sf.orcc.OrccLaunchConstants.BACKEND;
import static net.sf.orcc.OrccLaunchConstants.COMPILE_XDF;
import static net.sf.orcc.OrccLaunchConstants.OUTPUT_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.RUN_CONFIG_TYPE;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;
import static net.sf.orcc.util.OrccUtil.getQualifiedName;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.BackendFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * This class defines the launch shortcut to launch an Orcc compilation.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrccRunLaunchShortcut implements ILaunchShortcut {

	/**
	 * Opens a directory dialog to select the output folder.
	 * 
	 * @param shell
	 *            the shell
	 * @param resource
	 *            input file
	 * @return output folder
	 */
	private String browseOutputFolder(Shell shell, IResource resource) {
		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.NONE);
		dialog.setMessage("Please select an output folder:");
		dialog.setText("Choose output folder");

		// set initial directory
		String location = resource.getParent().getLocation().toOSString();
		dialog.setFilterPath(location);

		return dialog.open();
	}

	/**
	 * Chooses a configuration and launch it. If no configuration exists, create
	 * one.
	 * 
	 * @param resource
	 *            input file
	 * @param configs
	 *            a possibly empty array of configurations
	 */
	private void chooseAndLaunch(IResource resource,
			ILaunchConfiguration[] configs) {
		if (configs.length == 0) {
			Shell shell = getShell();

			// no configuration: Create one
			ILaunchConfiguration config = createConfiguration(resource);
			if (config != null) {
				// open launch dialog so the user can tune the settings
				DebugUITools.openLaunchConfigurationDialogOnGroup(shell,
						new StructuredSelection(config),
						IDebugUIConstants.ID_RUN_LAUNCH_GROUP);
			}

			return;
		}

		ILaunchConfiguration config = null;
		if (configs.length == 1) {
			// one existing configuration
			config = configs[0];
		} else {
			// several existing configurations, prompt the user
			config = chooseConfiguration(configs);
		}

		if (config != null) {
			DebugUITools.launch(config, ILaunchManager.RUN_MODE);
		}
	}

	/**
	 * Prompts the user to choose a back-end.
	 * 
	 * @return the back-end chosen, or <code>null</code>
	 */
	private String chooseBackend() {
		ILabelProvider labelProvider = new LabelProvider();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), labelProvider);
		BackendFactory factory = BackendFactory.getInstance();
		dialog.setElements(factory.listPlugins().toArray());
		dialog.setTitle("Select backend");
		dialog.setMessage("&Select backend:");
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK) {
			return (String) dialog.getFirstResult();
		}

		return null;
	}

	/**
	 * Prompts the user a launch configuration if there are several of them.
	 * 
	 * @param configs
	 *            a non-empty array of configurations
	 * @return the launch configuration chosen, or <code>null</code>
	 */
	private ILaunchConfiguration chooseConfiguration(
			ILaunchConfiguration[] configs) {
		IDebugModelPresentation labelProvider = DebugUITools
				.newDebugModelPresentation();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), labelProvider);
		dialog.setElements(configs);
		dialog.setTitle("Select Orcc compilation");
		dialog.setMessage("&Select existing configuration:");
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK) {
			return (ILaunchConfiguration) dialog.getFirstResult();
		}

		return null;
	}

	/**
	 * Creates a configuration based on the input file. Prompts the user to pick
	 * an output folder and a back-end.
	 * 
	 * @param file
	 *            the input file
	 * @return the launch configuration created, or <code>null</code>
	 */
	private ILaunchConfiguration createConfiguration(IResource resource) {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		String id = RUN_CONFIG_TYPE;
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(id);

		ILaunchConfiguration config = null;
		try {
			String folder = browseOutputFolder(getShell(), resource);
			if (folder == null) {
				return null;
			}

			String backend = chooseBackend();
			if (backend == null) {
				return null;
			}

			// generate configuration name
			String name = getConfigurationName(resource, backend);
			name = manager.generateLaunchConfigurationName(name);

			// create configuration
			ILaunchConfigurationWorkingCopy wc = type.newInstance(null, name);
			wc.setAttribute(PROJECT, resource.getProject().getName());
			wc.setAttribute(BACKEND, backend);

			wc.setAttribute(COMPILE_XDF, true);
			if (resource.getType() == IResource.FILE) {
				wc.setAttribute(XDF_FILE, getQualifiedName((IFile) resource));
			}
			wc.setAttribute(OUTPUT_FOLDER, folder);

			config = wc.doSave();
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return config;
	}

	private String getConfigurationName(IResource resource, String backend) {
		String name;
		if (resource.getType() == IResource.FILE) {
			name = getQualifiedName((IFile) resource);
		} else {
			name = resource.getName();
		}

		return name + " - " + backend;
	}

	/**
	 * Returns the configurations associated with the given input file.
	 * 
	 * @param resource
	 *            an input file
	 * @return a possibly empty, possibly <code>null</code> array of
	 *         configurations
	 */
	private ILaunchConfiguration[] getConfigurations(IResource resource) {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		String id = RUN_CONFIG_TYPE;
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(id);
		try {
			// configurations that match the given resource
			List<ILaunchConfiguration> configs = new ArrayList<ILaunchConfiguration>();

			// candidates
			ILaunchConfiguration[] candidates = manager
					.getLaunchConfigurations(type);
			String name = null;
			if (resource.getType() == IResource.FILE) {
				name = getQualifiedName((IFile) resource);
			}
			for (ILaunchConfiguration config : candidates) {
				String fileName = config.getAttribute(XDF_FILE, "");
				if (fileName.equals(name)) {
					configs.add(config);
				}
			}

			return configs.toArray(new ILaunchConfiguration[] {});
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}

	public IResource getLaunchableResource(IEditorPart editorpart) {
		IEditorInput input = editorpart.getEditorInput();
		if (input instanceof IFileEditorInput) {
			return ((IFileEditorInput) input).getFile();
		}

		return null;
	}

	public IResource getLaunchableResource(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				return (IResource) obj;
			}
		}

		return null;
	}

	private Shell getShell() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return window.getShell();
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		IResource resource = getLaunchableResource(editor);
		if (resource != null) {
			chooseAndLaunch(resource, getConfigurations(resource));
		}
	}

	@Override
	public void launch(ISelection selection, String mode) {
		IResource resource = getLaunchableResource(selection);
		if (resource != null) {
			chooseAndLaunch(resource, getConfigurations(resource));
		}
	}

}
