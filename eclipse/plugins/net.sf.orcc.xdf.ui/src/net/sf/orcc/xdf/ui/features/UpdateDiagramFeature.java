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
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.xdf.ui.Activator;
import net.sf.orcc.xdf.ui.diagram.OrccDiagramTypeProvider;
import net.sf.orcc.xdf.ui.layout.OrthogonalAutoLayoutFeature;
import net.sf.orcc.xdf.ui.patterns.ConnectionPattern;
import net.sf.orcc.xdf.ui.patterns.ConnectionPattern.PortInformation;
import net.sf.orcc.xdf.ui.patterns.InputNetworkPortPattern;
import net.sf.orcc.xdf.ui.patterns.InstancePattern;
import net.sf.orcc.xdf.ui.patterns.NetworkPortPattern;
import net.sf.orcc.xdf.ui.patterns.OutputNetworkPortPattern;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.impl.DefaultUpdateDiagramFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.dialogs.MessageDialog;

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

	public static String GLOBAL_VERSION_KEY = "xdf_diagram_version";
	public static String CURRENT_EDITOR_VERSION = "1";

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

		final EditingDomain editingDomain = getDiagramBehavior().getEditingDomain();
		final ResourceSet resourceSet = editingDomain.getResourceSet();
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		final Diagram diagram = (Diagram) context.getPictogramElement();
		final Object linkedBo = getBusinessObjectForPictogramElement(diagram);

		final URI diagramUri = diagram.eResource().getURI();
		final URI xdfUri = diagramUri.trimFileExtension().appendFileExtension(Activator.NETWORK_SUFFIX);


		final Network network;
		if (linkedBo == null || !(linkedBo instanceof Network)) {
			if (root.exists(new Path(xdfUri.toPlatformString(true)))) {
				network = (Network) resourceSet.getResource(xdfUri, true).getContents().get(0);
				link(diagram, network);
			} else {
				MessageDialog.openWarning(XdfUtil.getDefaultShell(), "Warning",
						"This diagram has no network attached. It should not be used.");
				return false;
			}
		} else {
			network = (Network) linkedBo;

			final Resource linkedRes = network.eResource();
			if (linkedRes == null || !linkedRes.getURI().equals(xdfUri)) {
				// Particular case. The existing network is not contained in a
				// resource, or its resource has a wrong URI. It can happen if
				// the diagram has been moved or duplicated without the
				// corresponding xdf. The Move/Rename participant should take
				// care of that.

				final Resource res = editingDomain.createResource(xdfUri.toString());
				res.getContents().add(EcoreUtil.copy(network));
				hasDoneChanges = true;
			}
		}

		if (diagram.getChildren().size() == 0 && network.getChildren().size() > 0) {
			hasDoneChanges |= initializeDiagramFromNetwork(network, diagram);
			return hasDoneChanges;
		}

		final String version = Graphiti.getPeService().getPropertyValue(diagram, GLOBAL_VERSION_KEY);
		if (version == null) {
			Graphiti.getPeService().setPropertyValue(diagram, GLOBAL_VERSION_KEY, CURRENT_EDITOR_VERSION);
		}
		// In future versions of this diagram editor, the diagram version should
		// be checked here. This should be used to automatically update diagrams
		// (styles, shapes tree, etc.) from an older to the current version of
		// the editor.
		return hasDoneChanges;
	}

	/**
	 * TODO: use this method when {@link NetworkPortPattern#getTypeFromShape}
	 * will work
	 * 
	 * @param diagram
	 * @return
	 * @deprecated This method does not work for now. See
	 *             {@link NetworkPortPattern#getTypeFromShape} for more
	 *             information
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private boolean initializeNetworkFromDiagram(Diagram diagram) {
		final URI diagramUri = diagram.eResource().getURI();
		final URI xdfUri = diagramUri.trimFileExtension().appendFileExtension(Activator.NETWORK_SUFFIX);

		final Network network;
		try {
			network = XdfUtil.createNetworkResource(getDiagramBehavior().getEditingDomain().getResourceSet(), xdfUri);
		} catch (IOException e) {
			OrccLogger.severeln("Unable to create the network resource " + xdfUri);
			return false;
		}
		link(diagram, network);

		for (Shape shape : diagram.getChildren()) {
			final IPattern pattern = ((IFeatureProviderWithPatterns) getFeatureProvider())
					.getPatternForPictogramElement(shape);

			if (pattern instanceof InstancePattern) {
				final InstancePattern instancePattern = (InstancePattern) pattern;
				final Instance instance = DfFactory.eINSTANCE.createInstance(instancePattern.getNameFromShape(shape),
						instancePattern.getRefinementFromShape(shape));

				network.add(instance);
				link(shape, instance);
			} else if (pattern instanceof InputNetworkPortPattern) {
				final InputNetworkPortPattern inPortPattern = (InputNetworkPortPattern) pattern;
				final Port port = DfFactory.eINSTANCE.createPort(inPortPattern.getTypeFromShape(shape),
						inPortPattern.getNameFromShape(shape));

				network.addInput(port);
				link(shape, port);
				shape.getLink().getBusinessObjects().add(port.getType());
			} else if (pattern instanceof OutputNetworkPortPattern) {
				final OutputNetworkPortPattern outPortPattern = (OutputNetworkPortPattern) pattern;
				final Port port = DfFactory.eINSTANCE.createPort(outPortPattern.getTypeFromShape(shape),
						outPortPattern.getNameFromShape(shape));

				network.addOutput(port);
				link(shape, port);
				shape.getLink().getBusinessObjects().add(port.getType());
			} else if (pattern instanceof ConnectionPattern) {
				final ConnectionPattern connPattern = (ConnectionPattern) pattern;
				final Connection connection = (Connection) shape;

				final PortInformation src = connPattern.getPortInformations(connection.getStart());
				final PortInformation tgt = connPattern.getPortInformations(connection.getEnd());

				final net.sf.orcc.df.Connection dfConnection = DfFactory.eINSTANCE.createConnection(src.getVertex(),
						src.getPort(), tgt.getVertex(), tgt.getPort());
				network.getConnections().add(dfConnection);
				link(connection, dfConnection);
			}
		}

		return true;
	}

	/**
	 * Read the given network, create corresponding graphical representation of
	 * Instance/Port/etc. and append them to the given diagram.
	 * 
	 * @param network
	 * @param diagram
	 * @return
	 */
	private boolean initializeDiagramFromNetwork(final Network network, final Diagram diagram) {

		final IFeatureProviderWithPatterns patternFP = (IFeatureProviderWithPatterns) getFeatureProvider();

		final Map<Port, Anchor> inAnchors = new HashMap<Port, Anchor>();
		final Map<Port, Anchor> outAnchors = new HashMap<Port, Anchor>();
		final Map<Instance, PictogramElement> instances = new HashMap<Instance, PictogramElement>();

		for (Vertex vertex : network.getChildren()) {
			if (vertex instanceof Instance) {
				final PictogramElement pe = addBoToDiagram(diagram, vertex);
				instances.put((Instance) vertex, pe);
			}
		}
		for (Port port : network.getInputs()) {
			final PictogramElement shape = addBoToDiagram(diagram, port);
			if (shape != null) {
				final InputNetworkPortPattern pattern = (InputNetworkPortPattern) patternFP
						.getPatternForPictogramElement(shape);
				final Anchor anchor = pattern.getAnchor((AnchorContainer) shape);
				if (anchor != null) {
					inAnchors.put(port, anchor);
				}
			}
		}
		for (Port port : network.getOutputs()) {
			final PictogramElement shape = addBoToDiagram(diagram, port);
			if (shape != null) {
				final OutputNetworkPortPattern pattern = (OutputNetworkPortPattern) patternFP
						.getPatternForPictogramElement(shape);
				final Anchor anchor = pattern.getAnchor((AnchorContainer) shape);
				if (anchor != null) {
					outAnchors.put(port, anchor);
				}
			}
		}
		for (net.sf.orcc.df.Connection con : network.getConnections()) {

			final Anchor srcAnchor, tgtAnchor;

			final Vertex sourceVertex = con.getSource();
			if (instances.containsKey(sourceVertex)) {
				final PictogramElement instancePe = instances.get(sourceVertex);
				final InstancePattern pattern = (InstancePattern) patternFP.getPatternForPictogramElement(instancePe);
				srcAnchor = pattern.getAnchorForPort(instancePe, con.getSourcePort());
			} else if (sourceVertex instanceof Port) {
				srcAnchor = inAnchors.get(sourceVertex);
			} else {
				srcAnchor = null;
			}

			final Vertex tgtVertex = con.getTarget();
			if (instances.containsKey(tgtVertex)) {
				final PictogramElement instancePe = instances.get(tgtVertex);
				final InstancePattern pattern = (InstancePattern) patternFP.getPatternForPictogramElement(instancePe);
				tgtAnchor = pattern.getAnchorForPort(instancePe, con.getTargetPort());
			} else if (tgtVertex instanceof Port) {
				tgtAnchor = outAnchors.get(tgtVertex);
			} else {
				tgtAnchor = null;
			}

			if (srcAnchor != null && tgtAnchor != null) {
				final AddConnectionContext ctxt = new AddConnectionContext(srcAnchor, tgtAnchor);
				ctxt.setNewObject(con);
				getFeatureProvider().addIfPossible(ctxt);
			} else {
				OrccLogger.warnln("Unable to retrieve the anchor corresponding to connection " + con);
			}
		}

		final IContext context = new CustomContext();
		final OrthogonalAutoLayoutFeature layoutFeature = new OrthogonalAutoLayoutFeature(getFeatureProvider());
		if (layoutFeature.canExecute(context)) {
			layoutFeature.execute(context);
		}

		return true;
	}

	/**
	 * Add the given business object to the given diagram if the diagram has at
	 * least a feature supporting the type of bo.
	 * 
	 * @param diagram
	 * @param bo
	 * @return
	 */
	private PictogramElement addBoToDiagram(final Diagram diagram, final EObject bo) {
		final AddContext addContext = new AddContext();
		addContext.setTargetContainer(diagram);
		addContext.setNewObject(bo);
		addContext.setLocation(10, 10);

		return getFeatureProvider().addIfPossible(addContext);
	}
}
