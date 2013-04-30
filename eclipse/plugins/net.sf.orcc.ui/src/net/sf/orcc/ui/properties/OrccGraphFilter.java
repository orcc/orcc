package net.sf.orcc.ui.properties;

import net.sf.orcc.graphiti.model.Graph;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class OrccGraphFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof EditPart) {
			Object model = ((EditPart) toTest).getModel();
			if (model instanceof Graph) {
				Graph graph = (Graph) model;
				return graph.getType().getName().equals("XML Dataflow Network");
			}
		}
		return false;
	}

}
