/*
 * Copyright (c) 2010-2011, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.transformations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.EcoreHelper;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines an actor transformation that inline the functions and/or
 * the procedures
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * 
 */
public class Inliner extends AbstractActorVisitor<Object> {

	private boolean inlineFunction;

	private boolean inlineProcedure;

	protected Map<Var, Var> variableToLocalVariableMap;

	public Inliner(boolean inlineProcedure, boolean inlineFunction) {
		this.inlineProcedure = inlineProcedure;
		this.inlineFunction = inlineFunction;
	}

	@Override
	public Object caseInstCall(InstCall call) {
		// Function case
		if (!call.getProcedure().getReturnType().isVoid() && inlineFunction) {
			inline(call);
		}

		// Procedure case
		if (call.getProcedure().getReturnType().isVoid() && inlineProcedure) {
			inline(call);
		}
		return null;
	}

	/**
	 * Clones the given nodes and replace every def/use with a def/use of the
	 * variable mapped in the variableToLocalVariableMap.
	 * 
	 * @param nodes
	 *            a collection of Nodes
	 * @return a collection of cloned Nodes
	 */
	private Collection<Node> cloneNodes(Collection<Node> nodes) {
		Copier copier = new Copier();
		Collection<Node> clonedNodes = copier.copyAll(nodes);
		copier.copyReferences();

		TreeIterator<EObject> it = EcoreUtil.getAllContents(nodes);
		while (it.hasNext()) {
			EObject object = it.next();

			if (object instanceof Def) {
				Def def = (Def) object;
				Def copyDef = (Def) copier.get(def);
				Var oldVar = def.getVariable();
				Var newVar = oldVar.isLocal() ? variableToLocalVariableMap
						.get(oldVar) : oldVar;
				copyDef.setVariable(newVar);
			} else if (object instanceof Use) {
				Use use = (Use) object;
				Use copyUse = (Use) copier.get(use);
				Var oldVar = use.getVariable();
				Var newVar = oldVar.isLocal() ? variableToLocalVariableMap
						.get(oldVar) : oldVar;
				copyUse.setVariable(newVar);
			}
		}

		return clonedNodes;
	}

	/**
	 * Inline the called function at call location. All variables of the inlined
	 * function are copied in the local procedure.
	 * 
	 * @param call
	 *            a call instruction to inline
	 */
	private void inline(InstCall call) {
		// The function or the procedure
		Procedure function = call.getProcedure();

		// Set the function/procedure to external thus it will not be printed
		function.setNative(true);

		// Create a new local variable to all function/procedure's variable
		// except for list (reference is using)
		variableToLocalVariableMap = new HashMap<Var, Var>();
		for (Var var : function.getLocals()) {
			Var newVar = procedure.newTempLocalVariable(var.getType(),
					"inlined_" + var.getName());
			newVar.setIndex(var.getIndex());
			newVar.setLineNumber(var.getLineNumber());
			newVar.setAssignable(var.isAssignable());
			variableToLocalVariableMap.put(var, newVar);
		}
		for (Param param : function.getParameters()) {
			Var newVar;
			Var var = param.getVariable();
			if (var.getType().isList()) {
				// In case of list, the parameter could be a global variable
				newVar = ((ExprVar) call.getParameters().get(
						function.getParameters().indexOf(var))).getUse()
						.getVariable();
			} else {
				newVar = procedure.newTempLocalVariable(var.getType(),
						"inlined_" + var.getName());
				newVar.setIndex(var.getIndex());
				newVar.setLineNumber(var.getLineNumber());
				newVar.setAssignable(var.isAssignable());

			}
			variableToLocalVariableMap.put(var, newVar);
		}

		// Assign all parameters except for list
		NodeBlock parametersBlock = IrFactory.eINSTANCE.createNodeBlock();
		for (int i = 0; i < function.getParameters().size(); i++) {
			Var parameter = function.getParameters().get(i).getVariable();
			if (!parameter.getType().isList()) {
				Arg arg = call.getParameters().get(i);
				if (arg.isByVal()) {
					Expression expr = ((ArgByVal) arg).getValue();
					InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
							variableToLocalVariableMap.get(parameter),
							IrUtil.copy(expr));
					parametersBlock.add(assign);
				}
			}
		}

		// Clone function/procedure body
		Collection<Node> clonedNodes = cloneNodes(function.getNodes());
		transformInstReturn(clonedNodes, call.getTarget());

		// Cut the block containing call instruction in two parts to put
		// inlined nodes between them
		NodeBlock currentBlock = EcoreHelper.getContainerOfType(call,
				NodeBlock.class);
		NodeBlock followingBlock = IrFactory.eINSTANCE.createNodeBlock();
		while (indexInst < currentBlock.getInstructions().size()) {
			followingBlock.add(currentBlock.getInstructions().get(indexInst));
		}

		// Add all inlined blocks
		Collection<Node> inlinedNodes = new ArrayList<Node>();
		inlinedNodes.add(parametersBlock);
		inlinedNodes.addAll(clonedNodes);
		inlinedNodes.add(followingBlock);

		List<Node> currentNodes = EcoreHelper.getContainingList(currentBlock);
		currentNodes.addAll(indexNode + 1, inlinedNodes);

		// Remove useless call instruction
		IrUtil.delete(call);
	}

	/**
	 * Replace the return instruction by an assignment to the given variable
	 * 
	 * @param nodes
	 *            a collection of Nodes
	 * @param callTarget
	 *            a variable
	 */
	private void transformInstReturn(Collection<Node> nodes, Def callTarget) {
		TreeIterator<EObject> it = EcoreUtil.getAllContents(nodes);
		while (it.hasNext()) {
			EObject object = it.next();

			if (object instanceof InstReturn) {
				InstReturn instReturn = (InstReturn) object;
				if (callTarget != null) {
					InstAssign instAssign = IrFactory.eINSTANCE
							.createInstAssign(callTarget.getVariable(),
									IrUtil.copy(instReturn.getValue()));
					EcoreUtil.replace(instReturn, instAssign);
				}
				IrUtil.delete(instReturn);
			}
		}
	}

}
