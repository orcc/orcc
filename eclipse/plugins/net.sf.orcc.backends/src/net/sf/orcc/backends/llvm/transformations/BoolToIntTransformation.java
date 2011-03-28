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
package net.sf.orcc.backends.llvm.transformations;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.util.OrderedMap;

/**
 * Change port of type bool declaration into port of type i32.
 * 
 * @author Jerome GORIN
 */
public class BoolToIntTransformation extends AbstractActorVisitor {

	private void changeType(Variable variable) {
		TypeList listType = (TypeList) variable.getType();
		if (listType.getElementType().isBool()) {
			listType.setType(IrFactory.eINSTANCE.createTypeInt(32));
		}
	}

	@Override
	public void visit(Action action) {
		// input pattern
		Pattern pattern = action.getInputPattern();
		for (Port port : pattern.getPorts()) {
			Variable variable = pattern.getVariable(port);
			changeType(variable);

			variable = pattern.getPeeked(port);
			if (variable != null) {
				changeType(variable);
			}
		}

		// output pattern
		pattern = action.getOutputPattern();
		for (Port port : pattern.getPorts()) {
			Variable variable = pattern.getVariable(port);
			changeType(variable);
		}
	}

	@Override
	public void visit(Actor actor) {
		// Set port to i32
		visitPort(actor.getInputs());
		visitPort(actor.getOutputs());

		super.visit(actor);
	}

	public void visitPort(OrderedMap<String, Port> ports) {
		// Transform Port into int Variable
		for (Port port : ports) {
			if (port.getType().isBool()) {
				port.setType(IrFactory.eINSTANCE.createTypeInt(32));
			}
		}
	}

}
