package net.sf.orcc.backends.xlim.transformations;

import net.sf.orcc.backends.transformations.VariableRenamer;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Variable;

public class XlimVariableRenamer extends VariableRenamer {

	@Override
	public void visit(Pattern pattern) {
		String actionName = action.getName();
		for (Variable variable : pattern.getVariableMap().values()) {
			if (!action.getBody().getLocals().getList().contains(variable)) {
				variable.setName(actionName + "_" + variable.getName());
			}
		}
	}

}
