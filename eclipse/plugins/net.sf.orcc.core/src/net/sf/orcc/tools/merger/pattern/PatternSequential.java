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
package net.sf.orcc.tools.merger.pattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class defines a loop pattern. A loop pattern is the invocation of one or
 * more actions within a loop. It has the form:
 * 
 * <pre>
 * P = (n0 * p0, n1 * p1, ... nn * pn)
 * </pre>
 * 
 * @author Matthieu Wipliez
 * 
 */
public class PatternSequential extends PatternExecution implements
		Iterable<PatternExecution> {

	private List<PatternExecution> patterns;

	public PatternSequential() {
		patterns = new ArrayList<PatternExecution>();
	}

	@Override
	public void accept(PatternVisitor visitor) {
		visitor.visit(this);
	}

	public void add(PatternExecution pattern) {
		patterns.add(pattern);
	}

	@Override
	public int cost() {
		int cost = 0;
		for (PatternExecution pattern : patterns) {
			cost += pattern.cost();
		}
		return cost;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PatternSequential) {
			PatternSequential other = (PatternSequential) obj;
			return patterns.equals(other.patterns);
		}
		return false;
	}

	public PatternExecution get(int index) {
		return patterns.get(index);
	}

	public PatternSequential getSubPattern(int beginIndex, int length) {
		PatternSequential pattern = new PatternSequential();
		for (int i = beginIndex; i < beginIndex + length; i++) {
			pattern.add(patterns.get(i));
		}

		return pattern;
	}

	@Override
	public boolean isLoop() {
		return false;
	}

	@Override
	public boolean isSequential() {
		return true;
	}

	@Override
	public boolean isSimple() {
		return false;
	}

	@Override
	public Iterator<PatternExecution> iterator() {
		return patterns.iterator();
	}

	/**
	 * Returns the size of this pattern.
	 * 
	 * @return the size of this pattern
	 */
	public int size() {
		return patterns.size();
	}

	@Override
	public String toString() {
		return patterns.toString();
	}

}
