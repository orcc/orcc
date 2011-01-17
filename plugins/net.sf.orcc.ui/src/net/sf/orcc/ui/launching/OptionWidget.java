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
package net.sf.orcc.ui.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

/**
 * This interface defines a widget that shows an option.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface OptionWidget {

	/**
	 * Hides this option widget (and this children, if any).
	 */
	void hide();

	/**
	 * Initializes this option widget from the given configuration.
	 * 
	 * @param configuration
	 *            a launch configuration
	 * @throws CoreException
	 */
	void initializeFrom(ILaunchConfiguration configuration)
			throws CoreException;

	/**
	 * Returns true if this option widget is valid in the context of the given
	 * configuration.
	 * 
	 * @param launchConfig
	 *            a launch configuration
	 */
	boolean isValid(ILaunchConfiguration launchConfig);

	/**
	 * Copies values from this option widget into the given working copy of a
	 * launch configuration.
	 * 
	 * @param configuration
	 *            a working copy of a launch configuration
	 */
	void performApply(ILaunchConfigurationWorkingCopy configuration);

	/**
	 * Shows this option widget (and this children, if any).
	 */
	void show();

}
