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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

/**
 * This class contains two utility methods used by ExprTransformer,
 * StmtTransformer, and ActorTransformer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AstIrUtil {

	/**
	 * Returns the IR equivalent of the given AST variable using its name. This
	 * method is intended for generators/foreach loops.
	 * 
	 * @param variable
	 *            a local variable
	 * @return the IR equivalent of the given AST variable
	 */
	public static Var getLocalByName(Procedure procedure, Variable variable) {
		Var var = procedure.getLocal(variable.getName());
		if (var == null) {
			var = Frontend.getMapping(variable);
			procedure.getLocals().add(var);
		} else {
			Frontend.putMapping(variable, var);
		}

		return var;
	}

	/**
	 * Transforms the given AST expressions to a list of IR expressions. In the
	 * process nodes may be created and added to the current {@link #procedure},
	 * since many RVC-CAL expressions are expressed with IR statements.
	 * 
	 * @param expressions
	 *            a list of AST expressions
	 * @return a list of IR expressions
	 */
	public static List<Expression> transformExpressions(Procedure procedure,
			List<Node> nodes, List<AstExpression> expressions) {
		int length = expressions.size();
		List<Expression> irExpressions = new ArrayList<Expression>(length);
		for (AstExpression expression : expressions) {
			ExprTransformer transformer = new ExprTransformer(procedure, nodes);
			irExpressions.add(transformer.doSwitch(expression));
		}
		return irExpressions;
	}

}
