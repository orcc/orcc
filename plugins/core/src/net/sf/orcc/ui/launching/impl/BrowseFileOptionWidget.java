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

import net.sf.orcc.backends.BrowseFileOption;
import net.sf.orcc.ui.OrccActivator;
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.RunSettingsTab;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
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
 * Class that create input file interface into backend options.
 * 
 * @author Jérôme Gorin
 * @author Matthieu Wipliez
 * 
 */
public class BrowseFileOptionWidget implements ModifyListener, OptionWidget {

	/**
	 * composite that contains the components of this option
	 */
	private Composite composite;

	private RunSettingsTab launchConfigurationTab;

	private BrowseFileOption option;

	/**
	 * Text connected with the option
	 */
	private Text text;

	/**
	 * The value of this option.
	 */
	private String value;

	private boolean updateLaunchConfiguration;

	/**
	 * Creates a new input file option.
	 */
	public BrowseFileOptionWidget(RunSettingsTab tab, BrowseFileOption option,
			Composite parent) {
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
		FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);

		String extension = option.getExtension();
		if (extension != null) {
			fileDialog.setFilterExtensions(new String[] { extension });
		}

		String file = fileDialog.open();
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

		IFile file = getFileFromText();
		if (file != null) {
			tree.setInitialSelection(file);
		}

		tree.setMessage("Please select an existing file:");
		tree.setTitle("Choose an existing file");

		tree.setValidator(new ISelectionStatusValidator() {

			@Override
			public IStatus validate(Object[] selection) {
				String extension = option.getExtension();
				if (selection.length == 1) {
					if (selection[0] instanceof IFile) {
						IFile file = (IFile) selection[0];
						if (extension != null) {
							if (file.getFileExtension().equals(extension)) {
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
					}
				}

				return new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
						"Only files can be selected, not folders nor projects");
			}

		});

		// opens the dialog
		if (tree.open() == Window.OK) {
			file = (IFile) tree.getFirstResult();
			text.setText(file.getLocation().toOSString());
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
	 * Returns an IFile instance of the focused file in text
	 * 
	 * @return an IFile instance of focused file
	 */
	private IFile getFileFromText() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IFile file = root.getFileForLocation(new Path(value));

		return file;
	}

	@Override
	public void hide() {
		composite.setVisible(false);
		((GridData) composite.getLayoutData()).exclude = true;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration)
			throws CoreException {
		updateLaunchConfiguration = false;
		text.setText(configuration.getAttribute(option.getIdentifier(), option
				.getDefaultValue()));
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
			IFile file = getFileFromText();
			if (file == null) {
				launchConfigurationTab.setErrorMessage(option.getName()
						+ " refers to a non-existent file.");
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
						+ " refers to a non-existent file.");
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
	}

}
