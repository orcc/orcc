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

import java.io.IOException;
import java.util.Collections;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Graph;
import net.sf.orcc.xdf.ui.Activator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
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

	private static ResourceSet resourceSet = new ResourceSetImpl();

	public static ResourceSet getCommonresourceSet() {
		return resourceSet;
	}

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

	public static Network createNetworkResource(URI uri) throws IOException {

		// Compute the new network name
		final String name = uri.trimFileExtension().lastSegment();

		// Create the network
		final Network network = DfFactory.eINSTANCE.createNetwork();
		network.setName(name);

		// Create the resource
		Resource res = resourceSet.createResource(uri);
		res.getContents().add(network);
		res.save(Collections.EMPTY_MAP);

		return network;
	}

	public static Diagram createDiagramResource(URI uri) throws IOException {
		// Compute the new diagram name
		final String name = uri.trimFileExtension().lastSegment();

		// Create the diagram
		final Diagram diagram = Graphiti.getPeCreateService().createDiagram(Activator.DIAGRAM_TYPE, name, true);

		// Create the resource
		Resource res = resourceSet.createResource(uri);
		res.getContents().add(diagram);
		res.save(Collections.EMPTY_MAP);

		return diagram;
	}


	/**
	 * Returns the minimal width needed to display the value of given Text with
	 * its associated Font.
	 * 
	 * @param text
	 * @return
	 */
	public static int getTextMinWidth(Text text) {

		final IUiLayoutService uiLayoutService = GraphitiUi.getUiLayoutService();
		if (text.getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getFont()).getWidth();
		} else if (text.getStyle().getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getStyle().getFont()).getWidth();
		}

		return -1;
	}

	/**
	 * Returns the minimal height needed to display the value of given Text with
	 * its associated Font.
	 * 
	 * @param text
	 * @return
	 */
	public static int getTextMinHeight(Text text) {

		final IUiLayoutService uiLayoutService = GraphitiUi.getUiLayoutService();
		if (text.getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getFont()).getHeight();
		} else if (text.getStyle().getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getStyle().getFont()).getHeight();
		}

		return -1;
	}

	/**
	 * Check if the given port is contained in a Network in its inputs.
	 * 
	 * @param port
	 * @return
	 */
	public static boolean isInputNetworkPort(final Port port) {
		final Graph graph = port.getGraph();
		if (graph != null && graph instanceof Network) {
			final Network network = (Network) graph;
			for (final Port p : network.getInputs()) {
				if (p == port) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if the given port is contained in a Network in its outputs.
	 * 
	 * @param port
	 * @return
	 */
	public static boolean isOutputNetworkPort(final Port port) {
		final Graph graph = port.getGraph();
		if (graph != null && graph instanceof Network) {
			final Network network = (Network) graph;
			for (final Port p : network.getOutputs()) {
				if (p == port) {
					return true;
				}
			}
		}
		return false;
	}
}
