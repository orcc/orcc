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
package net.sf.orcc.ui.launching;

import static net.sf.orcc.ui.launching.OrccLaunchConstants.BACKEND;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.OUTPUT_FOLDER;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.backends.BackendFactory;
import net.sf.orcc.backends.BackendOption;
import net.sf.orcc.ui.OrccActivator;
import net.sf.orcc.ui.launching.impl.OptionWidgetManager;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
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
 * @author Jérôme Gorin
 * 
 */
public class RunSettingsTab extends AbstractLaunchConfigurationTab implements
		ModifyListener {

	private String backend;

	private Combo comboBackend;

	private Group groupOptions;

	private Map<String, List<OptionWidget>> optionWidgets;

	private Text textOutput;

	private boolean updateLaunchConfiguration;

	public RunSettingsTab() {
		optionWidgets = new HashMap<String, List<OptionWidget>>();
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

		createControlBackend(font, composite);
		createControlOption(font, composite);
		createOptions();
	}

	private void createControlBackend(Font font, Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(font);
		group.setText("&Backend:");
		group.setLayout(new GridLayout(3, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		createControlOutputBackend(font, group);
		createControlOutputFolder(font, group);
	}

	private void createControlOption(Font font, Composite parent) {
		groupOptions = new Group(parent, SWT.NONE);
		groupOptions.setFont(font);
		groupOptions.setText("&Options:");

		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		groupOptions.setLayout(layout);

		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		groupOptions.setLayoutData(data);
	}

	private void createControlOutputBackend(final Font font, final Group group) {
		Label lbl = new Label(group, SWT.NONE);
		lbl.setFont(font);
		lbl.setText("Select a backend:");
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		lbl.setLayoutData(data);

		comboBackend = new Combo(group, SWT.BORDER | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		data.horizontalSpan = 2;
		comboBackend.setLayoutData(data);

		BackendFactory factory = BackendFactory.getInstance();
		for (String backend : factory.listBackends()) {
			comboBackend.add(backend);
		}

		comboBackend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateOptionSelection();
				updateLaunchConfigurationDialog();
			}
		});
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
		textOutput.addModifyListener(this);

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

	/**
	 * Creates the controls for all options, but do not show them yet.
	 */
	private void createOptions() {
		BackendFactory factory = BackendFactory.getInstance();
		for (String backend : factory.listBackends()) {
			List<BackendOption> options = factory.getOptions(backend);
			List<OptionWidget> widgets = OptionWidgetManager.createOptions(
					this, options, groupOptions);
			optionWidgets.put(backend, widgets);
		}
	}

	@Override
	public void dispose() {
		optionWidgets.clear();
		if (groupOptions != null) {
			groupOptions.dispose();
		}
	}

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
		return OrccActivator.getImage("icons/orcc_run.gif");
	}

	@Override
	public String getName() {
		return "Compilation settings";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			backend = configuration.getAttribute(BACKEND, "");

			int index = comboBackend.indexOf(backend);
			comboBackend.select(index);

			updateLaunchConfiguration = false;
			String value = configuration.getAttribute(OUTPUT_FOLDER, "");
			textOutput.setText(value);
			updateLaunchConfiguration = true;

			// initialize from all options
			for (Entry<String, List<OptionWidget>> entry : optionWidgets
					.entrySet()) {
				List<OptionWidget> widgets = entry.getValue();
				OptionWidgetManager.initializeFromOptions(widgets,
						configuration);
			}

			updateOptionSelection();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		int index = comboBackend.getSelectionIndex();
		if (index == -1) {
			setErrorMessage("No backend selected.");
			return false;
		}

		String value = textOutput.getText();
		if (value.isEmpty()) {
			setErrorMessage("Output path not specified");
			return false;
		}

		if (!getFolderFromText()) {
			setErrorMessage("Given output path does not specify an existing folder");
			return false;
		}

		List<OptionWidget> widgets = optionWidgets.get(backend);
		if (OptionWidgetManager.isValidOptions(widgets, launchConfig)) {
			setErrorMessage(null);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void modifyText(ModifyEvent e) {
		if (updateLaunchConfiguration) {
			updateLaunchConfigurationDialog();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String value = textOutput.getText();
		configuration.setAttribute(OUTPUT_FOLDER, value);

		int index = comboBackend.getSelectionIndex();
		if (index != -1) {
			value = comboBackend.getItem(index);
			configuration.setAttribute(BACKEND, value);
		}

		List<OptionWidget> widgets = optionWidgets.get(backend);
		if (widgets != null) {
			OptionWidgetManager.performApplyOptions(widgets, configuration);
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(OUTPUT_FOLDER, "");
		configuration.setAttribute(BACKEND, "");
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		super.setErrorMessage(errorMessage);
	}

	@Override
	public void updateLaunchConfigurationDialog() {
		super.updateLaunchConfigurationDialog();
	}

	private void updateOptionSelection() {
		if (!backend.isEmpty()) {
			List<OptionWidget> widgets = optionWidgets.get(backend);
			OptionWidgetManager.hideOptions(widgets);
		}

		int index = comboBackend.getSelectionIndex();
		if (index != -1) {
			backend = comboBackend.getItem(index);
			List<OptionWidget> widgets = optionWidgets.get(backend);
			OptionWidgetManager.showOptions(widgets);
		}
	}

}
