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
package net.sf.orcc.ui.launching.tabs;

import static net.sf.orcc.OrccLaunchConstants.ACTIVATE_BACKEND;
import static net.sf.orcc.OrccLaunchConstants.BACKEND;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_BACKEND;
import static net.sf.orcc.OrccLaunchConstants.OUTPUT_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.SIMULATOR;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccActivator;
import net.sf.orcc.ui.launching.OptionWidget;
import net.sf.orcc.ui.launching.impl.OptionWidgetManager;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * This class groups fields that are common to {@link RunSettingsTab} and
 * {@link SimuSettingsTab}.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public abstract class OrccAbstractSettingsTab extends
		AbstractLaunchConfigurationTab implements ModifyListener {

	protected Combo comboPlugin;

	private Composite composite;

	protected Group groupOptions;

	protected Map<String, List<OptionWidget>> optionWidgets;

	protected String plugin;

	private Text textProject;

	protected boolean updateLaunchConfiguration;

	/**
	 * Method called when the "Browse..." button is clicked. Shows a selection
	 * dialog with projects available in the workspace.
	 * 
	 * @param shell
	 *            a shell
	 */
	private void browseProject(Shell shell) {
		ElementTreeSelectionDialog tree = new ElementTreeSelectionDialog(shell,
				WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		tree.setAllowMultiple(false);
		tree.setInput(ResourcesPlugin.getWorkspace().getRoot());

		IProject project = getProjectFromText();
		if (project != null) {
			tree.setInitialSelection(project);
		}

		tree.setMessage("Please select an existing project:");
		tree.setTitle("Choose an existing project");

		tree.setValidator(new ISelectionStatusValidator() {

			@Override
			public IStatus validate(Object[] selection) {
				if (selection.length == 1) {
					if (selection[0] instanceof IProject) {
						return new Status(IStatus.OK, OrccActivator.PLUGIN_ID,
								"");
					} else {
						return new Status(IStatus.ERROR,
								OrccActivator.PLUGIN_ID,
								"Only projects can be selected");
					}
				}

				return new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
						"No project selected.");
			}

		});

		// opens the dialog
		if (tree.open() == Window.OK) {
			project = (IProject) tree.getFirstResult();
			textProject.setText(project.getName());
		}
	}

	@Override
	public void createControl(Composite parent) {
		ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL
				| SWT.V_SCROLL);
		setControl(sc);

		composite = new Composite(sc, SWT.NONE);
		sc.setContent(composite);

		Font font = parent.getFont();
		composite.setFont(font);

		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(data);

		createControlProject(composite);
		createControlPlugin(composite);
		createControlOption(composite);
		createOptions();
	}

	/**
	 * Creates the control for Browse project.
	 * 
	 * @param group
	 *            parent group
	 */
	private void createControlBrowseProject(final Group group) {
		textProject = new Text(group, SWT.BORDER | SWT.SINGLE);
		textProject.setFont(composite.getFont());
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textProject.setLayoutData(data);
		textProject.addModifyListener(this);

		Button buttonBrowse = new Button(group, SWT.PUSH);
		buttonBrowse.setFont(composite.getFont());
		data = new GridData(SWT.FILL, SWT.CENTER, false, false);
		buttonBrowse.setLayoutData(data);
		buttonBrowse.setText("&Browse...");
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseProject(group.getShell());
			}
		});
	}

	/**
	 * Creates the control for options.
	 * 
	 * @param parent
	 *            parent composite
	 */
	protected void createControlOption(Composite parent) {
		groupOptions = new Group(parent, SWT.NONE);
		groupOptions.setFont(getFont());
		groupOptions.setText("&Options:");

		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		groupOptions.setLayout(layout);

		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		groupOptions.setLayoutData(data);
	}

	/**
	 * Creates control for the selected plug-in: back-end or simulator.
	 * 
	 * @param parent
	 *            parent composite
	 */
	abstract protected void createControlPlugin(Composite parent);

	/**
	 * Creates the control for the project selection.
	 * 
	 * @param parent
	 *            parent composite
	 */
	private void createControlProject(Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(getFont());
		group.setText("&Project:");
		group.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		group.setLayoutData(data);

		createControlBrowseProject(group);
	}

	/**
	 * Creates the controls for all options, but do not show them yet.
	 */
	abstract protected void createOptions();

	@Override
	public void dispose() {
		optionWidgets.clear();
		if (groupOptions != null) {
			groupOptions.dispose();
		}
	}

	/**
	 * Returns the font of this tab.
	 * 
	 * @return the font of this tab
	 */
	final protected Font getFont() {
		return composite.getFont();
	}

	/**
	 * Get the current plugin type (BACKEND in compilation mode, SIMULATOR in
	 * simulation mode)
	 * 
	 * @return Correct OrccLaunchConstant plugin type identifier according to
	 *         current mode (compilation or simulation)
	 */
	public String getPluginType() {
		// By default, plugin is a backend
		return BACKEND;
	}

	public IProject getProjectFromText() {
		String projectName = textProject.getText();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (root.getFullPath().isValidSegment(projectName)) {
			return root.getProject(projectName);
		}
		return null;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			// hide options of the previously-selected back-end (if any)
			if (plugin != null && !plugin.isEmpty()) {
				List<OptionWidget> widgets = optionWidgets.get(plugin);
				OptionWidgetManager.hideOptions(widgets);
			}

			plugin = configuration.getAttribute(getPluginType(), "");

			int index = comboPlugin.indexOf(plugin);
			if (index == -1) {
				comboPlugin.deselectAll();
			} else {
				comboPlugin.select(index);
			}

			updateLaunchConfiguration = false;
			String value = configuration.getAttribute(PROJECT, "");
			textProject.setText(value);
			updateLaunchConfiguration = true;

			// initialize from all options
			for (Entry<String, List<OptionWidget>> entry : optionWidgets
					.entrySet()) {
				List<OptionWidget> widgets = entry.getValue();
				OptionWidgetManager.initializeFromOptions(widgets,
						configuration);
			}

			// show options of the newly-selected back-end or simulator plugin
			if (plugin != null && !plugin.isEmpty()) {
				List<OptionWidget> widgets = optionWidgets.get(plugin);
				OptionWidgetManager.showOptions(widgets);
			}

			updateSize();
		} catch (CoreException e) {
			e.printStackTrace();
			updateLaunchConfiguration = true;
		}
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		String projectName = textProject.getText();
		if (projectName.isEmpty()) {
			setErrorMessage("Project not specified");
			return false;
		}

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (!root.getFullPath().isValidSegment(projectName)) {
			setErrorMessage("Project name is not valid");
			return false;
		}

		IProject project = getProjectFromText();
		if (project == null || !project.exists()) {
			setErrorMessage("Specified project does not exist");
			return false;
		}

		List<OptionWidget> widgets = optionWidgets.get(plugin);
		if (OptionWidgetManager.isValidOptions(widgets, launchConfig)) {
			setErrorMessage(null);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void modifyText(ModifyEvent e) {
		if (updateLaunchConfiguration) {
			updateLaunchConfigurationDialog();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String value = textProject.getText();
		configuration.setAttribute(PROJECT, value);

		int index = comboPlugin.getSelectionIndex();
		if (index != -1) {
			value = comboPlugin.getItem(index);
			// It may be either a backend or a simulator
			configuration.setAttribute(BACKEND, value);
			configuration.setAttribute(SIMULATOR, value);
		}

		List<OptionWidget> widgets = optionWidgets.get(plugin);
		if (widgets != null) {
			OptionWidgetManager.performApplyOptions(widgets, configuration);
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(OUTPUT_FOLDER, "");
		configuration.setAttribute(BACKEND, "");
		configuration.setAttribute(SIMULATOR, "");
		configuration.setAttribute(ACTIVATE_BACKEND, DEFAULT_BACKEND);
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		super.setErrorMessage(errorMessage);
	}

	@Override
	public void updateLaunchConfigurationDialog() {
		super.updateLaunchConfigurationDialog();
	}

	/**
	 * Updates the selection of options and the size of the composite to show
	 * scrollbars if necessary.
	 */
	final protected void updateOptionSelection() {
		if (!plugin.isEmpty()) {
			List<OptionWidget> widgets = optionWidgets.get(plugin);
			OptionWidgetManager.hideOptions(widgets);
		}

		int index = comboPlugin.getSelectionIndex();
		if (index != -1) {
			plugin = comboPlugin.getItem(index);
			List<OptionWidget> widgets = optionWidgets.get(plugin);
			OptionWidgetManager.showOptions(widgets);
		}

		updateSize();
	}

	/**
	 * Updates the size of the composite. Will make scrollbars appear if
	 * necessary.
	 */
	public void updateSize() {
		groupOptions
				.setSize(groupOptions.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

}
