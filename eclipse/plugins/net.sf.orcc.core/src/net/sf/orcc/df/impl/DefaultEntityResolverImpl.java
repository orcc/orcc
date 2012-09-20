/*
 * Copyright (c) 2012, Synflow
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
package net.sf.orcc.df.impl;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.EntityResolver;
import net.sf.orcc.df.Instance;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This class is a default implementation of an entity resolver. This class may
 * serve as a base to other implementations of entity resolver.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DefaultEntityResolverImpl implements EntityResolver {

	protected IProject project;

	@Override
	public void initialize(Resource resource) {
		// cache project in which this resource is located
		URI uri = resource.getURI();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String name = new Path(uri.toPlatformString(true)).segment(0);
		project = root.getProject(name);
	}

	@Override
	public boolean resolve(Instance instance, String className) {
		EObject proxy = null;
		IFile file = OrccUtil.getFile(project, className, "ir");
		if (file != null && file.exists()) {
			proxy = DfFactory.eINSTANCE.createActor();
		} else {
			file = OrccUtil.getFile(project, className, "xdf");
			if (file != null && file.exists()) {
				proxy = DfFactory.eINSTANCE.createNetwork();
			} else {
				return false;
			}
		}

		// create proxy
		String pathName = file.getFullPath().toString();
		URI uri = URI.createPlatformResourceURI(pathName, true);
		uri = uri.appendFragment("/0");
		instance.setEntity(proxy);
		((InternalEObject) proxy).eSetProxyURI(uri);

		return true;
	}

}
