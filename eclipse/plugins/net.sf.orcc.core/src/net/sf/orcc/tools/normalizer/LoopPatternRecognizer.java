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
package net.sf.orcc.tools.normalizer;

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.moc.Invocation;

/**
 * This class defines a pattern. A pattern is the invocation of one or more
 * patterns. A pattern can invoke one action (simple pattern), a series of other
 * patterns (sequential pattern), or a loop of one pattern (loop pattern).
 * 
 * @author Matthieu Wipliez
 * 
 */
public class LoopPatternRecognizer {

	public LoopPatternRecognizer() {
	}

	/**
	 * If there are more than one iteration of the given pattern, adds a loop
	 * pattern with the given number of iterations to the sequential pattern.
	 * Otherwise just add the pattern to the sequential pattern.
	 * 
	 * @param seq
	 *            a sequential pattern
	 * @param iterations
	 *            number of iterations <code>pattern</code> is repeated
	 * @param pattern
	 *            a pattern
	 */
	private void addPattern(SequentialPattern seq, int iterations,
			ExecutionPattern pattern) {
		if (iterations > 1) {
			seq.add(new LoopPattern(iterations, pattern));
		} else {
			seq.add(pattern);
		}
	}

	private SequentialPattern createSequential(List<Invocation> invocations) {
		SequentialPattern pattern = new SequentialPattern();
		for (Invocation invocation : invocations) {
			Action action = invocation.getAction();
			SimplePattern simple = new SimplePattern(action);
			pattern.add(simple);
		}

		return pattern;
	}

	/**
	 * Finds a loop pattern in the given sequential pattern.
	 * 
	 * @param pattern
	 *            a sequential pattern
	 * @return a sequential pattern (possibly the same)
	 */
	private SequentialPattern findHigherOrderLoopPattern(
			SequentialPattern pattern, int length, SequentialPattern look) {
		SequentialPattern newPattern = new SequentialPattern();
		SequentialPattern sub = null;

		int iterations = 0;
		int maxIndex = pattern.size() - length - 1;
		int i = 0;
		while (i < maxIndex) {
			sub = pattern.getSubPattern(i, length);
			if (sub.equals(look)) {
				iterations++;
				i += length;
			} else {
				// add iterations of sub-pattern accumulated so far
				if (iterations > 0) {
					addPattern(newPattern, iterations, sub);
				}

				// add current element
				newPattern.add(pattern.get(i));
				iterations = 0;
				i++;
			}
		}

		// adds remaining iterations of sub-pattern
		if (iterations > 0) {
			addPattern(newPattern, iterations, sub);
		}

		// adds remaining non-recognized patterns
		while (i < pattern.size()) {
			newPattern.add(pattern.get(i));
			i++;
		}

		return newPattern;
	}

	/**
	 * Finds a higher-order pattern in the given sequential pattern.
	 * 
	 * @param pattern
	 *            a sequential pattern
	 * @return a sequential pattern (possibly the same)
	 */
	private SequentialPattern findHigherOrderPattern(SequentialPattern pattern) {
		SequentialPattern best = pattern;
		int maxPatternSize = pattern.size() / 2;
		for (int i = 2; i < maxPatternSize; i++) {
			SequentialPattern newPattern = findHigherOrderPattern(pattern, i);
			if (newPattern.cost() < best.cost()) {
				best = newPattern;
			}
		}

		return best;
	}

	/**
	 * Finds a higher-order pattern in the given sequential pattern, whose
	 * length is given.
	 * 
	 * @param pattern
	 *            a sequential pattern
	 * @return a sequential pattern (possibly the same)
	 */
	private SequentialPattern findHigherOrderPattern(SequentialPattern pattern,
			int length) {
		SequentialPattern best = pattern;
		int maxIndex = pattern.size() - length - 1;
		for (int i = 0; i < maxIndex; i++) {
			SequentialPattern look = pattern.getSubPattern(i, length);
			SequentialPattern newPattern = findHigherOrderLoopPattern(pattern,
					length, look);
			if (newPattern.cost() < best.cost()) {
				best = newPattern;
			}
		}

		return best;
	}

	/**
	 * Finds a loop pattern in the given sequential pattern.
	 * 
	 * @param oldPattern
	 *            a sequential pattern
	 * @return a sequential pattern (possibly the same)
	 */
	private SequentialPattern findLoopPattern(SequentialPattern oldPattern) {
		Iterator<ExecutionPattern> it = oldPattern.iterator();
		if (it.hasNext()) {
			SequentialPattern newPattern = new SequentialPattern();
			ExecutionPattern previous = it.next();
			int iterations = 1;

			while (it.hasNext()) {
				ExecutionPattern next = it.next();
				if (previous.equals(next)) {
					iterations++;
				} else {
					addPattern(newPattern, iterations, previous);
					previous = next;
					iterations = 1;
				}
			}

			if (iterations > 0) {
				addPattern(newPattern, iterations, previous);
			}

			return newPattern;
		} else {
			return oldPattern;
		}
	}

	/**
	 * Finds a pattern in the given sequential pattern.
	 * 
	 * @param pattern
	 *            a sequential pattern
	 * @return a sequential pattern (possibly the same)
	 */
	private SequentialPattern findPattern(SequentialPattern pattern) {
		SequentialPattern oldPattern;
		do {
			oldPattern = pattern;
			pattern = findLoopPattern(oldPattern);
		} while (!pattern.equals(oldPattern));

		do {
			oldPattern = pattern;
			pattern = findHigherOrderPattern(oldPattern);
		} while (!pattern.equals(oldPattern));

		return pattern;
	}

	/**
	 * Returns an execution pattern that matches the given list of actions.
	 * 
	 * @param actions
	 *            a list of actions
	 * @return an execution pattern that matches the given list of actions
	 */
	public ExecutionPattern getPattern(List<Invocation> invocations) {
		SequentialPattern oldPattern;
		SequentialPattern newPattern = createSequential(invocations);
		do {
			oldPattern = newPattern;
			newPattern = findPattern(oldPattern);
		} while (!newPattern.equals(oldPattern));

		return newPattern;
	}

}
