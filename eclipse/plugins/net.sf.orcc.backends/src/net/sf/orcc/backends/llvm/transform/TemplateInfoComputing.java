package net.sf.orcc.backends.llvm.transform;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

public class TemplateInfoComputing extends AbstractIrVisitor<Void> {

	private int blockIndex = 0;
	private Map<Instruction, Integer> accessMap;
	private Map<Var, Integer> tmpAccessMap;

	public TemplateInfoComputing() {
		tmpAccessMap = new HashMap<Var, Integer>();
	}

	@Override
	public Void caseBlockBasic(BlockBasic blockBasic) {
		blockBasic.getCfgNode().setNumber(blockIndex);
		blockIndex++;
		return super.caseBlockBasic(blockBasic);
	}

	@Override
	public Void caseBlockIf(BlockIf blockIf) {
		blockIf.getCfgNode().setNumber(blockIndex);
		blockIndex++;
		return super.caseBlockIf(blockIf);
	}

	@Override
	public Void caseInstLoad(InstLoad load) {
		Var var = load.getSource().getVariable();
		int accessNb;
		if (tmpAccessMap.containsKey(var)) {
			accessNb = tmpAccessMap.get(var);
			tmpAccessMap.remove(var);
		} else {
			accessNb = 0;
		}
		accessMap.put(load, accessNb);
		accessNb++;
		tmpAccessMap.put(var, accessNb);
		return null;
	}

	@Override
	public Void caseInstStore(InstStore store) {
		Var var = store.getTarget().getVariable();
		int accessNb;
		if (tmpAccessMap.containsKey(var)) {
			accessNb = tmpAccessMap.get(var);
			tmpAccessMap.remove(var);
		} else {
			accessNb = 0;
		}
		accessMap.put(store, accessNb);
		accessNb++;
		tmpAccessMap.put(var, accessNb);
		return null;
	}

	@Override
	public Void caseBlockWhile(BlockWhile blockWhile) {
		blockWhile.getCfgNode().setNumber(blockIndex);
		blockIndex++;
		return super.caseBlockWhile(blockWhile);
	}

	@Override
	public Void caseProcedure(Procedure procedure) {
		accessMap = new HashMap<Instruction, Integer>();
		tmpAccessMap.clear();
		super.caseProcedure(procedure);
		procedure.setAttribute("accessMap", accessMap);
		return null;
	}
}
