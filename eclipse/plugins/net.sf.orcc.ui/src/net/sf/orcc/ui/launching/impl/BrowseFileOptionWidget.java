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
package net.sf.orcc.ui.launching.impl;

import java.io.File;

import net.sf.orcc.OrccActivator;
import net.sf.orcc.plugins.OptionBrowseFile;
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.tabs.OrccAbstractSettingsTab;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * 
 * Class that create input file interface into plugin options.
 * 
 * @author Jerome Gorin
 * @author Matthieu Wipliez
 * 
 */
public class BrowseFileOptionWidget implements ModifyListener, OptionWidget {

	/**
	 * composite that contains the components of this option
	 */
	private Composite composite;

	private OrccAbstractSettingsTab launchConfigurationTab;

	private OptionBrowseFile option;

	/**
	 * Text connected with the option
	 */
	private Text text;

	private boolean updateLaunchConfiguration;

	/**
	 * The value of this option.
	 */
	private String value;

	/**
	 * Creates a new input file option.
	 */
	public BrowseFileOptionWidget(OrccAbstractSettingsTab tab,
			OptionBrowseFile option, Composite parent) {
		this.launchConfigurationTab = tab;
		this.option = option;
		this.value = "";

		createInputFile(parent);
	}

	/**
	 * Browses the file system.
	 * 
	 * @param shell
	 *            a shell
	 */
	private void browseFileSystem(Shell shell) {
		String file;
		if (option.isFolder()) {
			DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);
			dialog.setMessage("Please select an output folder:");
			dialog.setText("Choose output folder");

			// set initial directory
			dialog.setFilterPath(value);

			file = dialog.open();
		} else {
			FileDialog dialog = new FileDialog(shell, SWT.OPEN);

			String extension = option.getExtension();
			if (extension != null) {
				dialog.setFilterExtensions(new String[] { extension });
			}

			// set initial directory
			dialog.setFilterPath(value);

			file = dialog.open();
		}

