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
package net.sf.orcc.backends.options;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.BackendFactory;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;


/**
 *
 *  Class that add a check box input into backend options.
 *   
 * @author Jérôme Gorin
 * 
 */
public class CheckBoxOption implements AbstractOption, SelectionListener  {
	
	
	AbstractOption[] abstractOptions;
	
	/**
	 * Text connected with the interface
	 */
	private String caption;
	
	
	/**
	 * Text connected with the option
	 */
	private Button checkBox;
	
	/**
	 * Font connected with the option
	 */
	private Font font;
	
	/**
	 * group connected with the option
	 */
	private Group group;
	
	/**
	 * Label connected with the option
	 */
	private Label lbl;
	
	/**
	 * Name of the option
	 */
	private String option;
	
	/**
	 * Value of the option
	 */
	private boolean value;
	
	
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
	public CheckBoxOption(String option, String caption, String defaultVal, IConfigurationElement[] childrens){
		this.option = option;
		this.caption = caption;
		this.value = Boolean.getBoolean(defaultVal);
		
		for (IConfigurationElement children : childrens){
			String elementName = children.getName();
			
			if (elementName.equals("option")){
				IConfigurationElement[] configurationElements = children.getChildren();
				
				abstractOptions = BackendFactory.parseConfigurationElement(configurationElements);
			}
		}
	}


	/**
	 * Creates the interface of the BrowseFile text into the given group
	 * 
	 * @param font
	 *       Font used in the interface
	 * @param group
	 *       Group to add the input file interface
	 */
	private void createCheckBox(){	
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		data.horizontalSpan = 3;
		group.setLayoutData(data);
		
		lbl = new Label(group, SWT.NONE);
		lbl.setFont(font);
		lbl.setText(caption);
		
		data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		lbl.setLayoutData(data);
	
		checkBox = new Button(group, SWT.CHECK);
		checkBox.setFont(font);
		checkBox.setSelection(value);
		checkBox.addSelectionListener(this);
		
		data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		lbl.setLayoutData(data);
		lbl = new Label(group, SWT.NONE);
	}
	
	/**
	 * Dispose option elements
	 */
	@Override
	public void dispose() {
		if (checkBox != null){
			checkBox.dispose();
			lbl.dispose();
		}
	}
	
	/**
	 * Returns the option name
	 *
	 * @return a String containing the option name
	 */
	public String[] getOption() {
		List<String> options = new ArrayList<String>();
		options.add(option);
		
		if (value){			
			//Add suboptions to the option list
			for (AbstractOption abstractOption : abstractOptions){
				String[] subOptions = abstractOption.getOption();
				for (String subOption : subOptions){
					options.add(subOption);
				}
			}
		}
		
		return (String[]) options.toArray(new String[] {});
	}
	
	/**
	 * Returns the value of the option
	 *
	 * @return a String containing the value
	 */
	public String[] getValue() {
		List<String> values = new ArrayList<String>();
		values.add(Boolean.toString(value));
		if (value){		
			//Add suboptions value to the value list
			for (AbstractOption abstractOption : abstractOptions){
				String[] subOptions = abstractOption.getValue();
				for (String subOption : subOptions){
					values.add(subOption);
				}
			}
		}
		
		return (String[]) values.toArray(new String[] {});
	}
	

	/**
	 * Tests if the option is valid
	 *
	 * @return a boolean representing the validation of the option
	 */
	public boolean isValid(){
		 return true;
	}


	/**
	 * Apply option to the specificied ILaunchConfigurationWorkingCopy
	 * 	 * @param configuration
	 *            ILaunchConfigurationWorkingCopy of configuration tab
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		
	}
	
	@Override
	public void setOption(String option) {
		this.option = option;
	}


	@Override
	public void setValue(String value) {
		this.value = Boolean.getBoolean(value);
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
		this.font = font;
		this.group = group;

		createCheckBox();
		updateChildrens();
	}


	/**
	 * Update others options that depend on the checkbox value
	 * 
	 */
	private void updateChildrens() {
		if (abstractOptions != null){
			if (value){
				for (AbstractOption abstractOption: abstractOptions){
					abstractOption.show(font, group);
				}
			}else {
				for (AbstractOption abstractOption: abstractOptions){
					abstractOption.dispose();
				}
			}
			group.redraw();
		}
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		checkBox.setSelection(value);
		updateChildrens();
		
	}


	@Override
	public void widgetSelected(SelectionEvent arg0) {
		value = checkBox.getSelection();
		updateChildrens();
	}

}
