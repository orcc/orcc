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
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfUtil;
import net.sf.orcc.df.util.XdfParser;
import net.sf.orcc.df.util.XdfWriter;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
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

	/**
	 * Returns the connection of the given network that corresponds to the given
	 * fragment.
	 * 
	 * @param network
	 *            network
	 * @param fragment
	 *            a fragment of the form
	 *            "//@connections:source.port-target.port"
	 * @return a connection, or <code>null</code>
	 */
	private Connection getConnection(Network network, String fragment) {
		Vertex source, target;
		String sourcePort = null, targetPort = null;

		String arg = fragment.substring(12);
		int dot = arg.indexOf('.');
		int dash = arg.indexOf('-');
		if (dash < dot) {
			// first vertex is a port
			source = network.getInput(arg.substring(0, dash));
		} else {
			// first vertex is an instance
			source = network.getEntity(arg.substring(0, dot));
			sourcePort = arg.substring(dot + 1, dash);
		}

		dash++;
		dot = arg.indexOf('.', dash);
		if (dot == -1) {
			// second vertex is a port
			target = network.getOutput(arg.substring(dash));
		} else {
			// second vertex is an instance
			target = network.getEntity(arg.substring(dash, dot));
			targetPort = arg.substring(dot + 1);
		}

		for (Edge edge : source.getOutgoing()) {
			Connection connection = (Connection) edge;
			if (connection.getTarget() != target) {
				continue;
			}

			// if source port is not null, check connection's source port
			if (sourcePort != null) {
				Port port = connection.getSourcePort();
				if (port == null || !sourcePort.equals(port.getName())) {
					continue;
				}
			}

			// if target port is not null, check connection's target port
			if (targetPort != null) {
				Port port = connection.getTargetPort();
				if (port == null || !targetPort.equals(port.getName())) {
					continue;
				}
			}

			return connection;
		}

		return null;
	}

	@Override
	public EObject getEObject(String uriFragment) {
		if (getContents().isEmpty()) {
			return null;
		}

		if (uriFragment.length() < 4 || !uriFragment.startsWith("//@")) {
			return super.getEObject(uriFragment);
		}

		String fragment = uriFragment.substring(3);
		EObject root = getContents().get(0);
		if (root instanceof Network) {
			Network network = (Network) root;
			if (fragment.startsWith("connections:")) {
				return getConnection(network, fragment);
			} else {
				int index = fragment.lastIndexOf('.') + 1;
				if (!Character.isDigit(fragment.charAt(index))) {
					String name = fragment.substring(index);
					if (fragment.startsWith("inputs.")) {
						return network.getInput(name);
					} else if (fragment.startsWith("outputs.")) {
						return network.getOutput(name);
					} else if (fragment.startsWith("instances.")) {
						return network.getEntity(name);
					} else if (fragment.startsWith("parameters.")) {
						return network.getParameter(name);
					} else if (fragment.startsWith("variables.")) {
						return network.getVariable(name);
					}
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
		} else if (eObject instanceof Connection) {
			Connection connection = (Connection) eObject;
			StringBuilder builder = new StringBuilder("//@connections:");

			builder.append(connection.getSource().getLabel());
			Port sourcePort = connection.getSourcePort();
			if (sourcePort != null) {
				builder.append('.');
				builder.append(sourcePort.getName());
			}
			builder.append('-');

			builder.append(connection.getTarget().getLabel());
			Port targetPort = connection.getTargetPort();
			if (targetPort != null) {
				builder.append('.');
				builder.append(targetPort.getName());
			}

			return builder.toString();
		}

		// for other objects (types, expressions...)
		return super.getURIFragment(eObject);
	}

}
