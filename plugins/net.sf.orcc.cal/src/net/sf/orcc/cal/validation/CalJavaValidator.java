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
package net.sf.orcc.cal.validation;

import java.util.List;

import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.CallExpression;
import net.sf.orcc.cal.cal.CallStatement;
import net.sf.orcc.cal.cal.ForeachStatement;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.Procedure;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.VariableReference;

import org.eclipse.xtext.validation.Check;

/**
 * This class describes the validation of an RVC-CAL actor.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalJavaValidator extends AbstractCalJavaValidator {

	@Check
	public void checkIsFunctionUsed(Function function) {
		List<CallExpression> refs = function.getCalls();
		if (refs.isEmpty()) {
			warning("Unused function", CalPackage.FUNCTION__NAME);
		}
	}

	@Check
	public void checkIsFunctionUsed(Procedure procedure) {
		List<CallStatement> refs = procedure.getCalls();
		if (refs.isEmpty()) {
			warning("Unused procedure", CalPackage.PROCEDURE__NAME);
		}
	}

	@Check
	public void checkIsVariabledUsed(Variable variable) {
		// loop variables do not have to be used
		if (!(variable.eContainer() instanceof Generator || variable
				.eContainer() instanceof ForeachStatement)) {
			List<VariableReference> refs = variable.getReferences();
			if (refs.isEmpty()) {
				warning("Unused variable", CalPackage.VARIABLE__NAME);
			}
		}
	}

}
