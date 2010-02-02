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
package net.sf.orcc.tools.merger.sequitur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class defines the Sequitur algorithm.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Sequitur {

	public static void main(String[] args) {
		Sequitur seq = new Sequitur();
		List<Character> terminals = new ArrayList<Character>();
		for (char c : "abcdbcabcd".toCharArray()) {
			terminals.add(c);
		}
		seq.getSRule(terminals);
	}

	private Map<Digram, Symbol> digrams;

	private Symbol last;

	private char lastName;

	private Symbol penultimate;

	private Rule s;

	public Sequitur() {
		digrams = new HashMap<Digram, Symbol>();
		lastName = 'A';
	}

	private void createNewRule(Symbol symbol, Digram digram) {
		Rule newRule = new Rule(String.valueOf(lastName), digram);
		lastName++;

		// removes digram located at symbol, and references rule
		NonTerminalSymbol s1 = new NonTerminalSymbol(newRule);
		Symbol before = symbol.getPrevious();
		Symbol after = symbol.getNext().getNext();
		before.append(s1);
		s1.append(after);

		// remove obsolete digrams
		removeDigram(before, symbol);
		removeDigram(after.getPrevious(), after);

		// append reference to rule
		NonTerminalSymbol s2 = new NonTerminalSymbol(newRule);
		s.appendSymbol(s2);

		// update digrams
		digrams.put(digram, newRule.getGuard().getNext());
		last = s2;
	}

	private void removeDigram(Symbol s1, Symbol s2) {
		Digram digram = new Digram(s1, s2);
		digrams.remove(digram);
	}

	private void enforeDigramConstraint() {
		Digram digram = new Digram(penultimate, last);
		Symbol symbol = digrams.get(digram);
		if (symbol == null) {
			digrams.put(digram, penultimate);
		} else {
			Symbol g1 = symbol.getPrevious();
			Symbol g2 = symbol.getNext().getNext();

			// removes last two digrams
			removeDigram(penultimate.getPrevious(), penultimate);
			removeDigram(penultimate, last);
			
			// removes last two symbols
			s.pop();
			s.pop();

			if (g1.equals(g2)) {
				// there is a rule that contains only this digram
				Rule rule = ((GuardSymbol) g1).getRule();
				s.appendSymbol(new NonTerminalSymbol(rule));
			} else {
				// create a new rule
				createNewRule(symbol, digram);
			}
		}
	}

	public Rule getSRule(List<?> terminals) {
		s = new Rule("s");
		Iterator<?> it = terminals.iterator();
		if (it.hasNext()) {
			penultimate = new TerminalSymbol(it.next());
			s.appendSymbol(penultimate);
			while (it.hasNext()) {
				last = new TerminalSymbol(it.next());
				s.appendSymbol(last);
				enforeDigramConstraint();
				penultimate = last;
			}
		}

		return s;
	}

}
