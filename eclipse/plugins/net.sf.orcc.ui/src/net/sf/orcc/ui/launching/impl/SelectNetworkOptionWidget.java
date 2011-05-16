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

import net.sf.orcc.plugins.OptionSelectNetwork;
import net.sf.orcc.ui.editor.FilteredRefinementDialog;
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.tabs.OrccAbstractSettingsTab;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * Class that create input file interface into plugin options.
 * 
 * @author Jerome Gorin
 * @author Matthieu Wipliez
 * 
 */
public class SelectNetworkOptionWidget implements ModifyListener, OptionWidget {

	/**
	 * composite that contains the components of this option
	 */
	private Composite composite;

	private OrccAbstractSettingsTab launchConfigurationTab;

	private OptionSelectNetwork option;

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
	public SelectNetworkOptionWidget(OrccAbstractSettingsTab tab,
			OptionSelectNetwork option, Composite parent) {
		this.launchConfigurationTab = tab;
		this.option = option;
		this.value = "";

		createInputFile(parent);
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
				IProject project = launchConfigurationTab.getProjectFromText();
				String network = selectNetwork(project, composite.getShell());
				if (network == null) {
					network = "";
				}
				text.setText(network);
			}
		});

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

		IProject project = launchConfigurationTab.getProjectFromText();
		IResource file = OrccUtil.getFile(project, text.getText(), "xdf");
		if (file == null || !file.exists()) {
			launchConfigurationTab.setErrorMessage(option.getName()
					+ " refers to a non-existent network");
			return false;
		} else {
			return true;
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

	/**
	 * Selects the qualified identifier of a network.
	 * 
	 * @param vertex
	 *            a vertex
	 * @param shell
	 *            shell
	 * @return the qualified identifier of a network
	 */
	private String selectNetwork(IProject project, Shell shell) {
		FilteredRefinementDialog dialog = new FilteredRefinementDialog(project,
				shell, "xdf");
		dialog.setTitle("Select network");
		dialog.setMessage("&Select existing network:");
		String refinement = text.getText();
		if (refinement != null) {
			dialog.setInitialPattern(refinement);
		}
		int result = dialog.open();
		if (result == Window.OK) {
			return (String) dialog.getFirstResult();
		}

		return null;
	}

	@Override
	public void show() {
		composite.setVisible(true);
		((GridData) composite.getLayoutData()).exclude = false;
		launchConfigurationTab.updateSize();
	}

}
