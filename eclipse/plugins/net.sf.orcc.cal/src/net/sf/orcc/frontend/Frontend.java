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
 * This class defines an RVC-CAL front-end. Is is mainly used to manage links
 * between AST objects and their IR equivalent.
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
	 * <p>
	 * Returns the IR mapping equivalent of the AST object.
	 * </p>
	 * 
	 * <p>
	 * This method will first try to retrieve the IR object in cache. If it is
	 * impossible, a {@link StructTransformer} will be used to generate the IR
	 * equivalent of AST object. In that case, a warning will be displayed,
	 * since this situation should <b>not</b> happen.
	 * </p>
	 * 
	 * @param astObject
	 *            an AST object
	 * @return the IR equivalent of the given AST object
	 * @see Frontend#putMapping(EObject, EObject)
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> T getMapping(final EObject astObject) {
		EObject irObject = null;
		if (astObject.eResource() != null) {
			final Cache cache = CacheManager.instance.getCache(astObject
					.eResource());
			irObject = cache.getIrMap().get(astObject);
		}

		if (irObject == null) {
			OrccLogger.warnln("* " + astObject + " is missing from cache");
			// AST -> IR transformation. putMapping() is called in the
			// transformer, we don't need to call it now.
			irObject = new StructTransformer().doSwitch(astObject);
		}

		return (T) irObject;
	}

	/**
	 * <p>Store (in cache) a link between an AST object and its IR equivalent.</p>
	 * 
	 * <p>This mechanism avoid to transform twice the same object. It is also
	 * useful to set references to variables, procedures, functions, etc.
	 * References in AST model are kept in the IR model. It is mandatory to have
	 * a consistent model to avoid exception when the IR will be serialized.</p>
	 * 
	 * @param astObject
	 *            an AST object
	 * @param irObject
	 *            an equivalent IR object
	 * @see #getMapping(EObject)
	 */
	public void putMapping(final EObject astObject, final EObject irObject) {
		final Resource resource = astObject.eResource();
		if (resource != null) {
			Cache cache = CacheManager.instance.getCache(resource);
			cache.getIrMap().put(astObject, irObject);
		} else {
			OrccLogger.warnln("Try to put object not contained in a resource: "
					+ astObject);
		}
	}
}
