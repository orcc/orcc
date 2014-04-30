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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
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

	private Network newNetwork;

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

	@Override
	public void execute(ICustomContext context) {
		beforeJobExecution();
		execute(context, new NullProgressMonitor());
	}

	protected void beforeJobExecution() {
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
			return;
		}

		// The new network
		newNetwork = wizard.getCreatedNetwork();
	}

	protected void execute(ICustomContext context,
			IProgressMonitor parentMonitor) {

		if (newNetwork == null) {
			return;
		}

		final Network currentNetwork = (Network) getBusinessObjectForPictogramElement(getDiagram());
		final IFeatureProviderWithPatterns fp = (IFeatureProviderWithPatterns) getFeatureProvider();

		final SubMonitor monitor = SubMonitor.convert(parentMonitor, 140);

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

		monitor.worked(10);

		// This set will be filled with connections which needs to be
		// re-added to the diagram
		final Set<Connection> toUpdateInDiagram = new HashSet<Connection>();
		final Instance newInstance;
		try {
			// Update the current and the created network. Also create the new
			// instance used to replace all selected elements
			newInstance = updateNetworksAndCreateInstance(currentNetwork,
					newNetwork, selection, toUpdateInDiagram,
					monitor.newChild(50));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// Adds it to the current network
		final AddContext addContext = new AddContext();
		addContext.setTargetContainer(getDiagram());
		addContext.setNewObject(newInstance);
		// We will run the layout at the end
		addContext.setLocation(10, 10);
		final PictogramElement newInstancePe = getFeatureProvider()
				.addIfPossible(addContext);

		monitor.worked(10);

		SubMonitor loopProgress = monitor.newChild(30).setWorkRemaining(
				toUpdateInDiagram.size());
		monitor.setTaskName("Update existing connections");

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

			loopProgress.worked(1);
		}

		loopProgress = monitor.newChild(30)
				.setWorkRemaining(peSelection.size());
		monitor.setTaskName("Delete useless elements");

		// Finally remove from diagram useless elements. Inner connections
		// are also deleted, since deleting an instance or a port from a
		// diagram also clean related connections
		for (final PictogramElement pe : peSelection) {
			final IPattern pattern = fp.getPatternForPictogramElement(pe);
			final DeleteContext delContext = new DeleteContext(pe);
			delContext.setMultiDeleteInfo(new MultiDeleteInfo(false, false, 0));
			pattern.delete(delContext);

			loopProgress.worked(1);
		}

		monitor.setTaskName("Lay out the diagram");

		// Layout the resulting diagram
		final IContext layoutContext = new CustomContext();
		final ICustomFeature layoutFeature = ((XdfDiagramFeatureProvider) getFeatureProvider())
				.getDefaultLayoutFeature();
		if (layoutFeature.canExecute(layoutContext)) {
			layoutFeature.execute(layoutContext);
		}

		// Finally, active direct editing on the freshly created instance
		final IDirectEditingInfo dei = getFeatureProvider()
				.getDirectEditingInfo();
		dei.setMainPictogramElement(newInstancePe);
		dei.setActive(true);

		monitor.worked(10);

		hasDoneChanges = true;
	}

	/**
	 * <p>
	 * Here is the magic. In this function, both current and new network are
	 * updated to reflect changes of this feature.
	 * </p>
	 * 
	 * <p>
	 * All vertices (ports/instances) selected by user are duplicated. Copies
	 * are added to the new network, originals are removed from the current
	 * network. If selection cut connections in the current diagram, new ports
	 * are created and correctly connected in the new network. They are also
	 * updated to connect to the right port on the new instance.
	 * </p>
	 * 
	 * <p>
	 * This function modify networks only. It does not update corresponding
	 * diagrams.
	 * </p>
	 * 
	 * @param currentNetwork
	 *            The network user is working on
	 * @param newNetwork
	 *            The network created to contains elements selected by user
	 * @param selection
	 *            Vertices selected.
	 * @param toUpdateInDiagram
	 *            A set of connections. Needs to adds all these connections to
	 *            the current diagram
	 * @return The instance created
	 * @throws IOException
	 */
	private Instance updateNetworksAndCreateInstance(
			final Network currentNetwork, final Network newNetwork,
			final Set<Instance> selection,
			final Set<Connection> toUpdateInDiagram, final SubMonitor monitor)
			throws IOException {

		final Map<Instance, Instance> copies = new HashMap<Instance, Instance>();
		final Map<Connection, Port> toReconnectToTarget = new HashMap<Connection, Port>();
		final Map<Connection, Port> toReconnectFromSource = new HashMap<Connection, Port>();

		monitor.setWorkRemaining(100);

		SubMonitor loopProgress = monitor.newChild(20).setWorkRemaining(
				selection.size());
		// Adds copies of selected objects to the new network
		for (final Instance originalInstance : selection) {
			final Instance copy = EcoreUtil.copy(originalInstance);
			copies.put(originalInstance, copy);
			newNetwork.add(copy);

			loopProgress.worked(1);
		}

		loopProgress = monitor.newChild(40).setWorkRemaining(
				currentNetwork.getConnections().size());

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

			loopProgress.worked(1);
		}

		// Save the new network on the disk
		newNetwork.eResource().save(Collections.EMPTY_MAP);

		monitor.worked(20);

		final String instanceName = uniqueVertexName(currentNetwork,
				"groupedInstances");
		// Create the new instance
		final Instance newInstance = DfFactory.eINSTANCE.createInstance(
				instanceName, newNetwork);
		currentNetwork.add(newInstance);

		monitor.worked(10);

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

		monitor.worked(10);

		return newInstance;
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
