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
package net.sf.orcc.ir.classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Port;

/**
 * This class defines the static class. A static actor has fixed
 * production/consumption rates.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StaticClass extends AbstractActorClass {

	/**
	 * a list of actions that can be scheduled statically.
	 */
	private List<Action> actions;

	private Map<Port, Integer> tokenConsumption;

	private Map<Port, Integer> tokenProduction;

	/**
	 * Creates a new static class.
	 */
	public StaticClass() {
		actions = new ArrayList<Action>();
		tokenConsumption = new LinkedHashMap<Port, Integer>();
		tokenProduction = new LinkedHashMap<Port, Integer>();
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
	 * Returns the list of actions that can be scheduled statically.
	 * 
	 * @return the list of actions that can be scheduled statically
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 * Returns the number of tokens consumed by this port.
	 * 
	 * @param port
	 *            an input port
	 * @return the number of tokens consumed by this port.
	 */
	public int getNumTokensConsumed(Port port) {
		Integer numTokens = tokenConsumption.get(port);
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
		Integer numTokens = tokenConsumption.get(port);
		if (numTokens == null) {
			return 0;
		}
		return numTokens;
	}

	@Override
	public boolean isStatic() {
		return true;
	}

	/**
	 * Prints the given port tokens map.
	 * 
	 * @param map
	 *            a map from ports to number of tokens they produced/consumed
	 */
	private void print(Map<Port, Integer> map) {
		Iterator<Entry<Port, Integer>> it = map.entrySet().iterator();
		if (it.hasNext()) {
			Entry<Port, Integer> entry = it.next();
			Port port = entry.getKey();
			int tokens = entry.getValue();
			System.out.print(port.getName() + ": " + tokens);
			while (it.hasNext()) {
				entry = it.next();
				port = entry.getKey();
				tokens = entry.getValue();
				System.out.print(", " + port.getName() + ": " + tokens);
			}
		}
	}

	/**
	 * Prints the token consumption of this static class.
	 */
	public void printTokenConsumption() {
		System.out.print("input ports: [");
		print(tokenConsumption);
		System.out.println("]");
	}

	/**
	 * Prints the token production of this static class.
	 */
	public void printTokenProduction() {
		System.out.print("output ports: [");
		print(tokenProduction);
		System.out.println("]");
	}

	/**
	 * Saves the number of tokens consumed by input ports of the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public void setTokenConsumptions(Actor actor) {
		for (Port port : actor.getInputs()) {
			tokenConsumption.put(port, port.getNumTokensConsumed());
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
			tokenProduction.put(port, port.getNumTokensProduced());
		}
	}

}
