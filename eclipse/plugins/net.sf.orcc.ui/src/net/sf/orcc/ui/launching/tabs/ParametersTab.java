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

import static net.sf.orcc.OrccLaunchConstants.PARAMETERS;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.ui.OrccUiActivator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ParametersTab extends AbstractLaunchConfigurationTab {

	private class Variable {

		private final String name;

		private String value;

		public Variable(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	/**
	 * Content provider for the environment table
	 */
	private class VariableContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			Variable[] elements = new Variable[0];
			ILaunchConfiguration config = (ILaunchConfiguration) inputElement;
			Map<String, String> m;
			try {
				m = config.getAttribute(PARAMETERS, Collections.<String, String>emptyMap());
			} catch (CoreException e) {
				return elements;
			}

			if (!m.isEmpty()) {
				elements = new Variable[m.size()];
				String[] varNames = new String[m.size()];
				m.keySet().toArray(varNames);
				for (int i = 0; i < m.size(); i++) {
					elements[i] = new Variable(varNames[i], m.get(varNames[i]));
				}
			}
			return elements;
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if (newInput == null) {
				return;
			}
			if (viewer instanceof TableViewer) {
				TableViewer tableViewer = (TableViewer) viewer;
				if (tableViewer.getTable().isDisposed()) {
					return;
				}
				tableViewer.setComparator(new ViewerComparator() {
					@Override
					public int compare(Viewer iviewer, Object e1, Object e2) {
						if (e1 == null) {
							return -1;
						} else if (e2 == null) {
							return 1;
						} else {
							return ((Variable) e1).getName()
									.compareToIgnoreCase(
											((Variable) e2).getName());
						}
					}
				});
			}
		}
	}

	private class VariableEditingSupport extends EditingSupport {

		private final TextCellEditor editor;

		/**
		 * Creates a new {@link VariableEditingSupport}.
		 */
		public VariableEditingSupport() {
			super(environmentTable);
			editor = new TextCellEditor(environmentTable.getTable());
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected Object getValue(Object element) {
			Variable variable = (Variable) element;
			return variable.getValue();
		}

		@Override
		protected void setValue(Object element, Object value) {
			Variable variable = (Variable) element;
			variable.setValue((String) value);
			environmentTable.update(variable, null);
			updateLaunchConfigurationDialog();
		}

	}

	/**
	 * Label provider for the environment table
	 */
	private class VariableLabelProvider extends CellLabelProvider {

		@Override
		public void update(ViewerCell cell) {
			Variable variable = (Variable) cell.getElement();
			if (cell.getColumnIndex() == 0) {
				cell.setText(variable.getName());
			} else {
				cell.setText(variable.getValue());
			}
		}

	}

	private TableViewer environmentTable;

	@Override
	public void createControl(Composite parent) {
		// Create main composite
		Composite mainComposite = new Composite(parent, SWT.NONE);
		setControl(mainComposite);

		mainComposite.setLayout(new GridLayout());
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		mainComposite.setLayoutData(data);

		createEnvironmentTable(mainComposite);

		Dialog.applyDialogFont(mainComposite);
	}

	/**
	 * Creates and configures the table that displayed the key/value pairs that
	 * comprise the environment.
	 * 
	 * @param parent
	 *            the composite in which the table should be created
	 */
	private void createEnvironmentTable(Composite parent) {
		Font font = parent.getFont();
		// Create label, add it to the parent to align the right side buttons
		// with the top of the table
		Label l = new Label(parent, SWT.NONE);
		l.setFont(parent.getFont());
		l.setText("Parameters:");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = false;
		l.setLayoutData(gd);

		// Create table composite
		Composite tableComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		tableComposite.setLayout(layout);
		tableComposite.setFont(font);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 1;
		tableComposite.setLayoutData(gd);

		// Create table
		environmentTable = new TableViewer(tableComposite, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		Table table = environmentTable.getTable();
		table.setLayout(new GridLayout());
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setFont(font);

		environmentTable.setContentProvider(new VariableContentProvider());
		environmentTable.setColumnProperties(new String[] { "Name", "Value" });

		// Create columns
		final TableColumn tc1 = new TableColumn(table, SWT.NONE, 0);
		tc1.setText("Name");
		final TableColumn tc2 = new TableColumn(table, SWT.NONE, 1);
		tc2.setText("Value");

		TableViewerColumn tvc1 = new TableViewerColumn(environmentTable, tc1);
		tvc1.setLabelProvider(new VariableLabelProvider());

		TableViewerColumn tvc2 = new TableViewerColumn(environmentTable, tc2);
		tvc2.setLabelProvider(new VariableLabelProvider());

		tvc2.setEditingSupport(new VariableEditingSupport());

		final Table tref = table;
		final Composite comp = tableComposite;
		tableComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle area = comp.getClientArea();
				Point size = tref.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				ScrollBar vBar = tref.getVerticalBar();
				int width = area.width - tref.computeTrim(0, 0, 0, 0).width - 2;
				if (size.y > area.height + tref.getHeaderHeight()) {
					Point vBarSize = vBar.getSize();
					width -= vBarSize.x;
				}
				Point oldSize = tref.getSize();
				if (oldSize.x > area.width) {
					tc1.setWidth(width / 2 - 1);
					tc2.setWidth(width - tc1.getWidth());
					tref.setSize(area.width, area.height);
				} else {
					tref.setSize(area.width, area.height);
					tc1.setWidth(width / 2 - 1);
					tc2.setWidth(width - tc1.getWidth());
				}
			}
		});
	}

	@Override
	public Image getImage() {
		return OrccUiActivator.getImage("icons/orcc.png");
	}

	@Override
	public String getName() {
		return "Parameters";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		environmentTable.setInput(configuration);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// Convert the table's items into a Map so that this can be saved in the
		// configuration's attributes.
		TableItem[] items = environmentTable.getTable().getItems();
		Map<String, String> map = new HashMap<String, String>(items.length);
		for (int i = 0; i < items.length; i++) {
			Variable var = (Variable) items[i].getData();
			map.put(var.getName(), var.getValue());
		}

		if (map.size() == 0) {
			configuration.setAttribute(PARAMETERS, (Map<String, String>) null);
		} else {
			configuration.setAttribute(PARAMETERS, map);
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PARAMETERS, (Map<String, String>) null);
	}
}
