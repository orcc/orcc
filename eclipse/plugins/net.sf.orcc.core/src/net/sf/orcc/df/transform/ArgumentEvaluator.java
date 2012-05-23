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
package net.sf.orcc.df.transform;

import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.ir.util.ValueUtil;

/**
 * This class defines a transformation that evaluates all the arguments of all
 * instances of a network.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ArgumentEvaluator extends DfSwitch<Void> {

	@Override
	public Void caseInstance(Instance instance) {
		for (Argument argument : instance.getArguments()) {
			Var var = argument.getVariable();
			Object value = new ExpressionEvaluator().doSwitch(argument.getValue());
			var.setValue(new ExpressionEvaluator().doSwitch(argument.getValue()));
			argument.setValue(ValueUtil.getExpression(value));
		}

		doSwitch(instance.getEntity());
		return null;
	}

	@Override
	public Void caseNetwork(Network network) {
		for (Var var : network.getVariables()) {
			var.setValue(new ExpressionEvaluator().doSwitch(var
					.getInitialValue()));
		}

		for (Instance instance : network.getInstances()) {
			doSwitch(instance);
		}
		return null;
	}

}
