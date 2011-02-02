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
package net.sf.orcc.backends.transformations;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a transformation that changes size of variable to fit
 * types of general-purpose programming language such as C, C++ or Java.
 * 
 * @author Jerome Gorin
 * 
 */
public class TypeSizeTransformation extends AbstractActorTransformation {

	private void checkType(Type type) {
		int size;

		if (type.isInt()) {
			TypeInt intType = (TypeInt) type;
			size = getIntSize(intType.getSize());
			intType.setSize(size);
		} else if (type.isUint()) {
			TypeUint uintType = (TypeUint) type;
			size = getIntSize(uintType.getSize());
			uintType.setSize(size);
		} else if (type.isList()) {
			TypeList listType = (TypeList) type;
			checkType(listType.getType());
		}
	}

	private void checkVariables(OrderedMap<String, ? extends Variable> variables) {
		for (Variable variable : variables) {
			checkType(variable.getType());
		}
	}

	private int getIntSize(int size) {
		if (size <= 8) {
			return 8;
		} else if (size <= 16) {
			return 16;
		} else if (size <= 32) {
			return 32;
		} else {
			return 64;
		}
	}

	@Override
	public void transform(Actor actor) {
		checkVariables(actor.getParameters());
		checkVariables(actor.getStateVars());
		checkVariables(actor.getInputs());
		checkVariables(actor.getOutputs());

		super.transform(actor);
	}

	@Override
	public void visit(Procedure procedure) {
		checkVariables(procedure.getParameters());
		checkVariables(procedure.getLocals());
		checkType(procedure.getReturnType());
	}

}
