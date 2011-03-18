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
package net.sf.orcc.moc;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.ir.Action;

/**
 * This class defines a quasi-static dataflow (QSDF) MoC. QSDF is a model that
 * exhibits static behavior for a given configuration. An actor that has the
 * quasi-static class has one action for each configuration, therefore this
 * class associates one action with one static class.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class QSDFMoC extends AbstractMoC {

	private Map<Action, SDFMoC> configurations;
	
	/**
	 * Creates a new quasi-static MoC.
	 */
	public QSDFMoC() {
		configurations = new LinkedHashMap<Action, SDFMoC>();
	}

	@Override
	public Object accept(MoCInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	/**
	 * Adds a configuration to this quasi-static MoC. A configuration is given
	 * by an action and associated with a SDF MoC.
	 * 
	 * @param action
	 *            a configuration action
	 * @param moc
	 *            a SDF MoC
	 */
	public void addConfiguration(Action action, SDFMoC moc) {
		configurations.put(action, moc);
	}
	
	/**
	 * Return the configurations of this quasi-static MoC.
	 * 
	 * @return a map of configurations
	 */
	public Map<Action, SDFMoC> getConfigurations() {
		return configurations;
	}

	/**
	 * Returns the set of configuration actions.
	 * 
	 * @return the set of configuration actions
	 */
	public Set<Action> getActions() {
		return configurations.keySet();
	}

	/**
	 * Returns the SDF MoC that is associated with the configuration given by
	 * the action.
	 * 
	 * @param action
	 *            a configuration action
	 * @return a SDF MoC
	 */
	public SDFMoC getStaticClass(Action action) {
		return configurations.get(action);
	}

	@Override
	public boolean isQuasiStatic() {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<Action, SDFMoC>> it = configurations.entrySet()
				.iterator();
		Entry<Action, SDFMoC> entry = it.next();
		builder.append("QSDF configuration ");
		builder.append(entry.getKey());
		builder.append('\n');
		builder.append(entry.getValue().toString());

		while (it.hasNext()) {
			entry = it.next();
			builder.append('\n');
			builder.append("QSDF configuration ");
			builder.append(entry.getKey());
			builder.append('\n');
			builder.append(entry.getValue().toString());
		}

		return builder.toString();
	}

}
