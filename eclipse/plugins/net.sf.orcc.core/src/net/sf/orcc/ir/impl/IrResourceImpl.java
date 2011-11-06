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

import net.sf.orcc.ir.Actor;

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
		if (uriFragment.startsWith("//@inputs.")
				&& !Character.isDigit(uriFragment.charAt(10))) {
			String name = uriFragment.substring(10);
			Actor actor = (Actor) getContents().get(0);
			return actor.getInput(name);
		} else if (uriFragment.startsWith("//@outputs.")
				&& !Character.isDigit(uriFragment.charAt(11))) {
			String name = uriFragment.substring(11);
			Actor actor = (Actor) getContents().get(0);
			return actor.getOutput(name);
		} else {
			return super.getEObject(uriFragment);
		}
	}

}
