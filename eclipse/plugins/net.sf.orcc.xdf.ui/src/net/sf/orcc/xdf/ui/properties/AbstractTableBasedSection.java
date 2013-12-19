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

import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ColumnLayoutData;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * This is a base class for property sections which need to display a table with
 * 2 or more columns.
 * 
 * @author Antoine Lorence
 * 
 */
abstract public class AbstractTableBasedSection extends AbstractDiagramSection {

	/**
	 * Default dialog used to edit a table row.
	 * 
	 * @author Antoine Lorence
	 * 
	 */
	protected abstract class ItemEditor extends Dialog {

		final private TableItem item;

		/**
		 * Initialize the dialog.
		 * 
		 * @param parentShell
		 *            The parentShell
		 * @param item
		 */
		protected ItemEditor(final TableItem item) {
			super(XdfUtil.getDefaultShell());
			this.item = item;
		}

		// Set a default DridLayout
		@Override
		protected Control createDialogArea(Composite parent) {
			Composite container = (Composite) super.createDialogArea(parent);
			container.setLayout(new GridLayout(2, false));
			return container;
		}

		// Configure window title
		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText(getDialogTitle());
		}

		/**
		 * 
		 * @return The item this dialog is modifying
		 */
		public TableItem getItem() {
			return item;
		}

		/**
		 * 
		 * @return The title of the dialog window
		 */
		abstract protected String getDialogTitle();
	}

	protected Table table;

	protected Button addButton, removeButton, editButton;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		final ColumnLayout colLayout = new ColumnLayout();
		formBody.setLayout(colLayout);

		table = new Table(formBody, SWT.BORDER);
		table.setHeaderVisible(true);
		table.setLayoutData(new ColumnLayoutData(200, 120));

		final Composite buttonColumn = widgetFactory.createComposite(formBody);
		final RowLayout buttonLayout = new RowLayout(SWT.VERTICAL);
		buttonLayout.fill = true;
		buttonColumn.setLayout(buttonLayout);

		addButton = widgetFactory.createButton(buttonColumn, "Add", SWT.NONE);
		removeButton = widgetFactory.createButton(buttonColumn, "Remove", SWT.NONE);
		editButton = widgetFactory.createButton(buttonColumn, "Edit", SWT.NONE);

		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editTableItem(new TableItem(table, SWT.NONE));
				writeValuesInTransaction(table);
			}
		});
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int index = table.getSelectionIndex();
				if (index != -1) {
					table.remove(index);
					writeValuesInTransaction(table);
				}
			}
		});
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final TableItem[] selection = table.getSelection();
				if (selection.length == 0) {
					return;
				}
				editTableItem(selection[0]);
				writeValuesInTransaction(table);
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				final TableItem[] selection = table.getSelection();
				if (selection.length == 0) {
					return;
				}
				editTableItem(selection[0]);
				writeValuesInTransaction(table);
			}
		});
	}

	@Override
	public void refresh() {
		super.refresh();

		for (final TableColumn column : table.getColumns()) {
			column.pack();
		}
	}

	/**
	 * Opens a dialog which allow user to edit the fields
	 * 
	 * @param item
	 */
	abstract void editTableItem(final TableItem item);
}
