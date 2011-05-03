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

import java.util.List;

import net.sf.orcc.plugins.OptionCheckbox;
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.tabs.OrccAbstractSettingsTab;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * Class that add a check box input into plugin options.
 * 
 * @author Jerome Gorin
 * @author Matthieu Wipliez
 * 
 */
public class CheckBoxOptionWidget implements OptionWidget, SelectionListener {

	private Button checkBox;

	/**
	 * composite that contains the components of this option
	 */
	private Composite composite;

	private OrccAbstractSettingsTab launchConfigurationTab;

	private OptionCheckbox option;

	/**
	 * Value of the option
	 */
	private boolean value;

	private List<OptionWidget> widgets;

	/**
	 * Creates a new input file option.
	 */
	public CheckBoxOptionWidget(OrccAbstractSettingsTab tab, OptionCheckbox option,
			Composite parent) {
		this.launchConfigurationTab = tab;
		this.option = option;
		this.value = false;

		createCheckBox(parent);
	}

	/**
	 * Creates the interface of the BrowseFile text into the given group
	 * 
	 * @param font
	 *            Font used in the interface
	 * @param group
	 *            Group to add the input file interface
	 */
	private void createCheckBox(Composite parent) {
		Font font = parent.getFont();
		GridData data = new GridData(SWT.LEFT, SWT.TOP, false, false);
		data.horizontalIndent = 5;

		checkBox = new Button(parent, SWT.CHECK);
		checkBox.setFont(font);
		checkBox.setSelection(value);
		checkBox.addSelectionListener(this);
		checkBox.setText(option.getName());
		checkBox.setLayoutData(data);
		checkBox.setToolTipText(option.getDescription());

		composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		composite.setLayout(layout);

		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 1;
		data.horizontalIndent = 10;
		composite.setLayoutData(data);

		widgets = OptionWidgetManager.createOptions(launchConfigurationTab,
				option.getOptions(), composite);

		hide();
	}

	@Override
	public void hide() {
		checkBox.setVisible(false);
		((GridData) checkBox.getLayoutData()).exclude = true;

		showComposite(false);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration)
			throws CoreException {
		boolean defaultValue = Boolean.parseBoolean(option.getDefaultValue());
		value = configuration
				.getAttribute(option.getIdentifier(), defaultValue);

		checkBox.setSelection(value);
		if (value) {
			OptionWidgetManager.showOptions(widgets);
		}

		OptionWidgetManager.initializeFromOptions(widgets, configuration);
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		if (value) {
			return OptionWidgetManager.isValidOptions(widgets, launchConfig);
		} else {
			return true;
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(option.getIdentifier(), value);
		if (value) {
			OptionWidgetManager.performApplyOptions(widgets, configuration);
		}
	}

	@Override
	public void show() {
		checkBox.setVisible(true);
		((GridData) checkBox.getLayoutData()).exclude = false;

		showComposite(value);
	}

	private void showComposite(boolean show) {
		if (widgets.isEmpty()) {
			show = false;
		}

		composite.setVisible(show);
		((GridData) composite.getLayoutData()).exclude = !show;
		launchConfigurationTab.updateSize();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		value = checkBox.getSelection();
		if (value) {
			showComposite(true);
			OptionWidgetManager.showOptions(widgets);
		} else {
			showComposite(false);
		}
		launchConfigurationTab.updateLaunchConfigurationDialog();
	}

}
