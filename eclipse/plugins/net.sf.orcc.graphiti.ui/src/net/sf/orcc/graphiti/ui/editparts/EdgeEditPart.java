/*
 * Copyright (c) 2008-2011, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.ui.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.sf.orcc.graphiti.model.AbstractObject;
import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.editpolicies.DependencyEditPolicy;
import net.sf.orcc.graphiti.ui.editpolicies.DependencyEndPointEditPolicy;
import net.sf.orcc.graphiti.ui.figure.EdgeFigure;
import net.sf.orcc.graphiti.ui.properties.ModelPropertySource;
import net.sf.orcc.graphiti.ui.properties.PropertiesConstants;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * The EditPart associated to the Dependency gives methods to refresh the view
 * when a property has changed.
 * 
 * @author Samuel Beaussier
 * @author Nicolas Isch
 * 
 */
public class EdgeEditPart extends AbstractConnectionEditPart implements
		PropertyChangeListener, ITabbedPropertySheetPageContributor {

	@Override
	public void activate() {
		super.activate();
		((Edge) getModel()).addPropertyChangeListener(this);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new DependencyEndPointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new DependencyEditPolicy());
	}

	/**
	 * Creates a new DependencyGef figure
	 */
	@Override
	protected IFigure createFigure() {
		Edge edge = (Edge) getModel();
		return new EdgeFigure(edge);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		((Edge) getModel()).removePropertyChangeListener(this);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySource.class) {
			return new ModelPropertySource((AbstractObject) getModel());
		}
		if (adapter == IPropertySheetPage.class) {
			return new TabbedPropertySheetPage(this);
		}
		return super.getAdapter(adapter);
	}

	@Override
	public String getContributorId() {
		return PropertiesConstants.CONTRIBUTOR_ID;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		if (propertyName.equals(ObjectType.PARAMETER_SOURCE_PORT)) {
			VertexEditPart vertexEP = (VertexEditPart) getSource();
			vertexEP.propertyChange(new PropertyChangeEvent(this,
					Vertex.PROPERTY_SRC_VERTEX, null, null));
			// update anchors
			refresh();
		} else if (propertyName.equals(ObjectType.PARAMETER_TARGET_PORT)) {
			VertexEditPart vertexEP = (VertexEditPart) getTarget();
			vertexEP.propertyChange(new PropertyChangeEvent(this,
					Vertex.PROPERTY_DST_VERTEX, null, null));
			// update anchors
			refresh();
		}

		// any parameter
		// (including ports in case they are also displayed on the edge)
		EdgeFigure figure = (EdgeFigure) getFigure();
		figure.refresh(evt.getPropertyName(), evt.getNewValue());
	}

	void updateFigures() {
	}
}