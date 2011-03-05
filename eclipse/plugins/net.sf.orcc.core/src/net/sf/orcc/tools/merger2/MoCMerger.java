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
package net.sf.orcc.tools.merger2;

import java.util.Map.Entry;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.moc.AbstractMoCInterpreter;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.SDFMoC;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a transformation that merge to MoC.
 * 
 * @author Jerome Gorin
 * 
 */
public class MoCMerger extends AbstractMoCInterpreter {
	private CSDFMoC mergedMoC;
	private OrderedMap<String, Port> ports;
	private int rate;

	public MoCMerger(MoC moc, int rate, OrderedMap<String, Port> ports) {
		if (!moc.isCSDF()) {
			throw new OrccRuntimeException(
					"Only SDF/CSDF actors are allowed in merger 2");
		}

		mergedMoC = (CSDFMoC) moc;
		this.ports = ports;
		this.rate = rate;
	}

	@Override
	public Object interpret(CSDFMoC moc, Object... args) {
		// Merge the two CSDF
		mergedMoC.addActions(moc.getActions());
		mergedMoC.setNumberOfPhases(mergedMoC.getNumberOfPhases()
				+ moc.getNumberOfPhases());

		updatePattern(mergedMoC.getInputPattern(), moc.getInputPattern());
		updatePattern(mergedMoC.getOutputPattern(), moc.getOutputPattern());

		return mergedMoC;
	}

	@Override
	public Object interpret(SDFMoC moc, Object... args) {
		return this.interpret((CSDFMoC) moc);
	}

	private void updatePattern(Pattern source, Pattern candidate) {
		for (Entry<Port, Integer> entry : candidate.entrySet()) {
			Port port = entry.getKey();
			if (ports.contains(port.getName())) {
				// Port has to be kept, update pattern
				Integer tokens = rate * entry.getValue();

				if (source.containsKey(port)) {
					tokens = tokens + source.get(port);
				}

				source.put(port, tokens);
			}
		}
	}
}
