package net.sf.orcc.tools.merger.actor;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.util.OrccLogger;

/**
 * This analyzes a given schedule to discover
 * feedback edges (FIFOs).
 * 
 * @author Jani Boutellier
 * 
 */

public class ScheduleAnalyzer extends BufferSizer {

	protected Map<Port, Var> buffersMap = new HashMap<Port, Var>();

	public ScheduleAnalyzer(Network network) {
		super(network);
	}
	
	public void analyze(Actor superActor, Schedule schedule) {
		createBuffers(getMaxTokens(schedule));
		for (Iterand iterand : schedule.getIterands()) {
			processInputs(iterand.getAction());
			processOutputs(iterand.getAction());
		}
		checkBufferBalance(superActor);
	}

	private void createBuffers(Map<Connection, Integer> maxTokens) {
		int index = 0;
		for (Connection conn : maxTokens.keySet()) {
			int size = maxTokens.get(conn);
			if (size > 0) {
				String name = "buffer_" + index++;
				Var buffer = IrFactory.eINSTANCE.createVar(
						IrFactory.eINSTANCE.createTypeList(size,
						conn.getSourcePort().getType()), name, true, 0);
				buffer.addAttribute("rwBalance");
				buffer.setAttribute("rwBalance", new Integer(0));
				buffersMap.put(conn.getSourcePort(), buffer);
				buffersMap.put(conn.getTargetPort(), buffer);
			}
		}
	}

	private void processInputs(Action action) {
		for (Port port : action.getInputPattern().getPorts()) {
			accessBuffer(port, false);
		}
	}

	private void processOutputs(Action action) {
		for (Port source : action.getOutputPattern().getPorts()) {
			accessBuffer(source, true);
			Actor sourceActor = MergerUtil.getOwningActor(network, action, source);
			if (sourceActor.getOutgoingPortMap().get(source).size() > 1) {
				for (Connection c : sourceActor.getOutgoingPortMap().get(source)) {
					Port target = c.getTargetPort();
					if (target != null) {
						if (buffersMap.get(target) != buffersMap.get(source)) {
							accessBuffer(target, true);
						}
					}
				}
			}
		}
	}

	private void accessBuffer(Port port, boolean write) {
		if (buffersMap.containsKey(port)) {
			Var memVar = buffersMap.get(port);
			if (write) {
				incrementBufferAccess(memVar);
			} else {
				decrementBufferAccess(memVar);
			}
		}
	}

	private void incrementBufferAccess(Var var) {
		var.setAttribute("rwBalance", new Integer((
				(Integer)var.getValueAsObject("rwBalance")).intValue() + 1));
	}

	private void decrementBufferAccess(Var var) {
		var.setAttribute("rwBalance", new Integer((
				(Integer)var.getValueAsObject("rwBalance")).intValue() - 1));
	}

	private void checkBufferBalance(Actor superActor) {
		for (Actor actor : network.getAllActors()) {
			for (Port port : actor.getOutputs()) {
				Var buffer = buffersMap.get(port);
				if (buffer != null) {
					if (queryBufferAccess(buffer) > 0) {
						OrccLogger.traceln("Warning: schedule discards tokens in " + port.getName());
					}
				}
			}
		}
	}

	private int queryBufferAccess(Var var) {
		if (var.hasAttribute("rwBalance")) {
			return ((Integer)var.getValueAsObject("rwBalance")).intValue();
		}
		return 0;
	}

}
