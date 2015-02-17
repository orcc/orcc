package net.sf.orcc.tools.merger.actor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

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

	int totalBalance = 0;
	
	Copier copier;
	
	public ScheduleAnalyzer(Network network, Copier copier) {
		super(network);
		this.copier = copier;
	}
	
	public boolean analyze(Actor superActor, Schedule schedule) {
		createBuffers(getMaxTokens(schedule));
		createBuffers(getMinTokens(schedule));
		for (Iterand iterand : schedule.getIterands()) {
			processInputs(iterand.getAction());
			processOutputs(iterand.getAction());
		}
		return totalBalance < 0;
	}

	private void createBuffers(Map<Connection, Integer> maxTokens) {
		int index = 0;
		for (Connection conn : maxTokens.keySet()) {
			int size = maxTokens.get(conn);
			if (size != 0) {
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
			accessBuffer(port, false, action.getInputPattern().getNumTokens(port));
		}
	}

	private void processOutputs(Action action) {
		for (Port source : action.getOutputPattern().getPorts()) {
			accessBuffer(source, true, action.getOutputPattern().getNumTokens(source));
		}
	}

	private void accessBuffer(Port port, boolean write, int count) {
		if (buffersMap.containsKey(port)) {
			Var memVar = buffersMap.get(port);
			if (write) {
				incrementBufferAccess(memVar, count);
			} else {
				decrementBufferAccess(memVar, count);
			}
		}
	}

	private void incrementBufferAccess(Var var, int count) {
		var.setAttribute("rwBalance", new Integer((
				(Integer)var.getValueAsObject("rwBalance")).intValue() + count));
		totalBalance += count;
	}

	private void decrementBufferAccess(Var var, int count) {
		var.setAttribute("rwBalance", new Integer((
				(Integer)var.getValueAsObject("rwBalance")).intValue() - count));
		totalBalance -= count;
	}

	public void markInternalFifos(Actor superActor, boolean fullFifos) {
		for (Actor actor : network.getAllActors()) {
			for (Port port : actor.getOutputs()) {
				Var buffer = buffersMap.get(port);
				if (buffer != null) {
					if (queryBufferAccess(buffer) > 0) {
						if (fullFifos) {
							OrccLogger.traceln("Info: generating feedback FIFO starting from "
								+ port.getName());
						} else {
							OrccLogger.traceln("Info: generating feedback variable starting from "
									+ port.getName());
						}
						externalizeConnection(superActor, actor, port, fullFifos);
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

	private void externalizeConnection(Actor superActor, Actor actor, 
			Port port, boolean fullFifos) {
		Connection connection = actor.getOutgoingPortMap().get(port).get(0);
		// this is the 'port' of 'actor'
		copyOutputPortIfNotFound(superActor, actor, connection.getSourcePort(),
				fullFifos, connection.getTargetPort(), connection.getSize());
		// this is the remote port of another actor
		copyInputPortIfNotFound(superActor, 
				connection.getTarget().getAdapter(Actor.class),
				connection.getTargetPort(), fullFifos);
	}
	
	private void copyInputPortIfNotFound(Actor superActor, Actor actor, 
			Port port, boolean fullFifos) {
		for (Port in : superActor.getInputs()) {
			if (in.getName().equals(port.getName())) {
				return;
			}
		}
		port.addAttribute("externalized");
		if (fullFifos) {
			port.addAttribute("externalFifo");
			superActor.getInputs().add((Port)copier.copy(port));
		}
	}

	private void copyOutputPortIfNotFound(Actor superActor, Actor actor, 
			Port port, boolean fullFifos, Port target, Integer size) {
		for (Port out : superActor.getOutputs()) {
			if (out.getName().equals(port.getName())) {
				return;
			}
		}
		port.addAttribute("externalized");
		if (fullFifos) {
			port.addAttribute("externalFifo");
			port.setAttribute("externalFifo", size);
			port.addAttribute("targetPort");
			port.setAttribute("targetPort", target.getName());
			superActor.getOutputs().add((Port)copier.copy(port));
		}
	}
	
}
