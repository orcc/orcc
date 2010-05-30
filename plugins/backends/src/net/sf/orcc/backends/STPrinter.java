/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.FloatType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.network.Instance;

import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.debug.DebugST;

/**
 * This class defines a printer that uses StringTemplate.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class STPrinter {

	private class BooleanRenderer implements AttributeRenderer {

		@Override
		public String toString(Object o, String formatString, Locale locale) {
			return STPrinter.this.toString((Boolean) o);
		}

	}

	private class ExpressionRenderer implements AttributeRenderer {

		@Override
		public String toString(Object o, String formatString, Locale locale) {
			return STPrinter.this.toString((Expression) o);
		}

	}

	private class IntegerRenderer implements AttributeRenderer {

		@Override
		public String toString(Object o, String formatString, Locale locale) {
			return STPrinter.this.toString((Integer) o);
		}

	}

	private class ListRenderer implements AttributeRenderer {

		@Override
		public String toString(Object o, String formatString, Locale locale) {
			return STPrinter.this.toString((List<?>) o);
		}

	}

	private class StringRenderer implements AttributeRenderer {

		@Override
		public String toString(Object o, String formatString, Locale locale) {
			// only calls toString when format is "constant"
			// first tests for null because it is faster
			if (formatString != null && "constant".equals(formatString)) {
				return STPrinter.this.toString((String) o);
			} else {
				return (String) o;
			}
		}

	}

	private class TypeRenderer implements AttributeRenderer {

		@Override
		public String toString(Object o, String formatString, Locale locale) {
			return STPrinter.this.toString((Type) o);
		}

	}

	final protected STGroup group;

	/**
	 * Creates a new StringTemplate printer with the given template group name.
	 * 
	 * @param groupNames
	 *            names of the template groups
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	protected STPrinter(String... groupNames) {
		group = TemplateGroupLoader.loadGroup(groupNames);

		// set to "true" to inspect template
		group.debug = false;

		// register renderers
		group.registerRenderer(Boolean.class, new BooleanRenderer());
		group.registerRenderer(Integer.class, new IntegerRenderer());
		group.registerRenderer(List.class, new ListRenderer());
		group.registerRenderer(String.class, new StringRenderer());

		/*Class<?>[] classesINameable = { Procedure.class, GlobalVariable.class,
				LocalVariable.class, Port.class, StateVariable.class,
				Variable.class };
		AttributeRenderer renderer = new INameableRenderer();
		for (Class<?> clasz : classesINameable) {
			group.registerRenderer(clasz, renderer);
		}*/

		Class<?>[] classesExpression = { BinaryExpr.class, BoolExpr.class,
				IntExpr.class, ListExpr.class, StringExpr.class,
				UnaryExpr.class, VarExpr.class };
		AttributeRenderer renderer = new ExpressionRenderer();
		for (Class<?> clasz : classesExpression) {
			group.registerRenderer(clasz, renderer);
		}

		Class<?>[] classesType = { BoolType.class, FloatType.class,
				IntType.class, ListType.class, StringType.class,
				UintType.class, VoidType.class };
		renderer = new TypeRenderer();
		for (Class<?> clasz : classesType) {
			group.registerRenderer(clasz, renderer);
		}
	}

	/**
	 * Prints the given actor to a file whose name is given.
	 * 
	 * @param fileName
	 *            output file name
	 * @param actor
	 *            the actor
	 * @throws IOException
	 */
	public void printActor(String fileName, Actor actor) throws IOException {
		if (!actor.isSystem()) {
			if (group.debug) {
				DebugST template = (DebugST) group.getInstanceOf("actor");
				template.add("actor", actor);
				template.inspect();
			} else {
				ST template = group.getInstanceOf("actor");
				template.add("actor", actor);

				byte[] b = template.render(80).getBytes();
				OutputStream os = new FileOutputStream(fileName);
				os.write(b);
				os.close();
			}
		}
	}

	/**
	 * Prints the given instance to a file whose name is given.
	 * 
	 * @param fileName
	 *            output file name
	 * @param instance
	 *            the instance
	 * @throws IOException
	 */
	public void printInstance(String fileName, Instance instance)
			throws IOException {
		if (!instance.isActor() || !instance.getActor().isSystem()) {
			ST template = group.getInstanceOf("instance");

			template.add("instance", instance);

			byte[] b = template.render(80).getBytes();
			OutputStream os = new FileOutputStream(fileName);
			os.write(b);
			os.close();
		}
	}

	protected String toString(Boolean bool) {
		return bool.toString();
	}

	protected abstract String toString(Expression expression);

	protected String toString(Integer integer) {
		return integer.toString();
	}

	protected String toString(List<?> list) {
		// set instance of list template as current template
		ST template = group.getInstanceOf("listValue");
		for (Object cst : list) {
			template.add("value", cst.toString());
		}

		// restore previous template as current template, and set attribute
		// "value" to the instance of the list template
		return template.render(80);
	}

	protected String toString(String string) {
		StringBuilder builder = new StringBuilder();
		builder.append('"');
		builder.append(string.replaceAll("\\\\", "\\\\\\\\"));
		builder.append('"');
		return builder.toString();
	}

	protected abstract String toString(Type type);

}
