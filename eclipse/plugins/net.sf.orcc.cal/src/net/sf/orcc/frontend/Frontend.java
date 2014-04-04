/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.frontend;

import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This class defines an RVC-CAL front-end.
 * 
 * @author Matthieu Wipliez
 * @author Antoine Lorence
 * 
 */
public class Frontend {

	public static final Frontend instance = new Frontend();

	/**
	 * Set constructor to private, to ensure it will always used as Singleton
	 */
	private Frontend() {
	}

	/**
	 * Returns the IR mapping equivalent of the AST object. If
	 * <code>require</code> is <code>true</code>, first make sure that the IR of
	 * the given AST object's containing entity exists.
	 * 
	 * @param eObject
	 *            an AST object
	 * @param require
	 *            if <code>true</code>, first get the IR of the object's
	 *            containing entity
	 * @return the IR equivalent of the AST object
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> T getMapping(EObject eObject) {
		// no need to put the mapping back because the AstTransformer does it
		// that's also why we don't use getOrCompute
		EObject irObject = null;
		if (eObject.eResource() != null) {
			final Cache cache = CacheManager.instance.getCache(eObject
					.eResource());
			irObject = cache.getIrMap().get(eObject);
		}

		if (irObject == null) {
			OrccLogger.warnln("* " + eObject + " is missing from cache");
			irObject = new StructTransformer().doSwitch(eObject);
		}

		return (T) irObject;
	}

	/**
	 * Returns the IR equivalent of the given AST object using its URI.
	 * 
	 * @param eObject
	 *            an AST object
	 * @return the IR equivalent of the given object
	 */
	public void putMapping(EObject astObject, EObject irObject) {
		Resource resource = astObject.eResource();
		if (resource != null) {
			Cache cache = CacheManager.instance.getCache(resource);
			cache.getIrMap().put(astObject, irObject);
		} else {
			OrccLogger.warnln("Try to put object not contained in a resource: "
					+ astObject);
		}
	}
}
