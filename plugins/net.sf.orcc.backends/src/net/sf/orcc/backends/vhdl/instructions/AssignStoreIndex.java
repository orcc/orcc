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
package net.sf.orcc.backends.vhdl.instructions;

import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.SpecificInstruction;

/**
 * This class defines an AssignIndex instruction. This node is used in code
 * generation to assign the index of one dimension memory.
 * 
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 * 
 */
public class AssignStoreIndex extends SpecificInstruction {

	private List<Expression> indexes;

	private Variable source;

	/**
	 * Creates a new AssignIndex from the given indexes and target.
	 * 
	 * @param indexes
	 *            a list of indexes
	 */
	public AssignStoreIndex(Variable source, List<Expression> indexes) {
		super(source.getLocation());
		this.source = source;
		this.indexes = indexes;
	}

	/**
	 * Returns the expressions that are used by this AssignIndex.
	 * 
	 * @return the expressions that are used by this AssignIndex
	 */
	public List<Expression> getIndexes() {
		return indexes;
	}

	/**
	 * Returns the source of this AssignIndex.
	 * 
	 * @return the source of this AssignIndex
	 */
	public Variable getSource() {
		return source;
	}

}
