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
package net.sf.orcc.xdf.ui.features;

import net.sf.orcc.util.OrccLogger;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * This feature is part of context menu. It simply open the "Properties" tab and
 * initialize it with the current selected elements. It is useful when user
 * doesn't know how to display this tab. In some case, it is hidden by default
 * (if eclipse has just been reinstalled, etc.)
 * 
 * @author Antoine Lorence
 * 
 */
public class OpenPropertiesFeature extends AbstractCustomFeature {

	public OpenPropertiesFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Show properties";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		// Properties tab can always be opened. There is always something
		// selected when user trigger context menu
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		final IViewPart propertiesPart;
		try {
			// Get and show the "Properties" tab
			propertiesPart = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.showView("org.eclipse.ui.views.PropertySheet");
		} catch (PartInitException e) {
			e.printStackTrace();
			return;
		}

		final DiagramEditor diagramEditor = (DiagramEditor) getDiagramBehavior()
				.getDiagramContainer();

		if (propertiesPart instanceof PropertySheet) {
			// Get the TabbedPropertyPage
			TabbedPropertySheetPage page = (TabbedPropertySheetPage) propertiesPart
					.getAdapter(TabbedPropertySheetPage.class);

			// If the adapter didn't find a TabbedPage, the Properties tab
			// displays the default table content, we have to initialize it
			if (page == null) {
				// Initialize the tab with the current Diagram Editor
				((PropertySheet) propertiesPart).partActivated(diagramEditor);
				page = (TabbedPropertySheetPage) propertiesPart
						.getAdapter(TabbedPropertySheetPage.class);
			}

			if (page != null) {
				// Immediately displays content related to the current
				// selection
				final ISelection currentSelection = new StructuredSelection(
						context.getPictogramElements());
				page.selectionChanged(diagramEditor, currentSelection);
			} else {
				OrccLogger.warnln("Unable to refresh Property tab...");
			}
		}
	}

	@Override
	public boolean hasDoneChanges() {
		// This feature does not modify anything in the diagram
		return false;
	}
}
