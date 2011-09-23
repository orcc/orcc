/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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

import static net.sf.orcc.cal.cal.CalPackage.eINSTANCE;

import java.util.Iterator;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstState;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Import;
import net.sf.orcc.cal.cal.Inequality;
import net.sf.orcc.cal.cal.InputPattern;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.services.Evaluator;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.ir.util.IrSwitch;
import net.sf.orcc.ir.util.TypePrinter;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

/**
 * This class provides labels for AST nodes. Some fields use the computed IR
 * (e.g. types and values) to give more information.
 * 
 * @author Matthieu Wipliez
 */
public class CalLabelProvider extends DefaultEObjectLabelProvider {

	private static class DimensionsPrinter extends IrSwitch<String> {

		@Override
		public String caseTypeList(TypeList type) {
			StringBuilder builder = new StringBuilder();
			builder.append('[');
			builder.append(new ExpressionPrinter().doSwitch(type.getSizeExpr()));
			builder.append(']');

			String innerDim = doSwitch(type.getType());
			if (innerDim != null) {
				builder.append(innerDim);
			}

			return builder.toString();
		}

	}

	private static class ElementTypePrinter extends TypePrinter {

		@Override
		public String caseTypeList(TypeList type) {
			return doSwitch(type.getType());
		}

	}

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

	public String text(AstPort port) {
		Type type = Typer.getType(port);
		String eltType = new ElementTypePrinter().doSwitch(type);
		StringBuilder builder = new StringBuilder(eltType);
		builder.append(" ");
		builder.append(port.getName());
		return builder.toString();
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

	public String text(AstUnit unit) {
		return null;
	}

	public String text(Function function) {
		StringBuilder builder = new StringBuilder(function.getName());
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

		Type type = Typer.getType(function);
		builder.append(new TypePrinter().doSwitch(type));
		return builder.toString();
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
		Type type = Typer.getType(variable);
		if (type == null) {
			InputPattern pattern = EcoreUtil2.getContainerOfType(variable,
					InputPattern.class);
			if (pattern != null) {
				type = Typer.getType(pattern.getPort());
			}
		}

		// base type and name (prints C-like types)
		String eltType = new ElementTypePrinter().doSwitch(type);
		StringBuilder builder = new StringBuilder(eltType);
		builder.append(" ");
		builder.append(variable.getName());

		// dimensions
		String dimensions = new DimensionsPrinter().doSwitch(type);
		if (dimensions != null) {
			builder.append(dimensions);
		}

		// prints value for state variables and constants in units
		EStructuralFeature feature = variable.eContainingFeature();
		if ((feature == eINSTANCE.getAstUnit_Variables() || feature == eINSTANCE
				.getAstActor_StateVariables()) && variable.getValue() != null) {
			builder.append(" ");
			if (!variable.isConstant()) {
				builder.append(":");
			}
			builder.append("= ");

			// prints the evaluated value
			Expression expr = Evaluator.getValue(variable);
			builder.append(new ExpressionPrinter().doSwitch(expr));
		}

		return builder.toString();
	}

}
