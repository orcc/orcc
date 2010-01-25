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
package net.sf.orcc.backends.options;

import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_FILE;
import net.sf.orcc.ui.OrccActivator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;


/**
 *
 *  Class that create input file interface into backend options.
 *   
 * @author Jérôme Gorin
 * 
 */
public class InputFileOption implements AbtractOption {
	
	/**
	 * list of required attributes
	 */
	private String caption;
	private String extension;
	private String option;
	private boolean required;
	private Text text;
	private String value;

	/**
	 * BrowseFileOption constructor 
	 * 
	 * @param option
	 *       Name of the option
	 * @param caption
	 *       Caption associated to input file interface
	 * @param required
	 *       Indicate if this information is mandatory 
	 * @param defaultVal
	 *       Default value text of the Text
	 * @param extension
	 *       File extension for restricting selection
	 */
	public InputFileOption(String caption, String defaultVal, String extension){
		this.option = new String ("INPUT_FILE");
		this.caption = caption;
		this.required = true;
		this.value = defaultVal;
		this.extension = extension;
	}


	/**
	 * Creates the interface of the BrowseFile text into the given group
	 * 
	 * @param font
	 *       Font used in the interface
	 * @param group
	 *       Group to add the input file interface
	 */
	private void createInputFile(Font font, final Group group){

		Label lbl = new Label(group, SWT.NONE);
		lbl.setFont(font);
		lbl.setText(caption);
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		lbl.setLayoutData(data);
	
		text = new Text(group, SWT.BORDER | SWT.SINGLE);
		text.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		text.setLayoutData(data);
		text.setText(value);
		text.addModifyListener(this);
	
		Button buttonBrowse = new Button(group, SWT.PUSH);
		buttonBrowse.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, false, false);
		buttonBrowse.setLayoutData(data);
		buttonBrowse.setText("&Browse...");
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				inputFile(group.getShell(), text);
			}
		});

	}

	/**
	 * Returns an IFile instance of the focused file in text
	 *
	 * @param text
	 * 		Text containing the file 
	 * 
	 * @return an IFile instance of focused file 
	 */
	private IFile getFileFromText(Text text) {
		String value = text.getText();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IFile file = root.getFileForLocation(new Path(value));

		return file;
	}
	
	/**
	 * Returns the option name
	 *
	 * @return a String containing the option name
	 */
	public String getOption() {
		return option;
	}
	
	/**
	 * Returns the value of the option
	 *
	 * @return a String containing the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Creates the interface of the Input File button
	 * 
	 * @param shell
	 *       Instance of the windows manager
	 * @param text
	 *       Text of input file interface
	 */
	private void inputFile(Shell shell, Text text) {
		ElementTreeSelectionDialog tree = new ElementTreeSelectionDialog(shell,
				WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		tree.setAllowMultiple(false);
		tree.setInput(ResourcesPlugin.getWorkspace().getRoot());

		IFile file = getFileFromText(text);
		if (file != null) {
			tree.setInitialSelection(file);
		}

		tree.setMessage("Please select an existing file:");
		tree.setTitle("Choose an existing file");

		tree.setValidator(new ISelectionStatusValidator() {

			@Override
			public IStatus validate(Object[] selection) {
				if (selection.length == 1) {
					if (selection[0] instanceof IFile) {
						IFile file = (IFile) selection[0];
						if (extension!= null){
							if (file.getFileExtension().equals(extension)) {
								return new Status(IStatus.OK,
										OrccActivator.PLUGIN_ID, "");
							} else {
								return new Status(IStatus.ERROR,
										OrccActivator.PLUGIN_ID,
										"Selected file must be an "+extension+" file.");
							}
						} else {
							return new Status(IStatus.OK,
									OrccActivator.PLUGIN_ID, "");
						}
					}
				}

				return new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
						"Only files can be selected, not folders nor projects");
			}

		});

		// opens the dialog
		if (tree.open() == Window.OK) {
			file = (IFile) tree.getFirstResult();
			text.setText(file.getLocation().toOSString());
		}
	}
	
	/**
	 * Tests if the option is valid
	 *
	 * @return a boolean representing the validation of the option
	 */
	public boolean isValid(){
		if (required){
			return !value.equals("");
		}
		
		 return true;
	}
	

	/**
	 * Modify listener on events of text
	 *
	 * @param e
	 *       a ModifyEvent containing event from the text
	 *
	 */
	@Override
	public void modifyText(ModifyEvent e) {
		IFile file = getFileFromText(text);
		if (!file.toString().equals("")){
			value = text.getText();
		}
	}


	/**
	 * Apply option to the specificied ILaunchConfigurationWorkingCopy
	 * 	 * @param configuration
	 *            ILaunchConfigurationWorkingCopy of configuration tab
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(INPUT_FILE, value);
	}


	/**
	 * Show the interface on the selected group
	 * 
	 * @param font
	 *       Font used in the interface
	 * @param group
	 *       Group to add the input file interface
	 */
	@Override
	public void show(Font font, Group group) {
		createInputFile(font, group);
	}

}
