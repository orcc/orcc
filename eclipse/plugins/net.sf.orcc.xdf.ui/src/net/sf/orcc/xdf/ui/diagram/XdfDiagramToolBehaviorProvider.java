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
package net.sf.orcc.xdf.ui.diagram;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.xdf.ui.features.GroupInstancesFeature;
import net.sf.orcc.xdf.ui.features.InstanceDblClickFeature;
import net.sf.orcc.xdf.ui.features.OpenPropertiesFeature;
import net.sf.orcc.xdf.ui.features.UngroupNetworkFeature;
import net.sf.orcc.xdf.ui.features.UpdateRefinementFeature;
import net.sf.orcc.xdf.ui.patterns.InputNetworkPortPattern;
import net.sf.orcc.xdf.ui.patterns.NetworkPortPattern;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.tb.ContextButtonEntry;
import org.eclipse.graphiti.tb.ContextMenuEntry;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IContextButtonPadData;
import org.eclipse.graphiti.tb.IContextMenuEntry;

/**
 * Define some hacks to customize the way Graphiti works in general.
 * 
 * @author Antoine Lorence
 * 
 */
public class XdfDiagramToolBehaviorProvider extends DefaultToolBehaviorProvider {

	public XdfDiagramToolBehaviorProvider(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
	}

	/**
	 * Change the selection border of a shape.
	 * 
	 * For a Network port, the selection must be displayed on the inner polygon.
	 * We don't want to customize getClickArea(), because the default
	 * implementation (everything inside the invisible rectangle) fits to our
	 * needs.
	 * 
	 * For an Instance port, we want to select the whole instance when a user
	 * click on a port shape.
	 */
	@Override
	public GraphicsAlgorithm getSelectionBorder(PictogramElement pe) {
		final IPattern ipattern = ((IFeatureProviderWithPatterns) getFeatureProvider())
				.getPatternForPictogramElement(pe);
		if (ipattern instanceof NetworkPortPattern) {
			final NetworkPortPattern portPattern = (NetworkPortPattern) ipattern;
			return portPattern.getSelectionBorder(pe);
		}

		return super.getSelectionBorder(pe);
	}

	@Override
	public ICustomFeature getDoubleClickFeature(IDoubleClickContext context) {
		return new InstanceDblClickFeature(getFeatureProvider());
	}

	@Override
	public IContextButtonPadData getContextButtonPad(
			IPictogramElementContext context) {

		final IContextButtonPadData data = super.getContextButtonPad(context);
		final PictogramElement pe = context.getPictogramElement();
		final IPattern pattern = ((IFeatureProviderWithPatterns) getFeatureProvider())
				.getPatternForPictogramElement(pe);

		// We add a new button only on network ports
		if (pattern instanceof InputNetworkPortPattern) {
			final Anchor anchor = ((NetworkPortPattern) pattern)
					.getAnchor((AnchorContainer) pe);

			// Initialize the context for connection creation
			final CreateConnectionContext ccc = new CreateConnectionContext();
			ccc.setSourcePictogramElement(pe);
			ccc.setSourceAnchor(anchor);

			// Create the context button entry
			final ContextButtonEntry button = new ContextButtonEntry(null,
					context);
			button.setText("Start connection");
			button.setDescription("Drag to the target port to create a new connection");
			button.setIconId(XdfImageProvider.CONNECTION);

			// Add the create connection feature as D&D action on the button
			button.addDragAndDropFeature(getFeatureProvider()
					.getCreateConnectionFeatures()[0]);

			// Add button to icons displayed on overlay
			data.getDomainSpecificContextButtons().add(button);
		}

		return data;
	}

	/**
	 * Customize context menu. CustomFeatures are created and arranged here.
	 */
	@Override
	public IContextMenuEntry[] getContextMenu(ICustomContext context) {
		final List<IContextMenuEntry> contextMenuEntries = new ArrayList<IContextMenuEntry>();
		
		ContextMenuEntry entry;

		// 'Set/Update Refinement' menu entry
		entry = new ContextMenuEntry(new UpdateRefinementFeature(getFeatureProvider()), context);
		contextMenuEntries.add(entry);
		
		// 'Layout Diagram' menu entry
		entry = new ContextMenuEntry(null, context);
		entry.setText("Layout Diagram");

		final ICustomFeature[] layoutFeatures = ((XdfDiagramFeatureProvider) getFeatureProvider())
				.getLayoutFeatures();
		for (final ICustomFeature layoutFeature : layoutFeatures) {
			entry.add(new ContextMenuEntry(layoutFeature, context));
		}
		contextMenuEntries.add(entry);
		
		// 'Transformations' menu entry
		entry = new ContextMenuEntry(null, context);
		entry.setText("Transformations");
		entry.add(new ContextMenuEntry(new GroupInstancesFeature(getFeatureProvider()), context));
		entry.add(new ContextMenuEntry(new UngroupNetworkFeature(getFeatureProvider()), context));
		contextMenuEntries.add(entry);

		entry = new ContextMenuEntry(new OpenPropertiesFeature(
				getFeatureProvider()), context);
		contextMenuEntries.add(entry);

		return contextMenuEntries.toArray(NO_CONTEXT_MENU_ENTRIES);
	}
}
