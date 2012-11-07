package net.sf.orcc.ui.properties;

import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.Vertex;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class OrccVertexFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof EditPart) {
			Object model = ((EditPart) toTest).getModel();
			if (model instanceof Vertex) {
				Vertex vertex = (Vertex) model;
				Graph graph = vertex.getParent();
				return graph.getType().getName().equals("XML Dataflow Network");
			}
		}
		return false;
	}

}
