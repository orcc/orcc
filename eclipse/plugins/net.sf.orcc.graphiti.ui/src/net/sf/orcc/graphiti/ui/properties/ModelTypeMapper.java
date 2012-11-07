package net.sf.orcc.graphiti.ui.properties;

import org.eclipse.gef.EditPart;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class ModelTypeMapper implements ITypeMapper {

	@Override
	@SuppressWarnings("rawtypes")
	public Class mapType(Object object) {
		Class type = object.getClass();
		if (object instanceof EditPart) {
			type = ((EditPart) object).getModel().getClass();
		}
		return type;
	}

}
