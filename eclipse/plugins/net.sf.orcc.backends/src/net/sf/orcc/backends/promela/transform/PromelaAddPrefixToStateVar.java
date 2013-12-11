package net.sf.orcc.backends.promela.transform;

import java.util.List;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Var;



public class PromelaAddPrefixToStateVar extends DfVisitor<Void> {
	
	public PromelaAddPrefixToStateVar() {}

	@Override
	public Void caseActor(Actor actor) {
		this.actor=actor;
		renameVariables(actor.getStateVars());
		return null;
	}

	private void renameVariable(Var var) {
		var.setName("state_var_"+actor.getSimpleName()+"_"+var.getName());
	}

	private void renameVariables(List<Var> variables) {
		for (Var var : variables) {
			renameVariable(var);
		}
	}
}
