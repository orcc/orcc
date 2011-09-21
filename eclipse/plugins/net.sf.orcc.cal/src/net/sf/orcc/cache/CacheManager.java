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
package net.sf.orcc.cache;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Switch;

/**
 * This class defines a cache manager, that is responsible of loading caches
 * on-the-fly.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface CacheManager extends EObject {

	/**
	 * the instance of this cache manager
	 */
	static final CacheManager instance = CacheFactory.eINSTANCE
			.createCacheManager();

	/**
	 * Returns the cache associated with the given resource URI.
	 * 
	 * @param uri
	 *            URI of a resource
	 * @return the cache associated with the given resource URI
	 */
	Cache getCache(URI uri);

	/**
	 * Returns an object of type T associated with the source object of type F.
	 * If the object is in cache, retrieve it, otherwise compute it using the
	 * given function.
	 * 
	 * @param <F>
	 *            the type of the function input
	 * @param <T>
	 *            the type of the function output
	 * @param eObject
	 *            source object
	 * @param switchInst
	 *            an instance of a Switch to compute the object if necessary
	 * @param featureMap
	 *            the feature that returns a map to get/put the object
	 * @param featureList
	 *            the feature that returns a list to add the returned object to
	 *            (possibly <code>null</code>)
	 */
	<F extends EObject, T> T getOrCompute(F eObject, Switch<T> switchInst,
			EStructuralFeature featureMap, EStructuralFeature featureList);

	/**
	 * Returns the resource set used by this cache.
	 * 
	 * @return the resource set used by this cache
	 */
	ResourceSet getResourceSet();

	/**
	 * Removes the cache associated with the given resource URI.
	 * 
	 * @param uri
	 *            URI of a resource
	 */
	void removeCache(URI uri);

	/**
	 * Saves the cache associated with the given resource URI.
	 * 
	 * @param uri
	 *            URI of a resource
	 */
	void saveCache(URI uri);

	/**
	 * Unloads all caches (the underlying resources are NOT removed).
	 */
	void unloadAllCaches();

}
