/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.interpreter;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Port;

/**
 * This class describes a broadcast actor.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class BroadcastActor extends AbstractInterpretedActor {

	private Port inport;
	private List<Port> outports;

	private CommunicationFifo inFifo;

	public BroadcastActor(String id, Actor actor) {
		super(id, actor);
		outports = new ArrayList<Port>();
	}

	@Override
	public void close() {
	}

	private boolean hasRoom(List<Port> outports) {
		for (Port out : outports) {
			CommunicationFifo outFifo = ioFifos.get(out.getName());
			if (!outFifo.hasRoom(1))
				return false;
		}
		return true;
	}

	@Override
	public void initialize() {
		inFifo = ioFifos.get(inport.getName());
	}

	@Override
	public Integer run() {
		return schedule();
	}

	@Override
	public Integer schedule() {
		Object[] inData = new Object[1];
		Integer running = 0;
		while ((inFifo.hasTokens(1)) && hasRoom(outports)) {
			running = 1;
			inFifo.get(inData);
			for (Port outport : outports) {
				CommunicationFifo outFifo = ioFifos.get(outport.getName());
				outFifo.put(inData);
			}
		}

		return running;
	}

	public void setInport(Port inport) {
		this.inport = inport;
	}

	public void setOutport(Port outport) {
		this.outports.add(outport);
	}

	@Override
	public int step(boolean doStepInto) {
		return schedule();
	}

}
