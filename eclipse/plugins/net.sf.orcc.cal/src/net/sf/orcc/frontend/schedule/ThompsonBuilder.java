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
package net.sf.orcc.frontend.schedule;

import java.util.Stack;

import net.sf.orcc.cal.cal.RegExp;
import net.sf.orcc.cal.cal.RegExpBinary;
import net.sf.orcc.cal.cal.RegExpTag;
import net.sf.orcc.cal.cal.RegExpUnary;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Tag;

public class ThompsonBuilder extends CalSwitch<Void> {

	/**
	 * epsNFA stands for epsilon Nondeterministic Finite Automaton
	 */
	private Automaton eNFA;

	/**
	 * An index for state labeling
	 */
	private int index;

	/**
	 * A stack to store the initial and final states of the subexpressions.
	 */
	private Stack<Integer> recursionStack;

	public ThompsonBuilder() {
		eNFA = new Automaton(SimpleEdge.class);
		recursionStack = new Stack<Integer>();
		index = 0;
	}

	public Automaton build(RegExp regexp) {
		doSwitch(regexp);
		int initialGlobal = recursionStack.pop();
		int finalGlobal = recursionStack.pop();
		eNFA.setInitialState(initialGlobal);
		eNFA.addFinalState(finalGlobal);
		return eNFA;
	}

	private void caseAlternation(RegExpBinary regexp) {
		int initialCurrent = index++;
		eNFA.addVertex(initialCurrent);

		doSwitch(regexp.getLeft());
		int initialLeft = recursionStack.pop();
		int finalLeft = recursionStack.pop();
		doSwitch(regexp.getRight());
		int initialRight = recursionStack.pop();
		int finalRight = recursionStack.pop();

		int finalCurrent = index++;
		eNFA.addVertex(finalCurrent);

		eNFA.addEdge(initialCurrent, initialLeft, NewEpsilon());
		eNFA.addEdge(initialCurrent, initialRight, NewEpsilon());
		eNFA.addEdge(finalLeft, finalCurrent, NewEpsilon());
		eNFA.addEdge(finalRight, finalCurrent, NewEpsilon());

		recursionStack.push(finalCurrent);
		recursionStack.push(initialCurrent);
	}

	private void caseConcatenation(RegExpBinary regexp) {
		doSwitch(regexp.getLeft());
		int initialLeft = recursionStack.pop();
		int finalLeft = recursionStack.pop();
		doSwitch(regexp.getRight());
		int initialRight = recursionStack.pop();
		int finalRight = recursionStack.pop();

		// Simply add an epsilon transition between the two sub automata
		eNFA.addEdge(finalLeft, initialRight, NewEpsilon());
		recursionStack.push(finalRight);
		recursionStack.push(initialLeft);
	}

	@Override
	public Void caseRegExpBinary(RegExpBinary regexp) {
		if (regexp.getOperator() == null) {
			caseConcatenation(regexp);
		} else {
			caseAlternation(regexp);
		}
		return null;
	}

	@Override
	public Void caseRegExpTag(RegExpTag term) {
		int initialCurrent = index;
		int finalCurrent = index + 1;
		index = index + 2;

		eNFA.addVertex(initialCurrent);
		eNFA.addVertex(finalCurrent);
		Tag label = DfFactory.eINSTANCE.createTag(term.getTag()
				.getIdentifiers());
		eNFA.registerLetter(label);
		eNFA.addEdge(initialCurrent, finalCurrent, new SimpleEdge(label));

		recursionStack.push(finalCurrent);
		recursionStack.push(initialCurrent);
		return null;
	}

	@Override
	public Void caseRegExpUnary(RegExpUnary regexp) {
		if (regexp.getUnaryOperator().equals("*")) {
			caseStar(regexp);
		} else {
			caseZeroOrOne(regexp);
		}

		return null;
	}

	private void caseStar(RegExpUnary regexp) {
		int initialCurrent = index++;
		eNFA.addVertex(initialCurrent);

		doSwitch(regexp.getChild());
		int initialChild = recursionStack.pop();
		int finalChild = recursionStack.pop();

		int finalCurrent = index++;
		eNFA.addVertex(finalCurrent);
		// epsilon transition for 0 occurrences.
		eNFA.addEdge(initialCurrent, finalCurrent, NewEpsilon());

		// entering child regexp
		eNFA.addEdge(initialCurrent, initialChild, NewEpsilon());
		// returning to the beginning of the child regexp
		eNFA.addEdge(finalChild, initialChild, NewEpsilon());
		// exiting child regexp
		eNFA.addEdge(finalChild, finalCurrent, NewEpsilon());

		recursionStack.push(finalCurrent);
		recursionStack.push(initialCurrent);
	}

	private void caseZeroOrOne(RegExpUnary regexp) {
		// Match 0 or 1 element
		// Similar to Kleene Star but with no loop back.

		int initialCurrent = index++;
		eNFA.addVertex(initialCurrent);

		doSwitch(regexp.getChild());
		int initialChild = recursionStack.pop();
		int finalChild = recursionStack.pop();

		int finalCurrent = index++;
		eNFA.addVertex(finalCurrent);
		// epsilon transition for 0 occurrences.
		eNFA.addEdge(initialCurrent, finalCurrent, NewEpsilon());

		// entering child regexp
		eNFA.addEdge(initialCurrent, initialChild, NewEpsilon());
		// exiting child regexp
		eNFA.addEdge(finalChild, finalCurrent, NewEpsilon());

		recursionStack.push(finalCurrent);
		recursionStack.push(initialCurrent);
	}

	/**
	 * Returns a new epsilon transition.
	 * 
	 * @return a new epsilon transition
	 */
	private SimpleEdge NewEpsilon() {
		return new SimpleEdge(null);
	}

}
