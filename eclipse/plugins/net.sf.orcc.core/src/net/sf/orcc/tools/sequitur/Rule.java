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
package net.sf.orcc.tools.sequitur;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class defines a rule.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Rule {

	public static final Map<String, Rule> rules = new LinkedHashMap<String, Rule>();

	private GuardSymbol guard;

	private String name;

	private int referenceCount;

	public Rule(String name) {
		this.name = name;
		rules.put(name, this);

		guard = new GuardSymbol(this);
	}

	/**
	 * Creates a new rule with the given name, and copies the two symbols
	 * present in the digram.
	 * 
	 * @param name
	 *            rule name
	 * @param digram
	 *            a digram
	 */
	public Rule(String name, Digram digram) {
		this(name);
		append(digram.getS1().copy());
		append(digram.getS2().copy());
	}

	public void append(Symbol symbol) {
		symbol.insertBetween(getLast(), guard);
	}

	/**
	 * Decrements the reference count of this rule.
	 */
	public void decrementReferenceCount() {
		this.referenceCount--;
	}

	public void delete() {
		rules.remove(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rule) {
			Rule rule = (Rule) obj;
			return name.equals(rule.name);
		}

		return false;
	}

	/**
	 * Returns the first symbol of this rule, or the guard if there are no
	 * symbols in this rule.
	 * 
	 * @return the first symbol of this rule, or the guard if there are no
	 *         symbols in this rule
	 */
	public Symbol getFirst() {
		return guard.getNext();
	}

	/**
	 * Returns the last symbol of this rule, or the guard if there are no
	 * symbols in this rule.
	 * 
	 * @return the last symbol of this rule, or the guard if there are no
	 *         symbols in this rule
	 */
	public Symbol getLast() {
		return guard.getPrevious();
	}

	/**
	 * Returns this rule's name.
	 * 
	 * @return this rule's name
	 */
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/**
	 * Increments the reference count of this rule.
	 */
	public void incrementReferenceCount() {
		this.referenceCount++;
	}

	/**
	 * Returns <code>true</code> if this rule is only referenced once.
	 * 
	 * @return <code>true</code> if this rule is only referenced once
	 */
	public boolean isReferencedOnce() {
		return (referenceCount == 1);
	}

	@Override
	public String toString() {
		String res = name + ": ";
		Symbol symbol = guard.getNext();
		while (symbol != guard) {
			res += symbol.toString();
			symbol = symbol.getNext();
		}

		return res;
	}

}
