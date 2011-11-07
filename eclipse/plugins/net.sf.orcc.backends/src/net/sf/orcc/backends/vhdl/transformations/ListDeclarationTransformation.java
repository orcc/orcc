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
package net.sf.orcc.backends.vhdl.transformations;

import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.ValueUtil;

/**
 * This class defines an actor transformation that transforms declarations of
 * multi-dimensional lists to declarations of lists with a single dimension.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * @author Herve Yviquel
 * 
 */
public class ListDeclarationTransformation extends AbstractActorVisitor<Object> {

	private int index;

	private Object targetValue;

	@Override
	public Object caseActor(Actor actor) {
		// VHDL synthesizers don't support multi-dimensional memory yet
		for (Var variable : actor.getStateVars()) {
			if (variable.getType().isList() && variable.isInitialized()) {
				TypeList typeList = (TypeList) variable.getType();
				Type eltType = typeList.getInnermostType();

				// compute total size
				int size = 1;
				for (int dim : typeList.getDimensions()) {
					size *= dim;
				}

				// create array and fill it
				targetValue = ValueUtil.createArray(eltType, size);
				index = 0;
				flattenList(typeList, variable.getValue());
				variable.setValue(targetValue);
			}
		}
		return null;
	}

	/**
	 * Flattens a multi-dimensional array into a single-dimensional one.
	 * 
	 * @param type
	 *            type of the given value
	 * @param value
	 *            a value (array or scalar)
	 */
	private void flattenList(Type type, Object value) {
		if (type.isList()) {
			TypeList typeList = (TypeList) type;
			Type eltType = typeList.getType();
			for (int i = 0; i < typeList.getSize(); i++) {
				Object eltValue = ValueUtil.get(eltType, value, i);
				flattenList(eltType, eltValue);
			}
		} else {
			ValueUtil.set(type, targetValue, value, index);
			index++;
		}
	}

}
