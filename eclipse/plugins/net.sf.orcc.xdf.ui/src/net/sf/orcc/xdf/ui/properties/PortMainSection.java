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

import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.util.TypePrinter;
import net.sf.orcc.ui.editor.PartialCalParser;
import net.sf.orcc.xdf.ui.patterns.NetworkPortPattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Displayed in Main tab when a port is selected.
 * 
 * @author Antoine Lorence
 * 
 */
public class PortMainSection extends AbstractGridBasedSection {

	private Text portName;
	private Text portType;

	final private PartialCalParser calParser = new PartialCalParser();

	@Override
	protected String getFormText() {
		return "Port Properties";
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		widgetFactory.createCLabel(formBody, "Name:");

		portName = widgetFactory.createText(formBody, "", SWT.BORDER);
		portName.setLayoutData(fillHorizontalData);

		widgetFactory.createCLabel(formBody, "Type:");

		portType = widgetFactory.createText(formBody, "", SWT.BORDER);
		portType.setLayoutData(fillHorizontalData);
	}

	@Override
	protected void readValuesFromModels() {
		final Port port = (Port) getSelectedBusinessObject();
		final TypePrinter typePrinter = new TypePrinter();

		portName.setText(port.getName());
		portType.setText(typePrinter.doSwitch(port.getType()));
	}

	@Override
	protected String checkValueValid(Widget widget) {

		if (widget == portName) {
			final NetworkPortPattern pattern = getPattern(
					getSelectedPictogramElement(), NetworkPortPattern.class);
			if (pattern != null) {
				return pattern.checkValueValid(portName.getText(), null);
			}
		} else if (widget == portType) {
			final Type type = calParser.parseType(portType.getText());
			if (type == null) {
				return "Unable to parse this type";
			}
		}

		return null;
	}

	@Override
	protected void writeValuesToModel(final Widget widget) {
		final Port port = (Port) getSelectedBusinessObject();

		if (widget == portName) {
			port.setName(portName.getText());
		} else if (widget == portType) {
			final Type type = calParser.parseType(portType.getText());
			if (type != null) {
				port.setType(type);
			}
		}
	}
}
