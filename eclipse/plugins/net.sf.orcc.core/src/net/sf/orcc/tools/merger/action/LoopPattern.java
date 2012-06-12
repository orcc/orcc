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
package net.sf.orcc.tools.merger.action;

/**
 * This class defines a loop pattern. A loop pattern is the invocation of one or
 * more patterns within a loop. It has the form:
 * 
 * <pre>
 * P = (n0 * p0, n1 * p1, ... nn * pn)
 * </pre>
 * 
 * @author Matthieu Wipliez
 * 
 */
public class LoopPattern extends ExecutionPattern {

	private int numIterations;

	private ExecutionPattern pattern;

	public LoopPattern(int iterations, ExecutionPattern pattern) {
		this.numIterations = iterations;
		this.pattern = pattern;
	}

	@Override
	public void accept(PatternVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int cost() {
		return pattern.cost();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LoopPattern) {
			LoopPattern other = (LoopPattern) obj;
			return numIterations == other.numIterations
					&& pattern.equals(other.pattern);
		}
		return false;
	}

	public int getNumIterations() {
		return numIterations;
	}

	public ExecutionPattern getPattern() {
		return pattern;
	}

	@Override
	public boolean isLoop() {
		return true;
	}

	@Override
	public boolean isSequential() {
		return false;
	}

	@Override
	public boolean isSimple() {
		return false;
	}

	@Override
	public String toString() {
		return numIterations + " x " + pattern;
	}

}
