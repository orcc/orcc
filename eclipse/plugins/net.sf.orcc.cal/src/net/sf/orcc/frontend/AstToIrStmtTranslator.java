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
package net.sf.orcc.frontend;

import java.util.List;
import java.util.Map;

import net.sf.orcc.cal.cal.AstStatement;
import net.sf.orcc.cal.cal.AstStatementAssign;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an AST to IR statement translator.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AstToIrStmtTranslator extends CalSwitch<Object> {

	private Map<EObject, EObject> mapAstIr;

	public AstToIrStmtTranslator(Map<EObject, EObject> mapAstIr) {
		this.mapAstIr = mapAstIr;
	}

	@Override
	public Object caseAstStatementAssign(AstStatementAssign astAssign) {
		int lineNumber = Util.getLocation(astAssign);

		// get target
		AstVariable astTarget = astAssign.getTarget().getVariable();
		Var target = (Var) mapAstIr.get(astTarget);

		// transform indexes and value
		AstToIrExprTranslator translator = new AstToIrExprTranslator(mapAstIr);

		// create assign or store
		if (target.isLocal() && !target.getType().isList()) {
			Expression value = translator.transformExpression(astAssign
					.getValue());
			return IrFactory.eINSTANCE.createInstAssign(lineNumber, target,
					value);
		} else {
			List<Expression> indexes = translator
					.transformExpressions(astAssign.getIndexes());
			Expression value = translator.transformExpression(astAssign
					.getValue());
			return IrFactory.eINSTANCE.createInstStore(lineNumber, target,
					indexes, value);
		}
	}

	public void transformStatements(Procedure procedure,
			List<AstStatement> statements) {
		for (AstStatement statement : statements) {
			doSwitch(statement);
		}
	}

}
