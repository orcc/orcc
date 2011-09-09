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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.services.AstExpressionEvaluator;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.util.TypeUtil;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 * This class defines utility functions for the net.sf.orcc.cal plug-in.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Util {

	private static class Cache<T> {

		private Map<String, Map<String, T>> map = new HashMap<String, Map<String, T>>();

		public T get(EObject eObject) {
			URI uri = EcoreUtil.getURI(eObject);
			Map<String, T> resourceMap = getMap(uri);
			return resourceMap.get(uri.fragment());
		}

		private Map<String, T> getMap(URI uri) {
			Map<String, T> resourceMap = map.get(uri.path());
			if (resourceMap == null) {
				resourceMap = new HashMap<String, T>();
				map.put(uri.path(), resourceMap);
			}
			return resourceMap;
		}

		public void put(EObject eObject, T value) {
			URI uri = EcoreUtil.getURI(eObject);
			Map<String, T> resourceMap = getMap(uri);
			resourceMap.put(uri.fragment(), value);
		}

		public void removeEntries(URI uri) {
			Map<String, T> resourceMap = getMap(uri);
			resourceMap.clear();
		}
	}

	private static Cache<Integer> cacheIntValue = new Cache<Integer>();

	private static Cache<Type> cacheType = new Cache<Type>();

	private static Cache<Expression> cacheValue = new Cache<Expression>();

	public static int getIntValue(AstExpression expr) {
		Integer intValue = cacheIntValue.get(expr);
		if (intValue == null) {
			intValue = new AstExpressionEvaluator(null).evaluateAsInteger(expr);
			cacheIntValue.put(expr, intValue);
		}
		return intValue;
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
	 * Returns the type of the given object using its URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the type of the given object
	 */
	public static Type getType(EObject eObject) {
		return cacheType.get(eObject);
	}

	/**
	 * Returns the type of the given list of objects using their URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the type of the given object
	 */
	public static Type getType(List<? extends EObject> eObjects) {
		Iterator<? extends EObject> it = eObjects.iterator();
		if (!it.hasNext()) {
			return null;
		}

		Type type = getType(it.next());
		while (it.hasNext()) {
			type = TypeUtil.getLub(type, getType(it.next()));
		}

		return type;
	}

	public static Expression getValue(AstVariable variable) {
		return cacheValue.get(variable);
	}

	/**
	 * Stores the type of the given object using its URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @param type
	 *            the type of the object
	 */
	public static void putType(EObject eObject, Type type) {
		cacheType.put(eObject, type);
	}

	public static void putValue(AstVariable variable, Expression value) {
		cacheValue.put(variable, value);
	}

	public static void removeCacheForURI(URI uri) {
		cacheType.removeEntries(uri);
		cacheValue.removeEntries(uri);
		cacheIntValue.removeEntries(uri);
	}

}
