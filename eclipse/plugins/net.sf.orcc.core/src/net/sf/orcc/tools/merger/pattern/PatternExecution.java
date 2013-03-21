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

/**
 * This class defines a pattern. A pattern is the invocation of one or more
 * patterns. A pattern can invoke one action (simple pattern), a series of other
 * patterns (sequential pattern), or a loop of one pattern (loop pattern).
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class PatternExecution {

	public PatternExecution() {
	}

	/**
	 * Accepts a visitor.
	 * 
	 * @param visitor
	 *            a visitor
	 */
	public abstract void accept(PatternVisitor visitor);

	/**
	 * Returns the cost of this pattern. The cost is determined from the number
	 * of sequential patterns, for instance [a, b, a, b] is more expensive than
	 * [2 x [a, b]].
	 * 
	 * @return the cost of this pattern
	 */
	public abstract int cost();

	/**
	 * Returns <code>true</code> if this pattern is a loop pattern.
	 * 
	 * @return <code>true</code> if this pattern is a loop pattern
	 */
	public abstract boolean isLoop();

	/**
	 * Returns <code>true</code> if this pattern is a sequential pattern.
	 * 
	 * @return <code>true</code> if this pattern is a sequential pattern
	 */
	public abstract boolean isSequential();

	/**
	 * Returns <code>true</code> if this pattern is a simple pattern.
	 * 
	 * @return <code>true</code> if this pattern is a simple pattern
	 */
	public abstract boolean isSimple();

}
