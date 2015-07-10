package net.sf.orcc.backends.c.dal;

import java.util.Collection;
import java.util.Iterator;

import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Var;


/**
 * Abstract implementation of Token functionality
 *
 * @author James Guthrie
 *
 */
public abstract class TokenImpl implements Token, Comparable<Token> {

	protected Instruction i;

	@Override
	public Collection<Var> dependencies() {
		GetVars getVars = new GetVars();
		Collection<Var> result = getVars.doSwitch(i);
		return result;
	}

	@Override
	public boolean depsFulfilledBy(Collection<Var> fulfillingVars) {
		Collection<Var> deps = dependencies();
		Iterator<Var> iter = deps.iterator();
		while (iter.hasNext()) {
			Var dep = iter.next();
			if (dep.isGlobal()) {
				// If the dep has global scope it's not a true dep
				iter.remove();
			} else {
				// If it has local scope, and is contained in fulfullingVars, remove
				for (Var v : fulfillingVars) {
					if (dep.getName().equals(v.getName())) {
						iter.remove();
						break;
					}
				}
			}
		}
		// If all dependencies are fulfilled, deps is empty
		if (deps.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Token o) {
		// If an interdependency exists, use that as parameter for comparison
		Collection<Var> thisDeps, otherDeps;
		thisDeps = dependencies();
		otherDeps = o.dependencies();
		if (o.in(thisDeps)) {
			return 1;
		} else if (this.in(otherDeps)) {
			return -1;
		}
		int diff = thisDeps.size() - otherDeps.size();
		if (diff == 0) {
			return this.toString().compareTo(o.toString());
		} else {
			return diff;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LoadTokenImpl)) {
			return false;
		} else {
			return (this.compareTo((LoadTokenImpl) obj) == 0);
		}
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
