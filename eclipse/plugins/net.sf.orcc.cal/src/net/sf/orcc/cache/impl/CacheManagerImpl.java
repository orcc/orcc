/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.cache.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CacheFactory;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cache.CachePackage;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
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

	private final Map<URI, Cache> cacheMap = new HashMap<URI, Cache>();

	private final ResourceSet set = new ResourceSetImpl();

	private final Map<URI, URI> uriMap = new HashMap<URI, URI>();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CacheManagerImpl() {
		super();
	}

	private URI createCacheURI(URI uri) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String name = uri.segment(1);
		IProject project = root.getProject(name);
		IFolder folder = OrccUtil.getOutputFolder(project);
		if (folder == null) {
			return null;
		}

		IPath path = new Path(uri.path());
		path = path.removeFirstSegments(3).removeLastSegments(1);

		String nameNoExt = uri.trimFileExtension().lastSegment();
		IPath outputPath = folder.getFullPath().append(path)
				.append("cache_" + nameNoExt + ".xmi");

		URI cacheUri = URI.createPlatformResourceURI(outputPath.toString(),
				true);
		return cacheUri;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CachePackage.Literals.CACHE_MANAGER;
	}

	@Override
	public Cache getCache(URI uri) {
		Cache cache = cacheMap.get(uri);
		if (cache == null) {
			// try to load the cache
			URI cacheUri = getCacheURI(uri);
			Resource cacheResource = set.getResource(cacheUri, false);
			if (cacheResource == null) {
				cacheResource = set.createResource(cacheUri);

				// create cache and save resource
				cache = CacheFactory.eINSTANCE.createCache();
				cacheResource.getContents().add(cache);
				try {
					cacheResource.save(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// retrieve cache
			cache = (Cache) cacheResource.getContents().get(0);
			cacheMap.put(uri, cache);
		}

		return cache;
	}

	private URI getCacheURI(URI uri) {
		URI cacheUri = uriMap.get(uri);
		if (cacheUri == null) {
			cacheUri = createCacheURI(uri);
			uriMap.put(uri, cacheUri);
		}

		return cacheUri;
	}

	@Override
	public <F extends EObject, T> T getOrCompute(F eObject,
			Switch<T> switchInst, EStructuralFeature featureMap,
			EStructuralFeature featureList) {
		Resource resource = eObject.eResource();
		T result;
		if (resource == null) {
			result = switchInst.doSwitch(eObject);
		} else {
			Cache cache = getCache(resource.getURI());

			URI uri = EcoreUtil.getURI(eObject);
			String fragment = uri.fragment();

			@SuppressWarnings("unchecked")
			Map<String, T> map = (Map<String, T>) cache.eGet(featureMap);
			result = map.get(fragment);

			if (result == null) {
				result = switchInst.doSwitch(eObject);
				if (result != null) {
					if (featureList != null) {
						@SuppressWarnings("unchecked")
						List<T> list = (List<T>) cache.eGet(featureList);
						list.add(result);
					}
					map.put(fragment, result);
				}
			}
		}

		return result;
	}

	@Override
	public ResourceSet getResourceSet() {
		return set;
	}

	@Override
	public void removeCache(URI uri) {
		// removes the cache from the map
		cacheMap.remove(uri);

		// get the cache URI to delete the resource in which the cache was
		// serialized (if it exists)
		URI cacheUri = getCacheURI(uri);
		if (cacheUri != null) {
			Resource cacheResource = set.getResource(cacheUri, false);
			if (cacheResource != null) {
				try {
					cacheResource.delete(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void saveCache(URI uri) {
		Cache cache = cacheMap.remove(uri);
		if (cache != null) {
			// get the cache URI to save the resource to which the cache belongs
			URI cacheUri = getCacheURI(uri);
			Resource cacheResource = set.getResource(cacheUri, false);
			if (cacheResource != null) {
				try {
					cacheResource.save(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void unloadAllCaches() {
		cacheMap.clear();
		uriMap.clear();
	}

}
