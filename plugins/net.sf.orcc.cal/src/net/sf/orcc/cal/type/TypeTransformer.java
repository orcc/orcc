/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.type;

import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.expression.AstExpressionEvaluator;
import net.sf.orcc.cal.util.VoidSwitch;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;

/**
 * This class defines an AST type to IR type transformer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeTransformer extends VoidSwitch {

	/**
	 * Creates a new AST type to IR type transformation.
	 */
	public TypeTransformer() {
	}

	@Override
	public Void caseAstExpression(AstExpression expression) {
		TypeChecker checker = new TypeChecker();
		checker.getType(expression);
		return null;
	}

	@Override
	public Void caseAstFunction(AstFunction function) {
		TypeConverter converter = new TypeConverter();
		Type type = converter.transformType(function.getType());
		function.setIrType(type);
		return super.caseAstFunction(function);
	}

	@Override
	public Void caseAstInputPattern(AstInputPattern input) {
		AstPort port = input.getPort();
		doSwitch(port);

		// type of each token
		Type type = port.getIrType();

		// repeat equals to 1 when absent
		AstExpression astRepeat = input.getRepeat();
		if (astRepeat != null) {
			int repeat = new AstExpressionEvaluator()
					.evaluateAsInteger(astRepeat);
			type = IrFactory.eINSTANCE.createTypeList(repeat, type);
		}

		for (AstVariable token : input.getTokens()) {
			token.setIrType(type);
		}

		return null;
	}

	@Override
	public Void caseAstPort(AstPort port) {
		if (port.getIrType() == null) {
			TypeConverter converter = new TypeConverter();
			Type type = converter.transformType(port.getType());
			port.setIrType(type);
		}

		return null;
	}

	@Override
	public Void caseAstVariable(AstVariable variable) {
		TypeConverter converter = new TypeConverter();
		Type type = converter.transformType(variable.getType());
		variable.setIrType(type);

		doSwitch(variable.getValue());

		return null;
	}

	/**
	 * Transforms the given AST type to an IR type.
	 * 
	 * @param type
	 *            an AST type
	 * @return an IR type
	 */
	public void transformTypes(AstActor actor) {
		doSwitch(actor);
	}

}
