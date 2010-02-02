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

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Group;

/**
 *  Abstract implementation of backend's option.
 *
 * @author Jérôme Gorin
 * 
 */
public interface AbstractOption {
	
	/**
	 * Dispose option elements
	 */
	public void dispose();
	
	/**
	 * Returns the option name
	 *
	 * @return a String containing the option name
	 */
	public String[] getOption();
	
	/**
	 * Returns the value of the option
	 *
	 * @return a String containing the value
	 */
	public String[] getValue();
	
	/**
	 * Tests if the option is valid
	 *
	 * @return a boolean representing the validation of the option
	 */
	public boolean isValid();
	
	/**
	 * Apply option to the specificied ILaunchConfigurationWorkingCopy
	 * 	 * @param configuration
	 *            ILaunchConfigurationWorkingCopy of configuration tab
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration);
	
	/**
	 * Set the option name
	 *
	 * @param option
	 *		String containing the option name
	 */
	public void setOption(String option);
	
	/**
	 * Set the value of the option
	 *
	 * @param value
	 *		String containing the value
	 */
	public void setValue(String value);
	
	/**
	 * Show interfaces on the selected group
	 * 
	 * @param font
	 *       Font used in the interface
	 * @param group
	 *       Group to add the input file interface
	 */
	public void show(Font font, Group group);

}
