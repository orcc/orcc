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
package net.sf.orcc.cal.ui.labeling;

import java.util.Iterator;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstState;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.ExpressionBinary;
import net.sf.orcc.cal.cal.ExpressionBoolean;
import net.sf.orcc.cal.cal.ExpressionCall;
import net.sf.orcc.cal.cal.ExpressionFloat;
import net.sf.orcc.cal.cal.ExpressionIf;
import net.sf.orcc.cal.cal.ExpressionIndex;
import net.sf.orcc.cal.cal.ExpressionInteger;
import net.sf.orcc.cal.cal.ExpressionList;
import net.sf.orcc.cal.cal.ExpressionString;
import net.sf.orcc.cal.cal.ExpressionUnary;
import net.sf.orcc.cal.cal.ExpressionVariable;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.Import;
import net.sf.orcc.cal.cal.Inequality;
import net.sf.orcc.cal.cal.InputPattern;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

/**
 * Provides labels for a EObjects.
 * 
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class CalLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public CalLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	public String image(AstActor actor) {
		return "orcc.png";
	}

	public String image(AstEntity entity) {
		return "package_obj.gif";
	}

	public String image(Import import_) {
		return "imp_obj.gif";
	}

	public String text(AstAction action) {
		AstTag tag = action.getTag();
		if (tag == null) {
			return "(untagged)";
		} else {
			return getText(tag);
		}
	}

	public String text(AstActor actor) {
		return null;
	}

	public String text(AstState state) {
		return state.getName();
	}

	public String text(AstTag tag) {
		return OrccUtil.toString(tag.getIdentifiers(), ".");
	}

	public String text(AstTransition transition) {
		return getText(transition.getSource()) + " ("
				+ getText(transition.getTag()) + ") --> "
				+ getText(transition.getTarget());
	}

	public String text(AstTypeBool type) {
		return "bool";
	}

	public String text(AstTypeFloat type) {
		return "float";
	}

	public String text(AstTypeInt type) {
		AstExpression size = type.getSize();
		return size == null ? "int" : "int(size=" + getText(size) + ")";
	}

	public String text(AstTypeList type) {
		return "List(type:" + getText(type.getType()) + ", size="
				+ getText(type.getSize()) + ")";
	}

	public String text(AstTypeUint type) {
		AstExpression size = type.getSize();
		return size == null ? "uint" : "uint(size=" + getText(size) + ")";
	}

	public String text(AstUnit unit) {
		return null;
	}

	public String text(ExpressionBinary expr) {
		return getText(expr.getLeft()) + " " + expr.getOperator() + " "
				+ getText(expr.getRight());
	}

	public String text(ExpressionBoolean expr) {
		return String.valueOf(expr.isValue());
	}

	public String text(ExpressionCall expr) {
		StringBuilder builder = new StringBuilder(expr.getFunction().getName());
		builder.append("(");
		Iterator<AstExpression> it = expr.getParameters().iterator();
		if (it.hasNext()) {
			AstExpression param = it.next();
			builder.append(getText(param));
			while (it.hasNext()) {
				builder.append(", ");
				builder.append(getText(it.next()));
			}
		}
		builder.append(")");
		return builder.toString();
	}

	public String text(ExpressionFloat expr) {
		return String.valueOf(expr.getValue());
	}

	public String text(ExpressionIf expr) {
		return "if " + getText(expr.getCondition()) + " then "
				+ getText(expr.getThen()) + " else " + getText(expr.getElse())
				+ " end";
	}

	public String text(ExpressionIndex expr) {
		StringBuilder builder = new StringBuilder(expr.getSource()
				.getVariable().getName());
		for (AstExpression index : expr.getIndexes()) {
			builder.append('[');
			builder.append(getText(index));
			builder.append(']');
		}
		return builder.toString();
	}

	public String text(ExpressionInteger expr) {
		return String.valueOf(expr.getValue());
	}

	public String text(ExpressionList expr) {
		StringBuilder builder = new StringBuilder('[');
		Iterator<AstExpression> it = expr.getExpressions().iterator();
		if (it.hasNext()) {
			AstExpression element = it.next();
			builder.append(getText(element));
			while (it.hasNext()) {
				builder.append(", ");
				builder.append(getText(it.next()));
			}
		}

		Iterator<Generator> itG = expr.getGenerators().iterator();
		if (itG.hasNext()) {
			builder.append(" : ");
			Generator generator = itG.next();
			builder.append(getText(generator));
			while (itG.hasNext()) {
				builder.append(", ");
				builder.append(getText(itG.next()));
			}
		}

		builder.append(']');
		return builder.toString();
	}

	public String text(ExpressionString expr) {
		return String.valueOf(expr.getValue());
	}

	public String text(ExpressionUnary expr) {
		return expr.getUnaryOperator() + " " + getText(expr.getExpression());
	}

	public String text(ExpressionVariable expr) {
		return expr.getValue().getVariable().getName();
	}

	public String text(Function function) {
		StringBuilder builder = new StringBuilder("function ");
		builder.append(function.getName());
		builder.append("(");

		Iterator<Variable> it = function.getParameters().iterator();
		if (it.hasNext()) {
			Variable param = it.next();
			builder.append(getText(param));
			while (it.hasNext()) {
				builder.append(", ");
				builder.append(getText(it.next()));
			}
		}
		builder.append(") --> ");
		builder.append(getText(function.getType()));
		return builder.toString();
	}

	public String text(Generator generator) {
		return "for " + getText(generator.getVariable()) + " in "
				+ getText(generator.getLower()) + " .. "
				+ getText(generator.getHigher());
	}

	public String text(Inequality inequality) {
		Iterator<AstTag> it = inequality.getTags().iterator();
		StringBuilder builder = new StringBuilder();
		if (it.hasNext()) {
			builder.append(getText(it.next()));
			while (it.hasNext()) {
				builder.append(" > ");
				builder.append(getText(it.next()));
			}
			builder.append(';');
		}

		return builder.toString();
	}

	public String text(Variable variable) {
		AstType type = variable.getType();
		if (type == null) {
			InputPattern pattern = EcoreUtil2.getContainerOfType(variable,
					InputPattern.class);
			if (pattern != null) {
				type = pattern.getPort().getType();
			}
		}

		StringBuilder builder = new StringBuilder(getText(type));
		builder.append(" ");
		builder.append(variable.getName());

		for (AstExpression dim : variable.getDimensions()) {
			builder.append('[');
			builder.append(getText(dim));
			builder.append(']');
		}

		if (variable.getValue() != null) {
			builder.append(" ");
			if (!variable.isConstant()) {
				builder.append(":");
			}
			builder.append("= ");
			builder.append(getText(variable.getValue()));
		}
		return builder.toString();
	}

}
