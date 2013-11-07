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

import net.sf.orcc.df.Port;
import net.sf.orcc.xdf.ui.features.UpdateDiagramFeature;
import net.sf.orcc.xdf.ui.patterns.NetworkPortPattern;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;

public class XdfDiagramToolBehaviorProvider extends DefaultToolBehaviorProvider {

	public XdfDiagramToolBehaviorProvider(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
	}

	@Override
	public GraphicsAlgorithm getSelectionBorder(PictogramElement pe) {

		IPattern ipattern = ((IFeatureProviderWithPatterns) getFeatureProvider()).getPatternForPictogramElement(pe);
		if (ipattern instanceof NetworkPortPattern) {
			NetworkPortPattern portPattern = (NetworkPortPattern) ipattern;
			return portPattern.getSelectionBorder(pe);
		}

		return super.getSelectionBorder(pe);
	}

	/**
	 * Graphiti use this method to check equality between 2 business objects.
	 * For example, it is used to retrieve PictogramElements linked (from a
	 * business object). The default implementation use EcoreUtil.equals(), but
	 * this method returns true if 2 different objects have exactly the same
	 * values for each attribute.
	 * 
	 * To avoid some bugs, we need to ensure comparison is done on objects
	 * reference in some cases. More information:
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=335828
	 * 
	 * @see UpdateDiagramFeature#initializeDiagramFromNetwork
	 */
	@Override
	public boolean equalsBusinessObjects(Object o1, Object o2) {
		if (o1 instanceof Port && o2 instanceof Port) {
			return o1 == o2;
		}
		return super.equalsBusinessObjects(o1, o2);
	}

}
