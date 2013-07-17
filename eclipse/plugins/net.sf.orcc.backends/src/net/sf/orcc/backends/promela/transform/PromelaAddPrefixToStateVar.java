package net.sf.orcc.backends.promela.transform;

import java.util.List;

import net.sf.orcc.df.Instance;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Var;



public class PromelaAddPrefixToStateVar extends DfVisitor<Void> {

	Instance instance;
	
	public PromelaAddPrefixToStateVar() {}

	@Override
	public Void caseInstance(Instance instance) {
		this.instance=instance;
		renameVariables(instance.getActor().getStateVars());
		return null;
	}

	private void renameVariable(Var var) {
		var.setName(instance.getSimpleName()+"_"+var.getName());
	}

	private void renameVariables(List<Var> variables) {
		for (Var var : variables) {
			renameVariable(var);
		}
	}
}
