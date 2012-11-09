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
package net.sf.orcc.graphiti.ui.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.graphiti.model.AbstractObject;
import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Parameter;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.commands.ParameterChangeValueCommand;
import net.sf.orcc.graphiti.ui.editors.GraphEditor;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * This class implements a property source for the different objects of our
 * model.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ModelPropertySource implements IPropertySource,
		PropertyChangeListener {

	private IPropertyDescriptor[] descs;

	private boolean doRefresh;

	private AbstractObject model;

	private ObjectType type;

	public ModelPropertySource(AbstractObject model) {
		this.model = model;
		model.addPropertyChangeListener(this);
		this.type = model.getType();

		List<IPropertyDescriptor> descs = new ArrayList<IPropertyDescriptor>();
		for (Parameter parameter : type.getParameters()) {
			if (!(parameter.getType() == List.class || parameter.getType() == Map.class)) {
				String name = parameter.getName();
				TextPropertyDescriptor desc = new TextPropertyDescriptor(name,
						name);
				descs.add(desc);
			}
		}

		this.descs = descs.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	public Object getEditableValue() {
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descs;
	}

	@Override
	public Object getPropertyValue(Object id) {
		Object value = model.getValue((String) id);
		if (value == null) {
			return "";
		}
		return String.valueOf(value);
	}

	@Override
	public boolean isPropertySet(Object id) {
		Object value = model.getValue((String) id);
		Object defaultValue = model.getParameter((String) id).getDefault();
		return value != defaultValue;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!doRefresh) {
			return;
		}

		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		// show properties
		try {
			IViewPart part = page.showView(IPageLayout.ID_PROP_SHEET);
			if (part instanceof PropertySheet) {
				IPropertySheetPage propPage = (IPropertySheetPage) ((PropertySheet) part)
						.getCurrentPage();
				if (propPage instanceof PropertySheetPage) {
					((PropertySheetPage) propPage).refresh();
				} else if (propPage instanceof TabbedPropertySheetPage) {
					((TabbedPropertySheetPage) propPage).refresh();
				}
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resetPropertyValue(Object id) {
		Object defaultValue = model.getParameter((String) id).getDefault();
		if (defaultValue == null) {
			defaultValue = "";
		}
		model.setValue((String) id, defaultValue);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		Graph graph;
		if (model instanceof Vertex) {
			graph = ((Vertex) model).getParent();
		} else if (model instanceof Edge) {
			graph = ((Edge) model).getParent();
		} else {
			graph = (Graph) model;
		}

		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		try {
			IEditorPart part = IDE.openEditor(page, graph.getFile());
			if (part instanceof GraphEditor) {
				String parameterName = (String) id;

				// only update value if it is different than before
				Object oldValue = model.getValue(parameterName);
				String str = (String) value;
				if (oldValue == null && str.isEmpty()
						|| String.valueOf(oldValue).equals(value)) {
					return;
				}

				// For instance name
				if ("id".equals(id)) {
					// update only if another instance has not the same
					if (graph.vertexExistsCaseInsensitive(str)) {
						return;
					}
				}

				ParameterChangeValueCommand command = new ParameterChangeValueCommand(
						model, "Change value");
				Class<?> parameterType = model.getParameter((String) id)
						.getType();
				if (str.isEmpty()) {
					// get default value
					value = model.getParameter(parameterName).getDefault();
				} else {
					try {
						if (parameterType == Integer.class) {
							value = Integer.valueOf(str);
						} else if (parameterType == Float.class) {
							value = Float.valueOf(str);
						} else if (parameterType == Boolean.class) {
							if (!"true".equals(value) && !"false".equals(value)) {
								throw new IllegalArgumentException();
							}
							value = Boolean.valueOf(str);
						}
					} catch (RuntimeException e) {
						value = "invalid \"" + value + "\" value for "
								+ parameterType.getSimpleName();
					}
				}
				command.setValue(parameterName, value);
				doRefresh = false;
				((GraphEditor) part).executeCommand(command);
				doRefresh = true;
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
}
