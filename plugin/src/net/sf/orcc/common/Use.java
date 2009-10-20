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
package net.sf.orcc.common;

/**
 * This class defines a use of a variable.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Use {

	/**
	 * the variable referenced
	 */
	private Variable variable;

	/**
	 * Creates a new use of the given variable. This use is added to the use
	 * list of the newly referenced variable.
	 * 
	 * @param variable
	 *            a variable
	 */
	public Use(Variable variable) {
		setVariable(variable);
	}

	/**
	 * Returns the variable referenced by this use.
	 * 
	 * @return the variable referenced by this use
	 */
	public Variable getVariable() {
		return variable;
	}

	/**
	 * Sets the variable referenced by this use to the given variable. This use
	 * is removed from the use list of the previously referenced variable, and
	 * added to the use list of the newly referenced variable.
	 * 
	 * @param variable
	 *            a variable that this use will reference
	 */
	public void setVariable(Variable variable) {
		if (this.variable != null) {
			this.variable.removeUse(this);
		}
		this.variable = variable;
		variable.addUse(this);
	}

	@Override
	public String toString() {
		return variable.toString();
	}

}
