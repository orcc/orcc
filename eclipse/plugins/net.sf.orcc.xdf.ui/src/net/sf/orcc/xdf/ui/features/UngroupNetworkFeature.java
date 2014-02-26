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
package net.sf.orcc.xdf.ui.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.xdf.ui.diagram.XdfDiagramFeatureProvider;
import net.sf.orcc.xdf.ui.util.PropsUtil;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.features.context.impl.MultiDeleteInfo;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.services.Graphiti;

/**
 * Replace the selected instance by the content of the network it refined on.
 * 
 * @author Antoine Lorence
 * 
 */
public class UngroupNetworkFeature extends AbstractCustomFeature {

	private boolean hasDoneChanges;

	public UngroupNetworkFeature(IFeatureProvider fp) {
		super(fp);
		hasDoneChanges = false;
	}

	@Override
	public String getName() {
		return "Separate the network into new instances";
	}

	@Override
	public String getDescription() {
		return "Replace this instance by the network content it is refined on.";
	}

	@Override
	public boolean isAvailable(IContext context) {
		return super.isAvailable(context);
	}

	/*
	 * This feature can be executed if user selected only 1 instance, and if
	 * this instance is refined on a Network
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.graphiti.features.custom.AbstractCustomFeature#canExecute
	 * (org.eclipse.graphiti.features.context.ICustomContext)
	 */
	@Override
	public boolean canExecute(ICustomContext context) {

		if (context.getPictogramElements().length != 1) {
			return false;
		}

		final PictogramElement pe = context.getPictogramElements()[0];
		if (PropsUtil.isInstance(pe)) {
			final Object obj = getBusinessObjectForPictogramElement(pe);
			if (obj instanceof Instance && ((Instance) obj).isNetwork()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void execute(ICustomContext context) {
		final PictogramElement instancePe = context.getPictogramElements()[0];
		final Instance instance = (Instance) getBusinessObjectForPictogramElement(instancePe);
		final Network subNetwork = instance.getNetwork();
		final Network thisNetwork = (Network) getBusinessObjectForPictogramElement(getDiagram());

		final IFeatureProviderWithPatterns fp = (IFeatureProviderWithPatterns) getFeatureProvider();

		final Map<Instance, Instance> copies = new HashMap<Instance, Instance>();
		// Copy content of sub network in this network
		for (final Vertex vertex : subNetwork.getChildren()) {

			if (vertex instanceof Instance) {
				final Instance subInstance = EcoreUtil.copy((Instance) vertex);

				copies.put((Instance) vertex, subInstance);

				final AddContext addCtxt = new AddContext();
				addCtxt.setLocation(10, 10);
				addCtxt.setNewObject(subInstance);
				addCtxt.setTargetContainer(getDiagram());
				getFeatureProvider().addIfPossible(addCtxt);
			}
		}
		
		for(final Connection connection : subNetwork.getConnections()) {
			// The connection is between 2 instances
			// Connections from/to a network port are analyzed later
			if(connection.getSourcePort() != null && connection.getTargetPort() != null) {
				
				final Instance source = copies.get(connection.getSource());
				final Port sourcePort = source.getAdapter(Entity.class).getOutput(connection.getSourcePort().getName());
				final Instance target = copies.get(connection.getTarget());
				final Port targetPort = target.getAdapter(Entity.class).getInput(connection.getTargetPort().getName());
				
				final Connection newConnection = DfFactory.eINSTANCE.createConnection(
						source, sourcePort, target, targetPort);

				thisNetwork.add(newConnection);

				final AddConnectionContext addConContext = XdfUtil
						.getAddConnectionContext(fp, getDiagram(), newConnection);
				addConContext.setNewObject(newConnection);
				getFeatureProvider().addIfPossible(addConContext);
			}
		}

		// Merge connections:
		// outerCon = connected from something in the current graph to an input of the instance
		// innerCon = connected from a subNetwork input to something else (subNetwork too)
		for (final Port inPort : subNetwork.getInputs()) {

			final Connection outerCon = instance.getIncomingPortMap().get(inPort);
			if (outerCon == null) {
				continue;
			}

			for (final Edge innerEdge : inPort.getOutgoing()) {
				final Connection innnerCon = (Connection) innerEdge;

				final Instance target = copies.get(innnerCon.getTarget());
				final Port targetPort = target.getAdapter(Entity.class).getInput(innnerCon.getTargetPort().getName());
				
				final Connection c = DfFactory.eINSTANCE.createConnection(
						outerCon.getSource(), outerCon.getSourcePort(),
						target, targetPort);


				// Delete the link, to avoid loosing the connection when instance will be deleted
				final List<PictogramElement> pes = Graphiti.getLinkService().getPictogramElements(getDiagram(), outerCon);
				for(PictogramElement linkedPe : pes) {
					EcoreUtil.delete(linkedPe.getLink(), true);
				}

				thisNetwork.getConnections().remove(outerCon);
				thisNetwork.add(c);

				final AddConnectionContext addConContext = XdfUtil
						.getAddConnectionContext(fp, getDiagram(), c);
				addConContext.setNewObject(c);
				getFeatureProvider().addIfPossible(addConContext);
			}
		}

		// Merge connections:
		// outerCons = connected from an output of the instance to something in the current graph
		// innerCon = connected from something to an output in the subNetwork
		for (final Port outPort : subNetwork.getOutputs()) {

			final List<Connection> outerCons = instance.getOutgoingPortMap()
					.get(outPort);
			if (outerCons == null || outerCons.isEmpty()) {
				continue;
			}

			for (final Edge innerEdge : outPort.getIncoming()) {
				final Connection innerCon = (Connection) innerEdge;
				for (final Connection outerCon : outerCons) {

					final Instance source = copies.get(innerCon.getSource());
					final Port sourcePort = source.getAdapter(Entity.class).getOutput(innerCon.getSourcePort().getName());

					final Connection c = DfFactory.eINSTANCE.createConnection(
							source, sourcePort,
							outerCon.getTarget(), outerCon.getTargetPort());

					// Delete the link, to avoid loosing the connection when instance will be deleted
					final List<PictogramElement> pes = Graphiti.getLinkService().getPictogramElements(getDiagram(), outerCon);
					for(PictogramElement linkedPe : pes) {
						EcoreUtil.delete(linkedPe.getLink(), true);
					}

					thisNetwork.getConnections().remove(outerCon);
					thisNetwork.add(c);

					final AddConnectionContext addConContext = XdfUtil
							.getAddConnectionContext(fp, getDiagram(), c);
					addConContext.setNewObject(c);
					getFeatureProvider().addIfPossible(addConContext);
				}
			}
		}

		// Remove the selected instance from the current network
		final IPattern pattern = fp.getPatternForPictogramElement(instancePe);
		final DeleteContext delContext = new DeleteContext(instancePe);
		delContext.setMultiDeleteInfo(new MultiDeleteInfo(false, false, 0));
		pattern.delete(delContext);
		
		// And layout the resulting diagram
		final IContext layoutContext = new CustomContext();
		final ICustomFeature layoutFeature = ((XdfDiagramFeatureProvider) getFeatureProvider())
				.getDefaultLayoutFeature();
		if (layoutFeature.canExecute(layoutContext)) {
			layoutFeature.execute(layoutContext);
		}
		hasDoneChanges = true;
	}

	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}

}
