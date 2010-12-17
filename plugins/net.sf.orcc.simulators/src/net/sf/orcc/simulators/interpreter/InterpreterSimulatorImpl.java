/*
 * Copyright (c) 2010, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.simulators.interpreter;

import static net.sf.orcc.OrccLaunchConstants.DEFAULT_TRACES;
import static net.sf.orcc.OrccLaunchConstants.ENABLE_TRACES;
import static net.sf.orcc.OrccLaunchConstants.TRACES_FOLDER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Port;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.Fifo_String;
import net.sf.orcc.runtime.Fifo_boolean;
import net.sf.orcc.runtime.Fifo_int;
import net.sf.orcc.simulators.AbstractSimulator;
import net.sf.orcc.simulators.SimuActor;

public class InterpreterSimulatorImpl extends AbstractSimulator {

	protected List<Fifo> fifoList;

	protected int nbOfCycles;

	private AbstractInterpreterSimuActor breakpointActor = null;
	private Integer breakpointLine = 0;

	protected boolean enableTraces;

	public InterpreterSimulatorImpl() {
		super();
		fifoList = new ArrayList<Fifo>();
	}

	@Override
	protected SimuActor createSimuActorBroadcast(String instanceId,
			int numOutputs, boolean isBool) {
		return new InterpreterSimuBroadcast(instanceId, numOutputs, isBool);
	}

	@Override
	protected SimuActor createSimuActorInstance(String instanceId,
			Map<String, Expression> actorParameters, Actor actorIR) {
		SimuActor simuActorInstance;
		// Check if the actor is a "system actor". That is to say an actor
		// connected to a system I/O that has to be managed specifically
		String simpleName = actorIR.getSimpleName();
		if ("Source".equals(simpleName)) {
			simuActorInstance = new InterpreterSimuSource(instanceId,
					stimulusFile, process);
		} else if ("Display".equals(simpleName)) {
			simuActorInstance = new InterpreterSimuDisplay(instanceId, process);
		} else if ("ReadImage".equals(simpleName)) {
			simuActorInstance = new InterpreterSimuReadImage(instanceId, stimulusFile, process);
		} else if ("DisplayImage".equals(simpleName)) {
			simuActorInstance = new InterpreterSimuDisplayImage(instanceId, process);
		} else if ("WriteFile".equals(simpleName)) {
			simuActorInstance = new InterpreterSimuWriteFile(instanceId, actorParameters, process);
		} else if ("ReadFile".equals(simpleName)) {
			simuActorInstance = new InterpreterSimuReadFile(instanceId, actorParameters, process);
		} else {
			// Generic simulator actor
			simuActorInstance = new InterpreterSimuActor(instanceId,
					actorParameters, actorIR, process);
		}
		
		return simuActorInstance;
	}

	@Override
	protected void closeNetwork() {
		for (SimuActor simuActorInstance : simuActorsMap.values()) {
			AbstractInterpreterSimuActor myInstance = (AbstractInterpreterSimuActor) simuActorInstance;
			myInstance.close();
		}
		for (Fifo fifo : fifoList) {
			fifo.close();
		}
	}

	@Override
	protected void connectActors(SimuActor source, Port srcPort,
			SimuActor target, Port tgtPort, int fifoSize) {
		Fifo fifo = null;
		// Get traces folder and build fifo traces file name
		try {
			enableTraces = getAttribute(ENABLE_TRACES, DEFAULT_TRACES);
			String tracesFolder = getAttribute(TRACES_FOLDER, "");
			String fifoName = source.getInstanceId() + "_" + srcPort.getName();
			// Create FIFO with tracing capability
			if (srcPort.getType().isBool()) {
				fifo = new Fifo_boolean(fifoSize, tracesFolder, fifoName,
						enableTraces);
			} else if (srcPort.getType().isString()) {
				fifo = new Fifo_String(fifoSize, tracesFolder, fifoName,
						enableTraces);
			} else { // (srcPort.getType().isInt() or isUInt() TODO : manage
						// floats ?) {
				fifo = new Fifo_int(fifoSize, tracesFolder, fifoName,
						enableTraces);
			}
			fifoList.add(fifo);
			// Connect actors
			((AbstractInterpreterSimuActor) source).setFifo(srcPort, fifo);
			((AbstractInterpreterSimuActor) target).setFifo(tgtPort, fifo);
		} catch (OrccException e) {
			e.printStackTrace();
		}
		for (SimuActor simuActorInstance : simuActorsMap.values()) {
			AbstractInterpreterSimuActor myInstance = (AbstractInterpreterSimuActor) simuActorInstance;
			myInstance.connect();
		}
	}

	@Override
	protected String getBreakpointActorInstanceId() {
		if (breakpointActor != null) {
			return ((SimuActor) breakpointActor).getInstanceId();
		} else {
			return "";
		}
	}

	@Override
	protected Integer getBreakpointLineNumber() {
		return breakpointLine;
	}

	/**
	 * Add the number of total network run cycles to the network name
	 */
	public String getNetworkName() {
		return super.getNetworkName() + " at " + nbOfCycles + " cycles";
	}

	@Override
	protected void initializeNetwork() {
		for (SimuActor simuActorInstance : simuActorsMap.values()) {
			AbstractInterpreterSimuActor myInstance = (AbstractInterpreterSimuActor) simuActorInstance;
			myInstance.initialize();
		}
	}

	@Override
	protected int resumeNetwork() {
		if (breakpointActor != null) {
			while (breakpointActor.step(false) > 1)
				;
			breakpointActor = null;
		}
		return 0;
	}

	@Override
	protected int runNetwork() {
		int nbRunningActors = 0;
		int actorStatus = 0;
		for (SimuActor simuActorInstance : simuActorsMap.values()) {
			AbstractInterpreterSimuActor myInstance = (AbstractInterpreterSimuActor) simuActorInstance;
			actorStatus = myInstance.runAllSchedulableAction();
			// process.write("Run actor "
			// + ((SimuActor) myInstance).getInstanceId() + " returning "
			// + actorStatus + "\n");
			if (actorStatus < 0) {
				if (actorStatus == -2) {
					breakpointActor = myInstance;
				}
				return actorStatus;
			} else if (actorStatus > 0) {
				nbRunningActors++;
			}
		}
		nbOfCycles++;
		return nbRunningActors;
	}

	@Override
	protected int stepNetwork() {
		int nbRunningActors = 0;
		int actorStatus = 0;
		for (SimuActor simuActorInstance : simuActorsMap.values()) {
			AbstractInterpreterSimuActor myInstance = (AbstractInterpreterSimuActor) simuActorInstance;
			actorStatus = myInstance.runNextSchedulableAction();
			// process.write("Step actor "
			// + ((SimuActor) myInstance).getInstanceId() + " returning "
			// + actorStatus + "\n");
			if (actorStatus < 0) {
				if (actorStatus == -2) {
					breakpointActor = myInstance;
				}
				return actorStatus;
			} else {
				nbRunningActors += actorStatus;
			}
		}
		nbOfCycles++;
		return nbRunningActors;
	}

	/**
	 * Single step (into/over) the specified simulated actor instance. When
	 * current action has been complete : step over the whole network in order
	 * to generate required inputs for a new schedule of current stepping
	 * instance.
	 * 
	 * @param simuActorInstance
	 *            : stepping instance
	 * @param stepInto
	 *            : indicate if we have to go into or over the current stack
	 *            frame
	 * @return "2" if current action has not been completed yet; "1" if action
	 *         has been completed; "0" if no more action can be scheduled;
	 *         Otherwise, simulation is finished.
	 */
	private int step(AbstractInterpreterSimuActor simuActorInstance,
			boolean stepInto) {
		int status;
		int networkState;

		while ((status = simuActorInstance.step(true)) == 0) {
			// Stepping thread cannot schedule any action, so run all actors
			// to generate required inputs
			networkState = stepNetwork();
			if (networkState < 0) {
				return networkState;
			}
		}
		return status;
	}

	@Override
	protected int stepInto(String instanceId) {
		AbstractInterpreterSimuActor myInstance = (AbstractInterpreterSimuActor) simuActorsMap
				.get(instanceId);
		return step(myInstance, true);
	}

	@Override
	protected int stepOver(String instanceId) {
		AbstractInterpreterSimuActor myInstance = (AbstractInterpreterSimuActor) simuActorsMap
				.get(instanceId);
		return step(myInstance, false);
	}

	@Override
	protected void stepReturn(String instanceId) {
		AbstractInterpreterSimuActor myInstance = (AbstractInterpreterSimuActor) simuActorsMap
				.get(instanceId);
		if (myInstance.isStepping()) {
			while (myInstance.step(false) > 1)
				;
		}
	}

	@Override
	protected int suspendNetwork() {
		if (breakpointActor != null) {
			breakpointLine = breakpointActor.goToBreakpoint();
			return breakpointLine;
		}
		return 1;
	}

}
