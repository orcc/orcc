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
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.tabs.OrccAbstractSettingsTab;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * This abstract class defines an abstract implementation of option widget.
 * 
 * @author Matthieu Wipliez
 */
public abstract class AbstractOptionWidget implements OptionWidget {

	/**
	 * composite that contains the components of this option
	 */
	protected final Composite composite;

	protected final OrccAbstractSettingsTab launchConfigurationTab;

	protected final Option option;

	protected boolean updateLaunchConfiguration;

	/**
	 * Creates a new input file option.
	 */
	public AbstractOptionWidget(OrccAbstractSettingsTab tab, Option option,
			Composite parent) {
		this.launchConfigurationTab = tab;
		this.option = option;

		composite = createControl(parent);
		hide();
	}

	protected abstract Composite createControl(Composite parent);

	@Override
	public void hide() {
		composite.setVisible(false);
		((GridData) composite.getLayoutData()).exclude = true;
		launchConfigurationTab.updateSize();
	}

	@Override
	public void show() {
		composite.setVisible(true);
		((GridData) composite.getLayoutData()).exclude = false;
		launchConfigurationTab.updateSize();
	}

}
