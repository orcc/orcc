package net.sf.orcc.stats;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.network.Network;

public class MemoryStats {

	public static MemoryStats INSTANCE = new MemoryStats();

	private Map<Actor, Integer> actorToBitsMap;

	public Map<Actor, Integer> getActorToBitsMap() {
		return actorToBitsMap;
	}

	public void computeGlobalMemorySize(Network network) {
		actorToBitsMap = new HashMap<Actor, Integer>();
		for (Actor actor : network.getActors()) {
			int bitsNb = 0;
			for (GlobalVariable var : actor.getStateVars()) {
				bitsNb += getSize(var.getType());
			}
			actorToBitsMap.put(actor, bitsNb);
		}
	}

	private int getSize(Type type) {
		int size;
		if (type.isBool()) {
			size = 1;
		} else if (type.isFloat()) {
			size = 32;
		} else if (type.isInt()) {
			size = ((TypeInt) type).getSize();
		} else if (type.isList()) {
			size = getSize(((TypeList) type).getElementType());
			for (int dim : type.getDimensions()) {
				size *= dim;
			}
		} else if (type.isString()) {
			size = ((TypeString) type).getSize();
		} else if (type.isUint()) {
			size = ((TypeUint) type).getSize();
		} else {
			size = 0;
		}
		return size;
	}

}
