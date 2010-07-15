/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.naming;

import java.util.Collections;
import java.util.Iterator;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.CalPackage;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.SimpleAttributeResolver;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * This class defines a qualified name provider for RVC-CAL.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalQualifiedNameProvider extends
		IQualifiedNameProvider.AbstractImpl {

	private int blockCount;

	@Inject
	private IResourceScopeCache cache = IResourceScopeCache.NullImpl.INSTANCE;

	private PolymorphicDispatcher<String> qualifiedName = new PolymorphicDispatcher<String>(
			"qualifiedName", 1, 1, Collections.singletonList(this),
			PolymorphicDispatcher.NullErrorHandler.<String> get()) {
		@Override
		protected String handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	private SimpleAttributeResolver<EObject, String> resolver = SimpleAttributeResolver
			.newResolver(String.class, "name");

	private int untaggedCount;

	public String getDelimiter() {
		return ".";
	}

	public String getQualifiedName(final EObject obj) {
		return cache.get(Tuples.pair(obj, "fqn"), obj.eResource(),
				new Provider<String>() {

					public String get() {
						EObject temp = obj;
						String name = qualifiedName.invoke(temp);
						if (name != null)
							return name;
						String value = resolver.getValue(temp);
						if (value == null)
							return null;
						while (temp.eContainer() != null) {
							temp = temp.eContainer();
							if (CalPackage.eINSTANCE.getAstAction().isInstance(
									temp)) {
								return value;
							}

							String parentsName = getQualifiedName(temp);
							if (parentsName != null)
								return parentsName + getDelimiter() + value;
						}
						return value;
					}

				});

	}

	protected SimpleAttributeResolver<EObject, String> getResolver() {
		return resolver;
	}

	public String getWildcard() {
		return "*";
	}

	/**
	 * Resets the block count and returns the qualified name of the tag of the
	 * given action.
	 * 
	 * @param action
	 *            an action
	 * @return the action's tag qualified name
	 */
	public String qualifiedName(AstAction action) {
		AstTag tag = action.getTag();
		if (tag == null) {
			return "untagged_" + untaggedCount++;
		} else {
			return getQualifiedName(tag);
		}
	}

	/**
	 * Resets the untagged action count and returns the name of the given actor.
	 * 
	 * @param actor
	 *            an actor
	 * @return the actor name
	 */
	public String qualifiedName(AstActor actor) {
		return actor.getName();
	}

	public String qualifiedName(AstGenerator generator) {
		return "generator." + blockCount++;
	}

	public String qualifiedName(AstStatementForeach foreach) {
		return "foreach." + blockCount++;
	}

	public String qualifiedName(AstTag tag) {
		Iterator<String> it = tag.getIdentifiers().iterator();
		StringBuilder builder = new StringBuilder();
		if (it.hasNext()) {
			builder.append(it.next());
			while (it.hasNext()) {
				builder.append('.');
				builder.append(it.next());
			}
		}

		return builder.toString();
	}

	/**
	 * Resets the block count.
	 */
	public void resetBlockCount() {
		blockCount = 0;
	}

	/**
	 * Resets the untagged action count.
	 */
	public void resetUntaggedCount() {
		untaggedCount = 0;
	}

}
