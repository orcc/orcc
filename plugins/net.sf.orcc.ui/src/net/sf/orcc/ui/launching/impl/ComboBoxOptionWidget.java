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

import net.sf.orcc.plugins.ComboBoxOption;
import net.sf.orcc.plugins.backends.BackendFactory;
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.OrccAbstractSettingsTab;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Combo Box option. This option currently only manages a comboBox with ORCC
 * backends selection list for allowing a simulator to select a backend for code
 * generation assistance.
 * 
 * @author plagalay
 * 
 */
public class ComboBoxOptionWidget implements OptionWidget, SelectionListener {

	private Combo comboBox;

	/**
	 * composite that contains the components of this option
	 */
	private Composite composite;

	private OrccAbstractSettingsTab launchConfigurationTab;

	private ComboBoxOption option;

	/**
	 * Value of the option
	 */
	private int value;

	private List<OptionWidget> widgets;

	/**
	 * Creates a new combo box option.
	 */
	public ComboBoxOptionWidget(OrccAbstractSettingsTab tab,
			ComboBoxOption option, Composite parent) {
		this.launchConfigurationTab = tab;
		this.option = option;
		this.value = -1;

		createComboBox(parent);
	}

	/**
	 * Creates the interface of the ComboBox text into the given group
	 * 
	 * @param font
	 *            Font used in the interface
	 * @param group
	 *            Group to add the input file interface
	 */
	private void createComboBox(Composite parent) {
		Font font = parent.getFont();

		Label lbl = new Label(parent, SWT.NONE);
		lbl.setFont(font);
		lbl.setText("Select a backend:");
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		lbl.setLayoutData(data);

		data = new GridData(SWT.LEFT, SWT.TOP, false, false);
		data.horizontalIndent = 5;
		data.horizontalSpan = 2;

		comboBox = new Combo(parent, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
		comboBox.setFont(font);
		comboBox.addSelectionListener(this);
		comboBox.setText(option.getName());
		comboBox.setLayoutData(data);
		comboBox.setToolTipText(option.getDescription());

		// TODO : add comboxOption's selection content definition capability
		// List<String> selections = option.getSelections();
		// for (String selection : selections) {
		// comboBox.add(selection);
		// }
		// For the moment : comboBox lists all ORCC backends
		BackendFactory factory = BackendFactory.getInstance();
		for (String backend : factory.listPlugins()) {
			comboBox.add(backend);
		}

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
		comboBox.setVisible(false);
		((GridData) comboBox.getLayoutData()).exclude = true;

		showComposite(false);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration)
			throws CoreException {
		String defaultValue = configuration.getAttribute(
				option.getIdentifier(), option.getDefaultValue());
		value = comboBox.indexOf(defaultValue);
		if (value == -1) {
			comboBox.deselectAll();
		} else {
			comboBox.select(value);
			OptionWidgetManager.showOptions(widgets);
		}
		OptionWidgetManager.initializeFromOptions(widgets, configuration);
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		if (value == -1) {
			return false;
		}
		return true;
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (value > -1) {
			String attrValue = comboBox.getItem(value);
			configuration.setAttribute(option.getIdentifier(), attrValue);
			OptionWidgetManager.performApplyOptions(widgets, configuration);
		}
	}

	@Override
	public void show() {
		comboBox.setVisible(true);
		((GridData) comboBox.getLayoutData()).exclude = false;

		showComposite(value > -1);
	}

	private void showComposite(boolean show) {
		if (widgets.isEmpty()) {
			show = false;
		}

		composite.setVisible(show);
		((GridData) composite.getLayoutData()).exclude = !show;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		value = comboBox.getSelectionIndex();
		if (value != -1) {
			showComposite(true);
			// String selection = comboBox.getItem(value);
			OptionWidgetManager.showOptions(widgets);
		} else {
			showComposite(false);
		}

		launchConfigurationTab.updateLaunchConfigurationDialog();
	}
}
