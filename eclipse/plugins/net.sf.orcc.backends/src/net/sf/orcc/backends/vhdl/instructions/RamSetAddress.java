/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl.instructions;

import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Use;

/**
 * This class defines a specific instruction that sets the address of a RAM.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class RamSetAddress extends RamInstruction {

	private List<Expression> indexes;

	/**
	 * Creates a new RamSetAddress
	 */
	public RamSetAddress(List<Expression> indexes) {
		setIndexes(indexes);
	}

	/**
	 * Returns the expressions that are used by this RamSetAddress.
	 * 
	 * @return the expressions that are used by this RamSetAddress
	 */
	public List<Expression> getIndexes() {
		return indexes;
	}

	/**
	 * Returns <code>true</code>. Intended for use in template.
	 * 
	 * @return <code>true</code>
	 */
	public boolean isRamSetAddress() {
		return true;
	}

	/**
	 * Sets the indexes of this instruction. Uses are updated to point to this
	 * instruction. This method is internal. Indexes should be modified solely
	 * using the {@link #getIndexes()} method.
	 * 
	 * @param indexes
	 *            a list of expressions
	 */
	private void setIndexes(List<Expression> indexes) {
		if (this.indexes != null) {
			Use.removeUses(this, this.indexes);
		}
		this.indexes = indexes;
		Use.addUses(this, indexes);
	}

}
