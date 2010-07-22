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
package net.sf.orcc.ui.properties;

import static net.sf.orcc.OrccProperties.DEFAULT_OUTPUT;
import static net.sf.orcc.OrccProperties.PROPERTY_OUTPUT;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * This class defines a property page for an Orcc project.
 * 
 * @author Matthieu Wipliez
 * @author Jérôme Gorin
 * 
 */
public class ProjectPropertyPage extends PropertyPage {

	private Button isReadable;

	private IProject project;

	private Text textOutput;

	/**
	 * Creates a new project property page.
	 */
	public ProjectPropertyPage() {
		super();
	}

	private void browseOutputFolder(Shell shell) {
		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.NONE);
		dialog.setMessage("Select output folder:");
		if (getFolderFromText()) {
			// set initial directory if it is valid
			dialog.setFilterPath(textOutput.getText());
		}

		String dir = dialog.open();
		if (dir != null) {
			textOutput.setText(dir);
		}
	}

	@Override
	protected Control createContents(Composite parent) {
		// top-level group
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		composite.setLayoutData(data);

		initialize();
		createDescriptionLabel(composite);

		Composite container = createTopLevelComposite(composite);

		createControlOutputFolder(container);
		createControlReadableJson(container);

		// apply dialog font to the whole composite
		applyDialogFont(composite);

		return composite;
	}

	private void createControlOutputFolder(final Composite parent) {
		GridData data;

		Label lbl = new Label(parent, SWT.NONE);
		lbl.setText("Output folder:");
		lbl.setToolTipText("The folder where IR files will be generated.");
		data = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		lbl.setLayoutData(data);

		textOutput = new Text(parent, SWT.BORDER | SWT.SINGLE);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textOutput.setLayoutData(data);
		try {
			textOutput.setText(project.getPersistentProperty(PROPERTY_OUTPUT));
		} catch (CoreException e) {
		}

		Button buttonBrowse = new Button(parent, SWT.PUSH);
		data = new GridData(SWT.FILL, SWT.CENTER, false, false);
		buttonBrowse.setLayoutData(data);
		buttonBrowse.setText("&Browse...");
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseOutputFolder(parent.getShell());
			}
		});
	}

	private void createControlReadableJson(Composite parent) {
		GridData data;

		isReadable = new Button(parent, SWT.CHECK);
		isReadable.setText("Pretty-print the IR");
		isReadable.setToolTipText("Generate readable JSON files.");
		data = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		data.horizontalSpan = 3;
		isReadable.setLayoutData(data);
	}

	/**
	 * Creates a new Composite with a 3-column grid layout, with components
	 * aligned towards the top.
	 * 
	 * @param composite
	 *            parent composite
	 * @return a new Composite with a 3-column grid layout
	 */
	private Composite createTopLevelComposite(Composite composite) {
		final Composite container = new Composite(composite, SWT.NONE);

		GridLayout layout = new GridLayout(3, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		container.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		container.setLayoutData(data);

		return container;
	}

	private boolean getFolderFromText() {
		String value = textOutput.getText();
		File file = new File(value);
		if (file.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Initializes a ProjectReferencePage.
	 */
	private void initialize() {
		project = (IProject) getElement().getAdapter(IResource.class);
		noDefaultAndApplyButton();
		setDescription("Configure the properties of the project "
				+ project.getName()
				+ ".\n"
				+ "Please read tooltips for more information on configuration properties.");

		try {
			String outputFolder = project
					.getPersistentProperty(PROPERTY_OUTPUT);
			if (outputFolder == null) {
				project.setPersistentProperty(PROPERTY_OUTPUT, new Path(project
						.getLocation().toOSString()).append(DEFAULT_OUTPUT)
						.toOSString());
			}
		} catch (CoreException e) {
		}
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		// Populate the owner text field with the default value
		textOutput.setText(DEFAULT_OUTPUT);
	}

	@Override
	public boolean performOk() {
		try {
			String oldOutput = project.getPersistentProperty(PROPERTY_OUTPUT);
			String newOutput = textOutput.getText();
			project.setPersistentProperty(PROPERTY_OUTPUT, newOutput);
			if (!newOutput.equals(oldOutput)) {
				// build if new value is different from old value
				project.build(IncrementalProjectBuilder.CLEAN_BUILD, null);
			}
		} catch (CoreException e) {
			return false;
		}

		updateApplyButton();
		return true;
	}

}
