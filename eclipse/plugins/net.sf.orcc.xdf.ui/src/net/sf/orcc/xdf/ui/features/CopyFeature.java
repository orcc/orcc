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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICopyContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractCopyFeature;

/**
 * Implements the ability to copy/cut objects from diagram.
 * 
 * @author Antoine Lorence
 * 
 */
public class CopyFeature extends AbstractCopyFeature {

	public CopyFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canCopy(ICopyContext context) {
		final PictogramElement[] pes = context.getPictogramElements();
		if (pes == null || pes.length == 0) {
			// Nothing selected
			return false;
		}

		// We can copy anything
		return true;
	}

	@Override
	public void copy(ICopyContext context) {
		final List<Vertex> vertices = new ArrayList<Vertex>();
		for (final PictogramElement pe : context.getPictogramElements()) {
			final Object businessObject = getBusinessObjectForPictogramElement(pe);
			if (businessObject instanceof Instance
					|| businessObject instanceof Port) {
				vertices.add((Vertex) businessObject);
			}
		}
		// Store selected business objects into clipboard
		putToClipboard(vertices.toArray());
	}

}
