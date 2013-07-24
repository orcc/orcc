package net.sf.orcc.backends.transform;

import org.eclipse.emf.common.util.EList;

import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;

/**
 * add explicit cast in function arguments when argument type differ from
 * parameter type in the case of List type
 * 
 * @author Mariem Abid
 * 
 */
public class CastArgFuncCall extends AbstractIrVisitor<Void> {

	@Override
	public Void caseInstCall(InstCall call) {
		EList<Arg> arguments = call.getArguments();
		EList<Param> funcParams = call.getProcedure().getParameters();

		for (int i = 0; i < funcParams.size(); i++) {
			Param param = funcParams.get(i);
			Arg arg = arguments.get(i);

			Expression exprArg = ((ArgByVal) arg).getValue();
			if ((exprArg instanceof ExprVar)) {
				Var argVar = ((ExprVar) exprArg).getUse().getVariable();
				Type paramType = param.getVariable().getType();
				if (paramType.equals(exprArg.getType())) {
					// do nothing
				} else {
					Type argType = IrUtil.copy(exprArg.getType());

					Var target = IrUtil.copy(argVar);
					target.setType(argType);
					if (argType instanceof TypeList) {
						if (((TypeList) argType).getInnermostType().isInt()
								&& argType.getSizeInBits() == 32
								&& ((TypeList) paramType).getInnermostType()
										.isUint()
								&& paramType.getSizeInBits() == 32) {
							target.setName("(u32*)" + argVar.getName());
						} else if (((TypeList) argType).getInnermostType()
								.isUint()
								&& argType.getSizeInBits() == 32
								&& ((TypeList) paramType).getInnermostType()
										.isInt()
								&& paramType.getSizeInBits() == 32) {
							target.setName("(i32*)" + argVar.getName());
						} else if (((TypeList) argType).getInnermostType()
								.isUint()
								&& argType.getSizeInBits() == 32
								&& ((TypeList) paramType).getInnermostType()
										.isUint()
								&& paramType.getSizeInBits() == 8) {
							target.setName("(u8*)" + argVar.getName());
						}
					}

					((ExprVar) exprArg).getUse().setVariable(target);

				}
			}
		}
		return null;
	}
}
