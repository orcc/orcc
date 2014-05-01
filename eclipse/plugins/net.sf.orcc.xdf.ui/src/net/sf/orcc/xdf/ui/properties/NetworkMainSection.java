/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.xdf.ui.properties;

import net.sf.orcc.df.Network;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Displayed in Main tab when the current network is selected.
 * 
 * @author Antoine Lorence
 * 
 */
public class NetworkMainSection extends AbstractGridBasedSection {

	private Text networkName;

	@Override
	protected String getFormText() {
		return "Network Properties";
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		widgetFactory.createCLabel(formBody, "Name:");

		networkName = widgetFactory.createText(formBody, "", SWT.BORDER);
		networkName.setLayoutData(fillHorizontalData);
		networkName.setEditable(false);
		networkName.setBackground(disabledFieldBGColor);
	}

	@Override
	protected void readValuesFromModels() {
		final Network network = (Network) getSelectedBusinessObject();
		networkName.setText(network.getName());
	}

	@Override
	protected void writeValuesToModel(final Widget widget) {
		final Network network = (Network) getSelectedBusinessObject();

		if (widget == networkName) {
			// TODO: renaming a network must also rename both diagram and
			// network files
			network.setName(networkName.getText());
		}
	}
}
