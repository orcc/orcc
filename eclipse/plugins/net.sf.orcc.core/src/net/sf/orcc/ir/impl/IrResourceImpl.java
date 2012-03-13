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
package net.sf.orcc.ir.impl;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Unit;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * This class defines a resource implementation for the Df model which is used
 * to serialize to/deserialize from XDF.
 * 
 * @author mwipliez
 * 
 */
public class IrResourceImpl extends XMIResourceImpl {

	public IrResourceImpl() {
	}

	public IrResourceImpl(URI uri) {
		super(uri);

		setEncoding("UTF-8");

		getDefaultSaveOptions().put(
				XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		getDefaultSaveOptions().put(XMLResource.OPTION_LINE_WIDTH, 80);
		getDefaultSaveOptions().put(XMLResource.OPTION_URI_HANDLER,
				new URIHandlerImpl.PlatformSchemeAware());
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
		if (root instanceof Actor) {
			Actor actor = (Actor) root;
			int index = uriFragment.lastIndexOf('.') + 1;
			if (!Character.isDigit(uriFragment.charAt(index))) {
				String name = uriFragment.substring(index);
				if (uriFragment.startsWith("//@inputs.")) {
					return actor.getInput(name);
				} else if (uriFragment.startsWith("//@outputs.")) {
					return actor.getOutput(name);
				} else if (uriFragment.startsWith("//@parameters.")) {
					return actor.getParameter(name);
				} else if (uriFragment.startsWith("//@procedures.")) {
					return actor.getProcedure(name);
				} else if (uriFragment.startsWith("//@variables.")) {
					return actor.getStateVar(name);
				}
			}
		} else if (root instanceof Unit) {
			Unit unit = (Unit) root;
			int index = uriFragment.lastIndexOf('.') + 1;
			if (!Character.isDigit(uriFragment.charAt(index))) {
				String name = uriFragment.substring(index);
				if (uriFragment.startsWith("//@procedures.")) {
					return unit.getProcedure(name);
				} else if (uriFragment.startsWith("//@variables.")) {
					return unit.getConstant(name);
				}
			}
		}

		return super.getEObject(uriFragment);
	}

}
