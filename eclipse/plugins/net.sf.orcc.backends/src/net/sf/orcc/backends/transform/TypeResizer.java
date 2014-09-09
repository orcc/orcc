package net.sf.orcc.backends.transform;

import net.sf.orcc.backends.ir.InstCast;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

public class TypeResizer extends net.sf.orcc.df.transform.TypeResizer {

	private class InnerTypeResizer extends
			net.sf.orcc.df.transform.TypeResizer.InnerTypeResizer {

		@Override
		public Void defaultCase(EObject object) {
			if (object instanceof InstCast) {
				caseInstCast((InstCast) object);
			}
			return null;
		}

		public Void caseInstCast(InstCast cast) {
			Var source = cast.getSource().getVariable();
			Var target = cast.getTarget().getVariable();
			Type tSource = source.getType();
			Type tTarget = target.getType();

			// Check if the cast instruction is useless and can be removed. This
			// situation can happen after applying multiple IR transformations.
			if (tSource.getSizeInBits() == tTarget.getSizeInBits()
					&& ((tSource.getClass() == tTarget.getClass())
							|| (tSource.isInt() && tTarget.isUint()) || (tSource
							.isUint() && tTarget.isInt()))) {
				EList<Use> uses = target.getUses();
				while (!uses.isEmpty()) {
					uses.get(0).setVariable(source);
				}
				IrUtil.delete(cast);
				indexInst--;
			}
			return null;
		}

	}

	public TypeResizer(boolean castToPow2bits, boolean castTo32bits,
			boolean castNativePort, boolean castBoolToInt) {
		super(castToPow2bits, castTo32bits, castNativePort, castBoolToInt);
		irVisitor = new InnerTypeResizer();
	}

}
