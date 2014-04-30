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

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.xdf.ui.diagram.XdfDiagramFeatureProvider;
import net.sf.orcc.xdf.ui.dialogs.NewNetworkWizard;
import net.sf.orcc.xdf.ui.util.PropsUtil;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IDirectEditingInfo;
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
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

/**
 * Group selected instances into a new Network located somewhere. Replace the
 * selected instances by a new one, refined on the network created.
 * 
 * @author Antoine Lorence
 * 
 */
public class GroupInstancesFeature extends AbstractCustomFeature {

	private boolean hasDoneChanges;

	public GroupInstancesFeature(IFeatureProvider fp) {
		super(fp);
		hasDoneChanges = false;
	}

	@Override
	public String getName() {
		return "Group selected instances into new network";
	}

	@Override
	public String getDescription() {
		return "Create a new network containing selected elements, and replace them with a new instance refined on this network.";
	}

	@Override
	public boolean isAvailable(IContext context) {
		return super.isAvailable(context);
	}

	/*
	 * This feature can be executed if user selected at least 2 instances. If
	 * other elements are selected (ports, connections) they will be ignored
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.graphiti.features.custom.AbstractCustomFeature#canExecute
	 * (org.eclipse.graphiti.features.context.ICustomContext)
	 */
	@Override
	public boolean canExecute(ICustomContext context) {
		final PictogramElement[] selection = context.getPictogramElements();
		if (selection.length < 2) {
			return false;
		}

		int cptInstances = 0;
		for (final PictogramElement pe : selection) {
			if (PropsUtil.isInstance(pe)) {
				cptInstances++;
			}
		}

		return cptInstances >= 2;
	}

	protected Network selectNewNetworkResource() {
		final Network currentNetwork = (Network) getBusinessObjectForPictogramElement(getDiagram());

		// Create the wizard used to select name and location for the new
		// network
		final NewNetworkWizard wizard = new NewNetworkWizard(false);

		// Initialize the wizard with the current package location
		final StructuredSelection networkSelection = new StructuredSelection(
				currentNetwork.getFile().getParent());
		wizard.init(PlatformUI.getWorkbench(), networkSelection);

		final WizardDialog dialog = new WizardDialog(XdfUtil.getDefaultShell(),
				wizard);
		dialog.open();
		if (dialog.getReturnCode() != Dialog.OK) {
			return null;
		}

		// The new network
		return wizard.getCreatedNetwork();
	}

