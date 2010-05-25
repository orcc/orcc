package net.sf.orcc.cal.ui.builder;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.cal.cal.Actor;
import net.sf.orcc.frontend.Frontend;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;

public class ActorBuilder implements IXtextBuilderParticipant {

	@Override
	public void build(IBuildContext context, IProgressMonitor monitor)
			throws CoreException {
		IFolder folder = context.getBuiltProject().getFolder("generated");
		if (!folder.exists()) {
			folder.create(true, true, null);
		}

		String outputFolder = folder.getLocation().toOSString();
		Frontend frontend = new Frontend(outputFolder);

		ResourceSet set = context.getResourceSet();
		for (Resource resource : set.getResources()) {
			List<EObject> contents = resource.getContents();
			Iterator<EObject> it = contents.iterator();
			if (it.hasNext()) {
				EObject obj = it.next();
				if (obj instanceof Actor) {
					Actor actor = (Actor) obj;

					try {
						URL resourceUrl = new URL(resource.getURI().toString());
						URL url = FileLocator.toFileURL(resourceUrl);
						IPath path = new Path(url.getPath());
						String file = path.toOSString();

						frontend.compile(file, actor);
					} catch (IOException e) {
						IStatus status = new Status(IStatus.ERROR,
								"net.sf.orcc.cal.ui",
								"could not generate code for "
										+ actor.getName(), e);
						throw new CoreException(status);
					} catch (OrccException e) {
						IStatus status = new Status(IStatus.ERROR,
								"net.sf.orcc.cal.ui",
								"could not generate code for "
										+ actor.getName(), e);
						throw new CoreException(status);
					}
				}
			}
		}
	}
}
