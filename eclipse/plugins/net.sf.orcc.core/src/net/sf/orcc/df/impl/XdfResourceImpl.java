/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.serialize.XDFParser;
import net.sf.orcc.df.serialize.XDFWriter;
import net.sf.orcc.ir.Actor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

/**
 * This class defines a resource implementation for the Df model which is used
 * to serialize to/deserialize from XDF.
 * 
 * @author mwipliez
 * 
 */
public class XdfResourceImpl extends ResourceImpl {

	public XdfResourceImpl() {
	}

	public XdfResourceImpl(URI uri) {
		super(uri);
	}

	@Override
	public EObject getEObject(String uriFragment) {
		EObject top = getContents().get(0);
		EObject result = null;
		if (uriFragment.startsWith("//@inputs.")) {
			String name = uriFragment.substring(10);
			if (top instanceof Actor) {
				result = ((Actor) top).getInput(name);
			} else if (top instanceof Network) {
				result = ((Network) top).getInput(name);
			}
		} else if (uriFragment.startsWith("//@outputs.")) {
			String name = uriFragment.substring(11);
			if (top instanceof Actor) {
				result = ((Actor) top).getOutput(name);
			} else if (top instanceof Network) {
				result = ((Network) top).getOutput(name);
			}
		}

		if (result != null) {
			return result;
		}
		return super.getEObject(uriFragment);
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		try {
			URI uri = getURI();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IFile file = root.getFile(new Path(uri.toPlatformString(true)));
			IProject project = file.getProject();
			Network network = new XDFParser()
					.parseNetwork(project, inputStream);
			getContents().add(network);
		} catch (OrccRuntimeException e) {
			throw new IOException(e);
		}
	}

	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		try {
			Network network = (Network) getContents().get(0);
			new XDFWriter().write(network, outputStream);
		} catch (OrccRuntimeException e) {
			throw new IOException(e);
		}
	}

}
