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

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * This feature is used to open an Actor or an Instance in a new tab when user
 * perform a double click on a refined instance.
 * 
 * @author Antoine Lorence
 * 
 */
public class InstanceDblClickFeature extends AbstractCustomFeature {

	public InstanceDblClickFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		if (context.getPictogramElements().length == 1) {
			final PictogramElement pe = context.getPictogramElements()[0];
			final EObject eobject = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
			if (eobject instanceof Instance) {
				return ((Instance) eobject).getEntity() != null;
			}
		}
		return super.canExecute(context);
	}

	@Override
	public void execute(ICustomContext context) {
		final PictogramElement pe = context.getPictogramElements()[0];
		final Instance instance = (Instance) Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);

		final EObject entity = instance.getEntity();
		if (entity == null) {
			OrccLogger.warnln("No entity to open");
			return;
		}
		
		IFile file;
		if (entity instanceof Actor) {
			file = ((Actor) entity).getFile();
		} else {
			file = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(new Path(entity.eResource().getURI().toPlatformString(true)));
		}
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		try {
			IDE.openEditor(page, file, true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return false;
	}
}
