/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
import net.sf.orcc.ui.editor.FilteredRefinementDialog;
import net.sf.orcc.ui.launching.tabs.OrccAbstractSettingsTab;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
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
 * This class defines the select network option widget.
 * 
 * @author Jerome Gorin
 * @author Matthieu Wipliez
 */
public class SelectNetworkOptionWidget extends TextBoxOptionWidget {

	/**
	 * Creates a new selectNetwork option widget.
	 */
	public SelectNetworkOptionWidget(OrccAbstractSettingsTab tab,
			Option option, Composite parent) {
		super(tab, option, parent);
	}

	final protected boolean checkNetworkExists(IProject project, String name) {
		IResource file = OrccUtil.getFile(project, name, "xdf");
		return (file != null && file.exists());
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

		return composite;
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		if (super.isValid(launchConfig)) {
			IProject project = launchConfigurationTab.getProjectFromText();
			String name = text.getText();
			if (checkNetworkExists(project, name)) {
				return true;
			}

			launchConfigurationTab.setErrorMessage("The network \"" + name
					+ "\" specified by option \"" + option.getName()
					+ "\" does not exist");
		}
		return false;
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
	final protected String selectNetwork(IProject project, Shell shell) {
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

}
