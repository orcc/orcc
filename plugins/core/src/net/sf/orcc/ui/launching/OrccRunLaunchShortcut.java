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

import static net.sf.orcc.ui.launching.OrccLaunchConstants.BACKEND;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_FILE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.OUTPUT_FOLDER;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.RUN_CONFIG_TYPE;

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
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
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
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrccRunLaunchShortcut implements ILaunchShortcut2 {

	private String browseOutputFolder(Shell shell, IFile file) {
		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.NONE);
		dialog.setMessage("Select output folder:");
		// set initial directory
		String location = file.getParent().getLocation().toOSString();
		dialog.setFilterPath(location);

		return dialog.open();
	}

	private void chooseAndLaunch(IFile file, ILaunchConfiguration[] configs) {
		ILaunchConfiguration config = null;
		if (configs.length == 0) {
			config = createConfiguration(file);
		} else if (configs.length == 1) {
			config = configs[0];
		} else {
			config = chooseConfiguration(configs);
		}

		if (config != null) {
			DebugUITools.launch(config, ILaunchManager.RUN_MODE);
		}
	}

	private String chooseBackend() {
		ILabelProvider labelProvider = new LabelProvider();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), labelProvider);
		BackendFactory factory = BackendFactory.getInstance();
		dialog.setElements(factory.listBackends().toArray());
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

	private ILaunchConfiguration createConfiguration(IFile file) {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		String id = RUN_CONFIG_TYPE;
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(id);

		ILaunchConfigurationWorkingCopy wc;
		ILaunchConfiguration config = null;
		try {
			String name = file.getName();
			name = manager.generateUniqueLaunchConfigurationNameFrom(name);
			wc = type.newInstance(null, name);

			// source file
			wc.setAttribute(INPUT_FILE, file.getLocation().toOSString());

			// output folder
			String folder = browseOutputFolder(getShell(), file);
			if (folder == null) {
				return null;
			}
			wc.setAttribute(OUTPUT_FOLDER, folder);

			// backend
			String backend = chooseBackend();
			if (backend == null) {
				return null;
			}
			wc.setAttribute(BACKEND, backend);

			// other options need not be set.

			config = wc.doSave();
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return config;
	}

	private ILaunchConfiguration[] getConfigurations(IFile file) {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		String id = RUN_CONFIG_TYPE;
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(id);
		try {
			// configurations that match the given resource
			List<ILaunchConfiguration> configs = new ArrayList<ILaunchConfiguration>();

			// candidates
			ILaunchConfiguration[] candidates = manager
					.getLaunchConfigurations(type);
			for (ILaunchConfiguration config : candidates) {
				String fileName = config.getAttribute(INPUT_FILE, "");
				if (fileName.equals(file.getLocation().toOSString())) {
					configs.add(config);
				}
			}

			return configs.toArray(new ILaunchConfiguration[] {});
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		IEditorInput input = editorpart.getEditorInput();
		if (input instanceof IFileEditorInput) {
			return ((IFileEditorInput) input).getFile();
		}

		return null;
	}

	@Override
	public IResource getLaunchableResource(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IFile) {
				return (IFile) obj;
			}
		}

		return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		IResource resource = getLaunchableResource(editorpart);
		if (resource instanceof IFile) {
			return getConfigurations((IFile) resource);
		} else {
			return null;
		}
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		IResource resource = getLaunchableResource(selection);
		if (resource instanceof IFile) {
			return getConfigurations((IFile) resource);
		} else {
			return null;
		}
	}

	private Shell getShell() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return window.getShell();
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		IResource resource = getLaunchableResource(editor);
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			chooseAndLaunch(file, getConfigurations(file));
		}
	}

	@Override
	public void launch(ISelection selection, String mode) {
		IResource resource = getLaunchableResource(selection);
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			chooseAndLaunch(file, getConfigurations(file));
		}
	}

}
