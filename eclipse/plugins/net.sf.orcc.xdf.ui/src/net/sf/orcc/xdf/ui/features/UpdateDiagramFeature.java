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

import java.io.IOException;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.xdf.ui.Activator;
import net.sf.orcc.xdf.ui.diagram.OrccDiagramTypeProvider;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.DefaultUpdateDiagramFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.ILinkService;

/**
 * This feature try to detect cases when a Diagram or a Network need to be
 * updated according to data contained from other one. It is executed each time
 * a Diagram is opened, thanks to the result of isAutoUpdateAtStartup().
 * 
 * This feature only apply on a Diagram. For updates on sub-shapes (instances,
 * ports, etc.) please see updates method in the corresponding patterns.
 * 
 * @see OrccDiagramTypeProvider#isAutoUpdateAtStartup()
 * @author Antoine Lorence
 * 
 */
public class UpdateDiagramFeature extends DefaultUpdateDiagramFeature {

	private boolean hasDoneChanges;

	public UpdateDiagramFeature(IFeatureProvider fp) {
		super(fp);
		hasDoneChanges = false;
	}

	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}

	@Override
	public boolean update(IUpdateContext context) {
		if (!(context.getPictogramElement() instanceof Diagram)) {
			OrccLogger.debugln("UpdateDiagramFeature has been used with a non Diagram parameter: "
					+ context.getPictogramElement().getClass().toString());
			return false;
		}

		final ILinkService linkService = Graphiti.getLinkService();

		final Diagram diagram = (Diagram) context.getPictogramElement();
		final EObject linkedBo = linkService.getBusinessObjectForLinkedPictogramElement(diagram);

		final URI diagramUri = diagram.eResource().getURI();
		final URI xdfUri = diagramUri.trimFileExtension().appendFileExtension(Activator.NETWORK_SUFFIX);

		final Network network;
		if (linkedBo == null || !(linkedBo instanceof Network)) {
			try {
				network = XdfUtil.createNetworkResource(xdfUri);
			} catch (IOException e) {
				OrccLogger.severeln("Unable to create the network resource " + xdfUri);
				return false;
			}
			link(diagram, network);
			hasDoneChanges = true;
		} else {
			network = (Network) linkedBo;

			final Resource xdfRes = network.eResource();
			if (xdfRes == null || !xdfRes.getURI().equals(xdfUri)) {
				// Particular case. The existing network is not contained in a
				// resource, or its resource has a wrong URI. It can happen if
				// the diagram has been moved or duplicated without the
				// corresponding xdf. The Move/Rename participant should take
				// care of that.

				final ResourceSet rs = new ResourceSetImpl();
				final Resource res = rs.createResource(xdfUri);
				res.getContents().add(EcoreUtil.copy(network));
				hasDoneChanges = true;
			}
		}

		hasDoneChanges |= updateContentsIfNeeded(network, diagram);

		return true;
	}

	/**
	 * Control both given diagram and network and detect if one or other has
	 * missing elements.
	 * 
	 * The network always has the higher priority to determinate which one is
	 * outdated
	 * 
	 * @param network
	 * @param diagram
	 * @return
	 */
	private boolean updateContentsIfNeeded(Network network, Diagram diagram) {

		for (Vertex vertex : network.getChildren()) {
			if (vertex instanceof Instance) {
				Instance instance = (Instance) vertex;
				// TODO
			}
		}

		for (Port port : network.getInputs()) {
			// TODO
		}

		for (Port port : network.getOutputs()) {
			// TODO
		}

		for (Connection con : network.getConnections()) {
			// TODO
		}

		return true;
	}

}
