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
package net.sf.orcc.ui.launching.tabs;

import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_DEBUG;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.FIFO_SIZE;

import java.math.BigInteger;

import net.sf.orcc.ui.OrccUiActivator;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

/**
 * This class defines a tab for options.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OptionsTab extends AbstractLaunchConfigurationTab {

	protected Button debugMode;

	protected Spinner spinner;

	protected void createButton(Font font, Button button, String text,
			String tooltip) {
		button.setFont(font);
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		data.horizontalSpan = 4;
		button.setLayoutData(data);
		button.setText(text);
		button.setToolTipText(tooltip);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		setControl(composite);

		Font font = parent.getFont();
		composite.setFont(font);

		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(data);

		createGroup(font, composite);
	}

	protected void createFifoSize(Font font, Group group) {
		Label label = new Label(group, SWT.NONE);
		label.setFont(font);
		GridData data = new GridData(SWT.CENTER, SWT.CENTER, false, true);
		label.setLayoutData(data);
		label.setText("Default FIFO size: 2 to the power of");

		spinner = new Spinner(group, SWT.WRAP);
		spinner.setFont(font);
		data = new GridData(SWT.CENTER, SWT.CENTER, false, true);
		spinner.setLayoutData(data);
		spinner.setMinimum(0);
		spinner.setMaximum(30);
		spinner.setIncrement(1);
		spinner.setPageIncrement(8);
		
		label = new Label(group, SWT.NONE);
		label.setFont(font);
		data = new GridData(SWT.CENTER, SWT.CENTER, false, true);
		label.setLayoutData(data);
		label.setText("=");

		final Label fifoSize = new Label(group, SWT.CENTER);
		fifoSize.setFont(font);
		data = new GridData(SWT.CENTER, SWT.CENTER, false, true);
		fifoSize.setLayoutData(data);

		spinner.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				int exponent = spinner.getSelection();
				fifoSize.setText(String.valueOf(1 << exponent));
				updateLaunchConfigurationDialog();
			}
		});
	}

	protected void createGroup(Font font, Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(font);
		group.setText("&Options:");
		group.setLayout(new GridLayout(4, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		debugMode = new Button(group, SWT.CHECK);
		createButton(font, debugMode, "Debug mode",
				"Activates debug mode: actors are not cached.");
		createFifoSize(font, group);
	}

	@Override
	public Image getImage() {
		return OrccUiActivator.getImage("icons/orcc.png");
	}

	@Override
	public String getName() {
		return "Compilation options";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			boolean selected = configuration.getAttribute(DEBUG_MODE,
					DEFAULT_DEBUG);
			debugMode.setSelection(selected);

			int size = configuration.getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
			int exponent = BigInteger.valueOf(size).bitLength() - 1;
			spinner.setSelection(exponent);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (isValid(configuration)) {
			boolean selected = debugMode.getSelection();
			configuration.setAttribute(DEBUG_MODE, selected);

			int exponent = spinner.getSelection();
			configuration.setAttribute(FIFO_SIZE, 1 << exponent);
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(DEBUG_MODE, DEFAULT_DEBUG);
		configuration.setAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
	}

}
