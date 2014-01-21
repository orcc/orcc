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
package net.sf.orcc.xdf.ui.layout;

import java.util.Map;

import net.sf.orcc.xdf.ui.patterns.InputNetworkPortPattern;
import net.sf.orcc.xdf.ui.patterns.InstancePattern;
import net.sf.orcc.xdf.ui.patterns.OutputNetworkPortPattern;
import net.sf.orcc.xdf.ui.util.ShapePropertiesManager;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.cau.cs.kieler.core.alg.BasicProgressMonitor;
import de.cau.cs.kieler.core.kgraph.KEdge;
import de.cau.cs.kieler.core.kgraph.KGraphElement;
import de.cau.cs.kieler.core.kgraph.KNode;
import de.cau.cs.kieler.kiml.klayoutdata.KEdgeLayout;
import de.cau.cs.kieler.kiml.klayoutdata.KShapeLayout;
import de.cau.cs.kieler.kiml.options.LayoutOptions;
import de.cau.cs.kieler.kiml.options.PortConstraints;
import de.cau.cs.kieler.klay.layered.LayeredLayoutProvider;

/**
 * This custom feature implements an auto-layout for any graph. It uses the
 * LayeredLayout algorithm from Kieler project. More information:
 * http://www.informatik.uni-kiel.de/en/rtsys/kieler/
 * 
 * @author Antoine Lorence
 * 
 */
abstract class AutoLayoutFeature extends AbstractCustomFeature {

	private boolean hasDoneChanges;

	public AutoLayoutFeature(IFeatureProvider fp) {
		super(fp);
		hasDoneChanges = false;
	}

	abstract protected String getLayoutAlgorithmName();

	@Override
	public String getDescription() {
		return "Layout diagram with Kieler Layouter"; //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return "&Automatically layout Diagram (" + getLayoutAlgorithmName() + ")"; //$NON-NLS-1$
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}

	@Override
	public void execute(ICustomContext ctxt) {

		final XdfDiagramLayoutManager manager = new XdfDiagramLayoutManager(getDiagram());

		configureLayout(manager.getTopLevelNode(), manager.getPeKGraphMap());

		final LayeredLayoutProvider provider = new LayeredLayoutProvider();
		provider.doLayout(manager.getTopLevelNode(), new BasicProgressMonitor());

		manager.applyLayout();
		hasDoneChanges = true;
	}

	private void configureLayout(final KNode diagramNode, final Map<PictogramElement, KGraphElement> peKnodeMap) {

		for (final Shape shape : getDiagram().getChildren()) {
			final String identifier = ShapePropertiesManager.getIdentifier(shape);
			final KShapeLayout shapeLayout = ((KNode) peKnodeMap.get(shape)).getData(KShapeLayout.class);
			if (InstancePattern.INSTANCE_ID.equals(identifier)) {
				if (shapeLayout != null) {
					configureInstanceNode(shapeLayout);
				}
			} else if (OutputNetworkPortPattern.INOUT_ID.equals(identifier)
					|| InputNetworkPortPattern.INOUT_ID.equals(identifier)) {
				if (shapeLayout != null) {
					configureNetworkPortNode(shapeLayout);
				}
			}
		}

		for (final Connection connection : getDiagram().getConnections()) {
			final KEdgeLayout edgeLayout = ((KEdge) peKnodeMap.get(connection)).getData(KEdgeLayout.class);
			if (edgeLayout != null) {
				configureConnectionEdge(edgeLayout);
			}
		}

		final KShapeLayout diagramLayout = diagramNode.getData(KShapeLayout.class);
		if (diagramLayout != null) {
			configureDiagramNode(diagramLayout);
		}
	}

	protected void configureDiagramNode(final KShapeLayout diagramLayout) {

		diagramLayout.setProperty(LayoutOptions.SPACING, 30.0f);

		/*
		 * Some options have been tested in the past:
		 * 
		 * mappingShapeLayout.setProperty(Properties.NODE_LAYERING,
		 * LayeringStrategy.LONGEST_PATH);
		 * mappingShapeLayout.setProperty(Properties.MERGE_PORTS, true);
		 * mappingShapeLayout.setProperty(Properties.CROSS_MIN,
		 * CrossingMinimizationStrategy.INTERACTIVE);
		 */
	}

	protected void configureInstanceNode(final KShapeLayout instanceLayout) {
		// We never want to move port inside an Instance
		instanceLayout.setProperty(LayoutOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);
	}

	protected void configureNetworkPortNode(final KShapeLayout portLayout) {
	}

	protected void configureConnectionEdge(final KEdgeLayout edgeLayout) {
	}
}
