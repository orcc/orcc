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

import net.sf.orcc.xdf.ui.features.DropInstanceFromFileFeature;
import net.sf.orcc.xdf.ui.features.UpdateDiagramFeature;
import net.sf.orcc.xdf.ui.features.UpdateRefinmentFeature;
import net.sf.orcc.xdf.ui.layout.OrthogonalAutoLayoutFeature;
import net.sf.orcc.xdf.ui.layout.PolylineAutoLayoutFeature;
import net.sf.orcc.xdf.ui.patterns.ConnectionPattern;
import net.sf.orcc.xdf.ui.patterns.InputNetworkPortPattern;
import net.sf.orcc.xdf.ui.patterns.InstancePattern;
import net.sf.orcc.xdf.ui.patterns.OutputNetworkPortPattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.IMoveAnchorFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IMoveAnchorContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.DefaultMoveAnchorFeature;
import org.eclipse.graphiti.pattern.DefaultFeatureProviderWithPatterns;

/**
 * This is a default class, from a new Graphiti project. It should be modified
 * to fit our needs.
 * 
 * @author Antoine Lorence
 * 
 */
public class XdfDiagramFeatureProvider extends
		DefaultFeatureProviderWithPatterns {

	public XdfDiagramFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		addPattern(new InstancePattern());
		addPattern(new InputNetworkPortPattern());
		addPattern(new OutputNetworkPortPattern());
		addConnectionPattern(new ConnectionPattern());
	}
	
	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		return new ICustomFeature[] { new UpdateRefinmentFeature(this), new OrthogonalAutoLayoutFeature(this),
				new PolylineAutoLayoutFeature(this) };
	}

	@Override
	protected IUpdateFeature getUpdateFeatureAdditional(IUpdateContext context) {
		return new UpdateDiagramFeature(this);
	}

	/**
	 * Forbids to move any anchor in the diagram
	 */
	@Override
	public IMoveAnchorFeature getMoveAnchorFeature(IMoveAnchorContext context) {
		return new DefaultMoveAnchorFeature(this) {
			@Override
			public boolean canMoveAnchor(IMoveAnchorContext context) {
				return false;
			}
		};
	}

	@Override
	public IFeature[] getDragAndDropFeatures(IPictogramElementContext context) {
		return getCreateConnectionFeatures();
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		if (context.getNewObject() instanceof IFile) {
			return new DropInstanceFromFileFeature(this);
		}
		return super.getAddFeature(context);
	}
}
