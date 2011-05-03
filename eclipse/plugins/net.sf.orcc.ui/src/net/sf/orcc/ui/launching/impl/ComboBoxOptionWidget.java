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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.plugins.ComboBoxItem;
import net.sf.orcc.plugins.OptionComboBox;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Combo Box option. This option currently only manages a comboBox with ORCC
 * backends selection list for allowing a simulator to select a backend for code
 * generation assistance.
 * 
 * @author plagalay
 * @author Jerome Gorin
 * 
 */
public class ComboBoxOptionWidget implements OptionWidget, SelectionListener {

	private Combo comboBox;

	/**
	 * composite that contains the components of this option
	 */
	private Composite composite;

	private OrccAbstractSettingsTab launchConfigurationTab;

	private OptionComboBox option;
	
	private Label lbl;

	/**
	 * Value of the option
	 */
	private int value;

	private List<OptionWidget> widgets;

	/**
	 * Creates a new combo box option.
	 */
	public ComboBoxOptionWidget(OrccAbstractSettingsTab tab,
			OptionComboBox option, Composite parent) {
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
		    
		lbl = new Label(parent, SWT.NONE);
		lbl.setFont(font);
		lbl.setText(option.getName());
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

		List<ComboBoxItem> items = option.getComboBoxItems();
		for (ComboBoxItem item : items) {
			comboBox.add(item.getId());
		}

		composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		composite.setLayout(layout);

		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 1;
		data.horizontalIndent = 10;
		composite.setLayoutData(data);

		widgets = new ArrayList<OptionWidget>();
		hide();
	}

	@Override
	public void hide() {
		comboBox.setVisible(false);
		lbl.setVisible(false);
		
		((GridData) comboBox.getLayoutData()).exclude = true;
		((GridData) lbl.getLayoutData()).exclude = true;
		
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
			widgets = updateOptions(value);
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
		lbl.setVisible(true);
		
		((GridData) comboBox.getLayoutData()).exclude = false;
		((GridData) lbl.getLayoutData()).exclude = false;

		showComposite(value > -1);
	}

	private void showComposite(boolean show) {
		if (widgets.isEmpty()) {
			show = false;
		}

		composite.setVisible(show);
		((GridData) composite.getLayoutData()).exclude = !show;
		launchConfigurationTab.updateSize();
	}

	/**
	 * Update child options of the ComboBox selection
	 * 
	 * @param index
	 *            integer value of the selection
	 * 
	 * @return a list of corresponding OptionWidget
	 * 
	 */
	private List<OptionWidget> updateOptions(int index) {
		ComboBoxItem item = option.getComboBoxItems().get(index);
		return OptionWidgetManager.createOptions(launchConfigurationTab,
				item.getOptions(), composite);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		value = comboBox.getSelectionIndex();
		if (value != -1) {
			showComposite(true);
			widgets = updateOptions(value);
			OptionWidgetManager.showOptions(widgets);
		} else {
			showComposite(false);
		}

		launchConfigurationTab.updateLaunchConfigurationDialog();
	}
}
