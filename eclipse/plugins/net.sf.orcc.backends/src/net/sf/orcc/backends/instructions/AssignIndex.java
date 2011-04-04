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
package net.sf.orcc.backends.instructions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalTargetContainer;
import net.sf.orcc.ir.VarLocal;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.util.CommonNodeOperations;

/**
 * This class defines an AssignIndex instruction. This node is used in code
 * generation to assign the index of one dimension memory.
 * 
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class AssignIndex extends SpecificInstruction implements
		LocalTargetContainer {

	private Map<Expression,Integer> expressionToIndexMap;

	private List<Expression> indexes;

	private Type listType;
	
	private VarLocal target;

	/**
	 * Creates a new AssignIndex from the given indexes and target.
	 * 
	 * @param target
	 *            the target
	 * @param indexes
	 *            a list of indexes
	 */
	public AssignIndex(VarLocal target, List<Expression> indexes,
			Type listType) {
		super(target.getLocation());
		expressionToIndexMap = new HashMap<Expression, Integer>();
		setIndexes(indexes);
		setTarget(target);
		setListType(listType);
	}
	
	/**
	 * Returns the map of index expression to index number.
	 * 
	 * @return the map of index expression to index number
	 */
	public Map<Expression, Integer> getExpressionToIndexMap() {
		return expressionToIndexMap;
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
	 * Returns the type of the list which use this AssignIndex.
	 * 
	 * @return the type of the list which use this AssignIndex
	 */
	public Type getListType() {
		return listType;
	}

	@Override
	public VarLocal getTarget() {
		return target;
	}
	
	@Override
	public void internalSetTarget(VarLocal target) {
		this.target = target;
	}

	private void refreshMap(){
		expressionToIndexMap.clear();
		for(int i=0; i<indexes.size(); i++){
			expressionToIndexMap.put(indexes.get(i), i);
		}
	}

	/**
	 * Sets the indexes of this assign index instruction. Uses are updated to
	 * point to this instruction. This method is internal. Indexes should be
	 * modified solely using the {@link #getIndexes()} method.
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
		refreshMap();
	}

	/**
	 * Sets the type of the list which use this AssignIndex.
	 * 
	 * @param listType
	 *            a type
	 */
	public void setListType(Type listType) {
		this.listType = listType;
	}

	@Override
	public void setTarget(VarLocal target) {
		CommonNodeOperations.setTarget(this, target);
	}
	
	public boolean isAssignIndex(){
		return true;
	}
}
