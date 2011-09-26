/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Entity;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Unit;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines an RVC-CAL front-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Frontend {

	public static final Frontend instance = new Frontend();

	public static Actor getActor(AstActor actor) {
		return (Actor) CacheManager.instance.getOrCompute(actor,
				new ActorTransformer(),
				CachePackage.eINSTANCE.getCache_IrMap(), null);
	}

	public static Entity getEntity(AstEntity entity) {
		AstActor actor = entity.getActor();
		if (actor == null) {
			return getUnit(entity.getUnit());
		} else {
			return getActor(actor);
		}
	}

	/**
	 * Returns the IR equivalent of the given AST object using its URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the IR equivalent of the given object
	 */
	public static EObject getMapping(EObject eObject) {
		Resource resource = eObject.eResource();
		if (resource != null) {
			Cache cache = CacheManager.instance.getCache(resource.getURI());

			URI uri = EcoreUtil.getURI(eObject);
			String fragment = uri.fragment();
			return cache.getIrMap().get(fragment);
		}

		return null;
	}

	public static List<Procedure> getProcedures(EObject cter) {
		if (cter instanceof AstEntity) {
			Entity entity = getEntity((AstEntity) cter);
			if (entity instanceof Actor) {
				return ((Actor) entity).getProcs();
			} else if (entity instanceof Unit) {
				return ((Unit) entity).getProcedures();
			}
		}

		return null;
	}

	public static Unit getUnit(AstUnit unit) {
		return (Unit) CacheManager.instance.getOrCompute(unit,
				new UnitTransformer(), CachePackage.eINSTANCE.getCache_IrMap(),
				null);
	}

	/**
	 * Returns the IR equivalent of the given AST object using its URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the IR equivalent of the given object
	 */
	public static void putMapping(EObject astObject, EObject irObject) {
		Resource resource = astObject.eResource();
		if (resource != null) {
			Cache cache = CacheManager.instance.getCache(resource.getURI());

			URI uri = EcoreUtil.getURI(astObject);
			String fragment = uri.fragment();
			cache.getIrMap().put(fragment, irObject);
		}
	}

	private IFolder outputFolder;

	private final ResourceSet set = CacheManager.instance.getResourceSet();

	/**
	 * Warning: dirty hack! Removes uses that were created by the front-end but
	 * subsequently ignored.
	 * <p>
	 * These uses are called "dangling" because they cannot be reached from the
	 * top-level container (i.e. actor) through containment relations. Note that
	 * as such, using actor.eAllContents() does NOT return these uses, which is
	 * why this method is implemented as visiting the variables and their uses
	 * rather than by calling the EcoreHelper.getUses method.
	 * </p>
	 * <p>
	 * Removing allows IR actors to be serialized without errors. A long term
	 * solution would probably be to write a cleaner AST to IR translator.
	 * </p>
	 * 
	 * @param actor
	 *            the IR of an actor
	 */
	public void removeDanglingUses(Actor actor) {
		TreeIterator<EObject> it = actor.eAllContents();
		while (it.hasNext()) {
			EObject eObject = it.next();
			if (eObject instanceof Var) {
				Var var = (Var) eObject;
				for (int i = 0; i < var.getUses().size(); i++) {
					Use use = var.getUses().get(i);
					if (!EcoreUtil.isAncestor(actor, use)) {
						use.setVariable(null);
						i--;
					}
				}
			}
		}
	}

	/**
	 * Serializes the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public void serialize(Entity entity) {
		IrUtil.serializeActor(set, outputFolder, entity);
	}

	public void setOutputFolder(IFolder outputFolder) {
		this.outputFolder = outputFolder;
	}

}
