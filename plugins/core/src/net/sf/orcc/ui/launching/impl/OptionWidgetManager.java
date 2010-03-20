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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;

import net.sf.orcc.backends.BackendOption;
import net.sf.orcc.backends.CheckboxOption;
import net.sf.orcc.backends.BrowseFileOption;
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.RunSettingsTab;

/**
 * This class defines an option widget manager.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OptionWidgetManager {

	private List<OptionWidget> backendOptions;

	private Composite parent;

	private RunSettingsTab launchConfigurationTab;

	public OptionWidgetManager(RunSettingsTab tab) {
		backendOptions = new ArrayList<OptionWidget>();
		launchConfigurationTab = tab;
	}

	public void createOptions(List<BackendOption> options, Composite parent) {
		this.parent = parent;
		for (BackendOption option : options) {
			OptionWidget control = createOption(option);
			backendOptions.add(control);
		}
	}

	private OptionWidget createOption(BackendOption option) {
		if (option instanceof CheckboxOption) {
			return new CheckBoxOptionWidget(launchConfigurationTab,
					(CheckboxOption) option, parent);
		} else if (option instanceof BrowseFileOption) {
			return new BrowseFileOptionWidget(launchConfigurationTab,
					(BrowseFileOption) option, parent);
		} else {
			return null;
		}
	}

	public void disposeOptions() {
		for (OptionWidget optionWidget : backendOptions) {
			optionWidget.dispose();
		}

		backendOptions.clear();
	}

	/**
	 * Initializes this tab's controls with values from the given launch
	 * configuration.
	 * 
	 * @param configuration
	 *            launch configuration
	 */
	public void initializeFromOptions(ILaunchConfiguration configuration)
			throws CoreException {
		for (OptionWidget optionWidget : backendOptions) {
			optionWidget.initializeFrom(configuration);
		}
	}

	/**
	 * Returns <code>true</code> if the given launch configuration is valid,
	 * <code>false</code> otherwise.
	 * 
	 * @param launchConfig
	 *            a launch configuration
	 * @return <code>true</code> if the given launch configuration is valid
	 */
	public boolean isValidOptions(ILaunchConfiguration launchConfig) {
		for (OptionWidget optionWidget : backendOptions) {
			if (!optionWidget.isValid(launchConfig)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Copies values from this tab into the given launch configuration.
	 * 
	 * @param configuration
	 *            launch configuration
	 */
	public void performApplyOptions(
			ILaunchConfigurationWorkingCopy configuration) {
		for (OptionWidget optionWidget : backendOptions) {
			optionWidget.performApply(configuration);
		}
	}
	
	/**
	 * Sets this page's error message, possibly <code>null</code>.
	 * 
	 * @param errorMessage the error message or <code>null</code>
	 */
	public void setErrorMessage(String errorMessage) {
		launchConfigurationTab.setErrorMessage(errorMessage);
	}

	public void updateLaunchConfigurationDialog() {
		launchConfigurationTab.updateLaunchConfigurationDialog();
	}

}
