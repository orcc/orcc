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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.xdf.ui.diagram.XdfDiagramFeatureProvider;
import net.sf.orcc.xdf.ui.dialogs.NewNetworkWizard;
import net.sf.orcc.xdf.ui.patterns.InstancePattern;
import net.sf.orcc.xdf.ui.util.PropsUtil;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
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

	/**
	 * Display a window to user, to configure location and name of the Network
	 * which will be created to store grouped instances.
	 * 
	 * @return A valid Network instance, configured with its name, fileName and
	 *         registered in a Resource
	 */
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

		return wizard.getCreatedNetwork();
	}

	@Override
	public void execute(ICustomContext context) {
		// The network to fill with selected content
		final Network newNetwork = selectNewNetworkResource();
		if (newNetwork == null) {
			return;
		}

		// The current network, where the selected content will be replaced by a
		// new instance
		final Network currentNetwork = (Network) getBusinessObjectForPictogramElement(getDiagram());

		// Configure lists of selected Instances and PictogramElements. If user
		// selected Ports or Connections, they are ignored
		final Set<Instance> selectedInstances = new HashSet<Instance>();
		final Set<PictogramElement> selectedPe = new HashSet<PictogramElement>();
		for (final PictogramElement pe : context.getPictogramElements()) {
			final Object selected = getBusinessObjectForPictogramElement(pe);
			if (selected instanceof Instance) {
				selectedInstances.add((Instance) selected);
				selectedPe.add(pe);
			} else {
				continue;
			}
		}

		// Create an Instance refined on the new Network
		final String instanceName = uniqueVertexName(currentNetwork,
				"groupedInstances");
		final Instance newInstance = DfFactory.eINSTANCE.createInstance(
				instanceName, newNetwork);
		currentNetwork.add(newInstance);

		// This will store the mapping between original instances and their copy
		// in the new Network
		final Map<Instance, Instance> copyMap = new HashMap<Instance, Instance>();

		// Copy each selected Instance into the new Network
		for (final Instance originalInstance : selectedInstances) {
			final Instance copyInstance = IrUtil.copy(originalInstance);
			copyMap.put(originalInstance, copyInstance);
			newNetwork.add(copyInstance);

			// IrUtil.copy() duplicated all arguments, and set their Use objects
			// to original variables, owned by the current network
			for (Argument arg : copyInstance.getArguments()) {

				for (Iterator<EObject> it = arg.eAllContents(); it.hasNext();) {
					final EObject childEObject = it.next();

					if (childEObject instanceof ExprVar) {

						final ExprVar exprVar = (ExprVar) childEObject;
						final Var varOrig = exprVar.getUse().getVariable();

						// Create a copy of the original variable (owned by
						// currentNetwork)
						final Var varCopy = EcoreUtil.copy(varOrig);
						// The newNetwork must contains this copy
						newNetwork.getParameters().add(varCopy);
						exprVar.getUse().setVariable(varCopy);

						final ExprVar newExprVar = IrFactory.eINSTANCE
								.createExprVar(varOrig);
						final Argument newArg = DfFactory.eINSTANCE
								.createArgument(varCopy, newExprVar);
						newInstance.getArguments().add(newArg);
					}
				}
			}
		}

		// This set will be filled with connections which needs to be
		// re-added to the diagram
		final Set<Connection> toUpdateInDiagram = new HashSet<Connection>();

		// Manage Connections in the current Network. Connections will NOT be
		// deleted, only updated, so it is not mandatory to make a copy before
		// looping on the list
		for (final Connection connection : currentNetwork.getConnections()) {
			// 1 - Inner connection: connect 2 vertex both contained in the
			// selection
			if (selectedInstances.contains(connection.getSource())
					&& selectedInstances.contains(connection.getTarget())) {
				final Connection copy = EcoreUtil.copy(connection);

				final Instance src = copyMap.get(connection.getSource());
				final Instance tgt = copyMap.get(connection.getTarget());

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
			else if (selectedInstances.contains(connection.getTarget())) {
				// Create an input Port in the new Network
				final Port newInputPort = DfFactory.eINSTANCE.createPort(
						EcoreUtil.copy(connection.getTargetPort().getType()),
						uniqueVertexName(newNetwork, connection.getTargetPort()
								.getName()));
				newNetwork.addInput(newInputPort);

				// Create a Connection in the new Network
				final Instance target = copyMap.get(connection.getTarget());
				final Port targetPort = target.getAdapter(Entity.class)
						.getInput(connection.getTargetPort().getName());
				newNetwork.add(DfFactory.eINSTANCE.createConnection(newInputPort, null,
						target, targetPort));

				// Update this Connection target: the new Instance, on the right
				// input Port
				connection.setTarget(newInstance);
				connection.setTargetPort(newInputPort);

				toUpdateInDiagram.add(connection);
			}
			// 3 - Cut connections: connected FROM a vertex contained in the
			// selection
			else if (selectedInstances.contains(connection.getSource())) {
				// Create an output Port in the new Network
				final Port newOutputPort = DfFactory.eINSTANCE.createPort(
						EcoreUtil.copy(connection.getSourcePort().getType()),
						uniqueVertexName(newNetwork, connection.getSourcePort()
								.getName()));
				newNetwork.addOutput(newOutputPort);

				// Create a Connection in the new Network
				final Instance source = copyMap.get(connection.getSource());
				final Port sourcePort = source.getAdapter(Entity.class)
						.getOutput(connection.getSourcePort().getName());
				newNetwork.add(DfFactory.eINSTANCE.createConnection(source,
						sourcePort, newOutputPort, null));

				// Update this Connection source: the new Instance, on the right
				// output Port
				connection.setSource(newInstance);
				connection.setSourcePort(newOutputPort);

				toUpdateInDiagram.add(connection);
			}
		}

		// Now the new Network is up-to-date. In particular, it contains all its
		// Ports. We can add the new Instance refined on this Network in the
		// current Diagram. Doing this now allows to have Anchors for Instance
		// Port available. These Anchors will be used to update existing
		// FreeFormConnections
		final AddContext addContext = new AddContext();
		addContext.setTargetContainer(getDiagram());
		addContext.setNewObject(newInstance);
		// We will run the layout at the end
		addContext.setLocation(10, 10);
		final PictogramElement newInstancePe = getFeatureProvider()
				.addIfPossible(addContext);

		// Get the instancePattern
		final IFeatureProviderWithPatterns fp = (IFeatureProviderWithPatterns) getFeatureProvider();
		final InstancePattern instancePattern = (InstancePattern) fp
				.getPatternForPictogramElement(newInstancePe);

		// We can update graphiti Connections to start or end from/to the newly
		// added Instance
		for (final Connection connection : toUpdateInDiagram) {
			final List<PictogramElement> pictogramElements = Graphiti
					.getLinkService().getPictogramElements(getDiagram(),
							connection);
			if (newInstance.equals(connection.getTarget())) {
				// Update the PE connection(s) target
				// (org.eclipse.graphiti.mm.pictograms.Connection#setEnd())
				for (final PictogramElement pe : pictogramElements) {
					if (pe instanceof org.eclipse.graphiti.mm.pictograms.Connection) {
						org.eclipse.graphiti.mm.pictograms.Connection peConnection = (org.eclipse.graphiti.mm.pictograms.Connection) pe;
						peConnection.setEnd(instancePattern.getAnchorForPort(
								newInstancePe, connection.getTargetPort()));
					}
				}
			} else if (newInstance.equals(connection.getSource())) {
				// Update the PE connection(s) source
				// (org.eclipse.graphiti.mm.pictograms.Connection#setStart())
				for (final PictogramElement pe : pictogramElements) {
					if (pe instanceof org.eclipse.graphiti.mm.pictograms.Connection) {
						org.eclipse.graphiti.mm.pictograms.Connection peConnection = (org.eclipse.graphiti.mm.pictograms.Connection) pe;
						peConnection.setStart(instancePattern.getAnchorForPort(
								newInstancePe, connection.getSourcePort()));
					}
				}
			} else {
				OrccLogger.severeln("Some connections will not be updated. "
						+ "This is an error in the code. Please report a bug.");
			}
		}

		// Finally remove from diagram useless elements. Inner connections
		// are also deleted, since deleting an instance or a port from a
		// diagram also clean related connections
		for (final PictogramElement pe : selectedPe) {
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

		// Save the new Network on the disk
		try {
			newNetwork.eResource().save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
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
