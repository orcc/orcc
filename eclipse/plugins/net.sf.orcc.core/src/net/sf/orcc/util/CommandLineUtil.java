/*
 * Copyright (c) 2014, IETR/INSA of Rennes
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
package net.sf.orcc.util;

import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * Define methods to use in specific cases of IApplication executions, in
 * command-line
 * 
 * @author Antoine Lorence
 * 
 */
public class CommandLineUtil {

	/**
	 * If it is enabled, disable auto-building on the current workspace.
	 * 
	 * @return true if auto-building was enabled, false if it is already
	 *         disabled
	 * @throws CoreException
	 */
	public static boolean disableAutoBuild() throws CoreException {
		// IWorkspace.getDescription() returns a copy. We need to extract,
		// modify and set it to the current workspace.
		final IWorkspaceDescription desc = ResourcesPlugin.getWorkspace()
				.getDescription();
		if (desc.isAutoBuilding()) {
			OrccLogger.debugln("Disbale auto-building");
			desc.setAutoBuilding(false);
			ResourcesPlugin.getWorkspace().setDescription(desc);
			return true;
		}

		return false;
	}

	/**
	 * Enable auto-building on the current workspace
	 * 
	 * @throws CoreException
	 */
	public static void enableAutoBuild() throws CoreException {
		OrccLogger.debugln("Re-enable auto-building");
		// IWorkspace.getDescription() returns a copy. We need to extract,
		// modify and set it to the current workspace.
		final IWorkspaceDescription desc = ResourcesPlugin.getWorkspace()
				.getDescription();
		desc.setAutoBuilding(true);
		ResourcesPlugin.getWorkspace().setDescription(desc);
	}
}
