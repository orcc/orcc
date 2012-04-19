/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfUtil;
import net.sf.orcc.df.util.XdfParser;
import net.sf.orcc.df.util.XdfWriter;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

/**
 * This class defines a resource implementation for the Df model which is used
 * to serialize to/deserialize from XDF.
 * 
 * @author Matthie Wipliez
 * 
 */
public class XdfResourceImpl extends ResourceImpl {

	public XdfResourceImpl() {
	}

	public XdfResourceImpl(URI uri) {
		super(uri);
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		try {
			Network network = new XdfParser(this).parseNetwork(inputStream);
			getContents().add(network);
		} catch (OrccRuntimeException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}

	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		try {
			Network network = (Network) getContents().get(0);
			new XdfWriter().write(network, outputStream);
		} catch (OrccRuntimeException e) {
			throw new IOException(e);
		}
	}

	@Override
	public EObject getEObject(String uriFragment) {
		if (getContents().isEmpty()) {
			return null;
		}

		if (uriFragment.length() == 0) {
			return super.getEObject(uriFragment);
		}

		EObject root = getContents().get(0);
		if (root instanceof Network) {
			Network network = (Network) root;
			int index = uriFragment.lastIndexOf('.') + 1;
			if (!Character.isDigit(uriFragment.charAt(index))) {
				String name = uriFragment.substring(index);
				if (uriFragment.startsWith("//@inputs.")) {
					return network.getInput(name);
				} else if (uriFragment.startsWith("//@outputs.")) {
					return network.getOutput(name);
				} else if (uriFragment.startsWith("//@instances.")) {
					return network.getInstance(name);
				} else if (uriFragment.startsWith("//@parameters.")) {
					return network.getParameter(name);
				} else if (uriFragment.startsWith("//@variables.")) {
					return network.getVariable(name);
				}
			}
		}

		return super.getEObject(uriFragment);
	}

	@Override
	public String getURIFragment(EObject eObject) {
		if (eObject instanceof Network) {
			// only one network per XdfResource
			return "/";
		} else if (eObject instanceof Instance) {
			Instance instance = (Instance) eObject;
			return "//@instances." + instance.getName();
		} else if (eObject instanceof Port) {
			Port port = (Port) eObject;
			if (DfUtil.isInput(port)) {
				return "//@inputs." + port.getName();
			} else {
				return "//@outputs." + port.getName();
			}
		} else if (eObject instanceof Var) {
			Var var = (Var) eObject;
			if (var.eContainingFeature() == DfPackage.Literals.NETWORK__PARAMETERS) {
				return "//@parameters." + var.getName();
			} else {
				return "//@variables." + var.getName();
			}
		}

		// for connection and other objects
		return super.getURIFragment(eObject);
	}

}
