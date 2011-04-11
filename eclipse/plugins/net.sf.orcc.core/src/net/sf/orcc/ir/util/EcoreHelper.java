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
package net.sf.orcc.ir.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Use;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class contains several methods to help the manipulation of EMF models.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class EcoreHelper {

	/**
	 * Returns a deep copy of the given expressions, and updates uses.
	 * 
	 * @param expressions
	 *            a list of expressions
	 * @return a deep copy of the given expressions with uses correctly updated
	 */
	public static Collection<Expression> copy(Collection<Expression> expressions) {
		Copier copier = new Copier();
		Collection<Expression> result = copier.copyAll(expressions);
		copier.copyReferences();

		TreeIterator<EObject> it = EcoreUtil.getAllContents(expressions, true);
		while (it.hasNext()) {
			EObject obj = it.next();

			if (obj instanceof Use) {
				Use use = (Use) obj;
				Use copyUse = (Use) copier.get(use);
				copyUse.setVariable(use.getVariable());
			}
		}

		return result;
	}

	/**
	 * Returns a deep copy of the given expression, and updates uses.
	 * 
	 * @param expression
	 *            an expression
	 * @return a deep copy of the given expression with uses correctly updated
	 */
	public static Expression copy(Expression expression) {
		Copier copier = new Copier();
		Expression result = (Expression) copier.copy(expression);
		copier.copyReferences();

		TreeIterator<EObject> it = EcoreUtil.getAllContents(expression, true);
		while (it.hasNext()) {
			EObject obj = it.next();

			if (obj instanceof Use) {
				Use use = (Use) obj;
				Use copyUse = (Use) copier.get(use);
				copyUse.setVariable(use.getVariable());
			}
		}

		return result;
	}

	/**
	 * Removes the uses of the given expression and removes the expression
	 * itself from its container.
	 * 
	 * @param expression
	 *            an expression
	 */
	public static void delete(Expression expression) {
		TreeIterator<EObject> it = expression.eAllContents();
		while (it.hasNext()) {
			EObject descendant = it.next();
			if (descendant instanceof Use) {
				Use use = (Use) descendant;
				use.setVariable(null);
			}
		}

		EcoreUtil.remove(expression);
	}

	/**
	 * Removes the uses of the given instruction, removes the definition (if it
	 * has one), and finally removes the instruction itself from its container.
	 * 
	 * @param instruction
	 *            an instruction
	 */
	public static void delete(Instruction instruction) {
		TreeIterator<EObject> it = instruction.eAllContents();
		while (it.hasNext()) {
			EObject descendant = it.next();
			if (descendant instanceof Use) {
				Use use = (Use) descendant;
				use.setVariable(null);
			}
		}

		for (EObject eObject : instruction.eContents()) {
			if (eObject instanceof Def) {
				Def def = (Def) eObject;
				def.setVariable(null);
			}
		}

		EcoreUtil.remove(instruction);
	}

	/**
	 * Deletes the given expressions.
	 * 
	 * @param expressions
	 *            a list of expressions
	 */
	public static void delete(List<Expression> expressions) {
		while (!expressions.isEmpty()) {
			delete(expressions.get(0));
		}
	}

	/**
	 * Returns the container of <code>ele</code> with the given type, or
	 * <code>null</code> if no such container exists. This method has been
	 * copied from the EcoreUtil2 class of Xtext.
	 * 
	 * @param <T>
	 *            type parameter
	 * @param ele
	 *            an object
	 * @param type
	 *            the type of the container
	 * @return the container of <code>ele</code> with the given type
	 */
	@SuppressWarnings("unchecked")
	public static <T extends EObject> T getContainerOfType(EObject ele,
			Class<T> type) {
		if (type.isAssignableFrom(ele.getClass())) {
			return (T) ele;
		}

		if (ele.eContainer() != null) {
			return getContainerOfType(ele.eContainer(), type);
		}

		return null;
	}

	public static List<Use> getUses(EObject eObject) {
		List<Use> uses = new ArrayList<Use>();
		TreeIterator<EObject> it = eObject.eAllContents();
		while (it.hasNext()) {
			EObject descendant = it.next();
			if (descendant instanceof Use) {
				uses.add((Use) descendant);
			}
		}

		return uses;
	}

}
