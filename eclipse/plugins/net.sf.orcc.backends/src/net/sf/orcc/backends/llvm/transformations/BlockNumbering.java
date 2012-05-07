package net.sf.orcc.backends.llvm.transformations;

import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.util.AbstractIrVisitor;

public class BlockNumbering extends AbstractIrVisitor<Void> {
	
	private int index=0;
	
	@Override
	public Void caseBlockBasic(BlockBasic blockBasic) {
		blockBasic.getCfgNode().setNumber(index);
		index++;
		return super.caseBlockBasic(blockBasic);
	}

	@Override
	public Void caseBlockIf(BlockIf blockIf) {
		blockIf.getCfgNode().setNumber(index);
		index++;
		return super.caseBlockIf(blockIf);
	}

	@Override
	public Void caseBlockWhile(BlockWhile blockWhile) {
		blockWhile.getCfgNode().setNumber(index);
		index++;
		return super.caseBlockWhile(blockWhile);
	}
	
}
