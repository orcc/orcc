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

import java.util.Set;

import net.sf.orcc.ir.Action;

import org.eclipse.emf.common.util.EMap;

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
	
	/**
	 * Return the configurations of this quasi-static MoC.
	 * 
	 * @return a map of configurations
	 * @model
	 */
	EMap<Action, SDFMoC> getConfigurations();

	/**
	 * Sets the value of the '{@link net.sf.orcc.moc.QSDFMoC#getConfigurations <em>Configurations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configurations</em>' attribute.
	 * @see #getConfigurations()
	 * @generated
	 */
	void setConfigurations(EMap<Action, SDFMoC> value);

	/**
	 * Returns the set of configuration actions.
	 * 
	 * @return the set of configuration actions
	 */
	Set<Action> getActions();

	/**
	 * Returns the SDF MoC that is associated with the configuration given by
	 * the action.
	 * 
	 * @param action
	 *            a configuration action
	 * @return a SDF MoC
	 */
	SDFMoC getStaticClass(Action action);

}
