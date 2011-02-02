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

import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.util.OrccUtil;

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

	public String getDelimiter() {
		return ".";
	}

	public String getQualifiedName(final EObject obj) {
		return cache.get(Tuples.pair(obj, "fqn"), obj.eResource(),
				new Provider<String>() {

					public String get() {
						EObject temp = obj;
						String name = qualifiedName.invoke(temp);
						if (name != null) {
							return name;
						}

						AstEntity entity = null;
						if (temp instanceof AstEntity) {
							// return qualified name of entity
							entity = (AstEntity) temp;
						} else if (temp.eContainer() instanceof AstEntity) {
							// return qualified name of entity
							entity = (AstEntity) temp.eContainer();
						}

						if (entity == null) {
							// object inside an entity
							EObject cter = temp.eContainer();
							if (cter instanceof AstUnit) {
								String parentsName = getQualifiedName(cter);
								String value = resolver.getValue(temp);
								if (value != null) {
									return parentsName + getDelimiter() + value;
								}
							}
						} else {
							return Util.getQualifiedName(entity);
						}

						return null;
					}

				});

	}

	protected SimpleAttributeResolver<EObject, String> getResolver() {
		return resolver;
	}

	public String getWildcard() {
		return "*";
	}

	public String qualifiedName(AstTag tag) {
		return OrccUtil.toString(tag.getIdentifiers(), ".");
	}

}
