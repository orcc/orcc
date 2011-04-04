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
package net.sf.orcc.ir;

/**
 * This class represents a local variable. A local variable is a variable that
 * may have a suffix and an SSA index.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class VarLocal extends Var {

	/**
	 * SSA index.
	 */
	private int index;

	public VarLocal(boolean assignable, int index, Location loc,
			String name, Type type) {
		super(loc, type, name, false, assignable);
		this.index = index;
	}

	/**
	 * Returns the base name of this variable, which is the original name of the
	 * variable, without suffix nor index.
	 * 
	 * @return the base name of this variable
	 */
	public String getBaseName() {
		return super.getName();
	}

	/**
	 * Returns the SSA index of this variable. This information is added when
	 * translating CAL to SSA form.
	 * 
	 * @return the SSA index of this variable
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns true if this variable has been assigned to an SSA index of this
	 * variable.
	 * 
	 * @return true if the variable has an index otherwise false.
	 */
	public boolean isIndexed() {
		return index != 0;
	}

	@Override
	public String getName() {
		String indexStr = (index == 0) ? "" : "_" + index;
		return getBaseName() + indexStr;
	}

	/**
	 * Sets the SSA index of this variable. This information is added when
	 * translating CAL to SSA form.
	 * 
	 * @param index
	 *            the SSA index of this variable
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
