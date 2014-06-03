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
package net.sf.orcc.test.util;

import java.util.List;

import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.util.ActorInterpreter;
import net.sf.orcc.util.OrccUtil;

/**
 * This interpreter implements
 * {@link #callPrintProcedure(net.sf.orcc.ir.Procedure, java.util.List)} to
 * check it gives the expected output.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TestInterpreter extends ActorInterpreter {

	private StringBuilder builder;

	public TestInterpreter(Actor actor) {
		super(actor);
		builder = new StringBuilder();
	}

	@Override
	protected void callPrintProcedure(List<Arg> arguments) {
		for (Arg arg : arguments) {
			if (arg.isByVal()) {
				Expression expr = ((ArgByVal) arg).getValue();
				if (expr.isExprString()) {
					// String characters rework for escaped control
					// management
					String str = ((ExprString) expr).getValue();
					String unescaped = OrccUtil.getUnescapedString(str);
					builder.append(unescaped);
				} else {
					Object value = exprInterpreter.doSwitch(expr);
					builder.append(value);
				}
			}
		}
	}

	/**
	 * Returns a String that contains everything the actor has written to the
	 * standard output.
	 * 
	 * @return a String that contains everything the actor has written to the
	 *         standard output.
	 */
	public String getOutput() {
		return builder.toString();
	}

}
