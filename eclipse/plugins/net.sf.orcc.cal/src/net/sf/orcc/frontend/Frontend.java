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

import java.util.List;

import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cache.CachePackage;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Unit;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.xtext.EcoreUtil2;

/**
 * This class defines an RVC-CAL front-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Frontend {

	public static final Frontend instance = new Frontend();

	/**
	 * Returns the IR of the given AST entity. If it does not exist, creates it.
	 * 
	 * @param entity
	 *            an AST entity
	 * @return the IR of the given AST entity
	 */
	public static EObject getEntity(AstEntity entity) {
		AstActor actor = entity.getActor();
		EObject eObject;
		Switch<? extends EObject> emfSwitch;
		if (actor == null) {
			eObject = entity.getUnit();
			emfSwitch = new UnitTransformer();
		} else {
			eObject = actor;
			emfSwitch = new ActorTransformer();
		}

		return CacheManager.instance.getOrCompute(eObject, emfSwitch,
				CachePackage.eINSTANCE.getCache_IrMap());
	}

	/**
	 * Returns the IR equivalent of the AST object.
	 * 
	 * @param eObject
	 *            an AST object
	 * @return the IR equivalent of the AST object
	 */
	public static <T extends EObject> T getMapping(EObject eObject) {
		return getMapping(eObject, true);
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
	public static <T extends EObject> T getMapping(EObject eObject,
			boolean require) {
		if (require) {
			AstEntity entity = EcoreUtil2.getContainerOfType(eObject,
					AstEntity.class);
			getEntity(entity);
		}

		// no need to put the mapping back because the AstTransformer does it
		// that's also why we don't use getOrCompute
		EObject irObject = internalGetMapping(eObject);
		if (irObject == null) {
			irObject = new StructTransformer().doSwitch(eObject);
		}

		return (T) irObject;
	}

	public static List<Procedure> getProcedures(AstEntity astEntity) {
		EObject entity = getEntity(astEntity);
		if (entity instanceof Actor) {
			return ((Actor) entity).getProcs();
		} else if (entity instanceof Unit) {
			return ((Unit) entity).getProcedures();
		} else {
			return null;
		}
	}

	/**
	 * Returns the IR equivalent of the given AST object using its URI.
	 * 
	 * @param eObject
	 *            an AST object
	 * @return the IR equivalent of the given object
	 */
	private static EObject internalGetMapping(EObject eObject) {
		Resource resource = eObject.eResource();
		if (resource != null) {
			Cache cache = CacheManager.instance.getCache(resource);
			return cache.getIrMap().get(eObject);
		}

		return null;
	}

	/**
	 * Returns the IR equivalent of the given AST object using its URI.
	 * 
	 * @param eObject
	 *            an AST object
	 * @return the IR equivalent of the given object
	 */
	public static void putMapping(EObject astObject, EObject irObject) {
		Resource resource = astObject.eResource();
		if (resource != null) {
			Cache cache = CacheManager.instance.getCache(resource);
			cache.getIrMap().put(astObject, irObject);
		}
	}

	private final ResourceSet set = new ResourceSetImpl();

	public ResourceSet getResourceSet() {
		return set;
	}

	/**
	 * Serializes the given actor or unit.
	 * 
	 * @param eObject
	 *            an actor or unit
	 */
	public void serialize(EObject eObject) {
		IrUtil.serializeActor(set, eObject);
	}
}
