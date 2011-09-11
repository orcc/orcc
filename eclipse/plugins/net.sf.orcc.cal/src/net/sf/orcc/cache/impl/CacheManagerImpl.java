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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Manager</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class CacheManagerImpl extends EObjectImpl implements CacheManager {

	private Map<URI, Cache> cacheMap = new HashMap<URI, Cache>();

	private Map<URI, URI> uriMap = new HashMap<URI, URI>();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		IPath outputPath = folder.getFullPath();

		String nameNoExt = uri.trimFileExtension().lastSegment();
		IPath path = new Path(uri.path());
		path = path.removeFirstSegments(3).removeLastSegments(1);

		URI cacheUri = URI.createPlatformResourceURI(outputPath.append(path)
				.append("cache_" + nameNoExt).addFileExtension("xmi")
				.toString(), true);

		return cacheUri;
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
	public Cache getCache(EObject eObject) {
		Resource resource = eObject.eResource();
		URI uri = resource.getURI();
		Cache cache = cacheMap.get(uri);
		if (cache == null) {
			// try to load the cache
			URI cacheUri = getCacheURI(uri);

			ResourceSet set = resource.getResourceSet();
			Resource cacheResource = set.getResource(cacheUri, true);
			if (cacheResource.getContents().isEmpty()) {
				// create cache
				cache = CacheFactory.eINSTANCE.createCache();
				cacheResource.getContents().add(cache);
			} else {
				// retrieve cache
				cache = (Cache) cacheResource.getContents().get(0);
			}

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

}
