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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.xdf.ui.diagram.OrccDiagramTypeProvider;
import net.sf.orcc.xdf.ui.diagram.XdfDiagramFeatureProvider;
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
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.DefaultUpdateDiagramFeature;
import org.eclipse.graphiti.mm.algorithms.styles.Style;
import org.eclipse.graphiti.mm.algorithms.styles.StylesPackage;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramBehavior;
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

	private static String GLOBAL_VERSION_KEY = "xdf_diagram_version";
	private static String VERSION_1 = "1";
	private static String CURRENT_EDITOR_VERSION = "2";

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
		final URI xdfUri = diagramUri.trimFileExtension().appendFileExtension(
				OrccUtil.NETWORK_SUFFIX);

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
			final List<String> updatedNetworkWarnings = new ArrayList<String>();

			hasDoneChanges |= fixNetwork(network, updatedNetworkWarnings);
			hasDoneChanges |= initializeDiagramFromNetwork(network, diagram);

			// Display a synthesis message to user, to tell him what have been
			// automatically modified in the diagram/network he just opened
			if (updatedNetworkWarnings.size() > 0) {
				final StringBuilder message = new StringBuilder(
						"The network has been automatically updated:");
				message.append('\n');

				for (final String msg : updatedNetworkWarnings) {
					message.append(msg).append('\n');
				}
				MessageDialog.openInformation(XdfUtil.getDefaultShell(),
						"Network update", message.toString());
			}
		}

		updateVersion(diagram);

		return hasDoneChanges;
	}

	/**
	 * Check the given network to detect potential issue, and fix them. For each
	 * fixed issue, a message is appended to the given messagesList.
	 * 
	 * @param network
	 * @param messagesList
	 * @return true if something has been modified in the network
	 */
	private boolean fixNetwork(final Network network, final List<String> messagesList) {
		// In this list, we will store all invalid objects which needs to be
		// deleted
		final List<EObject> toDelete = new ArrayList<EObject>();

		// Check for null started or terminated connections. This can happen if
		// a port, an instance or an instance port has been renamed from outside
		for(final net.sf.orcc.df.Connection connection : network.getConnections()) {
			if(connection.getSource() == null || connection.getTarget() == null) {
				toDelete.add(connection);
				continue;
			}
			final Vertex sourceVertex = connection.getSource();
			if(sourceVertex instanceof Instance) {
				if (connection.getSourcePort() == null
						|| connection.getSourcePort().getName() == null) {
					toDelete.add(connection);
					continue;
				}
			}

			final Vertex targetVertex = connection.getTarget();
			if(targetVertex instanceof Instance) {
				if (connection.getTargetPort() == null
						|| connection.getTargetPort().getName() == null) {
					toDelete.add(connection);
					continue;
				}
			}
		}

		if(toDelete.size() == 1){
			messagesList.add("1 connection deleted.");
		} else if(toDelete.size() > 1) {
			messagesList.add(toDelete.size() + " connections deleted.");
		}

		boolean hasDoneChanges = !toDelete.isEmpty();

		// Check for issues in network children (instances, ports)
		for (final Vertex vertex : network.getChildren()) {
			if (vertex instanceof Instance) {
				hasDoneChanges |= fixInstance((Instance) vertex, toDelete,
						messagesList);
			}
			// Check ports here, if needed
		}

		// Really delete wrong objects
		for (final EObject eobject : toDelete) {
			OrccLogger.noticeln("[" + eobject.getClass().getName() + "] "
					+ eobject + " deleted.");
			EcoreUtil.delete(eobject, true);
		}

		return hasDoneChanges;
	}

	/**
	 * Check the given instance to detect potential issues, and fix them. For
	 * each fixed instance, a message is appended to the given messagesList and
	 * objects which needs to be deleted are appended to given list
	 * <i>toDelete</i>.
	 * 
	 * @param instance
	 * @param messagesList
	 * @param todelete
	 *            Objects to delete later.
	 * @return true if something has been modified in the network
	 */
	private boolean fixInstance(final Instance instance,
			final List<EObject> toDelete, final List<String> messagesList) {

		boolean hasDoneChanges = false;

		int errorCount = 0;
		for (final Argument arg : instance.getArguments()) {
			if (arg.getVariable() == null
					|| arg.getVariable().getName() == null) {
				toDelete.add(arg);
				hasDoneChanges = true;
				errorCount++;
			}
		}

		if (hasDoneChanges) {
			messagesList.add(errorCount
					+ " argument(s) deleted from instance "
					+ instance.getSimpleName() + ".");
		}

		return hasDoneChanges;
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

		final XdfDiagramFeatureProvider xdfFeatureProvider = (XdfDiagramFeatureProvider) getFeatureProvider();

		for (Vertex vertex : network.getChildren()) {
			if (vertex instanceof Instance) {
				addBoToDiagram(diagram, vertex);
			}
		}
		for (Port port : network.getInputs()) {
			addBoToDiagram(diagram, port);
		}
		for (Port port : network.getOutputs()) {
			addBoToDiagram(diagram, port);
		}
		for (net.sf.orcc.df.Connection connection : network.getConnections()) {
			final IAddConnectionContext ctxt = XdfUtil.getAddConnectionContext(
					xdfFeatureProvider, getDiagram(), connection);
			getFeatureProvider().addIfPossible(ctxt);
		}

		// Layout the diagram
		final IContext context = new CustomContext();
		final ICustomFeature layoutFeature = xdfFeatureProvider
				.getDefaultLayoutFeature();
		if (layoutFeature.canExecute(context)) {
			layoutFeature.execute(context);
		}

		// Reset the buffered selection. Without this, the last object of the
		// diagram will be selected at the first layout() call on any object
		((DiagramBehavior) getDiagramBehavior()).setPictogramElementForSelection(null);

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

	/**
	 * Check if this diagram is outdated and update it according to the version
	 * number stored in its properties.
	 * 
	 * @param diagram
	 */
	private void updateVersion(final Diagram diagram) {
		final String version = Graphiti.getPeService().getPropertyValue(
				diagram, GLOBAL_VERSION_KEY);

		if (CURRENT_EDITOR_VERSION.equals(version)) {
			return;
		} else if (version == null) {
			// A new Diagram: set version to current
			Graphiti.getPeService().setPropertyValue(diagram,
					GLOBAL_VERSION_KEY, CURRENT_EDITOR_VERSION);
			return;
		}

		/*
		 * Eclipse Luna comes with Graphiti 0.11. This new Graphiti version
		 * replaces 'angle' feature with 'rotation' equivalent. Unfortunately,
		 * 'rotation' is not defined in Graphiti model before 0.11. If a xdfdiag
		 * contains an 'angle' value, it will be converted by newer versions to
		 * 'rotation'. As a result, the diagram will become invalid for all
		 * older versions of Orcc. To fix the issue, we force to remove all
		 * usage of 'angle' property. They are not used and were in the default
		 * "COMMON_TEXT" style only because of a copy/paste from Graphiti doc.
		 */
		if (VERSION_1.equals(version)) {
			for (Style style : diagram.getStyles()) {
				if (style.eIsSet(StylesPackage.eINSTANCE.getStyle_Angle())) {
					style.eUnset(StylesPackage.eINSTANCE.getStyle_Angle());
				}
			}
		}
	}
}
