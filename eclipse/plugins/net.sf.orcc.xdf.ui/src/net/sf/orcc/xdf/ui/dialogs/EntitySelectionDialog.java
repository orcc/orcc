/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.xdf.ui.dialogs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Network;
import net.sf.orcc.ui.OrccUiActivator;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.xdf.ui.Activator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

/**
 * @author Antoine Lorence
 * 
 */
public class EntitySelectionDialog extends FilteredItemsSelectionDialog {

	private class EntityFilter extends ItemsFilter {
		@Override
		public boolean isConsistentItem(Object item) {
			return true;
		}

		@Override
		public boolean matchItem(Object item) {
			return matches(getElementName(item));
		}
	};

	private class EntityListLabelProvider implements ILabelProvider {

		List<ILabelProviderListener> listeners = new ArrayList<ILabelProviderListener>();

		@Override
		public void removeListener(ILabelProviderListener listener) {
			listeners.remove(listener);
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void dispose() {
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			listeners.add(listener);
		}

		@Override
		public String getText(Object element) {
			String result = "null";
			if (element instanceof EObject) {
				EObject obj = (EObject) element;
				if (obj.eClass().equals(DfPackage.eINSTANCE.getActor())) {
					result = ((Actor) obj).getName();
				} else if (obj.eClass().equals(DfPackage.eINSTANCE.getNetwork())) {
					result = ((Network) obj).getName();
				}
			} else {
				result = "BAD TYPE: " + element;
			}
			return result;
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof EObject) {
				EObject obj = (EObject) element;
				if (obj.eClass().equals(DfPackage.eINSTANCE.getActor())) {
					return OrccUiActivator.getImage("icons/orcc.png");
				} else if (obj.eClass().equals(DfPackage.eINSTANCE.getNetwork())) {
					return OrccUiActivator.getImage("icons/network.gif");
				}
			}
			return null;
		}
	};

	private static final String SETTINGS = EntitySelectionDialog.class.getCanonicalName();

	private final List<EObject> objects;
	private final static ResourceSet emfResourceSet = new ResourceSetImpl();

	/**
	 * @param shell
	 * @param multi
	 * @throws CoreException
	 */
	public EntitySelectionDialog(Shell shell, IProject project) throws CoreException {
		super(shell);

		setListLabelProvider(new EntityListLabelProvider());

		objects = new ArrayList<EObject>();

		// Compute the list of all source and ouput folders
		final List<IFolder> searchFolders = OrccUtil.getAllSourceFolders(project);
		searchFolders.addAll(OrccUtil.getOutputFolders(project));

		for (final IFolder searchFolder : searchFolders) {
			fillEntitiesList(searchFolder);
		}
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings().getSection(SETTINGS);

		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings().addNewSection(SETTINGS);
		}
		return settings;
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new EntityFilter();
	}

	@Override
	protected Comparator<?> getItemsComparator() {
		return new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				return getElementName(o1).compareTo(getElementName(o2));
			}
		};
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter,
			IProgressMonitor monitor) throws CoreException {

		monitor.beginTask("Searching", objects.size());
		for (EObject obj : objects) {
			contentProvider.add(obj, itemsFilter);
			monitor.worked(1);
		}
		monitor.done();
	}

	@Override
	public String getElementName(Object item) {
		String result = "null";
		if (item instanceof EObject) {
			EObject obj = (EObject) item;
			if (obj.eClass().equals(DfPackage.eINSTANCE.getActor())) {
				result = ((Actor) obj).getName();
			} else if (obj.eClass().equals(DfPackage.eINSTANCE.getNetwork())) {
				result = ((Network) obj).getName();
			}
		}
		return result;
	}

	/**
	 * Add all Actors and Networks contained in the given folder to the objects
	 * list, to allow them to be displayed later.
	 * 
	 * @param folder
	 * @throws CoreException
	 */
	private void fillEntitiesList(IFolder folder) throws CoreException {

		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		for (IResource resource : folder.members()) {
			if (resource.getType() == IResource.FOLDER) {
				fillEntitiesList((IFolder) resource);
			} else if (resource.getType() == IResource.FILE) {
				final String suffix = resource.getFileExtension();
				// Wee keep only cal Actors/Units and xdf networks
				if (!"ir".equals(suffix) && !"xdf".equals(suffix)) {
					continue;
				}

				final URI uri = URI.createPlatformResourceURI(resource.getFullPath().toString(), true);

				if (!root.exists(resource.getFullPath())) {
					continue;
				}

				EObject eobject = null;
				try {
					// FIXME: if a Network has to be updated, it will not be
					// stored to the final list
					final Resource emfRes = emfResourceSet.getResource(uri, true);

					// Do not try to read for invalid or empty files
					if (emfRes == null || emfRes.getContents().size() == 0) {
						continue;
					}
					eobject = emfRes.getContents().get(0);
					if (eobject == null) {
						continue;
					}
				} catch (NullPointerException e) {
					continue;
				} catch (Exception e) {
					continue;
				}

				final EClass classz = eobject.eClass();
				if (classz == null) {
					continue;
				}
				if (classz.equals(DfPackage.eINSTANCE.getNetwork())) {
					objects.add(eobject);
				} else if (classz.equals(DfPackage.eINSTANCE.getActor())) {
					objects.add(eobject);
				}
			}
		}
	}
}
