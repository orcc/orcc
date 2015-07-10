package net.sf.orcc.backends.c.dal.transform;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Void;
import net.sf.orcc.util.util.EcoreHelper;

/**
 * Replace state variables with constant value by their value.
 * Implementation based on
 * net.sf.orcc.backend.transform.ssa.ConstantPropagator
 *
 * @author Jani Boutellier
 *
 */
public class GlobalConstantPropagator extends DfVisitor<Void> {

	private class IrVisitor extends AbstractIrVisitor<Void> {
		IrVisitor() {
			super(true);
		}

		@Override
		public Void caseInstLoad(InstLoad load) {
			Var source = load.getSource().getVariable();
			if (source.isGlobal() && !source.isAssignable()) {
				Expression initialValue = source.getInitialValue();
				if (initialValue.isExprInt() || initialValue.isExprBool() ||
						initialValue.isExprFloat() || initialValue.isExprString()) {
					EList<Use> targetUses = load.getTarget().getVariable().getUses();
					while (!targetUses.isEmpty()) {
						ExprVar expr = EcoreHelper.getContainerOfType(
								targetUses.get(0), ExprVar.class);
						EcoreUtil.replace(expr, IrUtil.copy(initialValue));
						IrUtil.delete(expr);
					}
					IrUtil.delete(load);
					indexInst--;
				} else {
					OrccLogger.warnln("Variable " + source.getName() +
							": global constant propagation for type " +
							initialValue.getType().toString() + " not implemented.");
				}
			}
			return null;
		}
	}

	@Override
	public Void caseNetwork(Network network) {
		for (Actor actor : network.getAllActors()) {
			for (Action action : actor.getActions()) {
				IrVisitor irVisitor = new IrVisitor();
				irVisitor.doSwitch(action.getScheduler());
			}
		}
		return null;
	}

}
