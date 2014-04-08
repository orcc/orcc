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

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Network;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.ir.util.TypePrinter;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class NetworkVariablesSection extends AbstractTableBasedSection {

	/**
	 * Define the dialog used to edit a network parameter.
	 * 
	 * @author Antoine Lorence
	 * 
	 */
	private class VariableItemEditor extends ItemEditor {

		private Text type;
		private Text name;
		private Text value;

		protected VariableItemEditor(final TableItem item) {
			super(item);
		}

		@Override
		protected String getDialogTitle() {
			return "Edit variable";
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			final Composite container = (Composite) super.createDialogArea(parent);
			final GridData fill = new GridData(SWT.FILL, SWT.FILL, true, true);

			final Label typeLabel = new Label(container, SWT.NONE);
			typeLabel.setText("Type:");

			type = new Text(container, SWT.NONE);
			type.setLayoutData(fill);

			final Label nameLabel = new Label(container, SWT.NONE);
			nameLabel.setText("Name:");

			name = new Text(container, SWT.NONE);
			name.setLayoutData(fill);

			final Label valueLabel = new Label(container, SWT.NONE);
			valueLabel.setText("Value:");

			value = new Text(container, SWT.NONE);
			value.setLayoutData(fill);

			if (getItem() != null) {
				type.setText(getItem().getText(0));
				name.setText(getItem().getText(1));
				value.setText(getItem().getText(2));
			}

			return container;
		}

		@Override
		protected void okPressed() {
			getItem().setText(new String[] { type.getText(), name.getText(), value.getText() });
			super.okPressed();
		}
	}

	@Override
	protected String getFormText() {
		return "Network Variables";
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		final TableColumn typeColumn = new TableColumn(table, SWT.NONE);
		typeColumn.setText("Type");
		typeColumn.setWidth(80);

		final TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setText("Name");
		nameColumn.setWidth(100);

		final TableColumn valueColumn = new TableColumn(table, SWT.NONE);
		valueColumn.setText("Value");
	}

	@Override
	void editTableItem(TableItem item) {
		final VariableItemEditor editor = new VariableItemEditor(item);
		editor.open();
	}

	@Override
	protected void readValuesFromModels() {
		final Network network = (Network) getSelectedBusinessObject();
		final TypePrinter typePrinter = new TypePrinter();
		final ExpressionPrinter exprPrinter = new ExpressionPrinter();

		table.removeAll();

		for (final Var var : network.getVariables()) {
			final String[] itemValues = { typePrinter.doSwitch(var.getType()), var.getName(),
					exprPrinter.doSwitch(var.getInitialValue()) };
			final TableItem item = new TableItem(table, SWT.NONE);
			item.setText(itemValues);
		}
	}

	@Override
	protected void writeValuesToModel(final Widget widget) {
		final Network network = (Network) getSelectedBusinessObject();

		// Declare parameters which can be referenced by this variable
		calParser.setDeclaredVars(network.getParameters());

		network.getVariables().clear();

		for (final TableItem item : table.getItems()) {
			final String typeText = item.getText(0);
			final String nameText = item.getText(1);
			final String valueText = item.getText(2);

			try {
				final Var var = calParser.parseVariableDeclaration(typeText + " " + nameText + " = " + valueText);
				if (var != null) {
					network.getVariables().add(var);
					calParser.addDeclaredVar(var);
				} else {
					MessageDialog.openError(XdfUtil.getDefaultShell(), "Syntax error",
							"Values you entered could not be parsed");
				}
			} catch (OrccRuntimeException e) {
				MessageDialog.openError(XdfUtil.getDefaultShell(), "Error", e.getMessage());
			}
		}
	}
}
