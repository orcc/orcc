package net.sf.orcc.backends.c.dal;

import java.util.Collection;

import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.Var;

/**
 * Implementation of the Token for instructions of the type InstLoad
 *
 * @author James Guthrie
 *
 */
public class LoadTokenImpl extends TokenImpl implements Token {

	public LoadTokenImpl(InstLoad instLoad) {
		this.i = instLoad;
	}

	@Override
	public InstLoad getInstruction() {
		return (InstLoad) i;
	}

	@Override
	public Var getTargetVar() {
		return getInstruction().getTarget().getVariable();
	}

	@Override
	public String toString() {
		String thisName = "load_" + getInstruction().getSource().getVariable().getName();
		String thisIndexes = new Stringifier().doSwitch(getInstruction().getIndexes());
		if (thisIndexes.length() > 0) {
			return thisName + "_" + thisIndexes;
		} else {
			return thisName;
		}
	}

	@Override
	public boolean isStateToken() {
		if (getInstruction().getSource().getVariable().isGlobal()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isInputToken() {
		if (getInstruction().getSource().getVariable().isGlobal()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean in(Collection<Var> vars) {
		String thisName = getInstruction().getTarget().getVariable().getName();
		for (Var v : vars) {
			if (v.getName().equals(thisName)) {
				return true;
			}
		}
		return false;
	}

}
