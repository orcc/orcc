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
package net.sf.orcc.backends.llvm.transforms;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNodeVisitor;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.GetElementPtrNode;
import net.sf.orcc.backends.llvm.type.PointType;
import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.common.Use;
import net.sf.orcc.common.Variable;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.transforms.IActorTransformation;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.ir.type.ListType;

/**
 * Change every load on array into getElementPtrNode.
 * 
 * @author Jérôme GORIN
 * 
 */
public class ArrayListTransformation extends AbstractLLVMNodeVisitor implements
		IActorTransformation {

	int indexName;

	@SuppressWarnings("unchecked")
	public Use getElementPtrNodeCreate(Use varList, List<IExpr> indexes,
			Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];

		it.previous();

		IType listType;

		// Adding the getElementPtrNode
		Variable varDefList = varList.getVariable();
		listType = varDefList.getType();
		while (listType.getType() == IType.LIST) {
			listType = ((ListType) listType).getElementType();
		}

		String name = varDefList.getName();
		LocalVariable varDef = new LocalVariable(false, indexName++,
				new Location(), name, null, null, new PointType(listType));
		Use localUse = new Use(varDef);

		// Create and insert the new node
		GetElementPtrNode elementPtrNode = new GetElementPtrNode(0,
				new Location(), varDef, varList, indexes);

		it.add(elementPtrNode);
		it.next();

		return localUse;
	}

	@Override
	public void transform(Actor actor) {
		for (Procedure proc : actor.getProcs()) {
			visitProc(proc);
		}

		for (Action action : actor.getActions()) {
			visitProc(action.getBody());
			visitProc(action.getScheduler());
		}

		for (Action action : actor.getInitializes()) {
			visitProc(action.getBody());
			visitProc(action.getScheduler());
		}
	}

	@Override
	public void visit(BrNode node, Object... args) {
		visitNodes(node.getThenNodes());
		visitNodes(node.getElseNodes());
	}

	public void visit(LoadNode node, Object... args) {
		List<IExpr> indexes = node.getIndexes();

		if (indexes.size() > 0) {
			IExpr expr = indexes.get(0);
			try {
				// Check if the array is an only value
				if ((Integer.parseInt(expr.toString()) == 0)
						&& (indexes.size() == 1)) {
					return;
				}
			} catch (NumberFormatException e) {
			}

			// Insert the new VarDef in the load node
			node.setSource(getElementPtrNodeCreate(node.getSource(), indexes,
					args));
		}
	}

	public void visit(StoreNode node, Object... args) {

		List<IExpr> indexes = node.getIndexes();

		if (indexes.size() > 0) {
			IExpr expr = indexes.get(0);
			try {
				// Check if the array is an only value
				if ((Integer.parseInt(expr.toString()) == 0)
						&& (indexes.size() == 1)) {
					return;
				}
			} catch (NumberFormatException e) {
			}

			// Insert the new VarDef in the store node
			node.setTarget(getElementPtrNodeCreate(node.getTarget(), node
					.getIndexes(), args));
		}
	}

	private void visitNodes(List<AbstractNode> nodes) {
		ListIterator<AbstractNode> it = nodes.listIterator();

		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	private void visitProc(Procedure proc) {
		indexName = 0;
		List<AbstractNode> nodes = proc.getNodes();

		visitNodes(nodes);
	}
}
