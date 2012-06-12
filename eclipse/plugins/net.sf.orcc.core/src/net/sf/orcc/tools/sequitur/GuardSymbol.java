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

/**
 * This class defines a guard symbol.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class GuardSymbol extends Symbol {

	private Rule rule;

	/**
	 * Creates a new guard symbol associated with the given rule.
	 * 
	 * @param rule
	 *            the rule that references this guard
	 */
	public GuardSymbol(Rule rule) {
		this.rule = rule;
		append(this);
	}

	@Override
	public Symbol copy() {
		return new GuardSymbol(rule);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GuardSymbol) {
			GuardSymbol symbol = (GuardSymbol) obj;
			return rule.equals(symbol.rule);
		}

		return false;
	}

	/**
	 * Returns the rule that references this guard.
	 * 
	 * @return the rule that references this guard
	 */
	public Rule getRule() {
		return rule;
	}

	@Override
	public int hashCode() {
		return rule.hashCode();
	}

	@Override
	public boolean isGuard() {
		return true;
	}

	@Override
	public String toString() {
		return "guard of rule " + rule.getName();
	}

}
