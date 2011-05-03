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
package net.sf.orcc.ui.launching.tabs;

import static net.sf.orcc.OrccLaunchConstants.SIMULATOR;

import java.util.HashMap;
import java.util.List;

import net.sf.orcc.plugins.Option;
import net.sf.orcc.simulators.SimulatorFactory;
import net.sf.orcc.ui.OrccUiActivator;
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.impl.OptionWidgetManager;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

/**
 * 
 * @author Matthieu Wipliez
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class SimuSettingsTab extends OrccAbstractSettingsTab {

	public SimuSettingsTab() {
		optionWidgets = new HashMap<String, List<OptionWidget>>();
	}

	private void createControlOutputSimulator(final Group group) {
		Label lbl = new Label(group, SWT.NONE);
		lbl.setFont(getFont());
		lbl.setText("Select a simulator:");
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		lbl.setLayoutData(data);

		comboPlugin = new Combo(group, SWT.BORDER | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		data.horizontalSpan = 2;
		comboPlugin.setLayoutData(data);

		SimulatorFactory factory = SimulatorFactory.getInstance();
		for (String simulator : factory.listPlugins()) {
			comboPlugin.add(simulator);
		}

		comboPlugin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateOptionSelection();
				updateLaunchConfigurationDialog();
			}
		});
	}

	@Override
	protected void createControlPlugin(Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(getFont());
		group.setText("&Simulator:");
		group.setLayout(new GridLayout(3, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		createControlOutputSimulator(group);
	}

	@Override
	protected void createOptions() {
		SimulatorFactory factory = SimulatorFactory.getInstance();
		for (String simulator : factory.listPlugins()) {
			List<Option> options = factory.getOptions(simulator);
			List<OptionWidget> widgets = OptionWidgetManager.createOptions(
					this, options, groupOptions);
			optionWidgets.put(simulator, widgets);
		}
	}

	@Override
	public Image getImage() {
		return OrccUiActivator.getImage("icons/orcc.png");
	}

	@Override
	public String getName() {
		return "Simulation settings";
	}

	@Override
	public String getPluginType() {
		return SIMULATOR;
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		int index = comboPlugin.getSelectionIndex();
		if (index == -1) {
			setErrorMessage("No simulator selected.");
			return false;
		}

		setErrorMessage(null);

		return super.isValid(launchConfig);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
	}

}
