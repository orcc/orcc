/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.ui.builder;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.frontend.Frontend;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;

/**
 * This class defines an actor builder invoked by Xtext. The class is referenced
 * by an extension point in the plugin.xml as an Xtext builder participant.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActorBuilder implements IXtextBuilderParticipant {

	@Override
	public void build(IBuildContext context, IProgressMonitor monitor)
			throws CoreException {
		IFolder folder = context.getBuiltProject().getFolder(".generated");
		if (!folder.exists()) {
			folder.create(true, true, null);
		}

		String outputFolder = folder.getLocation().toOSString();
		Frontend frontend = new Frontend(outputFolder);

		ResourceSet set = context.getResourceSet();
		for (Resource resource : set.getResources()) {
			List<EObject> contents = resource.getContents();
			Iterator<EObject> it = contents.iterator();
			if (it.hasNext()) {
				EObject obj = it.next();
				if (obj instanceof AstActor) {
					AstActor actor = (AstActor) obj;

					try {
						URL resourceUrl = new URL(resource.getURI().toString());
						URL url = FileLocator.toFileURL(resourceUrl);
						IPath path = new Path(url.getPath());
						String file = path.toOSString();

						frontend.compile(file, actor);
					} catch (IOException e) {
						IStatus status = new Status(IStatus.ERROR,
								"net.sf.orcc.cal.ui",
								"could not generate code for "
										+ actor.getName(), e);
						throw new CoreException(status);
					} catch (OrccException e) {
						IStatus status = new Status(IStatus.ERROR,
								"net.sf.orcc.cal.ui",
								"could not generate code for "
										+ actor.getName(), e);
						throw new CoreException(status);
					}
				}
			}
		}
	}

}
