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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;

/**
 * This class defines the CSDF MoC. A CSDF actor has a sequence of fixed
 * production/consumption rates.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CSDFMoC extends AbstractMoC {

	/**
	 * a list of actions that can be scheduled statically.
	 */
	private List<Action> actions;

	private Pattern inputPattern;

	protected int numberOfPhases;

	private Pattern outputPattern;

	/**
	 * Creates a new CSDF MoC.
	 */
	public CSDFMoC() {
		actions = new ArrayList<Action>();
		inputPattern = new Pattern();
		outputPattern = new Pattern();
	}

	@Override
	public Object accept(MoCInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	/**
	 * Adds the given action to the list of actions that can be scheduled
	 * statically.
	 * 
	 * @param action
	 *            an action
	 */
	public void addAction(Action action) {
		actions.add(action);
	}

	/**
	 * Adds the given actions to the list of actions that can be scheduled
	 * statically.
	 * 
	 * @param action
	 *            an action
	 */
	public void addActions(List<Action> actions) {
		this.actions.addAll(actions);
	}

	/**
	 * Returns the list of actions that can be scheduled statically.
	 * 
	 * @return the list of actions that can be scheduled statically
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 * Returns the input pattern of this CSDF MoC.
	 * 
	 * @return the input pattern of this CSDF MoC
	 */
	public Pattern getInputPattern() {
		return inputPattern;
	}

	public int getNumberOfPhases() {
		return numberOfPhases;
	}

	/**
	 * Returns the number of tokens consumed by this port.
	 * 
	 * @param port
	 *            an input port
	 * @return the number of tokens consumed by this port.
	 */
	public int getNumTokensConsumed(Port port) {
		Integer numTokens = inputPattern.getNumTokens(port);
		if (numTokens == null) {
			return 0;
		}
		return numTokens;
	}

	/**
	 * Returns the number of tokens written to this port.
	 * 
	 * @param port
	 *            an output port
	 * @return the number of tokens written to this port.
	 */
	public int getNumTokensProduced(Port port) {
		Integer numTokens = outputPattern.getNumTokens(port);
		if (numTokens == null) {
			return 0;
		}
		return numTokens;
	}

	/**
	 * Returns the output pattern of this CSDF MoC.
	 * 
	 * @return the output pattern of this CSDF MoC
	 */
	public Pattern getOutputPattern() {
		return outputPattern;
	}

	@Override
	public boolean isCSDF() {
		return true;
	}

	/**
	 * Set the input pattern of this CSDF MoC.
	 * 
	 * @param pattern
	 *            the input pattern of this CSDF MoC
	 */
	public void setInputPattern(Pattern pattern) {
		this.inputPattern = pattern;
	}

	public void setNumberOfPhases(int numberOfPhases) {
		this.numberOfPhases = numberOfPhases;
	}

	/**
	 * Set the output pattern of this CSDF MoC.
	 * 
	 * @param pattern
	 *            set the output pattern
	 */
	public void setOutputPattern(Pattern pattern) {
		this.outputPattern = pattern;
	}

	/**
	 * Saves the number of tokens consumed by input ports of the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public void setTokenConsumptions(Actor actor) {
		for (Port port : actor.getInputs()) {
			inputPattern.setNumTokens(port, port.getNumTokensConsumed());
		}
	}

	/**
	 * Saves the number of tokens written to output ports of the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public void setTokenProductions(Actor actor) {
		for (Port port : actor.getOutputs()) {
			outputPattern.setNumTokens(port, port.getNumTokensProduced());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CSDF input ports: ");
		builder.append(inputPattern);
		builder.append('\n');
		builder.append("CSDF output ports: ");
		builder.append(outputPattern);
		return builder.toString();
	}

}
