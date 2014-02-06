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
package net.sf.orcc.xdf.ui.features;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Unit;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.xdf.ui.Activator;
import net.sf.orcc.xdf.ui.patterns.InstancePattern;
import net.sf.orcc.xdf.ui.util.ShapePropertiesManager;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * This special feature allow user to drag a file from the project explorer and
 * drop it directly in the diagram. An instance is created with a refinement
 * corresponding to the dropped entity. The user is automatically prompted for a
 * name to give to the instance newly created.
 * 
 * @author Antoine Lorence
 * 
 */
public class DropInstanceFromFileFeature extends AbstractAddFeature {

	public DropInstanceFromFileFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		
		final IFile file = (IFile) context.getNewObject();
		final String extension = file.getFileExtension();
		if (extension.equals(Activator.NETWORK_SUFFIX) || extension.equals(Activator.ACTOR_SUFFIX)) {

			final ContainerShape target = context.getTargetContainer();
			if (target == getDiagram()
					|| ShapePropertiesManager.isExpectedPc(target,
							InstancePattern.INSTANCE_ID)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public PictogramElement add(IAddContext context) {

		IFile file = (IFile) context.getNewObject();
		if (Activator.ACTOR_SUFFIX.equals(file.getFileExtension())) {
			file = OrccUtil.getFile(file.getProject(), OrccUtil.getQualifiedName(file), Activator.IR_SUFFIX);
		}

		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final Resource resource = getDiagramBehavior().getEditingDomain().getResourceSet().getResource(uri, true);
		// Sometimes, XdfParser fails for some reasons
		if (resource.getContents() == null) {
			MessageDialog.openWarning(XdfUtil.getDefaultShell(), "Warning", "This file has some errors. You can't use it here.");
			return null;
		} else if (resource.getContents().size() == 0) {
			MessageDialog.openWarning(XdfUtil.getDefaultShell(), "Warning",
					"No content found in this file, it is impossible to generate a refined Instance.");
			return null;
		}
		final EObject eobject = resource.getContents().get(0);

		if (eobject instanceof Unit) {
			MessageDialog.openWarning(XdfUtil.getDefaultShell(), "Warning", "You cannot drop a Unit here.");
			return null;
		}

		final ContainerShape target = context.getTargetContainer();
		if (target == getDiagram()) {
			return addToDiagram(eobject, context);
		} else if (ShapePropertiesManager.isExpectedPc(target,
				InstancePattern.INSTANCE_ID)) {
			return updateRefinement(target, eobject);
		}

		return null;
	}

	private PictogramElement addToDiagram(final EObject eobject,
			final IAddContext context) {

		final Instance instance = DfFactory.eINSTANCE.createInstance();
		final int objectCpt = getDiagram().getChildren().size() + 1;
		instance.setName("instance_" + objectCpt);
		instance.setEntity(eobject);

		final AddContext addInstanceContext = new AddContext(context, instance);
		final PictogramElement addedPe = getFeatureProvider().addIfPossible(addInstanceContext);

		getFeatureProvider().getDirectEditingInfo().setActive(true);
		return addedPe;
	}

	private PictogramElement updateRefinement(
			final ContainerShape instanceShape, final EObject refinement) {

		final InstancePattern pattern = (InstancePattern) ((IFeatureProviderWithPatterns) getFeatureProvider())
				.getPatternForPictogramElement(instanceShape);
		pattern.setInstanceRefinement(instanceShape, refinement);

		return instanceShape;

	}
}
