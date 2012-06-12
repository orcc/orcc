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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		// final String pattern =
		// "abbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbc";
		final String pattern = "abbbbbbbbbbccccccccbccccccccbccccccccbccccccccbccccccccbccccccccbccccccccbd";
		// final String pattern =
		// "aaaaaaaaaaaaaaaabcccccccccccccccccccccccccccccccc";
		for (int i = 0; i < 1; i++) {
			for (char c : pattern.toCharArray()) {
				terminals.add(c);
			}
		}

		seq.getSRule(terminals);
	}

	private Map<Digram, Symbol> digrams;

	private int ruleIndex;

	private Rule s;

	/**
	 * Creates a new Sequitur algorithm.
	 */
	public Sequitur() {
		digrams = new HashMap<Digram, Symbol>();
		ruleIndex = 1;
	}

	/**
	 * Creates a new rule to represent the given digram. The given symbol and
	 * its successor are replaced by a reference to the newly-created rule. The
	 * penultimate and last symbols are also replaced by a reference to the
	 * newly-created rule.
	 * 
	 * @param symbol
	 *            symbol where the digram occurs
	 * @param digram
	 *            a digram
	 */
	private void createNewRule(Symbol symbol, Digram digram) {
		Rule newRule = new Rule(String.valueOf(ruleIndex), digram);
		ruleIndex++;

		// removes digram located at symbol, and references rule
		NonTerminalSymbol s1 = new NonTerminalSymbol(newRule);
		replaceDigram(symbol, s1);

		// append reference to rule
		NonTerminalSymbol s2 = new NonTerminalSymbol(newRule);
		replaceDigram(digram.getS1(), s2);
	}

	/**
	 * Enforces the digram utility constraint.
	 * 
	 * @param digram
	 *            a digram whose symbols are newly-linked
	 * @param s1
	 *            first symbol that references the digram
	 */
	private void enforceDigramUtility(Digram digram, Symbol s1) {
		if (digram.getS1().isGuard() || digram.getS2().isGuard()) {
			return;
		}

		Symbol symbol = digrams.get(digram);
		if (symbol == null) {
			// digram is not repeated elsewhere
			digrams.put(digram, s1);
		} else if (symbol.getNext() != digram.getS1()) {
			// the new digram is repeated elsewhere and the repetitions do not
			// overlap
			Symbol g1 = symbol.getPrevious();
			Symbol g2 = symbol.getNext().getNext();

			if (g1 == g2) {
				// there is a rule that contains only this digram
				Rule rule = ((GuardSymbol) g1).getRule();
				replaceDigram(s1, new NonTerminalSymbol(rule));
			} else {
				// create a new rule
				createNewRule(symbol, digram);
			}
		}
	}

	/**
	 * Enforces the rule utility constraint when the digram located at the given
	 * symbol <code>s1</code> is replaced by the given non-terminal symbol.
	 * 
	 * @param s1
	 *            first symbol of the digram replaced
	 * @param ntSymbol
	 *            a non-terminal symbol
	 */
	private void enforceRuleUtility(Symbol s1, NonTerminalSymbol ntSymbol) {
		Symbol s2 = s1.getNext();
		if (s1.isNonTerminal()) {
			trySubstitute((NonTerminalSymbol) s1, ntSymbol);
		} else if (s2.isNonTerminal()) {
			trySubstitute((NonTerminalSymbol) s2, ntSymbol);
		}
	}

	public Rule getSRule(List<?> terminals) {
		s = new Rule("s");
		Iterator<?> it = terminals.iterator();
		if (it.hasNext()) {
			Symbol penultimate = new TerminalSymbol(it.next());
			s.append(penultimate);
			while (it.hasNext()) {
				Symbol last = new TerminalSymbol(it.next());
				s.append(last);

				// a link is made between penultimate and last
				enforceDigramUtility(new Digram(penultimate, last), penultimate);
				penultimate = s.getLast();
			}
		}

		System.out.println(this);

		return s;
	}

	/**
	 * Replaces the digram located at the given symbol <code>s1</code> by the
	 * given new symbol.
	 * 
	 * @param s1
	 *            first symbol of the digram to replace
	 * @param ntSymbol
	 *            a non-terminal symbol
	 */
	private void replaceDigram(Symbol s1, NonTerminalSymbol ntSymbol) {
		Symbol before = s1.getPrevious();
		Symbol s2 = s1.getNext();
		Symbol after = s2.getNext();
		ntSymbol.insertBetween(before, after);

		// if either symbol is a non-terminal, decrements the reference count of
		// the rule it references
		if (s1.isNonTerminal()) {
			((NonTerminalSymbol) s1).getRule().decrementReferenceCount();
		}
		if (s2.isNonTerminal()) {
			((NonTerminalSymbol) s2).getRule().decrementReferenceCount();
		}

		// removes old digrams
		digrams.remove(new Digram(before, s1));
		digrams.remove(new Digram(s2, after));

		// adds new digrams around the new symbol
		enforceDigramUtility(new Digram(before, ntSymbol), before);
		enforceDigramUtility(new Digram(ntSymbol, after), ntSymbol);

		// replaces the digram by a reference to the new symbol
		// does not enforce digram utility, because we just did that
		Symbol reference = ntSymbol.getRule().getFirst();
		digrams.put(new Digram(s1, s2), reference);

		// because a digram is replaced by a non-terminal symbol
		enforceRuleUtility(s1, ntSymbol);
	}

	@Override
	public String toString() {
		String res = "";
		for (Entry<String, Rule> entry : Rule.rules.entrySet()) {
			res += entry.getValue() + "\n";
		}

		return res;
	}

	private void trySubstitute(NonTerminalSymbol ntSymbol,
			NonTerminalSymbol other) {
		Rule rule = ntSymbol.getRule();
		if (rule.isReferencedOnce()) {
			Symbol symbol = other.getRule().getFirst();
			while (!symbol.equals(ntSymbol)) {
				symbol = symbol.getNext();
			}

			Symbol before = symbol.getPrevious();
			Symbol after = symbol.getNext();
			Symbol ruleStart = rule.getFirst();
			Symbol ruleEnd = rule.getLast().getNext();
			symbol = ruleStart;

			while (symbol != ruleEnd) {
				Symbol next = symbol.getNext();
				symbol.insertBetween(before, after);
				before = symbol;
				symbol = next;
			}

			rule.delete();
			toString();
		}
	}

}
