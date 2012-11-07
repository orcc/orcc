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
package net.sf.orcc.graphiti.ui.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.sf.orcc.graphiti.model.AbstractObject;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * This class defines an abstract section.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractSection extends AbstractPropertySection implements
		PropertyChangeListener {

	/**
	 * This class provides a command that changes the value of the currently
	 * selected parameter.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	protected class ParameterChangeValueCommand extends Command {

		final private String label;

		/**
		 * Set by {@link #setValue(String, Object)}.
		 */
		private String name;

		/**
		 * The new value.
		 */
		private Object newValue;

		/**
		 * The old value.
		 */
		private Object oldValue;

		/**
		 * The property bean we're modifying.
		 */
		private AbstractObject source;

		/**
		 * Creates a new add parameter command.
		 * 
		 * @param newValue
		 *            The value.
		 */
		public ParameterChangeValueCommand(AbstractObject source, String label) {
			this.source = source;
			this.label = label;
		}

		@Override
		public void execute() {
			oldValue = source.setValue(name, newValue);
		}

		@Override
		public String getLabel() {
			return label;
		}

		/**
		 * Sets the value of the parameter whose name is given to the given
		 * value.
		 * 
		 * @param name
		 *            The parameter name.
		 * @param value
		 *            Its new value.
		 */
		public void setValue(String name, Object value) {
			this.name = name;
			this.newValue = value;
		}

		@Override
		public void undo() {
			source.setValue(name, oldValue);
		}

	}

	private Button buttonAdd;

	private Button buttonRemove;

	private Form form;

	protected String parameterName;

	private TableViewer tableViewer;

	/**
	 * Called when "Add..." is pressed.
	 */
	abstract protected void buttonAddSelected();

	/**
	 * Called when "Remove" is pressed.
	 */
	abstract protected void buttonRemoveSelected();

	@Override
	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		form = getWidgetFactory().createForm(parent);
		getWidgetFactory().decorateFormHeading(form);

		Composite composite = form.getBody();
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	/**
	 * Creates the table component from the <code>parent</code> composite.
	 * 
	 * @param parent
	 *            The parent composite.
	 * @return The table created.
	 */
	final protected Table createTable(Composite parent) {
		// create table
		int style = SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION
				| SWT.HIDE_SELECTION;

		final Table table = getWidgetFactory().createTable(parent, style);
		tableViewer = new TableViewer(table);

		// create buttons
		buttonAdd = getWidgetFactory().createButton(parent, "Add...", SWT.NONE);
		buttonAdd.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		buttonAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				buttonAddSelected();
			}
		});

		// create buttons
		buttonRemove = getWidgetFactory().createButton(parent, "Remove",
				SWT.NONE);
		buttonRemove
				.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		buttonRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				buttonRemoveSelected();
			}
		});

		return table;
	}

	@Override
	public void dispose() {
		AbstractObject model = getModel();
		if (model != null) {
			model.removePropertyChangeListener(this);
		}

		if (form != null) {
			form.dispose();
		}
	}

	/**
	 * Returns the form of this section.
	 * 
	 * @return the form of this section
	 */
	public Form getForm() {
		return form;
	}

	/**
	 * Returns the model associated with this section. May be <code>null</code>.
	 * 
	 * @return the model associated with this section
	 */
	public AbstractObject getModel() {
		if (tableViewer == null) {
			return null;
		}
		return (AbstractObject) tableViewer.getInput();
	}

	/**
	 * Returns the shell associated with the form of this section.
	 * 
	 * @return the shell associated with the form of this section
	 */
	public Shell getShell() {
		return form.getShell();
	}

	/**
	 * Returns the current selection on the table of this section. May be
	 * <code>null</code>.
	 * 
	 * @return the current selection on the table of this section
	 */
	public IStructuredSelection getTableSelection() {
		ISelection sel = tableViewer.getSelection();
		if (sel instanceof IStructuredSelection) {
			return (IStructuredSelection) sel;
		}
		return null;
	}

	/**
	 * Returns the viewer of the table of this section.
	 * 
	 * @return the viewer of the table of this section
	 */
	public TableViewer getViewer() {
		return tableViewer;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}

	@Override
	public void refresh() {
		tableViewer.refresh();
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);

		// remove property listener on old model
		AbstractObject oldModel = getModel();
		if (oldModel != null) {
			oldModel.removePropertyChangeListener(this);
		}

		if (selection instanceof IStructuredSelection) {
			Object object = ((IStructuredSelection) selection)
					.getFirstElement();
			if (object instanceof EditPart) {
				Object editPartModel = ((EditPart) object).getModel();
				if (editPartModel instanceof AbstractObject) {
					AbstractObject model = (AbstractObject) editPartModel;

					if (model.getParameter(parameterName) == null) {
						tableViewer.getTable().setEnabled(false);
						buttonAdd.setEnabled(false);
						buttonRemove.setEnabled(false);
					} else {
						tableViewer.getTable().setEnabled(true);
						buttonAdd.setEnabled(true);
						buttonRemove.setEnabled(true);

						model.addPropertyChangeListener(this);
						tableViewer.setInput(model);
					}
				}
			}
		}
	}

	/**
	 * Sets the name of the parameter that this section uses.
	 * 
	 * @param parameterName
	 *            name of a parameter
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

}
