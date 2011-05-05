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

import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstStatement;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an AST to IR translator.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AstToIrTranslator {

	private Map<EObject, EObject> mapAstIr;

	public AstToIrTranslator(Map<EObject, EObject> mapAstIr) {
		this.mapAstIr = mapAstIr;
	}

	private void addParameters(Procedure procedure, List<AstVariable> parameters) {
		for (AstVariable parameter : parameters) {
			int lineNumber = Util.getLocation(parameter);
			Type type = parameter.getIrType();
			String name = parameter.getName();

			Var var = IrFactory.eINSTANCE.createVar(lineNumber, type, name,
					true, 0);
			procedure.getParameters().add(var);
			mapAstIr.put(parameter, var);
		}
	}

	public Procedure createProcedure(Actor actor, AstFunction function) {
		Procedure procedure = (Procedure) mapAstIr.get(function);
		if (procedure != null) {
			return procedure;
		}

		int lineNumber = Util.getLocation(function);
		procedure = IrFactory.eINSTANCE.createProcedure(function.getName(),
				lineNumber, function.getIrType());
		mapAstIr.put(function, procedure);
		actor.getProcs().add(procedure);

		addParameters(procedure, function.getParameters());
		transformLocals(procedure, function.getVariables());

		// add return
		lineNumber = Util.getLocation(function.getExpression());
		Expression expression = new AstToIrExprTranslator(mapAstIr)
				.transformExpression(function.getExpression());
		InstReturn instReturn = IrFactory.eINSTANCE.createInstReturn(
				lineNumber, expression);
		procedure.getLast().add(instReturn);

		return procedure;
	}

	public Procedure createProcedure(Actor actor, AstProcedure astProcedure) {
		Procedure procedure = (Procedure) mapAstIr.get(astProcedure);
		if (procedure != null) {
			return procedure;
		}

		int lineNumber = Util.getLocation(astProcedure);
		procedure = IrFactory.eINSTANCE.createProcedure(astProcedure.getName(),
				lineNumber, IrFactory.eINSTANCE.createTypeVoid());
		mapAstIr.put(astProcedure, procedure);
		actor.getProcs().add(procedure);

		addParameters(procedure, astProcedure.getParameters());
		transformLocals(procedure, astProcedure.getVariables());
		transformStatements(procedure, astProcedure.getStatements());

		return procedure;
	}

	public void transformLocal(Procedure procedure, AstVariable variable) {
		int lineNumber = Util.getLocation(variable);
		Type type = variable.getIrType();
		String name = variable.getName();
		boolean assignable = !variable.isConstant();

		Var var = IrFactory.eINSTANCE.createVar(lineNumber, type, name,
				assignable, 0);
		procedure.getLocals().add(var);
		mapAstIr.put(variable, var);

		if (variable.getValue() != null) {
		}
	}

	public void transformLocals(Procedure procedure, List<AstVariable> variables) {
		for (AstVariable variable : variables) {
			transformLocal(procedure, variable);
		}
	}

	public void transformStatements(Procedure procedure,
			List<AstStatement> statements) {
		AstToIrStmtTranslator translator = new AstToIrStmtTranslator(mapAstIr);
		translator.transformStatements(procedure, statements);
	}

}
