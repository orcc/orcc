package net.sf.orcc.backends.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;

import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;

import net.sf.orcc.df.util.DfVisitor;

import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Use;

import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.util.EcoreHelper;

public class DisconnectedOutputPortRemoval extends DfVisitor<Void> {

	List<Port> discPorts = new ArrayList<Port>();

	@Override
	public Void caseActor(Actor actor) {

		// TODO Auto-generated method stub
		EList<Port> outports = actor.getOutputs();

		Map<Port, List<Connection>> outMap = actor.getOutgoingPortMap();
		Set<Port> outgp = outMap.keySet();

		ListIterator<Port> it = outports.listIterator();
		while (it.hasNext()) {
			Port port = it.next();
			if (!outgp.contains(port)) {
				System.out.println(port.getName());
				discPorts.add(port);

			}

		}
		actor.getOutputs().removeAll(discPorts);

		return super.caseActor(actor);

	}

	public Void casePattern(Pattern pattern) {
		EList<Port> ports = pattern.getPorts();

		for (Port discPort : discPorts) {
			if (ports.contains(discPort)) {
				Var varOfPort = pattern.getVariable(discPort);
				System.out.println(varOfPort.getName());
				while (!varOfPort.getDefs().isEmpty()) {
					Def defVar = varOfPort.getDefs().get(0);

					Instruction instDefOfVar = EcoreHelper.getContainerOfType(
							defVar, Instruction.class);
					System.out.println(instDefOfVar);
					IrUtil.delete(instDefOfVar);
				}

				while (!varOfPort.getUses().isEmpty()) {
					Use defVar = varOfPort.getUses().get(0);

					Instruction instDefOfVar = EcoreHelper.getContainerOfType(
							defVar, Instruction.class);
					System.out.println(instDefOfVar);
					IrUtil.delete(instDefOfVar);
				}

				IrUtil.delete(varOfPort);

				pattern.remove(discPort);

				ports.remove(discPort);

			}
		}

		return null;

	}
}
