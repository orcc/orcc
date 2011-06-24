package net.sf.orcc.util;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class EcoreHelper {

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
		if (feature.getUpperBound() == EStructuralFeature.UNBOUNDED_MULTIPLICITY) {
			Object obj = eObject.eContainer().eGet(feature);
			if (obj != null && List.class.isAssignableFrom(obj.getClass())) {
				return (List<T>) obj;
			}
		}

		return null;
	}

}
