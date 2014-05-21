/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

import java.util.List;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Statement;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.services.Evaluator;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class transforms an AST actor to its IR equivalent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StructTransformer extends CalSwitch<EObject> {

	private Procedure procedure;

	/**
	 * Creates a new AST to IR transformer.
	 */
	public StructTransformer() {
	}

	/**
	 * Creates a new AST to IR transformer, which will append instructions and
	 * blocks to the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	public StructTransformer(Procedure procedure) {
		this.procedure = procedure;
	}

	/**
	 * Adds a return with the given value at the end of the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param value
	 *            an expression, possibly <code>null</code> for procedures that
	 *            do not have a return value
	 */
	public void addReturn(Procedure procedure, Expression value) {
		BlockBasic block = procedure.getLast();
		InstReturn returnInstr = eINSTANCE.createInstReturn(value);
		block.add(returnInstr);
	}

	@Override
	public EObject caseAstPort(AstPort astPort) {
		Type type = EcoreUtil.copy(Typer.getType(astPort));
		Port port = DfFactory.eINSTANCE.createPort(type, astPort.getName(),
				Util.hasAnnotation("native", astPort.getAnnotations()));
		Frontend.instance.putMapping(astPort, port);
		return port;
	}

	/**
	 * Transforms and adds a mapping from the given AST procedure to an IR
	 * procedure.
	 * 
	 * @param astProcedure
	 *            an AST procedure
	 * @return the IR procedure
	 */
	@Override
	public Procedure caseAstProcedure(AstProcedure astProcedure) {

		// Get existing procedure
		procedure = Frontend.instance.getMapping(astProcedure);
		// Set attributes
		procedure.setName(astProcedure.getName());
		procedure.setLineNumber(Util.getLocation(astProcedure));
		procedure.setReturnType(eINSTANCE.createTypeVoid());

		// set native flag
		if (Util.hasAnnotation("native", astProcedure.getAnnotations())) {
			procedure.setNative(true);
		}

		// Add annotations
		Util.transformAnnotations(procedure, astProcedure.getAnnotations());

		// add mapping now (in case this procedure is recursive)
		Frontend.instance.putMapping(astProcedure, procedure);

		transformParameters(astProcedure.getParameters());
		transformLocalVariables(astProcedure.getVariables());
		transformStatements(astProcedure.getStatements());

		addReturn(procedure, null);

		return procedure;
	}

	/**
	 * Transforms and adds a mapping from the given AST function to an IR
	 * procedure.
	 * 
	 * @param function
	 *            an AST function
	 * @return the IR procedure
	 */
	@Override
	public Procedure caseFunction(Function function) {

		// Get existing procedure
		procedure = Frontend.instance.getMapping(function);
		// Set attributes
		procedure.setName(function.getName());
		procedure.setLineNumber(Util.getLocation(function));
		procedure.setReturnType(Typer.getType(function));

		// set native flag
		if (Util.hasAnnotation("native", function.getAnnotations())) {
			procedure.setNative(true);
		}

		// Add annotations
		Util.transformAnnotations(procedure, function.getAnnotations());

		// add mapping now (in case this function is recursive)
		Frontend.instance.putMapping(function, procedure);

		transformParameters(function.getParameters());
		transformLocalVariables(function.getVariables());

		Expression value;
		if (procedure.isNative()) {
			value = null;
		} else {
			ExprTransformer transformer = new ExprTransformer(procedure,
					procedure.getBlocks());
			value = transformer.doSwitch(function.getExpression());
		}

		addReturn(procedure, value);

		return procedure;
	}

	@Override
	public EObject caseVariable(Variable variable) {
		if (Util.isGlobal(variable)) {
			return caseVariableGlobal(variable);
		} else {
			return caseVariableLocal(variable);
		}
	}

	/**
	 * Transforms a global AST variable to its IR equivalent. The initial value
	 * of an AST state variable is evaluated to a constant by
	 * {@link #exprEvaluator}.
	 * 
	 * @param variable
	 *            an AST variable
	 * @return the IR equivalent of the AST variable
	 */
	private Var caseVariableGlobal(Variable variable) {
		int lineNumber = Util.getLocation(variable);
		Type type = EcoreUtil.copy(Typer.getType(variable));
		String name = variable.getName();
		boolean assignable = Util.isAssignable(variable);

		// retrieve initial value (may be null)
		Expression initialValue = EcoreUtil.copy(Evaluator.getValue(variable));

		// create state variable and put it in the map
		Var var = eINSTANCE.createVar(lineNumber, type, name, assignable,
				initialValue);
		Util.transformAnnotations(var, variable.getAnnotations());
		Frontend.instance.putMapping(variable, var);

		return var;
	}

	/**
	 * Transforms the given AST variable to an IR variable that has the name and
	 * type of <code>variable</code>.
	 * 
	 * @param variable
	 *            an AST variable
	 * @return the IR local variable created
	 */
	private Var caseVariableLocal(Variable variable) {
		int lineNumber = Util.getLocation(variable);
		Type type = Typer.getType(variable);
		String name = variable.getName();
		boolean assignable = Util.isAssignable(variable);

		// create local variable with the given name
		Var local = eINSTANCE.createVar(lineNumber, type, name, assignable, 0);

		AstExpression value = variable.getValue();
		if (value != null) {
			AstIrUtil.setLocal(local);
			new ExprTransformer(procedure, procedure.getBlocks(), local)
					.doSwitch(value);
			AstIrUtil.unsetLocal(local);
		}

		Frontend.instance.putMapping(variable, local);
		return local;
	}

	/**
	 * Transforms the given list of AST variables to IR variables, and adds them
	 * to the current {@link #procedure}'s local variables list.
	 * 
	 * @param variables
	 *            a list of AST variables
	 */
	public void transformLocalVariables(List<Variable> variables) {
		for (Variable Variable : variables) {
			Var local = caseVariableLocal(Variable);
			procedure.getLocals().add(local);
		}
	}

	/**
	 * Transforms the given list of AST parameters to IR variables, and adds
	 * them to the current {@link #procedure}'s parameters list.
	 * 
	 * @param parameters
	 *            a list of AST parameters
	 */
	private void transformParameters(List<Variable> parameters) {
		List<Param> params = procedure.getParameters();
		for (Variable astParameter : parameters) {
			Var local = caseVariableLocal(astParameter);
			params.add(eINSTANCE.createParam(local));
		}
	}

	/**
	 * Transforms the given AST statements to IR instructions and/or blocks that
	 * are added directly to the current {@link #procedure}.
	 * 
	 * @param statements
	 *            a list of AST statements
	 */
	public void transformStatements(List<Statement> statements) {
		new StmtTransformer(procedure, procedure.getBlocks())
				.doSwitch(statements);
	}

}
