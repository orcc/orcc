/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.ui.preferences;

import static net.sf.orcc.preferences.PreferenceConstants.P_JADE;
import static net.sf.orcc.preferences.PreferenceConstants.P_SOLVER;
import static net.sf.orcc.preferences.PreferenceConstants.P_SOLVER_OPTIONS;
import static net.sf.orcc.preferences.PreferenceConstants.P_SOLVER_TYPE;
import net.sf.orcc.OrccActivator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog.
 * 
 * @author Matthieu Wipliez
 */
public class OrccPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private class SolverSelectionListener implements SelectionListener {

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Widget widget = e.widget;
			Button button = (Button) widget;
			String text = button.getText();

			updateMode = false;
			if (text.contains("Z3")) {
				if (Platform.OS_WIN32.equals(Platform.getOS())) {
					textControl.setText("/smt2");
				} else {
					textControl.setText("-smt2");
				}
			}
			updateMode = true;
		}

	};

	private Composite radioComposite;

	private Text textControl;

	private boolean updateMode;

	public OrccPreferencePage() {
		super(GRID);

		IPreferenceStore store = new ScopedPreferenceStore(
				InstanceScope.INSTANCE, OrccActivator.PLUGIN_ID);
		setPreferenceStore(store);
		setDescription("General settings for Orcc");
	}

	@Override
	public void adjustGridLayout() {

	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		parent.setLayout(new GridLayout(1, false));

		createJadeFieldEditors(parent);
		createSolverFieldEditors(parent);
	}

	/**
	 * Creates field editors for the Jade preferences.
	 * 
	 * @param parent
	 *            parent composite
	 */
	private void createJadeFieldEditors(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setFont(getFont());
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setText("Jade");

		addField(new FileFieldEditor(P_JADE, "Path of Jade executable:", group));
	}

	/**
	 * Creates a new solver selection listener, and add it to all children of
	 * the radioComposite field. Also add a modify listener to the text control
	 * field.
	 */
	private void createListeners() {
		SelectionListener sel = new SolverSelectionListener();

		final Control[] children = radioComposite.getChildren();
		for (Control child : children) {
			if (child instanceof Button) {
				Button button = (Button) child;
				button.addSelectionListener(sel);
			}
		}

		updateMode = true;
		textControl.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				if (updateMode) {
					for (int i = 0; i < children.length - 1; i++) {
						((Button) children[i]).setSelection(false);
					}
					((Button) children[children.length - 1]).setSelection(true);
				}
			}

		});
	}

	/**
	 * Creates field editors for the solver preferences.
	 * 
	 * @param parent
	 *            parent composite
	 */
	private void createSolverFieldEditors(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setFont(getFont());
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setText("SMT solver");

		addField(new FileFieldEditor(P_SOLVER, "Path of solver executable:",
				group));

		// composite for field radio
		Composite composite = new Composite(group, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, false, false);
		data.horizontalSpan = 2;
		composite.setLayoutData(data);

		// field options
		StringFieldEditor fieldOptions = new StringFieldEditor(
				P_SOLVER_OPTIONS, "Options of SMT solver:", composite);
		textControl = fieldOptions.getTextControl(composite);
		addField(fieldOptions);

		// composite for field solver type
		composite = new Composite(group, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		data = new GridData(SWT.FILL, SWT.TOP, true, false);
		data.horizontalSpan = 3;
		composite.setLayoutData(data);

		// radio buttons
		RadioGroupFieldEditor fieldRadio = new RadioGroupFieldEditor(
				P_SOLVER_TYPE, "Set default options for solver:", 2,
				new String[][] { { "Z3 v4.12+ (recommended)", "Z3" },
						{ "Custom", "Custom" }, }, composite);
		radioComposite = fieldRadio.getRadioBoxControl(composite);
		addField(fieldRadio);

		createListeners();
	}

	@Override
	public void init(IWorkbench workbench) {
	}

}
