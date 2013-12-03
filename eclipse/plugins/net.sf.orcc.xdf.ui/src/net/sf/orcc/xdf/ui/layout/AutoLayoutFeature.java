/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package net.sf.orcc.xdf.ui.layout;

import net.sf.orcc.xdf.ui.patterns.InstancePattern;
import net.sf.orcc.xdf.ui.patterns.NetworkPortPattern;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.ui.IWorkbenchPart;

import com.google.common.collect.BiMap;

import de.cau.cs.kieler.core.alg.BasicProgressMonitor;
import de.cau.cs.kieler.core.kgraph.KGraphElement;
import de.cau.cs.kieler.core.kgraph.KNode;
import de.cau.cs.kieler.kiml.graphiti.GraphitiDiagramLayoutManager;
import de.cau.cs.kieler.kiml.klayoutdata.KShapeLayout;
import de.cau.cs.kieler.kiml.options.EdgeRouting;
import de.cau.cs.kieler.kiml.options.LayoutOptions;
import de.cau.cs.kieler.kiml.options.PortConstraints;
import de.cau.cs.kieler.kiml.ui.diagram.LayoutMapping;
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
		@SuppressWarnings("deprecation")
		final IWorkbenchPart wbPart = (IWorkbenchPart) getDiagramEditor();

		final GraphitiDiagramLayoutManager manager = new GraphitiDiagramLayoutManager();
		final LayoutMapping<PictogramElement> mapping = manager.buildLayoutGraph(wbPart, null);

		preFixDiagramMapping(mapping);

		final LayeredLayoutProvider provider = new LayeredLayoutProvider();
		provider.doLayout(mapping.getLayoutGraph(), new BasicProgressMonitor());

		manager.applyLayout(mapping, false, 0);
		hasDoneChanges = true;
	}

	private void preFixDiagramMapping(final LayoutMapping<PictogramElement> mapping) {

		final BiMap<PictogramElement, KGraphElement> invMap = mapping.getGraphMap().inverse();

		IPattern pattern;
		for (final Shape shape : getDiagram().getChildren()) {
			pattern = ((IFeatureProviderWithPatterns) getFeatureProvider()).getPatternForPictogramElement(shape);
			if (pattern instanceof NetworkPortPattern) {
				preFixNetworkPortNode((KNode) invMap.get(shape));
			} else if (pattern instanceof InstancePattern) {
				preFixInstanceNode((KNode) invMap.get(shape));
			}
		}

		final KShapeLayout mappingShapeLayout = mapping.getLayoutGraph().getData(KShapeLayout.class);
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
}




