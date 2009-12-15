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

import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_FILE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_STIMULUS;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.OUTPUT_FOLDER;

import java.io.File;

import net.sf.orcc.ui.OrccActivator;

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
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * 
 * @author Matthieu Wipliez / Pierre-Laurent Lagalaye
 * 
 */
public class SimuSettingsTab extends AbstractLaunchConfigurationTab {

	private Text textNetwork;

	private Text textStimulus;

	private Text textOutput;

	private void browseFiles(Shell shell) {
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
				if (selection.length == 1) {
					if (selection[0] instanceof IFile) {
						IFile file = (IFile) selection[0];
						if (file.getFileExtension().equals("xdf")) {
							return new Status(Status.OK,
									OrccActivator.PLUGIN_ID, "");
						} else {
							return new Status(Status.ERROR,
									OrccActivator.PLUGIN_ID,
									"Selected file must be an XDF file.");
						}
					}
				}

				return new Status(Status.ERROR, OrccActivator.PLUGIN_ID,
						"Only files can be selected, not folders nor projects");
			}

		});

		// opens the dialog
		if (tree.open() == Window.OK) {
			file = (IFile) tree.getFirstResult();
			textNetwork.setText(file.getLocation().toOSString());
		}
	}

	private void browseOutputFolder(Shell shell) {
		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.NONE);
		dialog.setMessage("Select output folder:");
		if (getFolderFromText()) {
			// set initial directory if it is valid
			dialog.setFilterPath(textOutput.getText());
		}

		String dir = dialog.open();
		if (dir != null) {
			textOutput.setText(dir);
		}
	}

	private void browseStimulusFiles(Shell shell) {
		FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setText("Select input stimulus:");
		fd.setFilterPath(textStimulus.getText());
		String[] filterExt = { "*.*" };
		fd.setFilterExtensions(filterExt);
		String fileName = fd.open();
		if (fileName != null) {
			textStimulus.setText(fileName);
		}
	}

	@Override
	public void createControl(Composite parent) {
		Font font = parent.getFont();

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(font);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(data);
		setControl(composite);

		createControlNetwork(font, composite);
		createControlBitstream(font, composite);

		Label lbl = new Label(composite, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 1;
		lbl.setLayoutData(data);
		createControlOutput(font, composite);
	}

	private void createControlNetwork(Font font, Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(font);
		group.setText("&Input:");
		group.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		textNetwork = new Text(group, SWT.BORDER | SWT.SINGLE);
		textNetwork.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textNetwork.setLayoutData(data);
		textNetwork.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}

		});

		Button buttonBrowse = new Button(group, SWT.PUSH);
		buttonBrowse.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, false, false);
		buttonBrowse.setLayoutData(data);
		buttonBrowse.setText("&Browse...");
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseFiles(group.getShell());
			}
		});
	}

	private void createControlBitstream(Font font, Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(font);
		group.setText("&Input stimulus:");
		group.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		textStimulus = new Text(group, SWT.BORDER | SWT.SINGLE);
		textStimulus.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textStimulus.setLayoutData(data);
		textStimulus.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}

		});

		Button buttonBrowse = new Button(group, SWT.PUSH);
		buttonBrowse.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, false, false);
		buttonBrowse.setLayoutData(data);
		buttonBrowse.setText("&Browse...");
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseStimulusFiles(group.getShell());
			}
		});
	}

	private void createControlOutput(Font font, Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(font);
		group.setText("&Output:");
		group.setLayout(new GridLayout(3, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		createControlOutputFolder(font, group);
	}

	private void createControlOutputFolder(Font font, final Group group) {
		Label lbl = new Label(group, SWT.NONE);
		lbl.setFont(font);
		lbl.setText("Output folder:");
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		lbl.setLayoutData(data);

		textOutput = new Text(group, SWT.BORDER | SWT.SINGLE);
		textOutput.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textOutput.setLayoutData(data);
		textOutput.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		Button buttonBrowse = new Button(group, SWT.PUSH);
		buttonBrowse.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, false, false);
		buttonBrowse.setLayoutData(data);
		buttonBrowse.setText("&Browse...");
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseOutputFolder(group.getShell());
			}
		});
	}

	private IFile getFileFromText() {
		String value = textNetwork.getText();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IFile file = root.getFileForLocation(new Path(value));

		return file;
	}

	// private boolean getStimulusFromText() {
	// String value = textStimulus.getText();
	// File file = new File(value);
	// if (file.isFile()) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	private boolean getFolderFromText() {
		String value = textOutput.getText();
		File file = new File(value);
		if (file.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Image getImage() {
		return OrccActivator.getImage("icons/orcc_simu.gif");
	}

	@Override
	public String getName() {
		return "Interpretation settings";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			String value = configuration.getAttribute(INPUT_FILE, "");
			textNetwork.setText(value);

			value = configuration.getAttribute(INPUT_STIMULUS, "");
			textStimulus.setText(value);

			value = configuration.getAttribute(OUTPUT_FOLDER, "");
			textOutput.setText(value);

		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		String value = textNetwork.getText();
		if (value.isEmpty()) {
			setErrorMessage("Network path not specified");
			return false;
		}

		IFile file = getFileFromText();
		if (file == null || !file.exists()) {
			setErrorMessage("Given network path does not specify an existing file");
			return false;
		}

		// if (!getStimulusFromText()) {
		// setErrorMessage("Given stimulus path does not specify an existing file");
		// return false;
		// }

		value = textOutput.getText();
		if (value.isEmpty()) {
			setErrorMessage("Output path not specified");
			return false;
		}

		if (!getFolderFromText()) {
			setErrorMessage("Given output path does not specify an existing folder");
			return false;
		}

		setErrorMessage(null);
		return true;
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String value = textNetwork.getText();
		configuration.setAttribute(INPUT_FILE, value);

		value = textStimulus.getText();
		configuration.setAttribute(INPUT_STIMULUS, value);

		value = textOutput.getText();
		configuration.setAttribute(OUTPUT_FOLDER, value);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(INPUT_FILE, "");
		configuration.setAttribute(INPUT_STIMULUS, "");
		configuration.setAttribute(OUTPUT_FOLDER, "");
	}

}

