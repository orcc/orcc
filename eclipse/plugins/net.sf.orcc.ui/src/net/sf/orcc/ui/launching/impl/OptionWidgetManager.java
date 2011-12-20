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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.plugins.Option;
import net.sf.orcc.plugins.OptionBrowseFile;
import net.sf.orcc.plugins.OptionCheckbox;
import net.sf.orcc.plugins.OptionComboBox;
import net.sf.orcc.plugins.OptionSelectNetwork;
import net.sf.orcc.plugins.OptionSelectNetworks;
import net.sf.orcc.plugins.OptionTextBox;
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.tabs.OrccAbstractSettingsTab;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;

/**
 * This class defines an option widget manager.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 */
public class OptionWidgetManager {

	private static OptionWidget createOption(OrccAbstractSettingsTab tab,
			Option option, Composite parent) {
		if (option instanceof OptionCheckbox) {
			return new CheckBoxOptionWidget(tab, (OptionCheckbox) option,
					parent);
		} else if (option instanceof OptionBrowseFile) {
			return new BrowseFileOptionWidget(tab, (OptionBrowseFile) option,
					parent);
		} else if (option instanceof OptionComboBox) {
			return new ComboBoxOptionWidget(tab, (OptionComboBox) option,
					parent);
		} else if (option instanceof OptionTextBox) {
			return new TextBoxOptionWidget(tab, option, parent);
		} else if (option instanceof OptionSelectNetwork) {
			return new SelectNetworkOptionWidget(tab, option, parent);
		} else if (option instanceof OptionSelectNetworks) {
			return new SelectNetworksOptionWidget(tab, option, parent);
		} else {
			return null;
		}
	}

	public static List<OptionWidget> createOptions(OrccAbstractSettingsTab tab,
			List<Option> options, Composite parent) {
		List<OptionWidget> widgets = new ArrayList<OptionWidget>();
		for (Option option : options) {
			OptionWidget widget = createOption(tab, option, parent);
			if (widget != null) {
				widgets.add(widget);
			}
		}
		return widgets;
	}

	public static void hideOptions(List<OptionWidget> widgets) {
		for (OptionWidget widget : widgets) {
			widget.hide();
		}
	}

	/**
	 * Initializes this tab's controls with values from the given launch
	 * configuration.
	 * 
	 * @param configuration
	 *            launch configuration
	 */
	public static void initializeFromOptions(List<OptionWidget> widgets,
			ILaunchConfiguration configuration) throws CoreException {
		for (OptionWidget widget : widgets) {
			widget.initializeFrom(configuration);
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
	public static boolean isValidOptions(List<OptionWidget> widgets,
			ILaunchConfiguration launchConfig) {
		for (OptionWidget widget : widgets) {
			if (!widget.isValid(launchConfig)) {
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
	public static void performApplyOptions(List<OptionWidget> widgets,
			ILaunchConfigurationWorkingCopy configuration) {
		for (OptionWidget widget : widgets) {
			widget.performApply(configuration);
		}
	}

	public static void showOptions(List<OptionWidget> widgets) {
		for (OptionWidget widget : widgets) {
			widget.show();
		}
	}

}
