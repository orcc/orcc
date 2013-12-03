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
package net.sf.orcc.xdf.ui.features;

import net.sf.orcc.xdf.ui.layout.XdfLayoutManager;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.ui.IWorkbenchPart;

import de.cau.cs.kieler.core.alg.BasicProgressMonitor;
import de.cau.cs.kieler.core.kgraph.KGraphElement;
import de.cau.cs.kieler.kiml.klayoutdata.KShapeLayout;
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
public class AutoLayoutFeature extends AbstractCustomFeature {

	private boolean hasDoneChanges;

	public AutoLayoutFeature(IFeatureProvider fp) {
		super(fp);
		hasDoneChanges = false;
	}

	@Override
	public String getDescription() {
		return "Layout diagram with Kieler Layouter"; //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return "&Layout Diagram (Kieler)"; //$NON-NLS-1$
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

		final XdfLayoutManager manager = new XdfLayoutManager();
		final LayoutMapping<PictogramElement> mapping = manager.buildLayoutGraph(wbPart, null);

		for (final KGraphElement graphElt : mapping.getGraphMap().keySet()) {
			final KShapeLayout shapeLayout = graphElt.getData(KShapeLayout.class);
			if (shapeLayout != null) {
				// Do not change ports position inside instance shapes
				shapeLayout.setProperty(LayoutOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);
			}
		}

		final LayeredLayoutProvider provider = new LayeredLayoutProvider();
		provider.doLayout(mapping.getLayoutGraph(), new BasicProgressMonitor());

		manager.applyLayout(mapping, false, 0);
		hasDoneChanges = true;
	}
}