/*
 * Copyright (c) 2011, IETR/INSA of Rennes
 * Copyright (c) 2012, Synflow SAS
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
package net.sf.dftools.util.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * This class contains helper methods for Ecore models.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class EcoreHelper {

	private static Field modCount;

	static {
		try {
			modCount = AbstractList.class.getDeclaredField("modCount");

			// set accessible
			modCount.setAccessible(true);
		} catch (Exception e) {
			// should never happen
			e.printStackTrace();
		}
	}

	/**
	 * Returns the container of <code>ele</code> with the given type, or
	 * <code>null</code> if no such container exists. This method has been
	 * copied from the EcoreUtil2 class of Xtext.
	 * 
	 * @param <T>
	 *            type parameter
	 * @param ele
	 *            an object
	 * @param type
	 *            the type of the container
	 * @return the container of <code>ele</code> with the given type
	 */
	@SuppressWarnings("unchecked")
	public static <T extends EObject> T getContainerOfType(EObject ele,
			Class<T> type) {
		if (type.isAssignableFrom(ele.getClass())) {
			return (T) ele;
		}

		if (ele.eContainer() != null) {
			return getContainerOfType(ele.eContainer(), type);
		}

		return null;
	}

	/**
	 * Returns the list that contains this object, or <code>null</code>.
	 * 
	 * @param <T>
	 *            type of the objects contained in the list
	 * @param <T1>
	 *            type of the object as a specialization of <code>T</code>
	 * @param eObject
	 *            the object
	 * @return the list that contains this object, or <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public static <T extends EObject, T1 extends T> List<T> getContainingList(
			T1 eObject) {
		EStructuralFeature feature = eObject.eContainingFeature();
		if (feature.isMany()) {
			Object obj = eObject.eContainer().eGet(feature);
			if (obj != null && List.class.isAssignableFrom(obj.getClass())) {
				return (List<T>) obj;
			}
		}

		return null;
	}

	/**
	 * Deserializes the EObject stored in the given file, and returns it.
	 * 
	 * @param file
	 *            a file whose extension is registered within EMF
	 * @return the EObject serialized in the given file
	 */
	@SuppressWarnings("unchecked")
	public static <T extends EObject> T getEObject(ResourceSet set, IFile file) {
		Resource resource = set.getResource(URI.createPlatformResourceURI(file
				.getFullPath().toString(), true), true);
		T eObject = (T) resource.getContents().get(0);
		return eObject;
	}

	/**
	 * Finds the feature of the given object that has the given name, and
	 * returns its value in the given object.
	 * 
	 * @param eObject
	 *            an EObject
	 * @param name
	 *            name of a feature of the object's class
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFeature(EObject eObject, String name) {
		EClass eClass = eObject.eClass();
		EStructuralFeature feature = eClass.getEStructuralFeature(name);
		return (T) eObject.eGet(feature);
	}

	/**
	 * Returns the IFile associated with the given resource.
	 * 
	 * @param resource
	 *            a resource
	 * @throws CoreException
	 *             if something goes wrong
	 */
	public static IFile getFile(Resource resource) throws CoreException {
		String fullPath = resource.getURI().toPlatformString(true);
		IPath path = new Path(fullPath);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getFile(path);
	}

	/**
	 * Finds the feature of the given object that has the given name, and
	 * returns its value as a list.
	 * 
	 * @param eObject
	 *            an EObject
	 * @param name
	 *            name of a feature of the object's class
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(EObject eObject, String name) {
		EClass eClass = eObject.eClass();
		EStructuralFeature feature = eClass.getEStructuralFeature(name);
		return (List<T>) eObject.eGet(feature);
	}

	/**
	 * Returns the "modCount" of the given object. The object must extend
	 * AbstractList. If the field cannot be retrieved,
	 * <code>(int) System.currentTimeMillis()</code> is returned.
	 * 
	 * @param obj
	 *            an object that is supposed to extend AbstractList
	 * @return the modCount if available, or a timestamp
	 */
	public static int getModCount(Object obj) {
		if (obj instanceof AbstractList<?>) {
			try {
				return modCount.getInt(obj);
			} catch (Exception e) {
				// should never happen
				e.printStackTrace();
			}
		}
		return (int) System.currentTimeMillis();
	}

	/**
	 * Returns an Iterable that contains an iterator that filters descendants of
	 * the given object that match the given class (or one of its subclasses).
	 * The iterator does not explore the descendants of the objects of the given
	 * class (in other words, the underlying iterator is pruned everytime a
	 * candidate is found): if O of type T contain objects O1 and O2 both with
	 * the type T, only O will be returned, not O1 nor O2.
	 * 
	 * @param eObject
	 *            an object
	 * @param cls
	 *            class of the descendants to match
	 * @return an Iterable
	 */
	public static <T> Iterable<T> getObjects(EObject eObject, final Class<T> cls) {
		final TreeIterator<EObject> it = eObject.eAllContents();
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {

					private EObject nextObject;

					@Override
					public boolean hasNext() {
						while (it.hasNext()) {
							EObject next = it.next();
							if (cls.isAssignableFrom(next.getClass())) {
								// prune after next
								it.prune();

								nextObject = next;
								return true;
							}
						}
						return false;
					}

					@Override
					@SuppressWarnings("unchecked")
					public T next() {
						return (T) nextObject;
					}

					@Override
					public void remove() {
						it.remove();
					}
				};
			}

		};
	}

	/**
	 * Puts the given EObject in the resource that belongs to the given resource
	 * set as identified by the given URI.
	 * 
	 * @param set
	 *            a resource set
	 * @param uri
	 *            URI of a resource
	 * @param object
	 *            an EObject
	 * @return <code>true</code> if serialization succeeded
	 */
	public static <T extends EObject> boolean putEObject(ResourceSet set,
			URI uri, T object) {
		Resource resource = set.getResource(uri, false);
		if (resource == null) {
			resource = set.createResource(uri);
		} else {
			resource.getContents().clear();
		}

		resource.getContents().add(object);
		try {
			resource.save(null);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Finds the feature of the given object that has the given name, and sets
	 * its value in the given object to the given value.
	 * 
	 * @param eObject
	 *            an EObject
	 * @param name
	 *            name of a feature of the object's class
	 * @param value
	 *            value that should be set
	 */
	public static void setFeature(EObject eObject, String name, Object value) {
		EClass eClass = eObject.eClass();
		EStructuralFeature feature = eClass.getEStructuralFeature(name);
		eObject.eSet(feature, value);
	}

}
