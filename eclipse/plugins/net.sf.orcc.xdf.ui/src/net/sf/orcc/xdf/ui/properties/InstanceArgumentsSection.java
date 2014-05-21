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

import java.util.Collections;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
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

public class InstanceArgumentsSection extends AbstractTableBasedSection {

	/**
	 * Define the dialog used to edit an instance argument.
	 * 
	 * @author Antoine Lorence
	 * 
	 */
	private class ArgumentItemEditor extends ItemEditor {

		private Text name;
		private Text value;

		protected ArgumentItemEditor(final TableItem item) {
			super(item);
		}

		@Override
		protected String getDialogTitle() {
			return "Edit argument";
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			final Composite container = (Composite) super.createDialogArea(parent);
			final GridData fill = new GridData(SWT.FILL, SWT.FILL, true, true);

			final Label nameLabel = new Label(container, SWT.NONE);
			nameLabel.setText("Name:");

			name = new Text(container, SWT.NONE);
			name.setLayoutData(fill);

			final Label valueLabel = new Label(container, SWT.NONE);
			valueLabel.setText("Value:");

			value = new Text(container, SWT.NONE);
			value.setLayoutData(fill);

			if (getItem() != null) {
				name.setText(getItem().getText(0));
				value.setText(getItem().getText(1));
			}

			return container;
		}

		@Override
		protected void okPressed() {
			getItem().setText(new String[] { name.getText(), value.getText() });
			super.okPressed();
		}
	}

	@Override
	protected String getFormText() {
		return "Instance Arguments";
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		final TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setText("Argument name");
		nameColumn.setWidth(100);

		final TableColumn valueColumn = new TableColumn(table, SWT.NONE);
		valueColumn.setText("Value");
	}

	@Override
	boolean editTableItem(TableItem item) {
		final ArgumentItemEditor editor = new ArgumentItemEditor(item);
		return editor.open() == Window.OK;
	}

	@Override
	protected void readValuesFromModels() {
		final Instance instance = (Instance) getSelectedBusinessObject();
		final ExpressionPrinter exprPrinter = new ExpressionPrinter();

		table.removeAll();

		for (final Argument arg : instance.getArguments()) {
			final String[] itemValues = { arg.getVariable().getName(), exprPrinter.doSwitch(arg.getValue()) };
			final TableItem item = new TableItem(table, SWT.NONE);
			item.setText(itemValues);
		}
	}

	@Override
	protected void writeValuesToModel(final Widget widget) {
		final Instance instance = (Instance) getSelectedBusinessObject();

		// Only parameters of this instance's refinement (Actor or Network) can
		// be used as argument name
		final EObject refinement = instance.getEntity();
		List<Var> declaredVars = Collections.emptyList();
		if (refinement instanceof Network) {
			declaredVars = ((Network) refinement).getParameters();
		} else if (refinement instanceof Actor) {
			declaredVars = ((Actor) refinement).getParameters();
		}

		// Variables and parameters declared in the current network can be used
		// as value for an instance argument
		calParser.addDeclaredVars(getCurrentNetwork().getParameters());
		calParser.addDeclaredVars(getCurrentNetwork().getVariables());

		instance.getArguments().clear();

		for (final TableItem item : table.getItems()) {
			final String paramNameText = item.getText(0);
			final String valueText = item.getText(1);

			try {
				Var variable = null;
				for (final Var declaredVar : declaredVars) {
					if (declaredVar.getName().equals(paramNameText)) {
						variable = declaredVar;
					}
				}
				if (variable == null) {
					throw new OrccRuntimeException("Variable " + paramNameText
							+ " has not been found in this instance's refinement.");
				}
				final Expression exprValue = calParser.parseExpression(valueText);
				final Argument arg = DfFactory.eINSTANCE.createArgument(variable, exprValue);

				instance.getArguments().add(arg);
			} catch (OrccRuntimeException e) {
				MessageDialog.openError(XdfUtil.getDefaultShell(), "Error", e.getMessage());
			}
		}
	}
}
