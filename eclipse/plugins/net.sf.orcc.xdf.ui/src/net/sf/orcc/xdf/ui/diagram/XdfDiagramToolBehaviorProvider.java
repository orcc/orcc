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

import net.sf.orcc.xdf.ui.patterns.NetworkPortPattern;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IContextButtonPadData;

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

	/**
	 * Display context menu button without "Remove" button
	 * 
	 * @param context
	 * @return
	 */
	@Override
	public IContextButtonPadData getContextButtonPad(IPictogramElementContext context) {
		final IContextButtonPadData ret = super.getContextButtonPad(context);
		// Do not display the Remove context button by default
		setGenericContextButtons(ret, context.getPictogramElement(), CONTEXT_BUTTON_DELETE | CONTEXT_BUTTON_UPDATE);
		return ret;
	}

}
