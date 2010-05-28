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

import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionInteger;
import net.sf.orcc.cal.cal.AstExpressionString;
import net.sf.orcc.cal.cal.AstExpressionUnary;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.util.CalSwitch;
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
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.OrderedMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parsetree.CompositeNode;
import org.eclipse.xtext.parsetree.NodeUtil;

/**
 * This class transforms an AST actor to its IR equivalent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AstToIR {

	private class ExpressionEvaluator extends CalSwitch<Expression> {

		@Override
		public Expression caseAstExpressionBoolean(AstExpressionBoolean expr) {
			return new BoolExpr(Boolean.parseBoolean(expr.getValue()));
		}

		@Override
		public Expression caseAstExpressionInteger(AstExpressionInteger expr) {
			return new IntExpr(expr.getValue());
		}

		@Override
		public Expression caseAstExpressionString(AstExpressionString expr) {
			return new StringExpr(expr.getValue());
		}

		@Override
		public Expression caseAstExpressionUnary(AstExpressionUnary expr) {
			UnaryOp op = UnaryOp.getOperator(expr.getUnaryOperator());
			Expression subExpr = evalExpression(expr.getExpression());
			return new UnaryExpr(op, subExpr, null);
		}

		@Override
		public Expression caseAstExpressionVariable(AstExpressionVariable expr) {
			AstVariable variable = expr.getValue().getVariable();
			variable.toString();
			return null;
		}

	}

	private class ExpressionTransformer extends CalSwitch<Expression> {

	}

	private class TypeTransformer extends CalSwitch<Type> {

		@Override
		public Type caseAstTypeBool(AstTypeBool type) {
			return new BoolType();
		}

		@Override
		public Type caseAstTypeInt(AstTypeInt type) {
			Expression size = evalExpression(type.getSize());
			return new IntType(size);
		}

		@Override
		public Type caseAstTypeList(AstTypeList listType) {
			Type type = transformType(listType.getType());
			Expression size = evalExpression(listType.getSize());
			return new ListType(size, type);
		}

		@Override
		public Type caseAstTypeUint(AstTypeUint type) {
			Expression size = evalExpression(type.getSize());
			return new UintType(size);
		}

	}

	final private ExpressionEvaluator exprEvaluator;

	final private ExpressionTransformer exprTransformer;

	private String file;

	private Map<AstPort, Port> portMap;

	final private TypeTransformer typeTransformer;

	private Map<AstVariable, Variable> variableMap;

	public AstToIR() {
		portMap = new HashMap<AstPort, Port>();
		variableMap = new HashMap<AstVariable, Variable>();
		variableMap.toString();

		exprEvaluator = new ExpressionEvaluator();
		exprTransformer = new ExpressionTransformer();
		typeTransformer = new TypeTransformer();
	}

	private Expression evalExpression(AstExpression expr) {
		return exprEvaluator.doSwitch(expr);
	}

	private Location getLocation(EObject object) {
		CompositeNode node = NodeUtil.getNode(object);
		return new Location(node.getLine(), 0, node.getLength());
	}

	/**
	 * Transforms the given AST Actor to an IR actor.
	 * 
	 * @param file
	 *            the .cal file where the actor is defined
	 * @param astActor
	 *            the AST of the actor
	 * @return the actor in IR form
	 */
	public Actor transform(String file, AstActor astActor) {
		this.file = file;

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
		return exprTransformer.doSwitch(expr);
	}

	private OrderedMap<Port> transformPorts(List<AstPort> portList) {
		OrderedMap<Port> ports = new OrderedMap<Port>();
		for (AstPort aPort : portList) {
			Location location = getLocation(aPort);
			Type type = transformType(aPort.getType());
			Port port = new Port(location, type, aPort.getName());
			portMap.put(aPort, port);
			ports.add(file, location, port.getName(), port);
		}

		return ports;
	}

	private Type transformType(AstType aType) {
		return typeTransformer.doSwitch(aType);
	}

}
