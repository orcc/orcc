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

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.xdf.ui.patterns.InstancePattern;
import net.sf.orcc.xdf.ui.patterns.NetworkPortPattern;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.services.Graphiti;

import de.cau.cs.kieler.core.alg.BasicProgressMonitor;
import de.cau.cs.kieler.core.kgraph.KEdge;
import de.cau.cs.kieler.core.kgraph.KGraphElement;
import de.cau.cs.kieler.core.kgraph.KNode;
import de.cau.cs.kieler.core.kgraph.KPort;
import de.cau.cs.kieler.kiml.klayoutdata.KEdgeLayout;
import de.cau.cs.kieler.kiml.klayoutdata.KPoint;
import de.cau.cs.kieler.kiml.klayoutdata.KShapeLayout;
import de.cau.cs.kieler.kiml.options.EdgeRouting;
import de.cau.cs.kieler.kiml.options.LayoutOptions;
import de.cau.cs.kieler.kiml.options.PortConstraints;
import de.cau.cs.kieler.klay.layered.LayeredLayoutProvider;

/**
 * This custom feature implements an auto-layouting for any graph. It uses the
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

	abstract protected EdgeRouting getEdgeRouter();

	abstract protected String getEdgeRouterName();

	@Override
	public String getDescription() {
		return "Layout diagram with Kieler Layouter"; //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return "&Automatically layout Diagram (" + getEdgeRouterName() + " routing)"; //$NON-NLS-1$
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

		final XdfDiagramLayoutManager manager = new XdfDiagramLayoutManager(getDiagram(),
				(IFeatureProviderWithPatterns) getFeatureProvider());

		preFixDiagramMapping(manager.getTopLevelNode(), manager.getPeKGraphMap());

		final LayeredLayoutProvider provider = new LayeredLayoutProvider();
		provider.doLayout(manager.getTopLevelNode(), new BasicProgressMonitor());

		applyLayout(manager.getPeKGraphMap());
		hasDoneChanges = true;
	}

	private void preFixDiagramMapping(final KNode diagramNode, final Map<PictogramElement, KGraphElement> peKnodeMap) {

		IPattern pattern;
		for (final Shape shape : getDiagram().getChildren()) {
			pattern = ((IFeatureProviderWithPatterns) getFeatureProvider()).getPatternForPictogramElement(shape);
			if (pattern instanceof NetworkPortPattern) {
				preFixNetworkPortNode((KNode) peKnodeMap.get(shape));
			} else if (pattern instanceof InstancePattern) {
				preFixInstanceNode((KNode) peKnodeMap.get(shape));
			}
		}

		final KShapeLayout mappingShapeLayout = diagramNode.getData(KShapeLayout.class);
		if (mappingShapeLayout != null) {
			mappingShapeLayout.setProperty(LayoutOptions.SPACING, 30.0f);
			mappingShapeLayout.setProperty(LayoutOptions.EDGE_ROUTING, getEdgeRouter());

			/*
			 * Others options have been tested in the past:
			 * 
			 * mappingShapeLayout.setProperty(Properties.NODE_LAYERING,
			 * LayeringStrategy.LONGEST_PATH);
			 * mappingShapeLayout.setProperty(Properties.MERGE_PORTS, true);
			 * mappingShapeLayout.setProperty(Properties.CROSS_MIN,
			 * CrossingMinimizationStrategy.INTERACTIVE);
			 */
		}
	}

	private void preFixInstanceNode(final KNode instanceNode) {
		final KShapeLayout ksl = instanceNode.getData(KShapeLayout.class);
		if (ksl != null) {
			ksl.setProperty(LayoutOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);
		}
	}

	private void preFixNetworkPortNode(final KNode portNode) {
		final KShapeLayout ksl = portNode.getData(KShapeLayout.class);
		if (ksl != null) {
			ksl.setHeight(ksl.getHeight() + 20);
		}
	}

	public void applyLayout(Map<PictogramElement, KGraphElement> map) {

		for (Map.Entry<PictogramElement, KGraphElement> entry : map.entrySet()) {
			final PictogramElement pe = entry.getKey();
			final KGraphElement ge = entry.getValue();

			if (ge instanceof KNode) {
				applyLayoutOnNode(pe, (KNode) ge);
			} else if (ge instanceof KEdge) {
				applyLayoutOnConnection(pe, (KEdge) ge);
			} else if (ge instanceof KPort) {
				// We don't want to change ports position inside instances
			}
		}
	}

	private void applyLayoutOnNode(final PictogramElement pe, final KNode node) {
		final KShapeLayout shapeLayout = node.getData(KShapeLayout.class);
		final GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();

		final int x = Math.round(shapeLayout.getXpos());
		final int y = Math.round(shapeLayout.getYpos());

		Graphiti.getGaService().setLocation(ga, x, y);
	}

	private void applyLayoutOnConnection(final PictogramElement pe, final KEdge edge) {
		final KEdgeLayout edgeLayout = edge.getData(KEdgeLayout.class);
		final GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();

		// Check unsupported types
		if (!(pe instanceof FreeFormConnection)) {
			throw new OrccRuntimeException(pe.getClass() + " connection type is not supported.");
		} else if (!(ga instanceof Polyline)) {
			throw new OrccRuntimeException(ga.getClass() + " connection graphics type is not supported.");
		}

		final FreeFormConnection connection = (FreeFormConnection) pe;

		// Reset existing bendpoints for this connection
		connection.getBendpoints().clear();

		for (final KPoint kpoint : edgeLayout.getBendPoints()) {
			final Point point = Graphiti.getGaService().createPoint(Math.round(kpoint.getX()),
					Math.round(kpoint.getY()));
			connection.getBendpoints().add(point);
		}

	}
}
