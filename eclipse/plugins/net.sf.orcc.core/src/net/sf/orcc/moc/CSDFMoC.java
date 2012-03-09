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

import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines the CSDF MoC. A CSDF actor has a sequence of fixed
 * production/consumption rates.
 * 
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.moc.MoC"
 */
public interface CSDFMoC extends MoC {

	/**
	 * Returns the delay pattern of this CSDF MoC.
	 * 
	 * @return the delay pattern of this CSDF MoC
	 * @model containment="true"
	 */
	Pattern getDelayPattern();

	/**
	 * Returns the input pattern of this CSDF MoC.
	 * 
	 * @return the input pattern of this CSDF MoC
	 * @model containment="true"
	 */
	Pattern getInputPattern();

	/**
	 * Returns the list of actions invoked that can be scheduled statically.
	 * 
	 * @return the list of actions invoked that can be scheduled statically
	 * @model containment="true"
	 */
	EList<Invocation> getInvocations();

	/**
	 * Returns the number of phases of this MoC.
	 * 
	 * @return the number of phases of this MoC
	 * @model
	 */
	int getNumberOfPhases();

	/**
	 * Returns the number of tokens consumed by this port.
	 * 
	 * @param port
	 *            an input port
	 * @return the number of tokens consumed by this port.
	 */
	int getNumTokensConsumed(Port port);

	/**
	 * Returns the number of tokens written to this port.
	 * 
	 * @param port
	 *            an output port
	 * @return the number of tokens written to this port.
	 */
	int getNumTokensProduced(Port port);

	/**
	 * Returns the output pattern of this CSDF MoC.
	 * 
	 * @return the output pattern of this CSDF MoC
	 * @model containment="true"
	 */
	Pattern getOutputPattern();

	/**
	 * Set the delay pattern of this CSDF MoC.
	 * 
	 * @param pattern
	 *            the delay pattern of this CSDF MoC
	 */
	void setDelayPattern(Pattern value);

	/**
	 * Set the input pattern of this CSDF MoC.
	 * 
	 * @param pattern
	 *            the input pattern of this CSDF MoC
	 */
	void setInputPattern(Pattern pattern);

	void setNumberOfPhases(int numberOfPhases);

	/**
	 * Set the output pattern of this CSDF MoC.
	 * 
	 * @param pattern
	 *            set the output pattern
	 */
	void setOutputPattern(Pattern pattern);

}
