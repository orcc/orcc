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

import net.sf.orcc.df.Instance;
import net.sf.orcc.xdf.ui.patterns.InstancePattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Displayed in Main tab when an instance is selected.
 * 
 * @author Antoine Lorence
 * 
 */
public class InstanceMainSection extends AbstractGridBasedSection {

	private Text instanceName;
	private Text refinementValue;
	private Text part_name;

	@Override
	protected String getFormText() {
		return "Instance Properties";
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		widgetFactory.createCLabel(formBody, "Name:");
		instanceName = widgetFactory.createText(formBody, "", SWT.BORDER);
		instanceName.setLayoutData(fillHorizontalData);

		widgetFactory.createCLabel(formBody, "Refinement:");
		refinementValue = widgetFactory.createText(formBody, "", SWT.BORDER);
		refinementValue.setEditable(false);
		refinementValue.setBackground(disabledFieldBGColor);
		refinementValue.setLayoutData(fillHorizontalData);

		widgetFactory.createCLabel(formBody, "Part. name:");
		part_name = widgetFactory.createText(formBody, "", SWT.BORDER);
		part_name.setLayoutData(fillHorizontalData);
		part_name.setEditable(false);
		part_name.setBackground(disabledFieldBGColor);
	}

	@Override
	protected void readValuesFromModels() {
		final Instance instance = (Instance) getSelectedBusinessObject();
		instanceName.setText(instance.getName());

		if (instance.getEntity() != null) {
			// Refresh sometimes happen while the IR file is rebuilding. In
			// these cases, we do nothing. The next refresh will update this
			// field (maybe :)
			if (instance.getEntity().eResource() != null) {
				refinementValue.setText(instance.getEntity().eResource()
						.getURI().toString());
			}
		} else {
			refinementValue.setText("");
		}

		// TODO: get the "part name" value in the instance, and display it
	}

	@Override
	protected String checkValueValid(Widget widget) {

		if (widget == instanceName) {
			final InstancePattern pattern = getPattern(
					getSelectedPictogramElement(), InstancePattern.class);
			if (pattern != null) {
				return pattern.checkValueValid(instanceName.getText(),
						(Instance) getSelectedBusinessObject());
			}
		}

		return null;
	}

	@Override
	protected void writeValuesToModel(final Widget widget) {
		final Instance instance = (Instance) getSelectedBusinessObject();

		if (widget == instanceName) {
			instance.setName(instanceName.getText());
		} else if (widget == refinementValue) {

		} else if (widget == part_name) {
			// TODO: write the "part name" in the model
		}
	}
}
