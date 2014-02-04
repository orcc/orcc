/*
 * Copyright (c) 2009/2010, IETR/INSA of Rennes
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
package net.sf.orcc.ui.launching.tabs;

import static net.sf.orcc.OrccLaunchConstants.BACKEND;
import static net.sf.orcc.OrccLaunchConstants.OUTPUT_FOLDER;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import net.sf.orcc.backends.BackendFactory;
import net.sf.orcc.plugins.Option;
import net.sf.orcc.ui.OrccUiActivator;
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.impl.OptionWidgetManager;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * This class defines the settings tab for a "run" configuration of
 * "Orcc compilation".
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class RunSettingsTab extends OrccAbstractSettingsTab {

	private Text textOutput;

	public RunSettingsTab() {
		optionWidgets = new HashMap<String, List<OptionWidget>>();
	}

	private void browseOutputFolder(Shell shell) {
		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.NONE);
		dialog.setMessage("Select output folder:");
		if (outputIsFolder()) {
			// set initial directory if it is valid
			dialog.setFilterPath(textOutput.getText());
		}

		String dir = dialog.open();
		if (dir != null) {
			textOutput.setText(dir);
		}
	}

	private void createControlOutputBackend(final Group group) {
		Label lbl = new Label(group, SWT.NONE);
		lbl.setFont(getFont());
		lbl.setText("Select a backend:");
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		lbl.setLayoutData(data);

		comboPlugin = new Combo(group, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		data.horizontalSpan = 2;
		comboPlugin.setLayoutData(data);

		BackendFactory factory = BackendFactory.getInstance();
		for (String backend : factory.listPlugins()) {
			comboPlugin.add(backend);
		}

		comboPlugin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateOptionSelection();
				updateLaunchConfigurationDialog();
			}
		});
	}

	private void createControlOutputFolder(final Group group) {
		Label lbl = new Label(group, SWT.NONE);
		lbl.setFont(getFont());
		lbl.setText("Output folder:");
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		lbl.setLayoutData(data);

		textOutput = new Text(group, SWT.BORDER | SWT.SINGLE);
		textOutput.setFont(getFont());
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textOutput.setLayoutData(data);
		textOutput.addModifyListener(this);

		Button buttonBrowse = new Button(group, SWT.PUSH);
		buttonBrowse.setFont(getFont());
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

	@Override
	protected void createControlPlugin(Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(getFont());
		group.setText("&Backend:");
		group.setLayout(new GridLayout(3, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		createControlOutputBackend(group);
		createControlOutputFolder(group);
	}

	@Override
	protected void createOptions() {
		BackendFactory factory = BackendFactory.getInstance();
		for (String backend : factory.listPlugins()) {
			List<Option> options = factory.getOptions(backend);
			List<OptionWidget> widgets = OptionWidgetManager.createOptions(this, options, groupOptions);
			optionWidgets.put(backend, widgets);
		}
	}

	private boolean outputIsFolder() {
		String value = textOutput.getText();
		File file = new File(value);

		if (file.exists()) {
			return file.isDirectory();
		} else {
			return true;
		}
	}

	@Override
	public Image getImage() {
		return OrccUiActivator.getImage("icons/orcc.png");
	}

	@Override
	public String getName() {
		return "Compilation settings";
	}

	@Override
	public String getPluginType() {
		return BACKEND;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			updateLaunchConfiguration = false;
			String value = configuration.getAttribute(OUTPUT_FOLDER, "");
			textOutput.setText(value);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		updateLaunchConfiguration = true;
		super.initializeFrom(configuration);
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		String value = textOutput.getText();
		if (value.isEmpty()) {
			setErrorMessage("Output path not specified");
			return false;
		}

		if (!outputIsFolder()) {
			setErrorMessage("Given output must be a folder");
			return false;
		}

		int index = comboPlugin.getSelectionIndex();
		if (index == -1) {
			setErrorMessage("No backend selected.");
			return false;
		}

		return super.isValid(launchConfig);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String value = textOutput.getText();
		configuration.setAttribute(OUTPUT_FOLDER, value);
		super.performApply(configuration);
	}

}