// /*
// * Copyright (c) 2009, IETR/INSA of Rennes
// * All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// * * Redistributions of source code must retain the above copyright notice,
// * this list of conditions and the following disclaimer.
// * * Redistributions in binary form must reproduce the above copyright notice,
// * this list of conditions and the following disclaimer in the documentation
// * and/or other materials provided with the distribution.
// * * Neither the name of the IETR/INSA of Rennes nor the names of its
// * contributors may be used to endorse or promote products derived from this
// * software without specific prior written permission.
// *
// * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
// * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT,
// * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
// ANY
// * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
// * SUCH DAMAGE.
// */
// package net.sf.orcc.ui.launching;
//
// import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_FILE;
// import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_STIMULUS;
// import net.sf.orcc.ui.OrccActivator;
//
// import org.eclipse.core.resources.IFile;
// import org.eclipse.core.resources.IWorkspace;
// import org.eclipse.core.resources.IWorkspaceRoot;
// import org.eclipse.core.resources.ResourcesPlugin;
// import org.eclipse.core.runtime.CoreException;
// import org.eclipse.core.runtime.IStatus;
// import org.eclipse.core.runtime.Path;
// import org.eclipse.core.runtime.Status;
// import org.eclipse.debug.core.ILaunchConfiguration;
// import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
// import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
// import org.eclipse.jface.window.Window;
// import org.eclipse.swt.SWT;
// import org.eclipse.swt.events.ModifyEvent;
// import org.eclipse.swt.events.ModifyListener;
// import org.eclipse.swt.events.SelectionAdapter;
// import org.eclipse.swt.events.SelectionEvent;
// import org.eclipse.swt.graphics.Font;
// import org.eclipse.swt.graphics.Image;
// import org.eclipse.swt.layout.GridData;
// import org.eclipse.swt.layout.GridLayout;
// import org.eclipse.swt.widgets.Button;
// import org.eclipse.swt.widgets.Composite;
// import org.eclipse.swt.widgets.FileDialog;
// import org.eclipse.swt.widgets.Group;
// import org.eclipse.swt.widgets.Shell;
// import org.eclipse.swt.widgets.Text;
// import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
// import org.eclipse.ui.dialogs.ISelectionStatusValidator;
// import org.eclipse.ui.model.WorkbenchContentProvider;
// import org.eclipse.ui.model.WorkbenchLabelProvider;
//
// /**
// *
// * @author Pierre-Laurent Lagalaye
// *
// */
// public class SimuSettingsTab extends AbstractLaunchConfigurationTab {
//
// private Text textNetwork;
// private Text textStimulus;
//
// private void browseXdfFiles(Shell shell) {
// ElementTreeSelectionDialog tree = new ElementTreeSelectionDialog(shell,
// WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(),
// new WorkbenchContentProvider());
// tree.setAllowMultiple(false);
// tree.setInput(ResourcesPlugin.getWorkspace().getRoot());
//
// IFile file = getFileFromText();
// if (file != null) {
// tree.setInitialSelection(file);
// }
//
// tree.setMessage("Please select an existing file:");
// tree.setTitle("Choose an existing file");
//
// tree.setValidator(new ISelectionStatusValidator() {
//
// @Override
// public IStatus validate(Object[] selection) {
// if (selection.length == 1) {
// if (selection[0] instanceof IFile) {
// IFile file = (IFile) selection[0];
// if (file.getFileExtension().equals("xdf")) {
// return new Status(Status.OK,
// OrccActivator.PLUGIN_ID, "");
// } else {
// return new Status(Status.ERROR,
// OrccActivator.PLUGIN_ID,
// "Selected file must be an XDF file.");
// }
// }
// }
//
// return new Status(Status.ERROR, OrccActivator.PLUGIN_ID,
// "Only files can be selected, not folders nor projects");
// }
//
// });
//
// // opens the dialog
// if (tree.open() == Window.OK) {
// file = (IFile) tree.getFirstResult();
// textNetwork.setText(file.getLocation().toOSString());
// }
// }
//
// private void browseStimulusFiles(Shell shell) {
// FileDialog fd = new FileDialog(shell, SWT.OPEN);
// fd.setText("Select input stimulus:");
// fd.setFilterPath(textStimulus.getText());
// String[] filterExt = { "*.*" };
// fd.setFilterExtensions(filterExt);
// String fileName = fd.open();
// if (fileName != null) {
// textStimulus.setText(fileName);
// }
// }
//
// @Override
// public void createControl(Composite parent) {
// Font font = parent.getFont();
//
// Composite composite = new Composite(parent, SWT.NONE);
// composite.setFont(font);
// GridLayout layout = new GridLayout();
// layout.verticalSpacing = 0;
// composite.setLayout(layout);
// GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
// composite.setLayoutData(data);
// setControl(composite);
//
// createControlNetwork(font, composite);
// createControlBitstream(font, composite);
// }
//
// private void createControlNetwork(Font font, Composite parent) {
// final Group group = new Group(parent, SWT.NONE);
// group.setFont(font);
// group.setText("&Input network:");
// group.setLayout(new GridLayout(2, false));
// GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
// group.setLayoutData(data);
//
// textNetwork = new Text(group, SWT.BORDER | SWT.SINGLE);
// textNetwork.setFont(font);
// data = new GridData(SWT.FILL, SWT.CENTER, true, false);
// textNetwork.setLayoutData(data);
// textNetwork.addModifyListener(new ModifyListener() {
//
// @Override
// public void modifyText(ModifyEvent e) {
// updateLaunchConfigurationDialog();
// }
//
// });
//
// Button buttonBrowse = new Button(group, SWT.PUSH);
// buttonBrowse.setFont(font);
// data = new GridData(SWT.FILL, SWT.CENTER, false, false);
// buttonBrowse.setLayoutData(data);
// buttonBrowse.setText("&Browse...");
// buttonBrowse.addSelectionListener(new SelectionAdapter() {
// @Override
// public void widgetSelected(SelectionEvent e) {
// browseXdfFiles(group.getShell());
// }
// });
// }
//
// private void createControlBitstream(Font font, Composite parent) {
// final Group group = new Group(parent, SWT.NONE);
// group.setFont(font);
// group.setText("&Input stimulus:");
// group.setLayout(new GridLayout(2, false));
// GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
// group.setLayoutData(data);
//
// textStimulus = new Text(group, SWT.BORDER | SWT.SINGLE);
// textStimulus.setFont(font);
// data = new GridData(SWT.FILL, SWT.CENTER, true, false);
// textStimulus.setLayoutData(data);
// textStimulus.addModifyListener(new ModifyListener() {
//
// @Override
// public void modifyText(ModifyEvent e) {
// updateLaunchConfigurationDialog();
// }
//
// });
//
// Button buttonBrowse = new Button(group, SWT.PUSH);
// buttonBrowse.setFont(font);
// data = new GridData(SWT.FILL, SWT.CENTER, false, false);
// buttonBrowse.setLayoutData(data);
// buttonBrowse.setText("&Browse...");
// buttonBrowse.addSelectionListener(new SelectionAdapter() {
// @Override
// public void widgetSelected(SelectionEvent e) {
// browseStimulusFiles(group.getShell());
// }
// });
// }
//
// private IFile getFileFromText() {
// String value = textNetwork.getText();
// IWorkspace workspace = ResourcesPlugin.getWorkspace();
// IWorkspaceRoot root = workspace.getRoot();
// IFile file = root.getFileForLocation(new Path(value));
//
// return file;
// }
//
// @Override
// public Image getImage() {
// return OrccActivator.getImage("icons/orcc_simu.gif");
// }
//
// @Override
// public String getName() {
// return "Debug simulation settings";
// }
//
// @Override
// public void initializeFrom(ILaunchConfiguration configuration) {
// try {
// String value = configuration.getAttribute(INPUT_FILE, "");
// textNetwork.setText(value);
// value = configuration.getAttribute(INPUT_STIMULUS, "");
// textStimulus.setText(value);
// } catch (CoreException e) {
// e.printStackTrace();
// }
// }
//
// @Override
// public boolean isValid(ILaunchConfiguration launchConfig) {
// String value = textNetwork.getText();
// if (value.isEmpty()) {
// setErrorMessage("Network path not specified");
// return false;
// }
//
// IFile file = getFileFromText();
// if (file == null || !file.exists()) {
// setErrorMessage("Given network path does not specify an existing file");
// return false;
// }
//
// setErrorMessage(null);
// return true;
// }
//
// @Override
// public void performApply(ILaunchConfigurationWorkingCopy configuration) {
// String value = textNetwork.getText();
// configuration.setAttribute(INPUT_FILE, value);
// value = textStimulus.getText();
// configuration.setAttribute(INPUT_STIMULUS, value);
// }
//
// @Override
// public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
// // network model
// configuration.setAttribute(INPUT_FILE, "");
// // input stimulus
// configuration.setAttribute(INPUT_STIMULUS, "");
// }
//
// }
