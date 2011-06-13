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
package net.sf.orcc.cal;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstOutputPattern;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.expression.AstExpressionEvaluator;
import net.sf.orcc.cal.type.TypeTransformer;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * This class defines a CAL resource description that returns a set of exported
 * objects that only contains the actor and its ports.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalResourceDescription extends DefaultResourceDescriptionStrategy {

	public static final String KEY_INT_VALUE = "intValue";

	public static final String KEY_TYPE = "type";

	@Inject
	private IResourceDescription.Manager manager;

	@Inject
	public IResourceScopeCache cache;

	public static CalResourceDescription instance;

	public CalResourceDescription() {
		instance = this;
	}

	@Override
	public boolean createEObjectDescriptions(EObject eObject,
			IAcceptor<IEObjectDescription> acceptor) {
		if (getQualifiedNameProvider() == null) {
			return false;
		} else {
			if (Util.getNameProvider() == null) {
				Util.setManager(manager);
				Util.setNameProvider(getQualifiedNameProvider());
			}
		}

		try {
			QualifiedName qualifiedName = getQualifiedNameProvider()
					.getFullyQualifiedName(eObject);
			if (qualifiedName != null) {
				Map<String, String> userData = new HashMap<String, String>(0);

				// for expressions (because class is a subclass of
				// AST_EXPRESSION)
				if (eObject instanceof AstExpression) {
					if (eObject.eContainer() instanceof AstGenerator) {
						// store value of bounds of generator
						createInt(userData, (AstExpression) eObject);
					}
					createType(userData, eObject);
				}

				// for functions, ports, variables, and patterns
				switch (eObject.eClass().getClassifierID()) {
				case CalPackage.AST_FUNCTION:
				case CalPackage.AST_PORT:
				case CalPackage.AST_VARIABLE:
					createType(userData, eObject);
					break;

				case CalPackage.AST_INPUT_PATTERN:
					createInt(userData, ((AstInputPattern) eObject).getRepeat());
					break;

				case CalPackage.AST_OUTPUT_PATTERN:
					createInt(userData,
							((AstOutputPattern) eObject).getRepeat());
					break;
				}

				// create IEObjectDescription
				final IEObjectDescription desc = EObjectDescription.create(
						qualifiedName, eObject, userData);
				acceptor.accept(desc);

				// add to cache
				cache.get(eObject, eObject.eResource(),
						new Provider<IEObjectDescription>() {

							@Override
							public IEObjectDescription get() {
								return desc;
							}
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	private void createInt(Map<String, String> userData, AstExpression expr) {
		int repeat = new AstExpressionEvaluator(null).evaluateAsInteger(expr);
		userData.put(KEY_INT_VALUE, String.valueOf(repeat));
	}

	private void createType(Map<String, String> userData, EObject eObject)
			throws IOException {
		Type type = new TypeTransformer().doSwitch(eObject);
		XMIResource res = new XMIResourceImpl();
		res.getContents().add(type);
		StringWriter writer = new StringWriter();
		res.save(writer, null);

		userData.put(KEY_TYPE, writer.toString());
	}

}
