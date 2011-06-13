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
package net.sf.orcc.cal.util;

import java.io.IOException;
import java.io.StringReader;

import net.sf.orcc.cal.CalResourceDescription;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Manager;
import org.xml.sax.InputSource;

import com.google.inject.Provider;

/**
 * This class defines utility functions for the net.sf.orcc.cal plug-in.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Util {

	private static Manager manager;

	private static IQualifiedNameProvider nameProvider;

	public static long total;

	public static int getIntValue(EObject eObject) {
		IResourceDescription resDesc = manager.getResourceDescription(eObject
				.eResource());
		QualifiedName name = nameProvider.getFullyQualifiedName(eObject);
		for (IEObjectDescription desc : resDesc.getExportedObjects(
				eObject.eClass(), name, false)) {
			String s = desc.getUserData(CalResourceDescription.KEY_INT_VALUE);
			if (s != null) {
				return Integer.parseInt(s);
			}
		}

		return 0;
	}

	/**
	 * Returns the line on which the given object is defined.
	 * 
	 * @param object
	 *            an AST object
	 * @return the line on which the given object is defined
	 */
	public static int getLocation(EObject object) {
		ICompositeNode node = NodeModelUtils.getNode(object);
		if (node == null) {
			return 0;
		} else {
			return node.getStartLine();
		}
	}

	public static IQualifiedNameProvider getNameProvider() {
		return nameProvider;
	}

	/**
	 * Returns the qualified name of the given entity as
	 * <code>package + "." + name</code>. If <code>package</code> is
	 * <code>null</code>, only the name is returned.
	 * 
	 * @param entity
	 *            an entity
	 * @return the qualified name of the given entity
	 */
	public static String getQualifiedName(AstEntity entity) {
		String packageName = entity.getPackage();
		String simpleName = entity.getName();

		String name = simpleName;
		if (packageName != null) {
			name = packageName + "." + name;
		}

		return name;
	}

	/**
	 * Returns the top-level container in which <code>context</code> occurs.
	 * 
	 * @param context
	 *            an object
	 * @return the top-level container in which <code>context</code> occurs
	 */
	public static EObject getTopLevelContainer(EObject context) {
		EObject cter = context.eContainer();
		if (cter == null) {
			return context;
		} else {
			return getTopLevelContainer(cter);
		}
	}

	/**
	 * Returns the type of the given object using the resource description to
	 * which the object belongs.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the type of the given object
	 */
	public static Type getType(final EObject eObject) {
		long t1 = System.currentTimeMillis();
		final Resource resource = eObject.eResource();

		IEObjectDescription desc = CalResourceDescription.instance.cache.get(
				eObject, resource, new Provider<IEObjectDescription>() {

					@Override
					public IEObjectDescription get() {
						QualifiedName name = nameProvider
								.getFullyQualifiedName(eObject);
						IResourceDescription resDesc = manager
								.getResourceDescription(eObject.eResource());
						for (IEObjectDescription desc : resDesc
								.getExportedObjects(eObject.eClass(), name,
										false)) {
							return desc;
						}
						return null;
					}
				});

		String s = desc.getUserData(CalResourceDescription.KEY_TYPE);
		if (s == null) {
			return null;
		}

		XMIResource res = new XMIResourceImpl();
		try {
			res.load(new InputSource(new StringReader(s)), null);
			Type type = (Type) res.getContents().get(0);
			long t2 = System.currentTimeMillis();
			total += (t2 - t1);
			return type;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void setManager(IResourceDescription.Manager manager) {
		Util.manager = manager;
	}

	public static void setNameProvider(IQualifiedNameProvider nameProvider) {
		Util.nameProvider = nameProvider;
	}

}
