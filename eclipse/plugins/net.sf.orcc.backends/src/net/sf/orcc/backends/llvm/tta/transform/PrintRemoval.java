package net.sf.orcc.backends.llvm.tta.transform;

import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;

public class PrintRemoval extends AbstractIrVisitor<Void> {

	public Void caseInstCall(InstCall call) {
		if (call.isPrint()) {
			IrUtil.delete(call);
			indexInst--;
		}
		return null;
	}

}
