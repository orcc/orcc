/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.xdf.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.graphiti.ui.services.IUiLayoutService;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * @author Antoine Lorence
 * 
 */
public class XdfUtil {

	public static Shell getDefaultShell() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		return window.getShell();
	}

	public static IProject getproject(EObject object) {

		String path = object.eResource().getURI().toPlatformString(true);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path))
				.getProject();
	}

	public static int getTextMinWidth(Text text) {

		final IUiLayoutService uiLayoutService = GraphitiUi.getUiLayoutService();
		if (text.getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getFont()).getWidth();
		} else if (text.getStyle().getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getStyle().getFont()).getWidth();
		}

		return -1;
	}

	public static int getTextMinHeight(Text text) {

		final IUiLayoutService uiLayoutService = GraphitiUi.getUiLayoutService();
		if (text.getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getFont()).getHeight();
		} else if (text.getStyle().getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getStyle().getFont()).getHeight();
		}

		return -1;
	}
}
