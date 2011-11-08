/*
 * Copyright (c) 2011, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Attribute;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.ir.util.TypePrinter;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.common.util.EMap;
import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ModelAdaptor;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.ErrorManager;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

/**
 * This abstract class defines a printer that can be extended to provide
 * model-specific print methods. It provides basic tools such as model adaptors
 * and attribute renderers that may be used by classes that extend it.
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractPrinter {

	protected static class ConnectionModelAdaptor implements ModelAdaptor {

		@Override
		public Object getProperty(Interpreter interp, ST st, Object o,
				Object property, String propertyName)
				throws STNoSuchPropertyException {
			String name = String.valueOf(property);
			Attribute attribute = ((Connection) o).getAttribute(name);
			return (attribute == null) ? null : attribute.getValue();
		}

	}

	protected static class EMapModelAdaptor implements ModelAdaptor {

		@Override
		public Object getProperty(Interpreter interp, ST st, Object o,
				Object property, String propertyName)
				throws STNoSuchPropertyException {
			return ((EMap<?, ?>) o).get(property);
		}

	}

	protected class ExpressionRenderer implements AttributeRenderer {

		@Override
		public String toString(Object o, String formatString, Locale locale) {
			return expressionPrinter.doSwitch((Expression) o);
		}

	}

	protected static class InstanceModelAdaptor implements ModelAdaptor {

		@Override
		public Object getProperty(Interpreter interp, ST st, Object o,
				Object property, String propertyName)
				throws STNoSuchPropertyException {
			String name = String.valueOf(property);
			Attribute attribute = ((Instance) o).getAttribute(name);
			return (attribute == null) ? null : attribute.getValue();
		}

	}

	protected class TypeRenderer implements AttributeRenderer {

		@Override
		public String toString(Object o, String formatString, Locale locale) {
			return typePrinter.doSwitch((Type) o);
		}

	}

	private ExpressionPrinter expressionPrinter;

	protected STGroup group;

	private TypePrinter typePrinter;

	/**
	 * Creates a new printer.
	 * 
	 * @param fullPath
	 *            the full path of the template
	 */
	public AbstractPrinter(String fullPath) {
		group = OrccUtil.loadGroup(fullPath,
				AbstractPrinter.class.getClassLoader());
		group.registerRenderer(Expression.class, new ExpressionRenderer());
		group.registerRenderer(Type.class, new TypeRenderer());

		group.registerModelAdaptor(EMap.class, new EMapModelAdaptor());
		group.registerModelAdaptor(Instance.class, new InstanceModelAdaptor());
		group.registerModelAdaptor(Connection.class,
				new ConnectionModelAdaptor());
	}

	protected void printTemplate(ST template, String fileName) {
		try {
			template.write(new File(fileName),
					ErrorManager.DEFAULT_ERROR_LISTENER, "UTF-8", 80);
		} catch (IOException e) {
			new OrccException("I/O error", e);
		}
	}

	/**
	 * Registers a model adaptor for the given types.
	 * 
	 * @param attributeType
	 *            type of attribute
	 * @param adaptor
	 *            adaptor
	 */
	public void registerModelAdaptor(Class<?> attributeType,
			ModelAdaptor adaptor) {
		group.registerModelAdaptor(attributeType, adaptor);
	}

	/**
	 * Registers an attribute renderer for the given types.
	 * 
	 * @param attributeType
	 *            type of attribute
	 * @param renderer
	 *            renderer
	 */
	public void registerRenderer(Class<?> attributeType,
			AttributeRenderer renderer) {
		group.registerRenderer(attributeType, renderer);
	}

	public void setExpressionPrinter(ExpressionPrinter printer) {
		this.expressionPrinter = printer;
	}

	public void setTypePrinter(TypePrinter printer) {
		this.typePrinter = printer;
	}

}
