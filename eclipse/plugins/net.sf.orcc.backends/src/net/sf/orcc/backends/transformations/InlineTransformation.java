/*
 * Copyright (c) 2010, IRISA
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
import java.util.ListIterator;
import java.util.Map;

import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines an actor transformation that inline the functions and/or
 * the procedures
 * 
 * @author Herve Yviquel
 * 
 */
public class InlineTransformation extends AbstractActorVisitor<Object> {

	private boolean inlineFunction;

	private boolean inlineProcedure;

	private boolean needToSkipThisNode;

	// private Var returnVariableOfCurrentFunction;

	protected Map<Var, Var> variableToLocalVariableMap;

	public InlineTransformation(boolean inlineProcedure, boolean inlineFunction) {
		this.inlineProcedure = inlineProcedure;
		this.inlineFunction = inlineFunction;
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
				copyDef.setVariable(variableToLocalVariableMap.get(def
						.getVariable()));
			} else if (object instanceof Use) {
				Use use = (Use) object;
				Use copyUse = (Use) copier.get(use);
				copyUse.setVariable(variableToLocalVariableMap.get(use
						.getVariable()));
			}
		}

		return clonedNodes;
	}

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
					procedure.getName() + "_" + var.getName() + "_"
							+ call.getLocation().getStartLine() + "_"
							+ call.getLocation().getStartColumn());
			newVar.setIndex(var.getIndex());
			newVar.setLocation(var.getLocation());
			newVar.setAssignable(var.isAssignable());
			variableToLocalVariableMap.put(var, newVar);
		}
		for (Var var : function.getParameters()) {
			if (var.getType().isList()) {
				// In case of list, the parameter could be a global variable
				Var newVar = ((ExprVar) call.getParameters().get(
						function.getParameters().indexOf(var))).getUse()
						.getVariable();
				variableToLocalVariableMap.put(var, newVar);
			} else {
				Var newVar = procedure.newTempLocalVariable(var.getType(),
						procedure.getName() + "_" + var.getName() + "_"
								+ call.getLocation().getStartLine() + "_"
								+ call.getLocation().getStartColumn());
				newVar.setIndex(var.getIndex());
				newVar.setLocation(var.getLocation());
				newVar.setAssignable(var.isAssignable());
				variableToLocalVariableMap.put(var, newVar);
			}
		}

		List<Node> nodes = new ArrayList<Node>();

		// Assign all parameters except for list
		NodeBlock newBlockNode = IrFactory.eINSTANCE.createNodeBlock();
		for (int i = 0; i < function.getParameters().size(); i++) {
			Var parameter = function.getParameters().get(i);
			if (!parameter.getType().isList()) {
				Expression expr = call.getParameters().get(i);
				InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
						variableToLocalVariableMap.get(parameter), expr);
				newBlockNode.add(assign);
			}
		}
		if (newBlockNode.getInstructions().size() > 0) {
			nodes.add(newBlockNode);
		}

		// Clone function/procedure body
		nodes.addAll(cloneNodes(function.getNodes()));

		// Remove old block and add the new ones
		NodeBlock secondBlockNodePart = IrFactory.eINSTANCE.createNodeBlock();

		itInstruction.remove();
		while (itInstruction.hasNext()) {
			secondBlockNodePart.add(itInstruction.next());
			itInstruction.remove();
		}

		nodes.add(secondBlockNodePart);

		for (Node node : nodes) {
			itNode.add(node);
		}

		needToSkipThisNode = true;
	}

	public boolean isInlineFunction() {
		return inlineFunction;
	}

	public boolean isInlineProcedure() {
		return inlineProcedure;
	}

	public void setInlineFunction(boolean inlineFunction) {
		this.inlineFunction = inlineFunction;
	}

	public void setInlineProcedure(boolean inlineProcedure) {
		this.inlineProcedure = inlineProcedure;
	}

	@Override
	public void visit(InstCall call) {
		// Function case
		if (!call.getProcedure().getReturnType().isVoid() && inlineFunction) {
			//returnVariableOfCurrentFunction = call.getTarget().getVariable();
			inline(call);
			//returnVariableOfCurrentFunction = null;
		}

		// Procedure case
		if (call.getProcedure().getReturnType().isVoid() && inlineProcedure) {
			inline(call);
		}
	}

	@Override
	public void visit(NodeBlock nodeBlock) {
		ListIterator<Instruction> it = nodeBlock.listIterator();
		needToSkipThisNode = false;
		while (it.hasNext() && !needToSkipThisNode) {
			Instruction instruction = it.next();
			itInstruction = it;
			instruction.accept(this);
		}
		if (needToSkipThisNode) {
			itNode.previous();
		}
	}

	@Override
	public void visit(NodeIf nodeIf) {
		ListIterator<Node> oldNodeIterator = itNode;
		visit(nodeIf.getThenNodes());
		visit(nodeIf.getElseNodes());
		itNode = oldNodeIterator;
		visit(nodeIf.getJoinNode());
	}

	@Override
	public void visit(NodeWhile nodeWhile) {
		ListIterator<Node> oldNodeIterator = itNode;
		visit(nodeWhile.getNodes());
		itNode = oldNodeIterator;
		visit(nodeWhile.getJoinNode());
	}

}
