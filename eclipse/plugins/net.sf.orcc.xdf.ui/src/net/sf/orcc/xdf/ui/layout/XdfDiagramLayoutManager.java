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

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.xdf.ui.patterns.NetworkPortPattern;
import net.sf.orcc.xdf.ui.util.ShapePropertiesManager;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.services.Graphiti;

import de.cau.cs.kieler.core.kgraph.KEdge;
import de.cau.cs.kieler.core.kgraph.KGraphElement;
import de.cau.cs.kieler.core.kgraph.KNode;
import de.cau.cs.kieler.core.kgraph.KPort;
import de.cau.cs.kieler.kiml.klayoutdata.KEdgeLayout;
import de.cau.cs.kieler.kiml.klayoutdata.KPoint;
import de.cau.cs.kieler.kiml.klayoutdata.KShapeLayout;
import de.cau.cs.kieler.kiml.util.KimlUtil;

public class XdfDiagramLayoutManager {

	private final KNode diagramKNode;

	private final Map<PictogramElement, KGraphElement> peKGraphMap;

	XdfDiagramLayoutManager(final Diagram diagram, final IFeatureProviderWithPatterns fp) {
		peKGraphMap = new HashMap<PictogramElement, KGraphElement>();

		diagramKNode = KimlUtil.createInitializedNode();
		final GraphicsAlgorithm diagramGa = diagram.getGraphicsAlgorithm();
		final KShapeLayout rootLayout = diagramKNode.getData(KShapeLayout.class);
		rootLayout.setSize(diagramGa.getWidth(), diagramGa.getHeight());
		rootLayout.setPos(diagramGa.getX(), diagramGa.getY());

		peKGraphMap.put(diagram, diagramKNode);

		for (final Shape shape : diagram.getChildren()) {
			final EObject eObject = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(shape);
			if (eObject instanceof Instance) {
				registerInstance(shape);
			} else if (eObject instanceof Port) {
				registerPort(shape);
			} else {
				// ??
			}
		}

		for (final Connection connection : diagram.getConnections()) {
			registerConnection(connection);
		}
	}

	public KNode getTopLevelNode() {
		return diagramKNode;
	}

	public Map<PictogramElement, KGraphElement> getPeKGraphMap() {
		return peKGraphMap;
	}

	private void registerInstance(final Shape instanceShape) {
		final GraphicsAlgorithm instanceGa = instanceShape.getGraphicsAlgorithm();
		final KNode instanceKNode = KimlUtil.createInitializedNode();

		final KShapeLayout instanceLayout = instanceKNode.getData(KShapeLayout.class);
		instanceLayout.setSize(instanceGa.getWidth(), instanceGa.getHeight());
		instanceLayout.setPos(instanceGa.getX(), instanceGa.getY());

		diagramKNode.getChildren().add(instanceKNode);
		peKGraphMap.put(instanceShape, instanceKNode);

		for (final Anchor anchor : instanceShape.getAnchors()) {
			registerInstancePort((FixPointAnchor) anchor, instanceKNode);
		}
	}

	private void registerInstancePort(final FixPointAnchor anchor, final KNode instanceNode) {
		final GraphicsAlgorithm portRect = anchor.getGraphicsAlgorithm();
		final Point anchorLocation = anchor.getLocation();
		final KPort kport = KimlUtil.createInitializedPort();

		final KShapeLayout portLayout = kport.getData(KShapeLayout.class);

		portLayout.setSize(portRect.getWidth(), portRect.getHeight());
		portLayout.setPos(anchorLocation.getX() + portRect.getX(), anchorLocation.getY() + portRect.getY());

		instanceNode.getPorts().add(kport);
		peKGraphMap.put(anchor, kport);
	}

	private void registerPort(final Shape portShape) {

		final GraphicsAlgorithm topLevelInvisibleRect = portShape.getGraphicsAlgorithm();
		final GraphicsAlgorithm portPolygon = (GraphicsAlgorithm) ShapePropertiesManager.findPcFromIdentifier(
				topLevelInvisibleRect, NetworkPortPattern.SHAPE_ID);
		final KNode portKNode = KimlUtil.createInitializedNode();

		final KShapeLayout portLayout = portKNode.getData(KShapeLayout.class);
		portLayout.setSize(NetworkPortPattern.SHAPE_WIDTH, NetworkPortPattern.SHAPE_HEIGHT);
		portLayout.setPos(topLevelInvisibleRect.getX() + portPolygon.getX(),
				topLevelInvisibleRect.getY() + portPolygon.getY());

		diagramKNode.getChildren().add(portKNode);
		peKGraphMap.put(portShape, portKNode);
	}

	private void registerConnection(final Connection connection) {
		final KEdge edge = KimlUtil.createInitializedEdge();

		final Anchor sourceAnchor = connection.getStart();
		final Anchor targetAnchor = connection.getEnd();

		final KNode sourceNode = (KNode) peKGraphMap.get(sourceAnchor.getParent());
		KPort sourcePort = null;
		if (sourceAnchor instanceof FixPointAnchor) {
			sourcePort = (KPort) peKGraphMap.get(sourceAnchor);
		}

		final KNode targetNode = (KNode) peKGraphMap.get(targetAnchor.getParent());
		KPort targetPort = null;
		if (targetAnchor instanceof FixPointAnchor) {
			targetPort = (KPort) peKGraphMap.get(targetAnchor);
		}

		edge.setSource(sourceNode);
		edge.setTarget(targetNode);
		edge.setSourcePort(sourcePort);
		edge.setTargetPort(targetPort);

		peKGraphMap.put(connection, edge);
	}

	public void applyLayout() {

		for (Map.Entry<PictogramElement, KGraphElement> entry : peKGraphMap.entrySet()) {
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