		if (file != null) {
			text.setText(file);
		}
	}

	/**
	 * Browses the workspace.
	 * 
	 * @param shell
	 *            a shell
	 * 
	 */
	private void browseWorkspace(Shell shell) {
		ElementTreeSelectionDialog tree = new ElementTreeSelectionDialog(shell,
				WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		tree.setAllowMultiple(false);
		tree.setInput(ResourcesPlugin.getWorkspace().getRoot());

		IResource resource = getResourceFromText();
		if (resource != null) {
			tree.setInitialSelection(resource);
		}

		String name = option.isFolder() ? "folder" : "file";
		tree.setMessage("Please select an existing" + name + ":");
		tree.setTitle("Choose an existing " + name);

		tree.setValidator(new ISelectionStatusValidator() {

			@Override
			public IStatus validate(Object[] selection) {
				String extension = option.getExtension();
				if (selection.length == 1) {
					if (option.isFolder()) {
						if (selection[0] instanceof IFolder) {
							return new Status(IStatus.OK,
									OrccActivator.PLUGIN_ID, "");
						} else {
							return new Status(IStatus.ERROR,
									OrccActivator.PLUGIN_ID,
									"Only folders can be selected, not files nor projects");
						}
					} else if (selection[0] instanceof IFile) {
						IFile file = (IFile) selection[0];
						if (extension != null) {
							if (extension.equals(file.getFileExtension())) {
								return new Status(IStatus.OK,
										OrccActivator.PLUGIN_ID, "");
							} else {
								return new Status(IStatus.ERROR,
										OrccActivator.PLUGIN_ID,
										"Selected file must be an " + extension
												+ " file.");
							}
						} else {
							return new Status(IStatus.OK,
									OrccActivator.PLUGIN_ID, "");
						}
					} else {
						return new Status(IStatus.ERROR,
								OrccActivator.PLUGIN_ID,
								"Only files can be selected, not folders nor projects");
					}
				}

				return new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
						"No file or folder selected.");
			}

		});

		// opens the dialog
		if (tree.open() == Window.OK) {
			resource = (IResource) tree.getFirstResult();
			if (option.isWorkspace()) {
				text.setText(resource.getFullPath().toString());
			} else {
				text.setText(resource.getLocation().toOSString());
			}
		}
	}

	/**
	 * Creates the interface of the BrowseFile text into the given group
	 * 
	 * @param font
	 *            Font used in the interface
	 * @param composite
	 *            Group to add the input file interface
	 */
	private void createInputFile(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		data.horizontalSpan = 3;
		composite.setLayoutData(data);
		hide();

		Font font = parent.getFont();

		Label lbl = new Label(composite, SWT.NONE);
		lbl.setFont(font);
		lbl.setText(option.getName() + ":");
		lbl.setToolTipText(option.getDescription());

		data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		lbl.setLayoutData(data);

		text = new Text(composite, SWT.BORDER | SWT.SINGLE);
		text.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		text.setLayoutData(data);
		text.setText(value);
		text.addModifyListener(this);

		Button buttonBrowse = new Button(composite, SWT.PUSH);
		buttonBrowse.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, false, false);
		buttonBrowse.setLayoutData(data);
		buttonBrowse.setText("&Browse...");
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (option.isWorkspace()) {
					browseWorkspace(composite.getShell());
				} else {
					browseFileSystem(composite.getShell());
				}
			}
		});

	}

	/**
	 * Returns an {@link IResource} instance of the focused file/folder in text
	 * 
	 * @return an {@link IResource} instance of the focused file/folder in text
	 */
	private IResource getResourceFromText() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		IPath path = new Path(value);
		if (option.isFolder() && option.isWorkspace()) {
			return root.getFolder(path);
		} else if (option.isFolder()) {
			return root.getContainerForLocation(path);
		} else if (option.isWorkspace()) {
			return root.getFile(path);
		} else {
			return root.getFileForLocation(path);
		}
	}

	@Override
	public void hide() {
		composite.setVisible(false);
		((GridData) composite.getLayoutData()).exclude = true;
		launchConfigurationTab.updateSize();
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration)
			throws CoreException {
		updateLaunchConfiguration = false;
		text.setText(configuration.getAttribute(option.getIdentifier(),
				option.getDefaultValue()));
		updateLaunchConfiguration = true;
	}

	/**
	 * Tests if the option is valid
	 * 
	 * @return a boolean representing the validation of the option
	 */
	public boolean isValid(ILaunchConfiguration launchConfig) {
		if (value.isEmpty()) {
			launchConfigurationTab.setErrorMessage("The \"" + option.getName()
					+ "\" field is empty");
			return false;
		}

		if (option.isWorkspace()) {
			IResource file = getResourceFromText();
			if (file == null || !file.exists()) {
				launchConfigurationTab.setErrorMessage(option.getName()
						+ " refers to a non-existent "
						+ (option.isFolder() ? "folder" : "file") + ".");
				return false;
			} else {
				return true;
			}
		} else {
			File file = new File(value);
			if (file.exists()) {
				return true;
			} else {
				launchConfigurationTab.setErrorMessage(option.getName()
						+ " refers to a non-existent "
						+ (option.isFolder() ? "folder" : "file") + ".");
				return false;
			}
		}
	}

	@Override
	public void modifyText(ModifyEvent e) {
		value = text.getText();
		if (updateLaunchConfiguration) {
			launchConfigurationTab.updateLaunchConfigurationDialog();
		}
	}

	/**
	 * Apply option to the specificied ILaunchConfigurationWorkingCopy * @param
	 * configuration ILaunchConfigurationWorkingCopy of configuration tab
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(option.getIdentifier(), value);
	}

	@Override
	public void show() {
		composite.setVisible(true);
		((GridData) composite.getLayoutData()).exclude = false;
		launchConfigurationTab.updateSize();
	}

}
