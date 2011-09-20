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

import net.sf.orcc.OrccException;
import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Entity;
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

	private final IFolder outputFolder;

	private final ResourceSet set;

	public Frontend(IFolder outputFolder) {
		this.outputFolder = outputFolder;
		set = CacheManager.instance.getResourceSet();
	}

	/**
	 * Compiles the given actor which is defined in the given file, and writes
	 * IR to the output folder defined by {@link #setOutputFolder(String)}.
	 * <p>
	 * Note that callers of this method must ensure that the actor has no errors
	 * for it to be properly compiled.
	 * </p>
	 * 
	 * @param file
	 *            name of the file where the actor is defined
	 * @param astActor
	 *            AST of the actor
	 * @throws OrccException
	 */
	public Entity compile(AstEntity entity) {
		AstActor astActor = entity.getActor();
		if (astActor != null) {
			Actor actor = (Actor) getMapping(astActor);
			if (actor != null) {
				return actor;
			}

			ActorTransformer transformer = new ActorTransformer();
			actor = transformer.transform(this, astActor);
			putMapping(astActor, actor);
			removeDanglingUses(actor);
			IrUtil.serializeActor(set, outputFolder, actor);
			return actor;
		} else {
			AstUnit astUnit = entity.getUnit();

			Unit unit = (Unit) getMapping(astUnit);
			if (unit != null) {
				return unit;
			}

			UnitTransformer transformer = new UnitTransformer();
			unit = transformer.transform(this, astUnit);
			putMapping(astUnit, unit);
			IrUtil.serializeActor(set, outputFolder, unit);
			return unit;
		}
	}

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
	private void removeDanglingUses(Actor actor) {
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

}
