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

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Port;

/**
 * This class defines a quasi-static dataflow (QSDF) MoC. QSDF is a model that
 * exhibits static behavior for a given configuration. An actor that has the
 * quasi-static class has one action for each configuration, therefore this
 * class associates one action with one static class.
 * 
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.moc.MoC"
 */
public interface QSDFMoC extends MoC {

	// to generate a normal Java map (non-serializable)
	// @model
	// dataType="org.eclipse.emf.ecore.EMap<net.sf.orcc.ir.Action, net.sf.orcc.moc.SDFMoC>"

	/**
	 * Returns the set of configuration actions.
	 * 
	 * @return the set of configuration actions
	 */
	Set<Action> getActions();

	/**
	 * Returns the configuration ports of the QSDF MoC.
	 * 
	 * @return a list of Port used by the configuration
	 */
	List<Port> getConfigurationPorts();
	
	/**
	 * Returns the configurations of the QSDF MoC.
	 * 
	 * @return a map of Action and the associated SDF MoC
	 */
	Map<Action, MoC> getConfigurations();

	/**
	 * Returns the SDF MoC that is associated with the configuration given by
	 * the action.
	 * 
	 * @param action
	 *            a configuration action
	 * @return a SDF MoC
	 */
	MoC getMoC(Action action);

	/**
	 * Sets the SDF MoC that is associated with the configuration given by the
	 * action.
	 * 
	 * @param action
	 *            a configuration action
	 * @param moc
	 *            a SDF MoC
	 */
	void setMoC(Action action, MoC moc);

}
