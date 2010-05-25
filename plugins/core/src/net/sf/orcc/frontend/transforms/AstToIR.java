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
package net.sf.orcc.frontend.transforms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstBooleanExpression;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstIntType;
import net.sf.orcc.cal.cal.AstIntegerExpression;
import net.sf.orcc.cal.cal.AstListType;
import net.sf.orcc.cal.cal.AstLiteralExpression;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstStringExpression;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstUintType;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.OrderedMap;

/**
 * This class transforms an AST actor to its IR equivalent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AstToIR {

	private Map<AstPort, Port> portMap;

	private Map<AstVariable, Variable> variableMap;

	public AstToIR() {
		portMap = new HashMap<AstPort, Port>();
		variableMap = new HashMap<AstVariable, Variable>();
		variableMap.toString();
	}

	public Actor transform(String file, AstActor astActor) {
		String name = astActor.getName();
		OrderedMap<Variable> parameters = new OrderedMap<Variable>();
		OrderedMap<Port> inputs = transformPorts(astActor.getInputs());
		OrderedMap<Port> outputs = transformPorts(astActor.getOutputs());
		OrderedMap<Variable> stateVars = new OrderedMap<Variable>();
		OrderedMap<Procedure> procedures = new OrderedMap<Procedure>();

		ActionList actions = new ActionList();
		ActionList initializes = new ActionList();
		FSM fsm = null;
		ActionScheduler scheduler = new ActionScheduler(actions.getList(), fsm);

		return new Actor(name, file, parameters, inputs, outputs, stateVars,
				procedures, actions.getList(), initializes.getList(),
				scheduler, null);
	}

	private Expression transformExpression(AstExpression expr) {
		if (expr instanceof AstLiteralExpression) {
			return transformExprLiteral((AstLiteralExpression) expr);
		}

		Location location = new Location();
		return new IntExpr(location, 42);
	}

	private Expression transformExprLiteral(AstLiteralExpression expr) {
		if (expr instanceof AstBooleanExpression) {
			AstBooleanExpression aBool = (AstBooleanExpression) expr;
			Location location = new Location();
			boolean value = Boolean.parseBoolean(aBool.getValue());
			return new BoolExpr(location, value);
		} else if (expr instanceof AstIntegerExpression) {
			AstIntegerExpression aInt = (AstIntegerExpression) expr;
			Location location = new Location();
			int value = aInt.getValue();
			return new IntExpr(location, value);
		} else if (expr instanceof AstStringExpression) {
			AstStringExpression aString = (AstStringExpression) expr;
			Location location = new Location();
			String value = aString.getValue();
			return new StringExpr(location, value);
		}

		// never happens
		throw new OrccRuntimeException("unknown literal expression type");
	}

	private OrderedMap<Port> transformPorts(List<AstPort> portList) {
		OrderedMap<Port> ports = new OrderedMap<Port>();
		for (AstPort aPort : portList) {
			Location location = new Location();
			Type type = transformType(aPort.getType());
			Port port = new Port(location, type, aPort.getName());
			portMap.put(aPort, port);
			ports.add("", location, port.getName(), port);
		}

		return ports;
	}

	private Type transformType(AstType aType) {
		String typeName = aType.getName();
		if (typeName.equals("bool")) {
			return new BoolType();
		} else if (typeName.equals("int")) {
			Expression size = transformExpression(((AstIntType) aType)
					.getSize());
			return new IntType(size);
		} else if (typeName.equals("List")) {
			Type type = transformType(((AstListType) aType).getType());
			Expression size = transformExpression(((AstListType) aType)
					.getSize());
			return new ListType(size, type);
		} else if (typeName.equals("String")) {
			return new StringType();
		} else if (typeName.equals("uint")) {
			Expression size = transformExpression(((AstUintType) aType)
					.getSize());
			return new UintType(size);
		} else if (typeName.equals("void")) {
			return new VoidType();
		}

		// never happens
		throw new OrccRuntimeException("unknown type \"" + typeName + "\"");
	}

}
