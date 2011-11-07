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

import net.sf.orcc.df.Action;

/**
 * This class defines a simple pattern. A simple pattern is the invocation of
 * one action.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SimplePattern extends ExecutionPattern {

	private Action action;

	public SimplePattern(Action action) {
		this.action = action;
	}

	@Override
	public void accept(PatternVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int cost() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimplePattern) {
			return action.equals(((SimplePattern) obj).action);
		}
		return false;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public boolean isLoop() {
		return false;
	}

	@Override
	public boolean isSequential() {
		return false;
	}

	@Override
	public boolean isSimple() {
		return true;
	}

	@Override
	public String toString() {
		return action.toString();
	}

}
