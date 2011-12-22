/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import net.sf.orcc.plugins.Option;
import net.sf.orcc.ui.launching.tabs.OrccAbstractSettingsTab;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This class defines the select networks option widget.
 * 
 * @author Matthieu Wipliez
 */
public class SelectNetworksOptionWidget extends SelectNetworkOptionWidget {

	/**
	 * Creates a new selectNetworks option widget.
	 */
	public SelectNetworksOptionWidget(OrccAbstractSettingsTab tab,
			Option option, Composite parent) {
		super(tab, option, parent);
	}

	@Override
	protected Composite createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		data.horizontalSpan = 3;
		composite.setLayoutData(data);

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
		text.addModifyListener(this);

		Button buttonBrowse = new Button(composite, SWT.PUSH);
		buttonBrowse.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, false, false);
		buttonBrowse.setLayoutData(data);
		buttonBrowse.setText("&Add...");
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IProject project = launchConfigurationTab.getProjectFromText();
				String network = selectNetwork(project, composite.getShell());
				if (network != null) {
					String value = text.getText();
					if (!value.isEmpty()) {
						value += ", ";
					}
					value += network;
					text.setText(value);
				}
			}
		});

		return composite;
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		if (value.isEmpty()) {
			launchConfigurationTab.setErrorMessage("The \"" + option.getName()
					+ "\" field is empty");
			return false;
		}
		IProject project = launchConfigurationTab.getProjectFromText();

		String[] networks = text.getText().split("\\s*,\\s*");
		for (String network : networks) {
			if (!checkNetworkExists(project, network)) {
				launchConfigurationTab.setErrorMessage("The network \""
						+ network + "\" specified by option \""
						+ option.getName() + "\" does not exist");
				return false;
			}
		}
		return true;
	}

}
