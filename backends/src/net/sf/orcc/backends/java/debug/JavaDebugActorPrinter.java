/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.backends.java.debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.backends.c.CActorPrinter;
import net.sf.orcc.backends.c.VarDefPrinter;
import net.sf.orcc.backends.java.JavaConstPrinter;
import net.sf.orcc.backends.java.JavaExprPrinter;
import net.sf.orcc.backends.java.JavaTypePrinter;
import net.sf.orcc.ir.IType;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.StateVariable;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.TypeVisitor;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.OrderedMap;

/**
 * Actor printer.
 * 
 * @author Mathieu Wippliez
 * 
 */
public class JavaDebugActorPrinter extends CActorPrinter {

	private class TypeConstructor implements TypeVisitor {

		private StringBuilder builder;

		private IType type;

		public TypeConstructor(IType type) {
			this.type = type;
		}

		@Override
		public String toString() {
			builder = new StringBuilder();
			type.accept(this);
			return builder.toString();
		}

		@Override
		public void visit(BoolType type) {
			builder.append("new BoolType()");
		}

		@Override
		public void visit(IntType type) {
			builder.append("new IntType(" + type.getSize() + ")");
		}

		@Override
		public void visit(ListType type) {
			builder.append("new ListType(" + type.getSize() + ", ");
			type.getElementType().accept(this);
			builder.append(")");
		}

		@Override
		public void visit(StringType type) {
			builder.append("new StringType()");
		}

		@Override
		public void visit(UintType type) {
			builder.append("new UintType(" + type.getSize() + ")");
		}

		@Override
		public void visit(VoidType type) {
			builder.append("new VoidType()");
		}

	}

	/**
	 * Creates a new network printer with the template "Java_actor.stg".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public JavaDebugActorPrinter() throws IOException {
		super("Java_actor_debug");
		constPrinter = new JavaConstPrinter(group);
		typePrinter = new JavaTypePrinter();
		varDefPrinter = new VarDefPrinter(typePrinter);
		exprPrinter = new JavaExprPrinter(varDefPrinter);
	}

	private void setActionLocation(List<Action> actions) {
		Map<String, List<Integer>> locationMap = new TreeMap<String, List<Integer>>();
		for (Action action : actions) {
			Location location = action.getBody().getLocation();
			List<Integer> list = new ArrayList<Integer>();
			list.add(location.getStartLine());
			list.add(location.getStartColumn());
			list.add(location.getEndColumn());
			locationMap.put(action.toString(), list);
		}

		template.setAttribute("actionLoc", locationMap);
	}

	@Override
	protected void setAttributes(String id, Actor actor) {
		super.setAttributes(id, actor);
		String res = actor.getFile().replaceAll("\\\\", "\\\\\\\\");
		template.setAttribute("file", res);

		setActionLocation(actor.getActions());
		setStateVariables(actor.getStateVars());
	}

	private void setStateVariables(OrderedMap<Variable> stateVars) {
		Map<String, TypeVisitor> vars = new TreeMap<String, TypeVisitor>();
		for (Variable variable : stateVars) {
			StateVariable stateVar = (StateVariable) variable;

			String name = stateVar.getName();
			IType type = stateVar.getType();
			vars.put(name, new TypeConstructor(type));
		}

		template.setAttribute("stateVars_DEBUG", vars);
	}
}
