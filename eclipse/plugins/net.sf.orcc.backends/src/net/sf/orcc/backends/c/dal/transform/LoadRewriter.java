package net.sf.orcc.backends.c.dal.transform;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.backends.c.dal.Stringifier;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

/**
 * Rewrites the target of a load instruction to be the same name
 * across all instances of the load. Recursively rewrites uses of the
 * previous name to the new name.
 *
 * @author James Guthrie
 *
 */
public class LoadRewriter extends DfVisitor<Void> {

	private Map<String, String> oldToNew = new HashMap<String, String>();

	private class IrVisitor extends AbstractIrVisitor<Void> {
		IrVisitor() {
			super(true);
		}

		@Override
		public Void caseInstLoad(InstLoad load){
			String newName = "local_" + load.getSource().getVariable().getName();
			String indexString = new Stringifier().doSwitch(load.getIndexes());
			if (indexString.length() > 0) {
				newName += "_" + indexString;
			}
			Def target = load.getTarget();
			Var variable = target.getVariable();
			String oldName = variable.getName();
			if (!newName.equals(oldName)) {
				if (!oldToNew.containsKey(variable.getName())) {
					oldToNew.put(variable.getName(), newName);
				}
				variable.setName(newName);
				target.setVariable(variable);
				load.setTarget(target);
			}
			return null;
		}

		@Override
		public Void caseVar(Var var) {
			if (oldToNew.containsKey(var.getName())) {
				var.setName(oldToNew.get(var.getName()));
			}
			return null;
		}

		@Override
		public Void caseUse(Use use) {
			doSwitch(use.getVariable());
			return null;
		}

		@Override
		public Void caseExprVar(ExprVar e) {
			doSwitch(e.getUse());
			return null;
		}
	}

	@Override
	public Void caseNetwork(Network network) {
		for (Actor actor : network.getAllActors()) {
			for (Action action : actor.getActions()) {
				IrVisitor irVisitor = new IrVisitor();
				irVisitor.doSwitch(action.getScheduler().getBlocks());
				irVisitor.doSwitch(action.getBody().getBlocks());
			}
		}
		return null;
	}

}