	@Override
	public void execute(ICustomContext context) {
		Network newNetwork = selectNewNetworkResource();
		if (newNetwork == null) {
			return;
		}

		final Network currentNetwork = (Network) getBusinessObjectForPictogramElement(getDiagram());
		final IFeatureProviderWithPatterns fp = (IFeatureProviderWithPatterns) getFeatureProvider();

		final Set<Instance> selection = new HashSet<Instance>();
		final Set<PictogramElement> peSelection = new HashSet<PictogramElement>();
		for (final PictogramElement pe : context.getPictogramElements()) {
			final Object selected = getBusinessObjectForPictogramElement(pe);
			if (selected instanceof Instance) {
				selection.add((Instance) selected);
				peSelection.add(pe);
			} else {
				continue;
			}
		}

		// This set will be filled with connections which needs to be
		// re-added to the diagram
		final Set<Connection> toUpdateInDiagram = new HashSet<Connection>();
		final Instance newInstance;

		final Map<Instance, Instance> copies = new HashMap<Instance, Instance>();
		final Map<Connection, Port> toReconnectToTarget = new HashMap<Connection, Port>();
		final Map<Connection, Port> toReconnectFromSource = new HashMap<Connection, Port>();

		// Adds copies of selected objects to the new network
		for (final Instance originalInstance : selection) {
			final Instance copy = EcoreUtil.copy(originalInstance);
			copies.put(originalInstance, copy);
			newNetwork.add(copy);
		}

		// Manage connections
		for (final Connection connection : currentNetwork.getConnections()) {
			// 1 - Inner connection: connect 2 vertex both contained in the
			// selection
			if (selection.contains(connection.getSource())
					&& selection.contains(connection.getTarget())) {
				final Connection copy = EcoreUtil.copy(connection);

				final Instance src = copies.get(connection.getSource());
				final Instance tgt = copies.get(connection.getTarget());

				copy.setSource(src);
				copy.setTarget(tgt);
				copy.setSourcePort(src.getAdapter(Entity.class).getOutput(
						connection.getSourcePort().getName()));
				copy.setTargetPort(tgt.getAdapter(Entity.class).getInput(
						connection.getTargetPort().getName()));

				newNetwork.add(copy);
			}
			// 2 - Cut connection: connected TO a vertex contained in the
			// selection
			else if (selection.contains(connection.getTarget())) {
				// Create a new port
				final Port p = DfFactory.eINSTANCE.createPort(
						EcoreUtil.copy(connection.getTargetPort().getType()),
						uniqueVertexName(newNetwork, connection.getTargetPort()
								.getName()));
				newNetwork.addInput(p);
				// We will reconnect this connection when new instance will be
				// created
				toReconnectToTarget.put(connection, p);
				// Create a new connection, ...
				final Instance target = copies.get(connection.getTarget());
				final Port targetPort = target.getAdapter(Entity.class)
						.getInput(connection.getTargetPort().getName());
				final Connection c = DfFactory.eINSTANCE.createConnection(p,
						null, target, targetPort);
				// ... fully contained in the new network
				newNetwork.add(c);
			}
			// 3 - Cut connections: connected FROM a vertex contained in the
			// selection
			else if (selection.contains(connection.getSource())) {
				// Create a new port
				final Port p = DfFactory.eINSTANCE.createPort(
						EcoreUtil.copy(connection.getSourcePort().getType()),
						uniqueVertexName(newNetwork, connection.getSourcePort()
								.getName()));
				newNetwork.addOutput(p);
				// We will reconnect this connection when new instance will be
				// created
				toReconnectFromSource.put(connection, p);
				// Create a new connection, ...
				final Instance source = copies.get(connection.getSource());
				final Port sourcePort = source.getAdapter(Entity.class)
						.getOutput(connection.getSourcePort().getName());

				final Connection c = DfFactory.eINSTANCE.createConnection(
						source, sourcePort, p, null);
				// ... fully contained in the new network
				newNetwork.add(c);
			}
		}

		// Save the new network on the disk
		try {
			newNetwork.eResource().save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}

		final String instanceName = uniqueVertexName(currentNetwork,
				"groupedInstances");
		// Create the new instance
		newInstance = DfFactory.eINSTANCE.createInstance(
				instanceName, newNetwork);
		currentNetwork.add(newInstance);

		// Update existing connections. Re-connect them to the new instance, on
		// the right port
		for (final Map.Entry<Connection, Port> entry : toReconnectToTarget
				.entrySet()) {
			final Connection connection = entry.getKey();
			final Port targetPort = entry.getValue();

			connection.setTarget(newInstance);
			connection.setTargetPort(targetPort);
			toUpdateInDiagram.add(connection);
		}

		for (final Map.Entry<Connection, Port> entry : toReconnectFromSource
				.entrySet()) {
			final Connection connection = entry.getKey();
			final Port sourcePort = entry.getValue();

			connection.setSource(newInstance);
			connection.setSourcePort(sourcePort);
			toUpdateInDiagram.add(connection);
		}

		// Adds it to the current network
		final AddContext addContext = new AddContext();
		addContext.setTargetContainer(getDiagram());
		addContext.setNewObject(newInstance);
		// We will run the layout at the end
		addContext.setLocation(10, 10);
		final PictogramElement newInstancePe = getFeatureProvider()
				.addIfPossible(addContext);

		// Update connections to/from the new instance
		for (final Connection connection : toUpdateInDiagram) {

			// Delete the link, to avoid loosing the connection when instance will be deleted
			final List<PictogramElement> pes = Graphiti.getLinkService().getPictogramElements(getDiagram(), connection);
			for(PictogramElement linkedPe : pes) {
				EcoreUtil.delete(linkedPe.getLink(), true);
			}

			final IAddConnectionContext addConContext =
					XdfUtil.getAddConnectionContext(fp, getDiagram(), connection);
			getFeatureProvider().addIfPossible(addConContext);
		}

		// Finally remove from diagram useless elements. Inner connections
		// are also deleted, since deleting an instance or a port from a
		// diagram also clean related connections
		for (final PictogramElement pe : peSelection) {
			final IPattern pattern = fp.getPatternForPictogramElement(pe);
			final DeleteContext delContext = new DeleteContext(pe);
			delContext.setMultiDeleteInfo(new MultiDeleteInfo(false, false, 0));
			pattern.delete(delContext);
		}

		// Layout the resulting diagram
		final IContext layoutContext = new CustomContext();
		final ICustomFeature layoutFeature = ((XdfDiagramFeatureProvider) getFeatureProvider())
				.getDefaultLayoutFeature();
		if (layoutFeature.canExecute(layoutContext)) {
			layoutFeature.execute(layoutContext);
		}

		// Finally, active direct editing on the newly created instance
		final IDirectEditingInfo dei = getFeatureProvider()
				.getDirectEditingInfo();
		dei.setMainPictogramElement(newInstancePe);
		dei.setActive(true);

		hasDoneChanges = true;
	}

	/**
	 * Check if the given network contains a vertex (instance or port) with the
	 * given base name. If yes, return a new unique name formed from the given
	 * base and a numeric suffix. If not, returns the given base.
	 * 
	 * @param network
	 *            The network to check for existing vertex with the given name
	 * @param base
	 *            The base name to assign to a new vertex
	 * @return A unique name to assign to a new Instance / Port in the given
	 *         network
	 */
	private String uniqueVertexName(final Network network, final String base) {
		final Set<String> existingNames = new HashSet<String>();
		for (Vertex vertex : network.getVertices()) {
			existingNames.add(vertex.getLabel());
		}

		if (!existingNames.contains(base)) {
			return base;
		} else {
			int index = 0;
			while (existingNames.contains(base + '_' + index)) {
				++index;
			}
			return base + '_' + index;
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}
}
