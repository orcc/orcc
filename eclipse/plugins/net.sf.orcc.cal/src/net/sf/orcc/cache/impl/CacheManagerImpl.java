/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.cache.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CacheFactory;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cache.CachePackage;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Manager</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class CacheManagerImpl extends EObjectImpl implements CacheManager {

	private final Map<Resource, Cache> cacheMap = new HashMap<Resource, Cache>();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected CacheManagerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CachePackage.Literals.CACHE_MANAGER;
	}

	@Override
	public Cache getCache(Resource resource) {
		Cache cache = cacheMap.get(resource);
		if (cache == null) {
			cache = CacheFactory.eINSTANCE.createCache();
			cacheMap.put(resource, cache);
		}

		return cache;
	}

	@Override
	public <F extends EObject, T> T getOrCompute(F eObject,
			Switch<T> switchInst, EStructuralFeature featureMap) {
		Resource resource = eObject.eResource();
		T result;
		if (resource == null) {
			result = switchInst.doSwitch(eObject);
		} else {
			Cache cache = getCache(resource);

			@SuppressWarnings("unchecked")
			EMap<EObject, T> map = (EMap<EObject, T>) cache.eGet(featureMap);
			result = map.get(eObject);

			if (result == null) {
				result = switchInst.doSwitch(eObject);
				if (result != null) {
					map.put(eObject, result);
				}
			}
		}

		return result;
	}

	@Override
	public void unloadAllCaches() {
		cacheMap.clear();
	}

}
