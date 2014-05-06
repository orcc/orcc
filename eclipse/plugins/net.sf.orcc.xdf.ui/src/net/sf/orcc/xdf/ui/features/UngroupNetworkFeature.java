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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.xdf.ui.diagram.XdfDiagramFeatureProvider;
import net.sf.orcc.xdf.ui.patterns.InstancePattern;
import net.sf.orcc.xdf.ui.util.PropsUtil;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.features.context.impl.MultiDeleteInfo;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
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
		final Map<Instance, PictogramElement> peMap = new HashMap<Instance, PictogramElement>();

		// Copy subNetwork variables into the current network
		for (Var variable : subNetwork.getVariables()) {
			thisNetwork.getVariables().add(IrUtil.copy(variable));
		}

		// Copy content of sub network in this network
		for (final Vertex vertex : subNetwork.getChildren()) {

			if (vertex instanceof Instance) {
				final Instance subInstance = IrUtil.copy((Instance) vertex);

				copies.put((Instance) vertex, subInstance);

				final AddContext addCtxt = new AddContext();
				addCtxt.setLocation(10, 10);
				addCtxt.setNewObject(subInstance);
				addCtxt.setTargetContainer(getDiagram());
				final PictogramElement pe = getFeatureProvider().addIfPossible(addCtxt);
				if(pe != null) {
					peMap.put(subInstance, pe);
				}

				// Update subInstance argument variable use
				for (Argument arg : subInstance.getArguments()) {
					for (Iterator<EObject> it = arg.eAllContents(); it
							.hasNext();) {
						final EObject childEObject = it.next();

						if (childEObject instanceof ExprVar) {
							final ExprVar exprVar = (ExprVar) childEObject;
							final String varName = exprVar.getUse().getVariable().getName();

							Var theVar = thisNetwork.getVariable(varName);
							if (theVar == null) {
								theVar = thisNetwork.getParameter(varName);
							}
							if (theVar == null) {
								OrccLogger
										.severeln("Unable to retrieve the variable "
												+ varName
												+ " in the current network. Its is used in a "
												+ instance.getName()
												+ "'s argument");
							} else {
								exprVar.getUse().setVariable(theVar);
							}
						}
					}
				}
			}
		}

		// Re-generate connections between subNetwork instances
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

				// We will 'add' a new Connection to a diagram (not create it,
				// in Graphiti context). It must exists in the current network
				thisNetwork.add(newConnection);

				// Really add the connection
				final IAddConnectionContext addConContext = XdfUtil
						.getAddConnectionContext(fp, getDiagram(), newConnection);
				getFeatureProvider().addIfPossible(addConContext);
			}
		}

		// Merge connections:
		// outerCon = connected from something in the current graph to an input of the instance
		// innerCon = connected from a subNetwork input to something else (in
		// subNetwork too)
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

				// We will 'add' a new Connection to a diagram (not create it,
				// in Graphiti context). It must exists in the current network
				thisNetwork.add(c);

				// Really add the connection
				final IAddConnectionContext addConContext = XdfUtil
						.getAddConnectionContext(fp, getDiagram(), c);
				getFeatureProvider().addIfPossible(addConContext);
			}
		}

		final InstancePattern instancePattern = (InstancePattern) ((IFeatureProviderWithPatterns) getFeatureProvider())
				.getPatternForPictogramElement(instancePe);

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

					// Update this network connection with the new source
					// instance
					outerCon.setSource(source);
					outerCon.setSourcePort(sourcePort);

					// Update this diagram connection with the new start anchor
					final List<PictogramElement> linkedPes = Graphiti
							.getLinkService().getPictogramElements(
									getDiagram(), outerCon);
					for (final PictogramElement pe : linkedPes) {
						if (pe instanceof org.eclipse.graphiti.mm.pictograms.Connection) {
							final Anchor anchor = instancePattern
									.getAnchorForPort(peMap.get(source),
											sourcePort);
							((org.eclipse.graphiti.mm.pictograms.Connection) pe)
									.setStart(anchor);
						}
					}
				}
			}
		}

		// Delete the selected instance PictogramElement from the current
		// diagram. This will also delete:
		// - The linked Instance from the Network
		// - The FreeFormConnections from/to this instance
		// - The corresponding net.sf.orcc.df.Connection instances from the
		// network
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
