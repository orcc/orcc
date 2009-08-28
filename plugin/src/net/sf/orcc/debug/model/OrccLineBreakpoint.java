/**
 * 
 */
package net.sf.orcc.debug.model;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.LineBreakpoint;

/**
 * @author mwipliez
 * 
 */
public class OrccLineBreakpoint extends LineBreakpoint {

	public OrccLineBreakpoint(IResource resource, int lineNumber)
			throws CoreException {
		IMarker marker = resource.createMarker("net.sf.orcc.marker");
		setMarker(marker);
		setEnabled(true);
		ensureMarker().setAttribute(IMarker.LINE_NUMBER, lineNumber);
		ensureMarker().setAttribute(IBreakpoint.ID,
				IOrccDebugConstants.DEBUG_MODEL_ID);

	}

	@Override
	public String getModelIdentifier() {
		return IOrccDebugConstants.DEBUG_MODEL_ID;
	}

}
